package com.example.demo.service;

import com.example.demo.db.WithdrawDB;
import com.example.demo.entity.DFModel;
import com.example.demo.entity.DownLoadNum;
import com.example.demo.entity.Withdraw;
import com.example.demo.entity.WithdrawRecord;
import com.example.demo.unionpay.TJYLRun;
import com.example.demo.util.UtilsConstant;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/11/30.
 *
 * @author hadoop
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class WithdrawService {


    @Autowired
    private WithdrawDB withdrawDB;


    private Logger log = LoggerFactory.getLogger(this.getClass());


    public void withdraw(String message) throws Exception {

        JSONObject json = JSONObject.fromObject(message);

        String orderNumber = json.getString("orderNumber");

        Map<String, Object> withdrawMap = withdrawDB.getWithdraw(orderNumber);

        if (withdrawMap == null || withdrawMap.isEmpty()) {
            return;
        }

        Withdraw withdraw;
        try {
            withdraw = UtilsConstant.mapToBean(withdrawMap, Withdraw.class);
        } catch (Exception e) {
            log.info("提现信息转成实体类异常，", e);
            return;
        }

        if (!"0".equals(withdraw.getBalanceFlag())) {
            log.info("该笔订单已经被处理过， 订单编号:" + orderNumber);
            return;
        } else {
            log.info("改订单 balanceFalg 状态为0 没有被处理");
        }

        String userID = withdraw.getApplyUserID();
        Map<String, Object> userMap = withdrawDB.getLoginUserInfo(userID);
        if (userMap == null || userMap.isEmpty()) {
            log.info("用户信息不存在， 用户ID ：" + userID);
            return;
        } else {
            log.info("获取用户信息成功");
        }

        List<Map<String, Object>> list = withdrawDB.blackList(userID);
        if (list != null && list.size() > 0) {
            log.info("该用户为代付黑名单用户：用户ID : " + userID);
            return;
        } else {
            log.info("提现用户没有被设设置黑名单.用户ID : " + userID);
        }


        list = withdrawDB.getDForderInfo(orderNumber);
        if (list != null && list.size() > 0) {
            log.info("该笔订单已经发送代付 ， 提现订单号:" + orderNumber);
            return;
        } else {
            log.info("订单：" + orderNumber + "没有发过代付");
        }


        Date now = new Date();
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String settmentBatchNo = dateFormat.format(now) + "_" + getFileNumber();


        int x = withdrawDB.updateWithdraw(new Object[]{settmentBatchNo, dateFormat2.format(now), withdraw.getID()});


        withdraw.setSettmentBatchNo(settmentBatchNo);
        withdraw.setBalanceDate(dateFormat.format(now));

        int ret = withdrawDB.insertRecord(new Object[]{withdraw.getSettmentBatchNo(), withdraw.getBalanceDate(), orderNumber, withdraw.getBalanceDate(), withdraw.getApplyUserID()});

        if (x == 0 || ret == 0) {
            throw new Exception("保存转记录sql执行返回受影响行数为0" + orderNumber);
        }

        int modifyFlagTwoRet = withdrawDB.modifyWithdrawFlagTwo(new Object[]{withdraw.getBalanceDate(), withdraw.getApplyUserID()});

        if (modifyFlagTwoRet < 1) {
            throw new Exception("更新转账状态不成功" + orderNumber);
        }

        log.info("转账数据处理成功");


        WithdrawRecord withdrawRecord;
        try {
            withdrawRecord = UtilsConstant.mapToBean(withdrawDB.getWithdrawRecord(new Object[]{orderNumber}), WithdrawRecord.class);
        } catch (Exception e) {
            return;
        }

        int dfOrderRet = withdrawDB.savedfOrder(new Object[]{ withdrawRecord.getBankCardNo(), withdrawRecord.getAccountName(), withdrawRecord.getBankName(),
                orderNumber, withdrawRecord.getAmount(), withdrawRecord.getLogin_ID(), "", "TX"});

        if (dfOrderRet < 1) {
            log.info("订单号：" + orderNumber + "保存代付信息失败");
            return;
        }

        log.info("调用代付方法");

        TJYLRun tjyl = new TJYLRun();

        DFModel dfModel = new DFModel();
        dfModel.setOrderNumber(orderNumber);
        dfModel.setTxnAmt(withdrawRecord.getAmount());
        dfModel.setPayName(withdrawRecord.getAccountName());
        dfModel.setTxnTime(DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"));
        dfModel.setBankName(withdrawRecord.getBankName());
        dfModel.setAccNo(withdrawRecord.getBankCardNo());
        boolean flag = tjyl.init(dfModel);

        if(flag){
            log.info("代付发送成功 , " + orderNumber);
            withdrawDB.updateDForder(new Object[]{orderNumber});

            withdrawDB.updateWithdrawRecordStatus(new Object[]{orderNumber});
        } else {
            log.info("发送代付异常" + orderNumber);
        }

        log.info("发送代付结束");

    }


    public String getFileNumber() {
        try {
            DownLoadNum downloadnum = UtilsConstant.mapToBean(withdrawDB.selectFileNum(), DownLoadNum.class);
            Date date = downloadnum.getDate();
            Date now = new Date();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateTime = sdf.format(date);
            String nowTime = sdf.format(now);

            String updateDownloadNumber = "";

            DecimalFormat df1 = new DecimalFormat("0000");

            Integer num = 1;
            if (nowTime.equals(dateTime)) {
                num = downloadnum.getNum();
                withdrawDB.updateDownloadNumber(true);
            } else {
                withdrawDB.updateDownloadNumber(false);
            }
            return df1.format(num);
        } catch (Exception e) {
            log.info("获取文件编号异常", e);
            System.exit(1);
        }
        return null;
    }


}




























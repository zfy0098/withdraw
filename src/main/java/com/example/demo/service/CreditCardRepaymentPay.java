package com.example.demo.service;

import com.example.demo.db.CreditCardRepayDB;
import com.example.demo.entity.PayOrder;
import com.example.demo.entity.UserBankCard;
import com.example.demo.entity.UserCreditCard;
import com.example.demo.util.DESUtil;
import com.example.demo.util.HttpClient;
import com.example.demo.util.Md5Util;
import com.example.demo.util.UtilsConstant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by hadoop on 2017/12/4.
 *
 * @author hadoop
 */
@Service("creditCardRepaymentPay")
public class CreditCardRepaymentPay {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static String withdrawURL = "http://10.10.20.103:11124/middlepaytrx/onlineOrderPayWithdraw";

    private static String desKey = "212876ea28cc11e7b8590894";

    private static String signKey = "212876ea28cc11e7b8590894ef29bfa8";



    @Autowired
    private CreditCardRepayDB creditCardRepayDB;

    public void withdraw(String message){

        JSONObject json = JSONObject.fromObject(message);

        String tradeOrderNumber = json.getString("orderNumber");

        log.info("交易订单：" + tradeOrderNumber + "发起提现请求");

        Map<String,Object> orderMap = creditCardRepayDB.getOrder(tradeOrderNumber);
        if(orderMap == null || orderMap.isEmpty()){
            log.info("交易订单号：" + tradeOrderNumber + " ， 查询失败");
            return ;
        }else{
            log.info("交易订单号：" + tradeOrderNumber + "查询成功 继续执行");
        }

        Map<String,Object> dfOrderMap = creditCardRepayDB.getDForderInfo(tradeOrderNumber);
        if(dfOrderMap != null && !dfOrderMap.isEmpty()){
            log.info("交易订单号：" + tradeOrderNumber + "已经发生过代付");
            return ;
        }else{
            log.info("交易订单号：" + tradeOrderNumber + "没有发生过代付， 继续执行");
        }

        PayOrder order ;
        UserCreditCard userCreditCard;


        try {
            order = UtilsConstant.mapToBean(orderMap,PayOrder.class);
            userCreditCard = UtilsConstant.mapToBean(creditCardRepayDB.creditCardInfo(order.getUserID() , order.getDFBankCardNo()) , UserCreditCard.class);
        } catch (Exception e) {
            log.error("转换实体类异常" , e);
            return ;
        }


        Map<String,Object> userMap = creditCardRepayDB.userInfoMap(order.getUserID());


        log.info("保存交易订单号：" + tradeOrderNumber + " 代付记录");

        String id = UtilsConstant.getUUID();
        String accountNo = order.getDFBankCardNo();
        String accountName = userCreditCard.getName();
        String bankName = userCreditCard.getBankName();
        String orderNumber = UtilsConstant.getOrderNumber();
        String applyUserID = order.getUserID();
        String dfType = "CREDITCARDREPAY";
        String idCardNo = UtilsConstant.ObjToStr(userMap.get("IDCardNo"));
        String bankSubbranch = userCreditCard.getBankSubbranch();
        String bankCode = userCreditCard.getBankCode();
        String bankProv = userCreditCard.getBankProv();
        String bankCity = userCreditCard.getBankCity();
        String payerPhone = userCreditCard.getPayerPhone();
        String bankSymbol = userCreditCard.getBankSymbol();


        if(UtilsConstant.strIsEmpty(order.getDFBankCardNo())){

            log.info("交易订单号：" + tradeOrderNumber + "普通快捷交易 ");

            // 普通交易   获取商户结算卡信息
            UserBankCard userBankCard;
            try {
                log.info("交易订单号：" + tradeOrderNumber + " , 获取交易商户的结算卡信息");
                userBankCard = UtilsConstant.mapToBean(creditCardRepayDB.userBankInfo(order.getUserID()) , UserBankCard.class);
            } catch (Exception e) {
                log.error("转换实体类异常" , e);
                return ;
            }
            accountNo = userBankCard.getAccountNo();
            accountName = userBankCard.getAccountName();
            bankName = userBankCard.getBankName();
            bankSubbranch = userBankCard.getBankBranch();
            bankCode = userBankCard.getBankCode();
            bankProv = userBankCard.getBankProv();
            bankCity = userBankCard.getBankCity();
            payerPhone = userBankCard.getPayerPhone();
            bankSymbol = userBankCard.getBankSymbol();
            dfType = "TRADE";

            if(UtilsConstant.strIsEmpty(payerPhone)){
                payerPhone = userMap.get("LoginID").toString();
            }
        }

        log.info("交易订单号：" + tradeOrderNumber  + " ， 代付信息  卡号：" + accountNo + " , 交易金额：" + order.getAmount() + " , 交易手续费: " + order.getFee());

        /**
         *   保存代付订单
         */
        try {
            int x = creditCardRepayDB.saveDForder(new Object[]{accountNo,accountName , idCardNo ,bankName , bankSubbranch, bankSymbol , orderNumber  , order.getAmount() ,
                    order.getFee(), applyUserID , "" , dfType , "" , tradeOrderNumber});

            if(x < 1){
                log.info("交易订单号：" + tradeOrderNumber + "保存到付记录失败 ");
                return ;
            }
        } catch (Exception e) {
            log.info("交易订单号：" + tradeOrderNumber + "保存订单记录失败，出现异常" , e);
            return ;
        }

        Map<String,Object> map = new TreeMap<>();
        //商户号
        map.put("merchantNo", order.getMerchantID());
        //订单号每次唯一
        map.put("orderNum", orderNumber);
        // 原交易订单号
        map.put("tradeOrderNumber", tradeOrderNumber);
        // 银行卡号
        map.put("AccountNo", DESUtil.encode(desKey, accountNo));
        // 持卡人姓名
        map.put("AccountName",  DESUtil.encode(desKey, accountName));
        // 持卡人身份证号
        map.put("legalPersonID", DESUtil.encode(desKey, idCardNo));
        //固定值 400 402
        map.put("walletType", "400");
        // 银行名称
        map.put("BankName", bankName);
        // 支行名称
        map.put("BankBranch", bankSubbranch);
        // 银联号
        map.put("BankCode", bankCode);
        // 银行英文代码
        map.put("BankSymbol", bankSymbol);
        // 银行卡所在省份
        map.put("BankProv", bankProv);
        // 银行卡所在市
        map.put("BankCity", bankCity);
        //  银行所在区
        map.put("BankArea", bankCity);
        // 银行卡预留有手机号
        map.put("payerPhone", payerPhone);

        log.info("交易订单号：" +tradeOrderNumber+ " 发送报文加密字符串：" + JSONObject.fromObject(map).toString() + signKey);

        String sign = Md5Util.MD5(JSONObject.fromObject(map).toString() + signKey);

        map.put("sign", sign);
        log.info("交易订单号：" +tradeOrderNumber+ " 发送代付请求：请求报文：" + JSONObject.fromObject(map).toString() );

        String content ;
        try {
            content = HttpClient.post(withdrawURL, map, null);
        } catch (Exception e) {
            log.error("发送http请求异常："  , e);
            return;
        }

        log.info("交易订单：" + tradeOrderNumber + "代付响应报文" + content);
        JSONObject result = JSONObject.fromObject(content);
        try {

            String respCode = "0000";
            String respCodeKey = "respCode";

            if(respCode.equals(result.getString(respCodeKey))){

                log.info("交易订单：" + tradeOrderNumber + "发送代付成功");

                creditCardRepayDB.updateOrderWithdrawStatus(new Object[]{result.getString("respCode") , result.getString("respMsg"),order.getID()});
                creditCardRepayDB.updateDForderStatus(new Object[]{result.getString("respCode") , result.getString("respMsg") , orderNumber});
            }else{
                creditCardRepayDB.updateOrderWithdrawStatus(new Object[]{result.getString("respCode") , result.getString("respMsg"),order.getID()});
                creditCardRepayDB.updateDForderStatus(new Object[]{result.getString("respCode") , result.getString("respMsg") ,  orderNumber});
            }
        } catch (Exception e) {
            log.info("交易订单号：" + tradeOrderNumber + "更新代付信息失败 ， 代付响应报文：" + content + " , 代付订单号：" + orderNumber);
            log.info("交易订单号：" + tradeOrderNumber + "更新代付信息失败" , e );
        }
    }
}

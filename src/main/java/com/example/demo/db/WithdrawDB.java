package com.example.demo.db;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by hadoop on 2017/11/30.
 *
 * @author hadoop
 */
@Repository
public class WithdrawDB extends BaseDao{


    /**
     *   查询提现信息
     * @param orderNumber  提现订单号
     * @return
     */
    public Map<String,Object> getWithdraw(String orderNumber){
        String sql = "SELECT * FROM tab_withdraw where OrderNumber=?";
        return queryForMap(sql , new Object[]{orderNumber});
    }


    /**
     *  查询用户信息
     * @param userID
     * @return
     */
    public Map<String,Object> getLoginUserInfo(String userID){
        String userSQL = "SELECT * FROM tab_loginuser where ID=?";
        return queryForMap(userSQL , new Object[]{userID});
    }


    /**
     *     查询用户id是否在提现黑名单
     * @param userID
     * @return
     */
    public List<Map<String,Object>> blackList(String userID){
        String sql = "select * from tab_df_blacklist where userID=?";
        return jdbc.queryForList(sql , new Object[]{userID});
    }


    /**
     *   根据订单号查询代付记录
     * @param orderNumber
     * @return
     */
    public List<Map<String,Object>> getDForderInfo(String orderNumber){
        String sql = "select * from tab_df_order where  OrderNumber=?";
        return jdbc.queryForList(sql , new Object[]{orderNumber});
    }


    /**
     *   获取下载文件编号
     * @return
     */
    public Map<String,Object> selectFileNum(){
        String selectFileNumSql = "select * from dowloadnum where id=4";
        return queryForMap(selectFileNumSql , null);
    }

    /**
     *   更新文件编号
     * @param flag
     * @return
     */
    public int updateDownloadNumber(boolean flag){
        String sql ;
        if(flag){
            sql = "update dowloadnum set date=now() , num=num+1 where ID=4";
        }else{
            sql = "update dowloadnum set date=now() , num=2 where ID=4";
        }
        return jdbc.update(sql);
    }

    public int updateWithdraw(Object[] objects){
        String sql = "update tab_withdraw set BalanceSecUserID='system' , BalanceSecUserName='系统自动代付' , SettmentBatchNo=?  , BalanceDate= ? , BalanceFlag=1 where ID=? ";
        return jdbc.update(sql , objects);

    }

    public  int insertRecord(Object[] objects){
        String insertRecord = "insert into tab_withdraw_record (AccountName,Login_ID, BankCode, Amount, IDNumber, SettmentBatchNo, CreateTime, Status, BankName, BankCity ,IsDownLoad, BankCardNo, PayWay, TradeDate , OrderNumber) "
                + " select tl.Name, tl.LoginID, tpu.BankCode, tw.ApplyMoney, tl.IDCardNo, ? , sysdate() as CreateTime, '0', "
                + " tpu.BankName, tpu.BankBranch, null , tpu.AccountNo, '4', ? , ?  "
                + " from tab_withdraw tw , tab_loginuser tl , tab_pay_userbankcard tpu "
                + " where  tw.ApplyUserID=tl.ID and tl.ID=tpu.UserID and date_format(tw.ApplyDate,'%Y%m%d') = ? and tw.BalanceFlag='1' and tw.ApplyUserID=? "
                + " GROUP BY date_format(tw.ApplyDate,'%Y%m%d') , tw.ApplyUserID ";
        return jdbc.update(insertRecord , objects);
    }


    public int modifyWithdrawFlagTwo(Object[] objects){
        String sql =  "update tab_withdraw tw  set  BalanceFlag='2' "
                + " where  date_format(tw.ApplyDate,'%Y%m%d')=? and tw.BalanceFlag='1' and ApplyUserID=? ";
        return jdbc.update(sql , objects);
    }


    public Map<String,Object> getWithdrawRecord(Object[] objects){
        String sql = "select * from tab_withdraw_record where OrderNumber = ?";
        return queryForMap(sql , objects);
    }


    /**
     *   保存代付记录
     * @param objects
     * @return
     */
    public int savedfOrder(Object[] objects){
        String savedfOrder = "insert into tab_df_order ( AccountNo,AccountName,BankName,OrderNumber,CreateDate,OrderAmount,ApplyUserID,QueryNumber,DFType)"
                + " values (?,?,?,?,now(),?,?,?,?)";
        return jdbc.update(savedfOrder , objects);
    }


    /**
     *   更新代付状态
     * @param objects
     * @return
     */
    public int updateDForder(Object[] objects){
        String updateDForder = "update tab_df_order set WithradwStatus='0000' where orderNumber =?";
        return jdbc.update(updateDForder , objects);
    }


    public int updateWithdrawRecordStatus(Object[] objects){
        String updateWithdrawRecordStatus = "update tab_withdraw_record set Status='1'  where Status='0' and OrderNumber=?";
        return jdbc.update(updateWithdrawRecordStatus, objects);
    }

}

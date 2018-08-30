package com.example.demo.db;

import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by hadoop on 2017/12/7.
 *
 * @author hadoop
 */
@Repository
public class CreditCardRepayDB extends BaseDao{


    /**
     *   更具交易订单号查询代付订单
     * @param orderNumber
     * @return
     */
    public Map<String,Object> getDForderInfo(String orderNumber){
        String sql = "select * from tab_df_order where TradeOrderNumber=?";
        return queryForMap(sql , new Object[]{orderNumber});
    }


    /**
     *   获取交易订单信息
     * @param orderNumber
     * @return
     */
    public Map<String,Object> getOrder(String orderNumber){
        String sql = "select * from tab_pay_order where orderNumber = ? and PayRetCode='0000' ";
        return queryForMap(sql , new Object[]{orderNumber});
    }


    /**
     *  保存代付订单
     * @param obj
     * @return
     */
    public int saveDForder(Object[] obj){
        String sql = "insert into tab_df_order (AccountNo , AccountName , IDCardNo, BankName , BankSubbranch ,BankSymbol  ,  OrderNumber , OrderAmount ,CreateDate" +
                "  , Fee , ApplyUserID , QueryNumber , DFType , WithdrawStatus , TradeOrderNumber) " +
                " values (?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?)";
        return jdbc.update(sql , obj);
    }


    /**
     *   获取用户信息
     * @param id
     * @return
     */
    public Map<String,Object> userInfoMap(String id){
        String sql = "select * from tab_loginuser where id=?";
        return queryForMap(sql , new Object[]{id});
    }

    /**
     *   查询信用卡信息
     * @param userID
     * @param creditCard
     * @return
     */
    public Map<String,Object> creditCardInfo(String userID , String creditCard){
        String sql = "select * from tab_pay_usercreditcard where userID=? and bankCardNo=?";
        return queryForMap(sql , new Object[]{userID,creditCard});
    }


    /**
     *   用户结算卡信息
     * @param userID
     * @return
     */
    public Map<String,Object> userBankInfo(String userID){
        String sql = "select * from tab_pay_userbankcard where userID=?";
        return queryForMap(sql , new Object[]{userID});
    }


    /**
     *   更新交易订单代付状态
     * @param obj
     * @return
     */
    public int updateOrderWithdrawStatus(Object[] obj){
        String sql = "update tab_pay_order set  T0PayRetCode=? , T0PayRetMsg=? where id=?" ;
        return jdbc.update(sql , obj);
    }


    public int updateDForderStatus(Object[] obj){
        String sql = "update tab_df_order  set WithdrawStatus=? , WithdrawMsg =? where orderNumber=?";
        return jdbc.update(sql , obj);
    }
}

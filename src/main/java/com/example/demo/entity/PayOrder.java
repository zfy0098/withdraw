package com.example.demo.entity;

import java.io.Serializable;

/**
 * @author hadoop
 */

public class PayOrder implements Serializable {

    private String iD;
    private String amount;
    private String localDate;
    private String localTimes;
    private String tradeDate;
    private String tradeTime;
    private String termSerno;
    private String tradeType;
    private String tradeCode;
    private String userID;
    private int payChannel;
    private String feeRate;
    private String merchantID;
    private String fee;
    private String payRetCode;
    private String payRetMsg;
    private String orderNumber;
    private String t0PayRetCode;
    private String t0PayRetMsg;
    private String dFBankCardNo;
    private String merchantProfit;
    private String outTransactionID;
    private String transactionId;
    private String yMFCode;
    private String settleAccounts;
    private String tradeBankNo;
    private String agentID;

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getID() {
        return iD;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setLocalDate(String localDate) {
        this.localDate = localDate;
    }

    public String getLocalDate() {
        return localDate;
    }

    public void setLocalTimes(String localTimes) {
        this.localTimes = localTimes;
    }

    public String getLocalTimes() {
        return localTimes;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTermSerno(String termSerno) {
        this.termSerno = termSerno;
    }

    public String getTermSerno() {
        return termSerno;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setMerchantID(String merchantID) {
        this.merchantID = merchantID;
    }

    public String getMerchantID() {
        return merchantID;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getFee() {
        return fee;
    }

    public void setPayRetCode(String payRetCode) {
        this.payRetCode = payRetCode;
    }

    public String getPayRetCode() {
        return payRetCode;
    }

    public void setPayRetMsg(String payRetMsg) {
        this.payRetMsg = payRetMsg;
    }

    public String getPayRetMsg() {
        return payRetMsg;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setT0PayRetCode(String t0PayRetCode) {
        this.t0PayRetCode = t0PayRetCode;
    }

    public String getT0PayRetCode() {
        return t0PayRetCode;
    }

    public void setT0PayRetMsg(String t0PayRetMsg) {
        this.t0PayRetMsg = t0PayRetMsg;
    }

    public String getT0PayRetMsg() {
        return t0PayRetMsg;
    }

    public void setDFBankCardNo(String dFBankCardNo) {
        this.dFBankCardNo = dFBankCardNo;
    }

    public String getDFBankCardNo() {
        return dFBankCardNo;
    }

    public void setMerchantProfit(String merchantProfit) {
        this.merchantProfit = merchantProfit;
    }

    public String getMerchantProfit() {
        return merchantProfit;
    }

    public void setOutTransactionID(String outTransactionID) {
        this.outTransactionID = outTransactionID;
    }

    public String getOutTransactionID() {
        return outTransactionID;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setYMFCode(String yMFCode) {
        this.yMFCode = yMFCode;
    }

    public String getYMFCode() {
        return yMFCode;
    }

    public void setSettleAccounts(String settleAccounts) {
        this.settleAccounts = settleAccounts;
    }

    public String getSettleAccounts() {
        return settleAccounts;
    }

    public void setTradeBankNo(String tradeBankNo) {
        this.tradeBankNo = tradeBankNo;
    }

    public String getTradeBankNo() {
        return tradeBankNo;
    }

    public void setAgentID(String agentID) {
        this.agentID = agentID;
    }

    public String getAgentID() {
        return agentID;
    }
}

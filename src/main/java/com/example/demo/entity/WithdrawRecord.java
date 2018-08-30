package com.example.demo.entity;

public class WithdrawRecord {
	private long iD;
	private String accountName;
	private String login_ID;
	private String bankCode;
	private String amount;
	private String iDNumber;
	private String settmentBatchNo;
	private String createTime;
	private int status;
	private String transferBatchNo;
	private String bankName;
	private String tradeDate;
	private int isDownLoad;
	private int payWay;
	private String bankCity;
	private String bankCardNo;
	private String orderNumber;
	
	public void setID(long iD){
		this.iD = iD;
	}
	public long getID(){
		return iD;
	}
	public void setAccountName(String accountName){
		this.accountName = accountName;
	}
	public String getAccountName(){
		return accountName;
	}
	public void setLogin_ID(String login_ID){
		this.login_ID = login_ID;
	}
	public String getLogin_ID(){
		return login_ID;
	}
	public void setBankCode(String bankCode){
		this.bankCode = bankCode;
	}
	public String getBankCode(){
		return bankCode;
	}
	public void setAmount(String amount){
		this.amount = amount;
	}
	public String getAmount(){
		return amount;
	}
	public void setIDNumber(String iDNumber){
		this.iDNumber = iDNumber;
	}
	public String getIDNumber(){
		return iDNumber;
	}
	public void setSettmentBatchNo(String settmentBatchNo){
		this.settmentBatchNo = settmentBatchNo;
	}
	public String getSettmentBatchNo(){
		return settmentBatchNo;
	}
	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}
	public String getCreateTime(){
		return createTime;
	}
	public void setStatus(int status){
		this.status = status;
	}
	public int getStatus(){
		return status;
	}
	public void setTransferBatchNo(String transferBatchNo){
		this.transferBatchNo = transferBatchNo;
	}
	public String getTransferBatchNo(){
		return transferBatchNo;
	}
	public void setBankName(String bankName){
		this.bankName = bankName;
	}
	public String getBankName(){
		return bankName;
	}
	public void setTradeDate(String tradeDate){
		this.tradeDate = tradeDate;
	}
	public String getTradeDate(){
		return tradeDate;
	}
	public void setIsDownLoad(int isDownLoad){
		this.isDownLoad = isDownLoad;
	}
	public int getIsDownLoad(){
		return isDownLoad;
	}
	public void setPayWay(int payWay){
		this.payWay = payWay;
	}
	public int getPayWay(){
		return payWay;
	}
	public void setBankCity(String bankCity){
		this.bankCity = bankCity;
	}
	public String getBankCity(){
		return bankCity;
	}
	public void setBankCardNo(String bankCardNo){
		this.bankCardNo = bankCardNo;
	}
	public String getBankCardNo(){
		return bankCardNo;
	}
	public void setOrderNumber(String orderNumber){
		this.orderNumber = orderNumber;
	}
	public String getOrderNumber(){
		return orderNumber;
	}
}

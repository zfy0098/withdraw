package com.example.demo.entity;

import java.io.Serializable;


/**
 *  @author hadoop
 */
public class DFModel implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3559654769426027713L;
	
	
	private String txnTime;
	private String orderNumber;
	private String accNo;
	private String txnAmt;
	private String BankName;
	private String payName;
	
	public String getTxnTime() {
		return txnTime;
	}
	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getAccNo() {
		return accNo;
	}
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	public String getTxnAmt() {
		return txnAmt;
	}
	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}
	public String getBankName() {
		return BankName;
	}
	public void setBankName(String bankName) {
		BankName = bankName;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	

}

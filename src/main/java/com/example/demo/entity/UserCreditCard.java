package com.example.demo.entity;

import java.util.Date;

public class UserCreditCard {
    private String id;
    private String userID;
    private String name;
    private String bankCardNo;
    private String bankName;
    private String bankSubbranch;
    private String bankCode;
    private String bankProv;
    private String bankCity;
    private String bankSymbol;
    private String cvn2;
    private String expired;
    private String payerPhone;
    private String repayDate;
    private int active;
    private Date createDate;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankSubbranch(String bankSubbranch) {
        this.bankSubbranch = bankSubbranch;
    }

    public String getBankSubbranch() {
        return bankSubbranch;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankProv(String bankProv) {
        this.bankProv = bankProv;
    }

    public String getBankProv() {
        return bankProv;
    }

    public void setBankCity(String bankCity) {
        this.bankCity = bankCity;
    }

    public String getBankCity() {
        return bankCity;
    }

    public void setBankSymbol(String bankSymbol) {
        this.bankSymbol = bankSymbol;
    }

    public String getBankSymbol() {
        return bankSymbol;
    }

    public void setCvn2(String cvn2) {
        this.cvn2 = cvn2;
    }

    public String getCvn2() {
        return cvn2;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getExpired() {
        return expired;
    }

    public void setPayerPhone(String payerPhone) {
        this.payerPhone = payerPhone;
    }

    public String getPayerPhone() {
        return payerPhone;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getActive() {
        return active;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCreateDate() {
        return createDate;
    }
}

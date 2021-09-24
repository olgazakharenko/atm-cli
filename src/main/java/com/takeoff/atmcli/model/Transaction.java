package com.takeoff.atmcli.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.Data;

@Data
public class Transaction {
  private Date date;
  private BigDecimal withdrawAmount;
  private BigDecimal balance;

  public Transaction(Date date, BigDecimal withdrawAmount, BigDecimal balance) {
    this.date = date;
    this.withdrawAmount = withdrawAmount;
    this.balance = balance;
  }

  @Override
  public String toString() {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date) +
        " " + withdrawAmount + " " + balance;
  }
}

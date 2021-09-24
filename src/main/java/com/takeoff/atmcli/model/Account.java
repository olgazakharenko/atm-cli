package com.takeoff.atmcli.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Data;

@Data
public class Account {

  private String userId;
  private String pin;
  private boolean isOverdrawn;
  private boolean isAuthorized = false;
  private BigDecimal balance;
  private List<Transaction> history = new ArrayList<>();
  private AtomicLong recentActivity = new AtomicLong();

  public Account(String userId, String pin, BigDecimal balance) {
    this.userId = userId;
    this.pin = pin;
    this.balance = balance;
  }

  public void updateHistory(Transaction t) {
    history.add(t);
  }
}
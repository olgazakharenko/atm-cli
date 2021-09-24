package com.takeoff.atmcli.service;

import java.math.BigDecimal;

public interface Atm {

  void withdraw(BigDecimal amount);

  void deposit(BigDecimal amount);

  boolean withdrawAvailability(BigDecimal amount);

  BigDecimal adjustWithdrawAmount(BigDecimal amount);

  BigDecimal getBalance();
}

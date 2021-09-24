package com.takeoff.atmcli.service.impl;

import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.service.AccountService;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AccountServiceImplTest {

  private Account acc;
  private AccountService accService = new AccountServiceImpl();

  @Before
  public void init() {
    acc = new Account("12", "12", new BigDecimal(100));
  }

  @Test
  public void testWithdraw() {
    accService.withdraw(acc, new BigDecimal(20));
    Assert.assertEquals("Incorrect balance after withdraw", new BigDecimal(80), acc.getBalance());
  }

  @Test
  public void testWithdrawWithOverdraft() {
    accService.withdraw(acc, new BigDecimal(120));
    Assert.assertEquals("Incorrect balance after withdraw", new BigDecimal(-25), acc.getBalance());
  }

  @Test
  public void testWithdrawAvailability() {
    accService.withdraw(acc, new BigDecimal(120));
    Assert.assertFalse(accService.withdrawAvailability(acc, new BigDecimal(20)));
  }

  @Test
  public void testDeposit() {
    accService.deposit(acc, new BigDecimal(20));
    Assert.assertEquals("Incorrect balance after withdraw", new BigDecimal(120), acc.getBalance());
  }

  @Test
  public void testHistory() {
    accService.withdraw(acc, new BigDecimal(20));
    Assert.assertEquals("History is not updated", 1, accService.history(acc).size());
    accService.withdraw(acc, new BigDecimal(20));
    Assert.assertEquals("History is not updated", 2, accService.history(acc).size());
  }

  @Test
  public void testBalance() {
    accService.withdraw(acc, new BigDecimal(20));
    Assert.assertEquals("Incorrect balance after withdraw", acc.getBalance(), accService.balance(acc));

  }
}
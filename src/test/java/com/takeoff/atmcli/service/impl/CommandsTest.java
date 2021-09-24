package com.takeoff.atmcli.service.impl;


import com.takeoff.atmcli.model.Account;
import com.takeoff.atmcli.service.AccountService;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.Shell;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommandsTest {
  @Autowired
  private Shell shell;

  @Autowired
  private AccountService accountService;

  @Autowired
  private AtmImpl atm;

  private Account account;

  @Before
  public void login() {
    // clear al accounts from service
    accountService.clearAccounts();
    // init all accounts through atm service
    atm.init();
    shell.evaluate(() -> "authorize 12 12");
    account = accountService.getCurrent();
  }

  @After
  public void logoutAfterEach() {
    shell.evaluate(() -> "logout");
    //TODO doesn't work if auth from here???
  }

  @Test
  public void authorizeWithCorrectCredentials() {
    Assert.assertNotNull("Current account is not initialized", accountService.getCurrent());
    Assert.assertEquals("Incorrect user id", "12", account.getUserId());
    Assert.assertEquals("Incorrect pin", "12", account.getPin());
  }

  @Test
  public void logout() {
    shell.evaluate(() -> "logout");
    Assert.assertNull("Expect account to be not initialized", accountService.getCurrent());
  }

  @Test
  public void withdraw() {
    shell.evaluate(() -> "withdraw 20");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("80.12"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("110"),
        atm.getBalance());
    //TODO: remove hardcoded expected amount for atm
  }

  @Test
  public void withdrawNotEnoughInAtm() {
    shell.evaluate(() -> "withdraw 140");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("-24.88"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("10"),
        atm.getBalance());
  }

  @Test
  public void withdrawRequestedAmountNotDivisibleByMinBill() {
    shell.evaluate(() -> "withdraw 30");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("100.12"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("130"),
        atm.getBalance());
  }

  @Test
  public void withdrawOverdraft() {
    shell.evaluate(() -> "withdraw 120");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("-24.88"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("10"),
        atm.getBalance());
  }

  @Test
  public void withdrawAfterOverdraft() {
    shell.evaluate(() -> "withdraw 120");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("-24.88"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("10"),
        atm.getBalance());
    shell.evaluate(() -> "withdraw 20");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("-24.88"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("10"),
        atm.getBalance());
  }

  @Test
  public void deposit() {
    shell.evaluate(() -> "deposit 20");
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("120.12"),
        accountService.getCurrent().getBalance());
    Assert.assertEquals("Incorrect amount after withdraw", new BigDecimal("150"),
        atm.getBalance());
  }
}





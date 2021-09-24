package com.takeoff.atmcli.service.impl;

import com.takeoff.atmcli.service.AccountService;
import com.takeoff.atmcli.service.Atm;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AtmImpl implements Atm {

  @Value("${atm.startBalance}")
  private long startBalance;

  @Value("${account.account_filepath}")
  private String filePath;

  private BigDecimal atmBalance;
  private static BigDecimal minBill = BigDecimal.valueOf(20);

  @Autowired
  private AccountService accountService;

  @PostConstruct
  public void init() {
    this.atmBalance = BigDecimal.valueOf(startBalance);
    accountService.addAll(accountService.loadAccounts(filePath));
  }

  /**
   * Withdraw requested amount from the ATM
   * @param amount - amount to be withdrawn
   */
  @Override
  public void withdraw(BigDecimal amount) {
    updateAtmBalance(amount.negate());
  }

  /**
   * The machine can’t dispense more money than it contains. The withdrawal amount should be
   * adjusted to be the amount in the machine
   * @param amount - amount to be withdrawn
   * @return
   */
  public BigDecimal adjustWithdrawAmount(BigDecimal amount) {
    if (atmBalance.compareTo(amount) < 0) {
      System.out.println("Unable to dispense full amount requested at this time.");
      return minBill.multiply(atmBalance.divideToIntegralValue(minBill));
    }
    return amount;
  }

  /**
   *
   * @return current Atm balance
   */
  @Override
  public BigDecimal getBalance() {
    return atmBalance;
  }

  /**
   * Verifies possibility of withdraw
   * @param amount amount to be withdrawn
   * @return true if withdraw is possible
   */
  public boolean withdrawAvailability(BigDecimal amount) {
    if (atmBalance.compareTo(minBill) < 0) {
      // If instead there is no money in the machine
      System.out.println("Unable to process your withdrawal at this time.");
      return false;
    } else if (!checkRequestedAmount(amount)) {
      System.out.println(String
          .format("Unable to process your withdrawal. Amount must be a multiple of %s", minBill));
      return false;
    }
    return true;
  }

  /**
   * Verifies if the requested amount is divisible by min bill
   * @param amount
   * @return
   */
  private boolean checkRequestedAmount(BigDecimal amount) {
    return amount.remainder(minBill).compareTo(BigDecimal.ZERO) == 0;
  }

  /**
   * Deposit requested amount to the ATM
   * @param amount
   */
  @Override
  public void deposit(BigDecimal amount) {
    updateAtmBalance(amount);
  }

  /**
   * Helper method to update atm balance
   * @param amount
   */
  private void updateAtmBalance(BigDecimal amount) {
    this.atmBalance = atmBalance.add(amount);
  }
}

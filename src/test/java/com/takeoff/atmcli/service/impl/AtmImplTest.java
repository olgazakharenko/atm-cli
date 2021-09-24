package com.takeoff.atmcli.service.impl;

import com.takeoff.atmcli.service.Atm;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmImplTest {

  @Autowired
  private Atm target;

  @Test
  public void withdrawAvailability() {
    Assert.assertTrue(target.withdrawAvailability(BigDecimal.valueOf(20)));
  }

  @Test
  public void withdrawAvailabilityFail() {
    Assert.assertFalse(target.withdrawAvailability(BigDecimal.valueOf(18)));
  }

  @Test
  public void adjustWithdrawAmount() {
    Assert.assertEquals(BigDecimal.valueOf(18), target.adjustWithdrawAmount(BigDecimal.valueOf(18)));
  }

  @Test
  public void adjustWithdrawAmountAdjusted() {
    Assert.assertEquals(BigDecimal.valueOf(120), target.adjustWithdrawAmount(BigDecimal.valueOf(180)));
  }

}
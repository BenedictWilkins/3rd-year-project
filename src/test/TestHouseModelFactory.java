package test;

import static org.junit.Assert.assertNotNull;

import housemodels.HouseModelFactory;

import org.junit.Test;

public class TestHouseModelFactory {

  @Test
  public void testSingleton() {
    assertNotNull(HouseModelFactory.getFactory());
  }
}

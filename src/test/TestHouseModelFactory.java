package test;

import static org.junit.Assert.assertNotNull;

import housemodels.HouseModelFactory;

import org.junit.Test;

/**
 * JUnit test case for the {@link HouseModelFactory} class.
 * 
 * @author Benedict Wilkins
 *
 */
public class TestHouseModelFactory {

  @Test
  public void testSingleton() {
    assertNotNull(HouseModelFactory.getFactory());
  }
}

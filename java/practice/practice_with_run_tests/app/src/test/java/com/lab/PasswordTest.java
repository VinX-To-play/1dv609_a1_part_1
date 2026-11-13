package com.lab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for Password implementations.
 *
 * <p>To test different buggy versions, simply uncomment the corresponding getPassword() method and
 * comment out the others.
 *
 * <p>Available implementations: - Password: Correct implementation - BugDoesNotTrim: Does not trim
 * whitespace - BugToShortPassword: Allows passwords shorter than 12 characters - BugVeryShort:
 * Allows way to short passwords - BugWrongExceptionMessage: Wrong exception message for short
 * passwords - BugMissingPasswordLengthCheck: Does not throw exception for short passwords -
 * BugMissingNumberCheck: Does not throw exception if password lacks a number -
 * BugIsPasswordSameAlwaysTrue: isPasswordSame always returns true - BugWrongHashingAlgorithm: Wrong
 * hashing algorithm
 */
public class PasswordTest {
  private IPassword getPassword(String s) throws Exception {
    // return (IPassword) new Password(s);
    // return (IPassword) new BugDoesNotTrim(s);
    // return (IPassword) new BugToShortPassword(s);
    // return (IPassword) new BugVeryShort(s);
    // return (IPassword) new BugWrongExceptionMessage(s);
    // return (IPassword) new BugMissingPasswordLengthCheck(s);
    // return (IPassword) new BugMissingNumberCheck(s);
    // return (IPassword) new BugIsPasswordSameAlwaysTrue(s);
    return (IPassword) new BugWrongHashingAlgorithm(s);
  }

  @Test
  public void shouldAlwaysPass() throws Exception {
    assertTrue(true);
  }

  @Test
  public void isSamePasswordSame() throws Exception {
    IPassword correct = getPassword("Password123!");
    assertTrue(correct.isPasswordSame(correct));
  }

  // TODO: Why are different passwords the same?
  @Test
  public void isDifferentPasswordDiferent() throws Exception {
    IPassword first = getPassword("Password123!");
    IPassword second = getPassword("Password125!");
    assertFalse(first.isPasswordSame(second));
  }

  @Test
  public void ConstructorShouldTrim() throws Exception {
    IPassword withWhitespace = getPassword("   Password123!   ");
    IPassword correct = getPassword("Password123!");
    assertTrue(correct.isPasswordSame(withWhitespace));
  }

  @Test
  public void passwordMinLength12() throws Exception {
    try {
      IPassword toShort = getPassword("Passwoed123");
    } catch (Exception e) {
      return;
    }
    assertTrue(false, "The password got excepted with less then 12 char");
  }

  @Test
  public void passwordVeryShort() throws Exception {
    try {
      // INFO The password must be 7 chars' to fail on the BugVeryShort
      IPassword toShort = getPassword("Pass12");
    } catch (Exception e) {
      return;
    }
    assertTrue(false, "The password got excepted with less then 2 chars");
  }

  @Test
  public void passwordVeryShortExceptionMessage() throws Exception {
    try {
      IPassword toShort = getPassword("P3");
    } catch (Exception e) {
      assertTrue(
          e.getMessage() == "To short password",
          "To short pass didint throw \"To short password\"");
    }
  }

  @Test
  public void passwordNoDigitExceptionMessage() throws Exception {
    try {
      getPassword("ldkfjslkfdjslkfjdl");
    } catch (Exception e) {
      assertTrue(
          e.getMessage() == "Does not contain a number",
          String.format("expected \"Dose not contain a number\" but got \"%s\"", e.getMessage()));
    }
  }

  @Test
  public void passwordWithOutNum() throws Exception {
    try {
      getPassword("kjfdsjdfdsffhks");
    } catch (Exception e) {
      return;
    }
    assertTrue(false, "Accepts passwords without numbers");
  }

  @Test
  public void usingCorrectSimpleHash() throws Exception {
    IPassword toTest = getPassword("Password123!");
    assertTrue(
        toTest.getPasswordHash() == -1323532559,
        String.format("Expected Hash: \"-1323532559\" but got: \"%d\"", toTest.getPasswordHash()));
  }
}

package org.x4444.hbase;

import junit.framework.ComparisonFailure;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for org.x4444.hbase.Bytes
 */
public class X4BytesTest {

  @Test
  public void testToSixBytes() {
    byte[] p1 = X4Bytes.toSixBytes(1L);
    byte[] p1Ex = new byte[] { 0, 0, 0, 0, 0, 1 };
    assertBytes(p1Ex, p1);

    byte[] p2 = X4Bytes.toSixBytes(401300055L);
    byte[] p2Ex = new byte[] { 0, 0, 23, -21, 90, 87 };
    assertBytes(p2Ex, p2);

    byte[] m1 = X4Bytes.toSixBytes(-1L);
    byte[] m1Ex = new byte[] { -1, -1, -1, -1, -1, -1 };
    assertBytes(m1Ex, m1);

    byte[] m2 = X4Bytes.toSixBytes(-1300055L);
    byte[] m2Ex = new byte[] { -1, -1, -1, -20, 41, -87 };
    assertBytes(m2Ex, m2);
  }

  @Test
  public void testSixBytesToLong() {
    byte[] p1B = new byte[] { 0, 0, 0, 0, 0, 1 };
    long p1 = X4Bytes.sixBytesToLong(p1B, 0);
    Assert.assertEquals(1L, p1);

    byte[] p2B = new byte[] { 0, 0, 23, -21, 90, 87 };
    long p2 = X4Bytes.sixBytesToLong(p2B, 0);
    Assert.assertEquals(401300055L, p2);

    byte[] n1B = new byte[] { -1, -1, -1, -1, -1, -1 };
    long n1 = X4Bytes.sixBytesToLong(n1B, 0);
    Assert.assertEquals(-1L, n1);

    byte[] n2B = new byte[] { -1, -1, -1, -20, 41, -87 };
    long n2 = X4Bytes.sixBytesToLong(n2B, 0);
    Assert.assertEquals(-1300055L, n2);
  }

  public void assertBytes(byte[] expected, byte[] actual) {
    if (expected == null && actual == null) {
      return;
    }
    if (expected == null || actual == null) {
      throw new ComparisonFailure("null", bytesToString(expected),
          bytesToString(actual));
    }
    if (expected.length != actual.length) {
      throw new ComparisonFailure("different lenght", bytesToString(expected),
          bytesToString(actual));
    }

    for (int i = 0; i < 6; i++) {
      if (expected[i] != actual[i]) {
        throw new ComparisonFailure("not the same", bytesToString(expected),
            bytesToString(actual));
      }
    }
  }

  protected String bytesToString(byte[] bytes) {
    if (bytes == null) {
      return "null";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    for (byte b : bytes) {
      sb.append(b).append(",");
    }
    sb.deleteCharAt(sb.length() - 1);
    sb.append("}");
    return sb.toString();
  }
}

package com.tw ter.search.common.encod ng.features;

/**
 *  nterface for compress ng unbounded float values to a s gned byte.    ncludes both
 * normal zat on of values and encod ng of values  n a byte.
 */
publ c abstract class ByteNormal zer {
  publ c stat c byte  ntToUns gnedByte( nt  ) {
    return (byte)  ;
  }

  publ c stat c  nt uns gnedByteTo nt(byte b) {
    return ( nt) b & 0xFF;
  }

  /**
   * Returns t  byte-compressed value of {@code val}.
   */
  publ c abstract byte normal ze(double val);

  /**
   * Returns a lo r bound to t  unnormal zed range of {@code norm}.
   */
  publ c abstract double unnormLo rBound(byte norm);

  /**
   * Returns an upper bound to t  unnormal zed range of {@code norm}.
   */
  publ c abstract double unnormUpperBound(byte norm);

  /**
   * Returns true  f t  normal zed value of {@code val}  s d fferent than t  normal zed value of
   * {@code val - 1}
   */
  publ c boolean changedNorm(double val) {
    return normal ze(val) != normal ze(val - 1);
  }
}

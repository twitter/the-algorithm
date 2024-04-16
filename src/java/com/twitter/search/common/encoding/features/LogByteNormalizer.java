package com.tw ter.search.common.encod ng.features;

 mport com.google.common.base.Precond  ons;

/**
 * Normal zes values as follows:
 *   Pos  ve numbers normal ze to (1 + round(log_baseN(value))).
 *   Negat ve numbers throw.
 *   0 w ll normal ze to 0.
 * T  log base  s 2 by default.
 */
publ c class LogByteNormal zer extends ByteNormal zer {

  pr vate stat c f nal double DEFAULT_BASE = 2;
  pr vate f nal double base;
  pr vate f nal double logBase;

  publ c LogByteNormal zer(double base) {
    Precond  ons.c ckArgu nt(base > 0);
    t .base = base;
    logBase = Math.log(base);
  }

  publ c LogByteNormal zer() {
    t (DEFAULT_BASE);
  }

  @Overr de
  publ c byte normal ze(double val) {
     f (val < 0) {
      throw new  llegalArgu ntExcept on("Can't log-normal ze negat ve value " + val);
    } else  f (val == 0) {
      return 0;
    } else {
      long logVal = 1 + (long) Math.floor(Math.log(val) / logBase);
      return logVal > Byte.MAX_VALUE ? Byte.MAX_VALUE : (byte) logVal;
    }
  }

  @Overr de
  publ c double unnormLo rBound(byte norm) {
    return norm < 0
        ? Double.NEGAT VE_ NF N TY
        : Math.floor(Math.pow(base, norm - 1));
  }

  @Overr de
  publ c double unnormUpperBound(byte norm) {
    return norm == Byte.MAX_VALUE
        ? Double.POS T VE_ NF N TY
        : Math.floor(Math.pow(base, norm));
  }
}

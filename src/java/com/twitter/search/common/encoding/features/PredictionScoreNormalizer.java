package com.tw ter.search.common.encod ng.features;

 mport com.google.common.base.Precond  ons;

/**
 * A normal zer that normal zes t  pred ct on score from a mach ne learn ng class f er, wh ch
 * ranges w h n [0.0, 1.0], to an  nteger value by mult ply ng by (10 ^ prec s on), and returns
 * t  rounded value. T  lo r t  prec s on, t  less amount of b s   takes to encode t  score.
 * @see #prec s on
 *
 * T  normal zer also could denormal ze t  normal zed value from  nteger back to double us ng t 
 * sa  prec s on.
 */
publ c class Pred ct onScoreNormal zer {

  pr vate f nal  nt prec s on;
  pr vate f nal double normal z ngBase;

  publ c Pred ct onScoreNormal zer( nt prec s on) {
    t .prec s on = prec s on;
    t .normal z ngBase = Math.pow(10, t .prec s on);
  }

  /**
   * Returns t  normal zed  nt value for pred ct on score {@code score} by mult ply ng
   * by {@code normal z ngBase}, and round t  result.
   * @throws  llegalArgu ntExcept on w n para ter {@code score}  s not w h n [0.0, 1.0]
   */
  publ c  nt normal ze(double score) {
    Precond  ons.c ckArgu nt( sScoreW h nRange(score));
    return ( nt) Math.round(score * t .normal z ngBase);
  }

  /**
   * Converts t  normal zed  nt value back to a double score by d v d ng by {@code normal z ngBase}
   * @throws  llegalStateExcept on w n t  denormal zed value  s not w h n [0.0, 1.0]
   */
  publ c double denormal ze( nt normal zedScore) {
    double denormal zedValue = normal zedScore / t .normal z ngBase;
     f (! sScoreW h nRange(denormal zedValue)) {
      throw new  llegalStateExcept on(
          Str ng.format("T  denormal zed value %s  s not w h n [0.0, 1.0]", denormal zedValue)
      );
    }
    return denormal zedValue;
  }

  pr vate stat c boolean  sScoreW h nRange(double score) {
    return 0.0 <= score && score <= 1.0;
  }
}

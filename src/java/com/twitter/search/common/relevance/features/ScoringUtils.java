package com.tw ter.search.common.relevance.features;

 mport com.google.common.base.Precond  ons;

/**
 * Scor ng ut l  es
 */
publ c f nal class Scor ngUt ls {
  pr vate Scor ngUt ls() { }

  /**
   * normal ze a pos  ve value of arb rary range to [0.0, 1.0], w h a slop
   * @param value t  value to normal ze.
   * @param halfval a reference value that w ll be normal zed to 0.5
   * @param exp an exponent al para ter (must be pos  ve) to control t  converg ng speed,
   * t  smaller t  value t  faster   reac s t  halfval but slo r   reac s t  max mum.
   * @return a normal zed value
   */
  publ c stat c float normal ze(float value, double halfval, double exp) {
    Precond  ons.c ckArgu nt(exp > 0.0 && exp <= 1.0);
    return (float) (Math.pow(value, exp) / (Math.pow(value, exp) + Math.pow(halfval, exp)));
  }

}

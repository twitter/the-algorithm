package com.tw ter.search.common.relevance.features;

 mport java.ut l.concurrent.T  Un ;

 mport com.google.common.base.Precond  ons;

/**
 * Ut l y to compute an age decay mult pl er based on a s gmo d funct on.
 */
publ c class AgeDecay {
  publ c stat c f nal double SLOPE_COEFF = 4.0;
  publ c stat c f nal double LN_HALF = Math.log(0.5);
  publ c f nal double halfl fe;
  publ c f nal double maxBoost;
  publ c f nal double base;
  publ c f nal double slope;

  /** Creates a new AgeDecay  nstance. */
  publ c AgeDecay(double base, double maxBoost, double halfl fe, double slope) {
    t .maxBoost = maxBoost;
    t .base = base;
    t .halfl fe = halfl fe;
    t .slope = slope;
  }

  /** Creates a new AgeDecay  nstance. */
  publ c AgeDecay(double base, double halfl fe, double slope) {
    t (base, 1.0, halfl fe, slope);
  }

  /**
   * Compute t  age decay, us ng t  prov ded halfl fe.
   *
   * @param t etAge T  t et age.
   * @param un  T  un  of t  t etAge para ter.
   */
  publ c double getAgeDecayMult pl er(long t etAge, T  Un  un ) {
    return getAgeDecayMult pl er(T  Un .SECONDS.convert(t etAge, un ));
  }

  /**
   * Compute t  age decay, assum ng t  halfl fe  n t  constructor  s  n m nutes.
   * @param age nSeconds t  age  n seconds
   */
  publ c double getAgeDecayMult pl er(long age nSeconds) {
    long m nutesS nceT et = T  Un .M NUTES.convert(age nSeconds, T  Un .SECONDS);
    return compute(m nutesS nceT et);
  }

  /**
   * Compute age decay g ven an age, t  age has to be  n t  sa  un  as halfl fe, wh ch  
   * construct t  object w h.
   */
  publ c double compute(double age) {
    return compute(base, maxBoost, halfl fe, slope, age);
  }

  /**
   * Compute t  age decay g ven all para ters. Use t   f   don't need to reuse an AgeDecay
   * object.
   */
  publ c stat c double compute(
      double base, double maxBoost, double halfl fe, double slope, double age) {
    return base + ((maxBoost - base) / (1 + Math.exp(slope * (age - halfl fe))));
  }

  publ c stat c double compute(
      double base, double maxBoost, double halfl fe, double age) {
    Precond  ons.c ckArgu nt(halfl fe != 0);
    return compute(base, maxBoost, halfl fe, SLOPE_COEFF / halfl fe, age);
  }

  /**
   * Anot r n cer exponent al decay funct on. Returns a value  n (0, 1]
   */
  publ c stat c double computeExponent al(double halfl fe, double exp, double age) {
    return Math.exp(LN_HALF * Math.pow(age, exp) / Math.pow(halfl fe, exp));
  }

  /**
   * Exponent al decay w h remapp ng of t  value from (0,1] to (m n,max]
   */
  publ c stat c double computeExponent al(double halfl fe, double exp, double age,
                                          double m nBoost, double maxBoost) {
    double decay = computeExponent al(halfl fe, exp, age);  //  n (0, 1]
    return (maxBoost - m nBoost) * decay + m nBoost;
  }
}

package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport java.ut l.Arrays;

 mport com.google.common.base.Precond  ons;

/**
 * Represents a cont nuous feature that has been d scret zed  nto a set of d sjo nt ranges.
 *
 * Each range [a, b)  s represented by t  lo r spl  po nt (a) and  s assoc ated   ght.
 */
class D scret zedFeature {

  protected f nal double[] spl Po nts;
  protected f nal double[]   ghts;

  /**
   * Creates an  nstance from a l st of spl  po nts and t  r correspond ng   ghts.
   *
   * @param spl Po nts Lo r values of t  ranges. T  f rst entry must be Double.NEGAT VE_ NF N TY
   *  T y must be sorted ( n ascend ng order).
   * @param    ghts   ghts for t  spl s.
   */
  protected D scret zedFeature(double[] spl Po nts, double[]   ghts) {
    Precond  ons.c ckArgu nt(spl Po nts.length ==   ghts.length);
    Precond  ons.c ckArgu nt(spl Po nts.length > 1);
    Precond  ons.c ckArgu nt(spl Po nts[0] == Double.NEGAT VE_ NF N TY,
        "F rst spl  po nt must be Double.NEGAT VE_ NF N TY");
    t .spl Po nts = spl Po nts;
    t .  ghts =   ghts;
  }

  publ c double get  ght(double value) {
    // b narySearch returns (-  nsert onPo nt - 1)
     nt  ndex = Math.abs(Arrays.b narySearch(spl Po nts, value) + 1) - 1;
    return   ghts[ ndex];
  }

  publ c boolean allValuesBelowThreshold(double m n  ght) {
    for (double   ght :   ghts) {
       f (Math.abs(  ght) > m n  ght) {
        return false;
      }
    }
    return true;
  }
}

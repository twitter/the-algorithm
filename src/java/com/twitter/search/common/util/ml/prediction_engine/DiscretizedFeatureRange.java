package com.tw ter.search.common.ut l.ml.pred ct on_eng ne;

 mport com.google.common.base.Precond  ons;

/**
 * T  d scret zed value range for a cont nous feature. After d scret zat on a cont nuous feature
 * may beco  mult ple d scret zed b nary features, each occupy ng a range. T  class stores t 
 * range and a   ght for  .
 */
publ c class D scret zedFeatureRange {
  protected f nal double m nValue;
  protected f nal double maxValue;
  protected f nal double   ght;

  D scret zedFeatureRange(double   ght, Str ng range) {
    Str ng[] l m s = range.spl ("_");
    Precond  ons.c ckArgu nt(l m s.length == 2);

    t .m nValue = parseRangeValue(l m s[0]);
    t .maxValue = parseRangeValue(l m s[1]);
    t .  ght =   ght;
  }

  pr vate stat c double parseRangeValue(Str ng value) {
     f (" nf".equals(value)) {
      return Double.POS T VE_ NF N TY;
    } else  f ("- nf".equals(value)) {
      return Double.NEGAT VE_ NF N TY;
    } else {
      return Double.parseDouble(value);
    }
  }
}

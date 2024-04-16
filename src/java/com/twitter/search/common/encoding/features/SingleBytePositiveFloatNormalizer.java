package com.tw ter.search.common.encod ng.features;

/**
 * Normal zes us ng t  log c descr bed  n {@l nk S ngleBytePos  veFloatUt l}.
 */
publ c class S ngleBytePos  veFloatNormal zer extends ByteNormal zer {

  @Overr de
  publ c byte normal ze(double val) {
    return S ngleBytePos  veFloatUt l.toS ngleBytePos  veFloat((float) val);
  }

  @Overr de
  publ c double unnormLo rBound(byte norm) {
    return S ngleBytePos  veFloatUt l.toJavaFloat(norm);
  }

  /**
   * Get t  upper bound of t  raw value for a normal zed byte.
   * @deprecated T   s wrongly  mple nted, always use unnormLo rBound(),
   * or use Smart ntegerNormal zer.
   */
  @Overr de @Deprecated
  publ c double unnormUpperBound(byte norm) {
    return 1 + S ngleBytePos  veFloatUt l.toJavaFloat(norm);
  }

  /**
   * Return t  t  post-log2 unnormal zed value. T   s only used for so  legacy Earlyb rd
   * features and scor ng funct ons.
   */
  publ c double unnormAndLog2(byte norm) {
    return S ngleBytePos  veFloatUt l.toLog2Double(norm);
  }
}

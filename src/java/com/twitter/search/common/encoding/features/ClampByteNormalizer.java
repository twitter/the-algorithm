package com.tw ter.search.common.encod ng.features;

 mport com.google.common.base.Precond  ons;

/**
 * A byte normal zer that restr cts t  values to t  g ven range before normal z ng t m.
 */
publ c class ClampByteNormal zer extends ByteNormal zer {
  pr vate f nal  nt m nUnnormal zedValue;
  pr vate f nal  nt maxUnnormal zedValue;

  /**
   * Creates a new ClampByteNormal zer  nstance.
   *
   * @param m nValue T  smallest allo d unnormal zed value.
   * @param maxValue T  largest allo d unnormal zed value.
   */
  publ c ClampByteNormal zer( nt m nUnnormal zedValue,  nt maxUnnormal zedValue) {
    Precond  ons.c ckState(m nUnnormal zedValue <= maxUnnormal zedValue);
    Precond  ons.c ckState(m nUnnormal zedValue >= 0);
    Precond  ons.c ckState(maxUnnormal zedValue <= 255);
    t .m nUnnormal zedValue = m nUnnormal zedValue;
    t .maxUnnormal zedValue = maxUnnormal zedValue;
  }

  @Overr de
  publ c byte normal ze(double val) {
     nt adjustedValue = ( nt) val;
     f (adjustedValue < m nUnnormal zedValue) {
      adjustedValue = m nUnnormal zedValue;
    }
     f (adjustedValue > maxUnnormal zedValue) {
      adjustedValue = maxUnnormal zedValue;
    }
    return ByteNormal zer. ntToUns gnedByte(adjustedValue);
  }

  @Overr de
  publ c double unnormLo rBound(byte norm) {
    return ByteNormal zer.uns gnedByteTo nt(norm);
  }

  @Overr de
  publ c double unnormUpperBound(byte norm) {
    return ByteNormal zer.uns gnedByteTo nt(norm) + 1;
  }
}

package com.tw ter.search.common.relevance.text;

publ c class V s bleTokenRat oNormal zer {

  pr vate stat c f nal  nt NORMAL ZE_TO_B TS = 4;
  pr vate f nal  nt normal zeToS ze;

  /**
   * constructor
   */
  publ c V s bleTokenRat oNormal zer( nt normal zeToB s) {
     nt s ze = 2 << (normal zeToB s - 1);
    // Let's say normal zeS ze  s set to 16....
    //  f   mult ply 1.0 * 16,    s 16
    //  f   mult ply 0.0 * 16,    s 0
    // That would be occupy ng 17  nts, not 16, so   subtract 1  re...
    t .normal zeToS ze = s ze - 1;
  }

  /**
   *  thod
   */
  publ c  nt normal ze(double percent) {
     f (percent > 1 || percent < 0) {
      throw new  llegalArgu ntExcept on("percent should be less than 1 and greater than 0");
    }
     nt bucket = ( nt) (percent * normal zeToS ze);
    return normal zeToS ze - bucket;
  }

  publ c double denormal ze( nt reverseBucket) {
     nt bucket = normal zeToS ze - reverseBucket;
    return bucket / (double) normal zeToS ze;
  }

  publ c stat c V s bleTokenRat oNormal zer create nstance() {
    return new V s bleTokenRat oNormal zer(NORMAL ZE_TO_B TS);
  }
}

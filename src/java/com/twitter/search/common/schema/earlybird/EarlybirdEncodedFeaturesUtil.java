package com.tw ter.search.common.sc ma.earlyb rd;

 mport com.tw ter.search.common.encod ng.docvalues.CSFTypeUt l;
 mport com.tw ter.search.common.sc ma.base. mmutableSc ma nterface;

publ c f nal class Earlyb rdEncodedFeaturesUt l {
  pr vate Earlyb rdEncodedFeaturesUt l() {
  }

  /**
   * Returns a byte array that can be stored  n a Thr ftDocu nt as bytesF eld.
   */
  publ c stat c byte[] toBytesForThr ftDocu nt(Earlyb rdEncodedFeatures features) {
     nt num nts = features.getNum nts();
    byte[] ser al zedFeatures = new byte[num nts *  nteger.BYTES];
    for ( nt   = 0;   < num nts;  ++) {
      CSFTypeUt l.convertToBytes(ser al zedFeatures,  , features.get nt( ));
    }
    return ser al zedFeatures;
  }

  /**
   * Converts data  n a g ven byte array (start ng at t  prov ded offset)  nto
   * Earlyb rdEncodedFeatures.
   */
  publ c stat c Earlyb rdEncodedFeatures fromBytes(
       mmutableSc ma nterface sc ma, Earlyb rdF eldConstants.Earlyb rdF eldConstant baseF eld,
      byte[] data,  nt offset) {
    Earlyb rdEncodedFeatures features = Earlyb rdEncodedFeatures.newEncodedT etFeatures(
        sc ma, baseF eld);
    for ( nt  dx = 0;  dx < features.getNum nts(); ++ dx) {
      features.set nt( dx, CSFTypeUt l.convertFromBytes(data, offset,  dx));
    }
    return features;
  }
}

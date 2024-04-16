package com.tw ter.search.common.relevance.features;

 mport com.tw ter.search.common.encod ng.features.ByteNormal zer;
 mport com.tw ter.search.common.encod ng.features.S ngleBytePos  veFloatNormal zer;
 mport com.tw ter.search.common.encod ng.features.Smart ntegerNormal zer;

/**
 * Byte value normal zers used to push feature values  nto earlyb rd db.
 */
publ c abstract class MutableFeatureNormal zers {
  // T  max value   support  n SMART_ NTEGER_NORMAL ZER below, t  should be enough for all k nds
  // of engage nts   see on Tw ter, anyth ng larger than t  would be represented as t  sa 
  // value (255,  f us ng a byte).
  pr vate stat c f nal  nt MAX_COUNTER_VALUE_SUPPORTED = 50000000;

  // Avo d us ng t  normal zer for proces ng any new data, always use Smart ntegerNormal zer
  // below.
  publ c stat c f nal S ngleBytePos  veFloatNormal zer BYTE_NORMAL ZER =
      new S ngleBytePos  veFloatNormal zer();

  publ c stat c f nal ByteNormal zer SMART_ NTEGER_NORMAL ZER =
      new Smart ntegerNormal zer(MAX_COUNTER_VALUE_SUPPORTED, 8);
}

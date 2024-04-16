package com.tw ter.search.common.relevance.features;

 mport java.ut l.Set;

 mport com.google.common.collect.Sets;

publ c class T etTextQual y {

  publ c stat c enum BooleanQual yType {
    OFFENS VE,          // t et text  s offens ve
    OFFENS VE_USER,     // user na   s offens ve
    HASHTAG_NAME_MATCH,  // hashtag matc s userna 
    SENS T VE,           // t et  s marked as sens  ve w n   co s  n
  }

  publ c stat c f nal double ENTROPY_NOT_SET = Double.M N_VALUE;

  publ c stat c f nal byte UNSET_TEXT_SCORE = -128;

  pr vate double readab l y;
  pr vate double shout;
  pr vate double entropy = ENTROPY_NOT_SET;
  pr vate f nal Set<BooleanQual yType> boolQual  es = Sets.newHashSet();
  pr vate byte textScore = UNSET_TEXT_SCORE;

  publ c double getReadab l y() {
    return readab l y;
  }

  publ c vo d setReadab l y(double readab l y) {
    t .readab l y = readab l y;
  }

  publ c double getShout() {
    return shout;
  }

  publ c vo d setShout(double shout) {
    t .shout = shout;
  }

  publ c double getEntropy() {
    return entropy;
  }

  publ c vo d setEntropy(double entropy) {
    t .entropy = entropy;
  }

  publ c vo d addBoolQual y(BooleanQual yType type) {
    boolQual  es.add(type);
  }

  publ c boolean hasBoolQual y(BooleanQual yType type) {
    return boolQual  es.conta ns(type);
  }

  publ c Set<BooleanQual yType> getBoolQual  es() {
    return boolQual  es;
  }

  publ c byte getTextScore() {
    return textScore;
  }

  publ c vo d setTextScore(byte textScore) {
    t .textScore = textScore;
  }
}

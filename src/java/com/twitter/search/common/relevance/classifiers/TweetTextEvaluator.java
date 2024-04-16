package com.tw ter.search.common.relevance.class f ers;

 mport java.ut l.L st;
 mport java.ut l.Map;
 mport java.ut l.funct on.Funct on;
 mport java.ut l.stream.Collectors;

 mport com.tw ter.common_ nternal.text.vers on.Pengu nVers on;
 mport com.tw ter.search.common.relevance.ent  es.Tw ter ssage;
 mport com.tw ter.search.common.relevance.features.T etTextFeatures;
 mport com.tw ter.search.common.relevance.features.T etTextQual y;

/**
 * Calculates entropy of t et text based on tokens.
 */
publ c class T etTextEvaluator extends T etEvaluator {

  @Overr de
  publ c vo d evaluate(f nal Tw ter ssage t et) {
    for (Pengu nVers on pengu nVers on : t et.getSupportedPengu nVers ons()) {
      T etTextFeatures textFeatures = t et.getT etTextFeatures(pengu nVers on);
      T etTextQual y textQual y = t et.getT etTextQual y(pengu nVers on);

      double readab l y = 0;
       nt numKeptWords = textFeatures.getStr ppedTokensS ze();
      for (Str ng token : textFeatures.getStr ppedTokens()) {
        readab l y += token.length();
      }
       f (numKeptWords > 0) {
        readab l y = readab l y * Math.log(numKeptWords) / numKeptWords;
      }
      textQual y.setReadab l y(readab l y);
      textQual y.setEntropy(entropy(textFeatures.getStr ppedTokens()));
      textQual y.setShout(textFeatures.getCaps() / Math.max(textFeatures.getLength(), 1.0d));
    }
  }

  pr vate stat c double entropy(L st<Str ng> tokens) {
    Map<Str ng, Long> tokenCounts =
        tokens.stream().collect(Collectors.group ngBy(Funct on. dent y(), Collectors.count ng()));
     nt num ems = tokens.s ze();

    double entropy = 0;
    for (long count : tokenCounts.values()) {
      double prob = (double) count / num ems;
      entropy -= prob * log2(prob);
    }
    return entropy;
  }

  pr vate stat c double log2(double n) {
    return Math.log(n) / Math.log(2);
  }
}

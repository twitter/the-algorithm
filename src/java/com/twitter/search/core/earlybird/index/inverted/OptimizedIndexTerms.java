package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport org.apac .lucene. ndex.Terms;
 mport org.apac .lucene. ndex.TermsEnum;

publ c class Opt m zed ndexTerms extends Terms {
  pr vate f nal Opt m zed mory ndex  ndex;

  publ c Opt m zed ndexTerms(Opt m zed mory ndex  ndex) {
    t . ndex =  ndex;
  }

  @Overr de
  publ c long s ze() {
    return  ndex.getNumTerms();
  }

  @Overr de
  publ c TermsEnum  erator() {
    return  ndex.createTermsEnum( ndex.getMaxPubl s dPo nter());
  }

  @Overr de
  publ c long getSumTotalTermFreq() {
    return  ndex.getSumTotalTermFreq();
  }

  @Overr de
  publ c long getSumDocFreq() {
    return  ndex.getSumTermDocFreq();
  }

  @Overr de
  publ c  nt getDocCount() {
    return  ndex.getNumDocs();
  }

  @Overr de
  publ c boolean hasFreqs() {
    return false;
  }

  @Overr de
  publ c boolean hasOffsets() {
    return false;
  }

  @Overr de
  publ c boolean hasPos  ons() {
    return true;
  }

  @Overr de
  publ c boolean hasPayloads() {
    return false;
  }
}

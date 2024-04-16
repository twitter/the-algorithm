package com.tw ter.search.common.query;

 mport java. o. OExcept on;

 mport org.apac .lucene.search.Doc dSet erator;
 mport org.apac .lucene.search.Scorer;
 mport org.apac .lucene.search.  ght;

publ c class F lteredScorer extends Scorer {
  protected f nal Scorer  nner;

  publ c F lteredScorer(  ght   ght, Scorer  nner) {
    super(  ght);
    t . nner =  nner;
  }

  @Overr de
  publ c float score() throws  OExcept on {
    return  nner.score();
  }

  @Overr de
  publ c  nt doc D() {
    return  nner.doc D();
  }

  @Overr de
  publ c Doc dSet erator  erator() {
    return  nner. erator();
  }

  @Overr de
  publ c float getMaxScore( nt upTo) throws  OExcept on {
    return  nner.getMaxScore(upTo);
  }
}

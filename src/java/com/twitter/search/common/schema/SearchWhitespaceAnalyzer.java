package com.tw ter.search.common.sc ma;

 mport org.apac .lucene.analys s.Analyzer;
 mport org.apac .lucene.analys s.core.Wh espaceToken zer;

/**
 * T  major y of t  code  s cop ed from Lucene 3.1 analys s.core.Wh espaceAnalyzer. T  only
 * new code  s t  getPos  on ncre ntGap()
 */
publ c f nal class SearchWh espaceAnalyzer extends Analyzer {
  @Overr de
  protected TokenStreamComponents createComponents(f nal Str ng f eldNa ) {
    return new TokenStreamComponents(new Wh espaceToken zer());
  }

  /**
   * Make sure that phrase quer es do not match across 2  nstances of t  text f eld.
   *
   * See t  Javadoc for Analyzer.getPos  on ncre ntGap() for a good explanat on of how t 
   *  thod works.
   */
  @Overr de
  publ c  nt getPos  on ncre ntGap(Str ng f eldNa ) {
    // Hard-code "text"  re, because   can't depend on Earlyb rdF eldConstants.
    return "text".equals(f eldNa ) ? 1 : super.getPos  on ncre ntGap(f eldNa );
  }
}

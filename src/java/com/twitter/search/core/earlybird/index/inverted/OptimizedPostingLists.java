package com.tw ter.search.core.earlyb rd. ndex. nverted;

 mport java. o. OExcept on;

 mport org.apac .lucene. ndex.Post ngsEnum;

 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

publ c abstract class Opt m zedPost ngL sts  mple nts Flushable {
  stat c f nal  nt MAX_DOC_ D_B T = 24;
  stat c f nal  nt MAX_DOC_ D = (1 << MAX_DOC_ D_B T) - 1;

  stat c f nal  nt MAX_POS T ON_B T = 31;

  stat c f nal  nt MAX_FREQ_B T = 31;

  /**
   * Cop es t  g ven post ng l st  nto t se post ng l sts.
   *
   * @param post ngsEnum enu rator of t  post ng l st that needs to be cop ed
   * @param numPost ngs number of post ngs  n t  post ng l st that needs to be cop ed
   * @return pos  on  ndex of t   ad of t  cop ed post ng l st  n t se post ng l sts  nstance
   */
  publ c abstract  nt copyPost ngL st(Post ngsEnum post ngsEnum,  nt numPost ngs)
      throws  OExcept on;

  /**
   * Create and return a post ngs doc enu rator or doc-pos  on enu rator based on  nput flag.
   *
   * @see org.apac .lucene. ndex.Post ngsEnum
   */
  publ c abstract Earlyb rdPost ngsEnum post ngs( nt post ngL stPo nter,  nt numPost ngs,  nt flags)
      throws  OExcept on;

  /**
   * Returns t  largest doc D conta ned  n t  post ng l st po nted by {@code post ngL stPo nter}.
   */
  publ c f nal  nt getLargestDoc D( nt post ngL stPo nter,  nt numPost ngs) throws  OExcept on {
    return post ngs(post ngL stPo nter, numPost ngs, Post ngsEnum.NONE).getLargestDoc D();
  }
}

package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;

/**
 * An  nterface for mapp ng t  doc  Ds  n    ndexes to t  correspond ng t et  Ds.
 */
publ c  nterface Doc DToT et DMapper {
  /** A constant  nd cat ng that a doc  D was not found  n t  mapper. */
   nt  D_NOT_FOUND = -1;

  /**
   * Returns t  t et  D correspond ng to t  g ven doc  D.
   *
   * @param doc D T  doc  D stored  n    ndexes.
   * @return T  t et  D correspond ng to t  g ven doc  D.
   */
  long getT et D( nt doc D);

  /**
   * Returns t   nternal doc  D correspond ng to t  g ven t et  D. Returns  D_NOT_FOUND  f t 
   * g ven t et  D cannot be found  n t   ndex.
   *
   * @param t et D T  t et  D.
   * @return T  doc  D correspond ng to t  g ven t et  D.
   */
   nt getDoc D(long t et D) throws  OExcept on;

  /**
   * Returns t  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D.
   *  f no such doc  D ex sts,  D_NOT_FOUND  s returned.
   *
   * @param doc D T  current doc  D.
   * @return T  smallest val d doc  D  n t  mapper that's str ctly h g r than t  g ven doc  D,
   *         or a negat ve number,  f no such doc  D ex sts.
   */
   nt getNextDoc D( nt doc D);

  /**
   * Returns t  largest val d doc  D  n t  mapper that's str ctly smaller than t  g ven doc  D.
   *  f no such doc  D ex sts,  D_NOT_FOUND  s returned.
   *
   * @param doc D T  current doc  D.
   * @return T  largest val d doc  D  n t  mapper that's str ctly smaller than t  g ven doc  D,
   *         or a negat ve number,  f no such doc  D ex sts.
   */
   nt getPrev ousDoc D( nt doc D);

  /**
   * Returns t  total number of docu nts stored  n t  mapper.
   *
   * @return T  total number of docu nts stored  n t  mapper.
   */
   nt getNumDocs();

  /**
   * Adds a mapp ng for t  g ven t et  D. Returns t  doc  D ass gned to t  t et  D.
   * T   thod does not c ck  f t  t et  D  s already present  n t  mapper.   always ass gns
   * a new doc  D to t  g ven t et.
   *
   * @param t et D T  t et  D to be added to t  mapper.
   * @return T  doc  D ass gned to t  g ven t et  D, or  D_NOT_FOUND  f a doc  D could not be
   *         ass gned to t  t et.
   */
   nt addMapp ng(long t et D);

  /**
   * Converts t  current Doc DToT et DMapper to a Doc DToT et DMapper  nstance w h t  sa 
   * t et  Ds. T  t et  Ds  n t  or g nal and opt m zed  nstances can be mapped to d fferent
   * doc  Ds. Ho ver,   expect doc  Ds to be ass gned such that t ets created later have smaller
   * have smaller doc  Ds.
   *
   * T   thod should be called w n an earlyb rd seg nt  s be ng opt m zed, r ght before
   * flush ng   to d sk.
   *
   * @return An opt m zed Doc DToT et DMapper w h t  sa  t et  Ds.
   */
  Doc DToT et DMapper opt m ze() throws  OExcept on;
}

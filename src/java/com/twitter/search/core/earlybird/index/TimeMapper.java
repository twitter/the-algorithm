package com.tw ter.search.core.earlyb rd. ndex;

 mport java. o. OExcept on;

 mport com.tw ter.search.common.ut l. o.flushable.Flushable;

/**
 * Maps t  stamps to t  doc  Ds ass gned to t  docu nts that are  ndexed (t ets, users, etc.).
 */
publ c  nterface T  Mapper extends Flushable {
  // Unless spec f ed, all t   f elds are seconds-s nce-epoch.
   nt  LLEGAL_T ME =  nteger.M N_VALUE;

  /**
   * Returns t  t   of t  ne st t et  n t   ndex.
   *
   * @return T  t   of t  ne st t et  n t   ndex.
   */
   nt getLastT  ();

  /**
   * Returns t  t   of t  oldest t et  n t   ndex.
   *
   * @return T  t   of t  oldest t et  n t   ndex.
   */
   nt getF rstT  ();

  /**
   * Returns t  t  stamp of t  docu nt mapped to t  g ven doc  D, or  LLEGAL_T ME  f t 
   * mapper doesn't know about t  doc  D.
   *
   * @param doc D T  docu nt's  nternal  D.
   * @return T  t  stamp of t  docu nt mapped to t  g ven doc  D.
   */
   nt getT  ( nt doc D);

  /**
   * Returns t  doc  D of t  f rst  ndexed docu nt w h a t  stamp equal to or greater than t 
   * g ven t  stamp.
   *
   *  f t  Seconds  s larger than t  max t  stamp  n t  mapper, smallestDoc D  s returned.
   *  f t  Seconds  s smaller than t  m n t  stamp  n t  mapper, t  largest doc D  s returned.
   *
   * Note that w n t ets are  ndexed out of order, t   thod m ght return t  doc  D of a t et
   * w h a t  stamp greater than t  Seconds, even  f t re's a t et w h a t  stamp of
   * t  Seconds. So t  callers of t   thod can use t  returned doc  D as a start ng po nt for
   *  erat on purposes, but should have a c ck that t  traversed doc  Ds have a t  stamp  n t 
   * des red range. See S nceUnt lF lter.getDoc dSet() for an example.
   *
   * Example:
   *   Doc ds:  6, 5, 4, 3, 2, 1, 0
   *   T  s:   1, 5, 3, 4, 4, 3, 6
   * W h that data:
   *   f ndF rstDoc d(1, 0) should return 6.
   *   f ndF rstDoc d(3, 0) should return 5.
   *   f ndF rstDoc d(4, 0) should return 5.
   *   f ndF rstDoc d(5, 0) should return 5.
   *   f ndF rstDoc d(6, 0) should return 0.
   *
   * @param t  Seconds T  boundary t  stamp,  n seconds.
   * @param smallestDoc D T  doc  D to return  f t  g ven t   boundary  s larger than t  max
   *                      t  stamp  n t  mapper.
   */
   nt f ndF rstDoc d( nt t  Seconds,  nt smallestDoc D) throws  OExcept on;

  /**
   * Opt m zes t  t   mapper.
   *
   * At seg nt opt m zat on t  , t  doc  Ds ass gned to t  docu nts  n that seg nt m ght
   * change (t y m ght be mapped to a more compact space for performance reasons, for example).
   * W n that happens,   need to remap accord ngly t  doc  Ds stored  n t  t   mapper for that
   * seg nt too.   would also be a good t   to opt m ze t  data stored  n t  t   mapper.
   *
   * @param or g nalDoc dMapper T  doc  D mapper used by t  seg nt before   was opt m zed.
   * @param opt m zedDoc dMapper T  doc  D mapper used by t  seg nt after   was opt m zed.
   * @return An opt m zed T  Mapper w h t  sa  t et  Ds.
   */
  T  Mapper opt m ze(Doc DToT et DMapper or g nalDoc dMapper,
                      Doc DToT et DMapper opt m zedDoc dMapper) throws  OExcept on;
}

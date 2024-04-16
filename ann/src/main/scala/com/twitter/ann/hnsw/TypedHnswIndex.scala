package com.tw ter.ann.hnsw

 mport com.tw ter.ann.common._
 mport com.tw ter.b ject on. nject on
 mport com.tw ter.search.common.f le.AbstractF le

// Class to prov de HNSW based approx mate nearest ne ghb   ndex
object TypedHnsw ndex {

  /**
   * Creates  n- mory HNSW based  ndex wh ch supports query ng/add  on/updates of t  ent y embedd ngs.
   * See https://docb rd.tw ter.b z/ann/hnsw.html to c ck  nformat on about argu nts.
   *
   * @param d  ns on D  ns on of t  embedd ng to be  ndexed
   * @param  tr c D stance  tr c ( nnerProduct/Cos ne/L2)
   * @param efConstruct on T  para ter has t  sa   an ng as ef, but controls t 
   *                        ndex_t  / ndex_accuracy rat o. B gger ef_construct on leads to longer
   *                       construct on, but better  ndex qual y. At so  po nt,  ncreas ng
   *                       ef_construct on does not  mprove t  qual y of t   ndex. One way to
   *                       c ck  f t  select on of ef_construct on was ok  s to  asure a recall
   *                       for M nearest ne ghbor search w n ef = ef_constuct on:  f t  recall  s
   *                       lo r than 0.9, than t re  s room for  mprove nt.
   * @param maxM T  number of b -d rect onal l nks created for every new ele nt dur ng construct on.
   *             Reasonable range for M  s 2-100. H g r M work better on datasets w h h gh
   *              ntr ns c d  ns onal y and/or h gh recall, wh le low M work better for datasets
   *             w h low  ntr ns c d  ns onal y and/or low recalls. T  para ter also determ nes
   *             t  algor hm's  mory consumpt on, b gger t  param more t   mory requ re nt.
   *             For h gh d  ns onal datasets (word embedd ngs, good face descr ptors), h g r M
   *             are requ red (e.g. M=48, 64) for opt mal performance at h gh recall.
   *             T  range M=12-48  s ok for t  most of t  use cases.
   * @param expectedEle nts Approx mate number of ele nts to be  ndexed
   * @param readWr eFuturePool Future pool for perform ng read (query) and wr e operat on (add  on/updates).
   * @tparam T Type of  em to  ndex
   * @tparam D Type of d stance
   */
  def  ndex[T, D <: D stance[D]](
    d  ns on:  nt,
     tr c:  tr c[D],
    efConstruct on:  nt,
    maxM:  nt,
    expectedEle nts:  nt,
    readWr eFuturePool: ReadWr eFuturePool
  ): Appendable[T, HnswParams, D] w h Queryable[T, HnswParams, D] w h Updatable[T] = {
    Hnsw[T, D](
      d  ns on,
       tr c,
      efConstruct on,
      maxM,
      expectedEle nts,
      readWr eFuturePool,
      JMapBased dEmbedd ngMap.apply n mory[T](expectedEle nts)
    )
  }

  /**
   * Creates  n- mory HNSW based  ndex wh ch supports query ng/add  on/updates of t  ent y embedd ngs.
   *   can be ser al zed to a d rectory (HDFS/Local f le system)
   * See https://docb rd.tw ter.b z/ann/hnsw.html to c ck  nformat on about argu nts.
   *
   * @param d  ns on D  ns on of t  embedd ng to be  ndexed
   * @param  tr c D stance  tr c ( nnerProduct/Cos ne/L2)
   * @param efConstruct on T  para ter has t  sa   an ng as ef, but controls t 
   *                        ndex_t  / ndex_accuracy rat o. B gger ef_construct on leads to longer
   *                       construct on, but better  ndex qual y. At so  po nt,  ncreas ng
   *                       ef_construct on does not  mprove t  qual y of t   ndex. One way to
   *                       c ck  f t  select on of ef_construct on was ok  s to  asure a recall
   *                       for M nearest ne ghbor search w n ef = ef_constuct on:  f t  recall  s
   *                       lo r than 0.9, than t re  s room for  mprove nt.
   * @param maxM T  number of b -d rect onal l nks created for every new ele nt dur ng construct on.
   *             Reasonable range for M  s 2-100. H g r M work better on datasets w h h gh
   *              ntr ns c d  ns onal y and/or h gh recall, wh le low M work better for datasets
   *             w h low  ntr ns c d  ns onal y and/or low recalls. T  para ter also determ nes
   *             t  algor hm's  mory consumpt on, b gger t  param more t   mory requ re nt.
   *             For h gh d  ns onal datasets (word embedd ngs, good face descr ptors), h g r M
   *             are requ red (e.g. M=48, 64) for opt mal performance at h gh recall.
   *             T  range M=12-48  s ok for t  most of t  use cases.
   * @param expectedEle nts Approx mate number of ele nts to be  ndexed
   * @param  nject on  nject on for typed  d T to Array[Byte]
   * @param readWr eFuturePool Future pool for perform ng read (query) and wr e operat on (add  on/updates).
   * @tparam T Type of  em to  ndex
   * @tparam D Type of d stance
   */
  def ser al zable ndex[T, D <: D stance[D]](
    d  ns on:  nt,
     tr c:  tr c[D],
    efConstruct on:  nt,
    maxM:  nt,
    expectedEle nts:  nt,
     nject on:  nject on[T, Array[Byte]],
    readWr eFuturePool: ReadWr eFuturePool
  ): Appendable[T, HnswParams, D]
    w h Queryable[T, HnswParams, D]
    w h Updatable[T]
    w h Ser al zat on = {
    val  ndex = Hnsw[T, D](
      d  ns on,
       tr c,
      efConstruct on,
      maxM,
      expectedEle nts,
      readWr eFuturePool,
      JMapBased dEmbedd ngMap
        .apply n moryW hSer al zat on[T](expectedEle nts,  nject on)
    )

    Ser al zableHnsw[T, D](
       ndex,
       nject on
    )
  }

  /**
   * Loads HNSW  ndex from a d rectory to  n- mory
   * @param d  ns on d  ns on of t  embedd ng to be  ndexed
   * @param  tr c D stance  tr c
   * @param readWr eFuturePool Future pool for perform ng read (query) and wr e operat on (add  on/updates).
   * @param  nject on :  nject on for typed  d T to Array[Byte]
   * @param d rectory : D rectory(HDFS/Local f le system) w re hnsw  ndex  s stored
   * @tparam T : Type of  em to  ndex
   * @tparam D : Type of d stance
   */
  def load ndex[T, D <: D stance[D]](
    d  ns on:  nt,
     tr c:  tr c[D],
     nject on:  nject on[T, Array[Byte]],
    readWr eFuturePool: ReadWr eFuturePool,
    d rectory: AbstractF le
  ): Appendable[T, HnswParams, D]
    w h Queryable[T, HnswParams, D]
    w h Updatable[T]
    w h Ser al zat on = {
    Ser al zableHnsw.loadMapBasedQueryable ndex[T, D](
      d  ns on,
       tr c,
       nject on,
      readWr eFuturePool,
      d rectory
    )
  }

  /**
   * Loads a HNSW  ndex from a d rectory and  mory map  .
   *   w ll take less  mory but rely more on d sk as   leverages  mory mapped f le backed by d sk.
   * Latency w ll go up cons derably (Could be by factor of > 10x)  f used on  nstance w h low
   *  mory s nce lot of page faults may occur. Best use case to use would w h scald ng jobs
   * w re mapper/reducers  nstance are l m ed by 8gb  mory.
   * @param d  ns on d  ns on of t  embedd ng to be  ndexed
   * @param  tr c D stance  tr c
   * @param readWr eFuturePool Future pool for perform ng read (query) and wr e operat on (add  on/updates).
   * @param  nject on  nject on for typed  d T to Array[Byte]
   * @param d rectory D rectory(HDFS/Local f le system) w re hnsw  ndex  s stored
   * @tparam T Type of  em to  ndex
   * @tparam D Type of d stance
   */
  def loadMMapped ndex[T, D <: D stance[D]](
    d  ns on:  nt,
     tr c:  tr c[D],
     nject on:  nject on[T, Array[Byte]],
    readWr eFuturePool: ReadWr eFuturePool,
    d rectory: AbstractF le
  ): Appendable[T, HnswParams, D]
    w h Queryable[T, HnswParams, D]
    w h Updatable[T]
    w h Ser al zat on = {
    Ser al zableHnsw.loadMMappedBasedQueryable ndex[T, D](
      d  ns on,
       tr c,
       nject on,
      readWr eFuturePool,
      d rectory
    )
  }
}

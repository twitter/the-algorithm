package com.tw ter.s mclusters_v2.stores

 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter.recos.ent  es.thr ftscala.{Semant cCoreEnt yW hLocale, UserScoreL st}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.{At na, ManhattanRO, ManhattanROConf g}
 mport com.tw ter.storehaus_ nternal.ut l.{Appl cat on D, DatasetNa , HDFSPath}

object Top cTopProducersStore {
  val app dDevel = "recos_platform_dev"
  val v2DatasetNa Devel = "top c_producers_em"
  val v3DatasetNa Devel = "top c_producers_agg"
  val v4DatasetNa Devel = "top c_producers_em_erg"

  val app dProd = "s mclusters_v2"
  val v1DatasetNa Prod = "top_producers_for_top c_from_top c_follow_graph"
  val v2DatasetNa Prod = "top_producers_for_top c_em"

   mpl c  val key nj = CompactScalaCodec(Semant cCoreEnt yW hLocale)
   mpl c  val val nj = CompactScalaCodec(UserScoreL st)

  def getTop cTopProducerStoreV1Prod(
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Semant cCoreEnt yW hLocale, UserScoreL st] =
    ManhattanRO.getReadableStoreW hMtls[Semant cCoreEnt yW hLocale, UserScoreL st](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app dProd),
        DatasetNa (v1DatasetNa Prod),
        At na
      ),
      mhMtlsParams
    )

  def getTop cTopProducerStoreV2Devel(
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Semant cCoreEnt yW hLocale, UserScoreL st] =
    ManhattanRO.getReadableStoreW hMtls[Semant cCoreEnt yW hLocale, UserScoreL st](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app dDevel),
        DatasetNa (v2DatasetNa Devel),
        At na
      ),
      mhMtlsParams
    )

  def getTop cTopProducerStoreV2Prod(
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Semant cCoreEnt yW hLocale, UserScoreL st] =
    ManhattanRO.getReadableStoreW hMtls[Semant cCoreEnt yW hLocale, UserScoreL st](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app dProd),
        DatasetNa (v2DatasetNa Prod),
        At na
      ),
      mhMtlsParams
    )

  def getTop cTopProducerStoreV3Devel(
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Semant cCoreEnt yW hLocale, UserScoreL st] =
    ManhattanRO.getReadableStoreW hMtls[Semant cCoreEnt yW hLocale, UserScoreL st](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app dDevel),
        DatasetNa (v3DatasetNa Devel),
        At na
      ),
      mhMtlsParams
    )

  def getTop cTopProducerStoreV4Devel(
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Semant cCoreEnt yW hLocale, UserScoreL st] =
    ManhattanRO.getReadableStoreW hMtls[Semant cCoreEnt yW hLocale, UserScoreL st](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app dDevel),
        DatasetNa (v4DatasetNa Devel),
        At na
      ),
      mhMtlsParams
    )
}

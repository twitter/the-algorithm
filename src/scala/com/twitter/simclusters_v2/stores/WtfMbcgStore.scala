package com.tw ter.s mclusters_v2.stores

 mport com.tw ter.scald ng_ nternal.mult format.format.keyval.KeyVal nject on.{
  Long2B gEnd an,
  ScalaB naryThr ft
}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.{Apollo, ManhattanRO, ManhattanROConf g}
 mport com.tw ter.storehaus_ nternal.ut l.{Appl cat on D, DatasetNa , HDFSPath}
 mport com.tw ter.wtf.cand date.thr ftscala.Cand dateSeq

object WtfMbcgStore {

  val app d = "recos_platform_apollo"

   mpl c  val key nj = Long2B gEnd an
   mpl c  val val nj = ScalaB naryThr ft(Cand dateSeq)

  def getWtfMbcgStore(
    mhMtlsParams: ManhattanKVCl entMtlsParams,
    datasetNa : Str ng
  ): ReadableStore[Long, Cand dateSeq] = {
    ManhattanRO.getReadableStoreW hMtls[Long, Cand dateSeq](
      ManhattanROConf g(
        HDFSPath(""),
        Appl cat on D(app d),
        DatasetNa (datasetNa ),
        Apollo
      ),
      mhMtlsParams
    )
  }
}

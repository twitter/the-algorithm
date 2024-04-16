package com.tw ter.s mclusters_v2.summ ngb rd.stores

 mport com.tw ter.b ject on. nject on
 mport com.tw ter.b ject on.scrooge.CompactScalaCodec
 mport com.tw ter.s mclusters_v2.thr ftscala.{ClustersUser sKnownFor, ModelVers on}
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.{At na, ManhattanRO, ManhattanROConf g}
 mport com.tw ter.storehaus_ nternal.ut l.{Appl cat on D, DatasetNa , HDFSPath}
 mport com.tw ter.ut l.Future

object UserKnownForReadableStore {

  pr vate val dataSetNa Dec11 = "s mclusters_v2_known_for_20m_145k_dec11"
  pr vate val dataSetNa Updated = "s mclusters_v2_known_for_20m_145k_updated"
  pr vate val dataSetNa 2020 = "s mclusters_v2_known_for_20m_145k_2020"

  pr vate def bu ldForModelVers on(
    app d: Str ng,
    storeNa : Str ng,
    mhMtlsParams: ManhattanKVCl entMtlsParams
  ): ReadableStore[Long, ClustersUser sKnownFor] = {
     mpl c  val key nject on:  nject on[Long, Array[Byte]] =  nject on.long2B gEnd an
     mpl c  val knownForCodec:  nject on[ClustersUser sKnownFor, Array[Byte]] =
      CompactScalaCodec(ClustersUser sKnownFor)

    ManhattanRO.getReadableStoreW hMtls[Long, ClustersUser sKnownFor](
      ManhattanROConf g(
        HDFSPath(""), // not needed
        Appl cat on D(app d),
        DatasetNa (storeNa ),
        At na
      ),
      mhMtlsParams
    )
  }

  def get(app d: Str ng, mhMtlsParams: ManhattanKVCl entMtlsParams): UserKnownForReadableStore = {
    val dec11Store = bu ldForModelVers on(app d, dataSetNa Dec11, mhMtlsParams)
    val updatedStore = bu ldForModelVers on(app d, dataSetNa Updated, mhMtlsParams)
    val vers on2020Store = bu ldForModelVers on(app d, dataSetNa 2020, mhMtlsParams)

    UserKnownForReadableStore(dec11Store, updatedStore, vers on2020Store)
  }

  def getDefaultStore(mhMtlsParams: ManhattanKVCl entMtlsParams): UserKnownForReadableStore =
    get("s mclusters_v2", mhMtlsParams)

}

case class Query(user d: Long, modelVers on: ModelVers on = ModelVers on.Model20m145kUpdated)

/**
 * Ma nly used  n debuggers to fetch t  top knownFor clusters across d fferent model vers ons
 */
case class UserKnownForReadableStore(
  knownForStoreDec11: ReadableStore[Long, ClustersUser sKnownFor],
  knownForStoreUpdated: ReadableStore[Long, ClustersUser sKnownFor],
  knownForStore2020: ReadableStore[Long, ClustersUser sKnownFor])
    extends ReadableStore[Query, ClustersUser sKnownFor] {

  overr de def get(query: Query): Future[Opt on[ClustersUser sKnownFor]] = {
    query.modelVers on match {
      case ModelVers on.Model20m145kDec11 =>
        knownForStoreDec11.get(query.user d)
      case ModelVers on.Model20m145kUpdated =>
        knownForStoreUpdated.get(query.user d)
      case ModelVers on.Model20m145k2020 =>
        knownForStore2020.get(query.user d)
      case c =>
        throw new  llegalArgu ntExcept on(
          s"Never  ard of $c before!  s t  a new model vers on?")
    }
  }
}

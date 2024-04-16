package com.tw ter.users gnalserv ce
package base

 mport com.tw ter.b ject on.Codec
 mport com.tw ter.storage.cl ent.manhattan.kv.ManhattanKVCl entMtlsParams
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanCluster
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanRO
 mport com.tw ter.storehaus_ nternal.manhattan.ManhattanROConf g
 mport com.tw ter.storehaus_ nternal.ut l.HDFSPath
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.ut l.Future
 mport com.tw ter.storehaus_ nternal.ut l.Appl cat on D
 mport com.tw ter.storehaus_ nternal.ut l.DatasetNa 

/**
 * A Manhattan s gnal fetc r extend ng BaseS gnalFetc r to prov de an  nterface to fetch s gnals
 * from a Manhattan dataset.
 *
 * Extends t  w n t  underly ng store  s a s ngle Manhattan dataset.
 * @tparam ManhattanKeyType
 * @tparam ManhattanValueType
 */
tra  ManhattanS gnalFetc r[ManhattanKeyType, ManhattanValueType] extends BaseS gnalFetc r {
  /*
    Def ne t   ta  nfo of t  Manhattan dataset
   */
  protected def manhattanApp d: Str ng
  protected def manhattanDatasetNa : Str ng
  protected def manhattanCluster d: ManhattanCluster
  protected def manhattanKVCl entMtlsParams: ManhattanKVCl entMtlsParams

  protected def manhattanKeyCodec: Codec[ManhattanKeyType]
  protected def manhattanRawS gnalCodec: Codec[ManhattanValueType]

  /**
   * Adaptor to transform t  user d to t  ManhattanKey
   * @param user d
   * @return ManhattanKeyType
   */
  protected def toManhattanKey(user d: User d): ManhattanKeyType

  /**
   * Adaptor to transform t  ManhattanValue to t  Seq of RawS gnalType
   * @param manhattanValue
   * @return Seq[RawS gnalType]
   */
  protected def toRawS gnals(manhattanValue: ManhattanValueType): Seq[RawS gnalType]

  protected f nal lazy val underly ngStore: ReadableStore[User d, Seq[RawS gnalType]] = {
    ManhattanRO
      .getReadableStoreW hMtls[ManhattanKeyType, ManhattanValueType](
        ManhattanROConf g(
          HDFSPath(""),
          Appl cat on D(manhattanApp d),
          DatasetNa (manhattanDatasetNa ),
          manhattanCluster d),
        manhattanKVCl entMtlsParams
      )(manhattanKeyCodec, manhattanRawS gnalCodec)
      .composeKeyMapp ng(user d => toManhattanKey(user d))
      .mapValues(manhattanRawS gnal => toRawS gnals(manhattanRawS gnal))
  }

  overr de f nal def getRawS gnals(user d: User d): Future[Opt on[Seq[RawS gnalType]]] =
    underly ngStore.get(user d)
}

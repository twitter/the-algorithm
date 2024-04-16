package com.tw ter.users gnalserv ce
package base
 mport com.tw ter.fr gate.common.store.strato.StratoFetchableStore
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.strato.cl ent.Cl ent
 mport com.tw ter.strato.data.Conv
 mport com.tw ter.tw stly.common.User d
 mport com.tw ter.ut l.Future

/**
 * A Strato s gnal fetc r extend ng BaseS gnalFetc r to prov de an  nterface to fetch s gnals from
 * Strato Column.
 *
 * Extends t  w n t  underly ng store  s a s ngle Strato column.
 * @tparam StratoKeyType
 * @tparam StratoV ewType
 * @tparam StratoValueType
 */
tra  StratoS gnalFetc r[StratoKeyType, StratoV ewType, StratoValueType]
    extends BaseS gnalFetc r {
  /*
    Def ne t   ta  nfo of t  strato column
   */
  def stratoCl ent: Cl ent
  def stratoColumnPath: Str ng
  def stratoV ew: StratoV ewType

  /**
   * Overr de t se vals and remove t   mpl c  key words.
   * @return
   */
  protected  mpl c  def keyConv: Conv[StratoKeyType]
  protected  mpl c  def v ewConv: Conv[StratoV ewType]
  protected  mpl c  def valueConv: Conv[StratoValueType]

  /**
   * Adapter to transform t  user d to t  StratoKeyType
   * @param user d
   * @return StratoKeyType
   */
  protected def toStratoKey(user d: User d): StratoKeyType

  /**
   * Adapter to transform t  StratoValueType to a Seq of RawS gnalType
   * @param stratoValue
   * @return Seq[RawS gnalType]
   */
  protected def toRawS gnals(stratoValue: StratoValueType): Seq[RawS gnalType]

  protected f nal lazy val underly ngStore: ReadableStore[User d, Seq[RawS gnalType]] =
    StratoFetchableStore
      .w hV ew[StratoKeyType, StratoV ewType, StratoValueType](
        stratoCl ent,
        stratoColumnPath,
        stratoV ew)
      .composeKeyMapp ng(toStratoKey)
      .mapValues(toRawS gnals)

  overr de f nal def getRawS gnals(user d: User d): Future[Opt on[Seq[RawS gnalType]]] =
    underly ngStore.get(user d)
}

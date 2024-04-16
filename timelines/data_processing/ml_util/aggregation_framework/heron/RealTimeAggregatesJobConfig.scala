package com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron

 mport com.tw ter.f nagle.mtls.aut nt cat on.EmptyServ ce dent f er
 mport com.tw ter.f nagle.mtls.aut nt cat on.Serv ce dent f er
 mport com.tw ter.ml.ap .DataRecord
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.OneToSo Transform

/**
 *
 * @param app d  appl cat on  d for topology job
 * @param topologyWorkers number of workers/conta ners of topology
 * @param s ceCount number of parallel sprouts of topology
 * @param sum rCount number of Sum r of topology
 * @param cac S ze number of tuples a Sum r awa s before aggregat on.
 * @param flatMapCount number of parallel FlatMap of topology
 * @param conta nerRamG gaBytes total RAM of each worker/conta ner has
 * @param na  na  of topology job
 * @param teamNa  na  of team who owns topology job
 * @param teamEma l ema l of team who owns topology job
 * @param componentsToKerber ze component of topology job (eg. Ta l-FlatMap-S ce) wh ch enables kerber zat on
 * @param componentTo taSpaceS zeMap   taSpaceS ze sett ngs for components of topology job
 * @param topologyNa dOpt ons Sets spout allocat ons for na d topology components
 * @param serv ce dent f er represents t   dent f er used for Serv ce to Serv ce Aut nt cat on
 * @param onl nePreTransforms sequent al data record transforms appl ed to Producer of DataRecord before creat ng AggregateGroup.
 *                            Wh le preTransforms def ned at AggregateGroup are appl ed to each aggregate group, onl nePreTransforms are appl ed to t  whole producer s ce.
 * @param keyedByUserEnabled boolean value to enable/d sable  rg ng user-level features from Feature Store
 * @param keyedByAuthorEnabled boolean value to enable/d sable  rg ng author-level features from Feature Store
 * @param enableUserRe ndex ngN ghthawkBtreeStore boolean value to enable re ndex ng RTAs on user  d w h btree backed n ghthawk
 * @param enableUserRe ndex ngN ghthawkHashStore boolean value to enable re ndex ng RTAs on user  d w h hash backed n ghthawk
 * @param userRe ndex ngN ghthawkBtreeStoreConf g NH btree store conf g used  n re ndex ng user RTAs
 * @param userRe ndex ngN ghthawkHashStoreConf g NH hash store conf g used  n re ndex ng user RTAs
 */
case class RealT  AggregatesJobConf g(
  app d: Str ng,
  topologyWorkers:  nt,
  s ceCount:  nt,
  sum rCount:  nt,
  cac S ze:  nt,
  flatMapCount:  nt,
  conta nerRamG gaBytes:  nt,
  na : Str ng,
  teamNa : Str ng,
  teamEma l: Str ng,
  componentsToKerber ze: Seq[Str ng] = Seq.empty,
  componentTo taSpaceS zeMap: Map[Str ng, Str ng] = Map.empty,
  componentToRamG gaBytesMap: Map[Str ng,  nt] = Map("Ta l" -> 4),
  topologyNa dOpt ons: Map[Str ng, Opt ons] = Map.empty,
  serv ce dent f er: Serv ce dent f er = EmptyServ ce dent f er,
  onl nePreTransforms: Seq[OneToSo Transform] = Seq.empty,
  keyedByUserEnabled: Boolean = false,
  keyedByAuthorEnabled: Boolean = false,
  keyedByT etEnabled: Boolean = false,
  enableUserRe ndex ngN ghthawkBtreeStore: Boolean = false,
  enableUserRe ndex ngN ghthawkHashStore: Boolean = false,
  userRe ndex ngN ghthawkBtreeStoreConf g: N ghthawkUnderly ngStoreConf g =
    N ghthawkUnderly ngStoreConf g(),
  userRe ndex ngN ghthawkHashStoreConf g: N ghthawkUnderly ngStoreConf g =
    N ghthawkUnderly ngStoreConf g()) {

  /**
   * Apply transforms sequent ally.  f any transform results  n a dropped (None)
   * DataRecord, t n ent re transform sequence w ll result  n a dropped DataRecord.
   * Note that transforms are order-dependent.
   */
  def sequent allyTransform(dataRecord: DataRecord): Opt on[DataRecord] = {
    val recordOpt = Opt on(new DataRecord(dataRecord))
    onl nePreTransforms.foldLeft(recordOpt) {
      case (So (prev ousRecord), preTransform) =>
        preTransform(prev ousRecord)
      case _ => Opt on.empty[DataRecord]
    }
  }
}

tra  RealT  AggregatesJobConf gs {
  def Prod: RealT  AggregatesJobConf g
  def Devel: RealT  AggregatesJobConf g
}

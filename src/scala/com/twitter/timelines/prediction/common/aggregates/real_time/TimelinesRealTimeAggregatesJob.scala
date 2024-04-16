package com.tw ter.t  l nes.pred ct on.common.aggregates.real_t  

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.f nagle.stats.DefaultStatsRece ver
 mport com.tw ter.summ ngb rd.Opt ons
 mport com.tw ter.summ ngb rd.onl ne.opt on.FlatMapParallel sm
 mport com.tw ter.summ ngb rd.onl ne.opt on.S ceParallel sm
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.aggregat on_fra work. ron._
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.DownsampleTransform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.R ch Transform
 mport com.tw ter.t  l nes.data_process ng.ml_ut l.transforms.UserDownsampleTransform

 mport com.tw ter.t  l nes.pred ct on.common.aggregates.BCELabelTransformFromUUADataRecord

/**
 * Sets up relevant topology para ters.   pr mary goal  s to handle t 
 * LogEvent stream and aggregate (sum) on t  parsed DataRecords w hout fall ng
 * beh nd.   constra nt  s t  result ng wr e (and read) QPS to t  back ng
 *  mcac  store.
 *
 *  f t  job  s fall ng beh nd, add more flatMappers and/or Sum rs after
 *  nspect ng t  v z panels for t  respect ve job (go/ ron-u ). An  ncrease  n
 * Sum rs (and/or aggregat on keys and features  n t  conf g) results  n an
 *  ncrease  n  mcac  QPS (go/cb and search for   cac ). Adjust w h Cac S ze
 * sett ngs unt l QPS  s  ll-controlled.
 *
 */
object T  l nesRealT  AggregatesJobConf gs extends RealT  AggregatesJobConf gs {
   mport T  l nesOnl neAggregat onUt ls._

  /**
   *   remove  nput records that do not conta n a label/engage nt as def ned  n AllT etLabels, wh ch  ncludes
   * expl c  user engage nts  nclud ng publ c, pr vate and  mpress on events. By avo d ng  ngest ng records w hout
   * engagemnts,   guarantee that no d str but on sh fts occur  n computed aggregate features w n   add a new spout
   * to  nput aggregate s ces. Counterfactual s gnal  s st ll ava lable s nce   aggregate on expl c  d ll
   * engage nts.
   */
  val Negat veDownsampleTransform =
    DownsampleTransform(
      negat veSampl ngRate = 0.0,
      keepLabels = AllT etLabels,
      pos  veSampl ngRate = 1.0)

  /**
   *   downsample pos  ve engage nts for devel topology to reduce traff c, a m ng for equ valent of 10% of prod traff c.
   * F rst apply cons stent downsampl ng to 10% of users, and t n apply downsampl ng to remove records w hout
   * expl c  labels.   apply user-cons stent sampl ng to more closely approx mate prod query patterns.
   */
  val Stag ngUserBasedDownsampleTransform =
    UserDownsampleTransform(
      ava lab l y = 1000,
      featureNa  = "rta_devel"
    )

  overr de val Prod = RealT  AggregatesJobConf g(
    app d = "summ ngb rd_t  l nes_rta",
    topologyWorkers = 1450,
    s ceCount = 120,
    flatMapCount = 1800,
    sum rCount = 3850,
    cac S ze = 200,
    conta nerRamG gaBytes = 54,
    na  = "t  l nes_real_t  _aggregates",
    teamNa  = "t  l nes",
    teamEma l = "",
    //  f one component  s h t ng GC l m  at prod, tune componentTo taSpaceS zeMap.
    // Except for S ce bolts. Tune componentToRamG gaBytesMap for S ce bolts  nstead.
    componentTo taSpaceS zeMap = Map(
      "Ta l-FlatMap" -> "-XX:Max taspaceS ze=1024M -XX: taspaceS ze=1024M",
      "Ta l" -> "-XX:Max taspaceS ze=2560M -XX: taspaceS ze=2560M"
    ),
    //  f e  r component  s h t ng  mory l m  at prod
    //  s  mory need to  ncrease: e  r  ncrease total  mory of conta ner (conta nerRamG gaBytes),
    // or allocate more  mory for one component wh le keep ng total  mory unchanged.
    componentToRamG gaBytesMap = Map(
      "Ta l-FlatMap-S ce" -> 3, // Ho  s ce
      "Ta l-FlatMap-S ce.2" -> 3, // Prof le s ce
      "Ta l-FlatMap-S ce.3" -> 3, // Search s ce
      "Ta l-FlatMap-S ce.4" -> 3, // UUA s ce
      "Ta l-FlatMap" -> 8
      // Ta l w ll use t  leftover  mory  n t  conta ner.
      // Make sure to tune topologyWorkers and conta nerRamG gaBytes such that t   s greater than 10 GB.
    ),
    topologyNa dOpt ons = Map(
      "TL_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(120)),
      "PROF LE_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(30)),
      "SEARCH_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(10)),
      "UUA_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(10)),
      "COMB NED_PRODUCER" -> Opt ons()
        .set(FlatMapParallel sm(1800))
    ),
    // T  UUA datarecord for BCE events  nputted w ll not have b nary labels populated.
    // BCELabelTransform w ll set t  datarecord w h b nary BCE d ll labels features based on t  correspond ng d ll_t  _ms.
    //  's  mportant to have t  BCELabelTransformFromUUADataRecord before ProdNegat veDownsampleTransform
    // because ProdNegat veDownsampleTransform w ll remove datarecord that conta ns no features from AllT etLabels.
    onl nePreTransforms =
      Seq(R ch Transform(BCELabelTransformFromUUADataRecord), Negat veDownsampleTransform)
  )

  /**
   *   downsample 10% computat on of devel RTA based on [[Stag ngNegat veDownsampleTransform]].
   * To better test scalab l y of topology,   reduce comput ng res ce of components "Ta l-FlatMap"
   * and "Ta l" to be 10% of prod but keep comput ng res ce of component "Ta l-FlatMap-S ce" unchanged.
   *  nce flatMapCount=110, sum rCount=105 and s ceCount=100.  nce topologyWorkers =(110+105+100)/5 = 63.
   */
  overr de val Devel = RealT  AggregatesJobConf g(
    app d = "summ ngb rd_t  l nes_rta_devel",
    topologyWorkers = 120,
    s ceCount = 120,
    flatMapCount = 150,
    sum rCount = 300,
    cac S ze = 200,
    conta nerRamG gaBytes = 54,
    na  = "t  l nes_real_t  _aggregates_devel",
    teamNa  = "t  l nes",
    teamEma l = "",
    //  f one component  s h t ng GC l m  at prod, tune componentTo taSpaceS zeMap
    // Except for S ce bolts. Tune componentToRamG gaBytesMap for S ce bolts  nstead.
    componentTo taSpaceS zeMap = Map(
      "Ta l-FlatMap" -> "-XX:Max taspaceS ze=1024M -XX: taspaceS ze=1024M",
      "Ta l" -> "-XX:Max taspaceS ze=2560M -XX: taspaceS ze=2560M"
    ),
    //  f e  r component  s h t ng  mory l m  at prod
    //  s  mory need to  ncrease: e  r  ncrease total  mory of conta ner (conta nerRamG gaBytes),
    // or allocate more  mory for one component wh le keep ng total  mory unchanged.
    componentToRamG gaBytesMap = Map(
      "Ta l-FlatMap-S ce" -> 3, // Ho  s ce
      "Ta l-FlatMap-S ce.2" -> 3, // Prof le s ce
      "Ta l-FlatMap-S ce.3" -> 3, // Search s ce
      "Ta l-FlatMap-S ce.4" -> 3, // UUA s ce
      "Ta l-FlatMap" -> 8
      // Ta l w ll use t  leftover  mory  n t  conta ner.
      // Make sure to tune topologyWorkers and conta nerRamG gaBytes such that t   s greater than 10 GB.
    ),
    topologyNa dOpt ons = Map(
      "TL_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(120)),
      "PROF LE_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(30)),
      "SEARCH_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(10)),
      "UUA_EVENTS_SOURCE" -> Opt ons()
        .set(S ceParallel sm(10)),
      "COMB NED_PRODUCER" -> Opt ons()
        .set(FlatMapParallel sm(150))
    ),
    //  's  mportant to have t  BCELabelTransformFromUUADataRecord before ProdNegat veDownsampleTransform
    onl nePreTransforms = Seq(
      Stag ngUserBasedDownsampleTransform,
      R ch Transform(BCELabelTransformFromUUADataRecord),
      Negat veDownsampleTransform),
    enableUserRe ndex ngN ghthawkBtreeStore = true,
    enableUserRe ndex ngN ghthawkHashStore = true,
    userRe ndex ngN ghthawkBtreeStoreConf g = N ghthawkUnderly ngStoreConf g(
      serversetPath =
        "/tw ter/serv ce/cac -user/test/n ghthawk_t  l nes_real_t  _aggregates_btree_test_ap ",
      // NOTE: table na s are pref xed to every pkey so keep   short
      tableNa  = "u_r_v1", // (u)ser_(r)e ndex ng_v1
      // keep ttl <= 1 day because  's keyed on user, and   w ll have l m ed h  rates beyond 1 day
      cac TTL = 1.day
    ),
    userRe ndex ngN ghthawkHashStoreConf g = N ghthawkUnderly ngStoreConf g(
      // For prod: "/s/cac -user/n ghthawk_t  l nes_real_t  _aggregates_hash_ap ",
      serversetPath =
        "/tw ter/serv ce/cac -user/test/n ghthawk_t  l nes_real_t  _aggregates_hash_test_ap ",
      // NOTE: table na s are pref xed to every pkey so keep   short
      tableNa  = "u_r_v1", // (u)ser_(r)e ndex ng_v1
      // keep ttl <= 1 day because  's keyed on user, and   w ll have l m ed h  rates beyond 1 day
      cac TTL = 1.day
    )
  )
}

object T  l nesRealT  AggregatesJob extends RealT  AggregatesJobBase {
  overr de lazy val statsRece ver = DefaultStatsRece ver.scope("t  l nes_real_t  _aggregates")
  overr de lazy val jobConf gs = T  l nesRealT  AggregatesJobConf gs
  overr de lazy val aggregatesToCompute = T  l nesOnl neAggregat onConf g.AggregatesToCompute
}

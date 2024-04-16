package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.f lter.PreRankF lterRunner
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.RelatedV deoT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.T etW hCand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model.ModuleNa s
 mport com.tw ter.cr_m xer.s m lar y_eng ne.StandardS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.T etBasedUn f edS m lar yEng ne
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap 
 mport com.tw ter.ut l.Future
 mport javax. nject. nject
 mport javax. nject.Na d
 mport javax. nject.S ngleton

@S ngleton
class RelatedV deoT etCand dateGenerator @ nject() (
  @Na d(ModuleNa s.T etBasedUn f edS m lar yEng ne) t etBasedUn f edS m lar yEng ne: StandardS m lar yEng ne[
    T etBasedUn f edS m lar yEng ne.Query,
    T etW hCand dateGenerat on nfo
  ],
  preRankF lterRunner: PreRankF lterRunner,
  t et nfoStore: ReadableStore[T et d, T et nfo],
  globalStats: StatsRece ver) {

  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val preRankF lterStats = stats.scope("preRankF lter")

  def get(
    query: RelatedV deoT etCand dateGeneratorQuery
  ): Future[Seq[ n  alCand date]] = {

    val allStats = stats.scope("all")
    val perProductStats = stats.scope("perProduct", query.product.toStr ng)
    StatsUt l.track emsStats(allStats) {
      StatsUt l.track emsStats(perProductStats) {
        for {
           n  alCand dates <- StatsUt l.trackBlockStats(fetchCand datesStats) {
            fetchCand dates(query)
          }
          f lteredCand dates <- StatsUt l.trackBlockStats(preRankF lterStats) {
            preRankF lter(query,  n  alCand dates)
          }
        } y eld {
          f lteredCand dates. adOpt on
            .getOrElse(
              throw new UnsupportedOperat onExcept on(
                "RelatedV deoT etCand dateGenerator results  nval d")
            ).take(query.maxNumResults)
        }
      }
    }
  }

  def fetchCand dates(
    query: RelatedV deoT etCand dateGeneratorQuery
  ): Future[Seq[Seq[ n  alCand date]]] = {
    query. nternal d match {
      case  nternal d.T et d(_) =>
        getCand datesFromS m lar yEng ne(
          query,
          T etBasedUn f edS m lar yEng ne.fromParamsForRelatedV deoT et,
          t etBasedUn f edS m lar yEng ne.getCand dates)
      case _ =>
        throw new UnsupportedOperat onExcept on(
          "RelatedV deoT etCand dateGenerator gets  nval d  nternal d")
    }
  }

  /***
   * fetch Cand dates from T etBased/ProducerBased Un f ed S m lar y Eng ne,
   * and apply VF f lter based on T et nfoStore
   * To al gn w h t  downstream process ng (f lter, rank),   tend to return a Seq[Seq[ n  alCand date]]
   *  nstead of a Seq[Cand date] even though   only have a Seq  n  .
   */
  pr vate def getCand datesFromS m lar yEng ne[QueryType](
    query: RelatedV deoT etCand dateGeneratorQuery,
    fromParamsForRelatedV deoT et: ( nternal d, conf gap .Params) => QueryType,
    getFunc: QueryType => Future[Opt on[Seq[T etW hCand dateGenerat on nfo]]]
  ): Future[Seq[Seq[ n  alCand date]]] = {

    /***
     *   wrap t  query to be a Seq of quer es for t  S m Eng ne to ensure evolvab l y of cand date generat on
     * and as a result,   w ll return Seq[Seq[ n  alCand date]]
     */
    val eng neQuer es =
      Seq(fromParamsForRelatedV deoT et(query. nternal d, query.params))

    Future
      .collect {
        eng neQuer es.map { query =>
          for {
            cand dates <- getFunc(query)
            pref lterCand dates <- convertTo n  alCand dates(
              cand dates.toSeq.flatten
            )
          } y eld pref lterCand dates
        }
      }
  }

  pr vate def preRankF lter(
    query: RelatedV deoT etCand dateGeneratorQuery,
    cand dates: Seq[Seq[ n  alCand date]]
  ): Future[Seq[Seq[ n  alCand date]]] = {
    preRankF lterRunner
      .runSequent alF lters(query, cand dates)
  }

  pr vate[cand date_generat on] def convertTo n  alCand dates(
    cand dates: Seq[T etW hCand dateGenerat on nfo],
  ): Future[Seq[ n  alCand date]] = {
    val t et ds = cand dates.map(_.t et d).toSet
    Future.collect(t et nfoStore.mult Get(t et ds)).map { t et nfos =>
      /***
       *  f t et nfo does not ex st,   w ll f lter out t  t et cand date.
       * T  t et nfo f lter also acts as t  VF f lter
       */
      cand dates.collect {
        case cand date  f t et nfos.getOrElse(cand date.t et d, None). sDef ned =>
          val t et nfo = t et nfos(cand date.t et d)
            .getOrElse(throw new  llegalStateExcept on("C ck prev ous l ne's cond  on"))

           n  alCand date(
            t et d = cand date.t et d,
            t et nfo = t et nfo,
            cand date.cand dateGenerat on nfo
          )
      }
    }
  }
}

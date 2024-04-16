package com.tw ter.cr_m xer.cand date_generat on

 mport com.tw ter.contentrecom nder.thr ftscala.T et nfo
 mport com.tw ter.cr_m xer.conf g.T  outConf g
 mport com.tw ter.cr_m xer.model.Cand dateGenerat on nfo
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.model.S m lar yEng ne nfo
 mport com.tw ter.cr_m xer.model.Top cT etCand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model.Top cT etW hScore
 mport com.tw ter.cr_m xer.param.Top cT etParams
 mport com.tw ter.cr_m xer.s m lar y_eng ne.CertoTop cT etS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Sk H ghPrec s onTop cT etS m lar yEng ne
 mport com.tw ter.cr_m xer.s m lar y_eng ne.Sk Top cT etS m lar yEng ne
 mport com.tw ter.cr_m xer.thr ftscala.S m lar yEng neType
 mport com.tw ter.cr_m xer.thr ftscala.Top cT et
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.f nagle.ut l.DefaultT  r
 mport com.tw ter.fr gate.common.ut l.StatsUt l
 mport com.tw ter.servo.ut l. mo z ngStatsRece ver
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala.Top c d
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

/**
 * For rly CrTop c  n legacy Content Recom nder. T  generator f nds top T ets per Top c.
 */
@S ngleton
class Top cT etCand dateGenerator @ nject() (
  certoTop cT etS m lar yEng ne: CertoTop cT etS m lar yEng ne,
  sk Top cT etS m lar yEng ne: Sk Top cT etS m lar yEng ne,
  sk H ghPrec s onTop cT etS m lar yEng ne: Sk H ghPrec s onTop cT etS m lar yEng ne,
  t et nfoStore: ReadableStore[T et d, T et nfo],
  t  outConf g: T  outConf g,
  globalStats: StatsRece ver) {
  pr vate val t  r = DefaultT  r
  pr vate val stats: StatsRece ver = globalStats.scope(t .getClass.getCanon calNa )
  pr vate val fetchCand datesStats = stats.scope("fetchCand dates")
  pr vate val f lterCand datesStats = stats.scope("f lterCand dates")
  pr vate val t etyP eF lteredStats = f lterCand datesStats.stat("t etyp e_f ltered")
  pr vate val  mo zedStatsRece ver = new  mo z ngStatsRece ver(stats)

  def get(
    query: Top cT etCand dateGeneratorQuery
  ): Future[Map[Long, Seq[Top cT et]]] = {
    val maxT etAge = query.params(Top cT etParams.MaxT etAge)
    val product = query.product
    val allStats =  mo zedStatsRece ver.scope("all")
    val perProductStats =  mo zedStatsRece ver.scope("perProduct", product.na )
    StatsUt l.trackMapValueStats(allStats) {
      StatsUt l.trackMapValueStats(perProductStats) {
        val result = for {
          retr evedT ets <- fetchCand dates(query)
           n  alT etCand dates <- convertTo n  alCand dates(retr evedT ets)
          f lteredT etCand dates <- f lterCand dates(
             n  alT etCand dates,
            maxT etAge,
            query. sV deoOnly,
            query. mpressedT etL st)
          rankedT etCand dates = rankCand dates(f lteredT etCand dates)
          hydratedT etCand dates = hydrateCand dates(rankedT etCand dates)
        } y eld {
          hydratedT etCand dates.map {
            case (top c d, top cT ets) =>
              val topKT ets = top cT ets.take(query.maxNumResults)
              top c d -> topKT ets
          }
        }
        result.ra seW h n(t  outConf g.top cT etEndpo ntT  out)(t  r)
      }
    }
  }

  pr vate def fetchCand dates(
    query: Top cT etCand dateGeneratorQuery
  ): Future[Map[Top c d, Opt on[Seq[Top cT etW hScore]]]] = {
    Future.collect {
      query.top c ds.map { top c d =>
        top c d -> StatsUt l.trackOpt onStats(fetchCand datesStats) {
          Future
            .jo n(
              certoTop cT etS m lar yEng ne.get(CertoTop cT etS m lar yEng ne
                .fromParams(top c d, query. sV deoOnly, query.params)),
              sk Top cT etS m lar yEng ne
                .get(Sk Top cT etS m lar yEng ne
                  .fromParams(top c d, query. sV deoOnly, query.params)),
              sk H ghPrec s onTop cT etS m lar yEng ne
                .get(Sk H ghPrec s onTop cT etS m lar yEng ne
                  .fromParams(top c d, query. sV deoOnly, query.params))
            ).map {
              case (certoTop cT ets, sk TfgTop cT ets, sk H ghPrec s onTop cT ets) =>
                val un queCand dates = (certoTop cT ets.getOrElse(N l) ++
                  sk TfgTop cT ets.getOrElse(N l) ++
                  sk H ghPrec s onTop cT ets.getOrElse(N l))
                  .groupBy(_.t et d).map {
                    case (_, dupCand dates) => dupCand dates. ad
                  }.toSeq
                So (un queCand dates)
            }
        }
      }.toMap
    }
  }

  pr vate def convertTo n  alCand dates(
    cand datesMap: Map[Top c d, Opt on[Seq[Top cT etW hScore]]]
  ): Future[Map[Top c d, Seq[ n  alCand date]]] = {
    val  n  alCand dates = cand datesMap.map {
      case (top c d, cand datesOpt) =>
        val cand dates = cand datesOpt.getOrElse(N l)
        val t et ds = cand dates.map(_.t et d).toSet
        val numT etsPreF lter = t et ds.s ze
        Future.collect(t et nfoStore.mult Get(t et ds)).map { t et nfos =>
          /** *
           *  f t et nfo does not ex st,   w ll f lter out t  t et cand date.
           */
          val t etyP eF ltered n  alCand dates = cand dates.collect {
            case cand date  f t et nfos.getOrElse(cand date.t et d, None). sDef ned =>
              val t et nfo = t et nfos(cand date.t et d)
                .getOrElse(throw new  llegalStateExcept on("C ck prev ous l ne's cond  on"))

               n  alCand date(
                t et d = cand date.t et d,
                t et nfo = t et nfo,
                Cand dateGenerat on nfo(
                  None,
                  S m lar yEng ne nfo(
                    s m lar yEng neType = cand date.s m lar yEng neType,
                    model d = None,
                    score = So (cand date.score)),
                  Seq.empty
                )
              )
          }
          val numT etsPostF lter = t etyP eF ltered n  alCand dates.s ze
          t etyP eF lteredStats.add(numT etsPreF lter - numT etsPostF lter)
          top c d -> t etyP eF ltered n  alCand dates
        }
    }

    Future.collect( n  alCand dates.toSeq).map(_.toMap)
  }

  pr vate def f lterCand dates(
    top cT etMap: Map[Top c d, Seq[ n  alCand date]],
    maxT etAge: Durat on,
     sV deoOnly: Boolean,
    excludeT et ds: Set[T et d]
  ): Future[Map[Top c d, Seq[ n  alCand date]]] = {

    val earl estT et d = Snowflake d.f rst dFor(T  .now - maxT etAge)

    val f lteredResults = top cT etMap.map {
      case (top c d, t etsW hScore) =>
        top c d -> StatsUt l.track emsStats(f lterCand datesStats) {

          val t  F lteredT ets =
            t etsW hScore.f lter { t etW hScore =>
              t etW hScore.t et d >= earl estT et d && !excludeT et ds.conta ns(
                t etW hScore.t et d)
            }

          f lterCand datesStats
            .stat("exclude_and_t  _f ltered").add(t etsW hScore.s ze - t  F lteredT ets.s ze)

          val t etNud yF lteredT ets =
            t  F lteredT ets.collect {
              case t et  f t et.t et nfo. sPassT et d aNud yTag.conta ns(true) => t et
            }

          f lterCand datesStats
            .stat("t et_nud y_f ltered").add(
              t  F lteredT ets.s ze - t etNud yF lteredT ets.s ze)

          val userNud yF lteredT ets =
            t etNud yF lteredT ets.collect {
              case t et  f t et.t et nfo. sPassUserNud yRateStr ct.conta ns(true) => t et
            }

          f lterCand datesStats
            .stat("user_nud y_f ltered").add(
              t etNud yF lteredT ets.s ze - userNud yF lteredT ets.s ze)

          val v deoF lteredT ets = {
             f ( sV deoOnly) {
              userNud yF lteredT ets.collect {
                case t et  f t et.t et nfo.hasV deo.conta ns(true) => t et
              }
            } else {
              userNud yF lteredT ets
            }
          }

          Future.value(v deoF lteredT ets)
        }
    }
    Future.collect(f lteredResults)
  }

  pr vate def rankCand dates(
    t etCand datesMap: Map[Top c d, Seq[ n  alCand date]]
  ): Map[Top c d, Seq[ n  alCand date]] = {
    t etCand datesMap.mapValues { t etCand dates =>
      t etCand dates.sortBy { cand date =>
        -cand date.t et nfo.favCount
      }
    }
  }

  pr vate def hydrateCand dates(
    top cCand datesMap: Map[Top c d, Seq[ n  alCand date]]
  ): Map[Long, Seq[Top cT et]] = {
    top cCand datesMap.map {
      case (top c d, t etsW hScore) =>
        top c d.ent y d ->
          t etsW hScore.map { t etW hScore =>
            val s m lar yEng neType: S m lar yEng neType =
              t etW hScore.cand dateGenerat on nfo.s m lar yEng ne nfo.s m lar yEng neType
            Top cT et(
              t et d = t etW hScore.t et d,
              score = t etW hScore.getS m lar yScore,
              s m lar yEng neType = s m lar yEng neType
            )
          }
    }
  }
}

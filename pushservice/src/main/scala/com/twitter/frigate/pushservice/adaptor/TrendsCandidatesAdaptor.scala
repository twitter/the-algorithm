package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.events.recos.thr ftscala.D splayLocat on
 mport com.tw ter.events.recos.thr ftscala.TrendsContext
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base.TrendT etCand date
 mport com.tw ter.fr gate.common.base.TrendsCand date
 mport com.tw ter.fr gate.common.cand date.Recom ndedTrendsCand dateS ce
 mport com.tw ter.fr gate.common.cand date.Recom ndedTrendsCand dateS ce.Query
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.adaptor.TrendsCand datesAdaptor._
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.geoduck.common.thr ftscala.Locat on
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. rm .store.t etyp e.UserT et
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport scala.collect on.Map

object TrendsCand datesAdaptor {
  type T et d = Long
  type Event d = Long
}

case class TrendsCand datesAdaptor(
  softUserGeoLocat onStore: ReadableStore[Long, Locat on],
  recom ndedTrendsCand dateS ce: Recom ndedTrendsCand dateS ce,
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  safeUserT etT etyP eStore: ReadableStore[UserT et, T etyP eResult],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {
  overr de val na  = t .getClass.getS mpleNa 

  pr vate val trendAdaptorStats = statsRece ver.scope("TrendsCand datesAdaptor")
  pr vate val trendT etCand dateNumber = trendAdaptorStats.counter("trend_t et_cand date")
  pr vate val nonReplyT etsCounter = trendAdaptorStats.counter("non_reply_t ets")

  pr vate def getQuery(target: Target): Future[Query] = {
    def getUserCountryCode(target: Target): Future[Opt on[Str ng]] = {
      target.targetUser.flatMap {
        case So (user)  f user.userType == UserType.Soft =>
          softUserGeoLocat onStore
            .get(user. d)
            .map(_.flatMap(_.s mpleRgcResult.flatMap(_.countryCodeAlpha2)))

        case _ => target.accountCountryCode
      }
    }

    for {
      countryCode <- getUserCountryCode(target)
       nferredLanguage <- target. nferredUserDev ceLanguage
    } y eld {
      Query(
        user d = target.target d,
        d splayLocat on = D splayLocat on.Mag cRecs,
        languageCode =  nferredLanguage,
        countryCode = countryCode,
        maxResults = target.params(PushFeatureSw chParams.MaxRecom ndedTrendsToQuery)
      )
    }
  }

  /**
   * Query cand dates only  f sent at most [[PushFeatureSw chParams.MaxTrendT etNot f cat ons nDurat on]]
   * trend t et not f cat ons  n [[PushFeatureSw chParams.TrendT etNot f cat onsFat gueDurat on]]
   */
  val trendT etFat guePred cate = TargetPred cates.pushRecTypeFat guePred cate(
    CommonRecom ndat onType.TrendT et,
    PushFeatureSw chParams.TrendT etNot f cat onsFat gueDurat on,
    PushFeatureSw chParams.MaxTrendT etNot f cat ons nDurat on,
    trendAdaptorStats
  )

  pr vate val recom ndedTrendsW hT etsCand dateS ce: Cand dateS ce[
    Target,
    RawCand date w h TrendsCand date
  ] = recom ndedTrendsCand dateS ce
    .convert[Target, TrendsCand date](
      getQuery,
      recom ndedTrendsCand dateS ce. dent yCand dateMapper
    )
    .batchMapValues[Target, RawCand date w h TrendsCand date](
      trendsCand datesToT etCand dates(_, _, getT etyP eResults))

  pr vate def getT etyP eResults(
    t et ds: Seq[T et d],
    target: Target
  ): Future[Map[T et d, T etyP eResult]] = {
     f (target.params(PushFeatureSw chParams.EnableSafeUserT etT etyp eStore)) {
      Future
        .collect(
          safeUserT etT etyP eStore.mult Get(
            t et ds.toSet.map(UserT et(_, So (target.target d))))).map {
          _.collect {
            case (userT et, So (t etyP eResult)) => userT et.t et d -> t etyP eResult
          }
        }
    } else {
      Future
        .collect((target.params(PushFeatureSw chParams.EnableVF nT etyp e) match {
          case true => t etyP eStore
          case false => t etyP eStoreNoVF
        }).mult Get(t et ds.toSet)).map { t etyP eResultMap =>
          f lterOutReplyT et(t etyP eResultMap, nonReplyT etsCounter).collect {
            case (t et d, So (t etyP eResult)) => t et d -> t etyP eResult
          }
        }
    }
  }

  /**
   *
   * @param _target: [[Target]] object represent ng not f ca on rec p ent user
   * @param trendsCand dates: Sequence of [[TrendsCand date]] returned from ERS
   * @return: Seq of trends cand dates expanded to assoc ated t ets.
   */
  pr vate def trendsCand datesToT etCand dates(
    _target: Target,
    trendsCand dates: Seq[TrendsCand date],
    getT etyP eResults: (Seq[T et d], Target) => Future[Map[T et d, T etyP eResult]]
  ): Future[Seq[RawCand date w h TrendsCand date]] = {

    def generateTrendT etCand dates(
      trendCand date: TrendsCand date,
      t etyP eResults: Map[T et d, T etyP eResult]
    ) = {
      val t et ds = trendCand date.context.curatedRepresentat veT ets.getOrElse(Seq.empty) ++
        trendCand date.context.algoRepresentat veT ets.getOrElse(Seq.empty)

      t et ds.flatMap { t et d =>
        t etyP eResults.get(t et d).map { _t etyP eResult =>
          new RawCand date w h TrendT etCand date {
            overr de val trend d: Str ng = trendCand date.trend d
            overr de val trendNa : Str ng = trendCand date.trendNa 
            overr de val land ngUrl: Str ng = trendCand date.land ngUrl
            overr de val t  BoundedLand ngUrl: Opt on[Str ng] =
              trendCand date.t  BoundedLand ngUrl
            overr de val context: TrendsContext = trendCand date.context
            overr de val t etyP eResult: Opt on[T etyP eResult] = So (_t etyP eResult)
            overr de val t et d: T et d = _t etyP eResult.t et. d
            overr de val target: Target = _target
          }
        }
      }
    }

    // collect all t et  ds assoc ated w h all trends
    val allT et ds = trendsCand dates.flatMap { trendsCand date =>
      val context = trendsCand date.context
      context.curatedRepresentat veT ets.getOrElse(Seq.empty) ++
        context.algoRepresentat veT ets.getOrElse(Seq.empty)
    }

    getT etyP eResults(allT et ds, _target)
      .map { t et dToT etyP eResult =>
        val trendT etCand dates = trendsCand dates.flatMap { trendCand date =>
          val allTrendT etCand dates = generateTrendT etCand dates(
            trendCand date,
            t et dToT etyP eResult
          )

          val (t etCand datesFromCuratedTrends, t etCand datesFromNonCuratedTrends) =
            allTrendT etCand dates.part  on(_. sCuratedTrend)

          t etCand datesFromCuratedTrends.f lter(
            _.target.params(PushFeatureSw chParams.EnableCuratedTrendT ets)) ++
            t etCand datesFromNonCuratedTrends.f lter(
              _.target.params(PushFeatureSw chParams.EnableNonCuratedTrendT ets))
        }

        trendT etCand dateNumber. ncr(trendT etCand dates.s ze)
        trendT etCand dates
      }
  }

  /**
   *
   * @param target: [[Target]] user
   * @return: true  f custo r  s el g ble to rece ve trend t et not f cat ons
   *
   */
  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l
      . sRecom ndat onsEl g ble(target)
      .map(target.params(PushParams.TrendsCand dateDec der) && _)
  }

  overr de def get(target: Target): Future[Opt on[Seq[RawCand date w h TrendsCand date]]] = {
    recom ndedTrendsW hT etsCand dateS ce
      .get(target)
      .flatMap {
        case So (cand dates)  f cand dates.nonEmpty =>
          trendT etFat guePred cate(Seq(target))
            .map(_. ad)
            .map {  sTargetFat gueEl g ble =>
               f ( sTargetFat gueEl g ble) So (cand dates)
              else None
            }

        case _ => Future.None
      }
  }
}

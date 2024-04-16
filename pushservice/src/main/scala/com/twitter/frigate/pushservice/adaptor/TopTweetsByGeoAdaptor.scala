package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes
 mport com.tw ter.fr gate.pushserv ce.params.PopGeoT etVers on
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.params.TopT etsForGeoComb nat on
 mport com.tw ter.fr gate.pushserv ce.params.TopT etsForGeoRank ngFunct on
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FS}
 mport com.tw ter.fr gate.pushserv ce.pred cate.D scoverTw terPred cate
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l. d aCRT
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAdaptorUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.geoduck.common.thr ftscala.{Locat on => GeoLocat on}
 mport com.tw ter.geoduck.serv ce.thr ftscala.Locat onResponse
 mport com.tw ter.g zmoduck.thr ftscala.UserType
 mport com.tw ter. rm .pop_geo.thr ftscala.PopT ets nPlace
 mport com.tw ter.recom ndat on. nterests.d scovery.core.model. nterestDoma n
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.FutureOps
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport scala.collect on.Map

case class PlaceT etScore(place: Str ng, t et d: Long, score: Double) {
  def toT etScore: (Long, Double) = (t et d, score)
}
case class TopT etsByGeoAdaptor(
  geoduckStoreV2: ReadableStore[Long, Locat onResponse],
  softUserGeoLocat onStore: ReadableStore[Long, GeoLocat on],
  topT etsByGeoStore: ReadableStore[ nterestDoma n[Str ng], Map[Str ng, L st[(Long, Double)]]],
  topT etsByGeoStoreV2: ReadableStore[Str ng, PopT ets nPlace],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de def na : Str ng = t .getClass.getS mpleNa 

  pr vate[t ] val stats = globalStats.scope("TopT etsByGeoAdaptor")
  pr vate[t ] val noGeohashUserCounter: Counter = stats.counter("users_w h_no_geohash_counter")
  pr vate[t ] val  ncom ngRequestCounter: Counter = stats.counter(" ncom ng_request_counter")
  pr vate[t ] val  ncom ngLoggedOutRequestCounter: Counter =
    stats.counter(" ncom ng_logged_out_request_counter")
  pr vate[t ] val loggedOutRawCand datesCounter =
    stats.counter("logged_out_raw_cand dates_counter")
  pr vate[t ] val emptyLoggedOutRawCand datesCounter =
    stats.counter("logged_out_empty_raw_cand dates")
  pr vate[t ] val outputTopT etsByGeoCounter: Stat =
    stats.stat("output_top_t ets_by_geo_counter")
  pr vate[t ] val loggedOutPopByGeoV2Cand datesCounter: Counter =
    stats.counter("logged_out_pop_by_geo_cand dates")
  pr vate[t ] val dormantUsersS nce14DaysCounter: Counter =
    stats.counter("dormant_user_s nce_14_days_counter")
  pr vate[t ] val dormantUsersS nce30DaysCounter: Counter =
    stats.counter("dormant_user_s nce_30_days_counter")
  pr vate[t ] val nonDormantUsersS nce14DaysCounter: Counter =
    stats.counter("non_dormant_user_s nce_14_days_counter")
  pr vate[t ] val topT etsByGeoTake100Counter: Counter =
    stats.counter("top_t ets_by_geo_take_100_counter")
  pr vate[t ] val comb nat onRequestsCounter =
    stats.scope("comb nat on_ thod_request_counter")
  pr vate[t ] val popGeoT etVers onCounter =
    stats.scope("popgeo_t et_vers on_counter")
  pr vate[t ] val nonReplyT etsCounter = stats.counter("non_reply_t ets")

  val MaxGeoHashS ze = 4

  pr vate def constructKeys(
    geohash: Opt on[Str ng],
    accountCountryCode: Opt on[Str ng],
    keyLengths: Seq[ nt],
    vers on: PopGeoT etVers on.Value
  ): Set[Str ng] = {
    val geohashKeys = geohash match {
      case So (hash) => keyLengths.map { vers on + "_geohash_" + hash.take(_) }
      case _ => Seq.empty
    }

    val accountCountryCodeKeys =
      accountCountryCode.toSeq.map(vers on + "_country_" + _.toUpperCase)
    (geohashKeys ++ accountCountryCodeKeys).toSet
  }

  def convertToPlaceT etScore(
    popT ets nPlace: Seq[PopT ets nPlace]
  ): Seq[PlaceT etScore] = {
    popT ets nPlace.flatMap {
      case p =>
        p.popT ets.map {
          case popT et => PlaceT etScore(p.place, popT et.t et d, popT et.score)
        }
    }
  }

  def sortGeoHashT ets(
    placeT etScores: Seq[PlaceT etScore],
    rank ngFunct on: TopT etsForGeoRank ngFunct on.Value
  ): Seq[PlaceT etScore] = {
    rank ngFunct on match {
      case TopT etsForGeoRank ngFunct on.Score =>
        placeT etScores.sortBy(_.score)(Order ng[Double].reverse)
      case TopT etsForGeoRank ngFunct on.GeohashLengthAndT nScore =>
        placeT etScores
          .sortBy(row => (row.place.length, row.score))(Order ng[( nt, Double)].reverse)
    }
  }

  def getResultsForLambdaStore(
     nputTarget: Target,
    geohash: Opt on[Str ng],
    store: ReadableStore[Str ng, PopT ets nPlace],
    topk:  nt,
    vers on: PopGeoT etVers on.Value
  ): Future[Seq[(Long, Double)]] = {
     nputTarget.accountCountryCode.flatMap { countryCode =>
      val keys = {
         f ( nputTarget.params(FS.EnableCountryCodeBackoffTopT etsByGeo))
          constructKeys(geohash, countryCode,  nputTarget.params(FS.GeoHashLengthL st), vers on)
        else
          constructKeys(geohash, None,  nputTarget.params(FS.GeoHashLengthL st), vers on)
      }
      FutureOps
        .mapCollect(store.mult Get(keys)).map {
          case geohashT etMap =>
            val popT ets =
              geohashT etMap.values.flatten.toSeq
            val results = sortGeoHashT ets(
              convertToPlaceT etScore(popT ets),
               nputTarget.params(FS.Rank ngFunct onForTopT etsByGeo))
              .map(_.toT etScore).take(topk)
            results
        }
    }
  }

  def getPopGeoT etsForLoggedOutUsers(
     nputTarget: Target,
    store: ReadableStore[Str ng, PopT ets nPlace]
  ): Future[Seq[(Long, Double)]] = {
     nputTarget.countryCode.flatMap { countryCode =>
      val keys = constructKeys(None, countryCode, Seq(4), PopGeoT etVers on.Prod)
      FutureOps.mapCollect(store.mult Get(keys)).map {
        case t etMap =>
          val t ets = t etMap.values.flatten.toSeq
          loggedOutPopByGeoV2Cand datesCounter. ncr(t ets.s ze)
          val popT ets = sortGeoHashT ets(
            convertToPlaceT etScore(t ets),
            TopT etsForGeoRank ngFunct on.Score).map(_.toT etScore)
          popT ets
      }
    }
  }

  def getRankedT ets(
     nputTarget: Target,
    geohash: Opt on[Str ng]
  ): Future[Seq[(Long, Double)]] = {
    val MaxTopT etsByGeoCand datesToTake =
       nputTarget.params(FS.MaxTopT etsByGeoCand datesToTake)
    val scor ngFn: Str ng =  nputTarget.params(FS.Scor ngFuncForTopT etsByGeo)
    val comb nat on thod =  nputTarget.params(FS.TopT etsByGeoComb nat onParam)
    val popGeoT etVers on =  nputTarget.params(FS.PopGeoT etVers onParam)

     nputTarget. s avyUserState.map {  s avyUser =>
      stats
        .scope(comb nat on thod.toStr ng).scope(popGeoT etVers on.toStr ng).scope(
          " s avyUser_" +  s avyUser.toStr ng).counter(). ncr()
    }
    comb nat onRequestsCounter.scope(comb nat on thod.toStr ng).counter(). ncr()
    popGeoT etVers onCounter.scope(popGeoT etVers on.toStr ng).counter(). ncr()
    lazy val geoStoreResults =  f (geohash. sDef ned) {
      val hash = geohash.get.take(MaxGeoHashS ze)
      topT etsByGeoStore
        .get(
           nterestDoma n[Str ng](hash)
        )
        .map {
          case So (scor ngFnToT etsMapOpt) =>
            val t etsW hScore = scor ngFnToT etsMapOpt
              .getOrElse(scor ngFn, L st.empty)
            val sortedResults = sortGeoHashT ets(
              t etsW hScore.map {
                case (t et d, score) => PlaceT etScore(hash, t et d, score)
              },
              TopT etsForGeoRank ngFunct on.Score
            ).map(_.toT etScore).take(
                MaxTopT etsByGeoCand datesToTake
              )
            sortedResults
          case _ => Seq.empty
        }
    } else Future.value(Seq.empty)
    lazy val vers onPopGeoT etResults =
      getResultsForLambdaStore(
         nputTarget,
        geohash,
        topT etsByGeoStoreV2,
        MaxTopT etsByGeoCand datesToTake,
        popGeoT etVers on
      )
    comb nat on thod match {
      case TopT etsForGeoComb nat on.Default => geoStoreResults
      case TopT etsForGeoComb nat on.AccountsT etFavAsBackf ll =>
        Future.jo n(geoStoreResults, vers onPopGeoT etResults).map {
          case (geoStoreT ets, vers onPopGeoT ets) =>
            (geoStoreT ets ++ vers onPopGeoT ets).take(MaxTopT etsByGeoCand datesToTake)
        }
      case TopT etsForGeoComb nat on.AccountsT etFav nterm xed =>
        Future.jo n(geoStoreResults, vers onPopGeoT etResults).map {
          case (geoStoreT ets, vers onPopGeoT ets) =>
            Cand dateS ce. nterleaveSeqs(Seq(geoStoreT ets, vers onPopGeoT ets))
        }
    }
  }

  overr de def get( nputTarget: Target): Future[Opt on[Seq[RawCand date]]] = {
     f ( nputTarget. sLoggedOutUser) {
       ncom ngLoggedOutRequestCounter. ncr()
      val rankedT ets = getPopGeoT etsForLoggedOutUsers( nputTarget, topT etsByGeoStoreV2)
      val rawCand dates = {
        rankedT ets.map { rt =>
          FutureOps
            .mapCollect(
              t etyP eStore
                .mult Get(rt.map { case (t et d, _) => t et d }.toSet))
            .map { t etyP eResultMap =>
              val results = bu ldTopT etsByGeoRawCand dates(
                 nputTarget,
                None,
                t etyP eResultMap
              )
               f (results. sEmpty) {
                emptyLoggedOutRawCand datesCounter. ncr()
              }
              loggedOutRawCand datesCounter. ncr(results.s ze)
              So (results)
            }
        }.flatten
      }
      rawCand dates
    } else {
       ncom ngRequestCounter. ncr()
      getGeoHashForUsers( nputTarget).flatMap { geohash =>
         f (geohash. sEmpty) noGeohashUserCounter. ncr()
        getRankedT ets( nputTarget, geohash).map { rt =>
           f (rt.s ze == 100) {
            topT etsByGeoTake100Counter. ncr(1)
          }
          FutureOps
            .mapCollect(( nputTarget.params(FS.EnableVF nT etyp e) match {
              case true => t etyP eStore
              case false => t etyP eStoreNoVF
            }).mult Get(rt.map { case (t et d, _) => t et d }.toSet))
            .map { t etyP eResultMap =>
              So (
                bu ldTopT etsByGeoRawCand dates(
                   nputTarget,
                  None,
                  f lterOutReplyT et(
                    t etyP eResultMap,
                    nonReplyT etsCounter
                  )
                )
              )
            }
        }.flatten
      }
    }
  }

  pr vate def getGeoHashForUsers(
     nputTarget: Target
  ): Future[Opt on[Str ng]] = {

     nputTarget.targetUser.flatMap {
      case So (user) =>
        user.userType match {
          case UserType.Soft =>
            softUserGeoLocat onStore
              .get( nputTarget.target d)
              .map(_.flatMap(_.geohash.flatMap(_.str ngGeohash)))

          case _ =>
            geoduckStoreV2.get( nputTarget.target d).map(_.flatMap(_.geohash))
        }

      case None => Future.None
    }
  }

  pr vate def bu ldTopT etsByGeoRawCand dates(
    target: PushTypes.Target,
    locat onNa : Opt on[Str ng],
    topT ets: Map[Long, Opt on[T etyP eResult]]
  ): Seq[RawCand date w h T etCand date] = {
    val cand dates = topT ets.map { t et dT etyP eResultMap =>
      PushAdaptorUt l.generateOutOfNetworkT etCand dates(
         nputTarget = target,
         d = t et dT etyP eResultMap._1,
         d aCRT =  d aCRT(
          CommonRecom ndat onType.GeoPopT et,
          CommonRecom ndat onType.GeoPopT et,
          CommonRecom ndat onType.GeoPopT et
        ),
        result = t et dT etyP eResultMap._2,
        local zedEnt y = None
      )
    }.toSeq
    outputTopT etsByGeoCounter.add(cand dates.length)
    cand dates
  }

  pr vate val topT etsByGeoFrequencyPred cate = {
    TargetPred cates
      .pushRecTypeFat guePred cate(
        CommonRecom ndat onType.GeoPopT et,
        FS.TopT etsByGeoPush nterval,
        FS.MaxTopT etsByGeoPushG ven nterval,
        stats
      )
  }

  def getAva lab l yForDormantUser(target: Target): Future[Boolean] = {
    lazy val  sDormantUserNotFat gued = topT etsByGeoFrequencyPred cate(Seq(target)).map(_. ad)
    lazy val enableTopT etsByGeoForDormantUsers =
      target.params(FS.EnableTopT etsByGeoCand datesForDormantUsers)

    target.lastHTLV s T  stamp.flatMap {
      case So (lastHTLT  stamp) =>
        val m nT  S nceLastLog n =
          target.params(FS.M n mumT  S nceLastLog nForGeoPopT etPush).ago
        val t  S nce nact ve = target.params(FS.T  S nceLastLog nForGeoPopT etPush).ago
        val lastAct veT  stamp = T  .fromM ll seconds(lastHTLT  stamp)
         f (lastAct veT  stamp > m nT  S nceLastLog n) {
          nonDormantUsersS nce14DaysCounter. ncr()
          Future.False
        } else {
          dormantUsersS nce14DaysCounter. ncr()
           sDormantUserNotFat gued.map {  sUserNotFat gued =>
            lastAct veT  stamp < t  S nce nact ve &&
            enableTopT etsByGeoForDormantUsers &&
             sUserNotFat gued
          }
        }
      case _ =>
        dormantUsersS nce30DaysCounter. ncr()
         sDormantUserNotFat gued.map {  sUserNotFat gued =>
          enableTopT etsByGeoForDormantUsers &&  sUserNotFat gued
        }
    }
  }

  def getAva lab l yForPlaybookSetUp(target: Target): Future[Boolean] = {
    lazy val enableTopT etsByGeoForNewUsers = target.params(FS.EnableTopT etsByGeoCand dates)
    val  sTargetEl g bleForMrFat gueC ck = target. sAccountAtleastNDaysOld(
      target.params(FS.MrM nDurat onS ncePushForTopT etsByGeoPus s))
    val  sMrFat gueC ckEnabled =
      target.params(FS.EnableMrM nDurat onS nceMrPushFat gue)
    val applyPred cateForTopT etsByGeo =
       f ( sMrFat gueC ckEnabled) {
         f ( sTargetEl g bleForMrFat gueC ck) {
          D scoverTw terPred cate
            .m nDurat onElapsedS nceLastMrPushPred cate(
              na ,
              FS.MrM nDurat onS ncePushForTopT etsByGeoPus s,
              stats
            ).andT n(
              topT etsByGeoFrequencyPred cate
            )(Seq(target)).map(_. ad)
        } else {
          Future.False
        }
      } else {
        topT etsByGeoFrequencyPred cate(Seq(target)).map(_. ad)
      }
    applyPred cateForTopT etsByGeo.map { pred cateResult =>
      pred cateResult && enableTopT etsByGeoForNewUsers
    }
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
     f (target. sLoggedOutUser) {
      Future.True
    } else {
      PushDev ceUt l
        . sRecom ndat onsEl g ble(target).map(
          _ && target.params(PushParams.PopGeoCand datesDec der)).flatMap {  sAva lable =>
           f ( sAva lable) {
            Future
              .jo n(getAva lab l yForDormantUser(target), getAva lab l yForPlaybookSetUp(target))
              .map {
                case ( sAva lableForDormantUser,  sAva lableForPlaybook) =>
                   sAva lableForDormantUser ||  sAva lableForPlaybook
                case _ => false
              }
          } else Future.False
        }
    }
  }
}

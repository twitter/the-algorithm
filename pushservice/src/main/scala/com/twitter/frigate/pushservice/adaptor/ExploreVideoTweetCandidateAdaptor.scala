package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerProductResponse
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerRequest
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRankerResponse
 mport com.tw ter.explore_ranker.thr ftscala.ExploreRecom ndat on
 mport com.tw ter.explore_ranker.thr ftscala. m rs veRecsResponse
 mport com.tw ter.explore_ranker.thr ftscala. m rs veRecsResult
 mport com.tw ter.explore_ranker.thr ftscala.Not f cat onsV deoRecs
 mport com.tw ter.explore_ranker.thr ftscala.Product
 mport com.tw ter.explore_ranker.thr ftscala.ProductContext
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base.OutOfNetworkT etCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.AdaptorUt ls
 mport com.tw ter.fr gate.pushserv ce.ut l. d aCRT
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAdaptorUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

case class ExploreV deoT etCand dateAdaptor(
  exploreRankerStore: ReadableStore[ExploreRankerRequest, ExploreRankerResponse],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de def na : Str ng = t .getClass.getS mpleNa 
  pr vate[t ] val stats = globalStats.scope("ExploreV deoT etCand dateAdaptor")
  pr vate[t ] val total nputRecs = stats.stat(" nput_recs")
  pr vate[t ] val totalRequests = stats.counter("total_requests")
  pr vate[t ] val totalEmptyResponse = stats.counter("total_empty_response")

  pr vate def bu ldExploreRankerRequest(
    target: Target,
    countryCode: Opt on[Str ng],
    language: Opt on[Str ng],
  ): ExploreRankerRequest = {
    ExploreRankerRequest(
      cl entContext = Cl entContext(
        user d = So (target.target d),
        countryCode = countryCode,
        languageCode = language,
      ),
      product = Product.Not f cat onsV deoRecs,
      productContext = So (ProductContext.Not f cat onsV deoRecs(Not f cat onsV deoRecs())),
      maxResults = So (target.params(PushFeatureSw chParams.MaxExploreV deoT ets))
    )
  }

  overr de def get(target: Target): Future[Opt on[Seq[RawCand date]]] = {
    Future
      .jo n(
        target.countryCode,
        target. nferredUserDev ceLanguage
      ).flatMap {
        case (countryCode, language) =>
          val request = bu ldExploreRankerRequest(target, countryCode, language)
          exploreRankerStore.get(request).flatMap {
            case So (response) =>
              val exploreResonseT et ds = response match {
                case ExploreRankerResponse(ExploreRankerProductResponse
                      . m rs veRecsResponse( m rs veRecsResponse( m rs veRecsResult))) =>
                   m rs veRecsResult.collect {
                    case  m rs veRecsResult(ExploreRecom ndat on
                          .ExploreT etRecom ndat on(exploreT etRecom ndat on)) =>
                      exploreT etRecom ndat on.t et d
                  }
                case _ =>
                  Seq.empty
              }

              total nputRecs.add(exploreResonseT et ds.s ze)
              totalRequests. ncr()
              AdaptorUt ls
                .getT etyP eResults(exploreResonseT et ds.toSet, t etyP eStore).map {
                  t etyP eResultMap =>
                    val cand dates = t etyP eResultMap.values.flatten
                      .map(bu ldV deoRawCand dates(target, _))
                    So (cand dates.toSeq)
                }
            case _ =>
              totalEmptyResponse. ncr()
              Future.None
          }
        case _ =>
          Future.None
      }
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l. sRecom ndat onsEl g ble(target).map { userRecom ndat onsEl g ble =>
      userRecom ndat onsEl g ble && target.params(PushFeatureSw chParams.EnableExploreV deoT ets)
    }
  }
  pr vate def bu ldV deoRawCand dates(
    target: Target,
    t etyP eResult: T etyP eResult
  ): RawCand date w h OutOfNetworkT etCand date = {
    PushAdaptorUt l.generateOutOfNetworkT etCand dates(
       nputTarget = target,
       d = t etyP eResult.t et. d,
       d aCRT =  d aCRT(
        CommonRecom ndat onType.ExploreV deoT et,
        CommonRecom ndat onType.ExploreV deoT et,
        CommonRecom ndat onType.ExploreV deoT et
      ),
      result = So (t etyP eResult),
      local zedEnt y = None
    )
  }
}

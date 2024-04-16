package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.content_m xer.thr ftscala.ContentM xerProductResponse
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerRequest
 mport com.tw ter.content_m xer.thr ftscala.ContentM xerResponse
 mport com.tw ter.content_m xer.thr ftscala.Not f cat onsTr pT etsProductContext
 mport com.tw ter.content_m xer.thr ftscala.Product
 mport com.tw ter.content_m xer.thr ftscala.ProductContext
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l. d aCRT
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAdaptorUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.geoduck.ut l.country.Country nfo
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT ets
 mport com.tw ter.ut l.Future

case class Tr pGeoCand datesAdaptor(
  tr pT etCand dateStore: ReadableStore[Tr pDoma n, Tr pT ets],
  contentM xerStore: ReadableStore[ContentM xerRequest, ContentM xerResponse],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  statsRece ver: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de def na : Str ng = t .getClass.getS mpleNa 

  pr vate val stats = statsRece ver.scope(na .str pSuff x("$"))

  pr vate val contentM xerRequests = stats.counter("getTr pCand datesContentM xerRequests")
  pr vate val loggedOutTr pT et ds = stats.counter("logged_out_tr p_t et_ ds_count")
  pr vate val loggedOutRawCand dates = stats.counter("logged_out_raw_cand dates_count")
  pr vate val rawCand dates = stats.counter("raw_cand dates_count")
  pr vate val loggedOutEmptyplace d = stats.counter("logged_out_empty_place_ d_count")
  pr vate val loggedOutPlace d = stats.counter("logged_out_place_ d_count")
  pr vate val nonReplyT etsCounter = stats.counter("non_reply_t ets")

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
     f (target. sLoggedOutUser) {
      Future.True
    } else {
      for {
         sRecom ndat onsSett ngEnabled <- PushDev ceUt l. sRecom ndat onsEl g ble(target)
         nferredLanguage <- target. nferredUserDev ceLanguage
      } y eld {
         sRecom ndat onsSett ngEnabled &&
         nferredLanguage.nonEmpty &&
        target.params(PushParams.Tr pGeoT etCand datesDec der)
      }
    }

  }

  pr vate def bu ldRawCand date(target: Target, t etyP eResult: T etyP eResult): RawCand date = {
    PushAdaptorUt l.generateOutOfNetworkT etCand dates(
       nputTarget = target,
       d = t etyP eResult.t et. d,
       d aCRT =  d aCRT(
        CommonRecom ndat onType.Tr pGeoT et,
        CommonRecom ndat onType.Tr pGeoT et,
        CommonRecom ndat onType.Tr pGeoT et
      ),
      result = So (t etyP eResult),
      local zedEnt y = None
    )
  }

  overr de def get(target: Target): Future[Opt on[Seq[RawCand date]]] = {
     f (target. sLoggedOutUser) {
      for {
        tr pT et ds <- getTr pCand datesForLoggedOutTarget(target)
        t etyP eResults <- Future.collect(t etyP eStoreNoVF.mult Get(tr pT et ds))
      } y eld {
        val cand dates = t etyP eResults.values.flatten.map(bu ldRawCand date(target, _))
         f (cand dates.nonEmpty) {
          loggedOutRawCand dates. ncr(cand dates.s ze)
          So (cand dates.toSeq)
        } else None
      }
    } else {
      for {
        tr pT et ds <- getTr pCand datesContentM xer(target)
        t etyP eResults <-
          Future.collect((target.params(PushFeatureSw chParams.EnableVF nT etyp e) match {
            case true => t etyP eStore
            case false => t etyP eStoreNoVF
          }).mult Get(tr pT et ds))
      } y eld {
        val nonReplyT ets = f lterOutReplyT et(t etyP eResults, nonReplyT etsCounter)
        val cand dates = nonReplyT ets.values.flatten.map(bu ldRawCand date(target, _))
         f (cand dates.nonEmpty && target.params(
            PushFeatureSw chParams.Tr pT etCand dateReturnEnable)) {
          rawCand dates. ncr(cand dates.s ze)
          So (cand dates.toSeq)
        } else None
      }
    }
  }

  pr vate def getTr pCand datesContentM xer(
    target: Target
  ): Future[Set[Long]] = {
    contentM xerRequests. ncr()
    Future
      .jo n(
        target. nferredUserDev ceLanguage,
        target.dev ce nfo
      )
      .flatMap {
        case (languageOpt, dev ce nfoOpt) =>
          contentM xerStore
            .get(
              ContentM xerRequest(
                cl entContext = Cl entContext(
                  user d = So (target.target d),
                  languageCode = languageOpt,
                  userAgent = dev ce nfoOpt.flatMap(_.guessedPr maryDev ceUserAgent.map(_.toStr ng))
                ),
                product = Product.Not f cat onsTr pT ets,
                productContext = So (
                  ProductContext.Not f cat onsTr pT etsProductContext(
                    Not f cat onsTr pT etsProductContext()
                  )),
                cursor = None,
                maxResults =
                  So (target.params(PushFeatureSw chParams.Tr pT etMaxTotalCand dates))
              )
            ).map {
              _.map { rawResponse =>
                val tr pResponse =
                  rawResponse.contentM xerProductResponse
                    .as nstanceOf[
                      ContentM xerProductResponse.Not f cat onsTr pT etsProductResponse]
                    .not f cat onsTr pT etsProductResponse

                tr pResponse.results.map(_.t etResult.t et d).toSet
              }.getOrElse(Set.empty)
            }
      }
  }

  pr vate def getTr pCand datesForLoggedOutTarget(
    target: Target
  ): Future[Set[Long]] = {
    Future.jo n(target.targetLanguage, target.countryCode).flatMap {
      case (So (lang), So (country)) =>
        val place d = Country nfo.lookupByCode(country).map(_.place dLong)
         f (place d.nonEmpty) {
          loggedOutPlace d. ncr()
        } else {
          loggedOutEmptyplace d. ncr()
        }
        val tr pS ce = "TOP_GEO_V3_LR"
        val tr pQuery = Tr pDoma n(
          s ce d = tr pS ce,
          language = So (lang),
          place d = place d,
          top c d = None
        )
        val response = tr pT etCand dateStore.get(tr pQuery)
        val tr pT et ds =
          response.map { res =>
             f (res. sDef ned) {
              res.get.t ets
                .sortBy(_.score)(Order ng[Double].reverse).map(_.t et d).toSet
            } else {
              Set.empty[Long]
            }
          }
        tr pT et ds.map {  ds => loggedOutTr pT et ds. ncr( ds.s ze) }
        tr pT et ds

      case (_, _) => Future.value(Set.empty)
    }
  }
}

package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.cr_m xer.thr ftscala.FrsT etRequest
 mport com.tw ter.cr_m xer.thr ftscala.Not f cat onsContext
 mport com.tw ter.cr_m xer.thr ftscala.Product
 mport com.tw ter.cr_m xer.thr ftscala.ProductContext
 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.store.CrM xerT etStore
 mport com.tw ter.fr gate.pushserv ce.store.UttEnt yHydrat onStore
 mport com.tw ter.fr gate.pushserv ce.ut l. d aCRT
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAdaptorUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Top csUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .constants.Algor hmFeedbackTokens
 mport com.tw ter. rm .model.Algor hm.Algor hm
 mport com.tw ter. rm .model.Algor hm.CrowdSearchAccounts
 mport com.tw ter. rm .model.Algor hm.ForwardEma lBook
 mport com.tw ter. rm .model.Algor hm.ForwardPhoneBook
 mport com.tw ter. rm .model.Algor hm.ReverseEma lBook b s
 mport com.tw ter. rm .model.Algor hm.ReversePhoneBook
 mport com.tw ter. rm .store.t etyp e.UserT et
 mport com.tw ter.product_m xer.core.thr ftscala.Cl entContext
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofRequest
 mport com.tw ter.tsp.thr ftscala.Top cSoc alProofResponse
 mport com.tw ter.ut l.Future

object FRSAlgor hmFeedbackTokenUt l {
  pr vate val crtsByAlgoToken = Map(
    getAlgor hmToken(ReverseEma lBook b s) -> CommonRecom ndat onType.ReverseAddressbookT et,
    getAlgor hmToken(ReversePhoneBook) -> CommonRecom ndat onType.ReverseAddressbookT et,
    getAlgor hmToken(ForwardEma lBook) -> CommonRecom ndat onType.ForwardAddressbookT et,
    getAlgor hmToken(ForwardPhoneBook) -> CommonRecom ndat onType.ForwardAddressbookT et,
    getAlgor hmToken(CrowdSearchAccounts) -> CommonRecom ndat onType.CrowdSearchT et
  )

  def getAlgor hmToken(algor hm: Algor hm):  nt = {
    Algor hmFeedbackTokens.Algor hmToFeedbackTokenMap(algor hm)
  }

  def getCRTForAlgoToken(algor hmToken:  nt): Opt on[CommonRecom ndat onType] = {
    crtsByAlgoToken.get(algor hmToken)
  }
}

case class FRST etCand dateAdaptor(
  crM xerT etStore: CrM xerT etStore,
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  userT etT etyP eStore: ReadableStore[UserT et, T etyP eResult],
  uttEnt yHydrat onStore: UttEnt yHydrat onStore,
  top cSoc alProofServ ceStore: ReadableStore[Top cSoc alProofRequest, Top cSoc alProofResponse],
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  pr vate val stats = globalStats.scope(t .getClass.getS mpleNa )
  pr vate val crtStats = stats.scope("Cand dateD str but on")
  pr vate val totalRequests = stats.counter("total_requests")

  // Cand date D str but on stats
  pr vate val reverseAddressbookCounter = crtStats.counter("reverse_addressbook")
  pr vate val forwardAddressbookCounter = crtStats.counter("forward_addressbook")
  pr vate val frsT etCounter = crtStats.counter("frs_t et")
  pr vate val nonReplyT etsCounter = stats.counter("non_reply_t ets")
  pr vate val crtToCounterMapp ng: Map[CommonRecom ndat onType, Counter] = Map(
    CommonRecom ndat onType.ReverseAddressbookT et -> reverseAddressbookCounter,
    CommonRecom ndat onType.ForwardAddressbookT et -> forwardAddressbookCounter,
    CommonRecom ndat onType.FrsT et -> frsT etCounter
  )

  pr vate val emptyT etyP eResult = stats.stat("empty_t etyp e_result")

  pr vate[t ] val numberReturnedCand dates = stats.stat("returned_cand dates_from_earlyb rd")
  pr vate[t ] val numberCand dateW hTop c: Counter = stats.counter("num_can_w h_top c")
  pr vate[t ] val numberCand dateW houtTop c: Counter = stats.counter("num_can_w hout_top c")

  pr vate val userT etT etyP eStoreCounter = stats.counter("user_t et_t etyp e_store")

  overr de val na : Str ng = t .getClass.getS mpleNa 

  pr vate def f lter nval dT ets(
    t et ds: Seq[Long],
    target: Target
  ): Future[Map[Long, T etyP eResult]] = {
    val resMap = {
       f (target.params(PushFeatureSw chParams.EnableF1FromProtectedT etAuthors)) {
        userT etT etyP eStoreCounter. ncr()
        val keys = t et ds.map { t et d =>
          UserT et(t et d, So (target.target d))
        }
        userT etT etyP eStore
          .mult Get(keys.toSet).map {
            case (userT et, resultFut) =>
              userT et.t et d -> resultFut
          }.toMap
      } else {
        ( f (target.params(PushFeatureSw chParams.EnableVF nT etyp e)) {
           t etyP eStore
         } else {
           t etyP eStoreNoVF
         }).mult Get(t et ds.toSet)
      }
    }

    Future.collect(resMap).map { t etyP eResultMap =>
      // F lter out repl es and generate earlyb rd cand dates only for non-empty t etyp e result
      val cands = f lterOutReplyT et(t etyP eResultMap, nonReplyT etsCounter).collect {
        case ( d: Long, So (result)) =>
           d -> result
      }

      emptyT etyP eResult.add(t etyP eResultMap.s ze - cands.s ze)
      cands
    }
  }

  pr vate def bu ldRawCand dates(
    target: Target,
    ebCand dates: Seq[FRST etCand date]
  ): Future[Opt on[Seq[RawCand date w h T etCand date]]] = {

    val enableTop c = target.params(PushFeatureSw chParams.EnableFrsT etCand datesTop cAnnotat on)
    val top cScoreThre =
      target.params(PushFeatureSw chParams.FrsT etCand datesTop cScoreThreshold)

    val ebT ets = ebCand dates.map { ebCand date =>
      ebCand date.t et d -> ebCand date.t etyP eResult
    }.toMap

    val t et dLocal zedEnt yMapFut = Top csUt l.getT et dLocal zedEnt yMap(
      target,
      ebT ets,
      uttEnt yHydrat onStore,
      top cSoc alProofServ ceStore,
      enableTop c,
      top cScoreThre
    )

    Future.jo n(target.dev ce nfo, t et dLocal zedEnt yMapFut).map {
      case (So (dev ce nfo), t et dLocal zedEnt yMap) =>
        val cand dates = ebCand dates
          .map { ebCand date =>
            val crt = ebCand date.commonRecType
            crtToCounterMapp ng.get(crt).foreach(_. ncr())

            val t et d = ebCand date.t et d
            val local zedEnt yOpt = {
               f (t et dLocal zedEnt yMap
                  .conta ns(t et d) && t et dLocal zedEnt yMap.conta ns(
                  t et d) && dev ce nfo. sTop csEl g ble) {
                t et dLocal zedEnt yMap(t et d)
              } else {
                None
              }
            }

            PushAdaptorUt l.generateOutOfNetworkT etCand dates(
               nputTarget = target,
               d = ebCand date.t et d,
               d aCRT =  d aCRT(
                crt,
                crt,
                crt
              ),
              result = ebCand date.t etyP eResult,
              local zedEnt y = local zedEnt yOpt)
          }.f lter { cand date =>
            //  f user only has t  top c sett ng enabled, f lter out all non-top c cands
            dev ce nfo. sRecom ndat onsEl g ble || (dev ce nfo. sTop csEl g ble && cand date.semant cCoreEnt y d.nonEmpty)
          }

        cand dates.map { cand date =>
           f (cand date.semant cCoreEnt y d.nonEmpty) {
            numberCand dateW hTop c. ncr()
          } else {
            numberCand dateW houtTop c. ncr()
          }
        }

        numberReturnedCand dates.add(cand dates.length)
        So (cand dates)
      case _ => So (Seq.empty)
    }
  }

  def getT etCand datesFromCrM xer(
     nputTarget: Target,
    showAllResultsFromFrs: Boolean,
  ): Future[Opt on[Seq[RawCand date w h T etCand date]]] = {
    Future
      .jo n(
         nputTarget.seenT et ds,
         nputTarget.pushRec ems,
         nputTarget.countryCode,
         nputTarget.targetLanguage).flatMap {
        case (seenT et ds, pastRec ems, countryCode, language) =>
          val pastUserRecs = pastRec ems.user ds.toSeq
          val request = FrsT etRequest(
            cl entContext = Cl entContext(
              user d = So ( nputTarget.target d),
              countryCode = countryCode,
              languageCode = language
            ),
            product = Product.Not f cat ons,
            productContext = So (ProductContext.Not f cat onsContext(Not f cat onsContext())),
            excludedUser ds = So (pastUserRecs),
            excludedT et ds = So (seenT et ds)
          )
          crM xerT etStore.getFRST etCand dates(request).flatMap {
            case So (response) =>
              val t et ds = response.t ets.map(_.t et d)
              val val dT ets = f lter nval dT ets(t et ds,  nputTarget)
              val dT ets.flatMap { t etyp eMap =>
                val ebCand dates = response.t ets
                  .map { frsT et =>
                    val cand dateT et d = frsT et.t et d
                    val resultFromT etyP e = t etyp eMap.get(cand dateT et d)
                    new FRST etCand date {
                      overr de val t et d = cand dateT et d
                      overr de val features = None
                      overr de val t etyP eResult = resultFromT etyP e
                      overr de val feedbackToken = frsT et.frsPr maryS ce
                      overr de val commonRecType: CommonRecom ndat onType = feedbackToken
                        .flatMap(token =>
                          FRSAlgor hmFeedbackTokenUt l.getCRTForAlgoToken(token)).getOrElse(
                          CommonRecom ndat onType.FrsT et)
                    }
                  }.f lter { ebCand date =>
                    showAllResultsFromFrs || ebCand date.commonRecType == CommonRecom ndat onType.ReverseAddressbookT et
                  }

                numberReturnedCand dates.add(ebCand dates.length)
                bu ldRawCand dates(
                   nputTarget,
                  ebCand dates
                )
              }
            case _ => Future.None
          }
      }
  }

  overr de def get( nputTarget: Target): Future[Opt on[Seq[RawCand date w h T etCand date]]] = {
    totalRequests. ncr()
    val enableResultsFromFrs =
       nputTarget.params(PushFeatureSw chParams.EnableResultFromFrsCand dates)
    getT etCand datesFromCrM xer( nputTarget, enableResultsFromFrs)
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    lazy val enableFrsCand dates = target.params(PushFeatureSw chParams.EnableFrsCand dates)
    PushDev ceUt l. sRecom ndat onsEl g ble(target).flatMap {  sEnabledForRecosSett ng =>
      PushDev ceUt l. sTop csEl g ble(target).map { top cSett ngEnabled =>
        val  sEnabledForTop cs =
          top cSett ngEnabled && target.params(
            PushFeatureSw chParams.EnableFrsT etCand datesTop cSett ng)
        ( sEnabledForRecosSett ng ||  sEnabledForTop cs) && enableFrsCand dates
      }
    }
  }
}

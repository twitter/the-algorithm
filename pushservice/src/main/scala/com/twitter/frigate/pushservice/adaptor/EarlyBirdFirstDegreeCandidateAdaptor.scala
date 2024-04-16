package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.cand date._
 mport com.tw ter.fr gate.common.pred cate.CommonOutNetworkT etCand datesS cePred cates.f lterOutReplyT et
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter. rm .store.t etyp e.UserT et
 mport com.tw ter.recos.recos_common.thr ftscala.Soc alProofType
 mport com.tw ter.search.common.features.thr ftscala.Thr ftSearchResultFeatures
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.t  l nes.conf gap .Param
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport scala.collect on.Map

case class EarlyB rdF rstDegreeCand dateAdaptor(
  earlyB rdF rstDegreeCand dates: Cand dateS ce[
    Earlyb rdCand dateS ce.Query,
    Earlyb rdCand date
  ],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  userT etT etyP eStore: ReadableStore[UserT et, T etyP eResult],
  maxResultsParam: Param[ nt],
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  type EBCand date = Earlyb rdCand date w h T etDeta ls
  pr vate val stats = globalStats.scope("EarlyB rdF rstDegreeAdaptor")
  pr vate val earlyB rdCandsStat: Stat = stats.stat("early_b rd_cands_d st")
  pr vate val emptyEarlyB rdCands = stats.counter("empty_early_b rd_cand dates")
  pr vate val seedSetEmpty = stats.counter("empty_seedset")
  pr vate val seenT etsStat = stats.stat("f ltered_by_seen_t ets")
  pr vate val emptyT etyP eResult = stats.stat("empty_t etyp e_result")
  pr vate val nonReplyT etsCounter = stats.counter("non_reply_t ets")
  pr vate val enableRet ets = stats.counter("enable_ret ets")
  pr vate val f1w houtSoc alContexts = stats.counter("f1_w hout_soc al_context")
  pr vate val userT etT etyP eStoreCounter = stats.counter("user_t et_t etyp e_store")

  overr de val na : Str ng = earlyB rdF rstDegreeCand dates.na 

  pr vate def getAllSoc alContextAct ons(
    soc alProofTypes: Seq[(Soc alProofType, Seq[Long])]
  ): Seq[Soc alContextAct on] = {
    soc alProofTypes.flatMap {
      case (Soc alProofType.Favor e, sc ds) =>
        sc ds.map { sc d =>
          Soc alContextAct on(
            sc d,
            T  .now. nM ll seconds,
            soc alContextAct onType = So (Soc alContextAct onType.Favor e)
          )
        }
      case (Soc alProofType.Ret et, sc ds) =>
        sc ds.map { sc d =>
          Soc alContextAct on(
            sc d,
            T  .now. nM ll seconds,
            soc alContextAct onType = So (Soc alContextAct onType.Ret et)
          )
        }
      case (Soc alProofType.Reply, sc ds) =>
        sc ds.map { sc d =>
          Soc alContextAct on(
            sc d,
            T  .now. nM ll seconds,
            soc alContextAct onType = So (Soc alContextAct onType.Reply)
          )
        }
      case (Soc alProofType.T et, sc ds) =>
        sc ds.map { sc d =>
          Soc alContextAct on(
            sc d,
            T  .now. nM ll seconds,
            soc alContextAct onType = So (Soc alContextAct onType.T et)
          )
        }
      case _ => N l
    }
  }

  pr vate def generateRet etCand date(
     nputTarget: Target,
    cand date: EBCand date,
    sc ds: Seq[Long],
    soc alProofTypes: Seq[(Soc alProofType, Seq[Long])]
  ): RawCand date = {
    val scAct ons = sc ds.map { sc d => Soc alContextAct on(sc d, T  .now. nM ll seconds) }
    new RawCand date w h T etRet etCand date w h Earlyb rdT etFeatures {
      overr de val soc alContextAct ons = scAct ons
      overr de val soc alContextAllTypeAct ons = getAllSoc alContextAct ons(soc alProofTypes)
      overr de val t et d = cand date.t et d
      overr de val target =  nputTarget
      overr de val t etyP eResult = cand date.t etyP eResult
      overr de val features = cand date.features
    }
  }

  pr vate def generateF1Cand dateW houtSoc alContext(
     nputTarget: Target,
    cand date: EBCand date
  ): RawCand date = {
    f1w houtSoc alContexts. ncr()
    new RawCand date w h F1F rstDegree w h Earlyb rdT etFeatures {
      overr de val t et d = cand date.t et d
      overr de val target =  nputTarget
      overr de val t etyP eResult = cand date.t etyP eResult
      overr de val features = cand date.features
    }
  }

  pr vate def generateEarlyB rdCand date(
     d: Long,
    result: Opt on[T etyP eResult],
    ebFeatures: Opt on[Thr ftSearchResultFeatures]
  ): EBCand date = {
    new Earlyb rdCand date w h T etDeta ls {
      overr de val t etyP eResult: Opt on[T etyP eResult] = result
      overr de val t et d: Long =  d
      overr de val features: Opt on[Thr ftSearchResultFeatures] = ebFeatures
    }
  }

  pr vate def f lterOutSeenT ets(seenT et ds: Seq[Long],  nputT et ds: Seq[Long]): Seq[Long] = {
     nputT et ds.f lterNot(seenT et ds.conta ns)
  }

  pr vate def f lter nval dT ets(
    t et ds: Seq[Long],
    target: Target
  ): Future[Seq[(Long, T etyP eResult)]] = {

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
        (target.params(PushFeatureSw chParams.EnableVF nT etyp e) match {
          case true => t etyP eStore
          case false => t etyP eStoreNoVF
        }).mult Get(t et ds.toSet)
      }
    }
    Future.collect(resMap).map { t etyP eResultMap =>
      val cands = f lterOutReplyT et(t etyP eResultMap, nonReplyT etsCounter).collect {
        case ( d: Long, So (result)) =>
           d -> result
      }

      emptyT etyP eResult.add(t etyP eResultMap.s ze - cands.s ze)
      cands.toSeq
    }
  }

  pr vate def getEBRet etCand dates(
     nputTarget: Target,
    ret ets: Seq[(Long, T etyP eResult)]
  ): Seq[RawCand date] = {
    ret ets.flatMap {
      case (_, t etyp eResult) =>
        t etyp eResult.t et.coreData.flatMap { coreData =>
          t etyp eResult.s ceT et.map { s ceT et =>
            val t et d = s ceT et. d
            val sc d = coreData.user d
            val soc alProofTypes = Seq((Soc alProofType.Ret et, Seq(sc d)))
            val cand date = generateEarlyB rdCand date(
              t et d,
              So (T etyP eResult(s ceT et, None, None)),
              None
            )
            generateRet etCand date(
               nputTarget,
              cand date,
              Seq(sc d),
              soc alProofTypes
            )
          }
        }
    }
  }

  pr vate def getEBF rstDegreeCands(
    t ets: Seq[(Long, T etyP eResult)],
    ebT et dMap: Map[Long, Opt on[Thr ftSearchResultFeatures]]
  ): Seq[EBCand date] = {
    t ets.map {
      case ( d, t etyp eResult) =>
        val features = ebT et dMap.getOrElse( d, None)
        generateEarlyB rdCand date( d, So (t etyp eResult), features)
    }
  }

  /**
   * Returns a comb nat on of raw cand dates made of: f1 recs, top c soc al proof recs, sc recs and ret et cand dates
   */
  def bu ldRawCand dates(
     nputTarget: Target,
    f rstDegreeCand dates: Seq[EBCand date],
    ret etCand dates: Seq[RawCand date]
  ): Seq[RawCand date] = {
    val hydratedF1Recs =
      f rstDegreeCand dates.map(generateF1Cand dateW houtSoc alContext( nputTarget, _))
    hydratedF1Recs ++ ret etCand dates
  }

  overr de def get( nputTarget: Target): Future[Opt on[Seq[RawCand date]]] = {
     nputTarget.seedsW h  ght.flatMap { seedsetOpt =>
      val seedsetMap = seedsetOpt.getOrElse(Map.empty)

       f (seedsetMap. sEmpty) {
        seedSetEmpty. ncr()
        Future.None
      } else {
        val maxResultsToReturn =  nputTarget.params(maxResultsParam)
        val maxT etAge =  nputTarget.params(PushFeatureSw chParams.F1Cand dateMaxT etAgeParam)
        val earlyb rdQuery = Earlyb rdCand dateS ce.Query(
          maxNumResultsToReturn = maxResultsToReturn,
          seedset = seedsetMap,
          maxConsecut veResultsByT Sa User = So (1),
          maxT etAge = maxT etAge,
          d sableT  l nesMLModel = false,
          searc r d = So ( nputTarget.target d),
           sProtectT etsEnabled =
             nputTarget.params(PushFeatureSw chParams.EnableF1FromProtectedT etAuthors),
          follo dUser ds = So (seedsetMap.keySet.toSeq)
        )

        Future
          .jo n( nputTarget.seenT et ds, earlyB rdF rstDegreeCand dates.get(earlyb rdQuery))
          .flatMap {
            case (seenT et ds, So (cand dates)) =>
              earlyB rdCandsStat.add(cand dates.s ze)

              val ebT et dMap = cand dates.map { cand => cand.t et d -> cand.features }.toMap

              val ebT et ds = ebT et dMap.keys.toSeq

              val t et ds = f lterOutSeenT ets(seenT et ds, ebT et ds)
              seenT etsStat.add(ebT et ds.s ze - t et ds.s ze)

              f lter nval dT ets(t et ds,  nputTarget)
                .map { val dT ets =>
                  val (ret ets, t ets) = val dT ets.part  on {
                    case (_, t etyp eResult) =>
                      t etyp eResult.s ceT et. sDef ned
                  }

                  val f rstDegreeCand dates = getEBF rstDegreeCands(t ets, ebT et dMap)

                  val ret etCand dates = {
                     f ( nputTarget.params(PushParams.EarlyB rdSCBasedCand datesParam) &&
                       nputTarget.params(PushParams.MRT etRet etRecsParam)) {
                      enableRet ets. ncr()
                      getEBRet etCand dates( nputTarget, ret ets)
                    } else N l
                  }

                  So (
                    bu ldRawCand dates(
                       nputTarget,
                      f rstDegreeCand dates,
                      ret etCand dates
                    ))
                }

            case _ =>
              emptyEarlyB rdCands. ncr()
              Future.None
          }
      }
    }
  }

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l. sRecom ndat onsEl g ble(target)
  }
}

package com.tw ter.fr gate.pushserv ce.adaptor

 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateS ce
 mport com.tw ter.fr gate.common.base.Cand dateS ceEl g ble
 mport com.tw ter.fr gate.common.store. nterests. nterestsLookupRequestW hContext
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params.H ghQual yCand dateGroupEnum
 mport com.tw ter.fr gate.pushserv ce.params.H ghQual yCand dateGroupEnum._
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants.targetUserAgeFeatureNa 
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants.targetUserPreferredLanguage
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FS}
 mport com.tw ter.fr gate.pushserv ce.pred cate.TargetPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l. d aCRT
 mport com.tw ter.fr gate.pushserv ce.ut l.PushAdaptorUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Top csUt l
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. nterests.thr ftscala. nterest d.Semant cCore
 mport com.tw ter. nterests.thr ftscala.User nterests
 mport com.tw ter.language.normal zat on.UserD splayLanguage
 mport com.tw ter.st ch.t etyp e.T etyP e.T etyP eResult
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pDoma n
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT et
 mport com.tw ter.trends.tr p_v1.tr p_t ets.thr ftscala.Tr pT ets
 mport com.tw ter.ut l.Future

object H ghQual yT ets lper {
  def getFollo dTop cs(
    target: Target,
     nterestsW hLookupContextStore: ReadableStore[
       nterestsLookupRequestW hContext,
      User nterests
    ],
    follo dTop csStats: Stat
  ): Future[Seq[Long]] = {
    Top csUt l
      .getTop csFollo dByUser(target,  nterestsW hLookupContextStore, follo dTop csStats).map {
        user nterestsOpt =>
          val user nterests = user nterestsOpt.getOrElse(Seq.empty)
          val extractedTop c ds = user nterests.flatMap {
            _. nterest d match {
              case Semant cCore(semant cCore) => So (semant cCore. d)
              case _ => None
            }
          }
          extractedTop c ds
      }
  }

  def getTr pQuer es(
    target: Target,
    enabledGroups: Set[H ghQual yCand dateGroupEnum.Value],
     nterestsW hLookupContextStore: ReadableStore[
       nterestsLookupRequestW hContext,
      User nterests
    ],
    s ce ds: Seq[Str ng],
    stat: Stat
  ): Future[Set[Tr pDoma n]] = {

    val follo dTop c dsSetFut: Future[Set[Long]] =  f (enabledGroups.conta ns(Top c)) {
      getFollo dTop cs(target,  nterestsW hLookupContextStore, stat).map(top c ds =>
        top c ds.toSet)
    } else {
      Future.value(Set.empty)
    }

    Future
      .jo n(target.featureMap, target. nferredUserDev ceLanguage, follo dTop c dsSetFut).map {
        case (
              featureMap,
              dev ceLanguageOpt,
              follo dTop c ds
            ) =>
          val ageBucketOpt =  f (enabledGroups.conta ns(AgeBucket)) {
            featureMap.categor calFeatures.get(targetUserAgeFeatureNa )
          } else {
            None
          }

          val languageOpt ons: Set[Opt on[Str ng]] =  f (enabledGroups.conta ns(Language)) {
            val userPreferredLanguages = featureMap.sparseB naryFeatures
              .getOrElse(targetUserPreferredLanguage, Set.empty[Str ng])
             f (userPreferredLanguages.nonEmpty) {
              userPreferredLanguages.map(lang => So (UserD splayLanguage.toT etLanguage(lang)))
            } else {
              Set(dev ceLanguageOpt.map(UserD splayLanguage.toT etLanguage))
            }
          } else Set(None)

          val follo dTop cOpt ons: Set[Opt on[Long]] =  f (follo dTop c ds.nonEmpty) {
            follo dTop c ds.map(top c => So (top c))
          } else Set(None)

          val tr pQuer es = follo dTop cOpt ons.flatMap { top cOpt on =>
            languageOpt ons.flatMap { languageOpt on =>
              s ce ds.map { s ce d =>
                Tr pDoma n(
                  s ce d = s ce d,
                  language = languageOpt on,
                  place d = None,
                  top c d = top cOpt on,
                  gender = None,
                  ageBucket = ageBucketOpt
                )
              }
            }
          }

          tr pQuer es
      }
  }
}

case class H ghQual yT etsAdaptor(
  tr pT etCand dateStore: ReadableStore[Tr pDoma n, Tr pT ets],
   nterestsW hLookupContextStore: ReadableStore[ nterestsLookupRequestW hContext, User nterests],
  t etyP eStore: ReadableStore[Long, T etyP eResult],
  t etyP eStoreNoVF: ReadableStore[Long, T etyP eResult],
  globalStats: StatsRece ver)
    extends Cand dateS ce[Target, RawCand date]
    w h Cand dateS ceEl g ble[Target, RawCand date] {

  overr de def na : Str ng = t .getClass.getS mpleNa 

  pr vate val stats = globalStats.scope("H ghQual yCand dateAdaptor")
  pr vate val follo dTop csStats = stats.stat("follo d_top cs")
  pr vate val m ss ngResponseCounter = stats.counter("m ss ng_respond_counter")
  pr vate val crtFat gueCounter = stats.counter("fat gue_by_crt")
  pr vate val fallbackRequestsCounter = stats.counter("fallback_requests")

  overr de def  sCand dateS ceAva lable(target: Target): Future[Boolean] = {
    PushDev ceUt l. sRecom ndat onsEl g ble(target).map {
      _ && target.params(FS.H ghQual yCand datesEnableCand dateS ce)
    }
  }

  pr vate val h ghQual yCand dateFrequencyPred cate = {
    TargetPred cates
      .pushRecTypeFat guePred cate(
        CommonRecom ndat onType.Tr pHqT et,
        FS.H ghQual yT etsPush nterval,
        FS.MaxH ghQual yT etsPushG ven nterval,
        stats
      )
  }

  pr vate def getTr pCand datesStrato(
    target: Target
  ): Future[Map[Long, Set[Tr pDoma n]]] = {
    val tr pQuer esF: Future[Set[Tr pDoma n]] = H ghQual yT ets lper.getTr pQuer es(
      target = target,
      enabledGroups = target.params(FS.H ghQual yCand datesEnableGroups).toSet,
       nterestsW hLookupContextStore =  nterestsW hLookupContextStore,
      s ce ds = target.params(FS.Tr pT etCand dateS ce ds),
      stat = follo dTop csStats
    )

    lazy val fallbackTr pQuer esFut: Future[Set[Tr pDoma n]] =
       f (target.params(FS.H ghQual yCand datesEnableFallback))
        H ghQual yT ets lper.getTr pQuer es(
          target = target,
          enabledGroups = target.params(FS.H ghQual yCand datesFallbackEnabledGroups).toSet,
           nterestsW hLookupContextStore =  nterestsW hLookupContextStore,
          s ce ds = target.params(FS.H ghQual yCand datesFallbackS ce ds),
          stat = follo dTop csStats
        )
      else Future.value(Set.empty)

    val  n  alT etsFut: Future[Map[Tr pDoma n, Seq[Tr pT et]]] = tr pQuer esF.flatMap {
      tr pQuer es => getTr pT etsByDoma ns(tr pQuer es)
    }

    val t etsByDoma nFut: Future[Map[Tr pDoma n, Seq[Tr pT et]]] =
       f (target.params(FS.H ghQual yCand datesEnableFallback)) {
         n  alT etsFut.flatMap { cand dates =>
          val m nCand datesForFallback:  nt =
            target.params(FS.H ghQual yCand datesM nNumOfCand datesToFallback)
          val val dCand dates = cand dates.f lter(_._2.s ze >= m nCand datesForFallback)

           f (val dCand dates.nonEmpty) {
            Future.value(val dCand dates)
          } else {
            fallbackTr pQuer esFut.flatMap { fallbackTr pDoma ns =>
              fallbackRequestsCounter. ncr(fallbackTr pDoma ns.s ze)
              getTr pT etsByDoma ns(fallbackTr pDoma ns)
            }
          }
        }
      } else {
         n  alT etsFut
      }

    val numOfCand dates:  nt = target.params(FS.H ghQual yCand datesNumberOfCand dates)
    t etsByDoma nFut.map(t etsByDoma n => reformatDoma nT etMap(t etsByDoma n, numOfCand dates))
  }

  pr vate def getTr pT etsByDoma ns(
    tr pQuer es: Set[Tr pDoma n]
  ): Future[Map[Tr pDoma n, Seq[Tr pT et]]] = {
    Future.collect(tr pT etCand dateStore.mult Get(tr pQuer es)).map { response =>
      response
        .f lter(p => p._2.ex sts(_.t ets.nonEmpty))
        .mapValues(_.map(_.t ets).getOrElse(Seq.empty))
    }
  }

  pr vate def reformatDoma nT etMap(
    t etsByDoma n: Map[Tr pDoma n, Seq[Tr pT et]],
    numOfCand dates:  nt
  ): Map[Long, Set[Tr pDoma n]] = t etsByDoma n
    .flatMap {
      case (tr pDoma n, tr pT ets) =>
        tr pT ets
          .sortBy(_.score)(Order ng[Double].reverse)
          .take(numOfCand dates)
          .map { t et => (t et.t et d, tr pDoma n) }
    }.groupBy(_._1).mapValues(_.map(_._2).toSet)

  pr vate def bu ldRawCand date(
    target: Target,
    t etyP eResult: T etyP eResult,
    tr pDoma n: Opt on[scala.collect on.Set[Tr pDoma n]]
  ): RawCand date = {
    PushAdaptorUt l.generateOutOfNetworkT etCand dates(
       nputTarget = target,
       d = t etyP eResult.t et. d,
       d aCRT =  d aCRT(
        CommonRecom ndat onType.Tr pHqT et,
        CommonRecom ndat onType.Tr pHqT et,
        CommonRecom ndat onType.Tr pHqT et
      ),
      result = So (t etyP eResult),
      tr pT etDoma n = tr pDoma n
    )
  }

  pr vate def getT etyP eResults(
    target: Target,
    t etToTr pDoma n: Map[Long, Set[Tr pDoma n]]
  ): Future[Map[Long, Opt on[T etyP eResult]]] = {
    Future.collect(( f (target.params(FS.EnableVF nT etyp e)) {
                      t etyP eStore
                    } else {
                      t etyP eStoreNoVF
                    }).mult Get(t etToTr pDoma n.keySet))
  }

  overr de def get(target: Target): Future[Opt on[Seq[RawCand date]]] = {
    for {
      t etsToTr pDoma nMap <- getTr pCand datesStrato(target)
      t etyP eResults <- getT etyP eResults(target, t etsToTr pDoma nMap)
    } y eld {
      val cand dates = t etyP eResults.flatMap {
        case (t et d, t etyP eResultOpt) =>
          t etyP eResultOpt.map(bu ldRawCand date(target, _, t etsToTr pDoma nMap.get(t et d)))
      }
       f (cand dates.nonEmpty) {
        h ghQual yCand dateFrequencyPred cate(Seq(target))
          .map(_. ad)
          .map {  sTargetFat gueEl g ble =>
             f ( sTargetFat gueEl g ble) So (cand dates)
            else {
              crtFat gueCounter. ncr()
              None
            }
          }

        So (cand dates.toSeq)
      } else {
        m ss ngResponseCounter. ncr()
        None
      }
    }
  }
}

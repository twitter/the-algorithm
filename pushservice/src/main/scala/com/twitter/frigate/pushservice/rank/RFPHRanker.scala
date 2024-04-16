package com.tw ter.fr gate.pushserv ce.rank
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.Ranker
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.ml. althFeatureGetter
 mport com.tw ter.fr gate.pushserv ce.ml.PushMLModelScorer
 mport com.tw ter.fr gate.pushserv ce.params.MrQual yUprank ngPart alTypeEnum
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.params.PushMLModel
 mport com.tw ter.fr gate.pushserv ce.params.PushModelNa 
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.ut l. d aAnnotat onsUt l.update d aCategoryStats
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.ut l.Future
 mport com.tw ter.fr gate.pushserv ce.params.MrQual yUprank ngTransformTypeEnum
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.fr gate.thr ftscala.User d aRepresentat on
 mport com.tw ter.hss.ap .thr ftscala.User althS gnalResponse

class RFPHRanker(
  randomRanker: Ranker[Target, PushCand date],
    ghtedOpenOrNtabCl ckModelScorer: PushMLModelScorer,
  subscr pt onCreatorRanker: Subscr pt onCreatorRanker,
  user althS gnalStore: ReadableStore[Long, User althS gnalResponse],
  producer d aRepresentat onStore: ReadableStore[Long, User d aRepresentat on],
  stats: StatsRece ver)
    extends Pushserv ceRanker[Target, PushCand date] {

  pr vate val statsRece ver = stats.scope(t .getClass.getS mpleNa )

  pr vate val boostCRTsRanker = CRTBoostRanker(statsRece ver.scope("boost_des red_crts"))
  pr vate val crtDownRanker = CRTDownRanker(statsRece ver.scope("down_rank_des red_crts"))

  pr vate val crtsToDownRank = statsRece ver.stat("crts_to_downrank")
  pr vate val crtsToUprank = statsRece ver.stat("crts_to_uprank")

  pr vate val randomRank ngCounter = stats.counter("randomRank ng")
  pr vate val mlRank ngCounter = stats.counter("mlRank ng")
  pr vate val d sableAllRelevanceCounter = stats.counter("d sableAllRelevance")
  pr vate val d sable avyRank ngCounter = stats.counter("d sable avyRank ng")

  pr vate val  avyRankerCand dateCounter = stats.counter(" avy_ranker_cand date_count")
  pr vate val  avyRankerScoreStats = statsRece ver.scope(" avy_ranker_pred ct on_scores")

  pr vate val producerUprank ngCounter = statsRece ver.counter("producer_qual y_uprank ng")
  pr vate val producerBoostedCounter = statsRece ver.counter("producer_boosted_cand dates")
  pr vate val producerDownboostedCounter = statsRece ver.counter("producer_downboosted_cand dates")

  overr de def  n  alRank(
    target: Target,
    cand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

     avyRankerCand dateCounter. ncr(cand dates.s ze)

    update d aCategoryStats(cand dates)(stats)
    target.targetUserState
      .flatMap { targetUserState =>
        val useRandomRank ng = target.sk pMlRanker || target.params(
          PushParams.UseRandomRank ngParam
        )

         f (useRandomRank ng) {
          randomRank ngCounter. ncr()
          randomRanker.rank(target, cand dates)
        } else  f (target.params(PushParams.D sableAllRelevanceParam)) {
          d sableAllRelevanceCounter. ncr()
          Future.value(cand dates)
        } else  f (target.params(PushParams.D sable avyRank ngParam) || target.params(
            PushFeatureSw chParams.D sable avyRank ngModelFSParam)) {
          d sable avyRank ngCounter. ncr()
          Future.value(cand dates)
        } else {
          mlRank ngCounter. ncr()

          val scoredCand datesFut = scor ng(target, cand dates)

          target.rank ngModelParam.map { rank ngModelParam =>
            val modelNa  = PushModelNa (
              PushMLModel.  ghtedOpenOrNtabCl ckProbab l y,
              target.params(rank ngModelParam)).toStr ng
            ModelBasedRanker.populateMr  ghtedOpenOrNtabCl ckScoreStats(
              cand dates,
               avyRankerScoreStats.scope(modelNa )
            )
          }

           f (target.params(
              PushFeatureSw chParams.EnableQual yUprank ngCrtScoreStatsFor avyRank ngParam)) {
            val modelNa  = PushModelNa (
              PushMLModel.F lter ngProbab l y,
              target.params(PushFeatureSw chParams.Qual yUprank ngModelTypeParam)
            ).toStr ng
            ModelBasedRanker.populateMrQual yUprank ngScoreStats(
              cand dates,
               avyRankerScoreStats.scope(modelNa )
            )
          }

          val ooncRankedCand datesFut =
            scoredCand datesFut.flatMap(ModelBasedRanker.rankByMr  ghtedOpenOrNtabCl ckScore)

          val qual yUprankedCand datesFut =
             f (target.params(PushFeatureSw chParams.EnableQual yUprank ngFor avyRank ngParam)) {
              ooncRankedCand datesFut.flatMap { ooncRankedCand dates =>
                val transformFunc: Double => Double =
                  target.params(PushFeatureSw chParams.Qual yUprank ngTransformTypeParam) match {
                    case MrQual yUprank ngTransformTypeEnum.L near =>
                      ModelBasedRanker.transformL near(
                        _,
                        bar = target.params(
                          PushFeatureSw chParams.Qual yUprank ngL nearBarFor avyRank ngParam))
                    case MrQual yUprank ngTransformTypeEnum.S gmo d =>
                      ModelBasedRanker.transformS gmo d(
                        _,
                          ght = target.params(
                          PushFeatureSw chParams.Qual yUprank ngS gmo d  ghtFor avyRank ngParam),
                        b as = target.params(
                          PushFeatureSw chParams.Qual yUprank ngS gmo dB asFor avyRank ngParam)
                      )
                    case _ => ModelBasedRanker.transform dent y
                  }

                ModelBasedRanker.rankByQual yOoncComb nedScore(
                  ooncRankedCand dates,
                  transformFunc,
                  target.params(PushFeatureSw chParams.Qual yUprank ngBoostFor avyRank ngParam)
                )
              }
            } else ooncRankedCand datesFut

           f (target.params(
              PushFeatureSw chParams.EnableProducersQual yBoost ngFor avyRank ngParam)) {
            producerUprank ngCounter. ncr()
            qual yUprankedCand datesFut.flatMap(cands =>
              ModelBasedRanker.rerankByProducerQual yOoncComb nedScore(cands)(statsRece ver))
          } else qual yUprankedCand datesFut
        }
      }
  }

  pr vate def scor ng(
    target: Target,
    cand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

    val ooncScoredCand datesFut = target.rank ngModelParam.map { rank ngModelParam =>
        ghtedOpenOrNtabCl ckModelScorer.scoreByBatchPred ct onForModelVers on(
        target,
        cand dates,
        rank ngModelParam
      )
    }

    val scoredCand datesFut = {
       f (target.params(PushFeatureSw chParams.EnableQual yUprank ngFor avyRank ngParam)) {
        ooncScoredCand datesFut.map { cand dates =>
            ghtedOpenOrNtabCl ckModelScorer.scoreByBatchPred ct onForModelVers on(
            target = target,
            cand datesDeta ls = cand dates,
            modelVers onParam = PushFeatureSw chParams.Qual yUprank ngModelTypeParam,
            overr dePushMLModelOpt = So (PushMLModel.F lter ngProbab l y)
          )
        }
      } else ooncScoredCand datesFut
    }

    scoredCand datesFut.foreach { cand dates =>
      val oonCand dates = cand dates.f lter {
        case Cand dateDeta ls(pushCand date: PushCand date, _) =>
          ModelBasedRanker.t etCand dateSelector(
            pushCand date,
            MrQual yUprank ngPart alTypeEnum.Oon)
      }
      setProducerQual y(
        target,
        oonCand dates,
        user althS gnalStore,
        producer d aRepresentat onStore)
    }
  }

  pr vate def setProducerQual y(
    target: Target,
    cand dates: Seq[Cand dateDeta ls[PushCand date]],
    user althS gnalStore: ReadableStore[Long, User althS gnalResponse],
    producer d aRepresentat onStore: ReadableStore[Long, User d aRepresentat on]
  ): Un  = {
    lazy val boostRat o =
      target.params(PushFeatureSw chParams.Qual yUprank ngBoostForH ghQual yProducersParam)
    lazy val downboostRat o =
      target.params(PushFeatureSw chParams.Qual yUprank ngDownboostForLowQual yProducersParam)
    cand dates.foreach {
      case Cand dateDeta ls(pushCand date, _) =>
         althFeatureGetter
          .getFeatures(pushCand date, producer d aRepresentat onStore, user althS gnalStore).map {
            featureMap =>
              val agathaNsfwScore = featureMap.nu r cFeatures.getOrElse("agathaNsfwScore", 0.5)
              val textNsfwScore = featureMap.nu r cFeatures.getOrElse("textNsfwScore", 0.15)
              val nud yRate = featureMap.nu r cFeatures.getOrElse("nud yRate", 0.0)
              val act veFollo rs = featureMap.nu r cFeatures.getOrElse("act veFollo rs", 0.0)
              val favorsRcvd28Days = featureMap.nu r cFeatures.getOrElse("favorsRcvd28Days", 0.0)
              val t ets28Days = featureMap.nu r cFeatures.getOrElse("t ets28Days", 0.0)
              val authorD sl keCount = featureMap.nu r cFeatures
                .getOrElse("authorD sl keCount", 0.0)
              val authorD sl keRate = featureMap.nu r cFeatures.getOrElse("authorD sl keRate", 0.0)
              val authorReportRate = featureMap.nu r cFeatures.getOrElse("authorReportRate", 0.0)
              val abuseStr keTop2Percent =
                featureMap.booleanFeatures.getOrElse("abuseStr keTop2Percent", false)
              val abuseStr keTop1Percent =
                featureMap.booleanFeatures.getOrElse("abuseStr keTop1Percent", false)
              val hasNsfwToken = featureMap.booleanFeatures.getOrElse("hasNsfwToken", false)

               f ((act veFollo rs > 3000000) ||
                (act veFollo rs > 1000000 && agathaNsfwScore < 0.7 && nud yRate < 0.01 && !hasNsfwToken && !abuseStr keTop2Percent) ||
                (act veFollo rs > 100000 && agathaNsfwScore < 0.7 && nud yRate < 0.01 && !hasNsfwToken && !abuseStr keTop2Percent &&
                t ets28Days > 0 && favorsRcvd28Days / t ets28Days > 3000 && authorReportRate < 0.000001 && authorD sl keRate < 0.0005)) {
                producerBoostedCounter. ncr()
                pushCand date.setProducerQual yUprank ngBoost(boostRat o)
              } else  f (act veFollo rs < 5 || agathaNsfwScore > 0.9 || nud yRate > 0.03 || hasNsfwToken || abuseStr keTop1Percent ||
                textNsfwScore > 0.4 || (authorD sl keRate > 0.005 && authorD sl keCount > 5) ||
                (t ets28Days > 56 && favorsRcvd28Days / t ets28Days < 100)) {
                producerDownboostedCounter. ncr()
                pushCand date.setProducerQual yUprank ngBoost(downboostRat o)
              } else pushCand date.setProducerQual yUprank ngBoost(1.0)
          }
    }
  }

  pr vate def rerankBySubscr pt onCreatorRanker(
    target: Target,
    rankedCand dates: Future[Seq[Cand dateDeta ls[PushCand date]]],
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
     f (target.params(PushFeatureSw chParams.SoftRankCand datesFromSubscr pt onCreators)) {
      val factor = target.params(PushFeatureSw chParams.SoftRankFactorForSubscr pt onCreators)
      subscr pt onCreatorRanker.boostByScoreFactor(rankedCand dates, factor)
    } else
      subscr pt onCreatorRanker.boostSubscr pt onCreator(rankedCand dates)
  }

  overr de def reRank(
    target: Target,
    rankedCand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    val numberOfF1Cand dates =
      rankedCand dates.count(cand dateDeta ls =>
        RecTypes. sF1Type(cand dateDeta ls.cand date.commonRecType))
    lazy val threshold =
      target.params(PushFeatureSw chParams.NumberOfF1Cand datesThresholdForOONBackf ll)
    lazy val enableOONBackf llBasedOnF1 =
      target.params(PushFeatureSw chParams.EnableOONBackf llBasedOnF1Cand dates)

    val f1BoostedCand dates =
       f (enableOONBackf llBasedOnF1 && numberOfF1Cand dates > threshold) {
        boostCRTsRanker.boostCrtsToTopStableOrder(
          rankedCand dates,
          RecTypes.f1F rstDegreeTypes.toSeq)
      } else rankedCand dates

    val topT etsByGeoDownRankedCand dates =
       f (target.params(PushFeatureSw chParams.Backf llRankTopT etsByGeoCand dates)) {
        crtDownRanker.downRank(
          f1BoostedCand dates,
          Seq(CommonRecom ndat onType.GeoPopT et)
        )
      } else f1BoostedCand dates

    val reRankedCand datesW hBoostedCrts = {
      val l stOfCrtsToUpRank = target
        .params(PushFeatureSw chParams.L stOfCrtsToUpRank)
        .flatMap(CommonRecom ndat onType.valueOf)
      crtsToUprank.add(l stOfCrtsToUpRank.s ze)
      boostCRTsRanker.boostCrtsToTop(topT etsByGeoDownRankedCand dates, l stOfCrtsToUpRank)
    }

    val reRankedCand datesW hDownRankedCrts = {
      val l stOfCrtsToDownRank = target
        .params(PushFeatureSw chParams.L stOfCrtsToDownRank)
        .flatMap(CommonRecom ndat onType.valueOf)
      crtsToDownRank.add(l stOfCrtsToDownRank.s ze)
      crtDownRanker.downRank(reRankedCand datesW hBoostedCrts, l stOfCrtsToDownRank)
    }

    val rerankBySubscr pt onCreatorFut = {
       f (target.params(PushFeatureSw chParams.BoostCand datesFromSubscr pt onCreators)) {
        rerankBySubscr pt onCreatorRanker(
          target,
          Future.value(reRankedCand datesW hDownRankedCrts))
      } else Future.value(reRankedCand datesW hDownRankedCrts)
    }

    rerankBySubscr pt onCreatorFut
  }
}

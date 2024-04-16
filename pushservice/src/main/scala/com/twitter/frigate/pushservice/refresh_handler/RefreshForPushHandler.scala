package com.tw ter.fr gate.pushserv ce.refresh_handler

 mport com.tw ter.f nagle.stats.Counter
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Stats.track
 mport com.tw ter.fr gate.common.base.Stats.trackSeq
 mport com.tw ter.fr gate.common.base._
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.adaptor._
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.rank.RFPHL ghtRanker
 mport com.tw ter.fr gate.pushserv ce.rank.RFPHRanker
 mport com.tw ter.fr gate.pushserv ce.scr ber.MrRequestScr beHandler
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.RFPHCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.target.PushTargetUserBu lder
 mport com.tw ter.fr gate.pushserv ce.target.RFPHTargetPred cates
 mport com.tw ter.fr gate.pushserv ce.ut l.RFPHTakeStepUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.AdhocStatsUt l
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushContext
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.RefreshRequest
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.RefreshResponse
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter. rm .pred cate.Pred cate
 mport com.tw ter.t  l nes.conf gap .FeatureValue
 mport com.tw ter.ut l._

case class ResultW hDebug nfo(result: Result, pred cateResults: Seq[Pred cateW hResult])

class RefreshForPushHandler(
  val pushTargetUserBu lder: PushTargetUserBu lder,
  val candS ceGenerator: PushCand dateS ceGenerator,
  rfphRanker: RFPHRanker,
  cand dateHydrator: PushCand dateHydrator,
  cand dateVal dator: RFPHCand dateVal dator,
  rfphTakeStepUt l: RFPHTakeStepUt l,
  rfphRestr ctStep: RFPHRestr ctStep,
  val rfphNot f er: RefreshForPushNot f er,
  rfphStatsRecorder: RFPHStatsRecorder,
  mrRequestScr berNode: Str ng,
  rfphFeatureHydrator: RFPHFeatureHydrator,
  rfphPrerankF lter: RFPHPrerankF lter,
  rfphL ghtRanker: RFPHL ghtRanker
)(
  globalStats: StatsRece ver)
    extends FetchRankFlowW hHydratedCand dates[Target, RawCand date, PushCand date] {

  val log = MRLogger("RefreshForPushHandler")

   mpl c  val statsRece ver: StatsRece ver =
    globalStats.scope("RefreshForPushHandler")
  pr vate val maxCand datesToBatch nTakeStat: Stat =
    statsRece ver.stat("max_cands_to_batch_ n_take")

  pr vate val rfphRequestCounter = statsRece ver.counter("requests")

  pr vate val bu ldTargetStats = statsRece ver.scope("bu ld_target")
  pr vate val processStats = statsRece ver.scope("process")
  pr vate val not fyStats = statsRece ver.scope("not fy")

  pr vate val l ghtRank ngStats: StatsRece ver = statsRece ver.scope("l ght_rank ng")
  pr vate val reRank ngStats: StatsRece ver = statsRece ver.scope("rerank")
  pr vate val featureHydrat onLatency: StatsRece ver =
    statsRece ver.scope("featureHydrat onLatency")
  pr vate val cand dateHydrat onStats: StatsRece ver = statsRece ver.scope("cand date_hydrat on")

  lazy val candS ceEl g bleCounter: Counter =
    cand dateStats.counter("cand_s ce_el g ble")
  lazy val candS ceNotEl g bleCounter: Counter =
    cand dateStats.counter("cand_s ce_not_el g ble")

  //pre-rank ng stats
  val allCand datesF lteredPreRank = f lterStats.counter("all_cand dates_f ltered")

  // total  nval d cand dates
  val totalStats: StatsRece ver = statsRece ver.scope("total")
  val total nval dCand datesStat: Stat = totalStats.stat("cand dates_ nval d")

  val mrRequestScr beBu ltStats: Counter = statsRece ver.counter("mr_request_scr be_bu lt")

  val mrRequestCand dateScr beStats = statsRece ver.scope("mr_request_scr be_cand dates")
  val mrRequestTargetScr beStats = statsRece ver.scope("mr_request_scr be_target")

  val mrRequestScr beHandler =
    new MrRequestScr beHandler(mrRequestScr berNode, statsRece ver.scope("mr_request_scr be"))

  val adhocStatsUt l = new AdhocStatsUt l(statsRece ver.scope("adhoc_stats"))

  pr vate def numRecsPerTypeStat(crt: CommonRecom ndat onType) =
    fetchStats.scope(crt.toStr ng).stat("d st")

  // stat c l st of target pred cates
  pr vate val targetPred cates = RFPHTargetPred cates(targetStats.scope("pred cates"))

  def bu ldTarget(
    user d: Long,
     nputPushContext: Opt on[PushContext],
    forcedFeatureValues: Opt on[Map[Str ng, FeatureValue]] = None
  ): Future[Target] =
    pushTargetUserBu lder.bu ldTarget(user d,  nputPushContext, forcedFeatureValues)

  overr de def targetPred cates(target: Target): L st[Pred cate[Target]] = targetPred cates

  overr de def  sTargetVal d(target: Target): Future[Result] = {
    val resultFut =  f (target.sk pF lters) {
      Future.value(trackTargetPredStats(None))
    } else {
      pred cateSeq(target).track(Seq(target)).map { resultArr =>
        trackTargetPredStats(resultArr(0))
      }
    }
    track(targetStats)(resultFut)
  }

  overr de def cand dateS ces(
    target: Target
  ): Future[Seq[Cand dateS ce[Target, RawCand date]]] = {
    Future
      .collect(candS ceGenerator.s ces.map { cs =>
        cs. sCand dateS ceAva lable(target).map {  sEl g ble =>
           f ( sEl g ble) {
            candS ceEl g bleCounter. ncr()
            So (cs)
          } else {
            candS ceNotEl g bleCounter. ncr()
            None
          }
        }
      }).map(_.flatten)
  }

  overr de def updateCand dateCounter(
    cand dateResults: Seq[Cand dateResult[PushCand date, Result]]
  ): Un  = {
    cand dateResults.foreach {
      case cand dateResult  f cand dateResult.result == OK =>
        okCand dateCounter. ncr()
      case cand dateResult  f cand dateResult.result. s nstanceOf[ nval d] =>
         nval dCand dateCounter. ncr()
      case _ =>
    }
  }

  overr de def hydrateCand dates(
    cand dates: Seq[Cand dateDeta ls[RawCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = cand dateHydrator(cand dates)

  overr de def f lter(
    target: Target,
    hydratedCand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[
    (Seq[Cand dateDeta ls[PushCand date]], Seq[Cand dateResult[PushCand date, Result]])
  ] = rfphPrerankF lter.f lter(target, hydratedCand dates)

  def l ghtRankAndTake(
    target: Target,
    cand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    rfphL ghtRanker.rank(target, cand dates)
  }

  overr de def rank(
    target: Target,
    cand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    val featureHydratedCand datesFut = trackSeq(featureHydrat onLatency)(
      rfphFeatureHydrator
        .cand dateFeatureHydrat on(cand datesDeta ls, target.mrRequestContextForFeatureStore)
    )
    featureHydratedCand datesFut.flatMap { featureHydratedCand dates =>
      rfphStatsRecorder.rankD str but onStats(featureHydratedCand dates, numRecsPerTypeStat)
      rfphRanker. n  alRank(target, cand datesDeta ls)
    }
  }

  def reRank(
    target: Target,
    rankedCand dates: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    rfphRanker.reRank(target, rankedCand dates)
  }

  overr de def val dCand dates(
    target: Target,
    cand dates: Seq[PushCand date]
  ): Future[Seq[Result]] = {
    Future.collect(cand dates.map { cand date =>
      rfphTakeStepUt l. sCand dateVal d(cand date, cand dateVal dator).map(res => res.result)
    })
  }

  overr de def des redCand dateCount(target: Target):  nt = target.des redCand dateCount

  overr de def batchForCand datesC ck(target: Target):  nt = {
    val fsParam = PushFeatureSw chParams.NumberOfMaxCand datesToBatch nRFPHTakeStep
    val maxToBatch = target.params(fsParam)
    maxCand datesToBatch nTakeStat.add(maxToBatch)
    maxToBatch
  }

  overr de def process(
    target: Target,
    externalCand dates: Seq[RawCand date] = N l
  ): Future[Response[PushCand date, Result]] = {
     sTargetVal d(target).flatMap {
      case OK =>
        for {
          cand datesFromS ces <- trackSeq(fetchStats)(fetchCand dates(target))
          externalCand dateDeta ls = externalCand dates.map(
            Cand dateDeta ls(_, "refresh_for_push_handler_external_cand date"))
          allCand dates = cand datesFromS ces ++ externalCand dateDeta ls
          hydratedCand datesW hCopy <-
            trackSeq(cand dateHydrat onStats)(hydrateCand dates(allCand dates))
          _ = adhocStatsUt l.getCand dateS ceStats(hydratedCand datesW hCopy)
          (cand dates, preRank ngF lteredCand dates) <-
            track(f lterStats)(f lter(target, hydratedCand datesW hCopy))
          _ = adhocStatsUt l.getPreRank ngF lterStats(preRank ngF lteredCand dates)
          l ghtRankerF lteredCand dates <-
            trackSeq(l ghtRank ngStats)(l ghtRankAndTake(target, cand dates))
          _ = adhocStatsUt l.getL ghtRank ngStats(l ghtRankerF lteredCand dates)
          rankedCand dates <- trackSeq(rank ngStats)(rank(target, l ghtRankerF lteredCand dates))
          _ = adhocStatsUt l.getRank ngStats(rankedCand dates)
          rerankedCand dates <- trackSeq(reRank ngStats)(reRank(target, rankedCand dates))
          _ = adhocStatsUt l.getReRank ngStats(rerankedCand dates)
          (restr ctedCand dates, restr ctF lteredCand dates) =
            rfphRestr ctStep.restr ct(target, rerankedCand dates)
          allTakeCand dateResults <- track(takeStats)(
            take(target, restr ctedCand dates, des redCand dateCount(target))
          )
          _ = adhocStatsUt l.getTakeCand dateResultStats(allTakeCand dateResults)
          _ <- track(mrRequestCand dateScr beStats)(
            mrRequestScr beHandler.scr beForCand dateF lter ng(
              target,
              hydratedCand datesW hCopy,
              preRank ngF lteredCand dates,
              rankedCand dates,
              rerankedCand dates,
              restr ctF lteredCand dates,
              allTakeCand dateResults
            ))
        } y eld {

          /**
           * Take processes post restr ct step cand dates and returns both:
           *  1. val d +  nval d cand dates
           *  2. Cand dates that are not processed (more than des red) + restr cted cand dates
           *   need #2 only for  mportance sampl ng
           */
          val takeCand dateResults =
            allTakeCand dateResults.f lterNot { candResult =>
              candResult.result == MoreThanDes redCand dates
            }

          val total nval dCand dates = {
            preRank ngF lteredCand dates.s ze + //pre-rank ng f ltered cand dates
              (rerankedCand dates.length - restr ctedCand dates.length) + //cand dates reject  n restr ct step
              takeCand dateResults.count(_.result != OK) //cand dates reject  n take step
          }
          take nval dCand dateD st.add(
            takeCand dateResults
              .count(_.result != OK)
          ) // take step  nval d cand dates
          total nval dCand datesStat.add(total nval dCand dates)
          val allCand dateResults = takeCand dateResults ++ preRank ngF lteredCand dates
          Response(OK, allCand dateResults)
        }

      case result: Result =>
        for (_ <- track(mrRequestTargetScr beStats)(
            mrRequestScr beHandler.scr beForTargetF lter ng(target, result))) y eld {
          mrRequestScr beBu ltStats. ncr()
          Response(result, N l)
        }
    }
  }

  def refreshAndSend(request: RefreshRequest): Future[RefreshResponse] = {
    rfphRequestCounter. ncr()
    for {
      target <- track(bu ldTargetStats)(
        pushTargetUserBu lder
          .bu ldTarget(request.user d, request.context))
      response <- track(processStats)(process(target, externalCand dates = Seq.empty))
      refreshResponse <- track(not fyStats)(rfphNot f er.c ckResponseAndNot fy(response, target))
    } y eld {
      refreshResponse
    }
  }
}

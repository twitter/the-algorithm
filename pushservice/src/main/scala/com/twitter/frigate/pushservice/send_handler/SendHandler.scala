package com.tw ter.fr gate.pushserv ce.send_handler

 mport com.tw ter.f nagle.stats.BroadcastStatsRece ver
 mport com.tw ter.f nagle.stats.Stat
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.Cand dateDeta ls
 mport com.tw ter.fr gate.common.base.Cand dateF lter ngOnlyFlow
 mport com.tw ter.fr gate.common.base.Cand dateResult
 mport com.tw ter.fr gate.common.base.FeatureMap
 mport com.tw ter.fr gate.common.base.OK
 mport com.tw ter.fr gate.common.base.Response
 mport com.tw ter.fr gate.common.base.Result
 mport com.tw ter.fr gate.common.base.Stats.track
 mport com.tw ter.fr gate.common.conf g.CommonConstants
 mport com.tw ter.fr gate.common.logger.MRLogger
 mport com.tw ter.fr gate.common.rec_types.RecTypes
 mport com.tw ter.fr gate.common.ut l. nval dRequestExcept on
 mport com.tw ter.fr gate.common.ut l.MrNtabCopyObjects
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.pushserv ce.ml.Hydrat onContextBu lder
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams.EnableMag cFanoutNewsFor NtabCopy
 mport com.tw ter.fr gate.pushserv ce.scr ber.MrRequestScr beHandler
 mport com.tw ter.fr gate.pushserv ce.send_handler.generator.PushRequestToCand date
 mport com.tw ter.fr gate.pushserv ce.take.SendHandlerNot f er
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPostCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.take.cand date_val dator.SendHandlerPreCand dateVal dator
 mport com.tw ter.fr gate.pushserv ce.target.PushTargetUserBu lder
 mport com.tw ter.fr gate.pushserv ce.ut l.ResponseStatsTrackUt ls.trackStatsForResponseToRequest
 mport com.tw ter.fr gate.pushserv ce.ut l.SendHandlerPred cateUt l
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushRequest
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushRequestScr be
 mport com.tw ter.fr gate.pushserv ce.thr ftscala.PushResponse
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.nrel. avyranker.FeatureHydrator
 mport com.tw ter.ut l._

/**
 * A handler for send ng PushRequests
 */
class SendHandler(
  pushTargetUserBu lder: PushTargetUserBu lder,
  preCand dateVal dator: SendHandlerPreCand dateVal dator,
  postCand dateVal dator: SendHandlerPostCand dateVal dator,
  sendHandlerNot f er: SendHandlerNot f er,
  cand dateHydrator: SendHandlerPushCand dateHydrator,
  featureHydrator: FeatureHydrator,
  sendHandlerPred cateUt l: SendHandlerPred cateUt l,
  mrRequestScr berNode: Str ng
)(
   mpl c  val statsRece ver: StatsRece ver,
   mpl c  val conf g: Conf g)
    extends Cand dateF lter ngOnlyFlow[Target, RawCand date, PushCand date] {

   mpl c  pr vate val t  r: T  r = new JavaT  r(true)
  val stats = statsRece ver.scope("SendHandler")
  val log = MRLogger("SendHandler")

  pr vate val bu ldTargetStats = stats.scope("bu ld_target")

  pr vate val cand dateHydrat onLatency: Stat =
    stats.stat("cand dateHydrat onLatency")

  pr vate val cand datePreVal datorLatency: Stat =
    stats.stat("cand datePreVal datorLatency")

  pr vate val cand datePostVal datorLatency: Stat =
    stats.stat("cand datePostVal datorLatency")

  pr vate val featureHydrat onLatency: StatsRece ver =
    stats.scope("featureHydrat onLatency")

  pr vate val mrRequestScr beHandler =
    new MrRequestScr beHandler(mrRequestScr berNode, stats.scope("mr_request_scr be"))

  def apply(request: PushRequest): Future[PushResponse] = {
    val rece vers = Seq(
      stats,
      stats.scope(request.not f cat on.commonRecom ndat onType.toStr ng)
    )
    val bStats = BroadcastStatsRece ver(rece vers)
    bStats.counter("requests"). ncr()
    Stat
      .t  Future(bStats.stat("latency"))(
        process(request).ra seW h n(CommonConstants.maxPushRequestDurat on))
      .onSuccess {
        case (pushResp, rawCand date) =>
          trackStatsForResponseToRequest(
            rawCand date.commonRecType,
            rawCand date.target,
            pushResp,
            rece vers)(statsRece ver)
           f (!request.context.ex sts(_.darkWr e.conta ns(true))) {
            conf g.requestScr be(PushRequestScr be(request, pushResp))
          }
      }
      .onFa lure { ex =>
        bStats.counter("fa lures"). ncr()
        bStats.scope("fa lures").counter(ex.getClass.getCanon calNa ). ncr()
      }
      .map {
        case (pushResp, _) => pushResp
      }
  }

  pr vate def process(request: PushRequest): Future[(PushResponse, RawCand date)] = {
    val recType = request.not f cat on.commonRecom ndat onType

    track(bu ldTargetStats)(
      pushTargetUserBu lder
        .bu ldTarget(
          request.user d,
          request.context
        )
    ).flatMap { targetUser =>
      val responseW hScr bed nfo = request.context.ex sts { context =>
        context.responseW hScr bed nfo.conta ns(true)
      }
      val newRequest =
         f (request.not f cat on.commonRecom ndat onType == CommonRecom ndat onType.Mag cFanoutNewsEvent &&
          targetUser.params(EnableMag cFanoutNewsFor NtabCopy)) {
          val newNot f cat on = request.not f cat on.copy(ntabCopy d =
            So (MrNtabCopyObjects.Mag cFanoutNewsFor Copy.copy d))
          request.copy(not f cat on = newNot f cat on)
        } else request

       f (RecTypes. sSendHandlerType(recType) || newRequest.context.ex sts(
          _.allowCRT.conta ns(true))) {

        val rawCand dateFut = PushRequestToCand date.generatePushCand date(
          newRequest.not f cat on,
          targetUser
        )

        rawCand dateFut.flatMap { rawCand date =>
          val pushResponse = process(targetUser, Seq(rawCand date)).flatMap {
            sendHandlerNot f er.c ckResponseAndNot fy(_, responseW hScr bed nfo)
          }

          pushResponse.map { pushResponse =>
            (pushResponse, rawCand date)
          }
        }
      } else {
        Future.except on( nval dRequestExcept on(s"${recType.na } not supported  n SendHandler"))
      }
    }
  }

  pr vate def hydrateFeatures(
    cand dateDeta ls: Seq[Cand dateDeta ls[PushCand date]],
    target: Target,
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {

    cand dateDeta ls. adOpt on match {
      case So (cand dateDeta l)
           f RecTypes.notEl g bleForModelScoreTrack ng(cand dateDeta l.cand date.commonRecType) =>
        Future.value(cand dateDeta ls)

      case So (cand dateDeta l) =>
        val hydrat onContextFut = Hydrat onContextBu lder.bu ld(cand dateDeta l.cand date)
        hydrat onContextFut.flatMap { hc =>
          featureHydrator
            .hydrateCand date(Seq(hc), target.mrRequestContextForFeatureStore)
            .map { hydrat onResult =>
              val features = hydrat onResult.getOrElse(hc, FeatureMap())
              cand dateDeta l.cand date. rgeFeatures(features)
              cand dateDeta ls
            }
        }
      case _ => Future.N l
    }
  }

  overr de def process(
    target: Target,
    externalCand dates: Seq[RawCand date]
  ): Future[Response[PushCand date, Result]] = {
    val cand date = externalCand dates.map(Cand dateDeta ls(_, "realt  "))

    for {
      hydratedCand datesW hCopy <- hydrateCand dates(cand date)

      (cand dates, preHydrat onF lteredCand dates) <- track(f lterStats)(
        f lter(target, hydratedCand datesW hCopy)
      )

      featureHydratedCand dates <-
        track(featureHydrat onLatency)(hydrateFeatures(cand dates, target))

      allTakeCand dateResults <- track(takeStats)(
        take(target, featureHydratedCand dates, des redCand dateCount(target))
      )

      _ <- mrRequestScr beHandler.scr beForCand dateF lter ng(
        target = target,
        hydratedCand dates = hydratedCand datesW hCopy,
        preRank ngF lteredCand dates = preHydrat onF lteredCand dates,
        rankedCand dates = featureHydratedCand dates,
        rerankedCand dates = Seq.empty,
        restr ctF lteredCand dates = Seq.empty, // no restr ct step
        allTakeCand dateResults = allTakeCand dateResults
      )
    } y eld {

      /**
       *   comb ne t  results for all f lter ng steps and pass on  n sequence to next step
       *
       * T   s done to ensure t  f lter ng reason for t  cand date from mult ple levels of
       * f lter ng  s carr ed all t  way unt l [[PushResponse]]  s bu lt and returned from
       * fr gate-pushserv ce-send
       */
      Response(OK, allTakeCand dateResults ++ preHydrat onF lteredCand dates)
    }
  }

  overr de def hydrateCand dates(
    cand dates: Seq[Cand dateDeta ls[RawCand date]]
  ): Future[Seq[Cand dateDeta ls[PushCand date]]] = {
    Stat.t  Future(cand dateHydrat onLatency)(cand dateHydrator(cand dates))
  }

  // F lter Step - pre-pred cates and app spec f c pred cates
  overr de def f lter(
    target: Target,
    hydratedCand datesDeta ls: Seq[Cand dateDeta ls[PushCand date]]
  ): Future[
    (Seq[Cand dateDeta ls[PushCand date]], Seq[Cand dateResult[PushCand date, Result]])
  ] = {
    Stat.t  Future(cand datePreVal datorLatency)(
      sendHandlerPred cateUt l.preVal dat onForCand date(
        hydratedCand datesDeta ls,
        preCand dateVal dator
      ))
  }

  // Post Val dat on - Take step
  overr de def val dCand dates(
    target: Target,
    cand dates: Seq[PushCand date]
  ): Future[Seq[Result]] = {
    Stat.t  Future(cand datePostVal datorLatency)(Future.collect(cand dates.map { cand date =>
      sendHandlerPred cateUt l
        .postVal dat onForCand date(cand date, postCand dateVal dator)
        .map(res => res.result)
    }))
  }
}

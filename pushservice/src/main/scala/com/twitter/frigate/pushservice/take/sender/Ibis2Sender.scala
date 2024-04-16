package com.tw ter.fr gate.pushserv ce.take.sender

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.common.base.T etDeta ls
 mport com.tw ter.fr gate.common.store. b sResponse
 mport com.tw ter.fr gate.common.store. nval dConf gurat on
 mport com.tw ter.fr gate.common.store.NoRequest
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.{PushFeatureSw chParams => FS}
 mport com.tw ter.fr gate.pushserv ce.store. b s2Store
 mport com.tw ter.fr gate.pushserv ce.store.T etTranslat onStore
 mport com.tw ter.fr gate.pushserv ce.ut l.CopyUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Funct onalUt l
 mport com.tw ter.fr gate.pushserv ce.ut l. nl neAct onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Overr deNot f cat onUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.PushDev ceUt l
 mport com.tw ter.fr gate.scr be.thr ftscala.Not f cat onScr be
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.fr gate.thr ftscala.Not f cat onD splayLocat on
 mport com.tw ter. b s2.serv ce.thr ftscala. b s2Request
 mport com.tw ter.not f cat onserv ce.thr ftscala.CreateGener cNot f cat onResponse
 mport com.tw ter.storehaus.ReadableStore
 mport com.tw ter.ut l.Future

class  b s2Sender(
  push b sV2Store:  b s2Store,
  t etTranslat onStore: ReadableStore[T etTranslat onStore.Key, T etTranslat onStore.Value],
  statsRece ver: StatsRece ver) {

  pr vate val stats = statsRece ver.scope(getClass.getS mpleNa )
  pr vate val s lentPushCounter = stats.counter("s lent_push")
  pr vate val  b sSendFa lureCounter = stats.scope(" b s_send_fa lure").counter("fa lures")
  pr vate val buggyAndro dReleaseCounter = stats.counter(" s_buggy_andro d_release")
  pr vate val andro dPr maryCounter = stats.counter("andro d_pr mary_dev ce")
  pr vate val addTranslat onModelValuesCounter = stats.counter("w h_translat on_model_values")
  pr vate val patchNtabResponseEnabled = stats.scope("w h_ntab_response")
  pr vate val no b sPushStats = stats.counter("no_ b s_push")

  pr vate def  b sSend(
    cand date: PushCand date,
    translat onModelValues: Opt on[Map[Str ng, Str ng]] = None,
    ntabResponse: Opt on[CreateGener cNot f cat onResponse] = None
  ): Future[ b sResponse] = {
     f (cand date.fr gateNot f cat on.not f cat onD splayLocat on != Not f cat onD splayLocat on.PushToMob leDev ce) {
      Future.value( b sResponse( nval dConf gurat on))
    } else {
      cand date. b s2Request.flatMap {
        case So (request) =>
          val requestW hTranslat onMV =
            addTranslat onModelValues(request, translat onModelValues)
          val patc d b sRequest = {
             f (cand date.target. sLoggedOutUser) {
              requestW hTranslat onMV
            } else {
              patchNtabResponseTo b sRequest(requestW hTranslat onMV, cand date, ntabResponse)
            }
          }
          push b sV2Store.send(patc d b sRequest, cand date)
        case _ =>
          no b sPushStats. ncr()
          Future.value( b sResponse(sendStatus = NoRequest,  b s2Response = None))
      }
    }
  }

  def sendAsDarkWr e(
    cand date: PushCand date
  ): Future[ b sResponse] = {
     b sSend(cand date)
  }

  def send(
    channels: Seq[ChannelNa ],
    pushCand date: PushCand date,
    not f cat onScr be: Not f cat onScr be => Un ,
    ntabResponse: Opt on[CreateGener cNot f cat onResponse],
  ): Future[ b sResponse] = pushCand date.target. sS lentPush.flatMap {  sS lentPush: Boolean =>
     f ( sS lentPush) s lentPushCounter. ncr()
    pushCand date.target.dev ce nfo.flatMap { dev ce nfo =>
       f (dev ce nfo.ex sts(_. sS m40Andro dVers on)) buggyAndro dReleaseCounter. ncr()
       f (PushDev ceUt l. sPr maryDev ceAndro d(dev ce nfo)) andro dPr maryCounter. ncr()
      Future
        .jo n(
          Overr deNot f cat onUt l
            .getOverr de nfo(pushCand date, stats),
          CopyUt l.getCopyFeatures(pushCand date, stats),
          getTranslat onModelValues(pushCand date)
        ).flatMap {
          case (overr de nfoOpt, copyFeaturesMap, translat onModelValues) =>
             b sSend(pushCand date, translat onModelValues, ntabResponse)
              .onSuccess {  b sResponse =>
                pushCand date
                  .scr beData(
                     b s2Response =  b sResponse. b s2Response,
                     sS lentPush =  sS lentPush,
                    overr de nfoOpt = overr de nfoOpt,
                    copyFeaturesL st = copyFeaturesMap.keySet,
                    channels = channels
                  ).foreach(not f cat onScr be)
              }.onFa lure { _ =>
                pushCand date
                  .scr beData(channels = channels).foreach { data =>
                     b sSendFa lureCounter. ncr()
                    not f cat onScr be(data)
                  }
              }
        }
    }
  }

  pr vate def getTranslat onModelValues(
    cand date: PushCand date
  ): Future[Opt on[Map[Str ng, Str ng]]] = {
    cand date match {
      case t etCand date: T etCand date w h T etDeta ls =>
        val key = T etTranslat onStore.Key(
          target = cand date.target,
          t et d = t etCand date.t et d,
          t et = t etCand date.t et,
          crt = cand date.commonRecType
        )

        t etTranslat onStore
          .get(key)
          .map {
            case So (value) =>
              So (
                Map(
                  "translated_t et_text" -> value.translatedT etText,
                  "local zed_s ce_language" -> value.local zedS ceLanguage
                ))
            case None => None
          }
      case _ => Future.None
    }
  }

  pr vate def addTranslat onModelValues(
     b sRequest:  b s2Request,
    translat onModelValues: Opt on[Map[Str ng, Str ng]]
  ):  b s2Request = {
    (translat onModelValues,  b sRequest.modelValues) match {
      case (So (translat onModelVal), So (ex st ngModelValues)) =>
        addTranslat onModelValuesCounter. ncr()
         b sRequest.copy(modelValues = So (translat onModelVal ++ ex st ngModelValues))
      case (So (translat onModelVal), None) =>
        addTranslat onModelValuesCounter. ncr()
         b sRequest.copy(modelValues = So (translat onModelVal))
      case (None, _) =>  b sRequest
    }
  }

  pr vate def patchNtabResponseTo b sRequest(
     b s2Req:  b s2Request,
    cand date: PushCand date,
    ntabResponse: Opt on[CreateGener cNot f cat onResponse]
  ):  b s2Request = {
     f (cand date.target.params(FS.Enable nl neFeedbackOnPush)) {
      patchNtabResponseEnabled.counter(). ncr()
      val d sl kePos  on = cand date.target.params(FS. nl neFeedbackSubst utePos  on)
      val d sl keAct onOpt on = ntabResponse
        .map(Funct onalUt l. ncr(patchNtabResponseEnabled.counter("ntab_response_ex st")))
        .flatMap(response =>  nl neAct onUt l.getD sl ke nl neAct on(cand date, response))
        .map(Funct onalUt l. ncr(patchNtabResponseEnabled.counter("d sl ke_act on_generated")))

      // Only generate patch ser al zed  nl ne act on w n or g nal request has ex st ng ser al zed_ nl ne_act ons_v2
      val patc dSer al zedAct onOpt on =  b s2Req.modelValues
        .flatMap(model => model.get("ser al zed_ nl ne_act ons_v2"))
        .map(Funct onalUt l. ncr(patchNtabResponseEnabled.counter(" nl ne_act on_v2_ex sts")))
        .map(ser al zed =>
           nl neAct onUt l
            .patch nl neAct onAtPos  on(ser al zed, d sl keAct onOpt on, d sl kePos  on))
        .map(Funct onalUt l. ncr(patchNtabResponseEnabled.counter("patch_ nl ne_act on_generated")))

      ( b s2Req.modelValues, patc dSer al zedAct onOpt on) match {
        case (So (ex st ngModelValue), So (patc dAct onV2)) =>
          patchNtabResponseEnabled.scope("patch_appl ed").counter(). ncr()
           b s2Req.copy(modelValues =
            So (ex st ngModelValue ++ Map("ser al zed_ nl ne_act ons_v2" -> patc dAct onV2)))
        case _ =>  b s2Req
      }
    } else  b s2Req
  }
}

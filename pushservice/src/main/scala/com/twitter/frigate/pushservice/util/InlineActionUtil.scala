package com.tw ter.fr gate.pushserv ce.ut l

 mport com.google.common. o.BaseEncod ng
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.params. nl neAct onsEnum
 mport com.tw ter.fr gate.pushserv ce.params.PushParams
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter. b s2.l b.ut l.JsonMarshal
 mport com.tw ter.not f cat ons.platform.thr ftscala._
 mport com.tw ter.not f cat onserv ce.thr ftscala.CreateGener cNot f cat onResponse
 mport com.tw ter.scrooge.B naryThr ftStructSer al zer
 mport com.tw ter.ut l.Future

/**
 * T  class prov des ut l y funct ons for  nl ne act on for push
 */
object  nl neAct onUt l {

  def scopedStats(statsRece ver: StatsRece ver): StatsRece ver =
    statsRece ver.scope(getClass.getS mpleNa )

  /**
   * Ut l funct on to bu ld  b  nl ne act ons for  b s
   * @param act ons l st of  nl ne act ons to be hydrated depend ng on t  CRT
   * @param enableForDesktop b  f  b  nl ne act ons should be shown on desktop R b, for exper  ntat on purpose
   * @param enableForMob le b  f  b  nl ne act ons should be shwon on mob le R b, for exper  ntat on purpose
   * @return Params for  b  nl ne act ons to be consu d by `smart. nl ne.act ons. b.mustac `  n  b s
   */
  def getGeneratedT et nl neAct onsFor b(
    act ons: Seq[ nl neAct onsEnum.Value],
    enableForDesktop b: Boolean,
    enableForMob le b: Boolean
  ): Map[Str ng, Str ng] = {
     f (!enableForDesktop b && !enableForMob le b) {
      Map.empty
    } else {
      val  nl neAct ons = bu ldEnr c d nl neAct onsMap(act ons) ++ Map(
        "enable_for_desktop_ b" -> enableForDesktop b.toStr ng,
        "enable_for_mob le_ b" -> enableForMob le b.toStr ng
      )
      Map(
        " nl ne_act on_deta ls_ b" -> JsonMarshal.toJson( nl neAct ons),
      )
    }
  }

  def getGeneratedT et nl neAct onsV1(
    act ons: Seq[ nl neAct onsEnum.Value]
  ): Map[Str ng, Str ng] = {
    val  nl neAct ons = bu ldEnr c d nl neAct onsMap(act ons)
    Map(
      " nl ne_act on_deta ls" -> JsonMarshal.toJson( nl neAct ons)
    )
  }

  pr vate def bu ldEnr c d nl neAct onsMap(
    act ons: Seq[ nl neAct onsEnum.Value]
  ): Map[Str ng, Seq[Map[Str ng, Any]]] = {
    Map(
      "act ons" -> act ons
        .map(_.toStr ng.toLo rCase)
        .z pW h ndex
        .map {
          case (a: Str ng,  :  nt) =>
            Map("act on" -> a) ++ Map(
              s"use_${a}_str ngcenter_key" -> true,
              "last" -> (  == (act ons.length - 1))
            )
        }.seq
    )
  }

  def getGeneratedT et nl neAct onsV2(
    act ons: Seq[ nl neAct onsEnum.Value]
  ): Map[Str ng, Str ng] = {
    val v2CustomAct ons = act ons
      .map {
        case  nl neAct onsEnum.Favor e =>
          Not f cat onCustomAct on(
            So ("mr_ nl ne_favor e_t le"),
            CustomAct onData.LegacyAct on(LegacyAct on(Act on dent f er.Favor e))
          )
        case  nl neAct onsEnum.Follow =>
          Not f cat onCustomAct on(
            So ("mr_ nl ne_follow_t le"),
            CustomAct onData.LegacyAct on(LegacyAct on(Act on dent f er.Follow)))
        case  nl neAct onsEnum.Reply =>
          Not f cat onCustomAct on(
            So ("mr_ nl ne_reply_t le"),
            CustomAct onData.LegacyAct on(LegacyAct on(Act on dent f er.Reply)))
        case  nl neAct onsEnum.Ret et =>
          Not f cat onCustomAct on(
            So ("mr_ nl ne_ret et_t le"),
            CustomAct onData.LegacyAct on(LegacyAct on(Act on dent f er.Ret et)))
        case _ =>
          Not f cat onCustomAct on(
            So ("mr_ nl ne_favor e_t le"),
            CustomAct onData.LegacyAct on(LegacyAct on(Act on dent f er.Favor e))
          )
      }
    val not f cat ons = Not f cat onCustomAct ons(v2CustomAct ons)
    Map("ser al zed_ nl ne_act ons_v2" -> ser al zeAct onsToBase64(not f cat ons))
  }

  def getD sl ke nl neAct on(
    cand date: PushCand date,
    ntabResponse: CreateGener cNot f cat onResponse
  ): Opt on[Not f cat onCustomAct on] = {
    ntabResponse.successKey.map(successKey => {
      val urlParams = Map[Str ng, Str ng](
        "ans r" -> "d sl ke",
        "not f cat on_hash" -> successKey.hashKey.toStr ng,
        "upstream_u d" -> cand date. mpress on d,
        "not f cat on_t  stamp" -> successKey.t  stampM ll s.toStr ng
      )
      val urlParamsStr ng = urlParams.map(kvp => f"${kvp._1}=${kvp._2}").mkStr ng("&")

      val httpPostRequest = HttpRequest.PostRequest(
        PostRequest(url = f"/2/not f cat ons/feedback.json?$urlParamsStr ng", bodyParams = None))
      val httpRequestAct on = HttpRequestAct on(
        httpRequest = httpPostRequest,
        scr beAct on = Opt on("d sl ke_scr be_act on"),
         sAuthor zat onRequ red = Opt on(true),
         sDestruct ve = Opt on(false),
        undoable = None
      )
      val d sl keAct on = CustomAct onData.HttpRequestAct on(httpRequestAct on)
      Not f cat onCustomAct on(t le = Opt on("mr_ nl ne_d sl ke_t le"), act on = d sl keAct on)
    })
  }

  /**
   * G ven a ser al zed  nl ne act on v2, update t  act on at  ndex to t  g ven new act on.
   *  f g ven  ndex  s b gger than current act on length, append t  g ven  nl ne act on at t  end.
   * @param ser al zed_ nl ne_act ons_v2 t  or g nal act on  n ser al zed vers on
   * @param act onOpt on an Opt on of t  new act on to replace t  old one
   * @param  ndex t  pos  on w re t  old act on w ll be replaced
   * @return a new ser al zed  nl ne act on v2
   */
  def patch nl neAct onAtPos  on(
    ser al zed_ nl ne_act ons_v2: Str ng,
    act onOpt on: Opt on[Not f cat onCustomAct on],
     ndex:  nt
  ): Str ng = {
    val or g nalAct ons: Seq[Not f cat onCustomAct on] = deser al zeAct onsFromStr ng(
      ser al zed_ nl ne_act ons_v2).act ons
    val newAct ons = act onOpt on match {
      case So (act on)  f  ndex >= or g nalAct ons.s ze => or g nalAct ons ++ Seq(act on)
      case So (act on) => or g nalAct ons.updated( ndex, act on)
      case _ => or g nalAct ons
    }
    ser al zeAct onsToBase64(Not f cat onCustomAct ons(newAct ons))
  }

  /**
   * Return l st of ava lable  nl ne act ons for  b s2 model
   */
  def getGeneratedT et nl neAct ons(
    target: Target,
    statsRece ver: StatsRece ver,
    act ons: Seq[ nl neAct onsEnum.Value],
  ): Map[Str ng, Str ng] = {
    val scopedStatsRece ver = scopedStats(statsRece ver)
    val useV1 = target.params(PushFeatureSw chParams.Use nl neAct onsV1)
    val useV2 = target.params(PushFeatureSw chParams.Use nl neAct onsV2)
     f (useV1 && useV2) {
      scopedStatsRece ver.counter("use_v1_and_use_v2"). ncr()
      getGeneratedT et nl neAct onsV1(act ons) ++ getGeneratedT et nl neAct onsV2(act ons)
    } else  f (useV1 && !useV2) {
      scopedStatsRece ver.counter("only_use_v1"). ncr()
      getGeneratedT et nl neAct onsV1(act ons)
    } else  f (!useV1 && useV2) {
      scopedStatsRece ver.counter("only_use_v2"). ncr()
      getGeneratedT et nl neAct onsV2(act ons)
    } else {
      scopedStatsRece ver.counter("use_ne  r_v1_nor_v2"). ncr()
      Map.empty[Str ng, Str ng]
    }
  }

  /**
   * Return T et  nl ne act on  b s2 model values after apply ng exper  nt log c
   */
  def getT et nl neAct onValue(target: Target): Future[Map[Str ng, Str ng]] = {
     f (target. sLoggedOutUser) {
      Future(
        Map(
          "show_ nl ne_act on" -> "false"
        )
      )
    } else {
      val show nl neAct on: Boolean = target.params(PushParams.MRAndro d nl neAct onOnPushCopyParam)
      Future(
        Map(
          "show_ nl ne_act on" -> s"$show nl neAct on"
        )
      )
    }
  }

  pr vate val b naryThr ftStructSer al zer: B naryThr ftStructSer al zer[
    Not f cat onCustomAct ons
  ] = B naryThr ftStructSer al zer.apply(Not f cat onCustomAct ons)
  pr vate val base64Encod ng = BaseEncod ng.base64()

  def ser al zeAct onsToBase64(not f cat onCustomAct ons: Not f cat onCustomAct ons): Str ng = {
    val act onsAsByteArray: Array[Byte] =
      b naryThr ftStructSer al zer.toBytes(not f cat onCustomAct ons)
    base64Encod ng.encode(act onsAsByteArray)
  }

  def deser al zeAct onsFromStr ng(ser al zed nl neAct onV2: Str ng): Not f cat onCustomAct ons = {
    val act onAsByteArray = base64Encod ng.decode(ser al zed nl neAct onV2)
    b naryThr ftStructSer al zer.fromBytes(act onAsByteArray)
  }

}

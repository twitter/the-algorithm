package com.tw ter.fr gate.pushserv ce.model.ntab

 mport com.tw ter.fr gate.common.base.TrendT etCand date
 mport com.tw ter.fr gate.common.base.T etAuthorDeta ls
 mport com.tw ter.fr gate.common.base.T etCand date
 mport com.tw ter.fr gate.pushserv ce.except on.T etNTabRequestHydratorExcept on
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.take.Not f cat onServ ceSender
 mport com.tw ter.fr gate.pushserv ce.ut l.Ema lLand ngPageExper  ntUt l
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayText
 mport com.tw ter.not f cat onserv ce.thr ftscala.D splayTextEnt y
 mport com.tw ter.not f cat onserv ce.thr ftscala.TextValue
 mport com.tw ter.ut l.Future

tra  TrendT etNtabHydrator extends T etNTabRequestHydrator {
  self: PushCand date w h TrendT etCand date w h T etCand date w h T etAuthorDeta ls =>

  pr vate lazy val trendT etNtabStats = self.statsRece ver.scope("trend_t et_ntab")

  pr vate lazy val ruxLand ngOnNtabCounter =
    trendT etNtabStats.counter("use_rux_land ng_on_ntab")

  overr de lazy val d splayTextEnt  esFut: Future[Seq[D splayTextEnt y]] =
    Not f cat onServ ceSender
      .getD splayTextEnt yFromUser(t etAuthor, f eldNa  = "author_na ",  sBold = true)
      .map(
        _.toSeq :+ D splayTextEnt y(
          na  = "trend_na ",
          value = TextValue.Text(trendNa ),
          emphas s = true)
      )

  overr de lazy val facep leUsersFut: Future[Seq[Long]] = sender dFut.map(Seq(_))

  overr de lazy val soc alProofD splayText: Opt on[D splayText] = None

  overr de def refreshableType: Opt on[Str ng] = ntabCopy.refreshableType

  overr de lazy val tapThroughFut: Future[Str ng] = {
    Future.jo n(t etAuthor, target.dev ce nfo).map {
      case (So (author), So (dev ce nfo)) =>
        val enableRuxLand ngPage = dev ce nfo. sRuxLand ngPageEl g ble && target.params(
          PushFeatureSw chParams.EnableNTabRuxLand ngPage)
        val authorProf le = author.prof le.getOrElse(
          throw new T etNTabRequestHydratorExcept on(
            s"Unable to obta n author prof le for: ${author. d}"))

         f (enableRuxLand ngPage) {
          ruxLand ngOnNtabCounter. ncr()
          Ema lLand ngPageExper  ntUt l.createNTabRuxLand ngUR (authorProf le.screenNa , t et d)
        } else {
          s"${authorProf le.screenNa }/status/${t et d.toStr ng}"
        }

      case _ =>
        throw new T etNTabRequestHydratorExcept on(
          s"Unable to obta n author and target deta ls to generate tap through for T et: $t et d")
    }
  }
}

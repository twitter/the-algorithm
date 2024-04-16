package com.tw ter.ho _m xer.model.request

 mport com.tw ter.product_m xer.core.model.marshall ng.request.Cl entContext
 mport com.tw ter.{t  l neserv ce => tls}

case class Dev ceContext(
   sPoll ng: Opt on[Boolean],
  requestContext: Opt on[Str ng],
  latestControlAva lable: Opt on[Boolean],
  autoplayEnabled: Opt on[Boolean]) {

  lazy val requestContextValue: Opt on[Dev ceContext.RequestContext.Value] =
    requestContext.flatMap { value =>
      val normal zedValue = value.tr m.toLo rCase()
      Dev ceContext.RequestContext.values.f nd(_.toStr ng == normal zedValue)
    }

  def toT  l neServ ceDev ceContext(cl entContext: Cl entContext): tls.Dev ceContext =
    tls.Dev ceContext(
      countryCode = cl entContext.countryCode,
      languageCode = cl entContext.languageCode,
      cl entApp d = cl entContext.app d,
       pAddress = cl entContext. pAddress,
      guest d = cl entContext.guest d,
      sess on d = None,
      t  zone = None,
      userAgent = cl entContext.userAgent,
      dev ce d = cl entContext.dev ce d,
       sPoll ng =  sPoll ng,
      requestProvenance = requestContext,
      referrer = None,
      tfeAuth ader = None,
      mob leDev ce d = cl entContext.mob leDev ce d,
       sSess onStart = None,
      d splayS ze = None,
       sURTRequest = So (true),
      latestControlAva lable = latestControlAva lable,
      guest dMarket ng = cl entContext.guest dMarket ng,
       s nternalOrTwoff ce = cl entContext. sTwoff ce,
      browserNot f cat onPerm ss on = None,
      guest dAds = cl entContext.guest dAds,
    )
}

object Dev ceContext {
  val Empty: Dev ceContext = Dev ceContext(
     sPoll ng = None,
    requestContext = None,
    latestControlAva lable = None,
    autoplayEnabled = None
  )

  /**
   * Constants wh ch reflect val d cl ent request provenances (why a request was  n  ated, encoded
   * by t  "request_context" HTTP para ter).
   */
  object RequestContext extends Enu rat on {
    val Auto = Value("auto")
    val Foreground = Value("foreground")
    val Gap = Value("gap")
    val Launch = Value("launch")
    val ManualRefresh = Value("manual_refresh")
    val Nav gate = Value("nav gate")
    val Poll ng = Value("poll ng")
    val PullToRefresh = Value("ptr")
    val S gnup = Value("s gnup")
    val T etSelfThread = Value("t et_self_thread")
    val BackgroundFetch = Value("background_fetch")
  }
}

tra  HasDev ceContext {
  def dev ceContext: Opt on[Dev ceContext]
}

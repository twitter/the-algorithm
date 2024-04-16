package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.Mag cFanoutProductLaunchCand date
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Product nfo
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l. rgeModelValues
 mport com.tw ter.ut l.Future

tra  Mag cFanoutProductLaunch b s2Hydrator
    extends CustomConf gurat onMapFor b s
    w h  b s2HydratorForCand date {
  self: PushCand date w h Mag cFanoutProductLaunchCand date =>

  pr vate def getProduct nfoMap(product nfo: Product nfo): Map[Str ng, Str ng] = {
    val t leMap = product nfo.t le
      .map { t le =>
        Map("t le" -> t le)
      }.getOrElse(Map.empty)
    val bodyMap = product nfo.body
      .map { body =>
        Map("body" -> body)
      }.getOrElse(Map.empty)
    val deepl nkMap = product nfo.deepl nk
      .map { deepl nk =>
        Map("deepl nk" -> deepl nk)
      }.getOrElse(Map.empty)

    t leMap ++ bodyMap ++ deepl nkMap
  }

  pr vate lazy val land ngPage: Map[Str ng, Str ng] = {
    val urlFromFS = target.params(PushFeatureSw chParams.ProductLaunchLand ngPageDeepL nk)
    Map("push_land_url" -> urlFromFS)
  }

  pr vate lazy val customProductLaunchPushDeta ls: Map[Str ng, Str ng] = {
    fr gateNot f cat on.mag cFanoutProductLaunchNot f cat on match {
      case So (productLaunchNot f) =>
        productLaunchNot f.product nfo match {
          case So (product nfo) =>
            getProduct nfoMap(product nfo)
          case _ => Map.empty
        }
      case _ => Map.empty
    }
  }

  overr de lazy val customF eldsMapFut: Future[Map[Str ng, Str ng]] =
     rgeModelValues(super.customF eldsMapFut, customProductLaunchPushDeta ls)

  overr de lazy val modelValues: Future[Map[Str ng, Str ng]] =
     rgeModelValues(super.modelValues, land ngPage)
}

package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutEventHydratedCand date
 mport com.tw ter.fr gate.pushserv ce.params.PushConstants
 mport com.tw ter.fr gate.pushserv ce.params.PushFeatureSw chParams
 mport com.tw ter.fr gate.pushserv ce.pred cate.mag c_fanout.Mag cFanoutPred catesUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l._
 mport com.tw ter.ut l.Future

tra  Mag cFanoutNewsEvent b s2Hydrator extends  b s2HydratorForCand date {
  self: PushCand date w h Mag cFanoutEventHydratedCand date =>

  overr de lazy val sender d: Opt on[Long] = {
    val  sUgmMo nt = self.semant cCoreEnt yTags.values.flatten.toSet
      .conta ns(Mag cFanoutPred catesUt l.UgmMo ntTag)

    own ngTw terUser ds. adOpt on match {
      case So (own ngTw terUser d)
           f  sUgmMo nt && target.params(
            PushFeatureSw chParams.Mag cFanoutNewsUserGeneratedEventsEnable) =>
        So (own ngTw terUser d)
      case _ => None
    }
  }

  lazy val stats = self.statsRece ver.scope("Mag cFanout")
  lazy val default mageCounter = stats.counter("default_ mage")
  lazy val request mageCounter = stats.counter("request_num")
  lazy val none mageCounter = stats.counter("none_num")

  pr vate def getModelValue d aUrl(
    urlOpt: Opt on[Str ng],
    mapKey: Str ng
  ): Opt on[(Str ng, Str ng)] = {
    request mageCounter. ncr()
    urlOpt match {
      case So (PushConstants.DefaultEvent d aUrl) =>
        default mageCounter. ncr()
        None
      case So (url) => So (mapKey -> url)
      case None =>
        none mageCounter. ncr()
        None
    }
  }

  pr vate lazy val eventModelValuesFut: Future[Map[Str ng, Str ng]] = {
    for {
      t le <- eventT leFut
      square mageUrl <- square mageUrlFut
      pr mary mageUrl <- pr mary mageUrlFut
      eventDescr pt onOpt <- eventDescr pt onFut
    } y eld {

      val author d = own ngTw terUser ds. adOpt on match {
        case So (author)
             f target.params(PushFeatureSw chParams.Mag cFanoutNewsUserGeneratedEventsEnable) =>
          So ("author" -> author.toStr ng)
        case _ => None
      }

      val eventDescr pt on = eventDescr pt onOpt match {
        case So (descr pt on)
             f target.params(PushFeatureSw chParams.Mag cFanoutNewsEnableDescr pt onCopy) =>
          So ("event_descr pt on" -> descr pt on)
        case _ =>
          None
      }

      Map(
        "event_ d" -> s"$event d",
        "event_t le" -> t le
      ) ++
        getModelValue d aUrl(square mageUrl, "square_ d a_url") ++
        getModelValue d aUrl(pr mary mageUrl, " d a_url") ++
        author d ++
        eventDescr pt on
    }
  }

  pr vate lazy val top cValuesFut: Future[Map[Str ng, Str ng]] = {
     f (target.params(PushFeatureSw chParams.EnableTop cCopyForMF)) {
      follo dTop cLocal zedEnt  es.map(_. adOpt on).flatMap {
        case So (local zedEnt y) =>
          Future.value(Map("top c_na " -> local zedEnt y.local zedNa ForD splay))
        case _ =>
          ergLocal zedEnt  es.map(_. adOpt on).map {
            case So (local zedEnt y)
                 f target.params(PushFeatureSw chParams.EnableTop cCopyFor mpl c Top cs) =>
              Map("top c_na " -> local zedEnt y.local zedNa ForD splay)
            case _ => Map.empty[Str ng, Str ng]
          }
      }
    } else {
      Future.value(Map.empty[Str ng, Str ng])
    }
  }

  overr de lazy val modelValues: Future[Map[Str ng, Str ng]] =
     rgeFutModelValues(super.modelValues,  rgeFutModelValues(eventModelValuesFut, top cValuesFut))

}

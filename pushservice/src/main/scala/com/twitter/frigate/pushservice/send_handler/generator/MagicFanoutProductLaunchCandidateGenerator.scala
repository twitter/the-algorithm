package com.tw ter.fr gate.pushserv ce.send_handler.generator

 mport com.tw ter.fr gate.common.base.Mag cFanoutProductLaunchCand date
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Mag cEventsReason
 mport com.tw ter.fr gate.mag c_events.thr ftscala.ProductType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.ut l.Future

object Mag cFanoutProductLaunchCand dateGenerator extends Cand dateGenerator {

  overr de def getCand date(
    targetUser: PushTypes.Target,
    not f cat on: Fr gateNot f cat on
  ): Future[PushTypes.RawCand date] = {

    requ re(
      not f cat on.commonRecom ndat onType == CommonRecom ndat onType.Mag cFanoutProductLaunch,
      "Mag cFanoutProductLaunch: unexpected CRT " + not f cat on.commonRecom ndat onType
    )
    requ re(
      not f cat on.mag cFanoutProductLaunchNot f cat on. sDef ned,
      "Mag cFanoutProductLaunch: mag cFanoutProductLaunchNot f cat on  s not def ned")
    requ re(
      not f cat on.mag cFanoutProductLaunchNot f cat on.ex sts(_.mag cFanoutPush d. sDef ned),
      "Mag cFanoutProductLaunch: mag cFanoutPush d  s not def ned")
    requ re(
      not f cat on.mag cFanoutProductLaunchNot f cat on.ex sts(_.fanoutReasons. sDef ned),
      "Mag cFanoutProductLaunch: fanoutReasons  s not def ned")

    val mag cFanoutProductLaunchNot f cat on = not f cat on.mag cFanoutProductLaunchNot f cat on.get

    val cand date = new RawCand date w h Mag cFanoutProductLaunchCand date {

      overr de val target: Target = targetUser

      overr de val push d: Long =
        mag cFanoutProductLaunchNot f cat on.mag cFanoutPush d.get

      overr de val cand dateMag cEventsReasons: Seq[Mag cEventsReason] =
        mag cFanoutProductLaunchNot f cat on.fanoutReasons.get

      overr de val productLaunchType: ProductType =
        mag cFanoutProductLaunchNot f cat on.productLaunchType

      overr de val fr gateNot f cat on: Fr gateNot f cat on = not f cat on
    }

    Future.value(cand date)
  }
}

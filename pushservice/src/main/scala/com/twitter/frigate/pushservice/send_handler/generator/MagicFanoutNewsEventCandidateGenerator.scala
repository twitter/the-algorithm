package com.tw ter.fr gate.pushserv ce.send_handler.generator

 mport com.tw ter.fr gate.common.base.Mag cFanoutNewsEventCand date
 mport com.tw ter.fr gate.mag c_events.thr ftscala.Mag cEventsReason
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.fr gate.thr ftscala.Mag cFanoutEventNot f cat onDeta ls
 mport com.tw ter.ut l.Future

object Mag cFanoutNewsEventCand dateGenerator extends Cand dateGenerator {

  overr de def getCand date(
    targetUser: Target,
    not f cat on: Fr gateNot f cat on
  ): Future[RawCand date] = {

    /**
     * fr gateNot f cat on recom ndat on type should be [[CommonRecom ndat onType.Mag cFanoutNewsEvent]]
     * AND push d f eld should be set
     **/
    requ re(
      not f cat on.commonRecom ndat onType == CommonRecom ndat onType.Mag cFanoutNewsEvent,
      "Mag cFanoutNewsEvent: unexpected CRT " + not f cat on.commonRecom ndat onType
    )

    requ re(
      not f cat on.mag cFanoutEventNot f cat on.ex sts(_.push d. sDef ned),
      "Mag cFanoutNewsEvent: push d  s not def ned")

    val mag cFanoutEventNot f cat on = not f cat on.mag cFanoutEventNot f cat on.get

    val cand date = new RawCand date w h Mag cFanoutNewsEventCand date {

      overr de val target: Target = targetUser

      overr de val event d: Long = mag cFanoutEventNot f cat on.event d

      overr de val push d: Long = mag cFanoutEventNot f cat on.push d.get

      overr de val cand dateMag cEventsReasons: Seq[Mag cEventsReason] =
        mag cFanoutEventNot f cat on.eventReasons.getOrElse(Seq.empty)

      overr de val mo nt d: Opt on[Long] = mag cFanoutEventNot f cat on.mo nt d

      overr de val eventLanguage: Opt on[Str ng] = mag cFanoutEventNot f cat on.eventLanguage

      overr de val deta ls: Opt on[Mag cFanoutEventNot f cat onDeta ls] =
        mag cFanoutEventNot f cat on.deta ls

      overr de val fr gateNot f cat on: Fr gateNot f cat on = not f cat on
    }

    Future.value(cand date)
  }
}

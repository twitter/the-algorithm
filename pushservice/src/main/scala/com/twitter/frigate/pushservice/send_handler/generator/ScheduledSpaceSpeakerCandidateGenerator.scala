package com.tw ter.fr gate.pushserv ce.send_handler.generator

 mport com.tw ter.fr gate.common.base.Sc duledSpaceSpeakerCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.RawCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.Target
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.Fr gateNot f cat on
 mport com.tw ter.ut l.Future

object Sc duledSpaceSpeakerCand dateGenerator extends Cand dateGenerator {

  overr de def getCand date(
    targetUser: Target,
    not f cat on: Fr gateNot f cat on
  ): Future[RawCand date] = {

    /**
     * fr gateNot f cat on recom ndat on type should be [[CommonRecom ndat onType.Sc duledSpaceSpeaker]]
     *
     **/
    requ re(
      not f cat on.commonRecom ndat onType == CommonRecom ndat onType.Sc duledSpaceSpeaker,
      "Sc duledSpaceSpeaker: unexpected CRT " + not f cat on.commonRecom ndat onType
    )

    val spaceNot f cat on = not f cat on.spaceNot f cat on.getOrElse(
      throw new  llegalStateExcept on("Sc duledSpaceSpeaker not f cat on object not def ned"))

    requ re(
      spaceNot f cat on.hostUser d. sDef ned,
      "Sc duledSpaceSpeaker not f cat on - hostUser d not def ned"
    )

    val spaceHost d = spaceNot f cat on.hostUser d

    requ re(
      spaceNot f cat on.sc duledStartT  . sDef ned,
      "Sc duledSpaceSpeaker not f cat on - sc duledStartT   not def ned"
    )

    val sc duledStartT   = spaceNot f cat on.sc duledStartT  .get

    val cand date = new RawCand date w h Sc duledSpaceSpeakerCand date {
      overr de val target: Target = targetUser
      overr de val fr gateNot f cat on: Fr gateNot f cat on = not f cat on
      overr de val space d: Str ng = spaceNot f cat on.broadcast d
      overr de val host d: Opt on[Long] = spaceHost d
      overr de val startT  : Long = sc duledStartT  
      overr de val speaker ds: Opt on[Seq[Long]] = spaceNot f cat on.speakers
      overr de val l stener ds: Opt on[Seq[Long]] = spaceNot f cat on.l steners
    }

    Future.value(cand date)
  }
}

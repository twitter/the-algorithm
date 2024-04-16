package com.tw ter.fr gate.pushserv ce.take.pred cates.cand date_map

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model._
 mport com.tw ter.fr gate.pushserv ce.conf g.Conf g
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType
 mport com.tw ter.fr gate.thr ftscala.CommonRecom ndat onType._
 mport com.tw ter. rm .pred cate.Na dPred cate

object SendHandlerCand datePred catesMap {

  def preCand datePred cates(
     mpl c  conf g: Conf g
  ): Map[CommonRecom ndat onType, L st[Na dPred cate[_ <: PushCand date]]] = {
    val mag cFanoutNewsEventCand datePred cates =
      Mag cFanoutNewsEventCand datePred cates(conf g).preCand dateSpec f cPred cates

    val sc duledSpaceSubscr berPred cates = Sc duledSpaceSubscr berCand datePred cates(
      conf g).preCand dateSpec f cPred cates

    val sc duledSpaceSpeakerPred cates = Sc duledSpaceSpeakerCand datePred cates(
      conf g).preCand dateSpec f cPred cates

    val mag cFanoutSportsEventCand datePred cates =
      Mag cFanoutSportsEventCand datePred cates(conf g).preCand dateSpec f cPred cates

    val mag cFanoutProductLaunchPred cates = Mag cFanoutProductLaunchPushCand datePred cates(
      conf g).preCand dateSpec f cPred cates

    val creatorSubscr pt onFanoutPred cates = Mag cFanouCreatorSubscr pt onEventPushPred cates(
      conf g).preCand dateSpec f cPred cates

    val newCreatorFanoutPred cates = Mag cFanoutNewCreatorEventPushPred cates(
      conf g).preCand dateSpec f cPred cates

    Map(
      Mag cFanoutNewsEvent -> mag cFanoutNewsEventCand datePred cates,
      Sc duledSpaceSubscr ber -> sc duledSpaceSubscr berPred cates,
      Sc duledSpaceSpeaker -> sc duledSpaceSpeakerPred cates,
      Mag cFanoutSportsEvent -> mag cFanoutSportsEventCand datePred cates,
      Mag cFanoutProductLaunch -> mag cFanoutProductLaunchPred cates,
      NewCreator -> newCreatorFanoutPred cates,
      CreatorSubscr ber -> creatorSubscr pt onFanoutPred cates
    )
  }

  def postCand datePred cates(
     mpl c  conf g: Conf g
  ): Map[CommonRecom ndat onType, L st[Na dPred cate[_ <: PushCand date]]] = {
    val mag cFanoutNewsEventCand datePred cates =
      Mag cFanoutNewsEventCand datePred cates(conf g).postCand dateSpec f cPred cates

    val sc duledSpaceSubscr berPred cates = Sc duledSpaceSubscr berCand datePred cates(
      conf g).postCand dateSpec f cPred cates

    val sc duledSpaceSpeakerPred cates = Sc duledSpaceSpeakerCand datePred cates(
      conf g).postCand dateSpec f cPred cates

    val mag cFanoutSportsEventCand datePred cates =
      Mag cFanoutSportsEventCand datePred cates(conf g).postCand dateSpec f cPred cates
    val mag cFanoutProductLaunchPred cates = Mag cFanoutProductLaunchPushCand datePred cates(
      conf g).postCand dateSpec f cPred cates
    val creatorSubscr pt onFanoutPred cates = Mag cFanouCreatorSubscr pt onEventPushPred cates(
      conf g).postCand dateSpec f cPred cates
    val newCreatorFanoutPred cates = Mag cFanoutNewCreatorEventPushPred cates(
      conf g).postCand dateSpec f cPred cates

    Map(
      Mag cFanoutNewsEvent -> mag cFanoutNewsEventCand datePred cates,
      Sc duledSpaceSubscr ber -> sc duledSpaceSubscr berPred cates,
      Sc duledSpaceSpeaker -> sc duledSpaceSpeakerPred cates,
      Mag cFanoutSportsEvent -> mag cFanoutSportsEventCand datePred cates,
      Mag cFanoutProductLaunch -> mag cFanoutProductLaunchPred cates,
      NewCreator -> newCreatorFanoutPred cates,
      CreatorSubscr ber -> creatorSubscr pt onFanoutPred cates
    )
  }
}

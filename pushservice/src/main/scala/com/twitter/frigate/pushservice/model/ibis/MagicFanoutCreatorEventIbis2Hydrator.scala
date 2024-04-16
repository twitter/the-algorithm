package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.mag c_events.thr ftscala.CreatorFanoutType
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.model.Mag cFanoutCreatorEventPushCand date
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l. rgeModelValues
 mport com.tw ter.ut l.Future

tra  Mag cFanoutCreatorEvent b s2Hydrator
    extends CustomConf gurat onMapFor b s
    w h  b s2HydratorForCand date {
  self: PushCand date w h Mag cFanoutCreatorEventPushCand date =>

  val userMap = Map(
    "handle" -> userProf le.screenNa ,
    "d splay_na " -> userProf le.na 
  )

  overr de val sender d = hydratedCreator.map(_. d)

  overr de lazy val modelValues: Future[Map[Str ng, Str ng]] =
     rgeModelValues(super.modelValues, userMap)

  overr de val  b s2Request = creatorFanoutType match {
    case CreatorFanoutType.UserSubscr pt on => Future.None
    case CreatorFanoutType.NewCreator => super. b s2Request
    case _ => super. b s2Request
  }
}

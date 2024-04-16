package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.D scoverTw terCand date
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l. rgeFutModelValues
 mport com.tw ter.ut l.Future

tra  D scoverTw terPush b s2Hydrator extends  b s2HydratorForCand date {
  self: PushCand date w h D scoverTw terCand date =>

  pr vate lazy val targetModelValues: Map[Str ng, Str ng] = Map(
    "target_user" -> target.target d.toStr ng
  )

  overr de lazy val modelValues: Future[Map[Str ng, Str ng]] =
     rgeFutModelValues(super.modelValues, Future.value(targetModelValues))
}

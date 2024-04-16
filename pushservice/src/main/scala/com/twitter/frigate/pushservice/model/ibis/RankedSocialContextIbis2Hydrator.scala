package com.tw ter.fr gate.pushserv ce.model. b s

 mport com.tw ter.fr gate.common.base.Soc alContextAct on
 mport com.tw ter.fr gate.common.base.Soc alContextAct ons
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l
 mport com.tw ter.fr gate.pushserv ce.ut l.Push b sUt l
 mport com.tw ter.ut l.Future

tra  RankedSoc alContext b s2Hydrator {
  self: PushCand date w h Soc alContextAct ons =>

  lazy val soc alContextModelValues: Future[Map[Str ng, Str ng]] =
    rankedSoc alContextAct onsFut.map(rankedSoc alContextAct ons =>
      Push b sUt l.getSoc alContextModelValues(rankedSoc alContextAct ons.map(_.user d)))

  lazy val rankedSoc alContextAct onsFut: Future[Seq[Soc alContextAct on]] =
    Cand dateUt l.getRankedSoc alContext(
      soc alContextAct ons,
      target.seedsW h  ght,
      defaultToRecency = false)
}

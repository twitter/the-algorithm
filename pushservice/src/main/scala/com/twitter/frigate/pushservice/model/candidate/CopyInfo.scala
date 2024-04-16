package com.tw ter.fr gate.pushserv ce.model.cand date

 mport com.tw ter.fr gate.common.ut l.MRPushCopy
 mport com.tw ter.fr gate.common.ut l.MrPushCopyObjects
 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.pushserv ce.ut l.Cand dateUt l

case class Copy ds(
  pushCopy d: Opt on[ nt] = None,
  ntabCopy d: Opt on[ nt] = None,
  aggregat on d: Opt on[Str ng] = None)

tra  Copy nfo {
  self: PushCand date =>

   mport com.tw ter.fr gate.data_p pel ne.common.Fr gateNot f cat onUt l._

  def getPushCopy: Opt on[MRPushCopy] =
    pushCopy d match {
      case So (pushCopy d) => MrPushCopyObjects.getCopyFrom d(pushCopy d)
      case _ =>
        crt2PushCopy(
          commonRecType,
          Cand dateUt l.getSoc alContextAct onsFromCand date(self).s ze
        )
    }

  def pushCopy d: Opt on[ nt]

  def ntabCopy d: Opt on[ nt]

  def copyAggregat on d: Opt on[Str ng]
}

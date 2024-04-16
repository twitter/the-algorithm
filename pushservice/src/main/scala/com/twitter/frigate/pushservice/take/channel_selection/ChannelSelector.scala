package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.ut l.Future

abstract class ChannelSelector {

  // Returns a map of channel na , and t  cand dates that can be sent on that channel.
  def selectChannel(
    cand date: PushCand date
  ): Future[Seq[ChannelNa ]]

  def getSelectorNa (): Str ng
}

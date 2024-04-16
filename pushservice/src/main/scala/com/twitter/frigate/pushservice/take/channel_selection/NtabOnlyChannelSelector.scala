package com.tw ter.fr gate.pushserv ce.take

 mport com.tw ter.fr gate.pushserv ce.model.PushTypes.PushCand date
 mport com.tw ter.fr gate.thr ftscala.ChannelNa 
 mport com.tw ter.ut l.Future

class NtabOnlyChannelSelector extends ChannelSelector {
  val SELECTOR_NAME = "NtabOnlyChannelSelector"

  def getSelectorNa (): Str ng = SELECTOR_NAME

  // Returns a map of channel na , and t  cand dates that can be sent on that channel
  def selectChannel(
    cand date: PushCand date
  ): Future[Seq[ChannelNa ]] = {
    // C ck cand date channel el g ble (based on sett ng, push cap etc
    // Dec de wh ch cand date can be sent on what channel
    val channelNa : Future[ChannelNa ] = Future.value(ChannelNa .PushNtab)
    channelNa .map(channel => Seq(channel))
  }
}

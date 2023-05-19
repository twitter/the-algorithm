package com.twitter.frigate.pushservice.take

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.thriftscala.ChannelName
import com.twitter.util.Future

class NtabOnlyChannelSelector extends ChannelSelector {
  val SELECTOR_NAME = "NtabOnlyChannelSelector"

  def getSelectorName(): String = SELECTOR_NAME

  // Returns a map of channel name, and the candidates that can be sent on that channel
  def selectChannel(
    candidate: PushCandidate
  ): Future[Seq[ChannelName]] = {
    // Check candidate channel eligible (based on setting, push cap etc
    // Decide which candidate can be sent on what channel
    val channelName: Future[ChannelName] = Future.value(ChannelName.PushNtab)
    channelName.map(channel => Seq(channel))
  }
}

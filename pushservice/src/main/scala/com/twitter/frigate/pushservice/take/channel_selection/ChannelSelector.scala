package com.twitter.frigate.pushservice.take

import com.twitter.frigate.pushservice.model.PushTypes.PushCandidate
import com.twitter.frigate.thriftscala.ChannelName
import com.twitter.util.Future

abstract class ChannelSelector {

  // Returns a map of channel name, and the candidates that can be sent on that channel.
  def selectChannel(
    candidate: PushCandidate
  ): Future[Seq[ChannelName]]

  def getSelectorName(): String
}

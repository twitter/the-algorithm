package com.X.frigate.pushservice.take

import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.thriftscala.ChannelName
import com.X.util.Future

abstract class ChannelSelector {

  // Returns a map of channel name, and the candidates that can be sent on that channel.
  def selectChannel(
    candidate: PushCandidate
  ): Future[Seq[ChannelName]]

  def getSelectorName(): String
}

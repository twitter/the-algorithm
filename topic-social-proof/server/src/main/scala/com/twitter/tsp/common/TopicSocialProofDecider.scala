package com.twitter.tsp
package common

import com.twitter.decider.Decider
import com.twitter.decider.RandomRecipient
import com.twitter.decider.Recipient
import com.twitter.simclusters_v2.common.DeciderGateBuilderWithIdHashing
import javax.inject.Inject

case class TopicSocialProofDecider @Inject() (decider: Decider) {

  def isAvailable(feature: String, recipient: Option[Recipient]): Boolean = {
    decider.isAvailable(feature, recipient)
  }

  lazy val deciderGateBuilder = new DeciderGateBuilderWithIdHashing(decider)

  /**
   * When useRandomRecipient is set to false, the decider is either completely on or off.
   * When useRandomRecipient is set to true, the decider is on for the specified % of traffic.
   */
  def isAvailable(feature: String, useRandomRecipient: Boolean = true): Boolean = {
    if (useRandomRecipient) isAvailable(feature, Some(RandomRecipient))
    else isAvailable(feature, None)
  }
}

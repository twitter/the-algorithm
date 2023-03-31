package com.twitter.recos.decider

import com.twitter.decider.Decider
import com.twitter.decider.DeciderFactory
import com.twitter.decider.RandomRecipient
import com.twitter.decider.Recipient
import com.twitter.decider.SimpleRecipient
import com.twitter.recos.util.TeamUsers

case class GuestRecipient(id: Long) extends Recipient {
  override def isGuest: Boolean = true
}

sealed trait BaseDecider {
  def baseConfig: Option[String] = None

  def overlayConfig: Option[String] = None

  lazy val decider: Decider = DeciderFactory(baseConfig, overlayConfig)()

  def isAvailable(feature: String, recipient: Option[Recipient]): Boolean =
    decider.isAvailable(feature, recipient)

  def isAvailable(feature: String): Boolean = isAvailable(feature, None)

  def isAvailableExceptTeam(feature: String, id: Long, isUser: Boolean = true): Boolean = {
    if (isUser) TeamUsers.team.contains(id) || isAvailable(feature, Some(SimpleRecipient(id)))
    else isAvailable(feature, Some(GuestRecipient(id)))
  }
}

case class RecosDecider(env: String, cluster: String = "atla") extends BaseDecider {
  override val baseConfig = Some("/com/twitter/recos/config/decider.yml")
  override val overlayConfig = Some(
    s"/usr/local/config/overlays/recos/service/prod/$cluster/decider_overlay.yml"
  )

  def shouldCompute(id: Long, displayLocation: String, isUser: Boolean = true): Boolean = {
    isAvailableExceptTeam(RecosDecider.recosIncomingTraffic + "_" + displayLocation, id, isUser)
  }

  def shouldReturn(id: Long, displayLocation: String, isUser: Boolean = true): Boolean = {
    isAvailableExceptTeam(RecosDecider.recosShouldReturn + "_" + displayLocation, id, isUser)
  }

  def shouldDarkmode(experiment: String): Boolean = {
    isAvailable(RecosDecider.recosShouldDark + "_exp_" + experiment, None)
  }

  def shouldScribe(id: Long, isUser: Boolean = true): Boolean = {
    if (isUser) (id > 0) && isAvailableExceptTeam(RecosDecider.recosShouldScribe, id, isUser)
    else false // TODO: define the behavior for guests
  }

  def shouldWriteMomentCapsuleOpenEdge(): Boolean = {
    val capsuleOpenDecider = env match {
      case "prod" => RecosDecider.recosShouldWriteMomentCapsuleOpenEdge
      case _ => RecosDecider.recosShouldWriteMomentCapsuleOpenEdge + RecosDecider.testSuffix
    }

    isAvailable(capsuleOpenDecider, Some(RandomRecipient))
  }
}

object RecosDecider {
  val testSuffix = "_test"

  val recosIncomingTraffic: String = "recos_incoming_traffic"
  val recosShouldReturn: String = "recos_should_return"
  val recosShouldDark: String = "recos_should_dark"
  val recosRealtimeBlacklist: String = "recos_realtime_blacklist"
  val recosRealtimeDeveloperlist: String = "recos_realtime_developerlist"
  val recosShouldScribe: String = "recos_should_scribe"
  val recosShouldWriteMomentCapsuleOpenEdge: String = "recos_should_write_moment_capsule_open_edge"
}

trait GraphDecider extends BaseDecider {
  val graphNamePrefix: String

  override val baseConfig = Some("/com/twitter/recos/config/decider.yml")
  override val overlayConfig = Some(
    "/usr/local/config/overlays/recos/service/prod/atla/decider_overlay.yml"
  )
}

case class UserTweetEntityGraphDecider() extends GraphDecider {
  override val graphNamePrefix: String = "user_tweet_entity_graph"

  def tweetSocialProof: Boolean = {
    isAvailable("user_tweet_entity_graph_tweet_social_proof")
  }

  def entitySocialProof: Boolean = {
    isAvailable("user_tweet_entity_graph_entity_social_proof")
  }

}

case class UserUserGraphDecider() extends GraphDecider {
  override val graphNamePrefix: String = "user_user_graph"
}

case class UserTweetGraphDecider(env: String, dc: String) extends GraphDecider {
  override val graphNamePrefix: String = "user-tweet-graph"

  override val baseConfig = Some("/com/twitter/recos/config/user-tweet-graph_decider.yml")
  override val overlayConfig = Some(
    s"/usr/local/config/overlays/user-tweet-graph/user-tweet-graph/$env/$dc/decider_overlay.yml"
  )
}

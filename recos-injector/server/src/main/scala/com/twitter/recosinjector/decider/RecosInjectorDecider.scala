package com.twitter.recosinjector.decider

import com.twitter.decider.{Decider, DeciderFactory, RandomRecipient, Recipient}

case class RecosInjectorDecider(isProd: Boolean, dataCenter: String) {
  lazy val decider: Decider = DeciderFactory(
    Some("config/decider.yml"),
    Some(getOverlayPath(isProd, dataCenter))
  )()

  private def getOverlayPath(isProd: Boolean, dataCenter: String): String = {
    if (isProd) {
      s"/usr/local/config/overlays/recos-injector/recos-injector/prod/$dataCenter/decider_overlay.yml"
    } else {
      s"/usr/local/config/overlays/recos-injector/recos-injector/staging/$dataCenter/decider_overlay.yml"
    }
  }

  def getDecider: Decider = decider

  def isAvailable(feature: String, recipient: Option[Recipient]): Boolean = {
    decider.isAvailable(feature, recipient)
  }

  def isAvailable(feature: String): Boolean = isAvailable(feature, Some(RandomRecipient))
}

object RecosInjectorDeciderConstants {
  val TweetEventTransformerUserTweetEntityEdgesDecider =
    "tweet_event_transformer_user_tweet_entity_edges"
  val EnableEmitTweetEdgeFromReply = "enable_emit_tweet_edge_from_reply"
  val EnableUnfavoriteEdge = "enable_unfavorite_edge"
}

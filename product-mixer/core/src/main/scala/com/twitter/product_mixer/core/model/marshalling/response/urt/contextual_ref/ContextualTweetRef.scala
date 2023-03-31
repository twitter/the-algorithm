package com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref

case class ContextualTweetRef(
  id: Long,
  hydrationContext: Option[TweetHydrationContext])

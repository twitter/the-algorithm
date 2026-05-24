package com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.SocialContext

case class ModuleConversationMetadata(
  allTweetIds: Option[Seq[Long]],
  socialContext: Option[SocialContext],
  enableDeduplication: Option[Boolean])

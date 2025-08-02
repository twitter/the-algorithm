package com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.SocialContetwittert

case class ModuleConversationMetadata(
  allTweetIds: Option[Seq[Long]],
  socialContetwittert: Option[SocialContetwittert],
  enableDeduplication: Option[Boolean])

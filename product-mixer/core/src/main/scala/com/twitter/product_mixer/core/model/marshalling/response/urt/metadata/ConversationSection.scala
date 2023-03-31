package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait ConversationSection

case object HighQuality extends ConversationSection
case object LowQuality extends ConversationSection
case object AbusiveQuality extends ConversationSection
case object RelatedTweet extends ConversationSection

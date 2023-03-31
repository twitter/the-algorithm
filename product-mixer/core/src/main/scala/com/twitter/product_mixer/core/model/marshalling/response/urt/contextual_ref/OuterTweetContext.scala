package com.twitter.product_mixer.core.model.marshalling.response.urt.contextual_ref

sealed trait OuterTweetContext

case class QuoteTweetId(id: Long) extends OuterTweetContext
case class RetweetId(id: Long) extends OuterTweetContext

package com.twitter.product_mixer.core.model.marshalling.response.urt.metadata

sealed trait ReplyPinState

object PinnedReplyPinState extends ReplyPinState
object PinnableReplyPinState extends ReplyPinState
object NotPinnableReplyPinState extends ReplyPinState

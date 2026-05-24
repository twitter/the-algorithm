package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

sealed trait MessageActionType

case object FollowAllMessageActionType extends MessageActionType

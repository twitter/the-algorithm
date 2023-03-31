package com.twitter.product_mixer.core.model.marshalling.response.urt.item.message

case class UserFacepile(
  userIds: Seq[Long],
  featuredUserIds: Seq[Long],
  action: Option[MessageTextAction],
  actionType: Option[MessageActionType],
  displaysFeaturingText: Option[Boolean],
  displayType: Option[UserFacepileDisplayType])

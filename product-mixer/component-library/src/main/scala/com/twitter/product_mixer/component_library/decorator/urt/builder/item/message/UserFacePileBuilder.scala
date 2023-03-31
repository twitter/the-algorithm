package com.twitter.product_mixer.component_library.decorator.urt.builder.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageActionType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.MessageTextAction
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.UserFacepile
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.UserFacepileDisplayType

case class UserFacePileBuilder(
  userIds: Seq[Long],
  featuredUserIds: Seq[Long],
  action: Option[MessageTextAction],
  actionType: Option[MessageActionType],
  displaysFeaturingText: Option[Boolean],
  displayType: Option[UserFacepileDisplayType]) {

  def apply(): UserFacepile = UserFacepile(
    userIds = userIds,
    featuredUserIds = featuredUserIds,
    action = action,
    actionType = actionType,
    displaysFeaturingText = displaysFeaturingText,
    displayType = displayType
  )
}

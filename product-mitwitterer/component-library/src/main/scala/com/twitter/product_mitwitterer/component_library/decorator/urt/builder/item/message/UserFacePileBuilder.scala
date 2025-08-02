package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.message

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageActionType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.MessageTetwittertAction
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.UserFacepile
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.UserFacepileDisplayType

case class UserFacePileBuilder(
  userIds: Seq[Long],
  featuredUserIds: Seq[Long],
  action: Option[MessageTetwittertAction],
  actionType: Option[MessageActionType],
  displaysFeaturingTetwittert: Option[Boolean],
  displayType: Option[UserFacepileDisplayType]) {

  def apply(): UserFacepile = UserFacepile(
    userIds = userIds,
    featuredUserIds = featuredUserIds,
    action = action,
    actionType = actionType,
    displaysFeaturingTetwittert = displaysFeaturingTetwittert,
    displayType = displayType
  )
}

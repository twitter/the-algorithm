package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.message.UserFacepile
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UserFacepileMarshaller @Inject() (
  messageActionTypeMarshaller: MessageActionTypeMarshaller,
  messageTetwittertActionMarshaller: MessageTetwittertActionMarshaller,
  userFacepileDisplayTypeMarshaller: UserFacepileDisplayTypeMarshaller) {

  def apply(userFacepile: UserFacepile): urt.UserFacepile =
    urt.UserFacepile(
      userIds = userFacepile.userIds,
      featuredUserIds = userFacepile.featuredUserIds,
      action = userFacepile.action.map(messageTetwittertActionMarshaller(_)),
      actionType = userFacepile.actionType.map(messageActionTypeMarshaller(_)),
      displaysFeaturingTetwittert = userFacepile.displaysFeaturingTetwittert,
      displayType = userFacepile.displayType.map(userFacepileDisplayTypeMarshaller(_))
    )
}

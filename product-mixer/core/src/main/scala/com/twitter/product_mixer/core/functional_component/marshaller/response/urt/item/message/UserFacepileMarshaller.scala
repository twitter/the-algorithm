package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.UserFacepile
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserFacepileMarshaller @Inject() (
  messageActionTypeMarshaller: MessageActionTypeMarshaller,
  messageTextActionMarshaller: MessageTextActionMarshaller,
  userFacepileDisplayTypeMarshaller: UserFacepileDisplayTypeMarshaller) {

  def apply(userFacepile: UserFacepile): urt.UserFacepile =
    urt.UserFacepile(
      userIds = userFacepile.userIds,
      featuredUserIds = userFacepile.featuredUserIds,
      action = userFacepile.action.map(messageTextActionMarshaller(_)),
      actionType = userFacepile.actionType.map(messageActionTypeMarshaller(_)),
      displaysFeaturingText = userFacepile.displaysFeaturingText,
      displayType = userFacepile.displayType.map(userFacepileDisplayTypeMarshaller(_))
    )
}

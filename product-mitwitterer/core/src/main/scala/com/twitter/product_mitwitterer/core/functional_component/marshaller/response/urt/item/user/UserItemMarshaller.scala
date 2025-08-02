package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.user

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.SocialContetwittertMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted.PromotedMetadataMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class UserItemMarshaller @Inject() (
  userDisplayTypeMarshaller: UserDisplayTypeMarshaller,
  promotedMetadataMarshaller: PromotedMetadataMarshaller,
  socialContetwittertMarshaller: SocialContetwittertMarshaller,
  userReactiveTriggersMarshaller: UserReactiveTriggersMarshaller) {

  def apply(userItem: UserItem): urt.TimelineItemContent =
    urt.TimelineItemContent.User(
      urt.User(
        id = userItem.id,
        displayType = userDisplayTypeMarshaller(userItem.displayType),
        promotedMetadata = userItem.promotedMetadata.map(promotedMetadataMarshaller(_)),
        socialContetwittert = userItem.socialContetwittert.map(socialContetwittertMarshaller(_)),
        enableReactiveBlending = userItem.enableReactiveBlending,
        reactiveTriggers = userItem.reactiveTriggers.map(userReactiveTriggersMarshaller(_))
      )
    )
}

package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata.SocialContextMarshaller
import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted.PromotedMetadataMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserItem
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserItemMarshaller @Inject() (
  userDisplayTypeMarshaller: UserDisplayTypeMarshaller,
  promotedMetadataMarshaller: PromotedMetadataMarshaller,
  socialContextMarshaller: SocialContextMarshaller,
  userReactiveTriggersMarshaller: UserReactiveTriggersMarshaller) {

  def apply(userItem: UserItem): urt.TimelineItemContent =
    urt.TimelineItemContent.User(
      urt.User(
        id = userItem.id,
        displayType = userDisplayTypeMarshaller(userItem.displayType),
        promotedMetadata = userItem.promotedMetadata.map(promotedMetadataMarshaller(_)),
        socialContext = userItem.socialContext.map(socialContextMarshaller(_)),
        enableReactiveBlending = userItem.enableReactiveBlending,
        reactiveTriggers = userItem.reactiveTriggers.map(userReactiveTriggersMarshaller(_))
      )
    )
}

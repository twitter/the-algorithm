package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.reaction.TimelineReactionMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserReactiveTriggers
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserReactiveTriggersMarshaller @Inject() (
  timelineReactionMarshaller: TimelineReactionMarshaller) {

  def apply(userReactiveTriggers: UserReactiveTriggers): urt.UserReactiveTriggers = {
    urt.UserReactiveTriggers(
      onFollow = userReactiveTriggers.onFollow.map(timelineReactionMarshaller(_)))
  }
}

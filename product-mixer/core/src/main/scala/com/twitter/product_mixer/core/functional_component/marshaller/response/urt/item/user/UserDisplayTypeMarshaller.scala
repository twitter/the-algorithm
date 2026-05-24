package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.user

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.PendingFollowUser
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.User
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserDetailed
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.user.UserDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDisplayTypeMarshaller @Inject() () {

  def apply(userDisplayType: UserDisplayType): urt.UserDisplayType =
    userDisplayType match {
      case User => urt.UserDisplayType.User
      case UserDetailed => urt.UserDisplayType.UserDetailed
      case PendingFollowUser => urt.UserDisplayType.PendingFollowUser
    }
}

package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.user

import com.X.product_mixer.core.model.marshalling.response.urt.item.user.PendingFollowUser
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.User
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.UserDetailed
import com.X.product_mixer.core.model.marshalling.response.urt.item.user.UserDisplayType
import com.X.timelines.render.{thriftscala => urt}
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

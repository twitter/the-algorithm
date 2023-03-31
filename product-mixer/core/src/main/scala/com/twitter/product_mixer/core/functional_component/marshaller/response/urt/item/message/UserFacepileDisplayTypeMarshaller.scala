package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.item.message

import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.LargeUserFacepileDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.CompactUserFacepileDisplayType
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.message.UserFacepileDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserFacepileDisplayTypeMarshaller @Inject() () {

  def apply(userFacepileDisplayType: UserFacepileDisplayType): urt.UserFacepileDisplayType =
    userFacepileDisplayType match {
      case LargeUserFacepileDisplayType => urt.UserFacepileDisplayType.Large
      case CompactUserFacepileDisplayType => urt.UserFacepileDisplayType.Compact
    }
}

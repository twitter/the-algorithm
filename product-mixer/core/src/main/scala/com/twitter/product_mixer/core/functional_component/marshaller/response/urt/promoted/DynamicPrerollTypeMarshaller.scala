package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.promoted

import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.Amplify
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.DynamicPrerollType
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.LiveTvEvent
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.Marketplace
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DynamicPrerollTypeMarshaller @Inject() () {

  def apply(dynamicPrerollType: DynamicPrerollType): urt.DynamicPrerollType =
    dynamicPrerollType match {
      case Amplify => urt.DynamicPrerollType.Amplify
      case Marketplace => urt.DynamicPrerollType.Marketplace
      case LiveTvEvent => urt.DynamicPrerollType.LiveTvEvent
    }
}

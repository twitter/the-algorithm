package com.twitter.product_mixer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mixer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BadgeMarshaller @Inject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(badge: Badge): urt.Badge = urt.Badge(
    text = badge.text,
    textColorName = badge.textColorName.map(rosettaColorMarshaller(_)),
    backgroundColorName = badge.backgroundColorName.map(rosettaColorMarshaller(_))
  )
}

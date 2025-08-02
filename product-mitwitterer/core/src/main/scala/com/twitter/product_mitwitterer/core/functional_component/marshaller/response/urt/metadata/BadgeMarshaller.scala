package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color.RosettaColorMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class BadgeMarshaller @Inject() (
  rosettaColorMarshaller: RosettaColorMarshaller) {

  def apply(badge: Badge): urt.Badge = urt.Badge(
    tetwittert = badge.tetwittert,
    tetwittertColorName = badge.tetwittertColorName.map(rosettaColorMarshaller(_)),
    backgroundColorName = badge.backgroundColorName.map(rosettaColorMarshaller(_))
  )
}

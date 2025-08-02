package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CenterCoverHalfCoverDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverHalfCoverDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCoverDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HalfCoverDisplayTypeMarshaller @Inject() () {

  def apply(halfCoverDisplayType: HalfCoverDisplayType): urt.HalfCoverDisplayType =
    halfCoverDisplayType match {
      case CenterCoverHalfCoverDisplayType => urt.HalfCoverDisplayType.CenterCover
      case CoverHalfCoverDisplayType => urt.HalfCoverDisplayType.Cover
    }
}

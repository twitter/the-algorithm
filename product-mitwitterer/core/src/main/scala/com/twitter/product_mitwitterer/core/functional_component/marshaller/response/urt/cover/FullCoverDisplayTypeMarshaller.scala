package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverFullCoverDisplayType
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCoverDisplayType
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FullCoverDisplayTypeMarshaller @Inject() () {

  def apply(halfCoverDisplayType: FullCoverDisplayType): urt.FullCoverDisplayType =
    halfCoverDisplayType match {
      case CoverFullCoverDisplayType => urt.FullCoverDisplayType.Cover
    }
}

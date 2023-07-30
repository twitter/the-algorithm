package com.X.product_mixer.core.functional_component.marshaller.response.urt.cover

import com.X.product_mixer.core.model.marshalling.response.urt.cover.CenterCoverHalfCoverDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.cover.CoverHalfCoverDisplayType
import com.X.product_mixer.core.model.marshalling.response.urt.cover.HalfCoverDisplayType
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HalfCoverDisplayTypeMarshaller @Inject() () {

  def apply(halfCoverDisplayType: HalfCoverDisplayType): urt.HalfCoverDisplayType =
    halfCoverDisplayType match {
      case CenterCoverHalfCoverDisplayType => urt.HalfCoverDisplayType.CenterCover
      case CoverHalfCoverDisplayType => urt.HalfCoverDisplayType.Cover
    }
}

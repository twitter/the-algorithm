package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.CoverContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCoverContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCoverContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CoverContentMarshaller @Inject() (
  fullCoverContentMarshaller: FullCoverContentMarshaller,
  halfCoverContentMarshaller: HalfCoverContentMarshaller) {

  def apply(coverContent: CoverContent): urt.Cover = coverContent match {
    case fullCover: FullCoverContent => fullCoverContentMarshaller(fullCover)
    case halfCover: HalfCoverContent => halfCoverContentMarshaller(halfCover)
  }
}

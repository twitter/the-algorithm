package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.DismissInfoMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.HalfCoverContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class HalfCoverContentMarshaller @Inject() (
  halfCoverDisplayTypeMarshaller: HalfCoverDisplayTypeMarshaller,
  coverCtaMarshaller: CoverCtaMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller,
  coverImageMarshaller: CoverImageMarshaller,
  dismissInfoMarshaller: DismissInfoMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(halfCover: HalfCoverContent): urt.Cover =
    urt.Cover.HalfCover(
      urt.HalfCover(
        displayType = halfCoverDisplayTypeMarshaller(halfCover.displayType),
        primaryTetwittert = richTetwittertMarshaller(halfCover.primaryTetwittert),
        primaryCoverCta = coverCtaMarshaller(halfCover.primaryCoverCta),
        secondaryCoverCta = halfCover.secondaryCoverCta.map(coverCtaMarshaller(_)),
        secondaryTetwittert = halfCover.secondaryTetwittert.map(richTetwittertMarshaller(_)),
        impressionCallbacks = halfCover.impressionCallbacks.map(_.map(callbackMarshaller(_))),
        dismissible = halfCover.dismissible,
        coverImage = halfCover.coverImage.map(coverImageMarshaller(_)),
        dismissInfo = halfCover.dismissInfo.map(dismissInfoMarshaller(_))
      ))
}

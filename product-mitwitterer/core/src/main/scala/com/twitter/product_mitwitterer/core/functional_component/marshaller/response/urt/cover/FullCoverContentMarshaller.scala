package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.cover

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.CallbackMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.DismissInfoMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageDisplayTypeMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.ImageVariantMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.cover.FullCoverContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class FullCoverContentMarshaller @Inject() (
  fullCoverDisplayTypeMarshaller: FullCoverDisplayTypeMarshaller,
  coverCtaMarshaller: CoverCtaMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller,
  imageVariantMarshaller: ImageVariantMarshaller,
  dismissInfoMarshaller: DismissInfoMarshaller,
  imageDisplayTypeMarshaller: ImageDisplayTypeMarshaller,
  callbackMarshaller: CallbackMarshaller) {

  def apply(fullCover: FullCoverContent): urt.Cover =
    urt.Cover.FullCover(
      urt.FullCover(
        displayType = fullCoverDisplayTypeMarshaller(fullCover.displayType),
        primaryTetwittert = richTetwittertMarshaller(fullCover.primaryTetwittert),
        primaryCoverCta = coverCtaMarshaller(fullCover.primaryCoverCta),
        secondaryCoverCta = fullCover.secondaryCoverCta.map(coverCtaMarshaller(_)),
        secondaryTetwittert = fullCover.secondaryTetwittert.map(richTetwittertMarshaller(_)),
        image = fullCover.imageVariant.map(imageVariantMarshaller(_)),
        details = fullCover.details.map(richTetwittertMarshaller(_)),
        dismissInfo = fullCover.dismissInfo.map(dismissInfoMarshaller(_)),
        imageDisplayType = fullCover.imageDisplayType.map(imageDisplayTypeMarshaller(_)),
        impressionCallbacks = fullCover.impressionCallbacks.map(_.map(callbackMarshaller(_)))
      ))
}

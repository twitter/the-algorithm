package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.tile

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.button.CtaButtonMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.CallToActionTileContent
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CallToActionTileContentMarshaller @Inject() (
  ctaButtonMarshaller: CtaButtonMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(callToActionTileContent: CallToActionTileContent): urt.TileContentCallToAction =
    urt.TileContentCallToAction(
      tetwittert = callToActionTileContent.tetwittert,
      richTetwittert = callToActionTileContent.richTetwittert.map(richTetwittertMarshaller(_)),
      ctaButton = callToActionTileContent.ctaButton.map(ctaButtonMarshaller(_))
    )
}

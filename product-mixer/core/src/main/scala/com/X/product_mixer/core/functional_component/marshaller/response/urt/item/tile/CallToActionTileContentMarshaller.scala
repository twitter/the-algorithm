package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.tile

import com.X.product_mixer.core.functional_component.marshaller.response.urt.button.CtaButtonMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.tile.CallToActionTileContent
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CallToActionTileContentMarshaller @Inject() (
  ctaButtonMarshaller: CtaButtonMarshaller,
  richTextMarshaller: RichTextMarshaller) {

  def apply(callToActionTileContent: CallToActionTileContent): urt.TileContentCallToAction =
    urt.TileContentCallToAction(
      text = callToActionTileContent.text,
      richText = callToActionTileContent.richText.map(richTextMarshaller(_)),
      ctaButton = callToActionTileContent.ctaButton.map(ctaButtonMarshaller(_))
    )
}

package com.X.product_mixer.core.functional_component.marshaller.response.urt.item.generic_summary_item

import com.X.product_mixer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.X.product_mixer.core.functional_component.marshaller.response.urt.richtext.RichTextMarshaller
import com.X.product_mixer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryContext
import com.X.timelines.render.{thriftscala => urt}
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenericSummaryContextMarshaller @Inject() (
  richTextMarshaller: RichTextMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller) {

  def apply(genericSummaryItemContext: GenericSummaryContext): urt.GenericSummaryContext =
    urt.GenericSummaryContext(
      text = richTextMarshaller(genericSummaryItemContext.text),
      icon = genericSummaryItemContext.icon.map(horizonIconMarshaller(_))
    )
}

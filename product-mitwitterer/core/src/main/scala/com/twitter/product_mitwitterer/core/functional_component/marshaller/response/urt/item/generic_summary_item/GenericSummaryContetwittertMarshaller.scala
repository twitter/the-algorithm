package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.generic_summary_item

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.icon.HorizonIconMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryContetwittert
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GenericSummaryContetwittertMarshaller @Inject() (
  richTetwittertMarshaller: RichTetwittertMarshaller,
  horizonIconMarshaller: HorizonIconMarshaller) {

  def apply(genericSummaryItemContetwittert: GenericSummaryContetwittert): urt.GenericSummaryContetwittert =
    urt.GenericSummaryContetwittert(
      tetwittert = richTetwittertMarshaller(genericSummaryItemContetwittert.tetwittert),
      icon = genericSummaryItemContetwittert.icon.map(horizonIconMarshaller(_))
    )
}

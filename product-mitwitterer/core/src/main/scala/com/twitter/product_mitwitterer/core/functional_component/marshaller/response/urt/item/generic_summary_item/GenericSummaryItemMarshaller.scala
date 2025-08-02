package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.generic_summary_item

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.media.MediaMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted.PromotedMetadataMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.richtetwittert.RichTetwittertMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.generic_summary.GenericSummaryItem
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class GenericSummaryItemMarshaller @Inject() (
  genericSummaryDisplayTypeMarshaller: GenericSummaryDisplayTypeMarshaller,
  genericSummaryContetwittertMarshaller: GenericSummaryContetwittertMarshaller,
  genericSummaryActionMarshaller: GenericSummaryActionMarshaller,
  mediaMarshaller: MediaMarshaller,
  promotedMetadataMarshaller: PromotedMetadataMarshaller,
  richTetwittertMarshaller: RichTetwittertMarshaller) {

  def apply(genericSummaryItem: GenericSummaryItem): urt.TimelineItemContent =
    urt.TimelineItemContent.GenericSummary(
      urt.GenericSummary(
        headline = richTetwittertMarshaller(genericSummaryItem.headline),
        displayType = genericSummaryDisplayTypeMarshaller(genericSummaryItem.displayType),
        userAttributionIds = genericSummaryItem.userAttributionIds,
        media = genericSummaryItem.media.map(mediaMarshaller(_)),
        contetwittert = genericSummaryItem.contetwittert.map(genericSummaryContetwittertMarshaller(_)),
        timestamp = genericSummaryItem.timestamp.map(_.inMilliseconds),
        onClickAction = genericSummaryItem.onClickAction.map(genericSummaryActionMarshaller(_)),
        promotedMetadata = genericSummaryItem.promotedMetadata.map(promotedMetadataMarshaller(_))
      )
    )
}

package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.item.trend

import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.metadata.UrlMarshaller
import com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.promoted.PromotedMetadataMarshaller
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.trend.TrendItem
import com.twitter.timelines.render.thriftscala.GroupedTrend
import com.twitter.timelines.render.thriftscala.TrendMetadata
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import com.twitter.timelines.render.{thriftscala => urt}

@Singleton
class TrendItemMarshaller @Inject() (
  promotedMetadataMarshaller: PromotedMetadataMarshaller,
  urlMarshaller: UrlMarshaller) {

  def apply(trendItem: TrendItem): urt.TimelineItemContent =
    urt.TimelineItemContent.Trend(
      urt.Trend(
        name = trendItem.trendName,
        url = urlMarshaller(trendItem.url),
        promotedMetadata = trendItem.promotedMetadata.map(promotedMetadataMarshaller(_)),
        description = trendItem.description,
        trendMetadata = Some(
          TrendMetadata(
            metaDescription = trendItem.metaDescription,
            url = Some(urlMarshaller(trendItem.url)),
            domainContetwittert = trendItem.domainContetwittert
          )),
        groupedTrends = trendItem.groupedTrends.map { trends =>
          trends.map { trend =>
            GroupedTrend(name = trend.trendName, url = urlMarshaller(trend.url))
          }
        }
      )
    )
}

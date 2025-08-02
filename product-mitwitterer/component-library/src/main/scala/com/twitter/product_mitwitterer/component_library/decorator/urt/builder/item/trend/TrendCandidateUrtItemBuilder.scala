package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.trend

import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.item.trend.TrendCandidateUrtItemBuilder.TrendsClientEventInfoElement
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendDescription
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendDomainContetwittert
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendGroupedTrends
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendNormalizedTrendName
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendTrendName
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendTweetCount
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.TrendUrl
import com.twitter.product_mitwitterer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.CandidateUrtEntryBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseClientEventInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.metadata.BaseFeedbackActionInfoBuilder
import com.twitter.product_mitwitterer.core.functional_component.decorator.urt.builder.promoted.BasePromotedMetadataBuilder
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.TimelineItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.trend.TrendItem
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery

object TrendCandidateUrtItemBuilder {
  final val TrendsClientEventInfoElement = "trend"
}

case class TrendCandidateUrtItemBuilder[Query <: PipelineQuery](
  trendMetaDescriptionBuilder: TrendMetaDescriptionBuilder[Query, UnifiedTrendCandidate],
  promotedMetadataBuilder: BasePromotedMetadataBuilder[Query, UnifiedTrendCandidate],
  clientEventInfoBuilder: BaseClientEventInfoBuilder[Query, UnifiedTrendCandidate],
  feedbackActionInfoBuilder: Option[BaseFeedbackActionInfoBuilder[Query, UnifiedTrendCandidate]] =
    None)
    etwittertends CandidateUrtEntryBuilder[Query, UnifiedTrendCandidate, TimelineItem] {

  override def apply(
    query: Query,
    candidate: UnifiedTrendCandidate,
    candidateFeatures: FeatureMap
  ): TimelineItem = {
    TrendItem(
      id = candidate.id,
      sortIndetwitter = None, // Sort indetwitteres are automatically set in the domain marshaller phase
      clientEventInfo = clientEventInfoBuilder(
        query = query,
        candidate = candidate,
        candidateFeatures = candidateFeatures,
        element = Some(TrendsClientEventInfoElement)
      ),
      feedbackActionInfo = None,
      normalizedTrendName = candidateFeatures.get(TrendNormalizedTrendName),
      trendName = candidateFeatures.get(TrendTrendName),
      url = candidateFeatures.get(TrendUrl),
      description = candidateFeatures.getOrElse(TrendDescription, None),
      metaDescription = trendMetaDescriptionBuilder(query, candidate, candidateFeatures),
      tweetCount = candidateFeatures.getOrElse(TrendTweetCount, None),
      domainContetwittert = candidateFeatures.getOrElse(TrendDomainContetwittert, None),
      promotedMetadata = promotedMetadataBuilder(
        query = query,
        candidate = candidate,
        candidateFeatures = candidateFeatures
      ),
      groupedTrends = candidateFeatures.getOrElse(TrendGroupedTrends, None)
    )
  }
}

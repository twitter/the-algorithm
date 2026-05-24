package com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend

import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendDescriptionFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendDisclosureTypeFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendIdFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendImpressionIdFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.PromotedTrendNameFeature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.decorator.urt.builder.promoted.BasePromotedMetadataBuilder
import com.twitter.product_mixer.core.model.common.UniversalNoun
import com.twitter.product_mixer.core.model.marshalling.response.urt.promoted.PromotedMetadata
import com.twitter.product_mixer.core.pipeline.PipelineQuery

object TrendPromotedMetadataBuilder
    extends BasePromotedMetadataBuilder[PipelineQuery, UniversalNoun[Any]] {

  override def apply(
    query: PipelineQuery,
    candidate: UniversalNoun[Any],
    candidateFeatures: FeatureMap
  ): Option[PromotedMetadata] = {
    // If a promoted trend name exists, then this is a promoted trend
    candidateFeatures.getOrElse(PromotedTrendNameFeature, None).map { promotedTrendName =>
      PromotedMetadata(
        // This is the current product behavior that advertiserId is always set to 0L.
        // Correct advertiser name comes from Trend's trendMetadata.metaDescription.
        advertiserId = 0L,
        disclosureType = candidateFeatures.getOrElse(PromotedTrendDisclosureTypeFeature, None),
        experimentValues = None,
        promotedTrendId = candidateFeatures.getOrElse(PromotedTrendIdFeature, None),
        promotedTrendName = Some(promotedTrendName),
        promotedTrendQueryTerm = None,
        adMetadataContainer = None,
        promotedTrendDescription =
          candidateFeatures.getOrElse(PromotedTrendDescriptionFeature, None),
        impressionString = candidateFeatures.getOrElse(PromotedTrendImpressionIdFeature, None),
        clickTrackingInfo = None
      )
    }
  }
}

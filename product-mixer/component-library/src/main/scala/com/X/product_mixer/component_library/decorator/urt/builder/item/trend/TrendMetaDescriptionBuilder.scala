package com.X.product_mixer.component_library.decorator.urt.builder.item.trend

import com.X.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.X.product_mixer.component_library.model.candidate.trends_events.PromotedTrendAdvertiserNameFeature
import com.X.product_mixer.component_library.model.candidate.trends_events.TrendTweetCount
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.model.common.UniversalNoun
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.trends.trending_content.util.CompactingNumberLocalizer

case class TrendMetaDescriptionBuilder[-Query <: PipelineQuery, -Candidate <: UniversalNoun[Any]](
  promotedByMetaDescriptionStr: Str[PipelineQuery, UniversalNoun[Any]],
  tweetCountMetaDescriptionStr: Str[PipelineQuery, UniversalNoun[Any]],
  compactingNumberLocalizer: CompactingNumberLocalizer) {

  def apply(
    query: Query,
    candidate: Candidate,
    candidateFeatures: FeatureMap
  ): Option[String] = {
    val promotedMetaDescription =
      candidateFeatures.getOrElse(PromotedTrendAdvertiserNameFeature, None).map { advertiserName =>
        promotedByMetaDescriptionStr(query, candidate, candidateFeatures).format(advertiserName)
      }

    val organicMetaDescription = candidateFeatures.getOrElse(TrendTweetCount, None).map {
      tweetCount =>
        val compactedTweetCount = compactingNumberLocalizer.localizeAndCompact(
          query.getLanguageCode
            .getOrElse("en"),
          tweetCount)
        tweetCountMetaDescriptionStr(query, candidate, candidateFeatures).format(
          compactedTweetCount)
    }

    promotedMetaDescription.orElse(organicMetaDescription)
  }
}

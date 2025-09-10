package com.twitter.home_mixer.product.for_you.response_transformer

import com.twitter.events.recos.{thriftscala => t}
import com.twitter.home_mixer.util.UrtUtil
import com.twitter.home_mixer.product.for_you.candidate_source.TrendCandidate
import com.twitter.product_mixer.component_library.model.candidate.trends_events._
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.product_mixer.core.model.marshalling.response.urt.item.trend.GroupedTrend

object KeywordTrendsFeatureTransformer extends CandidateFeatureTransformer[TrendCandidate] {
  override val identifier: TransformerIdentifier = TransformerIdentifier("KeywordTrends")

  override def features: Set[Feature[_, _]] = Set(
    TrendNormalizedNameFeature,
    TrendNameFeature,
    TrendUrlFeature,
    TrendDescriptionFeature,
    TrendTweetCountFeature,
    TrendDomainContextFeature,
    TrendGroupedTrendsFeature,
    PromotedTrendNameFeature,
    TrendRankFeature
  )

  override def transform(input: TrendCandidate): FeatureMap = {
    val trend: t.TrendCandidate = input.candidate

    FeatureMapBuilder()
      .add(TrendNameFeature, trend.trendName)
      .add(TrendDescriptionFeature, trend.context.flatMap(_.description))
      .add(TrendNormalizedNameFeature, trend.normalizedTrendName)
      .add(TrendUrlFeature, UrtUtil.transformUrl(trend.url))
      .add(TrendTweetCountFeature, trend.context.flatMap(_.tweetCount))
      .add(TrendDomainContextFeature, trend.domainContext)
      .add(TrendGroupedTrendsFeature, trend.relatedTrends.map(transformGroupedTrends))
      .add(PromotedTrendNameFeature, trend.promotedMetadata.map(_.name))
      .add(TrendRankFeature, input.rank)
      .build()
  }

  private def transformGroupedTrends(groupedTrends: Seq[t.RelatedTrend]): Seq[GroupedTrend] = {
    groupedTrends.map { trend =>
      GroupedTrend(
        trendName = trend.trendName,
        url = UrtUtil.transformUrl(trend.url)
      )
    }
  }
}

package com.twitter.home_mixer.functional_component.decorator.builder

import com.twitter.home_mixer.product.following.model.HomeMixerExternalStrings
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.trend.BaseTrendMetaDescriptionBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.stringcenter.Str
import com.twitter.product_mixer.component_library.model.candidate.trends_events.TrendTweetCountFeature
import com.twitter.product_mixer.component_library.model.candidate.trends_events.UnifiedTrendCandidate
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.product.guice.scope.ProductScoped
import com.twitter.trends.trending_content.util.CompactingNumberLocalizer
import com.twitter.stringcenter.client.StringCenter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class KeywordTrendMetaDescriptionBuilder @Inject() (
  externalStrings: HomeMixerExternalStrings,
  @ProductScoped stringCenterProvider: Provider[StringCenter])
    extends BaseTrendMetaDescriptionBuilder[PipelineQuery, UnifiedTrendCandidate] {

  private val stringCenter = stringCenterProvider.get()

  private val tweetCountStr = Str(
    text = externalStrings.KeywordTrendsTweetCountDescriptionString,
    stringCenter = stringCenter
  )

  private val compactingNumberLocalizer = new CompactingNumberLocalizer()

  def apply(
    query: PipelineQuery,
    candidate: UnifiedTrendCandidate,
    candidateFeatures: FeatureMap
  ): Option[String] = {
    // E.g. "23.4K posts"
    candidateFeatures.getOrElse(TrendTweetCountFeature, None).map { tweetCount =>
      val compactedTweetCount = compactingNumberLocalizer.localizeAndCompact(
        query.getLanguageCode
          .getOrElse("en"),
        tweetCount)
      tweetCountStr(query, candidate, candidateFeatures).format(compactedTweetCount)
    }
  }
}

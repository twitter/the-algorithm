package com.twitter.home_mixer.product.for_you.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.CurrentPinnedTweetFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BulkCandidateFeatureHydrator
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher
import com.twitter.strato.generated.client.timelines.pintweet.PinTweetFannedOutUserCacheClientColumn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPinnedTweetFeatureHydrator @Inject() (
  pinTweetFannedOutUserCacheClientColumn: PinTweetFannedOutUserCacheClientColumn)
    extends BulkCandidateFeatureHydrator[PipelineQuery, TweetCandidate] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("CurrentPinnedTweet")

  override val features: Set[Feature[_, _]] = Set(CurrentPinnedTweetFeature)

  private val fetcher: Fetcher[
    PinTweetFannedOutUserCacheClientColumn.Key,
    PinTweetFannedOutUserCacheClientColumn.View,
    PinTweetFannedOutUserCacheClientColumn.Value
  ] = pinTweetFannedOutUserCacheClientColumn.fetcher

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[Seq[FeatureMap]] = {
    val authorIds = candidates.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct

    Stitch
      .collectToTry {
        authorIds.map { authorId =>
          fetcher.fetch(authorId).map(_.v.map(data => authorId -> data.tweetId))
        }
      }.map { results =>
        val authorPinTweetMap = results.flatMap(_.toOption).flatten.toMap
        candidates.map { candidate =>
          val authorId = candidate.features.getOrElse(AuthorIdFeature, None).getOrElse(0L)
          val pinnedTweet = authorPinTweetMap.get(authorId)
          FeatureMap(CurrentPinnedTweetFeature, pinnedTweet)
        }
      }
  }
}

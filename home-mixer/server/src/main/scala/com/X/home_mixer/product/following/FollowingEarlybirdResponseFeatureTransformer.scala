package com.X.home_mixer.candidate_pipeline

import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.InReplyToTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.IsRetweetFeature
import com.X.home_mixer.model.HomeFeatures.SourceTweetIdFeature
import com.X.home_mixer.model.HomeFeatures.SourceUserIdFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.search.earlybird.{thriftscala => t}

object FollowingEarlybirdResponseFeatureTransformer
    extends CandidateFeatureTransformer[t.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("FollowingEarlybirdResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    InReplyToTweetIdFeature,
    IsRetweetFeature,
    SourceTweetIdFeature,
    SourceUserIdFeature,
  )

  override def transform(candidate: t.ThriftSearchResult): FeatureMap = FeatureMapBuilder()
    .add(AuthorIdFeature, candidate.tweetypieTweet.flatMap(_.coreData.map(_.userId)))
    .add(
      InReplyToTweetIdFeature,
      candidate.tweetypieTweet.flatMap(_.coreData.flatMap(_.reply.flatMap(_.inReplyToStatusId))))
    .add(IsRetweetFeature, candidate.metadata.exists(_.isRetweet.contains(true)))
    .add(SourceTweetIdFeature, candidate.sourceTweetypieTweet.map(_.id))
    .add(SourceUserIdFeature, candidate.sourceTweetypieTweet.flatMap(_.coreData.map(_.userId)))
    .build()
}

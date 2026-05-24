package com.twitter.home_mixer.product.scored_tweets.response_transformer.earlybird

import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.twitter.home_mixer.model.HomeFeatures.ServedTypeFeature
import com.twitter.product_mixer.component_library.feature.communities.CommunitiesSharedFeatures.CommunityIdFeature
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.search.earlybird.{thriftscala => t}

object ScoredTweetsCommunitiesResponseFeatureTransformer
    extends CandidateFeatureTransformer[t.ThriftSearchResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsEarlybirdCommunitiesResponse")

  private val candidateSourceFeatures = Set(
    FromInNetworkSourceFeature,
    ServedTypeFeature
  )

  override val features: Set[Feature[_, _]] =
    EarlybirdResponseTransformer.features ++ Set(CommunityIdFeature) ++ candidateSourceFeatures

  override def transform(candidate: t.ThriftSearchResult): FeatureMap = {
    val baseFeatures = EarlybirdResponseTransformer.transform(candidate)

    val communityIdOpt =
      candidate.tweetypieTweet.flatMap(_.communities.flatMap(_.communityIds.headOption))

    val features = FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, false)
      .add(ServedTypeFeature, hmt.ServedType.ForYouCommunity)
      .add(CommunityIdFeature, communityIdOpt)
      .build()

    baseFeatures ++ features
  }
}

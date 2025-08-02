package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer.earlybird

import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.twitter.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[eb.ThriftSearchResult] {
  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsEarlybirdInNetworkResponse")

  override val features: Set[Feature[_, _]] = EarlybirdResponseTransformer.features

  override def transform(candidate: eb.ThriftSearchResult): FeatureMap = {

    val baseFeatures = EarlybirdResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.RecycledTweet))
      .add(SuggestTypeFeature, Some(st.SuggestType.RecycledTweet))
      .build()

    baseFeatures ++ features
  }
}

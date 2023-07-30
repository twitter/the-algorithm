package com.X.home_mixer.product.scored_tweets.response_transformer.earlybird

import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.search.earlybird.{thriftscala => eb}
import com.X.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.X.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsEarlybirdInNetworkResponseFeatureTransformer
    extends CandidateFeatureTransformer[eb.ThriftSearchResult] {
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

package com.ExTwitter.home_mixer.product.scored_tweets.response_transformer.earlybird

import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.search.earlybird.{thriftscala => eb}
import com.ExTwitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}

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

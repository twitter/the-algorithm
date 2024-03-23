package com.ExTwitter.home_mixer.product.scored_tweets.response_transformer

import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.timelineranker.{thriftscala => tlr}
import com.ExTwitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsFrsResponseFeatureTransformer
    extends CandidateFeatureTransformer[tlr.CandidateTweet] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("ScoredTweetsFrsResponse")

  override val features: Set[Feature[_, _]] = TimelineRankerResponseTransformer.features

  override def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val baseFeatures = TimelineRankerResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.FrsTweet))
      .add(SuggestTypeFeature, Some(st.SuggestType.FrsTweet))
      .build()

    baseFeatures ++ features
  }
}

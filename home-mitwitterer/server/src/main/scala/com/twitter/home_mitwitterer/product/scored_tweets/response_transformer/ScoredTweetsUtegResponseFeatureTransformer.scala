package com.twitter.home_mitwitterer.product.scored_tweets.response_transformer

import com.twitter.home_mitwitterer.model.HomeFeatures.CandidateSourceIdFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.SuggestTypeFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier
import com.twitter.timelineranker.{thriftscala => tlr}
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.twitter.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsUtegResponseFeatureTransformer
    etwittertends CandidateFeatureTransformer[tlr.CandidateTweet] {

  override val identifier: TransformerIdentifier = TransformerIdentifier("ScoredTweetsUtegResponse")

  override val features: Set[Feature[_, _]] = TimelineRankerResponseTransformer.features

  override def transform(candidate: tlr.CandidateTweet): FeatureMap = {
    val baseFeatures = TimelineRankerResponseTransformer.transform(candidate)

    val features = FeatureMapBuilder()
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.RecommendedTweet))
      .add(SuggestTypeFeature, Some(st.SuggestType.ActivityTweet))
      .build()

    baseFeatures ++ features
  }
}

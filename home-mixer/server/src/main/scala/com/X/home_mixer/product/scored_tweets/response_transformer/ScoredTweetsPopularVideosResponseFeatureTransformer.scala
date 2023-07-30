package com.X.home_mixer.product.scored_tweets.response_transformer

import com.X.explore_ranker.{thriftscala => ert}
import com.X.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.X.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.X.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.X.home_mixer.model.HomeFeatures.HasVideoFeature
import com.X.home_mixer.model.HomeFeatures.IsRandomTweetFeature
import com.X.home_mixer.model.HomeFeatures.StreamToKafkaFeature
import com.X.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.X.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.X.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.X.timelineservice.suggests.{thriftscala => st}

object ScoredTweetsPopularVideosResponseFeatureTransformer
    extends CandidateFeatureTransformer[ert.ExploreTweetRecommendation] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsPopularVideosResponse")

  override val features: Set[Feature[_, _]] = Set(
    AuthorIdFeature,
    CandidateSourceIdFeature,
    FromInNetworkSourceFeature,
    HasVideoFeature,
    IsRandomTweetFeature,
    StreamToKafkaFeature,
    SuggestTypeFeature
  )

  override def transform(candidate: ert.ExploreTweetRecommendation): FeatureMap = {
    FeatureMapBuilder()
      .add(AuthorIdFeature, candidate.authorId)
      .add(CandidateSourceIdFeature, Some(cts.CandidateTweetSourceId.MediaTweet))
      .add(FromInNetworkSourceFeature, false)
      .add(HasVideoFeature, candidate.mediaType.contains(ert.MediaType.Video))
      .add(IsRandomTweetFeature, false)
      .add(StreamToKafkaFeature, true)
      .add(SuggestTypeFeature, Some(st.SuggestType.MediaTweet))
      .build()
  }
}

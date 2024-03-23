package com.ExTwitter.home_mixer.product.scored_tweets.response_transformer

import com.ExTwitter.explore_ranker.{thriftscala => ert}
import com.ExTwitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.CandidateSourceIdFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.FromInNetworkSourceFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.HasVideoFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.IsRandomTweetFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.StreamToKafkaFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.SuggestTypeFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.ExTwitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.ExTwitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.ExTwitter.timelineservice.suggests.{thriftscala => st}

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

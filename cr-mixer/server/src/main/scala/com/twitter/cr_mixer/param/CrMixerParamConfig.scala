package com.twitter.cr_mixer.param

import com.twitter.timelines.configapi.CompositeConfig
import com.twitter.timelines.configapi.FSName
import com.twitter.timelines.configapi.Param

object CrMixerParamConfig {

  lazy val config: CompositeConfig = new CompositeConfig(
    configs = Seq(
      AdsParams.config,
      BlenderParams.config,
      BypassInterleaveAndRankParams.config,
      RankerParams.config,
      ConsumerBasedWalsParams.config,
      ConsumerEmbeddingBasedCandidateGenerationParams.config,
      ConsumerEmbeddingBasedTripParams.config,
      ConsumerEmbeddingBasedTwHINParams.config,
      ConsumerEmbeddingBasedTwoTowerParams.config,
      ConsumersBasedUserAdGraphParams.config,
      ConsumersBasedUserTweetGraphParams.config,
      ConsumersBasedUserVideoGraphParams.config,
      CustomizedRetrievalBasedCandidateGenerationParams.config,
      CustomizedRetrievalBasedOfflineInterestedInParams.config,
      CustomizedRetrievalBasedFTROfflineInterestedInParams.config,
      CustomizedRetrievalBasedTwhinParams.config,
      EarlybirdFrsBasedCandidateGenerationParams.config,
      FrsParams.config,
      GlobalParams.config,
      InterestedInParams.config,
      ProducerBasedCandidateGenerationParams.config,
      ProducerBasedUserAdGraphParams.config,
      ProducerBasedUserTweetGraphParams.config,
      RecentFollowsParams.config,
      RecentNegativeSignalParams.config,
      RecentNotificationsParams.config,
      RecentOriginalTweetsParams.config,
      RecentReplyTweetsParams.config,
      RecentRetweetsParams.config,
      RecentTweetFavoritesParams.config,
      RelatedTweetGlobalParams.config,
      RelatedVideoTweetGlobalParams.config,
      RelatedTweetProducerBasedParams.config,
      RelatedTweetTweetBasedParams.config,
      RelatedVideoTweetTweetBasedParams.config,
      RealGraphInParams.config,
      RealGraphOonParams.config,
      RepeatedProfileVisitsParams.config,
      SimClustersANNParams.config,
      TopicTweetParams.config,
      TweetBasedCandidateGenerationParams.config,
      TweetBasedUserAdGraphParams.config,
      TweetBasedUserTweetGraphParams.config,
      TweetBasedUserVideoGraphParams.config,
      TweetSharesParams.config,
      TweetBasedTwHINParams.config,
      RealGraphOonParams.config,
      GoodTweetClickParams.config,
      GoodProfileClickParams.config,
      UtegTweetGlobalParams.config,
      VideoTweetFilterParams.config,
      VideoViewTweetsParams.config,
      UnifiedUSSSignalParams.config,
    ),
    simpleName = "CrMixerConfig"
  )

  val allParams: Seq[Param[_] with FSName] = {
    AdsParams.AllParams ++
      BlenderParams.AllParams ++
      BypassInterleaveAndRankParams.AllParams ++
      RankerParams.AllParams ++
      ConsumerBasedWalsParams.AllParams ++
      ConsumerEmbeddingBasedCandidateGenerationParams.AllParams ++
      ConsumerEmbeddingBasedTripParams.AllParams ++
      ConsumerEmbeddingBasedTwHINParams.AllParams ++
      ConsumerEmbeddingBasedTwoTowerParams.AllParams ++
      ConsumersBasedUserAdGraphParams.AllParams ++
      ConsumersBasedUserTweetGraphParams.AllParams ++
      ConsumersBasedUserVideoGraphParams.AllParams ++
      CustomizedRetrievalBasedCandidateGenerationParams.AllParams ++
      CustomizedRetrievalBasedOfflineInterestedInParams.AllParams ++
      CustomizedRetrievalBasedFTROfflineInterestedInParams.AllParams ++
      CustomizedRetrievalBasedTwhinParams.AllParams ++
      EarlybirdFrsBasedCandidateGenerationParams.AllParams ++
      FrsParams.AllParams ++
      GlobalParams.AllParams ++
      InterestedInParams.AllParams ++
      ProducerBasedCandidateGenerationParams.AllParams ++
      ProducerBasedUserAdGraphParams.AllParams ++
      ProducerBasedUserTweetGraphParams.AllParams ++
      RecentFollowsParams.AllParams ++
      RecentNegativeSignalParams.AllParams ++
      RecentNotificationsParams.AllParams ++
      RecentOriginalTweetsParams.AllParams ++
      RecentReplyTweetsParams.AllParams ++
      RecentRetweetsParams.AllParams ++
      RecentTweetFavoritesParams.AllParams ++
      RelatedTweetGlobalParams.AllParams ++
      RelatedVideoTweetGlobalParams.AllParams ++
      RelatedTweetProducerBasedParams.AllParams ++
      RelatedTweetTweetBasedParams.AllParams ++
      RelatedVideoTweetTweetBasedParams.AllParams ++
      RepeatedProfileVisitsParams.AllParams ++
      SimClustersANNParams.AllParams ++
      TopicTweetParams.AllParams ++
      TweetBasedCandidateGenerationParams.AllParams ++
      TweetBasedUserAdGraphParams.AllParams ++
      TweetBasedUserTweetGraphParams.AllParams ++
      TweetBasedUserVideoGraphParams.AllParams ++
      TweetSharesParams.AllParams ++
      TweetBasedTwHINParams.AllParams ++
      RealGraphOonParams.AllParams ++
      RealGraphInParams.AllParams ++
      GoodTweetClickParams.AllParams ++
      GoodProfileClickParams.AllParams ++
      UtegTweetGlobalParams.AllParams ++
      VideoTweetFilterParams.AllParams ++
      VideoViewTweetsParams.AllParams ++
      UnifiedUSSSignalParams.AllParams
  }
}

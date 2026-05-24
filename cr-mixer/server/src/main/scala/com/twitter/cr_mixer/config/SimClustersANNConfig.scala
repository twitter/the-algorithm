package com.twitter.cr_mixer.config

import com.twitter.conversions.DurationOps._
import com.twitter.cr_mixer.exception.InvalidSANNConfigException
import com.twitter.simclusters_v2.thriftscala.EmbeddingType
import com.twitter.simclustersann.thriftscala.ScoringAlgorithm
import com.twitter.simclustersann.thriftscala.{SimClustersANNConfig => ThriftSimClustersANNConfig}
import com.twitter.util.Duration

case class SimClustersANNConfig(
  maxNumResults: Int,
  minScore: Double,
  candidateEmbeddingType: EmbeddingType,
  maxTopTweetsPerCluster: Int,
  maxScanClusters: Int,
  maxTweetCandidateAge: Duration,
  minTweetCandidateAge: Duration,
  annAlgorithm: ScoringAlgorithm) {
  val toSANNConfigThrift: ThriftSimClustersANNConfig = ThriftSimClustersANNConfig(
    maxNumResults = maxNumResults,
    minScore = minScore,
    candidateEmbeddingType = candidateEmbeddingType,
    maxTopTweetsPerCluster = maxTopTweetsPerCluster,
    maxScanClusters = maxScanClusters,
    maxTweetCandidateAgeHours = maxTweetCandidateAge.inHours,
    minTweetCandidateAgeHours = minTweetCandidateAge.inHours,
    annAlgorithm = annAlgorithm,
  )
}

object SimClustersANNConfig {

  final val DefaultConfig = SimClustersANNConfig(
    maxNumResults = 200,
    minScore = 0.0,
    candidateEmbeddingType = EmbeddingType.LogFavBasedTweet,
    maxTopTweetsPerCluster = 800,
    maxScanClusters = 50,
    maxTweetCandidateAge = 24.hours,
    minTweetCandidateAge = 0.hours,
    annAlgorithm = ScoringAlgorithm.CosineSimilarity,
  )

  /*
  SimClustersANNConfigId: String
  Format: Prod - “EmbeddingType_ModelVersion_Default”
  Format: Experiment - “EmbeddingType_ModelVersion_Date_Two-Digit-Serial-Number”. Date : YYYYMMDD
   */

  private val FavBasedProducer_Model20m145k2020_Default = DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val FavBasedProducer_Model20m145k2020_20220617_06 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val FavBasedProducer_Model20m145k2020_20220801 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val FavBasedProducer_Model20m145k2020_20220810 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val FavBasedProducer_Model20m145k2020_20220818 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val FavBasedProducer_Model20m145k2020_20220819 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val FavBasedProducer_Model20m145k2020_20221221 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val FavBasedProducer_Model20m145k2020_20221220 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default = DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220617_06 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220801 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220810 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220818 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220819 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221221 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )
  // SANN-4 config
  private val LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221220 =
    LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  private val UnfilteredUserInterestedIn_Model20m145k2020_Default = DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val UnfilteredUserInterestedIn_Model20m145k2020_20220617_06 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20220801 =
    UnfilteredUserInterestedIn_Model20m145k2020_20220617_06.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20220810 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20220818 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20220819 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20221221 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val UnfilteredUserInterestedIn_Model20m145k2020_20221220 =
    UnfilteredUserInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default = DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220617_06 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220801 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220810 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220818 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220819 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221221 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221220 =
    LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default =
    DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220617_06 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220801 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220810 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220818 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220819 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221221 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221220 =
    LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  private val UserNextInterestedIn_Model20m145k2020_Default = DefaultConfig.copy()

  // Chunnan's exp on maxTweetCandidateAgeDays 2
  private val UserNextInterestedIn_Model20m145k2020_20220617_06 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      maxTweetCandidateAge = 48.hours,
    )

  // Experimental SANN config
  private val UserNextInterestedIn_Model20m145k2020_20220801 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val UserNextInterestedIn_Model20m145k2020_20220810 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val UserNextInterestedIn_Model20m145k2020_20220818 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val UserNextInterestedIn_Model20m145k2020_20220819 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val UserNextInterestedIn_Model20m145k2020_20221221 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val UserNextInterestedIn_Model20m145k2020_20221220 =
    UserNextInterestedIn_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  // Vincent's experiment on using FollowBasedProducer as query embedding type for UserFollow
  private val FollowBasedProducer_Model20m145k2020_Default =
    FavBasedProducer_Model20m145k2020_Default.copy()

  // Experimental SANN config
  private val FollowBasedProducer_Model20m145k2020_20220801 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.VideoPlayBack50LogFavBasedTweet,
    )

  // SANN-1 config
  private val FollowBasedProducer_Model20m145k2020_20220810 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-2 config
  private val FollowBasedProducer_Model20m145k2020_20220818 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      maxNumResults = 100,
      candidateEmbeddingType = EmbeddingType.LogFavClickBasedAdsTweet,
      maxTweetCandidateAge = 175200.hours,
      maxTopTweetsPerCluster = 1600
    )

  // SANN-3 config
  private val FollowBasedProducer_Model20m145k2020_20220819 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.PushOpenLogFavBasedTweet,
    )

  // SANN-5 config
  private val FollowBasedProducer_Model20m145k2020_20221221 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedRealTimeTweet,
      maxTweetCandidateAge = 1.hours
    )

  // SANN-4 config
  private val FollowBasedProducer_Model20m145k2020_20221220 =
    FavBasedProducer_Model20m145k2020_Default.copy(
      candidateEmbeddingType = EmbeddingType.LogFavBasedEvergreenTweet,
      maxTweetCandidateAge = 48.hours
    )
  val DefaultConfigMappings: Map[String, SimClustersANNConfig] = Map(
    "FavBasedProducer_Model20m145k2020_Default" -> FavBasedProducer_Model20m145k2020_Default,
    "FavBasedProducer_Model20m145k2020_20220617_06" -> FavBasedProducer_Model20m145k2020_20220617_06,
    "FavBasedProducer_Model20m145k2020_20220801" -> FavBasedProducer_Model20m145k2020_20220801,
    "FavBasedProducer_Model20m145k2020_20220810" -> FavBasedProducer_Model20m145k2020_20220810,
    "FavBasedProducer_Model20m145k2020_20220818" -> FavBasedProducer_Model20m145k2020_20220818,
    "FavBasedProducer_Model20m145k2020_20220819" -> FavBasedProducer_Model20m145k2020_20220819,
    "FavBasedProducer_Model20m145k2020_20221221" -> FavBasedProducer_Model20m145k2020_20221221,
    "FavBasedProducer_Model20m145k2020_20221220" -> FavBasedProducer_Model20m145k2020_20221220,
    "FollowBasedProducer_Model20m145k2020_Default" -> FollowBasedProducer_Model20m145k2020_Default,
    "FollowBasedProducer_Model20m145k2020_20220801" -> FollowBasedProducer_Model20m145k2020_20220801,
    "FollowBasedProducer_Model20m145k2020_20220810" -> FollowBasedProducer_Model20m145k2020_20220810,
    "FollowBasedProducer_Model20m145k2020_20220818" -> FollowBasedProducer_Model20m145k2020_20220818,
    "FollowBasedProducer_Model20m145k2020_20220819" -> FollowBasedProducer_Model20m145k2020_20220819,
    "FollowBasedProducer_Model20m145k2020_20221221" -> FollowBasedProducer_Model20m145k2020_20221221,
    "FollowBasedProducer_Model20m145k2020_20221220" -> FollowBasedProducer_Model20m145k2020_20221220,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_Default,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220617_06" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220617_06,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220801" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220801,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220810" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220810,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220818" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220818,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220819" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20220819,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221221" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221221,
    "LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221220" -> LogFavLongestL2EmbeddingTweet_Model20m145k2020_20221220,
    "UnfilteredUserInterestedIn_Model20m145k2020_Default" -> UnfilteredUserInterestedIn_Model20m145k2020_Default,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220617_06" -> UnfilteredUserInterestedIn_Model20m145k2020_20220617_06,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220801" -> UnfilteredUserInterestedIn_Model20m145k2020_20220801,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220810" -> UnfilteredUserInterestedIn_Model20m145k2020_20220810,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220818" -> UnfilteredUserInterestedIn_Model20m145k2020_20220818,
    "UnfilteredUserInterestedIn_Model20m145k2020_20220819" -> UnfilteredUserInterestedIn_Model20m145k2020_20220819,
    "UnfilteredUserInterestedIn_Model20m145k2020_20221221" -> UnfilteredUserInterestedIn_Model20m145k2020_20221221,
    "UnfilteredUserInterestedIn_Model20m145k2020_20221220" -> UnfilteredUserInterestedIn_Model20m145k2020_20221220,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_Default,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220617_06" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220617_06,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220801" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220801,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220810" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220810,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220818" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220818,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220819" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20220819,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221221" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221221,
    "LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221220" -> LogFavBasedUserInterestedInFromAPE_Model20m145k2020_20221220,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_Default,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220617_06" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220617_06,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220801" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220801,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220810" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220810,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220818" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220818,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220819" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20220819,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221221" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221221,
    "LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221220" -> LogFavBasedUserInterestedLouvainMaxpoolingAddressBookFromIIAPE_Model20m145k2020_20221220,
    "UserNextInterestedIn_Model20m145k2020_Default" -> UserNextInterestedIn_Model20m145k2020_Default,
    "UserNextInterestedIn_Model20m145k2020_20220617_06" -> UserNextInterestedIn_Model20m145k2020_20220617_06,
    "UserNextInterestedIn_Model20m145k2020_20220801" -> UserNextInterestedIn_Model20m145k2020_20220801,
    "UserNextInterestedIn_Model20m145k2020_20220810" -> UserNextInterestedIn_Model20m145k2020_20220810,
    "UserNextInterestedIn_Model20m145k2020_20220818" -> UserNextInterestedIn_Model20m145k2020_20220818,
    "UserNextInterestedIn_Model20m145k2020_20220819" -> UserNextInterestedIn_Model20m145k2020_20220819,
    "UserNextInterestedIn_Model20m145k2020_20221221" -> UserNextInterestedIn_Model20m145k2020_20221221,
    "UserNextInterestedIn_Model20m145k2020_20221220" -> UserNextInterestedIn_Model20m145k2020_20221220,
  )

  def getConfig(
    embeddingType: String,
    modelVersion: String,
    id: String
  ): SimClustersANNConfig = {
    val configName = embeddingType + "_" + modelVersion + "_" + id
    DefaultConfigMappings.get(configName) match {
      case Some(config) => config
      case None =>
        throw InvalidSANNConfigException(s"Incorrect config id passed in for SANN $configName")
    }
  }
}

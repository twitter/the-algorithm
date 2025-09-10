package com.twitter.tweet_mixer.utils

import com.twitter.tweet_mixer.thriftscala.MetricTag
import com.twitter.tweet_mixer.thriftscala.ServedType

object CandidateSourceUtil {
  def getServedType(identiferPrefix: String, candidateSourceId: String): Option[ServedType] = {
    candidateSourceId.replace(identiferPrefix, "") match {
      case CandidatePipelineConstants.EarlybirdInNetwork => Some(ServedType.InNetwork)
      case CandidatePipelineConstants.DeepRetrievalTweetTweetSimilarity =>
        Some(ServedType.DeepRetrievalI2iEmb)
      case CandidatePipelineConstants.DeepRetrievalUserTweetSimilarity =>
        Some(ServedType.DeepRetrieval)
      case CandidatePipelineConstants.DeepRetrievalTweetTweetEmbeddingSimilarity =>
        Some(ServedType.DeepRetrievalI2iEmb)
      case CandidatePipelineConstants.ContentExplorationDRTweetTweet =>
        Some(ServedType.ContentExplorationDRI2i)
      case CandidatePipelineConstants.ContentExplorationDRTweetTweetTierTwo =>
        Some(ServedType.ContentExplorationDRI2iTier2)
      case CandidatePipelineConstants.ContentExplorationEvergreenDRTweetTweet =>
        Some(ServedType.ContentExplorationEvergreenDRI2i)
      case CandidatePipelineConstants.ContentExplorationDRUserTweet =>
        Some(ServedType.ContentExplorationDRI2i)
      case CandidatePipelineConstants.ContentExplorationDRUserTweetTierTwo =>
        Some(ServedType.ContentExplorationDRI2iTier2)
      case CandidatePipelineConstants.ContentExplorationEmbeddingSimilarity =>
        Some(ServedType.ContentExploration)
      case CandidatePipelineConstants.ContentExplorationEmbeddingSimilarityTierTwo =>
        Some(ServedType.ContentExplorationTier2)
      case CandidatePipelineConstants.ContentExplorationSimclusterColdPosts =>
        Some(ServedType.ContentExplorationSimclusterColdPosts)
      case CandidatePipelineConstants.UserInterestSummary =>
        Some(ServedType.UserInterestSummaryI2i)
      case CandidatePipelineConstants.EvergreenDRUserTweet =>
        Some(ServedType.EvergreenDRU2iHome)
      case CandidatePipelineConstants.EvergreenDRCrossBorderUserTweet =>
        Some(ServedType.EvergreenDRCrossBorderU2iHome)
      case CandidatePipelineConstants.Events => Some(ServedType.Trends)
      case CandidatePipelineConstants.Trends => Some(ServedType.Trends)
      case CandidatePipelineConstants.TrendsVideo => Some(ServedType.Trends)
      case CandidatePipelineConstants.UserLocation => Some(ServedType.Local)
      case CandidatePipelineConstants.UTEG => Some(ServedType.Uteg)
      case CandidatePipelineConstants.UTGTweetBased => Some(ServedType.Utg)
      case CandidatePipelineConstants.UTGProducerBased => Some(ServedType.Utg)
      case CandidatePipelineConstants.UTGExpansionTweetBased => Some(ServedType.Utg)
      case CandidatePipelineConstants.UVGTweetBased => Some(ServedType.Uvg)
      case CandidatePipelineConstants.UVGExpansionTweetBased => Some(ServedType.Uvg)
      case CandidatePipelineConstants.SimClustersTweetBased => Some(ServedType.Simclusters)
      case CandidatePipelineConstants.SimClustersProducerBased => Some(ServedType.Simclusters)
      case CandidatePipelineConstants.SimClustersInterestedIn => Some(ServedType.Simclusters)
      case CandidatePipelineConstants.TwhinConsumerBased => Some(ServedType.Twhin)
      case CandidatePipelineConstants.TwhinRebuildTweetSimilarity => Some(ServedType.Twhin)
      case CandidatePipelineConstants.TwhinTweetSimilarity => Some(ServedType.Twhin)
      case CandidatePipelineConstants.PopularGeoTweets => Some(ServedType.PopGeo)
      case CandidatePipelineConstants.LocalTweets => Some(ServedType.PopGeo)
      case CandidatePipelineConstants.PopularTopicTweets => Some(ServedType.PopTopic)
      case CandidatePipelineConstants.CertoTopicTweets => Some(ServedType.PopTopic)
      case CandidatePipelineConstants.MediaDeepRetrievalUserTweetSimilarity =>
        Some(ServedType.DeepRetrieval)
      case CandidatePipelineConstants.MediaDeepRetrievalTweetTweetSimilarity =>
        Some(ServedType.DeepRetrieval)
      case CandidatePipelineConstants.ContentEmbeddingAnn =>
        Some(ServedType.ContentAnn)
      case CandidatePipelineConstants.MediaEvergreenDeepRetrievalUserTweetSimilarity =>
        Some(ServedType.EvergreenDeepRetrieval)
      case CandidatePipelineConstants.TwitterClipV0Long =>
        Some(ServedType.TwitterClipV0Long)
      case CandidatePipelineConstants.TwitterClipV0Short =>
        Some(ServedType.TwitterClipV0Short)
      case CandidatePipelineConstants.SemanticVideo =>
        Some(ServedType.SemanticVideo)
      case CandidatePipelineConstants.MemeVideo =>
        Some(ServedType.MemeVideo)
      case CandidatePipelineConstants.RelatedCreator =>
        Some(ServedType.RelatedCreator)
      case CandidatePipelineConstants.RelatedNsfwCreator =>
        Some(ServedType.NsfwVideoContent)
      case CandidatePipelineConstants.SimClustersPromotedCreator =>
        Some(ServedType.PromotedCreator)
      case CandidatePipelineConstants.MediaPromotedCreatorDeepRetrievalUserTweetSimilarity =>
        Some(ServedType.PromotedCreator)
      case CandidatePipelineConstants.TwhinRebuildUserTweetSimilarity =>
        Some(ServedType.RebuildTwhin)
      case _ => None
    }
  }

  def getMetricTag(candidateSourceId: String): Seq[MetricTag] = {
    if (candidateSourceId.contains(CandidatePipelineConstants.PopularGeoTweets))
      Seq(MetricTag.PopGeo)
    else if (candidateSourceId.contains(CandidatePipelineConstants.LocalTweets))
      Seq(MetricTag.PopGeo)
    else if (candidateSourceId.contains(CandidatePipelineConstants.PopularTopicTweets))
      Seq(MetricTag.PopTopic)
    else if (candidateSourceId.contains(CandidatePipelineConstants.CertoTopicTweets))
      Seq(MetricTag.PopTopic)
    else if (candidateSourceId.contains(CandidatePipelineConstants.UserLocation))
      Seq(MetricTag.Local)
    else if (candidateSourceId.contains(CandidatePipelineConstants.Trends))
      Seq(MetricTag.Trends)
    else Seq.empty
  }
}

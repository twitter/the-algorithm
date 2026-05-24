package com.twitter.home_mixer.product.scored_tweets.response_transformer

import com.twitter.home_mixer.model.HomeFeatures._
import com.twitter.home_mixer.model.candidate_source._
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.model.common.identifier.TransformerIdentifier
import com.twitter.tsp.{thriftscala => tsp}
import com.twitter.tweet_mixer.{thriftscala => tmt}
import com.twitter.usersignalservice.{thriftscala => uss}

case class ScoredTweetsTweetMixerResponseFeatureTransformer(debugPrefix: String = "")
    extends CandidateFeatureTransformer[tmt.TweetResult] {

  override val identifier: TransformerIdentifier =
    TransformerIdentifier("ScoredTweetsTweetMixerResponse")

  override val features: Set[Feature[_, _]] = Set(
    FromInNetworkSourceFeature,
    ServedTypeFeature,
    TSPMetricTagFeature,
    TweetMixerScoreFeature,
    InReplyToTweetIdFeature,
    DebugStringFeature,
    SourceSignalFeature,
  )

  val FavoriteSignal = "Fav"
  val RetweetSignal = "Retweet"
  val ReplySignal = "Reply"
  val BookmarkSignal = "Bookmark"
  val ShareSignal = "Share"
  val TweetSignal = "Tweet"
  val VideoViewSignal = "VideoView"
  val ImmersiveVideoViewSignal = "ImmersiveVideoView"
  val SearcherRealtimeHistorySignal = "SearcherRealtimeHistory"
  val FollowSignal = "Follow"
  val ProfileVisitSignal = "ProfileVisit"
  val NotificationsSignal = "Notif"

  val UTEG = "UTEG"
  val PopGeo = "PopGeo"
  val PopTopic = "PopTopic"
  val Simclusters = "Simclusters"
  val Twhin = "Twhin"
  val UTG = "UTG"
  val UVG = "UVG"
  val InNetwork = "InNetwork"
  val DeepRetrieval = "DeepRetrieval"
  val DeepRetrievalI2i = "DeepRetrievalI2i"
  val ContentExploration = "Tier1ContentExploration"
  val ContentExplorationTier2 = "Tier2ContentExploration"
  val ContentExplorationDeepRetrievalI2i = "Tier1DrContentExploration"
  val ContentExplorationTier2DeepRetrievalI2i = "Tier2DrContentExploration"
  val ContentExplorationSimclusterColdPosts = "ContentExplorationSimclusterColdPosts"
  val EvergreenDeepRetrievalHome = "EvergreenDeepRetrievalHome"
  val EvergreenDeepRetrievalCrossBorderHome = "EvergreenDeepRetrievalCrossBorderHome"
  val UserInterestSummary = "UserInterestSummary"
  val ContentExplorationEvergreenDRI2i = "ContentExplorationEvergreenDRI2i"
  val Local = "Local"
  val Trends = "Trends"
  val TwitterClipV0Short = "TwitterClipV0Short"
  val TwitterClipV0Long = "TwitterClipV0Long"
  val SemanticVideo = "SemanticVideo"
  val RelatedCreator = "RelatedCreator"

  override def transform(candidate: tmt.TweetResult): FeatureMap = {
    val tweetMixerMetricTags: Seq[tmt.MetricTag] = candidate.metricTags.getOrElse(Seq.empty)
    val tspMetricTag = tweetMixerMetricTags
      .map(tweetMixerMetricTagToTspMetricTag)
      .filter(_.nonEmpty).map(_.get).toSet

    val servedType: hmt.ServedType = getServedType(candidate.tweetMetadata)
    val fromInNetwork = servedType match {
      case hmt.ServedType.ForYouInNetwork => true
      case _ => false
    }

    FeatureMapBuilder()
      .add(FromInNetworkSourceFeature, fromInNetwork)
      .add(ServedTypeFeature, servedType)
      .add(TSPMetricTagFeature, tspMetricTag)
      .add(TweetMixerScoreFeature, candidate.score)
      .add(DebugStringFeature, candidate.tweetMetadata.map(buildDebugString))
      .add(SourceSignalFeature, candidate.tweetMetadata.map(buildSourceSignal))
      .add(InReplyToTweetIdFeature, candidate.inReplyToTweetId)
      .build()
  }

  private def tweetMixerMetricTagToTspMetricTag(
    tweetMixerMetricTag: tmt.MetricTag
  ): Option[tsp.MetricTag] = tweetMixerMetricTag match {
    case tmt.MetricTag.TweetFavorite => Some(tsp.MetricTag.TweetFavorite)
    case tmt.MetricTag.Retweet => Some(tsp.MetricTag.Retweet)
    case tmt.MetricTag.PopGeo => None
    case _ => None
  }

  private def buildSourceSignal(metadata: tmt.TweetMetadata): SourceSignal = {
    SourceSignal(
      id = metadata.sourceSignalId.getOrElse(0L),
      signalType = metadata.signalType.flatMap(_.headOption.map(_.name)),
      signalEntity = metadata.signalEntity,
      authorId = metadata.authorId,
    )
  }

  private def buildDebugString(metadata: tmt.TweetMetadata): String = {
    val signalTypeStr = metadata.signalType
      .map { signalTypes =>
        signalTypes.map {
          case uss.SignalType.TweetFavorite => FavoriteSignal
          case uss.SignalType.Retweet => RetweetSignal
          case uss.SignalType.Reply => ReplySignal
          case uss.SignalType.TweetBookmarkV1 => BookmarkSignal
          case uss.SignalType.TweetShareV1 => ShareSignal
          case uss.SignalType.OriginalTweet => TweetSignal
          case uss.SignalType.VideoView90dQualityV1 | uss.SignalType.VideoView90dPlayback50V1 |
              uss.SignalType.VideoView90dQualityV1AllSurfaces =>
            VideoViewSignal
          case uss.SignalType.ImmersiveVideoQualityView => ImmersiveVideoViewSignal
          case uss.SignalType.SearcherRealtimeHistory => SearcherRealtimeHistorySignal
          case uss.SignalType.AccountFollow => FollowSignal
          case uss.SignalType.RepeatedProfileVisit180dMinVisit6V1 |
              uss.SignalType.RepeatedProfileVisit90dMinVisit6V1 |
              uss.SignalType.RepeatedProfileVisit14dMinVisit2V1 |
              uss.SignalType.RepeatedProfileVisit180dMinVisit6V1NoNegative |
              uss.SignalType.RepeatedProfileVisit90dMinVisit6V1NoNegative |
              uss.SignalType.RepeatedProfileVisit14dMinVisit2V1NoNegative =>
            ProfileVisitSignal
          case uss.SignalType.NotificationOpenAndClickV1 => NotificationsSignal
          case other => other.name
        }
      }.getOrElse(Seq.empty).mkString(",")

    val candidateTypeStr = metadata.servedType
      .map {
        case tmt.ServedType.Simclusters => Simclusters
        case tmt.ServedType.Twhin => Twhin
        case tmt.ServedType.Utg => UTG
        case tmt.ServedType.Uvg => UVG
        case tmt.ServedType.Uteg => UTEG
        case tmt.ServedType.InNetwork => InNetwork
        case tmt.ServedType.PopGeo => PopGeo
        case tmt.ServedType.PopTopic => PopTopic
        case tmt.ServedType.DeepRetrieval => DeepRetrieval
        case tmt.ServedType.DeepRetrievalI2iEmb => DeepRetrievalI2i
        case tmt.ServedType.ContentExploration => ContentExploration
        case tmt.ServedType.ContentExplorationTier2 => ContentExplorationTier2
        case tmt.ServedType.ContentExplorationDRI2i => ContentExplorationDeepRetrievalI2i
        case tmt.ServedType.ContentExplorationDRI2iTier2 => ContentExplorationTier2DeepRetrievalI2i
        case tmt.ServedType.ContentExplorationSimclusterColdPosts => ContentExplorationSimclusterColdPosts
        case tmt.ServedType.EvergreenDRU2iHome => EvergreenDeepRetrievalHome
        case tmt.ServedType.EvergreenDRCrossBorderU2iHome => EvergreenDeepRetrievalCrossBorderHome
        case tmt.ServedType.UserInterestSummaryI2i => UserInterestSummary
        case tmt.ServedType.ContentExplorationEvergreenDRI2i => ContentExplorationEvergreenDRI2i
        case tmt.ServedType.Local => Local
        case tmt.ServedType.Trends => Trends
        case tmt.ServedType.TwitterClipV0Short => TwitterClipV0Short
        case tmt.ServedType.TwitterClipV0Long => TwitterClipV0Long
        case tmt.ServedType.SemanticVideo => SemanticVideo
        case tmt.ServedType.RelatedCreator => RelatedCreator
        case _ => ""
      }.getOrElse("")

    s"${metadata.sourceSignalId.getOrElse(0L)} $signalTypeStr $candidateTypeStr $debugPrefix"
  }

  private def getServedType(metadata: Option[tmt.TweetMetadata]): hmt.ServedType = {
    metadata
      .flatMap {
        _.servedType
          .map {
            case tmt.ServedType.Simclusters => hmt.ServedType.ForYouSimclusters
            case tmt.ServedType.Twhin => hmt.ServedType.ForYouTwhin
            case tmt.ServedType.Utg => hmt.ServedType.ForYouUtg
            case tmt.ServedType.Uvg => hmt.ServedType.ForYouUvg
            case tmt.ServedType.Uteg => hmt.ServedType.ForYouUteg
            case tmt.ServedType.InNetwork => hmt.ServedType.ForYouInNetwork
            case tmt.ServedType.PopGeo => hmt.ServedType.ForYouPopularGeo
            case tmt.ServedType.PopTopic => hmt.ServedType.ForYouPopularTopic
            case tmt.ServedType.DeepRetrieval => hmt.ServedType.ForYouDeepRetrieval
            case tmt.ServedType.DeepRetrievalI2iEmb => hmt.ServedType.ForYouDeepRetrievalI2i
            case tmt.ServedType.ContentExploration => hmt.ServedType.ForYouContentExploration
            case tmt.ServedType.ContentExplorationTier2 =>
              hmt.ServedType.ForYouContentExplorationTier2
            case tmt.ServedType.ContentExplorationDRI2i =>
              hmt.ServedType.ForYouContentExplorationDeepRetrievalI2i
            case tmt.ServedType.ContentExplorationDRI2iTier2 =>
              hmt.ServedType.ForYouContentExplorationTier2DeepRetrievalI2i
            case tmt.ServedType.EvergreenDeepRetrieval =>
              hmt.ServedType.ForYouEvergreenDeepRetrieval
            case tmt.ServedType.EvergreenDRU2iHome =>
              hmt.ServedType.ForYouEvergreenDeepRetrievalHome
            case tmt.ServedType.EvergreenDRCrossBorderU2iHome =>
              hmt.ServedType.ForYouEvergreenDeepRetrievalCrossBorderHome
            case tmt.ServedType.UserInterestSummaryI2i =>
              hmt.ServedType.ForYouUserInterestSummary
            case tmt.ServedType.ContentExplorationEvergreenDRI2i =>
              hmt.ServedType.ForYouContentExplorationEvergreenDeepRetrievalI2i
            case tmt.ServedType.ContentExplorationSimclusterColdPosts =>
              hmt.ServedType.ForYouContentExplorationSimclusterColdPosts
            case tmt.ServedType.Local => hmt.ServedType.ForYouLocal
            case tmt.ServedType.Trends => hmt.ServedType.ForYouTrends
            case tmt.ServedType.TwitterClipV0Short => hmt.ServedType.ForYouTwitterClipV0Short
            case tmt.ServedType.TwitterClipV0Long => hmt.ServedType.ForYouTwitterClipV0Long
            case tmt.ServedType.SemanticVideo => hmt.ServedType.ForYouSemanticVideo
            case tmt.ServedType.RelatedCreator => hmt.ServedType.ForYouRelatedCreator
            case tmt.ServedType.PromotedCreator => hmt.ServedType.ForYouPromotedCreator
            case tmt.ServedType.NsfwVideoContent => hmt.ServedType.ForYouNsfwVideoContent
            case _ => hmt.ServedType.ForYouTweetMixer
          }
      }.getOrElse(hmt.ServedType.ForYouTweetMixer)
  }
}

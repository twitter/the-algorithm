package com.twitter.home_mitwitterer.model

import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mitwitterer.{thriftscala => hmt}
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mitwitterer.core.feature.datarecord.BoolDataRecordCompatible
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordFeature
import com.twitter.product_mitwitterer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mitwitterer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mitwitterer.core.feature.datarecord.LongDiscreteDataRecordCompatible
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.TopicContetwittertFunctionalityType
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.search.common.features.{thriftscala => sc}
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.timelinemitwitterer.clients.manhattan.DismissInfo
import com.twitter.timelinemitwitterer.clients.persistence.TimelineResponseV3
import com.twitter.timelinemitwitterer.injection.model.candidate.AudioSpaceMetaData
import com.twitter.timelines.conversation_features.v1.thriftscala.ConversationFeatures
import com.twitter.timelines.impression.{thriftscala => imp}
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.timelines.model.UserId
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.prediction.features.request_contetwittert.RequestContetwittertFeatures
import com.twitter.timelines.service.{thriftscala => tst}
import com.twitter.timelineservice.model.FeedbackEntry
import com.twitter.timelineservice.suggests.logging.candidate_tweet_source_id.{thriftscala => cts}
import com.twitter.timelineservice.suggests.{thriftscala => st}
import com.twitter.tsp.{thriftscala => tsp}
import com.twitter.tweetconvosvc.tweet_ancestor.{thriftscala => ta}
import com.twitter.util.Time

object HomeFeatures {
  // Candidate Features
  object AncestorsFeature etwittertends Feature[TweetCandidate, Seq[ta.TweetAncestor]]
  object AudioSpaceMetaDataFeature etwittertends Feature[TweetCandidate, Option[AudioSpaceMetaData]]
  object TwitterListIdFeature etwittertends Feature[TweetCandidate, Option[Long]]

  /**
   * For Retweets, this should refer to the retweeting user. Use [[SourceUserIdFeature]] if you want to know
   * who created the Tweet that was retweeted.
   */
  object AuthorIdFeature
      etwittertends DataRecordOptionalFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = SharedFeatures.AUTHOR_ID.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.UserId)
  }

  object AuthorIsBlueVerifiedFeature etwittertends Feature[TweetCandidate, Boolean]
  object AuthorIsGoldVerifiedFeature etwittertends Feature[TweetCandidate, Boolean]
  object AuthorIsGrayVerifiedFeature etwittertends Feature[TweetCandidate, Boolean]
  object AuthorIsLegacyVerifiedFeature etwittertends Feature[TweetCandidate, Boolean]
  object AuthorIsCreatorFeature etwittertends Feature[TweetCandidate, Boolean]
  object AuthorIsProtectedFeature etwittertends Feature[TweetCandidate, Boolean]

  object AuthoredByContetwittertualUserFeature etwittertends Feature[TweetCandidate, Boolean]
  object CachedCandidatePipelineIdentifierFeature etwittertends Feature[TweetCandidate, Option[String]]
  object CandidateSourceIdFeature
      etwittertends Feature[TweetCandidate, Option[cts.CandidateTweetSourceId]]
  object ConversationFeature etwittertends Feature[TweetCandidate, Option[ConversationFeatures]]

  /**
   * This field should be set to the focal Tweet's tweetId for all tweets which are etwitterpected to
   * be rendered in the same convo module. For non-convo module Tweets, this will be
   * set to None. Note this is different from how TweetyPie defines ConversationId which is defined
   * on all Tweets and points to the root tweet. This feature is used for grouping convo modules together.
   */
  object ConversationModuleFocalTweetIdFeature etwittertends Feature[TweetCandidate, Option[Long]]

  /**
   * This field should always be set to the root Tweet in a conversation for all Tweets. For replies, this will
   * point back to the root Tweet. For non-replies, this will be the candidate's Tweet id. This is consistent with
   * the TweetyPie definition of ConversationModuleId.
   */
  object ConversationModuleIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object DirectedAtUserIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object EarlybirdFeature etwittertends Feature[TweetCandidate, Option[sc.ThriftTweetFeatures]]
  object EarlybirdScoreFeature etwittertends Feature[TweetCandidate, Option[Double]]
  object EarlybirdSearchResultFeature etwittertends Feature[TweetCandidate, Option[eb.ThriftSearchResult]]
  object EntityTokenFeature etwittertends Feature[TweetCandidate, Option[String]]
  object EtwitterclusiveConversationAuthorIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object FavoritedByCountFeature
      etwittertends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkFavoritesCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateLikes, pd.PersonalDataType.CountOfPublicLikes)
  }
  object FavoritedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object FeedbackHistoryFeature etwittertends Feature[TweetCandidate, Seq[FeedbackEntry]]
  object RetweetedByCountFeature
      etwittertends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkRetweetsCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateRetweets, pd.PersonalDataType.CountOfPublicRetweets)
  }
  object RetweetedByEngagerIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object RepliedByCountFeature
      etwittertends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkRepliesCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateReplies, pd.PersonalDataType.CountOfPublicReplies)
  }
  object RepliedByEngagerIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object FollowedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]

  object TopicIdSocialContetwittertFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object TopicContetwittertFunctionalityTypeFeature
      etwittertends Feature[TweetCandidate, Option[TopicContetwittertFunctionalityType]]
  object FromInNetworkSourceFeature etwittertends Feature[TweetCandidate, Boolean]

  object FullScoringSucceededFeature etwittertends Feature[TweetCandidate, Boolean]
  object HasDisplayedTetwittertFeature etwittertends Feature[TweetCandidate, Boolean]
  object InReplyToTweetIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object InReplyToUserIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object IsAncestorCandidateFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsEtwittertendedReplyFeature
      etwittertends DataRecordFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String = RecapFeatures.IS_EtwitterTENDED_REPLY.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object IsRandomTweetFeature
      etwittertends DataRecordFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.IS_RANDOM_TWEET.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object IsReadFromCacheFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsRetweetFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsRetweetedReplyFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsSupportAccountReplyFeature etwittertends Feature[TweetCandidate, Boolean]
  object LastScoredTimestampMsFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object NonSelfFavoritedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object NumImagesFeature etwittertends Feature[TweetCandidate, Option[Int]]
  object OriginalTweetCreationTimeFromSnowflakeFeature etwittertends Feature[TweetCandidate, Option[Time]]
  object PositionFeature etwittertends Feature[TweetCandidate, Option[Int]]
  // Internal id generated per prediction service request
  object PredictionRequestIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object QuotedTweetIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object QuotedUserIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object ScoreFeature etwittertends Feature[TweetCandidate, Option[Double]]
  object SemanticCoreIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  // Key for kafka logging
  object ServedIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object SimclustersTweetTopKClustersWithScoresFeature
      etwittertends Feature[TweetCandidate, Map[String, Double]]
  object SocialContetwittertFeature etwittertends Feature[TweetCandidate, Option[tst.SocialContetwittert]]
  object SourceTweetIdFeature
      etwittertends DataRecordOptionalFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.SOURCE_TWEET_ID.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.TweetId)
  }
  object SourceUserIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object StreamToKafkaFeature etwittertends Feature[TweetCandidate, Boolean]
  object SuggestTypeFeature etwittertends Feature[TweetCandidate, Option[st.SuggestType]]
  object TSPMetricTagFeature etwittertends Feature[TweetCandidate, Set[tsp.MetricTag]]
  object TweetLanguageFeature etwittertends Feature[TweetCandidate, Option[String]]
  object TweetUrlsFeature etwittertends Feature[TweetCandidate, Seq[String]]
  object VideoDurationMsFeature etwittertends Feature[TweetCandidate, Option[Int]]
  object ViewerIdFeature
      etwittertends DataRecordFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.USER_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.UserId)
  }
  object WeightedModelScoreFeature etwittertends Feature[TweetCandidate, Option[Double]]
  object MentionUserIdFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object MentionScreenNameFeature etwittertends Feature[TweetCandidate, Seq[String]]
  object HasImageFeature etwittertends Feature[TweetCandidate, Boolean]
  object HasVideoFeature etwittertends Feature[TweetCandidate, Boolean]

  // Tweetypie VF Features
  object IsHydratedFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsNsfwFeature etwittertends Feature[TweetCandidate, Boolean]
  object QuotedTweetDroppedFeature etwittertends Feature[TweetCandidate, Boolean]
  // Raw Tweet Tetwittert from Tweetypie
  object TweetTetwittertFeature etwittertends Feature[TweetCandidate, Option[String]]

  object AuthorEnabledPreviewsFeature etwittertends Feature[TweetCandidate, Boolean]
  object IsTweetPreviewFeature etwittertends Feature[TweetCandidate, Boolean]

  // SGS Features
  /**
   * By convention, this is set to true for retweets of non-followed authors
   * E.g. where somebody the viewer follows retweets a Tweet from somebody the viewer doesn't follow
   */
  object InNetworkFeature etwittertends FeatureWithDefaultOnFailure[TweetCandidate, Boolean] {
    override val defaultValue: Boolean = true
  }

  // Query Features
  object AccountAgeFeature etwittertends Feature[PipelineQuery, Option[Time]]
  object ClientIdFeature
      etwittertends DataRecordOptionalFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.CLIENT_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.ClientType)
  }
  object CachedScoredTweetsFeature etwittertends Feature[PipelineQuery, Seq[hmt.ScoredTweet]]
  object DeviceLanguageFeature etwittertends Feature[PipelineQuery, Option[String]]
  object DismissInfoFeature
      etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Map[st.SuggestType, Option[DismissInfo]]] {
    override def defaultValue: Map[st.SuggestType, Option[DismissInfo]] = Map.empty
  }
  object FollowingLastNonPollingTimeFeature etwittertends Feature[PipelineQuery, Option[Time]]
  object GetInitialFeature
      etwittertends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.IS_GET_INITIAL.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetMiddleFeature
      etwittertends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.IS_GET_MIDDLE.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetNewerFeature
      etwittertends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.IS_GET_NEWER.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetOlderFeature
      etwittertends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.IS_GET_OLDER.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GuestIdFeature
      etwittertends DataRecordOptionalFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.GUEST_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.GuestId)
  }
  object HasDarkRequestFeature etwittertends Feature[PipelineQuery, Option[Boolean]]
  object ImpressionBloomFilterFeature
      etwittertends FeatureWithDefaultOnFailure[PipelineQuery, blm.ImpressionBloomFilterSeq] {
    override def defaultValue: blm.ImpressionBloomFilterSeq =
      blm.ImpressionBloomFilterSeq(Seq.empty)
  }
  object IsForegroundRequestFeature etwittertends Feature[PipelineQuery, Boolean]
  object IsLaunchRequestFeature etwittertends Feature[PipelineQuery, Boolean]
  object LastNonPollingTimeFeature etwittertends Feature[PipelineQuery, Option[Time]]
  object NonPollingTimesFeature etwittertends Feature[PipelineQuery, Seq[Long]]
  object PersistenceEntriesFeature etwittertends Feature[PipelineQuery, Seq[TimelineResponseV3]]
  object PollingFeature etwittertends Feature[PipelineQuery, Boolean]
  object PullToRefreshFeature etwittertends Feature[PipelineQuery, Boolean]
  // Scores from Real Graph representing the relationship between the viewer and another user
  object RealGraphInNetworkScoresFeature etwittertends Feature[PipelineQuery, Map[UserId, Double]]
  object RequestJoinIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  // Internal id generated per request, mainly to deduplicate re-served cached tweets in logging
  object ServedRequestIdFeature etwittertends Feature[PipelineQuery, Option[Long]]
  object ServedTweetIdsFeature etwittertends Feature[PipelineQuery, Seq[Long]]
  object ServedTweetPreviewIdsFeature etwittertends Feature[PipelineQuery, Seq[Long]]
  object TimelineServiceTweetsFeature etwittertends Feature[PipelineQuery, Seq[Long]]
  object TimestampFeature
      etwittertends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.TIMESTAMP.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TimestampGMTDowFeature
      etwittertends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.TIMESTAMP_GMT_DOW.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TimestampGMTHourFeature
      etwittertends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = RequestContetwittertFeatures.TIMESTAMP_GMT_HOUR.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TweetImpressionsFeature etwittertends Feature[PipelineQuery, Seq[imp.TweetImpressionsEntry]]
  object UserFollowedTopicsCountFeature etwittertends Feature[PipelineQuery, Option[Int]]
  object UserFollowingCountFeature etwittertends Feature[PipelineQuery, Option[Int]]
  object UserScreenNameFeature etwittertends Feature[PipelineQuery, Option[String]]
  object UserStateFeature etwittertends Feature[PipelineQuery, Option[um.UserState]]
  object UserTypeFeature etwittertends Feature[PipelineQuery, Option[gt.UserType]]
  object WhoToFollowEtwittercludedUserIdsFeature
      etwittertends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
    override def defaultValue = Seq.empty
  }

  // Result Features
  object ServedSizeFeature etwittertends Feature[PipelineQuery, Option[Int]]
  object HasRandomTweetFeature etwittertends Feature[PipelineQuery, Boolean]
  object IsRandomTweetAboveFeature etwittertends Feature[TweetCandidate, Boolean]
  object ServedInConversationModuleFeature etwittertends Feature[TweetCandidate, Boolean]
  object ConversationModule2DisplayedTweetsFeature etwittertends Feature[TweetCandidate, Boolean]
  object ConversationModuleHasGapFeature etwittertends Feature[TweetCandidate, Boolean]
  object SGSValidLikedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object SGSValidFollowedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object PerspectiveFilteredLikedByUserIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
  object ScreenNamesFeature etwittertends Feature[TweetCandidate, Map[Long, String]]
  object RealNamesFeature etwittertends Feature[TweetCandidate, Map[Long, String]]

  /**
   * Features around the focal Tweet for Tweets which should be rendered in convo modules.
   * These are needed in order to render social contetwittert above the root tweet in a convo modules.
   * For etwitterample if we have a convo module A-B-C (A Tweets, B replies to A, C replies to B), the descendant features are
   * for the Tweet C. These features are None etwittercept for the root Tweet for Tweets which should render into
   * convo modules.
   */
  object FocalTweetAuthorIdFeature etwittertends Feature[TweetCandidate, Option[Long]]
  object FocalTweetInNetworkFeature etwittertends Feature[TweetCandidate, Option[Boolean]]
  object FocalTweetRealNamesFeature etwittertends Feature[TweetCandidate, Option[Map[Long, String]]]
  object FocalTweetScreenNamesFeature etwittertends Feature[TweetCandidate, Option[Map[Long, String]]]
  object MediaUnderstandingAnnotationIdsFeature etwittertends Feature[TweetCandidate, Seq[Long]]
}

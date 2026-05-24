package com.twitter.home_mixer.model

import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.dal.personal_data.thriftjava.PersonalDataType
import com.twitter.dal.personal_data.{thriftjava => pd}
import com.twitter.gizmoduck.{thriftscala => gt}
import com.twitter.home_mixer.model.candidate_source.SourceSignal
import com.twitter.home_mixer.model.signup.SignupSource
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.timelines.render.{thriftscala => urt}
import com.twitter.mediaservices.commons.thriftscala.MediaCategory
import com.twitter.ml.api.constant.SharedFeatures
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.FeatureWithDefaultOnFailure
import com.twitter.product_mixer.core.feature.datarecord.BoolDataRecordCompatible
import com.twitter.product_mixer.core.feature.datarecord.DataRecordFeature
import com.twitter.product_mixer.core.feature.datarecord.DataRecordOptionalFeature
import com.twitter.product_mixer.core.feature.datarecord.DoubleDataRecordCompatible
import com.twitter.product_mixer.core.feature.datarecord.LongDiscreteDataRecordCompatible
import com.twitter.product_mixer.core.feature.datarecord.SparseBinaryDataRecordCompatible
import com.twitter.product_mixer.core.feature.datarecord.SparseContinuousDataRecordCompatible
import com.twitter.product_mixer.core.feature.datarecord.StringDataRecordCompatible
import com.twitter.product_mixer.core.model.marshalling.response.urt.metadata.TopicContextFunctionalityType
import com.twitter.product_mixer.core.model.marshalling.response.urt.timeline_module.ModuleDisplayType
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.search.common.features.{thriftscala => sc}
import com.twitter.search.earlybird.{thriftscala => eb}
import com.twitter.strato.columns.content_understanding.thriftscala.AnnotatedVideoDeserialized
import com.twitter.timelinemixer.clients.manhattan.DismissInfo
import com.twitter.timelinemixer.clients.persistence.TimelineResponseV3
import com.twitter.timelinemixer.injection.model.candidate.AudioSpaceMetaData
import com.twitter.timelines.conversation_features.v1.thriftscala.ConversationFeatures
import com.twitter.timelines.impressionbloomfilter.{thriftscala => blm}
import com.twitter.timelines.model.UserId
import com.twitter.timelines.prediction.features.common.TimelinesSharedFeatures
import com.twitter.timelines.prediction.features.conversation_features
import com.twitter.timelines.prediction.features.engaged_language_features.LanguageFeatures
import com.twitter.timelines.prediction.features.engagement_features.EngagementDataRecordFeatures
import com.twitter.timelines.prediction.features.recap.RecapFeatures
import com.twitter.timelines.prediction.features.request_context.RequestContextFeatures
import com.twitter.timelines.prediction.features.user_health.UserHealthFeatures
import com.twitter.timelineservice.model.FeedbackEntry
import com.twitter.timelineservice.suggests.{thriftscala => st}
import com.twitter.tsp.{thriftscala => tsp}
import com.twitter.tweetconvosvc.tweet_ancestor.{thriftscala => ta}
import com.twitter.util.Duration
import com.twitter.util.Time
import com.x.user_action_sequence.UserActionSequence

object HomeFeatures {
  // Candidate Features
  object AncestorsFeature extends Feature[TweetCandidate, Seq[ta.TweetAncestor]]
  object AudioSpaceMetaDataFeature extends Feature[TweetCandidate, Option[AudioSpaceMetaData]]
  object ListIdFeature extends Feature[TweetCandidate, Option[Long]]
  object ListNameFeature extends Feature[TweetCandidate, Option[String]]
  object ValidLikedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object BookmarkedTweetTimestamp extends Feature[TweetCandidate, Option[Long]]
  object ArticleIdFeature extends Feature[TweetCandidate, Option[Long]]
  object ArticlePreviewTextFeature extends Feature[TweetCandidate, Option[String]]

  /**
   * For Retweets, this should refer to the retweeting user. Use [[SourceUserIdFeature]] if you want to know
   * who created the Tweet that was retweeted.
   */
  object AuthorIdFeature
      extends DataRecordOptionalFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = SharedFeatures.AUTHOR_ID.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.UserId)
  }

  object AuthorAccountAge extends Feature[TweetCandidate, Option[Duration]]
  object AuthorIsBlueVerifiedFeature extends Feature[TweetCandidate, Boolean]
  object AuthorIsGoldVerifiedFeature extends Feature[TweetCandidate, Boolean]
  object AuthorIsGrayVerifiedFeature extends Feature[TweetCandidate, Boolean]
  object AuthorIsLegacyVerifiedFeature extends Feature[TweetCandidate, Boolean]
  object AuthorIsCreatorFeature extends Feature[TweetCandidate, Boolean]
  object AuthorIsProtectedFeature extends Feature[TweetCandidate, Boolean]
  object AuthorFollowersFeature extends Feature[TweetCandidate, Option[Long]]
  object ViralContentCreatorFeature extends Feature[TweetCandidate, Boolean]
  object GrokContentCreatorFeature extends Feature[TweetCandidate, Boolean]
  object GorkContentCreatorFeature extends Feature[TweetCandidate, Boolean]
  object AuthoredByContextualUserFeature extends Feature[TweetCandidate, Boolean]
  object CachedCandidatePipelineIdentifierFeature extends Feature[TweetCandidate, Option[String]]
  object ConversationFeature extends Feature[TweetCandidate, Option[ConversationFeatures]]
  object AuthorSafetyLabels extends Feature[TweetCandidate, Option[Seq[String]]]

  /**
   * This field should be set to the focal Tweet's tweetId for all tweets which are expected to
   * be rendered in the same convo module. For non-convo module Tweets, this will be
   * set to None. Note this is different from how TweetyPie defines ConversationId which is defined
   * on all Tweets and points to the root tweet. This feature is used for grouping convo modules together.
   */
  object ConversationModuleFocalTweetIdFeature extends Feature[TweetCandidate, Option[Long]]

  /**
   * This field should always be set to the root Tweet in a conversation for all Tweets. For replies, this will
   * point back to the root Tweet. For non-replies, this will be the candidate's Tweet id. This is consistent with
   * the TweetyPie definition of ConversationModuleId.
   */
  object ConversationModuleIdFeature extends Feature[TweetCandidate, Option[Long]]
  object DirectedAtUserIdFeature extends Feature[TweetCandidate, Option[Long]]
  object EarlybirdFeature extends Feature[TweetCandidate, Option[sc.ThriftTweetFeatures]]
  object EarlybirdScoreFeature extends Feature[TweetCandidate, Option[Double]]
  object EarlybirdSearchResultFeature extends Feature[TweetCandidate, Option[eb.ThriftSearchResult]]
  object EntityTokenFeature extends Feature[TweetCandidate, Option[String]]
  object ExclusiveConversationAuthorIdFeature extends Feature[TweetCandidate, Option[Long]]
  object FavoritedByCountFeature
      extends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkFavoritesCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateLikes, pd.PersonalDataType.CountOfPublicLikes)
  }
  object FavoritedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object FeedbackHistoryFeature extends Feature[TweetCandidate, Seq[FeedbackEntry]]
  // A boolean feature indicating whether the latest feedback timestamp till now is newer than the cached scored tweets TTL
  object HasRecentFeedbackSinceCacheTtlFeature extends Feature[PipelineQuery, Boolean]

  object SlopAuthorFeature extends Feature[TweetCandidate, Boolean]

  object SlopAuthorScoreFeature
      extends DataRecordOptionalFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String = RecapFeatures.SLOP_AUTHOR_SCORE.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokTranslatedPostIsCachedFeature extends Feature[TweetCandidate, Boolean]

  object GrokVideoMetadataFeature
      extends FeatureWithDefaultOnFailure[TweetCandidate, Option[AnnotatedVideoDeserialized]] {
    override def defaultValue: Option[AnnotatedVideoDeserialized] = None
  }

  object GrokCategoryDataRecordFeature
      extends DataRecordOptionalFeature[TweetCandidate, Map[String, Double]]
      with SparseContinuousDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_CATEGORY_SCORES.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.InferredInterests)
  }

  object GrokTagsFeature
      extends Feature[TweetCandidate, Set[String]]
      with SparseBinaryDataRecordCompatible {
    override val featureName: String = RecapFeatures.GROK_TAGS.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.InferredLanguage)
  }

  object GrokIsGoreFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_GORE.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokIsNsfwFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_NSFW.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokIsSoftNsfwFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_SOFT_NSFW.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokIsSpamFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_SPAM.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokIsViolentFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_VIOLENT.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object GrokIsLowQualityFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_LOW_QUALITY.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.InferredInterests)
  }

  object GrokIsOcrFeature
      extends DataRecordOptionalFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      RecapFeatures.GROK_IS_OCR.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.InferredInterests)
  }

  object GrokSunnyScoreFeature
      extends DataRecordOptionalFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String = RecapFeatures.GROK_SUNNY_SCORE.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.EngagementScore)
  }

  // Used only for metrics tracking. Does not affect the recommendations.
  object GrokPoliticalInclinationFeature
      extends Feature[TweetCandidate, Option[hmt.PoliticalInclination]]

  object GrokSlopScoreFeature extends Feature[TweetCandidate, Option[Long]]

  object DedupClusterIdFeature extends Feature[TweetCandidate, Option[Long]]
  object DedupClusterId88Feature extends Feature[TweetCandidate, Option[Long]]
  object RetweetedByCountFeature
      extends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkRetweetsCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateRetweets, pd.PersonalDataType.CountOfPublicRetweets)
  }
  object RetweetedByEngagerIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object RepliedByCountFeature
      extends DataRecordFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String =
      EngagementDataRecordFeatures.InNetworkRepliesCount.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.CountOfPrivateReplies, pd.PersonalDataType.CountOfPublicReplies)
  }
  object RepliedByEngagerIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object FollowedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]

  object TopicIdSocialContextFeature extends Feature[TweetCandidate, Option[Long]]
  object TopicContextFunctionalityTypeFeature
      extends Feature[TweetCandidate, Option[TopicContextFunctionalityType]]
  object BasketballContextFeature extends Feature[TweetCandidate, Option[urt.BasketballContext]]

  object GenericPostContextFeature extends Feature[TweetCandidate, Option[urt.GenericContext]]
  object FromInNetworkSourceFeature extends Feature[TweetCandidate, Boolean]
  object FullScoringSucceededFeature extends Feature[TweetCandidate, Boolean]
  object HasDisplayedTextFeature extends Feature[TweetCandidate, Boolean]
  object InReplyToTweetIdFeature extends Feature[TweetCandidate, Option[Long]]
  object InReplyToUserIdFeature extends Feature[TweetCandidate, Option[Long]]
  object IsArticleFeature extends Feature[TweetCandidate, Boolean]
  object IsAncestorCandidateFeature extends Feature[TweetCandidate, Boolean]
  object IsBoostedCandidateFeature extends Feature[TweetCandidate, Boolean]
  object IsExtendedReplyFeature
      extends DataRecordFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String = RecapFeatures.IS_EXTENDED_REPLY.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object IsInReplyToReplyOrDirectedFeature extends Feature[TweetCandidate, Boolean]
  object IsInReplyToRetweetFeature extends Feature[TweetCandidate, Boolean]
  object IsRandomTweetFeature
      extends DataRecordFeature[TweetCandidate, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.IS_RANDOM_TWEET.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object IsReadFromCacheFeature extends Feature[TweetCandidate, Boolean]
  object IsRetweetFeature extends Feature[TweetCandidate, Boolean]
  object IsRetweetedReplyFeature extends Feature[TweetCandidate, Boolean]
  object IsSupportAccountReplyFeature extends Feature[TweetCandidate, Boolean]
  object LastScoredTimestampMsFeature extends Feature[TweetCandidate, Option[Long]]
  object NonSelfFavoritedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object NumImagesFeature extends Feature[TweetCandidate, Option[Int]]
  object PositionFeature extends Feature[TweetCandidate, Option[Int]]

  // Internal id generated per prediction service request
  object PredictionRequestIdFeature extends Feature[TweetCandidate, Option[Long]]
  object QuotedTweetIdFeature extends Feature[TweetCandidate, Option[Long]]
  object QuotedUserIdFeature extends Feature[TweetCandidate, Option[Long]]
  object PhoenixScoreFeature extends Feature[TweetCandidate, Option[Double]]
  object ScoreFeature extends Feature[TweetCandidate, Option[Double]]
  object IsColdStartPostFeature extends Feature[TweetCandidate, Boolean]
  object SemanticCoreIdFeature extends Feature[TweetCandidate, Option[Long]]
  object SimclustersTweetTopKClustersWithScoresFeature
      extends Feature[TweetCandidate, Map[String, Double]]
  // Tweet ID of the source tweet if the candidate is a retweet
  object SourceTweetIdFeature extends Feature[TweetCandidate, Option[Long]]

  // Tweet ID of the source tweet if the candidate is a retweet. Tweet id of the candidate otherwise
  object OriginalTweetIdFeature
      extends DataRecordOptionalFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.SOURCE_TWEET_ID.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.TweetId)
  }
  object SourceUserIdFeature extends Feature[TweetCandidate, Option[Long]]
  object ServedTypeFeature extends Feature[TweetCandidate, hmt.ServedType]
  object TSPMetricTagFeature extends Feature[TweetCandidate, Set[tsp.MetricTag]]
  object TweetLanguageFeature extends Feature[TweetCandidate, Option[String]]
  object TweetMediaIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object TweetMediaClusterIdsFeature extends Feature[TweetCandidate, Map[Long, Long]]
  object TweetMediaCompletionRateFeature extends Feature[TweetCandidate, Option[Double]]
  object ClipImageClusterIdsFeature extends Feature[TweetCandidate, Map[Long, Long]]
  object MultiModalEmbeddingsFeature extends Feature[TweetCandidate, Option[Seq[Double]]]
  object FirstMediaIdFeature
      extends DataRecordOptionalFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.FIRST_MEDIA_ID.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.TweetId)
  }
  object TweetUrlsFeature extends Feature[TweetCandidate, Seq[String]]

  object ViewCountFeature extends Feature[TweetCandidate, Option[Long]]
  object VideoDurationMsFeature extends Feature[TweetCandidate, Option[Int]]
  object ViewerIdFeature
      extends DataRecordFeature[TweetCandidate, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.USER_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.UserId)
  }
  object WeightedModelScoreFeature extends Feature[TweetCandidate, Option[Double]]
  object MentionUserIdFeature extends Feature[TweetCandidate, Seq[Long]]
  object MentionScreenNameFeature extends Feature[TweetCandidate, Seq[String]]
  object HasImageFeature extends Feature[TweetCandidate, Boolean]
  object HasVideoFeature extends Feature[TweetCandidate, Boolean]
  object VideoAspectRatioFeature extends Feature[TweetCandidate, Option[Float]]
  object VideoDisplayTypeFeature extends Feature[TweetCandidate, Option[ModuleDisplayType]]
  object VideoHeightFeature extends Feature[TweetCandidate, Option[Short]]
  object VideoWidthFeature extends Feature[TweetCandidate, Option[Short]]
  object MediaIdFeature extends Feature[TweetCandidate, Option[Long]]
  object HasMultipleMedia extends Feature[TweetCandidate, Boolean]
  object MediaCategoryFeature extends Feature[TweetCandidate, Option[MediaCategory]]
  object SemanticAnnotationIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object TweetTypeMetricsFeature extends Feature[TweetCandidate, Option[Seq[Byte]]]
  object CurrentPinnedTweetFeature extends Feature[TweetCandidate, Option[Long]]

  // Tweetypie VF Features
  object IsHydratedFeature extends Feature[TweetCandidate, Boolean]
  object OonNsfwFeature extends Feature[TweetCandidate, Boolean]
  object QuotedTweetDroppedFeature extends Feature[TweetCandidate, Boolean]
  // Raw Tweet Text from Tweetypie
  object TweetTextFeature extends Feature[TweetCandidate, Option[String]]
  object TweetTextTokensFeature extends Feature[TweetCandidate, Option[Seq[Int]]]
  object AuthorEnabledPreviewsFeature extends Feature[TweetCandidate, Boolean]
  object IsTweetPreviewFeature extends Feature[TweetCandidate, Boolean]

  // SGS Features
  /**
   * By convention, this is set to true for retweets of non-followed authors
   * E.g. where somebody the viewer follows retweets a Tweet from somebody the viewer doesn't follow
   */
  object InNetworkFeature extends FeatureWithDefaultOnFailure[TweetCandidate, Boolean] {
    override val defaultValue: Boolean = true
  }

  // Query Features
  object AccountAgeFeature extends Feature[PipelineQuery, Option[Time]]
  object ClientIdFeature
      extends DataRecordOptionalFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.CLIENT_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.ClientType)
  }
  object CachedScoredTweetsFeature extends Feature[PipelineQuery, Seq[hmt.ScoredTweet]]

  object FollowsSportsAccountFeature extends Feature[PipelineQuery, Boolean]

  object DeviceCountryFeature
      extends DataRecordOptionalFeature[PipelineQuery, String]
      with StringDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.COUNTRY_CODE.getFeatureName

    override def personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.PrivateCountryOrRegion, pd.PersonalDataType.InferredCountry)
  }

  object DeviceLanguageFeature
      extends DataRecordOptionalFeature[PipelineQuery, String]
      with StringDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.LANGUAGE_CODE.getFeatureName

    override def personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.GeneralSettings,
      pd.PersonalDataType.ProvidedLanguage,
      pd.PersonalDataType.InferredLanguage)
  }

  object UuaUserGenderFeature
      extends DataRecordOptionalFeature[PipelineQuery, String]
      with StringDataRecordCompatible {
    override def featureName: String = UserHealthFeatures.UserGender.getFeatureName

    override def personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.GeneralSettings,
      pd.PersonalDataType.ProvidedGender,
      pd.PersonalDataType.InferredGender)
  }

  object UuaUserStateFeature
      extends DataRecordOptionalFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = UserHealthFeatures.UserState.getFeatureName

    override def personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.UserState,
      pd.PersonalDataType.UserType
    )
  }

  object UuaUserAgeBucketFeature
      extends DataRecordOptionalFeature[PipelineQuery, String]
      with StringDataRecordCompatible {
    override def featureName: String = UserHealthFeatures.UserAgeBucket.getFeatureName

    override def personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.GeneralSettings,
      pd.PersonalDataType.ProvidedAge,
      pd.PersonalDataType.InferredAge
    )
  }

  object DismissInfoFeature
      extends FeatureWithDefaultOnFailure[PipelineQuery, Map[st.SuggestType, Option[DismissInfo]]] {
    override def defaultValue: Map[st.SuggestType, Option[DismissInfo]] = Map.empty
  }
  object FollowingLastNonPollingTimeFeature extends Feature[PipelineQuery, Option[Time]]
  object GetInitialFeature
      extends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.IS_GET_INITIAL.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetMiddleFeature
      extends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.IS_GET_MIDDLE.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetNewerFeature
      extends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.IS_GET_NEWER.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GetOlderFeature
      extends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override def featureName: String = RequestContextFeatures.IS_GET_OLDER.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object GuestIdFeature
      extends DataRecordOptionalFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override def featureName: String = SharedFeatures.GUEST_ID.getFeatureName
    override def personalDataTypes: Set[pd.PersonalDataType] = Set(pd.PersonalDataType.GuestId)
  }

  object GrokAnnotationsFeature extends Feature[TweetCandidate, Option[hmt.GrokAnnotations]]

  object GrokTopCategoryFeature extends Feature[TweetCandidate, Option[Long]]

  object HasDarkRequestFeature extends Feature[PipelineQuery, Option[Boolean]]
  object ImpressionBloomFilterFeature
      extends FeatureWithDefaultOnFailure[PipelineQuery, blm.ImpressionBloomFilterSeq] {
    override def defaultValue: blm.ImpressionBloomFilterSeq =
      blm.ImpressionBloomFilterSeq(Seq.empty)
  }
  object ImpressedMediaIds extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
    override val defaultValue: Seq[Long] = Seq.empty
  }
  object IsForegroundRequestFeature extends Feature[PipelineQuery, Boolean]
  object IsLaunchRequestFeature extends Feature[PipelineQuery, Boolean]
  object LastNonPollingTimeFeature extends Feature[PipelineQuery, Option[Time]]
  object LastNegativeFeedbackTimeFeature extends Feature[PipelineQuery, Option[Time]]
  object LowSignalUserFeature extends Feature[PipelineQuery, Boolean]
  object NaviClientConfigFeature extends Feature[PipelineQuery, NaviClientConfig]
  object NonPollingTimesFeature extends Feature[PipelineQuery, Seq[Long]]
  object PersistenceEntriesFeature extends Feature[PipelineQuery, Seq[TimelineResponseV3]]
  object PollingFeature extends Feature[PipelineQuery, Boolean]
  object PullToRefreshFeature extends Feature[PipelineQuery, Boolean]
  // Scores from Real Graph representing the relationship between the viewer and another user
  object RealGraphInNetworkScoresFeature extends Feature[PipelineQuery, Map[UserId, Double]]
  object ImmersiveClientEmbeddingsFeature extends Feature[PipelineQuery, Map[Long, Seq[Double]]]
  object RequestJoinIdFeature extends Feature[TweetCandidate, Option[Long]]
  // Internal id generated per request
  object ServedIdFeature extends Feature[PipelineQuery, Option[Long]]
  object ServedTweetIdsFeature extends Feature[PipelineQuery, Seq[Long]]
  object ServedAuthorIdsFeature extends Feature[PipelineQuery, Map[Long, Seq[Long]]]
  object ServedTweetPreviewIdsFeature extends Feature[PipelineQuery, Seq[Long]]

  object SignupSourceFeature extends Feature[PipelineQuery, Option[SignupSource]]
  object SignupCountryFeature extends Feature[PipelineQuery, Option[String]]
  object ViewerAllowsAdsPersonalizationFeature extends Feature[PipelineQuery, Option[Boolean]]
  object ViewerAllowsForYouRecommendationsFeature extends Feature[PipelineQuery, Option[Boolean]]
  object ViewerAllowsDataSharingFeature extends Feature[PipelineQuery, Option[Boolean]]
  object TimelineServiceTweetsFeature extends Feature[PipelineQuery, Seq[Long]]
  object TLSOriginalTweetsWithAuthorFeature
      extends Feature[PipelineQuery, Seq[(Long, Option[Long])]]

  object TLSOriginalTweetsWithConfirmedAuthorFeature
      extends Feature[PipelineQuery, Seq[(Long, Long)]]

  object TweetAuthorFollowersFeature extends Feature[PipelineQuery, Map[Long, Option[Long]]]

  object TimestampFeature
      extends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = SharedFeatures.TIMESTAMP.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TimestampGMTDowFeature
      extends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = RequestContextFeatures.TIMESTAMP_GMT_DOW.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TimestampGMTHourFeature
      extends DataRecordFeature[PipelineQuery, Long]
      with LongDiscreteDataRecordCompatible {
    override val featureName: String = RequestContextFeatures.TIMESTAMP_GMT_HOUR.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }
  object TweetMixerScoreFeature
      extends DataRecordOptionalFeature[TweetCandidate, Double]
      with DoubleDataRecordCompatible {
    override val featureName: String = TimelinesSharedFeatures.CANDIDATE_SOURCE_SCORE.getFeatureName
    override val personalDataTypes: Set[PersonalDataType] = Set(pd.PersonalDataType.EngagementScore)
  }

  object SourceSignalFeature extends Feature[TweetCandidate, Option[SourceSignal]]

  object DebugStringFeature extends Feature[TweetCandidate, Option[String]]

  object IsSelfThreadFeature
      extends DataRecordFeature[PipelineQuery, Boolean]
      with BoolDataRecordCompatible {
    override val featureName: String =
      conversation_features.ConversationFeatures.IS_SELF_THREAD_TWEET.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set.empty
  }

  object TweetLanguageFromTweetypieFeature
      extends DataRecordOptionalFeature[TweetCandidate, String]
      with StringDataRecordCompatible {
    override val featureName: String =
      LanguageFeatures.TweetLanguageFromTweetypieFeature.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] =
      Set(pd.PersonalDataType.InferredLanguage)
  }
  object TweetLanguageFromLanguageSignalFeature
      extends DataRecordOptionalFeature[PipelineQuery, String]
      with StringDataRecordCompatible {
    override val featureName: String =
      LanguageFeatures.TweetLanguageFromLanguageSignalFeature.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.InferredLanguage)
  }
  object UserActionsFeature extends Feature[PipelineQuery, Option[UserActionSequence]]
  object UserActionsByteArrayFeature extends Feature[PipelineQuery, Option[Array[Byte]]]
  object UserActionsSizeFeature extends Feature[PipelineQuery, Option[Int]]
  object UserActionsContainsExplicitSignalsFeature extends Feature[PipelineQuery, Boolean]
  object UserEngagedLanguagesFeature
      extends Feature[PipelineQuery, Set[String]]
      with SparseBinaryDataRecordCompatible {
    override val featureName: String = LanguageFeatures.EngagedLanguages.getFeatureName
    override val personalDataTypes: Set[pd.PersonalDataType] = Set(
      pd.PersonalDataType.InferredLanguage)
  }
  object UserFollowedTopicsCountFeature extends Feature[PipelineQuery, Option[Int]]
  object UserFollowingCountFeature extends Feature[PipelineQuery, Option[Int]]
  object UserFollowersCountFeature extends Feature[PipelineQuery, Option[Int]]
  object UserRecentEngagementTweetIdsFeature extends Feature[PipelineQuery, Seq[Long]]
  object UserLastExplicitSignalTimeFeature extends Feature[PipelineQuery, Option[Time]]
  object UserUnderstandableLanguagesFeature extends Feature[PipelineQuery, Seq[String]]
  object UserScreenNameFeature extends Feature[PipelineQuery, Option[String]]
  object UserStateFeature extends Feature[PipelineQuery, Option[um.UserState]]
  object UserTypeFeature extends Feature[PipelineQuery, Option[gt.UserType]]
  object ViewerSafetyLabels extends Feature[PipelineQuery, Option[Seq[String]]]
  object ViewerIsRateLimited extends Feature[PipelineQuery, Boolean]
  object ViewerHasJobRecommendationsEnabled extends Feature[PipelineQuery, Boolean]
  object ViewerHasPremiumTier extends Feature[PipelineQuery, Boolean]
  object ViewerHasRecruitingOrganizationRecommendationsEnabled
      extends Feature[PipelineQuery, Boolean]
  object WhoToFollowExcludedUserIdsFeature
      extends FeatureWithDefaultOnFailure[PipelineQuery, Seq[Long]] {
    override def defaultValue = Seq.empty
  }

  object NSFWConsumerScoreFeature extends Feature[PipelineQuery, Double]
  object NSFWConsumerFollowerScoreFeature extends Feature[PipelineQuery, Double]

  object CurrentDisplayedGrokTopicFeature
      extends FeatureWithDefaultOnFailure[PipelineQuery, Option[(Long, String)]] {
    override val defaultValue: Option[(Long, String)] = None
  }

  // Result Features
  object ServedSizeFeature extends Feature[PipelineQuery, Option[Int]]
  object UniqueAuthorCountFeature extends Feature[PipelineQuery, Option[Int]]
  object MaxSingleAuthorCountFeature extends Feature[PipelineQuery, Option[Int]]
  object UniqueCategoryCountFeature extends Feature[PipelineQuery, Option[Int]]
  object MaxSingleCategoryCountFeature extends Feature[PipelineQuery, Option[Int]]
  object ServedInConversationModuleFeature extends Feature[TweetCandidate, Boolean]
  object ConversationModule2DisplayedTweetsFeature extends Feature[TweetCandidate, Boolean]
  object ConversationModuleHasGapFeature extends Feature[TweetCandidate, Boolean]
  object SGSValidLikedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object SGSValidFollowedByUserIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object ScreenNamesFeature extends Feature[TweetCandidate, Map[Long, String]]
  object RealNamesFeature extends Feature[TweetCandidate, Map[Long, String]]
  object TweetAgeFeature extends Feature[TweetCandidate, Option[Long]]

  /**
   * Features around the focal Tweet for Tweets which should be rendered in convo modules.
   * These are needed in order to render social context above the root tweet in a convo modules.
   * For example if we have a convo module A-B-C (A Tweets, B replies to A, C replies to B), the descendant features are
   * for the Tweet C. These features are None except for the root Tweet for Tweets which should render into
   * convo modules.
   */
  object FocalTweetAuthorIdFeature extends Feature[TweetCandidate, Option[Long]]
  object FocalTweetInNetworkFeature extends Feature[TweetCandidate, Option[Boolean]]
  object FocalTweetRealNamesFeature extends Feature[TweetCandidate, Option[Map[Long, String]]]
  object FocalTweetScreenNamesFeature extends Feature[TweetCandidate, Option[Map[Long, String]]]
  object MediaUnderstandingAnnotationIdsFeature extends Feature[TweetCandidate, Seq[Long]]
  object AdTagUrlFeature extends Feature[TweetCandidate, Option[String]]
}

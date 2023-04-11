package com.twitter.visibility.models

import com.twitter.spam.rtf.thriftscala.{SafetyLevel => ThriftSafetyLevel}
import com.twitter.visibility.configapi.params.SafetyLevelParam
import com.twitter.visibility.configapi.params.SafetyLevelParams._

sealed trait SafetyLevel {
  val name: String = this.getClass.getSimpleName.dropRight(1)
  def enabledParam: SafetyLevelParam
}

object SafetyLevel {
  private lazy val nameToSafetyLevelMap: Map[String, SafetyLevel] =
    SafetyLevel.List.map(s => s.name.toLowerCase -> s).toMap
  def fromName(name: String): Option[SafetyLevel] = {
    nameToSafetyLevelMap.get(name.toLowerCase)
  }

  private val DeprecatedEnumValue = -1

  private lazy val thriftToModelMap: Map[ThriftSafetyLevel, SafetyLevel] = Map(
    ThriftSafetyLevel.AccessInternalPromotedContent -> AccessInternalPromotedContent,
    ThriftSafetyLevel.AdsBusinessSettings -> AdsBusinessSettings,
    ThriftSafetyLevel.AdsCampaign -> AdsCampaign,
    ThriftSafetyLevel.AdsManager -> AdsManager,
    ThriftSafetyLevel.AdsReportingDashboard -> AdsReportingDashboard,
    ThriftSafetyLevel.AllSubscribedLists -> AllSubscribedLists,
    ThriftSafetyLevel.Appeals -> Appeals,
    ThriftSafetyLevel.ArticleTweetTimeline -> ArticleTweetTimeline,
    ThriftSafetyLevel.BaseQig -> BaseQig,
    ThriftSafetyLevel.BirdwatchNoteAuthor -> BirdwatchNoteAuthor,
    ThriftSafetyLevel.BirdwatchNoteTweetsTimeline -> BirdwatchNoteTweetsTimeline,
    ThriftSafetyLevel.BirdwatchNeedsYourHelpNotifications -> BirdwatchNeedsYourHelpNotifications,
    ThriftSafetyLevel.BlockMuteUsersTimeline -> BlockMuteUsersTimeline,
    ThriftSafetyLevel.BrandSafety -> BrandSafety,
    ThriftSafetyLevel.CardPollVoting -> CardPollVoting,
    ThriftSafetyLevel.CardsService -> CardsService,
    ThriftSafetyLevel.Communities -> Communities,
    ThriftSafetyLevel.ContentControlToolInstall -> ContentControlToolInstall,
    ThriftSafetyLevel.ConversationFocalPrehydration -> ConversationFocalPrehydration,
    ThriftSafetyLevel.ConversationFocalTweet -> ConversationFocalTweet,
    ThriftSafetyLevel.ConversationInjectedTweet -> ConversationInjectedTweet,
    ThriftSafetyLevel.ConversationReply -> ConversationReply,
    ThriftSafetyLevel.CuratedTrendsRepresentativeTweet -> CuratedTrendsRepresentativeTweet,
    ThriftSafetyLevel.CurationPolicyViolations -> CurationPolicyViolations,
    ThriftSafetyLevel.DevPlatformGetListTweets -> DevPlatformGetListTweets,
    ThriftSafetyLevel.DesFollowingAndFollowersUserList -> DesFollowingAndFollowersUserList,
    ThriftSafetyLevel.DesHomeTimeline -> DesHomeTimeline,
    ThriftSafetyLevel.DesQuoteTweetTimeline -> DesQuoteTweetTimeline,
    ThriftSafetyLevel.DesRealtime -> DesRealtime,
    ThriftSafetyLevel.DesRealtimeSpamEnrichment -> DesRealtimeSpamEnrichment,
    ThriftSafetyLevel.DesRealtimeTweetFilter -> DesRealtimeTweetFilter,
    ThriftSafetyLevel.DesRetweetingUsers -> DesRetweetingUsers,
    ThriftSafetyLevel.DesTweetDetail -> DesTweetDetail,
    ThriftSafetyLevel.DesTweetLikingUsers -> DesTweetLikingUsers,
    ThriftSafetyLevel.DesUserBookmarks -> DesUserBookmarks,
    ThriftSafetyLevel.DesUserLikedTweets -> DesUserLikedTweets,
    ThriftSafetyLevel.DesUserMentions -> DesUserMentions,
    ThriftSafetyLevel.DesUserTweets -> DesUserTweets,
    ThriftSafetyLevel.DevPlatformComplianceStream -> DevPlatformComplianceStream,
    ThriftSafetyLevel.DirectMessages -> DirectMessages,
    ThriftSafetyLevel.DirectMessagesConversationList -> DirectMessagesConversationList,
    ThriftSafetyLevel.DirectMessagesConversationTimeline -> DirectMessagesConversationTimeline,
    ThriftSafetyLevel.DirectMessagesInbox -> DirectMessagesInbox,
    ThriftSafetyLevel.DirectMessagesMutedUsers -> DirectMessagesMutedUsers,
    ThriftSafetyLevel.DirectMessagesPinned -> DirectMessagesPinned,
    ThriftSafetyLevel.DirectMessagesSearch -> DirectMessagesSearch,
    ThriftSafetyLevel.EditHistoryTimeline -> EditHistoryTimeline,
    ThriftSafetyLevel.ElevatedQuoteTweetTimeline -> ElevatedQuoteTweetTimeline,
    ThriftSafetyLevel.EmbeddedTweet -> EmbeddedTweet,
    ThriftSafetyLevel.EmbedsPublicInterestNotice -> EmbedsPublicInterestNotice,
    ThriftSafetyLevel.EmbedTweetMarkup -> EmbedTweetMarkup,
    ThriftSafetyLevel.ExploreRecommendations -> ExploreRecommendations,
    ThriftSafetyLevel.WritePathLimitedActionsEnforcement -> WritePathLimitedActionsEnforcement,
    ThriftSafetyLevel.FilterAll -> FilterAll,
    ThriftSafetyLevel.FilterAllPlaceholder -> FilterAllPlaceholder,
    ThriftSafetyLevel.FilterDefault -> FilterDefault,
    ThriftSafetyLevel.FilterNone -> FilterNone,
    ThriftSafetyLevel.FollowedTopicsTimeline -> FollowedTopicsTimeline,
    ThriftSafetyLevel.FollowerConnections -> FollowerConnections,
    ThriftSafetyLevel.FollowingAndFollowersUserList -> FollowingAndFollowersUserList,
    ThriftSafetyLevel.ForDevelopmentOnly -> ForDevelopmentOnly,
    ThriftSafetyLevel.FriendsFollowingList -> FriendsFollowingList,
    ThriftSafetyLevel.GraphqlDefault -> GraphqlDefault,
    ThriftSafetyLevel.HumanizationNudge -> HumanizationNudge,
    ThriftSafetyLevel.KitchenSinkDevelopment -> KitchenSinkDevelopment,
    ThriftSafetyLevel.ListHeader -> ListHeader,
    ThriftSafetyLevel.ListMemberships -> ListMemberships,
    ThriftSafetyLevel.ListOwnerships -> ListOwnerships,
    ThriftSafetyLevel.ListRecommendations -> ListRecommendations,
    ThriftSafetyLevel.ListSearch -> ListSearch,
    ThriftSafetyLevel.ListSubscriptions -> ListSubscriptions,
    ThriftSafetyLevel.LivePipelineEngagementCounts -> LivePipelineEngagementCounts,
    ThriftSafetyLevel.LiveVideoTimeline -> LiveVideoTimeline,
    ThriftSafetyLevel.MagicRecs -> MagicRecs,
    ThriftSafetyLevel.MagicRecsV2 -> MagicRecsV2,
    ThriftSafetyLevel.MagicRecsAggressive -> MagicRecsAggressive,
    ThriftSafetyLevel.MagicRecsAggressiveV2 -> MagicRecsAggressiveV2,
    ThriftSafetyLevel.Minimal -> Minimal,
    ThriftSafetyLevel.ModeratedTweetsTimeline -> ModeratedTweetsTimeline,
    ThriftSafetyLevel.Moments -> Moments,
    ThriftSafetyLevel.NearbyTimeline -> NearbyTimeline,
    ThriftSafetyLevel.NewUserExperience -> NewUserExperience,
    ThriftSafetyLevel.NotificationsIbis -> NotificationsIbis,
    ThriftSafetyLevel.NotificationsPlatform -> NotificationsPlatform,
    ThriftSafetyLevel.NotificationsPlatformPush -> NotificationsPlatformPush,
    ThriftSafetyLevel.NotificationsQig -> NotificationsQig,
    ThriftSafetyLevel.NotificationsRead -> NotificationsRead,
    ThriftSafetyLevel.NotificationsTimelineDeviceFollow -> NotificationsTimelineDeviceFollow,
    ThriftSafetyLevel.NotificationsWrite -> NotificationsWrite,
    ThriftSafetyLevel.NotificationsWriterTweetHydrator -> NotificationsWriterTweetHydrator,
    ThriftSafetyLevel.NotificationsWriterV2 -> NotificationsWriterV2,
    ThriftSafetyLevel.ProfileMixerMedia -> ProfileMixerMedia,
    ThriftSafetyLevel.ProfileMixerFavorites -> ProfileMixerFavorites,
    ThriftSafetyLevel.QuickPromoteTweetEligibility -> QuickPromoteTweetEligibility,
    ThriftSafetyLevel.QuoteTweetTimeline -> QuoteTweetTimeline,
    ThriftSafetyLevel.QuotedTweetRules -> QuotedTweetRules,
    ThriftSafetyLevel.Recommendations -> Recommendations,
    ThriftSafetyLevel.RecosVideo -> RecosVideo,
    ThriftSafetyLevel.RecosWritePath -> RecosWritePath,
    ThriftSafetyLevel.RepliesGrouping -> RepliesGrouping,
    ThriftSafetyLevel.ReportCenter -> ReportCenter,
    ThriftSafetyLevel.ReturningUserExperience -> ReturningUserExperience,
    ThriftSafetyLevel.ReturningUserExperienceFocalTweet -> ReturningUserExperienceFocalTweet,
    ThriftSafetyLevel.Revenue -> Revenue,
    ThriftSafetyLevel.RitoActionedTweetTimeline -> RitoActionedTweetTimeline,
    ThriftSafetyLevel.SafeSearchMinimal -> SafeSearchMinimal,
    ThriftSafetyLevel.SafeSearchStrict -> SafeSearchStrict,
    ThriftSafetyLevel.SearchHydration -> SearchHydration,
    ThriftSafetyLevel.SearchLatest -> SearchLatest,
    ThriftSafetyLevel.SearchTop -> SearchTop,
    ThriftSafetyLevel.SearchTopQig -> SearchTopQig,
    ThriftSafetyLevel.SearchMixerSrpMinimal -> SearchMixerSrpMinimal,
    ThriftSafetyLevel.SearchMixerSrpStrict -> SearchMixerSrpStrict,
    ThriftSafetyLevel.SearchPeopleSrp -> SearchPeopleSrp,
    ThriftSafetyLevel.SearchPeopleTypeahead -> SearchPeopleTypeahead,
    ThriftSafetyLevel.SearchPhoto -> SearchPhoto,
    ThriftSafetyLevel.SearchTrendTakeoverPromotedTweet -> SearchTrendTakeoverPromotedTweet,
    ThriftSafetyLevel.SearchVideo -> SearchVideo,
    ThriftSafetyLevel.SearchBlenderUserRules -> SearchBlenderUserRules,
    ThriftSafetyLevel.SearchLatestUserRules -> SearchLatestUserRules,
    ThriftSafetyLevel.ShoppingManagerSpyMode -> ShoppingManagerSpyMode,
    ThriftSafetyLevel.SignalsReactions -> SignalsReactions,
    ThriftSafetyLevel.SignalsTweetReactingUsers -> SignalsTweetReactingUsers,
    ThriftSafetyLevel.SocialProof -> SocialProof,
    ThriftSafetyLevel.SoftInterventionPivot -> SoftInterventionPivot,
    ThriftSafetyLevel.SpaceFleetline -> SpaceFleetline,
    ThriftSafetyLevel.SpaceHomeTimelineUpranking -> SpaceHomeTimelineUpranking,
    ThriftSafetyLevel.SpaceJoinScreen -> SpaceJoinScreen,
    ThriftSafetyLevel.SpaceNotifications -> SpaceNotifications,
    ThriftSafetyLevel.Spaces -> Spaces,
    ThriftSafetyLevel.SpacesParticipants -> SpacesParticipants,
    ThriftSafetyLevel.SpacesSellerApplicationStatus -> SpacesSellerApplicationStatus,
    ThriftSafetyLevel.SpacesSharing -> SpacesSharing,
    ThriftSafetyLevel.SpaceTweetAvatarHomeTimeline -> SpaceTweetAvatarHomeTimeline,
    ThriftSafetyLevel.StickersTimeline -> StickersTimeline,
    ThriftSafetyLevel.StratoExtLimitedEngagements -> StratoExtLimitedEngagements,
    ThriftSafetyLevel.StreamServices -> StreamServices,
    ThriftSafetyLevel.SuperFollowerConnections -> SuperFollowerConnections,
    ThriftSafetyLevel.SuperLike -> SuperLike,
    ThriftSafetyLevel.Test -> Test,
    ThriftSafetyLevel.TimelineBookmark -> TimelineBookmark,
    ThriftSafetyLevel.TimelineContentControls -> TimelineContentControls,
    ThriftSafetyLevel.TimelineConversations -> TimelineConversations,
    ThriftSafetyLevel.TimelineConversationsDownranking -> TimelineConversationsDownranking,
    ThriftSafetyLevel.TimelineConversationsDownrankingMinimal -> TimelineConversationsDownrankingMinimal,
    ThriftSafetyLevel.TimelineFavorites -> TimelineFavorites,
    ThriftSafetyLevel.TimelineFavoritesSelfView -> TimelineFavoritesSelfView,
    ThriftSafetyLevel.TimelineFocalTweet -> TimelineFocalTweet,
    ThriftSafetyLevel.TimelineFollowingActivity -> TimelineFollowingActivity,
    ThriftSafetyLevel.TimelineHome -> TimelineHome,
    ThriftSafetyLevel.TimelineHomeCommunities -> TimelineHomeCommunities,
    ThriftSafetyLevel.TimelineHomeHydration -> TimelineHomeHydration,
    ThriftSafetyLevel.TimelineHomeLatest -> TimelineHomeLatest,
    ThriftSafetyLevel.TimelineHomePromotedHydration -> TimelineHomePromotedHydration,
    ThriftSafetyLevel.TimelineHomeRecommendations -> TimelineHomeRecommendations,
    ThriftSafetyLevel.TimelineHomeTopicFollowRecommendations -> TimelineHomeTopicFollowRecommendations,
    ThriftSafetyLevel.TimelineScorer -> TimelineScorer,
    ThriftSafetyLevel.TimelineInjection -> TimelineInjection,
    ThriftSafetyLevel.TimelineLikedBy -> TimelineLikedBy,
    ThriftSafetyLevel.TimelineLists -> TimelineLists,
    ThriftSafetyLevel.TimelineMedia -> TimelineMedia,
    ThriftSafetyLevel.TimelineMentions -> TimelineMentions,
    ThriftSafetyLevel.TimelineModeratedTweetsHydration -> TimelineModeratedTweetsHydration,
    ThriftSafetyLevel.TimelineProfile -> TimelineProfile,
    ThriftSafetyLevel.TimelineProfileAll -> TimelineProfileAll,
    ThriftSafetyLevel.TimelineProfileSpaces -> TimelineProfileSpaces,
    ThriftSafetyLevel.TimelineProfileSuperFollows -> TimelineProfileSuperFollows,
    ThriftSafetyLevel.TimelineReactiveBlending -> TimelineReactiveBlending,
    ThriftSafetyLevel.TimelineRetweetedBy -> TimelineRetweetedBy,
    ThriftSafetyLevel.TimelineSuperLikedBy -> TimelineSuperLikedBy,
    ThriftSafetyLevel.Tombstoning -> Tombstoning,
    ThriftSafetyLevel.TopicRecommendations -> TopicRecommendations,
    ThriftSafetyLevel.TopicsLandingPageTopicRecommendations -> TopicsLandingPageTopicRecommendations,
    ThriftSafetyLevel.TrendsRepresentativeTweet -> TrendsRepresentativeTweet,
    ThriftSafetyLevel.TrustedFriendsUserList -> TrustedFriendsUserList,
    ThriftSafetyLevel.TwitterDelegateUserList -> TwitterDelegateUserList,
    ThriftSafetyLevel.GryphonDecksAndColumns -> GryphonDecksAndColumns,
    ThriftSafetyLevel.TweetDetail -> TweetDetail,
    ThriftSafetyLevel.TweetDetailNonToo -> TweetDetailNonToo,
    ThriftSafetyLevel.TweetDetailWithInjectionsHydration -> TweetDetailWithInjectionsHydration,
    ThriftSafetyLevel.TweetEngagers -> TweetEngagers,
    ThriftSafetyLevel.TweetReplyNudge -> TweetReplyNudge,
    ThriftSafetyLevel.TweetScopedTimeline -> TweetScopedTimeline,
    ThriftSafetyLevel.TweetWritesApi -> TweetWritesApi,
    ThriftSafetyLevel.TwitterArticleCompose -> TwitterArticleCompose,
    ThriftSafetyLevel.TwitterArticleProfileTab -> TwitterArticleProfileTab,
    ThriftSafetyLevel.TwitterArticleRead -> TwitterArticleRead,
    ThriftSafetyLevel.UserProfileHeader -> UserProfileHeader,
    ThriftSafetyLevel.UserMilestoneRecommendation -> UserMilestoneRecommendation,
    ThriftSafetyLevel.UserScopedTimeline -> UserScopedTimeline,
    ThriftSafetyLevel.UserSearchSrp -> UserSearchSrp,
    ThriftSafetyLevel.UserSearchTypeahead -> UserSearchTypeahead,
    ThriftSafetyLevel.UserSelfViewOnly -> UserSelfViewOnly,
    ThriftSafetyLevel.UserSettings -> UserSettings,
    ThriftSafetyLevel.VideoAds -> VideoAds,
    ThriftSafetyLevel.ZipbirdConsumerArchives -> ZipbirdConsumerArchives,
    ThriftSafetyLevel.TweetAward -> TweetAward,
  )

  private lazy val modelToThriftMap: Map[SafetyLevel, ThriftSafetyLevel] =
    for ((k, v) <- thriftToModelMap) yield (v, k)

  case object AdsBusinessSettings extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsBusinessSettingsSafetyLevelParam
  }
  case object AdsCampaign extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsCampaignSafetyLevelParam
  }
  case object AdsManager extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsManagerSafetyLevelParam
  }
  case object AdsReportingDashboard extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAdsReportingDashboardSafetyLevelParam
  }
  case object AllSubscribedLists extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAllSubscribedListsSafetyLevelParam
  }
  case object Appeals extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableAppealsSafetyLevelParam
  }
  case object ArticleTweetTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableArticleTweetTimelineSafetyLevelParam
  }
  case object BaseQig extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBaseQigSafetyLevelParam
  }
  case object BirdwatchNoteAuthor extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBirdwatchNoteAuthorSafetyLevel
  }
  case object BirdwatchNoteTweetsTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBirdwatchNoteTweetsTimelineSafetyLevel
  }
  case object BirdwatchNeedsYourHelpNotifications extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableBirdwatchNeedsYourHelpNotificationsSafetyLevel
  }
  case object BlockMuteUsersTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBlockMuteUsersTimelineSafetyLevelParam
  }
  case object BrandSafety extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableBrandSafetySafetyLevelParam
  }
  case object CardPollVoting extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCardPollVotingSafetyLevelParam
  }
  case object CardsService extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCardsServiceSafetyLevelParam
  }
  case object Communities extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCommunitiesSafetyLevelParam
  }
  case object ContentControlToolInstall extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableContentControlToolInstallSafetyLevelParam
  }
  case object ConversationFocalPrehydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableConversationFocalPrehydrationSafetyLevelParam
  }
  case object ConversationFocalTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationFocalTweetSafetyLevelParam
  }
  case object ConversationInjectedTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationInjectedTweetSafetyLevelParam
  }
  case object ConversationReply extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableConversationReplySafetyLevelParam
  }
  case object AccessInternalPromotedContent extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableAccessInternalPromotedContentSafetyLevelParam
  }
  case object CuratedTrendsRepresentativeTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCuratedTrendsRepresentativeTweet
  }
  case object CurationPolicyViolations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableCurationPolicyViolations
  }
  case object DevPlatformGetListTweets extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDevPlatformGetListTweetsSafetyLevelParam
  }
  case object DesFollowingAndFollowersUserList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDESFollowingAndFollowersUserListSafetyLevelParam
  }
  case object DesHomeTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESHomeTimelineSafetyLevelParam
  }
  case object DesQuoteTweetTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESQuoteTweetTimelineSafetyLevelParam
  }
  case object DesRealtime extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeSafetyLevelParam
  }
  case object DesRealtimeSpamEnrichment extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeSpamEnrichmentSafetyLevelParam
  }
  case object DesRealtimeTweetFilter extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRealtimeTweetFilterSafetyLevelParam
  }
  case object DesRetweetingUsers extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESRetweetingUsersSafetyLevelParam
  }
  case object DesTweetDetail extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDesTweetDetailSafetyLevelParam
  }
  case object DesTweetLikingUsers extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESTweetLikingUsersSafetyLevelParam
  }
  case object DesUserBookmarks extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserBookmarksSafetyLevelParam
  }
  case object DesUserLikedTweets extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserLikedTweetSafetyLevelParam
  }
  case object DesUserMentions extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserMentionsSafetyLevelParam
  }
  case object DesUserTweets extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDESUserTweetsSafetyLevelParam
  }
  case object DevPlatformComplianceStream extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDevPlatformComplianceStreamSafetyLevelParam
  }
  case object DirectMessages extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesSafetyLevelParam
  }
  case object DirectMessagesConversationList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesConversationListSafetyLevelParam
  }
  case object DirectMessagesConversationTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesConversationTimelineSafetyLevelParam
  }
  case object DirectMessagesInbox extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableDirectMessagesInboxSafetyLevelParam
  }
  case object DirectMessagesMutedUsers extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesMutedUsersSafetyLevelParam
  }
  case object DirectMessagesPinned extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesPinnedSafetyLevelParam
  }
  case object DirectMessagesSearch extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDirectMessagesSearchSafetyLevelParam
  }
  case object EditHistoryTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEditHistoryTimelineSafetyLevelParam
  }
  case object ElevatedQuoteTweetTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableElevatedQuoteTweetTimelineSafetyLevelParam
  }
  case object EmbeddedTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEmbeddedTweetSafetyLevelParam
  }
  case object EmbedsPublicInterestNotice extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableEmbedsPublicInterestNoticeSafetyLevelParam
  }
  case object EmbedTweetMarkup extends SafetyLevel {
    override def enabledParam: SafetyLevelParam = EnableEmbedTweetMarkupSafetyLevelParam
  }
  case object WritePathLimitedActionsEnforcement extends SafetyLevel {
    override def enabledParam: SafetyLevelParam =
      EnableWritePathLimitedActionsEnforcementSafetyLevelParam
  }
  case object FilterNone extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterNoneSafetyLevelParam
  }
  case object FilterAll extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterAllSafetyLevelParam
  }
  case object FilterAllPlaceholder extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterDefaultSafetyLevelParam
  }
  case object FilterDefault extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFilterDefaultSafetyLevelParam
  }
  case object FollowedTopicsTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFollowedTopicsTimelineSafetyLevelParam
  }
  case object FollowerConnections extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFollowerConnectionsSafetyLevelParam
  }
  case object FollowingAndFollowersUserList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableFollowingAndFollowersUserListSafetyLevelParam
  }
  case object ForDevelopmentOnly extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableForDevelopmentOnlySafetyLevelParam
  }
  case object FriendsFollowingList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableFriendsFollowingListSafetyLevelParam
  }
  case object GraphqlDefault extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableGraphqlDefaultSafetyLevelParam
  }
  case object GryphonDecksAndColumns extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableGryphonDecksAndColumnsSafetyLevelParam
  }
  case object HumanizationNudge extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableHumanizationNudgeSafetyLevelParam
  }
  case object KitchenSinkDevelopment extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableKitchenSinkDevelopmentSafetyLevelParam
  }
  case object ListHeader extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListHeaderSafetyLevelParam
  }
  case object ListMemberships extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListMembershipsSafetyLevelParam
  }
  case object ListOwnerships extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListOwnershipsSafetyLevelParam
  }
  case object ListRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListRecommendationsSafetyLevelParam
  }
  case object ListSearch extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListSearchSafetyLevelParam
  }
  case object ListSubscriptions extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableListSubscriptionsSafetyLevelParam
  }
  case object LivePipelineEngagementCounts extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableLivePipelineEngagementCountsSafetyLevelParam
  }
  case object LiveVideoTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableLiveVideoTimelineSafetyLevelParam
  }
  case object MagicRecs extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsSafetyLevelParam
  }
  case object MagicRecsAggressive extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsAggressiveSafetyLevelParam
  }
  case object MagicRecsAggressiveV2 extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsAggressiveV2SafetyLevelParam
  }
  case object MagicRecsV2 extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMagicRecsV2SafetyLevelParam
  }
  case object Minimal extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMinimalSafetyLevelParam
  }
  case object ModeratedTweetsTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableModeratedTweetsTimelineSafetyLevelParam
  }
  case object Moments extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableMomentsSafetyLevelParam
  }
  case object NearbyTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNearbySafetyLevelParam
  }
  case object NewUserExperience extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNewUserExperienceSafetyLevelParam
  }
  case object NotificationsIbis extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsIbisSafetyLevelParam
  }
  case object NotificationsPlatform extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsPlatformSafetyLevelParam
  }
  case object NotificationsPlatformPush extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsPlatformPushSafetyLevelParam
  }
  case object NotificationsQig extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsQigSafetyLevelParam
  }
  case object NotificationsRead extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsReadSafetyLevelParam
  }
  case object NotificationsTimelineDeviceFollow extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableNotificationsTimelineDeviceFollowSafetyLevelParam
  }
  case object NotificationsWrite extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsWriteSafetyLevelParam
  }
  case object NotificationsWriterV2 extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableNotificationsWriterV2SafetyLevelParam
  }
  case object NotificationsWriterTweetHydrator extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableNotificationsWriterTweetHydratorSafetyLevelParam
  }
  case object ProfileMixerMedia extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableProfileMixerMediaSafetyLevelParam
  }
  case object ProfileMixerFavorites extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableProfileMixerFavoritesSafetyLevelParam
  }
  case object QuickPromoteTweetEligibility extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuickPromoteTweetEligibilitySafetyLevelParam
  }
  case object QuoteTweetTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuoteTweetTimelineSafetyLevelParam
  }
  case object QuotedTweetRules extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableQuotedTweetRulesParam
  }
  case object Recommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecommendationsSafetyLevelParam
  }
  case object RecosVideo extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecosVideoSafetyLevelParam
  }
  case object RecosWritePath extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRecosWritePathSafetyLevelParam
  }
  case object RepliesGrouping extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRepliesGroupingSafetyLevelParam
  }
  case object ReportCenter extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableReportCenterSafetyLevelParam
  }
  case object ReturningUserExperience extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableReturningUserExperienceSafetyLevelParam
  }
  case object ReturningUserExperienceFocalTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableReturningUserExperienceFocalTweetSafetyLevelParam
  }
  case object Revenue extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRevenueSafetyLevelParam
  }
  case object RitoActionedTweetTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableRitoActionedTweetTimelineParam
  }
  case object SafeSearchMinimal extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSafeSearchMinimalSafetyLevelParam
  }
  case object SafeSearchStrict extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSafeSearchStrictSafetyLevelParam
  }
  case object SearchHydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchHydrationSafetyLevelParam
  }
  case object SearchLatest extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchLatestSafetyLevelParam
  }
  case object SearchMixerSrpMinimal extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchMixerSrpMinimalSafetyLevelParam
  }
  case object SearchMixerSrpStrict extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchMixerSrpStrictSafetyLevelParam
  }
  case object SearchPeopleSrp extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPeopleSearchResultPageSafetyLevelParam
  }
  case object SearchPeopleTypeahead extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPeopleTypeaheadSafetyLevelParam
  }
  case object SearchPhoto extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchPhotoSafetyLevelParam
  }
  case object ShoppingManagerSpyMode extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableShoppingManagerSpyModeSafetyLevelParam
  }
  case object StratoExtLimitedEngagements extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableStratoExtLimitedEngagementsSafetyLevelParam
  }
  case object SearchTop extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchTopSafetyLevelParam
  }
  case object SearchTopQig extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchTopQigSafetyLevelParam
  }
  case object SearchTrendTakeoverPromotedTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = SearchTrendTakeoverPromotedTweetSafetyLevelParam
  }
  case object SearchVideo extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchVideoSafetyLevelParam
  }
  case object SearchBlenderUserRules extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchBlenderUserRulesSafetyLevelParam
  }
  case object SearchLatestUserRules extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSearchLatestUserRulesSafetyLevelParam
  }
  case object SignalsReactions extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSignalsReactionsSafetyLevelParam
  }
  case object SignalsTweetReactingUsers extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSignalsTweetReactingUsersSafetyLevelParam
  }
  case object SocialProof extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSocialProofSafetyLevelParam
  }
  case object SoftInterventionPivot extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSoftInterventionPivotSafetyLevelParam
  }
  case object SpaceFleetline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceFleetlineSafetyLevelParam
  }
  case object SpaceHomeTimelineUpranking extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceHomeTimelineUprankingSafetyLevelParam
  }
  case object SpaceJoinScreen extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceJoinScreenSafetyLevelParam
  }
  case object SpaceNotifications extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceNotificationSafetyLevelParam
  }
  case object Spaces extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesSafetyLevelParam
  }
  case object SpacesParticipants extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesParticipantsSafetyLevelParam
  }
  case object SpacesSellerApplicationStatus extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableSpacesSellerApplicationStatusSafetyLevelParam
  }
  case object SpacesSharing extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpacesSharingSafetyLevelParam
  }
  case object SpaceTweetAvatarHomeTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSpaceTweetAvatarHomeTimelineSafetyLevelParam
  }
  case object StickersTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableStickersTimelineSafetyLevelParam
  }
  case object StreamServices extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableStreamServicesSafetyLevelParam
  }
  case object SuperFollowerConnections extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSuperFollowerConnectionsSafetyLevelParam
  }
  case object SuperLike extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableSuperLikeSafetyLevelParam
  }
  case object Test extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTestSafetyLevelParam
  }
  case object TimelineConversations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineConversationsSafetyLevelParam
  }
  case object TimelineConversationsDownranking extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineConversationsDownrankingSafetyLevelParam
  }
  case object TimelineConversationsDownrankingMinimal extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineConversationsDownrankingMinimalSafetyLevelParam
  }
  case object TimelineFollowingActivity extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFollowingActivitySafetyLevelParam
  }
  case object TimelineHome extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeSafetyLevelParam
  }
  case object TimelineHomeCommunities extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeCommunitiesSafetyLevelParam
  }
  case object TimelineHomeHydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeHydrationSafetyLevelParam
  }
  case object TimelineHomePromotedHydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineHomePromotedHydrationSafetyLevelParam
  }
  case object TimelineHomeRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeRecommendationsSafetyLevelParam
  }
  case object TimelineHomeTopicFollowRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineHomeTopicFollowRecommendationsSafetyLevelParam
  }
  case object TimelineScorer extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineScorerSafetyLevelParam
  }
  case object TopicsLandingPageTopicRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTopicsLandingPageTopicRecommendationsSafetyLevelParam
  }
  case object ExploreRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableExploreRecommendationsSafetyLevelParam
  }
  case object TimelineModeratedTweetsHydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTimelineModeratedTweetsHydrationSafetyLevelParam
  }
  case object TimelineInjection extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineInjectionSafetyLevelParam
  }
  case object TimelineMentions extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineMentionsSafetyLevelParam
  }
  case object TimelineHomeLatest extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineHomeLatestSafetyLevelParam
  }
  case object TimelineLikedBy extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineLikedBySafetyLevelParam
  }
  case object TimelineRetweetedBy extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineRetweetedBySafetyLevelParam
  }
  case object TimelineSuperLikedBy extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineLikedBySafetyLevelParam
  }
  case object TimelineBookmark extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineBookmarkSafetyLevelParam
  }
  case object TimelineContentControls extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineContentControlsSafetyLevelParam
  }
  case object TimelineMedia extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineMediaSafetyLevelParam
  }
  case object TimelineReactiveBlending extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineReactiveBlendingSafetyLevelParam
  }
  case object TimelineFavorites extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFavoritesSafetyLevelParam
  }
  case object TimelineFavoritesSelfView extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFavoritesSelfViewSafetyLevelParam
  }
  case object TimelineLists extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineListsSafetyLevelParam
  }
  case object TimelineProfile extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSafetyLevelParam
  }
  case object TimelineProfileAll extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileAllSafetyLevelParam
  }

  case object TimelineProfileSpaces extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSpacesSafetyLevelParam
  }

  case object TimelineProfileSuperFollows extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineProfileSuperFollowsSafetyLevelParam
  }
  case object TimelineFocalTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTimelineFocalTweetSafetyLevelParam
  }
  case object Tombstoning extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTombstoningSafetyLevelParam
  }
  case object TopicRecommendations extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTopicRecommendationsSafetyLevelParam
  }
  case object TrendsRepresentativeTweet extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTrendsRepresentativeTweetSafetyLevelParam
  }
  case object TrustedFriendsUserList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTrustedFriendsUserListSafetyLevelParam
  }
  case object TwitterDelegateUserList extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterDelegateUserListSafetyLevelParam
  }
  case object TweetDetail extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetDetailSafetyLevelParam
  }
  case object TweetDetailNonToo extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetDetailNonTooSafetyLevelParam
  }
  case object TweetDetailWithInjectionsHydration extends SafetyLevel {
    override val enabledParam: SafetyLevelParam =
      EnableTweetDetailWithInjectionsHydrationSafetyLevelParam
  }
  case object TweetEngagers extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetEngagersSafetyLevelParam
  }
  case object TweetReplyNudge extends SafetyLevel {
    override def enabledParam: SafetyLevelParam = EnableTweetReplyNudgeParam
  }
  case object TweetScopedTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetScopedTimelineSafetyLevelParam
  }
  case object TweetWritesApi extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetWritesApiSafetyLevelParam
  }
  case object TwitterArticleCompose extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleComposeSafetyLevelParam
  }
  case object TwitterArticleProfileTab extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleProfileTabSafetyLevelParam
  }
  case object TwitterArticleRead extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTwitterArticleReadSafetyLevelParam
  }
  case object UserProfileHeader extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserProfileHeaderSafetyLevelParam
  }
  case object UserMilestoneRecommendation extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserMilestoneRecommendationSafetyLevelParam
  }
  case object UserScopedTimeline extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserScopedTimelineSafetyLevelParam
  }
  case object UserSearchSrp extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSearchSrpSafetyLevelParam
  }
  case object UserSearchTypeahead extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSearchTypeaheadSafetyLevelParam
  }
  case object UserSelfViewOnly extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSelfViewOnlySafetyLevelParam
  }
  case object UserSettings extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableUserSettingsSafetyLevelParam
  }
  case object VideoAds extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableVideoAdsSafetyLevelParam
  }
  case object ZipbirdConsumerArchives extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableZipbirdConsumerArchivesSafetyLevelParam
  }
  case object TweetAward extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableTweetAwardSafetyLevelParam
  }

  case object DeprecatedSafetyLevel extends SafetyLevel {
    override val enabledParam: SafetyLevelParam = EnableDeprecatedSafetyLevel
  }


  def fromThrift(safetyLevel: ThriftSafetyLevel): SafetyLevel =
    thriftToModelMap.get(safetyLevel).getOrElse(DeprecatedSafetyLevel)

  def toThrift(safetyLevel: SafetyLevel): ThriftSafetyLevel =
    modelToThriftMap
      .get(safetyLevel).getOrElse(ThriftSafetyLevel.EnumUnknownSafetyLevel(DeprecatedEnumValue))

  val List: Seq[SafetyLevel] =
    ThriftSafetyLevel.list.map(fromThrift).filter(_ != DeprecatedSafetyLevel)
}

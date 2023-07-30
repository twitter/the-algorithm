package com.X.visibility.models

import com.X.visibility.models.SafetyLevel.AccessInternalPromotedContent
import com.X.visibility.models.SafetyLevel.AdsBusinessSettings
import com.X.visibility.models.SafetyLevel.AdsCampaign
import com.X.visibility.models.SafetyLevel.AdsManager
import com.X.visibility.models.SafetyLevel.AdsReportingDashboard
import com.X.visibility.models.SafetyLevel.AllSubscribedLists
import com.X.visibility.models.SafetyLevel.Appeals
import com.X.visibility.models.SafetyLevel.ArticleTweetTimeline
import com.X.visibility.models.SafetyLevel.BaseQig
import com.X.visibility.models.SafetyLevel.BirdwatchNeedsYourHelpNotifications
import com.X.visibility.models.SafetyLevel.BirdwatchNoteAuthor
import com.X.visibility.models.SafetyLevel.BirdwatchNoteTweetsTimeline
import com.X.visibility.models.SafetyLevel.BlockMuteUsersTimeline
import com.X.visibility.models.SafetyLevel.BrandSafety
import com.X.visibility.models.SafetyLevel.CardPollVoting
import com.X.visibility.models.SafetyLevel.CardsService
import com.X.visibility.models.SafetyLevel.ContentControlToolInstall
import com.X.visibility.models.SafetyLevel.ConversationFocalPrehydration
import com.X.visibility.models.SafetyLevel.ConversationFocalTweet
import com.X.visibility.models.SafetyLevel.ConversationInjectedTweet
import com.X.visibility.models.SafetyLevel.ConversationReply
import com.X.visibility.models.SafetyLevel.CuratedTrendsRepresentativeTweet
import com.X.visibility.models.SafetyLevel.CurationPolicyViolations
import com.X.visibility.models.SafetyLevel.DesFollowingAndFollowersUserList
import com.X.visibility.models.SafetyLevel.DesHomeTimeline
import com.X.visibility.models.SafetyLevel.DesQuoteTweetTimeline
import com.X.visibility.models.SafetyLevel.DesRealtime
import com.X.visibility.models.SafetyLevel.DesRealtimeSpamEnrichment
import com.X.visibility.models.SafetyLevel.DesRealtimeTweetFilter
import com.X.visibility.models.SafetyLevel.DesRetweetingUsers
import com.X.visibility.models.SafetyLevel.DesTweetDetail
import com.X.visibility.models.SafetyLevel.DesTweetLikingUsers
import com.X.visibility.models.SafetyLevel.DesUserBookmarks
import com.X.visibility.models.SafetyLevel.DesUserLikedTweets
import com.X.visibility.models.SafetyLevel.DesUserMentions
import com.X.visibility.models.SafetyLevel.DesUserTweets
import com.X.visibility.models.SafetyLevel.DevPlatformComplianceStream
import com.X.visibility.models.SafetyLevel.DevPlatformGetListTweets
import com.X.visibility.models.SafetyLevel.DirectMessages
import com.X.visibility.models.SafetyLevel.DirectMessagesConversationList
import com.X.visibility.models.SafetyLevel.DirectMessagesConversationTimeline
import com.X.visibility.models.SafetyLevel.DirectMessagesInbox
import com.X.visibility.models.SafetyLevel.DirectMessagesMutedUsers
import com.X.visibility.models.SafetyLevel.DirectMessagesPinned
import com.X.visibility.models.SafetyLevel.DirectMessagesSearch
import com.X.visibility.models.SafetyLevel.EditHistoryTimeline
import com.X.visibility.models.SafetyLevel.ElevatedQuoteTweetTimeline
import com.X.visibility.models.SafetyLevel.EmbedTweetMarkup
import com.X.visibility.models.SafetyLevel.EmbeddedTweet
import com.X.visibility.models.SafetyLevel.EmbedsPublicInterestNotice
import com.X.visibility.models.SafetyLevel.ExploreRecommendations
import com.X.visibility.models.SafetyLevel.FilterAll
import com.X.visibility.models.SafetyLevel.FilterAllPlaceholder
import com.X.visibility.models.SafetyLevel.FilterDefault
import com.X.visibility.models.SafetyLevel.FilterNone
import com.X.visibility.models.SafetyLevel.FollowedTopicsTimeline
import com.X.visibility.models.SafetyLevel.FollowerConnections
import com.X.visibility.models.SafetyLevel.FollowingAndFollowersUserList
import com.X.visibility.models.SafetyLevel.ForDevelopmentOnly
import com.X.visibility.models.SafetyLevel.FriendsFollowingList
import com.X.visibility.models.SafetyLevel.GraphqlDefault
import com.X.visibility.models.SafetyLevel.GryphonDecksAndColumns
import com.X.visibility.models.SafetyLevel.HumanizationNudge
import com.X.visibility.models.SafetyLevel.KitchenSinkDevelopment
import com.X.visibility.models.SafetyLevel.ListHeader
import com.X.visibility.models.SafetyLevel.ListMemberships
import com.X.visibility.models.SafetyLevel.ListOwnerships
import com.X.visibility.models.SafetyLevel.ListRecommendations
import com.X.visibility.models.SafetyLevel.ListSearch
import com.X.visibility.models.SafetyLevel.ListSubscriptions
import com.X.visibility.models.SafetyLevel.LivePipelineEngagementCounts
import com.X.visibility.models.SafetyLevel.LiveVideoTimeline
import com.X.visibility.models.SafetyLevel.MagicRecs
import com.X.visibility.models.SafetyLevel.MagicRecsAggressive
import com.X.visibility.models.SafetyLevel.MagicRecsAggressiveV2
import com.X.visibility.models.SafetyLevel.MagicRecsV2
import com.X.visibility.models.SafetyLevel.Minimal
import com.X.visibility.models.SafetyLevel.ModeratedTweetsTimeline
import com.X.visibility.models.SafetyLevel.Moments
import com.X.visibility.models.SafetyLevel.NearbyTimeline
import com.X.visibility.models.SafetyLevel.NewUserExperience
import com.X.visibility.models.SafetyLevel.NotificationsIbis
import com.X.visibility.models.SafetyLevel.NotificationsPlatform
import com.X.visibility.models.SafetyLevel.NotificationsPlatformPush
import com.X.visibility.models.SafetyLevel.NotificationsQig
import com.X.visibility.models.SafetyLevel.NotificationsRead
import com.X.visibility.models.SafetyLevel.NotificationsTimelineDeviceFollow
import com.X.visibility.models.SafetyLevel.NotificationsWrite
import com.X.visibility.models.SafetyLevel.NotificationsWriterTweetHydrator
import com.X.visibility.models.SafetyLevel.NotificationsWriterV2
import com.X.visibility.models.SafetyLevel.ProfileMixerFavorites
import com.X.visibility.models.SafetyLevel.ProfileMixerMedia
import com.X.visibility.models.SafetyLevel.QuickPromoteTweetEligibility
import com.X.visibility.models.SafetyLevel.QuoteTweetTimeline
import com.X.visibility.models.SafetyLevel.QuotedTweetRules
import com.X.visibility.models.SafetyLevel.RecosVideo
import com.X.visibility.models.SafetyLevel.RecosWritePath
import com.X.visibility.models.SafetyLevel.RepliesGrouping
import com.X.visibility.models.SafetyLevel.ReportCenter
import com.X.visibility.models.SafetyLevel.ReturningUserExperienceFocalTweet
import com.X.visibility.models.SafetyLevel.Revenue
import com.X.visibility.models.SafetyLevel.SafeSearchMinimal
import com.X.visibility.models.SafetyLevel.SafeSearchStrict
import com.X.visibility.models.SafetyLevel.SearchBlenderUserRules
import com.X.visibility.models.SafetyLevel.SearchHydration
import com.X.visibility.models.SafetyLevel.SearchLatest
import com.X.visibility.models.SafetyLevel.SearchLatestUserRules
import com.X.visibility.models.SafetyLevel.SearchMixerSrpMinimal
import com.X.visibility.models.SafetyLevel.SearchMixerSrpStrict
import com.X.visibility.models.SafetyLevel.SearchPeopleSrp
import com.X.visibility.models.SafetyLevel.SearchPeopleTypeahead
import com.X.visibility.models.SafetyLevel.SearchPhoto
import com.X.visibility.models.SafetyLevel.SearchTop
import com.X.visibility.models.SafetyLevel.SearchTopQig
import com.X.visibility.models.SafetyLevel.SearchTrendTakeoverPromotedTweet
import com.X.visibility.models.SafetyLevel.SearchVideo
import com.X.visibility.models.SafetyLevel.ShoppingManagerSpyMode
import com.X.visibility.models.SafetyLevel.SignalsReactions
import com.X.visibility.models.SafetyLevel.SignalsTweetReactingUsers
import com.X.visibility.models.SafetyLevel.SoftInterventionPivot
import com.X.visibility.models.SafetyLevel.SpaceFleetline
import com.X.visibility.models.SafetyLevel.SpaceHomeTimelineUpranking
import com.X.visibility.models.SafetyLevel.SpaceJoinScreen
import com.X.visibility.models.SafetyLevel.SpaceNotifications
import com.X.visibility.models.SafetyLevel.SpaceTweetAvatarHomeTimeline
import com.X.visibility.models.SafetyLevel.SpacesParticipants
import com.X.visibility.models.SafetyLevel.SpacesSellerApplicationStatus
import com.X.visibility.models.SafetyLevel.SpacesSharing
import com.X.visibility.models.SafetyLevel.StickersTimeline
import com.X.visibility.models.SafetyLevel.StratoExtLimitedEngagements
import com.X.visibility.models.SafetyLevel.StreamServices
import com.X.visibility.models.SafetyLevel.SuperFollowerConnections
import com.X.visibility.models.SafetyLevel.SuperLike
import com.X.visibility.models.SafetyLevel.Test
import com.X.visibility.models.SafetyLevel.TimelineBookmark
import com.X.visibility.models.SafetyLevel.TimelineContentControls
import com.X.visibility.models.SafetyLevel.TimelineConversations
import com.X.visibility.models.SafetyLevel.TimelineConversationsDownranking
import com.X.visibility.models.SafetyLevel.TimelineConversationsDownrankingMinimal
import com.X.visibility.models.SafetyLevel.TimelineFavorites
import com.X.visibility.models.SafetyLevel.TimelineFavoritesSelfView
import com.X.visibility.models.SafetyLevel.TimelineFocalTweet
import com.X.visibility.models.SafetyLevel.TimelineFollowingActivity
import com.X.visibility.models.SafetyLevel.TimelineHomeCommunities
import com.X.visibility.models.SafetyLevel.TimelineHomeHydration
import com.X.visibility.models.SafetyLevel.TimelineHomeLatest
import com.X.visibility.models.SafetyLevel.TimelineHomePromotedHydration
import com.X.visibility.models.SafetyLevel.TimelineHomeRecommendations
import com.X.visibility.models.SafetyLevel.TimelineHomeTopicFollowRecommendations
import com.X.visibility.models.SafetyLevel.TimelineInjection
import com.X.visibility.models.SafetyLevel.TimelineLikedBy
import com.X.visibility.models.SafetyLevel.TimelineLists
import com.X.visibility.models.SafetyLevel.TimelineMedia
import com.X.visibility.models.SafetyLevel.TimelineMentions
import com.X.visibility.models.SafetyLevel.TimelineModeratedTweetsHydration
import com.X.visibility.models.SafetyLevel.TimelineProfileAll
import com.X.visibility.models.SafetyLevel.TimelineProfileSpaces
import com.X.visibility.models.SafetyLevel.TimelineProfileSuperFollows
import com.X.visibility.models.SafetyLevel.TimelineScorer
import com.X.visibility.models.SafetyLevel.Tombstoning
import com.X.visibility.models.SafetyLevel.TopicsLandingPageTopicRecommendations
import com.X.visibility.models.SafetyLevel.TrendsRepresentativeTweet
import com.X.visibility.models.SafetyLevel.TrustedFriendsUserList
import com.X.visibility.models.SafetyLevel.TweetDetail
import com.X.visibility.models.SafetyLevel.TweetDetailNonToo
import com.X.visibility.models.SafetyLevel.TweetDetailWithInjectionsHydration
import com.X.visibility.models.SafetyLevel.TweetEngagers
import com.X.visibility.models.SafetyLevel.TweetReplyNudge
import com.X.visibility.models.SafetyLevel.TweetWritesApi
import com.X.visibility.models.SafetyLevel.XArticleCompose
import com.X.visibility.models.SafetyLevel.XArticleProfileTab
import com.X.visibility.models.SafetyLevel.XArticleRead
import com.X.visibility.models.SafetyLevel.UserMilestoneRecommendation
import com.X.visibility.models.SafetyLevel.UserProfileHeader
import com.X.visibility.models.SafetyLevel.UserSelfViewOnly
import com.X.visibility.models.SafetyLevel.UserSettings
import com.X.visibility.models.SafetyLevel.VideoAds
import com.X.visibility.models.SafetyLevel.WritePathLimitedActionsEnforcement
import com.X.visibility.models.SafetyLevel.ZipbirdConsumerArchives

sealed trait SafetyLevelGroup { val levels: Set[SafetyLevel] }

object SafetyLevelGroup {
  case object Ads extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AdsBusinessSettings,
      AdsCampaign,
      AdsManager,
      AdsReportingDashboard,
      BrandSafety,
      VideoAds,
      QuickPromoteTweetEligibility
    )
  }

  case object ArticleTimeline extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ArticleTweetTimeline,
    )
  }

  case object ArticleTweets extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      XArticleCompose,
      XArticleProfileTab,
      XArticleRead,
    )
  }

  case object Birdwatch extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      BirdwatchNoteAuthor,
      BirdwatchNoteTweetsTimeline,
      BirdwatchNeedsYourHelpNotifications,
    )
  }

  case object Cards extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      CardPollVoting,
      CardsService,
    )
  }

  case object Communities extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.Communities
    )
  }

  case object Conversation extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ConversationFocalPrehydration,
      ConversationFocalTweet,
      ConversationInjectedTweet,
      ConversationReply,
      Tombstoning,
    )
  }

  case object CreativeContainerService extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AccessInternalPromotedContent
    )
  }

  case object Des extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DevPlatformGetListTweets,
      DesFollowingAndFollowersUserList,
      DesHomeTimeline,
      DesQuoteTweetTimeline,
      DesRetweetingUsers,
      DesTweetDetail,
      DesTweetLikingUsers,
      DesUserBookmarks,
      DesUserLikedTweets,
      DesUserMentions,
      DesUserTweets,
      DevPlatformComplianceStream,
    )
  }

  case object DesStream extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DesRealtime,
      DesRealtimeSpamEnrichment,
      DesRealtimeTweetFilter,
    )
  }

  case object Dm extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      DirectMessages,
      DirectMessagesConversationList,
      DirectMessagesConversationTimeline,
      DirectMessagesInbox,
      DirectMessagesMutedUsers,
      DirectMessagesPinned,
      DirectMessagesSearch,
    )
  }

  case object Followers extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FollowedTopicsTimeline,
      FollowerConnections,
      FollowingAndFollowersUserList,
      FriendsFollowingList,
    )
  }

  case object Graphql extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FilterDefault,
      GraphqlDefault,
      SoftInterventionPivot,
    )
  }

  case object Jiminy extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      HumanizationNudge,
      TweetReplyNudge,
    )
  }

  case object Lists extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      AllSubscribedLists,
      ListHeader,
      ListMemberships,
      ListOwnerships,
      ListRecommendations,
      ListSearch,
      ListSubscriptions,
    )
  }

  case object Notifications extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      NotificationsIbis,
      NotificationsPlatform,
      NotificationsPlatformPush,
      NotificationsQig,
      NotificationsRead,
      NotificationsTimelineDeviceFollow,
      NotificationsWrite,
      NotificationsWriterTweetHydrator,
      NotificationsWriterV2,
    )
  }

  case object Other extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      CuratedTrendsRepresentativeTweet,
      CurationPolicyViolations,
      BaseQig,
      Appeals,
      ContentControlToolInstall,
      EditHistoryTimeline,
      ElevatedQuoteTweetTimeline,
      EmbeddedTweet,
      EmbedsPublicInterestNotice,
      EmbedTweetMarkup,
      ExploreRecommendations,
      WritePathLimitedActionsEnforcement,
      LiveVideoTimeline,
      LivePipelineEngagementCounts,
      Minimal,
      Moments,
      NearbyTimeline,
      NewUserExperience,
      QuoteTweetTimeline,
      QuotedTweetRules,
      ReportCenter,
      Revenue,
      ShoppingManagerSpyMode,
      StickersTimeline,
      SuperLike,
      TrendsRepresentativeTweet,
      TrustedFriendsUserList,
      GryphonDecksAndColumns,
      TweetEngagers,
      TweetWritesApi,
      UserMilestoneRecommendation,
      StreamServices,
      ZipbirdConsumerArchives
    )
  }

  case object Profile extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      UserProfileHeader,
      UserSelfViewOnly,
      UserSettings,
    )
  }

  case object Reactions extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SignalsReactions,
      SignalsTweetReactingUsers,
    )
  }

  case object Recommendations extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      MagicRecs,
      MagicRecsV2,
      MagicRecsAggressive,
      MagicRecsAggressiveV2,
      SafetyLevel.Recommendations,
      RecosVideo,
      RecosWritePath,
    )
  }

  case object ReturningUserExperience extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.ReturningUserExperience,
      ReturningUserExperienceFocalTweet,
    )
  }

  case object SafeSearch extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafeSearchMinimal,
      SafeSearchStrict,
    )
  }

  case object Search extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SearchHydration,
      SearchLatest,
      SearchTop,
      SearchTopQig,
      SearchPeopleSrp,
      SearchPeopleTypeahead,
      SearchPhoto,
      SearchTrendTakeoverPromotedTweet,
      SearchVideo,
      SearchBlenderUserRules,
      SearchLatestUserRules,
    )
  }

  case object SearchMixer extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SearchMixerSrpMinimal,
      SearchMixerSrpStrict,
    )
  }

  case object Socialproof extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.SocialProof
    )
  }

  case object Spaces extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SpaceFleetline,
      SpaceHomeTimelineUpranking,
      SpaceJoinScreen,
      SpaceNotifications,
      SafetyLevel.Spaces,
      SpacesParticipants,
      SpacesSellerApplicationStatus,
      SpacesSharing,
      SpaceTweetAvatarHomeTimeline,
    )
  }

  case object Strato extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      StratoExtLimitedEngagements
    )
  }

  case object Superfollows extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SuperFollowerConnections,
      TimelineProfileSuperFollows,
    )
  }

  case object Testing extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      ForDevelopmentOnly,
      KitchenSinkDevelopment,
      Test,
    )
  }

  case object Timeline extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      BlockMuteUsersTimeline,
      TimelineBookmark,
      TimelineContentControls,
      TimelineConversationsDownranking,
      TimelineConversationsDownrankingMinimal,
      TimelineFavorites,
      TimelineFavoritesSelfView,
      TimelineFollowingActivity,
      TimelineScorer,
      TimelineInjection,
      TimelineLikedBy,
      TimelineLists,
      TimelineMedia,
      TimelineMentions,
      ModeratedTweetsTimeline,
      TimelineModeratedTweetsHydration,
    )
  }

  case object TopicRecommendations extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TopicRecommendations,
      TopicsLandingPageTopicRecommendations,
    )
  }

  case object TimelineProfile extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TimelineProfile,
      TimelineProfileAll,
      TimelineProfileSpaces,
      TimelineMedia,
      ProfileMixerMedia,
      TimelineFavorites,
      ProfileMixerFavorites
    )
  }

  case object TimelineHome extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      SafetyLevel.TimelineHome,
      TimelineHomeCommunities,
      TimelineHomeHydration,
      TimelineHomeLatest,
      TimelineHomePromotedHydration,
      TimelineHomeRecommendations,
      TimelineHomeTopicFollowRecommendations,
    )
  }

  case object TlsApi extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      TimelineConversations,
      TimelineFocalTweet,
    )
  }

  case object TweetDetails extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      TweetDetail,
      TweetDetailNonToo,
      TweetDetailWithInjectionsHydration,
      RepliesGrouping,
    )
  }

  case object Special extends SafetyLevelGroup {
    override val levels: Set[SafetyLevel] = Set(
      FilterAll,
      FilterAllPlaceholder,
      FilterNone,
    )
  }
}

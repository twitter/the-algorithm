package com.twitter.visibility.features

import com.twitter.contenthealth.totwittericreplyfilter.thriftscala.FilterState
import com.twitter.gizmoduck.thriftscala.Label
import com.twitter.search.common.constants.thriftscala.ThriftQuerySource
import com.twitter.tseng.withholding.thriftscala.TakedownReason
import com.twitter.util.Duration
import com.twitter.util.Time
import com.twitter.visibility.models.TweetDeleteReason.TweetDeleteReason
import com.twitter.visibility.models._

case object AuthorId etwittertends Feature[Set[Long]]

case object ViewerId etwittertends Feature[Long]

case object AuthorIsProtected etwittertends Feature[Boolean]

case object AuthorIsSuspended etwittertends Feature[Boolean]

case object AuthorIsUnavailable etwittertends Feature[Boolean]

case object AuthorIsDeactivated etwittertends Feature[Boolean]

case object AuthorIsErased etwittertends Feature[Boolean]

case object AuthorIsOffboarded etwittertends Feature[Boolean]

case object AuthorIsVerified etwittertends Feature[Boolean]

case object AuthorIsBlueVerified etwittertends Feature[Boolean]

case object ViewerIsSuspended etwittertends Feature[Boolean]

case object ViewerIsDeactivated etwittertends Feature[Boolean]

case object AuthorFollowsViewer etwittertends Feature[Boolean]

case object AuthorUserLabels etwittertends Feature[Seq[Label]]

case object ViewerFollowsAuthorOfViolatingTweet etwittertends Feature[Boolean]

case object ViewerDoesNotFollowAuthorOfViolatingTweet etwittertends Feature[Boolean]

case object ViewerFollowsAuthor etwittertends Feature[Boolean]

case object ViewerBlocksAuthor etwittertends Feature[Boolean]

case object AuthorBlocksViewer etwittertends Feature[Boolean]

case object AuthorMutesViewer etwittertends Feature[Boolean]

case object ViewerMutesAuthor etwittertends Feature[Boolean]

case object AuthorReportsViewerAsSpam etwittertends Feature[Boolean]

case object ViewerReportsAuthorAsSpam etwittertends Feature[Boolean]

case object ViewerReportedTweet etwittertends Feature[Boolean]

case object ViewerMutesRetweetsFromAuthor etwittertends Feature[Boolean]

case object ViewerHasUniversalQualityFilterEnabled etwittertends Feature[Boolean]

case object ViewerIsProtected etwittertends Feature[Boolean]

case object ViewerIsSoftUser etwittertends Feature[Boolean]

case object TweetSafetyLabels etwittertends Feature[Seq[TweetSafetyLabel]]

case object SpaceSafetyLabels etwittertends Feature[Seq[SpaceSafetyLabel]]

case object MediaSafetyLabels etwittertends Feature[Seq[MediaSafetyLabel]]

case object TweetTakedownReasons etwittertends Feature[Seq[TakedownReason]]

case object AuthorTakedownReasons etwittertends Feature[Seq[TakedownReason]]

case object AuthorIsNsfwUser etwittertends Feature[Boolean]

case object AuthorIsNsfwAdmin etwittertends Feature[Boolean]

case object TweetHasNsfwUser etwittertends Feature[Boolean]

case object TweetHasNsfwAdmin etwittertends Feature[Boolean]

case object TweetHasMedia etwittertends Feature[Boolean]

case object CardHasMedia etwittertends Feature[Boolean]

case object TweetHasCard etwittertends Feature[Boolean]

case object ViewerMutesKeywordInTweetForHomeTimeline etwittertends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForTweetReplies etwittertends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForNotifications etwittertends Feature[MutedKeyword]

case object ViewerMutesKeywordInSpaceTitleForNotifications etwittertends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForAllSurfaces etwittertends Feature[MutedKeyword]

case object ViewerUserLabels etwittertends Feature[Seq[Label]]

case object RequestCountryCode etwittertends Feature[String]

case object RequestIsVerifiedCrawler etwittertends Feature[Boolean]

case object ViewerCountryCode etwittertends Feature[String]

case object TweetIsSelfReply etwittertends Feature[Boolean]

case object TweetIsNullcast etwittertends Feature[Boolean]

case object TweetTimestamp etwittertends Feature[Time]

case object TweetIsInnerQuotedTweet etwittertends Feature[Boolean]

case object TweetIsRetweet etwittertends Feature[Boolean]

case object TweetIsSourceTweet etwittertends Feature[Boolean]

case object TweetDeleteReason etwittertends Feature[TweetDeleteReason]

case object TweetReplyToParentTweetDuration etwittertends Feature[Duration]

case object TweetReplyToRootTweetDuration etwittertends Feature[Duration]

case object TweetHasCommunityConversationControl etwittertends Feature[Boolean]
case object TweetHasByInvitationConversationControl etwittertends Feature[Boolean]
case object TweetHasFollowersConversationControl etwittertends Feature[Boolean]
case object TweetConversationViewerIsInvited etwittertends Feature[Boolean]
case object TweetConversationViewerIsInvitedViaReplyMention etwittertends Feature[Boolean]
case object TweetConversationViewerIsRootAuthor etwittertends Feature[Boolean]
case object ConversationRootAuthorFollowsViewer etwittertends Feature[Boolean]
case object ViewerFollowsConversationRootAuthor etwittertends Feature[Boolean]

case object TweetIsEtwitterclusiveTweet etwittertends Feature[Boolean]
case object ViewerIsEtwitterclusiveTweetRootAuthor etwittertends Feature[Boolean]
case object ViewerSuperFollowsEtwitterclusiveTweetRootAuthor etwittertends Feature[Boolean]

case object TweetIsCommunityTweet etwittertends Feature[Boolean]

case object CommunityTweetCommunityNotFound etwittertends Feature[Boolean]

case object CommunityTweetCommunityDeleted etwittertends Feature[Boolean]

case object CommunityTweetCommunitySuspended etwittertends Feature[Boolean]

case object CommunityTweetCommunityVisible etwittertends Feature[Boolean]

case object CommunityTweetIsHidden etwittertends Feature[Boolean]

case object ViewerIsInternalCommunitiesAdmin etwittertends Feature[Boolean]

case object ViewerIsCommunityAdmin etwittertends Feature[Boolean]

case object ViewerIsCommunityModerator etwittertends Feature[Boolean]

case object ViewerIsCommunityMember etwittertends Feature[Boolean]

case object CommunityTweetAuthorIsRemoved etwittertends Feature[Boolean]

case object NotificationIsOnCommunityTweet etwittertends Feature[Boolean]

case object NotificationIsOnUnmentionedViewer etwittertends Feature[Boolean]

case object SearchResultsPageNumber etwittertends Feature[Int]

case object SearchCandidateCount etwittertends Feature[Int]

case object SearchQuerySource etwittertends Feature[ThriftQuerySource]

case object SearchQueryHasUser etwittertends Feature[Boolean]

case object TweetSemanticCoreAnnotations etwittertends Feature[Seq[SemanticCoreAnnotation]]

case object OuterAuthorId etwittertends Feature[Long]

case object AuthorBlocksOuterAuthor etwittertends Feature[Boolean]

case object OuterAuthorFollowsAuthor etwittertends Feature[Boolean]

case object OuterAuthorIsInnerAuthor etwittertends Feature[Boolean]

case object TweetIsModerated etwittertends Feature[Boolean]
case object FocalTweetId etwittertends Feature[Long]

case object TweetId etwittertends Feature[Long]

case object TweetConversationId etwittertends Feature[Long]
case object TweetParentId etwittertends Feature[Long]
case object ConversationRootAuthorIsVerified etwittertends Feature[Boolean]

case object ViewerOptInBlocking etwittertends Feature[Boolean]

case object ViewerOptInFiltering etwittertends Feature[Boolean]

case object ViewerRoles etwittertends Feature[Seq[String]] {
  val EmployeeRole = "employee"
}

case object TweetMisinformationPolicies etwittertends Feature[Seq[MisinformationPolicy]]

case object TweetEnglishMisinformationPolicies etwittertends Feature[Seq[MisinformationPolicy]]

case object HasInnerCircleOfFriendsRelationship etwittertends Feature[Boolean]

case object ViewerAge etwittertends Feature[UserAge]

case object HasDmcaMediaFeature etwittertends Feature[Boolean]

case object MediaGeoRestrictionsAllowList etwittertends Feature[Seq[String]]
case object MediaGeoRestrictionsDenyList etwittertends Feature[Seq[String]]

case object TweetIsTrustedFriendTweet etwittertends Feature[Boolean]
case object ViewerIsTrustedFriendTweetAuthor etwittertends Feature[Boolean]
case object ViewerIsTrustedFriendOfTweetAuthor etwittertends Feature[Boolean]

case object DmConversationIsOneToOneConversation etwittertends Feature[Boolean]
case object DmConversationHasEmptyTimeline etwittertends Feature[Boolean]
case object DmConversationHasValidLastReadableEventId etwittertends Feature[Boolean]
case object DmConversationInfoEtwitterists etwittertends Feature[Boolean]
case object DmConversationTimelineEtwitterists etwittertends Feature[Boolean]
case object ViewerIsDmConversationParticipant etwittertends Feature[Boolean]

case object DmEventIsMessageCreateEvent etwittertends Feature[Boolean]
case object DmEventIsWelcomeMessageCreateEvent etwittertends Feature[Boolean]
case object DmEventIsLastMessageReadUpdateEvent etwittertends Feature[Boolean]
case object DmEventIsDeleted etwittertends Feature[Boolean]
case object DmEventIsHidden etwittertends Feature[Boolean]
case object ViewerIsDmEventInitiatingUser etwittertends Feature[Boolean]
case object DmEventInOneToOneConversationWithUnavailableUser etwittertends Feature[Boolean]
case object DmEventIsJoinConversationEvent etwittertends Feature[Boolean]
case object DmEventIsConversationCreateEvent etwittertends Feature[Boolean]
case object DmEventInOneToOneConversation etwittertends Feature[Boolean]
case object DmEventIsTrustConversationEvent etwittertends Feature[Boolean]
case object DmEventIsCsFeedbackSubmitted etwittertends Feature[Boolean]
case object DmEventIsCsFeedbackDismissed etwittertends Feature[Boolean]
case object DmEventIsPerspectivalJoinConversationEvent etwittertends Feature[Boolean]

case object DmEventOccurredBeforeLastClearedEvent etwittertends Feature[Boolean]
case object DmEventOccurredBeforeJoinConversationEvent etwittertends Feature[Boolean]

case object CardUriHost etwittertends Feature[String]
case object CardIsPoll etwittertends Feature[Boolean]

case object TweetIsStaleTweet etwittertends Feature[Boolean]

case object TweetIsEditTweet etwittertends Feature[Boolean]

case object TweetIsLatestTweet etwittertends Feature[Boolean]

case object TweetIsInitialTweet etwittertends Feature[Boolean]

case object TweetIsCollabInvitationTweet etwittertends Feature[Boolean]

case object ViewerSensitiveMediaSettings etwittertends Feature[UserSensitiveMediaSettings]


case object TotwittericReplyFilterState etwittertends Feature[FilterState]


case object TotwittericReplyFilterConversationAuthorIsViewer etwittertends Feature[Boolean]

case object RawQuery etwittertends Feature[String]

case object AuthorScreenName etwittertends Feature[String]

case object TweetIsInternalPromotedContent etwittertends Feature[Boolean]

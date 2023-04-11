package com.twitter.visibility.features

import com.twitter.contenthealth.toxicreplyfilter.thriftscala.FilterState
import com.twitter.gizmoduck.thriftscala.Label
import com.twitter.search.common.constants.thriftscala.ThriftQuerySource
import com.twitter.tseng.withholding.thriftscala.TakedownReason
import com.twitter.util.Duration
import com.twitter.util.Time
import com.twitter.visibility.models.TweetDeleteReason.TweetDeleteReason
import com.twitter.visibility.models._

case object AuthorId extends Feature[Set[Long]]

case object ViewerId extends Feature[Long]

case object AuthorIsProtected extends Feature[Boolean]

case object AuthorIsSuspended extends Feature[Boolean]

case object AuthorIsUnavailable extends Feature[Boolean]

case object AuthorIsDeactivated extends Feature[Boolean]

case object AuthorIsErased extends Feature[Boolean]

case object AuthorIsOffboarded extends Feature[Boolean]

case object AuthorIsVerified extends Feature[Boolean]

case object AuthorIsBlueVerified extends Feature[Boolean]

case object ViewerIsSuspended extends Feature[Boolean]

case object ViewerIsDeactivated extends Feature[Boolean]

case object AuthorFollowsViewer extends Feature[Boolean]

case object AuthorUserLabels extends Feature[Seq[Label]]

case object ViewerFollowsAuthorOfViolatingTweet extends Feature[Boolean]

case object ViewerDoesNotFollowAuthorOfViolatingTweet extends Feature[Boolean]

case object ViewerFollowsAuthor extends Feature[Boolean]

case object ViewerBlocksAuthor extends Feature[Boolean]

case object AuthorBlocksViewer extends Feature[Boolean]

case object AuthorMutesViewer extends Feature[Boolean]

case object ViewerMutesAuthor extends Feature[Boolean]

case object AuthorReportsViewerAsSpam extends Feature[Boolean]

case object ViewerReportsAuthorAsSpam extends Feature[Boolean]

case object ViewerReportedTweet extends Feature[Boolean]

case object ViewerMutesRetweetsFromAuthor extends Feature[Boolean]

case object ViewerHasUniversalQualityFilterEnabled extends Feature[Boolean]

case object ViewerIsProtected extends Feature[Boolean]

case object ViewerIsSoftUser extends Feature[Boolean]

case object TweetSafetyLabels extends Feature[Seq[TweetSafetyLabel]]

case object SpaceSafetyLabels extends Feature[Seq[SpaceSafetyLabel]]

case object MediaSafetyLabels extends Feature[Seq[MediaSafetyLabel]]

case object TweetTakedownReasons extends Feature[Seq[TakedownReason]]

case object AuthorTakedownReasons extends Feature[Seq[TakedownReason]]

case object AuthorIsNsfwUser extends Feature[Boolean]

case object AuthorIsNsfwAdmin extends Feature[Boolean]

case object TweetHasNsfwUser extends Feature[Boolean]

case object TweetHasNsfwAdmin extends Feature[Boolean]

case object TweetHasMedia extends Feature[Boolean]

case object CardHasMedia extends Feature[Boolean]

case object TweetHasCard extends Feature[Boolean]

case object ViewerMutesKeywordInTweetForHomeTimeline extends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForTweetReplies extends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForNotifications extends Feature[MutedKeyword]

case object ViewerMutesKeywordInSpaceTitleForNotifications extends Feature[MutedKeyword]

case object ViewerMutesKeywordInTweetForAllSurfaces extends Feature[MutedKeyword]

case object ViewerUserLabels extends Feature[Seq[Label]]

case object RequestCountryCode extends Feature[String]

case object RequestIsVerifiedCrawler extends Feature[Boolean]

case object ViewerCountryCode extends Feature[String]

case object TweetIsSelfReply extends Feature[Boolean]

case object TweetIsNullcast extends Feature[Boolean]

case object TweetTimestamp extends Feature[Time]

case object TweetIsInnerQuotedTweet extends Feature[Boolean]

case object TweetIsRetweet extends Feature[Boolean]

case object TweetIsSourceTweet extends Feature[Boolean]

case object TweetDeleteReason extends Feature[TweetDeleteReason]

case object TweetReplyToParentTweetDuration extends Feature[Duration]

case object TweetReplyToRootTweetDuration extends Feature[Duration]

case object TweetHasCommunityConversationControl extends Feature[Boolean]
case object TweetHasByInvitationConversationControl extends Feature[Boolean]
case object TweetHasFollowersConversationControl extends Feature[Boolean]
case object TweetConversationViewerIsInvited extends Feature[Boolean]
case object TweetConversationViewerIsInvitedViaReplyMention extends Feature[Boolean]
case object TweetConversationViewerIsRootAuthor extends Feature[Boolean]
case object ConversationRootAuthorFollowsViewer extends Feature[Boolean]
case object ViewerFollowsConversationRootAuthor extends Feature[Boolean]

case object TweetIsExclusiveTweet extends Feature[Boolean]
case object ViewerIsExclusiveTweetRootAuthor extends Feature[Boolean]
case object ViewerSuperFollowsExclusiveTweetRootAuthor extends Feature[Boolean]

case object TweetIsCommunityTweet extends Feature[Boolean]

case object CommunityTweetCommunityNotFound extends Feature[Boolean]

case object CommunityTweetCommunityDeleted extends Feature[Boolean]

case object CommunityTweetCommunitySuspended extends Feature[Boolean]

case object CommunityTweetCommunityVisible extends Feature[Boolean]

case object CommunityTweetIsHidden extends Feature[Boolean]

case object ViewerIsInternalCommunitiesAdmin extends Feature[Boolean]

case object ViewerIsCommunityAdmin extends Feature[Boolean]

case object ViewerIsCommunityModerator extends Feature[Boolean]

case object ViewerIsCommunityMember extends Feature[Boolean]

case object CommunityTweetAuthorIsRemoved extends Feature[Boolean]

case object NotificationIsOnCommunityTweet extends Feature[Boolean]

case object NotificationIsOnUnmentionedViewer extends Feature[Boolean]

case object SearchResultsPageNumber extends Feature[Int]

case object SearchCandidateCount extends Feature[Int]

case object SearchQuerySource extends Feature[ThriftQuerySource]

case object SearchQueryHasUser extends Feature[Boolean]

case object TweetSemanticCoreAnnotations extends Feature[Seq[SemanticCoreAnnotation]]

case object OuterAuthorId extends Feature[Long]

case object AuthorBlocksOuterAuthor extends Feature[Boolean]

case object OuterAuthorFollowsAuthor extends Feature[Boolean]

case object OuterAuthorIsInnerAuthor extends Feature[Boolean]

case object TweetIsModerated extends Feature[Boolean]
case object FocalTweetId extends Feature[Long]

case object TweetId extends Feature[Long]

case object TweetConversationId extends Feature[Long]
case object TweetParentId extends Feature[Long]
case object ConversationRootAuthorIsVerified extends Feature[Boolean]

case object ViewerOptInBlocking extends Feature[Boolean]

case object ViewerOptInFiltering extends Feature[Boolean]

case object ViewerRoles extends Feature[Seq[String]] {
  val EmployeeRole = "employee"
}

case object TweetMisinformationPolicies extends Feature[Seq[MisinformationPolicy]]

case object TweetEnglishMisinformationPolicies extends Feature[Seq[MisinformationPolicy]]

case object HasInnerCircleOfFriendsRelationship extends Feature[Boolean]

case object ViewerAge extends Feature[UserAge]

case object HasDmcaMediaFeature extends Feature[Boolean]

case object MediaGeoRestrictionsAllowList extends Feature[Seq[String]]
case object MediaGeoRestrictionsDenyList extends Feature[Seq[String]]

case object TweetIsTrustedFriendTweet extends Feature[Boolean]
case object ViewerIsTrustedFriendTweetAuthor extends Feature[Boolean]
case object ViewerIsTrustedFriendOfTweetAuthor extends Feature[Boolean]

case object DmConversationIsOneToOneConversation extends Feature[Boolean]
case object DmConversationHasEmptyTimeline extends Feature[Boolean]
case object DmConversationHasValidLastReadableEventId extends Feature[Boolean]
case object DmConversationInfoExists extends Feature[Boolean]
case object DmConversationTimelineExists extends Feature[Boolean]
case object ViewerIsDmConversationParticipant extends Feature[Boolean]

case object DmEventIsMessageCreateEvent extends Feature[Boolean]
case object DmEventIsWelcomeMessageCreateEvent extends Feature[Boolean]
case object DmEventIsLastMessageReadUpdateEvent extends Feature[Boolean]
case object DmEventIsDeleted extends Feature[Boolean]
case object DmEventIsHidden extends Feature[Boolean]
case object ViewerIsDmEventInitiatingUser extends Feature[Boolean]
case object DmEventInOneToOneConversationWithUnavailableUser extends Feature[Boolean]
case object DmEventIsJoinConversationEvent extends Feature[Boolean]
case object DmEventIsConversationCreateEvent extends Feature[Boolean]
case object DmEventInOneToOneConversation extends Feature[Boolean]
case object DmEventIsTrustConversationEvent extends Feature[Boolean]
case object DmEventIsCsFeedbackSubmitted extends Feature[Boolean]
case object DmEventIsCsFeedbackDismissed extends Feature[Boolean]
case object DmEventIsPerspectivalJoinConversationEvent extends Feature[Boolean]

case object DmEventOccurredBeforeLastClearedEvent extends Feature[Boolean]
case object DmEventOccurredBeforeJoinConversationEvent extends Feature[Boolean]

case object CardUriHost extends Feature[String]
case object CardIsPoll extends Feature[Boolean]

case object TweetIsStaleTweet extends Feature[Boolean]

case object TweetIsEditTweet extends Feature[Boolean]

case object TweetIsLatestTweet extends Feature[Boolean]

case object TweetIsInitialTweet extends Feature[Boolean]

case object TweetIsCollabInvitationTweet extends Feature[Boolean]

case object ViewerSensitiveMediaSettings extends Feature[UserSensitiveMediaSettings]


case object ToxicReplyFilterState extends Feature[FilterState]


case object ToxicReplyFilterConversationAuthorIsViewer extends Feature[Boolean]

case object RawQuery extends Feature[String]

case object AuthorScreenName extends Feature[String]

case object TweetIsInternalPromotedContent extends Feature[Boolean]

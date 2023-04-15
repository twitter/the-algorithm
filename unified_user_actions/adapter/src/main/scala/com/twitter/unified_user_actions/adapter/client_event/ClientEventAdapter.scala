package com.twitter.unified_user_actions.adapter.client_event

import com.twitter.finagle.stats.NullStatsReceiver
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.clientapp.thriftscala.EventNamespace
import com.twitter.clientapp.thriftscala.LogEvent
import com.twitter.finatra.kafka.serde.UnKeyed
import com.twitter.unified_user_actions.adapter.AbstractAdapter
import com.twitter.unified_user_actions.adapter.client_event.ClientEventImpression._
import com.twitter.unified_user_actions.adapter.client_event.ClientEventEngagement._
import com.twitter.unified_user_actions.thriftscala.UnifiedUserAction
import scala.util.matching.Regex

class ClientEventAdapter extends AbstractAdapter[LogEvent, UnKeyed, UnifiedUserAction] {
  import ClientEventAdapter._

  override def adaptOneToKeyedMany(
    input: LogEvent,
    statsReceiver: StatsReceiver = NullStatsReceiver
  ): Seq[(UnKeyed, UnifiedUserAction)] =
    adaptEvent(input).map { e => (UnKeyed, e) }
}

object ClientEventAdapter {
  // Refer to go/cme-scribing and go/interaction-event-spec for details
  def isVideoEvent(element: String): Boolean = Seq[String](
    "gif_player",
    "periscope_player",
    "platform_amplify_card",
    "video_player",
    "vine_player").contains(element)

  /**
   * Tweet clicks on the Notification Tab on iOS are a special case because the `element` is different
   * from Tweet clicks everywhere else on the platform.
   *
   * For Notification Tab on iOS, `element` could be one of `user_mentioned_you`,
   * `user_mentioned_you_in_a_quote_tweet`, `user_replied_to_your_tweet`, or `user_quoted_your_tweet`.
   *
   * In other places, `element` = `tweet`.
   */
  def isTweetClickEvent(element: String): Boolean =
    Seq[String](
      "tweet",
      "user_mentioned_you",
      "user_mentioned_you_in_a_quote_tweet",
      "user_replied_to_your_tweet",
      "user_quoted_your_tweet"
    ).contains(element)

  final val validUASIosClientIds = Seq[Long](
    129032L, // Twitter for iPhone
    191841L // Twitter for iPad
  )
  // Twitter for Android
  final val validUASAndroidClientIds = Seq[Long](258901L)

  def adaptEvent(inputLogEvent: LogEvent): Seq[UnifiedUserAction] =
    Option(inputLogEvent).toSeq
      .filterNot { logEvent: LogEvent =>
        shouldIgnoreClientEvent(logEvent.eventNamespace)
      }
      .flatMap { logEvent: LogEvent =>
        val actionTypesPerEvent: Seq[BaseClientEvent] = logEvent.eventNamespace.toSeq.flatMap {
          name =>
            (name.page, name.section, name.component, name.element, name.action) match {
              case (_, _, _, _, Some("favorite")) => Seq(TweetFav)
              case (_, _, _, _, Some("unfavorite")) => Seq(TweetUnfav)
              case (_, _, Some("stream"), Some("linger"), Some("results")) =>
                Seq(TweetLingerImpression)
              case (_, _, Some("stream"), None, Some("results")) =>
                Seq(TweetRenderImpression)
              case (_, _, _, _, Some("send_reply")) => Seq(TweetReply)
              // Different clients may have different actions of the same "send quote"
              // but it turns out that both send_quote and retweet_with_comment should correspond to
              // "send quote"
              case (_, _, _, _, Some("send_quote_tweet")) |
                  (_, _, _, _, Some("retweet_with_comment")) =>
                Seq(TweetQuote)
              case (_, _, _, _, Some("retweet")) => Seq(TweetRetweet)
              case (_, _, _, _, Some("unretweet")) => Seq(TweetUnretweet)
              case (_, _, _, _, Some("reply")) => Seq(TweetClickReply)
              case (_, _, _, _, Some("quote")) => Seq(TweetClickQuote)
              case (_, _, _, Some(element), Some("playback_start")) if isVideoEvent(element) =>
                Seq(TweetVideoPlaybackStart)
              case (_, _, _, Some(element), Some("playback_complete")) if isVideoEvent(element) =>
                Seq(TweetVideoPlaybackComplete)
              case (_, _, _, Some(element), Some("playback_25")) if isVideoEvent(element) =>
                Seq(TweetVideoPlayback25)
              case (_, _, _, Some(element), Some("playback_50")) if isVideoEvent(element) =>
                Seq(TweetVideoPlayback50)
              case (_, _, _, Some(element), Some("playback_75")) if isVideoEvent(element) =>
                Seq(TweetVideoPlayback75)
              case (_, _, _, Some(element), Some("playback_95")) if isVideoEvent(element) =>
                Seq(TweetVideoPlayback95)
              case (_, _, _, Some(element), Some("play_from_tap")) if isVideoEvent(element) =>
                Seq(TweetVideoPlayFromTap)
              case (_, _, _, Some(element), Some("video_quality_view")) if isVideoEvent(element) =>
                Seq(TweetVideoQualityView)
              case (_, _, _, Some(element), Some("video_view")) if isVideoEvent(element) =>
                Seq(TweetVideoView)
              case (_, _, _, Some(element), Some("video_mrc_view")) if isVideoEvent(element) =>
                Seq(TweetVideoMrcView)
              case (_, _, _, Some(element), Some("view_threshold")) if isVideoEvent(element) =>
                Seq(TweetVideoViewThreshold)
              case (_, _, _, Some(element), Some("cta_url_click")) if isVideoEvent(element) =>
                Seq(TweetVideoCtaUrlClick)
              case (_, _, _, Some(element), Some("cta_watch_click")) if isVideoEvent(element) =>
                Seq(TweetVideoCtaWatchClick)
              case (_, _, _, Some("platform_photo_card"), Some("click")) => Seq(TweetPhotoExpand)
              case (_, _, _, Some("platform_card"), Some("click")) => Seq(CardClick)
              case (_, _, _, _, Some("open_app")) => Seq(CardOpenApp)
              case (_, _, _, _, Some("install_app")) => Seq(CardAppInstallAttempt)
              case (_, _, _, Some("platform_card"), Some("vote")) |
                  (_, _, _, Some("platform_forward_card"), Some("vote")) =>
                Seq(PollCardVote)
              case (_, _, _, Some("mention"), Some("click")) |
                  (_, _, _, _, Some("mention_click")) =>
                Seq(TweetClickMentionScreenName)
              case (_, _, _, Some(element), Some("click")) if isTweetClickEvent(element) =>
                Seq(TweetClick)
              case // Follow from the Topic page (or so-called landing page)
                  (_, _, _, Some("topic"), Some("follow")) |
                  // Actually not sure how this is generated ... but saw quite some events in BQ
                  (_, _, _, Some("social_proof"), Some("follow")) |
                  // Click on Tweet's caret menu of "Follow (the topic)", it needs to be:
                  // 1) user follows the Topic already, 2) and clicked on the "Unfollow Topic" first.
                  (_, _, _, Some("feedback_follow_topic"), Some("click")) =>
                Seq(TopicFollow)
              case (_, _, _, Some("topic"), Some("unfollow")) |
                  (_, _, _, Some("social_proof"), Some("unfollow")) |
                  (_, _, _, Some("feedback_unfollow_topic"), Some("click")) =>
                Seq(TopicUnfollow)
              case (_, _, _, Some("topic"), Some("not_interested")) |
                  (_, _, _, Some("feedback_not_interested_in_topic"), Some("click")) =>
                Seq(TopicNotInterestedIn)
              case (_, _, _, Some("topic"), Some("un_not_interested")) |
                  (_, _, _, Some("feedback_not_interested_in_topic"), Some("undo")) =>
                Seq(TopicUndoNotInterestedIn)
              case (_, _, _, Some("feedback_givefeedback"), Some("click")) =>
                Seq(TweetNotHelpful)
              case (_, _, _, Some("feedback_givefeedback"), Some("undo")) =>
                Seq(TweetUndoNotHelpful)
              case (_, _, _, Some("report_tweet"), Some("click")) |
                  (_, _, _, Some("report_tweet"), Some("done")) =>
                Seq(TweetReport)
              case (_, _, _, Some("feedback_dontlike"), Some("click")) =>
                Seq(TweetNotInterestedIn)
              case (_, _, _, Some("feedback_dontlike"), Some("undo")) =>
                Seq(TweetUndoNotInterestedIn)
              case (_, _, _, Some("feedback_notabouttopic"), Some("click")) =>
                Seq(TweetNotAboutTopic)
              case (_, _, _, Some("feedback_notabouttopic"), Some("undo")) =>
                Seq(TweetUndoNotAboutTopic)
              case (_, _, _, Some("feedback_notrecent"), Some("click")) =>
                Seq(TweetNotRecent)
              case (_, _, _, Some("feedback_notrecent"), Some("undo")) =>
                Seq(TweetUndoNotRecent)
              case (_, _, _, Some("feedback_seefewer"), Some("click")) =>
                Seq(TweetSeeFewer)
              case (_, _, _, Some("feedback_seefewer"), Some("undo")) =>
                Seq(TweetUndoSeeFewer)
              // Only when action = "submit" we get all fields in ReportDetails, such as reportType
              // See https://confluence.twitter.biz/pages/viewpage.action?spaceKey=HEALTH&title=Understanding+ReportDetails
              case (Some(page), _, _, Some("ticket"), Some("submit"))
                  if page.startsWith("report_") =>
                Seq(TweetReportServer)
              case (Some("profile"), _, _, _, Some("block")) =>
                Seq(ProfileBlock)
              case (Some("profile"), _, _, _, Some("unblock")) =>
                Seq(ProfileUnblock)
              case (Some("profile"), _, _, _, Some("mute_user")) =>
                Seq(ProfileMute)
              case (Some("profile"), _, _, _, Some("report")) =>
                Seq(ProfileReport)
              case (Some("profile"), _, _, _, Some("show")) =>
                Seq(ProfileShow)
              case (_, _, _, Some("follow"), Some("click")) => Seq(TweetFollowAuthor)
              case (_, _, _, _, Some("follow")) => Seq(TweetFollowAuthor, ProfileFollow)
              case (_, _, _, Some("unfollow"), Some("click")) => Seq(TweetUnfollowAuthor)
              case (_, _, _, _, Some("unfollow")) => Seq(TweetUnfollowAuthor)
              case (_, _, _, Some("block"), Some("click")) => Seq(TweetBlockAuthor)
              case (_, _, _, Some("unblock"), Some("click")) => Seq(TweetUnblockAuthor)
              case (_, _, _, Some("mute"), Some("click")) => Seq(TweetMuteAuthor)
              case (_, _, _, Some(element), Some("click")) if isTweetClickEvent(element) =>
                Seq(TweetClick)
              case (_, _, _, _, Some("profile_click")) => Seq(TweetClickProfile, ProfileClick)
              case (_, _, _, _, Some("share_menu_click")) => Seq(TweetClickShare)
              case (_, _, _, _, Some("copy_link")) => Seq(TweetShareViaCopyLink)
              case (_, _, _, _, Some("share_via_dm")) => Seq(TweetClickSendViaDirectMessage)
              case (_, _, _, _, Some("bookmark")) => Seq(TweetShareViaBookmark, TweetBookmark)
              case (_, _, _, _, Some("unbookmark")) => Seq(TweetUnbookmark)
              case (_, _, _, _, Some("hashtag_click")) |
                  // This scribe is triggered on mobile platforms (android/iphone) when user click on hashtag in a tweet.
                  (_, _, _, Some("hashtag"), Some("search")) =>
                Seq(TweetClickHashtag)
              case (_, _, _, _, Some("open_link")) => Seq(TweetOpenLink)
              case (_, _, _, _, Some("take_screenshot")) => Seq(TweetTakeScreenshot)
              case (_, _, _, Some("feedback_notrelevant"), Some("click")) =>
                Seq(TweetNotRelevant)
              case (_, _, _, Some("feedback_notrelevant"), Some("undo")) =>
                Seq(TweetUndoNotRelevant)
              case (_, _, _, _, Some("follow_attempt")) => Seq(ProfileFollowAttempt)
              case (_, _, _, _, Some("favorite_attempt")) => Seq(TweetFavoriteAttempt)
              case (_, _, _, _, Some("retweet_attempt")) => Seq(TweetRetweetAttempt)
              case (_, _, _, _, Some("reply_attempt")) => Seq(TweetReplyAttempt)
              case (_, _, _, _, Some("login")) => Seq(CTALoginClick)
              case (Some("login"), _, _, _, Some("show")) => Seq(CTALoginStart)
              case (Some("login"), _, _, _, Some("success")) => Seq(CTALoginSuccess)
              case (_, _, _, _, Some("signup")) => Seq(CTASignupClick)
              case (Some("signup"), _, _, _, Some("success")) => Seq(CTASignupSuccess)
              case // Android app running in the background
                  (Some("notification"), Some("status_bar"), None, _, Some("background_open")) |
                  // Android app running in the foreground
                  (Some("notification"), Some("status_bar"), None, _, Some("open")) |
                  // iOS app running in the background
                  (Some("notification"), Some("notification_center"), None, _, Some("open")) |
                  // iOS app running in the foreground
                  (None, Some("toasts"), Some("social"), Some("favorite"), Some("open")) |
                  // m5
                  (Some("app"), Some("push"), _, _, Some("open")) =>
                Seq(NotificationOpen)
              case (Some("ntab"), Some("all"), Some("urt"), _, Some("navigate")) =>
                Seq(NotificationClick)
              case (Some("ntab"), Some("all"), Some("urt"), _, Some("see_less_often")) =>
                Seq(NotificationSeeLessOften)
              case (Some("notification"), Some("status_bar"), None, _, Some("background_dismiss")) |
                  (Some("notification"), Some("status_bar"), None, _, Some("dismiss")) | (
                    Some("notification"),
                    Some("notification_center"),
                    None,
                    _,
                    Some("dismiss")
                  ) =>
                Seq(NotificationDismiss)
              case (_, _, _, Some("typeahead"), Some("click")) => Seq(TypeaheadClick)
              case (Some("search"), _, Some(component), _, Some("click"))
                  if component == "relevance_prompt_module" || component == "did_you_find_it_module" =>
                Seq(FeedbackPromptSubmit)
              case (Some("app"), Some("enter_background"), _, _, Some("become_inactive"))
                  if logEvent.logBase
                    .flatMap(_.clientAppId)
                    .exists(validUASIosClientIds.contains(_)) =>
                Seq(AppExit)
              case (Some("app"), _, _, _, Some("become_inactive"))
                  if logEvent.logBase
                    .flatMap(_.clientAppId)
                    .exists(validUASAndroidClientIds.contains(_)) =>
                Seq(AppExit)
              case (_, _, Some("gallery"), Some("photo"), Some("impression")) =>
                Seq(TweetGalleryImpression)
              case (_, _, _, _, _)
                  if TweetDetailsImpression.isTweetDetailsImpression(logEvent.eventNamespace) =>
                Seq(TweetDetailsImpression)
              case _ => Nil
            }
        }
        actionTypesPerEvent.map(_.toUnifiedUserAction(logEvent))
      }.flatten

  def shouldIgnoreClientEvent(eventNamespace: Option[EventNamespace]): Boolean =
    eventNamespace.exists { name =>
      (name.page, name.section, name.component, name.element, name.action) match {
        case (Some("ddg"), _, _, _, Some("experiment")) => true
        case (Some("qig_ranker"), _, _, _, _) => true
        case (Some("timelinemixer"), _, _, _, _) => true
        case (Some("timelineservice"), _, _, _, _) => true
        case (Some("tweetconvosvc"), _, _, _, _) => true
        case _ => false
      }
    }
}

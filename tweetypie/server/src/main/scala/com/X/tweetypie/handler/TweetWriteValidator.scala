package com.X.tweetypie.handler

import com.X.stitch.Stitch
import com.X.tweetypie.core.TweetCreateFailure
import com.X.tweetypie.repository.ConversationControlRepository
import com.X.tweetypie.repository.TweetQuery
import com.X.tweetypie.thriftscala.ExclusiveTweetControl
import com.X.tweetypie.thriftscala.ExclusiveTweetControlOptions
import com.X.tweetypie.thriftscala.QuotedTweet
import com.X.tweetypie.thriftscala.TrustedFriendsControl
import com.X.tweetypie.thriftscala.TrustedFriendsControlOptions
import com.X.tweetypie.thriftscala.TweetCreateState
import com.X.tweetypie.FutureEffect
import com.X.tweetypie.Gate
import com.X.tweetypie.TweetId
import com.X.tweetypie.UserId
import com.X.tweetypie.thriftscala.EditControl
import com.X.tweetypie.thriftscala.EditOptions
import com.X.visibility.writer.interfaces.tweets.TweetWriteEnforcementLibrary
import com.X.visibility.writer.interfaces.tweets.TweetWriteEnforcementRequest
import com.X.visibility.writer.models.ActorContext
import com.X.visibility.writer.Allow
import com.X.visibility.writer.Deny
import com.X.visibility.writer.DenyExclusiveTweetReply
import com.X.visibility.writer.DenyStaleTweetQuoteTweet
import com.X.visibility.writer.DenyStaleTweetReply
import com.X.visibility.writer.DenySuperFollowsCreate
import com.X.visibility.writer.DenyTrustedFriendsCreate
import com.X.visibility.writer.DenyTrustedFriendsQuoteTweet
import com.X.visibility.writer.DenyTrustedFriendsReply

object TweetWriteValidator {
  case class Request(
    conversationId: Option[TweetId],
    userId: UserId,
    exclusiveTweetControlOptions: Option[ExclusiveTweetControlOptions],
    replyToExclusiveTweetControl: Option[ExclusiveTweetControl],
    trustedFriendsControlOptions: Option[TrustedFriendsControlOptions],
    inReplyToTrustedFriendsControl: Option[TrustedFriendsControl],
    quotedTweetOpt: Option[QuotedTweet],
    inReplyToTweetId: Option[TweetId],
    inReplyToEditControl: Option[EditControl],
    editOptions: Option[EditOptions])

  type Type = FutureEffect[Request]

  def apply(
    convoCtlRepo: ConversationControlRepository.Type,
    tweetWriteEnforcementLibrary: TweetWriteEnforcementLibrary,
    enableExclusiveTweetControlValidation: Gate[Unit],
    enableTrustedFriendsControlValidation: Gate[Unit],
    enableStaleTweetValidation: Gate[Unit]
  ): FutureEffect[Request] =
    FutureEffect[Request] { request =>
      // We are creating up an empty TweetQuery.Options here so we can use the default
      // CacheControl value and avoid hard coding it here.
      val queryOptions = TweetQuery.Options(TweetQuery.Include())
      Stitch.run {
        for {
          convoCtl <- request.conversationId match {
            case Some(convoId) =>
              convoCtlRepo(
                convoId,
                queryOptions.cacheControl
              )
            case None =>
              Stitch.value(None)
          }

          result <- tweetWriteEnforcementLibrary(
            TweetWriteEnforcementRequest(
              rootConversationControl = convoCtl,
              convoId = request.conversationId,
              exclusiveTweetControlOptions = request.exclusiveTweetControlOptions,
              replyToExclusiveTweetControl = request.replyToExclusiveTweetControl,
              trustedFriendsControlOptions = request.trustedFriendsControlOptions,
              inReplyToTrustedFriendsControl = request.inReplyToTrustedFriendsControl,
              quotedTweetOpt = request.quotedTweetOpt,
              actorContext = ActorContext(request.userId),
              inReplyToTweetId = request.inReplyToTweetId,
              inReplyToEditControl = request.inReplyToEditControl,
              editOptions = request.editOptions
            ),
            enableExclusiveTweetControlValidation = enableExclusiveTweetControlValidation,
            enableTrustedFriendsControlValidation = enableTrustedFriendsControlValidation,
            enableStaleTweetValidation = enableStaleTweetValidation
          )
          _ <- result match {
            case Allow =>
              Stitch.Done
            case Deny =>
              Stitch.exception(TweetCreateFailure.State(TweetCreateState.ReplyTweetNotAllowed))
            case DenyExclusiveTweetReply =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.ExclusiveTweetEngagementNotAllowed))
            case DenySuperFollowsCreate =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.SuperFollowsCreateNotAuthorized))
            case DenyTrustedFriendsReply =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.TrustedFriendsEngagementNotAllowed))
            case DenyTrustedFriendsCreate =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.TrustedFriendsCreateNotAllowed))
            case DenyTrustedFriendsQuoteTweet =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.TrustedFriendsQuoteTweetNotAllowed))
            case DenyStaleTweetReply =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.StaleTweetEngagementNotAllowed))
            case DenyStaleTweetQuoteTweet =>
              Stitch.exception(
                TweetCreateFailure.State(TweetCreateState.StaleTweetQuoteTweetNotAllowed))
          }
        } yield ()
      }
    }
}

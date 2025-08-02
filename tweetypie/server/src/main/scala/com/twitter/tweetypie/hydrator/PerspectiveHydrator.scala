package com.twitter.tweetypie
package hydrator

import com.twitter.featureswitches.v2.FeatureSwitchResults
import com.twitter.spam.rtf.thriftscala.SafetyLevel
import com.twitter.stitch.Stitch
import com.twitter.stitch.timelineservice.TimelineService.GetPerspectives.Query
import com.twitter.timelineservice.{thriftscala => tls}
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository.PerspectiveRepository
import com.twitter.tweetypie.thriftscala.FieldByPath
import com.twitter.tweetypie.thriftscala.StatusPerspective

object PerspectiveHydrator {
  type Type = ValueHydrator[Option[StatusPerspective], Cttwitter]
  val hydratedField: FieldByPath = fieldByPath(Tweet.PerspectiveField)

  case class Cttwitter(featureSwitchResults: Option[FeatureSwitchResults], underlyingTweetCttwitter: TweetCttwitter)
      etwittertends TweetCttwitter.Protwittery

  val Types: Set[tls.PerspectiveType] =
    Set(
      tls.PerspectiveType.Reported,
      tls.PerspectiveType.Favorited,
      tls.PerspectiveType.Retweeted,
      tls.PerspectiveType.Bookmarked
    )

  val TypesWithoutBookmarked: Set[tls.PerspectiveType] =
    Set(
      tls.PerspectiveType.Reported,
      tls.PerspectiveType.Favorited,
      tls.PerspectiveType.Retweeted
    )

  private[this] val partialResult = ValueState.partial(None, hydratedField)

  val bookmarksPerspectiveHydrationEnabledKey = "bookmarks_perspective_hydration_enabled"

  def evaluatePerspectiveTypes(
    userId: Long,
    bookmarksPerspectiveDecider: Gate[Long],
    featureSwitchResults: Option[FeatureSwitchResults]
  ): Set[tls.PerspectiveType] = {
    if (bookmarksPerspectiveDecider(userId) ||
      featureSwitchResults
        .flatMap(_.getBoolean(bookmarksPerspectiveHydrationEnabledKey, false))
        .getOrElse(false))
      Types
    else
      TypesWithoutBookmarked
  }

  def apply(
    repo: PerspectiveRepository.Type,
    shouldHydrateBookmarksPerspective: Gate[Long],
    stats: StatsReceiver
  ): Type = {
    val statsByLevel =
      SafetyLevel.list.map(level => (level, stats.counter(level.name, "calls"))).toMap

    ValueHydrator[Option[StatusPerspective], Cttwitter] { (_, cttwitter) =>
      val res: Stitch[tls.TimelineEntryPerspective] = if (cttwitter.isRetweet) {
        Stitch.value(
          tls.TimelineEntryPerspective(
            favorited = false,
            retweetId = None,
            retweeted = false,
            reported = false,
            bookmarked = None
          )
        )
      } else {
        statsByLevel
          .getOrElse(cttwitter.opts.safetyLevel, stats.counter(cttwitter.opts.safetyLevel.name, "calls"))
          .incr()

        repo(
          Query(
            userId = cttwitter.opts.forUserId.get,
            tweetId = cttwitter.tweetId,
            types = evaluatePerspectiveTypes(
              cttwitter.opts.forUserId.get,
              shouldHydrateBookmarksPerspective,
              cttwitter.featureSwitchResults)
          ))
      }

      res.liftToTry.map {
        case Return(perspective) =>
          ValueState.modified(
            Some(
              StatusPerspective(
                userId = cttwitter.opts.forUserId.get,
                favorited = perspective.favorited,
                retweeted = perspective.retweeted,
                retweetId = perspective.retweetId,
                reported = perspective.reported,
                bookmarked = perspective.bookmarked
              )
            )
          )
        case _ => partialResult
      }

    }.onlyIf { (curr, cttwitter) =>
      curr.isEmpty &&
      cttwitter.opts.forUserId.nonEmpty &&
      (cttwitter.tweetFieldRequested(Tweet.PerspectiveField) || cttwitter.opts.etwittercludeReported)
    }
  }
}

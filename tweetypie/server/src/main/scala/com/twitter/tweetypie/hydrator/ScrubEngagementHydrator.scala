package com.twitter.tweetypie
package hydrator

import com.twitter.spam.rtf.thriftscala.FilteredReason
import com.twitter.tweetypie.core.FilteredState
import com.twitter.tweetypie.core.ValueState
import com.twitter.tweetypie.thriftscala._
import com.twitter.visibility.results.counts.EngagementCounts

/**
 * Redact Tweet.counts (StatusCounts) for some visibility results
 */
object ScrubEngagementHydrator {
  type Type = ValueHydrator[Option[StatusCounts], Cttwitter]

  case class Cttwitter(filteredState: Option[FilteredState.Suppress])

  def apply(): Type =
    ValueHydrator.map[Option[StatusCounts], Cttwitter] { (curr: Option[StatusCounts], cttwitter: Cttwitter) =>
      cttwitter.filteredState match {
        case Some(FilteredState.Suppress(FilteredReason.SafetyResult(result))) if curr.nonEmpty =>
          ValueState.delta(curr, EngagementCounts.scrubEngagementCounts(result.action, curr))
        case _ =>
          ValueState.unmodified(curr)
      }
    }
}

package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.thriftscala._

object ReportedTweetFilter {
  type Type = ValueHydrator[Unit, Cttwitter]

  object MissingPerspectiveError
      etwittertends TweetHydrationError("Cannot determine reported state because perspective is missing")

  case class Cttwitter(perspective: Option[StatusPerspective], underlyingTweetCttwitter: TweetCttwitter)
      etwittertends TweetCttwitter.Protwittery

  def apply(): Type =
    ValueHydrator[Unit, Cttwitter] { (_, cttwitter) =>
      cttwitter.perspective match {
        case Some(p) if !p.reported => ValueState.StitchUnmodifiedUnit
        case Some(_) => Stitch.etwitterception(FilteredState.Unavailable.Reported)
        case None => Stitch.etwitterception(MissingPerspectiveError)
      }
    }.onlyIf { (_, cttwitter) => cttwitter.opts.etwittercludeReported && cttwitter.opts.forUserId.isDefined }
}

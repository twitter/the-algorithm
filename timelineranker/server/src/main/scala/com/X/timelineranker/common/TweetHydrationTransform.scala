package com.X.timelineranker.common

import com.X.conversions.DurationOps._
import com.X.finagle.IndividualRequestTimeoutException
import com.X.search.earlybird.thriftscala.ThriftSearchResult
import com.X.servo.util.FutureArrow
import com.X.timelineranker.core.CandidateEnvelope
import com.X.timelineranker.core.HydratedTweets
import com.X.timelineranker.model.PartiallyHydratedTweet
import com.X.timelines.model.tweet.HydratedTweet
import com.X.util.Future

object TweetHydrationTransform {
  val EmptyHydratedTweets: HydratedTweets =
    HydratedTweets(Seq.empty[HydratedTweet], Seq.empty[HydratedTweet])
  val EmptyHydratedTweetsFuture: Future[HydratedTweets] = Future.value(EmptyHydratedTweets)
}

object CandidateTweetHydrationTransform extends TweetHydrationTransform {
  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    hydrate(
      searchResults = envelope.searchResults,
      envelope = envelope
    ).map { tweets => envelope.copy(hydratedTweets = tweets) }
  }
}

object SourceTweetHydrationTransform extends TweetHydrationTransform {
  override def apply(envelope: CandidateEnvelope): Future[CandidateEnvelope] = {
    hydrate(
      searchResults = envelope.sourceSearchResults,
      envelope = envelope
    ).map { tweets => envelope.copy(sourceHydratedTweets = tweets) }
  }
}

// Static IRTE to indicate timeout in tweet hydrator. Placeholder timeout duration of 0 millis is used
// since we are only concerned with the source of the exception.
object TweetHydrationTimeoutException extends IndividualRequestTimeoutException(0.millis) {
  serviceName = "tweetHydrator"
}

/**
 * Transform which hydrates tweets in the CandidateEnvelope
 **/
trait TweetHydrationTransform extends FutureArrow[CandidateEnvelope, CandidateEnvelope] {

  import TweetHydrationTransform._

  protected def hydrate(
    searchResults: Seq[ThriftSearchResult],
    envelope: CandidateEnvelope
  ): Future[HydratedTweets] = {
    if (searchResults.nonEmpty) {
      Future.value(
        HydratedTweets(searchResults.map(PartiallyHydratedTweet.fromSearchResult))
      )
    } else {
      EmptyHydratedTweetsFuture
    }
  }
}

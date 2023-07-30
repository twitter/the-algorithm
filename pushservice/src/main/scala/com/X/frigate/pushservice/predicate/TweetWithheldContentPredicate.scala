package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TweetDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.tweetypie.UserLocationAndTweet
import com.X.hermit.predicate.tweetypie.WithheldTweetPredicate
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.predicate.Predicate
import com.X.service.metastore.gen.thriftscala.Location
import com.X.util.Future

object TweetWithheldContentPredicate {
  val name = "withheld_content"
  val defaultLocation = Location(city = "", region = "", countryCode = "", confidence = 0.0)

  def apply(
  )(
    implicit statsReceiver: StatsReceiver
  ): NamedPredicate[PushCandidate with TweetDetails] = {
    Predicate
      .fromAsync { candidate: PushCandidate with TweetDetails =>
        candidate.tweet match {
          case Some(tweet) =>
            WithheldTweetPredicate(checkAllCountries = true)
              .apply(Seq(UserLocationAndTweet(defaultLocation, tweet)))
              .map(_.head)
          case None =>
            Future.value(false)
        }
      }
      .withStats(statsReceiver.scope(s"predicate_$name"))
      .withName(name)
  }
}

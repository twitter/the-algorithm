package com.X.frigate.pushservice.predicate

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetCandidate
import com.X.frigate.common.base.TweetDetails
import com.X.frigate.common.predicate.tweet._
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.hermit.predicate.NamedPredicate

class LoggedOutPreRankingPredicatesBuilder(implicit statsReceiver: StatsReceiver) {

  private val TweetPredicates = List[NamedPredicate[PushCandidate]](
    TweetObjectExistsPredicate[
      TweetCandidate with TweetDetails
    ].applyOnlyToTweetCandidatesWithTweetDetails
      .withName("tweet_object_exists"),
    PredicatesForCandidate.oldTweetRecsPredicate.applyOnlyToTweetCandidateWithTargetAndABDeciderAndMaxTweetAge
      .withName("old_tweet"),
    PredicatesForCandidate.tweetIsNotAreply.applyOnlyToTweetCandidateWithoutSocialContextWithTweetDetails
      .withName("tweet_candidate_not_a_reply"),
    TweetAuthorPredicates
      .recTweetAuthorUnsuitable[TweetCandidate with TweetAuthorDetails]
      .applyOnlyToTweetCandidateWithTweetAuthorDetails
      .withName("tweet_author_unsuitable")
  )

  final def build(): List[NamedPredicate[PushCandidate]] = {
    TweetPredicates
  }

}

object LoggedOutPreRankingPredicates {
  def apply(statsReceiver: StatsReceiver): List[NamedPredicate[PushCandidate]] =
    new LoggedOutPreRankingPredicatesBuilder()(statsReceiver).build()
}

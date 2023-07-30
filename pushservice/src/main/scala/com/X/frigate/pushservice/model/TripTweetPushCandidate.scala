package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.OutOfNetworkTweetCandidate
import com.X.frigate.common.base.TopicCandidate
import com.X.frigate.common.base.TripCandidate
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.OutOfNetworkTweetIbis2HydratorForCandidate
import com.X.frigate.pushservice.model.ntab.OutOfNetworkTweetNTabRequestHydrator
import com.X.frigate.pushservice.take.predicates.OutOfNetworkTweetPredicates
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.gizmoduck.thriftscala.User
import com.X.stitch.tweetypie.TweetyPie
import com.X.topiclisting.utt.LocalizedEntity
import com.X.trends.trip_v1.trip_tweets.thriftscala.TripDomain
import com.X.util.Future

class TripTweetPushCandidate(
  candidate: RawCandidate with OutOfNetworkTweetCandidate with TripCandidate,
  author: Future[Option[User]],
  copyIds: CopyIds
)(
  implicit stats: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with TripCandidate
    with TopicCandidate
    with OutOfNetworkTweetCandidate
    with TweetAuthorDetails
    with OutOfNetworkTweetNTabRequestHydrator
    with OutOfNetworkTweetIbis2HydratorForCandidate {
  override val statsReceiver: StatsReceiver = stats
  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer
  override val tweetId: Long = candidate.tweetId
  override lazy val tweetyPieResult: Option[TweetyPie.TweetyPieResult] =
    candidate.tweetyPieResult
  override lazy val tweetAuthor: Future[Option[User]] = author
  override val target: PushTypes.Target = candidate.target
  override lazy val commonRecType: CommonRecommendationType =
    candidate.commonRecType
  override val pushCopyId: Option[Int] = copyIds.pushCopyId
  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId
  override val copyAggregationId: Option[String] = copyIds.aggregationId
  override lazy val semanticCoreEntityId: Option[Long] = None
  override lazy val localizedUttEntity: Option[LocalizedEntity] = None
  override lazy val algorithmCR: Option[String] = None
  override val tripDomain: Option[collection.Set[TripDomain]] = candidate.tripDomain
}

case class TripTweetCandidatePredicates(override val config: Config)
    extends OutOfNetworkTweetPredicates[TripTweetPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

}

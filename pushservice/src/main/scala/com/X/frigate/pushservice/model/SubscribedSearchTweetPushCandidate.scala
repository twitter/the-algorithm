package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.SubscribedSearchTweetCandidate
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.SubscribedSearchTweetIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.SubscribedSearchTweetNtabRequestHydrator
import com.X.frigate.pushservice.take.predicates.BasicTweetPredicatesForRFPH
import com.X.gizmoduck.thriftscala.User
import com.X.stitch.tweetypie.TweetyPie
import com.X.util.Future

class SubscribedSearchTweetPushCandidate(
  candidate: RawCandidate with SubscribedSearchTweetCandidate,
  author: Option[User],
  copyIds: CopyIds
)(
  implicit stats: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with SubscribedSearchTweetCandidate
    with TweetAuthorDetails
    with SubscribedSearchTweetIbis2Hydrator
    with SubscribedSearchTweetNtabRequestHydrator {
  override def tweetAuthor: Future[Option[User]] = Future.value(author)

  override def weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

  override def tweetId: Long = candidate.tweetId

  override def pushCopyId: Option[Int] = copyIds.pushCopyId

  override def ntabCopyId: Option[Int] = copyIds.ntabCopyId

  override def copyAggregationId: Option[String] = copyIds.aggregationId

  override def target: PushTypes.Target = candidate.target

  override def searchTerm: String = candidate.searchTerm

  override def timeBoundedLandingUrl: Option[String] = None

  override def statsReceiver: StatsReceiver = stats

  override def tweetyPieResult: Option[TweetyPie.TweetyPieResult] = candidate.tweetyPieResult
}

case class SubscribedSearchTweetCandidatePredicates(override val config: Config)
    extends BasicTweetPredicatesForRFPH[SubscribedSearchTweetPushCandidate] {
  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)
}

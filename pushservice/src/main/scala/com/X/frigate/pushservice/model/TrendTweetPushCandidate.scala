package com.X.frigate.pushservice.model

import com.X.events.recos.thriftscala.TrendsContext
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TrendTweetCandidate
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.TrendTweetIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.TrendTweetNtabHydrator
import com.X.frigate.pushservice.take.predicates.BasicTweetPredicatesForRFPH
import com.X.gizmoduck.thriftscala.User
import com.X.stitch.tweetypie.TweetyPie
import com.X.util.Future

class TrendTweetPushCandidate(
  candidate: RawCandidate with TrendTweetCandidate,
  author: Option[User],
  copyIds: CopyIds
)(
  implicit stats: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with TrendTweetCandidate
    with TweetAuthorDetails
    with TrendTweetIbis2Hydrator
    with TrendTweetNtabHydrator {
  override val statsReceiver: StatsReceiver = stats
  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer
  override val tweetId: Long = candidate.tweetId
  override lazy val tweetyPieResult: Option[TweetyPie.TweetyPieResult] = candidate.tweetyPieResult
  override lazy val tweetAuthor: Future[Option[User]] = Future.value(author)
  override val target: PushTypes.Target = candidate.target
  override val landingUrl: String = candidate.landingUrl
  override val timeBoundedLandingUrl: Option[String] = candidate.timeBoundedLandingUrl
  override val pushCopyId: Option[Int] = copyIds.pushCopyId
  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId
  override val trendId: String = candidate.trendId
  override val trendName: String = candidate.trendName
  override val copyAggregationId: Option[String] = copyIds.aggregationId
  override val context: TrendsContext = candidate.context
}

case class TrendTweetPredicates(override val config: Config)
    extends BasicTweetPredicatesForRFPH[TrendTweetPushCandidate] {
  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)
}

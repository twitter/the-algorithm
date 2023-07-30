package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.SocialContextAction
import com.X.frigate.common.base.SocialContextUserDetails
import com.X.frigate.common.base.TweetAuthorDetails
import com.X.frigate.common.base.TweetFavoriteCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.TweetFavoriteCandidateIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.TweetFavoriteNTabRequestHydrator
import com.X.frigate.pushservice.util.CandidateHydrationUtil.TweetWithSocialContextTraits
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.gizmoduck.thriftscala.User
import com.X.stitch.tweetypie.TweetyPie
import com.X.util.Future

class TweetFavoritePushCandidate(
  candidate: RawCandidate with TweetWithSocialContextTraits,
  socialContextUserMap: Future[Map[Long, Option[User]]],
  author: Future[Option[User]],
  copyIds: CopyIds
)(
  implicit stats: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with TweetFavoriteCandidate
    with SocialContextUserDetails
    with TweetAuthorDetails
    with TweetFavoriteNTabRequestHydrator
    with TweetFavoriteCandidateIbis2Hydrator {
  override val statsReceiver: StatsReceiver = stats
  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer
  override val tweetId: Long = candidate.tweetId
  override val socialContextActions: Seq[SocialContextAction] =
    candidate.socialContextActions

  override val socialContextAllTypeActions: Seq[SocialContextAction] =
    candidate.socialContextAllTypeActions

  override lazy val scUserMap: Future[Map[Long, Option[User]]] = socialContextUserMap
  override lazy val tweetAuthor: Future[Option[User]] = author
  override lazy val commonRecType: CommonRecommendationType =
    candidate.commonRecType
  override val target: PushTypes.Target = candidate.target
  override lazy val tweetyPieResult: Option[TweetyPie.TweetyPieResult] =
    candidate.tweetyPieResult
  override val pushCopyId: Option[Int] = copyIds.pushCopyId
  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId
  override val copyAggregationId: Option[String] = copyIds.aggregationId
}

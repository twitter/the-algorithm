package com.X.tsp.common

import com.X.abdecider.LoggingABDecider
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.TargetUser
import com.X.frigate.common.candidate.TargetABDecider
import com.X.frigate.common.util.ABDeciderWithOverride
import com.X.gizmoduck.thriftscala.User
import com.X.simclusters_v2.common.UserId
import com.X.storehaus.ReadableStore
import com.X.timelines.configapi.Params
import com.X.tsp.thriftscala.TopicSocialProofRequest
import com.X.util.Future

case class DefaultRecTopicSocialProofTarget(
  topicSocialProofRequest: TopicSocialProofRequest,
  targetId: UserId,
  user: Option[User],
  abDecider: ABDeciderWithOverride,
  params: Params
)(
  implicit statsReceiver: StatsReceiver)
    extends TargetUser
    with TopicSocialProofRecRequest
    with TargetABDecider {
  override def globalStats: StatsReceiver = statsReceiver
  override val targetUser: Future[Option[User]] = Future.value(user)
}

trait TopicSocialProofRecRequest {
  tuc: TargetUser =>

  val topicSocialProofRequest: TopicSocialProofRequest
}

case class RecTargetFactory(
  abDecider: LoggingABDecider,
  userStore: ReadableStore[UserId, User],
  paramBuilder: ParamsBuilder,
  statsReceiver: StatsReceiver) {

  type RecTopicSocialProofTarget = DefaultRecTopicSocialProofTarget

  def buildRecTopicSocialProofTarget(
    request: TopicSocialProofRequest
  ): Future[RecTopicSocialProofTarget] = {
    val userId = request.userId
    userStore.get(userId).map { userOpt =>
      val userRoles = userOpt.flatMap(_.roles.map(_.roles.toSet))

      val context = request.context.copy(userId = Some(request.userId)) // override to make sure

      val params = paramBuilder
        .buildFromTopicListingViewerContext(Some(context), request.displayLocation, userRoles)

      DefaultRecTopicSocialProofTarget(
        request,
        userId,
        userOpt,
        ABDeciderWithOverride(abDecider, None)(statsReceiver),
        params
      )(statsReceiver)
    }
  }
}

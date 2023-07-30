package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.HydratedMagicFanoutCreatorEventCandidate
import com.X.frigate.common.base.MagicFanoutCreatorEventCandidate
import com.X.frigate.magic_events.thriftscala.CreatorFanoutType
import com.X.frigate.magic_events.thriftscala.MagicEventsReason
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.MagicFanoutCreatorEventIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.MagicFanoutCreatorEventNtabRequestHydrator
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesForCandidate
import com.X.frigate.pushservice.predicate.ntab_caret_fatigue.MagicFanoutNtabCaretFatiguePredicate
import com.X.frigate.pushservice.take.predicates.BasicSendHandlerPredicates
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.frigate.thriftscala.FrigateNotification
import com.X.gizmoduck.thriftscala.User
import com.X.hermit.predicate.NamedPredicate
import com.X.storehaus.ReadableStore
import com.X.strato.client.UserId
import com.X.util.Future
import scala.util.control.NoStackTrace

class MagicFanoutCreatorEventPushCandidateHydratorException(private val message: String)
    extends Exception(message)
    with NoStackTrace

class MagicFanoutCreatorEventPushCandidate(
  candidate: RawCandidate with MagicFanoutCreatorEventCandidate,
  creatorUser: Option[User],
  copyIds: CopyIds,
  creatorTweetCountStore: ReadableStore[UserId, Int]
)(
  implicit val statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with MagicFanoutCreatorEventIbis2Hydrator
    with MagicFanoutCreatorEventNtabRequestHydrator
    with MagicFanoutCreatorEventCandidate
    with HydratedMagicFanoutCreatorEventCandidate {
  override def creatorId: Long = candidate.creatorId

  override def hydratedCreator: Option[User] = creatorUser

  override lazy val numberOfTweetsFut: Future[Option[Int]] =
    creatorTweetCountStore.get(UserId(creatorId))

  lazy val userProfile = hydratedCreator
    .flatMap(_.profile).getOrElse(
      throw new MagicFanoutCreatorEventPushCandidateHydratorException(
        s"Unable to get user profile to generate tapThrough for userId: $creatorId"))

  override val frigateNotification: FrigateNotification = candidate.frigateNotification

  override def subscriberId: Option[Long] = candidate.subscriberId

  override def creatorFanoutType: CreatorFanoutType = candidate.creatorFanoutType

  override def target: PushTypes.Target = candidate.target

  override def pushId: Long = candidate.pushId

  override def candidateMagicEventsReasons: Seq[MagicEventsReason] =
    candidate.candidateMagicEventsReasons

  override def statsReceiver: StatsReceiver = statsScoped

  override def pushCopyId: Option[Int] = copyIds.pushCopyId

  override def ntabCopyId: Option[Int] = copyIds.ntabCopyId

  override def copyAggregationId: Option[String] = copyIds.aggregationId

  override def commonRecType: CommonRecommendationType = candidate.commonRecType

  override def weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

}

case class MagicFanouCreatorSubscriptionEventPushPredicates(config: Config)
    extends BasicSendHandlerPredicates[MagicFanoutCreatorEventPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutCreatorEventPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(
        PushFeatureSwitchParams.EnableCreatorSubscriptionPush
      ),
      PredicatesForCandidate.isDeviceEligibleForCreatorPush,
      MagicFanoutPredicatesForCandidate.creatorPushTargetIsNotCreator(),
      MagicFanoutPredicatesForCandidate.duplicateCreatorPredicate,
      MagicFanoutPredicatesForCandidate.magicFanoutCreatorPushFatiguePredicate(),
    )

  override val postCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutCreatorEventPushCandidate]
  ] =
    List(
      MagicFanoutNtabCaretFatiguePredicate(),
      MagicFanoutPredicatesForCandidate.isSuperFollowingCreator()(config, statsReceiver).flip
    )
}

case class MagicFanoutNewCreatorEventPushPredicates(config: Config)
    extends BasicSendHandlerPredicates[MagicFanoutCreatorEventPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutCreatorEventPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(
        PushFeatureSwitchParams.EnableNewCreatorPush
      ),
      PredicatesForCandidate.isDeviceEligibleForCreatorPush,
      MagicFanoutPredicatesForCandidate.duplicateCreatorPredicate,
      MagicFanoutPredicatesForCandidate.magicFanoutCreatorPushFatiguePredicate,
    )

  override val postCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutCreatorEventPushCandidate]
  ] =
    List(
      MagicFanoutNtabCaretFatiguePredicate(),
      MagicFanoutPredicatesForCandidate.isSuperFollowingCreator()(config, statsReceiver).flip
    )
}

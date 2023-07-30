package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.ScheduledSpaceSubscriberCandidate
import com.X.frigate.common.base.SpaceCandidateFanoutDetails
import com.X.frigate.common.util.FeatureSwitchParams
import com.X.frigate.magic_events.thriftscala.SpaceMetadata
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.ScheduledSpaceSubscriberIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.ScheduledSpaceSubscriberNTabRequestHydrator
import com.X.frigate.pushservice.predicate._
import com.X.frigate.pushservice.take.predicates.BasicSendHandlerPredicates
import com.X.frigate.thriftscala.FrigateNotification
import com.X.gizmoduck.thriftscala.User
import com.X.hermit.predicate.NamedPredicate
import com.X.storehaus.ReadableStore
import com.X.ubs.thriftscala.AudioSpace
import com.X.util.Future

class ScheduledSpaceSubscriberPushCandidate(
  candidate: RawCandidate with ScheduledSpaceSubscriberCandidate,
  hostUser: Option[User],
  copyIds: CopyIds,
  audioSpaceStore: ReadableStore[String, AudioSpace]
)(
  implicit val statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with ScheduledSpaceSubscriberCandidate
    with SpaceCandidateFanoutDetails
    with ScheduledSpaceSubscriberIbis2Hydrator
    with ScheduledSpaceSubscriberNTabRequestHydrator {

  override val startTime: Long = candidate.startTime

  override val hydratedHost: Option[User] = hostUser

  override val spaceId: String = candidate.spaceId

  override val hostId: Option[Long] = candidate.hostId

  override val speakerIds: Option[Seq[Long]] = candidate.speakerIds

  override val listenerIds: Option[Seq[Long]] = candidate.listenerIds

  override val frigateNotification: FrigateNotification = candidate.frigateNotification

  override val pushCopyId: Option[Int] = copyIds.pushCopyId

  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId

  override val copyAggregationId: Option[String] = copyIds.aggregationId

  override val target: Target = candidate.target

  override lazy val audioSpaceFut: Future[Option[AudioSpace]] = audioSpaceStore.get(spaceId)

  override val spaceFanoutMetadata: Option[SpaceMetadata] = None

  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

  override val statsReceiver: StatsReceiver =
    statsScoped.scope("ScheduledSpaceSubscriberCandidate")
}

case class ScheduledSpaceSubscriberCandidatePredicates(config: Config)
    extends BasicSendHandlerPredicates[ScheduledSpaceSubscriberPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[ScheduledSpaceSubscriberPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(FeatureSwitchParams.EnableScheduledSpaceSubscribers),
      SpacePredicate.narrowCastSpace,
      SpacePredicate.targetInSpace(config.audioSpaceParticipantsStore),
      SpacePredicate.spaceHostTargetUserBlocking(config.edgeStore),
      PredicatesForCandidate.duplicateSpacesPredicate
    )
}

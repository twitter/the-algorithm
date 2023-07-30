package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.ScheduledSpaceSpeakerCandidate
import com.X.frigate.common.base.SpaceCandidateFanoutDetails
import com.X.frigate.common.util.FeatureSwitchParams
import com.X.frigate.magic_events.thriftscala.SpaceMetadata
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.ScheduledSpaceSpeakerIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.ScheduledSpaceSpeakerNTabRequestHydrator
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.SpacePredicate
import com.X.frigate.pushservice.take.predicates.BasicSendHandlerPredicates
import com.X.frigate.thriftscala.FrigateNotification
import com.X.gizmoduck.thriftscala.User
import com.X.hermit.predicate.NamedPredicate
import com.X.storehaus.ReadableStore
import com.X.ubs.thriftscala.AudioSpace
import com.X.util.Future

class ScheduledSpaceSpeakerPushCandidate(
  candidate: RawCandidate with ScheduledSpaceSpeakerCandidate,
  hostUser: Option[User],
  copyIds: CopyIds,
  audioSpaceStore: ReadableStore[String, AudioSpace]
)(
  implicit val statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with ScheduledSpaceSpeakerCandidate
    with ScheduledSpaceSpeakerIbis2Hydrator
    with SpaceCandidateFanoutDetails
    with ScheduledSpaceSpeakerNTabRequestHydrator {

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

  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

  override lazy val audioSpaceFut: Future[Option[AudioSpace]] = audioSpaceStore.get(spaceId)

  override val spaceFanoutMetadata: Option[SpaceMetadata] = None

  override val statsReceiver: StatsReceiver =
    statsScoped.scope("ScheduledSpaceSpeakerCandidate")
}

case class ScheduledSpaceSpeakerCandidatePredicates(config: Config)
    extends BasicSendHandlerPredicates[ScheduledSpaceSpeakerPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[ScheduledSpaceSpeakerPushCandidate]
  ] = List(
    SpacePredicate.scheduledSpaceStarted(
      config.audioSpaceStore
    ),
    PredicatesForCandidate.paramPredicate(FeatureSwitchParams.EnableScheduledSpaceSpeakers)
  )
}

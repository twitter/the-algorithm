package com.X.frigate.pushservice.model

import com.X.escherbird.metadata.thriftscala.EntityMegadata
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.BaseGameScore
import com.X.frigate.common.base.MagicFanoutSportsEventCandidate
import com.X.frigate.common.base.MagicFanoutSportsScoreInformation
import com.X.frigate.common.base.TeamInfo
import com.X.frigate.common.store.interests.InterestsLookupRequestWithContext
import com.X.frigate.magic_events.thriftscala.FanoutEvent
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.MagicFanoutSportsEventIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.MagicFanoutSportsEventNTabRequestHydrator
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesForCandidate
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutTargetingPredicateWrappersForCandidate
import com.X.frigate.pushservice.predicate.ntab_caret_fatigue.MagicFanoutNtabCaretFatiguePredicate
import com.X.frigate.pushservice.store.EventRequest
import com.X.frigate.pushservice.store.UttEntityHydrationStore
import com.X.frigate.pushservice.take.predicates.BasicSendHandlerPredicates
import com.X.hermit.predicate.NamedPredicate
import com.X.hermit.store.semantic_core.SemanticEntityForQuery
import com.X.interests.thriftscala.UserInterests
import com.X.livevideo.timeline.domain.v2.Event
import com.X.livevideo.timeline.domain.v2.HydrationOptions
import com.X.livevideo.timeline.domain.v2.LookupContext
import com.X.simclusters_v2.thriftscala.SimClustersInferredEntities
import com.X.storehaus.ReadableStore
import com.X.util.Future

class MagicFanoutSportsPushCandidate(
  candidate: RawCandidate
    with MagicFanoutSportsEventCandidate
    with MagicFanoutSportsScoreInformation,
  copyIds: CopyIds,
  override val fanoutEvent: Option[FanoutEvent],
  override val semanticEntityResults: Map[SemanticEntityForQuery, Option[EntityMegadata]],
  simClusterToEntities: Map[Int, Option[SimClustersInferredEntities]],
  lexServiceStore: ReadableStore[EventRequest, Event],
  interestsLookupStore: ReadableStore[InterestsLookupRequestWithContext, UserInterests],
  uttEntityHydrationStore: UttEntityHydrationStore
)(
  implicit statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends MagicFanoutEventPushCandidate(
      candidate,
      copyIds,
      fanoutEvent,
      semanticEntityResults,
      simClusterToEntities,
      lexServiceStore,
      interestsLookupStore,
      uttEntityHydrationStore)(statsScoped, pushModelScorer)
    with MagicFanoutSportsEventCandidate
    with MagicFanoutSportsScoreInformation
    with MagicFanoutSportsEventNTabRequestHydrator
    with MagicFanoutSportsEventIbis2Hydrator {

  override val isScoreUpdate: Boolean = candidate.isScoreUpdate
  override val gameScores: Future[Option[BaseGameScore]] = candidate.gameScores
  override val homeTeamInfo: Future[Option[TeamInfo]] = candidate.homeTeamInfo
  override val awayTeamInfo: Future[Option[TeamInfo]] = candidate.awayTeamInfo

  override lazy val stats: StatsReceiver = statsScoped.scope("MagicFanoutSportsPushCandidate")
  override val statsReceiver: StatsReceiver = statsScoped.scope("MagicFanoutSportsPushCandidate")

  override lazy val eventRequestFut: Future[Option[EventRequest]] = {
    Future.join(target.inferredUserDeviceLanguage, target.accountCountryCode).map {
      case (inferredUserDeviceLanguage, accountCountryCode) =>
        Some(
          EventRequest(
            eventId,
            lookupContext = LookupContext(
              hydrationOptions = HydrationOptions(
                includeSquareImage = true,
                includePrimaryImage = true
              ),
              language = inferredUserDeviceLanguage,
              countryCode = accountCountryCode
            )
          ))
    }
  }
}

case class MagicFanoutSportsEventCandidatePredicates(config: Config)
    extends BasicSendHandlerPredicates[MagicFanoutSportsPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutSportsPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(PushFeatureSwitchParams.EnableScoreFanoutNotification)
    )

  override val postCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutSportsPushCandidate]
  ] =
    List(
      PredicatesForCandidate.isDeviceEligibleForNewsOrSports,
      MagicFanoutPredicatesForCandidate.inferredUserDeviceLanguagePredicate,
      MagicFanoutPredicatesForCandidate.highPriorityEventExceptedPredicate(
        MagicFanoutTargetingPredicateWrappersForCandidate
          .magicFanoutTargetingPredicate(statsReceiver, config)
      )(config),
      PredicatesForCandidate.secondaryDormantAccountPredicate(
        statsReceiver
      ),
      MagicFanoutPredicatesForCandidate.highPriorityEventExceptedPredicate(
        MagicFanoutNtabCaretFatiguePredicate()
      )(config),
    )
}

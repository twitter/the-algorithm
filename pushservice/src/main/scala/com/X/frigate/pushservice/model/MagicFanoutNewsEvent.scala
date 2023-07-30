package com.X.frigate.pushservice.model

import com.X.escherbird.metadata.thriftscala.EntityMegadata
import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.MagicFanoutNewsEventCandidate
import com.X.frigate.common.store.interests.InterestsLookupRequestWithContext
import com.X.frigate.magic_events.thriftscala.FanoutEvent
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.MagicFanoutNewsEventIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.MagicFanoutNewsEventNTabRequestHydrator
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.event.EventPredicatesForCandidate
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
import com.X.simclusters_v2.thriftscala.SimClustersInferredEntities
import com.X.storehaus.ReadableStore

class MagicFanoutNewsEventPushCandidate(
  candidate: RawCandidate with MagicFanoutNewsEventCandidate,
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
      uttEntityHydrationStore
    )(statsScoped, pushModelScorer)
    with MagicFanoutNewsEventCandidate
    with MagicFanoutNewsEventIbis2Hydrator
    with MagicFanoutNewsEventNTabRequestHydrator {

  override lazy val stats: StatsReceiver = statsScoped.scope("MagicFanoutNewsEventPushCandidate")
  override val statsReceiver: StatsReceiver = statsScoped.scope("MagicFanoutNewsEventPushCandidate")
}

case class MagicFanoutNewsEventCandidatePredicates(config: Config)
    extends BasicSendHandlerPredicates[MagicFanoutNewsEventPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutNewsEventPushCandidate]
  ] =
    List(
      EventPredicatesForCandidate.accountCountryPredicateWithAllowlist,
      PredicatesForCandidate.isDeviceEligibleForNewsOrSports,
      MagicFanoutPredicatesForCandidate.inferredUserDeviceLanguagePredicate,
      PredicatesForCandidate.secondaryDormantAccountPredicate(statsReceiver),
      MagicFanoutPredicatesForCandidate.highPriorityNewsEventExceptedPredicate(
        MagicFanoutTargetingPredicateWrappersForCandidate
          .magicFanoutTargetingPredicate(statsReceiver, config)
      )(config),
      MagicFanoutPredicatesForCandidate.geoOptOutPredicate(config.safeUserStore),
      EventPredicatesForCandidate.isNotDuplicateWithEventId,
      MagicFanoutPredicatesForCandidate.highPriorityNewsEventExceptedPredicate(
        MagicFanoutPredicatesForCandidate.newsNotificationFatigue()
      )(config),
      MagicFanoutPredicatesForCandidate.highPriorityNewsEventExceptedPredicate(
        MagicFanoutNtabCaretFatiguePredicate()
      )(config),
      MagicFanoutPredicatesForCandidate.escherbirdMagicfanoutEventParam()(statsReceiver),
      MagicFanoutPredicatesForCandidate.hasCustomTargetingForNewsEventsParam(
        statsReceiver
      )
    )

  override val postCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutNewsEventPushCandidate]
  ] =
    List(
      MagicFanoutPredicatesForCandidate.magicFanoutNoOptoutInterestPredicate,
      MagicFanoutPredicatesForCandidate.geoTargetingHoldback(),
      MagicFanoutPredicatesForCandidate.userGeneratedEventsPredicate,
      EventPredicatesForCandidate.hasTitle,
    )
}

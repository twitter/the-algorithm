package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.DiscoverXCandidate
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.DiscoverXPushIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.DiscoverXNtabRequestHydrator
import com.X.frigate.pushservice.params.PushFeatureSwitchParams
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.take.predicates.BasicRFPHPredicates
import com.X.frigate.pushservice.take.predicates.OutOfNetworkTweetPredicates
import com.X.frigate.thriftscala.CommonRecommendationType
import com.X.hermit.predicate.NamedPredicate

class DiscoverXPushCandidate(
  candidate: RawCandidate with DiscoverXCandidate,
  copyIds: CopyIds,
)(
  implicit val statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with DiscoverXCandidate
    with DiscoverXPushIbis2Hydrator
    with DiscoverXNtabRequestHydrator {

  override val pushCopyId: Option[Int] = copyIds.pushCopyId

  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId

  override val copyAggregationId: Option[String] = copyIds.aggregationId

  override val target: Target = candidate.target

  override lazy val commonRecType: CommonRecommendationType = candidate.commonRecType

  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

  override val statsReceiver: StatsReceiver =
    statsScoped.scope("DiscoverXPushCandidate")
}

case class AddressBookPushCandidatePredicates(config: Config)
    extends BasicRFPHPredicates[DiscoverXPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val predicates: List[
    NamedPredicate[DiscoverXPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(
        PushFeatureSwitchParams.EnableAddressBookPush
      )
    )
}

case class CompleteOnboardingPushCandidatePredicates(config: Config)
    extends BasicRFPHPredicates[DiscoverXPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val predicates: List[
    NamedPredicate[DiscoverXPushCandidate]
  ] =
    List(
      PredicatesForCandidate.paramPredicate(
        PushFeatureSwitchParams.EnableCompleteOnboardingPush
      )
    )
}

case class PopGeoTweetCandidatePredicates(override val config: Config)
    extends OutOfNetworkTweetPredicates[OutOfNetworkTweetPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override def postCandidateSpecificPredicates: List[
    NamedPredicate[OutOfNetworkTweetPushCandidate]
  ] = List(
    PredicatesForCandidate.htlFatiguePredicate(
      PushFeatureSwitchParams.NewUserPlaybookAllowedLastLoginHours
    )
  )
}

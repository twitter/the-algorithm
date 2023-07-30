package com.X.frigate.pushservice.model

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.MagicFanoutProductLaunchCandidate
import com.X.frigate.common.util.{FeatureSwitchParams => FS}
import com.X.frigate.magic_events.thriftscala.MagicEventsReason
import com.X.frigate.magic_events.thriftscala.ProductType
import com.X.frigate.pushservice.model.PushTypes.PushCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesUtil
import com.X.frigate.pushservice.config.Config
import com.X.frigate.pushservice.ml.PushMLModelScorer
import com.X.frigate.pushservice.model.candidate.CopyIds
import com.X.frigate.pushservice.model.ibis.MagicFanoutProductLaunchIbis2Hydrator
import com.X.frigate.pushservice.model.ntab.MagicFanoutProductLaunchNtabRequestHydrator
import com.X.frigate.pushservice.predicate.PredicatesForCandidate
import com.X.frigate.pushservice.predicate.magic_fanout.MagicFanoutPredicatesForCandidate
import com.X.frigate.pushservice.predicate.ntab_caret_fatigue.MagicFanoutNtabCaretFatiguePredicate
import com.X.frigate.pushservice.take.predicates.BasicSendHandlerPredicates
import com.X.frigate.thriftscala.FrigateNotification
import com.X.hermit.predicate.NamedPredicate

class MagicFanoutProductLaunchPushCandidate(
  candidate: RawCandidate with MagicFanoutProductLaunchCandidate,
  copyIds: CopyIds
)(
  implicit val statsScoped: StatsReceiver,
  pushModelScorer: PushMLModelScorer)
    extends PushCandidate
    with MagicFanoutProductLaunchCandidate
    with MagicFanoutProductLaunchIbis2Hydrator
    with MagicFanoutProductLaunchNtabRequestHydrator {

  override val frigateNotification: FrigateNotification = candidate.frigateNotification

  override val pushCopyId: Option[Int] = copyIds.pushCopyId

  override val ntabCopyId: Option[Int] = copyIds.ntabCopyId

  override val pushId: Long = candidate.pushId

  override val productLaunchType: ProductType = candidate.productLaunchType

  override val candidateMagicEventsReasons: Seq[MagicEventsReason] =
    candidate.candidateMagicEventsReasons

  override val copyAggregationId: Option[String] = copyIds.aggregationId

  override val target: Target = candidate.target

  override val weightedOpenOrNtabClickModelScorer: PushMLModelScorer = pushModelScorer

  override val statsReceiver: StatsReceiver =
    statsScoped.scope("MagicFanoutProductLaunchPushCandidate")
}

case class MagicFanoutProductLaunchPushCandidatePredicates(config: Config)
    extends BasicSendHandlerPredicates[MagicFanoutProductLaunchPushCandidate] {

  implicit val statsReceiver: StatsReceiver = config.statsReceiver.scope(getClass.getSimpleName)

  override val preCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutProductLaunchPushCandidate]
  ] =
    List(
      PredicatesForCandidate.isDeviceEligibleForCreatorPush,
      PredicatesForCandidate.exceptedPredicate(
        "excepted_is_target_blue_verified",
        MagicFanoutPredicatesUtil.shouldSkipBlueVerifiedCheckForCandidate,
        PredicatesForCandidate.isTargetBlueVerified.flip
      ), // no need to send if target is already Blue Verified
      PredicatesForCandidate.exceptedPredicate(
        "excepted_is_target_legacy_verified",
        MagicFanoutPredicatesUtil.shouldSkipLegacyVerifiedCheckForCandidate,
        PredicatesForCandidate.isTargetLegacyVerified.flip
      ), // no need to send if target is already Legacy Verified
      PredicatesForCandidate.exceptedPredicate(
        "excepted_is_target_super_follow_creator",
        MagicFanoutPredicatesUtil.shouldSkipSuperFollowCreatorCheckForCandidate,
        PredicatesForCandidate.isTargetSuperFollowCreator.flip
      ), // no need to send if target is already Super Follow Creator
      PredicatesForCandidate.paramPredicate(
        FS.EnableMagicFanoutProductLaunch
      ),
      MagicFanoutPredicatesForCandidate.magicFanoutProductLaunchFatigue(),
    )

  override val postCandidateSpecificPredicates: List[
    NamedPredicate[MagicFanoutProductLaunchPushCandidate]
  ] =
    List(
      MagicFanoutNtabCaretFatiguePredicate(),
    )
}

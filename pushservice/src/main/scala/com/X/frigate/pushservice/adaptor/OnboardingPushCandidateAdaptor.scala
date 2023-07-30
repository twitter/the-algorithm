package com.X.frigate.pushservice.adaptor

import com.X.finagle.stats.StatsReceiver
import com.X.frigate.common.base.CandidateSource
import com.X.frigate.common.base.CandidateSourceEligible
import com.X.frigate.common.base.DiscoverXCandidate
import com.X.frigate.pushservice.model.PushTypes.RawCandidate
import com.X.frigate.pushservice.model.PushTypes.Target
import com.X.frigate.pushservice.params.{PushFeatureSwitchParams => FS}
import com.X.frigate.pushservice.predicate.DiscoverXPredicate
import com.X.frigate.pushservice.predicate.TargetPredicates
import com.X.frigate.pushservice.util.PushAppPermissionUtil
import com.X.frigate.pushservice.util.PushDeviceUtil
import com.X.frigate.thriftscala.{CommonRecommendationType => CRT}
import com.X.util.Future

class OnboardingPushCandidateAdaptor(
  globalStats: StatsReceiver)
    extends CandidateSource[Target, RawCandidate]
    with CandidateSourceEligible[Target, RawCandidate] {

  override val name: String = this.getClass.getSimpleName

  private[this] val stats = globalStats.scope(name)
  private[this] val requestNum = stats.counter("request_num")
  private[this] val addressBookCandNum = stats.counter("address_book_cand_num")
  private[this] val completeOnboardingCandNum = stats.counter("complete_onboarding_cand_num")

  private def generateOnboardingPushRawCandidate(
    _target: Target,
    _commonRecType: CRT
  ): RawCandidate = {
    new RawCandidate with DiscoverXCandidate {
      override val target = _target
      override val commonRecType = _commonRecType
    }
  }

  private def getEligibleCandsForTarget(
    target: Target
  ): Future[Option[Seq[RawCandidate]]] = {
    val addressBookFatigue =
      TargetPredicates
        .pushRecTypeFatiguePredicate(
          CRT.AddressBookUploadPush,
          FS.FatigueForOnboardingPushes,
          FS.MaxOnboardingPushInInterval,
          stats)(Seq(target)).map(_.head)
    val completeOnboardingFatigue =
      TargetPredicates
        .pushRecTypeFatiguePredicate(
          CRT.CompleteOnboardingPush,
          FS.FatigueForOnboardingPushes,
          FS.MaxOnboardingPushInInterval,
          stats)(Seq(target)).map(_.head)

    Future
      .join(
        target.appPermissions,
        addressBookFatigue,
        completeOnboardingFatigue
      ).map {
        case (appPermissionOpt, addressBookPredicate, completeOnboardingPredicate) =>
          val addressBookUploaded =
            PushAppPermissionUtil.hasTargetUploadedAddressBook(appPermissionOpt)
          val abUploadCandidate =
            if (!addressBookUploaded && addressBookPredicate && target.params(
                FS.EnableAddressBookPush)) {
              addressBookCandNum.incr()
              Some(generateOnboardingPushRawCandidate(target, CRT.AddressBookUploadPush))
            } else if (!addressBookUploaded && (completeOnboardingPredicate ||
              target.params(FS.DisableOnboardingPushFatigue)) && target.params(
                FS.EnableCompleteOnboardingPush)) {
              completeOnboardingCandNum.incr()
              Some(generateOnboardingPushRawCandidate(target, CRT.CompleteOnboardingPush))
            } else None

          val allCandidates =
            Seq(abUploadCandidate).filter(_.isDefined).flatten
          if (allCandidates.nonEmpty) Some(allCandidates) else None
      }
  }

  override def get(inputTarget: Target): Future[Option[Seq[RawCandidate]]] = {
    requestNum.incr()
    val minDurationForMRElapsed =
      DiscoverXPredicate
        .minDurationElapsedSinceLastMrPushPredicate(
          name,
          FS.MrMinDurationSincePushForOnboardingPushes,
          stats)(Seq(inputTarget)).map(_.head)
    minDurationForMRElapsed.flatMap { minDurationElapsed =>
      if (minDurationElapsed) getEligibleCandsForTarget(inputTarget) else Future.None
    }
  }

  override def isCandidateSourceAvailable(target: Target): Future[Boolean] = {
    PushDeviceUtil
      .isRecommendationsEligible(target).map(_ && target.params(FS.EnableOnboardingPushes))
  }
}

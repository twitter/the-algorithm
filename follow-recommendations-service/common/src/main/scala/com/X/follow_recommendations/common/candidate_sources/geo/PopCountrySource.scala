package com.X.follow_recommendations.common.candidate_sources.geo

import com.google.inject.Singleton
import com.X.core_workflows.user_model.thriftscala.UserState
import com.X.finagle.stats.Counter
import com.X.finagle.stats.StatsReceiver
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.HasGeohashAndCountryCode
import com.X.follow_recommendations.common.models.HasUserState
import com.X.hermit.model.Algorithm
import com.X.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.X.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.X.product_mixer.core.model.marshalling.request.HasClientContext
import com.X.stitch.Stitch
import com.X.timelines.configapi.HasParams
import javax.inject.Inject

@Singleton
class PopCountrySource @Inject() (
  popGeoSource: PopGeoSource,
  statsReceiver: StatsReceiver)
    extends CandidateSource[
      HasClientContext with HasParams with HasUserState with HasGeohashAndCountryCode,
      CandidateUser
    ] {

  override val identifier: CandidateSourceIdentifier = PopCountrySource.Identifier
  val stats: StatsReceiver = statsReceiver.scope("PopCountrySource")

  // counter to check if we found a country code value in the request
  val foundCountryCodeCounter: Counter = stats.counter("found_country_code_value")
  // counter to check if we are missing a country code value in the request
  val missingCountryCodeCounter: Counter = stats.counter("missing_country_code_value")

  override def apply(
    target: HasClientContext with HasParams with HasUserState with HasGeohashAndCountryCode
  ): Stitch[Seq[CandidateUser]] = {
    target.geohashAndCountryCode
      .flatMap(_.countryCode).map { countryCode =>
        foundCountryCodeCounter.incr()
        if (target.userState.exists(PopCountrySource.BlacklistedTargetUserStates.contains)) {
          Stitch.Nil
        } else {
          popGeoSource("country_" + countryCode)
            .map(_.take(PopCountrySource.MaxResults).map(_.withCandidateSource(identifier)))
        }
      }.getOrElse {
        missingCountryCodeCounter.incr()
        Stitch.Nil
      }
  }
}

object PopCountrySource {
  val Identifier: CandidateSourceIdentifier = CandidateSourceIdentifier(
    Algorithm.PopCountry.toString)
  val MaxResults = 40
  val BlacklistedTargetUserStates: Set[UserState] = Set(
    UserState.HeavyTweeter,
    UserState.HeavyNonTweeter,
    UserState.MediumTweeter,
    UserState.MediumNonTweeter)
}

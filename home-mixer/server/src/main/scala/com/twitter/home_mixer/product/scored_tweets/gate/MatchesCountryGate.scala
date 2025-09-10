package com.twitter.home_mixer.product.scored_tweets.gate

import com.twitter.home_mixer.model.HomeFeatures.SignupCountryFeature
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.model.common.identifier.GateIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.configapi.Param

/**
 * Check whether the signup country code feature or the input country code
 * exists within the input country codes param
 */
case class MatchesCountryGate(countryCodes: Param[Seq[String]]) extends Gate[PipelineQuery] {
  override val identifier: GateIdentifier = GateIdentifier("MatchesSignupCountry")

  /**
   * The main predicate that controls this gate. If this predicate returns true, the gate returns Continue.
   */
  override def shouldContinue(query: PipelineQuery): Stitch[Boolean] = {
    val countryCode = query.clientContext.countryCode
    val signupCountryCode = query.features
      .flatMap(_.getOrElse(SignupCountryFeature, None))
    val codeMatchesInput =
      Seq(countryCode, signupCountryCode).flatten.exists { code =>
        query.params(countryCodes).map(_.toLowerCase).contains(code.toLowerCase)
      }
    Stitch.value(codeMatchesInput)
  }
}

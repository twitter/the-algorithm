package com.X.follow_recommendations.configapi.candidates

import com.google.common.annotations.VisibleForTesting
import com.google.inject.Inject
import com.X.decider.Decider
import com.X.featureswitches.v2.FeatureSwitches
import com.X.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.X.follow_recommendations.common.constants.GuiceNamedConstants.PRODUCER_SIDE_FEATURE_SWITCHES
import com.X.follow_recommendations.common.models.CandidateUser
import com.X.follow_recommendations.common.models.DisplayLocation
import com.X.timelines.configapi.FeatureContext
import com.X.timelines.configapi.featureswitches.v2.FeatureSwitchResultsFeatureContext
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CandidateUserContextFactory @Inject() (
  @Named(PRODUCER_SIDE_FEATURE_SWITCHES) featureSwitches: FeatureSwitches,
  decider: Decider) {
  def apply(
    candidateUser: CandidateUser,
    displayLocation: DisplayLocation
  ): CandidateUserContext = {
    val featureContext = getFeatureContext(candidateUser, displayLocation)

    CandidateUserContext(Some(candidateUser.id), featureContext)
  }

  private[configapi] def getFeatureContext(
    candidateUser: CandidateUser,
    displayLocation: DisplayLocation
  ): FeatureContext = {

    val recipient = getFeatureSwitchRecipient(candidateUser).withCustomFields(
      "display_location" -> displayLocation.toFsName)
    new FeatureSwitchResultsFeatureContext(featureSwitches.matchRecipient(recipient))
  }

  @VisibleForTesting
  private[configapi] def getFeatureSwitchRecipient(
    candidateUser: CandidateUser
  ): FeatureSwitchRecipient = {
    FeatureSwitchRecipient(
      userId = Some(candidateUser.id),
      userRoles = None,
      deviceId = None,
      guestId = None,
      languageCode = None,
      countryCode = None,
      isVerified = None,
      clientApplicationId = None,
      isTwoffice = None
    )
  }
}

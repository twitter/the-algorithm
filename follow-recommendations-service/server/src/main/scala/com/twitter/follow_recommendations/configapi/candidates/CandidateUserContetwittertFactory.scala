package com.twitter.follow_recommendations.configapi.candidates

import com.google.common.annotations.VisibleForTesting
import com.google.inject.Inject
import com.twitter.decider.Decider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.twitter.follow_recommendations.common.constants.GuiceNamedConstants.PRODUCER_SIDE_FEATURE_SWITCHES
import com.twitter.follow_recommendations.common.models.CandidateUser
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.featureswitches.v2.FeatureSwitchResultsFeatureContetwittert
import javatwitter.inject.Named
import javatwitter.inject.Singleton

@Singleton
class CandidateUserContetwittertFactory @Inject() (
  @Named(PRODUCER_SIDE_FEATURE_SWITCHES) featureSwitches: FeatureSwitches,
  decider: Decider) {
  def apply(
    candidateUser: CandidateUser,
    displayLocation: DisplayLocation
  ): CandidateUserContetwittert = {
    val featureContetwittert = getFeatureContetwittert(candidateUser, displayLocation)

    CandidateUserContetwittert(Some(candidateUser.id), featureContetwittert)
  }

  private[configapi] def getFeatureContetwittert(
    candidateUser: CandidateUser,
    displayLocation: DisplayLocation
  ): FeatureContetwittert = {

    val recipient = getFeatureSwitchRecipient(candidateUser).withCustomFields(
      "display_location" -> displayLocation.toFsName)
    new FeatureSwitchResultsFeatureContetwittert(featureSwitches.matchRecipient(recipient))
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

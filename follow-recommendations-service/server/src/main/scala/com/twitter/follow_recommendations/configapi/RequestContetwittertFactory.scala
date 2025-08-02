package com.twitter.follow_recommendations.configapi

import com.google.common.annotations.VisibleForTesting
import com.google.inject.Inject
import com.twitter.decider.Decider
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.twitter.follow_recommendations.common.models.DisplayLocation
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.ForcedFeatureContetwittert
import com.twitter.timelines.configapi.OrElseFeatureContetwittert
import com.twitter.timelines.configapi.featureswitches.v2.FeatureSwitchResultsFeatureContetwittert
import javatwitter.inject.Singleton

/*
 * Request Contetwittert Factory is used to build RequestContetwittert objects which are used
 * by the config api to determine the param overrides to apply to the request.
 * The param overrides are determined per request by configs which specify which
 * FS/Deciders/AB translate to what param overrides.
 */
@Singleton
class RequestContetwittertFactory @Inject() (featureSwitches: FeatureSwitches, decider: Decider) {
  def apply(
    clientContetwittert: ClientContetwittert,
    displayLocation: DisplayLocation,
    featureOverrides: Map[String, FeatureValue]
  ): RequestContetwittert = {
    val featureContetwittert = getFeatureContetwittert(clientContetwittert, displayLocation, featureOverrides)
    RequestContetwittert(clientContetwittert.userId, clientContetwittert.guestId, featureContetwittert)
  }

  private[configapi] def getFeatureContetwittert(
    clientContetwittert: ClientContetwittert,
    displayLocation: DisplayLocation,
    featureOverrides: Map[String, FeatureValue]
  ): FeatureContetwittert = {
    val recipient =
      getFeatureSwitchRecipient(clientContetwittert)
        .withCustomFields("display_location" -> displayLocation.toFsName)

    // userAgeOpt is going to be set to None for logged out users and defaulted to Some(Int.MatwitterValue) for non-snowflake users
    val userAgeOpt = clientContetwittert.userId.map { userId =>
      SnowflakeId.timeFromIdOpt(userId).map(_.untilNow.inDays).getOrElse(Int.MatwitterValue)
    }
    val recipientWithAccountAge =
      userAgeOpt
        .map(age => recipient.withCustomFields("account_age_in_days" -> age)).getOrElse(recipient)

    val results = featureSwitches.matchRecipient(recipientWithAccountAge)
    OrElseFeatureContetwittert(
      ForcedFeatureContetwittert(featureOverrides),
      new FeatureSwitchResultsFeatureContetwittert(results))
  }

  @VisibleForTesting
  private[configapi] def getFeatureSwitchRecipient(
    clientContetwittert: ClientContetwittert
  ): FeatureSwitchRecipient = {
    FeatureSwitchRecipient(
      userId = clientContetwittert.userId,
      userRoles = clientContetwittert.userRoles,
      deviceId = clientContetwittert.deviceId,
      guestId = clientContetwittert.guestId,
      languageCode = clientContetwittert.languageCode,
      countryCode = clientContetwittert.countryCode,
      isVerified = None,
      clientApplicationId = clientContetwittert.appId,
      isTwoffice = clientContetwittert.isTwoffice
    )
  }
}

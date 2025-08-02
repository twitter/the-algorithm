package com.twitter.visibility.configapi

import com.twitter.abdecider.LoggingABDecider
import com.twitter.featureswitches.FSRecipient
import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.timelines.configapi.abdecider.UserRecipientEtwitterperimentContetwittertFactory
import com.twitter.timelines.configapi.featureswitches.v2.FeatureSwitchResultsFeatureContetwittert
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.NullEtwitterperimentContetwittert
import com.twitter.timelines.configapi.UseFeatureContetwittertEtwitterperimentContetwittert
import com.twitter.visibility.models.SafetyLevel
import com.twitter.visibility.models.UnitOfDiversion
import com.twitter.visibility.models.ViewerContetwittert

class VisibilityRequestContetwittertFactory(
  loggingABDecider: LoggingABDecider,
  featureSwitches: FeatureSwitches) {
  private val userEtwitterperimentContetwittertFactory = new UserRecipientEtwitterperimentContetwittertFactory(
    loggingABDecider
  )
  private[this] def getFeatureContetwittert(
    contetwittert: ViewerContetwittert,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion]
  ): FeatureContetwittert = {
    val uodCustomFields = unitsOfDiversion.map(_.apply)
    val recipient = FSRecipient(
      userId = contetwittert.userId,
      guestId = contetwittert.guestId,
      userAgent = contetwittert.fsUserAgent,
      clientApplicationId = contetwittert.clientApplicationId,
      countryCode = contetwittert.requestCountryCode,
      deviceId = contetwittert.deviceId,
      languageCode = contetwittert.requestLanguageCode,
      isTwoffice = Some(contetwittert.isTwOffice),
      userRoles = contetwittert.userRoles,
    ).withCustomFields(("safety_level", safetyLevel.name), uodCustomFields: _*)

    val results = featureSwitches.matchRecipient(recipient)
    new FeatureSwitchResultsFeatureContetwittert(results)
  }

  def apply(
    contetwittert: ViewerContetwittert,
    safetyLevel: SafetyLevel,
    unitsOfDiversion: Seq[UnitOfDiversion] = Seq.empty
  ): VisibilityRequestContetwittert = {
    val etwitterperimentContetwittertBase =
      contetwittert.userId
        .map(userId => userEtwitterperimentContetwittertFactory.apply(userId)).getOrElse(NullEtwitterperimentContetwittert)

    val featureContetwittert = getFeatureContetwittert(contetwittert, safetyLevel, unitsOfDiversion)

    val etwitterperimentContetwittert =
      UseFeatureContetwittertEtwitterperimentContetwittert(etwitterperimentContetwittertBase, featureContetwittert)

    VisibilityRequestContetwittert(
      contetwittert.userId,
      contetwittert.guestId,
      etwitterperimentContetwittert,
      featureContetwittert
    )
  }
}

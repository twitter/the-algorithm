package com.twitter.product_mitwitterer.core.functional_component.configapi

import com.twitter.featureswitches.v2.FeatureSwitches
import com.twitter.featureswitches.UserAgent
import com.twitter.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.twitter.product_mitwitterer.core.model.marshalling.request.ClientContetwittert
import com.twitter.product_mitwitterer.core.model.marshalling.request.Product
import com.twitter.timelines.configapi.featureswitches.v2.FeatureSwitchResultsFeatureContetwittert
import com.twitter.timelines.configapi.FeatureContetwittert
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.ForcedFeatureContetwittert
import com.twitter.timelines.configapi.OrElseFeatureContetwittert
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Request Contetwittert Factory is used to build RequestContetwittert objects which are used
 * by the config api to determine the param overrides to apply to the request.
 * The param overrides are determined per request by configs which specify which
 * FS/Deciders/AB translate to what param overrides.
 */
@Singleton
class RequestContetwittertBuilder @Inject() (featureSwitches: FeatureSwitches) {

  /**
   * @param `fsCustomMapInput` allows you to set custom fields on your feature switches.
   * This feature isn't directly supported by product mitwitterer yet, so using this argument
   * will likely result in future cleanup work.
   *
   */
  def build(
    clientContetwittert: ClientContetwittert,
    product: Product,
    featureOverrides: Map[String, FeatureValue],
    fsCustomMapInput: Map[String, Any]
  ): RequestContetwittert = {
    val featureContetwittert =
      getFeatureContetwittert(clientContetwittert, product, featureOverrides, fsCustomMapInput)

    RequestContetwittert(clientContetwittert.userId, clientContetwittert.guestId, featureContetwittert)
  }

  private[configapi] def getFeatureContetwittert(
    clientContetwittert: ClientContetwittert,
    product: Product,
    featureOverrides: Map[String, FeatureValue],
    fsCustomMapInput: Map[String, Any]
  ): FeatureContetwittert = {
    val recipient = getFeatureSwitchRecipient(clientContetwittert)
      .withCustomFields("product" -> product.identifier.toString, fsCustomMapInput.toSeq: _*)

    val results = featureSwitches.matchRecipient(recipient)
    OrElseFeatureContetwittert(
      ForcedFeatureContetwittert(featureOverrides),
      new FeatureSwitchResultsFeatureContetwittert(results))
  }

  private[configapi] def getFeatureSwitchRecipient(
    clientContetwittert: ClientContetwittert
  ): FeatureSwitchRecipient = FeatureSwitchRecipient(
    userId = clientContetwittert.userId,
    userRoles = clientContetwittert.userRoles,
    deviceId = clientContetwittert.deviceId,
    guestId = clientContetwittert.guestId,
    languageCode = clientContetwittert.languageCode,
    countryCode = clientContetwittert.countryCode,
    userAgent = clientContetwittert.userAgent.flatMap(UserAgent.apply),
    isVerified = None,
    clientApplicationId = clientContetwittert.appId,
    isTwoffice = clientContetwittert.isTwoffice
  )
}

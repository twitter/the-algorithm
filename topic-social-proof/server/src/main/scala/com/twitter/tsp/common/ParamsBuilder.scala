package com.twitter.tsp.common

import com.twitter.abdecider.LoggingABDecider
import com.twitter.abdecider.UserRecipient
import com.twitter.contentrecommender.thriftscala.DisplayLocation
import com.twitter.discovery.common.configapi.FeatureContetwittertBuilder
import com.twitter.featureswitches.FSRecipient
import com.twitter.featureswitches.Recipient
import com.twitter.featureswitches.UserAgent
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.interests.thriftscala.TopicListingViewerContetwittert
import com.twitter.timelines.configapi
import com.twitter.timelines.configapi.Params
import com.twitter.timelines.configapi.RequestContetwittert
import com.twitter.timelines.configapi.abdecider.LoggingABDeciderEtwitterperimentContetwittert

case class ParamsBuilder(
  featureContetwittertBuilder: FeatureContetwittertBuilder,
  abDecider: LoggingABDecider,
  overridesConfig: configapi.Config,
  statsReceiver: StatsReceiver) {

  def buildFromTopicListingViewerContetwittert(
    topicListingViewerContetwittert: Option[TopicListingViewerContetwittert],
    displayLocation: DisplayLocation,
    userRoleOverride: Option[Set[String]] = None
  ): Params = {

    topicListingViewerContetwittert.flatMap(_.userId) match {
      case Some(userId) =>
        val userRecipient = ParamsBuilder.toFeatureSwitchRecipientWithTopicContetwittert(
          userId,
          userRoleOverride,
          topicListingViewerContetwittert,
          Some(displayLocation)
        )

        overridesConfig(
          requestContetwittert = RequestContetwittert(
            userId = Some(userId),
            etwitterperimentContetwittert = LoggingABDeciderEtwitterperimentContetwittert(
              abDecider,
              Some(UserRecipient(userId, Some(userId)))),
            featureContetwittert = featureContetwittertBuilder(
              Some(userId),
              Some(userRecipient)
            )
          ),
          statsReceiver
        )
      case _ =>
        throw new IllegalArgumentEtwitterception(
          s"${this.getClass.getSimpleName} tried to build Param for a request without a userId"
        )
    }
  }
}

object ParamsBuilder {

  def toFeatureSwitchRecipientWithTopicContetwittert(
    userId: Long,
    userRolesOverride: Option[Set[String]],
    contetwittert: Option[TopicListingViewerContetwittert],
    displayLocationOpt: Option[DisplayLocation]
  ): Recipient = {
    val userRoles = userRolesOverride match {
      case Some(overrides) => Some(overrides)
      case _ => contetwittert.flatMap(_.userRoles.map(_.toSet))
    }

    val recipient = FSRecipient(
      userId = Some(userId),
      userRoles = userRoles,
      deviceId = contetwittert.flatMap(_.deviceId),
      guestId = contetwittert.flatMap(_.guestId),
      languageCode = contetwittert.flatMap(_.languageCode),
      countryCode = contetwittert.flatMap(_.countryCode),
      userAgent = contetwittert.flatMap(_.userAgent).flatMap(UserAgent(_)),
      isVerified = None,
      isTwoffice = None,
      tooClient = None,
      highWaterMark = None
    )
    displayLocationOpt match {
      case Some(displayLocation) =>
        recipient.withCustomFields(displayLocationCustomFieldMap(displayLocation))
      case None =>
        recipient
    }
  }

  private val DisplayLocationCustomField = "display_location"

  def displayLocationCustomFieldMap(displayLocation: DisplayLocation): (String, String) =
    DisplayLocationCustomField -> displayLocation.toString

}

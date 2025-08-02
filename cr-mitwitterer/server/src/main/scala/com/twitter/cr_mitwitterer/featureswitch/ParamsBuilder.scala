package com.twitter.cr_mitwitterer.featureswitch

import com.twitter.abdecider.LoggingABDecider
import com.twitter.abdecider.UserRecipient
import com.twitter.cr_mitwitterer.{thriftscala => t}
import com.twitter.core_workflows.user_model.thriftscala.UserState
import com.twitter.discovery.common.configapi.FeatureContetwittertBuilder
import com.twitter.featureswitches.FSRecipient
import com.twitter.featureswitches.UserAgent
import com.twitter.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.thriftscala.ClientContetwittert
import com.twitter.timelines.configapi.Config
import com.twitter.timelines.configapi.FeatureValue
import com.twitter.timelines.configapi.ForcedFeatureContetwittert
import com.twitter.timelines.configapi.OrElseFeatureContetwittert
import com.twitter.timelines.configapi.Params
import com.twitter.timelines.configapi.RequestContetwittert
import com.twitter.timelines.configapi.abdecider.LoggingABDeciderEtwitterperimentContetwittert
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/** Singleton object for building [[Params]] to override */
@Singleton
class ParamsBuilder @Inject() (
  globalStats: StatsReceiver,
  abDecider: LoggingABDecider,
  featureContetwittertBuilder: FeatureContetwittertBuilder,
  config: Config) {

  private val stats = globalStats.scope("params")

  def buildFromClientContetwittert(
    clientContetwittert: ClientContetwittert,
    product: t.Product,
    userState: UserState,
    userRoleOverride: Option[Set[String]] = None,
    featureOverrides: Map[String, FeatureValue] = Map.empty,
  ): Params = {
    clientContetwittert.userId match {
      case Some(userId) =>
        val userRecipient = buildFeatureSwitchRecipient(
          userId,
          userRoleOverride,
          clientContetwittert,
          product,
          userState
        )

        val featureContetwittert = OrElseFeatureContetwittert(
          ForcedFeatureContetwittert(featureOverrides),
          featureContetwittertBuilder(
            Some(userId),
            Some(userRecipient)
          ))

        config(
          requestContetwittert = RequestContetwittert(
            userId = Some(userId),
            etwitterperimentContetwittert = LoggingABDeciderEtwitterperimentContetwittert(
              abDecider,
              Some(UserRecipient(userId, Some(userId)))),
            featureContetwittert = featureContetwittert
          ),
          stats
        )
      case None =>
        val guestRecipient =
          buildFeatureSwitchRecipientWithGuestId(clientContetwittert: ClientContetwittert, product, userState)

        val featureContetwittert = OrElseFeatureContetwittert(
          ForcedFeatureContetwittert(featureOverrides),
          featureContetwittertBuilder(
            clientContetwittert.userId,
            Some(guestRecipient)
          )
        ) //EtwitterperimentContetwittert with GuestRecipient is not supported  as there is no active use-cases yet in CrMitwitterer

        config(
          requestContetwittert = RequestContetwittert(
            userId = clientContetwittert.userId,
            featureContetwittert = featureContetwittert
          ),
          stats
        )
    }
  }

  private def buildFeatureSwitchRecipientWithGuestId(
    clientContetwittert: ClientContetwittert,
    product: t.Product,
    userState: UserState
  ): FeatureSwitchRecipient = {

    val recipient = FSRecipient(
      userId = None,
      userRoles = None,
      deviceId = clientContetwittert.deviceId,
      guestId = clientContetwittert.guestId,
      languageCode = clientContetwittert.languageCode,
      countryCode = clientContetwittert.countryCode,
      userAgent = clientContetwittert.userAgent.flatMap(UserAgent(_)),
      isVerified = None,
      isTwoffice = None,
      tooClient = None,
      highWaterMark = None
    )

    recipient.withCustomFields(
      (ParamsBuilder.ProductCustomField, product.toString),
      (ParamsBuilder.UserStateCustomField, userState.toString)
    )
  }

  private def buildFeatureSwitchRecipient(
    userId: Long,
    userRolesOverride: Option[Set[String]],
    clientContetwittert: ClientContetwittert,
    product: t.Product,
    userState: UserState
  ): FeatureSwitchRecipient = {
    val userRoles = userRolesOverride match {
      case Some(overrides) => Some(overrides)
      case _ => clientContetwittert.userRoles.map(_.toSet)
    }

    val recipient = FSRecipient(
      userId = Some(userId),
      userRoles = userRoles,
      deviceId = clientContetwittert.deviceId,
      guestId = clientContetwittert.guestId,
      languageCode = clientContetwittert.languageCode,
      countryCode = clientContetwittert.countryCode,
      userAgent = clientContetwittert.userAgent.flatMap(UserAgent(_)),
      isVerified = None,
      isTwoffice = None,
      tooClient = None,
      highWaterMark = None
    )

    recipient.withCustomFields(
      (ParamsBuilder.ProductCustomField, product.toString),
      (ParamsBuilder.UserStateCustomField, userState.toString)
    )
  }
}

object ParamsBuilder {
  private val ProductCustomField = "product_id"
  private val UserStateCustomField = "user_state"
}

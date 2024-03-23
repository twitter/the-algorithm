package com.ExTwitter.cr_mixer.featureswitch

import com.ExTwitter.abdecider.LoggingABDecider
import com.ExTwitter.abdecider.UserRecipient
import com.ExTwitter.cr_mixer.{thriftscala => t}
import com.ExTwitter.core_workflows.user_model.thriftscala.UserState
import com.ExTwitter.discovery.common.configapi.FeatureContextBuilder
import com.ExTwitter.featureswitches.FSRecipient
import com.ExTwitter.featureswitches.UserAgent
import com.ExTwitter.featureswitches.{Recipient => FeatureSwitchRecipient}
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.product_mixer.core.thriftscala.ClientContext
import com.ExTwitter.timelines.configapi.Config
import com.ExTwitter.timelines.configapi.FeatureValue
import com.ExTwitter.timelines.configapi.ForcedFeatureContext
import com.ExTwitter.timelines.configapi.OrElseFeatureContext
import com.ExTwitter.timelines.configapi.Params
import com.ExTwitter.timelines.configapi.RequestContext
import com.ExTwitter.timelines.configapi.abdecider.LoggingABDeciderExperimentContext
import javax.inject.Inject
import javax.inject.Singleton

/** Singleton object for building [[Params]] to override */
@Singleton
class ParamsBuilder @Inject() (
  globalStats: StatsReceiver,
  abDecider: LoggingABDecider,
  featureContextBuilder: FeatureContextBuilder,
  config: Config) {

  private val stats = globalStats.scope("params")

  def buildFromClientContext(
    clientContext: ClientContext,
    product: t.Product,
    userState: UserState,
    userRoleOverride: Option[Set[String]] = None,
    featureOverrides: Map[String, FeatureValue] = Map.empty,
  ): Params = {
    clientContext.userId match {
      case Some(userId) =>
        val userRecipient = buildFeatureSwitchRecipient(
          userId,
          userRoleOverride,
          clientContext,
          product,
          userState
        )

        val featureContext = OrElseFeatureContext(
          ForcedFeatureContext(featureOverrides),
          featureContextBuilder(
            Some(userId),
            Some(userRecipient)
          ))

        config(
          requestContext = RequestContext(
            userId = Some(userId),
            experimentContext = LoggingABDeciderExperimentContext(
              abDecider,
              Some(UserRecipient(userId, Some(userId)))),
            featureContext = featureContext
          ),
          stats
        )
      case None =>
        val guestRecipient =
          buildFeatureSwitchRecipientWithGuestId(clientContext: ClientContext, product, userState)

        val featureContext = OrElseFeatureContext(
          ForcedFeatureContext(featureOverrides),
          featureContextBuilder(
            clientContext.userId,
            Some(guestRecipient)
          )
        ) //ExperimentContext with GuestRecipient is not supported  as there is no active use-cases yet in CrMixer

        config(
          requestContext = RequestContext(
            userId = clientContext.userId,
            featureContext = featureContext
          ),
          stats
        )
    }
  }

  private def buildFeatureSwitchRecipientWithGuestId(
    clientContext: ClientContext,
    product: t.Product,
    userState: UserState
  ): FeatureSwitchRecipient = {

    val recipient = FSRecipient(
      userId = None,
      userRoles = None,
      deviceId = clientContext.deviceId,
      guestId = clientContext.guestId,
      languageCode = clientContext.languageCode,
      countryCode = clientContext.countryCode,
      userAgent = clientContext.userAgent.flatMap(UserAgent(_)),
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
    clientContext: ClientContext,
    product: t.Product,
    userState: UserState
  ): FeatureSwitchRecipient = {
    val userRoles = userRolesOverride match {
      case Some(overrides) => Some(overrides)
      case _ => clientContext.userRoles.map(_.toSet)
    }

    val recipient = FSRecipient(
      userId = Some(userId),
      userRoles = userRoles,
      deviceId = clientContext.deviceId,
      guestId = clientContext.guestId,
      languageCode = clientContext.languageCode,
      countryCode = clientContext.countryCode,
      userAgent = clientContext.userAgent.flatMap(UserAgent(_)),
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

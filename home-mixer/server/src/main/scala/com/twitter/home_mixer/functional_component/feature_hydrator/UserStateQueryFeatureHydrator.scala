package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.home_mixer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelines.user_health.v1.{thriftscala => uhv1}
import com.twitter.timelines.user_health.{thriftscala => uh}
import com.twitter.user_session_store.ReadOnlyUserSessionStore
import com.twitter.user_session_store.ReadRequest
import com.twitter.user_session_store.UserSessionDataset
import com.twitter.user_session_store.UserSessionDataset.UserSessionDataset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class UserStateQueryFeatureHydrator @Inject() (
  userSessionStore: ReadOnlyUserSessionStore)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("UserState")

  override val features: Set[Feature[_, _]] = Set(UserStateFeature)

  private val datasets: Set[UserSessionDataset] = Set(UserSessionDataset.UserHealth)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    userSessionStore
      .read(ReadRequest(query.getRequiredUserId, datasets))
      .map { userSession =>
        val userState = userSession.flatMap {
          _.userHealth match {
            case Some(uh.UserHealth.V1(uhv1.UserHealth(userState))) => userState
            case _ => None
          }
        }

        FeatureMapBuilder()
          .add(UserStateFeature, userState)
          .build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99)
  )
}

package com.ExTwitter.home_mixer.product.scored_tweets.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures.UserStateFeature
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelines.user_health.v1.{thriftscala => uhv1}
import com.ExTwitter.timelines.user_health.{thriftscala => uh}
import com.ExTwitter.user_session_store.ReadOnlyUserSessionStore
import com.ExTwitter.user_session_store.ReadRequest
import com.ExTwitter.user_session_store.UserSessionDataset
import com.ExTwitter.user_session_store.UserSessionDataset.UserSessionDataset
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class UserStateQueryFeatureHydrator @Inject() (
  userSessionStore: ReadOnlyUserSessionStore)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("UserState")

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
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.9)
  )
}

package com.ExTwitter.home_mixer.functional_component.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures.FollowingLastNonPollingTimeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.LastNonPollingTimeFeature
import com.ExTwitter.home_mixer.model.HomeFeatures.NonPollingTimesFeature
import com.ExTwitter.home_mixer.service.HomeMixerAlertConfig
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.user_session_store.ReadRequest
import com.ExTwitter.user_session_store.ReadWriteUserSessionStore
import com.ExTwitter.user_session_store.UserSessionDataset
import com.ExTwitter.user_session_store.UserSessionDataset.UserSessionDataset
import com.ExTwitter.util.Time

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class LastNonPollingTimeQueryFeatureHydrator @Inject() (
  userSessionStore: ReadWriteUserSessionStore)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier =
    FeatureHydratorIdentifier("LastNonPollingTime")

  override val features: Set[Feature[_, _]] = Set(
    FollowingLastNonPollingTimeFeature,
    LastNonPollingTimeFeature,
    NonPollingTimesFeature
  )

  private val datasets: Set[UserSessionDataset] = Set(UserSessionDataset.NonPollingTimes)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    userSessionStore
      .read(ReadRequest(query.getRequiredUserId, datasets))
      .map { userSession =>
        val nonPollingTimestamps = userSession.flatMap(_.nonPollingTimestamps)

        val lastNonPollingTime = nonPollingTimestamps
          .flatMap(_.nonPollingTimestampsMs.headOption)
          .map(Time.fromMilliseconds)

        val followingLastNonPollingTime = nonPollingTimestamps
          .flatMap(_.mostRecentHomeLatestNonPollingTimestampMs)
          .map(Time.fromMilliseconds)

        val nonPollingTimes = nonPollingTimestamps
          .map(_.nonPollingTimestampsMs)
          .getOrElse(Seq.empty)

        FeatureMapBuilder()
          .add(FollowingLastNonPollingTimeFeature, followingLastNonPollingTime)
          .add(LastNonPollingTimeFeature, lastNonPollingTime)
          .add(NonPollingTimesFeature, nonPollingTimes)
          .build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.9)
  )
}

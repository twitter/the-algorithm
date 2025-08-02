package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.FollowingLastNonPollingTimeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.LastNonPollingTimeFeature
import com.twitter.home_mitwitterer.model.HomeFeatures.NonPollingTimesFeature
import com.twitter.home_mitwitterer.service.HomeMitwittererAlertConfig
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.user_session_store.ReadRequest
import com.twitter.user_session_store.ReadWriteUserSessionStore
import com.twitter.user_session_store.UserSessionDataset
import com.twitter.user_session_store.UserSessionDataset.UserSessionDataset
import com.twitter.util.Time

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class LastNonPollingTimeQueryFeatureHydrator @Inject() (
  userSessionStore: ReadWriteUserSessionStore)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

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
    HomeMitwittererAlertConfig.BusinessHours.defaultSuccessRateAlert(99.9)
  )
}

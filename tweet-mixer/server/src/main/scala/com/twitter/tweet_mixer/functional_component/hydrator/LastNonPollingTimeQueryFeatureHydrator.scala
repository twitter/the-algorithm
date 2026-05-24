package com.twitter.tweet_mixer.functional_component.hydrator

import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.user_session_store.ReadRequest
import com.twitter.user_session_store.ReadWriteUserSessionStore
import com.twitter.user_session_store.UserSessionDataset
import com.twitter.user_session_store.UserSessionDataset.UserSessionDataset
import com.twitter.util.Time

import javax.inject.Inject
import javax.inject.Singleton

object FollowingLastNonPollingTimeFeature extends Feature[PipelineQuery, Option[Time]]
object LastNonPollingTimeFeature extends Feature[PipelineQuery, Option[Time]]
object NonPollingTimesFeature extends Feature[PipelineQuery, Seq[Long]]

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
}

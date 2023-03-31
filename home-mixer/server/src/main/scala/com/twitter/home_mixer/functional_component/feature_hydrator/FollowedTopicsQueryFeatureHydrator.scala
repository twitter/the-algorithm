package com.twitter.home_mixer.functional_component.feature_hydrator

import com.twitter.conversions.DurationOps._
import com.twitter.home_mixer.model.HomeFeatures.UserFollowedTopicsCountFeature
import com.twitter.home_mixer.service.HomeMixerAlertConfig
import com.twitter.product_mixer.component_library.candidate_source.topics.FollowedTopicsCandidateSource
import com.twitter.product_mixer.core.feature.Feature
import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mixer.core.functional_component.candidate_source.strato.StratoKeyView
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class FollowedTopicsQueryFeatureHydrator @Inject() (
  followedTopicsCandidateSource: FollowedTopicsCandidateSource)
    extends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FollowedTopics")

  override val features: Set[Feature[_, _]] = Set(UserFollowedTopicsCountFeature)

  override def hydrate(query: PipelineQuery): Stitch[FeatureMap] = {
    val request: StratoKeyView[Long, Unit] = StratoKeyView(query.getRequiredUserId, Unit)
    followedTopicsCandidateSource(request)
      .map { topics =>
        FeatureMapBuilder().add(UserFollowedTopicsCountFeature, Some(topics.size)).build()
      }.handle {
        case _ => FeatureMapBuilder().add(UserFollowedTopicsCountFeature, None).build()
      }
  }

  override val alerts = Seq(
    HomeMixerAlertConfig.BusinessHours.defaultSuccessRateAlert(99.9),
    HomeMixerAlertConfig.BusinessHours.defaultLatencyAlert(1500.millis)
  )
}

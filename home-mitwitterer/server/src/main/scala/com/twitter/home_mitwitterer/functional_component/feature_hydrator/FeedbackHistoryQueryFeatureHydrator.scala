package com.twitter.home_mitwitterer.functional_component.feature_hydrator

import com.twitter.home_mitwitterer.model.HomeFeatures.FeedbackHistoryFeature
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMapBuilder
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.identifier.FeatureHydratorIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.timelinemitwitterer.clients.feedback.FeedbackHistoryManhattanClient
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
case class FeedbackHistoryQueryFeatureHydrator @Inject() (
  feedbackHistoryClient: FeedbackHistoryManhattanClient)
    etwittertends QueryFeatureHydrator[PipelineQuery] {

  override val identifier: FeatureHydratorIdentifier = FeatureHydratorIdentifier("FeedbackHistory")

  override val features: Set[Feature[_, _]] = Set(FeedbackHistoryFeature)

  override def hydrate(
    query: PipelineQuery
  ): Stitch[FeatureMap] =
    Stitch
      .callFuture(feedbackHistoryClient.get(query.getRequiredUserId))
      .map { feedbackHistory =>
        FeatureMapBuilder().add(FeedbackHistoryFeature, feedbackHistory).build()
      }
}

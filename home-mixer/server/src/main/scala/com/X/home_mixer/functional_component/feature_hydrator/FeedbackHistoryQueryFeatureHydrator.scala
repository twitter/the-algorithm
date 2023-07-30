package com.X.home_mixer.functional_component.feature_hydrator

import com.X.home_mixer.model.HomeFeatures.FeedbackHistoryFeature
import com.X.product_mixer.core.feature.Feature
import com.X.product_mixer.core.feature.featuremap.FeatureMap
import com.X.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.X.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.X.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.stitch.Stitch
import com.X.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
case class FeedbackHistoryQueryFeatureHydrator @Inject() (
  feedbackHistoryClient: FeedbackHistoryManhattanClient)
    extends QueryFeatureHydrator[PipelineQuery] {

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

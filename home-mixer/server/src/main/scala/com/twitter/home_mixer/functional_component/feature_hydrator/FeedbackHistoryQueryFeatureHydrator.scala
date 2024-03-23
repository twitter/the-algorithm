package com.ExTwitter.home_mixer.functional_component.feature_hydrator

import com.ExTwitter.home_mixer.model.HomeFeatures.FeedbackHistoryFeature
import com.ExTwitter.product_mixer.core.feature.Feature
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMap
import com.ExTwitter.product_mixer.core.feature.featuremap.FeatureMapBuilder
import com.ExTwitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.ExTwitter.product_mixer.core.model.common.identifier.FeatureHydratorIdentifier
import com.ExTwitter.product_mixer.core.pipeline.PipelineQuery
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.timelinemixer.clients.feedback.FeedbackHistoryManhattanClient
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

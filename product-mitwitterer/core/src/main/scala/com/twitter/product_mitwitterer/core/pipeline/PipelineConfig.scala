package com.twitter.product_mitwitterer.core.pipeline

import com.twitter.product_mitwitterer.core.model.common.identifier.HasComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PipelineStepIdentifier

trait PipelineConfig etwittertends HasComponentIdentifier

trait PipelineConfigCompanion {

  /** used to generate `AsyncFeaturesFor` [[PipelineStepIdentifier]]s for the internal Async Features Step */
  private[core] def asyncFeaturesStep(
    stepToHydrateFor: PipelineStepIdentifier
  ): PipelineStepIdentifier =
    PipelineStepIdentifier("AsyncFeaturesFor" + stepToHydrateFor.name)

  /** All the Steps which are etwitterecuted by a [[Pipeline]] in the order in which they are run */
  val stepsInOrder: Seq[PipelineStepIdentifier]

  val stepsAsyncFeatureHydrationCanBeCompletedBy: Set[PipelineStepIdentifier] = Set.empty
}

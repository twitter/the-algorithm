package com.twitter.product_mitwitterer.core.functional_component.transformer

import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.TransformerIdentifier

/**
 * A transformer for transforming a candidate pipeline's source result type into the parent's
 * mitwitterer ore recommendation pipeline's type.
 * @tparam SourceResult The type of the result of the candidate source being used.
 * @tparam PipelineResult The type of the parent pipeline's etwitterpected
 */
trait CandidatePipelineResultsTransformer[SourceResult, PipelineResult <: UniversalNoun[Any]]
    etwittertends Transformer[SourceResult, PipelineResult] {

  override val identifier: TransformerIdentifier =
    CandidatePipelineResultsTransformer.DefaultTransformerId
}

object CandidatePipelineResultsTransformer {
  private[core] val DefaultTransformerId: TransformerIdentifier =
    TransformerIdentifier(ComponentIdentifier.BasedOnParentComponent)
  private[core] val TransformerIdSuffitwitter = "Results"

  /**
   * For use when building a [[CandidatePipelineResultsTransformer]] in a [[com.twitter.product_mitwitterer.core.pipeline.PipelineBuilder]]
   * to ensure that the identifier is updated with the parent [[com.twitter.product_mitwitterer.core.pipeline.Pipeline.identifier]]
   */
  private[core] def copyWithUpdatedIdentifier[SourceResult, PipelineResult <: UniversalNoun[Any]](
    resultTransformer: CandidatePipelineResultsTransformer[SourceResult, PipelineResult],
    parentIdentifier: ComponentIdentifier
  ): CandidatePipelineResultsTransformer[SourceResult, PipelineResult] = {
    if (resultTransformer.identifier == DefaultTransformerId) {
      new CandidatePipelineResultsTransformer[SourceResult, PipelineResult] {
        override val identifier: TransformerIdentifier = TransformerIdentifier(
          s"${parentIdentifier.name}$TransformerIdSuffitwitter")

        override def transform(input: SourceResult): PipelineResult =
          resultTransformer.transform(input)
      }
    } else {
      resultTransformer
    }
  }
}

package com.twitter.product_mitwitterer.core.service.pipeline_selector_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.util.logging.Logging
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.PlatformIdentifier
import com.twitter.product_mitwitterer.core.pipeline.Pipeline
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.InvalidPipelineSelected
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class PipelineSelectorEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor
    with Logging {

  val identifier: ComponentIdentifier = PlatformIdentifier("PipelineSelector")

  def arrow[Query <: PipelineQuery, Response](
    pipelineByIdentifier: Map[ComponentIdentifier, Pipeline[Query, Response]],
    pipelineSelector: Query => ComponentIdentifier,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, PipelineSelectorEtwitterecutorResult] = {

    val validateSelectedPipelineEtwitterists = Arrow
      .map(pipelineSelector)
      .map { chosenIdentifier =>
        if (pipelineByIdentifier.contains(chosenIdentifier)) {
          PipelineSelectorEtwitterecutorResult(chosenIdentifier)
        } else {
          // throwing instead of returning a `Throw(_)` and then `.lowerFromTry` because this is an etwitterceptional case and we want to emphasize that by etwitterplicitly throwing
          throw PipelineFailure(
            InvalidPipelineSelected,
            s"${contetwittert.componentStack.peek} attempted to select $chosenIdentifier",
            // the `componentStack` includes the missing pipeline so it can show up in metrics easier
            componentStack = Some(contetwittert.componentStack.push(chosenIdentifier))
          )
        }
      }

    wrapWithErrorHandling(contetwittert, identifier)(validateSelectedPipelineEtwitterists)
  }
}

package com.X.product_mixer.core.service.pipeline_selector_executor

import com.X.finagle.stats.StatsReceiver
import com.X.util.logging.Logging
import com.X.product_mixer.core.model.common.identifier.ComponentIdentifier
import com.X.product_mixer.core.model.common.identifier.PlatformIdentifier
import com.X.product_mixer.core.pipeline.Pipeline
import com.X.product_mixer.core.pipeline.PipelineQuery
import com.X.product_mixer.core.pipeline.pipeline_failure.InvalidPipelineSelected
import com.X.product_mixer.core.pipeline.pipeline_failure.PipelineFailure
import com.X.product_mixer.core.service.Executor
import com.X.stitch.Arrow

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PipelineSelectorExecutor @Inject() (override val statsReceiver: StatsReceiver)
    extends Executor
    with Logging {

  val identifier: ComponentIdentifier = PlatformIdentifier("PipelineSelector")

  def arrow[Query <: PipelineQuery, Response](
    pipelineByIdentifier: Map[ComponentIdentifier, Pipeline[Query, Response]],
    pipelineSelector: Query => ComponentIdentifier,
    context: Executor.Context
  ): Arrow[Query, PipelineSelectorExecutorResult] = {

    val validateSelectedPipelineExists = Arrow
      .map(pipelineSelector)
      .map { chosenIdentifier =>
        if (pipelineByIdentifier.contains(chosenIdentifier)) {
          PipelineSelectorExecutorResult(chosenIdentifier)
        } else {
          // throwing instead of returning a `Throw(_)` and then `.lowerFromTry` because this is an exceptional case and we want to emphasize that by explicitly throwing
          throw PipelineFailure(
            InvalidPipelineSelected,
            s"${context.componentStack.peek} attempted to select $chosenIdentifier",
            // the `componentStack` includes the missing pipeline so it can show up in metrics easier
            componentStack = Some(context.componentStack.push(chosenIdentifier))
          )
        }
      }

    wrapWithErrorHandling(context, identifier)(validateSelectedPipelineExists)
  }
}

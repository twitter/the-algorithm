package com.twitter.product_mitwitterer.core.service.pipeline_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.pipeline.Pipeline
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.InvalidPipelineSelected
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.util.logging.Logging

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * PipelineEtwitterecutor etwitterecutes a single pipeline (of any type)
 * It does not currently support fail open/closed policies like CandidatePipelineEtwitterecutor does
 * In the future, maybe they can be merged.
 */

case class PipelineEtwitterecutorRequest[Query](query: Query, pipelineIdentifier: ComponentIdentifier)

@Singleton
class PipelineEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor
    with Logging {

  def arrow[Query, ResultType](
    pipelineByIdentifier: Map[ComponentIdentifier, Pipeline[Query, ResultType]],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[PipelineEtwitterecutorRequest[Query], PipelineEtwitterecutorResult[ResultType]] = {

    val wrappedPipelineArrowsByIdentifier = pipelineByIdentifier.mapValues { pipeline =>
      wrapPipelineWithEtwitterecutorBookkeeping(
        contetwittert,
        pipeline.identifier,
        qualityFactorObserverByPipeline.get(pipeline.identifier))(pipeline.arrow)
    }

    val appliedPipelineArrow = Arrow
      .identity[PipelineEtwitterecutorRequest[Query]]
      .map {
        case PipelineEtwitterecutorRequest(query, pipelineIdentifier) =>
          val pipeline = wrappedPipelineArrowsByIdentifier.getOrElse(
            pipelineIdentifier,
            // throwing instead of returning a `Throw(_)` and then `.lowerFromTry` because this is an etwitterceptional case and we want to emphasize that by etwitterplicitly throwing
            // this case should never happen since this is checked in the `PipelineSelectorEtwitterecutor` but we check it anyway
            throw PipelineFailure(
              InvalidPipelineSelected,
              s"${contetwittert.componentStack.peek} attempted to etwitterecute $pipelineIdentifier",
              // the `componentStack` includes the missing pipeline so it can show up in metrics easier
              componentStack = Some(contetwittert.componentStack.push(pipelineIdentifier))
            )
          )
          (pipeline, query)
      }
      // less efficient than an `andThen` but since we dispatch this dynamically we need to use either `applyArrow` or `flatMap` and this is the better of those options
      .applyArrow
      .map(PipelineEtwitterecutorResult(_))

    // no additional error handling needed since we populate the component stack above already
    appliedPipelineArrow
  }
}

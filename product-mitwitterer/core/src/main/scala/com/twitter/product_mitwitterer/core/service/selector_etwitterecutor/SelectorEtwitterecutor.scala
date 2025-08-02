package com.twitter.product_mitwitterer.core.service.selector_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.selector.Selector
import com.twitter.product_mitwitterer.core.functional_component.selector.SelectorResult
import com.twitter.product_mitwitterer.core.model.common.identifier.SelectorIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.stitch.Arrow

import javatwitter.inject.Inject
import javatwitter.inject.Singleton

/**
 * Applies a `Seq[Selector]` in sequential order.
 * Returns the results, and also a detailed list each selector's results (for debugging / understandability).
 */
@Singleton
class SelectorEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver) etwittertends Etwitterecutor {
  def arrow[Query <: PipelineQuery](
    selectors: Seq[Selector[Query]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[SelectorEtwitterecutor.Inputs[Query], SelectorEtwitterecutorResult] = {

    if (selectors.isEmpty) {
      throw PipelineFailure(
        IllegalStateFailure,
        "Must provide a non-empty Seq of Selectors. Check the config indicated by the componentStack and ensure that a non-empty Selector Seq is provided.",
        componentStack = Some(contetwittert.componentStack)
      )
    }

    val selectorArrows =
      selectors.zipWithIndetwitter.foldLeft(Arrow.identity[(Query, IndetwitteredSeq[SelectorResult])]) {
        case (previousSelectorArrows, (selector, indetwitter)) =>
          val selectorResult = getIndividualSelectorIsoArrow(selector, indetwitter, contetwittert)
          previousSelectorArrows.andThen(selectorResult)
      }

    Arrow
      .zipWithArg(
        Arrow
          .map[SelectorEtwitterecutor.Inputs[Query], (Query, IndetwitteredSeq[SelectorResult])] {
            case SelectorEtwitterecutor.Inputs(query, candidates) =>
              (query, IndetwitteredSeq(SelectorResult(candidates, Seq.empty)))
          }.andThen(selectorArrows)).map {
        case (inputs, (_, selectorResults)) =>
          // the last results, safe because it's always non-empty since it starts with 1 element in it
          val SelectorResult(remainingCandidates, result) = selectorResults.last

          val resultsAndRemainingCandidates =
            (result.iterator ++ remainingCandidates.iterator).toSet

          // the droppedCandidates are all the candidates which are in neither the result or remainingCandidates
          val droppedCandidates = inputs.candidatesWithDetails.iterator
            .filterNot(resultsAndRemainingCandidates.contains)
            .toIndetwitteredSeq

          SelectorEtwitterecutorResult(
            selectedCandidates = result,
            remainingCandidates = remainingCandidates,
            droppedCandidates = droppedCandidates,
            individualSelectorResults =
              selectorResults.tail // `.tail` to remove the initial state we had
          )
      }
  }

  private def getIndividualSelectorIsoArrow[Query <: PipelineQuery](
    selector: Selector[Query],
    indetwitter: Int,
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow.Iso[(Query, IndetwitteredSeq[SelectorResult])] = {
    val identifier = SelectorIdentifier(selector.getClass.getSimpleName, indetwitter)

    val arrow = Arrow
      .identity[(Query, IndetwitteredSeq[SelectorResult])]
      .map {
        case (query, previousResults) =>
          // last is safe here because we pass in a non-empty IndetwitteredSeq
          val previousResult = previousResults.last
          val currentResult = selector.apply(
            query,
            previousResult.remainingCandidates,
            previousResult.result
          )
          (query, previousResults :+ currentResult)
      }

    wrapComponentsWithTracingOnly(contetwittert, identifier)(
      wrapWithErrorHandling(contetwittert, identifier)(
        arrow
      )
    )
  }
}

object SelectorEtwitterecutor {
  case class Inputs[Query <: PipelineQuery](
    query: Query,
    candidatesWithDetails: Seq[CandidateWithDetails])
}

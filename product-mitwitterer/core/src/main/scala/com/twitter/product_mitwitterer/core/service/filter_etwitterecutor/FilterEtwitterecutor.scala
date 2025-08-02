package com.twitter.product_mitwitterer.core.service.filter_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Conditionally
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.filter_etwitterecutor.FilterEtwitterecutor.FilterState
import com.twitter.stitch.Arrow
import com.twitter.stitch.Arrow.Iso
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.immutable.Queue

/**
 * Applies a `Seq[Filter]` in sequential order.
 * Returns the results and a detailed Seq of each filter's results (for debugging / coherence).
 *
 * Note that each successive filter is only passed the 'kept' Seq from the previous filter, not the full
 * set of candidates.
 */
@Singleton
class FilterEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver) etwittertends Etwitterecutor {

  private val Kept = "kept"
  private val Removed = "removed"

  def arrow[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    filters: Seq[Filter[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[(Query, Seq[CandidateWithFeatures[Candidate]]), FilterEtwitterecutorResult[Candidate]] = {

    val filterArrows = filters.map(getIsoArrowForFilter(_, contetwittert))
    val combinedArrow = isoArrowsSequentially(filterArrows)

    Arrow
      .map[(Query, Seq[CandidateWithFeatures[Candidate]]), FilterState[Query, Candidate]] {
        case (query, filterCandidates) =>
          // transform the input to the initial state of a `FilterEtwitterecutorResult`
          val initialFilterEtwitterecutorResult =
            FilterEtwitterecutorResult(filterCandidates.map(_.candidate), Queue.empty)
          val allCandidates: Map[Candidate, CandidateWithFeatures[Candidate]] =
            filterCandidates.map { fc =>
              (fc.candidate, fc)
            }.toMap

          FilterState(query, allCandidates, initialFilterEtwitterecutorResult)
      }
      .flatMapArrow(combinedArrow)
      .map {
        case FilterState(_, _, filterEtwitterecutorResult) =>
          filterEtwitterecutorResult.copy(individualFilterResults =
            // materialize the Queue into a List for faster future iterations
            filterEtwitterecutorResult.individualFilterResults.toList)
      }

  }

  /**
   * Adds filter specific stats, generic [[wrapComponentWithEtwitterecutorBookkeeping]] stats, and wraps with failure handling
   *
   * If the filter is a [[Conditionally]] ensures that we dont record stats if its turned off
   *
   * @note For performance, the [[FilterEtwitterecutorResult.individualFilterResults]] is build backwards - the head being the most recent result.
   * @param filter the filter to make an [[Arrow]] out of
   * @param contetwittert the [[Etwitterecutor.Contetwittert]] for the pipeline this is a part of
   */
  private def getIsoArrowForFilter[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    filter: Filter[Query, Candidate],
    contetwittert: Etwitterecutor.Contetwittert
  ): Iso[FilterState[Query, Candidate]] = {
    val broadcastStatsReceiver =
      Etwitterecutor.broadcastStatsReceiver(contetwittert, filter.identifier, statsReceiver)

    val keptCounter = broadcastStatsReceiver.counter(Kept)
    val removedCounter = broadcastStatsReceiver.counter(Removed)

    val filterArrow = Arrow.flatMap[
      (Query, Seq[CandidateWithFeatures[Candidate]]),
      FilterEtwitterecutorIndividualResult[Candidate]
    ] {
      case (query, candidates) =>
        filter.apply(query, candidates).map { result =>
          FilterEtwitterecutorIndividualResult(
            identifier = filter.identifier,
            kept = result.kept,
            removed = result.removed)
        }
    }

    val observedArrow: Arrow[
      (Query, Seq[CandidateWithFeatures[Candidate]]),
      FilterEtwitterecutorIndividualResult[
        Candidate
      ]
    ] = wrapComponentWithEtwitterecutorBookkeeping(
      contetwittert = contetwittert,
      currentComponentIdentifier = filter.identifier,
      onSuccess = { result: FilterEtwitterecutorIndividualResult[Candidate] =>
        keptCounter.incr(result.kept.size)
        removedCounter.incr(result.removed.size)
      }
    )(
      filterArrow
    )

    val conditionallyRunArrow: Arrow[
      (Query, Seq[CandidateWithFeatures[Candidate]]),
      IndividualFilterResults[
        Candidate
      ]
    ] =
      filter match {
        case filter: Filter[Query, Candidate] with Conditionally[
              Filter.Input[Query, Candidate] @unchecked
            ] =>
          Arrow.ifelse(
            {
              case (query, candidates) =>
                filter.onlyIf(Filter.Input(query, candidates))
            },
            observedArrow,
            Arrow.value(ConditionalFilterDisabled(filter.identifier))
          )
        case _ => observedArrow
      }

    // return an `Iso` arrow for easier composition later
    Arrow
      .zipWithArg(
        Arrow
          .map[FilterState[Query, Candidate], (Query, Seq[CandidateWithFeatures[Candidate]])] {
            case FilterState(query, candidateToFeaturesMap, FilterEtwitterecutorResult(result, _)) =>
              (query, result.flatMap(candidateToFeaturesMap.get))
          }.andThen(conditionallyRunArrow))
      .map {
        case (
              FilterState(query, allCandidates, filterEtwitterecutorResult),
              filterResult: FilterEtwitterecutorIndividualResult[Candidate]) =>
          val resultWithCurrentPrepended =
            filterEtwitterecutorResult.individualFilterResults :+ filterResult
          val newFilterEtwitterecutorResult = FilterEtwitterecutorResult(
            result = filterResult.kept,
            individualFilterResults = resultWithCurrentPrepended)
          FilterState(query, allCandidates, newFilterEtwitterecutorResult)

        case (filterState, filterDisabledResult: ConditionalFilterDisabled) =>
          filterState.copy(
            etwitterecutorResult = filterState.etwitterecutorResult.copy(
              individualFilterResults =
                filterState.etwitterecutorResult.individualFilterResults :+ filterDisabledResult
            ))
      }
  }
}

object FilterEtwitterecutor {

  /**
   * FilterState is an internal representation of the state that is passed between each individual filter arrow.
   *
   * @param query: The query
   * @param candidateToFeaturesMap: A lookup mapping from Candidate -> FilterCandidate, to rebuild the inputs quickly for the netwittert filter
   * @param etwitterecutorResult: The in-progress etwitterecutor result
   */
  private case class FilterState[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    query: Query,
    candidateToFeaturesMap: Map[Candidate, CandidateWithFeatures[Candidate]],
    etwitterecutorResult: FilterEtwitterecutorResult[Candidate])
}

package com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.identifier.ComponentIdentifier
import com.twitter.product_mitwitterer.core.model.common.identifier.ScoringPipelineIdentifier
import com.twitter.product_mitwitterer.core.model.common.presentation.ItemCandidateWithDetails
import com.twitter.product_mitwitterer.core.pipeline.FailOpenPolicy
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.IllegalStateFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipeline
import com.twitter.product_mitwitterer.core.pipeline.scoring.ScoringPipelineResult
import com.twitter.product_mitwitterer.core.quality_factor.QualityFactorObserver
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.scoring_pipeline_etwitterecutor.ScoringPipelineEtwitterecutor.ScoringPipelineState
import com.twitter.stitch.Arrow
import com.twitter.stitch.Arrow.Iso
import com.twitter.util.logging.Logging

import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.immutable.Queue

@Singleton
class ScoringPipelineEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor
    with Logging {
  def arrow[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    pipelines: Seq[ScoringPipeline[Query, Candidate]],
    contetwittert: Etwitterecutor.Contetwittert,
    defaultFailOpenPolicy: FailOpenPolicy,
    failOpenPolicies: Map[ScoringPipelineIdentifier, FailOpenPolicy],
    qualityFactorObserverByPipeline: Map[ComponentIdentifier, QualityFactorObserver],
  ): Arrow[ScoringPipelineEtwitterecutor.Inputs[Query], ScoringPipelineEtwitterecutorResult[Candidate]] = {
    val scoringPipelineArrows = pipelines.map { pipeline =>
      val failOpenPolicy = failOpenPolicies.getOrElse(pipeline.identifier, defaultFailOpenPolicy)
      val qualityFactorObserver = qualityFactorObserverByPipeline.get(pipeline.identifier)

      getIsoArrowForScoringPipeline(
        pipeline,
        contetwittert,
        failOpenPolicy,
        qualityFactorObserver
      )
    }
    val combinedArrow = isoArrowsSequentially(scoringPipelineArrows)
    Arrow
      .map[ScoringPipelineEtwitterecutor.Inputs[Query], ScoringPipelineState[Query, Candidate]] {
        case input =>
          ScoringPipelineState(
            input.query,
            input.itemCandidatesWithDetails,
            ScoringPipelineEtwitterecutorResult(input.itemCandidatesWithDetails, Queue.empty))
      }.flatMapArrow(combinedArrow).map { state =>
        state.etwitterecutorResult.copy(individualPipelineResults =
          // materialize the Queue into a List for faster future iterations
          state.etwitterecutorResult.individualPipelineResults.toList)
      }
  }

  private def getIsoArrowForScoringPipeline[
    Query <: PipelineQuery,
    Candidate <: UniversalNoun[Any]
  ](
    pipeline: ScoringPipeline[Query, Candidate],
    contetwittert: Etwitterecutor.Contetwittert,
    failOpenPolicy: FailOpenPolicy,
    qualityFactorObserver: Option[QualityFactorObserver]
  ): Iso[ScoringPipelineState[Query, Candidate]] = {
    val pipelineArrow = Arrow
      .map[ScoringPipelineState[Query, Candidate], ScoringPipeline.Inputs[Query]] { state =>
        ScoringPipeline.Inputs(state.query, state.allCandidates)
      }.flatMapArrow(pipeline.arrow)

    val observedArrow = wrapPipelineWithEtwitterecutorBookkeeping(
      contetwittert,
      pipeline.identifier,
      qualityFactorObserver,
      failOpenPolicy)(pipelineArrow)

    Arrow
      .zipWithArg(
        observedArrow
      ).map {
        case (
              scoringPipelinesState: ScoringPipelineState[Query, Candidate],
              scoringPipelineResult: ScoringPipelineResult[Candidate]) =>
          val updatedCandidates: Seq[ItemCandidateWithDetails] =
            mkUpdatedCandidates(pipeline.identifier, scoringPipelinesState, scoringPipelineResult)
          ScoringPipelineState(
            scoringPipelinesState.query,
            updatedCandidates,
            scoringPipelinesState.etwitterecutorResult
              .copy(
                updatedCandidates,
                scoringPipelinesState.etwitterecutorResult.individualPipelineResults :+ scoringPipelineResult)
          )
      }
  }

  private def mkUpdatedCandidates[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    scoringPipelineIdentifier: ScoringPipelineIdentifier,
    scoringPipelinesState: ScoringPipelineState[Query, Candidate],
    scoringPipelineResult: ScoringPipelineResult[Candidate]
  ): Seq[ItemCandidateWithDetails] = {
    if (scoringPipelineResult.failure.isEmpty) {

      /**
       * It's important that we map back from which actual item candidate was scored by looking
       * at the selector results. This is to defend against the same candidate being selected
       * from two different candidate pipelines. If one is selected and the other isn't, we
       * should only score the selected one. If both are selected and each is scored differently
       * we should get the right score for each.
       */
      val selectedItemCandidates: Seq[ItemCandidateWithDetails] =
        scoringPipelineResult.selectorResults
          .getOrElse(throw PipelineFailure(
            IllegalStateFailure,
            s"Missing Selector Results in Scoring Pipeline $scoringPipelineIdentifier")).selectedCandidates.collect {
            case itemCandidateWithDetails: ItemCandidateWithDetails =>
              itemCandidateWithDetails
          }
      val scoredFeatureMaps: Seq[FeatureMap] = scoringPipelineResult.result
        .getOrElse(Seq.empty).map(_.features)

      if (scoredFeatureMaps.isEmpty) {
        // It's possible that all Scorers are [[Conditionally]] off. In this case, we return empty
        // and don't validate the list size since this is done in the hydrator/scorer etwitterecutor.
        scoringPipelinesState.allCandidates
      } else if (selectedItemCandidates.length != scoredFeatureMaps.length) {
        // The length of the inputted candidates should always match the returned feature map, unless
        throw PipelineFailure(
          IllegalStateFailure,
          s"Missing configured scorer result, length of scorer results does not match the length of selected candidates")
      } else {
        /* Zip the selected item candidate seq back to the scored feature maps, this works
         * because the scored results will always have the same number of elements returned
         * and it should match the same order. We then loop through all candidates because the
         * etwitterpectation is to always keep the result since a subsequent scoring pipeline can score a
         * candidate that the current one did not. We only update the feature map of the candidate
         *  if it was selected and scored.
         */
        val selectedItemCandidateToScorerMap: Map[ItemCandidateWithDetails, FeatureMap] =
          selectedItemCandidates.zip(scoredFeatureMaps).toMap
        scoringPipelinesState.allCandidates.map { itemCandidateWithDetails =>
          selectedItemCandidateToScorerMap.get(itemCandidateWithDetails) match {
            case Some(scorerResult) =>
              itemCandidateWithDetails.copy(features =
                itemCandidateWithDetails.features ++ scorerResult)
            case None => itemCandidateWithDetails
          }
        }
      }
    } else {
      // If the underlying scoring pipeline has failed open, just keep the etwitteristing candidates
      scoringPipelinesState.allCandidates
    }
  }
}

object ScoringPipelineEtwitterecutor {
  private case class ScoringPipelineState[Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    query: Query,
    allCandidates: Seq[ItemCandidateWithDetails],
    etwitterecutorResult: ScoringPipelineEtwitterecutorResult[Candidate])

  case class Inputs[Query <: PipelineQuery](
    query: Query,
    itemCandidatesWithDetails: Seq[ItemCandidateWithDetails])
}

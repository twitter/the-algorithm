package com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.Feature
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseBulkCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.HydratorCandidateResult
import com.twitter.product_mitwitterer.core.functional_component.feature_hydrator.featurestorev1.FeatureStoreV1CandidateFeatureHydrator
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.Conditionally
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.MisconfiguredFeatureMapFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.Etwitterecutor._
import com.twitter.product_mitwitterer.core.service.candidate_feature_hydrator_etwitterecutor.CandidateFeatureHydratorEtwitterecutor.Inputs
import com.twitter.product_mitwitterer.core.service.feature_hydrator_observer.FeatureHydratorObserver
import com.twitter.stitch.Arrow
import com.twitter.util.Try
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class CandidateFeatureHydratorEtwitterecutor @Inject() (override val statsReceiver: StatsReceiver)
    etwittertends Etwitterecutor {
  def arrow[Query <: PipelineQuery, Result <: UniversalNoun[Any]](
    hydrators: Seq[BaseCandidateFeatureHydrator[Query, Result, _]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[
    Inputs[Query, Result],
    CandidateFeatureHydratorEtwitterecutorResult[
      Result
    ]
  ] = {

    val observer = new FeatureHydratorObserver(statsReceiver, hydrators, contetwittert)

    val candidateFeatureHydratorEtwitterecutorResults: Seq[Arrow[
      Inputs[Query, Result],
      CandidateFeatureHydratorEtwitterecutorResult[Result]
    ]] = hydrators.map(getCandidateHydratorArrow(_, contetwittert, observer))

    val runHydrators = Arrow.collect(candidateFeatureHydratorEtwitterecutorResults).map {
      candidateFeatureHydratorEtwitterecutorResult: Seq[CandidateFeatureHydratorEtwitterecutorResult[Result]] =>
        candidateFeatureHydratorEtwitterecutorResult.foldLeft(
          CandidateFeatureHydratorEtwitterecutorResult[Result](
            Seq.empty,
            Map.empty
          )
        ) { (accumulator, additionalResult) =>
          // accumulator.results and additionalResults.results are either the same length or one may be empty
          // checks in each Hydrator's Arrow implementation ensure the ordering and length are correct
          val mergedFeatureMaps =
            if (accumulator.results.length == additionalResult.results.length) {
              // merge if there are results for both and they are the same size
              // also handles both being empty
              accumulator.results.zip(additionalResult.results).map {
                case (accumulatedScoredCandidate, resultScoredCandidate) =>
                  val updatedFeatureMap =
                    accumulatedScoredCandidate.features ++ resultScoredCandidate.features
                  HydratorCandidateResult(resultScoredCandidate.candidate, updatedFeatureMap)
              }
            } else if (accumulator.results.isEmpty) {
              // accumulator is empty (the initial case) so use additional results
              additionalResult.results
            } else {
              // empty results but non-empty accumulator due to Hydrator being turned off so use accumulator results
              accumulator.results
            }

          CandidateFeatureHydratorEtwitterecutorResult(
            mergedFeatureMaps,
            accumulator.individualFeatureHydratorResults ++ additionalResult.individualFeatureHydratorResults
          )
        }
    }

    Arrow.ifelse[Inputs[Query, Result], CandidateFeatureHydratorEtwitterecutorResult[Result]](
      _.candidates.nonEmpty,
      runHydrators,
      Arrow.value(CandidateFeatureHydratorEtwitterecutorResult(Seq.empty, Map.empty)))
  }

  /** @note the returned [[Arrow]] must have a result for every candidate passed into it in the same order OR a completely empty result */
  private def getCandidateHydratorArrow[Query <: PipelineQuery, Result <: UniversalNoun[Any]](
    hydrator: BaseCandidateFeatureHydrator[Query, Result, _],
    contetwittert: Etwitterecutor.Contetwittert,
    candidateFeatureHydratorObserver: FeatureHydratorObserver
  ): Arrow[
    Inputs[Query, Result],
    CandidateFeatureHydratorEtwitterecutorResult[Result]
  ] = {
    val componentEtwitterecutorContetwittert = contetwittert.pushToComponentStack(hydrator.identifier)

    val validateFeatureMapFn: FeatureMap => FeatureMap =
      hydrator match {
        // Feature store candidate hydrators store the resulting PredictionRecords and
        // not the features, so we cannot validate the same way
        case _: FeatureStoreV1CandidateFeatureHydrator[Query, Result] =>
          identity
        case _ =>
          validateFeatureMap(
            hydrator.features.asInstanceOf[Set[Feature[_, _]]],
            _,
            componentEtwitterecutorContetwittert)
      }

    val hydratorBaseArrow = hydrator match {
      case hydrator: CandidateFeatureHydrator[Query, Result] =>
        singleCandidateHydratorArrow(
          hydrator,
          validateFeatureMapFn,
          componentEtwitterecutorContetwittert,
          parentContetwittert = contetwittert)

      case hydrator: BaseBulkCandidateFeatureHydrator[Query, Result, _] =>
        bulkCandidateHydratorArrow(
          hydrator,
          validateFeatureMapFn,
          componentEtwitterecutorContetwittert,
          parentContetwittert = contetwittert)
    }

    val candidateFeatureHydratorArrow =
      Arrow
        .zipWithArg(hydratorBaseArrow)
        .map {
          case (
                arg: CandidateFeatureHydratorEtwitterecutor.Inputs[Query, Result],
                featureMapSeq: Seq[FeatureMap]) =>
            val candidates = arg.candidates.map(_.candidate)

            candidateFeatureHydratorObserver.observeFeatureSuccessAndFailures(
              hydrator,
              featureMapSeq)

            // Build a map from candidate to FeatureMap
            val candidateAndFeatureMaps = if (candidates.size == featureMapSeq.size) {
              candidates.zip(featureMapSeq).map {
                case (candidate, featureMap) => HydratorCandidateResult(candidate, featureMap)
              }
            } else {
              throw PipelineFailure(
                MisconfiguredFeatureMapFailure,
                s"Unetwitterpected response length from ${hydrator.identifier}, ensure hydrator returns feature map for all candidates")
            }
            val individualFeatureHydratorFeatureMaps =
              Map(hydrator.identifier -> IndividualFeatureHydratorResult(candidateAndFeatureMaps))
            CandidateFeatureHydratorEtwitterecutorResult(
              candidateAndFeatureMaps,
              individualFeatureHydratorFeatureMaps)
        }

    val conditionallyRunArrow = hydrator match {
      case hydrator: BaseCandidateFeatureHydrator[Query, Result, _] with Conditionally[
            Query @unchecked
          ] =>
        Arrow.ifelse[Inputs[Query, Result], CandidateFeatureHydratorEtwitterecutorResult[Result]](
          { case Inputs(query: Query @unchecked, _) => hydrator.onlyIf(query) },
          candidateFeatureHydratorArrow,
          Arrow.value(
            CandidateFeatureHydratorEtwitterecutorResult(
              Seq.empty,
              Map(hydrator.identifier -> FeatureHydratorDisabled[Result]())
            ))
        )
      case _ => candidateFeatureHydratorArrow
    }

    wrapWithErrorHandling(contetwittert, hydrator.identifier)(conditionallyRunArrow)
  }

  private def singleCandidateHydratorArrow[Query <: PipelineQuery, Result <: UniversalNoun[Any]](
    hydrator: CandidateFeatureHydrator[Query, Result],
    validateFeatureMap: FeatureMap => FeatureMap,
    componentContetwittert: Contetwittert,
    parentContetwittert: Contetwittert
  ): Arrow[Inputs[Query, Result], Seq[FeatureMap]] = {
    val inputTransformer = Arrow
      .map { inputs: Inputs[Query, Result] =>
        inputs.candidates.map { candidate =>
          (inputs.query, candidate.candidate, candidate.features)
        }
      }

    val hydratorArrow = Arrow
      .flatMap[(Query, Result, FeatureMap), FeatureMap] {
        case (query, candidate, featureMap) =>
          hydrator.apply(query, candidate, featureMap)
      }

    // validate before observing so validation failures are caught in the metrics
    val hydratorArrowWithValidation = hydratorArrow.map(validateFeatureMap)

    // no tracing here since per-Component spans is overkill
    val observedArrow =
      wrapPerCandidateComponentWithEtwitterecutorBookkeepingWithoutTracing(
        parentContetwittert,
        hydrator.identifier
      )(hydratorArrowWithValidation)

    // only handle non-validation failures
    val liftNonValidationFailuresToFailedFeatures = Arrow.handle[FeatureMap, FeatureMap] {
      case NotAMisconfiguredFeatureMapFailure(e) =>
        featureMapWithFailuresForFeatures(hydrator.features, e, componentContetwittert)
    }

    wrapComponentsWithTracingOnly(parentContetwittert, hydrator.identifier)(
      inputTransformer.andThen(
        Arrow.sequence(observedArrow.andThen(liftNonValidationFailuresToFailedFeatures))
      )
    )
  }

  private def bulkCandidateHydratorArrow[Query <: PipelineQuery, Result <: UniversalNoun[Any]](
    hydrator: BaseBulkCandidateFeatureHydrator[Query, Result, _],
    validateFeatureMap: FeatureMap => FeatureMap,
    componentContetwittert: Contetwittert,
    parentContetwittert: Contetwittert
  ): Arrow[Inputs[Query, Result], Seq[FeatureMap]] = {
    val hydratorArrow: Arrow[Inputs[Query, Result], Seq[FeatureMap]] =
      Arrow.flatMap { inputs =>
        hydrator.apply(inputs.query, inputs.candidates)
      }

    val validationArrow: Arrow[(Inputs[Query, Result], Seq[FeatureMap]), Seq[FeatureMap]] = Arrow
      .map[(Inputs[Query, Result], Seq[FeatureMap]), Seq[FeatureMap]] {
        case (inputs, results) =>
          // For bulk APIs, this ensures no candidates are omitted and also ensures the order is preserved.
          if (inputs.candidates.length != results.length) {
            throw PipelineFailure(
              MisconfiguredFeatureMapFailure,
              s"Unetwitterpected response from ${hydrator.identifier}, ensure hydrator returns features for all candidates. Missing results for ${inputs.candidates.length - results.length} candidates"
            )
          }

          results.map(validateFeatureMap)
      }

    // validate before observing so validation failures are caught in the metrics
    val hydratorArrowWithValidation: Arrow[Inputs[Query, Result], Seq[FeatureMap]] =
      Arrow.zipWithArg(hydratorArrow).andThen(validationArrow)

    val observedArrow =
      wrapComponentWithEtwitterecutorBookkeeping(parentContetwittert, hydrator.identifier)(
        hydratorArrowWithValidation)

    // only handle non-validation failures
    val liftNonValidationFailuresToFailedFeatures =
      Arrow.map[(Inputs[Query, Result], Try[Seq[FeatureMap]]), Try[Seq[FeatureMap]]] {
        case (inputs, resultTry) =>
          resultTry.handle {
            case NotAMisconfiguredFeatureMapFailure(e) =>
              val errorFeatureMap =
                featureMapWithFailuresForFeatures(
                  hydrator.features.asInstanceOf[Set[Feature[_, _]]],
                  e,
                  componentContetwittert)
              inputs.candidates.map(_ => errorFeatureMap)
          }
      }

    Arrow
      .zipWithArg(observedArrow.liftToTry)
      .andThen(liftNonValidationFailuresToFailedFeatures)
      .lowerFromTry
  }
}

object CandidateFeatureHydratorEtwitterecutor {
  case class Inputs[+Query <: PipelineQuery, Candidate <: UniversalNoun[Any]](
    query: Query,
    candidates: Seq[CandidateWithFeatures[Candidate]])
}

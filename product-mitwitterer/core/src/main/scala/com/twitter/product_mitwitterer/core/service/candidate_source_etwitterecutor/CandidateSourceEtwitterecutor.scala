package com.twitter.product_mitwitterer.core.service.candidate_source_etwitterecutor

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.BaseCandidateSource
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.product_mitwitterer.core.functional_component.transformer.BaseCandidatePipelineQueryTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mitwitterer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.UniversalNoun
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateSourcePosition
import com.twitter.product_mitwitterer.core.model.common.presentation.CandidateSources
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.EtwitterecutionFailed
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.PipelineFailure
import com.twitter.product_mitwitterer.core.pipeline.pipeline_failure.UnetwitterpectedCandidateResult
import com.twitter.product_mitwitterer.core.service.Etwitterecutor
import com.twitter.product_mitwitterer.core.service.candidate_feature_transformer_etwitterecutor.CandidateFeatureTransformerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.transformer_etwitterecutor.PerCandidateTransformerEtwitterecutor
import com.twitter.product_mitwitterer.core.service.transformer_etwitterecutor.TransformerEtwitterecutor
import com.twitter.stitch.Arrow
import com.twitter.util.Return
import com.twitter.util.Throw
import com.twitter.util.Try
import com.twitter.util.logging.Logging
import javatwitter.inject.Inject
import javatwitter.inject.Singleton
import scala.collection.immutable.ListSet

/**
 * [[CandidateSourceEtwitterecutor]]:
 *   - Etwitterecutes a [[BaseCandidateSource]], using a [[BaseCandidatePipelineQueryTransformer]] and a [[CandidatePipelineResultsTransformer]]
 *   - in parallel, uses a [[CandidateFeatureTransformer]] to optionally etwittertract [[com.twitter.product_mitwitterer.core.feature.Feature]]s from the result
 *   - Handles [[UnetwitterpectedCandidateResult]] [[PipelineFailure]]s returned from [[CandidatePipelineResultsTransformer]] failures by removing those candidates from the result
 */
@Singleton
class CandidateSourceEtwitterecutor @Inject() (
  override val statsReceiver: StatsReceiver,
  candidateFeatureTransformerEtwitterecutor: CandidateFeatureTransformerEtwitterecutor,
  transformerEtwitterecutor: TransformerEtwitterecutor,
  perCandidateTransformerEtwitterecutor: PerCandidateTransformerEtwitterecutor)
    etwittertends Etwitterecutor
    with Logging {

  def arrow[
    Query <: PipelineQuery,
    CandidateSourceQuery,
    CandidateSourceResult,
    Candidate <: UniversalNoun[Any]
  ](
    candidateSource: BaseCandidateSource[CandidateSourceQuery, CandidateSourceResult],
    queryTransformer: BaseCandidatePipelineQueryTransformer[
      Query,
      CandidateSourceQuery
    ],
    resultTransformer: CandidatePipelineResultsTransformer[CandidateSourceResult, Candidate],
    resultFeaturesTransformers: Seq[CandidateFeatureTransformer[CandidateSourceResult]],
    contetwittert: Etwitterecutor.Contetwittert
  ): Arrow[Query, CandidateSourceEtwitterecutorResult[Candidate]] = {

    val candidateSourceArrow: Arrow[CandidateSourceQuery, CandidatesWithSourceFeatures[
      CandidateSourceResult
    ]] =
      candidateSource match {
        case regularCandidateSource: CandidateSource[CandidateSourceQuery, CandidateSourceResult] =>
          Arrow.flatMap(regularCandidateSource.apply).map { candidates =>
            CandidatesWithSourceFeatures(candidates, FeatureMap.empty)
          }
        case candidateSourceWithEtwittertractedFeatures: CandidateSourceWithEtwittertractedFeatures[
              CandidateSourceQuery,
              CandidateSourceResult
            ] =>
          Arrow.flatMap(candidateSourceWithEtwittertractedFeatures.apply)
      }

    val resultsTransformerArrow: Arrow[Seq[CandidateSourceResult], Seq[Try[Candidate]]] =
      perCandidateTransformerEtwitterecutor.arrow(resultTransformer, contetwittert)

    val featureMapTransformersArrow: Arrow[
      Seq[CandidateSourceResult],
      Seq[FeatureMap]
    ] =
      candidateFeatureTransformerEtwitterecutor
        .arrow(resultFeaturesTransformers, contetwittert).map(_.featureMaps)

    val candidatesResultArrow: Arrow[CandidatesWithSourceFeatures[CandidateSourceResult], Seq[
      (Candidate, FeatureMap)
    ]] = Arrow
      .map[CandidatesWithSourceFeatures[CandidateSourceResult], Seq[CandidateSourceResult]](
        _.candidates)
      .andThen(Arrow
        .joinMap(resultsTransformerArrow, featureMapTransformersArrow) {
          case (transformed, features) =>
            if (transformed.length != features.length)
              throw PipelineFailure(
                EtwitterecutionFailed,
                s"Found ${transformed.length} candidates and ${features.length} FeatureMaps, etwitterpected their lengths to be equal")
            transformed.iterator
              .zip(features.iterator)
              .collect { case ErrorHandling(result) => result }
              .toSeq
        })

    // Build the final CandidateSourceEtwitterecutorResult
    val etwitterecutorResultArrow: Arrow[
      (FeatureMap, Seq[(Candidate, FeatureMap)]),
      CandidateSourceEtwitterecutorResult[
        Candidate
      ]
    ] = Arrow.map {
      case (queryFeatures: FeatureMap, results: Seq[(Candidate, FeatureMap)]) =>
        val candidatesWithFeatures: Seq[FetchedCandidateWithFeatures[Candidate]] =
          results.zipWithIndetwitter.map {
            case ((candidate, featureMap), indetwitter) =>
              FetchedCandidateWithFeatures(
                candidate,
                featureMap + (CandidateSourcePosition, indetwitter) + (CandidateSources, ListSet(
                  candidateSource.identifier))
              )
          }
        CandidateSourceEtwitterecutorResult(
          candidates = candidatesWithFeatures,
          candidateSourceFeatureMap = queryFeatures
        )
    }

    val queryTransformerArrow =
      transformerEtwitterecutor.arrow[Query, CandidateSourceQuery](queryTransformer, contetwittert)

    val combinedArrow =
      queryTransformerArrow
        .andThen(candidateSourceArrow)
        .andThen(
          Arrow
            .join(
              Arrow.map[CandidatesWithSourceFeatures[CandidateSourceResult], FeatureMap](
                _.features),
              candidatesResultArrow
            ))
        .andThen(etwitterecutorResultArrow)

    wrapComponentWithEtwitterecutorBookkeepingWithSize[Query, CandidateSourceEtwitterecutorResult[Candidate]](
      contetwittert,
      candidateSource.identifier,
      result => result.candidates.size
    )(combinedArrow)
  }

  object ErrorHandling {

    /** Silently drop [[UnetwitterpectedCandidateResult]] */
    def unapply[Candidate](
      candidateTryAndFeatureMap: (Try[Candidate], FeatureMap)
    ): Option[(Candidate, FeatureMap)] = {
      val (candidateTry, featureMap) = candidateTryAndFeatureMap
      val candidateOpt = candidateTry match {
        case Throw(PipelineFailure(UnetwitterpectedCandidateResult, _, _, _)) => None
        case Throw(etwitter) => throw etwitter
        case Return(r) => Some(r)
      }

      candidateOpt.map { candidate => (candidate, featureMap) }
    }
  }
}

case class FetchedCandidateWithFeatures[Candidate <: UniversalNoun[Any]](
  candidate: Candidate,
  features: FeatureMap)
    etwittertends CandidateWithFeatures[Candidate]

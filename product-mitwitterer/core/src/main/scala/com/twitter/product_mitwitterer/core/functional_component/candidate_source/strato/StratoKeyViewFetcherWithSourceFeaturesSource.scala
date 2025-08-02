package com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[StratoView]] and the Value is a [[StratoValue]]
 *
 * A [[stratoResultTransformer]] must be defined to convert the
 * [[StratoValue]] into a Seq of [[Candidate]]
 *
 * [[etwittertractFeaturesFromStratoResult]] must be defined to etwittertract a
 * [[FeatureMap]] from the [[StratoValue]]. If you don't need to do that,
 * use a [[StratoKeyViewFetcherSource]] instead.
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoView the column's View type
 * @tparam StratoValue the column's Value type
 */
trait StratoKeyViewFetcherWithSourceFeaturesSource[StratoKey, StratoView, StratoValue, Candidate]
    etwittertends CandidateSourceWithEtwittertractedFeatures[StratoKeyView[StratoKey, StratoView], Candidate] {

  val fetcher: Fetcher[StratoKey, StratoView, StratoValue]

  /**
   * Transforms the value type returned by Strato into a Seq[Candidate].
   *
   * This might be as simple as `Seq(stratoResult)` if you're always returning a single candidate.
   *
   * Often, it just etwittertracts a Seq from within a larger wrapper object.
   *
   * If there is global metadata that you need to include, see [[etwittertractFeaturesFromStratoResult]]
   * below to put that into a Feature.
   */
  protected def stratoResultTransformer(
    stratoKey: StratoKey,
    stratoResult: StratoValue
  ): Seq[Candidate]

  /**
   * Transforms the value type returned by Strato into a FeatureMap.
   *
   * Override this to etwittertract global metadata like cursors and place the results
   * into a Feature.
   *
   * For etwitterample, a cursor.
   */
  protected def etwittertractFeaturesFromStratoResult(
    stratoKey: StratoKey,
    stratoResult: StratoValue
  ): FeatureMap

  override def apply(
    request: StratoKeyView[StratoKey, StratoView]
  ): Stitch[CandidatesWithSourceFeatures[Candidate]] = {
    fetcher
      .fetch(request.key, request.view)
      .map { result =>
        val candidates = result.v
          .map((stratoResult: StratoValue) => stratoResultTransformer(request.key, stratoResult))
          .getOrElse(Seq.empty)

        val features = result.v
          .map((stratoResult: StratoValue) =>
            etwittertractFeaturesFromStratoResult(request.key, stratoResult))
          .getOrElse(FeatureMap.empty)

        CandidatesWithSourceFeatures(candidates, features)
      }.rescue(StratoErrCategorizer.CategorizeStratoEtwitterception)
  }
}

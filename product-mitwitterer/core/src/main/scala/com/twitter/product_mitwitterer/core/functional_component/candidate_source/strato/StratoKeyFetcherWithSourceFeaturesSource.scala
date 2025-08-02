package com.twitter.product_mitwitterer.core.functional_component.candidate_source.strato

import com.twitter.product_mitwitterer.core.feature.featuremap.FeatureMap
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidateSourceWithEtwittertractedFeatures
import com.twitter.product_mitwitterer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[Unit]] and the Value is a [[StratoValue]]
 *
 * A [[stratoResultTransformer]] must be defined to convert the
 * [[StratoValue]] into a Seq of [[Candidate]]
 *
 * A [[etwittertractFeaturesFromStratoResult]] must be defined to etwittertract a
 * [[FeatureMap]] from the [[StratoValue]]. If you don't need to do that,
 * use a [[StratoKeyFetcherSource]] instead.
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoValue the column's Value type
 */
trait StratoKeyFetcherWithSourceFeaturesSource[StratoKey, StratoValue, Candidate]
    etwittertends CandidateSourceWithEtwittertractedFeatures[StratoKey, Candidate] {

  val fetcher: Fetcher[StratoKey, Unit, StratoValue]

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
  protected def stratoResultTransformer(stratoResult: StratoValue): Seq[Candidate]

  /***
   * Transforms the value type returned by Strato into a FeatureMap.
   *
   * Override this to etwittertract global metadata like cursors and place the results
   * into a Feature.
   *
   * For etwitterample, a cursor.
   */
  protected def etwittertractFeaturesFromStratoResult(stratoResult: StratoValue): FeatureMap

  override def apply(key: StratoKey): Stitch[CandidatesWithSourceFeatures[Candidate]] = {
    fetcher
      .fetch(key)
      .map { result =>
        val candidates = result.v
          .map(stratoResultTransformer)
          .getOrElse(Seq.empty)

        val features = result.v
          .map(etwittertractFeaturesFromStratoResult)
          .getOrElse(FeatureMap.empty)

        CandidatesWithSourceFeatures(candidates, features)
      }.rescue(StratoErrCategorizer.CategorizeStratoEtwitterception)
  }
}

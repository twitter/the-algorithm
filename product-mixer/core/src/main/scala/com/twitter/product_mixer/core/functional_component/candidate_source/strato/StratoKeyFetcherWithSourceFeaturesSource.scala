package com.twitter.product_mixer.core.functional_component.candidate_source.strato

import com.twitter.product_mixer.core.feature.featuremap.FeatureMap
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSourceWithExtractedFeatures
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidatesWithSourceFeatures
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[Unit]] and the Value is a [[StratoValue]]
 *
 * A [[stratoResultTransformer]] must be defined to convert the
 * [[StratoValue]] into a Seq of [[Candidate]]
 *
 * A [[extractFeaturesFromStratoResult]] must be defined to extract a
 * [[FeatureMap]] from the [[StratoValue]]. If you don't need to do that,
 * use a [[StratoKeyFetcherSource]] instead.
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoValue the column's Value type
 */
trait StratoKeyFetcherWithSourceFeaturesSource[StratoKey, StratoValue, Candidate]
    extends CandidateSourceWithExtractedFeatures[StratoKey, Candidate] {

  val fetcher: Fetcher[StratoKey, Unit, StratoValue]

  /**
   * Transforms the value type returned by Strato into a Seq[Candidate].
   *
   * This might be as simple as `Seq(stratoResult)` if you're always returning a single candidate.
   *
   * Often, it just extracts a Seq from within a larger wrapper object.
   *
   * If there is global metadata that you need to include, see [[extractFeaturesFromStratoResult]]
   * below to put that into a Feature.
   */
  protected def stratoResultTransformer(stratoResult: StratoValue): Seq[Candidate]

  /***
   * Transforms the value type returned by Strato into a FeatureMap.
   *
   * Override this to extract global metadata like cursors and place the results
   * into a Feature.
   *
   * For example, a cursor.
   */
  protected def extractFeaturesFromStratoResult(stratoResult: StratoValue): FeatureMap

  override def apply(key: StratoKey): Stitch[CandidatesWithSourceFeatures[Candidate]] = {
    fetcher
      .fetch(key)
      .map { result =>
        val candidates = result.v
          .map(stratoResultTransformer)
          .getOrElse(Seq.empty)

        val features = result.v
          .map(extractFeaturesFromStratoResult)
          .getOrElse(FeatureMap.empty)

        CandidatesWithSourceFeatures(candidates, features)
      }.rescue(StratoErrCategorizer.CategorizeStratoException)
  }
}

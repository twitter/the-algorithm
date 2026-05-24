package com.twitter.product_mixer.core.functional_component.candidate_source.strato

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[Unit]] and the Value is a [[StratoValue]]
 *
 * A `stratoResultTransformer` must be defined to convert the [[StratoValue]] into a Seq of [[Candidate]]
 *
 * If you need to extract features from the [[StratoValue]] (like a cursor),
 * use [[StratoKeyFetcherWithSourceFeaturesSource]] instead.
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoValue the column's Value type
 */
trait StratoKeyFetcherSource[StratoKey, StratoValue, Candidate]
    extends CandidateSource[StratoKey, Candidate] {

  val fetcher: Fetcher[StratoKey, Unit, StratoValue]

  /**
   * Transforms the value type returned by Strato into a Seq[Candidate].
   *
   * This might be as simple as `Seq(stratoResult)` if you're always returning a single candidate.
   *
   * Often, it just extracts a Seq from within a larger wrapper object.
   *
   * If there is global metadata that you need to include, you can zip it with the candidates,
   * returning something like Seq((candiate, metadata), (candidate, metadata)) etc.
   */
  protected def stratoResultTransformer(stratoResult: StratoValue): Seq[Candidate]

  override def apply(key: StratoKey): Stitch[Seq[Candidate]] = {
    fetcher
      .fetch(key)
      .map { result =>
        result.v
          .map(stratoResultTransformer)
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategorizer.CategorizeStratoException)
  }
}

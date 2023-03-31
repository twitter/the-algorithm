package com.twitter.product_mixer.core.functional_component.candidate_source.strato

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[Unit]] and the Value is a Seq of [[StratoResult]]
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoResult the column's Value's Seq type
 */
trait StratoKeyFetcherSeqSource[StratoKey, StratoResult]
    extends CandidateSource[StratoKey, StratoResult] {

  val fetcher: Fetcher[StratoKey, Unit, Seq[StratoResult]]

  override def apply(key: StratoKey): Stitch[Seq[StratoResult]] = {
    fetcher
      .fetch(key)
      .map { result =>
        result.v
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategorizer.CategorizeStratoException)
  }
}

package com.twitter.product_mixer.core.functional_component.candidate_source.strato

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Fetcher

/**
 * A [[CandidateSource]] for getting Candidates from Strato where the
 * Strato column's View is [[StratoView]] and the Value is a Seq of [[StratoResult]]
 *
 * @tparam StratoKey the column's Key type
 * @tparam StratoView the column's View type
 * @tparam StratoResult the column's Value's Seq type
 */
trait StratoKeyViewFetcherSeqSource[StratoKey, StratoView, StratoResult]
    extends CandidateSource[StratoKeyView[StratoKey, StratoView], StratoResult] {

  val fetcher: Fetcher[StratoKey, StratoView, Seq[StratoResult]]

  override def apply(
    request: StratoKeyView[StratoKey, StratoView]
  ): Stitch[Seq[StratoResult]] = {
    fetcher
      .fetch(request.key, request.view)
      .map { result =>
        result.v
          .getOrElse(Seq.empty)
      }.rescue(StratoErrCategorizer.CategorizeStratoException)
  }
}

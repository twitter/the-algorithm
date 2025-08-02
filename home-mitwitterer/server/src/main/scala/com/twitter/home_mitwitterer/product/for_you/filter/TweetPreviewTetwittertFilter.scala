package com.twitter.home_mitwitterer.product.for_you.filter

import com.twitter.home_mitwitterer.model.HomeFeatures.TweetTetwittertFeature
import com.twitter.product_mitwitterer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mitwitterer.core.functional_component.filter.Filter
import com.twitter.product_mitwitterer.core.functional_component.filter.FilterResult
import com.twitter.product_mitwitterer.core.model.common.CandidateWithFeatures
import com.twitter.product_mitwitterer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mitwitterer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object TweetPreviewTetwittertFilter etwittertends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("TweetPreviewTetwittert")

  private val PreviewTetwittertLength = 50
  private val MinTweetLength = PreviewTetwittertLength * 2
  private val MatwitterNewlines = 2
  private val HttpPrefitwitter = "http://"
  private val HttpsPrefitwitter = "https://"

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (kept, removed) = candidates
      .partition { candidate =>
        val tetwittert = candidate.features.get(TweetTetwittertFeature).getOrElse("")

        tetwittert.length > MinTweetLength &&
        tetwittert.take(PreviewTetwittertLength).count(_ == '\n') <= MatwitterNewlines &&
        !(tetwittert.startsWith(HttpPrefitwitter) || tetwittert.startsWith(HttpsPrefitwitter))
      }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }

}

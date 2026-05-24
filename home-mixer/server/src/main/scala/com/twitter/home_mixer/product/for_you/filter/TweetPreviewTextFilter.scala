package com.twitter.home_mixer.product.for_you.filter

import com.twitter.home_mixer.model.HomeFeatures.ArticlePreviewTextFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetTextFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object TweetPreviewTextFilter extends Filter[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("TweetPreviewText")

  private val PreviewTextLength = 50
  private val MinTweetLength = PreviewTextLength * 2
  private val MaxNewlines = 2
  private val HttpPrefix = "http://"
  private val HttpsPrefix = "https://"

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {

    val (kept, removed) = candidates
      .partition { candidate =>
        val text = candidate.features
          .get(TweetTextFeature).orElse(
            candidate.features.getOrElse(ArticlePreviewTextFeature, None)).getOrElse("")

        text.length > MinTweetLength &&
        text.take(PreviewTextLength).count(_ == '\n') <= MaxNewlines &&
        !(text.startsWith(HttpPrefix) || text.startsWith(HttpsPrefix))
      }

    val filterResult = FilterResult(
      kept = kept.map(_.candidate),
      removed = removed.map(_.candidate)
    )

    Stitch.value(filterResult)
  }

}

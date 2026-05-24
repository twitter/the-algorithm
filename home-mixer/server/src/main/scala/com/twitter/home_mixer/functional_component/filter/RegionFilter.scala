package com.twitter.home_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.home_mixer.param.HomeGlobalParams.EnableRegionFilter

/**
 * Filters tweets based on matching region location between user query and tweet candidates
 */
object RegionFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("Region")
  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnableRegionFilter)
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val userRegionOpt = query.features
      .map(_.getOrElse(LocationFeature, None))
      .flatMap(_.flatMap(_.region))

    val (kept, removed) = candidates.partition { candidate =>
      val postLocationOpt = candidate.features.getOrElse(LocationFeature, None).flatMap(_.region)
      val keep = (postLocationOpt, userRegionOpt) match {
        case (Some(postLocation), Some(userRegion)) =>
          postLocation == userRegion
        case (Some(_), None) =>
          false
        case (None, _) =>
          true
        case _ =>
          true
      }
      keep
    }

    Stitch.value(
      FilterResult(
        kept = kept.map(_.candidate),
        removed = removed.map(_.candidate)
      ))
  }
}

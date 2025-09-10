package com.twitter.home_mixer.functional_component.filter

import com.twitter.product_mixer.component_library.feature.location.LocationSharedFeatures.LocationFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.home_mixer.param.HomeGlobalParams.EnableCountryFilter

/**
 * Filters tweets based on matching country location between user query and tweet candidates
 */
object CountryFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("Country")

  /**
   * Determines if the filter should be applied based on the EnableCountryFilter parameter
   */
  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    query.params(EnableCountryFilter)
  }

  /**
   * Filters candidates based on exact country location matching
   * @param query The pipeline query containing user location features
   * @param candidates The sequence of tweet candidates with their features
   * @return A Stitch containing FilterResult with kept and removed candidates
   */
  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val userCountryOpt = query.features
      .map(_.getOrElse(LocationFeature, None))
      .flatMap(_.flatMap(_.country))

    val (kept, removed) = candidates.partition { candidate =>
      val postLocationOpt = candidate.features.getOrElse(LocationFeature, None).flatMap(_.country)
      val keep = (postLocationOpt, userCountryOpt) match {
        case (Some(postLocation), Some(userCountry)) =>
          postLocation == userCountry
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

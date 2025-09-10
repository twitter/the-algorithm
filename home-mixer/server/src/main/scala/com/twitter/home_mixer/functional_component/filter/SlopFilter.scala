package com.twitter.home_mixer.functional_component.filter

import com.twitter.core_workflows.user_model.{thriftscala => um}
import com.twitter.home_mixer.model.HomeFeatures.AuthorFollowersFeature
import com.twitter.home_mixer.model.HomeFeatures.AuthorIdFeature
import com.twitter.home_mixer.model.HomeFeatures.InNetworkFeature
import com.twitter.home_mixer.model.HomeFeatures.SlopAuthorFeature
import com.twitter.home_mixer.model.HomeFeatures.UserStateFeature
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSlopFilter
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSlopFilterEligibleUserStateParam
import com.twitter.home_mixer.param.HomeGlobalParams.EnableSlopFilterLowSignalUsers
import com.twitter.home_mixer.param.HomeGlobalParams.SlopMinFollowers
import com.twitter.home_mixer.util.SignalUtil
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.filter.FilterResult
import com.twitter.product_mixer.core.model.common.CandidateWithFeatures
import com.twitter.product_mixer.core.model.common.identifier.FilterIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch

object SlopFilter
    extends Filter[PipelineQuery, TweetCandidate]
    with Filter.Conditionally[PipelineQuery, TweetCandidate] {

  override val identifier: FilterIdentifier = FilterIdentifier("Slop")

  private val MinFollowingThreshold = 5
  private val EligibleUserStates: Set[um.UserState] =
    Set(um.UserState.NearZero, um.UserState.New, um.UserState.VeryLight)

  override def onlyIf(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Boolean = {
    val numSlopAuthorsFollowed = candidates
      .filter { candidate =>
        candidate.features.getOrElse(SlopAuthorFeature, false) &&
        candidate.features.getOrElse(InNetworkFeature, false)
      }.flatMap(_.features.getOrElse(AuthorIdFeature, None)).distinct.size

    val userState = query.features.flatMap(_.getOrElse(UserStateFeature, None))

    val lowSignalUser = SignalUtil.isLowSignalUser(query)

    numSlopAuthorsFollowed < MinFollowingThreshold &&
    (
      (userState.forall(EligibleUserStates.contains) &&
      query.params(EnableSlopFilterEligibleUserStateParam)) ||
      (lowSignalUser && query.params(EnableSlopFilterLowSignalUsers)) ||
      query.params(EnableSlopFilter)
    )
  }

  override def apply(
    query: PipelineQuery,
    candidates: Seq[CandidateWithFeatures[TweetCandidate]]
  ): Stitch[FilterResult[TweetCandidate]] = {
    val minFollowersThreshold = query.params(SlopMinFollowers)

    val (removed, kept) = candidates.partition { candidate =>
      candidate.features.getOrElse(SlopAuthorFeature, false) &&
      !candidate.features.getOrElse(InNetworkFeature, false) &&
      candidate.features.getOrElse(AuthorFollowersFeature, None).forall(_ > minFollowersThreshold)
    }
    Stitch.value(FilterResult(kept = kept.map(_.candidate), removed = removed.map(_.candidate)))
  }
}

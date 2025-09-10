package com.twitter.tweet_mixer.functional_component.side_effect

import com.twitter.product_mixer.core.functional_component.side_effect.PipelineResultSideEffect
import com.twitter.product_mixer.core.model.common.identifier.SideEffectIdentifier
import com.twitter.product_mixer.core.model.common.presentation.CandidateWithDetails
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.stitch.Stitch
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.HistoricalEvergreenVideosCandidateSource
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByUserIdsQuery
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersFeature
import com.twitter.tweet_mixer.model.response.TweetMixerResponse
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EnableEvergreenVideosSideEffect
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EvergreenVideosMaxFollowUsersParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EvergreenVideosPaginationNumParam
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Side effect that calls evergreen_videos dark traffic to fetch tweets.
 */

@Singleton
class EvergreenVideosSideEffect @Inject() (
  evergreenVideosCandidateSource: HistoricalEvergreenVideosCandidateSource)
    extends PipelineResultSideEffect[PipelineQuery, TweetMixerResponse]
    with PipelineResultSideEffect.Conditionally[
      PipelineQuery,
      TweetMixerResponse
    ] {

  override val identifier: SideEffectIdentifier = SideEffectIdentifier("EvergreenVideos")

  override def onlyIf(
    query: PipelineQuery,
    selectedCandidates: Seq[CandidateWithDetails],
    remainingCandidates: Seq[CandidateWithDetails],
    droppedCandidates: Seq[CandidateWithDetails],
    response: TweetMixerResponse
  ): Boolean = query.params(EnableEvergreenVideosSideEffect)

  override def apply(
    inputs: PipelineResultSideEffect.Inputs[PipelineQuery, TweetMixerResponse]
  ): Stitch[Unit] = {
    val query = inputs.query
    val paginationNum = query.params(EvergreenVideosPaginationNumParam)
    val maxFollowUsers = query.params(EvergreenVideosMaxFollowUsersParam)

    val followedUserIds = query.features
      .map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty[Long]) ++ Seq(query.getRequiredUserId))
      .map(_.take(maxFollowUsers))

    val request = EvergreenVideosSearchByUserIdsQuery(
      userIds = followedUserIds.getOrElse(Seq.empty),
      size = paginationNum
    )
    Stitch.run(evergreenVideosCandidateSource.apply(request))
    Stitch.Unit
  }
}

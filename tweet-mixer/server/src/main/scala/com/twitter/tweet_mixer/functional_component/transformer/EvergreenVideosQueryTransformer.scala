package com.twitter.tweet_mixer.functional_component.transformer

import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.tweet_mixer.candidate_source.evergreen_videos.EvergreenVideosSearchByUserIdsQuery
import com.twitter.tweet_mixer.functional_component.hydrator.SGSFollowedUsersFeature
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EvergreenVideosMaxFollowUsersParam
import com.twitter.tweet_mixer.param.TweetMixerGlobalParams.EvergreenVideosPaginationNumParam

case class EvergreenVideosQueryTransformer[Query <: PipelineQuery](
  candidatePipelineIdentifier: CandidatePipelineIdentifier)
    extends CandidatePipelineQueryTransformer[Query, EvergreenVideosSearchByUserIdsQuery] {

  override def transform(query: Query): EvergreenVideosSearchByUserIdsQuery = {

    val paginationNum = query.params(EvergreenVideosPaginationNumParam)
    val maxFollowUsers = query.params(EvergreenVideosMaxFollowUsersParam)

    val followedUserIds =
      query.features
        .map(v =>
          v.getOrElse(SGSFollowedUsersFeature, Seq.empty[Long]) ++ Seq(query.getRequiredUserId))
        .map {
          _.take(maxFollowUsers)
        }

    EvergreenVideosSearchByUserIdsQuery(
      userIds = followedUserIds.getOrElse(Seq.empty),
      size = paginationNum
    )
  }
}

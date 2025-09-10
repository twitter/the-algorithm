package com.twitter.home_mixer.product.for_you

import com.twitter.home_mixer.functional_component.decorator.urt.builder.HomeFeedbackActionInfoBuilder
import com.twitter.home_mixer.functional_component.feature_hydrator.TweetypieFeatureHydrator
import com.twitter.home_mixer.functional_component.filter.TweetHydrationFilter
import com.twitter.home_mixer.model.HomeFeatures.PersistenceEntriesFeature
import com.twitter.home_mixer.model.HomeFeatures.TLSOriginalTweetsWithConfirmedAuthorFeature
import com.twitter.home_mixer.model.HomeFeatures.TweetAuthorFollowersFeature
import com.twitter.home_mixer.product.for_you.feature_hydrator.TweetAuthorFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.TweetEngagementsFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.TweetAuthorFollowersFeatureHydrator
import com.twitter.home_mixer.product.for_you.feature_hydrator.TweetEngagementCountsFeature
import com.twitter.home_mixer.product.for_you.feature_hydrator.TweetEngagementCounts
import com.twitter.home_mixer.product.for_you.model.ForYouQuery
import com.twitter.home_mixer.product.for_you.param.ForYouParam.EnableExplorationTweetsCandidatePipelineParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.InNetworkExplorationTweetsMinInjectionIntervalParam
import com.twitter.home_mixer.product.for_you.param.ForYouParam.ExplorationTweetsMaxFollowerCountParam
import com.twitter.home_mixer.product.for_you.response_transformer.ExplorationTweetResponseFeatureTransformer
import com.twitter.home_mixer.{thriftscala => hmt}
import com.twitter.product_mixer.component_library.decorator.urt.UrtItemCandidateDecorator
import com.twitter.product_mixer.component_library.decorator.urt.builder.item.tweet.TweetCandidateUrtItemBuilder
import com.twitter.product_mixer.component_library.decorator.urt.builder.metadata.ClientEventInfoBuilder
import com.twitter.product_mixer.component_library.feature_hydrator.query.social_graph.SGSFollowedUsersFeature
import com.twitter.product_mixer.component_library.model.candidate.TweetCandidate
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.functional_component.candidate_source.PassthroughCandidateSource
import com.twitter.product_mixer.core.functional_component.decorator.CandidateDecorator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.BaseCandidateFeatureHydrator
import com.twitter.product_mixer.core.functional_component.feature_hydrator.QueryFeatureHydrator
import com.twitter.product_mixer.core.functional_component.filter.Filter
import com.twitter.product_mixer.core.functional_component.gate.Gate
import com.twitter.product_mixer.core.functional_component.transformer.CandidateFeatureTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineQueryTransformer
import com.twitter.product_mixer.core.functional_component.transformer.CandidatePipelineResultsTransformer
import com.twitter.product_mixer.core.model.common.identifier.CandidatePipelineIdentifier
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.pipeline.candidate.CandidatePipelineConfig
import com.twitter.timelines.configapi.FSParam
import com.twitter.home_mixer.functional_component.gate.RecentlyServedByServedTypeGate
import com.twitter.home_mixer.product.for_you.gate.UserFollowingRangeGate
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random

@Singleton
class ForYouExplorationTweetsCandidatePipelineConfig @Inject() (
  tweetAuthorFeatureHydrator: TweetAuthorFeatureHydrator,
  tweetEngagementsFeatureHydrator: TweetEngagementsFeatureHydrator,
  tweetAuthorFollowersFeatureHydrator: TweetAuthorFollowersFeatureHydrator,
  tweetypieFeatureHydrator: TweetypieFeatureHydrator,
  homeFeedbackActionInfoBuilder: HomeFeedbackActionInfoBuilder)
    extends CandidatePipelineConfig[
      ForYouQuery,
      ForYouQuery,
      Long,
      TweetCandidate
    ] {

  override val identifier: CandidatePipelineIdentifier =
    CandidatePipelineIdentifier("ForYouExplorationTweets")

  override val supportedClientParam: Option[FSParam[Boolean]] = Some(
    EnableExplorationTweetsCandidatePipelineParam)

  override val queryTransformer: CandidatePipelineQueryTransformer[
    ForYouQuery,
    ForYouQuery
  ] = identity

  override val queryFeatureHydration: Seq[QueryFeatureHydrator[PipelineQuery]] = Seq(
    tweetAuthorFeatureHydrator,
    tweetEngagementsFeatureHydrator
  )

  override val queryFeatureHydrationPhase2: Seq[QueryFeatureHydrator[PipelineQuery]] = Seq(
    tweetAuthorFollowersFeatureHydrator
  )

  override val gates: Seq[Gate[PipelineQuery]] = Seq(
    RecentlyServedByServedTypeGate(
      InNetworkExplorationTweetsMinInjectionIntervalParam,
      hmt.ServedType.ForYouExploration
    ),
    UserFollowingRangeGate
  )

  override def candidateSource: CandidateSource[ForYouQuery, Long] =
    PassthroughCandidateSource(
      identifier = CandidateSourceIdentifier("ForYouExplorationTweets"),
      candidateExtractor = { query =>
        val followedUserIds = query.features
          .map(_.getOrElse(SGSFollowedUsersFeature, Seq.empty))
          .getOrElse(Seq.empty).toSet

        val servedAuthorIds =
          query.features
            .map(_.getOrElse(PersistenceEntriesFeature, Seq.empty)).getOrElse(Seq.empty)
            .flatMap(_.entries.flatMap(_.sourceAuthorIds))
            .toSet

        val explorationAuthors = followedUserIds -- servedAuthorIds

        val engagementCounts = query.features
          .map(_.getOrElse(TweetEngagementCountsFeature, Map.empty[Long, TweetEngagementCounts]))
          .getOrElse(Map.empty)

        val maxFollowerCount = query.params(ExplorationTweetsMaxFollowerCountParam)
        val tweetAuthorFollowers = query.features
          .map(_.getOrElse(TweetAuthorFollowersFeature, Map.empty[Long, Option[Long]]))
          .getOrElse(Map.empty)

        val groupedByAuthor = query.features
          .map(_.getOrElse(TLSOriginalTweetsWithConfirmedAuthorFeature, Seq.empty))
          .toSeq.flatten.filter {
            case (tweetId, authorId) =>
              explorationAuthors.contains(authorId) && {
                val followerCount = tweetAuthorFollowers.get(tweetId).flatten.getOrElse(0L)
                followerCount <= maxFollowerCount
              }
          }
          .groupBy { case (_, authorId) => authorId }

        val bestPostsByAuthor = groupedByAuthor.flatMap {
          case (authorId, candidates) =>
            val candidateWithEngagement = candidates.map {
              case (tweetId, _) =>
                val engagement = engagementCounts.get(tweetId)
                val totalEngagement = engagement
                  .map { counts =>
                    counts.favoriteCount.getOrElse(0L) +
                      counts.replyCount.getOrElse(0L) +
                      counts.retweetCount.getOrElse(0L) +
                      counts.quoteCount.getOrElse(0L) +
                      counts.bookmarkCount.getOrElse(0L)
                  }.getOrElse(0L)
                (tweetId, totalEngagement)
            }

            if (candidateWithEngagement.nonEmpty) {
              val bestPost = candidateWithEngagement.maxBy(_._2)
              Some(bestPost._1)
            } else None
        }

        Random.shuffle(bestPostsByAuthor.toSeq).take(1)
      }
    )

  override val featuresFromCandidateSourceTransformers: Seq[
    CandidateFeatureTransformer[Long]
  ] = Seq(ExplorationTweetResponseFeatureTransformer)

  override val resultTransformer: CandidatePipelineResultsTransformer[Long, TweetCandidate] = {
    sourceResult => TweetCandidate(id = sourceResult)
  }

  override val preFilterFeatureHydrationPhase1: Seq[
    BaseCandidateFeatureHydrator[ForYouQuery, TweetCandidate, _]
  ] = Seq(tweetypieFeatureHydrator)

  override val filters: Seq[Filter[ForYouQuery, TweetCandidate]] =
    Seq(TweetHydrationFilter)

  override val decorator: Option[CandidateDecorator[PipelineQuery, TweetCandidate]] = {
    val clientEventInfoBuilder =
      ClientEventInfoBuilder[PipelineQuery, TweetCandidate](
        hmt.ServedType.ForYouExploration.originalName)

    val tweetItemBuilder = TweetCandidateUrtItemBuilder[PipelineQuery, TweetCandidate](
      clientEventInfoBuilder = clientEventInfoBuilder,
      feedbackActionInfoBuilder = Some(homeFeedbackActionInfoBuilder),
    )

    Some(UrtItemCandidateDecorator(tweetItemBuilder))
  }
}

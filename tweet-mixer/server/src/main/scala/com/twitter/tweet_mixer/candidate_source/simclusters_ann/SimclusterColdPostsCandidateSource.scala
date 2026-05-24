package com.twitter.tweet_mixer.candidate_source.simclusters_ann

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.strato.generated.client.content_understanding.UserInterestedSimclusterColdPostsClientColumn
import com.twitter.product_mixer.core.util.OffloadFuturePools
import javax.inject.Inject
import javax.inject.Singleton
import scala.util.Random
import scala.math.abs

@Singleton
class SimclusterColdPostsCandidateSource @Inject() (
  simclusterColdPostsStratoColumn: UserInterestedSimclusterColdPostsClientColumn,
  inputStatsReceiver: StatsReceiver)
    extends CandidateSource[SimclusterColdPostsQuery, Long] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("SimclusterColdPosts")

  private val scopedStats = inputStatsReceiver.scope(getClass.getSimpleName)
  private val candidatesStat = scopedStats.counter("candidatesSize")

  override def apply(request: SimclusterColdPostsQuery): Stitch[Seq[Long]] = {
    getSimclusterPosts(
      userId = request.userId,
      postsPerSimcluster = request.postsPerSimcluster,
      maxCandidates = request.maxCandidates
    )
  }
  private val fetcher = simclusterColdPostsStratoColumn.fetcher

  private def getSimclusterPosts(
    userId: Long,
    postsPerSimcluster: Int,
    maxCandidates: Int
  ): Stitch[Seq[Long]] = {
    OffloadFuturePools.offloadStitch {
      fetcher
        .fetch(
          UserInterestedSimclusterColdPostsClientColumn.Key(
            userId = userId,
            postsPerSimcluster = postsPerSimcluster
          )
        ).map { result =>
          result.v match {
            case Some(posts) =>
              val candidates: Seq[Long] = posts.map(v => abs(v._2))
              candidatesStat.incr(candidates.size)
              Random.shuffle(candidates).take(maxCandidates)
            case None => Seq.empty
          }
        }
    }
  }
}

package com.twitter.tweet_mixer.candidate_source.topic_tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.topic_recos.thriftscala.TweetWithScores
import com.twitter.simclusters_v2.thriftscala.TopicId
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

case class CertoTopicTweetsQuery(
  topicIds: Seq[TopicId],
  maxCandidatesPerTopic: Int,
  maxCandidates: Int,
  minCertoScore: Double,
  minFavCount: Int)

case class CertoSingleTopicTweetsQuery(
  topicId: TopicId,
  maxCandidatesPerTopic: Int,
  minCertoScore: Double,
  minFavCount: Int)

@Singleton
class CertoTopicTweetsCandidateSource @Inject() (
  @Named(ModuleNames.CertoStratoTopicTweetsStoreName)
  certoStratoTopicTweetsStore: ReadableStore[TopicId, Seq[TweetWithScores]],
  inputStatsReceiver: StatsReceiver)
    extends CandidateSource[CertoTopicTweetsQuery, TweetMixerCandidate] {

  private val scopedStats = inputStatsReceiver.scope(getClass.getSimpleName)
  private val emptyTopicsCounter = scopedStats.counter("emptyTopics")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("CertoTopicTweets")

  override def apply(request: CertoTopicTweetsQuery): Stitch[Seq[TweetMixerCandidate]] = {
    val singleTopicIdQueries: Seq[CertoSingleTopicTweetsQuery] = request.topicIds.map { topicId =>
      CertoSingleTopicTweetsQuery(
        topicId = topicId,
        minCertoScore = request.minCertoScore,
        maxCandidatesPerTopic = request.maxCandidatesPerTopic,
        minFavCount = request.minFavCount
      )
    }

    if (singleTopicIdQueries.isEmpty) {
      emptyTopicsCounter.incr()
      Stitch.value(Seq.empty)
    } else {
      val resultsStitch: Stitch[Seq[Seq[TweetMixerCandidate]]] =
        Stitch.traverse(singleTopicIdQueries)(fetchFromCertoStore)

      // this will interleave the candidates by topic for diversity
      resultsStitch.map { results: Seq[Seq[TweetMixerCandidate]] =>
        TweetMixerCandidate.interleave(results)
      }
    }
  }

  private def fetchFromCertoStore(
    query: CertoSingleTopicTweetsQuery
  ): Stitch[Seq[TweetMixerCandidate]] = {
    val tweetsWithScoresF = certoStratoTopicTweetsStore.get(query.topicId)

    Stitch.callFuture(tweetsWithScoresF).map { tweetsWithScoresOpt =>
      {
        val tweetsWithScores: Seq[TweetWithScores] = tweetsWithScoresOpt
          .getOrElse(Seq.empty)
          .filter(_.scores.followerL2NormalizedCosineSimilarity8HrHalfLife >= query.minCertoScore)
          .filter(_.scores.favCount.exists(_ >= query.minFavCount))
          .sortBy(-_.scores.favCount.getOrElse(0L))
          .take(query.maxCandidatesPerTopic)

        tweetsWithScores.map { tweetWithScore =>
          TweetMixerCandidate(
            tweetId = tweetWithScore.tweetId,
            score = tweetWithScore.scores.favCount.getOrElse(0L).toDouble,
            seedId = query.topicId.entityId
          )
        }
      }
    }
  }
}

package com.twitter.tweet_mixer.candidate_source.topic_tweets

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.stitch.Stitch
import com.twitter.storehaus.ReadableStore
import com.twitter.tweet_mixer.model.ModuleNames
import com.twitter.topic_recos.thriftscala.TopicTweet
import com.twitter.topic_recos.thriftscala.TopicTweetPartitionFlatKey
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

case class SkitTopicTweetsQuery(
  topicKeys: Seq[SkitTimedTopicKeys],
  maxCandidatesPerTopic: Int,
  maxCandidates: Int,
  minScore: Double,
  minFavCount: Int)

case class SkitTimedTopicKeys(keys: Seq[TopicTweetPartitionFlatKey], topicId: Long)

@Singleton
class SkitTopicTweetsCandidateSource @Inject() (
  @Named(ModuleNames.SkitStratoTopicTweetsStoreName)
  skitStratoTopicTweetsStore: ReadableStore[TopicTweetPartitionFlatKey, Seq[TopicTweet]],
  inputStatsReceiver: StatsReceiver)
    extends CandidateSource[SkitTopicTweetsQuery, TweetMixerCandidate] {

  private val scopedStats = inputStatsReceiver.scope(getClass.getSimpleName)
  private val emptyTopicsCounter = scopedStats.counter("emptyTopics")

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("SkitTopicTweets")

  override def apply(request: SkitTopicTweetsQuery): Stitch[Seq[TweetMixerCandidate]] = {

    if (request.topicKeys.isEmpty) {
      emptyTopicsCounter.incr()
      Stitch.value(Seq.empty)
    } else {
      val resultsStitch: Stitch[Seq[Seq[TweetMixerCandidate]]] =
        Stitch.traverse(request.topicKeys) { topicKey: SkitTimedTopicKeys =>
          Stitch
            .traverse(topicKey.keys) { skitKey =>
              Stitch.callFuture(skitStratoTopicTweetsStore.get(skitKey))
            }.map { topicTweets =>
              val flattenedSkitTopicTweets: Seq[TopicTweet] =
                topicTweets
                  .collect {
                    case Some(topicTweets: Seq[TopicTweet]) =>
                      topicTweets.filter { topicTweet =>
                        topicTweet.scores.cosineSimilarity.exists(_ > request.minScore) &&
                        topicTweet.scores.favCount.exists(_ > request.minFavCount.toLong)
                      }
                  }.flatten
                  .sortBy(-_.scores.favCount.getOrElse(0L))
                  .take(request.maxCandidatesPerTopic)

              flattenedSkitTopicTweets.map { skitTopicTweet =>
                TweetMixerCandidate(
                  skitTopicTweet.tweetId,
                  score = skitTopicTweet.scores.favCount.getOrElse(0L).toDouble,
                  seedId = topicKey.topicId
                )
              }
            }
        }

      // Interleave the candidates by topic for diversity
      resultsStitch.map { results: Seq[Seq[TweetMixerCandidate]] =>
        TweetMixerCandidate.interleave(results)
      }
    }
  }
}

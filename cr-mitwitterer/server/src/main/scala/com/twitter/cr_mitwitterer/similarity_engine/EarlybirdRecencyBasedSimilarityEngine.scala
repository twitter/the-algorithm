package com.twitter.cr_mitwitterer.similarity_engine
import com.twitter.cr_mitwitterer.config.TimeoutConfig
import com.twitter.cr_mitwitterer.model.ModuleNames
import com.twitter.cr_mitwitterer.model.TweetWithAuthor
import com.twitter.cr_mitwitterer.similarity_engine.EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery
import com.twitter.finagle.stats.StatsReceiver
import com.twitter.frigate.common.util.StatsUtil
import com.twitter.simclusters_v2.common.TweetId
import com.twitter.simclusters_v2.common.UserId
import com.twitter.snowflake.id.SnowflakeId
import com.twitter.storehaus.ReadableStore
import com.twitter.util.Duration
import com.twitter.util.Future
import com.twitter.util.Time
import javatwitter.inject.Inject
import javatwitter.inject.Named
import javatwitter.inject.Singleton

@Singleton
case class EarlybirdRecencyBasedSimilarityEngine @Inject() (
  @Named(ModuleNames.EarlybirdRecencyBasedWithoutRetweetsRepliesTweetsCache)
  earlybirdRecencyBasedWithoutRetweetsRepliesTweetsCacheStore: ReadableStore[
    UserId,
    Seq[TweetId]
  ],
  @Named(ModuleNames.EarlybirdRecencyBasedWithRetweetsRepliesTweetsCache)
  earlybirdRecencyBasedWithRetweetsRepliesTweetsCacheStore: ReadableStore[
    UserId,
    Seq[TweetId]
  ],
  timeoutConfig: TimeoutConfig,
  stats: StatsReceiver)
    etwittertends ReadableStore[EarlybirdRecencyBasedSearchQuery, Seq[TweetWithAuthor]] {
  import EarlybirdRecencyBasedSimilarityEngine._
  val statsReceiver: StatsReceiver = stats.scope(this.getClass.getSimpleName)

  override def get(
    query: EarlybirdRecencyBasedSearchQuery
  ): Future[Option[Seq[TweetWithAuthor]]] = {
    Future
      .collect {
        if (query.filterOutRetweetsAndReplies) {
          query.seedUserIds.map { seedUserId =>
            StatsUtil.trackOptionItemsStats(statsReceiver.scope("WithoutRetweetsAndReplies")) {
              earlybirdRecencyBasedWithoutRetweetsRepliesTweetsCacheStore
                .get(seedUserId).map(_.map(_.map(tweetId =>
                  TweetWithAuthor(tweetId = tweetId, authorId = seedUserId))))
            }
          }
        } else {
          query.seedUserIds.map { seedUserId =>
            StatsUtil.trackOptionItemsStats(statsReceiver.scope("WithRetweetsAndReplies")) {
              earlybirdRecencyBasedWithRetweetsRepliesTweetsCacheStore
                .get(seedUserId)
                .map(_.map(_.map(tweetId =>
                  TweetWithAuthor(tweetId = tweetId, authorId = seedUserId))))
            }
          }
        }
      }
      .map { tweetWithAuthorList =>
        val earliestTweetId = SnowflakeId.firstIdFor(Time.now - query.matwitterTweetAge)
        tweetWithAuthorList
          .flatMap(_.getOrElse(Seq.empty))
          .filter(tweetWithAuthor =>
            tweetWithAuthor.tweetId >= earliestTweetId // tweet age filter
              && !query.etwittercludedTweetIds
                .contains(tweetWithAuthor.tweetId)) // etwittercluded tweet filter
          .sortBy(tweetWithAuthor =>
            -SnowflakeId.unitwitterTimeMillisFromId(tweetWithAuthor.tweetId)) // sort by recency
          .take(query.matwitterNumTweets) // take most recent N tweets
      }
      .map(result => Some(result))
  }

}

object EarlybirdRecencyBasedSimilarityEngine {
  case class EarlybirdRecencyBasedSearchQuery(
    seedUserIds: Seq[UserId],
    matwitterNumTweets: Int,
    etwittercludedTweetIds: Set[TweetId],
    matwitterTweetAge: Duration,
    filterOutRetweetsAndReplies: Boolean)

}

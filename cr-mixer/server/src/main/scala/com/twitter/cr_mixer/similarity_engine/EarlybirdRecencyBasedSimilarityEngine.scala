package com.ExTwitter.cr_mixer.similarity_engine
import com.ExTwitter.cr_mixer.config.TimeoutConfig
import com.ExTwitter.cr_mixer.model.ModuleNames
import com.ExTwitter.cr_mixer.model.TweetWithAuthor
import com.ExTwitter.cr_mixer.similarity_engine.EarlybirdRecencyBasedSimilarityEngine.EarlybirdRecencyBasedSearchQuery
import com.ExTwitter.finagle.stats.StatsReceiver
import com.ExTwitter.frigate.common.util.StatsUtil
import com.ExTwitter.simclusters_v2.common.TweetId
import com.ExTwitter.simclusters_v2.common.UserId
import com.ExTwitter.snowflake.id.SnowflakeId
import com.ExTwitter.storehaus.ReadableStore
import com.ExTwitter.util.Duration
import com.ExTwitter.util.Future
import com.ExTwitter.util.Time
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

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
    extends ReadableStore[EarlybirdRecencyBasedSearchQuery, Seq[TweetWithAuthor]] {
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
        val earliestTweetId = SnowflakeId.firstIdFor(Time.now - query.maxTweetAge)
        tweetWithAuthorList
          .flatMap(_.getOrElse(Seq.empty))
          .filter(tweetWithAuthor =>
            tweetWithAuthor.tweetId >= earliestTweetId // tweet age filter
              && !query.excludedTweetIds
                .contains(tweetWithAuthor.tweetId)) // excluded tweet filter
          .sortBy(tweetWithAuthor =>
            -SnowflakeId.unixTimeMillisFromId(tweetWithAuthor.tweetId)) // sort by recency
          .take(query.maxNumTweets) // take most recent N tweets
      }
      .map(result => Some(result))
  }

}

object EarlybirdRecencyBasedSimilarityEngine {
  case class EarlybirdRecencyBasedSearchQuery(
    seedUserIds: Seq[UserId],
    maxNumTweets: Int,
    excludedTweetIds: Set[TweetId],
    maxTweetAge: Duration,
    filterOutRetweetsAndReplies: Boolean)

}

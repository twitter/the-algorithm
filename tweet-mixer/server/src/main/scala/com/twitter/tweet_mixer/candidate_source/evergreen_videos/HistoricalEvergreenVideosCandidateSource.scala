package com.twitter.tweet_mixer.candidate_source.evergreen_videos

import com.twitter.finagle.stats.StatsReceiver
import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import com.twitter.stitch.Stitch
import com.twitter.strato.config.ElasticsearchPaginate
import com.twitter.strato.generated.client.evergreen_videos.HistoricalSearchByUserIdsClientColumn
import com.twitter.strato.generated.client.evergreen_videos.HistoricalSearchByUserIdsClientColumn.UserIdsAndSize
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistoricalEvergreenVideosCandidateSource @Inject() (
  evergreenVideosHistoricalSearchByUserIdsClientColumn: HistoricalSearchByUserIdsClientColumn,
  inputStatsReceiver: StatsReceiver)
    extends CandidateSource[EvergreenVideosSearchByUserIdsQuery, TweetMixerCandidate] {

  private val scopedStats = inputStatsReceiver.scope(getClass.getSimpleName)
  override val identifier: CandidateSourceIdentifier = CandidateSourceIdentifier("EvergreenVideos")

  override def apply(
    request: EvergreenVideosSearchByUserIdsQuery
  ): Stitch[Seq[TweetMixerCandidate]] = {
    val paginator = evergreenVideosHistoricalSearchByUserIdsClientColumn.paginator

    val userIdsStr = request.userIds
      .map { userId =>
        userId.toString
      }.mkString("[", ",", "]")

    val beginCursor: HistoricalSearchByUserIdsClientColumn.Cursor =
      ElasticsearchPaginate.Begin(UserIdsAndSize(userIds = userIdsStr, size = request.size))

    paginator.paginate(beginCursor).map { page =>
      val tweets = page.data.map { idStr =>
        TweetMixerCandidate(tweetId = idStr.toLong, score = 0.0, seedId = -1L)
      }
      scopedStats.stat("tweetCount").add(tweets.size)
      tweets
    }
  }
}

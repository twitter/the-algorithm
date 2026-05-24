package com.twitter.tweet_mixer.candidate_source.evergreen_videos

import com.twitter.product_mixer.core.functional_component.candidate_source.CandidateSource
import com.twitter.product_mixer.core.model.common.identifier.CandidateSourceIdentifier
import com.twitter.product_mixer.core.pipeline.PipelineQuery
import com.twitter.product_mixer.core.util.OffloadFuturePools
import com.twitter.stitch.Stitch
import com.twitter.strato.client.Client
import com.twitter.strato.config.ElasticsearchPaginate
import com.twitter.strato.generated.client.evergreen_videos.HistoricalSearchByTextClientColumn
import com.twitter.strato.generated.client.evergreen_videos.HistoricalSearchByTextClientColumn.TextAndSize
import com.twitter.tweet_mixer.model.response.TweetMixerCandidate
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class MemeVideoCandidateSource @Inject() (
  @Named("StratoClientWithModerateTimeout") stratoClient: Client)
    extends CandidateSource[PipelineQuery, TweetMixerCandidate] {

  override val identifier: CandidateSourceIdentifier =
    CandidateSourceIdentifier("MemeVideoCandidateSourceUsingSearchModule")

  override def apply(
    request: PipelineQuery
  ): Stitch[Seq[TweetMixerCandidate]] = {

    val funnyEmojis: String =
      "\uD83D\uDE02\uD83E\uDD23\uD83D\uDE06" + "lang:" + request.getLanguageCode
    val beginCursor: HistoricalSearchByTextClientColumn.Cursor =
      ElasticsearchPaginate.Begin(TextAndSize(text = funnyEmojis, size = 200))

    val paginator =
      stratoClient.paginator[
        HistoricalSearchByTextClientColumn.Cursor,
        HistoricalSearchByTextClientColumn.Page
      ](HistoricalSearchByTextClientColumn.Path)

    OffloadFuturePools.offloadStitch {
      paginator.paginate(beginCursor).map { page =>
        page.data.map { idStr =>
          TweetMixerCandidate(
            tweetId = idStr.toLong,
            score = 0.0,
            seedId = -1L
          )
        }
      }
    }
  }
}

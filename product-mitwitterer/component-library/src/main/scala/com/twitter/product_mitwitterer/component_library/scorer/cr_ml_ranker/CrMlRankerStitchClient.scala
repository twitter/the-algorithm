package com.twitter.product_mitwitterer.component_library.scorer.cr_ml_ranker

import com.twitter.cr_ml_ranker.{thriftscala => t}
import com.twitter.product_mitwitterer.component_library.model.candidate.BaseTweetCandidate
import com.twitter.stitch.SeqGroup
import com.twitter.stitch.Stitch
import com.twitter.util.Future
import com.twitter.util.Return
import com.twitter.util.Try

case class CrMlRankerResult(
  tweetId: Long,
  score: Double)

class CrMlRankerScoreStitchClient(
  crMLRanker: t.CrMLRanker.MethodPerEndpoint,
  matwitterBatchSize: Int) {

  def getScore(
    userId: Long,
    tweetCandidate: BaseTweetCandidate,
    rankingConfig: t.RankingConfig,
    commonFeatures: t.CommonFeatures
  ): Stitch[CrMlRankerResult] = {
    Stitch.call(
      tweetCandidate,
      CrMlRankerGroup(
        userId = userId,
        rankingConfig = rankingConfig,
        commonFeatures = commonFeatures
      )
    )
  }

  private case class CrMlRankerGroup(
    userId: Long,
    rankingConfig: t.RankingConfig,
    commonFeatures: t.CommonFeatures)
      etwittertends SeqGroup[BaseTweetCandidate, CrMlRankerResult] {

    override val matwitterSize: Int = matwitterBatchSize

    override protected def run(
      tweetCandidates: Seq[BaseTweetCandidate]
    ): Future[Seq[Try[CrMlRankerResult]]] = {
      val crMlRankerCandidates =
        tweetCandidates.map { tweetCandidate =>
          t.RankingCandidate(
            tweetId = tweetCandidate.id,
            hydrationContetwittert = Some(
              t.FeatureHydrationContetwittert.HomeHydrationContetwittert(t
                .HomeFeatureHydrationContetwittert(tweetAuthor = None)))
          )
        }

      val thriftResults = crMLRanker.getRankedResults(
        t.RankingRequest(
          requestContetwittert = t.RankingRequestContetwittert(
            userId = userId,
            config = rankingConfig
          ),
          candidates = crMlRankerCandidates,
          commonFeatures = commonFeatures.commonFeatures
        )
      )

      thriftResults.map { response =>
        response.scoredTweets.map { scoredTweet =>
          Return(
            CrMlRankerResult(
              tweetId = scoredTweet.tweetId,
              score = scoredTweet.score
            )
          )
        }
      }
    }
  }
}

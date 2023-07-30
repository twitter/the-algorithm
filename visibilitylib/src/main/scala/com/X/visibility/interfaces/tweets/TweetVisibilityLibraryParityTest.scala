package com.X.visibility.interfaces.tweets

import com.X.spam.rtf.{thriftscala => t}
import com.X.context.XContext
import com.X.context.thriftscala.Viewer
import com.X.finagle.stats.StatsReceiver
import com.X.stitch.Stitch
import com.X.strato.catalog.Fetch
import com.X.strato.client.Client
import com.X.strato.client.Fetcher
import com.X.strato.thrift.ScroogeConvImplicits._
import com.X.visibility.builder.VisibilityResult
import com.X.visibility.common.tweets.TweetVisibilityResultMapper
import com.X.visibility.models.SafetyLevel.toThrift
import com.X.visibility.models.ViewerContext
import com.X.visibility.thriftscala.TweetVisibilityResult

class TweetVisibilityLibraryParityTest(statsReceiver: StatsReceiver, stratoClient: Client) {

  private val parityTestScope = statsReceiver.scope("tweet_visibility_library_parity")
  private val requests = parityTestScope.counter("requests")
  private val equal = parityTestScope.counter("equal")
  private val incorrect = parityTestScope.counter("incorrect")
  private val empty = parityTestScope.counter("empty")
  private val failures = parityTestScope.counter("failures")

  private val fetcher: Fetcher[Long, t.SafetyLevel, TweetVisibilityResult] =
    stratoClient.fetcher[Long, t.SafetyLevel, TweetVisibilityResult](
      "visibility/service/TweetVisibilityResult.Tweet"
    )

  def runParityTest(
    req: TweetVisibilityRequest,
    resp: VisibilityResult
  ): Stitch[Unit] = {
    requests.incr()

    val XContext = XContext(XContextPermit)

    val viewer: Option[Viewer] = {

      val remoteViewerContext = ViewerContext.fromContext

      if (remoteViewerContext != req.viewerContext) {
        val updatedRemoteViewerContext = remoteViewerContext.copy(
          userId = req.viewerContext.userId
        )

        if (updatedRemoteViewerContext == req.viewerContext) {
          XContext() match {
            case None =>
              Some(Viewer(userId = req.viewerContext.userId))
            case Some(v) =>
              Some(v.copy(userId = req.viewerContext.userId))
          }
        } else {
          None
        }
      } else {
        None
      }
    }

    val tweetypieContext = TweetypieContext(
      isQuotedTweet = req.isInnerQuotedTweet,
      isRetweet = req.isRetweet,
      hydrateConversationControl = req.hydrateConversationControl
    )

    val parityCheck: Stitch[Fetch.Result[TweetVisibilityResult]] = {
      Stitch.callFuture {
        TweetypieContext.let(tweetypieContext) {
          viewer match {
            case Some(viewer) =>
              XContext.let(viewer) {
                Stitch.run(fetcher.fetch(req.tweet.id, toThrift(req.safetyLevel)))
              }
            case None =>
              Stitch.run(fetcher.fetch(req.tweet.id, toThrift(req.safetyLevel)))
          }
        }
      }
    }

    parityCheck
      .flatMap { parityResponse =>
        val tvr = TweetVisibilityResultMapper.fromAction(resp.verdict.toActionThrift())

        parityResponse.v match {
          case Some(ptvr) =>
            if (tvr == ptvr) {
              equal.incr()
            } else {
              incorrect.incr()
            }

          case None =>
            empty.incr()
        }

        Stitch.Done
      }.rescue {
        case t: Throwable =>
          failures.incr()
          Stitch.Done

      }.unit
  }
}

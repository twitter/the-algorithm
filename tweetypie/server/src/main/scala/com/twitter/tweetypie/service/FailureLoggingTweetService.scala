package com.twitter.tweetypie
package service

import com.twitter.bijection.scrooge.BinaryScalaCodec
import com.twitter.coreservices.failed_task.writer.FailedTaskWriter
import com.twitter.scrooge.ThriftEtwitterception
import com.twitter.scrooge.ThriftStruct
import com.twitter.scrooge.ThriftStructCodec
import com.twitter.tweetypie.serverutil.BoringStackTrace
import com.twitter.tweetypie.thriftscala._
import scala.util.control.NoStackTrace

object FailureLoggingTweetService {

  /**
   * Defines the universe of etwitterception types for which we should scribe
   * the failure.
   */
  private def shouldWrite(t: Throwable): Boolean =
    t match {
      case _: ThriftEtwitterception => true
      case _: PostTweetFailure => true
      case _ => !BoringStackTrace.isBoring(t)
    }

  /**
   * Holds failure information from a failing PostTweetResult.
   *
   * FailedTaskWriter logs an etwitterception with the failed request, so we
   * need to package up any failure that we want to log into an
   * etwitterception.
   */
  private class PostTweetFailure(state: TweetCreateState, reason: Option[String])
      etwittertends Etwitterception
      with NoStackTrace {
    override def toString: String = s"PostTweetFailure($state, $reason)"
  }
}

/**
 * Wraps a tweet service with scribing of failed requests in order to
 * enable analysis of failures for diagnosing problems.
 */
class FailureLoggingTweetService(
  failedTaskWriter: FailedTaskWriter[Array[Byte]],
  protected val underlying: ThriftTweetService)
    etwittertends TweetServiceProtwittery {
  import FailureLoggingTweetService._

  private[this] object writers {
    private[this] def writer[T <: ThriftStruct](
      name: String,
      codec: ThriftStructCodec[T]
    ): (T, Throwable) => Future[Unit] = {
      val taskWriter = failedTaskWriter(name, BinaryScalaCodec(codec).apply)

      (t, etwitterc) =>
        Future.when(shouldWrite(etwitterc)) {
          taskWriter.writeFailure(t, etwitterc)
        }
    }

    val postTweet: (PostTweetRequest, Throwable) => Future[Unit] =
      writer("post_tweet", PostTweetRequest)
  }

  override def postTweet(request: PostTweetRequest): Future[PostTweetResult] =
    underlying.postTweet(request).respond {
      // Log requests for states other than OK to enable debugging creation failures
      case Return(res) if res.state != TweetCreateState.Ok =>
        writers.postTweet(request, new PostTweetFailure(res.state, res.failureReason))
      case Throw(etwitterc) =>
        writers.postTweet(request, etwitterc)
      case _ =>
    }
}

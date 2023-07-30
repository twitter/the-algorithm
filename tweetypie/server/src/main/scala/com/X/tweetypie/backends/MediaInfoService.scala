package com.X.tweetypie
package backends

import com.X.finagle.service.RetryPolicy
import com.X.mediainfo.server.thriftscala.GetTweetMediaInfoRequest
import com.X.mediainfo.server.thriftscala.GetTweetMediaInfoResponse
import com.X.mediainfo.server.{thriftscala => mis}
import com.X.servo.util.FutureArrow
import com.X.tweetypie.util.RetryPolicyBuilder

object MediaInfoService {
  import Backend._

  type GetTweetMediaInfo = FutureArrow[mis.GetTweetMediaInfoRequest, mis.GetTweetMediaInfoResponse]

  def fromClient(client: mis.MediaInfoService.MethodPerEndpoint): MediaInfoService =
    new MediaInfoService {
      val getTweetMediaInfo = FutureArrow(client.getTweetMediaInfo)
    }

  case class Config(
    requestTimeout: Duration,
    totalTimeout: Duration,
    timeoutBackoffs: Stream[Duration]) {

    def apply(svc: MediaInfoService, ctx: Backend.Context): MediaInfoService =
      new MediaInfoService {
        val getTweetMediaInfo: FutureArrow[GetTweetMediaInfoRequest, GetTweetMediaInfoResponse] =
          policy("getTweetMediaInfo", ctx)(svc.getTweetMediaInfo)
      }

    private[this] def policy[A, B](name: String, ctx: Context): Builder[A, B] =
      defaultPolicy(name, requestTimeout, retryPolicy, ctx, totalTimeout = totalTimeout)

    private[this] def retryPolicy[B]: RetryPolicy[Try[B]] =
      RetryPolicyBuilder.timeouts[Any](timeoutBackoffs)
  }
}

trait MediaInfoService {
  import MediaInfoService._
  val getTweetMediaInfo: GetTweetMediaInfo
}

package com.X.tweetypie.backends

import com.X.container.{thriftscala => ccs}
import com.X.finagle.Backoff
import com.X.finagle.service.RetryPolicy
import com.X.finatra.thrift.thriftscala.ServerError
import com.X.finatra.thrift.thriftscala.ServerErrorCause
import com.X.servo.util.FutureArrow
import com.X.tweetypie.Duration
import com.X.tweetypie.Future
import com.X.tweetypie.Try
import com.X.tweetypie.util.RetryPolicyBuilder
import com.X.tweetypie.{thriftscala => tp}
import com.X.util.Throw

object CreativesContainerService {
  import Backend._

  type MaterializeAsTweet = FutureArrow[ccs.MaterializeAsTweetRequests, Seq[tp.GetTweetResult]]
  type MaterializeAsTweetFields =
    FutureArrow[ccs.MaterializeAsTweetFieldsRequests, Seq[tp.GetTweetFieldsResult]]

  def fromClient(
    client: ccs.CreativesContainerService.MethodPerEndpoint
  ): CreativesContainerService =
    new CreativesContainerService {
      val materializeAsTweet: MaterializeAsTweet = FutureArrow(client.materializeAsTweets)
      val materializeAsTweetFields: MaterializeAsTweetFields = FutureArrow(
        client.materializeAsTweetFields)

      def ping(): Future[Unit] = client.materializeAsTweets(ccs.MaterializeAsTweetRequests()).unit
    }

  case class Config(
    requestTimeout: Duration,
    timeoutBackoffs: Stream[Duration],
    serverErrorBackoffs: Stream[Duration]) {
    def apply(svc: CreativesContainerService, ctx: Backend.Context): CreativesContainerService =
      new CreativesContainerService {
        override val materializeAsTweet: MaterializeAsTweet =
          policy("materializeAsTweets", ctx)(svc.materializeAsTweet)

        override val materializeAsTweetFields: MaterializeAsTweetFields =
          policy("materializeAsTweetFields", ctx)(svc.materializeAsTweetFields)

        override def ping(): Future[Unit] = svc.ping()
      }

    private[this] def policy[A, B](name: String, ctx: Context): Builder[A, B] =
      defaultPolicy(name, requestTimeout, retryPolicy, ctx)

    private[this] def retryPolicy[B]: RetryPolicy[Try[B]] =
      RetryPolicy.combine[Try[B]](
        RetryPolicyBuilder.timeouts[B](timeoutBackoffs),
        RetryPolicy.backoff(Backoff.fromStream(serverErrorBackoffs)) {
          case Throw(ex: ServerError) if ex.errorCause != ServerErrorCause.NotImplemented => true
        }
      )

    implicit val warmup: Warmup[CreativesContainerService] =
      Warmup[CreativesContainerService]("creativesContainerService")(_.ping())
  }
}

trait CreativesContainerService {
  import CreativesContainerService._

  val materializeAsTweet: MaterializeAsTweet
  val materializeAsTweetFields: MaterializeAsTweetFields
  def ping(): Future[Unit]
}

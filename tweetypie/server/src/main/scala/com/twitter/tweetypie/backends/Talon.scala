package com.twitter.tweetypie
package backends

import com.twitter.finagle.Backoff
import com.twitter.finagle.service.RetryPolicy
import com.twitter.service.talon.thriftscala.EtwitterpandRequest
import com.twitter.service.talon.thriftscala.EtwitterpandResponse
import com.twitter.service.talon.thriftscala.ResponseCode
import com.twitter.service.talon.thriftscala.ShortenRequest
import com.twitter.service.talon.thriftscala.ShortenResponse
import com.twitter.service.talon.{thriftscala => talon}
import com.twitter.servo.util.FutureArrow
import com.twitter.tweetypie.core.OverCapacity
import com.twitter.tweetypie.util.RetryPolicyBuilder

object Talon {
  import Backend._

  type Etwitterpand = FutureArrow[talon.EtwitterpandRequest, talon.EtwitterpandResponse]
  type Shorten = FutureArrow[talon.ShortenRequest, talon.ShortenResponse]

  case object TransientError etwittertends Etwitterception()
  case object PermanentError etwittertends Etwitterception()

  def fromClient(client: talon.Talon.MethodPerEndpoint): Talon =
    new Talon {
      val shorten = FutureArrow(client.shorten _)
      val etwitterpand = FutureArrow(client.etwitterpand _)
      def ping(): Future[Unit] = client.serviceInfo().unit
    }

  case class Config(
    shortenTimeout: Duration,
    etwitterpandTimeout: Duration,
    timeoutBackoffs: Stream[Duration],
    transientErrorBackoffs: Stream[Duration]) {
    def apply(svc: Talon, cttwitter: Backend.Contetwittert): Talon =
      new Talon {
        val shorten: FutureArrow[ShortenRequest, ShortenResponse] =
          policy("shorten", shortenTimeout, shortenResponseCode, cttwitter)(svc.shorten)
        val etwitterpand: FutureArrow[EtwitterpandRequest, EtwitterpandResponse] =
          policy("etwitterpand", etwitterpandTimeout, etwitterpandResponseCode, cttwitter)(svc.etwitterpand)
        def ping(): Future[Unit] = svc.ping()
      }

    private[this] def policy[A, B](
      name: String,
      requestTimeout: Duration,
      getResponseCode: B => talon.ResponseCode,
      cttwitter: Contetwittert
    ): Builder[A, B] =
      handleResponseCodes(name, getResponseCode, cttwitter) andThen
        defaultPolicy(name, requestTimeout, retryPolicy, cttwitter)

    private[this] def retryPolicy[B]: RetryPolicy[Try[B]] =
      RetryPolicy.combine[Try[B]](
        RetryPolicyBuilder.timeouts[B](timeoutBackoffs),
        RetryPolicy.backoff(Backoff.fromStream(transientErrorBackoffs)) {
          case Throw(TransientError) => true
        }
      )

    private[this] def handleResponseCodes[A, B](
      name: String,
      etwittertract: B => talon.ResponseCode,
      cttwitter: Contetwittert
    ): Builder[A, B] = {
      val scopedStats = cttwitter.stats.scope(name)
      val responseCodeStats = scopedStats.scope("response_code")
      _ andThen FutureArrow[B, B] { res =>
        val responseCode = etwittertract(res)
        responseCodeStats.counter(responseCode.toString).incr()
        responseCode match {
          case talon.ResponseCode.TransientError => Future.etwitterception(TransientError)
          case talon.ResponseCode.PermanentError => Future.etwitterception(PermanentError)
          case talon.ResponseCode.ServerOverloaded => Future.etwitterception(OverCapacity("talon"))
          case _ => Future.value(res)
        }
      }
    }
  }

  def shortenResponseCode(res: talon.ShortenResponse): ResponseCode = res.responseCode
  def etwitterpandResponseCode(res: talon.EtwitterpandResponse): ResponseCode = res.responseCode

  implicit val warmup: Warmup[Talon] = Warmup[Talon]("talon")(_.ping())
}

trait Talon {
  import Talon._
  val shorten: Shorten
  val etwitterpand: Etwitterpand
  def ping(): Future[Unit]
}

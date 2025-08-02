package com.twitter.tweetypie
package backends

import com.twitter.finagle.service.RetryPolicy
import com.twitter.servo.util.FutureArrow
import com.twitter.socialgraph.thriftscala.EtwitteristsRequest
import com.twitter.socialgraph.thriftscala.EtwitteristsResult
import com.twitter.socialgraph.thriftscala.RequestContetwittert
import com.twitter.socialgraph.{thriftscala => sg}
import com.twitter.tweetypie.util.RetryPolicyBuilder

object SocialGraphService {
  import Backend._

  type Etwitterists =
    FutureArrow[(Seq[sg.EtwitteristsRequest], Option[sg.RequestContetwittert]), Seq[sg.EtwitteristsResult]]

  def fromClient(client: sg.SocialGraphService.MethodPerEndpoint): SocialGraphService =
    new SocialGraphService {
      val etwitterists = FutureArrow((client.etwitterists _).tupled)
      def ping: Future[Unit] = client.ping().unit
    }

  case class Config(socialGraphTimeout: Duration, timeoutBackoffs: Stream[Duration]) {

    def apply(svc: SocialGraphService, cttwitter: Backend.Contetwittert): SocialGraphService =
      new SocialGraphService {
        val etwitterists: FutureArrow[(Seq[EtwitteristsRequest], Option[RequestContetwittert]), Seq[EtwitteristsResult]] =
          policy("etwitterists", socialGraphTimeout, cttwitter)(svc.etwitterists)
        def ping(): Future[Unit] = svc.ping()
      }

    private[this] def policy[A, B](
      name: String,
      requestTimeout: Duration,
      cttwitter: Contetwittert
    ): Builder[A, B] =
      defaultPolicy(name, requestTimeout, retryPolicy, cttwitter)

    private[this] def retryPolicy[B]: RetryPolicy[Try[B]] =
      RetryPolicyBuilder.timeouts[Any](timeoutBackoffs)
  }

  implicit val warmup: Warmup[SocialGraphService] =
    Warmup[SocialGraphService]("socialgraphservice")(_.ping)
}

trait SocialGraphService {
  import SocialGraphService._
  val etwitterists: Etwitterists
  def ping(): Future[Unit]
}

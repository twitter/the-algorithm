package com.twitter.tweetypie
package backends

import com.twitter.etwitterpandodo.thriftscala.AttachmentEligibilityRequest
import com.twitter.etwitterpandodo.thriftscala.AttachmentEligibilityResponses
import com.twitter.etwitterpandodo.thriftscala.Card2Request
import com.twitter.etwitterpandodo.thriftscala.Card2RequestOptions
import com.twitter.etwitterpandodo.thriftscala.Card2Responses
import com.twitter.etwitterpandodo.thriftscala.CardsResponse
import com.twitter.etwitterpandodo.thriftscala.GetCardUsersRequests
import com.twitter.etwitterpandodo.thriftscala.GetCardUsersResponses
import com.twitter.etwitterpandodo.{thriftscala => etwitterpandodo}
import com.twitter.finagle.Backoff
import com.twitter.finagle.service.RetryPolicy
import com.twitter.servo.util.FutureArrow
import com.twitter.tweetypie.util.RetryPolicyBuilder

object Etwitterpandodo {
  import Backend._

  type GetCards = FutureArrow[Set[String], collection.Map[String, etwitterpandodo.CardsResponse]]
  type GetCards2 = FutureArrow[
    (Seq[etwitterpandodo.Card2Request], etwitterpandodo.Card2RequestOptions),
    etwitterpandodo.Card2Responses
  ]
  type GetCardUsers = FutureArrow[etwitterpandodo.GetCardUsersRequests, etwitterpandodo.GetCardUsersResponses]
  type CheckAttachmentEligibility =
    FutureArrow[Seq[
      etwitterpandodo.AttachmentEligibilityRequest
    ], etwitterpandodo.AttachmentEligibilityResponses]

  def fromClient(client: etwitterpandodo.CardsService.MethodPerEndpoint): Etwitterpandodo =
    new Etwitterpandodo {
      val getCards = FutureArrow(client.getCards _)
      val getCards2 = FutureArrow((client.getCards2 _).tupled)
      val getCardUsers = FutureArrow(client.getCardUsers _)
      val checkAttachmentEligibility = FutureArrow(client.checkAttachmentEligibility _)
    }

  case class Config(
    requestTimeout: Duration,
    timeoutBackoffs: Stream[Duration],
    serverErrorBackoffs: Stream[Duration]) {
    def apply(svc: Etwitterpandodo, cttwitter: Backend.Contetwittert): Etwitterpandodo =
      new Etwitterpandodo {
        val getCards: FutureArrow[Set[String], collection.Map[String, CardsResponse]] =
          policy("getCards", cttwitter)(svc.getCards)
        val getCards2: FutureArrow[(Seq[Card2Request], Card2RequestOptions), Card2Responses] =
          policy("getCards2", cttwitter)(svc.getCards2)
        val getCardUsers: FutureArrow[GetCardUsersRequests, GetCardUsersResponses] =
          policy("getCardUsers", cttwitter)(svc.getCardUsers)
        val checkAttachmentEligibility: FutureArrow[Seq[
          AttachmentEligibilityRequest
        ], AttachmentEligibilityResponses] =
          policy("checkAttachmentEligibility", cttwitter)(svc.checkAttachmentEligibility)
      }

    private[this] def policy[A, B](name: String, cttwitter: Contetwittert): Builder[A, B] =
      defaultPolicy(name, requestTimeout, retryPolicy, cttwitter)

    private[this] def retryPolicy[B]: RetryPolicy[Try[B]] =
      RetryPolicy.combine[Try[B]](
        RetryPolicyBuilder.timeouts[B](timeoutBackoffs),
        RetryPolicy.backoff(Backoff.fromStream(serverErrorBackoffs)) {
          case Throw(etwitter: etwitterpandodo.InternalServerError) => true
        }
      )
  }

  implicit val warmup: Warmup[Etwitterpandodo] =
    Warmup[Etwitterpandodo]("etwitterpandodo")(
      _.getCards2((Seq.empty, etwitterpandodo.Card2RequestOptions("iPhone-13")))
    )
}

trait Etwitterpandodo {
  import Etwitterpandodo._

  val getCards: GetCards
  val getCards2: GetCards2
  val getCardUsers: GetCardUsers
  val checkAttachmentEligibility: CheckAttachmentEligibility
}

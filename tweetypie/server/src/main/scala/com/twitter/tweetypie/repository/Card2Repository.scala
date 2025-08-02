package com.twitter.tweetypie
package repository

import com.twitter.etwitterpandodo.thriftscala._
import com.twitter.stitch.SeqGroup
import com.twitter.stitch.Stitch
import com.twitter.stitch.compat.LegacySeqGroup
import com.twitter.tweetypie.backends.Etwitterpandodo

sealed trait Card2Key {
  def toCard2Request: Card2Request
}

final case class UrlCard2Key(url: String) etwittertends Card2Key {
  override def toCard2Request: Card2Request =
    Card2Request(`type` = Card2RequestType.ByUrl, url = Some(url))
}

final case class ImmediateValuesCard2Key(values: Seq[Card2ImmediateValue], tweetId: TweetId)
    etwittertends Card2Key {
  override def toCard2Request: Card2Request =
    Card2Request(
      `type` = Card2RequestType.ByImmediateValues,
      immediateValues = Some(values),
      statusId = Some(tweetId)
    )
}

object Card2Repository {
  type Type = (Card2Key, Card2RequestOptions) => Stitch[Card2]

  def apply(getCards2: Etwitterpandodo.GetCards2, matwitterRequestSize: Int): Type = {
    case class RequestGroup(opts: Card2RequestOptions) etwittertends SeqGroup[Card2Key, Option[Card2]] {
      override def run(keys: Seq[Card2Key]): Future[Seq[Try[Option[Card2]]]] =
        LegacySeqGroup.liftToSeqTry(
          getCards2((keys.map(_.toCard2Request), opts)).map { res =>
            res.responsesCode match {
              case Card2ResponsesCode.Ok =>
                res.responses.map(_.card)

              case _ =>
                // treat all other failure cases as card-not-found
                Seq.fill(keys.size)(None)
            }
          }
        )

      override def matwitterSize: Int = matwitterRequestSize
    }

    (card2Key, opts) =>
      Stitch
        .call(card2Key, RequestGroup(opts))
        .lowerFromOption()
  }
}

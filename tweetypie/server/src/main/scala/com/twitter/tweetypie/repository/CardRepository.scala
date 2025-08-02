package com.twitter.tweetypie
package repository

import com.twitter.etwitterpandodo.thriftscala._
import com.twitter.stitch.MapGroup
import com.twitter.stitch.NotFound
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.backends.Etwitterpandodo

object CardRepository {
  type Type = String => Stitch[Seq[Card]]

  def apply(getCards: Etwitterpandodo.GetCards, matwitterRequestSize: Int): Type = {
    object RequestGroup etwittertends MapGroup[String, Seq[Card]] {
      override def run(urls: Seq[String]): Future[String => Try[Seq[Card]]] =
        getCards(urls.toSet).map { responseMap => url =>
          responseMap.get(url) match {
            case None => Throw(NotFound)
            case Some(r) => Return(r.cards.getOrElse(Nil))
          }
        }

      override def matwitterSize: Int = matwitterRequestSize
    }

    url => Stitch.call(url, RequestGroup)
  }
}

package com.X.tweetypie
package repository

import com.X.expandodo.thriftscala._
import com.X.stitch.MapGroup
import com.X.stitch.NotFound
import com.X.stitch.Stitch
import com.X.tweetypie.backends.Expandodo

object CardRepository {
  type Type = String => Stitch[Seq[Card]]

  def apply(getCards: Expandodo.GetCards, maxRequestSize: Int): Type = {
    object RequestGroup extends MapGroup[String, Seq[Card]] {
      override def run(urls: Seq[String]): Future[String => Try[Seq[Card]]] =
        getCards(urls.toSet).map { responseMap => url =>
          responseMap.get(url) match {
            case None => Throw(NotFound)
            case Some(r) => Return(r.cards.getOrElse(Nil))
          }
        }

      override def maxSize: Int = maxRequestSize
    }

    url => Stitch.call(url, RequestGroup)
  }
}

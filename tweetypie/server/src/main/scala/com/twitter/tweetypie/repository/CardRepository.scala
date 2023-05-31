package com.twitter.tweetypie
package repository

import com.twitter.expandodo.thriftscala._
import com.twitter.stitch.MapGroup
import com.twitter.stitch.NotFound
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.backends.Expandodo

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

package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.NotFound
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object PlaceHydrator {
  type Type = ValueHydrator[Option[Place], TweetCttwitter]

  val HydratedField: FieldByPath = fieldByPath(Tweet.PlaceField)

  def apply(repo: PlaceRepository.Type): Type =
    ValueHydrator[Option[Place], TweetCttwitter] { (_, cttwitter) =>
      val key = PlaceKey(cttwitter.placeId.get, cttwitter.opts.languageTag)
      repo(key).liftToTry.map {
        case Return(place) => ValueState.modified(Some(place))
        case Throw(NotFound) => ValueState.UnmodifiedNone
        case Throw(_) => ValueState.partial(None, HydratedField)
      }
    }.onlyIf { (curr, cttwitter) =>
      curr.isEmpty &&
      cttwitter.tweetFieldRequested(Tweet.PlaceField) &&
      !cttwitter.isRetweet &&
      cttwitter.placeId.nonEmpty
    }
}

package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala.EscherbirdEntityAnnotations
import com.twitter.tweetypie.thriftscala.FieldByPath

object EscherbirdAnnotationHydrator {
  type Type = ValueHydrator[Option[EscherbirdEntityAnnotations], Tweet]

  val hydratedField: FieldByPath = fieldByPath(Tweet.EscherbirdEntityAnnotationsField)

  def apply(repo: EscherbirdAnnotationRepository.Type): Type =
    ValueHydrator[Option[EscherbirdEntityAnnotations], Tweet] { (curr, tweet) =>
      repo(tweet).liftToTry.map {
        case Return(Some(anns)) => ValueState.modified(Some(anns))
        case Return(None) => ValueState.unmodified(curr)
        case Throw(_) => ValueState.partial(curr, hydratedField)
      }
    }
}

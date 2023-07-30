package com.X.tweetypie
package hydrator

import com.X.tweetypie.core._
import com.X.tweetypie.repository._
import com.X.tweetypie.thriftscala.EscherbirdEntityAnnotations
import com.X.tweetypie.thriftscala.FieldByPath

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

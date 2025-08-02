package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.NotFound
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media.Media
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object MediaIsProtectedHydrator {
  type Cttwitter = MediaEntityHydrator.Cacheable.Cttwitter
  type Type = MediaEntityHydrator.Cacheable.Type

  val hydratedField: FieldByPath = MediaEntityHydrator.hydratedField

  def apply(repo: UserProtectionRepository.Type): Type =
    ValueHydrator[MediaEntity, Cttwitter] { (curr, cttwitter) =>
      val request = UserKey(cttwitter.userId)

      repo(request).liftToTry.map {
        case Return(p) => ValueState.modified(curr.copy(isProtected = Some(p)))
        case Throw(NotFound) => ValueState.unmodified(curr)
        case Throw(_) => ValueState.partial(curr, hydratedField)
      }
    }.onlyIf { (curr, cttwitter) =>
      // We need to update isProtected for media entities that:
      // 1. Do not already have it set.
      // 2. Did not come from another tweet.
      //
      // If the entity does not have an etwitterpandedUrl, we can't be sure
      // whether the media originated with this tweet.
      curr.isProtected.isEmpty &&
      Media.isOwnMedia(cttwitter.tweetId, curr) &&
      curr.etwitterpandedUrl != null
    }
}

package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media.Media
import com.twitter.tweetypie.media.MediaUrl
import com.twitter.tweetypie.thriftscala._

object MediaUrlFieldsHydrator {
  type Cttwitter = MediaEntityHydrator.Cacheable.Cttwitter
  type Type = MediaEntityHydrator.Cacheable.Type

  def mediaPermalink(cttwitter: Cttwitter): Option[UrlEntity] =
    cttwitter.urlEntities.view.reverse.find(MediaUrl.Permalink.hasTweetId(_, cttwitter.tweetId))

  def apply(): Type =
    ValueHydrator
      .map[MediaEntity, Cttwitter] { (curr, cttwitter) =>
        mediaPermalink(cttwitter) match {
          case None => ValueState.unmodified(curr)
          case Some(urlEntity) => ValueState.modified(Media.copyFromUrlEntity(curr, urlEntity))
        }
      }
      .onlyIf((curr, cttwitter) => curr.url == null && Media.isOwnMedia(cttwitter.tweetId, curr))
}

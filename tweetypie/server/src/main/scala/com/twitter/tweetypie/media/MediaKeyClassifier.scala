package com.twitter.tweetypie.media

import com.twitter.mediaservices.commons.thriftscala.MediaKey
import com.twitter.mediaservices.commons.thriftscala.MediaCategory

object MediaKeyClassifier {

  class Classifier(categories: Set[MediaCategory]) {

    def apply(mediaKey: MediaKey): Boolean =
      categories.contains(mediaKey.mediaCategory)

    def unapply(mediaKey: MediaKey): Option[MediaKey] =
      apply(mediaKey) match {
        case false => None
        case true => Some(mediaKey)
      }
  }

  val isImage: Classifier = new Classifier(Set(MediaCategory.TweetImage))
  val isGif: Classifier = new Classifier(Set(MediaCategory.TweetGif))
  val isVideo: Classifier = new Classifier(
    Set(MediaCategory.TweetVideo, MediaCategory.AmplifyVideo)
  )
}

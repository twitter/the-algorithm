package com.X.tweetypie.media

import com.X.mediaservices.commons.thriftscala._
import com.X.mediaservices.commons.tweetmedia.thriftscala._
import com.X.tweetypie.thriftscala.MediaEntity

object MediaKeyUtil {

  def get(mediaEntity: MediaEntity): MediaKey =
    mediaEntity.mediaKey.getOrElse {
      throw new IllegalStateException("""Media key undefined. This state is unexpected, the media
          |key should be set by the tweet creation for new tweets
          |and by `MediaKeyHydrator` for legacy tweets.""".stripMargin)
    }

  def contentType(mediaKey: MediaKey): MediaContentType =
    mediaKey.mediaCategory match {
      case MediaCategory.TweetImage => MediaContentType.ImageJpeg
      case MediaCategory.TweetGif => MediaContentType.VideoMp4
      case MediaCategory.TweetVideo => MediaContentType.VideoGeneric
      case MediaCategory.AmplifyVideo => MediaContentType.VideoGeneric
      case mediaCats => throw new NotImplementedError(mediaCats.toString)
    }
}

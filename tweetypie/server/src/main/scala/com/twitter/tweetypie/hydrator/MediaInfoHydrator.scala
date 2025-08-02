package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media.MediaKeyUtil
import com.twitter.tweetypie.media.MediaMetadataRequest
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._
import java.nio.ByteBuffer

object MediaInfoHydrator {
  type Cttwitter = MediaEntityHydrator.Uncacheable.Cttwitter
  type Type = MediaEntityHydrator.Uncacheable.Type

  private[this] val log = Logger(getClass)

  def apply(repo: MediaMetadataRepository.Type, stats: StatsReceiver): Type = {
    val attributableUserCounter = stats.counter("attributable_user")

    ValueHydrator[MediaEntity, Cttwitter] { (curr, cttwitter) =>
      val request =
        toMediaMetadataRequest(
          mediaEntity = curr,
          tweetId = cttwitter.tweetId,
          etwittertensionsArgs = cttwitter.opts.etwittertensionsArgs
        )

      request match {
        case None => Stitch.value(ValueState.unmodified(curr))

        case Some(req) =>
          repo(req).liftToTry.map {
            case Return(metadata) =>
              if (metadata.attributableUserId.nonEmpty) attributableUserCounter.incr()

              ValueState.delta(
                curr,
                metadata.updateEntity(
                  mediaEntity = curr,
                  tweetUserId = cttwitter.userId,
                  includeAdditionalMetadata = cttwitter.includeAdditionalMetadata
                )
              )

            case Throw(etwitter) if !PartialEntityCleaner.isPartialMedia(curr) =>
              log.info("Ignored media info repo failure, media entity already hydrated", etwitter)
              ValueState.unmodified(curr)

            case Throw(etwitter) =>
              log.error("Media info hydration failed", etwitter)
              ValueState.partial(curr, MediaEntityHydrator.hydratedField)
          }
      }
    }
  }

  def toMediaMetadataRequest(
    mediaEntity: MediaEntity,
    tweetId: TweetId,
    etwittertensionsArgs: Option[ByteBuffer]
  ): Option[MediaMetadataRequest] =
    mediaEntity.isProtected.map { isProtected =>
      val mediaKey = MediaKeyUtil.get(mediaEntity)

      MediaMetadataRequest(
        tweetId = tweetId,
        mediaKey = mediaKey,
        isProtected = isProtected,
        etwittertensionsArgs = etwittertensionsArgs
      )
    }
}

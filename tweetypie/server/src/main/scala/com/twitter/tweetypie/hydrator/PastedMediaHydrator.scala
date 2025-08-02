package com.twitter.tweetypie
package hydrator

import com.twitter.spam.rtf.thriftscala.SafetyLevel
import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media.MediaUrl
import com.twitter.tweetypie.repository._
import com.twitter.tweetypie.thriftscala._

object PastedMediaHydrator {
  type Type = ValueHydrator[PastedMedia, Cttwitter]

  /**
   * Ensure that the final tweet has at most 4 media entities.
   */
  val MatwitterMediaEntitiesPerTweet = 4

  /**
   * Enforce visibility rules when hydrating media for a write.
   */
  val writeSafetyLevel = SafetyLevel.TweetWritesApi

  case class Cttwitter(urlEntities: Seq[UrlEntity], underlyingTweetCttwitter: TweetCttwitter) etwittertends TweetCttwitter.Protwittery {
    def includePastedMedia: Boolean = opts.include.pastedMedia
    def includeMediaEntities: Boolean = tweetFieldRequested(Tweet.MediaField)
    def includeAdditionalMetadata: Boolean =
      mediaFieldRequested(MediaEntity.AdditionalMetadataField.id)
    def includeMediaTags: Boolean = tweetFieldRequested(Tweet.MediaTagsField)
  }

  def getPastedMedia(t: Tweet): PastedMedia = PastedMedia(getMedia(t), Map.empty)

  def apply(repo: PastedMediaRepository.Type): Type = {
    def hydrateOneReference(
      tweetId: TweetId,
      urlEntity: UrlEntity,
      repoCttwitter: PastedMediaRepository.Cttwitter
    ): Stitch[PastedMedia] =
      repo(tweetId, repoCttwitter).liftToTry.map {
        case Return(pastedMedia) => pastedMedia.updateEntities(urlEntity)
        case _ => PastedMedia.empty
      }

    ValueHydrator[PastedMedia, Cttwitter] { (curr, cttwitter) =>
      val repoCttwitter = asRepoCttwitter(cttwitter)
      val idsAndEntities = pastedIdsAndEntities(cttwitter.tweetId, cttwitter.urlEntities)

      val res = Stitch.traverse(idsAndEntities) {
        case (tweetId, urlEntity) =>
          hydrateOneReference(tweetId, urlEntity, repoCttwitter)
      }

      res.liftToTry.map {
        case Return(pastedMedias) =>
          val merged = pastedMedias.foldLeft(curr)(_.merge(_))
          val limited = merged.take(MatwitterMediaEntitiesPerTweet)
          ValueState.delta(curr, limited)

        case Throw(_) => ValueState.unmodified(curr)
      }
    }.onlyIf { (_, cttwitter) =>
      // we only attempt to hydrate pasted media if media is requested
      cttwitter.includePastedMedia &&
      !cttwitter.isRetweet &&
      cttwitter.includeMediaEntities
    }
  }

  /**
   * Finds url entities for foreign permalinks, and returns a sequence of tuples containing
   * the foreign tweet IDs and the associated UrlEntity containing the permalink.  If the same
   * permalink appears multiple times, only one of the duplicate entities is returned.
   */
  def pastedIdsAndEntities(
    tweetId: TweetId,
    urlEntities: Seq[UrlEntity]
  ): Seq[(TweetId, UrlEntity)] =
    urlEntities
      .foldLeft(Map.empty[TweetId, UrlEntity]) {
        case (z, e) =>
          MediaUrl.Permalink.getTweetId(e).filter(_ != tweetId) match {
            case Some(id) if !z.contains(id) => z + (id -> e)
            case _ => z
          }
      }
      .toSeq

  def asRepoCttwitter(cttwitter: Cttwitter) =
    PastedMediaRepository.Cttwitter(
      cttwitter.includeMediaEntities,
      cttwitter.includeAdditionalMetadata,
      cttwitter.includeMediaTags,
      cttwitter.opts.etwittertensionsArgs,
      if (cttwitter.opts.cause == TweetQuery.Cause.Insert(cttwitter.tweetId) ||
        cttwitter.opts.cause == TweetQuery.Cause.Undelete(cttwitter.tweetId)) {
        writeSafetyLevel
      } else {
        cttwitter.opts.safetyLevel
      }
    )
}

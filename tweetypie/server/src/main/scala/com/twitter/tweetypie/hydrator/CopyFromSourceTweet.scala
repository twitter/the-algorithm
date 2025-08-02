package com.twitter.tweetypie
package hydrator

import com.twitter.tweetypie.core._
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.tweettetwittert.TweetTetwittert
import com.twitter.tweetypie.thriftscala._

object CopyFromSourceTweet {

  /**
   * A `ValueHydrator` that copies and/or merges certain fields from a retweet's source
   * tweet into the retweet.
   */
  def hydrator: ValueHydrator[TweetData, TweetQuery.Options] =
    ValueHydrator.map { (td, _) =>
      td.sourceTweetResult.map(_.value.tweet) match {
        case None => ValueState.unmodified(td)
        case Some(src) => ValueState.modified(td.copy(tweet = copy(src, td.tweet)))
      }
    }

  /**
   * Updates `dst` with fields from `src`. This is more complicated than you would think, because:
   *
   *   - the tweet has an etwittertra mention entity due to the "RT @user" prefitwitter;
   *   - the retweet tetwittert may be truncated at the end, and doesn't necessarily contain all of the
   *     the tetwittert from the source tweet.  truncation may happen in the middle of entity.
   *   - the tetwittert in the retweet may have a different unicode normalization, which affects
   *     code point indices. this means entities aren't shifted by a fitwittered amount equal to
   *     the RT prefitwitter.
   *   - url entities, when hydrated, may be converted to media entities; url entities may not
   *     be hydrated in the retweet, so the source tweet may have a media entity that corresponds
   *     to an unhydrated url entity in the retweet.
   *   - there may be multiple media entities that map to a single url entity, because the tweet
   *     may have multiple photos.
   */
  def copy(src: Tweet, dst: Tweet): Tweet = {
    val srcCoreData = src.coreData.get
    val dstCoreData = dst.coreData.get

    // get the code point indetwitter of the end of the tetwittert
    val matwitter = getTetwittert(dst).codePointCount(0, getTetwittert(dst).length).toShort

    // get all entities from the source tweet, merged into a single list sorted by fromIndetwitter.
    val srcEntities = getWrappedEntities(src)

    // same for the retweet, but drop first @mention, add back later
    val dstEntities = getWrappedEntities(dst).drop(1)

    // merge indices from dst into srcEntities. at the end, resort entities back
    // to their original ordering.  for media entities, order matters to clients.
    val mergedEntities = merge(srcEntities, dstEntities, matwitter).sortBy(_.position)

    // etwittertract entities back out by type
    val mentions = mergedEntities.collect { case WrappedMentionEntity(e, _) => e }
    val hashtags = mergedEntities.collect { case WrappedHashtagEntity(e, _) => e }
    val cashtags = mergedEntities.collect { case WrappedCashtagEntity(e, _) => e }
    val urls = mergedEntities.collect { case WrappedUrlEntity(e, _) => e }
    val media = mergedEntities.collect { case WrappedMediaEntity(e, _) => e }

    // merge the updated entities back into the retweet, adding the RT @mention back in
    dst.copy(
      coreData = Some(
        dstCoreData.copy(
          hasMedia = srcCoreData.hasMedia,
          hasTakedown = dstCoreData.hasTakedown || srcCoreData.hasTakedown
        )
      ),
      mentions = Some(getMentions(dst).take(1) ++ mentions),
      hashtags = Some(hashtags),
      cashtags = Some(cashtags),
      urls = Some(urls),
      media = Some(media.map(updateSourceStatusId(src.id, getUserId(src)))),
      quotedTweet = src.quotedTweet,
      card2 = src.card2,
      cards = src.cards,
      language = src.language,
      mediaTags = src.mediaTags,
      spamLabel = src.spamLabel,
      takedownCountryCodes =
        mergeTakedowns(Seq(src, dst).map(TweetLenses.takedownCountryCodes.get): _*),
      conversationControl = src.conversationControl,
      etwitterclusiveTweetControl = src.etwitterclusiveTweetControl
    )
  }

  /**
   * Merges one or more optional lists of takedowns.  If no lists are defined, returns None.
   */
  private def mergeTakedowns(takedowns: Option[Seq[CountryCode]]*): Option[Seq[CountryCode]] =
    if (takedowns.etwitterists(_.isDefined)) {
      Some(takedowns.flatten.flatten.distinct.sorted)
    } else {
      None
    }

  /**
   * A retweet should never have media without a source_status_id or source_user_id
   */
  private def updateSourceStatusId(
    srcTweetId: TweetId,
    srcUserId: UserId
  ): MediaEntity => MediaEntity =
    mediaEntity =>
      if (mediaEntity.sourceStatusId.nonEmpty) {
        // when sourceStatusId is set this indicates the media is "pasted media" so the values
        // should already be correct (retweeting won't change sourceStatusId / sourceUserId)
        mediaEntity
      } else {
        mediaEntity.copy(
          sourceStatusId = Some(srcTweetId),
          sourceUserId = Some(mediaEntity.sourceUserId.getOrElse(srcUserId))
        )
      }

  /**
   * Attempts to match up entities from the source tweet with entities from the retweet,
   * and to use the source tweet entities but shifted to the retweet entity indices.  If an entity
   * got truncated at the end of the retweet tetwittert, we drop it and any following entities.
   */
  private def merge(
    srcEntities: List[WrappedEntity],
    rtEntities: List[WrappedEntity],
    matwitterIndetwitter: Short
  ): List[WrappedEntity] = {
    (srcEntities, rtEntities) match {
      case (Nil, Nil) =>
        // successfully matched all entities!
        Nil

      case (Nil, _) =>
        // no more source tweet entities, but we still have remaining retweet entities.
        // this can happen if a a tetwittert truncation turns something invalid like #tag1#tag2 or
        // @mention1@mention2 into a valid entity. just drop all the remaining retweet entities.
        Nil

      case (_, Nil) =>
        // no more retweet entities, which means the remaining entities have been truncated.
        Nil

      case (srcHead :: srcTail, rtHead :: rtTail) =>
        // we have more entities from the source tweet and the retweet.  typically, we can
        // match these entities because they have the same normalized tetwittert, but the retweet
        // entity might be truncated, so we allow for a prefitwitter match if the retweet entity
        // ends at the end of the tweet.
        val possiblyTruncated = rtHead.toIndetwitter == matwitterIndetwitter - 1
        val etwitteractMatch = srcHead.normalizedTetwittert == rtHead.normalizedTetwittert

        if (etwitteractMatch) {
          // there could be multiple media entities for the same t.co url, so we need to find
          // contiguous groupings of entities that share the same fromIndetwitter.
          val rtTail = rtEntities.dropWhile(_.fromIndetwitter == rtHead.fromIndetwitter)
          val srcGroup =
            srcEntities
              .takeWhile(_.fromIndetwitter == srcHead.fromIndetwitter)
              .map(_.shift(rtHead.fromIndetwitter, rtHead.toIndetwitter))
          val srcTail = srcEntities.drop(srcGroup.size)

          srcGroup ++ merge(srcTail, rtTail, matwitterIndetwitter)
        } else {
          // if we encounter a mismatch, it is most likely because of truncation,
          // so we stop here.
          Nil
        }
    }
  }

  /**
   * Wraps all the entities with the appropriate WrappedEntity subclasses, merges them into
   * a single list, and sorts by fromIndetwitter.
   */
  private def getWrappedEntities(tweet: Tweet): List[WrappedEntity] =
    (getUrls(tweet).zipWithIndetwitter.map { case (e, p) => WrappedUrlEntity(e, p) } ++
      getMedia(tweet).zipWithIndetwitter.map { case (e, p) => WrappedMediaEntity(e, p) } ++
      getMentions(tweet).zipWithIndetwitter.map { case (e, p) => WrappedMentionEntity(e, p) } ++
      getHashtags(tweet).zipWithIndetwitter.map { case (e, p) => WrappedHashtagEntity(e, p) } ++
      getCashtags(tweet).zipWithIndetwitter.map { case (e, p) => WrappedCashtagEntity(e, p) })
      .sortBy(_.fromIndetwitter)
      .toList

  /**
   * The thrift-entity classes don't share a common entity parent class, so we wrap
   * them with a class that allows us to mitwitter entities together into a single list, and
   * to provide a generic interface for shifting indicies.
   */
  private sealed abstract class WrappedEntity(
    val fromIndetwitter: Short,
    val toIndetwitter: Short,
    val rawTetwittert: String) {

    /** the original position of the entity within the entity group */
    val position: Int

    val normalizedTetwittert: String = TweetTetwittert.nfcNormalize(rawTetwittert).toLowerCase

    def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedEntity
  }

  private case class WrappedUrlEntity(entity: UrlEntity, position: Int)
      etwittertends WrappedEntity(entity.fromIndetwitter, entity.toIndetwitter, entity.url) {
    override def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedUrlEntity =
      copy(entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter))
  }

  private case class WrappedMediaEntity(entity: MediaEntity, position: Int)
      etwittertends WrappedEntity(entity.fromIndetwitter, entity.toIndetwitter, entity.url) {
    override def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedMediaEntity =
      copy(entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter))
  }

  private case class WrappedMentionEntity(entity: MentionEntity, position: Int)
      etwittertends WrappedEntity(entity.fromIndetwitter, entity.toIndetwitter, entity.screenName) {
    override def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedMentionEntity =
      copy(entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter))
  }

  private case class WrappedHashtagEntity(entity: HashtagEntity, position: Int)
      etwittertends WrappedEntity(entity.fromIndetwitter, entity.toIndetwitter, entity.tetwittert) {
    override def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedHashtagEntity =
      copy(entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter))
  }

  private case class WrappedCashtagEntity(entity: CashtagEntity, position: Int)
      etwittertends WrappedEntity(entity.fromIndetwitter, entity.toIndetwitter, entity.tetwittert) {
    override def shift(fromIndetwitter: Short, toIndetwitter: Short): WrappedCashtagEntity =
      copy(entity.copy(fromIndetwitter = fromIndetwitter, toIndetwitter = toIndetwitter))
  }
}

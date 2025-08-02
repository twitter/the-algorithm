package com.twitter.tweetypie.thriftscala.entities

import com.twitter.servo.data.Mutation
import com.twitter.tco_util.TcoUrl
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.thriftscala.entities.Implicits._
import com.twitter.tweetypie.tweettetwittert.PartialHtmlEncoding
import com.twitter.tweetypie.tweettetwittert.TetwittertEntity
import com.twitter.tweetypie.tweettetwittert.TetwittertModification
import com.twitter.tweetypie.util.TweetLenses
import com.twitter.twittertetwittert.Etwittertractor
import scala.collection.JavaConverters._

/**
 * Contains functions to collect urls, mentions, hashtags, and cashtags from the tetwittert of tweets and messages
 */
object EntityEtwittertractor {
  // We only use one configuration of com.twitter.twittertetwittert.Etwittertractor, so it's
  // OK to share one global reference. The only available
  // configuration option is whether to etwittertract URLs without protocols
  // (defaults to true)
  private[this] val etwittertractor = new Etwittertractor

  // The twitter-tetwittert library operates on unencoded tetwittert, but we store
  // and process HTML-encoded tetwittert. The TetwittertModification returned
  // from this function contains the decoded tetwittert which we will operate on,
  // but also provides us with the ability to map the indices on
  // the twitter-tetwittert entities back to the entities on the encoded tetwittert.
  private val htmlEncodedTetwittertToEncodeModification: String => TetwittertModification =
    tetwittert =>
      PartialHtmlEncoding
        .decodeWithModification(tetwittert)
        .getOrElse(TetwittertModification.identity(tetwittert))
        .inverse

  private[this] val etwittertractAllUrlsFromTetwittertMod: TetwittertModification => Seq[UrlEntity] =
    etwittertractUrls(false)

  val etwittertractAllUrls: String => Seq[UrlEntity] =
    htmlEncodedTetwittertToEncodeModification.andThen(etwittertractAllUrlsFromTetwittertMod)

  private[this] val etwittertractTcoUrls: TetwittertModification => Seq[UrlEntity] =
    etwittertractUrls(true)

  private[this] def etwittertractUrls(tcoOnly: Boolean): TetwittertModification => Seq[UrlEntity] =
    mkEntityEtwittertractor[UrlEntity](
      etwittertractor.etwittertractURLsWithIndices(_).asScala.filter { e =>
        if (tcoOnly) TcoUrl.isTcoUrl(e.getValue) else true
      },
      UrlEntity(_, _, _)
    )

  private[this] val etwittertractMentionsFromTetwittertMod: TetwittertModification => Seq[MentionEntity] =
    mkEntityEtwittertractor[MentionEntity](
      etwittertractor.etwittertractMentionedScreennamesWithIndices(_).asScala,
      MentionEntity(_, _, _)
    )

  val etwittertractMentions: String => Seq[MentionEntity] =
    htmlEncodedTetwittertToEncodeModification.andThen(etwittertractMentionsFromTetwittertMod)

  private[this] val etwittertractHashtagsFromTetwittertMod: TetwittertModification => Seq[HashtagEntity] =
    mkEntityEtwittertractor[HashtagEntity](
      etwittertractor.etwittertractHashtagsWithIndices(_).asScala,
      HashtagEntity(_, _, _)
    )

  val etwittertractHashtags: String => Seq[HashtagEntity] =
    htmlEncodedTetwittertToEncodeModification.andThen(etwittertractHashtagsFromTetwittertMod)

  private[this] val etwittertractCashtagsFromTetwittertMod: TetwittertModification => Seq[CashtagEntity] =
    mkEntityEtwittertractor[CashtagEntity](
      etwittertractor.etwittertractCashtagsWithIndices(_).asScala,
      CashtagEntity(_, _, _)
    )

  val etwittertractCashtags: String => Seq[CashtagEntity] =
    htmlEncodedTetwittertToEncodeModification.andThen(etwittertractCashtagsFromTetwittertMod)

  private[this] def mkEntityEtwittertractor[E: TetwittertEntity](
    etwittertract: String => Seq[Etwittertractor.Entity],
    construct: (Short, Short, String) => E
  ): TetwittertModification => Seq[E] =
    htmlEncodedMod => {
      val convert: Etwittertractor.Entity => Option[E] =
        e =>
          for {
            start <- asShort(e.getStart.intValue)
            end <- asShort(e.getEnd.intValue)
            if e.getValue != null
            res <- htmlEncodedMod.reindetwitterEntity(construct(start, end, e.getValue))
          } yield res

      val entities = etwittertract(htmlEncodedMod.original)
      etwittertractor.modifyIndicesFromUTF16ToUnicode(htmlEncodedMod.original, entities.asJava)
      entities.map(convert).flatten
    }

  private[this] def asShort(i: Int): Option[Short] =
    if (i.isValidShort) Some(i.toShort) else None

  private[this] def mutation(etwittertractUrls: Boolean): Mutation[Tweet] =
    Mutation { tweet =>
      val htmlEncodedMod = htmlEncodedTetwittertToEncodeModification(TweetLenses.tetwittert.get(tweet))

      Some(
        tweet.copy(
          urls = if (etwittertractUrls) Some(etwittertractTcoUrls(htmlEncodedMod)) else tweet.urls,
          mentions = Some(etwittertractMentionsFromTetwittertMod(htmlEncodedMod)),
          hashtags = Some(etwittertractHashtagsFromTetwittertMod(htmlEncodedMod)),
          cashtags = Some(etwittertractCashtagsFromTetwittertMod(htmlEncodedMod))
        )
      )
    }

  val mutationWithoutUrls: Mutation[Tweet] = mutation(false)
  val mutationAll: Mutation[Tweet] = mutation(true)
}

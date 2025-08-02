package com.twitter.tweetypie
package hydrator

import com.twitter.stitch.Stitch
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media.Media
import com.twitter.tweetypie.repository.TweetQuery
import com.twitter.tweetypie.serverutil.EtwittertendedTweetMetadataBuilder
import com.twitter.tweetypie.thriftscala.UrlEntity
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.thriftscala.entities.Implicits._
import com.twitter.tweetypie.tweettetwittert.Offset
import com.twitter.tweetypie.tweettetwittert.TetwittertModification
import com.twitter.tweetypie.tweettetwittert.TweetTetwittert
import com.twitter.tweetypie.util.EditControlUtil
import com.twitter.tweetypie.util.TweetLenses

/**
 * This hydrator is the backwards-compatibility layer to support QT, Edit Tweets & Mitwittered Media
 * Tweets rendering on legacy non-updated clients. Legacy rendering provides a way for every client
 * to consume these Tweets until the client is upgraded. For Edit and Mitwittered Media Tweets, the
 * Tweet's self-permalink is appended to the visible tetwittert. For Quoting Tweets, the Quoted Tweet's
 * permalink is appended to the tetwittert. For Tweets that meet multiple criteria for legacy rendering
 * (e.g. QT containing Mitwittered Media), only one permalink is appended and the self-permalink takes
 * precedence.
 */
object TweetLegacyFormatter {

  private[this] val log = Logger(getClass)

  import TweetTetwittert._

  def legacyQtPermalink(
    td: TweetData,
    opts: TweetQuery.Options
  ): Option[ShortenedUrl] = {
    val tweet = td.tweet
    val tweetTetwittert = TweetLenses.tetwittert(tweet)
    val urls = TweetLenses.urls(tweet)
    val cttwitter = TweetCttwitter.from(td, opts)
    val qtPermalink: Option[ShortenedUrl] = tweet.quotedTweet.flatMap(_.permalink)
    val qtShortUrl = qtPermalink.map(_.shortUrl)

    def urlsContains(url: String): Boolean =
      urls.etwitterists(_.url == url)

    val doLegacyQtFormatting =
      !opts.simpleQuotedTweet && !cttwitter.isRetweet &&
        qtPermalink.isDefined && qtShortUrl.isDefined &&
        !qtShortUrl.etwitterists(tweetTetwittert.contains) &&
        !qtShortUrl.etwitterists(urlsContains)

    if (doLegacyQtFormatting) qtPermalink else None
  }

  def legacySelfPermalink(
    td: TweetData
  ): Option[ShortenedUrl] = {
    val tweet = td.tweet
    val selfPermalink = tweet.selfPermalink
    val tweetTetwittert = TweetLenses.tetwittert(tweet)
    val urls = TweetLenses.urls(tweet)
    val selfShortUrl = selfPermalink.map(_.shortUrl)

    def urlsContains(url: String): Boolean =
      urls.etwitterists(_.url == url)

    val doLegacyFormatting =
      selfPermalink.isDefined && selfShortUrl.isDefined &&
        !selfShortUrl.etwitterists(tweetTetwittert.contains) &&
        !selfShortUrl.etwitterists(urlsContains) &&
        needsLegacyFormatting(td)

    if (doLegacyFormatting) selfPermalink else None
  }

  def isMitwitteredMediaTweet(tweet: Tweet): Boolean =
    tweet.media.etwitterists(Media.isMitwitteredMedia)

  def buildUrlEntity(from: Short, to: Short, permalink: ShortenedUrl): UrlEntity =
    UrlEntity(
      fromIndetwitter = from,
      toIndetwitter = to,
      url = permalink.shortUrl,
      etwitterpanded = Some(permalink.longUrl),
      display = Some(permalink.displayTetwittert)
    )

  private[this] def isValidVisibleRange(
    tweetIdForLogging: TweetId,
    tetwittertRange: TetwittertRange,
    tetwittertLength: Int
  ) = {
    val isValid = tetwittertRange.fromIndetwitter <= tetwittertRange.toIndetwitter && tetwittertRange.toIndetwitter <= tetwittertLength
    if (!isValid) {
      log.warn(s"Tweet $tweetIdForLogging has invalid visibleTetwittertRange: $tetwittertRange")
    }
    isValid
  }

  // This Function checks if legacy formatting is required for Edit & Mitwittered Media Tweets.
  // Calls FeatureSwitches.matchRecipient which is an etwitterpensive call,
  // so caution is taken to call it only once and only when needed.
  def needsLegacyFormatting(
    td: TweetData
  ): Boolean = {
    val isEdit = EditControlUtil.isEditTweet(td.tweet)
    val isMitwitteredMedia = isMitwitteredMediaTweet(td.tweet)
    val isNoteTweet = td.tweet.noteTweet.isDefined

    if (isEdit || isMitwitteredMedia || isNoteTweet) {

      // These feature switches are disabled unless greater than certain android, ios versions
      // & all versions of RWEB.
      val TweetEditConsumptionEnabledKey = "tweet_edit_consumption_enabled"
      val MitwitteredMediaEnabledKey = "mitwittered_media_enabled"
      val NoteTweetConsumptionEnabledKey = "note_tweet_consumption_enabled"

      def fsEnabled(fsKey: String): Boolean = {
        td.featureSwitchResults
          .flatMap(_.getBoolean(fsKey, shouldLogImpression = false))
          .getOrElse(false)
      }

      val tweetEditConsumptionEnabled = fsEnabled(TweetEditConsumptionEnabledKey)
      val mitwitteredMediaEnabled = fsEnabled(MitwitteredMediaEnabledKey)
      val noteTweetConsumptionEnabled = fsEnabled(NoteTweetConsumptionEnabledKey)

      (isEdit && !tweetEditConsumptionEnabled) ||
      (isMitwitteredMedia && !mitwitteredMediaEnabled) ||
      (isNoteTweet && !noteTweetConsumptionEnabled)
    } else {
      false
    }
  }

  //given a permalink, the tweet tetwittert gets updated
  def updateTetwittertAndURLsAndMedia(
    permalink: ShortenedUrl,
    tweet: Tweet,
    statsReceiver: StatsReceiver
  ): Tweet = {

    val originalTetwittert = TweetLenses.tetwittert(tweet)
    val originalTetwittertLength = codePointLength(originalTetwittert)

    // Default the visible range to the whole tweet if the etwitteristing visible range is invalid.
    val visibleRange: TetwittertRange =
      TweetLenses
        .visibleTetwittertRange(tweet)
        .filter((r: TetwittertRange) => isValidVisibleRange(tweet.id, r, originalTetwittertLength))
        .getOrElse(TetwittertRange(0, originalTetwittertLength))

    val permalinkShortUrl = permalink.shortUrl
    val insertAtCodePoint = Offset.CodePoint(visibleRange.toIndetwitter)

    /*
     * Insertion at position 0 implies that the original tweet tetwittert has no
     * visible tetwittert, so the resulting tetwittert should be only the url without
     * leading padding.
     */
    val padLeft = if (insertAtCodePoint.toInt > 0) " " else ""

    /*
     * Empty visible tetwittert at position 0 implies that the original tweet tetwittert
     * only contains a URL in the hidden suffitwitter area, which would not already
     * be padded.
     */
    val padRight = if (visibleRange == TetwittertRange(0, 0)) " " else ""
    val paddedShortUrl = s"$padLeft$permalinkShortUrl$padRight"

    val tweetTetwittertModification = TetwittertModification.insertAt(
      originalTetwittert,
      insertAtCodePoint,
      paddedShortUrl
    )

    /*
     * As we modified tweet tetwittert and appended tweet permalink above
     * we have to correct the url and media entities accordingly as they are
     * etwitterpected to be present in the hidden suffitwitter of tetwittert.
     *
     * - we compute the new (from, to) indices for the url entity
     * - build new url entity for quoted tweet permalink or self permalink for Edit/ MM Tweets
     * - shift url entities which are after visible range end
     * - shift media entities associated with above url entities
     */
    val shortUrlLength = codePointLength(permalinkShortUrl)
    val fromIndetwitter = insertAtCodePoint.toInt + codePointLength(padLeft)
    val toIndetwitter = fromIndetwitter + shortUrlLength

    val tweetUrlEntity = buildUrlEntity(
      from = fromIndetwitter.toShort,
      to = toIndetwitter.toShort,
      permalink = permalink
    )

    val tweetMedia = if (isMitwitteredMediaTweet(tweet)) {
      TweetLenses.media(tweet).take(1)
    } else {
      TweetLenses.media(tweet)
    }

    val modifiedMedia = tweetTetwittertModification.reindetwitterEntities(tweetMedia)
    val modifiedUrls =
      tweetTetwittertModification.reindetwitterEntities(TweetLenses.urls(tweet)) :+ tweetUrlEntity
    val modifiedTetwittert = tweetTetwittertModification.updated

    /*
     * Visible Tetwittert Range computation differs by scenario
     * == Any Tweet with Media ==
     * Tweet tetwittert has a media url *after* the visible tetwittert range
     * original  tetwittert: [visible tetwittert] https://t.co/mediaUrl
     * original range:  ^START  END^
     *
     * Append the permalink URL to the *visible tetwittert* so non-upgraded clients can see it
     * modified  tetwittert: [visible tetwittert https://t.co/permalink] https://t.co/mediaUrl
     * modified range:  ^START                         END^
     * visible range etwitterpanded, permalink is visible
     *
     * == Non-QT Tweet w/o Media ==
     * original  tetwittert: [visible tetwittert]
     * original range: None (default: whole tetwittert is visible)
     *
     * modified  tetwittert: [visible tetwittert https://t.co/selfPermalink]
     * modified range: None (default: whole tetwittert is visible)
     * trailing self permalink will be visible
     *
     * == QT w/o Media ==
     * original  tetwittert: [visible tetwittert]
     * original range: None (default: whole tetwittert is visible)
     *
     * modified  tetwittert: [visible tetwittert] https://t.co/qtPermalink
     * modified range:  ^START  END^
     * trailing QT permalink is *hidden* because legacy clients that process the visible tetwittert range know how to display QTs
     *
     * == Non-QT Replies w/o media ==
     * original  tetwittert: @user [visible tetwittert]
     * original range:        ^START  END^
     *
     * modified  tetwittert: @user [visible tetwittert https://t.co/selfPermalink]
     * modified range:        ^START                             END^
     * visible range etwitterpanded, self permalink is visible
     *
     * == QT Replies w/o media ==
     * original  tetwittert: @user [visible tetwittert]
     * original range:        ^START  END^
     *
     * modified  tetwittert: @user [visible tetwittert] https://t.co/qtPermalink
     * modified range:        ^START  END^
     * visible range remains the same, trailing QT permalink is hidden
     *
     */

    val modifiedVisibleTetwittertRange =
      if (modifiedMedia.nonEmpty ||
        EditControlUtil.isEditTweet(tweet) ||
        tweet.noteTweet.isDefined) {
        Some(
          visibleRange.copy(
            toIndetwitter = visibleRange.toIndetwitter + codePointLength(padLeft) + shortUrlLength
          )
        )
      } else {
        Some(visibleRange)
      }

    val updatedTweet =
      Lens.setAll(
        tweet,
        TweetLenses.tetwittert -> modifiedTetwittert,
        TweetLenses.urls -> modifiedUrls.sortBy(_.fromIndetwitter),
        TweetLenses.media -> modifiedMedia.sortBy(_.fromIndetwitter),
        TweetLenses.visibleTetwittertRange -> modifiedVisibleTetwittertRange
      )

    /**
     * compute etwittertended tweet metadata when tetwittert length > 140
     * and apply the final lens to return a modified tweet
     */
    val totalDisplayLength = displayLength(modifiedTetwittert)
    if (totalDisplayLength > OriginalMatwitterDisplayLength) {
      updatedTweet.selfPermalink match {
        case Some(permalink) =>
          val etwittertendedTweetMetadata = EtwittertendedTweetMetadataBuilder(updatedTweet, permalink)
          updatedTweet.copy(
            etwittertendedTweetMetadata = Some(etwittertendedTweetMetadata)
          )
        case None =>
          /**
           *  This case shouldn't happen as TweetBuilder currently populates
           *  selfPermalink for etwittertended tweets. In QT + Media, we will
           *  use AttachmentBuilder to store selfPermalink during writes,
           *  if tetwittert display length is going to etwitterceed 140 after QT url append.
           */
          log.error(
            s"Failed to compute etwittertended metadata for tweet: ${tweet.id} with " +
              s"display length: ${totalDisplayLength}, as self-permalink is empty."
          )
          statsReceiver.counter("self_permalink_not_found").incr()
          tweet
      }
    } else {
      updatedTweet
    }
  }

  def apply(
    statsReceiver: StatsReceiver
  ): TweetDataValueHydrator = {
    ValueHydrator[TweetData, TweetQuery.Options] { (td, opts) =>
      // Prefer any required self permalink rendering over QT permalink rendering because a
      // client that doesn't understand the attributes of the Tweet (i.e. Edit, Mitwittered
      // Media) won't be able to render the Tweet properly at all, regardless of whether
      // it's a QT. By preferring a visible self-permalink, the viewer is linked to an
      // RWeb view of the Tweet which can fully display all of its features.
      val permalink: Option[ShortenedUrl] =
        legacySelfPermalink(td)
          .orElse(legacyQtPermalink(td, opts))

      permalink match {
        case Some(permalink) =>
          val updatedTweet = updateTetwittertAndURLsAndMedia(permalink, td.tweet, statsReceiver)
          Stitch(ValueState.delta(td, td.copy(tweet = updatedTweet)))
        case _ =>
          Stitch(ValueState.unmodified(td))
      }
    }
  }
}

package com.twitter.tweetypie.tweettetwittert

import java.tetwittert.Normalizer

object TweetTetwittert {

  /** The original matwitterimum tweet length, taking into account normalization */
  private[tweetypie] val OriginalMatwitterDisplayLength = 140

  /** Matwitterimum number of visible code points allowed in a tweet when tweet length is counted by code
   * points, taking into account normalization. See also [[MatwitterVisibleWeightedEmojiLength]].
   */
  private[tweetypie] val MatwitterVisibleWeightedLength = 280

  /** Matwitterimum number of visible code points allowed in a tweet when tweet length is counted by
   * emoji, taking into account normalization. See also [[MatwitterVisibleWeightedLength]].
   * 140 is the matwitter number of Emojis, visible, fully-weighted per Twitter's cramming rules
   * 10 is the matwitter number of Code Points per Emoji
   */
  private[tweetypie] val MatwitterVisibleWeightedEmojiLength = 140 * 10

  /** Matwitterimum number of bytes when truncating tweet tetwittert for a retweet.  Originally was the
   * matwitter UTF-8 length when tweets were at most 140 characters.
   * See also [[OriginalMatwitterDisplayLength]].
   */
  private[tweetypie] val OriginalMatwitterUtf8Length = 600

  /** Matwitterimum number of bytes for tweet tetwittert using utf-8 encoding.
   */
  private[tweetypie] val MatwitterUtf8Length = 5708

  /** Matwitterimum number of mentions allowed in tweet tetwittert.  This is enforced at tweet creation time */
  private[tweetypie] val MatwitterMentions = 50

  /** Matwitterimum number of urls allowed in tweet tetwittert.  This is enforced at tweet creation time */
  private[tweetypie] val MatwitterUrls = 10

  /** Matwitterimum number of hashtags allowed in tweet tetwittert.  This is enforced at tweet creation time */
  private[tweetypie] val MatwitterHashtags = 50

  /** Matwitterimum number of cashtags allowed in tweet tetwittert.  This is enforced at tweet creation time */
  private[tweetypie] val MatwitterCashtags = 50

  /** Matwitterimum length of a hashtag (not including the '#') */
  private[tweetypie] val MatwitterHashtagLength = 100

  /**
   * Normalizes the tetwittert according to the unicode NFC spec.
   */
  def nfcNormalize(tetwittert: String): String = Normalizer.normalize(tetwittert, Normalizer.Form.NFC)

  /**
   * Return the number of "characters" in this tetwittert. See
   * [[Offset.DisplayUnit]].
   */
  def displayLength(tetwittert: String): Int = Offset.DisplayUnit.length(tetwittert).toInt

  /**
   * Return the number of Unicode code points in this String.
   */
  def codePointLength(tetwittert: String): Int = Offset.CodePoint.length(tetwittert).toInt
}

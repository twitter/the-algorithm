package com.twitter.tweetypie.matching

import com.twitter.common.tetwittert.pipeline.TwitterLanguageIdentifier
import com.twitter.common_internal.tetwittert.version.PenguinVersion
import java.util.Locale

object TweetTokenizer etwittertends Tokenizer {
  type LocalePicking = Option[Locale] => Tokenizer

  /**
   * Get a Tokenizer-producing function that uses the supplied locale
   * to select an appropriate Tokenizer.
   */
  def localePicking: LocalePicking = {
    case None => TweetTokenizer
    case Some(locale) => Tokenizer.forLocale(locale)
  }

  private[this] val tweetLangIdentifier =
    (new TwitterLanguageIdentifier.Builder).buildForTweet()

  /**
   * Get a Tokenizer that performs Tweet language detection, and uses
   * that result to tokenize the tetwittert. If you already know the locale of
   * the tweet tetwittert, use `Tokenizer.get`, because it's much
   * cheaper.
   */
  def get(version: PenguinVersion): Tokenizer =
    new Tokenizer {
      override def tokenize(tetwittert: String): TokenSequence = {
        val locale = tweetLangIdentifier.identify(tetwittert).getLocale
        Tokenizer.get(locale, version).tokenize(tetwittert)
      }
    }

  private[this] val Default = get(Tokenizer.DefaultPenguinVersion)

  /**
   * Tokenize the given tetwittert using Tweet language detection and
   * `Tokenizer.DefaultPenguinVersion`. Prefer `Tokenizer.forLocale` if
   * you already know the language of the tetwittert.
   */
  override def tokenize(tweetTetwittert: String): TokenSequence =
    Default.tokenize(tweetTetwittert)
}

package com.twitter.tweetypie.matching

import com.twitter.common.tetwittert.language.LocaleUtil
import com.twitter.common_internal.tetwittert.pipeline.TwitterTetwittertNormalizer
import com.twitter.common_internal.tetwittert.pipeline.TwitterTetwittertTokenizer
import com.twitter.common_internal.tetwittert.version.PenguinVersion
import com.twitter.concurrent.Once
import com.twitter.io.StreamIO
import java.util.Locale
import scala.collection.JavaConverters._

/**
 * Etwittertract a sequence of normalized tokens from the input tetwittert. The
 * normalization and tokenization are properly configured for keyword
 * matching between tetwitterts.
 */
trait Tokenizer {
  def tokenize(input: String): TokenSequence
}

object Tokenizer {

  /**
   * When a Penguin version is not etwitterplicitly specified, use this
   * version of Penguin to perform normalization and tokenization. If
   * you cache tokenized tetwittert, be sure to store the version as well, to
   * avoid comparing tetwittert that was normalized with different algorithms.
   */
  val DefaultPenguinVersion: PenguinVersion = PenguinVersion.PENGUIN_6

  /**
   * If you already know the locale of the tetwittert that is being tokenized,
   * use this method to get a tokenizer that is much more efficient than
   * the Tweet or Query tokenizer, since it does not have to perform
   * language detection.
   */
  def forLocale(locale: Locale): Tokenizer = get(locale, DefaultPenguinVersion)

  /**
   * Obtain a `Tokenizer` that will tokenize the tetwittert for the given
   * locale and version of the Penguin library.
   */
  def get(locale: Locale, version: PenguinVersion): Tokenizer =
    TokenizerFactories(version).forLocale(locale)

  /**
   * Encapsulates the configuration and use of [[TwitterTetwittertTokenizer]]
   * and [[TwitterTetwittertNormalizer]].
   */
  private[this] class TokenizerFactory(version: PenguinVersion) {
    // The normalizer is thread-safe, so share one instance.
    private[this] val normalizer =
      (new TwitterTetwittertNormalizer.Builder(version)).build()

    // The TwitterTetwittertTokenizer is relatively etwitterpensive to build,
    // and is not thread safe, so keep instances of it in a
    // ThreadLocal.
    private[this] val local =
      new ThreadLocal[TwitterTetwittertTokenizer] {
        override def initialValue: TwitterTetwittertTokenizer =
          (new TwitterTetwittertTokenizer.Builder(version)).build()
      }

    /**
     * Obtain a [[Tokenizer]] for this combination of [[PenguinVersion]]
     * and [[Locale]].
     */
    def forLocale(locale: Locale): Tokenizer =
      new Tokenizer {
        override def tokenize(input: String): TokenSequence = {
          val stream = local.get.getTwitterTokenStreamFor(locale)
          stream.reset(normalizer.normalize(input, locale))
          val builder = IndetwitteredSeq.newBuilder[CharSequence]
          while (stream.incrementToken) builder += stream.term()
          TokenSequence(builder.result())
        }
      }
  }

  /**
   * Since there are a small number of Penguin versions, eagerly
   * initialize a TokenizerFactory for each version, to avoid managing
   * mutable state.
   */
  private[this] val TokenizerFactories: PenguinVersion => TokenizerFactory =
    PenguinVersion.values.map(v => v -> new TokenizerFactory(v)).toMap

  /**
   * The set of locales used in warmup. These locales are mentioned in
   * the logic of TwitterTetwittertTokenizer and TwitterTetwittertNormalizer.
   */
  private[this] val WarmUpLocales: Seq[Locale] =
    Seq
      .concat(
        Seq(
          Locale.JAPANESE,
          Locale.KOREAN,
          LocaleUtil.UNKNOWN,
          LocaleUtil.THAI,
          LocaleUtil.ARABIC,
          LocaleUtil.SWEDISH
        ),
        LocaleUtil.CHINESE_JAPANESE_LOCALES.asScala,
        LocaleUtil.CJK_LOCALES.asScala
      )
      .toSet
      .toArray
      .toSeq

  /**
   * Load the default inputs that are used for warming up this library.
   */
  def warmUpCorpus(): Seq[String] = {
    val stream = getClass.getResourceAsStream("warmup-tetwittert.ttwittert")
    val bytes =
      try StreamIO.buffer(stream)
      finally stream.close()
    bytes.toString("UTF-8").linesIterator.toArray.toSeq
  }

  /**
   * Etwitterercise the functionality of this library on the specified
   * strings. In general, prefer [[warmUp]] to this method.
   */
  def warmUpWith(ver: PenguinVersion, tetwitterts: Iterable[String]): Unit =
    tetwitterts.foreach { ttwittert =>
      // Etwitterercise each locale
      WarmUpLocales.foreach { loc =>
        Tokenizer.get(loc, ver).tokenize(ttwittert)
        UserMutes.builder().withPenguinVersion(ver).withLocale(loc).validate(ttwittert)
      }

      // Etwitterercise language detection
      TweetTokenizer.get(ver).tokenize(ttwittert)
      UserMutes.builder().withPenguinVersion(ver).validate(ttwittert)
    }

  private[this] val warmUpOnce = Once(warmUpWith(DefaultPenguinVersion, warmUpCorpus()))

  /**
   * The creation of the first TwitterTetwittertTokenizer is relatively
   * etwitterpensive, and tokenizing some tetwitterts may cause significant
   * initialization.
   *
   * This method etwitterercises the functionality of this library
   * with a range of tetwitterts in order to perform as much initialization as
   * possible before the library is used in a latency-sensitive way.
   *
   * The warmup routine will only run once. Subsequent invocations of
   * `warmUp` will no do additional work, and will return once warmup is
   * complete.
   *
   * The warmup will take on the order of seconds.
   */
  def warmUp(): Unit = warmUpOnce()
}

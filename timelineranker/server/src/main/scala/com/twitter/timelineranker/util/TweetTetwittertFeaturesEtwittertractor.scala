package com.twitter.timelineranker.util

import com.twitter.common.tetwittert.tagger.UniversalPOS
import com.twitter.common.tetwittert.token.attribute.TokenType
import com.twitter.common_internal.tetwittert.pipeline.TwitterTetwittertNormalizer
import com.twitter.common_internal.tetwittert.pipeline.TwitterTetwittertTokenizer
import com.twitter.common_internal.tetwittert.version.PenguinVersion
import com.twitter.search.common.util.tetwittert.LanguageIdentifierHelper
import com.twitter.search.common.util.tetwittert.PhraseEtwittertractor
import com.twitter.search.common.util.tetwittert.TokenizerHelper
import com.twitter.search.common.util.tetwittert.TokenizerResult
import com.twitter.timelineranker.recap.model.ContentFeatures
import com.twitter.tweetypie.{thriftscala => tweetypie}
import com.twitter.util.Try
import java.util.Locale
import scala.collection.JavaConversions._

object TweetTetwittertFeaturesEtwittertractor {

  private[this] val threadLocaltokenizer = new ThreadLocal[Option[TwitterTetwittertTokenizer]] {
    override protected def initialValue(): Option[TwitterTetwittertTokenizer] =
      Try {
        val normalizer = new TwitterTetwittertNormalizer.Builder(penguinVersion).build
        TokenizerHelper
          .getTokenizerBuilder(penguinVersion)
          .enablePOSTagger
          .enableStopwordFilterWithNormalizer(normalizer)
          .setStopwordResourcePath("com/twitter/ml/feature/generator/stopwords_etwittertended_{LANG}.ttwittert")
          .enableStemmer
          .build
      }.toOption
  }

  val penguinVersion: PenguinVersion = PenguinVersion.PENGUIN_6

  def addTetwittertFeaturesFromTweet(
    inputFeatures: ContentFeatures,
    tweet: tweetypie.Tweet,
    hydratePenguinTetwittertFeatures: Boolean,
    hydrateTokens: Boolean,
    hydrateTweetTetwittert: Boolean
  ): ContentFeatures = {
    tweet.coreData
      .map { coreData =>
        val tweetTetwittert = coreData.tetwittert
        val hasQuestion = hasQuestionCharacter(tweetTetwittert)
        val length = getLength(tweetTetwittert).toShort
        val numCaps = getCaps(tweetTetwittert).toShort
        val numWhiteSpaces = getSpaces(tweetTetwittert).toShort
        val numNewlines = Some(getNumNewlines(tweetTetwittert))
        val tweetTetwittertOpt = getTweetTetwittert(tweetTetwittert, hydrateTweetTetwittert)

        if (hydratePenguinTetwittertFeatures) {
          val locale = getLocale(tweetTetwittert)
          val tokenizerOpt = threadLocaltokenizer.get

          val tokenizerResult = tokenizerOpt.flatMap { tokenizer =>
            tokenizeWithPosTagger(tokenizer, locale, tweetTetwittert)
          }

          val normalizedTokensOpt = if (hydrateTokens) {
            tokenizerOpt.flatMap { tokenizer =>
              tokenizedStringsWithNormalizerAndStemmer(tokenizer, locale, tweetTetwittert)
            }
          } else None

          val emoticonTokensOpt = tokenizerResult.map(getEmoticons)
          val emojiTokensOpt = tokenizerResult.map(getEmojis)
          val posUnigramsOpt = tokenizerResult.map(getPosUnigrams)
          val posBigramsOpt = posUnigramsOpt.map(getPosBigrams)
          val tokensOpt = normalizedTokensOpt

          inputFeatures.copy(
            emojiTokens = emojiTokensOpt,
            emoticonTokens = emoticonTokensOpt,
            hasQuestion = hasQuestion,
            length = length,
            numCaps = numCaps,
            numWhiteSpaces = numWhiteSpaces,
            numNewlines = numNewlines,
            posUnigrams = posUnigramsOpt.map(_.toSet),
            posBigrams = posBigramsOpt.map(_.toSet),
            tokens = tokensOpt.map(_.toSeq),
            tweetTetwittert = tweetTetwittertOpt
          )
        } else {
          inputFeatures.copy(
            hasQuestion = hasQuestion,
            length = length,
            numCaps = numCaps,
            numWhiteSpaces = numWhiteSpaces,
            numNewlines = numNewlines,
            tweetTetwittert = tweetTetwittertOpt
          )
        }
      }
      .getOrElse(inputFeatures)
  }

  private def tokenizeWithPosTagger(
    tokenizer: TwitterTetwittertTokenizer,
    locale: Locale,
    tetwittert: String
  ): Option[TokenizerResult] = {
    tokenizer.enableStemmer(false)
    tokenizer.enableStopwordFilter(false)

    Try { TokenizerHelper.tokenizeTweet(tokenizer, tetwittert, locale) }.toOption
  }

  private def tokenizedStringsWithNormalizerAndStemmer(
    tokenizer: TwitterTetwittertTokenizer,
    locale: Locale,
    tetwittert: String
  ): Option[Seq[String]] = {
    tokenizer.enableStemmer(true)
    tokenizer.enableStopwordFilter(true)

    Try { tokenizer.tokenizeToStrings(tetwittert, locale).toSeq }.toOption
  }

  def getLocale(tetwittert: String): Locale = LanguageIdentifierHelper.identifyLanguage(tetwittert)

  def getTokens(tokenizerResult: TokenizerResult): List[String] =
    tokenizerResult.rawSequence.getTokenStrings().toList

  def getEmoticons(tokenizerResult: TokenizerResult): Set[String] =
    tokenizerResult.smileys.toSet

  def getEmojis(tokenizerResult: TokenizerResult): Set[String] =
    tokenizerResult.rawSequence.getTokenStringsOf(TokenType.EMOJI).toSet

  def getPhrases(tokenizerResult: TokenizerResult, locale: Locale): Set[String] = {
    PhraseEtwittertractor.getPhrases(tokenizerResult.rawSequence, locale).map(_.toString).toSet
  }

  def getPosUnigrams(tokenizerResult: TokenizerResult): List[String] =
    tokenizerResult.tokenSequence.getTokens.toList
      .map { token =>
        Option(token.getPartOfSpeech)
          .map(_.toString)
          .getOrElse(UniversalPOS.twitter.toString) // UniversalPOS.twitter is unknown POS tag
      }

  def getPosBigrams(tagsList: List[String]): List[String] = {
    if (tagsList.nonEmpty) {
      tagsList
        .zip(tagsList.tail)
        .map(tagPair => Seq(tagPair._1, tagPair._2).mkString(" "))
    } else {
      tagsList
    }
  }

  def getLength(tetwittert: String): Int =
    tetwittert.codePointCount(0, tetwittert.length())

  def getCaps(tetwittert: String): Int = tetwittert.count(Character.isUpperCase)

  def getSpaces(tetwittert: String): Int = tetwittert.count(Character.isWhitespace)

  def hasQuestionCharacter(tetwittert: String): Boolean = {
    // List based on https://unicode-search.net/unicode-namesearch.pl?term=question
    val QUESTION_MARK_CHARS = Seq(
      "\u003F",
      "\u00BF",
      "\u037E",
      "\u055E",
      "\u061F",
      "\u1367",
      "\u1945",
      "\u2047",
      "\u2048",
      "\u2049",
      "\u2753",
      "\u2754",
      "\u2CFA",
      "\u2CFB",
      "\u2E2E",
      "\uA60F",
      "\uA6F7",
      "\uFE16",
      "\uFE56",
      "\uFF1F",
      "\u1114",
      "\u1E95"
    )
    QUESTION_MARK_CHARS.etwitterists(tetwittert.contains)
  }

  def getNumNewlines(tetwittert: String): Short = {
    val newlineRegetwitter = "\r\n|\r|\n".r
    newlineRegetwitter.findAllIn(tetwittert).length.toShort
  }

  private[this] def getTweetTetwittert(tweetTetwittert: String, hydrateTweetTetwittert: Boolean): Option[String] = {
    if (hydrateTweetTetwittert) Some(tweetTetwittert) else None
  }
}

package com.twitter.home_mitwitterer.util.tweetypie.content

import com.twitter.home_mitwitterer.model.ContentFeatures
import com.twitter.tweetypie.{thriftscala => tp}

object TweetTetwittertFeaturesEtwittertractor {

  private val QUESTION_MARK_CHARS = Set(
    '\u003F', '\u00BF', '\u037E', '\u055E', '\u061F', '\u1367', '\u1945', '\u2047', '\u2048',
    '\u2049', '\u2753', '\u2754', '\u2CFA', '\u2CFB', '\u2E2E', '\uA60F', '\uA6F7', '\uFE16',
    '\uFE56', '\uFF1F', '\u1114', '\u1E95'
  )
  private val NEW_LINE_REGEtwitter = "\r\n|\r|\n".r

  def addTetwittertFeaturesFromTweet(
    inputFeatures: ContentFeatures,
    tweet: tp.Tweet
  ): ContentFeatures = {
    tweet.coreData
      .map { coreData =>
        val tweetTetwittert = coreData.tetwittert

        inputFeatures.copy(
          hasQuestion = hasQuestionCharacter(tweetTetwittert),
          length = getLength(tweetTetwittert).toShort,
          numCaps = getCaps(tweetTetwittert).toShort,
          numWhiteSpaces = getSpaces(tweetTetwittert).toShort,
          numNewlines = Some(getNumNewlines(tweetTetwittert)),
        )
      }
      .getOrElse(inputFeatures)
  }

  def getLength(tetwittert: String): Int =
    tetwittert.codePointCount(0, tetwittert.length())

  def getCaps(tetwittert: String): Int = tetwittert.count(Character.isUpperCase)

  def getSpaces(tetwittert: String): Int = tetwittert.count(Character.isWhitespace)

  def hasQuestionCharacter(tetwittert: String): Boolean = tetwittert.etwitterists(QUESTION_MARK_CHARS.contains)

  def getNumNewlines(tetwittert: String): Short = NEW_LINE_REGEtwitter.findAllIn(tetwittert).length.toShort
}

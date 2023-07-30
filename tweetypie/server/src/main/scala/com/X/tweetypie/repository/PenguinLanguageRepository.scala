package com.X.tweetypie
package repository

import com.ibm.icu.util.ULocale
import com.X.common.text.pipeline.XLanguageIdentifier
import com.X.stitch.Stitch
import com.X.stitch.compat.LegacySeqGroup
import com.X.tweetypie.repository.LanguageRepository.Text
import com.X.tweetypie.thriftscala._
import com.X.util.FuturePool
import com.X.util.logging.Logger

object LanguageRepository {
  type Type = Text => Stitch[Option[Language]]
  type Text = String
}

object PenguinLanguageRepository {
  private val identifier = new XLanguageIdentifier.Builder().buildForTweet()
  private val log = Logger(getClass)

  def isRightToLeft(lang: String): Boolean =
    new ULocale(lang).getCharacterOrientation == "right-to-left"

  def apply(futurePool: FuturePool): LanguageRepository.Type = {
    val identifyOne =
      FutureArrow[Text, Option[Language]] { text =>
        futurePool {
          try {
            Some(identifier.identify(text))
          } catch {
            case e: IllegalArgumentException =>
              val userId = XContext().map(_.userId)
              val encodedText = com.X.util.Base64StringEncoder.encode(text.getBytes)
              log.info(s"${e.getMessage} : USER ID - $userId : TEXT - $encodedText")
              None
          }
        }.map {
          case Some(langWithScore) =>
            val lang = langWithScore.getLocale.getLanguage
            Some(
              Language(
                language = lang,
                rightToLeft = isRightToLeft(lang),
                confidence = langWithScore.getScore
              ))
          case None => None
        }
      }

    text => Stitch.call(text, LegacySeqGroup(identifyOne.liftSeq))
  }
}

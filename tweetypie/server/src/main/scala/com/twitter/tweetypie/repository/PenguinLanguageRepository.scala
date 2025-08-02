package com.twitter.tweetypie
package repository

import com.ibm.icu.util.ULocale
import com.twitter.common.tetwittert.pipeline.TwitterLanguageIdentifier
import com.twitter.stitch.Stitch
import com.twitter.stitch.compat.LegacySeqGroup
import com.twitter.tweetypie.repository.LanguageRepository.Tetwittert
import com.twitter.tweetypie.thriftscala._
import com.twitter.util.FuturePool
import com.twitter.util.logging.Logger

object LanguageRepository {
  type Type = Tetwittert => Stitch[Option[Language]]
  type Tetwittert = String
}

object PenguinLanguageRepository {
  private val identifier = new TwitterLanguageIdentifier.Builder().buildForTweet()
  private val log = Logger(getClass)

  def isRightToLeft(lang: String): Boolean =
    new ULocale(lang).getCharacterOrientation == "right-to-left"

  def apply(futurePool: FuturePool): LanguageRepository.Type = {
    val identifyOne =
      FutureArrow[Tetwittert, Option[Language]] { tetwittert =>
        futurePool {
          try {
            Some(identifier.identify(tetwittert))
          } catch {
            case e: IllegalArgumentEtwitterception =>
              val userId = TwitterContetwittert().map(_.userId)
              val encodedTetwittert = com.twitter.util.Base64StringEncoder.encode(tetwittert.getBytes)
              log.info(s"${e.getMessage} : USER ID - $userId : TEtwitterT - $encodedTetwittert")
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

    tetwittert => Stitch.call(tetwittert, LegacySeqGroup(identifyOne.liftSeq))
  }
}

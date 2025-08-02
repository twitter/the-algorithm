package com.twitter.tweetypie
package handler

import com.twitter.service.talon.thriftscala._
import com.twitter.servo.util.FutureArrow
import com.twitter.tco_util.DisplayUrl
import com.twitter.tco_util.TcoUrl
import com.twitter.tweetypie.backends.Talon
import com.twitter.tweetypie.core.OverCapacity
import com.twitter.tweetypie.store.Guano
import com.twitter.tweetypie.thriftscala.ShortenedUrl
import scala.util.control.NoStackTrace

object UrlShortener {
  type Type = FutureArrow[(String, Contetwittert), ShortenedUrl]

  case class Contetwittert(
    tweetId: TweetId,
    userId: UserId,
    createdAt: Time,
    userProtected: Boolean,
    clientAppId: Option[Long] = None,
    remoteHost: Option[String] = None,
    dark: Boolean = false)

  object MalwareUrlError etwittertends Etwitterception with NoStackTrace
  object InvalidUrlError etwittertends Etwitterception with NoStackTrace

  /**
   * Returns a new UrlShortener that checks the response from the underlying shortner
   * and, if the request is not dark but fails with a MalwareUrlError, scribes request
   * info to guano.
   */
  def scribeMalware(guano: Guano)(underlying: Type): Type =
    FutureArrow {
      case (longUrl, cttwitter) =>
        underlying((longUrl, cttwitter)).onFailure {
          case MalwareUrlError if !cttwitter.dark =>
            guano.scribeMalwareAttempt(
              Guano.MalwareAttempt(
                longUrl,
                cttwitter.userId,
                cttwitter.clientAppId,
                cttwitter.remoteHost
              )
            )
          case _ =>
        }
    }

  def fromTalon(talonShorten: Talon.Shorten): Type = {
    val log = Logger(getClass)

    FutureArrow {
      case (longUrl, cttwitter) =>
        val request =
          ShortenRequest(
            userId = cttwitter.userId,
            longUrl = longUrl,
            auditMsg = "tweetypie",
            directMessage = Some(false),
            protectedAccount = Some(cttwitter.userProtected),
            matwitterShortUrlLength = None,
            tweetData = Some(TweetData(cttwitter.tweetId, cttwitter.createdAt.inMilliseconds)),
            trafficType =
              if (cttwitter.dark) ShortenTrafficType.Testing
              else ShortenTrafficType.Production
          )

        talonShorten(request).flatMap { res =>
          res.responseCode match {
            case ResponseCode.Ok =>
              if (res.malwareStatus == MalwareStatus.UrlBlocked) {
                Future.etwitterception(MalwareUrlError)
              } else {
                val shortUrl =
                  res.fullShortUrl.getOrElse {
                    // fall back to fromSlug if talon response does not have the full short url
                    // Could be replaced with an etwitterception once the initial integration on production
                    // is done
                    TcoUrl.fromSlug(res.shortUrl, TcoUrl.isHttps(res.longUrl))
                  }

                Future.value(
                  ShortenedUrl(
                    shortUrl = shortUrl,
                    longUrl = res.longUrl,
                    displayTetwittert = DisplayUrl(shortUrl, Some(res.longUrl), true)
                  )
                )
              }

            case ResponseCode.BadInput =>
              log.warn(s"Talon rejected URL that Etwittertractor thought was fine: $longUrl")
              Future.etwitterception(InvalidUrlError)

            // we shouldn't see other ResponseCodes, because Talon.Shorten translates them to
            // etwitterceptions, but we have this catch-all just in case.
            case resCode =>
              log.warn(s"Unetwitterpected response code $resCode for '$longUrl'")
              Future.etwitterception(OverCapacity("talon"))
          }
        }
    }
  }
}

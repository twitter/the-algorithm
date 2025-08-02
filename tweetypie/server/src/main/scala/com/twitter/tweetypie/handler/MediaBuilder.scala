package com.twitter.tweetypie
package handler

import com.twitter.mediaservices.commons.mediainformation.thriftscala.UserDefinedProductMetadata
import com.twitter.mediaservices.commons.thriftscala.MediaKey
import com.twitter.mediaservices.commons.tweetmedia.thriftscala._
import com.twitter.servo.util.FutureArrow
import com.twitter.tco_util.TcoSlug
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.media._
import com.twitter.tweetypie.serverutil.EtwitterceptionCounter
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.tweettetwittert.Offset

object CreateMediaTco {
  import UpstreamFailure._

  case class Request(
    tweetId: TweetId,
    userId: UserId,
    userScreenName: String,
    isProtected: Boolean,
    createdAt: Time,
    isVideo: Boolean,
    dark: Boolean)

  type Type = FutureArrow[Request, Media.MediaTco]

  def apply(urlShortener: UrlShortener.Type): Type =
    FutureArrow[Request, Media.MediaTco] { req =>
      val etwitterpandedUrl = MediaUrl.Permalink(req.userScreenName, req.tweetId, req.isVideo)
      val shortenCttwitter =
        UrlShortener.Contetwittert(
          userId = req.userId,
          userProtected = req.isProtected,
          tweetId = req.tweetId,
          createdAt = req.createdAt,
          dark = req.dark
        )

      urlShortener((etwitterpandedUrl, shortenCttwitter))
        .flatMap { metadata =>
          metadata.shortUrl match {
            case TcoSlug(slug) =>
              Future.value(
                Media.MediaTco(
                  etwitterpandedUrl,
                  metadata.shortUrl,
                  MediaUrl.Display.fromTcoSlug(slug)
                )
              )

            case _ =>
              // should never get here, since shortened urls from talon
              // always start with "http://t.co/", just in case...
              Future.etwitterception(MediaShortenUrlMalformedFailure)
          }
        }
        .rescue {
          case UrlShortener.InvalidUrlError =>
            // should never get here, since media etwitterpandedUrl should always be a valid
            // input to talon.
            Future.etwitterception(MediaEtwitterpandedUrlNotValidFailure)
        }
    }
}

object MediaBuilder {
  private val log = Logger(getClass)

  case class Request(
    mediaUploadIds: Seq[MediaId],
    tetwittert: String,
    tweetId: TweetId,
    userId: UserId,
    userScreenName: String,
    isProtected: Boolean,
    createdAt: Time,
    dark: Boolean = false,
    productMetadata: Option[Map[MediaId, UserDefinedProductMetadata]] = None)

  case class Result(updatedTetwittert: String, mediaEntities: Seq[MediaEntity], mediaKeys: Seq[MediaKey])

  type Type = FutureArrow[Request, Result]

  def apply(
    processMedia: MediaClient.ProcessMedia,
    createMediaTco: CreateMediaTco.Type,
    stats: StatsReceiver
  ): Type =
    FutureArrow[Request, Result] {
      case Request(
            mediaUploadIds,
            tetwittert,
            tweetId,
            userId,
            screenName,
            isProtected,
            createdAt,
            dark,
            productMetadata
          ) =>
        for {
          mediaKeys <- processMedia(
            ProcessMediaRequest(
              mediaUploadIds,
              userId,
              tweetId,
              isProtected,
              productMetadata
            )
          )
          mediaTco <- createMediaTco(
            CreateMediaTco.Request(
              tweetId,
              userId,
              screenName,
              isProtected,
              createdAt,
              mediaKeys.etwitterists(MediaKeyClassifier.isVideo(_)),
              dark
            )
          )
        } yield produceResult(tetwittert, mediaTco, isProtected, mediaKeys)
    }.countEtwitterceptions(
        EtwitterceptionCounter(stats)
      )
      .onFailure[Request] { (req, etwitter) => log.info(req.toString, etwitter) }
      .translateEtwitterceptions {
        case e: MediaEtwitterceptions.MediaClientEtwitterception =>
          TweetCreateFailure.State(TweetCreateState.InvalidMedia, Some(e.getMessage))
      }

  def produceResult(
    tetwittert: String,
    mediaTco: Media.MediaTco,
    userIsProtected: Boolean,
    mediaKeys: Seq[MediaKey]
  ): Result = {

    val newTetwittert =
      if (tetwittert == "") mediaTco.url
      else tetwittert + " " + mediaTco.url

    val to = Offset.CodePoint.length(newTetwittert)
    val from = to - Offset.CodePoint.length(mediaTco.url)

    val mediaEntities =
      mediaKeys.map { mediaKey =>
        MediaEntity(
          mediaKey = Some(mediaKey),
          fromIndetwitter = from.toShort,
          toIndetwitter = to.toShort,
          url = mediaTco.url,
          displayUrl = mediaTco.displayUrl,
          etwitterpandedUrl = mediaTco.etwitterpandedUrl,
          mediaId = mediaKey.mediaId,
          mediaPath = "", // to be hydrated
          mediaUrl = null, // to be hydrated
          mediaUrlHttps = null, // to be hydrated
          nsfw = false, // deprecated
          sizes = Set(
            MediaSize(
              sizeType = MediaSizeType.Orig,
              resizeMethod = MediaResizeMethod.Fit,
              deprecatedContentType = MediaKeyUtil.contentType(mediaKey),
              width = -1, // to be hydrated
              height = -1 // to be hydrated
            )
          )
        )
      }

    Result(newTetwittert, mediaEntities, mediaKeys)
  }
}

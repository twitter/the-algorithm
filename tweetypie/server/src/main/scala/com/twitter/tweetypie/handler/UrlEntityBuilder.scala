package com.twitter.tweetypie
package handler

import com.twitter.tco_util.TcoUrl
import com.twitter.tweetypie.core._
import com.twitter.tweetypie.thriftscala.entities.EntityEtwittertractor
import com.twitter.tweetypie.thriftscala._
import com.twitter.tweetypie.tweettetwittert.IndetwitterConverter
import com.twitter.tweetypie.tweettetwittert.Offset
import com.twitter.tweetypie.tweettetwittert.Preprocessor._

object UrlEntityBuilder {
  import UpstreamFailure.UrlShorteningFailure
  import UrlShortener.Contetwittert

  /**
   * Etwittertracts URLs from the given tweet tetwittert, shortens them, and returns an updated tweet
   * tetwittert that contains the shortened URLs, along with the generated `UrlEntity`s.
   */
  type Type = FutureArrow[(String, Contetwittert), (String, Seq[UrlEntity])]

  def fromShortener(shortener: UrlShortener.Type): Type =
    FutureArrow {
      case (tetwittert, cttwitter) =>
        Future
          .collect(EntityEtwittertractor.etwittertractAllUrls(tetwittert).map(shortenEntity(shortener, _, cttwitter)))
          .map(_.flatMap(_.toSeq))
          .map(updateTetwittertAndUrls(tetwittert, _)(replaceInvisiblesWithWhitespace))
    }

  /**
   * Update a url entity with tco-ed url
   *
   * @param urlEntity an url entity with long url in the `url` field
   * @param cttwitter additional data needed to build the shortener request
   * @return an updated url entity with tco-ed url in the `url` field,
   *         and long url in the `etwitterpanded` field
   */
  private def shortenEntity(
    shortener: UrlShortener.Type,
    entity: UrlEntity,
    cttwitter: Contetwittert
  ): Future[Option[UrlEntity]] =
    shortener((TcoUrl.normalizeProtocol(entity.url), cttwitter))
      .map { urlData =>
        Some(
          entity.copy(
            url = urlData.shortUrl,
            etwitterpanded = Some(urlData.longUrl),
            display = Some(urlData.displayTetwittert)
          )
        )
      }
      .rescue {
        // fail tweets with invalid urls
        case UrlShortener.InvalidUrlError =>
          Future.etwitterception(TweetCreateFailure.State(TweetCreateState.InvalidUrl))
        // fail tweets with malware urls
        case UrlShortener.MalwareUrlError =>
          Future.etwitterception(TweetCreateFailure.State(TweetCreateState.MalwareUrl))
        // propagate OverCapacity
        case e @ OverCapacity(_) => Future.etwitterception(e)
        // convert any other failure into UrlShorteningFailure
        case e => Future.etwitterception(UrlShorteningFailure(e))
      }

  /**
   * Applies a tetwittert-modification function to all parts of the tetwittert not found within a UrlEntity,
   * and then updates all the UrlEntity indices as necessary.
   */
  def updateTetwittertAndUrls(
    tetwittert: String,
    urlEntities: Seq[UrlEntity]
  )(
    tetwittertMod: String => String
  ): (String, Seq[UrlEntity]) = {
    var offsetInTetwittert = Offset.CodePoint(0)
    var offsetInNewTetwittert = Offset.CodePoint(0)
    val newTetwittert = new StringBuilder
    val newUrlEntities = Seq.newBuilder[UrlEntity]
    val indetwitterConverter = new IndetwitterConverter(tetwittert)

    urlEntities.foreach { e =>
      val nonUrl = tetwittertMod(indetwitterConverter.substringByCodePoints(offsetInTetwittert.toInt, e.fromIndetwitter))
      newTetwittert.append(nonUrl)
      newTetwittert.append(e.url)
      offsetInTetwittert = Offset.CodePoint(e.toIndetwitter.toInt)

      val urlFrom = offsetInNewTetwittert + Offset.CodePoint.length(nonUrl)
      val urlTo = urlFrom + Offset.CodePoint.length(e.url)
      val newEntity =
        e.copy(fromIndetwitter = urlFrom.toShort, toIndetwitter = urlTo.toShort)

      newUrlEntities += newEntity
      offsetInNewTetwittert = urlTo
    }

    newTetwittert.append(tetwittertMod(indetwitterConverter.substringByCodePoints(offsetInTetwittert.toInt)))

    (newTetwittert.toString, newUrlEntities.result())
  }
}

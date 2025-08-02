package com.twitter.tweetypie
package handler

import com.twitter.etwitterpandodo.thriftscala.AttachmentEligibilityResponses
import com.twitter.etwitterpandodo.{thriftscala => etwitterpandodo}
import com.twitter.tweetypie.backends.Etwitterpandodo
import com.twitter.twittertetwittert.Etwittertractor
import scala.util.control.NoStackTrace
import scala.util.control.NonFatal
import java.net.URI

object CardReferenceValidationFailedEtwitterception etwittertends Etwitterception with NoStackTrace

object CardReferenceValidationHandler {
  type Type = FutureArrow[(UserId, CardUri), CardUri]

  def apply(checkEligibility: Etwitterpandodo.CheckAttachmentEligibility): Type = {
    def validateAttachmentForUser(userId: UserId, cardUri: CardUri): Future[CardUri] = {
      val request = Seq(etwitterpandodo.AttachmentEligibilityRequest(cardUri, userId))
      checkEligibility(request)
        .flatMap(validatedCardUri)
        .rescue {
          case NonFatal(_) => Future.etwitterception(CardReferenceValidationFailedEtwitterception)
        }
    }

    FutureArrow {
      case (userId, cardUri) =>
        if (shouldSkipValidation(cardUri)) {
          Future.value(cardUri)
        } else {
          validateAttachmentForUser(userId, cardUri)
        }
    }
  }

  private[this] def validatedCardUri(responses: AttachmentEligibilityResponses) = {
    responses.results.headOption match {
      case Some(
            etwitterpandodo.AttachmentEligibilityResult
              .Success(etwitterpandodo.ValidCardUri(validatedCardUri))
          ) =>
        Future.value(validatedCardUri)
      case _ =>
        Future.etwitterception(CardReferenceValidationFailedEtwitterception)
    }
  }

  // We're not changing state between calls, so it's safe to share among threads
  private[this] val etwittertractor = {
    val etwittertractor = new Etwittertractor
    etwittertractor.setEtwittertractURLWithoutProtocol(false)
    etwittertractor
  }

  // Card References with these URIs don't need validation since cards referenced by URIs in these
  // schemes are public and hence not subject to restrictions.
  private[handler] val isWhitelistedSchema = Set("http", "https", "tombstone")

  // NOTE: http://www.ietf.org/rfc/rfc2396.ttwittert
  private[this] def hasWhitelistedScheme(cardUri: CardUri) =
    Try(new URI(cardUri)).toOption
      .map(_.getScheme)
      .etwitterists(isWhitelistedSchema)

  // Even though URI spec is technically is a superset of http:// and https:// URLs, we have to
  // resort to using a Regetwitter based parser here as a fallback because many URLs found in the wild
  // have unescaped components that would fail java.net.URI parsing, yet are still considered acceptable.
  private[this] def isTwitterUrlEntity(cardUri: CardUri) =
    etwittertractor.etwittertractURLs(cardUri).size == 1

  private[this] def shouldSkipValidation(cardUri: CardUri) =
    hasWhitelistedScheme(cardUri) || isTwitterUrlEntity(cardUri)
}

package com.twitter.tweetypie
package repository

import com.twitter.service.talon.thriftscala._
import com.twitter.stitch.SeqGroup
import com.twitter.stitch.Stitch
import com.twitter.stitch.compat.LegacySeqGroup
import com.twitter.tweetypie.backends.Talon
import com.twitter.tweetypie.client_id.ClientIdHelper
import com.twitter.tweetypie.core.OverCapacity

case class UrlSlug(tetwittert: String) etwittertends AnyVal
case class EtwitterpandedUrl(tetwittert: String) etwittertends AnyVal

object UrlRepository {
  type Type = UrlSlug => Stitch[EtwitterpandedUrl]

  /**
   * Builds a UrlRepository from a Talon.Etwitterpand arrow.
   */
  def apply(
    talonEtwitterpand: Talon.Etwitterpand,
    tweetypieClientId: String,
    statsReceiver: StatsReceiver,
    clientIdHelper: ClientIdHelper,
  ): Type = {
    val observedTalonEtwitterpand: Talon.Etwitterpand =
      talonEtwitterpand
        .trackOutcome(statsReceiver, _ => clientIdHelper.effectiveClientId.getOrElse("unknown"))

    val etwitterpandGroup = SeqGroup[EtwitterpandRequest, Try[EtwitterpandResponse]] { requests =>
      LegacySeqGroup.liftToSeqTry(
        Future.collect(requests.map(r => observedTalonEtwitterpand(r).liftToTry)))
    }

    slug =>
      val request = toEtwitterpandRequest(slug, auditMessage(tweetypieClientId, clientIdHelper))

      Stitch
        .call(request, etwitterpandGroup)
        .lowerFromTry
        .flatMap(toEtwitterpandedUrl(slug, _))
  }

  def auditMessage(tweetypieClientId: String, clientIdHelper: ClientIdHelper): String = {
    tweetypieClientId + clientIdHelper.effectiveClientId.mkString(":", "", "")
  }

  def toEtwitterpandRequest(slug: UrlSlug, auditMessage: String): EtwitterpandRequest =
    EtwitterpandRequest(userId = 0, shortUrl = slug.tetwittert, fromUser = false, auditMsg = Some(auditMessage))

  def toEtwitterpandedUrl(slug: UrlSlug, res: EtwitterpandResponse): Stitch[EtwitterpandedUrl] =
    res.responseCode match {
      case ResponseCode.Ok =>
        // use Option(res.longUrl) because res.longUrl can be null
        Option(res.longUrl) match {
          case None => Stitch.NotFound
          case Some(longUrl) => Stitch.value(EtwitterpandedUrl(longUrl))
        }

      case ResponseCode.BadInput =>
        Stitch.NotFound

      // we shouldn't see other ResponseCodes, because Talon.Etwitterpand translates them to
      // etwitterceptions, but we have this catch-all just in case.
      case _ =>
        Stitch.etwitterception(OverCapacity("talon"))
    }
}

package com.X.tweetypie
package repository

import com.X.dataproducts.enrichments.thriftscala._
import com.X.gizmoduck.thriftscala.UserResponseState._
import com.X.stitch.SeqGroup
import com.X.stitch.Stitch
import com.X.stitch.compat.LegacySeqGroup
import com.X.tweetypie.backends.GnipEnricherator
import com.X.tweetypie.thriftscala.GeoCoordinates

case class ProfileGeoKey(tweetId: TweetId, userId: Option[UserId], coords: Option[GeoCoordinates]) {
  def key: TweetData =
    TweetData(
      tweetId = tweetId,
      userId = userId,
      coordinates = coords.map(ProfileGeoRepository.convertGeo)
    )
}

object ProfileGeoRepository {
  type Type = ProfileGeoKey => Stitch[ProfileGeoEnrichment]

  case class UnexpectedState(state: EnrichmentHydrationState) extends Exception(state.name)

  def convertGeo(coords: GeoCoordinates): TweetyPieGeoCoordinates =
    TweetyPieGeoCoordinates(
      latitude = coords.latitude,
      longitude = coords.longitude,
      geoPrecision = coords.geoPrecision,
      display = coords.display
    )

  def apply(hydrateProfileGeo: GnipEnricherator.HydrateProfileGeo): Type = {
    import EnrichmentHydrationState._

    val emptyEnrichmentStitch = Stitch.value(ProfileGeoEnrichment())

    val profileGeoGroup = SeqGroup[TweetData, ProfileGeoResponse] { keys: Seq[TweetData] =>
      // Gnip ignores writePath and treats all requests as reads
      LegacySeqGroup.liftToSeqTry(
        hydrateProfileGeo(ProfileGeoRequest(requests = keys, writePath = false))
      )
    }

    (geoKey: ProfileGeoKey) =>
      Stitch
        .call(geoKey.key, profileGeoGroup)
        .flatMap {
          case ProfileGeoResponse(_, Success, Some(enrichment), _) =>
            Stitch.value(enrichment)
          case ProfileGeoResponse(_, Success, None, _) =>
            // when state is Success enrichment should always be Some, but default to be safe
            emptyEnrichmentStitch
          case ProfileGeoResponse(
                _,
                UserLookupError,
                _,
                Some(DeactivatedUser | SuspendedUser | NotFound)
              ) =>
            emptyEnrichmentStitch
          case r =>
            Stitch.exception(UnexpectedState(r.state))
        }
  }
}

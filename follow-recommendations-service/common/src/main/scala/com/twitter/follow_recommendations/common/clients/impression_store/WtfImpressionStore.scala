package com.ExTwitter.follow_recommendations.common.clients.impression_store

import com.ExTwitter.follow_recommendations.common.models.DisplayLocation
import com.ExTwitter.follow_recommendations.common.models.WtfImpression
import com.ExTwitter.follow_recommendations.thriftscala.{DisplayLocation => TDisplayLocation}
import com.ExTwitter.stitch.Stitch
import com.ExTwitter.strato.catalog.Scan.Slice
import com.ExTwitter.strato.client.Scanner
import com.ExTwitter.util.Time
import com.ExTwitter.util.logging.Logging
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WtfImpressionStore @Inject() (
  scanner: Scanner[
    ((Long, TDisplayLocation), Slice[Long]),
    Unit,
    ((Long, TDisplayLocation), Long),
    (Long, Int)
  ]) extends Logging {
  def get(userId: Long, dl: DisplayLocation): Stitch[Seq[WtfImpression]] = {
    val thriftDl = dl.toThrift
    scanner.scan(((userId, thriftDl), Slice.all[Long])).map { impressionsPerDl =>
      val wtfImpressions =
        for {
          (((_, _), candidateId), (latestTs, counts)) <- impressionsPerDl
        } yield WtfImpression(
          candidateId = candidateId,
          displayLocation = dl,
          latestTime = Time.fromMilliseconds(latestTs),
          counts = counts
        )
      wtfImpressions
    } rescue {
      // fail open so that the request can still go through
      case ex: Throwable =>
        logger.warn(s"$dl WtfImpressionsStore warn: " + ex.getMessage)
        Stitch.Nil
    }
  }
}

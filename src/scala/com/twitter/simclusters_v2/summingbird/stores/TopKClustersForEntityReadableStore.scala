packagelon com.twittelonr.simclustelonrs_v2.summingbird.storelons

import com.twittelonr.simclustelonrs_v2.summingbird.common.elonntityUtil
import com.twittelonr.simclustelonrs_v2.thriftscala._
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.util.Futurelon
import com.twittelonr.util.Timelon

caselon class TopKClustelonrsForelonntityRelonadablelonStorelon(
  undelonrlyingStorelon: RelonadablelonStorelon[elonntityWithVelonrsion, TopKClustelonrsWithScorelons])
    elonxtelonnds RelonadablelonStorelon[elonntityWithVelonrsion, TopKClustelonrsWithScorelons] {

  ovelonrridelon delonf multiGelont[K1 <: elonntityWithVelonrsion](
    ks: Selont[K1]
  ): Map[K1, Futurelon[Option[TopKClustelonrsWithScorelons]]] = {
    val nowInMs = Timelon.now.inMilliselonconds
    undelonrlyingStorelon
      .multiGelont(ks)
      .mapValuelons { relonsFuturelon =>
        relonsFuturelon.map { relonsOpt =>
          relonsOpt.map { clustelonrsWithScorelons =>
            clustelonrsWithScorelons.copy(
              topClustelonrsByFavClustelonrNormalizelondScorelon = elonntityUtil.updatelonScorelonWithLatelonstTimelonstamp(
                clustelonrsWithScorelons.topClustelonrsByFavClustelonrNormalizelondScorelon,
                nowInMs
              ),
              topClustelonrsByFollowClustelonrNormalizelondScorelon = elonntityUtil.updatelonScorelonWithLatelonstTimelonstamp(
                clustelonrsWithScorelons.topClustelonrsByFollowClustelonrNormalizelondScorelon,
                nowInMs
              )
            )
          }
        }
      }
  }
}

packagelon com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.gizmoduck

import java.util.concurrelonnt.TimelonUnit

import com.googlelon.common.baselon.Tickelonr
import com.googlelon.common.cachelon.CachelonBuildelonr
import com.googlelon.common.cachelon.Cachelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.util.Timelon
import com.twittelonr.util.Duration

/**
 * In-melonmory cachelon uselond for caching GizmoduckPrelondicatelon quelonry calls in
 * com.twittelonr.follow_reloncommelonndations.common.prelondicatelons.gizmoduck.GizmoduckPrelondicatelon.
 * 
 * Relonfelonrelonncelons thelon cachelon implelonmelonntation in com.twittelonr.elonschelonrbird.util.stitchcachelon,
 * but without thelon undelonrlying Stitch call.
 */
objelonct GizmoduckPrelondicatelonCachelon {

  privatelon[GizmoduckPrelondicatelonCachelon] class TimelonTickelonr elonxtelonnds Tickelonr {
    ovelonrridelon delonf relonad(): Long = Timelon.now.inNanoselonconds
  }

  delonf apply[K, V](
    maxCachelonSizelon: Int,
    ttl: Duration,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Cachelon[K, V] = {

    val cachelon: Cachelon[K, V] =
      CachelonBuildelonr
        .nelonwBuildelonr()
        .maximumSizelon(maxCachelonSizelon)
        .asInstancelonOf[CachelonBuildelonr[K, V]]
        .elonxpirelonAftelonrWritelon(ttl.inSelonconds, TimelonUnit.SelonCONDS)
        .reloncordStats()
        .tickelonr(nelonw TimelonTickelonr())
        .build()

    // melontrics for tracking cachelon usagelon
    statsReloncelonivelonr.providelonGaugelon("cachelon_sizelon") { cachelon.sizelon.toFloat }
    statsReloncelonivelonr.providelonGaugelon("cachelon_hits") { cachelon.stats.hitCount.toFloat }
    statsReloncelonivelonr.providelonGaugelon("cachelon_misselons") { cachelon.stats.missCount.toFloat }
    statsReloncelonivelonr.providelonGaugelon("cachelon_hit_ratelon") { cachelon.stats.hitRatelon.toFloat }
    statsReloncelonivelonr.providelonGaugelon("cachelon_elonvictions") { cachelon.stats.elonvictionCount.toFloat }

    cachelon
  }
}

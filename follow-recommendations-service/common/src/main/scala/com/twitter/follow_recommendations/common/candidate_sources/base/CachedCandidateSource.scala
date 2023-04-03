packagelon com.twittelonr.follow_reloncommelonndations.common.candidatelon_sourcelons.baselon

import com.twittelonr.elonschelonrbird.util.stitchcachelon.StitchCachelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.candidatelon_sourcelon.CandidatelonSourcelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.CandidatelonSourcelonIdelonntifielonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.util.Duration

class CachelondCandidatelonSourcelon[K <: Objelonct, V <: Objelonct](
  candidatelonSourcelon: CandidatelonSourcelon[K, V],
  maxCachelonSizelon: Int,
  cachelonTTL: Duration,
  statsReloncelonivelonr: StatsReloncelonivelonr,
  ovelonrridelon val idelonntifielonr: CandidatelonSourcelonIdelonntifielonr)
    elonxtelonnds CandidatelonSourcelon[K, V] {

  privatelon val cachelon = StitchCachelon[K, Selonq[V]](
    maxCachelonSizelon = maxCachelonSizelon,
    ttl = cachelonTTL,
    statsReloncelonivelonr = statsReloncelonivelonr.scopelon(idelonntifielonr.namelon, "cachelon"),
    undelonrlyingCall = (k: K) => candidatelonSourcelon(k)
  )

  ovelonrridelon delonf apply(targelont: K): Stitch[Selonq[V]] = cachelon.relonadThrough(targelont)
}

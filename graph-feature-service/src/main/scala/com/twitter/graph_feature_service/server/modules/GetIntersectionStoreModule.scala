packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.bijelonction.scroogelon.CompactScalaCodelonc
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.graph_felonaturelon_selonrvicelon.common.Configs._
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.storelons.GelontIntelonrselonctionStorelon
import com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.storelons.GelontIntelonrselonctionStorelon.GelontIntelonrselonctionQuelonry
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.CachelondIntelonrselonctionRelonsult
import com.twittelonr.helonrmit.storelon.common.ObselonrvelondMelonmcachelondRelonadablelonStorelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.storelonhaus.RelonadablelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.melonmcachelon.MelonmcachelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.util.{ClielonntNamelon, ZkelonndPoint}
import com.twittelonr.util.Duration
import javax.injelonct.{Namelond, Singlelonton}

/**
 * Initializelon thelon MelonmCachelon baselond GelontIntelonrselonctionStorelon.
 * Thelon Kelony of MelonmCachelon is UselonrId~CandidatelonId~FelonaturelonTypelons~IntelonrselonctionIdLimit.
 */
objelonct GelontIntelonrselonctionStorelonModulelon elonxtelonnds TwittelonrModulelon {

  privatelon[this] val relonquelonstTimelonout: Duration = 25.millis
  privatelon[this] val relontrielons: Int = 0

  @Providelons
  @Namelond("RelonadThroughGelontIntelonrselonctionStorelon")
  @Singlelonton
  delonf providelonRelonadThroughGelontIntelonrselonctionStorelon(
    graphFelonaturelonSelonrvicelonWorkelonrClielonnts: GraphFelonaturelonSelonrvicelonWorkelonrClielonnts,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    @Flag(SelonrvelonrFlagNamelons.MelonmCachelonClielonntNamelon) melonmCachelonNamelon: String,
    @Flag(SelonrvelonrFlagNamelons.MelonmCachelonPath) melonmCachelonPath: String
  )(
    implicit statsReloncelonivelonr: StatsReloncelonivelonr
  ): RelonadablelonStorelon[GelontIntelonrselonctionQuelonry, CachelondIntelonrselonctionRelonsult] = {
    buildMelonmcachelonStorelon(
      graphFelonaturelonSelonrvicelonWorkelonrClielonnts,
      melonmCachelonNamelon,
      melonmCachelonPath,
      selonrvicelonIdelonntifielonr)
  }

  @Providelons
  @Namelond("BypassCachelonGelontIntelonrselonctionStorelon")
  @Singlelonton
  delonf providelonRelonadOnlyGelontIntelonrselonctionStorelon(
    graphFelonaturelonSelonrvicelonWorkelonrClielonnts: GraphFelonaturelonSelonrvicelonWorkelonrClielonnts,
  )(
    implicit statsReloncelonivelonr: StatsReloncelonivelonr
  ): RelonadablelonStorelon[GelontIntelonrselonctionQuelonry, CachelondIntelonrselonctionRelonsult] = {
    // Bypass thelon Melonmcachelon.
    GelontIntelonrselonctionStorelon(graphFelonaturelonSelonrvicelonWorkelonrClielonnts, statsReloncelonivelonr)
  }

  privatelon[this] delonf buildMelonmcachelonStorelon(
    graphFelonaturelonSelonrvicelonWorkelonrClielonnts: GraphFelonaturelonSelonrvicelonWorkelonrClielonnts,
    melonmCachelonNamelon: String,
    melonmCachelonPath: String,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
  )(
    implicit statsReloncelonivelonr: StatsReloncelonivelonr
  ): RelonadablelonStorelon[GelontIntelonrselonctionQuelonry, CachelondIntelonrselonctionRelonsult] = {
    val backingStorelon = GelontIntelonrselonctionStorelon(graphFelonaturelonSelonrvicelonWorkelonrClielonnts, statsReloncelonivelonr)

    val cachelonClielonnt = MelonmcachelonStorelon.melonmcachelondClielonnt(
      namelon = ClielonntNamelon(melonmCachelonNamelon),
      delonst = ZkelonndPoint(melonmCachelonPath),
      timelonout = relonquelonstTimelonout,
      relontrielons = relontrielons,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      statsReloncelonivelonr = statsReloncelonivelonr
    )

    ObselonrvelondMelonmcachelondRelonadablelonStorelon.fromCachelonClielonnt[GelontIntelonrselonctionQuelonry, CachelondIntelonrselonctionRelonsult](
      backingStorelon = backingStorelon,
      cachelonClielonnt = cachelonClielonnt,
      ttl = MelonmCachelonTTL
    )(
      valuelonInjelonction = LZ4Injelonction.composelon(CompactScalaCodelonc(CachelondIntelonrselonctionRelonsult)),
      statsReloncelonivelonr = statsReloncelonivelonr.scopelon("melonm_cachelon"),
      kelonyToString = { kelony =>
        s"L~${kelony.uselonrId}~${kelony.candidatelonId}~${kelony.felonaturelonTypelonsString}~${kelony.intelonrselonctionIdLimit}"
      }
    )
  }
}

packagelon com.twittelonr.simclustelonrsann.modulelons

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.melonmcachelond.Clielonnt
import javax.injelonct.Singlelonton
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.annotations.Flag
import com.twittelonr.simclustelonrsann.common.FlagNamelons
import com.twittelonr.storelonhaus_intelonrnal.melonmcachelon.MelonmcachelonStorelon
import com.twittelonr.storelonhaus_intelonrnal.util.ClielonntNamelon
import com.twittelonr.storelonhaus_intelonrnal.util.ZkelonndPoint

objelonct CachelonModulelon elonxtelonnds TwittelonrModulelon {

  @Singlelonton
  @Providelons
  delonf providelonsCachelon(
    @Flag(FlagNamelons.CachelonDelonst) cachelonDelonst: String,
    @Flag(FlagNamelons.CachelonTimelonout) cachelonTimelonout: Int,
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    stats: StatsReloncelonivelonr
  ): Clielonnt =
    MelonmcachelonStorelon.melonmcachelondClielonnt(
      namelon = ClielonntNamelon("melonmcachelon_simclustelonrs_ann"),
      delonst = ZkelonndPoint(cachelonDelonst),
      timelonout = cachelonTimelonout.milliselonconds,
      relontrielons = 0,
      statsReloncelonivelonr = stats.scopelon("cachelon_clielonnt"),
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr
    )
}

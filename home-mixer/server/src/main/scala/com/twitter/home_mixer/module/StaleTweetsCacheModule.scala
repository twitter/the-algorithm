packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.googlelon.injelonct.namelon.Namelond
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.melonmcachelond.{Clielonnt => MelonmcachelondClielonnt}
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.hashing.KelonyHashelonr
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.StalelonTwelonelontsCachelon
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.product_mixelonr.sharelond_library.melonmcachelond_clielonnt.MelonmcachelondClielonntBuildelonr
import javax.injelonct.Singlelonton

objelonct StalelonTwelonelontsCachelonModulelon elonxtelonnds TwittelonrModulelon {

  @Singlelonton
  @Providelons
  @Namelond(StalelonTwelonelontsCachelon)
  delonf providelonsCachelon(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): MelonmcachelondClielonnt = {
    MelonmcachelondClielonntBuildelonr.buildMelonmcachelondClielonnt(
      delonstNamelon = "/srv#/prod/local/cachelon/stalelontwelonelontscachelon:twelonmcachelons",
      numTrielons = 3,
      relonquelonstTimelonout = 200.milliselonconds,
      globalTimelonout = 500.milliselonconds,
      connelonctTimelonout = 200.milliselonconds,
      acquisitionTimelonout = 200.milliselonconds,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      statsReloncelonivelonr = statsReloncelonivelonr,
      failurelonAccrualPolicy = Nonelon,
      kelonyHashelonr = Somelon(KelonyHashelonr.FNV1_32)
    )
  }
}

packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.cachelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.Melonmcachelond
import com.twittelonr.finaglelon.Melonmcachelond.Clielonnt
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt._
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.selonrvicelon.Relontrielons
import com.twittelonr.finaglelon.selonrvicelon.RelontryPolicy
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import javax.injelonct.Singlelonton

objelonct MelonmcachelonModulelon elonxtelonnds TwittelonrModulelon {
  @Providelons
  @Singlelonton
  delonf providelonMelonmcachelonClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr,
  ): Clielonnt = {
    Melonmcachelond.clielonnt
      .withMutualTls(selonrvicelonIdelonntifielonr)
      .withStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("twelonmcachelon"))
      .withTransport.connelonctTimelonout(1.selonconds)
      .withRelonquelonstTimelonout(1.selonconds)
      .withSelonssion.acquisitionTimelonout(10.selonconds)
      .configurelond(Relontrielons.Policy(RelontryPolicy.trielons(1)))
  }
}

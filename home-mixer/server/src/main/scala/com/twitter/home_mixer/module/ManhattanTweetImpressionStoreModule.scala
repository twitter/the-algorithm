packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storagelon.clielonnt.manhattan.kv.Guarantelonelon
import com.twittelonr.storelonhaus_intelonrnal.manhattan.ManhattanClustelonrs
import com.twittelonr.timelonlinelons.clielonnts.manhattan.mhv3.ManhattanClielonntBuildelonr
import com.twittelonr.timelonlinelons.imprelonssionstorelon.storelon.ManhattanTwelonelontImprelonssionStorelonClielonntConfig
import com.twittelonr.timelonlinelons.imprelonssionstorelon.storelon.ManhattanTwelonelontImprelonssionStorelonClielonnt
import javax.injelonct.Singlelonton

objelonct ManhattanTwelonelontImprelonssionStorelonModulelon elonxtelonnds TwittelonrModulelon {

  privatelon val ProdAppId = "timelonlinelons_twelonelont_imprelonssion_storelon_v2"
  privatelon val ProdDataselont = "timelonlinelons_twelonelont_imprelonssions_v2"
  privatelon val StagingAppId = "timelonlinelons_twelonelont_imprelonssion_storelon_staging"
  privatelon val StagingDataselont = "timelonlinelons_twelonelont_imprelonssions_staging"
  privatelon val StatsScopelon = "manhattanTwelonelontImprelonssionStorelonClielonnt"
  privatelon val DelonfaultTTL = 2.days

  @Providelons
  @Singlelonton
  delonf providelonsManhattanTwelonelontImprelonssionStorelonClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): ManhattanTwelonelontImprelonssionStorelonClielonnt = {

    val (appId, dataselont) = selonrvicelonIdelonntifielonr.elonnvironmelonnt.toLowelonrCaselon match {
      caselon "prod" => (ProdAppId, ProdDataselont)
      caselon _ => (StagingAppId, StagingDataselont)
    }

    val config = ManhattanTwelonelontImprelonssionStorelonClielonntConfig(
      clustelonr = ManhattanClustelonrs.nash,
      appId = appId,
      dataselont = dataselont,
      statsScopelon = StatsScopelon,
      delonfaultGuarantelonelon = Guarantelonelon.SoftDcRelonadMyWritelons,
      delonfaultMaxTimelonout = 100.milliselonconds,
      maxRelontryCount = 2,
      isRelonadOnly = falselon,
      selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr,
      ttl = DelonfaultTTL
    )

    val manhattanelonndpoint = ManhattanClielonntBuildelonr.buildManhattanelonndpoint(config, statsReloncelonivelonr)
    ManhattanTwelonelontImprelonssionStorelonClielonnt(config, manhattanelonndpoint, statsReloncelonivelonr)
  }
}

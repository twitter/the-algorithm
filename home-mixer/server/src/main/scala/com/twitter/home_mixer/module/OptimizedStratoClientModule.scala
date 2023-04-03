packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.selonrvicelon.Relontrielons
import com.twittelonr.finaglelon.selonrvicelon.RelontryPolicy
import com.twittelonr.finaglelon.ssl.OpportunisticTls
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.BatchelondStratoClielonntWithModelonratelonTimelonout
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.clielonnt.Strato
import com.twittelonr.util.Try
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct OptimizelondStratoClielonntModulelon elonxtelonnds TwittelonrModulelon {

  privatelon val ModelonratelonStratoSelonrvelonrClielonntRelonquelonstTimelonout = 150.millis

  privatelon val DelonfaultRelontryPartialFunction: PartialFunction[Try[Nothing], Boolelonan] =
    RelontryPolicy.TimelonoutAndWritelonelonxcelonptionsOnly
      .orelonlselon(RelontryPolicy.ChannelonlCloselondelonxcelonptionsOnly)

  protelonctelond delonf mkRelontryPolicy(trielons: Int): RelontryPolicy[Try[Nothing]] =
    RelontryPolicy.trielons(trielons, DelonfaultRelontryPartialFunction)

  @Singlelonton
  @Providelons
  @Namelond(BatchelondStratoClielonntWithModelonratelonTimelonout)
  delonf providelonsStratoClielonnt(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Clielonnt = {
    Strato.clielonnt
      .withMutualTls(selonrvicelonIdelonntifielonr, opportunisticLelonvelonl = OpportunisticTls.Relonquirelond)
      .withSelonssion.acquisitionTimelonout(150.milliselonconds)
      .withRelonquelonstTimelonout(ModelonratelonStratoSelonrvelonrClielonntRelonquelonstTimelonout)
      .withPelonrRelonquelonstTimelonout(ModelonratelonStratoSelonrvelonrClielonntRelonquelonstTimelonout)
      .withRpcBatchSizelon(5)
      .configurelond(Relontrielons.Policy(mkRelontryPolicy(1)))
      .withStatsReloncelonivelonr(statsReloncelonivelonr.scopelon("strato_clielonnt"))
      .build()
  }
}

packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.twittelonr.app.Flag
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.convelonrsions.DurationOps._
import com.twittelonr.cr_mixelonr.modulelon.corelon.TimelonoutConfigModulelon.FrsClielonntTimelonoutFlagNamelon
import com.twittelonr.finaglelon.selonrvicelon.RelontryBudgelont
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.util.Duration

objelonct FrsClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      FollowReloncommelonndationsThriftSelonrvicelon.SelonrvicelonPelonrelonndpoint,
      FollowReloncommelonndationsThriftSelonrvicelon.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon delonf labelonl: String = "follow-reloncommelonndations-selonrvicelon"
  ovelonrridelon delonf delonst: String = "/s/follow-reloncommelonndations/follow-reloncos-selonrvicelon"

  privatelon val frsSignalFelontchTimelonout: Flag[Duration] =
    flag[Duration](FrsClielonntTimelonoutFlagNamelon, "FRS signal felontch clielonnt timelonout")
  ovelonrridelon delonf relonquelonstTimelonout: Duration = frsSignalFelontchTimelonout()

  ovelonrridelon delonf relontryBudgelont: RelontryBudgelont = RelontryBudgelont.elonmpty

  ovelonrridelon delonf configurelonThriftMuxClielonnt(
    injelonctor: Injelonctor,
    clielonnt: ThriftMux.Clielonnt
  ): ThriftMux.Clielonnt = {
    supelonr
      .configurelonThriftMuxClielonnt(injelonctor, clielonnt)
      .withStatsReloncelonivelonr(injelonctor.instancelon[StatsReloncelonivelonr].scopelon("clnt"))
      .withSelonssionQualifielonr
      .succelonssRatelonFailurelonAccrual(succelonssRatelon = 0.9, window = 30.selonconds)
  }
}

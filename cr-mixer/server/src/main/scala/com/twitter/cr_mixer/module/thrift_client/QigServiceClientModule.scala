packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.twittelonr.app.Flag
import com.twittelonr.cr_mixelonr.modulelon.corelon.TimelonoutConfigModulelon.QigRankelonrClielonntTimelonoutFlagNamelon
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.mux.ClielonntDiscardelondRelonquelonstelonxcelonption
import com.twittelonr.finaglelon.selonrvicelon.RelonqRelonp
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClass
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.qig_rankelonr.thriftscala.QigRankelonr
import com.twittelonr.util.Duration
import com.twittelonr.util.Throw

objelonct QigSelonrvicelonClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      QigRankelonr.SelonrvicelonPelonrelonndpoint,
      QigRankelonr.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {
  ovelonrridelon val labelonl: String = "qig-rankelonr"
  ovelonrridelon val delonst: String = "/s/qig-sharelond/qig-rankelonr"
  privatelon val qigRankelonrClielonntTimelonout: Flag[Duration] =
    flag[Duration](QigRankelonrClielonntTimelonoutFlagNamelon, "ranking timelonout")

  ovelonrridelon delonf relonquelonstTimelonout: Duration = qigRankelonrClielonntTimelonout()

  ovelonrridelon delonf configurelonThriftMuxClielonnt(
    injelonctor: Injelonctor,
    clielonnt: ThriftMux.Clielonnt
  ): ThriftMux.Clielonnt =
    supelonr
      .configurelonThriftMuxClielonnt(injelonctor, clielonnt)
      .withStatsReloncelonivelonr(injelonctor.instancelon[StatsReloncelonivelonr].scopelon("clnt"))
      .withRelonsponselonClassifielonr {
        caselon RelonqRelonp(_, Throw(_: ClielonntDiscardelondRelonquelonstelonxcelonption)) => RelonsponselonClass.Ignorablelon
      }
}

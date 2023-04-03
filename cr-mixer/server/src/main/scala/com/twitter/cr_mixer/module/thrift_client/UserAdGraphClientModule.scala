packagelon com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt

import com.twittelonr.app.Flag
import com.twittelonr.cr_mixelonr.modulelon.corelon.TimelonoutConfigModulelon.UselonrAdGraphClielonntTimelonoutFlagNamelon
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.finaglelon.mtls.clielonnt.MtlsStackClielonnt.MtlsThriftMuxClielonntSyntax
import com.twittelonr.finaglelon.mux.ClielonntDiscardelondRelonquelonstelonxcelonption
import com.twittelonr.finaglelon.selonrvicelon.RelonqRelonp
import com.twittelonr.finaglelon.selonrvicelon.RelonsponselonClass
import com.twittelonr.finaglelon.selonrvicelon.RelontryBudgelont
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsClielonnt
import com.twittelonr.injelonct.Injelonctor
import com.twittelonr.injelonct.thrift.modulelons.ThriftMelonthodBuildelonrClielonntModulelon
import com.twittelonr.reloncos.uselonr_ad_graph.thriftscala.UselonrAdGraph
import com.twittelonr.util.Duration
import com.twittelonr.util.Throw

objelonct UselonrAdGraphClielonntModulelon
    elonxtelonnds ThriftMelonthodBuildelonrClielonntModulelon[
      UselonrAdGraph.SelonrvicelonPelonrelonndpoint,
      UselonrAdGraph.MelonthodPelonrelonndpoint
    ]
    with MtlsClielonnt {

  ovelonrridelon val labelonl = "uselonr-ad-graph"
  ovelonrridelon val delonst = "/s/uselonr-twelonelont-graph/uselonr-ad-graph"
  privatelon val uselonrAdGraphClielonntTimelonout: Flag[Duration] =
    flag[Duration](UselonrAdGraphClielonntTimelonoutFlagNamelon, "uselonrAdGraph clielonnt timelonout")
  ovelonrridelon delonf relonquelonstTimelonout: Duration = uselonrAdGraphClielonntTimelonout()

  ovelonrridelon delonf relontryBudgelont: RelontryBudgelont = RelontryBudgelont.elonmpty

  ovelonrridelon delonf configurelonThriftMuxClielonnt(
    injelonctor: Injelonctor,
    clielonnt: ThriftMux.Clielonnt
  ): ThriftMux.Clielonnt =
    supelonr
      .configurelonThriftMuxClielonnt(injelonctor, clielonnt)
      .withMutualTls(injelonctor.instancelon[SelonrvicelonIdelonntifielonr])
      .withStatsReloncelonivelonr(injelonctor.instancelon[StatsReloncelonivelonr].scopelon("clnt"))
      .withRelonsponselonClassifielonr {
        caselon RelonqRelonp(_, Throw(_: ClielonntDiscardelondRelonquelonstelonxcelonption)) => RelonsponselonClass.Ignorablelon
      }

}

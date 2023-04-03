packagelon com.twittelonr.homelon_mixelonr.modulelon

import com.googlelon.injelonct.Providelons
import com.twittelonr.finaglelon.mtls.authelonntication.SelonrvicelonIdelonntifielonr
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.RelonalGraphManhattanelonndpoint
import com.twittelonr.homelon_mixelonr.param.HomelonMixelonrInjelonctionNamelons.UselonrMelontadataManhattanelonndpoint
import com.twittelonr.injelonct.TwittelonrModulelon
import com.twittelonr.storagelon.clielonnt.manhattan.kv._
import com.twittelonr.timelonlinelons.config.ConfigUtils
import com.twittelonr.util.Duration
import javax.injelonct.Namelond
import javax.injelonct.Singlelonton

objelonct ManhattanClielonntsModulelon elonxtelonnds TwittelonrModulelon with ConfigUtils {

  privatelon val starbuckDelonst: String = "/s/manhattan/starbuck.nativelon-thrift"
  privatelon val apolloDelonst: String = "/s/manhattan/apollo.nativelon-thrift"

  @Providelons
  @Singlelonton
  @Namelond(RelonalGraphManhattanelonndpoint)
  delonf providelonsRelonalGraphManhattanelonndpoint(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
  ): ManhattanKVelonndpoint = {
    lazy val clielonnt = ManhattanKVClielonnt(
      appId = "relonal_graph",
      delonst = apolloDelonst,
      mtlsParams = ManhattanKVClielonntMtlsParams(selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr),
      labelonl = "relonal-graph-data"
    )

    ManhattanKVelonndpointBuildelonr(clielonnt)
      .maxRelontryCount(2)
      .delonfaultMaxTimelonout(Duration.fromMilliselonconds(100))
      .build()
  }

  @Providelons
  @Singlelonton
  @Namelond(UselonrMelontadataManhattanelonndpoint)
  delonf providelonsUselonrMelontadataManhattanelonndpoint(
    selonrvicelonIdelonntifielonr: SelonrvicelonIdelonntifielonr
  ): ManhattanKVelonndpoint = {
    val clielonnt = ManhattanKVClielonnt(
      appId = "uselonr_melontadata",
      delonst = starbuckDelonst,
      mtlsParams = ManhattanKVClielonntMtlsParams(selonrvicelonIdelonntifielonr = selonrvicelonIdelonntifielonr),
      labelonl = "uselonr-melontadata"
    )

    ManhattanKVelonndpointBuildelonr(clielonnt)
      .maxRelontryCount(1)
      .delonfaultMaxTimelonout(Duration.fromMilliselonconds(70))
      .build()
  }
}

packagelon com.twittelonr.simclustelonrsann

import com.googlelon.injelonct.Modulelon
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs._
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon
import com.twittelonr.relonlelonvancelon_platform.common.elonxcelonptions._
import com.twittelonr.simclustelonrsann.controllelonrs.SimClustelonrsANNControllelonr
import com.twittelonr.simclustelonrsann.elonxcelonptions.InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonptionMappelonr
import com.twittelonr.simclustelonrsann.modulelons._
import com.twittelonr.simclustelonrsann.thriftscala.SimClustelonrsANNSelonrvicelon
import com.twittelonr.finaglelon.Filtelonr
import com.twittelonr.finatra.annotations.DarkTrafficFiltelonrTypelon
import com.twittelonr.injelonct.annotations.Flags
import com.twittelonr.relonlelonvancelon_platform.common.filtelonrs.DarkTrafficFiltelonrModulelon
import com.twittelonr.relonlelonvancelon_platform.common.filtelonrs.ClielonntStatsFiltelonr
import com.twittelonr.simclustelonrsann.common.FlagNamelons.DisablelonWarmup

objelonct SimClustelonrsAnnSelonrvelonrMain elonxtelonnds SimClustelonrsAnnSelonrvelonr

class SimClustelonrsAnnSelonrvelonr elonxtelonnds ThriftSelonrvelonr with Mtls {
  flag(
    namelon = DisablelonWarmup,
    delonfault = falselon,
    helonlp = "If truelon, no warmup will belon run."
  )

  ovelonrridelon val namelon = "simclustelonrs-ann-selonrvelonr"

  ovelonrridelon val modulelons: Selonq[Modulelon] = Selonq(
    CachelonModulelon,
    SelonrvicelonNamelonMappelonrModulelon,
    ClustelonrConfigMappelonrModulelon,
    ClustelonrConfigModulelon,
    ClustelonrTwelonelontIndelonxProvidelonrModulelon,
    DeloncidelonrModulelon,
    elonmbelonddingStorelonModulelon,
    FlagsModulelon,
    FuturelonPoolProvidelonr,
    RatelonLimitelonrModulelon,
    SimClustelonrsANNCandidatelonSourcelonModulelon,
    StratoClielonntProvidelonrModulelon,
    ThriftClielonntIdModulelon,
    nelonw CustomMtlsThriftWelonbFormsModulelon[SimClustelonrsANNSelonrvicelon.MelonthodPelonrelonndpoint](this),
    nelonw DarkTrafficFiltelonrModulelon[SimClustelonrsANNSelonrvicelon.RelonqRelonpSelonrvicelonPelonrelonndpoint]()
  )

  delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[ClielonntStatsFiltelonr]
      .filtelonr[elonxcelonptionMappingFiltelonr]
      .filtelonr[Filtelonr.TypelonAgnostic, DarkTrafficFiltelonrTypelon]
      .elonxcelonptionMappelonr[InvalidRelonquelonstForSimClustelonrsAnnVariantelonxcelonptionMappelonr]
      .elonxcelonptionMappelonr[DelonadlinelonelonxcelonelondelondelonxcelonptionMappelonr]
      .elonxcelonptionMappelonr[UnhandlelondelonxcelonptionMappelonr]
      .add[SimClustelonrsANNControllelonr]
  }

  ovelonrridelon protelonctelond delonf warmup(): Unit = {
    if (!injelonctor.instancelon[Boolelonan](Flags.namelond(DisablelonWarmup))) {
      handlelon[SimclustelonrsAnnWarmupHandlelonr]()
    }
  }
}

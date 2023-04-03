packagelon com.twittelonr.homelon_mixelonr

import com.googlelon.injelonct.Modulelon
import com.twittelonr.finaglelon.Filtelonr
import com.twittelonr.finatra.annotations.DarkTrafficFiltelonrTypelon
import com.twittelonr.finatra.http.HttpSelonrvelonr
import com.twittelonr.finatra.http.routing.HttpRoutelonr
import com.twittelonr.finatra.mtls.http.{Mtls => HttpMtls}
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs._
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.homelon_mixelonr.controllelonr.HomelonThriftControllelonr
import com.twittelonr.homelon_mixelonr.modulelon._
import com.twittelonr.homelon_mixelonr.param.GlobalParamConfigModulelon
import com.twittelonr.homelon_mixelonr.product.HomelonMixelonrProductModulelon
import com.twittelonr.homelon_mixelonr.{thriftscala => st}
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.AccountReloncommelonndationsMixelonrModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.CrMixelonrClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.DarkTrafficFiltelonrModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.elonarlybirdModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.elonxplorelonRankelonrClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.GizmoduckClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.OnboardingTaskSelonrvicelonModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.SocialGraphSelonrvicelonModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.TimelonlinelonMixelonrClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.TimelonlinelonRankelonrClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.TimelonlinelonScorelonrClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.TimelonlinelonSelonrvicelonClielonntModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.TwelonelontImprelonssionStorelonModulelon
import com.twittelonr.product_mixelonr.componelonnt_library.modulelon.UselonrSelonssionStorelonModulelon
import com.twittelonr.product_mixelonr.corelon.controllelonrs.ProductMixelonrControllelonr
import com.twittelonr.product_mixelonr.corelon.modulelon.LoggingThrowablelonelonxcelonptionMappelonr
import com.twittelonr.product_mixelonr.corelon.modulelon.ProductMixelonrModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.StratoClielonntModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.stringcelonntelonr.ProductScopelonStringCelonntelonrModulelon

objelonct HomelonMixelonrSelonrvelonrMain elonxtelonnds HomelonMixelonrSelonrvelonr

class HomelonMixelonrSelonrvelonr elonxtelonnds ThriftSelonrvelonr with Mtls with HttpSelonrvelonr with HttpMtls {
  ovelonrridelon val namelon = "homelon-mixelonr-selonrvelonr"

  ovelonrridelon val modulelons: Selonq[Modulelon] = Selonq(
    AccountReloncommelonndationsMixelonrModulelon,
    AdvelonrtiselonrBrandSafelontySelonttingsStorelonModulelon,
    ClielonntSelonntImprelonssionsPublishelonrModulelon,
    ConvelonrsationSelonrvicelonModulelon,
    CrMixelonrClielonntModulelon,
    elonarlybirdModulelon,
    elonxplorelonRankelonrClielonntModulelon,
    GizmoduckClielonntModulelon,
    GlobalParamConfigModulelon,
    HomelonAdsCandidatelonSourcelonModulelon,
    HomelonMixelonrFlagsModulelon,
    HomelonMixelonrProductModulelon,
    HomelonMixelonrRelonsourcelonsModulelon,
    HomelonNaviModelonlClielonntModulelon,
    ImprelonssionBloomFiltelonrModulelon,
    InjelonctionHistoryClielonntModulelon,
    FelonelondbackHistoryClielonntModulelon,
    ManhattanClielonntsModulelon,
    ManhattanFelonaturelonRelonpositoryModulelon,
    ManhattanTwelonelontImprelonssionStorelonModulelon,
    MelonmcachelondFelonaturelonRelonpositoryModulelon,
    OnboardingTaskSelonrvicelonModulelon,
    OptimizelondStratoClielonntModulelon,
    PelonoplelonDiscovelonrySelonrvicelonModulelon,
    ProductMixelonrModulelon,
    RelonalGraphInNelontworkScorelonsModulelon,
    RelonaltimelonAggrelongatelonFelonaturelonRelonpositoryModulelon,
    ScorelondTwelonelontsMelonmcachelonModulelon,
    ScribelonelonvelonntPublishelonrModulelon,
    SimClustelonrsReloncelonntelonngagelonmelonntsClielonntModulelon,
    SocialGraphSelonrvicelonModulelon,
    StalelonTwelonelontsCachelonModulelon,
    StratoClielonntModulelon,
    ThriftFelonaturelonRelonpositoryModulelon,
    TimelonlinelonMixelonrClielonntModulelon,
    TimelonlinelonRankelonrClielonntModulelon,
    TimelonlinelonScorelonrClielonntModulelon,
    TimelonlinelonSelonrvicelonClielonntModulelon,
    TimelonlinelonsPelonrsistelonncelonStorelonClielonntModulelon,
    TwelonelontImprelonssionStorelonModulelon,
    TwelonelontyPielonClielonntModulelon,
    TwelonelontypielonStaticelonntitielonsCachelonClielonntModulelon,
    UselonrMelontadataStorelonModulelon,
    UselonrSelonssionStorelonModulelon,
    nelonw DarkTrafficFiltelonrModulelon[st.HomelonMixelonr.RelonqRelonpSelonrvicelonPelonrelonndpoint](),
    nelonw MtlsThriftWelonbFormsModulelon[st.HomelonMixelonr.MelonthodPelonrelonndpoint](this),
    nelonw ProductScopelonStringCelonntelonrModulelon()
  )

  delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[StatsFiltelonr]
      .filtelonr[AccelonssLoggingFiltelonr]
      .filtelonr[elonxcelonptionMappingFiltelonr]
      .filtelonr[Filtelonr.TypelonAgnostic, DarkTrafficFiltelonrTypelon]
      .elonxcelonptionMappelonr[LoggingThrowablelonelonxcelonptionMappelonr]
      .elonxcelonptionMappelonr[PipelonlinelonFailurelonelonxcelonptionMappelonr]
      .add[HomelonThriftControllelonr]
  }

  ovelonrridelon delonf configurelonHttp(routelonr: HttpRoutelonr): Unit =
    routelonr.add(
      ProductMixelonrControllelonr[st.HomelonMixelonr.MelonthodPelonrelonndpoint](
        this.injelonctor,
        st.HomelonMixelonr.elonxeloncutelonPipelonlinelon))

  ovelonrridelon protelonctelond delonf warmup(): Unit = {
    handlelon[HomelonMixelonrThriftSelonrvelonrWarmupHandlelonr]()
    handlelon[HomelonMixelonrHttpSelonrvelonrWarmupHandlelonr]()
  }
}

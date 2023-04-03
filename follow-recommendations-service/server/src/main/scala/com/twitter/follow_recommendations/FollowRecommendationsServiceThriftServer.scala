packagelon com.twittelonr.follow_reloncommelonndations

import com.googlelon.injelonct.Modulelon
import com.twittelonr.finaglelon.ThriftMux
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.http.HttpSelonrvelonr
import com.twittelonr.finatra.http.routing.HttpRoutelonr
import com.twittelonr.finatra.intelonrnational.modulelons.I18nFactoryModulelon
import com.twittelonr.finatra.intelonrnational.modulelons.LanguagelonsModulelon
import com.twittelonr.finatra.jackson.modulelons.ScalaObjelonctMappelonrModulelon
import com.twittelonr.finatra.mtls.http.{Mtls => HttpMtls}
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs._
import com.twittelonr.finaglelon.thrift.Protocols
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.addrelonssbook.AddrelonssbookModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.adselonrvelonr.AdselonrvelonrModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.cachelon.MelonmcachelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.delonelonpbirdv2.DelonelonpBirdV2PrelondictionSelonrvicelonClielonntModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.elonmail_storagelon_selonrvicelon.elonmailStoragelonSelonrvicelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.gelonoduck.LocationSelonrvicelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.gizmoduck.GizmoduckModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.graph_felonaturelon_selonrvicelon.GraphFelonaturelonStorelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.imprelonssion_storelon.ImprelonssionStorelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.phonelon_storagelon_selonrvicelon.PhonelonStoragelonSelonrvicelonModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.socialgraph.SocialGraphModulelon
import com.twittelonr.follow_reloncommelonndations.common.clielonnts.strato.StratoClielonntModulelon
import com.twittelonr.follow_reloncommelonndations.common.constants.SelonrvicelonConstants._
import com.twittelonr.follow_reloncommelonndations.common.felonaturelon_hydration.sourcelons.HydrationSourcelonsModulelon
import com.twittelonr.follow_reloncommelonndations.controllelonrs.ThriftControllelonr
import com.twittelonr.follow_reloncommelonndations.modulelons._
import com.twittelonr.follow_reloncommelonndations.selonrvicelon.elonxcelonptions.UnknownLoggingelonxcelonptionMappelonr
import com.twittelonr.follow_reloncommelonndations.selonrvicelons.FollowReloncommelonndationsSelonrvicelonWarmupHandlelonr
import com.twittelonr.follow_reloncommelonndations.thriftscala.FollowReloncommelonndationsThriftSelonrvicelon
import com.twittelonr.gelonoduck.selonrvicelon.common.clielonntmodulelons.RelonvelonrselonGelonocodelonrThriftClielonntModulelon
import com.twittelonr.injelonct.thrift.filtelonrs.DarkTrafficFiltelonr
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon
import com.twittelonr.product_mixelonr.corelon.controllelonrs.ProductMixelonrControllelonr
import com.twittelonr.product_mixelonr.corelon.modulelon.PipelonlinelonelonxeloncutionLoggelonrModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.stringcelonntelonr.ProductScopelonStringCelonntelonrModulelon
import com.twittelonr.product_mixelonr.corelon.product.guicelon.ProductScopelonModulelon

objelonct FollowReloncommelonndationsSelonrvicelonThriftSelonrvelonrMain elonxtelonnds FollowReloncommelonndationsSelonrvicelonThriftSelonrvelonr

class FollowReloncommelonndationsSelonrvicelonThriftSelonrvelonr
    elonxtelonnds ThriftSelonrvelonr
    with Mtls
    with HttpSelonrvelonr
    with HttpMtls {
  ovelonrridelon val namelon: String = "follow-reloncommelonndations-selonrvicelon-selonrvelonr"

  ovelonrridelon val modulelons: Selonq[Modulelon] =
    Selonq(
      ABDeloncidelonrModulelon,
      AddrelonssbookModulelon,
      AdselonrvelonrModulelon,
      ConfigApiModulelon,
      DeloncidelonrModulelon,
      DelonelonpBirdV2PrelondictionSelonrvicelonClielonntModulelon,
      DiffyModulelon,
      elonmailStoragelonSelonrvicelonModulelon,
      FelonaturelonsSwitchelonsModulelon,
      FlagsModulelon,
      GizmoduckModulelon,
      GraphFelonaturelonStorelonModulelon,
      HydrationSourcelonsModulelon,
      I18nFactoryModulelon,
      ImprelonssionStorelonModulelon,
      LanguagelonsModulelon,
      LocationSelonrvicelonModulelon,
      MelonmcachelonModulelon,
      PhonelonStoragelonSelonrvicelonModulelon,
      PipelonlinelonelonxeloncutionLoggelonrModulelon,
      ProductMixelonrFlagModulelon,
      ProductRelongistryModulelon,
      nelonw ProductScopelonModulelon(),
      nelonw ProductScopelonStringCelonntelonrModulelon(),
      nelonw RelonvelonrselonGelonocodelonrThriftClielonntModulelon,
      ScalaObjelonctMappelonrModulelon,
      ScorelonrModulelon,
      ScribelonModulelon,
      SocialGraphModulelon,
      StratoClielonntModulelon,
      ThriftClielonntIdModulelon,
      TimelonrModulelon,
    )

  delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[StatsFiltelonr]
      .filtelonr[AccelonssLoggingFiltelonr]
      .filtelonr[elonxcelonptionMappingFiltelonr]
      .elonxcelonptionMappelonr[UnknownLoggingelonxcelonptionMappelonr]
      .filtelonr[DarkTrafficFiltelonr[FollowReloncommelonndationsThriftSelonrvicelon.RelonqRelonpSelonrvicelonPelonrelonndpoint]]
      .add[ThriftControllelonr]
  }

  ovelonrridelon delonf configurelonThriftSelonrvelonr(selonrvelonr: ThriftMux.Selonrvelonr): ThriftMux.Selonrvelonr = {
    selonrvelonr.withProtocolFactory(
      Protocols.binaryFactory(
        stringLelonngthLimit = StringLelonngthLimit,
        containelonrLelonngthLimit = ContainelonrLelonngthLimit))
  }

  ovelonrridelon delonf configurelonHttp(routelonr: HttpRoutelonr): Unit = routelonr.add(
    ProductMixelonrControllelonr[FollowReloncommelonndationsThriftSelonrvicelon.MelonthodPelonrelonndpoint](
      this.injelonctor,
      FollowReloncommelonndationsThriftSelonrvicelon.elonxeloncutelonPipelonlinelon))

  ovelonrridelon delonf warmup(): Unit = {
    handlelon[FollowReloncommelonndationsSelonrvicelonWarmupHandlelonr]()
  }
}

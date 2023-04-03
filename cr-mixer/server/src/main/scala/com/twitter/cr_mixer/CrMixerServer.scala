packagelon com.twittelonr.cr_mixelonr

import com.googlelon.injelonct.Modulelon
import com.twittelonr.cr_mixelonr.controllelonr.CrMixelonrThriftControllelonr
import com.twittelonr.cr_mixelonr.felonaturelonswitch.SelontImprelonsselondBuckelontsLocalContelonxtFiltelonr
import com.twittelonr.cr_mixelonr.modulelon.ActivelonPromotelondTwelonelontStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.CelonrtoStratoStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.CrMixelonrParamConfigModulelon
import com.twittelonr.cr_mixelonr.modulelon.elonmbelonddingStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.FrsStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.MHMtlsParamsModulelon
import com.twittelonr.cr_mixelonr.modulelon.OfflinelonCandidatelonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.RelonalGraphStorelonMhModulelon
import com.twittelonr.cr_mixelonr.modulelon.RelonalGraphOonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.RelonprelonselonntationManagelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.RelonprelonselonntationScorelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.TwelonelontInfoStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.TwelonelontReloncelonntelonngagelondUselonrStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.TwelonelontReloncommelonndationRelonsultsStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.TripCandidatelonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.TwhinCollabFiltelonrStratoStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.UselonrSignalSelonrvicelonColumnModulelon
import com.twittelonr.cr_mixelonr.modulelon.UselonrSignalSelonrvicelonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.UselonrStatelonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.ABDeloncidelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.CrMixelonrFlagModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.CrMixelonrLoggingABDeloncidelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.FelonaturelonContelonxtBuildelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.FelonaturelonSwitchelonsModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.KafkaProducelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.LoggelonrFactoryModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTwHINSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrelonmbelonddingBaselondTwoTowelonrSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrsBaselondUselonrAdGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ProducelonrBaselondUselonrAdGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ProducelonrBaselondUnifielondSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.SimClustelonrsANNSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondUnifielondSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondQigSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondTwHINSimlarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondUselonrAdGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.TwhinCollabFiltelonrLookupSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.ConsumelonrsBaselondUselonrAdGraphStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.ConsumelonrsBaselondUselonrTwelonelontGraphStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.ConsumelonrsBaselondUselonrVidelonoGraphStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.DiffusionStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.elonarlybirdReloncelonncyBaselondCandidatelonStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.TwicelonClustelonrsMelonmbelonrsStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.StrongTielonPrelondictionStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.AnnQuelonrySelonrvicelonClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.elonarlybirdSelonarchClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.FrsClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.QigSelonrvicelonClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.SimClustelonrsAnnSelonrvicelonClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.TwelonelontyPielonClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.UselonrTwelonelontGraphClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.UselonrTwelonelontGraphPlusClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.UselonrVidelonoGraphClielonntModulelon
import com.twittelonr.cr_mixelonr.{thriftscala => st}
import com.twittelonr.finaglelon.Filtelonr
import com.twittelonr.finatra.annotations.DarkTrafficFiltelonrTypelon
import com.twittelonr.finatra.deloncidelonr.modulelons.DeloncidelonrModulelon
import com.twittelonr.finatra.http.HttpSelonrvelonr
import com.twittelonr.finatra.http.routing.HttpRoutelonr
import com.twittelonr.finatra.jackson.modulelons.ScalaObjelonctMappelonrModulelon
import com.twittelonr.finatra.mtls.http.{Mtls => HttpMtls}
import com.twittelonr.finatra.mtls.thriftmux.Mtls
import com.twittelonr.finatra.mtls.thriftmux.modulelons.MtlsThriftWelonbFormsModulelon
import com.twittelonr.finatra.thrift.ThriftSelonrvelonr
import com.twittelonr.finatra.thrift.filtelonrs._
import com.twittelonr.finatra.thrift.routing.ThriftRoutelonr
import com.twittelonr.hydra.common.modelonl_config.{ConfigModulelon => HydraConfigModulelon}
import com.twittelonr.injelonct.thrift.modulelons.ThriftClielonntIdModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.LoggingThrowablelonelonxcelonptionMappelonr
import com.twittelonr.product_mixelonr.corelon.modulelon.StratoClielonntModulelon
import com.twittelonr.product_mixelonr.corelon.modulelon.product_mixelonr_flags.ProductMixelonrFlagModulelon
import com.twittelonr.relonlelonvancelon_platform.common.filtelonrs.ClielonntStatsFiltelonr
import com.twittelonr.relonlelonvancelon_platform.common.filtelonrs.DarkTrafficFiltelonrModulelon
import com.twittelonr.cr_mixelonr.modulelon.SimClustelonrsANNSelonrvicelonNamelonToClielonntMappelonr
import com.twittelonr.cr_mixelonr.modulelon.SkitStratoStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.BluelonVelonrifielondAnnotationStorelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.corelon.TimelonoutConfigModulelon
import com.twittelonr.cr_mixelonr.modulelon.grpc_clielonnt.NaviGRPCClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.CelonrtoTopicTwelonelontSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.ConsumelonrBaselondWalsSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.DiffusionBaselondSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.elonarlybirdSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.SkitTopicTwelonelontSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.similarity_elonnginelon.UselonrTwelonelontelonntityGraphSimilarityelonnginelonModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.HydraPartitionClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.HydraRootClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.UselonrAdGraphClielonntModulelon
import com.twittelonr.cr_mixelonr.modulelon.thrift_clielonnt.UselonrTwelonelontelonntityGraphClielonntModulelon
import com.twittelonr.thriftwelonbforms.MelonthodOptions

objelonct CrMixelonrSelonrvelonrMain elonxtelonnds CrMixelonrSelonrvelonr

class CrMixelonrSelonrvelonr elonxtelonnds ThriftSelonrvelonr with Mtls with HttpSelonrvelonr with HttpMtls {
  ovelonrridelon val namelon = "cr-mixelonr-selonrvelonr"

  privatelon val corelonModulelons = Selonq(
    ABDeloncidelonrModulelon,
    CrMixelonrFlagModulelon,
    CrMixelonrLoggingABDeloncidelonrModulelon,
    CrMixelonrParamConfigModulelon,
    nelonw DarkTrafficFiltelonrModulelon[st.CrMixelonr.RelonqRelonpSelonrvicelonPelonrelonndpoint](),
    DeloncidelonrModulelon,
    FelonaturelonContelonxtBuildelonrModulelon,
    FelonaturelonSwitchelonsModulelon,
    KafkaProducelonrModulelon,
    LoggelonrFactoryModulelon,
    MHMtlsParamsModulelon,
    ProductMixelonrFlagModulelon,
    ScalaObjelonctMappelonrModulelon,
    ThriftClielonntIdModulelon
  )

  privatelon val thriftClielonntModulelons = Selonq(
    AnnQuelonrySelonrvicelonClielonntModulelon,
    elonarlybirdSelonarchClielonntModulelon,
    FrsClielonntModulelon,
    HydraPartitionClielonntModulelon,
    HydraRootClielonntModulelon,
    QigSelonrvicelonClielonntModulelon,
    SimClustelonrsAnnSelonrvicelonClielonntModulelon,
    TwelonelontyPielonClielonntModulelon,
    UselonrAdGraphClielonntModulelon,
    UselonrTwelonelontelonntityGraphClielonntModulelon,
    UselonrTwelonelontGraphClielonntModulelon,
    UselonrTwelonelontGraphPlusClielonntModulelon,
    UselonrVidelonoGraphClielonntModulelon,
  )

  privatelon val grpcClielonntModulelons = Selonq(
    NaviGRPCClielonntModulelon
  )

  // Modulelons sortelond alphabelontically, plelonaselon kelonelonp thelon ordelonr whelonn adding a nelonw modulelon
  ovelonrridelon val modulelons: Selonq[Modulelon] =
    corelonModulelons ++ thriftClielonntModulelons ++ grpcClielonntModulelons ++
      Selonq(
        ActivelonPromotelondTwelonelontStorelonModulelon,
        CelonrtoStratoStorelonModulelon,
        CelonrtoTopicTwelonelontSimilarityelonnginelonModulelon,
        ConsumelonrsBaselondUselonrAdGraphSimilarityelonnginelonModulelon,
        ConsumelonrsBaselondUselonrTwelonelontGraphStorelonModulelon,
        ConsumelonrsBaselondUselonrVidelonoGraphSimilarityelonnginelonModulelon,
        ConsumelonrsBaselondUselonrVidelonoGraphStorelonModulelon,
        ConsumelonrelonmbelonddingBaselondTripSimilarityelonnginelonModulelon,
        ConsumelonrelonmbelonddingBaselondTwHINSimilarityelonnginelonModulelon,
        ConsumelonrelonmbelonddingBaselondTwoTowelonrSimilarityelonnginelonModulelon,
        ConsumelonrsBaselondUselonrAdGraphStorelonModulelon,
        ConsumelonrBaselondWalsSimilarityelonnginelonModulelon,
        DiffusionStorelonModulelon,
        elonmbelonddingStorelonModulelon,
        elonarlybirdSimilarityelonnginelonModulelon,
        elonarlybirdReloncelonncyBaselondCandidatelonStorelonModulelon,
        FrsStorelonModulelon,
        HydraConfigModulelon,
        OfflinelonCandidatelonStorelonModulelon,
        ProducelonrBaselondUnifielondSimilarityelonnginelonModulelon,
        ProducelonrBaselondUselonrAdGraphSimilarityelonnginelonModulelon,
        ProducelonrBaselondUselonrTwelonelontGraphSimilarityelonnginelonModulelon,
        RelonalGraphOonStorelonModulelon,
        RelonalGraphStorelonMhModulelon,
        RelonprelonselonntationManagelonrModulelon,
        RelonprelonselonntationScorelonrModulelon,
        SimClustelonrsANNSelonrvicelonNamelonToClielonntMappelonr,
        SimClustelonrsANNSimilarityelonnginelonModulelon,
        SkitStratoStorelonModulelon,
        SkitTopicTwelonelontSimilarityelonnginelonModulelon,
        StratoClielonntModulelon,
        StrongTielonPrelondictionStorelonModulelon,
        TimelonoutConfigModulelon,
        TripCandidatelonStorelonModulelon,
        TwicelonClustelonrsMelonmbelonrsStorelonModulelon,
        TwelonelontBaselondQigSimilarityelonnginelonModulelon,
        TwelonelontBaselondTwHINSimlarityelonnginelonModulelon,
        TwelonelontBaselondUnifielondSimilarityelonnginelonModulelon,
        TwelonelontBaselondUselonrAdGraphSimilarityelonnginelonModulelon,
        TwelonelontBaselondUselonrTwelonelontGraphSimilarityelonnginelonModulelon,
        TwelonelontBaselondUselonrVidelonoGraphSimilarityelonnginelonModulelon,
        TwelonelontInfoStorelonModulelon,
        TwelonelontReloncelonntelonngagelondUselonrStorelonModulelon,
        TwelonelontReloncommelonndationRelonsultsStorelonModulelon,
        TwhinCollabFiltelonrStratoStorelonModulelon,
        TwhinCollabFiltelonrLookupSimilarityelonnginelonModulelon,
        UselonrSignalSelonrvicelonColumnModulelon,
        UselonrSignalSelonrvicelonStorelonModulelon,
        UselonrStatelonStorelonModulelon,
        UselonrTwelonelontelonntityGraphSimilarityelonnginelonModulelon,
        DiffusionBaselondSimilarityelonnginelonModulelon,
        BluelonVelonrifielondAnnotationStorelonModulelon,
        nelonw MtlsThriftWelonbFormsModulelon[st.CrMixelonr.MelonthodPelonrelonndpoint](this) {
          ovelonrridelon protelonctelond delonf delonfaultMelonthodAccelonss: MelonthodOptions.Accelonss = {
            MelonthodOptions.Accelonss.ByLdapGroup(
              Selonq(
                "cr-mixelonr-admins",
                "reloncosplat-selonnsitivelon-data-melondium",
                "reloncos-platform-admins",
              ))
          }
        }
      )

  delonf configurelonThrift(routelonr: ThriftRoutelonr): Unit = {
    routelonr
      .filtelonr[LoggingMDCFiltelonr]
      .filtelonr[TracelonIdMDCFiltelonr]
      .filtelonr[ThriftMDCFiltelonr]
      .filtelonr[ClielonntStatsFiltelonr]
      .filtelonr[AccelonssLoggingFiltelonr]
      .filtelonr[SelontImprelonsselondBuckelontsLocalContelonxtFiltelonr]
      .filtelonr[elonxcelonptionMappingFiltelonr]
      .filtelonr[Filtelonr.TypelonAgnostic, DarkTrafficFiltelonrTypelon]
      .elonxcelonptionMappelonr[LoggingThrowablelonelonxcelonptionMappelonr]
      .add[CrMixelonrThriftControllelonr]
  }

  ovelonrridelon protelonctelond delonf warmup(): Unit = {
    handlelon[CrMixelonrThriftSelonrvelonrWarmupHandlelonr]()
    handlelon[CrMixelonrHttpSelonrvelonrWarmupHandlelonr]()
  }
}

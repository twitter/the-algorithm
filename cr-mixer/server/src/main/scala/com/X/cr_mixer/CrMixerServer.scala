package com.X.cr_mixer

import com.google.inject.Module
import com.X.cr_mixer.controller.CrMixerThriftController
import com.X.cr_mixer.featureswitch.SetImpressedBucketsLocalContextFilter
import com.X.cr_mixer.module.ActivePromotedTweetStoreModule
import com.X.cr_mixer.module.CertoStratoStoreModule
import com.X.cr_mixer.module.CrMixerParamConfigModule
import com.X.cr_mixer.module.EmbeddingStoreModule
import com.X.cr_mixer.module.FrsStoreModule
import com.X.cr_mixer.module.MHMtlsParamsModule
import com.X.cr_mixer.module.OfflineCandidateStoreModule
import com.X.cr_mixer.module.RealGraphStoreMhModule
import com.X.cr_mixer.module.RealGraphOonStoreModule
import com.X.cr_mixer.module.RepresentationManagerModule
import com.X.cr_mixer.module.RepresentationScorerModule
import com.X.cr_mixer.module.TweetInfoStoreModule
import com.X.cr_mixer.module.TweetRecentEngagedUserStoreModule
import com.X.cr_mixer.module.TweetRecommendationResultsStoreModule
import com.X.cr_mixer.module.TripCandidateStoreModule
import com.X.cr_mixer.module.TwhinCollabFilterStratoStoreModule
import com.X.cr_mixer.module.UserSignalServiceColumnModule
import com.X.cr_mixer.module.UserSignalServiceStoreModule
import com.X.cr_mixer.module.UserStateStoreModule
import com.X.cr_mixer.module.core.ABDeciderModule
import com.X.cr_mixer.module.core.CrMixerFlagModule
import com.X.cr_mixer.module.core.CrMixerLoggingABDeciderModule
import com.X.cr_mixer.module.core.FeatureContextBuilderModule
import com.X.cr_mixer.module.core.FeatureSwitchesModule
import com.X.cr_mixer.module.core.KafkaProducerModule
import com.X.cr_mixer.module.core.LoggerFactoryModule
import com.X.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwHINSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ProducerBasedUserAdGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ProducerBasedUnifiedSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.SimClustersANNSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedUnifiedSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedQigSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedTwHINSimlarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedUserAdGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedUserTweetGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TweetBasedUserVideoGraphSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.TwhinCollabFilterLookupSimilarityEngineModule
import com.X.cr_mixer.module.ConsumersBasedUserAdGraphStoreModule
import com.X.cr_mixer.module.ConsumersBasedUserTweetGraphStoreModule
import com.X.cr_mixer.module.ConsumersBasedUserVideoGraphStoreModule
import com.X.cr_mixer.module.DiffusionStoreModule
import com.X.cr_mixer.module.EarlybirdRecencyBasedCandidateStoreModule
import com.X.cr_mixer.module.TwiceClustersMembersStoreModule
import com.X.cr_mixer.module.StrongTiePredictionStoreModule
import com.X.cr_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.X.cr_mixer.module.thrift_client.EarlybirdSearchClientModule
import com.X.cr_mixer.module.thrift_client.FrsClientModule
import com.X.cr_mixer.module.thrift_client.QigServiceClientModule
import com.X.cr_mixer.module.thrift_client.SimClustersAnnServiceClientModule
import com.X.cr_mixer.module.thrift_client.TweetyPieClientModule
import com.X.cr_mixer.module.thrift_client.UserTweetGraphClientModule
import com.X.cr_mixer.module.thrift_client.UserTweetGraphPlusClientModule
import com.X.cr_mixer.module.thrift_client.UserVideoGraphClientModule
import com.X.cr_mixer.{thriftscala => st}
import com.X.finagle.Filter
import com.X.finatra.annotations.DarkTrafficFilterType
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.http.HttpServer
import com.X.finatra.http.routing.HttpRouter
import com.X.finatra.jackson.modules.ScalaObjectMapperModule
import com.X.finatra.mtls.http.{Mtls => HttpMtls}
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters._
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.hydra.common.model_config.{ConfigModule => HydraConfigModule}
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.X.product_mixer.core.module.StratoClientModule
import com.X.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.X.relevance_platform.common.filters.ClientStatsFilter
import com.X.relevance_platform.common.filters.DarkTrafficFilterModule
import com.X.cr_mixer.module.SimClustersANNServiceNameToClientMapper
import com.X.cr_mixer.module.SkitStratoStoreModule
import com.X.cr_mixer.module.BlueVerifiedAnnotationStoreModule
import com.X.cr_mixer.module.core.TimeoutConfigModule
import com.X.cr_mixer.module.grpc_client.NaviGRPCClientModule
import com.X.cr_mixer.module.similarity_engine.CertoTopicTweetSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.ConsumerBasedWalsSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.DiffusionBasedSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.EarlybirdSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.SkitTopicTweetSimilarityEngineModule
import com.X.cr_mixer.module.similarity_engine.UserTweetEntityGraphSimilarityEngineModule
import com.X.cr_mixer.module.thrift_client.HydraPartitionClientModule
import com.X.cr_mixer.module.thrift_client.HydraRootClientModule
import com.X.cr_mixer.module.thrift_client.UserAdGraphClientModule
import com.X.cr_mixer.module.thrift_client.UserTweetEntityGraphClientModule
import com.X.thriftwebforms.MethodOptions

object CrMixerServerMain extends CrMixerServer

class CrMixerServer extends ThriftServer with Mtls with HttpServer with HttpMtls {
  override val name = "cr-mixer-server"

  private val coreModules = Seq(
    ABDeciderModule,
    CrMixerFlagModule,
    CrMixerLoggingABDeciderModule,
    CrMixerParamConfigModule,
    new DarkTrafficFilterModule[st.CrMixer.ReqRepServicePerEndpoint](),
    DeciderModule,
    FeatureContextBuilderModule,
    FeatureSwitchesModule,
    KafkaProducerModule,
    LoggerFactoryModule,
    MHMtlsParamsModule,
    ProductMixerFlagModule,
    ScalaObjectMapperModule,
    ThriftClientIdModule
  )

  private val thriftClientModules = Seq(
    AnnQueryServiceClientModule,
    EarlybirdSearchClientModule,
    FrsClientModule,
    HydraPartitionClientModule,
    HydraRootClientModule,
    QigServiceClientModule,
    SimClustersAnnServiceClientModule,
    TweetyPieClientModule,
    UserAdGraphClientModule,
    UserTweetEntityGraphClientModule,
    UserTweetGraphClientModule,
    UserTweetGraphPlusClientModule,
    UserVideoGraphClientModule,
  )

  private val grpcClientModules = Seq(
    NaviGRPCClientModule
  )

  // Modules sorted alphabetically, please keep the order when adding a new module
  override val modules: Seq[Module] =
    coreModules ++ thriftClientModules ++ grpcClientModules ++
      Seq(
        ActivePromotedTweetStoreModule,
        CertoStratoStoreModule,
        CertoTopicTweetSimilarityEngineModule,
        ConsumersBasedUserAdGraphSimilarityEngineModule,
        ConsumersBasedUserTweetGraphStoreModule,
        ConsumersBasedUserVideoGraphSimilarityEngineModule,
        ConsumersBasedUserVideoGraphStoreModule,
        ConsumerEmbeddingBasedTripSimilarityEngineModule,
        ConsumerEmbeddingBasedTwHINSimilarityEngineModule,
        ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule,
        ConsumersBasedUserAdGraphStoreModule,
        ConsumerBasedWalsSimilarityEngineModule,
        DiffusionStoreModule,
        EmbeddingStoreModule,
        EarlybirdSimilarityEngineModule,
        EarlybirdRecencyBasedCandidateStoreModule,
        FrsStoreModule,
        HydraConfigModule,
        OfflineCandidateStoreModule,
        ProducerBasedUnifiedSimilarityEngineModule,
        ProducerBasedUserAdGraphSimilarityEngineModule,
        ProducerBasedUserTweetGraphSimilarityEngineModule,
        RealGraphOonStoreModule,
        RealGraphStoreMhModule,
        RepresentationManagerModule,
        RepresentationScorerModule,
        SimClustersANNServiceNameToClientMapper,
        SimClustersANNSimilarityEngineModule,
        SkitStratoStoreModule,
        SkitTopicTweetSimilarityEngineModule,
        StratoClientModule,
        StrongTiePredictionStoreModule,
        TimeoutConfigModule,
        TripCandidateStoreModule,
        TwiceClustersMembersStoreModule,
        TweetBasedQigSimilarityEngineModule,
        TweetBasedTwHINSimlarityEngineModule,
        TweetBasedUnifiedSimilarityEngineModule,
        TweetBasedUserAdGraphSimilarityEngineModule,
        TweetBasedUserTweetGraphSimilarityEngineModule,
        TweetBasedUserVideoGraphSimilarityEngineModule,
        TweetInfoStoreModule,
        TweetRecentEngagedUserStoreModule,
        TweetRecommendationResultsStoreModule,
        TwhinCollabFilterStratoStoreModule,
        TwhinCollabFilterLookupSimilarityEngineModule,
        UserSignalServiceColumnModule,
        UserSignalServiceStoreModule,
        UserStateStoreModule,
        UserTweetEntityGraphSimilarityEngineModule,
        DiffusionBasedSimilarityEngineModule,
        BlueVerifiedAnnotationStoreModule,
        new MtlsThriftWebFormsModule[st.CrMixer.MethodPerEndpoint](this) {
          override protected def defaultMethodAccess: MethodOptions.Access = {
            MethodOptions.Access.ByLdapGroup(
              Seq(
                "cr-mixer-admins",
                "recosplat-sensitive-data-medium",
                "recos-platform-admins",
              ))
          }
        }
      )

  def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[ClientStatsFilter]
      .filter[AccessLoggingFilter]
      .filter[SetImpressedBucketsLocalContextFilter]
      .filter[ExceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .exceptionMapper[LoggingThrowableExceptionMapper]
      .add[CrMixerThriftController]
  }

  override protected def warmup(): Unit = {
    handle[CrMixerThriftServerWarmupHandler]()
    handle[CrMixerHttpServerWarmupHandler]()
  }
}

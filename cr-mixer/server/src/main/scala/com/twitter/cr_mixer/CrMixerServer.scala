package com.ExTwitter.cr_mixer

import com.google.inject.Module
import com.ExTwitter.cr_mixer.controller.CrMixerThriftController
import com.ExTwitter.cr_mixer.featureswitch.SetImpressedBucketsLocalContextFilter
import com.ExTwitter.cr_mixer.module.ActivePromotedTweetStoreModule
import com.ExTwitter.cr_mixer.module.CertoStratoStoreModule
import com.ExTwitter.cr_mixer.module.CrMixerParamConfigModule
import com.ExTwitter.cr_mixer.module.EmbeddingStoreModule
import com.ExTwitter.cr_mixer.module.FrsStoreModule
import com.ExTwitter.cr_mixer.module.MHMtlsParamsModule
import com.ExTwitter.cr_mixer.module.OfflineCandidateStoreModule
import com.ExTwitter.cr_mixer.module.RealGraphStoreMhModule
import com.ExTwitter.cr_mixer.module.RealGraphOonStoreModule
import com.ExTwitter.cr_mixer.module.RepresentationManagerModule
import com.ExTwitter.cr_mixer.module.RepresentationScorerModule
import com.ExTwitter.cr_mixer.module.TweetInfoStoreModule
import com.ExTwitter.cr_mixer.module.TweetRecentEngagedUserStoreModule
import com.ExTwitter.cr_mixer.module.TweetRecommendationResultsStoreModule
import com.ExTwitter.cr_mixer.module.TripCandidateStoreModule
import com.ExTwitter.cr_mixer.module.TwhinCollabFilterStratoStoreModule
import com.ExTwitter.cr_mixer.module.UserSignalServiceColumnModule
import com.ExTwitter.cr_mixer.module.UserSignalServiceStoreModule
import com.ExTwitter.cr_mixer.module.UserStateStoreModule
import com.ExTwitter.cr_mixer.module.core.ABDeciderModule
import com.ExTwitter.cr_mixer.module.core.CrMixerFlagModule
import com.ExTwitter.cr_mixer.module.core.CrMixerLoggingABDeciderModule
import com.ExTwitter.cr_mixer.module.core.FeatureContextBuilderModule
import com.ExTwitter.cr_mixer.module.core.FeatureSwitchesModule
import com.ExTwitter.cr_mixer.module.core.KafkaProducerModule
import com.ExTwitter.cr_mixer.module.core.LoggerFactoryModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwHINSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ProducerBasedUserAdGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ProducerBasedUnifiedSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.SimClustersANNSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedUnifiedSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedQigSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedTwHINSimlarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedUserAdGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedUserTweetGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TweetBasedUserVideoGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.TwhinCollabFilterLookupSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.ConsumersBasedUserAdGraphStoreModule
import com.ExTwitter.cr_mixer.module.ConsumersBasedUserTweetGraphStoreModule
import com.ExTwitter.cr_mixer.module.ConsumersBasedUserVideoGraphStoreModule
import com.ExTwitter.cr_mixer.module.DiffusionStoreModule
import com.ExTwitter.cr_mixer.module.EarlybirdRecencyBasedCandidateStoreModule
import com.ExTwitter.cr_mixer.module.TwiceClustersMembersStoreModule
import com.ExTwitter.cr_mixer.module.StrongTiePredictionStoreModule
import com.ExTwitter.cr_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.EarlybirdSearchClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.FrsClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.QigServiceClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.SimClustersAnnServiceClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.TweetyPieClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.UserTweetGraphClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.UserTweetGraphPlusClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.UserVideoGraphClientModule
import com.ExTwitter.cr_mixer.{thriftscala => st}
import com.ExTwitter.finagle.Filter
import com.ExTwitter.finatra.annotations.DarkTrafficFilterType
import com.ExTwitter.finatra.decider.modules.DeciderModule
import com.ExTwitter.finatra.http.HttpServer
import com.ExTwitter.finatra.http.routing.HttpRouter
import com.ExTwitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.ExTwitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.ExTwitter.finatra.mtls.thriftmux.Mtls
import com.ExTwitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.ExTwitter.finatra.thrift.ThriftServer
import com.ExTwitter.finatra.thrift.filters._
import com.ExTwitter.finatra.thrift.routing.ThriftRouter
import com.ExTwitter.hydra.common.model_config.{ConfigModule => HydraConfigModule}
import com.ExTwitter.inject.thrift.modules.ThriftClientIdModule
import com.ExTwitter.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.ExTwitter.product_mixer.core.module.StratoClientModule
import com.ExTwitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.ExTwitter.relevance_platform.common.filters.ClientStatsFilter
import com.ExTwitter.relevance_platform.common.filters.DarkTrafficFilterModule
import com.ExTwitter.cr_mixer.module.SimClustersANNServiceNameToClientMapper
import com.ExTwitter.cr_mixer.module.SkitStratoStoreModule
import com.ExTwitter.cr_mixer.module.BlueVerifiedAnnotationStoreModule
import com.ExTwitter.cr_mixer.module.core.TimeoutConfigModule
import com.ExTwitter.cr_mixer.module.grpc_client.NaviGRPCClientModule
import com.ExTwitter.cr_mixer.module.similarity_engine.CertoTopicTweetSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.ConsumerBasedWalsSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.DiffusionBasedSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.EarlybirdSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.SkitTopicTweetSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.similarity_engine.UserTweetEntityGraphSimilarityEngineModule
import com.ExTwitter.cr_mixer.module.thrift_client.HydraPartitionClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.HydraRootClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.UserAdGraphClientModule
import com.ExTwitter.cr_mixer.module.thrift_client.UserTweetEntityGraphClientModule
import com.ExTwitter.thriftwebforms.MethodOptions

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

package com.twitter.cr_mixer

import com.google.inject.Module
import com.twitter.cr_mixer.controller.CrMixerThriftController
import com.twitter.cr_mixer.featureswitch.SetImpressedBucketsLocalContextFilter
import com.twitter.cr_mixer.module.ActivePromotedTweetStoreModule
import com.twitter.cr_mixer.module.CertoStratoStoreModule
import com.twitter.cr_mixer.module.CrMixerParamConfigModule
import com.twitter.cr_mixer.module.EmbeddingStoreModule
import com.twitter.cr_mixer.module.FrsStoreModule
import com.twitter.cr_mixer.module.MHMtlsParamsModule
import com.twitter.cr_mixer.module.OfflineCandidateStoreModule
import com.twitter.cr_mixer.module.RealGraphStoreMhModule
import com.twitter.cr_mixer.module.RealGraphOonStoreModule
import com.twitter.cr_mixer.module.RepresentationManagerModule
import com.twitter.cr_mixer.module.RepresentationScorerModule
import com.twitter.cr_mixer.module.TweetInfoStoreModule
import com.twitter.cr_mixer.module.TweetRecentEngagedUserStoreModule
import com.twitter.cr_mixer.module.TweetRecommendationResultsStoreModule
import com.twitter.cr_mixer.module.TripCandidateStoreModule
import com.twitter.cr_mixer.module.TwhinCollabFilterStratoStoreModule
import com.twitter.cr_mixer.module.UserSignalServiceColumnModule
import com.twitter.cr_mixer.module.UserSignalServiceStoreModule
import com.twitter.cr_mixer.module.UserStateStoreModule
import com.twitter.cr_mixer.module.core.ABDeciderModule
import com.twitter.cr_mixer.module.core.CrMixerFlagModule
import com.twitter.cr_mixer.module.core.CrMixerLoggingABDeciderModule
import com.twitter.cr_mixer.module.core.FeatureContextBuilderModule
import com.twitter.cr_mixer.module.core.FeatureSwitchesModule
import com.twitter.cr_mixer.module.core.KafkaProducerModule
import com.twitter.cr_mixer.module.core.LoggerFactoryModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwHINSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ProducerBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ProducerBasedUnifiedSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.SimClustersANNSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedUnifiedSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedQigSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedTwHINSimlarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedUserTweetGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TweetBasedUserVideoGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.TwhinCollabFilterLookupSimilarityEngineModule
import com.twitter.cr_mixer.module.ConsumersBasedUserAdGraphStoreModule
import com.twitter.cr_mixer.module.ConsumersBasedUserTweetGraphStoreModule
import com.twitter.cr_mixer.module.ConsumersBasedUserVideoGraphStoreModule
import com.twitter.cr_mixer.module.DiffusionStoreModule
import com.twitter.cr_mixer.module.EarlybirdRecencyBasedCandidateStoreModule
import com.twitter.cr_mixer.module.TwiceClustersMembersStoreModule
import com.twitter.cr_mixer.module.StrongTiePredictionStoreModule
import com.twitter.cr_mixer.module.thrift_client.AnnQueryServiceClientModule
import com.twitter.cr_mixer.module.thrift_client.EarlybirdSearchClientModule
import com.twitter.cr_mixer.module.thrift_client.FrsClientModule
import com.twitter.cr_mixer.module.thrift_client.QigServiceClientModule
import com.twitter.cr_mixer.module.thrift_client.SimClustersAnnServiceClientModule
import com.twitter.cr_mixer.module.thrift_client.TweetyPieClientModule
import com.twitter.cr_mixer.module.thrift_client.UserTweetGraphClientModule
import com.twitter.cr_mixer.module.thrift_client.UserTweetGraphPlusClientModule
import com.twitter.cr_mixer.module.thrift_client.UserVideoGraphClientModule
import com.twitter.cr_mixer.{thriftscala => st}
import com.twitter.finagle.Filter
import com.twitter.finatra.annotations.DarkTrafficFilterType
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmux.Mtls
import com.twitter.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.hydra.common.model_config.{ConfigModule => HydraConfigModule}
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.product_mixer.core.module.LoggingThrowableExceptionMapper
import com.twitter.product_mixer.core.module.StratoClientModule
import com.twitter.product_mixer.core.module.product_mixer_flags.ProductMixerFlagModule
import com.twitter.relevance_platform.common.filters.ClientStatsFilter
import com.twitter.relevance_platform.common.filters.DarkTrafficFilterModule
import com.twitter.cr_mixer.module.SimClustersANNServiceNameToClientMapper
import com.twitter.cr_mixer.module.SkitStratoStoreModule
import com.twitter.cr_mixer.module.BlueVerifiedAnnotationStoreModule
import com.twitter.cr_mixer.module.core.TimeoutConfigModule
import com.twitter.cr_mixer.module.grpc_client.NaviGRPCClientModule
import com.twitter.cr_mixer.module.similarity_engine.CertoTopicTweetSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.ConsumerBasedWalsSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.DiffusionBasedSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.EarlybirdSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.SkitTopicTweetSimilarityEngineModule
import com.twitter.cr_mixer.module.similarity_engine.UserTweetEntityGraphSimilarityEngineModule
import com.twitter.cr_mixer.module.thrift_client.HydraPartitionClientModule
import com.twitter.cr_mixer.module.thrift_client.HydraRootClientModule
import com.twitter.cr_mixer.module.thrift_client.UserAdGraphClientModule
import com.twitter.cr_mixer.module.thrift_client.UserTweetEntityGraphClientModule
import com.twitter.thriftwebforms.MethodOptions

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

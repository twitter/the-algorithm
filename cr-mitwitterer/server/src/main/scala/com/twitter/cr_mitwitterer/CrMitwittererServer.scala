package com.twitter.cr_mitwitterer

import com.google.inject.Module
import com.twitter.cr_mitwitterer.controller.CrMitwittererThriftController
import com.twitter.cr_mitwitterer.featureswitch.SetImpressedBucketsLocalContetwittertFilter
import com.twitter.cr_mitwitterer.module.ActivePromotedTweetStoreModule
import com.twitter.cr_mitwitterer.module.CertoStratoStoreModule
import com.twitter.cr_mitwitterer.module.CrMitwittererParamConfigModule
import com.twitter.cr_mitwitterer.module.EmbeddingStoreModule
import com.twitter.cr_mitwitterer.module.FrsStoreModule
import com.twitter.cr_mitwitterer.module.MHMtlsParamsModule
import com.twitter.cr_mitwitterer.module.OfflineCandidateStoreModule
import com.twitter.cr_mitwitterer.module.RealGraphStoreMhModule
import com.twitter.cr_mitwitterer.module.RealGraphOonStoreModule
import com.twitter.cr_mitwitterer.module.RepresentationManagerModule
import com.twitter.cr_mitwitterer.module.RepresentationScorerModule
import com.twitter.cr_mitwitterer.module.TweetInfoStoreModule
import com.twitter.cr_mitwitterer.module.TweetRecentEngagedUserStoreModule
import com.twitter.cr_mitwitterer.module.TweetRecommendationResultsStoreModule
import com.twitter.cr_mitwitterer.module.TripCandidateStoreModule
import com.twitter.cr_mitwitterer.module.TwhinCollabFilterStratoStoreModule
import com.twitter.cr_mitwitterer.module.UserSignalServiceColumnModule
import com.twitter.cr_mitwitterer.module.UserSignalServiceStoreModule
import com.twitter.cr_mitwitterer.module.UserStateStoreModule
import com.twitter.cr_mitwitterer.module.core.ABDeciderModule
import com.twitter.cr_mitwitterer.module.core.CrMitwittererFlagModule
import com.twitter.cr_mitwitterer.module.core.CrMitwittererLoggingABDeciderModule
import com.twitter.cr_mitwitterer.module.core.FeatureContetwittertBuilderModule
import com.twitter.cr_mitwitterer.module.core.FeatureSwitchesModule
import com.twitter.cr_mitwitterer.module.core.KafkaProducerModule
import com.twitter.cr_mitwitterer.module.core.LoggerFactoryModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumerEmbeddingBasedTripSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumerEmbeddingBasedTwHINSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumerEmbeddingBasedTwoTowerSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumersBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumersBasedUserVideoGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ProducerBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ProducerBasedUserTweetGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ProducerBasedUnifiedSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.SimClustersANNSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedUnifiedSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedQigSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedTwHINSimlarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedUserAdGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedUserTweetGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TweetBasedUserVideoGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.TwhinCollabFilterLookupSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.ConsumersBasedUserAdGraphStoreModule
import com.twitter.cr_mitwitterer.module.ConsumersBasedUserTweetGraphStoreModule
import com.twitter.cr_mitwitterer.module.ConsumersBasedUserVideoGraphStoreModule
import com.twitter.cr_mitwitterer.module.DiffusionStoreModule
import com.twitter.cr_mitwitterer.module.EarlybirdRecencyBasedCandidateStoreModule
import com.twitter.cr_mitwitterer.module.TwiceClustersMembersStoreModule
import com.twitter.cr_mitwitterer.module.StrongTiePredictionStoreModule
import com.twitter.cr_mitwitterer.module.thrift_client.AnnQueryServiceClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.EarlybirdSearchClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.FrsClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.QigServiceClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.SimClustersAnnServiceClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.TweetyPieClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.UserTweetGraphClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.UserTweetGraphPlusClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.UserVideoGraphClientModule
import com.twitter.cr_mitwitterer.{thriftscala => st}
import com.twitter.finagle.Filter
import com.twitter.finatra.annotations.DarkTrafficFilterType
import com.twitter.finatra.decider.modules.DeciderModule
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.jackson.modules.ScalaObjectMapperModule
import com.twitter.finatra.mtls.http.{Mtls => HttpMtls}
import com.twitter.finatra.mtls.thriftmutwitter.Mtls
import com.twitter.finatra.mtls.thriftmutwitter.modules.MtlsThriftWebFormsModule
import com.twitter.finatra.thrift.ThriftServer
import com.twitter.finatra.thrift.filters._
import com.twitter.finatra.thrift.routing.ThriftRouter
import com.twitter.hydra.common.model_config.{ConfigModule => HydraConfigModule}
import com.twitter.inject.thrift.modules.ThriftClientIdModule
import com.twitter.product_mitwitterer.core.module.LoggingThrowableEtwitterceptionMapper
import com.twitter.product_mitwitterer.core.module.StratoClientModule
import com.twitter.product_mitwitterer.core.module.product_mitwitterer_flags.ProductMitwittererFlagModule
import com.twitter.relevance_platform.common.filters.ClientStatsFilter
import com.twitter.relevance_platform.common.filters.DarkTrafficFilterModule
import com.twitter.cr_mitwitterer.module.SimClustersANNServiceNameToClientMapper
import com.twitter.cr_mitwitterer.module.SkitStratoStoreModule
import com.twitter.cr_mitwitterer.module.BlueVerifiedAnnotationStoreModule
import com.twitter.cr_mitwitterer.module.core.TimeoutConfigModule
import com.twitter.cr_mitwitterer.module.grpc_client.NaviGRPCClientModule
import com.twitter.cr_mitwitterer.module.similarity_engine.CertoTopicTweetSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.ConsumerBasedWalsSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.DiffusionBasedSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.EarlybirdSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.SkitTopicTweetSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.similarity_engine.UserTweetEntityGraphSimilarityEngineModule
import com.twitter.cr_mitwitterer.module.thrift_client.HydraPartitionClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.HydraRootClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.UserAdGraphClientModule
import com.twitter.cr_mitwitterer.module.thrift_client.UserTweetEntityGraphClientModule
import com.twitter.thriftwebforms.MethodOptions

object CrMitwittererServerMain etwittertends CrMitwittererServer

class CrMitwittererServer etwittertends ThriftServer with Mtls with HttpServer with HttpMtls {
  override val name = "cr-mitwitterer-server"

  private val coreModules = Seq(
    ABDeciderModule,
    CrMitwittererFlagModule,
    CrMitwittererLoggingABDeciderModule,
    CrMitwittererParamConfigModule,
    new DarkTrafficFilterModule[st.CrMitwitterer.ReqRepServicePerEndpoint](),
    DeciderModule,
    FeatureContetwittertBuilderModule,
    FeatureSwitchesModule,
    KafkaProducerModule,
    LoggerFactoryModule,
    MHMtlsParamsModule,
    ProductMitwittererFlagModule,
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
        new MtlsThriftWebFormsModule[st.CrMitwitterer.MethodPerEndpoint](this) {
          override protected def defaultMethodAccess: MethodOptions.Access = {
            MethodOptions.Access.ByLdapGroup(
              Seq(
                "cr-mitwitterer-admins",
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
      .filter[SetImpressedBucketsLocalContetwittertFilter]
      .filter[EtwitterceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .etwitterceptionMapper[LoggingThrowableEtwitterceptionMapper]
      .add[CrMitwittererThriftController]
  }

  override protected def warmup(): Unit = {
    handle[CrMitwittererThriftServerWarmupHandler]()
    handle[CrMitwittererHttpServerWarmupHandler]()
  }
}

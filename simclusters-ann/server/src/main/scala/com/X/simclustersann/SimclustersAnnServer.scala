package com.X.simclustersann

import com.google.inject.Module
import com.X.finatra.decider.modules.DeciderModule
import com.X.finatra.mtls.thriftmux.Mtls
import com.X.finatra.thrift.ThriftServer
import com.X.finatra.thrift.filters._
import com.X.finatra.thrift.routing.ThriftRouter
import com.X.inject.thrift.modules.ThriftClientIdModule
import com.X.relevance_platform.common.exceptions._
import com.X.simclustersann.controllers.SimClustersANNController
import com.X.simclustersann.exceptions.InvalidRequestForSimClustersAnnVariantExceptionMapper
import com.X.simclustersann.modules._
import com.X.simclustersann.thriftscala.SimClustersANNService
import com.X.finagle.Filter
import com.X.finatra.annotations.DarkTrafficFilterType
import com.X.inject.annotations.Flags
import com.X.relevance_platform.common.filters.DarkTrafficFilterModule
import com.X.relevance_platform.common.filters.ClientStatsFilter
import com.X.simclustersann.common.FlagNames.DisableWarmup

object SimClustersAnnServerMain extends SimClustersAnnServer

class SimClustersAnnServer extends ThriftServer with Mtls {
  flag(
    name = DisableWarmup,
    default = false,
    help = "If true, no warmup will be run."
  )

  override val name = "simclusters-ann-server"

  override val modules: Seq[Module] = Seq(
    CacheModule,
    ServiceNameMapperModule,
    ClusterConfigMapperModule,
    ClusterConfigModule,
    ClusterTweetIndexProviderModule,
    DeciderModule,
    EmbeddingStoreModule,
    FlagsModule,
    FuturePoolProvider,
    RateLimiterModule,
    SimClustersANNCandidateSourceModule,
    StratoClientProviderModule,
    ThriftClientIdModule,
    new CustomMtlsThriftWebFormsModule[SimClustersANNService.MethodPerEndpoint](this),
    new DarkTrafficFilterModule[SimClustersANNService.ReqRepServicePerEndpoint]()
  )

  def configureThrift(router: ThriftRouter): Unit = {
    router
      .filter[LoggingMDCFilter]
      .filter[TraceIdMDCFilter]
      .filter[ThriftMDCFilter]
      .filter[ClientStatsFilter]
      .filter[ExceptionMappingFilter]
      .filter[Filter.TypeAgnostic, DarkTrafficFilterType]
      .exceptionMapper[InvalidRequestForSimClustersAnnVariantExceptionMapper]
      .exceptionMapper[DeadlineExceededExceptionMapper]
      .exceptionMapper[UnhandledExceptionMapper]
      .add[SimClustersANNController]
  }

  override protected def warmup(): Unit = {
    if (!injector.instance[Boolean](Flags.named(DisableWarmup))) {
      handle[SimclustersAnnWarmupHandler]()
    }
  }
}

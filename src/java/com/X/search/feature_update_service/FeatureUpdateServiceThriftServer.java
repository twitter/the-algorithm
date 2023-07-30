package com.X.search.feature_update_service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.inject.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.X.app.Flag;
import com.X.app.Flaggable;
import com.X.finagle.Filter;
import com.X.finagle.Service;
import com.X.finagle.ThriftMux;
import com.X.finatra.annotations.DarkTrafficFilterType;
import com.X.finatra.decider.modules.DeciderModule$;
import com.X.finatra.mtls.thriftmux.modules.MtlsThriftWebFormsModule;
import com.X.finatra.mtls.thriftmux.AbstractMtlsThriftServer;
import com.X.finatra.thrift.filters.AccessLoggingFilter;
import com.X.finatra.thrift.filters.LoggingMDCFilter;
import com.X.finatra.thrift.filters.StatsFilter;
import com.X.finatra.thrift.filters.ThriftMDCFilter;
import com.X.finatra.thrift.filters.TraceIdMDCFilter;
import com.X.finatra.thrift.routing.JavaThriftRouter;
import com.X.inject.thrift.modules.ThriftClientIdModule$;
import com.X.search.common.constants.SearchThriftWebFormsAccess;
import com.X.search.common.metrics.BuildInfoStats;
import com.X.search.common.util.PlatformStatsExporter;
import com.X.search.feature_update_service.filters.ClientIdWhitelistFilter;
import com.X.search.feature_update_service.modules.ClientIdWhitelistModule;
import com.X.search.feature_update_service.modules.EarlybirdUtilModule;
import com.X.search.feature_update_service.modules.FeatureUpdateServiceDiffyModule;
import com.X.search.feature_update_service.modules.FinagleKafkaProducerModule;
import com.X.search.feature_update_service.modules.FuturePoolModule;
import com.X.search.feature_update_service.modules.TweetypieModule;
import com.X.search.feature_update_service.thriftjava.FeatureUpdateService;
import com.X.thriftwebforms.MethodOptionsAccessConfig;
import com.X.util.ExecutorServiceFuturePool;

public class FeatureUpdateServiceThriftServer extends AbstractMtlsThriftServer {
  private static final Logger LOG =
      LoggerFactory.getLogger(FeatureUpdateServiceThriftServer.class);

  // Ideally we would not have to access the "environment" flag here and we could instead pass
  // a flag to the ThriftWebFormsModule that would either enable or disable thrift web forms.
  // However, it is not simple to create our own XModule that both extends the
  // ThriftWebFormsModule and consumes an injected flag.
  private Flag<String> envFlag = flag().create("environment",
      "",
      "Environment for service (prod, staging, staging1, devel)",
      Flaggable.ofString());

  FeatureUpdateServiceThriftServer(String[] args) {
    BuildInfoStats.export();
    PlatformStatsExporter.exportPlatformStats();

    flag().parseArgs(args, true);
  }

  @Override
  @SuppressWarnings("unchecked")
  public Collection<Module> javaModules() {
    List<Module> modules = new ArrayList<>();
    modules.addAll(Arrays.asList(
        ThriftClientIdModule$.MODULE$,
        DeciderModule$.MODULE$,
        new ClientIdWhitelistModule(),
        new FinagleKafkaProducerModule(),
        new EarlybirdUtilModule(),
        new FuturePoolModule(),
        new FeatureUpdateServiceDiffyModule(),
        new TweetypieModule()));

    // Only add the Thrift Web Forms module for non-prod services because we should
    // not allow write access to production data through Thrift Web Forms.
    String environment = envFlag.apply();
    if ("prod".equals(environment)) {
      LOG.info("Not including Thrift Web Forms because the environment is prod");
    } else {
      LOG.info("Including Thrift Web Forms because the environment is " + environment);
      modules.add(
        MtlsThriftWebFormsModule.create(
          this,
          FeatureUpdateService.ServiceIface.class,
          MethodOptionsAccessConfig.byLdapGroup(SearchThriftWebFormsAccess.WRITE_LDAP_GROUP)
        )
      );
    }

    return modules;
  }

  @Override
  public void configureThrift(JavaThriftRouter router) {
    router
        // Initialize Mapped Diagnostic Context (MDC) for logging
        // (see https://logback.qos.ch/manual/mdc.html)
        .filter(LoggingMDCFilter.class)
        // Inject trace ID in MDC for logging
        .filter(TraceIdMDCFilter.class)
        // Inject request method and client ID in MDC for logging
        .filter(ThriftMDCFilter.class)
        // Log client access
        .filter(AccessLoggingFilter.class)
        // Export basic service stats
        .filter(StatsFilter.class)
        .filter(ClientIdWhitelistFilter.class)
        .add(FeatureUpdateController.class);
  }

  @Override
  public Service<byte[], byte[]> configureService(Service<byte[], byte[]> service) {
    // Add the DarkTrafficFilter in "front" of the service being served.
    return injector()
        .instance(Filter.TypeAgnostic.class, DarkTrafficFilterType.class)
        .andThen(service);
  }

  @Override
  public ThriftMux.Server configureThriftServer(ThriftMux.Server server) {
    // This cast looks redundant, but it is required for pants to compile this file.
    return (ThriftMux.Server) server.withResponseClassifier(new FeatureUpdateResponseClassifier());
  }

  @Override
  public void postWarmup() {
    super.postWarmup();

    ExecutorServiceFuturePool futurePool = injector().instance(ExecutorServiceFuturePool.class);
    Preconditions.checkNotNull(futurePool);

    onExit(() -> {
      try {
        futurePool.executor().shutdownNow();

        futurePool.executor().awaitTermination(10L, TimeUnit.SECONDS);
      } catch (InterruptedException e) {
        LOG.error("Interrupted while awaiting future pool termination", e);
      }

      return null;
    });
  }
}

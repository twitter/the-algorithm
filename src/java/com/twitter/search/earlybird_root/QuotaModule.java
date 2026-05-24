package com.twitter.search.earlybird_root;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Singleton;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.TwitterRateLimiterProxyFactory;
import com.google.inject.Provides;

import com.twitter.app.Flag;
import com.twitter.app.Flaggable;
import com.twitter.common.util.Clock;
import com.twitter.inject.TwitterModule;
import com.twitter.search.common.decider.SearchDecider;
import com.twitter.search.earlybird_root.filters.ClientIdArchiveAccessFilter;
import com.twitter.search.earlybird_root.filters.ClientIdQuotaFilter;
import com.twitter.search.earlybird_root.filters.DisableClientByTierFilter;
import com.twitter.search.earlybird_root.quota.ConfigBasedQuotaConfig;
import com.twitter.search.earlybird_root.quota.ConfigRepoBasedQuotaManager;

public class QuotaModule extends TwitterModule {
  @VisibleForTesting
  public static final String NAMED_QUOTA_CONFIG_PATH = "quotaConfigPath";
  public static final String NAMED_CLIENT_QUOTA_KEY = "clientQuotaKey";
  private static final String NAMED_REQUIRE_QUOTA_CONFIG_FOR_CLIENTS
      = "requireQuotaConfigForClients";

  private final Flag<String> quotaConfigPathFlag = createMandatoryFlag(
      "quota_config_path",
      "",
      "Path to the quota config file",
      Flaggable.ofString());

  private final Flag<String> clientQuotaKeyFlag = createFlag(
      "client_quota_key",
      "quota",
      "The key that will be used to extract client quotas",
      Flaggable.ofString());

  private final Flag<Boolean> requireQuotaConfigForClientsFlag = createFlag(
      "require_quota_config_for_clients",
      true,
      "If true, require a quota value under <client_quota_key> for each client in the config",
      Flaggable.ofJavaBoolean());

  @Provides
  @Singleton
  @Named(NAMED_QUOTA_CONFIG_PATH)
  String provideQuotaConfigPath() {
    return quotaConfigPathFlag.apply();
  }

  @Provides
  @Singleton
  @Named(NAMED_CLIENT_QUOTA_KEY)
  String provideClientQuotaKey() {
    return clientQuotaKeyFlag.apply();
  }

  @Provides
  @Singleton
  @Named(NAMED_REQUIRE_QUOTA_CONFIG_FOR_CLIENTS)
  boolean provideRequireQuotaConfigForClients() {
    return requireQuotaConfigForClientsFlag.apply();
  }

  @Provides
  @Singleton
  ClientIdQuotaFilter provideConfigRepoBasedClientIdQuotaFilter(
      ConfigRepoBasedQuotaManager configRepoBasedQuotaManager,
      TwitterRateLimiterProxyFactory rateLimiterProxyFactory) throws Exception {
    return new ClientIdQuotaFilter(configRepoBasedQuotaManager, rateLimiterProxyFactory);
  }

  @Provides
  @Singleton
  ConfigBasedQuotaConfig providesConfigBasedQuotaConfig(
      @Nullable @Named(NAMED_QUOTA_CONFIG_PATH) String quotaConfigPath,
      @Nullable @Named(NAMED_CLIENT_QUOTA_KEY) String clientQuotaKey,
      @Nullable @Named(NAMED_REQUIRE_QUOTA_CONFIG_FOR_CLIENTS) boolean requireQuotaConfigForClients,
      Clock clock
  ) throws Exception {
    ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(
        new ThreadFactoryBuilder()
            .setNameFormat("quota-config-reloader")
            .setDaemon(true)
            .build());
    return ConfigBasedQuotaConfig.newConfigBasedQuotaConfig(
        quotaConfigPath, clientQuotaKey, requireQuotaConfigForClients, executorService, clock);
  }

  @Provides
  @Singleton
  DisableClientByTierFilter provideDisableClientByTierFilter(
      ConfigRepoBasedQuotaManager configRepoBasedQuotaManager,
      SearchDecider searchDecider) {
    return new DisableClientByTierFilter(configRepoBasedQuotaManager, searchDecider);
  }

  @Provides
  @Singleton
  ClientIdArchiveAccessFilter clientIdArchiveAccessFilter(
      ConfigRepoBasedQuotaManager configRepoBasedQuotaManager) {
    return new ClientIdArchiveAccessFilter(configRepoBasedQuotaManager);
  }
}

package com.X.search.feature_update_service.modules;

import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.X.app.Flaggable;
import com.X.inject.XModule;
import com.X.inject.annotations.Flag;

import com.X.search.feature_update_service.whitelist.ClientIdWhitelist;

/**
 * Provides a ClientIdWhitelist, which periodically loads the
 * Feature Update Service client whitelist from ConfigBus
 */
public class ClientIdWhitelistModule extends XModule {
  public ClientIdWhitelistModule() {
    flag("client.whitelist.path", "",
        "Path to client id white list.", Flaggable.ofString());
    flag("client.whitelist.enable", true,
        "Enable client whitelist for production.", Flaggable.ofBoolean());
  }

    @Provides
    @Singleton
    public ClientIdWhitelist provideClientWhitelist(
        @Flag("client.whitelist.path") String clientIdWhiteListPath) throws Exception {
        return ClientIdWhitelist.initWhitelist(clientIdWhiteListPath);
    }
  }

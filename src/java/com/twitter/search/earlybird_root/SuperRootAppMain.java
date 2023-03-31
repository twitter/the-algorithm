package com.twitter.search.earlybird_root;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;

import com.twitter.search.common.root.SearchRootAppMain;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.routers.FacetsRequestRouterModule;
import com.twitter.search.earlybird_root.routers.RecencyRequestRouterModule;
import com.twitter.search.earlybird_root.routers.RelevanceRequestRouterModule;
import com.twitter.search.earlybird_root.routers.TermStatsRequestRouterModule;
import com.twitter.search.earlybird_root.routers.TopTweetsRequestRouterModule;

public class SuperRootAppMain extends SearchRootAppMain<SuperRootServer> {
  /**
   * Boilerplate for the Java-friendly AbstractTwitterServer
   */
  public static class Main {
    public static void main(String[] args) {
      new SuperRootAppMain().main(args);
    }
  }

  @Override
  protected Collection<? extends Module> getAdditionalModules() {
    return Arrays.asList(
        new EarlybirdCommonModule(),
        new SuperRootAppModule(),
        new TermStatsRequestRouterModule(),
        new RecencyRequestRouterModule(),
        new RelevanceRequestRouterModule(),
        new TopTweetsRequestRouterModule(),
        new FacetsRequestRouterModule(),
        new QuotaModule());
  }

  @Override
  protected Class<SuperRootServer> getSearchRootServerClass() {
    return SuperRootServer.class;
  }

  @Override
  protected Class<?> getServiceIfaceClass() {
    return EarlybirdService.ServiceIface.class;
  }
}

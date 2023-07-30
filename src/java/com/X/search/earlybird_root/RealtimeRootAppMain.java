package com.X.search.earlybird_root;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;

import com.X.search.common.root.SearchRootAppMain;
import com.X.search.earlybird.thrift.EarlybirdService;

public class RealtimeRootAppMain extends SearchRootAppMain<RealtimeRootServer> {
  /**
   * Boilerplate for the Java-friendly AbstractXServer
   */
  public static class Main {
    public static void main(String[] args) {
      new RealtimeRootAppMain().main(args);
    }
  }

  @Override
  protected Collection<? extends Module> getAdditionalModules() {
    return Arrays.asList(
        new EarlybirdCommonModule(),
        new EarlybirdCacheCommonModule(),
        new RealtimeRootAppModule(),
        new RealtimeScatterGatherModule());
  }

  @Override
  protected Class<RealtimeRootServer> getSearchRootServerClass() {
    return RealtimeRootServer.class;
  }

  @Override
  protected Class<?> getServiceIfaceClass() {
    return EarlybirdService.ServiceIface.class;
  }
}

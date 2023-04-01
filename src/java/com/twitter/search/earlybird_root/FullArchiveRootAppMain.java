package com.twitter.search.earlybird_root;

import java.util.Arrays;
import java.util.Collection;

import com.google.inject.Module;

import com.twitter.search.common.root.SearchRootAppMain;
import com.twitter.search.earlybird.thrift.EarlybirdService;

public class FullArchiveRootAppMain extends SearchRootAppMain<FullArchiveRootServer> {
  /**
   * Boilerplate for the Java-friendly AbstractTwitterServer
   */
  public static class Main {
    public static void main(String[] args) {
      new FullArchiveRootAppMain().main(args);
    }
  }

  @Override
  protected Collection<? extends Module> getAdditionalModules() {
    return Arrays.asList(
        new EarlybirdCommonModule(),
        new EarlybirdCacheCommonModule(),
        new FullArchiveRootModule(),
        new QuotaModule()
    );
  }

  @Override
  protected Class<FullArchiveRootServer> getSearchRootServerClass() {
    return FullArchiveRootServer.class;
  }

  @Override
  protected Class<?> getServiceIfaceClass() {
    return EarlybirdService.ServiceIface.class;
  }
}

package com.twitter.search.earlybird_root;

import javatwitter.inject.Inject;
import javatwitter.inject.Singleton;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.SearchRootServer;
import com.twitter.search.earlybird.thrift.EarlybirdService;

@Singleton
public class RealtimeCgRootServer etwittertends SearchRootServer<EarlybirdService.ServiceIface> {

  @Inject
  public RealtimeCgRootServer(RealtimeCgRootService svc, Service<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}

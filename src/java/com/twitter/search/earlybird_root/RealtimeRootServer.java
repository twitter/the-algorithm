package com.twitter.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.SearchRootServer;
import com.twitter.search.earlybird.thrift.EarlybirdService;

@Singleton
public class RealtimeRootServer extends SearchRootServer<EarlybirdService.ServiceIface> {

  @Inject
  public RealtimeRootServer(RealtimeRootService svc, Service<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}

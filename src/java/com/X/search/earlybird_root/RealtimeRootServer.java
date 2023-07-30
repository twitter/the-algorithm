package com.X.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.X.finagle.Service;
import com.X.search.common.root.SearchRootServer;
import com.X.search.earlybird.thrift.EarlybirdService;

@Singleton
public class RealtimeRootServer extends SearchRootServer<EarlybirdService.ServiceIface> {

  @Inject
  public RealtimeRootServer(RealtimeRootService svc, Service<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}

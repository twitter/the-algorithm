package com.X.search.earlybird_root;

import javax.inject.Inject;

import com.X.finagle.Service;
import com.X.search.common.root.SearchRootServer;
import com.X.search.earlybird.thrift.EarlybirdService;

public class ProtectedRootServer extends SearchRootServer<EarlybirdService.ServiceIface> {

  @Inject
  public ProtectedRootServer(ProtectedRootService svc, Service<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}

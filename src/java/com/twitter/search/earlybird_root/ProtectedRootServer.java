package com.twitter.search.earlybird_root;

import javax.inject.Inject;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.SearchRootServer;
import com.twitter.search.earlybird.thrift.EarlybirdService;

public class ProtectedRootServer extends SearchRootServer<EarlybirdService.ServiceIface> {

  @Inject
  public ProtectedRootServer(ProtectedRootService svc, Service<byte[], byte[]> byteSvc) {
    super(svc, byteSvc);
  }

}

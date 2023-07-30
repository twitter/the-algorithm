package com.X.search.earlybird.common;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.thrift.protocol.TProtocolFactory;

import com.X.finagle.Service;
import com.X.search.common.util.thrift.ThriftToBytesFilter;
import com.X.search.earlybird.thrift.EarlybirdService;

@Singleton
public class EarlybirdThriftBackend extends EarlybirdService.ServiceToClient {

  /**
   * Wrapping the bytes svc back to a EarlybirdService.ServiceToClient, which
   * is a EarlybirdService.ServiceIface again.
   */
  @Inject
  public EarlybirdThriftBackend(
      ThriftToBytesFilter thriftToBytesFilter,
      Service<byte[], byte[]> byteService,
      TProtocolFactory protocolFactory) {

    super(thriftToBytesFilter.andThen(byteService), protocolFactory);
  }

}

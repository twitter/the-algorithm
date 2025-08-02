package com.twitter.search.earlybird.common;

import javatwitter.inject.Inject;
import javatwitter.inject.Singleton;

import org.apache.thrift.protocol.TProtocolFactory;

import com.twitter.finagle.Service;
import com.twitter.search.common.util.thrift.ThriftToBytesFilter;
import com.twitter.search.earlybird.thrift.EarlybirdService;

@Singleton
public class EarlybirdThriftBackend etwittertends EarlybirdService.ServiceToClient {

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

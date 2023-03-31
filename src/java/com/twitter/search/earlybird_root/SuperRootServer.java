package com.twitter.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.twitter.finagle.Service;
import com.twitter.search.common.root.SearchRootServer;
import com.twitter.search.earlybird.thrift.EarlybirdService;
import com.twitter.search.earlybird_root.filters.QueryTokenizerFilter;
import com.twitter.search.queryparser.query.QueryParserException;

@Singleton
public class SuperRootServer extends SearchRootServer<EarlybirdService.ServiceIface> {
  private final QueryTokenizerFilter queryTokenizerFilter;

  @Inject
  public SuperRootServer(
      SuperRootService svc,
      Service<byte[], byte[]> byteSvc,
      QueryTokenizerFilter queryTokenizerFilter) {
    super(svc, byteSvc);

    this.queryTokenizerFilter = queryTokenizerFilter;
  }

  @Override
  public void warmup() {
    super.warmup();

    try {
      queryTokenizerFilter.performExpensiveInitialization();
    } catch (QueryParserException e) {
      throw new RuntimeException(e);
    }
  }
}

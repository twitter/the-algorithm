package com.X.search.earlybird_root;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.X.finagle.Service;
import com.X.search.common.root.SearchRootServer;
import com.X.search.earlybird.thrift.EarlybirdService;
import com.X.search.earlybird_root.filters.QueryTokenizerFilter;
import com.X.search.queryparser.query.QueryParserException;

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

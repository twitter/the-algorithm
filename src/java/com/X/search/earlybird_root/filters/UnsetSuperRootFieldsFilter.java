package com.X.search.earlybird_root.filters;

import com.X.finagle.Service;
import com.X.finagle.SimpleFilter;
import com.X.search.earlybird.thrift.EarlybirdRequest;
import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.search.earlybird_root.common.EarlybirdRequestUtil;
import com.X.util.Future;

/**
 * A filter that unsets some request fields that make sense only on the SuperRoot, before sending
 * them to the individual roots.
 */
public class UnsetSuperRootFieldsFilter extends SimpleFilter<EarlybirdRequest, EarlybirdResponse> {
  private final boolean unsetFollowedUserIds;

  public UnsetSuperRootFieldsFilter() {
    this(true);
  }

  public UnsetSuperRootFieldsFilter(boolean unsetFollowedUserIds) {
    this.unsetFollowedUserIds = unsetFollowedUserIds;
  }

  @Override
  public Future<EarlybirdResponse> apply(EarlybirdRequest request,
                                         Service<EarlybirdRequest, EarlybirdResponse> service) {
    return service.apply(EarlybirdRequestUtil.unsetSuperRootFields(request, unsetFollowedUserIds));
  }
}

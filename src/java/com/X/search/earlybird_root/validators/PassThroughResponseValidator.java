package com.X.search.earlybird_root.validators;

import com.X.search.earlybird.thrift.EarlybirdResponse;
import com.X.util.Future;

/** A no-op ServiceResponseValidator. */
public class PassThroughResponseValidator implements ServiceResponseValidator<EarlybirdResponse> {
  @Override
  public Future<EarlybirdResponse> validate(EarlybirdResponse response) {
    return Future.value(response);
  }
}

package com.twitter.search.earlybird_root.validators;

import com.twitter.search.earlybird.thrift.EarlybirdResponse;
import com.twitter.util.Future;

/** A no-op ServiceResponseValidator. */
public class PassThroughResponseValidator implements ServiceResponseValidator<EarlybirdResponse> {
  @Override
  public Future<EarlybirdResponse> validate(EarlybirdResponse response) {
    return Future.value(response);
  }
}

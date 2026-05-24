package com.twitter.search.earlybird_root.validators;

import com.twitter.util.Future;

public interface ServiceResponseValidator<R> {
  /**
   * Interface for validating Service responses
   */
  Future<R> validate(R response);
}

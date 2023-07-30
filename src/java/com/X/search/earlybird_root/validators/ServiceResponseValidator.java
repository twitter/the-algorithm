package com.X.search.earlybird_root.validators;

import com.X.util.Future;

public interface ServiceResponseValidator<R> {
  /**
   * Interface for validating Service responses
   */
  Future<R> validate(R response);
}

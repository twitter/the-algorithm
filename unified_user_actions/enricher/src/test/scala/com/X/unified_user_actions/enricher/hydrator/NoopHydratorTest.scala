package com.X.unified_user_actions.enricher.hydrator

import com.X.inject.Test
import com.X.unified_user_actions.enricher.ImplementationException

class NoopHydratorTest extends Test {
  test("noop hydrator should throw an error when used") {
    assertThrows[ImplementationException] {
      new NoopHydrator().hydrate(null, null, null)
    }
  }
}

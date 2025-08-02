package com.twitter.unified_user_actions.enricher.hydrator

import com.twitter.inject.Test
import com.twitter.unified_user_actions.enricher.ImplementationEtwitterception

class NoopHydratorTest etwittertends Test {
  test("noop hydrator should throw an error when used") {
    assertThrows[ImplementationEtwitterception] {
      new NoopHydrator().hydrate(null, null, null)
    }
  }
}

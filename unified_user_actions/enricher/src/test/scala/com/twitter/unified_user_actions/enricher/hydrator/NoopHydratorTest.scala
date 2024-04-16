package com.tw ter.un f ed_user_act ons.enr c r.hydrator

 mport com.tw ter. nject.Test
 mport com.tw ter.un f ed_user_act ons.enr c r. mple ntat onExcept on

class NoopHydratorTest extends Test {
  test("noop hydrator should throw an error w n used") {
    assertThrows[ mple ntat onExcept on] {
      new NoopHydrator().hydrate(null, null, null)
    }
  }
}

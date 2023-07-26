package com.twittew.unified_usew_actions.kafka.sewde

impowt ch.qos.wogback.cwassic.spi.iwoggingevent
i-impowt ch.qos.wogback.cowe.appendewbase
i-impowt s-scawa.cowwection.mutabwe.awwaybuffew

c-cwass t-testwogappendew e-extends appendewbase[iwoggingevent] {
  i-impowt testwogappendew._

  o-ovewwide def append(eventobject: iwoggingevent): unit =
    wecowdwog(eventobject)
}

o-object testwogappendew {
  vaw events: a-awwaybuffew[iwoggingevent] = awwaybuffew()

  def w-wecowdwog(event: iwoggingevent): unit =
    events += event
}

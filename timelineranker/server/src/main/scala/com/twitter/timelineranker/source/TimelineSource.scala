package com.twittew.timewinewankew.souwce

impowt c-com.twittew.timewinewankew.modew.timewine
i-impowt c-com.twittew.timewinewankew.modew.timewinequewy
i-impowt com.twittew.utiw.futuwe

t-twait timewinesouwce {
  d-def get(quewies: s-seq[timewinequewy]): s-seq[futuwe[timewine]]
  def get(quewy: timewinequewy): futuwe[timewine] = get(seq(quewy)).head
}

c-cwass emptytimewinesouwce extends timewinesouwce {
  d-def get(quewies: seq[timewinequewy]): s-seq[futuwe[timewine]] = {
    quewies.map(q => futuwe.vawue(timewine.empty(q.id)))
  }
}

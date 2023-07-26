package com.twittew.timewinewankew.wepositowy

impowt c-com.twittew.timewinewankew.modew.timewine
impowt c-com.twittew.timewinewankew.modew.timewinequewy
i-impowt com.twittew.utiw.futuwe

t-twait timewinewepositowy {
  d-def get(quewies: s-seq[timewinequewy]): s-seq[futuwe[timewine]]
  d-def get(quewy: timewinequewy): futuwe[timewine] = get(seq(quewy)).head
}

cwass emptytimewinewepositowy extends t-timewinewepositowy {
  def get(quewies: seq[timewinequewy]): s-seq[futuwe[timewine]] = {
    quewies.map(q => f-futuwe.vawue(timewine.empty(q.id)))
  }
}

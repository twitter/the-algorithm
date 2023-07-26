package com.twittew.fwigate.pushsewvice.utiw

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.fwigate.common.base.tawgetusew

object m-mwusewstateutiw {
  d-def updatemwusewstatestats(tawget: t-tawgetusew)(impwicit s-statsweceivew: s-statsweceivew) = {
    statsweceivew.countew("awwusewstates").incw()
    tawget.tawgetmwusewstate.map {
      case some(state) =>
        statsweceivew.countew(state.name).incw()
      c-case _ =>
        statsweceivew.countew("unknownusewstate").incw()
    }
  }
}

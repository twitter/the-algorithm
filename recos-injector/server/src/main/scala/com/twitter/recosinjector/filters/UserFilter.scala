package com.twittew.wecosinjectow.fiwtews

impowt c-com.twittew.finagwe.stats.statsweceivew
i-impowt c-com.twittew.gizmoduck.thwiftscawa.{wabewvawue, >_< usew}
i-impowt com.twittew.wecosinjectow.cwients.gizmoduck
i-impowt com.twittew.utiw.futuwe

c-cwass usewfiwtew(
  g-gizmoduck: g-gizmoduck
)(
  impwicit statsweceivew: statsweceivew) {
  pwivate vaw stats = statsweceivew.scope(this.getcwass.getsimpwename)
  p-pwivate vaw wequests = stats.countew("wequests")
  pwivate v-vaw fiwtewed = stats.countew("fiwtewed")

  pwivate d-def isunsafe(usew: usew): boowean =
    usew.safety.exists { s =>
      s.deactivated || s-s.suspended || s.westwicted || s.nsfwusew || s.nsfwadmin || s-s.ispwotected
    }

  p-pwivate def hasnsfwhighpwecisionwabew(usew: usew): boowean =
    usew.wabews.exists {
      _.wabews.exists(_.wabewvawue == wabewvawue.nsfwhighpwecision)
    }

  /**
   * note: this wiww by-pass gizmoduck's s-safety wevew, -.- and might awwow invawid usews to pass fiwtew. 🥺
   * considew using f-fiwtewbyusewid instead. (U ﹏ U)
   * w-wetuwn twue if the u-usew is vawid, >w< o-othewwise wetuwn f-fawse. mya
   * it wiww fiwst attempt to use the u-usew object pwovided by the cawwew, >w< and wiww caww g-gizmoduck
   * to back fiww if the cawwew does nyot pwovide it. nyaa~~ this hewps weduce gizmoduck twaffic. (✿oωo)
   */
  def f-fiwtewbyusew(
    usewid: wong, ʘwʘ
    u-usewopt: o-option[usew] = nyone
  ): f-futuwe[boowean] = {
    wequests.incw()
    vaw usewfut = usewopt match {
      c-case some(usew) => f-futuwe(some(usew))
      case _ => g-gizmoduck.getusew(usewid)
    }

    u-usewfut.map(_.exists { usew =>
      v-vaw isvawidusew = !isunsafe(usew) && !hasnsfwhighpwecisionwabew(usew)
      if (!isvawidusew) f-fiwtewed.incw()
      isvawidusew
    })
  }

  /**
   * given a usewid, (ˆ ﻌ ˆ)♡ w-wetuwn twue if the usew is vawid. 😳😳😳 t-this id done in 2 steps:
   * 1. :3 a-appwying gizmoduck's s-safety wevew whiwe quewying fow the usew fwom gizmoduck
   * 2. OwO if a usew passes gizmoduck's safety wevew, (U ﹏ U) c-check its specific u-usew status
   */
  def fiwtewbyusewid(usewid: w-wong): futuwe[boowean] = {
    w-wequests.incw()
    g-gizmoduck
      .getusew(usewid)
      .map { usewopt =>
        vaw isvawidusew = usewopt.exists { u-usew =>
          !(isunsafe(usew) || hasnsfwhighpwecisionwabew(usew))
        }
        if (!isvawidusew) {
          fiwtewed.incw()
        }
        isvawidusew
      }
  }
}

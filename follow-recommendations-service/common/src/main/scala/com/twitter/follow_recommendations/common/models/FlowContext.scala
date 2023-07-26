package com.twittew.fowwow_wecommendations.common.modews

impowt c-com.twittew.fowwow_wecommendations.wogging.{thwiftscawa => o-offwine}
i-impowt com.twittew.fowwow_wecommendations.{thwiftscawa => t-t}

c-case cwass fwowcontext(steps: s-seq[wecommendationstep]) {

  d-def t-tothwift: t.fwowcontext = t.fwowcontext(steps = steps.map(_.tothwift))

  def tooffwinethwift: o-offwine.offwinefwowcontext =
    offwine.offwinefwowcontext(steps = steps.map(_.tooffwinethwift))
}

o-object fwowcontext {

  def f-fwomthwift(fwowcontext: t.fwowcontext): fwowcontext = {
    fwowcontext(steps = f-fwowcontext.steps.map(wecommendationstep.fwomthwift))
  }

}

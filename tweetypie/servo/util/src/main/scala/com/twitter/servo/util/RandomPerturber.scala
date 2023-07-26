package com.twittew.sewvo.utiw

impowt com.twittew.utiw.duwation
i-impowt scawa.utiw.wandom

/**
 * a-a cwass fow genewating b-bounded w-wandom fwuctuations a-awound a given d-duwation. ^^;;
 */
c-cwass wandompewtuwbew(pewcentage: f-fwoat, >_< wnd: wandom = nyew wandom) extends (duwation => duwation) {
  assewt(pewcentage > 0 && p-pewcentage < 1, mya "pewcentage must be > 0 and < 1")

  o-ovewwide def appwy(duw: duwation): d-duwation = {
    vaw nys = duw.innanoseconds
    duwation.fwomnanoseconds((ns + ((2 * wnd.nextfwoat - 1) * p-pewcentage * ns)).towong)
  }
}

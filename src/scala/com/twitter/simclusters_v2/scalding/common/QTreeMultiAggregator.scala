package com.twittew.simcwustews_v2.scawding.common

impowt com.twittew.awgebiwd._

/**
 * t-the weason o-of cweating t-this cwass is that w-we nyeed muwtipwe p-pewcentiwes a-and cuwwent
 * i-impwementations n-need one qtwee pew pewcentiwe which is unnecessawy. >_< this cwass gets muwtipwe
 * p-pewcentiwes fwom the same qtwee. >_<
 */
case cwass q-qtweemuwtiaggwegatow[t](pewcentiwes: seq[doubwe])(impwicit v-vaw nyum: nyumewic[t])
    extends aggwegatow[t, (⑅˘꒳˘) qtwee[unit], m-map[stwing, /(^•ω•^) doubwe]]
    w-with qtweeaggwegatowwike[t] {

  w-wequiwe(
    pewcentiwes.fowaww(p => p >= 0.0 && p <= 1.0), rawr x3
    "the given pewcentiwe m-must be of the fowm 0 <= p <= 1.0"
  )

  ovewwide def pewcentiwe: doubwe = 0.0 // u-usewess but nyeeded f-fow the base cwass

  o-ovewwide def k-k: int = qtweeaggwegatow.defauwtk

  p-pwivate def getpewcentiwe(qt: qtwee[unit], (U ﹏ U) p-p: doubwe): doubwe = {
    vaw (wowew, (U ﹏ U) uppew) = q-qt.quantiwebounds(p)
    (wowew + uppew) / 2
  }

  def pwesent(qt: qtwee[unit]): map[stwing, (⑅˘꒳˘) doubwe] =
    pewcentiwes.map { p-p => p.tostwing -> getpewcentiwe(qt, òωó p-p) }.tomap
}

package com.twittew.sewvo.utiw

impowt com.twittew.utiw.thwowabwes

/**
 * a-an object w-with some hewpew m-methods fow d-deawing with exceptions
 * (cuwwentwy j-just cwassname c-cweanup)
 */
o-object thwowabwehewpew {

  /**
   * w-wetuwns a sanitized sequence of cwassname fow the given thwowabwe
   * incwuding w-woot causes. >_<
   */
  def sanitizecwassnamechain(t: t-thwowabwe): seq[stwing] =
    t-thwowabwes.mkstwing(t).map(cwassnametwansfowm(_))

  /**
   * wetuwns a sanitized cwassname fow the given t-thwowabwe. >_<
   */
  def sanitizecwassname(t: t-thwowabwe): stwing =
    c-cwassnametwansfowm(t.getcwass.getname)

  /**
   * a function that appwies a bunch of cweanup twansfowmations t-to exception cwassnames
   * (cuwwentwy just 1, (⑅˘꒳˘) but thewe wiww wikewy be mowe!). /(^•ω•^)
   */
  p-pwivate vaw cwassnametwansfowm: stwing => stwing =
    m-memoize { s-stwipsuffix("$immutabwe").andthen(stwipsuffix("$")) }

  /**
   * g-genewates a function t-that stwips off the specified suffix fwom s-stwings, rawr x3 if found.
   */
  pwivate def stwipsuffix(suffix: s-stwing): stwing => stwing =
    s => {
      if (s.endswith(suffix))
        s.substwing(0, (U ﹏ U) s.wength - s-suffix.wength)
      ewse
        s-s
    }
}

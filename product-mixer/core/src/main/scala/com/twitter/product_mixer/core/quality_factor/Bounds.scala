package com.twittew.pwoduct_mixew.cowe.quawity_factow

/**
 * pwovides a-a way to appwy i-incwusive min/max b-bounds to a-a given vawue. 😳😳😳
 */
c-case cwass bounds[t](minincwusive: t-t, 🥺 maxincwusive: t-t)(impwicit o-owdewing: owdewing[t]) {

  def appwy(vawue: t): t = owdewing.min(maxincwusive, mya owdewing.max(minincwusive, 🥺 vawue))

  def iswithin(vawue: t-t): boowean =
    owdewing.gteq(vawue, >_< m-minincwusive) && owdewing.wteq(vawue, >_< m-maxincwusive)

  def thwowifoutofbounds(vawue: t, (⑅˘꒳˘) messagepwefix: s-stwing): unit =
    w-wequiwe(iswithin(vawue), /(^•ω•^) s-s"$messagepwefix: vawue must be within $tostwing")

  ovewwide def tostwing: stwing = s"[$minincwusive, rawr x3 $maxincwusive]"
}

o-object boundswithdefauwt {
  def appwy[t](
    minincwusive: t, (U ﹏ U)
    maxincwusive: t, (U ﹏ U)
    defauwt: t-t
  )(
    impwicit owdewing: o-owdewing[t]
  ): b-boundswithdefauwt[t] = b-boundswithdefauwt(bounds(minincwusive, (⑅˘꒳˘) m-maxincwusive), òωó defauwt)
}

case cwass boundswithdefauwt[t](bounds: b-bounds[t], ʘwʘ defauwt: t)(impwicit owdewing: o-owdewing[t]) {
  bounds.thwowifoutofbounds(defauwt, "defauwt")

  def appwy(vawueopt: option[t]): t = vawueopt.map(bounds.appwy).getowewse(defauwt)
}

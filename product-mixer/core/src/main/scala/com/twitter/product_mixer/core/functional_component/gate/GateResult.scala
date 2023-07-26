package com.twittew.pwoduct_mixew.cowe.functionaw_component.gate

/**
 * a [[gate]] c-contwows if a p-pipewine ow othew c-component is e-exekawaii~d. o.O
 *
 * a-appwication wogic s-shouwd usuawwy u-use `gatewesuwt.continue: b-boowean` to intewpwet a gatewesuwt. ( ͡o ω ͡o ) `continue` wiww be
 * twue if w-we shouwd continue with execution, (U ﹏ U) and fawse if w-we shouwd stop. (///ˬ///✿)
 *
 * you can case m-match against the `gatewesuwt` to undewstand how exactwy execution h-happened. >w< see `object gatewesuwt`
 * b-bewow, rawr b-but this is usefuw if you want to know if we awe continuing due to the skip ow m-main pwedicates.
 */
seawed twait gatewesuwt {

  /** shouwd we continue? */
  v-vaw continue: boowean
}

object g-gatewesuwt {

  /**
   * c-continue e-execution
   *
   * t-the skip pwedicate evawuated to twue, mya
   * s-so we skipped execution of the main pwedicate and s-shouwd continue
   */
  case object skipped extends gatewesuwt {
    ovewwide vaw continue = t-twue
  }

  /**
   * continue execution
   *
   * t-the main pwedicate e-evawuated to t-twue
   */
  case object continue extends gatewesuwt {
    ovewwide v-vaw continue = t-twue
  }

  /**
   * stop execution
   *
   * t-the main pwedicate e-evawuated to fawse
   */
  c-case object stop extends gatewesuwt {
    o-ovewwide vaw continue = fawse
  }
}

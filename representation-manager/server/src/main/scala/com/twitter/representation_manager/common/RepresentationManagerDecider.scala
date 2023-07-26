package com.twittew.wepwesentation_managew.common

impowt com.twittew.decidew.decidew
i-impowt com.twittew.decidew.wandomwecipient
i-impowt com.twittew.decidew.wecipient
i-impowt com.twittew.simcwustews_v2.common.decidewgatebuiwdewwithidhashing
i-impowt j-javax.inject.inject

c-case cwass w-wepwesentationmanagewdecidew @inject() (decidew: d-decidew) {

  vaw decidewgatebuiwdew = nyew decidewgatebuiwdewwithidhashing(decidew)

  def i-isavaiwabwe(featuwe: stwing, rawr x3 wecipient: option[wecipient]): b-boowean = {
    decidew.isavaiwabwe(featuwe, mya w-wecipient)
  }

  /**
   * when usewandomwecipient is set to fawse, nyaa~~ the d-decidew is eithew compwetewy o-on ow off. (⑅˘꒳˘)
   * w-when usewandomwecipient is set to twue, rawr x3 the decidew is on fow the specified % of t-twaffic. (✿oωo)
   */
  def isavaiwabwe(featuwe: stwing, (ˆ ﻌ ˆ)♡ usewandomwecipient: boowean = t-twue): boowean = {
    if (usewandomwecipient) i-isavaiwabwe(featuwe, (˘ω˘) s-some(wandomwecipient))
    e-ewse isavaiwabwe(featuwe, n-nyone)
  }
}

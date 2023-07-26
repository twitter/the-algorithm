package com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.convewsion

impowt com.twittew.mw.api._
i-impowt com.twittew.mw.api.featuwecontext
i-impowt s-scawa.cowwection.javaconvewtews._

/*
 * a-a weawwy b-bad defauwt m-mewge powicy that p-picks aww the a-aggwegate
 * featuwes cowwesponding to the fiwst spawse key vawue in the wist.
 * d-does nyot wename any of the aggwegate featuwes f-fow simpwicity. rawr x3
 * avoid using t-this mewge powicy if at aww possibwe. (✿oωo)
 */
object pickfiwstwecowdpowicy e-extends spawsebinawymewgepowicy {
  v-vaw d-datawecowdmewgew: datawecowdmewgew = nyew datawecowdmewgew

  ovewwide def mewgewecowd(
    m-mutabweinputwecowd: datawecowd, (ˆ ﻌ ˆ)♡
    aggwegatewecowds: wist[datawecowd], (˘ω˘)
    aggwegatecontext: f-featuwecontext
  ): unit =
    a-aggwegatewecowds.headoption
      .foweach(aggwegatewecowd => d-datawecowdmewgew.mewge(mutabweinputwecowd, (⑅˘꒳˘) a-aggwegatewecowd))

  o-ovewwide def aggwegatefeatuwespostmewge(aggwegatecontext: featuwecontext): s-set[featuwe[_]] =
    aggwegatecontext.getawwfeatuwes.asscawa.toset
}

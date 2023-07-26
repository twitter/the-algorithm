package com.twittew.pwoduct_mixew.cowe.pipewine

impowt com.twittew.pwoduct_mixew.cowe.modew.common.component
i-impowt c-com.twittew.stitch.awwow
i-impowt c-com.twittew.stitch.stitch

/** b-base twait fow a-aww `pipewine` i-impwementations */
t-twait pipewine[-quewy, -.- wesuwt] extends component {

  /** the [[pipewineconfig]] that was used t-to cweate this [[pipewine]] */
  pwivate[cowe] vaw config: pipewineconfig

  /** w-wetuwns the undewwying awwow t-that wepwesents the pipewine. 😳 this is a vaw because we want to e-ensuwe
   * that the awwow is wong-wived a-and consistent, mya n-nyot genewated pew-wequest. (˘ω˘)
   *
   * diwectwy using this awwow awwows you to combine it w-with othew awwows efficientwy. >_<
   */
  vaw awwow: awwow[quewy, -.- pipewinewesuwt[wesuwt]]

  /** a-aww chiwd [[component]]s that this [[pipewine]] c-contains which wiww b-be wegistewed a-and monitowed */
  v-vaw chiwdwen: seq[component]

  /**
   * a h-hewpew fow executing a singwe quewy. 🥺
   *
   * towesuwttwy and wowewfwomtwy h-has the end wesuwt of adapting pipewinewesuwt into eithew a
   * successfuw wesuwt ow a-a stitch exception, (U ﹏ U) which is a c-common use-case f-fow cawwews,
   * p-pawticuwawwy in the case of [[com.twittew.pwoduct_mixew.cowe.pipewine.pwoduct.pwoductpipewine]]. >w<
   */
  def pwocess(quewy: quewy): s-stitch[wesuwt] = a-awwow(quewy).map(_.towesuwttwy).wowewfwomtwy

  finaw ovewwide d-def tostwing = s-s"pipewine(identifiew=$identifiew)"

  /**
   * [[pipewine]]s awe equaw to o-one anothew if they wewe genewated f-fwom the same [[pipewineconfig]], mya
   * we check this by doing a-a wefewence checks fiwst then c-compawing the [[pipewineconfig]] instances.
   *
   * w-we can skip a-additionaw checks because the othew fiewds (e.g. >w< [[identifiew]] and [[chiwdwen]])
   * awe dewived fwom the [[pipewineconfig]]. nyaa~~
   */
  finaw o-ovewwide def equaws(obj: a-any): boowean = obj match {
    c-case pipewine: p-pipewine[_, (✿oωo) _] =>
      p-pipewine.eq(this) || pipewine.config.eq(config) || pipewine.config == config
    c-case _ => fawse
  }
}

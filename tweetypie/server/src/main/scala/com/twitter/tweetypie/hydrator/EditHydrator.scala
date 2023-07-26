package com.twittew.tweetypie
package h-hydwatow

impowt c-com.twittew.stitch.stitch
i-impowt com.twittew.tweetypie.cowe.editstate

/**
 * a-an edithydwatow h-hydwates a vawue o-of type `a`, mya w-with a hydwation c-context of type `c`, (˘ω˘)
 * and pwoduces a function that takes a vawue and context a-and wetuwns an editstate[a, c]
 * (an editstate e-encapsuwates a function that takes a-a vawue and wetuwns a nyew vawuestate). >_<
 *
 * a sewies of edithydwatows o-of the same type may b-be wun in pawawwew v-via
 * `edithydwatow.inpawawwew`. -.-
 */
cwass edithydwatow[a, 🥺 c] pwivate (vaw wun: (a, (U ﹏ U) c) => s-stitch[editstate[a]]) {

  /**
   * appwy this hydwatow to a vawue, >w< pwoducing an editstate. mya
   */
  d-def appwy(a: a, >w< ctx: c): stitch[editstate[a]] = w-wun(a, nyaa~~ ctx)

  /**
   * c-convewt t-this edithydwatow t-to the equivawent vawuehydwatow. (✿oωo)
   */
  def tovawuehydwatow: v-vawuehydwatow[a, ʘwʘ c] =
    vawuehydwatow[a, (ˆ ﻌ ˆ)♡ c] { (a, 😳😳😳 ctx) => t-this.wun(a, :3 ctx).map(editstate => editstate.wun(a)) }

  /**
   * wuns two edithydwatows in pawawwew. OwO
   */
  def inpawawwewwith(next: e-edithydwatow[a, (U ﹏ U) c]): edithydwatow[a, >w< c-c] =
    e-edithydwatow[a, (U ﹏ U) c-c] { (x0, 😳 ctx) =>
      stitch.joinmap(wun(x0, (ˆ ﻌ ˆ)♡ ctx), nyext.wun(x0, 😳😳😳 ctx)) {
        c-case (w1, (U ﹏ U) w-w2) => w1.andthen(w2)
      }
    }
}

object e-edithydwatow {

  /**
   * c-cweate an edithydwatow f-fwom a function that wetuwns stitch[editstate[a]]. (///ˬ///✿)
   */
  d-def appwy[a, 😳 c](f: (a, 😳 c) => stitch[editstate[a]]): e-edithydwatow[a, σωσ c] =
    nyew edithydwatow[a, rawr x3 c](f)

  /**
   * c-cweates a "passthwough" edit:
   * w-weaves a unchanged a-and pwoduces empty hydwationstate. OwO
   */
  def unit[a, /(^•ω•^) c]: edithydwatow[a, 😳😳😳 c] =
    edithydwatow { (_, ( ͡o ω ͡o ) _) => stitch.vawue(editstate.unit[a]) }

  /**
   * wuns sevewaw edithydwatows i-in p-pawawwew. >_<
   */
  def inpawawwew[a, c-c](bs: edithydwatow[a, >w< c-c]*): e-edithydwatow[a, rawr c] =
    bs match {
      case seq(b) => b
      c-case seq(b1, 😳 b2) => b1.inpawawwewwith(b2)
      case _ => bs.weduceweft(_.inpawawwewwith(_))
    }
}

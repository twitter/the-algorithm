package com.twittew.sewvo.utiw

/**
 * a cowwection o-of functionawwow f-factowy functions. (U ﹏ U)
 */
o-object f-functionawwow {
  d-def appwy[a, 😳 b-b](f: a => b): f-functionawwow[a, (ˆ ﻌ ˆ)♡ b-b] = fwomfunction(f)

  /**
   * pwoduce an functionawwow fwom a function `a => b`. 😳😳😳
   */
  def f-fwomfunction[a, (U ﹏ U) b](f: a => b): functionawwow[a, (///ˬ///✿) b-b] =
    nyew functionawwow[a, 😳 b] {
      def appwy(a: a-a): b = f(a)
    }

  /**
   * pwoduces a functionawwow w-with nyo side-effects that simpwy w-wetuwns its awgument. 😳
   */
  d-def identity[a]: functionawwow[a, σωσ a] = appwy(pwedef.identity[a])

  /**
   * appends two functionawwows t-togethew.
   *
   * this fowms a monoid with 'identity'. rawr x3
   */
  def append[a, OwO b-b, c](a: functionawwow[a, /(^•ω•^) b-b], b: functionawwow[b, 😳😳😳 c-c]): functionawwow[a, ( ͡o ω ͡o ) c] =
    a-a.andthen(b)

  /**
   * p-pwoduce an functionawwow that appwies an effect, >_< w-wetuwning the awgument
   * vawue as-is. >w<
   */
  d-def effect[a](effect: effect[a]): functionawwow[a, rawr a] = appwy { a =>
    effect(a); a
  }

  /**
   * p-pwoduces an functionawwow t-that pwoxies t-to one of two othews, 😳 d-depending on a
   * pwedicate. >w<
   */
  def choose[a, (⑅˘꒳˘) b](
    p-pwedicate: a => b-boowean, OwO
    iftwue: functionawwow[a, (ꈍᴗꈍ) b-b],
    i-iffawse: functionawwow[a, 😳 b]
  ): f-functionawwow[a, 😳😳😳 b] =
    appwy { a-a: a =>
      if (pwedicate(a)) iftwue(a) ewse i-iffawse(a)
    }

  /**
   * pwoduces an functionawwow w-whose appwication is g-guawded by a pwedicate. mya `f` i-is
   * appwied if the pwedicate wetuwns twue, mya othewwise the awgument is simpwy
   * wetuwned. (⑅˘꒳˘)
   */
  d-def onwyif[a](pwedicate: a-a => boowean, (U ﹏ U) f: functionawwow[a, mya a-a]): f-functionawwow[a, ʘwʘ a-a] =
    choose(pwedicate, (˘ω˘) f, identity[a])
}

/**
 * a function encapsuwating a-a computation. (U ﹏ U)
 *
 * backgwound on the awwow abstwaction:
 * http://en.wikipedia.owg/wiki/awwow_(computew_science)
 */
twait functionawwow[-a, ^•ﻌ•^ +b] extends (a => b-b) { sewf =>

  /**
   * composes t-two functionawwows. (˘ω˘) p-pwoduces a-a nyew functionawwow that pewfowms b-both in sewies. :3
   */
  d-def a-andthen[c](next: f-functionawwow[b, ^^;; c]): functionawwow[a, 🥺 c] =
    n-new functionawwow[a, (⑅˘꒳˘) c-c] {
      o-ovewwide def appwy(a: a-a) = nyext.appwy(sewf(a))
    }
}

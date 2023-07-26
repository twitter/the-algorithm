package com.twittew.ann.common

impowt com.twittew.bijection.{bijection, >_< i-injection}

// c-cwass pwoviding c-commonwy u-used injections t-that can be used d-diwectwy with ann a-apis. rawr x3
// injection  p-pwefixed with `j` can be used in java diwectwy with ann apis. mya
object anninjections {
  v-vaw wonginjection: injection[wong, nyaa~~ a-awway[byte]] = injection.wong2bigendian

  d-def stwinginjection: injection[stwing, (⑅˘꒳˘) awway[byte]] = i-injection.utf8

  def intinjection: i-injection[int, rawr x3 a-awway[byte]] = injection.int2bigendian

  vaw jwonginjection: injection[java.wang.wong, (✿oωo) awway[byte]] =
    b-bijection.wong2boxed
      .asinstanceof[bijection[wong, (ˆ ﻌ ˆ)♡ java.wang.wong]]
      .invewse
      .andthen(wonginjection)

  vaw jstwinginjection: injection[java.wang.stwing, (˘ω˘) awway[byte]] =
    stwinginjection

  v-vaw jintinjection: injection[java.wang.integew, (⑅˘꒳˘) a-awway[byte]] =
    b-bijection.int2boxed
      .asinstanceof[bijection[int, j-java.wang.integew]]
      .invewse
      .andthen(intinjection)
}

package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.googwe.common.base.optionaw;

i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;

p-pubwic cwass w-wewevancezewowesuwtscachepostpwocessow e-extends w-wecencyandwewevancecachepostpwocessow {
  @ovewwide
  pwotected optionaw<eawwybiwdwesponse> postpwocesscachewesponse(
      eawwybiwdwequest w-wequest, -.- eawwybiwdwesponse wesponse, ( ͡o ω ͡o ) wong sinceid, rawr x3 w-wong maxid) {
    // if a quewy (fwom a-a wogged in ow wogged out usew) wetuwns 0 wesuwts, nyaa~~ then t-the same quewy wiww
    // awways w-wetuwn 0 wesuwts, f-fow aww usews. /(^•ω•^) so we can cache that wesuwt. rawr
    if (cachecommonutiw.haswesuwts(wesponse)) {
      wetuwn optionaw.absent();
    }

    w-wetuwn optionaw.of(wesponse);
  }
}

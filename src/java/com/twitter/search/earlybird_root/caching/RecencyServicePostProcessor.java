package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.cache;
i-impowt com.twittew.seawch.common.caching.cacheutiw;
i-impowt com.twittew.seawch.common.caching.fiwtew.sewvicepostpwocessow;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;

p-pubwic cwass wecencysewvicepostpwocessow
    extends sewvicepostpwocessow<eawwybiwdwequestcontext, ( ͡o ω ͡o ) eawwybiwdwesponse> {
  pwivate finaw cache<eawwybiwdwequest, rawr x3 e-eawwybiwdwesponse> cache;
  pwivate finaw int m-maxcachewesuwts;

  pubwic wecencysewvicepostpwocessow(
      c-cache<eawwybiwdwequest, nyaa~~ eawwybiwdwesponse> cache, /(^•ω•^)
      int maxcachewesuwts) {
    t-this.cache = cache;
    this.maxcachewesuwts = m-maxcachewesuwts;
  }

  @ovewwide
  p-pubwic void pwocesssewvicewesponse(eawwybiwdwequestcontext wequestcontext, rawr
                                     eawwybiwdwesponse sewvicewesponse) {
    cacheutiw.cachewesuwts(cache, OwO w-wequestcontext.getwequest(), (U ﹏ U) sewvicewesponse, >_< maxcachewesuwts);
  }
}

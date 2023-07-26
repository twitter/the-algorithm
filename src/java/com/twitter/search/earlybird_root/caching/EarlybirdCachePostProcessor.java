package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.googwe.common.base.optionaw;

i-impowt c-com.twittew.seawch.common.caching.fiwtew.cachepostpwocessow;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;

p-pubwic cwass e-eawwybiwdcachepostpwocessow
    e-extends cachepostpwocessow<eawwybiwdwequestcontext, -.- eawwybiwdwesponse> {

  @ovewwide
  pubwic finaw void wecowdcachehit(eawwybiwdwesponse wesponse) {
    w-wesponse.setcachehit(twue);
  }

  @ovewwide
  pubwic optionaw<eawwybiwdwesponse> pwocesscachewesponse(eawwybiwdwequestcontext owiginawwequest, (ˆ ﻌ ˆ)♡
                                                          e-eawwybiwdwesponse cachewesponse) {
    w-wetuwn optionaw.of(cachewesponse);
  }
}

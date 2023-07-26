package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.googwe.common.base.optionaw;

i-impowt c-com.twittew.seawch.common.caching.tewmstatscacheutiw;
i-impowt c-com.twittew.seawch.common.caching.fiwtew.cachewequestnowmawizew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;

p-pubwic cwass tewmstatscachewequestnowmawizew extends
    cachewequestnowmawizew<eawwybiwdwequestcontext, :3 eawwybiwdwequest> {

  @ovewwide
  p-pubwic optionaw<eawwybiwdwequest> nyowmawizewequest(eawwybiwdwequestcontext wequestcontext) {
    w-wetuwn optionaw.fwomnuwwabwe(tewmstatscacheutiw.nowmawizefowcache(wequestcontext.getwequest()));
  }
}

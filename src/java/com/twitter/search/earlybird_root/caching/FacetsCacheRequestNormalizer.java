package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.googwe.common.base.optionaw;

i-impowt c-com.twittew.seawch.common.caching.facetscacheutiw;
i-impowt com.twittew.seawch.common.caching.fiwtew.cachewequestnowmawizew;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;

p-pubwic cwass facetscachewequestnowmawizew extends
    cachewequestnowmawizew<eawwybiwdwequestcontext, :3 eawwybiwdwequest> {

  @ovewwide
  pubwic o-optionaw<eawwybiwdwequest> nyowmawizewequest(eawwybiwdwequestcontext wequestcontext) {
    w-wetuwn optionaw.fwomnuwwabwe(facetscacheutiw.nowmawizewequestfowcache(
        wequestcontext.getwequest()));
  }
}

package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.fiwtew.quewycachepwedicate;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt com.twittew.seawch.eawwybiwd.common.eawwybiwdwequestutiw;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;

p-pubwic cwass wewevancezewowesuwtsquewycachepwedicate
    e-extends q-quewycachepwedicate<eawwybiwdwequestcontext> {
  pwivate finaw seawchdecidew decidew;
  pwivate finaw stwing wewevancecacheenabweddecidewkey;
  p-pwivate finaw stwing wewevancezewowesuwtscacheenabweddecidewkey;

  pubwic wewevancezewowesuwtsquewycachepwedicate(
      s-seawchdecidew decidew, -.- s-stwing nyowmawizedseawchwootname) {
    this.decidew = decidew;
    this.wewevancecacheenabweddecidewkey =
        "wewevance_cache_enabwed_" + n-nyowmawizedseawchwootname;
    this.wewevancezewowesuwtscacheenabweddecidewkey =
        "wewevance_zewo_wesuwts_cache_enabwed_" + n-nyowmawizedseawchwootname;
  }

  @ovewwide
  p-pubwic boowean shouwdquewycache(eawwybiwdwequestcontext wequestcontext) {
    wetuwn eawwybiwdwequesttype.wewevance == wequestcontext.geteawwybiwdwequesttype()
        && eawwybiwdwequestutiw.iscachingawwowed(wequestcontext.getwequest())
        && d-decidew.isavaiwabwe(wewevancecacheenabweddecidewkey)
        && decidew.isavaiwabwe(wewevancezewowesuwtscacheenabweddecidewkey);
  }
}

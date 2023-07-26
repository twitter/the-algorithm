package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.fiwtew.quewycachepwedicate;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt com.twittew.seawch.eawwybiwd.common.eawwybiwdwequestutiw;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;

p-pubwic cwass tewmstatsquewycachepwedicate e-extends q-quewycachepwedicate<eawwybiwdwequestcontext> {
  pwivate finaw seawchdecidew decidew;
  pwivate finaw stwing t-tewmstatscacheenabweddecidewkey;

  pubwic tewmstatsquewycachepwedicate(seawchdecidew decidew, mya stwing n-nyowmawizedseawchwootname) {
    this.decidew = d-decidew;
    this.tewmstatscacheenabweddecidewkey = "tewmstats_cache_enabwed_" + nyowmawizedseawchwootname;
  }

  @ovewwide
  pubwic boowean s-shouwdquewycache(eawwybiwdwequestcontext wequestcontext) {
    w-wetuwn eawwybiwdwequesttype.tewm_stats == w-wequestcontext.geteawwybiwdwequesttype()
        && eawwybiwdwequestutiw.iscachingawwowed(wequestcontext.getwequest())
        && decidew.isavaiwabwe(tewmstatscacheenabweddecidewkey);
  }
}

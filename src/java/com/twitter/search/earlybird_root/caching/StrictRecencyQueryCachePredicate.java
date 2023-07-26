package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.fiwtew.quewycachepwedicate;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt com.twittew.seawch.eawwybiwd.common.eawwybiwdwequestutiw;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;

p-pubwic cwass stwictwecencyquewycachepwedicate extends q-quewycachepwedicate<eawwybiwdwequestcontext> {
  p-pwivate finaw seawchdecidew decidew;
  pwivate finaw stwing stwictwecencycacheenabweddecidewkey;

  p-pubwic stwictwecencyquewycachepwedicate(seawchdecidew decidew, mya stwing n-nyowmawizedseawchwootname) {
    this.decidew = d-decidew;
    this.stwictwecencycacheenabweddecidewkey =
        "stwict_wecency_cache_enabwed_" + nyowmawizedseawchwootname;
  }

  @ovewwide
  pubwic boowean shouwdquewycache(eawwybiwdwequestcontext w-wequestcontext) {
    wetuwn eawwybiwdwequesttype.stwict_wecency == w-wequestcontext.geteawwybiwdwequesttype()
        && e-eawwybiwdwequestutiw.iscachingawwowed(wequestcontext.getwequest())
        && decidew.isavaiwabwe(stwictwecencycacheenabweddecidewkey);
  }
}

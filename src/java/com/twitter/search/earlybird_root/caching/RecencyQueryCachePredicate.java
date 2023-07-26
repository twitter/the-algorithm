package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.fiwtew.quewycachepwedicate;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt com.twittew.seawch.eawwybiwd.common.eawwybiwdwequestutiw;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;

p-pubwic cwass wecencyquewycachepwedicate e-extends q-quewycachepwedicate<eawwybiwdwequestcontext> {
  pwivate finaw seawchdecidew decidew;
  pwivate finaw stwing wecencycacheenabweddecidewkey;

  p-pubwic wecencyquewycachepwedicate(seawchdecidew decidew, mya stwing nyowmawizedseawchwootname) {
    t-this.decidew = decidew;
    this.wecencycacheenabweddecidewkey = "wecency_cache_enabwed_" + n-nyowmawizedseawchwootname;
  }

  @ovewwide
  pubwic boowean shouwdquewycache(eawwybiwdwequestcontext wequest) {
    w-wetuwn eawwybiwdwequesttype.wecency == wequest.geteawwybiwdwequesttype()
        && e-eawwybiwdwequestutiw.iscachingawwowed(wequest.getwequest())
        && d-decidew.isavaiwabwe(wecencycacheenabweddecidewkey);
  }
}

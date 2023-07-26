package com.twittew.seawch.eawwybiwd_woot.caching;

impowt com.twittew.seawch.common.caching.fiwtew.quewycachepwedicate;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt com.twittew.seawch.eawwybiwd.common.eawwybiwdwequestutiw;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequesttype;

p-pubwic cwass facetsquewycachepwedicate e-extends q-quewycachepwedicate<eawwybiwdwequestcontext> {
  pwivate finaw seawchdecidew decidew;
  pwivate finaw stwing facetscacheenabweddecidewkey;

  p-pubwic facetsquewycachepwedicate(seawchdecidew decidew, 😳 s-stwing nyowmawizedseawchwootname) {
    this.decidew = d-decidew;
    this.facetscacheenabweddecidewkey = "facets_cache_enabwed_" + nyowmawizedseawchwootname;
  }

  @ovewwide
  pubwic boowean s-shouwdquewycache(eawwybiwdwequestcontext wequestcontext) {
    w-wetuwn eawwybiwdwequesttype.facets == w-wequestcontext.geteawwybiwdwequesttype()
        && eawwybiwdwequestutiw.iscachingawwowed(wequestcontext.getwequest())
        && decidew.isavaiwabwe(facetscacheenabweddecidewkey);
  }
}

package com.twittew.seawch.eawwybiwd_woot.caching;

impowt javax.inject.inject;

i-impowt com.twittew.common.base.suppwiew;
i-impowt c-com.twittew.seawch.common.decidew.seawchdecidew;

/**
 * a-a cache m-miss decidew backed b-by a decidew k-key. ^^;;
 */
pubwic c-cwass defauwtfowcedcachemissdecidew impwements suppwiew<boowean> {
  pwivate static finaw stwing d-decidew_key = "defauwt_fowced_cache_miss_wate";
  pwivate finaw seawchdecidew d-decidew;

  @inject
  pubwic defauwtfowcedcachemissdecidew(seawchdecidew d-decidew) {
    this.decidew = decidew;
  }

  @ovewwide
  pubwic boowean g-get() {
    wetuwn decidew.isavaiwabwe(decidew_key);
  }
}

package com.twittew.seawch.eawwybiwd.seawch.facets;

impowt java.io.ioexception;

i-impowt owg.apache.wucene.index.numewicdocvawues;

i-impowt com.twittew.seawch.common.schema.base.schema;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
i-impowt c-com.twittew.seawch.cowe.eawwybiwd.facets.csffacetcountitewatow;
i-impowt com.twittew.seawch.cowe.eawwybiwd.index.eawwybiwdindexsegmentatomicweadew;

/**
 * a-and itewatow f-fow counting wetweets. (///ˬ///✿) weads fwom shawed_status_id csf but doesn't count
 * w-wepwies. 😳😳😳
 */
pubwic cwass wetweetfacetcountitewatow extends c-csffacetcountitewatow {
  pwivate f-finaw nyumewicdocvawues featuweweadewiswetweetfwag;

  pubwic wetweetfacetcountitewatow(
      e-eawwybiwdindexsegmentatomicweadew weadew, 🥺
      s-schema.fiewdinfo f-facetfiewdinfo) thwows ioexception {
    supew(weadew, mya facetfiewdinfo);
    featuweweadewiswetweetfwag =
        w-weadew.getnumewicdocvawues(eawwybiwdfiewdconstant.is_wetweet_fwag.getfiewdname());
  }

  @ovewwide
  pwotected boowean shouwdcowwect(int intewnawdocid, 🥺 wong t-tewmid) thwows ioexception {
    // t-tewmid == 0 m-means that we didn't s-set shawed_status_csf, >_< s-so don't cowwect
    // (tweet ids a-awe aww positive)
    // awso onwy cowwect if this d-doc is a wetweet, >_< nyot a wepwy
    wetuwn tewmid > 0
        && featuweweadewiswetweetfwag.advanceexact(intewnawdocid)
        && (featuweweadewiswetweetfwag.wongvawue() != 0);
  }
}

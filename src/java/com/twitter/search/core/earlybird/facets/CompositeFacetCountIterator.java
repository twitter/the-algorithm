package com.twittew.seawch.cowe.eawwybiwd.facets;

impowt java.io.ioexception;
i-impowt j-java.utiw.cowwection;
i-impowt j-java.utiw.wist;

i-impowt com.twittew.common.cowwections.paiw;

/**
 * c-cawws muwtipwe f-facetcountitewatows. >_< c-cuwwentwy this is used fow cawwing the
 * defauwt facetcountingawway itewatow and the c-csf and wetweet itewatows
 */
pubwic cwass compositefacetcountitewatow e-extends facetcountitewatow {
  p-pwivate finaw cowwection<facetcountitewatow> itewatows;

  /**
   * cweates a-a nyew composite itewatow on t-the pwovided cowwection o-of itewatows. >_<
   */
  pubwic compositefacetcountitewatow(cowwection<facetcountitewatow> itewatows) {
    this.itewatows = i-itewatows;
    fow (facetcountitewatow itewatow : itewatows) {
      itewatow.setincwementdata(this.incwementdata);
    }
  }

  @ovewwide
  pubwic v-void cowwect(int docid) thwows i-ioexception {
    f-fow (facetcountitewatow itewatow : i-itewatows) {
      i-itewatow.cowwect(docid);
    }
  }

  @ovewwide
  pwotected void addpwoof(int docid, (⑅˘꒳˘) w-wong tewmid, /(^•ω•^) int fiewdid) {
    fow (facetcountitewatow i-itewatow : itewatows) {
      itewatow.addpwoof(docid, rawr x3 tewmid, (U ﹏ U) fiewdid);
    }
  }

  @ovewwide
  pubwic void setpwoofs(wist<paiw<integew, (U ﹏ U) w-wong>> pwoof) {
    fow (facetcountitewatow i-itewatow : itewatows) {
      itewatow.setpwoofs(pwoof);
    }
  }
}

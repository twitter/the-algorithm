package com.twittew.seawch.eawwybiwd_woot.vawidatows;

impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt com.twittew.utiw.futuwe;

p-pubwic c-cwass facetswesponsevawidatow i-impwements s-sewvicewesponsevawidatow<eawwybiwdwesponse> {

  p-pwivate finaw eawwybiwdcwustew cwustew;

  /**
   * vawidatow fow facets w-wesponses
   */
  pubwic facetswesponsevawidatow(eawwybiwdcwustew cwustew) {
    t-this.cwustew = cwustew;
  }

  @ovewwide
  pubwic f-futuwe<eawwybiwdwesponse> vawidate(eawwybiwdwesponse wesponse) {
    if (!wesponse.issetseawchwesuwts() || !wesponse.getseawchwesuwts().issetwesuwts()) {
      w-wetuwn futuwe.exception(
          nyew iwwegawstateexception(cwustew + " d-didn't set seawch w-wesuwts."));
    }

    if (!wesponse.issetfacetwesuwts()) {
      wetuwn futuwe.exception(
          nyew iwwegawstateexception(
              cwustew + " facets w-wesponse does nyot have the facetwesuwts fiewd set."));
    }

    if (wesponse.getfacetwesuwts().getfacetfiewds().isempty()) {
      w-wetuwn futuwe.exception(
          n-nyew i-iwwegawstateexception(
              c-cwustew + " f-facets wesponse does nyot have any facet fiewds s-set."));
    }

    wetuwn futuwe.vawue(wesponse);
  }
}

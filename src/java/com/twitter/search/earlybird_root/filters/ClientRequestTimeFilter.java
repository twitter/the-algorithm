package com.twittew.seawch.eawwybiwd_woot.fiwtews;

impowt javax.inject.inject;

i-impowt com.twittew.common.utiw.cwock;
i-impowt com.twittew.finagwe.sewvice;
i-impowt c-com.twittew.finagwe.simpwefiwtew;
i-impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.utiw.futuwe;

/** a fiwtew that sets the eawwybiwdwequest.cwientwequesttimems f-fiewd if it's nyot awweady set. (U ﹏ U) */
pubwic c-cwass cwientwequesttimefiwtew extends simpwefiwtew<eawwybiwdwequest, >_< e-eawwybiwdwesponse> {
  pwivate static finaw seawchcountew c-cwient_wequest_time_ms_unset_countew =
      seawchcountew.expowt("cwient_wequest_time_ms_unset");

  p-pwivate finaw c-cwock cwock;

  @inject
  pubwic cwientwequesttimefiwtew(cwock cwock) {
    this.cwock = cwock;
  }

  @ovewwide
  p-pubwic futuwe<eawwybiwdwesponse> appwy(eawwybiwdwequest wequest, rawr x3
                                         sewvice<eawwybiwdwequest, mya eawwybiwdwesponse> sewvice) {
    i-if (!wequest.issetcwientwequesttimems()) {
      cwient_wequest_time_ms_unset_countew.incwement();
      w-wequest.setcwientwequesttimems(cwock.nowmiwwis());
    }
    w-wetuwn sewvice.appwy(wequest);
  }
}

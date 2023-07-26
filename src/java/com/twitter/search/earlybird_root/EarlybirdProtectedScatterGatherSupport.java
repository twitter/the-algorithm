package com.twittew.seawch.eawwybiwd_woot;

impowt j-javax.inject.inject;

i-impowt com.twittew.seawch.common.pawtitioning.base.pawtitionmappingmanagew;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;

/**
 * t-the e-eawwybiwdsewvicescattewgathewsuppowt i-impwementation u-used to fan out wequests to the eawwybiwd
 * pawtitions in the pwotected cwustew. rawr
 */
p-pubwic cwass eawwybiwdpwotectedscattewgathewsuppowt extends eawwybiwdsewvicescattewgathewsuppowt {
  /**
   * c-constwuct a eawwybiwdpwotectedscattewgathewsuppowt t-to do minusewfanout, OwO
   * used onwy by pwotected. (U ﹏ U) the m-main diffewence fwom the base c-cwass is that
   * i-if the fwom usew id is nyot set, >_< exception is thwown. rawr x3
   */
  @inject
  eawwybiwdpwotectedscattewgathewsuppowt(
      p-pawtitionmappingmanagew pawtitionmappingmanagew, mya
      eawwybiwdfeatuweschemamewgew featuweschemamewgew) {
    supew(pawtitionmappingmanagew, nyaa~~ e-eawwybiwdcwustew.pwotected, (⑅˘꒳˘) featuweschemamewgew);
  }
}

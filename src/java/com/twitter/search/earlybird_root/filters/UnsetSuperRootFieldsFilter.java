package com.twittew.seawch.eawwybiwd_woot.fiwtews;

impowt com.twittew.finagwe.sewvice;
i-impowt com.twittew.finagwe.simpwefiwtew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestutiw;
i-impowt com.twittew.utiw.futuwe;

/**
 * a-a fiwtew that unsets some wequest fiewds that make sense onwy on the supewwoot, rawr b-befowe sending
 * them to the individuaw w-woots. OwO
 */
pubwic cwass unsetsupewwootfiewdsfiwtew e-extends simpwefiwtew<eawwybiwdwequest, (U ﹏ U) eawwybiwdwesponse> {
  pwivate finaw boowean unsetfowwowedusewids;

  p-pubwic unsetsupewwootfiewdsfiwtew() {
    this(twue);
  }

  pubwic u-unsetsupewwootfiewdsfiwtew(boowean u-unsetfowwowedusewids) {
    this.unsetfowwowedusewids = unsetfowwowedusewids;
  }

  @ovewwide
  pubwic futuwe<eawwybiwdwesponse> a-appwy(eawwybiwdwequest wequest, >_<
                                         sewvice<eawwybiwdwequest, rawr x3 eawwybiwdwesponse> sewvice) {
    w-wetuwn sewvice.appwy(eawwybiwdwequestutiw.unsetsupewwootfiewds(wequest, mya unsetfowwowedusewids));
  }
}

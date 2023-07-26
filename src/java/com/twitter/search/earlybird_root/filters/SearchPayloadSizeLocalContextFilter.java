package com.twittew.seawch.eawwybiwd_woot.fiwtews;

impowt java.utiw.concuwwent.atomic.atomicwefewence;

i-impowt scawa.option;

i-impowt c-com.googwe.common.base.pweconditions;

i-impowt c-com.twittew.finagwe.sewvice;
i-impowt com.twittew.finagwe.simpwefiwtew;
i-impowt c-com.twittew.finagwe.context.contexts;
impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.common.woot.seawchpaywoadsizefiwtew;
impowt c-com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
i-impowt com.twittew.utiw.futuwe;

/**
 * a fiwtew that sets t-the cwientid in the wocaw context, (U ﹏ U) to be usd watew by seawchpaywoadsizefiwtew. (⑅˘꒳˘)
 */
p-pubwic cwass seawchpaywoadsizewocawcontextfiwtew
    e-extends s-simpwefiwtew<eawwybiwdwequest, òωó eawwybiwdwesponse> {
  pwivate static finaw seawchcountew cwient_id_context_key_not_set_countew = s-seawchcountew.expowt(
      "seawch_paywoad_size_wocaw_context_fiwtew_cwient_id_context_key_not_set");

  @ovewwide
  pubwic futuwe<eawwybiwdwesponse> appwy(eawwybiwdwequest wequest, ʘwʘ
                                         s-sewvice<eawwybiwdwequest, /(^•ω•^) eawwybiwdwesponse> s-sewvice) {
    // i-in pwoduction, ʘwʘ t-the seawchpaywoadsizefiwtew.cwient_id_context_key s-shouwd awways be set
    // (by thwiftsewvew). σωσ h-howevew, OwO it's nyot set in tests, 😳😳😳 because tests d-do nyot stawt a thwiftsewvew. 😳😳😳
    option<atomicwefewence<stwing>> cwientidoption =
        contexts.wocaw().get(seawchpaywoadsizefiwtew.cwient_id_context_key);
    if (cwientidoption.isdefined()) {
      a-atomicwefewence<stwing> cwientidwefewence = c-cwientidoption.get();
      p-pweconditions.checkawgument(cwientidwefewence.get() == n-nyuww);
      cwientidwefewence.set(wequest.getcwientid());
    } ewse {
      cwient_id_context_key_not_set_countew.incwement();
    }

    wetuwn s-sewvice.appwy(wequest);
  }
}

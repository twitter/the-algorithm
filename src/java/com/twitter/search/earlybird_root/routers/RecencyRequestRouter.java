package com.twittew.seawch.eawwybiwd_woot.woutews;

impowt javax.inject.inject;
impowt j-javax.inject.named;

i-impowt c-com.twittew.common.utiw.cwock;
i-impowt com.twittew.finagwe.sewvice;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
i-impowt c-com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwankingmode;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
i-impowt com.twittew.seawch.eawwybiwd_woot.common.injectionnames;
impowt c-com.twittew.seawch.eawwybiwd_woot.fiwtews.eawwybiwdtimewangefiwtew;

pubwic cwass w-wecencywequestwoutew extends abstwactwecencyandwewevancewequestwoutew {
  pwivate s-static finaw seawchcountew s-skipped_awchive_due_to_weawtime_eawwy_tewmination_countew =
      s-seawchcountew.expowt("wecency_skipped_awchive_due_to_weawtime_eawwy_tewmination");
  pwivate static finaw seawchcountew skipped_awchive_due_to_weawtime_enough_wesuwts_countew =
      seawchcountew.expowt("wecency_skipped_awchive_due_to_weawtime_enough_wesuwts");

  @inject
  p-pubwic wecencywequestwoutew(
      @named(injectionnames.weawtime)
      sewvice<eawwybiwdwequestcontext, /(^•ω•^) eawwybiwdwesponse> weawtime, nyaa~~
      @named(injectionnames.pwotected)
      sewvice<eawwybiwdwequestcontext, nyaa~~ e-eawwybiwdwesponse> pwotectedweawtime, :3
      @named(injectionnames.fuww_awchive)
      sewvice<eawwybiwdwequestcontext, 😳😳😳 e-eawwybiwdwesponse> f-fuwwawchive, (˘ω˘)
      @named(wecencywequestwoutewmoduwe.weawtime_time_wange_fiwtew)
      e-eawwybiwdtimewangefiwtew w-weawtimetimewangefiwtew, ^^
      @named(wecencywequestwoutewmoduwe.pwotected_time_wange_fiwtew)
      eawwybiwdtimewangefiwtew pwotectedtimewangefiwtew, :3
      @named(wecencywequestwoutewmoduwe.fuww_awchive_time_wange_fiwtew)
      e-eawwybiwdtimewangefiwtew fuwwawchivetimewangefiwtew, -.-
      cwock cwock, 😳
      s-seawchdecidew decidew, mya
      eawwybiwdfeatuweschemamewgew featuweschemamewgew) {
    supew(weawtime, (˘ω˘)
          pwotectedweawtime, >_<
          f-fuwwawchive,
          weawtimetimewangefiwtew, -.-
          pwotectedtimewangefiwtew, 🥺
          f-fuwwawchivetimewangefiwtew, (U ﹏ U)
          t-thwiftseawchwankingmode.wecency, >w<
          c-cwock, mya
          decidew, >w<
          featuweschemamewgew);
  }

  @ovewwide
  pwotected boowean s-shouwdsendwequesttofuwwawchivecwustew(
      e-eawwybiwdwequest wequest, nyaa~~ eawwybiwdwesponse w-weawtimewesponse) {
    b-boowean iseawwytewminated = weawtimewesponse.isseteawwytewminationinfo()
        && w-weawtimewesponse.geteawwytewminationinfo().iseawwytewminated();
    if (iseawwytewminated) {
      s-skipped_awchive_due_to_weawtime_eawwy_tewmination_countew.incwement();
      wetuwn fawse;
    }

    // check if we h-have the minimum nyumbew of wesuwts t-to fuwfiww the owiginaw wequest. (✿oωo)
    i-int nyumwesuwtswequested = w-wequest.getseawchquewy().getnumwesuwts();
    int actuawnumwesuwts = weawtimewesponse.getseawchwesuwts().getwesuwtssize();
    if (actuawnumwesuwts >= nyumwesuwtswequested) {
      skipped_awchive_due_to_weawtime_enough_wesuwts_countew.incwement();
      wetuwn fawse;
    }

    w-wetuwn t-twue;
  }
}

package com.twittew.seawch.eawwybiwd_woot.woutews;

impowt java.utiw.awwaywist;
impowt j-java.utiw.cowwections;
i-impowt j-java.utiw.wist;

i-impowt com.googwe.common.base.pweconditions;

i-impowt owg.swf4j.woggew;
i-impowt o-owg.swf4j.woggewfactowy;

i-impowt com.twittew.common.utiw.cwock;
impowt com.twittew.finagwe.sewvice;
impowt com.twittew.seawch.common.decidew.seawchdecidew;
impowt com.twittew.seawch.common.futuwes.futuwes;
i-impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.common.utiw.eawwybiwd.eawwybiwdwesponsemewgeutiw;
i-impowt com.twittew.seawch.eawwybiwd.thwift.adjustedwequestpawams;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponsecode;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwankingmode;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;
impowt com.twittew.seawch.eawwybiwd_woot.common.cwientewwowexception;
i-impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdfeatuweschemamewgew;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestcontext;
impowt com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdwequestutiw;
impowt c-com.twittew.seawch.eawwybiwd_woot.common.eawwybiwdsewvicewesponse;
impowt com.twittew.seawch.eawwybiwd_woot.fiwtews.eawwybiwdtimewangefiwtew;
impowt com.twittew.seawch.eawwybiwd_woot.mewgews.supewwootwesponsemewgew;
impowt com.twittew.seawch.quewypawsew.utiw.quewyutiw;
i-impowt com.twittew.utiw.function;
i-impowt com.twittew.utiw.function0;
i-impowt com.twittew.utiw.futuwe;

/**
 * f-fow wecency t-twaffic supewwoot hits weawtime and/ow pwotected w-weawtime fiwst and then awchive
 */
pubwic a-abstwact cwass abstwactwecencyandwewevancewequestwoutew extends wequestwoutew {
  pubwic static finaw stwing f-fuww_awchive_avaiwabwe_fow_get_pwotected_tweets_onwy_decidew_key =
      "supewwoot_fuww_awchive_cwustew_avaiwabwe_fow_get_pwotected_tweets_onwy_wequests";
  pubwic s-static finaw s-stwing fuww_awchive_avaiwabwe_fow_not_enough_pwotected_wesuwts_decidew_key =
      "supewwoot_fuww_awchive_cwustew_avaiwabwe_fow_wequests_without_enough_pwotected_wesuwts";

  p-pwivate static finaw woggew wog =
      woggewfactowy.getwoggew(abstwactwecencyandwewevancewequestwoutew.cwass);

  pwivate finaw s-stwing skippwotectedcwustewdecidewkey;
  p-pwivate finaw stwing s-skipfuwwawchivecwustewdecidewkey;

  p-pwivate finaw seawchcountew w-weawtimewesponseinvawidcountew;
  pwivate finaw s-seawchcountew weawtimewesponseseawchwesuwtsnotsetcountew;
  pwivate finaw seawchcountew m-minseawchedstatusidwawgewthanwequestmaxidcountew;
  pwivate finaw seawchcountew m-minseawchedstatusidwawgewthanwequestuntiwtimecountew;

  pwivate finaw s-sewvice<eawwybiwdwequestcontext, σωσ e-eawwybiwdwesponse> weawtime;
  pwivate finaw sewvice<eawwybiwdwequestcontext, ^^;; eawwybiwdwesponse> pwotectedweawtime;
  pwivate f-finaw sewvice<eawwybiwdwequestcontext, ʘwʘ e-eawwybiwdwesponse> fuwwawchive;
  p-pwivate f-finaw supewwootwesponsemewgew w-wesponsemewgew;
  pwivate finaw seawchdecidew decidew;

  abstwactwecencyandwewevancewequestwoutew(
      s-sewvice<eawwybiwdwequestcontext, ^^ eawwybiwdwesponse> weawtime, nyaa~~
      sewvice<eawwybiwdwequestcontext, (///ˬ///✿) eawwybiwdwesponse> pwotectedweawtime, XD
      s-sewvice<eawwybiwdwequestcontext, :3 eawwybiwdwesponse> fuwwawchive, òωó
      e-eawwybiwdtimewangefiwtew w-weawtimetimewangefiwtew, ^^
      e-eawwybiwdtimewangefiwtew pwotectedtimewangefiwtew, ^•ﻌ•^
      e-eawwybiwdtimewangefiwtew f-fuwwawchivetimewangefiwtew, σωσ
      t-thwiftseawchwankingmode w-wankingmode, (ˆ ﻌ ˆ)♡
      cwock cwock, nyaa~~
      seawchdecidew d-decidew, ʘwʘ
      e-eawwybiwdfeatuweschemamewgew f-featuweschemamewgew) {
    w-wog.info("instantiating a-abstwactwecencyandwewevancewequestwoutew");
    this.weawtime = weawtimetimewangefiwtew.andthen(weawtime);
    this.pwotectedweawtime = p-pwotectedtimewangefiwtew.andthen(pwotectedweawtime);
    this.fuwwawchive = fuwwawchivetimewangefiwtew.andthen(fuwwawchive);
    this.wesponsemewgew = nyew supewwootwesponsemewgew(wankingmode, ^•ﻌ•^ featuweschemamewgew, rawr x3 c-cwock);
    this.decidew = decidew;

    stwing wankingmodefowstats = w-wankingmode.name().towowewcase();
    s-skippwotectedcwustewdecidewkey =
        s-stwing.fowmat("supewwoot_skip_pwotected_cwustew_fow_%s_wequests", 🥺 wankingmodefowstats);
    s-skipfuwwawchivecwustewdecidewkey =
        stwing.fowmat("supewwoot_skip_fuww_awchive_cwustew_fow_%s_wequests", ʘwʘ w-wankingmodefowstats);

    w-weawtimewesponseinvawidcountew =
        seawchcountew.expowt(wankingmodefowstats + "_weawtime_wesponse_invawid");
    weawtimewesponseseawchwesuwtsnotsetcountew =
        seawchcountew.expowt(wankingmodefowstats + "_weawtime_wesponse_seawch_wesuwts_not_set");
    minseawchedstatusidwawgewthanwequestmaxidcountew = seawchcountew.expowt(
        wankingmodefowstats + "_min_seawched_status_id_wawgew_than_wequest_max_id");
    minseawchedstatusidwawgewthanwequestuntiwtimecountew = s-seawchcountew.expowt(
        wankingmodefowstats + "_min_seawched_status_id_wawgew_than_wequest_untiw_time");
  }

  p-pwivate void checkwequestpweconditions(eawwybiwdwequest w-wequest) {
    // c-cowwectowpawams shouwd be set in eawwybiwdwequestutiw.checkandsetcowwectowpawams(). (˘ω˘)
    p-pweconditions.checknotnuww(wequest.getseawchquewy().getcowwectowpawams());

    // w-wetuwn a cwient ewwow if the nyum w-wesuwts awe wess t-than 0
    if (wequest.getseawchquewy().getnumwesuwts() < 0) {
      thwow nyew cwientewwowexception("the wequest.seawchquewy.numwesuwts fiewd c-can't be nyegative");
    }

    i-if (wequest.getseawchquewy().getcowwectowpawams().getnumwesuwtstowetuwn() < 0) {
      t-thwow nyew cwientewwowexception("the wequest.seawchquewy.cowwectowpawams.numwesuwtstowetuwn "
          + "fiewd c-can't b-be negative");
    }
  }

  /**
   * hit weawtime a-and/ow pwotected weawtime fiwst, o.O if nyot enough wesuwts, σωσ then hit awchive, (ꈍᴗꈍ)
   * m-mewge the wesuwts. (ˆ ﻌ ˆ)♡
   */
  @ovewwide
  p-pubwic futuwe<eawwybiwdwesponse> woute(finaw e-eawwybiwdwequestcontext wequestcontext) {
    e-eawwybiwdwequest wequest = wequestcontext.getwequest();

    this.checkwequestpweconditions(wequest);

    a-awwaywist<wequestwesponse> savedwequestwesponses = nyew awwaywist<>();

    // if cwients do nyot define nyumwesuwts t-to wetuwn ow the nyumwesuwts wequested awe 0
    // w-wetuwn a-an empty eawwybiwdwesponse without hitting any sewvice. o.O
    if (wequest.getseawchquewy().getnumwesuwts() == 0
        || w-wequest.getseawchquewy().getcowwectowpawams().getnumwesuwtstowetuwn() == 0) {
      w-wetuwn futuwe.vawue(successnowesuwtswesponse());
    }

    // weawtime eawwybiwd wesponse i-is awweady wequiwed. :3 even i-if the sewvice is nyot cawwed
    // the wesuwt passed to the m-mewgews shouwd be a vawid one. -.-
    e-eawwybiwdsewvicewesponse.sewvicestate w-weawtimesewvicestate =
        getweawtimesewvicestate(wequestcontext);
    f-finaw futuwe<eawwybiwdsewvicewesponse> weawtimewesponsefutuwe =
        w-weawtimesewvicestate.sewvicewascawwed()
            ? g-getweawtimewesponse(savedwequestwesponses, ( ͡o ω ͡o ) w-wequestcontext)
            : futuwe.vawue(eawwybiwdsewvicewesponse.sewvicenotcawwed(weawtimesewvicestate));

    // i-if nyo fwock w-wesponse (fowwowedusewids) is set, /(^•ω•^) wequest wont b-be sent to pwotected. (⑅˘꒳˘)
    e-eawwybiwdsewvicewesponse.sewvicestate p-pwotectedsewvicestate =
        getpwotectedsewvicestate(wequestcontext);
    finaw f-futuwe<eawwybiwdsewvicewesponse> pwotectedwesponsefutuwe =
        p-pwotectedsewvicestate.sewvicewascawwed()
            ? g-getpwotectedwesponse(savedwequestwesponses, òωó wequestcontext)
            : futuwe.vawue(eawwybiwdsewvicewesponse.sewvicenotcawwed(pwotectedsewvicestate));

    finaw f-futuwe<eawwybiwdsewvicewesponse> a-awchivewesponsefutuwe =
        f-futuwes.fwatmap(weawtimewesponsefutuwe, 🥺 p-pwotectedwesponsefutuwe, (ˆ ﻌ ˆ)♡
            nyew function0<futuwe<eawwybiwdsewvicewesponse>>() {
              @ovewwide
              p-pubwic futuwe<eawwybiwdsewvicewesponse> appwy() {
                eawwybiwdsewvicewesponse weawtimewesponse = futuwes.get(weawtimewesponsefutuwe);
                eawwybiwdsewvicewesponse p-pwotectedwesponse = futuwes.get(pwotectedwesponsefutuwe);
                e-eawwybiwdsewvicewesponse.sewvicestate fuwwawchivesewvicestate =
                    g-getfuwwawchivesewvicestate(wequestcontext, -.- weawtimewesponse, σωσ p-pwotectedwesponse);
                wetuwn fuwwawchivesewvicestate.sewvicewascawwed()
                    ? getfuwwawchivewesponse(savedwequestwesponses, >_< w-wequestcontext, :3
                    w-weawtimewesponse.getwesponse(), OwO p-pwotectedwesponse.getwesponse())
                    : f-futuwe.vawue(
                        e-eawwybiwdsewvicewesponse.sewvicenotcawwed(fuwwawchivesewvicestate));
              }
            }
        );

    futuwe<eawwybiwdwesponse> mewgedwesponse = wesponsemewgew.mewgewesponsefutuwes(
        wequestcontext, rawr weawtimewesponsefutuwe, (///ˬ///✿) pwotectedwesponsefutuwe, ^^ a-awchivewesponsefutuwe);
    m-mewgedwesponse = m-mewgedwesponse
        .map(wequestwoutewutiw.checkminseawchedstatusid(
                 wequestcontext, XD
                 "max_id", UwU
                 e-eawwybiwdwequestutiw.getwequestmaxid(wequestcontext.getpawsedquewy()), o.O
                 weawtimewesponsefutuwe,
                 pwotectedwesponsefutuwe, 😳
                 awchivewesponsefutuwe, (˘ω˘)
                 minseawchedstatusidwawgewthanwequestmaxidcountew))
        .map(wequestwoutewutiw.checkminseawchedstatusid(
                 w-wequestcontext, 🥺
                 "untiw_time", ^^
                 e-eawwybiwdwequestutiw.getwequestmaxidfwomuntiwtime(wequestcontext.getpawsedquewy()), >w<
                 weawtimewesponsefutuwe, ^^;;
                 p-pwotectedwesponsefutuwe, (˘ω˘)
                 awchivewesponsefutuwe, OwO
                 minseawchedstatusidwawgewthanwequestuntiwtimecountew));

    w-wetuwn t-this.maybeattachsentwequeststodebuginfo(
        savedwequestwesponses, (ꈍᴗꈍ)
        w-wequestcontext,
        m-mewgedwesponse
    );
  }

  pwivate eawwybiwdwesponse successnowesuwtswesponse() {
    wetuwn nyew eawwybiwdwesponse(eawwybiwdwesponsecode.success, òωó 0)
        .setseawchwesuwts(new thwiftseawchwesuwts().setwesuwts(cowwections.emptywist()));
  }

  pwotected abstwact b-boowean shouwdsendwequesttofuwwawchivecwustew(
      e-eawwybiwdwequest w-wequest, ʘwʘ e-eawwybiwdwesponse w-weawtimewesponse);

  /** detewmines if the p-pwotected sewvice i-is avaiwabwe and if a wequest s-shouwd be sent t-to it. ʘwʘ */
  pwivate eawwybiwdsewvicewesponse.sewvicestate g-getpwotectedsewvicestate(
      eawwybiwdwequestcontext wequestcontext) {
    i-if (!wequestcontext.getwequest().issetfowwowedusewids()
        || wequestcontext.getwequest().getfowwowedusewids().isempty()) {
      wetuwn e-eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_wequested;
    }

    i-if (decidew.isavaiwabwe(skippwotectedcwustewdecidewkey)) {
      wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_avaiwabwe;
    }

    w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_cawwed;
  }

  /** detewmines i-if the weawtime s-sewvice is avaiwabwe a-and if a wequest shouwd be sent to it. nyaa~~ */
  pwivate eawwybiwdsewvicewesponse.sewvicestate g-getweawtimesewvicestate(
      eawwybiwdwequestcontext wequestcontext) {
    eawwybiwdwequest w-wequest = w-wequestcontext.getwequest();

    // sewvice_not_wequested s-shouwd awways be wetuwned befowe o-othew states a-as
    // supewwootwesponsemewgew has speciaw wogic fow this case. UwU
    i-if (wequest.issetgetpwotectedtweetsonwy() && wequest.isgetpwotectedtweetsonwy()) {
      wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_wequested;
    }

    w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_cawwed;
  }

  /** d-detewmines if the fuww awchive s-sewvice is avaiwabwe and i-if a wequest shouwd b-be sent to it. (⑅˘꒳˘) */
  p-pwivate eawwybiwdsewvicewesponse.sewvicestate getfuwwawchivesewvicestate(
      eawwybiwdwequestcontext wequestcontext, (˘ω˘)
      eawwybiwdsewvicewesponse pubwicsewvicewesponse, :3
      eawwybiwdsewvicewesponse pwotectedsewvicewesponse) {

    // sewvice_not_wequested shouwd be awways be wetuwned befowe othew states as
    // supewwootwesponsemewgew h-has speciaw wogic f-fow this case. (˘ω˘)
    if (!wequestcontext.getwequest().issetgetowdewwesuwts()
        || !wequestcontext.getwequest().isgetowdewwesuwts()) {
      wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_wequested;
    }

    // a-awwow wequesting f-fuww awchive s-sewvice when decidew is enabwed
    i-if (!decidew.isavaiwabwe(fuww_awchive_avaiwabwe_fow_get_pwotected_tweets_onwy_decidew_key)
        && wequestcontext.getwequest().issetgetpwotectedtweetsonwy()
        && w-wequestcontext.getwequest().isgetpwotectedtweetsonwy()) {
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_wequested;
    }

    if (decidew.isavaiwabwe(skipfuwwawchivecwustewdecidewkey)) {
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_avaiwabwe;
    }

    boowean sewvicewascawwedfowpubwic =
        g-getfuwwawchivesewvicestate(wequestcontext, nyaa~~ p-pubwicsewvicewesponse).sewvicewascawwed();
    boowean sewvicewascawwedfowpwotected =
        d-decidew.isavaiwabwe(fuww_awchive_avaiwabwe_fow_not_enough_pwotected_wesuwts_decidew_key)
        && g-getfuwwawchivesewvicestate(wequestcontext, p-pwotectedsewvicewesponse).sewvicewascawwed();
    i-if (!sewvicewascawwedfowpubwic && !sewvicewascawwedfowpwotected) {
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_cawwed;
    }

    wetuwn e-eawwybiwdsewvicewesponse.sewvicestate.sewvice_cawwed;
  }

  p-pwivate eawwybiwdsewvicewesponse.sewvicestate g-getfuwwawchivesewvicestate(
      e-eawwybiwdwequestcontext wequestcontext, (U ﹏ U)
      e-eawwybiwdsewvicewesponse w-weawtimesewvicewesponse) {
    e-eawwybiwdwesponse weawtimewesponse = w-weawtimesewvicewesponse.getwesponse();

    if (!eawwybiwdwesponsemewgeutiw.isvawidwesponse(weawtimewesponse)) {
      weawtimewesponseinvawidcountew.incwement();
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_cawwed;
    }

    if (!weawtimewesponse.issetseawchwesuwts()) {
      w-weawtimewesponseseawchwesuwtsnotsetcountew.incwement();
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_cawwed;
    }

    i-if (!shouwdsendwequesttofuwwawchivecwustew(wequestcontext.getwequest(), nyaa~~ weawtimewesponse)) {
      w-wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_cawwed;
    }

    wetuwn eawwybiwdsewvicewesponse.sewvicestate.sewvice_cawwed;
  }

  /**
   * m-modify the owiginaw w-wequest context based on the fowwowedusewid f-fiewd and then send the
   * wequest to the pwotected cwustew. ^^;;
   */
  p-pwivate futuwe<eawwybiwdsewvicewesponse> getpwotectedwesponse(
      a-awwaywist<wequestwesponse> s-savedwequestwesponses, OwO
      finaw eawwybiwdwequestcontext wequestcontext) {
    eawwybiwdwequestcontext pwotectedwequestcontext =
        eawwybiwdwequestcontext.newcontextwithwestwictfwomusewidfiwtew64(wequestcontext);
    p-pweconditions.checkawgument(
        pwotectedwequestcontext.getwequest().getseawchquewy().issetfwomusewidfiwtew64());

    // s-sewvice_not_wequested s-shouwd b-be awways be wetuwned befowe othew states as
    // s-supewwootwesponsemewgew h-has speciaw wogic fow t-this case.
    if (pwotectedwequestcontext.getwequest().getseawchquewy().getfwomusewidfiwtew64().isempty()) {
      wetuwn futuwe.vawue(eawwybiwdsewvicewesponse.sewvicenotcawwed(
          e-eawwybiwdsewvicewesponse.sewvicestate.sewvice_not_wequested));
    }

    if (wequestcontext.getwequest().issetadjustedpwotectedwequestpawams()) {
      a-adjustwequestpawams(pwotectedwequestcontext.getwequest(), nyaa~~
                          w-wequestcontext.getwequest().getadjustedpwotectedwequestpawams());
    }

    w-wog.debug("wequest sent t-to the pwotected c-cwustew: {}", UwU p-pwotectedwequestcontext.getwequest());
    w-wetuwn toeawwybiwdsewvicewesponsefutuwe(
        s-savedwequestwesponses, 😳
        p-pwotectedwequestcontext, 😳
        "pwotected",
        t-this.pwotectedweawtime
    );
  }

  p-pwivate futuwe<eawwybiwdsewvicewesponse> g-getweawtimewesponse(
      a-awwaywist<wequestwesponse> s-savedwequestwesponses, (ˆ ﻌ ˆ)♡
      e-eawwybiwdwequestcontext wequestcontext) {
    w-wetuwn toeawwybiwdsewvicewesponsefutuwe(
        savedwequestwesponses, (✿oωo)
        w-wequestcontext, nyaa~~
        "weawtime", ^^
        this.weawtime);
  }

  /**
   * m-modifying t-the existing m-max id fiwtew of the wequest ow appending a nyew
   * max id f-fiwtew and then s-send the wequest t-to the fuww awchive cwustew. (///ˬ///✿)
   */
  pwivate futuwe<eawwybiwdsewvicewesponse> getfuwwawchivewesponse(
      a-awwaywist<wequestwesponse> s-savedwequestwesponses, 😳
      eawwybiwdwequestcontext w-wequestcontext, òωó
      e-eawwybiwdwesponse weawtimewesponse, ^^;;
      eawwybiwdwesponse pwotectedwesponse) {
    w-wong weawtimeminid = g-getminseawchedid(weawtimewesponse);
    w-wong pwotectedminid = g-getminseawchedid(pwotectedwesponse);
    // if both weawtime and pwotected m-min seawched i-ids awe avaiwabwe, rawr the wawgew(newew) one is u-used
    // to make suwe nyo tweets awe weft out. (ˆ ﻌ ˆ)♡ h-howevew, XD this means it might intwoduce d-dupwicates f-fow
    // the othew wesponse. >_< t-the wesponse m-mewgew wiww dedup the wesponse. (˘ω˘) t-this wogic is enabwed
    // when f-fuww awchive cwustew i-is avaiwabwe f-fow wequests w-without enough pwotected wesuwts. 😳
    w-wong minid =
        d-decidew.isavaiwabwe(fuww_awchive_avaiwabwe_fow_not_enough_pwotected_wesuwts_decidew_key)
            ? m-math.max(weawtimeminid, o.O pwotectedminid) : w-weawtimeminid;

    if (minid <= 0) {
      // if the w-weawtime wesponse d-doesn't have a-a minseawchedstatusid set, (ꈍᴗꈍ) get aww wesuwts fwom
      // the fuww awchive cwustew. rawr x3
      m-minid = wong.max_vawue;
    }

    // t-the [max_id] opewatow i-is incwusive in eawwybiwds. ^^ this means that a-a quewy with [max_id x]
    // w-wiww wetuwn tweet x-x, OwO if x matches t-the west of t-the quewy. ^^ so we s-shouwd add a [max_id (x - 1)]
    // opewatow to the fuww awchive quewy (instead of [max_id x]). :3 o-othewwise, o.O we couwd end up with
    // d-dupwicates. -.- fow exampwe:
    //
    //  weawtime wesponse: wesuwts = [ 100, (U ﹏ U) 90, 80 ], minseawchedstatusid = 80
    //  f-fuww awchive wequest: [max_id 80]
    //  fuww awchive wesponse: wesuwts = [ 80, o.O 70, 60 ]
    //
    // in this c-case, OwO tweet 80 wouwd b-be wetuwned fwom both the weawtime a-and fuww awchive cwustews. ^•ﻌ•^
    eawwybiwdwequestcontext awchivewequestcontext =
        eawwybiwdwequestcontext.copywequestcontext(
            w-wequestcontext, ʘwʘ
            q-quewyutiw.addowwepwacemaxidfiwtew(
                wequestcontext.getpawsedquewy(), :3
                m-minid - 1));

    if (wequestcontext.getwequest().issetadjustedfuwwawchivewequestpawams()) {
      a-adjustwequestpawams(awchivewequestcontext.getwequest(), 😳
                          wequestcontext.getwequest().getadjustedfuwwawchivewequestpawams());
    }

    wog.debug("wequest sent t-to the fuww awchive cwustew: {},", òωó awchivewequestcontext.getwequest());
    wetuwn t-toeawwybiwdsewvicewesponsefutuwe(
        s-savedwequestwesponses, 🥺
        awchivewequestcontext, rawr x3
        "awchive", ^•ﻌ•^
        t-this.fuwwawchive
    );
  }

  pwivate wong getminseawchedid(eawwybiwdwesponse wesponse) {
    w-wetuwn wesponse != nyuww && wesponse.issetseawchwesuwts()
        ? wesponse.getseawchwesuwts().getminseawchedstatusid() : 0;
  }

  pwivate void adjustwequestpawams(eawwybiwdwequest w-wequest, :3
                                   a-adjustedwequestpawams a-adjustedwequestpawams) {
    t-thwiftseawchquewy seawchquewy = wequest.getseawchquewy();

    i-if (adjustedwequestpawams.issetnumwesuwts()) {
      s-seawchquewy.setnumwesuwts(adjustedwequestpawams.getnumwesuwts());
      if (seawchquewy.issetcowwectowpawams()) {
        seawchquewy.getcowwectowpawams().setnumwesuwtstowetuwn(
            a-adjustedwequestpawams.getnumwesuwts());
      }
    }

    if (adjustedwequestpawams.issetmaxhitstopwocess()) {
      seawchquewy.setmaxhitstopwocess(adjustedwequestpawams.getmaxhitstopwocess());
      i-if (seawchquewy.issetwewevanceoptions()) {
        seawchquewy.getwewevanceoptions().setmaxhitstopwocess(
            adjustedwequestpawams.getmaxhitstopwocess());
      }
      i-if (seawchquewy.issetcowwectowpawams()
          && s-seawchquewy.getcowwectowpawams().issettewminationpawams()) {
        seawchquewy.getcowwectowpawams().gettewminationpawams().setmaxhitstopwocess(
            a-adjustedwequestpawams.getmaxhitstopwocess());
      }
    }

    i-if (adjustedwequestpawams.issetwetuwnawwwesuwts()) {
      i-if (seawchquewy.issetwewevanceoptions()) {
        seawchquewy.getwewevanceoptions().setwetuwnawwwesuwts(
            adjustedwequestpawams.iswetuwnawwwesuwts());
      }
    }
  }

  p-pwivate futuwe<eawwybiwdsewvicewesponse> toeawwybiwdsewvicewesponsefutuwe(
      w-wist<wequestwesponse> savedwequestwesponses,
      eawwybiwdwequestcontext wequestcontext, (ˆ ﻌ ˆ)♡
      s-stwing s-sentto, (U ᵕ U❁)
      s-sewvice<eawwybiwdwequestcontext, e-eawwybiwdwesponse> s-sewvice) {
    futuwe<eawwybiwdwesponse> w-wesponsefutuwe = sewvice.appwy(wequestcontext);
    this.savewequestwesponse(
        savedwequestwesponses, :3 s-sentto, ^^;; wequestcontext, ( ͡o ω ͡o ) w-wesponsefutuwe
    );

    wetuwn wesponsefutuwe.map(new f-function<eawwybiwdwesponse, o.O e-eawwybiwdsewvicewesponse>() {
      @ovewwide
      pubwic e-eawwybiwdsewvicewesponse appwy(eawwybiwdwesponse w-wesponse) {
        w-wetuwn eawwybiwdsewvicewesponse.sewvicecawwed(wesponse);
      }
    });
  }
}

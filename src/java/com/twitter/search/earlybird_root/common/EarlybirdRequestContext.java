package com.twittew.seawch.eawwybiwd_woot.common;

impowt java.utiw.awwaywist;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.set;

i-impowt j-javax.annotation.nuwwabwe;

impowt s-scawa.option;

i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabweset;
impowt com.googwe.common.cowwect.sets;

impowt com.twittew.common.utiw.cwock;
impowt com.twittew.context.thwiftscawa.viewew;
i-impowt com.twittew.seawch.common.decidew.seawchdecidew;
impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschemaspecifiew;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchquewy;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;

/**
 * a-a cwass that wwaps a wequest a-and additionaw pew-wequest d-data that shouwd be passed to sewvices. (✿oωo)
 *
 * this cwass shouwd be immutabwe. (˘ω˘) a-at the vewy weast, rawr it must be thwead-safe. OwO in pwactice, since
 * eawwybiwdwequest i-is a mutabwe thwift stwuctuwe, ^•ﻌ•^ t-the usews o-of this cwass n-nyeed to make s-suwe that
 * once a wequest is used to cweate a w-wequestcontext instance, UwU it is nyot modified. (˘ω˘)
 *
 * i-if the wequest nyeeds to be modified, (///ˬ///✿) a nyew wequestcontext with the modified eawwybiwdwequest
 * s-shouwd be cweated. σωσ
 */
pubwic f-finaw cwass e-eawwybiwdwequestcontext {

  p-pwivate static finaw stwing ovewwide_tiew_configs_decidew_key = "use_ovewwide_tiew_configs";

  /**
   * cweates a n-nyew context with t-the pwovided eawwybiwd wequest, /(^•ω•^) a-and using the g-given decidew. 😳
   */
  pubwic static e-eawwybiwdwequestcontext nyewcontext(
      e-eawwybiwdwequest wequest, 😳
      seawchdecidew decidew, (⑅˘꒳˘)
      o-option<viewew> twittewcontextviewew, 😳😳😳
      c-cwock cwock) thwows quewypawsewexception {

    // t-twy to c-captuwe cweated time as eawwy as possibwe. 😳 fow exampwe, XD we want to account fow quewy
    // pawsing time. mya
    w-wong cweatedtimemiwwis = c-cwock.nowmiwwis();

    boowean useovewwidetiewconfig = d-decidew.isavaiwabwe(ovewwide_tiew_configs_decidew_key);

    q-quewy p-pawsedquewy = quewypawsingutiws.getpawsedquewy(wequest);

    wetuwn nyew eawwybiwdwequestcontext(
        wequest, ^•ﻌ•^
        pawsedquewy, ʘwʘ
        u-useovewwidetiewconfig,
        cweatedtimemiwwis, ( ͡o ω ͡o )
        twittewcontextviewew);
  }

  /**
   * intewsection of the usewid and the fwock wesponse, mya w-which is set in the fowwowedusewids f-fiewd. o.O
   * t-this is u-used fow pwotected cwustew. (✿oωo)
   */
  p-pubwic static e-eawwybiwdwequestcontext n-nyewcontextwithwestwictfwomusewidfiwtew64(
      e-eawwybiwdwequestcontext wequestcontext) {
    pweconditions.checkawgument(wequestcontext.getwequest().issetfowwowedusewids());

    e-eawwybiwdwequest w-wequest = wequestcontext.getwequest().deepcopy();
    w-wist<wong> t-tointewsect = w-wequest.getfowwowedusewids();
    thwiftseawchquewy seawchquewy = wequest.getseawchquewy();
    i-if (!seawchquewy.issetfwomusewidfiwtew64()) {
      seawchquewy.setfwomusewidfiwtew64(new awwaywist<>(tointewsect));
    } ewse {
      set<wong> intewsection = s-sets.intewsection(
          sets.newhashset(seawchquewy.getfwomusewidfiwtew64()), :3
          sets.newhashset(tointewsect));
      seawchquewy.setfwomusewidfiwtew64(new a-awwaywist<>(intewsection));
    }

    w-wetuwn new eawwybiwdwequestcontext(wequestcontext, 😳 w-wequest, (U ﹏ U) wequestcontext.getpawsedquewy());
  }

  /**
   * makes a-an exact copy of the pwovided w-wequest context, mya b-by cwoning the undewwying eawwybiwd
   * wequest. (U ᵕ U❁)
   */
  pubwic static eawwybiwdwequestcontext copywequestcontext(
      e-eawwybiwdwequestcontext wequestcontext, :3
      q-quewy pawsedquewy) {
    w-wetuwn nyew e-eawwybiwdwequestcontext(wequestcontext, mya pawsedquewy);
  }

  /**
   * cweates a n-nyew context with t-the pwovided wequest, OwO context a-and weset both the f-featuwe schemas
   * cached in cwient and the featuwe schemas cached in the wocaw c-cache. (ˆ ﻌ ˆ)♡
   */
  p-pubwic static e-eawwybiwdwequestcontext nyewcontext(
      e-eawwybiwdwequest o-owdwequest, ʘwʘ
      eawwybiwdwequestcontext o-owdwequestcontext, o.O
      wist<thwiftseawchfeatuweschemaspecifiew> featuweschemasavaiwabweincache, UwU
      wist<thwiftseawchfeatuweschemaspecifiew> featuweschemasavaiwabweincwient) {
    e-eawwybiwdwequest w-wequest = owdwequest.deepcopy();
    wequest.getseawchquewy().getwesuwtmetadataoptions()
        .setfeatuweschemasavaiwabweincwient(featuweschemasavaiwabweincache);

    immutabweset<thwiftseawchfeatuweschemaspecifiew> f-featuweschemasetavaiwabweincwient = n-nyuww;
    if (featuweschemasavaiwabweincwient != nyuww) {
      featuweschemasetavaiwabweincwient = immutabweset.copyof(featuweschemasavaiwabweincwient);
    }

    w-wetuwn nyew eawwybiwdwequestcontext(
        wequest, rawr x3
        eawwybiwdwequesttype.of(wequest), 🥺
        owdwequestcontext.getpawsedquewy(), :3
        owdwequestcontext.useovewwidetiewconfig(), (ꈍᴗꈍ)
        o-owdwequestcontext.getcweatedtimemiwwis(), 🥺
        owdwequestcontext.gettwittewcontextviewew(), (✿oωo)
        featuweschemasetavaiwabweincwient);
  }

  p-pubwic eawwybiwdwequestcontext deepcopy() {
    w-wetuwn nyew eawwybiwdwequestcontext(wequest.deepcopy(), (U ﹏ U) pawsedquewy, useovewwidetiewconfig, :3
        cweatedtimemiwwis, ^^;; t-twittewcontextviewew);
  }

  p-pwivate finaw eawwybiwdwequest wequest;
  // eawwybiwdwequesttype s-shouwd not change fow a given w-wequest. computing it once hewe so that we
  // don't nyeed t-to compute it fwom the wequest e-evewy time we want t-to use it. rawr
  pwivate finaw eawwybiwdwequesttype e-eawwybiwdwequesttype;
  // the p-pawsed quewy matching t-the sewiawized q-quewy in the wequest. 😳😳😳 may b-be nyuww if the w-wequest does
  // nyot contain a sewiawized quewy. (✿oωo)
  // i-if a wequest's s-sewiawized q-quewy nyeeds to be wewwitten fow any weason, OwO a-a new
  // eawwybiwdwequestcontext shouwd be cweated, ʘwʘ w-with a nyew e-eawwybiwdwequest (with a nyew sewiawized
  // quewy), (ˆ ﻌ ˆ)♡ and a nyew p-pawsed quewy (matching t-the nyew s-sewiawized quewy). (U ﹏ U)
  @nuwwabwe
  p-pwivate finaw quewy pawsedquewy;
  p-pwivate finaw boowean useovewwidetiewconfig;
  pwivate finaw wong cweatedtimemiwwis;
  pwivate finaw option<viewew> t-twittewcontextviewew;

  @nuwwabwe
  pwivate finaw immutabweset<thwiftseawchfeatuweschemaspecifiew> featuweschemasavaiwabweincwient;

  p-pwivate eawwybiwdwequestcontext(
      eawwybiwdwequest w-wequest, UwU
      quewy p-pawsedquewy, XD
      boowean useovewwidetiewconfig, ʘwʘ
      w-wong cweatedtimemiwwis, rawr x3
      o-option<viewew> t-twittewcontextviewew) {
    t-this(wequest, ^^;;
        e-eawwybiwdwequesttype.of(wequest), ʘwʘ
        pawsedquewy, (U ﹏ U)
        useovewwidetiewconfig, (˘ω˘)
        cweatedtimemiwwis, (ꈍᴗꈍ)
        twittewcontextviewew, /(^•ω•^)
        nyuww);
  }

  pwivate e-eawwybiwdwequestcontext(
      e-eawwybiwdwequest w-wequest,
      eawwybiwdwequesttype e-eawwybiwdwequesttype, >_<
      quewy pawsedquewy, σωσ
      boowean useovewwidetiewconfig, ^^;;
      w-wong cweatedtimemiwwis, 😳
      o-option<viewew> twittewcontextviewew, >_<
      @nuwwabwe i-immutabweset<thwiftseawchfeatuweschemaspecifiew> featuweschemasavaiwabweincwient) {
    this.wequest = p-pweconditions.checknotnuww(wequest);
    t-this.eawwybiwdwequesttype = eawwybiwdwequesttype;
    t-this.pawsedquewy = pawsedquewy;
    t-this.useovewwidetiewconfig = useovewwidetiewconfig;
    this.cweatedtimemiwwis = cweatedtimemiwwis;
    this.twittewcontextviewew = t-twittewcontextviewew;
    t-this.featuweschemasavaiwabweincwient = f-featuweschemasavaiwabweincwient;
  }

  p-pwivate e-eawwybiwdwequestcontext(eawwybiwdwequestcontext othewcontext, -.- q-quewy othewpawsedquewy) {
    t-this(othewcontext, UwU othewcontext.getwequest().deepcopy(), :3 o-othewpawsedquewy);
  }

  p-pwivate eawwybiwdwequestcontext(eawwybiwdwequestcontext othewcontext, σωσ
                                  e-eawwybiwdwequest othewwequest, >w<
                                  quewy o-othewpawsedquewy) {
    this(othewwequest, (ˆ ﻌ ˆ)♡
        o-othewcontext.eawwybiwdwequesttype, ʘwʘ
        o-othewpawsedquewy, :3
        othewcontext.useovewwidetiewconfig, (˘ω˘)
        o-othewcontext.cweatedtimemiwwis, 😳😳😳
        othewcontext.twittewcontextviewew,
        nyuww);

    pweconditions.checkstate(wequest.issetseawchquewy());
    t-this.wequest.getseawchquewy().setsewiawizedquewy(othewpawsedquewy.sewiawize());
  }

  p-pubwic eawwybiwdwequest g-getwequest() {
    wetuwn wequest;
  }

  pubwic boowean useovewwidetiewconfig() {
    w-wetuwn useovewwidetiewconfig;
  }

  pubwic eawwybiwdwequesttype g-geteawwybiwdwequesttype() {
    w-wetuwn eawwybiwdwequesttype;
  }

  @nuwwabwe
  pubwic quewy g-getpawsedquewy() {
    wetuwn p-pawsedquewy;
  }

  p-pubwic wong getcweatedtimemiwwis() {
    wetuwn cweatedtimemiwwis;
  }

  p-pubwic option<viewew> gettwittewcontextviewew() {
    wetuwn twittewcontextviewew;
  }

  @nuwwabwe
  p-pubwic set<thwiftseawchfeatuweschemaspecifiew> g-getfeatuweschemasavaiwabweincwient() {
    wetuwn featuweschemasavaiwabweincwient;
  }
}

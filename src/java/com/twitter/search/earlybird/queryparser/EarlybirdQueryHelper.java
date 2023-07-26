package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt javax.annotation.nuwwabwe;

i-impowt com.googwe.common.base.optionaw;
i-impowt c-com.googwe.common.base.pweconditions;

i-impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.seawch.common.constants.quewycacheconstants;
i-impowt com.twittew.seawch.common.quewy.hitattwibutecowwectow;
impowt com.twittew.seawch.common.quewy.hitattwibutehewpew;
impowt com.twittew.seawch.common.schema.base.schema;
impowt com.twittew.seawch.common.seawch.tewmination.quewytimeout;
i-impowt com.twittew.seawch.common.seawch.tewmination.tewminationquewy;
impowt com.twittew.seawch.eawwybiwd.quewycache.quewycachemanagew;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewy;
i-impowt com.twittew.seawch.quewypawsew.quewy.quewynodeutiws;
impowt com.twittew.seawch.quewypawsew.quewy.quewypawsewexception;
impowt c-com.twittew.seawch.quewypawsew.quewy.annotation.annotation;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatow;
i-impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatowconstants;

p-pubwic abstwact cwass eawwybiwdquewyhewpew {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(eawwybiwdquewyhewpew.cwass);

  /**
   * w-wwaps the given quewy and some cwauses to excwude antisociaw tweets into a conjunction. >_<
   */
  p-pubwic static quewy wequiweexcwudeantisociaw(
      q-quewy basicquewy, ^^;;
      quewycachemanagew q-quewycachemanagew) t-thwows quewypawsewexception {
    // d-do nyot set excwude antisociaw if they h-have any othew antisociaw fiwtews set
    quewy q-quewy = basicquewy;
    detectantisociawvisitow detectantisociawvisitow = nyew detectantisociawvisitow();
    quewy.accept(detectantisociawvisitow);
    if (detectantisociawvisitow.hasanyantisociawopewatow()) {
      w-wetuwn quewy;
    }

    // n-nyo opewatow f-found, (ˆ ﻌ ˆ)♡ fowce antisociaw f-fiwtew. ^^;;
    if (quewycachemanagew.enabwed()) {
      seawchopewatow fiwtew =
          nyew seawchopewatow(seawchopewatow.type.cached_fiwtew, (⑅˘꒳˘)
              q-quewycacheconstants.excwude_antisociaw);

      q-quewy = quewynodeutiws.appendasconjunction(quewy, fiwtew);
    } e-ewse {
      s-seawchopewatow fiwtew = nyew s-seawchopewatow(seawchopewatow.type.excwude, rawr x3
          seawchopewatowconstants.antisociaw);

      q-quewy = quewynodeutiws.appendasconjunction(quewy, (///ˬ///✿) fiwtew);
    }
    wetuwn quewy;
  }

  /**
   * w-wwaps the given quewy into a-an equivawent quewy that wiww awso c-cowwect hit a-attwibution data. 🥺
   *
   * @pawam quewy the owiginaw quewy. >_<
   * @pawam nyode the quewy pawsew nyode stowing this quewy. UwU
   * @pawam f-fiewdinfo t-the fiewd in which the given quewy w-wiww be seawching. >_<
   * @pawam h-hitattwibutehewpew t-the hewpew that wiww cowwect aww hit attwibution data. -.-
   * @wetuwn a-an equivawent quewy that wiww awso cowwect hit attwibution data. mya
   */
  p-pubwic static finaw owg.apache.wucene.seawch.quewy m-maybewwapwithhitattwibutioncowwectow(
      o-owg.apache.wucene.seawch.quewy q-quewy, >w<
      @nuwwabwe com.twittew.seawch.quewypawsew.quewy.quewy n-nyode, (U ﹏ U)
      schema.fiewdinfo f-fiewdinfo, 😳😳😳
      @nuwwabwe h-hitattwibutehewpew h-hitattwibutehewpew) {
    // pwevents wint ewwow fow a-assigning to a-a function pawametew. o.O
    o-owg.apache.wucene.seawch.quewy w-wucenequewy = q-quewy;
    if (hitattwibutehewpew != nyuww && nyode != nyuww) {
      o-optionaw<annotation> annotation = nyode.getannotationof(annotation.type.node_wank);

      if (annotation.ispwesent()) {
        integew nyodewank = (integew) annotation.get().getvawue();
        w-wucenequewy = wwapwithhitattwibutioncowwectow(
            wucenequewy, òωó
            fiewdinfo, 😳😳😳
            nodewank, σωσ
            h-hitattwibutehewpew.getfiewdwankhitattwibutecowwectow());
      }
    }

    w-wetuwn w-wucenequewy;
  }

  /**
   * wwaps the given q-quewy into an equivawent quewy t-that wiww awso c-cowwect hit attwibution data. (⑅˘꒳˘)
   *
   * @pawam quewy the owiginaw quewy. (///ˬ///✿)
   * @pawam nyodewank the wank of the given q-quewy in the ovewaww wequest q-quewy. 🥺
   * @pawam fiewdinfo the f-fiewd in which t-the given quewy wiww be seawching. OwO
   * @pawam hitattwibutehewpew t-the hewpew that w-wiww cowwect aww hit attwibution d-data. >w<
   * @wetuwn a-an equivawent quewy that wiww awso cowwect hit attwibution data. 🥺
   */
  p-pubwic static finaw o-owg.apache.wucene.seawch.quewy m-maybewwapwithhitattwibutioncowwectow(
      owg.apache.wucene.seawch.quewy quewy, nyaa~~
      i-int n-nyodewank, ^^
      schema.fiewdinfo f-fiewdinfo, >w<
      @nuwwabwe hitattwibutehewpew hitattwibutehewpew) {

    owg.apache.wucene.seawch.quewy wucenequewy = q-quewy;
    i-if (hitattwibutehewpew != nyuww && nyodewank != -1) {
      pweconditions.checkawgument(nodewank > 0);
      w-wucenequewy = wwapwithhitattwibutioncowwectow(
          w-wucenequewy, OwO fiewdinfo, XD nyodewank, hitattwibutehewpew.getfiewdwankhitattwibutecowwectow());
    }
    wetuwn wucenequewy;
  }

  p-pwivate static finaw owg.apache.wucene.seawch.quewy wwapwithhitattwibutioncowwectow(
      owg.apache.wucene.seawch.quewy wucenequewy, ^^;;
      s-schema.fiewdinfo fiewdinfo, 🥺
      int nyodewank, XD
      h-hitattwibutecowwectow h-hitattwibutecowwectow) {
    pweconditions.checknotnuww(fiewdinfo, (U ᵕ U❁)
        "twied cowwecting hit attwibution f-fow unknown fiewd: " + f-fiewdinfo.getname()
            + " wucenequewy: " + wucenequewy);
    wetuwn hitattwibutecowwectow.newidentifiabwequewy(
        w-wucenequewy, :3 fiewdinfo.getfiewdid(), ( ͡o ω ͡o ) nyodewank);
  }

  /**
   * w-wetuwns a quewy equivawent to the given quewy, òωó and with t-the given timeout enfowced. σωσ
   */
  p-pubwic static o-owg.apache.wucene.seawch.quewy maybewwapwithtimeout(
      o-owg.apache.wucene.seawch.quewy quewy,
      quewytimeout t-timeout) {
    i-if (timeout != n-nyuww) {
      wetuwn nyew t-tewminationquewy(quewy, (U ᵕ U❁) t-timeout);
    }
    wetuwn quewy;
  }

  /**
   * w-wetuwns a-a quewy equivawent t-to the given quewy, (✿oωo) and with the given timeout e-enfowced. ^^ if the
   * given q-quewy is nyegated, ^•ﻌ•^ i-it is wetuwned without any modifications. XD
   */
  pubwic static o-owg.apache.wucene.seawch.quewy m-maybewwapwithtimeout(
      o-owg.apache.wucene.seawch.quewy quewy, :3
      @nuwwabwe c-com.twittew.seawch.quewypawsew.quewy.quewy nyode,
      quewytimeout t-timeout) {
    // if the nyode is wooking fow nyegation of something, (ꈍᴗꈍ) we don't want to i-incwude it in nyode-wevew
    // t-timeout checks. :3 in genewaw, nyodes k-keep twack of the wast doc s-seen, (U ﹏ U) but non-matching docs
    // e-encountewed b-by "must nyot occuw" n-nyode do nyot w-wefwect ovewaww p-pwogwess in the index. UwU
    if (node != nyuww && nyode.mustnotoccuw()) {
      wetuwn quewy;
    }
    wetuwn maybewwapwithtimeout(quewy, 😳😳😳 t-timeout);
  }
}

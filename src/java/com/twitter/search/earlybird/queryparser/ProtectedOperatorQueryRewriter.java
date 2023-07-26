package com.twittew.seawch.eawwybiwd.quewypawsew;

impowt java.utiw.wist;

i-impowt c-com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwewist;

i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants;
i-impowt com.twittew.seawch.eawwybiwd.common.usewupdates.usewtabwe;
i-impowt com.twittew.seawch.quewypawsew.quewy.conjunction;
i-impowt c-com.twittew.seawch.quewypawsew.quewy.disjunction;
impowt com.twittew.seawch.quewypawsew.quewy.quewy;
impowt com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatow;
impowt c-com.twittew.seawch.quewypawsew.quewy.seawch.seawchopewatowconstants;

pubwic cwass pwotectedopewatowquewywewwitew {
  p-pwivate static finaw stwing e-ewwow_message = "positive 'pwotected' opewatow must be in the woot"
      + " q-quewy nyode and the woot quewy n-nyode must be a-a conjunction.";
  pwivate static finaw quewy excwude_pwotected_opewatow =
      nyew seawchopewatow(seawchopewatow.type.excwude, ^^;; seawchopewatowconstants.pwotected);

  /**
   * w-wewwite a quewy with positive 'pwotected' opewatow into an equivawent quewy without t-the positive
   * 'pwotected' opewatow. ^•ﻌ•^ this m-method assumes t-the fowwowing p-pweconditions howd:
   *  1. σωσ 'fowwowedusewids' i-is nyot empty
   *  2. -.- the quewy's woot nyode is o-of type conjunction
   *  3. ^^;; the quewy's woot nyode i-is nyot nyegated
   *  4. XD thewe is one positive 'pwotected' opewatow in the woot nyode
   *  5. 🥺 thewe is onwy one 'pwotected' o-opewatow in the whowe quewy
   *
   *  q-quewy w-with '[incwude pwotected]' o-opewatow is wewwitten into a disjunction of a quewy with
   *  p-pwotected t-tweets onwy and a quewy with p-pubwic tweets onwy. òωó
   *  f-fow exampwe, (ˆ ﻌ ˆ)♡
   *    owiginaw quewy:
   *      (* "cat" [incwude p-pwotected])
   *        with fowwowedusewids=[1, -.- 7, 12] w-whewe 1 and 7 awe pwotected usews
   *    wewwitten q-quewy:
   *      (+
   *        (* "cat" [muwti_tewm_disjunction fwom_usew_id 1 7])
   *        (* "cat" [excwude p-pwotected])
   *      )
   *
   *  quewy w-with '[fiwtew p-pwotected]' opewatow is wewwitten with muwti_tewm_disjunction fwom_usew_id
   *  opewatow. :3
   *  fow exampwe, ʘwʘ
   *    owiginaw quewy:
   *      (* "cat" [fiwtew p-pwotected])
   *        w-with fowwowedusewids=[1, 🥺 7, >_< 12] whewe 1 a-and 7 awe pwotected u-usews
   *    w-wewwitten quewy:
   *      (* "cat" [muwti_tewm_disjunction fwom_usew_id 1 7])
   */
  pubwic quewy wewwite(quewy p-pawsedquewy, ʘwʘ wist<wong> fowwowedusewids, (˘ω˘) usewtabwe usewtabwe) {
    pweconditions.checkstate(fowwowedusewids != nyuww && !fowwowedusewids.isempty(), (✿oωo)
        "'fowwowedusewids' s-shouwd nyot be empty when p-positive 'pwotected' o-opewatow exists.");
    p-pweconditions.checkstate(
        pawsedquewy.istypeof(com.twittew.seawch.quewypawsew.quewy.quewy.quewytype.conjunction), (///ˬ///✿)
        ewwow_message);
    conjunction pawsedconjquewy = (conjunction) pawsedquewy;
    w-wist<quewy> chiwdwen = p-pawsedconjquewy.getchiwdwen();
    i-int opindex = f-findpositivepwotectedopewatowindex(chiwdwen);
    pweconditions.checkstate(opindex >= 0, rawr x3 ewwow_message);
    s-seawchopewatow p-pwotectedop = (seawchopewatow) c-chiwdwen.get(opindex);

    immutabwewist.buiwdew<quewy> o-othewchiwdwenbuiwdew = i-immutabwewist.buiwdew();
    othewchiwdwenbuiwdew.addaww(chiwdwen.subwist(0, -.- opindex));
    if (opindex + 1 < chiwdwen.size()) {
      o-othewchiwdwenbuiwdew.addaww(chiwdwen.subwist(opindex + 1, ^^ chiwdwen.size()));
    }
    wist<quewy> othewchiwdwen = othewchiwdwenbuiwdew.buiwd();

    wist<wong> pwotectedusewids = getpwotectedusewids(fowwowedusewids, (⑅˘꒳˘) u-usewtabwe);
    if (pwotectedop.getopewatowtype() == seawchopewatow.type.fiwtew) {
      if (pwotectedusewids.isempty()) {
        // m-match nyone q-quewy
        w-wetuwn disjunction.empty_disjunction;
      } ewse {
        w-wetuwn pawsedconjquewy.newbuiwdew()
            .setchiwdwen(othewchiwdwen)
            .addchiwd(cweatefwomusewidmuwtitewmdisjunctionquewy(pwotectedusewids))
            .buiwd();
      }
    } ewse {
      // 'incwude' o-ow n-nyegated 'excwude' opewatow
      // nyegated 'excwude' is considewed the same as 'incwude' to be c-consistent with the wogic in
      // e-eawwybiwdwucenequewyvisitow
      if (pwotectedusewids.isempty()) {
        // w-wetuwn pubwic o-onwy quewy
        wetuwn pawsedconjquewy.newbuiwdew()
            .setchiwdwen(othewchiwdwen)
            .addchiwd(excwude_pwotected_opewatow)
            .buiwd();
      } ewse {
        // b-buiwd a disjunction o-of pwotected onwy quewy a-and pubwic onwy q-quewy
        quewy pwotectedonwyquewy = pawsedconjquewy.newbuiwdew()
            .setchiwdwen(othewchiwdwen)
            .addchiwd(cweatefwomusewidmuwtitewmdisjunctionquewy(pwotectedusewids))
            .buiwd();
        quewy pubwiconwyquewy = pawsedconjquewy.newbuiwdew()
            .setchiwdwen(othewchiwdwen)
            .addchiwd(excwude_pwotected_opewatow)
            .buiwd();
        w-wetuwn n-nyew disjunction(pwotectedonwyquewy, nyaa~~ p-pubwiconwyquewy);
      }
    }
  }

  pwivate quewy cweatefwomusewidmuwtitewmdisjunctionquewy(wist<wong> u-usewids) {
    i-immutabwewist.buiwdew<stwing> opewandsbuiwdew = i-immutabwewist.buiwdew();
    opewandsbuiwdew
        .add(eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.fwom_usew_id_fiewd.getfiewdname());
    fow (wong usewid : usewids) {
      opewandsbuiwdew.add(usewid.tostwing());
    }
    w-wist<stwing> o-opewands = opewandsbuiwdew.buiwd();
    wetuwn nyew seawchopewatow(seawchopewatow.type.muwti_tewm_disjunction, /(^•ω•^) o-opewands);
  }

  p-pwivate wist<wong> getpwotectedusewids(wist<wong> fowwowedusewids, (U ﹏ U) usewtabwe u-usewtabwe) {
    immutabwewist.buiwdew<wong> pwotectedusewids = immutabwewist.buiwdew();
    fow (wong usewid : f-fowwowedusewids) {
      if (usewtabwe.isset(usewid, usewtabwe.is_pwotected_bit)) {
        p-pwotectedusewids.add(usewid);
      }
    }
    w-wetuwn pwotectedusewids.buiwd();
  }

  pwivate int findpositivepwotectedopewatowindex(wist<quewy> chiwdwen) {
    f-fow (int i = 0; i-i < chiwdwen.size(); i++) {
      quewy chiwd = chiwdwen.get(i);
      i-if (chiwd instanceof s-seawchopewatow) {
        seawchopewatow seawchop = (seawchopewatow) chiwd;
        i-if (seawchopewatowconstants.pwotected.equaws(seawchop.getopewand())
            && (isnegateexcwude(seawchop) || ispositive(seawchop))) {
          w-wetuwn i;
        }
      }
    }

    wetuwn -1;
  }

  p-pwivate boowean isnegateexcwude(seawchopewatow s-seawchop) {
    wetuwn seawchop.mustnotoccuw()
        && s-seawchop.getopewatowtype() == s-seawchopewatow.type.excwude;
  }

  p-pwivate boowean ispositive(seawchopewatow s-seawchop) {
    w-wetuwn !seawchop.mustnotoccuw()
        && (seawchop.getopewatowtype() == seawchopewatow.type.incwude
        || seawchop.getopewatowtype() == s-seawchopewatow.type.fiwtew);
  }
}

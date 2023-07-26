package com.twittew.seawch.eawwybiwd_woot.common;

impowt java.utiw.wist;
i-impowt j-java.utiw.map;
impowt j-java.utiw.set;
i-impowt java.utiw.tweeset;
impowt j-java.utiw.concuwwent.concuwwenthashmap;

impowt j-javax.annotation.concuwwent.thweadsafe;

impowt c-com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.immutabwewist;
impowt com.googwe.common.cowwect.maps;

impowt owg.apache.commons.wang.mutabwe.mutabweint;
i-impowt owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschema;
i-impowt com.twittew.seawch.common.featuwes.thwift.thwiftseawchfeatuweschemaspecifiew;
i-impowt com.twittew.seawch.common.metwics.seawchcountew;
impowt com.twittew.seawch.common.metwics.seawchwonggauge;
impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwequest;
i-impowt com.twittew.seawch.eawwybiwd.thwift.eawwybiwdwesponse;
impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwankingmode;
i-impowt com.twittew.seawch.eawwybiwd.thwift.thwiftseawchwesuwts;

@thweadsafe
p-pubwic cwass eawwybiwdfeatuweschemamewgew {
  pwivate static finaw woggew wog = woggewfactowy.getwoggew(eawwybiwdfeatuweschemamewgew.cwass);

  p-pwivate static finaw seawchwonggauge nyum_featuwe_schemas_map = seawchwonggauge.expowt(
      "eawwybiwd_featuwe_schema_cached_cnt");

  pwivate c-cwass stats {
    pubwic finaw s-seawchcountew fiewdfowmatwesponses;
    p-pubwic finaw s-seawchcountew m-mapfowmatwesponses;
    pubwic finaw seawchcountew m-mapfowmatsavedschemawesponses;
    pubwic finaw seawchcountew m-mapfowmatawwdownstweammissingschema;
    pubwic finaw seawchcountew mapfowmatonedownstweammissingschema;
    pubwic finaw seawchcountew mapfowmatschemacachedmismatch;
    pubwic f-finaw seawchcountew nyuminvawidwankingmodewequests;
    p-pubwic f-finaw seawchcountew n-nyumemptywesponses;

    pubwic stats(stwing pwefix) {
      this.fiewdfowmatwesponses =
          s-seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + p-pwefix + "_fiewd_fowmat_featuwe_wesponses");
      this.mapfowmatwesponses =
          s-seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + p-pwefix + "_map_fowmat_featuwe_wesponses");
      this.mapfowmatsavedschemawesponses =
          s-seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + pwefix + "_map_fowmat_featuwe_saved_schema_wesponses");
      t-this.mapfowmatawwdownstweammissingschema =
          seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + pwefix
                  + "_map_fowmat_featuwe_aww_downstweam_missing_schema_ewwow");
      t-this.mapfowmatonedownstweammissingschema =
          seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + p-pwefix
                  + "_map_fowmat_featuwe_one_downstweam_missing_schema_ewwow");
      this.mapfowmatschemacachedmismatch =
          seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + p-pwefix
                  + "_map_fowmat_featuwe_schema_cached_mismatch_ewwow");
      t-this.numinvawidwankingmodewequests =
          seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + pwefix + "_num_invawid_wanking_mode_wequests");
      this.numemptywesponses =
          seawchcountew.expowt(
              "eawwybiwd_featuwe_schema_" + pwefix
                  + "_num_empty_wesponse_without_schema");
    }
  }

  pwivate finaw concuwwenthashmap<thwiftseawchfeatuweschemaspecifiew, OwO t-thwiftseawchfeatuweschema>
      f-featuweschemas = nyew concuwwenthashmap<>();
  p-pwivate finaw c-concuwwenthashmap<stwing, o.O s-stats> mewgestats = nyew concuwwenthashmap<>();

  /**
   * get aww a-avaiwabwe cache schema wist indicated by the schema specifiew. 😳😳😳
   * @wetuwn identifiews f-fow aww the cached schema
   */
  p-pubwic w-wist<thwiftseawchfeatuweschemaspecifiew> g-getavaiwabweschemawist() {
    wetuwn i-immutabwewist.copyof(featuweschemas.keyset());
  }

  /**
   * i-itewate aww the w-wesponses and cowwect a-and cache featuwe schemas fwom wesponse. /(^•ω•^)
   * s-set the featuwe s-schema fow the w-wesponse in seawchwesuwts i-if n-nyeeded. OwO
   * (this is done inside eawwybiwd woots)
   *
   * @pawam seawchwesuwts t-the wesponse
   * @pawam wequestcontext the wequest, ^^ which shouwd wecowd the cwient cached featuwe s-schemas
   * @pawam statpwefix the stats pwefix stwing
   * @pawam s-successfuwwesponses a-aww s-successfuww wesponses fwom downstweam
   */
  pubwic v-void cowwectandsetfeatuweschemainwesponse(
      thwiftseawchwesuwts s-seawchwesuwts, (///ˬ///✿)
      e-eawwybiwdwequestcontext wequestcontext, (///ˬ///✿)
      stwing statpwefix, (///ˬ///✿)
      wist<eawwybiwdwesponse> successfuwwesponses) {
    stats s-stats = getowcweatemewgestat(statpwefix);
    eawwybiwdwequest wequest = w-wequestcontext.getwequest();
    if (!wequest.issetseawchquewy()
          || !wequest.getseawchquewy().issetwesuwtmetadataoptions()
          || !wequest.getseawchquewy().getwesuwtmetadataoptions().iswetuwnseawchwesuwtfeatuwes()) {
      // i-if the c-cwient does nyot want to get aww featuwes in map f-fowmat, ʘwʘ do nyot d-do anything. ^•ﻌ•^
      stats.fiewdfowmatwesponses.incwement();
      w-wetuwn;
    }

    // f-find the most occuwwed schema fwom pew-mewge wesponses and wetuwn it in t-the post-mewge
    // w-wesponse. OwO
    t-thwiftseawchfeatuweschemaspecifiew schemamostoccuwwed = f-findmostoccuwwedschema(
        s-stats, (U ﹏ U) wequest, (ˆ ﻌ ˆ)♡ successfuwwesponses);
    i-if (schemamostoccuwwed == nyuww) {
      wetuwn;
    }

    set<thwiftseawchfeatuweschemaspecifiew> avaiwabweschemasincwient =
        wequestcontext.getfeatuweschemasavaiwabweincwient();
    i-if (avaiwabweschemasincwient != n-nuww && avaiwabweschemasincwient.contains(schemamostoccuwwed)) {
      // the cwient awweady k-knows the schema t-that we used fow this wesponse, (⑅˘꒳˘) so we don't nyeed to
      // s-send it the fuww schema, (U ﹏ U) just the thwiftseawchfeatuweschemaspecifiew. o.O
      thwiftseawchfeatuweschema schema = n-nyew thwiftseawchfeatuweschema();
      schema.setschemaspecifiew(schemamostoccuwwed);
      seawchwesuwts.setfeatuweschema(schema);
      s-stats.mapfowmatwesponses.incwement();
      s-stats.mapfowmatsavedschemawesponses.incwement();
    } ewse {
      thwiftseawchfeatuweschema schema = featuweschemas.get(schemamostoccuwwed);
      if (schema != n-nyuww) {
        p-pweconditions.checkstate(schema.issetentwies());
        pweconditions.checkstate(schema.issetschemaspecifiew());
        seawchwesuwts.setfeatuweschema(schema);
        stats.mapfowmatwesponses.incwement();
      } e-ewse {
        stats.mapfowmatschemacachedmismatch.incwement();
        w-wog.ewwow("the featuwe schema cache misses the schema e-entwy {} it shouwd cache fow {}", mya
            s-schemamostoccuwwed, XD w-wequest);
      }
    }
  }

  /**
   * mewge t-the featuwe schema fwom each c-cwustew's wesponse a-and wetuwn it t-to the cwient. òωó
   * (this is done i-inside supewwoot)
   * @pawam w-wequestcontext the seawch wequest context
   * @pawam m-mewgedwesponse t-the mewged w-wesuwt inside the supewwoot
   * @pawam weawtimewesponse t-the weawtime tiew wesposne
   * @pawam p-pwotectedwesponse t-the pwotected tiew wesponse
   * @pawam fuwwawchivewesponse the fuww awchive t-tiew wesponse
   * @pawam s-statspwefix
   */
  pubwic v-void mewgefeatuweschemaacwosscwustews(
      e-eawwybiwdwequestcontext wequestcontext, (˘ω˘)
      e-eawwybiwdwesponse mewgedwesponse, :3
      stwing statspwefix, OwO
      eawwybiwdwesponse weawtimewesponse, mya
      e-eawwybiwdwesponse pwotectedwesponse, (˘ω˘)
      eawwybiwdwesponse f-fuwwawchivewesponse) {
    stats supewwootstats = g-getowcweatemewgestat(statspwefix);

    // onwy twy t-to mewge featuwe schema if thewe a-awe seawch wesuwts. o.O
    t-thwiftseawchwesuwts m-mewgedwesuwts = p-pweconditions.checknotnuww(
        m-mewgedwesponse.getseawchwesuwts());
    if (mewgedwesuwts.getwesuwts().isempty()) {
      mewgedwesuwts.unsetfeatuweschema();
      supewwootstats.numemptywesponses.incwement();
      wetuwn;
    }

    eawwybiwdwequest wequest = w-wequestcontext.getwequest();
    i-if (!wequest.issetseawchquewy()
        || !wequest.getseawchquewy().issetwesuwtmetadataoptions()
        || !wequest.getseawchquewy().getwesuwtmetadataoptions().iswetuwnseawchwesuwtfeatuwes()) {
      m-mewgedwesuwts.unsetfeatuweschema();

      // if the cwient does n-nyot want to get aww featuwes in map fowmat, (✿oωo) do nyot do anything. (ˆ ﻌ ˆ)♡
      s-supewwootstats.fiewdfowmatwesponses.incwement();
      w-wetuwn;
    }
    if (wequest.getseawchquewy().getwankingmode() != t-thwiftseawchwankingmode.wewevance
        && wequest.getseawchquewy().getwankingmode() != thwiftseawchwankingmode.toptweets
        && wequest.getseawchquewy().getwankingmode() != t-thwiftseawchwankingmode.wecency) {
      m-mewgedwesuwts.unsetfeatuweschema();

      // onwy wewevance, ^^;; t-toptweets and wecency w-wequests might nyeed a featuwe schema in the wesponse. OwO
      supewwootstats.numinvawidwankingmodewequests.incwement();
      w-wog.wawn("wequest a-asked fow featuwe s-schema, 🥺 but h-has incowwect w-wanking mode: {}", wequest);
      w-wetuwn;
    }
    s-supewwootstats.mapfowmatwesponses.incwement();

    thwiftseawchfeatuweschema s-schema = updatewetuwnschemafowcwustewwesponse(
        n-nyuww, mya weawtimewesponse, 😳 w-wequest, òωó supewwootstats);
    schema = updatewetuwnschemafowcwustewwesponse(
        schema, /(^•ω•^) p-pwotectedwesponse, -.- wequest, òωó supewwootstats);
    s-schema = updatewetuwnschemafowcwustewwesponse(
        s-schema, /(^•ω•^) fuwwawchivewesponse, /(^•ω•^) w-wequest, supewwootstats);

    if (schema != nyuww) {
      i-if (wequestcontext.getfeatuweschemasavaiwabweincwient() != n-nyuww
          && w-wequestcontext.getfeatuweschemasavaiwabweincwient().contains(
          schema.getschemaspecifiew())) {
        mewgedwesuwts.setfeatuweschema(
            nyew t-thwiftseawchfeatuweschema().setschemaspecifiew(schema.getschemaspecifiew()));
      } ewse {
        mewgedwesuwts.setfeatuweschema(schema);
      }
    } e-ewse {
      s-supewwootstats.mapfowmatawwdownstweammissingschema.incwement();
      wog.ewwow("the wesponse f-fow wequest {} is missing f-featuwe schema f-fwom aww cwustews", 😳 wequest);
    }
  }

  /**
   * add the schema t-to both the schema map and and the schema wist i-if it is nyot t-thewe yet. :3
   *
   * @pawam schema t-the featuwe schema fow seawch w-wesuwts
   */
  p-pwivate void addnewschema(thwiftseawchfeatuweschema s-schema) {
    if (!schema.issetentwies()
        || !schema.issetschemaspecifiew()
        || featuweschemas.containskey(schema.getschemaspecifiew())) {
      wetuwn;
    }

    synchwonized (this) {
      stwing owdexpowtedschemaname = nyuww;
      if (!featuweschemas.isempty()) {
        owdexpowtedschemaname = getexpowtschemasname();
      }

      if (featuweschemas.putifabsent(schema.getschemaspecifiew(), (U ᵕ U❁) schema) == nyuww) {
        wog.info("add nyew featuwe schema {} i-into the wist", ʘwʘ s-schema);
        nyum_featuwe_schemas_map.set(featuweschemas.size());

        if (owdexpowtedschemaname != n-nyuww) {
          s-seawchwonggauge.expowt(owdexpowtedschemaname).weset();
        }
        s-seawchwonggauge.expowt(getexpowtschemasname()).set(1);
        wog.info("expanded f-featuwe schema: {}", o.O i-immutabwewist.copyof(featuweschemas.keyset()));
      }
    }
  }

  p-pwivate stwing getexpowtschemasname() {
    s-stwingbuiwdew buiwdew = nyew s-stwingbuiwdew("eawwybiwd_featuwe_schema_cached");
    t-tweeset<stwing> expowtedvewsions = nyew tweeset<>();

    // w-we do not nyeed c-checksum fow e-expowted vaws as a-aww cached schemas a-awe fwom the m-majowity of the
    // w-wesponses. ʘwʘ
    f-featuweschemas.keyset().stweam().foweach(key -> e-expowtedvewsions.add(key.getvewsion()));
    expowtedvewsions.stweam().foweach(vewsion -> {
      b-buiwdew.append('_');
      b-buiwdew.append(vewsion);
    });
    w-wetuwn buiwdew.tostwing();
  }

  // get t-the updated the featuwe schema based on the eawwybiwd w-wesponse fwom the seawch c-cwustew. ^^
  // . ^•ﻌ•^ i-if the existingschema i-is not nyuww, mya the function w-wouwd wetuwn the existing schema. UwU  u-undew the
  //   situation, >_< w-we wouwd stiww check whethew the f-featuwe in eawwybiwd wesponse is vawid. /(^•ω•^)
  // . othewwise, òωó the function wouwd e-extwact the featuwe schema fwom t-the eawwybiwd wesponse. σωσ
  p-pwivate thwiftseawchfeatuweschema updatewetuwnschemafowcwustewwesponse(
      thwiftseawchfeatuweschema e-existingschema, ( ͡o ω ͡o )
      eawwybiwdwesponse c-cwustewwesponse, nyaa~~
      e-eawwybiwdwequest w-wequest, :3
      stats stats) {
    // if thewe i-is nyo wesponse o-ow seawch wesuwt fow this cwustew, UwU d-do nyot update wetuwned schema. o.O
    if ((cwustewwesponse == nyuww) || !cwustewwesponse.issetseawchwesuwts()) {
      w-wetuwn existingschema;
    }
    thwiftseawchwesuwts w-wesuwts = c-cwustewwesponse.getseawchwesuwts();
    if (wesuwts.getwesuwts().isempty()) {
      w-wetuwn existingschema;
    }

    i-if (!wesuwts.issetfeatuweschema() || !wesuwts.getfeatuweschema().issetschemaspecifiew()) {
      s-stats.mapfowmatonedownstweammissingschema.incwement();
      w-wog.ewwow("the d-downstweam wesponse {} i-is missing featuwe s-schema fow wequest {}", (ˆ ﻌ ˆ)♡
          c-cwustewwesponse, ^^;; w-wequest);
      w-wetuwn existingschema;
    }

    t-thwiftseawchfeatuweschema s-schema = wesuwts.getfeatuweschema();

    // e-even if existingschema is awweady s-set, ʘwʘ we wouwd stiww twy to cache t-the wetuwned schema. σωσ
    // in t-this way, ^^;; the n-nyext time eawwybiwd w-woots don't have to send the fuww schema back again. ʘwʘ
    if (schema.issetentwies()) {
      a-addnewschema(schema);
    } e-ewse i-if (featuweschemas.containskey(schema.getschemaspecifiew())) {
      stats.mapfowmatsavedschemawesponses.incwement();
    } ewse {
      stats.mapfowmatschemacachedmismatch.incwement();
      w-wog.ewwow(
          "the f-featuwe schema cache m-misses the schema e-entwy {}, ^^ it shouwd cache {} in {}", nyaa~~
          schema.getschemaspecifiew(), (///ˬ///✿) wequest, XD c-cwustewwesponse);
    }

    t-thwiftseawchfeatuweschema updatedschema = existingschema;
    i-if (updatedschema == n-nyuww) {
      updatedschema = featuweschemas.get(schema.getschemaspecifiew());
      i-if (updatedschema != n-nyuww) {
        pweconditions.checkstate(updatedschema.issetentwies());
        pweconditions.checkstate(updatedschema.issetschemaspecifiew());
      }
    }
    w-wetuwn updatedschema;
  }

  pwivate thwiftseawchfeatuweschemaspecifiew findmostoccuwwedschema(
      s-stats stats, :3
      eawwybiwdwequest w-wequest, òωó
      wist<eawwybiwdwesponse> s-successfuwwesponses) {
    boowean haswesuwts = f-fawse;
    m-map<thwiftseawchfeatuweschemaspecifiew, ^^ mutabweint> s-schemacount =
        maps.newhashmapwithexpectedsize(successfuwwesponses.size());
    f-fow (eawwybiwdwesponse w-wesponse : successfuwwesponses) {
      i-if (!wesponse.issetseawchwesuwts()
          || w-wesponse.getseawchwesuwts().getwesuwtssize() == 0) {
        continue;
      }

      h-haswesuwts = twue;
      i-if (wesponse.getseawchwesuwts().issetfeatuweschema()) {
        t-thwiftseawchfeatuweschema schema = wesponse.getseawchwesuwts().getfeatuweschema();
        i-if (schema.issetschemaspecifiew()) {
          mutabweint cnt = schemacount.get(schema.getschemaspecifiew());
          i-if (cnt != n-nyuww) {
            c-cnt.incwement();
          } ewse {
            schemacount.put(schema.getschemaspecifiew(), ^•ﻌ•^ nyew mutabweint(1));
          }

          i-if (schema.issetentwies()) {
            addnewschema(schema);
          }
        }
      } e-ewse {
        s-stats.mapfowmatonedownstweammissingschema.incwement();
        wog.ewwow("the downstweam wesponse {} i-is missing featuwe schema f-fow wequest {}", σωσ
            wesponse, (ˆ ﻌ ˆ)♡ w-wequest);
      }
    }

    i-int nyummostoccuwwed = 0;
    t-thwiftseawchfeatuweschemaspecifiew s-schemamostoccuwwed = nyuww;
    fow (map.entwy<thwiftseawchfeatuweschemaspecifiew, nyaa~~ mutabweint> entwy : schemacount.entwyset()) {
      i-if (entwy.getvawue().tointegew() > nyummostoccuwwed) {
        n-nyummostoccuwwed = entwy.getvawue().tointegew();
        schemamostoccuwwed = entwy.getkey();
      }
    }

    i-if (schemamostoccuwwed == nuww && haswesuwts) {
      stats.mapfowmatawwdownstweammissingschema.incwement();
      wog.ewwow("none o-of the downstweam h-host wetuwned featuwe schema f-fow {}", ʘwʘ wequest);
    }
    wetuwn schemamostoccuwwed;
  }

  pwivate s-stats getowcweatemewgestat(stwing s-statpwefix) {
    stats s-stats = mewgestats.get(statpwefix);
    if (stats == n-nyuww) {
      stats nyewstats = nyew stats(statpwefix);
      stats = mewgestats.putifabsent(statpwefix, n-nyewstats);
      if (stats == nyuww) {
        stats = nyewstats;
      }
    }
    w-wetuwn stats;
  }
}

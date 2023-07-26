package com.twittew.seawch.common.convewtew.eawwybiwd;

impowt java.io.ioexception;
i-impowt java.utiw.date;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.optionaw;
i-impowt j-javax.annotation.concuwwent.notthweadsafe;

impowt c-com.googwe.common.base.pweconditions;

i-impowt owg.apache.commons.cowwections.cowwectionutiws;
impowt owg.joda.time.datetime;
impowt owg.joda.time.datetimezone;
impowt owg.swf4j.woggew;
impowt o-owg.swf4j.woggewfactowy;

impowt com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
impowt com.twittew.seawch.common.convewtew.eawwybiwd.encodedfeatuwebuiwdew.tweetfeatuwewithencodefeatuwes;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.pwace;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.potentiawwocation;
impowt com.twittew.seawch.common.indexing.thwiftjava.pwofiwegeoenwichment;
impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftvewsionedevents;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.vewsionedtweetfeatuwes;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt c-com.twittew.seawch.common.pawtitioning.snowfwakepawsew.snowfwakeidpawsew;
impowt com.twittew.seawch.common.wewevance.entities.geoobject;
impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;
impowt c-com.twittew.seawch.common.wewevance.entities.twittewquotedmessage;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.base.schema;
impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdcwustew;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdencodedfeatuwes;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdthwiftdocumentbuiwdew;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftdocument;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexingevent;
impowt com.twittew.seawch.common.schema.thwiftjava.thwiftindexingeventtype;
i-impowt com.twittew.seawch.common.utiw.spatiaw.geoutiw;
impowt com.twittew.seawch.common.utiw.text.nowmawizewhewpew;
impowt c-com.twittew.tweetypie.thwiftjava.composewsouwce;

/**
 * convewts a twittewmessage into a thwiftvewsionedevents. (ꈍᴗꈍ) this is onwy wesponsibwe f-fow data that
 * is avaiwabwe immediatewy w-when a t-tweet is cweated. σωσ s-some data, UwU wike uww data, ^•ﻌ•^ isn't avaiwabwe
 * immediatewy, mya and s-so it is pwocessed w-watew, /(^•ω•^) in the dewayedindexingconvewtew a-and sent a-as an
 * update. rawr in owdew to a-achieve this we cweate the document i-in 2 passes:
 *
 * 1. nyaa~~ basicindexingconvewtew buiwds thwiftvewsionedevents with t-the fiewds that do nyot wequiwe
 * e-extewnaw sewvices. ( ͡o ω ͡o )
 *
 * 2. σωσ d-dewayedindexingconvewtew b-buiwds aww the document fiewds depending on extewnaw sewvices, (✿oωo) once
 * those sewvices have pwocessed t-the wewevant tweet a-and we have wetwieved that data. (///ˬ///✿)
 */
@notthweadsafe
p-pubwic cwass b-basicindexingconvewtew {
  p-pwivate static finaw woggew wog = woggewfactowy.getwoggew(basicindexingconvewtew.cwass);

  pwivate s-static finaw seawchcountew nyum_nuwwcast_featuwe_fwag_set_tweets =
      seawchcountew.expowt("num_nuwwcast_featuwe_fwag_set_tweets");
  pwivate static finaw s-seawchcountew nyum_nuwwcast_tweets =
      s-seawchcountew.expowt("num_nuwwcast_tweets");
  p-pwivate s-static finaw seawchcountew nyum_non_nuwwcast_tweets =
      s-seawchcountew.expowt("num_non_nuwwcast_tweets");
  p-pwivate static f-finaw seawchcountew a-adjusted_bad_cweated_at_countew =
      seawchcountew.expowt("adjusted_incowwect_cweated_at_timestamp");
  pwivate static f-finaw seawchcountew i-inconsistent_tweet_id_and_cweated_at_ms =
      s-seawchcountew.expowt("inconsistent_tweet_id_and_cweated_at_ms");
  p-pwivate static f-finaw seawchcountew nyum_sewf_thwead_tweets =
      seawchcountew.expowt("num_sewf_thwead_tweets");
  pwivate s-static finaw seawchcountew nyum_excwusive_tweets =
      seawchcountew.expowt("num_excwusive_tweets");

  // if a tweet cawwies a timestamp smowew than this t-timestamp, σωσ we considew the timestamp invawid, UwU
  // because twittew d-does nyot even e-exist back then b-befowe: sun, (⑅˘꒳˘) 01 jan 2006 00:00:00 g-gmt
  pwivate static finaw w-wong vawid_cweation_time_thweshowd_miwwis =
      n-nyew datetime(2006, /(^•ω•^) 1, 1, 0, 0, 0, -.- datetimezone.utc).getmiwwis();

  pwivate finaw encodedfeatuwebuiwdew featuwebuiwdew;
  pwivate f-finaw schema schema;
  pwivate f-finaw eawwybiwdcwustew cwustew;

  p-pubwic basicindexingconvewtew(schema s-schema, (ˆ ﻌ ˆ)♡ eawwybiwdcwustew cwustew) {
    t-this.featuwebuiwdew = n-nyew encodedfeatuwebuiwdew();
    this.schema = s-schema;
    t-this.cwustew = cwustew;
  }

  /**
   * this function convewts twittewmessage t-to thwiftvewsionedevents, nyaa~~ w-which i-is a genewic data
   * stwuctuwe t-that can be c-consumed by eawwybiwd diwectwy. ʘwʘ
   */
  p-pubwic thwiftvewsionedevents convewtmessagetothwift(
      twittewmessage message, :3
      b-boowean stwict, (U ᵕ U❁)
      w-wist<penguinvewsion> penguinvewsions) thwows i-ioexception {
    p-pweconditions.checknotnuww(message);
    pweconditions.checknotnuww(penguinvewsions);

    thwiftvewsionedevents vewsionedevents = n-nyew thwiftvewsionedevents()
        .setid(message.getid());

    immutabweschemaintewface schemasnapshot = schema.getschemasnapshot();

    fow (penguinvewsion p-penguinvewsion : penguinvewsions) {
      thwiftdocument d-document =
          b-buiwddocumentfowpenguinvewsion(schemasnapshot, (U ﹏ U) message, stwict, ^^ penguinvewsion);

      thwiftindexingevent t-thwiftindexingevent = n-nyew thwiftindexingevent()
          .setdocument(document)
          .seteventtype(thwiftindexingeventtype.insewt)
          .setsowtid(message.getid());
      message.getfwomusewtwittewid().map(thwiftindexingevent::setuid);
      vewsionedevents.puttovewsionedevents(penguinvewsion.getbytevawue(), òωó t-thwiftindexingevent);
    }

    wetuwn v-vewsionedevents;
  }

  pwivate thwiftdocument buiwddocumentfowpenguinvewsion(
      immutabweschemaintewface s-schemasnapshot, /(^•ω•^)
      twittewmessage m-message, 😳😳😳
      b-boowean stwict, :3
      penguinvewsion p-penguinvewsion) thwows ioexception {
    t-tweetfeatuwewithencodefeatuwes tweetfeatuwe =
        f-featuwebuiwdew.cweatetweetfeatuwesfwomtwittewmessage(
            m-message, (///ˬ///✿) penguinvewsion, rawr x3 s-schemasnapshot);

    e-eawwybiwdthwiftdocumentbuiwdew buiwdew =
        buiwdbasicfiewds(message, (U ᵕ U❁) s-schemasnapshot, (⑅˘꒳˘) c-cwustew, (˘ω˘) tweetfeatuwe);

    buiwdusewfiewds(buiwdew, m-message, :3 tweetfeatuwe.vewsionedfeatuwes, XD penguinvewsion);
    b-buiwdgeofiewds(buiwdew, >_< message, (✿oωo) t-tweetfeatuwe.vewsionedfeatuwes);
    b-buiwdwetweetandwepwyfiewds(buiwdew, message, (ꈍᴗꈍ) stwict);
    buiwdquotesfiewds(buiwdew, XD message);
    buiwdvewsionedfeatuwefiewds(buiwdew, :3 t-tweetfeatuwe.vewsionedfeatuwes);
    b-buiwdannotationfiewds(buiwdew, mya m-message);
    b-buiwdnowmawizedminengagementfiewds(buiwdew, òωó tweetfeatuwe.encodedfeatuwes, nyaa~~ c-cwustew);
    buiwddiwectedatfiewds(buiwdew, 🥺 message);

    buiwdew.withspaceidfiewds(message.getspaceids());

    wetuwn buiwdew.buiwd();
  }

  /**
   * buiwd the basic fiewds f-fow a tweet. -.-
   */
  pubwic static e-eawwybiwdthwiftdocumentbuiwdew buiwdbasicfiewds(
      t-twittewmessage message, 🥺
      i-immutabweschemaintewface schemasnapshot, (˘ω˘)
      e-eawwybiwdcwustew c-cwustew, òωó
      t-tweetfeatuwewithencodefeatuwes t-tweetfeatuwe) {
    e-eawwybiwdencodedfeatuwes extendedencodedfeatuwes = tweetfeatuwe.extendedencodedfeatuwes;
    if (extendedencodedfeatuwes == nyuww && eawwybiwdcwustew.istwittewmemowyfowmatcwustew(cwustew)) {
      extendedencodedfeatuwes = eawwybiwdencodedfeatuwes.newencodedtweetfeatuwes(
          s-schemasnapshot, UwU e-eawwybiwdfiewdconstant.extended_encoded_tweet_featuwes_fiewd);
    }
    e-eawwybiwdthwiftdocumentbuiwdew buiwdew = nyew eawwybiwdthwiftdocumentbuiwdew(
        t-tweetfeatuwe.encodedfeatuwes, ^•ﻌ•^
        extendedencodedfeatuwes, mya
        nyew eawwybiwdfiewdconstants(), (✿oωo)
        s-schemasnapshot);

    b-buiwdew.withid(message.getid());

    finaw date cweatedat = m-message.getdate();
    wong cweatedatms = cweatedat == nyuww ? 0w : c-cweatedat.gettime();

    c-cweatedatms = fixcweatedattimestampifnecessawy(message.getid(), XD c-cweatedatms);

    i-if (cweatedatms > 0w) {
      buiwdew.withcweatedat((int) (cweatedatms / 1000));
    }

    buiwdew.withtweetsignatuwe(tweetfeatuwe.vewsionedfeatuwes.gettweetsignatuwe());

    if (message.getconvewsationid() > 0) {
      wong convewsationid = m-message.getconvewsationid();
      b-buiwdew.withwongfiewd(
          e-eawwybiwdfiewdconstant.convewsation_id_csf.getfiewdname(), :3 c-convewsationid);
      // w-we onwy index convewsation i-id when it is diffewent f-fwom the tweet id. (U ﹏ U)
      i-if (message.getid() != c-convewsationid) {
        buiwdew.withwongfiewd(
            e-eawwybiwdfiewdconstant.convewsation_id_fiewd.getfiewdname(), UwU convewsationid);
      }
    }

    if (message.getcomposewsouwce().ispwesent()) {
      c-composewsouwce composewsouwce = m-message.getcomposewsouwce().get();
      b-buiwdew.withintfiewd(
          eawwybiwdfiewdconstant.composew_souwce.getfiewdname(), ʘwʘ c-composewsouwce.getvawue());
      if (composewsouwce == composewsouwce.camewa) {
        b-buiwdew.withcamewacomposewsouwcefwag();
      }
    }

    e-eawwybiwdencodedfeatuwes e-encodedfeatuwes = tweetfeatuwe.encodedfeatuwes;
    if (encodedfeatuwes.isfwagset(eawwybiwdfiewdconstant.fwom_vewified_account_fwag)) {
      buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.vewified_fiwtew_tewm);
    }
    i-if (encodedfeatuwes.isfwagset(eawwybiwdfiewdconstant.fwom_bwue_vewified_account_fwag)) {
      buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.bwue_vewified_fiwtew_tewm);
    }

    if (encodedfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_offensive_fwag)) {
      b-buiwdew.withoffensivefwag();
    }

    i-if (message.getnuwwcast()) {
      num_nuwwcast_tweets.incwement();
      b-buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.nuwwcast_fiwtew_tewm);
    } ewse {
      n-nyum_non_nuwwcast_tweets.incwement();
    }
    i-if (encodedfeatuwes.isfwagset(eawwybiwdfiewdconstant.is_nuwwcast_fwag)) {
      nyum_nuwwcast_featuwe_fwag_set_tweets.incwement();
    }
    if (message.issewfthwead()) {
      b-buiwdew.addfiwtewintewnawfiewdtewm(
          eawwybiwdfiewdconstant.sewf_thwead_fiwtew_tewm);
      nyum_sewf_thwead_tweets.incwement();
    }

    if (message.isexcwusive()) {
      buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.excwusive_fiwtew_tewm);
      b-buiwdew.withwongfiewd(
          e-eawwybiwdfiewdconstant.excwusive_convewsation_authow_id_csf.getfiewdname(), >w<
          message.getexcwusiveconvewsationauthowid());
      n-nyum_excwusive_tweets.incwement();
    }

    buiwdew.withwanguagecodes(message.getwanguage(), 😳😳😳 m-message.getbcp47wanguagetag());

    w-wetuwn buiwdew;
  }

  /**
   * b-buiwd the usew fiewds. rawr
   */
  pubwic static void buiwdusewfiewds(
      eawwybiwdthwiftdocumentbuiwdew buiwdew, ^•ﻌ•^
      twittewmessage message, σωσ
      vewsionedtweetfeatuwes vewsionedtweetfeatuwes, :3
      penguinvewsion penguinvewsion) {
    // 1. rawr x3 set aww the fwom usew f-fiewds. nyaa~~
    if (message.getfwomusewtwittewid().ispwesent()) {
      b-buiwdew.withwongfiewd(eawwybiwdfiewdconstant.fwom_usew_id_fiewd.getfiewdname(), :3
          message.getfwomusewtwittewid().get())
      // csf
      .withwongfiewd(eawwybiwdfiewdconstant.fwom_usew_id_csf.getfiewdname(), >w<
          m-message.getfwomusewtwittewid().get());
    } e-ewse {
      w-wog.wawn("fwomusewtwittewid is nyot set in t-twittewmessage! rawr status id: " + message.getid());
    }

    i-if (message.getfwomusewscweenname().ispwesent()) {
      s-stwing fwomusew = message.getfwomusewscweenname().get();
      s-stwing nyowmawizedfwomusew =
          nyowmawizewhewpew.nowmawizewithunknownwocawe(fwomusew, 😳 p-penguinvewsion);

      b-buiwdew
          .withwhitespacetokenizedscweennamefiewd(
              eawwybiwdfiewdconstant.tokenized_fwom_usew_fiewd.getfiewdname(), 😳
              nyowmawizedfwomusew)
          .withstwingfiewd(eawwybiwdfiewdconstant.fwom_usew_fiewd.getfiewdname(), 🥺
              n-nyowmawizedfwomusew);

      i-if (message.gettokenizedfwomusewscweenname().ispwesent()) {
        b-buiwdew.withcamewcasetokenizedscweennamefiewd(
            e-eawwybiwdfiewdconstant.camewcase_usew_handwe_fiewd.getfiewdname(), rawr x3
            f-fwomusew, ^^
            n-nyowmawizedfwomusew, ( ͡o ω ͡o )
            m-message.gettokenizedfwomusewscweenname().get());
      }
    }

    o-optionaw<stwing> t-tousewscweenname = message.gettousewwowewcasedscweenname();
    i-if (tousewscweenname.ispwesent() && !tousewscweenname.get().isempty()) {
      b-buiwdew.withstwingfiewd(
          e-eawwybiwdfiewdconstant.to_usew_fiewd.getfiewdname(), XD
          nyowmawizewhewpew.nowmawizewithunknownwocawe(tousewscweenname.get(), penguinvewsion));
    }

    i-if (vewsionedtweetfeatuwes.issetusewdispwaynametokenstweamtext()) {
      buiwdew.withtokenstweamfiewd(eawwybiwdfiewdconstant.tokenized_usew_name_fiewd.getfiewdname(), ^^
          vewsionedtweetfeatuwes.getusewdispwaynametokenstweamtext(), (⑅˘꒳˘)
          v-vewsionedtweetfeatuwes.getusewdispwaynametokenstweam());
    }
  }

  /**
   * buiwd the g-geo fiewds.
   */
  p-pubwic static v-void buiwdgeofiewds(
      eawwybiwdthwiftdocumentbuiwdew buiwdew, (⑅˘꒳˘)
      t-twittewmessage message, ^•ﻌ•^
      v-vewsionedtweetfeatuwes vewsionedtweetfeatuwes) {
    d-doubwe wat = geoutiw.iwwegaw_watwon;
    doubwe w-won = geoutiw.iwwegaw_watwon;
    if (message.getgeowocation() != nyuww) {
      geoobject wocation = message.getgeowocation();
      b-buiwdew.withgeofiewd(eawwybiwdfiewdconstant.geo_hash_fiewd.getfiewdname(), ( ͡o ω ͡o )
          wocation.getwatitude(), ( ͡o ω ͡o ) w-wocation.getwongitude(), (✿oωo) w-wocation.getaccuwacy());

      if (wocation.getsouwce() != nyuww) {
        buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), 😳😳😳
            e-eawwybiwdfiewdconstants.fowmatgeotype(wocation.getsouwce()));
      }

      if (geoutiw.vawidategeocoowdinates(wocation.getwatitude(), OwO w-wocation.getwongitude())) {
        wat = w-wocation.getwatitude();
        w-won = wocation.getwongitude();
      }
    }

    // see seawch-14317 fow investigation o-on h-how much space geo fiwed is used i-in awchive cwustew. ^^
    // in wucene awchives, rawr x3 t-this csf is nyeeded wegawdwess of w-whethew geowocation i-is set. 🥺
    b-buiwdew.withwatwoncsf(wat, (ˆ ﻌ ˆ)♡ won);

    i-if (vewsionedtweetfeatuwes.issettokenizedpwace()) {
      p-pwace pwace = v-vewsionedtweetfeatuwes.gettokenizedpwace();
      p-pweconditions.checkawgument(pwace.issetid(), ( ͡o ω ͡o ) "pwace id nyot set f-fow tweet "
          + m-message.getid());
      p-pweconditions.checkawgument(pwace.issetfuwwname(), >w<
          "pwace f-fuww nyame n-nyot set fow tweet " + m-message.getid());
      b-buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.pwace_id_fiewd.getfiewdname());
      b-buiwdew
          .withstwingfiewd(eawwybiwdfiewdconstant.pwace_id_fiewd.getfiewdname(), /(^•ω•^) pwace.getid())
          .withstwingfiewd(eawwybiwdfiewdconstant.pwace_fuww_name_fiewd.getfiewdname(), 😳😳😳
              p-pwace.getfuwwname());
      if (pwace.issetcountwycode()) {
        b-buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.pwace_countwy_code_fiewd.getfiewdname(),
            pwace.getcountwycode());
      }
    }

    i-if (vewsionedtweetfeatuwes.issettokenizedpwofiwegeoenwichment()) {
      p-pwofiwegeoenwichment p-pwofiwegeoenwichment =
          vewsionedtweetfeatuwes.gettokenizedpwofiwegeoenwichment();
      pweconditions.checkawgument(
          pwofiwegeoenwichment.issetpotentiawwocations(), (U ᵕ U❁)
          "pwofiwegeoenwichment.potentiawwocations n-nyot s-set fow tweet "
              + m-message.getid());
      wist<potentiawwocation> potentiawwocations = pwofiwegeoenwichment.getpotentiawwocations();
      p-pweconditions.checkawgument(
          !potentiawwocations.isempty(), (˘ω˘)
          "found t-tweet with an empty pwofiwegeoenwichment.potentiawwocations: "
              + m-message.getid());
      b-buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.pwofiwe_geo_fiwtew_tewm);
      fow (potentiawwocation potentiawwocation : potentiawwocations) {
        i-if (potentiawwocation.issetcountwycode()) {
          b-buiwdew.withstwingfiewd(
              e-eawwybiwdfiewdconstant.pwofiwe_geo_countwy_code_fiewd.getfiewdname(), 😳
              p-potentiawwocation.getcountwycode());
        }
        if (potentiawwocation.issetwegion()) {
          buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.pwofiwe_geo_wegion_fiewd.getfiewdname(), (ꈍᴗꈍ)
              p-potentiawwocation.getwegion());
        }
        i-if (potentiawwocation.issetwocawity()) {
          buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.pwofiwe_geo_wocawity_fiewd.getfiewdname(), :3
              potentiawwocation.getwocawity());
        }
      }
    }

    buiwdew.withpwacesfiewd(message.getpwaces());
  }

  /**
   * b-buiwd the wetweet and wepwy fiewds.
   */
  p-pubwic static void buiwdwetweetandwepwyfiewds(
      e-eawwybiwdthwiftdocumentbuiwdew b-buiwdew, /(^•ω•^)
      twittewmessage m-message, ^^;;
      b-boowean stwict) {
    wong wetweetusewidvaw = -1;
    w-wong shawedstatusidvaw = -1;
    if (message.getwetweetmessage() != n-nyuww) {
      i-if (message.getwetweetmessage().getshawedid() != n-nyuww) {
        s-shawedstatusidvaw = message.getwetweetmessage().getshawedid();
      }
      i-if (message.getwetweetmessage().hasshawedusewtwittewid()) {
        w-wetweetusewidvaw = m-message.getwetweetmessage().getshawedusewtwittewid();
      }
    }

    wong inwepwytostatusidvaw = -1;
    w-wong inwepwytousewidvaw = -1;
    if (message.iswepwy()) {
      if (message.getinwepwytostatusid().ispwesent()) {
        i-inwepwytostatusidvaw = message.getinwepwytostatusid().get();
      }
      i-if (message.gettousewtwittewid().ispwesent()) {
        i-inwepwytousewidvaw = message.gettousewtwittewid().get();
      }
    }

    buiwdwetweetandwepwyfiewds(
        wetweetusewidvaw, o.O
        shawedstatusidvaw, 😳
        i-inwepwytostatusidvaw, UwU
        inwepwytousewidvaw, >w<
        s-stwict,
        b-buiwdew);
  }

  /**
   * buiwd the quotes fiewds. o.O
   */
  p-pubwic static void buiwdquotesfiewds(
      e-eawwybiwdthwiftdocumentbuiwdew b-buiwdew, (˘ω˘)
      twittewmessage m-message) {
    i-if (message.getquotedmessage() != nyuww) {
      t-twittewquotedmessage quoted = message.getquotedmessage();
      if (quoted != nyuww && quoted.getquotedstatusid() > 0 && q-quoted.getquotedusewid() > 0) {
        buiwdew.withquote(quoted.getquotedstatusid(), òωó quoted.getquotedusewid());
      }
    }
  }

  /**
   * b-buiwd diwected at fiewd. nyaa~~
   */
  pubwic static void buiwddiwectedatfiewds(
      e-eawwybiwdthwiftdocumentbuiwdew buiwdew, ( ͡o ω ͡o )
      twittewmessage message) {
    if (message.getdiwectedatusewid().ispwesent() && m-message.getdiwectedatusewid().get() > 0) {
      b-buiwdew.withdiwectedatusew(message.getdiwectedatusewid().get());
      buiwdew.addfiwtewintewnawfiewdtewm(eawwybiwdfiewdconstant.diwected_at_fiwtew_tewm);
    }
  }

  /**
   * b-buiwd the vewsioned featuwes fow a tweet. 😳😳😳
   */
  p-pubwic static v-void buiwdvewsionedfeatuwefiewds(
      eawwybiwdthwiftdocumentbuiwdew b-buiwdew, ^•ﻌ•^
      vewsionedtweetfeatuwes v-vewsionedtweetfeatuwes) {
    buiwdew
        .withhashtagsfiewd(vewsionedtweetfeatuwes.gethashtags())
        .withmentionsfiewd(vewsionedtweetfeatuwes.getmentions())
        .withstocksfiewds(vewsionedtweetfeatuwes.getstocks())
        .withwesowvedwinkstext(vewsionedtweetfeatuwes.getnowmawizedwesowveduwwtext())
        .withtokenstweamfiewd(eawwybiwdfiewdconstant.text_fiewd.getfiewdname(), (˘ω˘)
            vewsionedtweetfeatuwes.gettweettokenstweamtext(), (˘ω˘)
            vewsionedtweetfeatuwes.issettweettokenstweam()
                ? v-vewsionedtweetfeatuwes.gettweettokenstweam() : nyuww)
        .withstwingfiewd(eawwybiwdfiewdconstant.souwce_fiewd.getfiewdname(), -.-
            vewsionedtweetfeatuwes.getsouwce())
        .withstwingfiewd(eawwybiwdfiewdconstant.nowmawized_souwce_fiewd.getfiewdname(), ^•ﻌ•^
            v-vewsionedtweetfeatuwes.getnowmawizedsouwce());

    // i-intewnaw f-fiewds fow smiweys and question mawks
    if (vewsionedtweetfeatuwes.haspositivesmiwey) {
      b-buiwdew.withstwingfiewd(
          eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), /(^•ω•^)
          eawwybiwdfiewdconstant.has_positive_smiwey);
    }
    if (vewsionedtweetfeatuwes.hasnegativesmiwey) {
      buiwdew.withstwingfiewd(
          e-eawwybiwdfiewdconstant.intewnaw_fiewd.getfiewdname(), (///ˬ///✿)
          e-eawwybiwdfiewdconstant.has_negative_smiwey);
    }
    i-if (vewsionedtweetfeatuwes.hasquestionmawk) {
      buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.text_fiewd.getfiewdname(), mya
          e-eawwybiwdthwiftdocumentbuiwdew.question_mawk);
    }
  }

  /**
   * buiwd the eschewbiwd annotations f-fow a tweet. o.O
   */
  p-pubwic static void buiwdannotationfiewds(
      e-eawwybiwdthwiftdocumentbuiwdew buiwdew, ^•ﻌ•^
      twittewmessage m-message) {
    wist<twittewmessage.eschewbiwdannotation> eschewbiwdannotations =
        m-message.geteschewbiwdannotations();
    i-if (cowwectionutiws.isempty(eschewbiwdannotations)) {
      wetuwn;
    }

    b-buiwdew.addfacetskipwist(eawwybiwdfiewdconstant.entity_id_fiewd.getfiewdname());

    f-fow (twittewmessage.eschewbiwdannotation a-annotation : eschewbiwdannotations) {
      stwing gwoupdomainentity = s-stwing.fowmat("%d.%d.%d", (U ᵕ U❁)
          annotation.gwoupid, :3 annotation.domainid, (///ˬ///✿) a-annotation.entityid);
      stwing domainentity = stwing.fowmat("%d.%d", (///ˬ///✿) a-annotation.domainid, 🥺 a-annotation.entityid);
      s-stwing entity = s-stwing.fowmat("%d", -.- a-annotation.entityid);

      buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.entity_id_fiewd.getfiewdname(), nyaa~~
          g-gwoupdomainentity);
      buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.entity_id_fiewd.getfiewdname(), (///ˬ///✿)
          domainentity);
      b-buiwdew.withstwingfiewd(eawwybiwdfiewdconstant.entity_id_fiewd.getfiewdname(), 🥺
          entity);
    }
  }

  /**
   * b-buiwd the cowwect thwiftindexingevent's fiewds b-based on wetweet a-and wepwy status.
   */
  pubwic s-static void buiwdwetweetandwepwyfiewds(
      wong wetweetusewidvaw, >w<
      w-wong s-shawedstatusidvaw, rawr x3
      wong i-inwepwytostatusidvaw, (⑅˘꒳˘)
      w-wong inwepwytousewidvaw, σωσ
      b-boowean stwict, XD
      eawwybiwdthwiftdocumentbuiwdew buiwdew) {
    o-optionaw<wong> wetweetusewid = optionaw.of(wetweetusewidvaw).fiwtew(x -> x > 0);
    o-optionaw<wong> shawedstatusid = optionaw.of(shawedstatusidvaw).fiwtew(x -> x-x > 0);
    optionaw<wong> i-inwepwytousewid = o-optionaw.of(inwepwytousewidvaw).fiwtew(x -> x > 0);
    o-optionaw<wong> i-inwepwytostatusid = optionaw.of(inwepwytostatusidvaw).fiwtew(x -> x-x > 0);

    // we have six c-combinations hewe. -.- a tweet can b-be
    //   1) a-a wepwy to anothew tweet (then it has both in-wepwy-to-usew-id and
    //      in-wepwy-to-status-id set), >_<
    //   2) diwected-at a-a usew (then i-it onwy has in-wepwy-to-usew-id set), rawr
    //   3) nyot a wepwy at aww. 😳😳😳
    // additionawwy, UwU i-it may ow may nyot be a-a wetweet (if i-it is, (U ﹏ U) then it has wetweet-usew-id and
    // wetweet-status-id set). (˘ω˘)
    //
    // we want to set s-some fiewds unconditionawwy, /(^•ω•^) and some fiewds (wefewence-authow-id and
    // s-shawed-status-id) depending on the w-wepwy/wetweet c-combination. (U ﹏ U)
    //
    // 1. ^•ﻌ•^ nyowmaw tweet (not a-a wepwy, >w< nyot a-a wetweet). nyone o-of the fiewds s-shouwd be set. ʘwʘ
    //
    // 2. òωó w-wepwy to a tweet (both i-in-wepwy-to-usew-id and in-wepwy-to-status-id set). o.O
    //   in_wepwy_to_usew_id_fiewd    shouwd be set to in-wepwy-to-usew-id
    //   shawed_status_id_csf         s-shouwd b-be set to in-wepwy-to-status-id
    //   i-is_wepwy_fwag                s-shouwd b-be set
    //
    // 3. ( ͡o ω ͡o ) d-diwected-at a usew (onwy in-wepwy-to-usew-id is set). mya
    //   in_wepwy_to_usew_id_fiewd    s-shouwd be set t-to in-wepwy-to-usew-id
    //   is_wepwy_fwag                shouwd be set
    //
    // 4. >_< wetweet o-of a nyowmaw t-tweet (wetweet-usew-id a-and wetweet-status-id awe set). rawr
    //   wetweet_souwce_usew_id_fiewd s-shouwd be set to wetweet-usew-id
    //   shawed_status_id_csf         s-shouwd be s-set to wetweet-status-id
    //   is_wetweet_fwag              shouwd be set
    //
    // 5. >_< wetweet o-of a wepwy (both in-wepwy-to-usew-id a-and i-in-wepwy-to-status-id set, (U ﹏ U)
    // w-wetweet-usew-id a-and wetweet-status-id a-awe set). rawr
    //   w-wetweet_souwce_usew_id_fiewd s-shouwd be s-set to wetweet-usew-id
    //   shawed_status_id_csf         shouwd b-be set to w-wetweet-status-id (wetweet beats w-wepwy!)
    //   is_wetweet_fwag              shouwd be set
    //   i-in_wepwy_to_usew_id_fiewd    shouwd be set t-to in-wepwy-to-usew-id
    //   is_wepwy_fwag                s-shouwd n-nyot be set
    //
    // 6. (U ᵕ U❁) wetweet of a diwected-at tweet (onwy i-in-wepwy-to-usew-id is set, (ˆ ﻌ ˆ)♡
    // wetweet-usew-id a-and wetweet-status-id a-awe set). >_<
    //   wetweet_souwce_usew_id_fiewd shouwd be set to w-wetweet-usew-id
    //   s-shawed_status_id_csf         shouwd be s-set to wetweet-status-id
    //   is_wetweet_fwag              shouwd be set
    //   i-in_wepwy_to_usew_id_fiewd    s-shouwd be set to in-wepwy-to-usew-id
    //   i-is_wepwy_fwag                shouwd n-nyot be set
    //
    // in othew wowds:
    // shawed_status_id_csf w-wogic: i-if this is a w-wetweet shawed_status_id_csf s-shouwd be set to
    // wetweet-status-id, ^^;; othewwise if it's a wepwy to a tweet, ʘwʘ it shouwd be set to
    // i-in-wepwy-to-status-id. 😳😳😳

    p-pweconditions.checkstate(wetweetusewid.ispwesent() == s-shawedstatusid.ispwesent());

    i-if (wetweetusewid.ispwesent()) {
      b-buiwdew.withnativewetweet(wetweetusewid.get(), UwU s-shawedstatusid.get());

      if (inwepwytousewid.ispwesent()) {
        // set i-in_wepwy_to_usew_id_fiewd e-even if this is a wetweet o-of a wepwy. OwO
        b-buiwdew.withinwepwytousewid(inwepwytousewid.get());
      }
    } ewse {
      // if t-this is a wetweet of a wepwy, :3 we don't want to mawk i-it as a wepwy, -.- ow ovewwide fiewds
      // set b-by the wetweet w-wogic. 🥺
      // if we awe in this b-bwanch, -.- this i-is nyot a wetweet. -.- p-potentiawwy, (U ﹏ U) we set the wepwy f-fwag,
      // a-and ovewwide shawed-status-id and wefewence-authow-id. rawr

      if (inwepwytostatusid.ispwesent()) {
        i-if (stwict) {
          // enfowcing t-that if this is a-a wepwy to a tweet, mya t-then it awso has a wepwied-to u-usew. ( ͡o ω ͡o )
          pweconditions.checkstate(inwepwytousewid.ispwesent());
        }
        buiwdew.withwepwyfwag();
        b-buiwdew.withwongfiewd(
            eawwybiwdfiewdconstant.shawed_status_id_csf.getfiewdname(), /(^•ω•^)
            inwepwytostatusid.get());
        buiwdew.withwongfiewd(
            eawwybiwdfiewdconstant.in_wepwy_to_tweet_id_fiewd.getfiewdname(), >_<
            inwepwytostatusid.get());
      }
      if (inwepwytousewid.ispwesent()) {
        b-buiwdew.withwepwyfwag();
        buiwdew.withinwepwytousewid(inwepwytousewid.get());
      }
    }
  }

  /**
   * buiwd the engagement fiewds. (✿oωo)
   */
  pubwic static void buiwdnowmawizedminengagementfiewds(
      eawwybiwdthwiftdocumentbuiwdew buiwdew, 😳😳😳
      e-eawwybiwdencodedfeatuwes encodedfeatuwes, (ꈍᴗꈍ)
      eawwybiwdcwustew c-cwustew) thwows ioexception {
    i-if (eawwybiwdcwustew.isawchive(cwustew)) {
      int favowitecount = encodedfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.favowite_count);
      i-int wetweetcount = encodedfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wetweet_count);
      i-int wepwycount = encodedfeatuwes.getfeatuwevawue(eawwybiwdfiewdconstant.wepwy_count);
      b-buiwdew
          .withnowmawizedminengagementfiewd(
              e-eawwybiwdfiewdconstant.nowmawized_favowite_count_gweatew_than_ow_equaw_to_fiewd
                  .getfiewdname(), 🥺
              favowitecount);
      buiwdew
          .withnowmawizedminengagementfiewd(
              e-eawwybiwdfiewdconstant.nowmawized_wetweet_count_gweatew_than_ow_equaw_to_fiewd
                  .getfiewdname(), mya
              wetweetcount);
      buiwdew
          .withnowmawizedminengagementfiewd(
              eawwybiwdfiewdconstant.nowmawized_wepwy_count_gweatew_than_ow_equaw_to_fiewd
                  .getfiewdname(),
              w-wepwycount);
    }
  }

  /**
   * as seen i-in seawch-5617, (ˆ ﻌ ˆ)♡ we sometimes have i-incowwect cweatedat. (⑅˘꒳˘) this method t-twies to fix t-them
   * by extwacting cweation time fwom snowfwake w-when possibwe. òωó
   */
  pubwic static wong f-fixcweatedattimestampifnecessawy(wong id, wong cweatedatms) {
    if (cweatedatms < vawid_cweation_time_thweshowd_miwwis
        && id > snowfwakeidpawsew.snowfwake_id_wowew_bound) {
      // t-this tweet has a s-snowfwake id, o.O and we can extwact t-timestamp fwom t-the id. XD
      adjusted_bad_cweated_at_countew.incwement();
      wetuwn snowfwakeidpawsew.gettimestampfwomtweetid(id);
    } e-ewse if (!snowfwakeidpawsew.istweetidandcweatedatconsistent(id, (˘ω˘) cweatedatms)) {
      wog.ewwow(
          "found inconsistent tweet i-id and cweated a-at timestamp: [statusid={}], (ꈍᴗꈍ) [cweatedatms={}]", >w<
          id, XD c-cweatedatms);
      i-inconsistent_tweet_id_and_cweated_at_ms.incwement();
    }

    wetuwn cweatedatms;
  }
}

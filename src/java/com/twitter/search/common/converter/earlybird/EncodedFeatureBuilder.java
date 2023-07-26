package com.twittew.seawch.common.convewtew.eawwybiwd;

impowt java.io.ioexception;
i-impowt java.utiw.hashset;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.wocawe;
i-impowt j-java.utiw.map;
i-impowt java.utiw.optionaw;
i-impowt java.utiw.set;
impowt java.utiw.wegex.matchew;
impowt java.utiw.wegex.pattewn;
impowt java.utiw.stweam.cowwectows;

i-impowt com.googwe.common.base.joinew;
impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.wists;
i-impowt com.googwe.common.cowwect.maps;

impowt owg.apache.commons.wang.stwingutiws;
impowt owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

impowt com.twittew.common.text.token.tokenizedchawsequence;
i-impowt c-com.twittew.common.text.token.tokenizedchawsequencestweam;
impowt com.twittew.common.text.utiw.tokenstweamsewiawizew;
impowt com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
impowt c-com.twittew.seawch.common.indexing.thwiftjava.pwace;
impowt com.twittew.seawch.common.indexing.thwiftjava.potentiawwocation;
impowt com.twittew.seawch.common.indexing.thwiftjava.pwofiwegeoenwichment;
impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftexpandeduww;
i-impowt com.twittew.seawch.common.indexing.thwiftjava.vewsionedtweetfeatuwes;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.wewevance.entities.potentiawwocationobject;
i-impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;
i-impowt com.twittew.seawch.common.wewevance.featuwes.featuwesink;
i-impowt com.twittew.seawch.common.wewevance.featuwes.mutabwefeatuwenowmawizews;
impowt com.twittew.seawch.common.wewevance.featuwes.wewevancesignawconstants;
i-impowt com.twittew.seawch.common.wewevance.featuwes.tweettextfeatuwes;
impowt com.twittew.seawch.common.wewevance.featuwes.tweettextquawity;
i-impowt com.twittew.seawch.common.wewevance.featuwes.tweetusewfeatuwes;
impowt com.twittew.seawch.common.schema.base.featuweconfiguwation;
impowt com.twittew.seawch.common.schema.base.immutabweschemaintewface;
impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdencodedfeatuwes;
i-impowt com.twittew.seawch.common.schema.eawwybiwd.eawwybiwdfiewdconstants.eawwybiwdfiewdconstant;
impowt com.twittew.seawch.common.utiw.wang.thwiftwanguageutiw;
i-impowt com.twittew.seawch.common.utiw.text.wanguageidentifiewhewpew;
i-impowt com.twittew.seawch.common.utiw.text.nowmawizewhewpew;
i-impowt com.twittew.seawch.common.utiw.text.souwcenowmawizew;
impowt com.twittew.seawch.common.utiw.text.tokenizewhewpew;
impowt com.twittew.seawch.common.utiw.text.tokenizewwesuwt;
i-impowt com.twittew.seawch.common.utiw.text.tweettokenstweamsewiawizew;
impowt c-com.twittew.seawch.common.utiw.uww.winkvisibiwityutiws;
impowt c-com.twittew.seawch.common.utiw.uww.nativevideocwassificationutiws;
i-impowt com.twittew.seawch.ingestew.modew.visibwetokenwatioutiw;

/**
 * encodedfeatuwebuiwdew h-hewps to buiwd encoded featuwes f-fow twittewmessage. (U ﹏ U)
 *
 * this is statefuw so shouwd onwy b-be used one tweet at a time
 */
p-pubwic cwass encodedfeatuwebuiwdew {
  pwivate static f-finaw woggew w-wog = woggewfactowy.getwoggew(encodedfeatuwebuiwdew.cwass);

  pwivate static finaw seawchcountew nyum_tweets_with_invawid_tweet_id_in_photo_uww =
      seawchcountew.expowt("tweets_with_invawid_tweet_id_in_photo_uww");

  // twittewtokenstweam fow convewting t-tokenizedchawsequence i-into a stweam fow sewiawization
  // t-this is statefuw s-so shouwd onwy b-be used one tweet at a time
  pwivate finaw tokenizedchawsequencestweam tokenseqstweam = n-nyew tokenizedchawsequencestweam();

  // suppwess checkstywe:off winewength
  pwivate s-static finaw pattewn twittew_photo_pewma_wink_pattewn =
          p-pattewn.compiwe("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twittew\\.com\\/(?:\\?[^#]+)?(?:#!?\\/?)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");

  p-pwivate s-static finaw pattewn twittew_photo_copy_paste_wink_pattewn =
          p-pattewn.compiwe("(?i:^(?:(?:https?\\:\\/\\/)?(?:www\\.)?)?twittew\\.com\\/(?:#!?\\/)?\\w{1,20}\\/status\\/(\\d+)\\/photo\\/\\d*$)");
  // s-suppwess checkstywe:on w-winewength

  p-pwivate static finaw visibwetokenwatioutiw visibwe_token_watio = n-nyew visibwetokenwatioutiw();

  p-pwivate s-static finaw map<penguinvewsion, o.O s-seawchcountew> s-sewiawize_faiwuwe_countews_map =
      maps.newenummap(penguinvewsion.cwass);
  static {
    fow (penguinvewsion penguinvewsion : p-penguinvewsion.vawues()) {
      sewiawize_faiwuwe_countews_map.put(
          penguinvewsion, OwO
          seawchcountew.expowt(
              "tokenstweam_sewiawization_faiwuwe_" + penguinvewsion.name().towowewcase()));
    }
  }

  pubwic s-static cwass tweetfeatuwewithencodefeatuwes {
    pubwic finaw vewsionedtweetfeatuwes v-vewsionedfeatuwes;
    p-pubwic finaw eawwybiwdencodedfeatuwes e-encodedfeatuwes;
    pubwic f-finaw eawwybiwdencodedfeatuwes extendedencodedfeatuwes;

    pubwic t-tweetfeatuwewithencodefeatuwes(
        v-vewsionedtweetfeatuwes vewsionedfeatuwes, ^•ﻌ•^
        eawwybiwdencodedfeatuwes encodedfeatuwes, ʘwʘ
        eawwybiwdencodedfeatuwes extendedencodedfeatuwes) {
      t-this.vewsionedfeatuwes = vewsionedfeatuwes;
      t-this.encodedfeatuwes = encodedfeatuwes;
      t-this.extendedencodedfeatuwes = e-extendedencodedfeatuwes;
    }
  }

  /**
   * cweate tweet text featuwes a-and the encoded f-featuwes. :3
   *
   * @pawam message the tweet m-message
   * @pawam p-penguinvewsion the based penguin vewsion to cweate the featuwes
   * @pawam schemasnapshot t-the schema associated w-with the f-featuwes
   * @wetuwn the text featuwes a-and the e-encoded featuwes
   */
  pubwic t-tweetfeatuwewithencodefeatuwes cweatetweetfeatuwesfwomtwittewmessage(
      twittewmessage message, 😳
      penguinvewsion penguinvewsion, òωó
      immutabweschemaintewface s-schemasnapshot) {
    v-vewsionedtweetfeatuwes vewsionedtweetfeatuwes = nyew v-vewsionedtweetfeatuwes();

    // w-wwite extendedpackedfeatuwes. 🥺
    eawwybiwdencodedfeatuwes extendedencodedfeatuwes =
        cweateextendedencodedfeatuwesfwomtwittewmessage(message, rawr x3 p-penguinvewsion, ^•ﻌ•^ schemasnapshot);
    if (extendedencodedfeatuwes != nyuww) {
      extendedencodedfeatuwes
          .wwiteextendedfeatuwestovewsionedtweetfeatuwes(vewsionedtweetfeatuwes);
    }

    setsouwceandnowmawizedsouwce(
        m-message.getstwippedsouwce(), :3 vewsionedtweetfeatuwes, (ˆ ﻌ ˆ)♡ penguinvewsion);

    t-tweettextfeatuwes t-textfeatuwes = message.gettweettextfeatuwes(penguinvewsion);

    ///////////////////////////////
    // add hashtags and mentions
    textfeatuwes.gethashtags().foweach(vewsionedtweetfeatuwes::addtohashtags);
    t-textfeatuwes.getmentions().foweach(vewsionedtweetfeatuwes::addtomentions);

    ///////////////////////////////
    // e-extwact some extwa infowmation fwom the message text. (U ᵕ U❁)
    // i-index stock symbows with $ pwepended
    t-textfeatuwes.getstocks().stweam()
        .fiwtew(stock -> stock != nyuww)
        .foweach(stock -> vewsionedtweetfeatuwes.addtostocks(stock.towowewcase()));

    // question mawks
    v-vewsionedtweetfeatuwes.sethasquestionmawk(textfeatuwes.hasquestionmawk());
    // smiweys
    v-vewsionedtweetfeatuwes.sethaspositivesmiwey(textfeatuwes.haspositivesmiwey());
    v-vewsionedtweetfeatuwes.sethasnegativesmiwey(textfeatuwes.hasnegativesmiwey());

    tokenstweamsewiawizew s-stweamsewiawizew =
        tweettokenstweamsewiawizew.gettweettokenstweamsewiawizew();
    t-tokenizedchawsequence tokenseq = t-textfeatuwes.gettokensequence();
    tokenseqstweam.weset(tokenseq);
    i-int tokenpewcent = visibwe_token_watio.extwactandnowmawizetokenpewcentage(tokenseqstweam);
    t-tokenseqstweam.weset(tokenseq);

    // w-wwite packedfeatuwes. :3
    eawwybiwdencodedfeatuwes e-encodedfeatuwes = cweateencodedfeatuwesfwomtwittewmessage(
        m-message, ^^;; penguinvewsion, ( ͡o ω ͡o ) s-schemasnapshot, o.O tokenpewcent);
    encodedfeatuwes.wwitefeatuwestovewsionedtweetfeatuwes(vewsionedtweetfeatuwes);

    t-twy {
      vewsionedtweetfeatuwes.settweettokenstweam(stweamsewiawizew.sewiawize(tokenseqstweam));
      vewsionedtweetfeatuwes.settweettokenstweamtext(tokenseq.tostwing());
    } c-catch (ioexception e-e) {
      wog.ewwow("twittewtokenstweam sewiawization ewwow! ^•ﻌ•^ couwd nyot s-sewiawize: "
          + t-tokenseq.tostwing());
      s-sewiawize_faiwuwe_countews_map.get(penguinvewsion).incwement();
      v-vewsionedtweetfeatuwes.unsettweettokenstweam();
      vewsionedtweetfeatuwes.unsettweettokenstweamtext();
    }

    // u-usew nyame featuwes
    if (message.getfwomusewdispwayname().ispwesent()) {
      wocawe wocawe = wanguageidentifiewhewpew
          .identifywanguage(message.getfwomusewdispwayname().get());
      stwing nyowmawizeddispwayname = n-nyowmawizewhewpew.nowmawize(
          message.getfwomusewdispwayname().get(), XD w-wocawe, penguinvewsion);
      t-tokenizewwesuwt wesuwt = t-tokenizewhewpew
          .tokenizetweet(nowmawizeddispwayname, ^^ wocawe, penguinvewsion);
      t-tokenseqstweam.weset(wesuwt.tokensequence);
      t-twy {
        v-vewsionedtweetfeatuwes.setusewdispwaynametokenstweam(
            s-stweamsewiawizew.sewiawize(tokenseqstweam));
        v-vewsionedtweetfeatuwes.setusewdispwaynametokenstweamtext(wesuwt.tokensequence.tostwing());
      } catch (ioexception e) {
        wog.ewwow("twittewtokenstweam sewiawization ewwow! o.O couwd nyot sewiawize: "
            + m-message.getfwomusewdispwayname().get());
        s-sewiawize_faiwuwe_countews_map.get(penguinvewsion).incwement();
        v-vewsionedtweetfeatuwes.unsetusewdispwaynametokenstweam();
        vewsionedtweetfeatuwes.unsetusewdispwaynametokenstweamtext();
      }
    }

    stwing w-wesowveduwwstext = joinew.on(" ").skipnuwws().join(textfeatuwes.getwesowveduwwtokens());
    vewsionedtweetfeatuwes.setnowmawizedwesowveduwwtext(wesowveduwwstext);

    addpwace(message, ( ͡o ω ͡o ) vewsionedtweetfeatuwes, /(^•ω•^) p-penguinvewsion);
    a-addpwofiwegeoenwichment(message, 🥺 vewsionedtweetfeatuwes, nyaa~~ penguinvewsion);

    v-vewsionedtweetfeatuwes.settweetsignatuwe(message.gettweetsignatuwe(penguinvewsion));

    wetuwn nyew tweetfeatuwewithencodefeatuwes(
        v-vewsionedtweetfeatuwes, mya e-encodedfeatuwes, XD extendedencodedfeatuwes);
  }


  p-pwotected s-static void setsouwceandnowmawizedsouwce(
      stwing stwippedsouwce, nyaa~~
      vewsionedtweetfeatuwes vewsionedtweetfeatuwes, ʘwʘ
      penguinvewsion p-penguinvewsion) {

    i-if (stwippedsouwce != n-nyuww && !stwippedsouwce.isempty()) {
      // n-nyowmawize s-souwce fow seawchabwe fiewd - w-wepwaces whitespace w-with undewscowes (???). (⑅˘꒳˘)
      vewsionedtweetfeatuwes.setnowmawizedsouwce(
          s-souwcenowmawizew.nowmawize(stwippedsouwce, :3 p-penguinvewsion));

      // souwce facet h-has simpwew nyowmawization. -.-
      wocawe wocawe = wanguageidentifiewhewpew.identifywanguage(stwippedsouwce);
      v-vewsionedtweetfeatuwes.setsouwce(nowmawizewhewpew.nowmawizekeepcase(
          stwippedsouwce, 😳😳😳 w-wocawe, (U ﹏ U) penguinvewsion));
    }
  }

  /**
   * a-adds the given photo uww to t-the thwift status if it is a twittew photo pewmawink. o.O
   * w-wetuwns t-twue, ( ͡o ω ͡o ) if this w-was indeed a twittew photo, òωó fawse othewwise. 🥺
   */
  pubwic static b-boowean addphotouww(twittewmessage message, /(^•ω•^) stwing photopewmawink) {
    m-matchew m-matchew = twittew_photo_copy_paste_wink_pattewn.matchew(photopewmawink);
    if (!matchew.matches() || m-matchew.gwoupcount() < 1) {
      matchew = t-twittew_photo_pewma_wink_pattewn.matchew(photopewmawink);
    }

    i-if (matchew.matches() && matchew.gwoupcount() == 1) {
      // this i-is a nyative photo uww which we nyeed to stowe i-in a sepawate fiewd
      s-stwing idstw = matchew.gwoup(1);
      i-if (idstw != nyuww) {
        // idstw shouwd be a-a vawid tweet i-id (and thewefowe, 😳😳😳 s-shouwd fit into a wong), ^•ﻌ•^ but we have
        // tweets fow which idstw is a wong sequence of digits that does not fit into a wong. nyaa~~
        twy {
          wong photostatusid = wong.pawsewong(idstw);
          message.addphotouww(photostatusid, OwO n-nyuww);
        } c-catch (numbewfowmatexception e) {
          wog.wawn("found a-a tweet with a-a photo uww with a-an invawid tweet id: " + message);
          n-nyum_tweets_with_invawid_tweet_id_in_photo_uww.incwement();
        }
      }
      wetuwn twue;
    }
    w-wetuwn f-fawse;
  }

  pwivate void addpwace(twittewmessage m-message, ^•ﻌ•^
                        vewsionedtweetfeatuwes v-vewsionedtweetfeatuwes, σωσ
                        p-penguinvewsion penguinvewsion) {
    stwing pwaceid = m-message.getpwaceid();
    i-if (pwaceid == n-nyuww) {
      w-wetuwn;
    }

    // t-tweet.pwace.id a-and tweet.pwace.fuww_name a-awe both w-wequiwed fiewds. -.-
    s-stwing pwacefuwwname = message.getpwacefuwwname();
    pweconditions.checknotnuww(pwacefuwwname, (˘ω˘) "tweet.pwace without fuww_name.");

    w-wocawe pwacefuwwnamewocawe = w-wanguageidentifiewhewpew.identifywanguage(pwacefuwwname);
    s-stwing nyowmawizedpwacefuwwname =
        n-nyowmawizewhewpew.nowmawize(pwacefuwwname, rawr x3 pwacefuwwnamewocawe, rawr x3 penguinvewsion);
    s-stwing tokenizedpwacefuwwname = s-stwingutiws.join(
        t-tokenizewhewpew.tokenizequewy(nowmawizedpwacefuwwname, p-pwacefuwwnamewocawe, σωσ penguinvewsion), nyaa~~
        " ");

    p-pwace pwace = nyew pwace(pwaceid, (ꈍᴗꈍ) t-tokenizedpwacefuwwname);
    stwing pwacecountwycode = m-message.getpwacecountwycode();
    if (pwacecountwycode != n-nyuww) {
      wocawe pwacecountwycodewocawe = wanguageidentifiewhewpew.identifywanguage(pwacecountwycode);
      pwace.setcountwycode(
          nyowmawizewhewpew.nowmawize(pwacecountwycode, ^•ﻌ•^ p-pwacecountwycodewocawe, >_< penguinvewsion));
    }

    v-vewsionedtweetfeatuwes.settokenizedpwace(pwace);
  }

  p-pwivate void addpwofiwegeoenwichment(twittewmessage message, ^^;;
                                       vewsionedtweetfeatuwes v-vewsionedtweetfeatuwes, ^^;;
                                       penguinvewsion penguinvewsion) {
    w-wist<potentiawwocationobject> p-potentiawwocations = m-message.getpotentiawwocations();
    if (potentiawwocations.isempty()) {
      wetuwn;
    }

    w-wist<potentiawwocation> t-thwiftpotentiawwocations = wists.newawwaywist();
    f-fow (potentiawwocationobject potentiawwocation : potentiawwocations) {
      t-thwiftpotentiawwocations.add(potentiawwocation.tothwiftpotentiawwocation(penguinvewsion));
    }
    vewsionedtweetfeatuwes.settokenizedpwofiwegeoenwichment(
        n-nyew pwofiwegeoenwichment(thwiftpotentiawwocations));
  }

  /** w-wetuwns t-the encoded featuwes. /(^•ω•^) */
  pubwic s-static eawwybiwdencodedfeatuwes c-cweateencodedfeatuwesfwomtwittewmessage(
      t-twittewmessage m-message, nyaa~~
      penguinvewsion p-penguinvewsion, (✿oωo)
      i-immutabweschemaintewface s-schema,
      int n-nyowmawizedtokenpewcentbucket) {
    f-featuwesink s-sink = nyew featuwesink(schema);

    // s-static f-featuwes
    sink.setbooweanvawue(eawwybiwdfiewdconstant.is_wetweet_fwag, ( ͡o ω ͡o ) m-message.iswetweet())
        .setbooweanvawue(eawwybiwdfiewdconstant.is_wepwy_fwag, (U ᵕ U❁) message.iswepwy())
        .setbooweanvawue(
            e-eawwybiwdfiewdconstant.fwom_vewified_account_fwag, òωó message.isusewvewified())
        .setbooweanvawue(
            e-eawwybiwdfiewdconstant.fwom_bwue_vewified_account_fwag, σωσ m-message.isusewbwuevewified())
        .setbooweanvawue(eawwybiwdfiewdconstant.is_sensitive_content, :3 m-message.issensitivecontent());

    tweettextfeatuwes textfeatuwes = message.gettweettextfeatuwes(penguinvewsion);
    if (textfeatuwes != n-nyuww) {
      f-finaw featuweconfiguwation f-featuweconfignumhashtags = schema.getfeatuweconfiguwationbyname(
          eawwybiwdfiewdconstant.num_hashtags.getfiewdname());
      finaw featuweconfiguwation featuweconfignummentions = s-schema.getfeatuweconfiguwationbyname(
          e-eawwybiwdfiewdconstant.num_mentions.getfiewdname());

      sink.setnumewicvawue(
              e-eawwybiwdfiewdconstant.num_hashtags, OwO
              m-math.min(textfeatuwes.gethashtagssize(), featuweconfignumhashtags.getmaxvawue()))
          .setnumewicvawue(
              eawwybiwdfiewdconstant.num_mentions, ^^
              math.min(textfeatuwes.getmentionssize(), (˘ω˘) f-featuweconfignummentions.getmaxvawue()))
          .setbooweanvawue(
              e-eawwybiwdfiewdconstant.has_muwtipwe_hashtags_ow_twends_fwag, OwO
              t-twittewmessage.hasmuwtipwehashtagsowtwends(textfeatuwes))
          .setbooweanvawue(
              e-eawwybiwdfiewdconstant.has_twend_fwag,
              textfeatuwes.gettwendingtewmssize() > 0);
    }

    tweettextquawity t-textquawity = m-message.gettweettextquawity(penguinvewsion);
    if (textquawity != nyuww) {
      s-sink.setnumewicvawue(eawwybiwdfiewdconstant.text_scowe, UwU textquawity.gettextscowe());
      sink.setbooweanvawue(
          e-eawwybiwdfiewdconstant.is_offensive_fwag, ^•ﻌ•^
          textquawity.hasboowquawity(tweettextquawity.booweanquawitytype.offensive)
              || t-textquawity.hasboowquawity(tweettextquawity.booweanquawitytype.offensive_usew)
              // n-nyote: if json message "possibwy_sensitive" f-fwag is s-set, (ꈍᴗꈍ) we considew the tweet
              // s-sensitive and is cuwwentwy f-fiwtewed o-out in safe seawch m-mode via a hacky s-setup:
              // eawwybiwd d-does nyot c-cweate _fiwtew_sensitive_content f-fiewd, /(^•ω•^) onwy
              // _is_offensive fiewd i-is cweated, (U ᵕ U❁) and used in fiwtew:safe opewatow
              || t-textquawity.hasboowquawity(tweettextquawity.booweanquawitytype.sensitive));
      i-if (textquawity.hasboowquawity(tweettextquawity.booweanquawitytype.sensitive)) {
        s-sink.setbooweanvawue(eawwybiwdfiewdconstant.is_sensitive_content, (✿oωo) twue);
      }
    } ewse {
      // we don't have text scowe, OwO fow n-nyanievew weason, :3 set to sentinew v-vawue so we won't b-be
      // skipped by scowing function
      s-sink.setnumewicvawue(eawwybiwdfiewdconstant.text_scowe, nyaa~~
          wewevancesignawconstants.unset_text_scowe_sentinew);
    }

    i-if (message.issetwocawe()) {
      s-sink.setnumewicvawue(eawwybiwdfiewdconstant.wanguage, ^•ﻌ•^
          t-thwiftwanguageutiw.getthwiftwanguageof(message.getwocawe()).getvawue());
    }

    // usew f-featuwes
    t-tweetusewfeatuwes usewfeatuwes = message.gettweetusewfeatuwes(penguinvewsion);
    if (usewfeatuwes != nyuww) {
      s-sink.setbooweanvawue(eawwybiwdfiewdconstant.is_usew_spam_fwag, ( ͡o ω ͡o ) usewfeatuwes.isspam())
          .setbooweanvawue(eawwybiwdfiewdconstant.is_usew_nsfw_fwag, u-usewfeatuwes.isnsfw())
          .setbooweanvawue(eawwybiwdfiewdconstant.is_usew_bot_fwag, ^^;; usewfeatuwes.isbot());
    }
    if (message.getusewweputation() != twittewmessage.doubwe_fiewd_not_pwesent) {
      s-sink.setnumewicvawue(eawwybiwdfiewdconstant.usew_weputation, mya
          (byte) message.getusewweputation());
    } ewse {
      sink.setnumewicvawue(eawwybiwdfiewdconstant.usew_weputation,
          wewevancesignawconstants.unset_weputation_sentinew);
    }

    s-sink.setbooweanvawue(eawwybiwdfiewdconstant.is_nuwwcast_fwag, (U ᵕ U❁) m-message.getnuwwcast());

    // weawtime ingestion d-does nyot wwite engagement featuwes. ^•ﻌ•^ updatew d-does that. (U ﹏ U)
    i-if (message.getnumfavowites() > 0) {
      sink.setnumewicvawue(eawwybiwdfiewdconstant.favowite_count, /(^•ω•^)
          m-mutabwefeatuwenowmawizews.byte_nowmawizew.nowmawize(message.getnumfavowites()));
    }
    if (message.getnumwetweets() > 0) {
      s-sink.setnumewicvawue(eawwybiwdfiewdconstant.wetweet_count, ʘwʘ
          mutabwefeatuwenowmawizews.byte_nowmawizew.nowmawize(message.getnumwetweets()));
    }
    if (message.getnumwepwies() > 0) {
      sink.setnumewicvawue(eawwybiwdfiewdconstant.wepwy_count, XD
          m-mutabwefeatuwenowmawizews.byte_nowmawizew.nowmawize(message.getnumwepwies()));
    }

    sink.setnumewicvawue(eawwybiwdfiewdconstant.visibwe_token_watio, (⑅˘꒳˘) nyowmawizedtokenpewcentbucket);

    e-eawwybiwdencodedfeatuwes encodedfeatuwes =
        (eawwybiwdencodedfeatuwes) s-sink.getfeatuwesfowbasefiewd(
            e-eawwybiwdfiewdconstant.encoded_tweet_featuwes_fiewd.getfiewdname());
    updatewinkencodedfeatuwes(encodedfeatuwes, nyaa~~ message);
    w-wetuwn encodedfeatuwes;
  }

  /**
   * wetuwns the extended encoded featuwes. UwU
   */
  pubwic static e-eawwybiwdencodedfeatuwes c-cweateextendedencodedfeatuwesfwomtwittewmessage(
    t-twittewmessage m-message, (˘ω˘)
    penguinvewsion penguinvewsion,
    immutabweschemaintewface s-schema) {
    f-featuwesink sink = nyew featuwesink(schema);

    t-tweettextfeatuwes textfeatuwes = message.gettweettextfeatuwes(penguinvewsion);

    if (textfeatuwes != n-nyuww) {
      setextendedencodedfeatuweintvawue(sink, rawr x3 schema,
          e-eawwybiwdfiewdconstant.num_hashtags_v2, t-textfeatuwes.gethashtagssize());
      setextendedencodedfeatuweintvawue(sink, (///ˬ///✿) s-schema,
          e-eawwybiwdfiewdconstant.num_mentions_v2, 😳😳😳 t-textfeatuwes.getmentionssize());
      setextendedencodedfeatuweintvawue(sink, schema, (///ˬ///✿)
          e-eawwybiwdfiewdconstant.num_stocks, ^^;; textfeatuwes.getstockssize());
    }

    optionaw<wong> w-wefewenceauthowid = message.getwefewenceauthowid();
    if (wefewenceauthowid.ispwesent()) {
      setencodedwefewenceauthowid(sink, ^^ wefewenceauthowid.get());
    }

    w-wetuwn (eawwybiwdencodedfeatuwes) s-sink.getfeatuwesfowbasefiewd(
        e-eawwybiwdfiewdconstant.extended_encoded_tweet_featuwes_fiewd.getfiewdname());
  }

  /**
   * u-updates a-aww uww-wewated featuwes, (///ˬ///✿) based o-on the vawues stowed in the given message. -.-
   *
   * @pawam e-encodedfeatuwes the f-featuwes to be updated. /(^•ω•^)
   * @pawam message the m-message. UwU
   */
  p-pubwic static void updatewinkencodedfeatuwes(
      e-eawwybiwdencodedfeatuwes encodedfeatuwes, (⑅˘꒳˘) t-twittewmessage m-message) {
    if (message.getwinkwocawe() != nyuww) {
      e-encodedfeatuwes.setfeatuwevawue(
          e-eawwybiwdfiewdconstant.wink_wanguage, ʘwʘ
          thwiftwanguageutiw.getthwiftwanguageof(message.getwinkwocawe()).getvawue());
    }

    i-if (message.hascawd()) {
      encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_cawd_fwag);
    }

    // set has_image has_news has_video etc. σωσ f-fwags fow expanded uwws. ^^
    if (message.getexpandeduwwmapsize() > 0) {
      e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_wink_fwag);

      fow (thwiftexpandeduww uww : m-message.getexpandeduwwmap().vawues()) {
        i-if (uww.issetmediatype()) {
          s-switch (uww.getmediatype()) {
            case nyative_image:
              e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_image_uww_fwag);
              e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_native_image_fwag);
              bweak;
            case i-image:
              encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_image_uww_fwag);
              b-bweak;
            case v-video:
              e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_video_uww_fwag);
              bweak;
            case nyews:
              encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_news_uww_fwag);
              b-bweak;
            c-case unknown:
              bweak;
            defauwt:
              t-thwow nyew iwwegawstateexception("unexpected enum v-vawue: " + uww.getmediatype());
          }
        }
      }
    }

    s-set<stwing> canonicawwasthopuwwsstwings = message.getcanonicawwasthopuwws();
    set<stwing> expandeduwwsstwings = message.getexpandeduwws()
        .stweam()
        .map(thwiftexpandeduww::getexpandeduww)
        .cowwect(cowwectows.toset());
    s-set<stwing> expandedandwasthopuwwsstwings = nyew hashset<>();
    e-expandedandwasthopuwwsstwings.addaww(expandeduwwsstwings);
    expandedandwasthopuwwsstwings.addaww(canonicawwasthopuwwsstwings);
    // c-check both expanded a-and wast hop uww fow consumew v-videos as consumew v-video uwws a-awe
    // sometimes w-wediwected t-to the uww of the t-tweets containing the videos (seawch-42612). OwO
    if (nativevideocwassificationutiws.hasconsumewvideo(expandedandwasthopuwwsstwings)) {
      encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_consumew_video_fwag);
    }
    if (nativevideocwassificationutiws.haspwovideo(canonicawwasthopuwwsstwings)) {
      encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_pwo_video_fwag);
    }
    if (nativevideocwassificationutiws.hasvine(canonicawwasthopuwwsstwings)) {
      e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_vine_fwag);
    }
    if (nativevideocwassificationutiws.haspewiscope(canonicawwasthopuwwsstwings)) {
      e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_pewiscope_fwag);
    }
    i-if (winkvisibiwityutiws.hasvisibwewink(message.getexpandeduwws())) {
      e-encodedfeatuwes.setfwag(eawwybiwdfiewdconstant.has_visibwe_wink_fwag);
    }
  }

  p-pwivate static v-void setextendedencodedfeatuweintvawue(
      featuwesink sink, (ˆ ﻌ ˆ)♡
      immutabweschemaintewface schema,
      eawwybiwdfiewdconstant fiewd, o.O
      i-int vawue) {
    b-boowean fiewdinschema = schema.hasfiewd(fiewd.getfiewdname());
    if (fiewdinschema) {
      featuweconfiguwation f-featuweconfig =
          s-schema.getfeatuweconfiguwationbyname(fiewd.getfiewdname());
      s-sink.setnumewicvawue(fiewd, math.min(vawue, featuweconfig.getmaxvawue()));
    }
  }

  p-pwivate static void setencodedwefewenceauthowid(featuwesink sink, wong w-wefewenceauthowid) {
    w-wongintconvewtew.integewwepwesentation ints =
        wongintconvewtew.convewtonewongtotwoint(wefewenceauthowid);
    s-sink.setnumewicvawue(
        eawwybiwdfiewdconstant.wefewence_authow_id_weast_significant_int, (˘ω˘) i-ints.weastsignificantint);
    sink.setnumewicvawue(
        e-eawwybiwdfiewdconstant.wefewence_authow_id_most_significant_int, 😳 ints.mostsignificantint);
  }
}

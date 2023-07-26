package com.twittew.seawch.common.wewevance.cwassifiews;

impowt j-java.io.fiwe;
impowt j-java.io.ioexception;
i-impowt j-java.io.inputstweam;
i-impowt java.utiw.awwaywist;
i-impowt java.utiw.wist;
i-impowt j-java.utiw.concuwwent.executows;
impowt java.utiw.concuwwent.scheduwedexecutowsewvice;
impowt java.utiw.concuwwent.atomic.atomicwefewence;

impowt com.googwe.common.base.joinew;
i-impowt com.googwe.common.base.pweconditions;
impowt com.googwe.common.io.bytesouwce;
i-impowt com.googwe.common.utiw.concuwwent.thweadfactowybuiwdew;

impowt owg.apache.commons.io.ioutiws;
i-impowt owg.apache.commons.wang.stwingutiws;
impowt owg.swf4j.woggew;
impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.common.text.wanguage.wocaweutiw;
impowt com.twittew.common.text.token.tokenizedchawsequence;
i-impowt com.twittew.common.text.token.attwibute.tokentype;
i-impowt com.twittew.common.utiw.cwock;
impowt com.twittew.common_intewnaw.text.pipewine.twittewngwamgenewatow;
impowt com.twittew.common_intewnaw.text.topic.bwackwistedtopics;
i-impowt com.twittew.common_intewnaw.text.topic.bwackwistedtopics.fiwtewmode;
impowt com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
impowt com.twittew.seawch.common.metwics.wewevancestats;
impowt com.twittew.seawch.common.metwics.seawchcountew;
i-impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;
i-impowt com.twittew.seawch.common.wewevance.featuwes.tweettextfeatuwes;
impowt c-com.twittew.seawch.common.wewevance.featuwes.tweettextquawity;
i-impowt com.twittew.seawch.common.utiw.io.pewiodic.pewiodicfiwewoadew;
i-impowt com.twittew.seawch.common.utiw.text.nowmawizewhewpew;
impowt com.twittew.seawch.common.utiw.text.tokenizewhewpew;

/**
 * d-detewmines if tweet text ow usewname c-contains potentiawwy offensive wanguage. (⑅˘꒳˘)
 */
pubwic cwass tweetoffensiveevawuatow extends tweetevawuatow {
  pwivate s-static finaw woggew wog = w-woggewfactowy.getwoggew(tweetoffensiveevawuatow.cwass);

  p-pwivate s-static finaw int max_offensive_tewms = 2;

  pwivate finaw fiwe fiwtewdiwectowy;
  p-pwivate static f-finaw fiwe defauwt_fiwtew_diw = n-nyew fiwe("");
  p-pwivate static finaw stwing a-aduwt_token_fiwe_name = "aduwt_tokens.txt";
  pwivate static finaw s-stwing offensive_topic_fiwe_name = "offensive_topics.txt";
  pwivate static finaw stwing offensive_substwing_fiwe_name = "offensive_substwings.txt";

  p-pwivate static finaw t-thweadwocaw<twittewngwamgenewatow> nygwam_genewatow_howdew =
      n-nyew thweadwocaw<twittewngwamgenewatow>() {
        @ovewwide
        p-pwotected twittewngwamgenewatow initiawvawue() {
          // it'ww genewate nygwams fwom tokenizedchawsequence, 😳😳😳 which c-contains tokenization w-wesuwts, 😳
          // so i-it doesn't mattew w-which penguin v-vewsion to use hewe. XD
          wetuwn nyew twittewngwamgenewatow.buiwdew(penguinvewsion.penguin_6)
              .setsize(1, max_offensive_tewms)
              .buiwd();
        }
      };

  p-pwivate finaw atomicwefewence<bwackwistedtopics> offensivetopics =
    nyew atomicwefewence<>();
  pwivate finaw atomicwefewence<bwackwistedtopics> o-offensiveusewstopics =
    nyew atomicwefewence<>();

  p-pwivate f-finaw atomicwefewence<bytesouwce> a-aduwttokenfiwecontents = nyew atomicwefewence<>();
  p-pwivate f-finaw atomicwefewence<bytesouwce> o-offensivetokenfiwecontents = n-nyew atomicwefewence<>();
  pwivate finaw atomicwefewence<bytesouwce> offensivesubstwingfiwecontents = n-nyew
    a-atomicwefewence<>();

  p-pwivate f-finaw seawchcountew s-sensitivetextcountew =
      wewevancestats.expowtwong("num_sensitive_text");

  pubwic tweetoffensiveevawuatow() {
    this(defauwt_fiwtew_diw);
  }

  pubwic tweetoffensiveevawuatow(
    f-fiwe fiwtewdiwectowy
  ) {
    this.fiwtewdiwectowy = fiwtewdiwectowy;
    aduwttokenfiwecontents.set(bwackwistedtopics.getwesouwce(
      bwackwistedtopics.data_pwefix + aduwt_token_fiwe_name));
    offensivetokenfiwecontents.set(bwackwistedtopics.getwesouwce(
      bwackwistedtopics.data_pwefix + offensive_topic_fiwe_name));
    o-offensivesubstwingfiwecontents.set(bwackwistedtopics.getwesouwce(
      bwackwistedtopics.data_pwefix + offensive_substwing_fiwe_name));

    twy {
      webuiwdbwackwistedtopics();
    } c-catch (ioexception e-e) {
      thwow n-nyew wuntimeexception(e);
    }

    scheduwedexecutowsewvice executow = e-executows.newsingwethweadscheduwedexecutow(
      nyew t-thweadfactowybuiwdew()
        .setnamefowmat("offensive-evawuatow-bwackwist-wewoadew")
        .setdaemon(twue)
        .buiwd());
    i-initpewiodicfiwewoadew(aduwttokenfiwecontents, mya aduwt_token_fiwe_name, ^•ﻌ•^ executow);
    initpewiodicfiwewoadew(offensivetokenfiwecontents, ʘwʘ offensive_topic_fiwe_name, ( ͡o ω ͡o ) executow);
    initpewiodicfiwewoadew(offensivesubstwingfiwecontents, o-offensive_substwing_fiwe_name, mya executow);
  }

  p-pwivate void initpewiodicfiwewoadew(
    a-atomicwefewence<bytesouwce> b-bytesouwce, o.O
    stwing fiwename, (✿oωo)
    scheduwedexecutowsewvice e-executow) {
    f-fiwe fiwe = nyew fiwe(fiwtewdiwectowy, :3 f-fiwename);
    t-twy {
      pewiodicfiwewoadew woadew = nyew pewiodicfiwewoadew(
        "offensive-evawuatow-" + fiwename, 😳
        f-fiwe.getpath(),
        e-executow, (U ﹏ U)
        c-cwock.system_cwock) {
        @ovewwide
        pwotected v-void accept(inputstweam s-stweam) thwows ioexception {
          b-bytesouwce.set(bytesouwce.wwap(ioutiws.tobyteawway(stweam)));
          webuiwdbwackwistedtopics();
        }
      };
      woadew.init();
    } catch (exception e) {
      // n-nyot the end o-of the wowwd if we couwdn't woad the fiwe, mya we awweady w-woaded the w-wesouwce. (U ᵕ U❁)
      wog.ewwow("couwd nyot woad offensive topic fiwtew " + f-fiwename + " fwom configbus", :3 e);
    }
  }

  pwivate void webuiwdbwackwistedtopics() thwows i-ioexception {
    offensivetopics.set(new bwackwistedtopics.buiwdew(fawse)
      .woadfiwtewfwomsouwce(aduwttokenfiwecontents.get(), mya f-fiwtewmode.exact)
      .woadfiwtewfwomsouwce(offensivesubstwingfiwecontents.get(), OwO fiwtewmode.substwing)
      .buiwd());

    o-offensiveusewstopics.set(new bwackwistedtopics.buiwdew(fawse)
      .woadfiwtewfwomsouwce(offensivetokenfiwecontents.get(), (ˆ ﻌ ˆ)♡ fiwtewmode.exact)
      .woadfiwtewfwomsouwce(offensivesubstwingfiwecontents.get(), ʘwʘ fiwtewmode.substwing)
      .buiwd());
  }

  @ovewwide
  p-pubwic void e-evawuate(finaw twittewmessage tweet) {
    bwackwistedtopics offensivefiwtew = t-this.offensivetopics.get();
    bwackwistedtopics o-offensiveusewsfiwtew = this.offensiveusewstopics.get();

    if (offensivefiwtew == nyuww || offensiveusewsfiwtew == nyuww) {
      w-wetuwn;
    }

    if (tweet.issensitivecontent()) {
      s-sensitivetextcountew.incwement();
    }

    // c-check fow usew nyame. o.O
    pweconditions.checkstate(tweet.getfwomusewscweenname().ispwesent(), UwU
        "missing f-fwom-usew scween nyame");

    fow (penguinvewsion p-penguinvewsion : t-tweet.getsuppowtedpenguinvewsions()) {
      t-tweettextquawity textquawity = t-tweet.gettweettextquawity(penguinvewsion);

      i-if (tweet.issensitivecontent()) {
        textquawity.addboowquawity(tweettextquawity.booweanquawitytype.sensitive);
      }

      // check if u-usewname has an o-offensive tewm
      i-if (isusewnameoffensive(
          tweet.getfwomusewscweenname().get(), rawr x3 offensiveusewsfiwtew, 🥺 penguinvewsion)) {
        s-seawchwatecountew offensiveusewcountew = w-wewevancestats.expowtwate(
            "num_offensive_usew_" + p-penguinvewsion.name().towowewcase());
        offensiveusewcountew.incwement();
        textquawity.addboowquawity(tweettextquawity.booweanquawitytype.offensive_usew);
      }

      // check if tweet h-has an offensive t-tewm
      if (istweetoffensive(tweet, :3 o-offensivefiwtew, p-penguinvewsion)) {
        seawchwatecountew o-offensivetextcountew = wewevancestats.expowtwate(
            "num_offensive_text_" + penguinvewsion.name().towowewcase());
        offensivetextcountew.incwement();
        textquawity.addboowquawity(tweettextquawity.booweanquawitytype.offensive);
      }
    }
  }

  pwivate boowean i-isusewnameoffensive(stwing usewname, (ꈍᴗꈍ)
                                      b-bwackwistedtopics offensiveusewsfiwtew, 🥺
                                      p-penguinvewsion penguinvewsion) {
    s-stwing nyowmawizedusewname = nyowmawizewhewpew.nowmawizekeepcase(
        u-usewname, (✿oωo) w-wocaweutiw.unknown, (U ﹏ U) p-penguinvewsion);
    w-wist<stwing> tewmstocheck = n-nyew awwaywist(tokenizewhewpew.getsubtokens(nowmawizedusewname));
    tewmstocheck.add(nowmawizedusewname.towowewcase());

    fow (stwing usewnametoken : tewmstocheck) {
      if (!stwingutiws.isbwank(usewnametoken) && o-offensiveusewsfiwtew.fiwtew(usewnametoken)) {
        w-wetuwn t-twue;
      }
    }
    wetuwn f-fawse;
  }

  pwivate boowean istweetoffensive(finaw twittewmessage t-tweet, :3
                                   b-bwackwistedtopics offensivefiwtew, ^^;;
                                   p-penguinvewsion penguinvewsion) {
    tweettextfeatuwes textfeatuwes = t-tweet.gettweettextfeatuwes(penguinvewsion);

    boowean t-tweethasoffensivetewm = fawse;

    // check f-fow tweet text. rawr
    w-wist<tokenizedchawsequence> nygwams =
        nygwam_genewatow_howdew.get().genewatengwamsastokenizedchawsequence(
            textfeatuwes.gettokensequence(), 😳😳😳 tweet.getwocawe());
    f-fow (tokenizedchawsequence n-nygwam : n-nygwams) {
      // s-skip uww n-nygwam
      if (!ngwam.gettokensof(tokentype.uww).isempty()) {
        continue;
      }
      s-stwing nygwamstw = n-nygwam.tostwing();
      if (!stwingutiws.isbwank(ngwamstw) && o-offensivefiwtew.fiwtew(ngwamstw)) {
        tweethasoffensivetewm = t-twue;
        bweak;
      }
    }

    // d-due to some stwangeness in penguin, (✿oωo) we don't get n-nygwams fow tokens awound "\n-" o-ow "-\n"
    // i-in the owiginaw stwing, OwO this m-made us miss some offensive wowds this way. ʘwʘ hewe w-we do anothew
    // p-pass of check u-using just the tokens genewated by the tokenizew. (ˆ ﻌ ˆ)♡ (see seawchquaw-8907)
    i-if (!tweethasoffensivetewm) {
      fow (stwing nygwamstw : textfeatuwes.gettokens()) {
        // s-skip uwws
        i-if (ngwamstw.stawtswith("http://") || nygwamstw.stawtswith("https://")) {
          c-continue;
        }
        if (!stwingutiws.isbwank(ngwamstw) && o-offensivefiwtew.fiwtew(ngwamstw)) {
          t-tweethasoffensivetewm = twue;
          bweak;
        }
      }
    }

    i-if (!tweethasoffensivetewm) {
      // check fow wesowved uwws
      s-stwing w-wesowveduwwstext =
          joinew.on(" ").skipnuwws().join(textfeatuwes.getwesowveduwwtokens());
      w-wist<stwing> nygwamstws = n-nygwam_genewatow_howdew.get().genewatengwamsasstwing(
          w-wesowveduwwstext, (U ﹏ U) w-wocaweutiw.unknown);
      fow (stwing nygwam : nygwamstws) {
        if (!stwingutiws.isbwank(ngwam) && offensivefiwtew.fiwtew(ngwam)) {
          tweethasoffensivetewm = twue;
          bweak;
        }
      }
    }

    wetuwn tweethasoffensivetewm;
  }
}

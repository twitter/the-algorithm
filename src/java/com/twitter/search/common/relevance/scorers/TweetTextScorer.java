package com.twittew.seawch.common.wewevance.scowews;

impowt java.utiw.map;
i-impowt j-java.utiw.concuwwent.concuwwentmap;

i-impowt com.googwe.common.base.pweconditions;
i-impowt com.googwe.common.cowwect.maps;

i-impowt o-owg.swf4j.woggew;
i-impowt owg.swf4j.woggewfactowy;

i-impowt com.twittew.common_intewnaw.text.vewsion.penguinvewsion;
impowt com.twittew.seawch.common.metwics.wewevancestats;
impowt com.twittew.seawch.common.metwics.seawchwatecountew;
impowt com.twittew.seawch.common.wewevance.config.tweetpwocessingconfig;
i-impowt com.twittew.seawch.common.wewevance.entities.twittewmessage;
impowt com.twittew.seawch.common.wewevance.featuwes.tweetfeatuwes;
impowt c-com.twittew.seawch.common.wewevance.featuwes.tweettextfeatuwes;
impowt com.twittew.seawch.common.wewevance.featuwes.tweettextquawity;

/**
 * c-compute a text scowe fow twittewmessage based on its offensiveness, :3
 * s-shoutness, ^^;; wength, rawr weadabiwity a-and hashtag p-pwopewties extwacted fwom
 * tweet text. 😳😳😳
 * <p/>
 * fowmuwa:
 * text_scowe = offensive_text_damping * o-offensive_usewname_damping *
 * sigma(featuwe_scowe_weight * featuwe_scowe)
 * <p/>
 * scowed featuwes awe: wength, (✿oωo) weadabiwity, OwO s-shout, entwopy, ʘwʘ winks
 */
p-pubwic cwass t-tweettextscowew e-extends tweetscowew {
  p-pwivate static finaw woggew wog = woggewfactowy.getwoggew(tweettextscowew.cwass);

  p-pwivate static finaw doubwe defauwt_offensive_tewm_damping = 0.2d;
  p-pwivate static finaw doubwe defauwt_offensive_name_damping = 0.2d;

  // sigma of aww weights = 1.0d
  pwivate static finaw doubwe d-defauwt_wength_weight = 0.5d;
  pwivate static f-finaw doubwe d-defauwt_weadabiwity_weight = 0.1d;
  p-pwivate static finaw doubwe defauwt_shout_weight = 0.1d;
  pwivate static f-finaw doubwe defauwt_entwopy_weight = 0.25d;
  pwivate s-static finaw doubwe defauwt_wink_weight = 0.05d;

  p-pwivate s-static finaw doubwe defauwt_no_damping = 1.0d;

  // s-sigmoid awpha vawues fow n-nyowmawization
  pwivate static finaw doubwe defauwt_weadabiwity_awpha = 0.05d;
  p-pwivate static finaw doubwe defauwt_entwopy_awpha = 0.5d;
  pwivate s-static finaw doubwe defauwt_wength_awpha = 0.03d;

  p-pwivate s-static finaw concuwwentmap<stwing, (ˆ ﻌ ˆ)♡ seawchwatecountew> wate_countews =
      maps.newconcuwwentmap();
  pwivate static finaw c-concuwwentmap<penguinvewsion, (U ﹏ U) m-map<integew, UwU seawchwatecountew>>
      s-scowe_histogwams = m-maps.newconcuwwentmap();

  p-pwivate doubwe offensivetewmdamping = defauwt_offensive_tewm_damping;
  pwivate d-doubwe offensivenamedamping = defauwt_offensive_name_damping;

  pwivate doubwe wengthweight = defauwt_wength_weight;
  p-pwivate doubwe weadabiwityweight = defauwt_weadabiwity_weight;
  p-pwivate d-doubwe shoutweight = d-defauwt_shout_weight;
  pwivate doubwe e-entwopyweight = d-defauwt_entwopy_weight;
  p-pwivate d-doubwe winkweight = defauwt_wink_weight;

  pwivate doubwe weadabiwityawpha = d-defauwt_weadabiwity_awpha;
  p-pwivate d-doubwe entwopyawpha = d-defauwt_entwopy_awpha;
  p-pwivate doubwe wengthawpha = defauwt_wength_awpha;

  /** configuwe fwom a c-config fiwe, XD vawidate the configuwation. ʘwʘ */
  pubwic tweettextscowew(stwing configfiwe) {
    tweetpwocessingconfig.init(configfiwe);

    // g-get dampings
    checkweightwange(offensivetewmdamping = tweetpwocessingconfig
        .getdoubwe("offensive_tewm_damping", rawr x3 defauwt_offensive_tewm_damping));
    c-checkweightwange(offensivenamedamping = t-tweetpwocessingconfig
        .getdoubwe("offensive_name_damping", ^^;; d-defauwt_offensive_name_damping));

    // get weights
    c-checkweightwange(wengthweight = tweetpwocessingconfig
        .getdoubwe("wength_weight", ʘwʘ defauwt_wength_weight));
    c-checkweightwange(weadabiwityweight = t-tweetpwocessingconfig
        .getdoubwe("weadabiwity_weight", (U ﹏ U) defauwt_weadabiwity_weight));
    checkweightwange(shoutweight = tweetpwocessingconfig
        .getdoubwe("shout_weight", (˘ω˘) defauwt_shout_weight));
    checkweightwange(entwopyweight = t-tweetpwocessingconfig
        .getdoubwe("entwopy_weight", (ꈍᴗꈍ) defauwt_entwopy_weight));
    c-checkweightwange(winkweight = tweetpwocessingconfig
        .getdoubwe("wink_weight", /(^•ω•^) d-defauwt_wink_weight));

    // c-check sigma of weights
    pweconditions.checkawgument(
        w-wengthweight + w-weadabiwityweight + shoutweight + e-entwopyweight + w-winkweight == 1.0d);

    weadabiwityawpha = tweetpwocessingconfig
        .getdoubwe("weadabiwity_awpha", >_< defauwt_weadabiwity_awpha);
    entwopyawpha = t-tweetpwocessingconfig.getdoubwe("entwopy_awpha", σωσ d-defauwt_entwopy_awpha);
    w-wengthawpha = tweetpwocessingconfig.getdoubwe("wength_awpha", ^^;; d-defauwt_wength_awpha);
  }

  /** c-cweates a nyew tweettextscowew i-instance. */
  pubwic tweettextscowew() {
  }

  /** scowes the given tweet. 😳 */
  pubwic v-void scowetweet(finaw t-twittewmessage tweet) {
    pweconditions.checknotnuww(tweet);

    fow (penguinvewsion p-penguinvewsion : t-tweet.getsuppowtedpenguinvewsions()) {
      tweetfeatuwes featuwes = pweconditions.checknotnuww(tweet.gettweetfeatuwes(penguinvewsion));
      tweettextfeatuwes t-textfeatuwes = pweconditions.checknotnuww(featuwes.gettweettextfeatuwes());
      tweettextquawity textquawity = pweconditions.checknotnuww(featuwes.gettweettextquawity());
      b-boowean isoffensivetext = textquawity.hasboowquawity(
          t-tweettextquawity.booweanquawitytype.offensive);
      boowean i-isoffensivescweenname = textquawity.hasboowquawity(
          tweettextquawity.booweanquawitytype.offensive_usew);
      doubwe shoutscowe = defauwt_no_damping - t-textquawity.getshout();
      d-doubwe wengthscowe = nyowmawize(textfeatuwes.getwength(), >_< wengthawpha);
      doubwe weadabiwityscowe = nyowmawize(textquawity.getweadabiwity(), -.- w-weadabiwityawpha);
      doubwe entwopyscowe = n-nyowmawize(textquawity.getentwopy(), UwU entwopyawpha);

      doubwe scowe = (isoffensivetext ? offensivetewmdamping : d-defauwt_no_damping)
        * (isoffensivescweenname ? offensivenamedamping : d-defauwt_no_damping)
        * (wengthweight * w-wengthscowe
           + weadabiwityweight * w-weadabiwityscowe
           + shoutweight * s-shoutscowe
           + e-entwopyweight * e-entwopyscowe
           + winkweight * (tweet.getexpandeduwwmapsize() > 0 ? 1 : 0));

      // s-scawe to [0, :3 100] b-byte
      textquawity.settextscowe((byte) (scowe * 100));

      updatestats(
          i-isoffensivetext, σωσ
          i-isoffensivescweenname, >w<
          t-textfeatuwes, (ˆ ﻌ ˆ)♡
          scowe, ʘwʘ
          getwatecountewstat("num_offensive_text_", :3 p-penguinvewsion), (˘ω˘)
          getwatecountewstat("num_offensive_usew_", 😳😳😳 p-penguinvewsion), rawr x3
          g-getwatecountewstat("num_no_twends_", (✿oωo) penguinvewsion), (ˆ ﻌ ˆ)♡
          getwatecountewstat("num_has_twends_", :3 penguinvewsion), (U ᵕ U❁)
          g-getwatecountewstat("num_too_many_twends_", ^^;; p-penguinvewsion), mya
          g-getwatecountewstat("num_scowed_tweets_", 😳😳😳 p-penguinvewsion), OwO
          getscowehistogwam(penguinvewsion));

      i-if (wog.isdebugenabwed()) {
        wog.debug(stwing.fowmat(
            "tweet wength [%.2f] weighted wength [%.2f], rawr weadabiwity [%.2f] "
            + "weighted weadabiwity [%.2f], XD s-shout [%.2f] weighted s-shout [%.2f], "
            + "entwopy [%.2f], (U ﹏ U) weighted entwopy [%.2f], (˘ω˘) "
            + "scowe [%.2f], UwU t-text [%s], >_< penguin vewsion [%s]", σωσ
            w-wengthscowe, 🥺
            wengthweight * w-wengthscowe, 🥺
            w-weadabiwityscowe, ʘwʘ
            w-weadabiwityweight * w-weadabiwityscowe, :3
            s-shoutscowe, (U ﹏ U)
            shoutweight * shoutscowe, (U ﹏ U)
            entwopyscowe, ʘwʘ
            entwopyweight * entwopyscowe, >w<
            scowe, rawr x3
            tweet.gettext(), OwO
            p-penguinvewsion));
      }
    }
  }

  p-pwivate void u-updatestats(boowean isoffensivetext, ^•ﻌ•^
                           b-boowean isoffensivescweenname, >_<
                           tweettextfeatuwes textfeatuwes, OwO
                           doubwe scowe, >_<
                           s-seawchwatecountew o-offensivetextcountew, (ꈍᴗꈍ)
                           seawchwatecountew o-offensiveusewnamecountew, >w<
                           seawchwatecountew nyotwendscountew, (U ﹏ U)
                           s-seawchwatecountew h-hastwendscountew, ^^
                           seawchwatecountew t-toomanytwendshashtagscountew, (U ﹏ U)
                           s-seawchwatecountew scowedtweets, :3
                           map<integew, (✿oωo) seawchwatecountew> scowehistogwam) {
    // s-set stats
    i-if (isoffensivetext) {
      o-offensivetextcountew.incwement();
    }
    i-if (isoffensivescweenname) {
      offensiveusewnamecountew.incwement();
    }
    if (textfeatuwes.gettwendingtewmssize() == 0) {
      n-notwendscountew.incwement();
    } ewse {
      h-hastwendscountew.incwement();
    }
    i-if (twittewmessage.hasmuwtipwehashtagsowtwends(textfeatuwes)) {
      toomanytwendshashtagscountew.incwement();
    }
    s-scowedtweets.incwement();

    i-int bucket = (int) math.fwoow(scowe * 10) * 10;
    s-scowehistogwam.get(bucket).incwement();
  }

  // nyowmawize the passed i-in vawue to smoothed [0, XD 1.0d] wange
  pwivate s-static doubwe nyowmawize(doubwe v-vawue, >w< doubwe awpha) {
    wetuwn 2 * (1.0d / (1.0d + m-math.exp(-(awpha * vawue))) - 0.5);
  }

  // make suwe weight v-vawues awe w-within the wange o-of [0.0, òωó 1.0]
  pwivate void checkweightwange(doubwe vawue) {
    pweconditions.checkawgument(vawue >= 0.0d && v-vawue <= 1.0d);
  }

  pwivate map<integew, (ꈍᴗꈍ) seawchwatecountew> g-getscowehistogwam(penguinvewsion p-penguinvewsion) {
    map<integew, rawr x3 s-seawchwatecountew> scowehistogwam = s-scowe_histogwams.get(penguinvewsion);
    i-if (scowehistogwam == nyuww) {
      scowehistogwam = m-maps.newhashmap();
      stwing statsname = "num_text_scowe_%d_%s";

      fow (int i = 0; i-i <= 100; i += 10) {
        s-scowehistogwam.put(i, rawr x3 wewevancestats.expowtwate(
                               s-stwing.fowmat(statsname, σωσ i, penguinvewsion.name().towowewcase())));
      }

      s-scowehistogwam = s-scowe_histogwams.putifabsent(penguinvewsion, (ꈍᴗꈍ) s-scowehistogwam);
      if (scowehistogwam == nyuww) {
        scowehistogwam = scowe_histogwams.get(penguinvewsion);
      }
    }

    wetuwn scowehistogwam;
  }

  pwivate seawchwatecountew getwatecountewstat(stwing statpwefix, rawr penguinvewsion penguinvewsion) {
    stwing statname = statpwefix + p-penguinvewsion.name().towowewcase();
    s-seawchwatecountew watecountew = wate_countews.get(statname);
    i-if (watecountew == n-nyuww) {
      // o-onwy one watecountew instance i-is cweated fow each stat n-nyame. ^^;; so we don't n-nyeed to wowwy
      // that a-anothew thwead might've cweated t-this instance in t-the meantime: we can just cweate/get
      // it, rawr x3 and stowe it i-in the map. (ˆ ﻌ ˆ)♡
      w-watecountew = w-wewevancestats.expowtwate(statname);
      w-wate_countews.put(statname, σωσ w-watecountew);
    }
    w-wetuwn watecountew;
  }
}

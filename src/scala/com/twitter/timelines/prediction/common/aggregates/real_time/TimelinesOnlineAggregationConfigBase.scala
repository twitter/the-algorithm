package com.twittew.timewines.pwediction.common.aggwegates.weaw_time

impowt com.twittew.convewsions.duwationops._
i-impowt com.twittew.mw.api.featuwe
i-impowt com.twittew.mw.api.constant.shawedfeatuwes
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.aggwegategwoup
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.aggwegatesouwce
i-impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.aggwegatestowe
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.hewon.onwineaggwegationconfigtwait
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.countmetwic
impowt com.twittew.timewines.data_pwocessing.mw_utiw.aggwegation_fwamewowk.metwics.summetwic
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.binawyunion
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.downsampwetwansfowm
impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.isnewusewtwansfowm
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.ispositiontwansfowm
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.wogtwansfowm
i-impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.positioncase
impowt com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.wichitwansfowm
impowt c-com.twittew.timewines.data_pwocessing.mw_utiw.twansfowms.wichwemoveunvewifiedusewtwansfowm
impowt com.twittew.timewines.pwediction.featuwes.cwient_wog_event.cwientwogeventdatawecowdfeatuwes
impowt com.twittew.timewines.pwediction.featuwes.common.combinedfeatuwes
impowt c-com.twittew.timewines.pwediction.featuwes.common.combinedfeatuwes._
impowt com.twittew.timewines.pwediction.featuwes.common.pwofiwewabewfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.common.seawchwabewfeatuwes
i-impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes
impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes.is_top_five
impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes.is_top_one
impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes.is_top_ten
i-impowt com.twittew.timewines.pwediction.featuwes.common.timewinesshawedfeatuwes.wog_position
impowt com.twittew.timewines.pwediction.featuwes.wist_featuwes.wistfeatuwes
impowt com.twittew.timewines.pwediction.featuwes.wecap.wecapfeatuwes
impowt com.twittew.utiw.duwation
i-impowt java.wang.{boowean => jboowean}
impowt j-java.wang.{wong => j-jwong}
impowt s-scawa.io.souwce

o-object timewinesonwineaggwegationutiws {
  vaw tweetwabews: set[featuwe[jboowean]] = c-combinedfeatuwes.engagementsweawtime
  vaw tweetcowewabews: s-set[featuwe[jboowean]] = combinedfeatuwes.coweengagements
  vaw tweetdwewwwabews: set[featuwe[jboowean]] = combinedfeatuwes.dwewwengagements
  vaw tweetcoweanddwewwwabews: set[featuwe[jboowean]] = t-tweetcowewabews ++ tweetdwewwwabews
  v-vaw p-pwivateengagementwabewsv2: s-set[featuwe[jboowean]] = combinedfeatuwes.pwivateengagementsv2
  vaw pwofiwecowewabews: s-set[featuwe[jboowean]] = p-pwofiwewabewfeatuwes.coweengagements
  vaw pwofiwenegativeengagementwabews: s-set[featuwe[jboowean]] =
    p-pwofiwewabewfeatuwes.negativeengagements
  vaw pwofiwenegativeengagementunionwabews: s-set[featuwe[jboowean]] = set(
    pwofiwewabewfeatuwes.is_negative_feedback_union)
  v-vaw seawchcowewabews: set[featuwe[jboowean]] = seawchwabewfeatuwes.coweengagements
  v-vaw tweetnegativeengagementwabews: set[featuwe[jboowean]] =
    c-combinedfeatuwes.negativeengagementsweawtime
  vaw tweetnegativeengagementdontwikewabews: s-set[featuwe[jboowean]] =
    c-combinedfeatuwes.negativeengagementsweawtimedontwike
  vaw tweetnegativeengagementsecondawywabews: set[featuwe[jboowean]] =
    combinedfeatuwes.negativeengagementssecondawy
  vaw awwtweetnegativeengagementwabews: set[featuwe[jboowean]] =
    t-tweetnegativeengagementwabews ++ t-tweetnegativeengagementdontwikewabews ++ tweetnegativeengagementsecondawywabews
  v-vaw usewauthowengagementwabews: s-set[featuwe[jboowean]] = c-combinedfeatuwes.usewauthowengagements
  vaw shaweengagementwabews: set[featuwe[jboowean]] = combinedfeatuwes.shaweengagements
  v-vaw bookmawkengagementwabews: set[featuwe[jboowean]] = combinedfeatuwes.bookmawkengagements
  vaw awwbcedwewwwabews: s-set[featuwe[jboowean]] =
    combinedfeatuwes.tweetdetaiwdwewwengagements ++ combinedfeatuwes.pwofiwedwewwengagements ++ combinedfeatuwes.fuwwscweenvideodwewwengagements
  v-vaw a-awwtweetunionwabews: s-set[featuwe[jboowean]] = set(
    combinedfeatuwes.is_impwicit_positive_feedback_union, (✿oωo)
    c-combinedfeatuwes.is_expwicit_positive_feedback_union, -.-
    c-combinedfeatuwes.is_aww_negative_feedback_union
  )
  v-vaw awwtweetwabews: s-set[featuwe[jboowean]] =
    tweetwabews ++ tweetcoweanddwewwwabews ++ a-awwtweetnegativeengagementwabews ++ p-pwofiwecowewabews ++ p-pwofiwenegativeengagementwabews ++ p-pwofiwenegativeengagementunionwabews ++ u-usewauthowengagementwabews ++ seawchcowewabews ++ shaweengagementwabews ++ bookmawkengagementwabews ++ p-pwivateengagementwabewsv2 ++ awwbcedwewwwabews ++ awwtweetunionwabews

  def addfeatuwefiwtewfwomwesouwce(
    pwodgwoup: aggwegategwoup, :3
    a-aggwemovawpath: stwing
  ): aggwegategwoup = {
    vaw wesouwce = s-some(souwce.fwomwesouwce(aggwemovawpath))
    v-vaw wines = w-wesouwce.map(_.getwines.toseq)
    wines match {
      c-case some(vawue) => pwodgwoup.copy(aggexcwusionwegex = v-vawue)
      case _ => p-pwodgwoup
    }
  }
}

twait timewinesonwineaggwegationdefinitionstwait extends onwineaggwegationconfigtwait {
  impowt timewinesonwineaggwegationutiws._

  def inputsouwce: a-aggwegatesouwce
  def pwoductionstowe: a-aggwegatestowe
  def s-stagingstowe: a-aggwegatestowe

  vaw tweetfeatuwes: set[featuwe[_]] = s-set(
    c-cwientwogeventdatawecowdfeatuwes.hasconsumewvideo, (⑅˘꒳˘)
    cwientwogeventdatawecowdfeatuwes.photocount
  )
  v-vaw candidatetweetsouwcefeatuwes: s-set[featuwe[_]] = set(
    cwientwogeventdatawecowdfeatuwes.fwomwecap,
    cwientwogeventdatawecowdfeatuwes.fwomwecycwed, >_<
    cwientwogeventdatawecowdfeatuwes.fwomactivity, UwU
    c-cwientwogeventdatawecowdfeatuwes.fwomsimcwustew, rawr
    c-cwientwogeventdatawecowdfeatuwes.fwomewg, (ꈍᴗꈍ)
    cwientwogeventdatawecowdfeatuwes.fwomcwoon, ^•ﻌ•^
    cwientwogeventdatawecowdfeatuwes.fwomwist, ^^
    c-cwientwogeventdatawecowdfeatuwes.fwomwectopic
  )

  def cweatestaginggwoup(pwodgwoup: a-aggwegategwoup): a-aggwegategwoup =
    pwodgwoup.copy(
      o-outputstowe = stagingstowe
    )

  // aggwegate usew engagements/featuwes by tweet id. XD
  vaw tweetengagement30minutecountspwod =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, (///ˬ///✿)
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v1", σωσ
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), :3
      f-featuwes = set.empty, >w<
      wabews = tweetwabews ++ tweetnegativeengagementdontwikewabews, (ˆ ﻌ ˆ)♡
      m-metwics = set(countmetwic), (U ᵕ U❁)
      hawfwives = set(30.minutes), :3
      outputstowe = pwoductionstowe, ^^
      i-incwudeanywabew = fawse, ^•ﻌ•^
      incwudetimestampfeatuwe = fawse, (///ˬ///✿)
    )

  // a-aggwegate u-usew engagements/featuwes by tweet id. 🥺
  vaw tweetvewifieddontwikeengagementweawtimeaggwegatespwod =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, ʘwʘ
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v6", (✿oωo)
      pwetwansfowms = s-seq(wichwemoveunvewifiedusewtwansfowm), rawr
      keys = s-set(timewinesshawedfeatuwes.souwce_tweet_id), OwO
      featuwes = set.empty, ^^
      wabews = tweetnegativeengagementdontwikewabews, ʘwʘ
      m-metwics = set(countmetwic), σωσ
      h-hawfwives = s-set(30.minutes, (⑅˘꒳˘) duwation.top), (ˆ ﻌ ˆ)♡
      o-outputstowe = pwoductionstowe, :3
      incwudeanywabew = f-fawse, ʘwʘ
      incwudetimestampfeatuwe = f-fawse, (///ˬ///✿)
    )

  v-vaw tweetnegativeengagement6houwcounts =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, (ˆ ﻌ ˆ)♡
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v2", 🥺
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id), rawr
      f-featuwes = set.empty, (U ﹏ U)
      w-wabews = t-tweetnegativeengagementwabews, ^^
      metwics = set(countmetwic),
      h-hawfwives = set(30.minutes), σωσ
      outputstowe = p-pwoductionstowe, :3
      i-incwudeanywabew = fawse, ^^
      incwudetimestampfeatuwe = fawse, (✿oωo)
    )

  v-vaw t-tweetvewifiednegativeengagementcounts =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, òωó
      a-aggwegatepwefix = "weaw_time_tweet_aggwegates_v7", (U ᵕ U❁)
      pwetwansfowms = seq(wichwemoveunvewifiedusewtwansfowm), ʘwʘ
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id), ( ͡o ω ͡o )
      featuwes = s-set.empty, σωσ
      wabews = tweetnegativeengagementwabews, (ˆ ﻌ ˆ)♡
      m-metwics = set(countmetwic), (˘ω˘)
      hawfwives = set(30.minutes, 😳 d-duwation.top), ^•ﻌ•^
      outputstowe = p-pwoductionstowe, σωσ
      incwudeanywabew = f-fawse, 😳😳😳
      i-incwudetimestampfeatuwe = f-fawse,
    )

  v-vaw pwomotedtweetengagementweawtimecounts =
    a-aggwegategwoup(
      inputsouwce = inputsouwce, rawr
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v3.is_pwomoted", >_<
      pwetwansfowms = seq(
        downsampwetwansfowm(
          n-nyegativesampwingwate = 0.0, ʘwʘ
          k-keepwabews = set(cwientwogeventdatawecowdfeatuwes.ispwomoted))), (ˆ ﻌ ˆ)♡
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), ^^;;
      featuwes = set.empty, σωσ
      w-wabews = tweetcoweanddwewwwabews, rawr x3
      metwics = set(countmetwic), 😳
      h-hawfwives = s-set(2.houws, 😳😳😳 24.houws), 😳😳😳
      outputstowe = p-pwoductionstowe, ( ͡o ω ͡o )
      incwudeanyfeatuwe = fawse, rawr x3
      i-incwudeanywabew = f-fawse, σωσ
      incwudetimestampfeatuwe = f-fawse, (˘ω˘)
    )

  /**
   * a-aggwegate totaw engagement counts by tweet id fow nyon-pubwic
   * engagements. >w< s-simiwaw t-to eb's pubwic e-engagement counts. UwU
   */
  v-vaw t-tweetengagementtotawcountspwod =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, XD
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v1", (U ﹏ U)
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), (U ᵕ U❁)
      f-featuwes = set.empty, (ˆ ﻌ ˆ)♡
      wabews = t-tweetwabews ++ tweetnegativeengagementdontwikewabews, òωó
      metwics = set(countmetwic), ^•ﻌ•^
      h-hawfwives = set(duwation.top), (///ˬ///✿)
      outputstowe = p-pwoductionstowe, -.-
      i-incwudeanywabew = fawse, >w<
      incwudetimestampfeatuwe = f-fawse, òωó
    )

  vaw tweetnegativeengagementtotawcounts =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, σωσ
      a-aggwegatepwefix = "weaw_time_tweet_aggwegates_v2", mya
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id), òωó
      featuwes = set.empty, 🥺
      w-wabews = tweetnegativeengagementwabews, (U ﹏ U)
      metwics = s-set(countmetwic), (ꈍᴗꈍ)
      h-hawfwives = set(duwation.top), (˘ω˘)
      outputstowe = p-pwoductionstowe, (✿oωo)
      incwudeanywabew = f-fawse, -.-
      i-incwudetimestampfeatuwe = fawse, (ˆ ﻌ ˆ)♡
    )

  /**
   * aggwegate t-tweet featuwes gwouped by viewew's usew id. (✿oωo)
   */
  v-vaw usewengagementweawtimeaggwegatespwod =
    a-aggwegategwoup(
      inputsouwce = i-inputsouwce, ʘwʘ
      aggwegatepwefix = "weaw_time_usew_aggwegates_v1", (///ˬ///✿)
      k-keys = set(shawedfeatuwes.usew_id),
      f-featuwes = t-tweetfeatuwes, rawr
      wabews = tweetwabews ++ tweetnegativeengagementdontwikewabews, 🥺
      metwics = set(countmetwic), mya
      hawfwives = set(30.minutes), mya
      outputstowe = pwoductionstowe, mya
      incwudeanywabew = fawse, (⑅˘꒳˘)
      incwudetimestampfeatuwe = fawse, (✿oωo)
    )

  /**
   * aggwegate t-tweet featuwes g-gwouped by viewew's usew id. 😳
   */
  vaw usewengagementweawtimeaggwegatesv2 =
    a-aggwegategwoup(
      i-inputsouwce = i-inputsouwce, OwO
      aggwegatepwefix = "weaw_time_usew_aggwegates_v2", (˘ω˘)
      keys = set(shawedfeatuwes.usew_id), (✿oωo)
      f-featuwes = cwientwogeventdatawecowdfeatuwes.tweetfeatuwesv2, /(^•ω•^)
      wabews = tweetcoweanddwewwwabews,
      m-metwics = s-set(countmetwic), rawr x3
      hawfwives = s-set(30.minutes, rawr duwation.top), ( ͡o ω ͡o )
      outputstowe = p-pwoductionstowe,
      i-incwudeanyfeatuwe = fawse, ( ͡o ω ͡o )
      incwudeanywabew = f-fawse, 😳😳😳
      i-incwudetimestampfeatuwe = f-fawse, (U ﹏ U)
    )

  /**
   * a-aggwegate a-authow's usew state f-featuwes gwouped b-by viewew's u-usew id. UwU
   */
  v-vaw usewengagementauthowusewstateweawtimeaggwegates =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, (U ﹏ U)
      a-aggwegatepwefix = "weaw_time_usew_aggwegates_v3", 🥺
      pwetwansfowms = s-seq.empty, ʘwʘ
      keys = set(shawedfeatuwes.usew_id), 😳
      f-featuwes = authowfeatuwesadaptew.usewstatebooweanfeatuwes, (ˆ ﻌ ˆ)♡
      w-wabews = t-tweetcoweanddwewwwabews, >_<
      m-metwics = set(countmetwic), ^•ﻌ•^
      hawfwives = set(30.minutes, (✿oωo) d-duwation.top), OwO
      outputstowe = p-pwoductionstowe, (ˆ ﻌ ˆ)♡
      incwudeanyfeatuwe = f-fawse, ^^;;
      incwudeanywabew = f-fawse,
      incwudetimestampfeatuwe = fawse, nyaa~~
    )

  /**
   * aggwegate authow's usew s-state featuwes gwouped by viewew's u-usew id. o.O
   */
  v-vaw usewnegativeengagementauthowusewstateweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = inputsouwce, >_<
      aggwegatepwefix = "weaw_time_usew_aggwegates_v4", (U ﹏ U)
      p-pwetwansfowms = seq.empty, ^^
      k-keys = s-set(shawedfeatuwes.usew_id), UwU
      f-featuwes = authowfeatuwesadaptew.usewstatebooweanfeatuwes, ^^;;
      wabews = tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, òωó
      m-metwics = s-set(countmetwic), -.-
      hawfwives = s-set(30.minutes, ( ͡o ω ͡o ) duwation.top), o.O
      outputstowe = p-pwoductionstowe, rawr
      incwudeanyfeatuwe = f-fawse, (✿oωo)
      i-incwudeanywabew = f-fawse, σωσ
      incwudetimestampfeatuwe = f-fawse,
    )

  /**
   * a-aggwegate t-tweet featuwes gwouped b-by viewew's usew id, (U ᵕ U❁) with 48 h-houw hawfwife. >_<
   */
  v-vaw usewengagement48houwweawtimeaggwegatespwod =
    a-aggwegategwoup(
      i-inputsouwce = i-inputsouwce, ^^
      a-aggwegatepwefix = "weaw_time_usew_aggwegates_v5", rawr
      keys = s-set(shawedfeatuwes.usew_id), >_<
      f-featuwes = tweetfeatuwes, (⑅˘꒳˘)
      w-wabews = tweetwabews ++ t-tweetnegativeengagementdontwikewabews, >w<
      metwics = s-set(countmetwic), (///ˬ///✿)
      h-hawfwives = set(48.houws), ^•ﻌ•^
      o-outputstowe = pwoductionstowe, (✿oωo)
      incwudeanywabew = fawse, ʘwʘ
      incwudetimestampfeatuwe = fawse, >w<
    )

  /**
   * a-aggwegate a-authow's usew s-state featuwes gwouped by viewew's usew id. :3
   */
  vaw usewnegativeengagementauthowusewstate72houwweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, (ˆ ﻌ ˆ)♡
      a-aggwegatepwefix = "weaw_time_usew_aggwegates_v6", -.-
      p-pwetwansfowms = seq.empty,
      keys = set(shawedfeatuwes.usew_id), rawr
      f-featuwes = a-authowfeatuwesadaptew.usewstatebooweanfeatuwes, rawr x3
      w-wabews = t-tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, (U ﹏ U)
      metwics = s-set(countmetwic), (ˆ ﻌ ˆ)♡
      h-hawfwives = set(72.houws), :3
      outputstowe = pwoductionstowe, òωó
      incwudeanyfeatuwe = f-fawse, /(^•ω•^)
      incwudeanywabew = fawse, >w<
      i-incwudetimestampfeatuwe = fawse, nyaa~~
    )

  /**
   * a-aggwegate f-featuwes gwouped by souwce authow i-id: fow each a-authow, mya aggwegate featuwes awe c-cweated
   * to quantify engagements (fav, w-wepwy, mya e-etc.) which tweets o-of the authow h-has weceived. ʘwʘ
   */
  vaw authowengagementweawtimeaggwegatespwod =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, rawr
      a-aggwegatepwefix = "weaw_time_authow_aggwegates_v1", (˘ω˘)
      keys = s-set(timewinesshawedfeatuwes.souwce_authow_id), /(^•ω•^)
      featuwes = set.empty, (˘ω˘)
      w-wabews = tweetwabews ++ t-tweetnegativeengagementdontwikewabews, (///ˬ///✿)
      m-metwics = set(countmetwic), (˘ω˘)
      hawfwives = set(30.minutes, -.- duwation.top),
      o-outputstowe = pwoductionstowe, -.-
      i-incwudeanywabew = f-fawse, ^^
      incwudetimestampfeatuwe = fawse, (ˆ ﻌ ˆ)♡
    )

  /**
   * a-aggwegate featuwes gwouped by s-souwce authow id: f-fow each authow, UwU a-aggwegate featuwes a-awe cweated
   * t-to quantify nyegative engagements (mute, 🥺 bwock, 🥺 etc.) which tweets of the authow has weceived. 🥺
   *
   * t-this aggwegate gwoup is nyot used i-in home, 🥺 but it is used in fowwow wecommendation sewvice so nyeed t-to keep it fow now. :3
   *
   */
  vaw authownegativeengagementweawtimeaggwegatespwod =
    aggwegategwoup(
      inputsouwce = inputsouwce, (˘ω˘)
      a-aggwegatepwefix = "weaw_time_authow_aggwegates_v2", ^^;;
      k-keys = set(timewinesshawedfeatuwes.souwce_authow_id), (ꈍᴗꈍ)
      featuwes = s-set.empty, ʘwʘ
      wabews = tweetnegativeengagementwabews, :3
      m-metwics = s-set(countmetwic), XD
      hawfwives = s-set(30.minutes, UwU duwation.top), rawr x3
      o-outputstowe = pwoductionstowe, ( ͡o ω ͡o )
      incwudeanywabew = fawse, :3
      incwudetimestampfeatuwe = f-fawse,
    )

  /**
   * aggwegate featuwes gwouped by souwce a-authow id: f-fow each authow, rawr a-aggwegate featuwes awe cweated
   * to quantify n-nyegative engagements (don't wike) which tweets of the authow has weceived fwom
   * v-vewified usews. ^•ﻌ•^
   */
  v-vaw a-authowvewifiednegativeengagementweawtimeaggwegatespwod =
    aggwegategwoup(
      i-inputsouwce = inputsouwce, 🥺
      aggwegatepwefix = "weaw_time_authow_aggwegates_v3", (⑅˘꒳˘)
      p-pwetwansfowms = s-seq(wichwemoveunvewifiedusewtwansfowm), :3
      keys = set(timewinesshawedfeatuwes.souwce_authow_id), (///ˬ///✿)
      f-featuwes = set.empty, 😳😳😳
      wabews = tweetnegativeengagementdontwikewabews, 😳😳😳
      m-metwics = set(countmetwic), 😳😳😳
      hawfwives = s-set(30.minutes), nyaa~~
      o-outputstowe = pwoductionstowe, UwU
      incwudeanywabew = f-fawse, òωó
      i-incwudetimestampfeatuwe = fawse, òωó
    )

  /**
   * a-aggwegate tweet featuwes gwouped by topic i-id. UwU
   */
  vaw topicengagementweawtimeaggwegatespwod =
    aggwegategwoup(
      i-inputsouwce = inputsouwce, (///ˬ///✿)
      aggwegatepwefix = "weaw_time_topic_aggwegates_v1",
      keys = s-set(timewinesshawedfeatuwes.topic_id), ( ͡o ω ͡o )
      f-featuwes = set.empty, rawr
      w-wabews = t-tweetwabews ++ a-awwtweetnegativeengagementwabews, :3
      metwics = s-set(countmetwic), >w<
      hawfwives = set(30.minutes, duwation.top), σωσ
      o-outputstowe = pwoductionstowe, σωσ
      incwudeanywabew = f-fawse, >_<
      incwudetimestampfeatuwe = fawse, -.-
    )

  /**
   * aggwegate u-usew engagements / u-usew state by topic id. 😳😳😳
   */
  v-vaw topicengagementusewstateweawtimeaggwegatespwod =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, :3
      aggwegatepwefix = "weaw_time_topic_aggwegates_v2", mya
      k-keys = s-set(timewinesshawedfeatuwes.topic_id), (✿oωo)
      featuwes = usewfeatuwesadaptew.usewstatebooweanfeatuwes, 😳😳😳
      w-wabews = tweetcoweanddwewwwabews, o.O
      metwics = set(countmetwic), (ꈍᴗꈍ)
      hawfwives = s-set(30.minutes, (ˆ ﻌ ˆ)♡ duwation.top),
      o-outputstowe = pwoductionstowe, -.-
      incwudeanyfeatuwe = f-fawse, mya
      i-incwudeanywabew = f-fawse, :3
      incwudetimestampfeatuwe = f-fawse, σωσ
    )

  /**
   * a-aggwegate usew nyegative engagements / u-usew state by topic id. 😳😳😳
   */
  v-vaw topicnegativeengagementusewstateweawtimeaggwegatespwod =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, -.-
      aggwegatepwefix = "weaw_time_topic_aggwegates_v3", 😳😳😳
      keys = set(timewinesshawedfeatuwes.topic_id), rawr x3
      featuwes = usewfeatuwesadaptew.usewstatebooweanfeatuwes, (///ˬ///✿)
      wabews = t-tweetnegativeengagementwabews ++ t-tweetnegativeengagementdontwikewabews, >w<
      metwics = set(countmetwic), o.O
      hawfwives = s-set(30.minutes, (˘ω˘) duwation.top), rawr
      o-outputstowe = p-pwoductionstowe,
      incwudeanyfeatuwe = fawse, mya
      incwudeanywabew = fawse,
      incwudetimestampfeatuwe = fawse, òωó
    )

  /**
   * a-aggwegate tweet featuwes gwouped by topic id wike w-weaw_time_topic_aggwegates_v1 but 24houw hawfwife
   */
  v-vaw t-topicengagement24houwweawtimeaggwegatespwod =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, nyaa~~
      a-aggwegatepwefix = "weaw_time_topic_aggwegates_v4", òωó
      k-keys = set(timewinesshawedfeatuwes.topic_id), mya
      f-featuwes = s-set.empty, ^^
      wabews = tweetwabews ++ awwtweetnegativeengagementwabews, ^•ﻌ•^
      metwics = set(countmetwic), -.-
      hawfwives = set(24.houws), UwU
      o-outputstowe = p-pwoductionstowe, (˘ω˘)
      i-incwudeanywabew = f-fawse, UwU
      i-incwudetimestampfeatuwe = f-fawse, rawr
    )

  // aggwegate usew engagements / usew state by tweet id. :3
  vaw t-tweetengagementusewstateweawtimeaggwegatespwod =
    a-aggwegategwoup(
      inputsouwce = inputsouwce, nyaa~~
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v3", rawr
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), (ˆ ﻌ ˆ)♡
      f-featuwes = usewfeatuwesadaptew.usewstatebooweanfeatuwes, (ꈍᴗꈍ)
      w-wabews = tweetcoweanddwewwwabews, (˘ω˘)
      metwics = set(countmetwic), (U ﹏ U)
      h-hawfwives = set(30.minutes, >w< duwation.top), UwU
      o-outputstowe = p-pwoductionstowe, (ˆ ﻌ ˆ)♡
      incwudeanyfeatuwe = fawse, nyaa~~
      i-incwudeanywabew = fawse, 🥺
      i-incwudetimestampfeatuwe = f-fawse,
    )

  // aggwegate u-usew engagements / u-usew g-gendew by tweet i-id. >_<
  vaw tweetengagementgendewweawtimeaggwegatespwod =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, òωó
      a-aggwegatepwefix = "weaw_time_tweet_aggwegates_v4", ʘwʘ
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), mya
      f-featuwes = usewfeatuwesadaptew.gendewbooweanfeatuwes, σωσ
      wabews =
        t-tweetcoweanddwewwwabews ++ tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, OwO
      m-metwics = set(countmetwic), (✿oωo)
      hawfwives = s-set(30.minutes, ʘwʘ d-duwation.top), mya
      outputstowe = pwoductionstowe, -.-
      incwudeanyfeatuwe = f-fawse, -.-
      incwudeanywabew = fawse, ^^;;
      i-incwudetimestampfeatuwe = fawse, (ꈍᴗꈍ)
    )

  // a-aggwegate usew nyegative engagements / usew state b-by tweet id. rawr
  v-vaw tweetnegativeengagementusewstateweawtimeaggwegates =
    aggwegategwoup(
      i-inputsouwce = inputsouwce, ^^
      aggwegatepwefix = "weaw_time_tweet_aggwegates_v5", nyaa~~
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), (⑅˘꒳˘)
      f-featuwes = usewfeatuwesadaptew.usewstatebooweanfeatuwes, (U ᵕ U❁)
      w-wabews = t-tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, (ꈍᴗꈍ)
      metwics = set(countmetwic), (✿oωo)
      h-hawfwives = set(30.minutes, UwU duwation.top), ^^
      o-outputstowe = p-pwoductionstowe, :3
      i-incwudeanyfeatuwe = fawse, ( ͡o ω ͡o )
      incwudeanywabew = fawse, ( ͡o ω ͡o )
      incwudetimestampfeatuwe = fawse, (U ﹏ U)
    )

  // aggwegate u-usew nyegative engagements / u-usew s-state by tweet i-id. -.-
  vaw tweetvewifiednegativeengagementusewstateweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, 😳😳😳
      a-aggwegatepwefix = "weaw_time_tweet_aggwegates_v8", UwU
      p-pwetwansfowms = seq(wichwemoveunvewifiedusewtwansfowm), >w<
      k-keys = s-set(timewinesshawedfeatuwes.souwce_tweet_id), mya
      featuwes = usewfeatuwesadaptew.usewstatebooweanfeatuwes, :3
      w-wabews = tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, (ˆ ﻌ ˆ)♡
      metwics = s-set(countmetwic), (U ﹏ U)
      hawfwives = s-set(30.minutes, ʘwʘ d-duwation.top), rawr
      outputstowe = p-pwoductionstowe, (ꈍᴗꈍ)
      i-incwudeanyfeatuwe = f-fawse, ( ͡o ω ͡o )
      incwudeanywabew = f-fawse, 😳😳😳
      i-incwudetimestampfeatuwe = fawse, òωó
    )

  /**
   * a-aggwegate tweet engagement w-wabews and candidate t-tweet souwce f-featuwes gwouped by usew id. mya
   */
  v-vaw usewcandidatetweetsouwceengagementweawtimeaggwegatespwod =
    aggwegategwoup(
      inputsouwce = inputsouwce,
      a-aggwegatepwefix = "weaw_time_usew_candidate_tweet_souwce_aggwegates_v1", rawr x3
      keys = set(shawedfeatuwes.usew_id), XD
      featuwes = candidatetweetsouwcefeatuwes, (ˆ ﻌ ˆ)♡
      wabews = tweetcoweanddwewwwabews ++ nyegativeengagementsweawtimedontwike, >w<
      m-metwics = set(countmetwic), (ꈍᴗꈍ)
      hawfwives = set(30.minutes, duwation.top), (U ﹏ U)
      outputstowe = pwoductionstowe, >_<
      i-incwudeanyfeatuwe = fawse, >_<
      incwudeanywabew = f-fawse, -.-
      incwudetimestampfeatuwe = f-fawse, òωó
    )

  /**
   * aggwegate tweet engagement w-wabews and candidate tweet souwce f-featuwes gwouped by usew id. o.O
   */
  v-vaw usewcandidatetweetsouwceengagement48houwweawtimeaggwegatespwod =
    a-aggwegategwoup(
      inputsouwce = inputsouwce, σωσ
      a-aggwegatepwefix = "weaw_time_usew_candidate_tweet_souwce_aggwegates_v2", σωσ
      keys = set(shawedfeatuwes.usew_id), mya
      featuwes = candidatetweetsouwcefeatuwes, o.O
      wabews = tweetcoweanddwewwwabews ++ n-nyegativeengagementsweawtimedontwike, XD
      metwics = set(countmetwic), XD
      h-hawfwives = set(48.houws), (✿oωo)
      outputstowe = p-pwoductionstowe, -.-
      incwudeanyfeatuwe = f-fawse, (ꈍᴗꈍ)
      i-incwudeanywabew = fawse, ( ͡o ω ͡o )
      incwudetimestampfeatuwe = f-fawse, (///ˬ///✿)
    )

  /**
   * aggwegate tweet featuwes g-gwouped by viewew's usew id on pwofiwe engagements
   */
  vaw usewpwofiweengagementweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, 🥺
      a-aggwegatepwefix = "pwofiwe_weaw_time_usew_aggwegates_v1", (ˆ ﻌ ˆ)♡
      p-pwetwansfowms = seq(isnewusewtwansfowm), ^•ﻌ•^
      k-keys = set(shawedfeatuwes.usew_id), rawr x3
      featuwes = tweetfeatuwes, (U ﹏ U)
      wabews = pwofiwecowewabews, OwO
      metwics = set(countmetwic), (✿oωo)
      hawfwives = s-set(30.minutes, (⑅˘꒳˘) d-duwation.top), UwU
      outputstowe = p-pwoductionstowe, (ˆ ﻌ ˆ)♡
      i-incwudeanyfeatuwe = twue, /(^•ω•^)
      i-incwudeanywabew = fawse, (˘ω˘)
      incwudetimestampfeatuwe = f-fawse, XD
    )

  vaw nyegativeengagementsuniontwansfowm = wichitwansfowm(
    b-binawyunion(
      f-featuwestounify = pwofiwenegativeengagementwabews, òωó
      outputfeatuwe = p-pwofiwewabewfeatuwes.is_negative_feedback_union
    ))

  /**
   * aggwegate tweet featuwes gwouped by viewew's usew id on pwofiwe nyegative engagements. UwU
   */
  vaw usewpwofiwenegativeengagementweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, -.-
      aggwegatepwefix = "pwofiwe_negative_engagement_weaw_time_usew_aggwegates_v1", (ꈍᴗꈍ)
      p-pwetwansfowms = s-seq(negativeengagementsuniontwansfowm), (⑅˘꒳˘)
      keys = set(shawedfeatuwes.usew_id), 🥺
      f-featuwes = set.empty, òωó
      wabews = pwofiwenegativeengagementwabews ++ pwofiwenegativeengagementunionwabews, 😳
      metwics = set(countmetwic), òωó
      h-hawfwives = set(30.minutes, 🥺 72.houws, ( ͡o ω ͡o ) 14.day),
      outputstowe = pwoductionstowe, UwU
      incwudeanyfeatuwe = twue, 😳😳😳
      incwudeanywabew = f-fawse, ʘwʘ
      i-incwudetimestampfeatuwe = f-fawse, ^^
    )

  /**
   * aggwegate tweet featuwes gwouped by v-viewew's and authow's u-usew ids a-and on pwofiwe engagements
   */
  vaw usewauthowpwofiweengagementweawtimeaggwegates =
    a-aggwegategwoup(
      inputsouwce = inputsouwce, >_<
      a-aggwegatepwefix = "usew_authow_pwofiwe_weaw_time_aggwegates_v1", (ˆ ﻌ ˆ)♡
      keys = s-set(shawedfeatuwes.usew_id, (ˆ ﻌ ˆ)♡ timewinesshawedfeatuwes.souwce_authow_id), 🥺
      f-featuwes = set.empty, ( ͡o ω ͡o )
      wabews = p-pwofiwecowewabews, (ꈍᴗꈍ)
      metwics = s-set(countmetwic), :3
      h-hawfwives = set(30.minutes, (✿oωo) 24.houws, 72.houws), (U ᵕ U❁)
      o-outputstowe = p-pwoductionstowe, UwU
      incwudeanyfeatuwe = t-twue, ^^
      incwudeanywabew = f-fawse, /(^•ω•^)
      incwudetimestampfeatuwe = f-fawse, (˘ω˘)
    )

  /**
   * a-aggwegate tweet featuwes gwouped by viewew's a-and authow's usew ids and on nyegative pwofiwe engagements
   */
  vaw usewauthowpwofiwenegativeengagementweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = inputsouwce, OwO
      aggwegatepwefix = "usew_authow_pwofiwe_negative_engagement_weaw_time_aggwegates_v1", (U ᵕ U❁)
      p-pwetwansfowms = seq(negativeengagementsuniontwansfowm), (U ﹏ U)
      keys = s-set(shawedfeatuwes.usew_id, mya timewinesshawedfeatuwes.souwce_authow_id), (⑅˘꒳˘)
      featuwes = s-set.empty, (U ᵕ U❁)
      wabews = pwofiwenegativeengagementunionwabews, /(^•ω•^)
      m-metwics = set(countmetwic), ^•ﻌ•^
      hawfwives = set(30.minutes, (///ˬ///✿) 72.houws, o.O 14.day),
      outputstowe = p-pwoductionstowe, (ˆ ﻌ ˆ)♡
      incwudeanyfeatuwe = twue, 😳
      incwudeanywabew = f-fawse, òωó
      incwudetimestampfeatuwe = fawse, (⑅˘꒳˘)
    )

  v-vaw nyewusewauthowengagementweawtimeaggwegatespwod =
    aggwegategwoup(
      inputsouwce = i-inputsouwce, rawr
      a-aggwegatepwefix = "weaw_time_new_usew_authow_aggwegates_v1", (ꈍᴗꈍ)
      pwetwansfowms = seq(isnewusewtwansfowm), ^^
      k-keys = set(shawedfeatuwes.usew_id, (ˆ ﻌ ˆ)♡ t-timewinesshawedfeatuwes.souwce_authow_id), /(^•ω•^)
      featuwes = s-set.empty, ^^
      w-wabews = tweetcoweanddwewwwabews ++ set(
        i-is_cwicked, o.O
        is_pwofiwe_cwicked, 😳😳😳
        is_photo_expanded
      ), XD
      metwics = s-set(countmetwic), nyaa~~
      hawfwives = set(30.minutes, ^•ﻌ•^ duwation.top), :3
      o-outputstowe = p-pwoductionstowe, ^^
      i-incwudeanyfeatuwe = twue, o.O
      incwudeanywabew = fawse, ^^
      i-incwudetimestampfeatuwe = fawse, (⑅˘꒳˘)
    )

  v-vaw usewauthowengagementweawtimeaggwegatespwod = {
    // computing usew-authow w-weaw-time a-aggwegates is vewy expensive so we
    // take the union of aww majow nyegative feedback engagements t-to cweate
    // a-a singwe nyegtive wabew fow aggwegation. ʘwʘ w-we awso incwude a nyumbew of
    // cowe positive e-engagements. mya
    v-vaw binawyunionnegativeengagements =
      b-binawyunion(
        f-featuwestounify = a-awwtweetnegativeengagementwabews, >w<
        o-outputfeatuwe = is_negative_feedback_union
      )
    vaw binawyunionnegativeengagementstwansfowm = w-wichitwansfowm(binawyunionnegativeengagements)

    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, o.O
      a-aggwegatepwefix = "weaw_time_usew_authow_aggwegates_v1", OwO
      p-pwetwansfowms = s-seq(binawyunionnegativeengagementstwansfowm), -.-
      keys = set(shawedfeatuwes.usew_id, (U ﹏ U) t-timewinesshawedfeatuwes.souwce_authow_id), òωó
      f-featuwes = s-set.empty, >w<
      wabews = usewauthowengagementwabews,
      metwics = set(countmetwic), ^•ﻌ•^
      h-hawfwives = set(30.minutes, /(^•ω•^) 1.day),
      outputstowe = pwoductionstowe, ʘwʘ
      i-incwudeanyfeatuwe = twue, XD
      incwudeanywabew = f-fawse, (U ᵕ U❁)
      i-incwudetimestampfeatuwe = fawse, (ꈍᴗꈍ)
    )
  }

  /**
   * aggwegate tweet featuwes g-gwouped by wist i-id. rawr x3
   */
  vaw wistengagementweawtimeaggwegatespwod =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, :3
      aggwegatepwefix = "weaw_time_wist_aggwegates_v1", (˘ω˘)
      keys = set(wistfeatuwes.wist_id), -.-
      f-featuwes = set.empty,
      w-wabews =
        tweetcoweanddwewwwabews ++ tweetnegativeengagementwabews ++ tweetnegativeengagementdontwikewabews, (ꈍᴗꈍ)
      m-metwics = s-set(countmetwic), UwU
      hawfwives = set(30.minutes, σωσ d-duwation.top), ^^
      outputstowe = pwoductionstowe, :3
      incwudeanywabew = fawse, ʘwʘ
      incwudetimestampfeatuwe = fawse,
    )

  // a-aggwegate featuwes gwouped by topic o-of tweet and countwy f-fwom usew's w-wocation
  vaw topiccountwyweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = i-inputsouwce, 😳
      a-aggwegatepwefix = "weaw_time_topic_countwy_aggwegates_v1", ^^
      k-keys = set(timewinesshawedfeatuwes.topic_id, σωσ usewfeatuwesadaptew.usew_countwy_id), /(^•ω•^)
      f-featuwes = s-set.empty, 😳😳😳
      w-wabews =
        tweetcoweanddwewwwabews ++ a-awwtweetnegativeengagementwabews ++ p-pwivateengagementwabewsv2 ++ shaweengagementwabews, 😳
      m-metwics = set(countmetwic), OwO
      h-hawfwives = s-set(30.minutes, :3 72.houws), nyaa~~
      o-outputstowe = p-pwoductionstowe,
      i-incwudeanywabew = fawse, OwO
      incwudetimestampfeatuwe = f-fawse, o.O
    )

  // aggwegate f-featuwes gwouped b-by tweetid_countwy fwom usew's wocation
  vaw tweetcountwyweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, (U ﹏ U)
      aggwegatepwefix = "weaw_time_tweet_countwy_aggwegates_v1", (⑅˘꒳˘)
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id, OwO u-usewfeatuwesadaptew.usew_countwy_id), 😳
      featuwes = set.empty, :3
      wabews = t-tweetcoweanddwewwwabews ++ a-awwtweetnegativeengagementwabews, ( ͡o ω ͡o )
      m-metwics = s-set(countmetwic), 🥺
      h-hawfwives = s-set(30.minutes, /(^•ω•^) duwation.top), nyaa~~
      outputstowe = p-pwoductionstowe,
      incwudeanywabew = twue, (✿oωo)
      incwudetimestampfeatuwe = fawse, (✿oωo)
    )

  // a-additionaw a-aggwegate featuwes gwouped by tweetid_countwy fwom usew's w-wocation
  vaw t-tweetcountwypwivateengagementsweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = i-inputsouwce, (ꈍᴗꈍ)
      aggwegatepwefix = "weaw_time_tweet_countwy_aggwegates_v2", OwO
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id, :3 u-usewfeatuwesadaptew.usew_countwy_id), mya
      f-featuwes = set.empty, >_<
      w-wabews = pwivateengagementwabewsv2 ++ shaweengagementwabews, (///ˬ///✿)
      metwics = set(countmetwic), (///ˬ///✿)
      h-hawfwives = set(30.minutes, 😳😳😳 72.houws),
      outputstowe = p-pwoductionstowe, (U ᵕ U❁)
      incwudeanywabew = f-fawse, (///ˬ///✿)
      incwudetimestampfeatuwe = fawse, ( ͡o ω ͡o )
    )

  // aggwegate f-featuwes gwouped by tweetid_countwy f-fwom usew's wocation
  vaw tweetcountwyvewifiednegativeengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      inputsouwce = i-inputsouwce, (✿oωo)
      aggwegatepwefix = "weaw_time_tweet_countwy_aggwegates_v3", òωó
      pwetwansfowms = seq(wichwemoveunvewifiedusewtwansfowm), (ˆ ﻌ ˆ)♡
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id, :3 usewfeatuwesadaptew.usew_countwy_id), (ˆ ﻌ ˆ)♡
      featuwes = s-set.empty, (U ᵕ U❁)
      w-wabews = a-awwtweetnegativeengagementwabews, (U ᵕ U❁)
      m-metwics = set(countmetwic), XD
      hawfwives = s-set(30.minutes, nyaa~~ duwation.top), (ˆ ﻌ ˆ)♡
      outputstowe = pwoductionstowe, ʘwʘ
      i-incwudeanywabew = t-twue, ^•ﻌ•^
      i-incwudetimestampfeatuwe = f-fawse, mya
    )

  object positiontwanfowms extends ispositiontwansfowm {
    ovewwide vaw i-isinpositionwangefeatuwe: s-seq[positioncase] =
      seq(positioncase(1, (ꈍᴗꈍ) is_top_one), positioncase(5, i-is_top_five), positioncase(10, (ˆ ﻌ ˆ)♡ i-is_top_ten))
    o-ovewwide v-vaw decodedpositionfeatuwe: featuwe.discwete =
      cwientwogeventdatawecowdfeatuwes.injectedposition
  }

  vaw usewpositionengagementscountspwod =
    aggwegategwoup(
      inputsouwce = inputsouwce, (ˆ ﻌ ˆ)♡
      a-aggwegatepwefix = "weaw_time_position_based_usew_aggwegates_v1", ( ͡o ω ͡o )
      keys = s-set(shawedfeatuwes.usew_id), o.O
      featuwes = set(is_top_one, 😳😳😳 is_top_five, is_top_ten), ʘwʘ
      w-wabews = tweetcoweanddwewwwabews, :3
      m-metwics = set(countmetwic), UwU
      hawfwives = s-set(30.minutes, nyaa~~ 24.houws), :3
      o-outputstowe = p-pwoductionstowe, nyaa~~
      p-pwetwansfowms = s-seq(positiontwanfowms), ^^
      incwudeanywabew = f-fawse, nyaa~~
      i-incwudeanyfeatuwe = fawse, 😳😳😳
      i-incwudetimestampfeatuwe = fawse, ^•ﻌ•^
    )

  vaw usewpositionengagementssumpwod =
    a-aggwegategwoup(
      inputsouwce = inputsouwce, (⑅˘꒳˘)
      a-aggwegatepwefix = "weaw_time_position_based_usew_sum_aggwegates_v2", (✿oωo)
      k-keys = set(shawedfeatuwes.usew_id), mya
      f-featuwes = s-set(wog_position), (///ˬ///✿)
      wabews = tweetcoweanddwewwwabews, ʘwʘ
      metwics = set(summetwic), >w<
      h-hawfwives = set(30.minutes, o.O 24.houws),
      o-outputstowe = pwoductionstowe, ^^;;
      p-pwetwansfowms =
        s-seq(new wogtwansfowm(cwientwogeventdatawecowdfeatuwes.injectedposition, :3 wog_position)), (ꈍᴗꈍ)
      incwudeanywabew = f-fawse,
      incwudeanyfeatuwe = fawse, XD
      i-incwudetimestampfeatuwe = fawse, ^^;;
    )

  // aggwegates f-fow shawe engagements
  vaw tweetshaweengagementsweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = i-inputsouwce,
      aggwegatepwefix = "weaw_time_tweet_shawe_aggwegates_v1", (U ﹏ U)
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), (ꈍᴗꈍ)
      f-featuwes = s-set.empty, 😳
      wabews = s-shaweengagementwabews, rawr
      m-metwics = set(countmetwic), ( ͡o ω ͡o )
      hawfwives = set(30.minutes, (ˆ ﻌ ˆ)♡ 24.houws), OwO
      outputstowe = p-pwoductionstowe, >_<
      i-incwudeanywabew = f-fawse, XD
      i-incwudetimestampfeatuwe = fawse, (ˆ ﻌ ˆ)♡
    )

  v-vaw u-usewshaweengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      inputsouwce = i-inputsouwce, (ꈍᴗꈍ)
      aggwegatepwefix = "weaw_time_usew_shawe_aggwegates_v1", (✿oωo)
      keys = set(shawedfeatuwes.usew_id), UwU
      featuwes = set.empty, (ꈍᴗꈍ)
      wabews = s-shaweengagementwabews, (U ﹏ U)
      metwics = s-set(countmetwic), >w<
      hawfwives = set(30.minutes, ^•ﻌ•^ 24.houws), 😳
      o-outputstowe = pwoductionstowe, XD
      incwudeanywabew = f-fawse,
      i-incwudetimestampfeatuwe = f-fawse, :3
    )

  v-vaw usewauthowshaweengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      inputsouwce = inputsouwce,
      a-aggwegatepwefix = "weaw_time_usew_authow_shawe_aggwegates_v1", rawr x3
      keys = s-set(shawedfeatuwes.usew_id, (⑅˘꒳˘) timewinesshawedfeatuwes.souwce_authow_id), ^^
      featuwes = set.empty, >w<
      wabews = shaweengagementwabews, 😳
      m-metwics = set(countmetwic), rawr
      hawfwives = s-set(30.minutes, rawr x3 24.houws), (ꈍᴗꈍ)
      outputstowe = pwoductionstowe, -.-
      i-incwudeanyfeatuwe = twue, òωó
      i-incwudeanywabew = fawse, (U ﹏ U)
      incwudetimestampfeatuwe = f-fawse, ( ͡o ω ͡o )
    )

  vaw topicshaweengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      inputsouwce = i-inputsouwce, :3
      a-aggwegatepwefix = "weaw_time_topic_shawe_aggwegates_v1", >w<
      keys = set(timewinesshawedfeatuwes.topic_id), ^^
      f-featuwes = set.empty, 😳😳😳
      wabews = s-shaweengagementwabews, OwO
      m-metwics = set(countmetwic), XD
      h-hawfwives = set(30.minutes, (⑅˘꒳˘) 24.houws), OwO
      outputstowe = pwoductionstowe, (⑅˘꒳˘)
      incwudeanywabew = fawse, (U ﹏ U)
      incwudetimestampfeatuwe = fawse, (ꈍᴗꈍ)
    )

  v-vaw authowshaweengagementsweawtimeaggwegates =
    aggwegategwoup(
      i-inputsouwce = i-inputsouwce, rawr
      aggwegatepwefix = "weaw_time_authow_shawe_aggwegates_v1", XD
      keys = set(timewinesshawedfeatuwes.souwce_authow_id), >w<
      f-featuwes = set.empty, UwU
      wabews = s-shaweengagementwabews, 😳
      metwics = set(countmetwic), (ˆ ﻌ ˆ)♡
      hawfwives = set(30.minutes, ^•ﻌ•^ 24.houws),
      o-outputstowe = pwoductionstowe, ^^
      i-incwudeanywabew = fawse, 😳
      incwudetimestampfeatuwe = f-fawse, :3
    )

  // b-bookmawk wtas
  vaw tweetbookmawkengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = inputsouwce, (⑅˘꒳˘)
      aggwegatepwefix = "weaw_time_tweet_bookmawk_aggwegates_v1", ( ͡o ω ͡o )
      k-keys = set(timewinesshawedfeatuwes.souwce_tweet_id), :3
      featuwes = s-set.empty, (⑅˘꒳˘)
      w-wabews = b-bookmawkengagementwabews, >w<
      m-metwics = set(countmetwic), OwO
      h-hawfwives = set(30.minutes, 😳 24.houws), OwO
      o-outputstowe = p-pwoductionstowe, 🥺
      incwudeanywabew = fawse, (˘ω˘)
      i-incwudetimestampfeatuwe = fawse, 😳😳😳
    )

  v-vaw usewbookmawkengagementsweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = inputsouwce, mya
      aggwegatepwefix = "weaw_time_usew_bookmawk_aggwegates_v1", OwO
      keys = set(shawedfeatuwes.usew_id), >_<
      f-featuwes = set.empty,
      w-wabews = bookmawkengagementwabews, 😳
      metwics = s-set(countmetwic), (U ᵕ U❁)
      h-hawfwives = set(30.minutes, 🥺 24.houws),
      o-outputstowe = pwoductionstowe, (U ﹏ U)
      incwudeanywabew = f-fawse, (U ﹏ U)
      incwudetimestampfeatuwe = fawse, rawr x3
    )

  v-vaw usewauthowbookmawkengagementsweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = inputsouwce, :3
      aggwegatepwefix = "weaw_time_usew_authow_bookmawk_aggwegates_v1", rawr
      keys = set(shawedfeatuwes.usew_id, XD timewinesshawedfeatuwes.souwce_authow_id), ^^
      f-featuwes = set.empty, mya
      wabews = bookmawkengagementwabews, (U ﹏ U)
      m-metwics = set(countmetwic), 😳
      h-hawfwives = set(30.minutes, mya 24.houws), 😳
      outputstowe = pwoductionstowe, ^^
      incwudeanyfeatuwe = twue, :3
      incwudeanywabew = fawse, (U ﹏ U)
      incwudetimestampfeatuwe = fawse, UwU
    )

  vaw authowbookmawkengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = i-inputsouwce, (ˆ ﻌ ˆ)♡
      aggwegatepwefix = "weaw_time_authow_bookmawk_aggwegates_v1", (ˆ ﻌ ˆ)♡
      k-keys = set(timewinesshawedfeatuwes.souwce_authow_id), ^^;;
      f-featuwes = set.empty, rawr
      wabews = b-bookmawkengagementwabews, nyaa~~
      metwics = set(countmetwic), rawr x3
      h-hawfwives = s-set(30.minutes, (⑅˘꒳˘) 24.houws), OwO
      outputstowe = p-pwoductionstowe, OwO
      i-incwudeanywabew = f-fawse, ʘwʘ
      i-incwudetimestampfeatuwe = f-fawse, :3
    )

  /**
   * aggwegate o-on usew w-wevew dweww wabews f-fwom bce
   */
  v-vaw usewbcedwewwengagementsweawtimeaggwegates =
    a-aggwegategwoup(
      i-inputsouwce = i-inputsouwce, mya
      aggwegatepwefix = "weaw_time_usew_bce_dweww_aggwegates", OwO
      k-keys = s-set(shawedfeatuwes.usew_id), :3
      f-featuwes = set.empty, >_<
      wabews = awwbcedwewwwabews, σωσ
      metwics = s-set(countmetwic), /(^•ω•^)
      hawfwives = s-set(30.minutes, mya 24.houws), nyaa~~
      outputstowe = pwoductionstowe, 😳
      i-incwudeanywabew = f-fawse, ^^;;
      i-incwudetimestampfeatuwe = fawse, 😳😳😳
    )

  /**
   * a-aggwegate o-on tweet wevew dweww wabews fwom bce
   */
  vaw tweetbcedwewwengagementsweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = i-inputsouwce, nyaa~~
      aggwegatepwefix = "weaw_time_tweet_bce_dweww_aggwegates", 🥺
      keys = set(timewinesshawedfeatuwes.souwce_tweet_id), XD
      featuwes = set.empty, (ꈍᴗꈍ)
      w-wabews = awwbcedwewwwabews, 😳😳😳
      m-metwics = set(countmetwic), ( ͡o ω ͡o )
      h-hawfwives = s-set(30.minutes, nyaa~~ 24.houws),
      o-outputstowe = p-pwoductionstowe, XD
      i-incwudeanywabew = f-fawse, (ˆ ﻌ ˆ)♡
      i-incwudetimestampfeatuwe = fawse, rawr x3
    )

  vaw impwicitpositiveengagementsuniontwansfowm = w-wichitwansfowm(
    binawyunion(
      f-featuwestounify = combinedfeatuwes.impwicitpositiveengagements, OwO
      o-outputfeatuwe = c-combinedfeatuwes.is_impwicit_positive_feedback_union
    )
  )

  vaw expwicitpositiveengagementsuniontwansfowm = w-wichitwansfowm(
    binawyunion(
      featuwestounify = c-combinedfeatuwes.expwicitpositiveengagements, UwU
      o-outputfeatuwe = c-combinedfeatuwes.is_expwicit_positive_feedback_union
    )
  )

  v-vaw awwnegativeengagementsuniontwansfowm = wichitwansfowm(
    b-binawyunion(
      f-featuwestounify = c-combinedfeatuwes.awwnegativeengagements, ^^
      outputfeatuwe = c-combinedfeatuwes.is_aww_negative_feedback_union
    )
  )

  /**
   * aggwegate featuwes fow authow content pwefewence
   */
  vaw authowcontentpwefewenceweawtimeaggwegates =
    aggwegategwoup(
      inputsouwce = inputsouwce, (✿oωo)
      aggwegatepwefix = "weaw_time_authow_content_pwefewence_aggwegates", 😳😳😳
      p-pwetwansfowms = s-seq(
        impwicitpositiveengagementsuniontwansfowm, 🥺
        expwicitpositiveengagementsuniontwansfowm, ʘwʘ
        awwnegativeengagementsuniontwansfowm), 😳
      keys = s-set(timewinesshawedfeatuwes.souwce_authow_id), ^^;;
      f-featuwes =
        cwientwogeventdatawecowdfeatuwes.authowcontentpwefewencetweettypefeatuwes ++ authowfeatuwesadaptew.usewstatebooweanfeatuwes, (///ˬ///✿)
      wabews = a-awwtweetunionwabews, OwO
      m-metwics = set(countmetwic), -.-
      hawfwives = set(24.houws), ^^
      o-outputstowe = p-pwoductionstowe, (ꈍᴗꈍ)
      incwudeanywabew = f-fawse, ^^;;
      incwudeanyfeatuwe = f-fawse, (˘ω˘)
    )

  v-vaw featuwesgenewatedbypwetwansfowms = set(wog_position, 🥺 is_top_ten, ʘwʘ is_top_five, (///ˬ///✿) is_top_one)

  v-vaw p-pwodaggwegategwoups = s-set(
    tweetengagement30minutecountspwod, ^^;;
    t-tweetengagementtotawcountspwod, XD
    tweetnegativeengagement6houwcounts, (ˆ ﻌ ˆ)♡
    t-tweetnegativeengagementtotawcounts, (˘ω˘)
    u-usewengagementweawtimeaggwegatespwod, σωσ
    u-usewengagement48houwweawtimeaggwegatespwod, 😳😳😳
    u-usewnegativeengagementauthowusewstateweawtimeaggwegates, ^•ﻌ•^
    usewnegativeengagementauthowusewstate72houwweawtimeaggwegates, σωσ
    authowengagementweawtimeaggwegatespwod, (///ˬ///✿)
    t-topicengagementweawtimeaggwegatespwod, XD
    t-topicengagement24houwweawtimeaggwegatespwod, >_<
    tweetengagementusewstateweawtimeaggwegatespwod, òωó
    tweetnegativeengagementusewstateweawtimeaggwegates, (U ᵕ U❁)
    usewpwofiweengagementweawtimeaggwegates, (˘ω˘)
    nyewusewauthowengagementweawtimeaggwegatespwod, 🥺
    u-usewauthowengagementweawtimeaggwegatespwod, (✿oωo)
    w-wistengagementweawtimeaggwegatespwod, (˘ω˘)
    tweetcountwyweawtimeaggwegates, (ꈍᴗꈍ)
    t-tweetshaweengagementsweawtimeaggwegates,
    usewshaweengagementsweawtimeaggwegates, ( ͡o ω ͡o )
    usewauthowshaweengagementsweawtimeaggwegates, (U ᵕ U❁)
    topicshaweengagementsweawtimeaggwegates, ʘwʘ
    authowshaweengagementsweawtimeaggwegates, (ˆ ﻌ ˆ)♡
    t-tweetbookmawkengagementsweawtimeaggwegates, /(^•ω•^)
    u-usewbookmawkengagementsweawtimeaggwegates, (ˆ ﻌ ˆ)♡
    u-usewauthowbookmawkengagementsweawtimeaggwegates, (✿oωo)
    authowbookmawkengagementsweawtimeaggwegates, ^•ﻌ•^
    t-topiccountwyweawtimeaggwegates, (ˆ ﻌ ˆ)♡
    t-tweetcountwypwivateengagementsweawtimeaggwegates, XD
    usewbcedwewwengagementsweawtimeaggwegates, :3
    tweetbcedwewwengagementsweawtimeaggwegates, -.-
    a-authowcontentpwefewenceweawtimeaggwegates, ^^;;
    a-authowvewifiednegativeengagementweawtimeaggwegatespwod,
    t-tweetvewifieddontwikeengagementweawtimeaggwegatespwod, OwO
    t-tweetvewifiednegativeengagementcounts, ^^;;
    t-tweetvewifiednegativeengagementusewstateweawtimeaggwegates, 🥺
    t-tweetcountwyvewifiednegativeengagementsweawtimeaggwegates
  ).map(
    addfeatuwefiwtewfwomwesouwce(
      _,
      "com/twittew/timewines/pwediction/common/aggwegates/weaw_time/aggwegates_to_dwop.txt"))

  vaw stagingaggwegategwoups = pwodaggwegategwoups.map(cweatestaginggwoup)

  /**
   * contains the fuwwy typed aggwegate g-gwoups fwom which impowtant
   * v-vawues can be d-dewived e.g. ^^ the featuwes to be computed, o.O hawfwives etc. ( ͡o ω ͡o )
   */
  o-ovewwide vaw p-pwodaggwegates = pwodaggwegategwoups.fwatmap(_.buiwdtypedaggwegategwoups())

  ovewwide v-vaw stagingaggwegates = stagingaggwegategwoups.fwatmap(_.buiwdtypedaggwegategwoups())


  o-ovewwide vaw pwodcommonaggwegates = pwodaggwegates
    .fiwtew(_.keystoaggwegate == set(shawedfeatuwes.usew_id))

  /**
   * this defines the s-set of sewected featuwes fwom a candidate
   * that we'd wike to send to the sewved f-featuwes cache b-by twm. nyaa~~
   * t-these shouwd incwude  i-intewesting and nyecessawy featuwes that
   * c-cannot be extwacted fwom wogevents o-onwy by the weaw-time aggwegates
   * job. (///ˬ///✿) i-if you awe adding n-nyew aggwegategwoups w-wequiwing twm-side
   * candidate featuwes, (ˆ ﻌ ˆ)♡ m-make suwe to add them hewe. XD
   */
  vaw candidatefeatuwestocache: set[featuwe[_]] = set(
    timewinesshawedfeatuwes.souwce_authow_id, >_<
    wecapfeatuwes.hashtags, (U ﹏ U)
    w-wecapfeatuwes.mentioned_scween_names, òωó
    w-wecapfeatuwes.uww_domains
  )
}

/**
 * this config shouwd onwy be used to access the aggwegate featuwes constwucted b-by the
 * aggwegation config, >w< and nyot f-fow impwementing a-an onwine weaw-time a-aggwegates j-job. ^•ﻌ•^
 */
object timewinesonwineaggwegationfeatuwesonwyconfig
    extends timewinesonwineaggwegationdefinitionstwait {

  pwivate[weaw_time] case cwass dummyaggwegatesouwce(name: s-stwing, 🥺 timestampfeatuwe: f-featuwe[jwong])
      e-extends aggwegatesouwce

  pwivate[weaw_time] c-case cwass dummyaggwegatestowe(name: stwing) extends a-aggwegatestowe

  ovewwide w-wazy vaw inputsouwce = dummyaggwegatesouwce(
    nyame = "timewines_wta",
    timestampfeatuwe = s-shawedfeatuwes.timestamp
  )
  o-ovewwide wazy v-vaw pwoductionstowe = d-dummyaggwegatestowe("timewines_wta")
  ovewwide w-wazy vaw stagingstowe = d-dummyaggwegatestowe("timewines_wta")

  ovewwide wazy vaw aggwegatestocompute = pwodaggwegates ++ s-stagingaggwegates
}

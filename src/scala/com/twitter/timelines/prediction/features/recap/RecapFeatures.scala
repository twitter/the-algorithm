package com.twittew.timewines.pwediction.featuwes.wecap

impowt com.twittew.daw.pewsonaw_data.thwiftjava.pewsonawdatatype._
i-impowt c-com.twittew.mw.api.featuwe.binawy
i-impowt com.twittew.mw.api.featuwe.continuous
i-impowt com.twittew.mw.api.featuwe.discwete
i-impowt c-com.twittew.mw.api.featuwe.spawsebinawy
i-impowt c-com.twittew.mw.api.featuwe.text
impowt scawa.cowwection.javaconvewtews._

object wecapfeatuwes extends wecapfeatuwes("")
o-object inwepwytowecapfeatuwes extends w-wecapfeatuwes("in_wepwy_to_tweet")

cwass wecapfeatuwes(pwefix: s-stwing) {
  pwivate def nyame(featuwename: stwing): stwing = {
    i-if (pwefix.nonempty) {
      s"$pwefix.$featuwename"
    } ewse {
      f-featuwename
    }
  }

  v-vaw is_ipad_cwient = nyew binawy(name("wecap.cwient.is_ipad"), set(cwienttype).asjava)
  vaw is_web_cwient = n-nyew binawy(name("wecap.cwient.is_web"), òωó set(cwienttype).asjava)
  vaw is_iphone_cwient = nyew binawy(name("wecap.cwient.is_phone"), ^^;; s-set(cwienttype).asjava)
  vaw is_andwoid_cwient = n-nyew binawy(name("wecap.cwient.is_andwoid"), (✿oωo) s-set(cwienttype).asjava)
  v-vaw is_andwoid_tabwet_cwient =
    n-nyew binawy(name("wecap.cwient.is_andwoid_tabwet"), rawr set(cwienttype).asjava)

  // featuwes fwom u-usewagent
  vaw cwient_name = nyew text(name("wecap.usew_agent.cwient_name"), XD s-set(cwienttype).asjava)
  vaw cwient_souwce = nyew discwete(name("wecap.usew_agent.cwient_souwce"), 😳 set(cwienttype).asjava)
  vaw cwient_vewsion = nyew text(name("wecap.usew_agent.cwient_vewsion"), (U ᵕ U❁) s-set(cwientvewsion).asjava)
  vaw cwient_vewsion_code =
    n-nyew text(name("wecap.usew_agent.cwient_vewsion_code"), UwU s-set(cwientvewsion).asjava)
  v-vaw device = nyew text(name("wecap.usew_agent.device"), OwO set(devicetype).asjava)
  vaw fwom_dog_food = nyew b-binawy(name("wecap.meta.fwom_dog_food"), 😳 s-set(usewagent).asjava)
  vaw fwom_twittew_cwient =
    n-nyew binawy(name("wecap.usew_agent.fwom_twittew_cwient"), (˘ω˘) s-set(usewagent).asjava)
  vaw manufactuwew = n-nyew text(name("wecap.usew_agent.manufactuwew"), òωó set(usewagent).asjava)
  v-vaw modew = nyew text(name("wecap.usew_agent.modew"), OwO set(usewagent).asjava)
  v-vaw nyetwowk_connection =
    new discwete(name("wecap.usew_agent.netwowk_connection"), (✿oωo) s-set(usewagent).asjava)
  vaw sdk_vewsion = n-nyew text(name("wecap.usew_agent.sdk_vewsion"), (⑅˘꒳˘) s-set(appid, /(^•ω•^) usewagent).asjava)

  // engagement
  vaw is_wetweeted = nyew binawy(
    nyame("wecap.engagement.is_wetweeted"), 🥺
    set(pubwicwetweets, -.- pwivatewetweets, ( ͡o ω ͡o ) e-engagementspwivate, 😳😳😳 e-engagementspubwic).asjava)
  vaw is_favowited = n-nyew b-binawy(
    nyame("wecap.engagement.is_favowited"), (˘ω˘)
    s-set(pubwicwikes, ^^ pwivatewikes, σωσ engagementspwivate, engagementspubwic).asjava)
  v-vaw is_wepwied = nyew binawy(
    nyame("wecap.engagement.is_wepwied"), 🥺
    set(pubwicwepwies, 🥺 pwivatewepwies, /(^•ω•^) e-engagementspwivate, (⑅˘꒳˘) engagementspubwic).asjava)
  // v-v1: p-post cwick engagements: f-fav, -.- wepwy
  vaw is_good_cwicked_convo_desc_v1 = n-nyew b-binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_favowited_ow_wepwied"), 😳
    s-set(
      p-pubwicwikes, 😳😳😳
      pwivatewikes, >w<
      pubwicwepwies,
      p-pwivatewepwies, UwU
      e-engagementspwivate, /(^•ω•^)
      e-engagementspubwic).asjava)
  // v-v2: post cwick e-engagements: cwick
  vaw is_good_cwicked_convo_desc_v2 = nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_v2"), 🥺
    s-set(tweetscwicked, >_< engagementspwivate).asjava)

  vaw is_good_cwicked_convo_desc_favowited = nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_favowited"), rawr
    set(pubwicwikes, (ꈍᴗꈍ) p-pwivatewikes, -.- engagementspwivate, ( ͡o ω ͡o ) engagementspubwic).asjava)
  vaw is_good_cwicked_convo_desc_wepwied = n-nyew binawy(
    n-nyame("wecap.engagement.is_good_cwicked_convo_desc_wepwied"), (⑅˘꒳˘)
    s-set(pubwicwepwies, mya pwivatewepwies, rawr x3 e-engagementspwivate, (ꈍᴗꈍ) engagementspubwic).asjava)
  v-vaw is_good_cwicked_convo_desc_wetweeted = n-nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_wetweeted"), ʘwʘ
    set(pubwicwetweets, :3 pwivatewetweets, o.O engagementspwivate, /(^•ω•^) engagementspubwic).asjava)
  vaw is_good_cwicked_convo_desc_cwicked = n-nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_cwicked"), OwO
    s-set(tweetscwicked, σωσ engagementspwivate).asjava)
  vaw i-is_good_cwicked_convo_desc_fowwowed = n-nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_fowwowed"), (ꈍᴗꈍ)
    set(engagementspwivate).asjava)
  v-vaw i-is_good_cwicked_convo_desc_shawe_dm_cwicked = nyew binawy(
    n-nyame("wecap.engagement.is_good_cwicked_convo_desc_shawe_dm_cwicked"), ( ͡o ω ͡o )
    s-set(engagementspwivate).asjava)
  vaw is_good_cwicked_convo_desc_pwofiwe_cwicked = nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_pwofiwe_cwicked"), rawr x3
    s-set(engagementspwivate).asjava)

  v-vaw i-is_good_cwicked_convo_desc_uam_gt_0 = nyew binawy(
    n-nyame("wecap.engagement.is_good_cwicked_convo_desc_uam_gt_0"), UwU
    s-set(engagementspwivate, o.O engagementspubwic).asjava)
  vaw i-is_good_cwicked_convo_desc_uam_gt_1 = nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_uam_gt_1"), OwO
    set(engagementspwivate, o.O engagementspubwic).asjava)
  v-vaw is_good_cwicked_convo_desc_uam_gt_2 = n-new binawy(
    name("wecap.engagement.is_good_cwicked_convo_desc_uam_gt_2"), ^^;;
    set(engagementspwivate, (⑅˘꒳˘) e-engagementspubwic).asjava)
  v-vaw is_good_cwicked_convo_desc_uam_gt_3 = nyew binawy(
    nyame("wecap.engagement.is_good_cwicked_convo_desc_uam_gt_3"), (ꈍᴗꈍ)
    set(engagementspwivate, o.O e-engagementspubwic).asjava)

  vaw is_tweet_detaiw_dwewwed = nyew binawy(
    nyame("wecap.engagement.is_tweet_detaiw_dwewwed"), (///ˬ///✿)
    set(tweetscwicked, 😳😳😳 e-engagementspwivate).asjava)
  vaw is_tweet_detaiw_dwewwed_8_sec = n-nyew binawy(
    n-nyame("wecap.engagement.is_tweet_detaiw_dwewwed_8_sec"), UwU
    set(tweetscwicked, nyaa~~ engagementspwivate).asjava)
  vaw is_tweet_detaiw_dwewwed_15_sec = n-nyew binawy(
    n-nyame("wecap.engagement.is_tweet_detaiw_dwewwed_15_sec"), (✿oωo)
    set(tweetscwicked, -.- engagementspwivate).asjava)
  vaw is_tweet_detaiw_dwewwed_25_sec = n-nyew binawy(
    nyame("wecap.engagement.is_tweet_detaiw_dwewwed_25_sec"), :3
    s-set(tweetscwicked, (⑅˘꒳˘) engagementspwivate).asjava)
  vaw is_tweet_detaiw_dwewwed_30_sec = nyew binawy(
    n-nyame("wecap.engagement.is_tweet_detaiw_dwewwed_30_sec"), >_<
    set(tweetscwicked, UwU engagementspwivate).asjava)

  v-vaw is_pwofiwe_dwewwed = n-nyew binawy(
    "wecap.engagement.is_pwofiwe_dwewwed", rawr
    set(pwofiwesviewed, (ꈍᴗꈍ) p-pwofiwescwicked, ^•ﻌ•^ engagementspwivate).asjava)
  v-vaw is_pwofiwe_dwewwed_10_sec = n-new binawy(
    "wecap.engagement.is_pwofiwe_dwewwed_10_sec", ^^
    s-set(pwofiwesviewed, XD pwofiwescwicked, engagementspwivate).asjava)
  v-vaw i-is_pwofiwe_dwewwed_20_sec = nyew binawy(
    "wecap.engagement.is_pwofiwe_dwewwed_20_sec", (///ˬ///✿)
    s-set(pwofiwesviewed, σωσ p-pwofiwescwicked, :3 e-engagementspwivate).asjava)
  vaw is_pwofiwe_dwewwed_30_sec = nyew binawy(
    "wecap.engagement.is_pwofiwe_dwewwed_30_sec", >w<
    s-set(pwofiwesviewed, (ˆ ﻌ ˆ)♡ pwofiwescwicked, (U ᵕ U❁) e-engagementspwivate).asjava)

  v-vaw is_fuwwscween_video_dwewwed = nyew binawy(
    "wecap.engagement.is_fuwwscween_video_dwewwed", :3
    set(mediaengagementactivities, ^^ e-engagementtypepwivate, ^•ﻌ•^ e-engagementspwivate).asjava)

  v-vaw is_fuwwscween_video_dwewwed_5_sec = nyew b-binawy(
    "wecap.engagement.is_fuwwscween_video_dwewwed_5_sec", (///ˬ///✿)
    set(mediaengagementactivities, 🥺 e-engagementtypepwivate, ʘwʘ engagementspwivate).asjava)

  vaw is_fuwwscween_video_dwewwed_10_sec = nyew binawy(
    "wecap.engagement.is_fuwwscween_video_dwewwed_10_sec", (✿oωo)
    set(mediaengagementactivities, rawr engagementtypepwivate, OwO e-engagementspwivate).asjava)

  vaw is_fuwwscween_video_dwewwed_20_sec = n-nyew binawy(
    "wecap.engagement.is_fuwwscween_video_dwewwed_20_sec", ^^
    set(mediaengagementactivities, ʘwʘ e-engagementtypepwivate, σωσ engagementspwivate).asjava)

  v-vaw is_fuwwscween_video_dwewwed_30_sec = nyew b-binawy(
    "wecap.engagement.is_fuwwscween_video_dwewwed_30_sec", (⑅˘꒳˘)
    s-set(mediaengagementactivities, (ˆ ﻌ ˆ)♡ e-engagementtypepwivate, :3 e-engagementspwivate).asjava)

  v-vaw is_wink_dwewwed_15_sec = nyew binawy(
    "wecap.engagement.is_wink_dwewwed_15_sec", ʘwʘ
    set(mediaengagementactivities, (///ˬ///✿) engagementtypepwivate, (ˆ ﻌ ˆ)♡ engagementspwivate).asjava)

  vaw is_wink_dwewwed_30_sec = n-nyew b-binawy(
    "wecap.engagement.is_wink_dwewwed_30_sec", 🥺
    s-set(mediaengagementactivities, rawr engagementtypepwivate, (U ﹏ U) e-engagementspwivate).asjava)

  vaw is_wink_dwewwed_60_sec = nyew binawy(
    "wecap.engagement.is_wink_dwewwed_60_sec", ^^
    s-set(mediaengagementactivities, σωσ e-engagementtypepwivate, :3 engagementspwivate).asjava)

  v-vaw is_quoted = new binawy(
    name("wecap.engagement.is_quoted"), ^^
    s-set(pubwicwetweets, (✿oωo) pwivatewetweets, òωó e-engagementspwivate, (U ᵕ U❁) engagementspubwic).asjava)
  v-vaw is_wetweeted_without_quote = n-nyew binawy(
    nyame("wecap.engagement.is_wetweeted_without_quote"), ʘwʘ
    set(pubwicwetweets, ( ͡o ω ͡o ) pwivatewetweets, σωσ engagementspwivate, (ˆ ﻌ ˆ)♡ e-engagementspubwic).asjava)
  v-vaw is_cwicked =
    n-nyew binawy(name("wecap.engagement.is_cwicked"), (˘ω˘) s-set(tweetscwicked, 😳 e-engagementspwivate).asjava)
  vaw is_dwewwed = n-nyew b-binawy(name("wecap.engagement.is_dwewwed"), ^•ﻌ•^ set(engagementspwivate).asjava)
  v-vaw i-is_dwewwed_in_bounds_v1 =
    nyew binawy(name("wecap.engagement.is_dwewwed_in_bounds_v1"), σωσ s-set(engagementspwivate).asjava)
  vaw dweww_nowmawized_ovewaww = nyew continuous(
    n-nyame("wecap.engagement.dweww_nowmawized_ovewaww"),
    set(engagementspwivate).asjava)
  v-vaw d-dweww_cdf_ovewaww =
    new continuous(name("wecap.engagement.dweww_cdf_ovewaww"), 😳😳😳 s-set(engagementspwivate).asjava)
  vaw dweww_cdf = nyew continuous(name("wecap.engagement.dweww_cdf"), rawr s-set(engagementspwivate).asjava)

  v-vaw i-is_dwewwed_1s =
    nyew binawy(name("wecap.engagement.is_dwewwed_1s"), >_< set(engagementspwivate).asjava)
  vaw i-is_dwewwed_2s =
    nyew binawy(name("wecap.engagement.is_dwewwed_2s"), ʘwʘ set(engagementspwivate).asjava)
  v-vaw is_dwewwed_3s =
    n-nyew binawy(name("wecap.engagement.is_dwewwed_3s"), set(engagementspwivate).asjava)
  v-vaw is_dwewwed_4s =
    nyew binawy(name("wecap.engagement.is_dwewwed_4s"), s-set(engagementspwivate).asjava)
  v-vaw is_dwewwed_5s =
    nyew binawy(name("wecap.engagement.is_dwewwed_5s"), (ˆ ﻌ ˆ)♡ s-set(engagementspwivate).asjava)
  vaw is_dwewwed_6s =
    nyew b-binawy(name("wecap.engagement.is_dwewwed_6s"), ^^;; s-set(engagementspwivate).asjava)
  vaw is_dwewwed_7s =
    n-nyew binawy(name("wecap.engagement.is_dwewwed_7s"), σωσ set(engagementspwivate).asjava)
  v-vaw is_dwewwed_8s =
    n-nyew binawy(name("wecap.engagement.is_dwewwed_8s"), rawr x3 s-set(engagementspwivate).asjava)
  vaw is_dwewwed_9s =
    nyew binawy(name("wecap.engagement.is_dwewwed_9s"), 😳 set(engagementspwivate).asjava)
  vaw is_dwewwed_10s =
    nyew binawy(name("wecap.engagement.is_dwewwed_10s"), 😳😳😳 set(engagementspwivate).asjava)

  vaw is_skipped_1s =
    new binawy(name("wecap.engagement.is_skipped_1s"), 😳😳😳 set(engagementspwivate).asjava)
  v-vaw is_skipped_2s =
    n-nyew binawy(name("wecap.engagement.is_skipped_2s"), ( ͡o ω ͡o ) set(engagementspwivate).asjava)
  vaw is_skipped_3s =
    n-nyew binawy(name("wecap.engagement.is_skipped_3s"), rawr x3 s-set(engagementspwivate).asjava)
  v-vaw is_skipped_4s =
    nyew b-binawy(name("wecap.engagement.is_skipped_4s"), σωσ set(engagementspwivate).asjava)
  v-vaw is_skipped_5s =
    n-nyew binawy(name("wecap.engagement.is_skipped_5s"), (˘ω˘) s-set(engagementspwivate).asjava)
  vaw is_skipped_6s =
    n-nyew binawy(name("wecap.engagement.is_skipped_6s"), s-set(engagementspwivate).asjava)
  vaw is_skipped_7s =
    nyew binawy(name("wecap.engagement.is_skipped_7s"), s-set(engagementspwivate).asjava)
  v-vaw i-is_skipped_8s =
    n-nyew binawy(name("wecap.engagement.is_skipped_8s"), >w< s-set(engagementspwivate).asjava)
  v-vaw i-is_skipped_9s =
    n-nyew binawy(name("wecap.engagement.is_skipped_9s"), UwU s-set(engagementspwivate).asjava)
  vaw is_skipped_10s =
    n-nyew binawy(name("wecap.engagement.is_skipped_10s"), s-set(engagementspwivate).asjava)

  v-vaw is_impwessed =
    nyew binawy(name("wecap.engagement.is_impwessed"), XD s-set(engagementspwivate).asjava)
  vaw is_fowwowed =
    nyew b-binawy("wecap.engagement.is_fowwowed", (U ﹏ U) set(engagementspwivate, (U ᵕ U❁) e-engagementspubwic).asjava)
  v-vaw i-is_pwofiwe_cwicked = nyew binawy(
    n-nyame("wecap.engagement.is_pwofiwe_cwicked"), (ˆ ﻌ ˆ)♡
    set(pwofiwesviewed, òωó p-pwofiwescwicked, ^•ﻌ•^ engagementspwivate).asjava)
  vaw i-is_open_winked = new binawy(
    n-name("wecap.engagement.is_open_winked"), (///ˬ///✿)
    set(engagementspwivate, -.- winkscwickedon).asjava)
  vaw is_photo_expanded =
    nyew binawy(name("wecap.engagement.is_photo_expanded"), >w< s-set(engagementspwivate).asjava)
  vaw is_video_viewed =
    n-nyew binawy(name("wecap.engagement.is_video_viewed"), òωó s-set(engagementspwivate).asjava)
  vaw is_video_pwayback_stawt =
    nyew binawy(name("wecap.engagement.is_video_pwayback_stawt"), σωσ s-set(engagementspwivate).asjava)
  vaw is_video_pwayback_25 =
    n-nyew binawy(name("wecap.engagement.is_video_pwayback_25"), mya s-set(engagementspwivate).asjava)
  v-vaw is_video_pwayback_50 =
    new binawy(name("wecap.engagement.is_video_pwayback_50"), òωó set(engagementspwivate).asjava)
  v-vaw is_video_pwayback_75 =
    n-nyew binawy(name("wecap.engagement.is_video_pwayback_75"), 🥺 set(engagementspwivate).asjava)
  v-vaw is_video_pwayback_95 =
    nyew b-binawy(name("wecap.engagement.is_video_pwayback_95"), (U ﹏ U) set(engagementspwivate).asjava)
  v-vaw is_video_pwayback_compwete =
    nyew b-binawy(name("wecap.engagement.is_video_pwayback_compwete"), (ꈍᴗꈍ) s-set(engagementspwivate).asjava)
  vaw is_video_viewed_and_pwayback_50 = n-nyew binawy(
    n-nyame("wecap.engagement.is_video_viewed_and_pwayback_50"), (˘ω˘)
    s-set(engagementspwivate).asjava)
  v-vaw is_video_quawity_viewed = nyew binawy(
    n-nyame("wecap.engagement.is_video_quawity_viewed"),
    s-set(engagementspwivate).asjava
  ) 
  v-vaw is_tweet_shawe_dm_cwicked =
    n-nyew binawy(name("wecap.engagement.is_tweet_shawe_dm_cwicked"), (✿oωo) s-set(engagementspwivate).asjava)
  v-vaw i-is_tweet_shawe_dm_sent =
    n-nyew binawy(name("wecap.engagement.is_tweet_shawe_dm_sent"), -.- s-set(engagementspwivate).asjava)
  vaw i-is_bookmawked =
    nyew binawy(name("wecap.engagement.is_bookmawked"), (ˆ ﻌ ˆ)♡ s-set(engagementspwivate).asjava)
  v-vaw is_shawed =
    n-nyew binawy(name("wecap.engagement.is_shawed"), (✿oωo) set(engagementspwivate).asjava)
  vaw is_shawe_menu_cwicked =
    n-nyew binawy(name("wecap.engagement.is_shawe_menu_cwicked"), ʘwʘ s-set(engagementspwivate).asjava)

  // n-negative engagements
  vaw is_dont_wike =
    nyew binawy(name("wecap.engagement.is_dont_wike"), (///ˬ///✿) set(engagementspwivate).asjava)
  v-vaw is_bwock_cwicked = n-nyew binawy(
    nyame("wecap.engagement.is_bwock_cwicked"), rawr
    s-set(tweetscwicked, 🥺 e-engagementspwivate, mya engagementspubwic).asjava)
  vaw is_bwock_diawog_bwocked = nyew binawy(
    n-nyame("wecap.engagement.is_bwock_diawog_bwocked"), mya
    s-set(engagementspwivate, mya engagementspubwic).asjava)
  v-vaw i-is_mute_cwicked = nyew binawy(
    nyame("wecap.engagement.is_mute_cwicked"),
    s-set(tweetscwicked, (⑅˘꒳˘) e-engagementspwivate).asjava)
  vaw is_mute_diawog_muted =
    nyew binawy(name("wecap.engagement.is_mute_diawog_muted"), (✿oωo) s-set(engagementspwivate).asjava)
  vaw is_wepowt_tweet_cwicked = nyew b-binawy(
    nyame("wecap.engagement.is_wepowt_tweet_cwicked"), 😳
    set(tweetscwicked, OwO e-engagementspwivate).asjava)
  v-vaw is_negative_feedback =
    nyew binawy("wecap.engagement.is_negative_feedback", (˘ω˘) s-set(engagementspwivate).asjava)
  v-vaw is_not_about_topic =
    n-nyew binawy(name("wecap.engagement.is_not_about_topic"), (✿oωo) set(engagementspwivate).asjava)
  v-vaw is_not_wecent =
    n-nyew b-binawy(name("wecap.engagement.is_not_wecent"), /(^•ω•^) s-set(engagementspwivate).asjava)
  vaw is_not_wewevant =
    n-nyew b-binawy(name("wecap.engagement.is_not_wewevant"), rawr x3 s-set(engagementspwivate).asjava)
  vaw is_see_fewew =
    n-nyew binawy(name("wecap.engagement.is_see_fewew"), rawr set(engagementspwivate).asjava)
  v-vaw is_topic_spec_neg_engagement =
    n-nyew binawy("wecap.engagement.is_topic_spec_neg_engagement", ( ͡o ω ͡o ) s-set(engagementspwivate).asjava)
  vaw is_unfowwow_topic =
    new binawy("wecap.engagement.is_unfowwow_topic", ( ͡o ω ͡o ) set(engagementspwivate).asjava)
  vaw is_unfowwow_topic_expwicit_positive_wabew =
    n-nyew binawy(
      "wecap.engagement.is_unfowwow_topic_expwicit_positive_wabew", 😳😳😳
      set(engagementspwivate).asjava)
  v-vaw is_unfowwow_topic_impwicit_positive_wabew =
    n-nyew binawy(
      "wecap.engagement.is_unfowwow_topic_impwicit_positive_wabew", (U ﹏ U)
      set(engagementspwivate).asjava)
  vaw is_unfowwow_topic_stwong_expwicit_negative_wabew =
    n-nyew binawy(
      "wecap.engagement.is_unfowwow_topic_stwong_expwicit_negative_wabew", UwU
      set(engagementspwivate).asjava)
  v-vaw is_unfowwow_topic_expwicit_negative_wabew =
    n-nyew b-binawy(
      "wecap.engagement.is_unfowwow_topic_expwicit_negative_wabew", (U ﹏ U)
      s-set(engagementspwivate).asjava)
  v-vaw is_not_intewested_in =
    new binawy("wecap.engagement.is_not_intewested_in", 🥺 set(engagementspwivate).asjava)
  vaw is_not_intewested_in_expwicit_positive_wabew =
    n-nyew binawy(
      "wecap.engagement.is_not_intewested_in_expwicit_positive_wabew", ʘwʘ
      set(engagementspwivate).asjava)
  vaw i-is_not_intewested_in_expwicit_negative_wabew =
    nyew binawy(
      "wecap.engagement.is_not_intewested_in_expwicit_negative_wabew", 😳
      set(engagementspwivate).asjava)
  vaw is_cawet_cwicked =
    n-nyew binawy(name("wecap.engagement.is_cawet_cwicked"), (ˆ ﻌ ˆ)♡ set(engagementspwivate).asjava)
  vaw is_fowwow_topic =
    nyew binawy("wecap.engagement.is_fowwow_topic", >_< s-set(engagementspwivate).asjava)
  v-vaw is_not_intewested_in_topic =
    nyew binawy("wecap.engagement.is_not_intewested_in_topic", ^•ﻌ•^ s-set(engagementspwivate).asjava)
  vaw is_home_watest_visited =
    nyew binawy(name("wecap.engagement.is_home_watest_visited"), (✿oωo) s-set(engagementspwivate).asjava)

  // w-wewevance pwompt tweet engagements
  v-vaw is_wewevance_pwompt_yes_cwicked = n-nyew binawy(
    nyame("wecap.engagement.is_wewevance_pwompt_yes_cwicked"), OwO
    set(engagementspwivate).asjava)
  vaw is_wewevance_pwompt_no_cwicked = n-nyew binawy(
    nyame("wecap.engagement.is_wewevance_pwompt_no_cwicked"), (ˆ ﻌ ˆ)♡
    set(engagementspwivate).asjava)
  v-vaw is_wewevance_pwompt_impwessed = nyew b-binawy(
    n-nyame("wecap.engagement.is_wewevance_pwompt_impwessed"), ^^;;
    set(engagementspwivate).asjava)

  // wecipwocaw engagements f-fow wepwy fowwawd engagement
  vaw is_wepwied_wepwy_impwessed_by_authow = nyew binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_impwessed_by_authow"), nyaa~~
    s-set(engagementspwivate).asjava)
  v-vaw is_wepwied_wepwy_favowited_by_authow = n-nyew binawy(
    n-nyame("wecap.engagement.is_wepwied_wepwy_favowited_by_authow"), o.O
    set(engagementspwivate, >_< engagementspubwic, (U ﹏ U) p-pwivatewikes, ^^ p-pubwicwikes).asjava)
  vaw is_wepwied_wepwy_quoted_by_authow = nyew binawy(
    n-nyame("wecap.engagement.is_wepwied_wepwy_quoted_by_authow"), UwU
    set(engagementspwivate, ^^;; engagementspubwic, òωó p-pwivatewetweets, -.- pubwicwetweets).asjava)
  vaw is_wepwied_wepwy_wepwied_by_authow = n-nyew binawy(
    n-nyame("wecap.engagement.is_wepwied_wepwy_wepwied_by_authow"), ( ͡o ω ͡o )
    set(engagementspwivate, o.O engagementspubwic, p-pwivatewepwies, rawr p-pubwicwepwies).asjava)
  v-vaw is_wepwied_wepwy_wetweeted_by_authow = nyew binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_wetweeted_by_authow"), (✿oωo)
    s-set(engagementspwivate, σωσ engagementspubwic, (U ᵕ U❁) pwivatewetweets, >_< p-pubwicwetweets).asjava)
  vaw is_wepwied_wepwy_bwocked_by_authow = nyew binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_bwocked_by_authow"), ^^
    s-set(engagementspwivate, rawr e-engagementspubwic).asjava)
  v-vaw is_wepwied_wepwy_fowwowed_by_authow = n-nyew b-binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_fowwowed_by_authow"), >_<
    set(engagementspwivate, (⑅˘꒳˘) e-engagementspubwic, >w< fowwow).asjava)
  vaw i-is_wepwied_wepwy_unfowwowed_by_authow = nyew binawy(
    n-nyame("wecap.engagement.is_wepwied_wepwy_unfowwowed_by_authow"), (///ˬ///✿)
    set(engagementspwivate, ^•ﻌ•^ engagementspubwic).asjava)
  vaw is_wepwied_wepwy_muted_by_authow = n-nyew b-binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_muted_by_authow"), (✿oωo)
    set(engagementspwivate).asjava)
  v-vaw is_wepwied_wepwy_wepowted_by_authow = n-nyew binawy(
    n-nyame("wecap.engagement.is_wepwied_wepwy_wepowted_by_authow"), ʘwʘ
    set(engagementspwivate).asjava)

  // t-this dewived wabew i-is the wogicaw ow of wepwy_wepwied, >w< w-wepwy_favowited, :3 wepwy_wetweeted
  vaw is_wepwied_wepwy_engaged_by_authow = n-nyew binawy(
    nyame("wecap.engagement.is_wepwied_wepwy_engaged_by_authow"), (ˆ ﻌ ˆ)♡
    s-set(engagementspwivate, -.- engagementspubwic).asjava)

  // wecipwocaw engagements f-fow fav f-fowwawd engagement
  v-vaw is_favowited_fav_favowited_by_authow = nyew binawy(
    n-nyame("wecap.engagement.is_favowited_fav_favowited_by_authow"), rawr
    s-set(engagementspwivate, rawr x3 engagementspubwic, (U ﹏ U) p-pwivatewikes, (ˆ ﻌ ˆ)♡ pubwicwikes).asjava
  )
  vaw is_favowited_fav_wepwied_by_authow = n-nyew binawy(
    nyame("wecap.engagement.is_favowited_fav_wepwied_by_authow"), :3
    s-set(engagementspwivate, òωó e-engagementspubwic, /(^•ω•^) pwivatewepwies, >w< pubwicwepwies).asjava
  )
  vaw is_favowited_fav_wetweeted_by_authow = nyew binawy(
    nyame("wecap.engagement.is_favowited_fav_wetweeted_by_authow"), nyaa~~
    set(engagementspwivate, mya e-engagementspubwic, mya p-pwivatewetweets, ʘwʘ pubwicwetweets).asjava
  )
  vaw is_favowited_fav_fowwowed_by_authow = new b-binawy(
    name("wecap.engagement.is_favowited_fav_fowwowed_by_authow"),
    set(engagementspwivate, rawr e-engagementspubwic, (˘ω˘) p-pwivatewetweets, /(^•ω•^) pubwicwetweets).asjava
  )
  // this dewived wabew is the wogicaw ow o-of fav_wepwied, (˘ω˘) fav_favowited, (///ˬ///✿) fav_wetweeted, (˘ω˘) fav_fowwowed
  v-vaw is_favowited_fav_engaged_by_authow = n-nyew binawy(
    n-nyame("wecap.engagement.is_favowited_fav_engaged_by_authow"), -.-
    set(engagementspwivate, -.- e-engagementspubwic).asjava)

  // d-define good pwofiwe c-cwick by c-considewing fowwowing e-engagements (fowwow, ^^ f-fav, wepwy, (ˆ ﻌ ˆ)♡ wetweet, etc.) at pwofiwe page
  vaw is_pwofiwe_cwicked_and_pwofiwe_fowwow = nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_fowwow"), UwU
    s-set(pwofiwesviewed, 🥺 p-pwofiwescwicked, 🥺 engagementspwivate, 🥺 f-fowwow).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_fav = n-nyew b-binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_fav"), 🥺
    set(pwofiwesviewed, :3 pwofiwescwicked, (˘ω˘) engagementspwivate, ^^;; pwivatewikes, p-pubwicwikes).asjava)
  vaw i-is_pwofiwe_cwicked_and_pwofiwe_wepwy = nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_wepwy"), (ꈍᴗꈍ)
    set(pwofiwesviewed, ʘwʘ pwofiwescwicked, e-engagementspwivate, :3 p-pwivatewepwies, XD p-pubwicwepwies).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_wetweet = nyew b-binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_wetweet"), UwU
    set(
      pwofiwesviewed, rawr x3
      p-pwofiwescwicked, ( ͡o ω ͡o )
      e-engagementspwivate, :3
      pwivatewetweets,
      pubwicwetweets).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_tweet_cwick = nyew binawy(
    n-nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_tweet_cwick"), rawr
    s-set(pwofiwesviewed, ^•ﻌ•^ pwofiwescwicked, 🥺 e-engagementspwivate, (⑅˘꒳˘) t-tweetscwicked).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_shawe_dm_cwick = n-new binawy(
    n-name("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_shawe_dm_cwick"), :3
    s-set(pwofiwesviewed, (///ˬ///✿) pwofiwescwicked, 😳😳😳 e-engagementspwivate).asjava)
  // t-this dewived wabew is t-the union of aww binawy featuwes above
  vaw is_pwofiwe_cwicked_and_pwofiwe_engaged = n-nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_engaged"), 😳😳😳
    set(pwofiwesviewed, 😳😳😳 p-pwofiwescwicked, nyaa~~ engagementspwivate, UwU e-engagementspubwic).asjava)

  // d-define bad pwofiwe cwick by considewing f-fowwowing engagements (usew wepowt, òωó tweet wepowt, òωó m-mute, UwU bwock, e-etc) at pwofiwe page
  vaw is_pwofiwe_cwicked_and_pwofiwe_usew_wepowt_cwick = nyew b-binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_usew_wepowt_cwick"), (///ˬ///✿)
    set(pwofiwesviewed, ( ͡o ω ͡o ) p-pwofiwescwicked, rawr engagementspwivate).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_tweet_wepowt_cwick = nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_tweet_wepowt_cwick"), :3
    s-set(pwofiwesviewed, >w< p-pwofiwescwicked, σωσ engagementspwivate).asjava)
  vaw i-is_pwofiwe_cwicked_and_pwofiwe_mute = n-nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_mute"), σωσ
    set(pwofiwesviewed, >_< p-pwofiwescwicked, e-engagementspwivate).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_bwock = n-nyew binawy(
    nyame("wecap.engagement.is_pwofiwe_cwicked_and_pwofiwe_bwock"),
    set(pwofiwesviewed, -.- pwofiwescwicked, 😳😳😳 engagementspwivate).asjava)
  // this dewived wabew is the u-union of bad pwofiwe c-cwick engagements a-and existing n-negative feedback
  v-vaw is_negative_feedback_v2 = n-nyew binawy(
    nyame("wecap.engagement.is_negative_feedback_v2"), :3
    s-set(pwofiwesviewed, mya p-pwofiwescwicked, (✿oωo) engagementspwivate).asjava)
  v-vaw is_stwong_negative_feedback = n-nyew binawy(
    nyame("wecap.engagement.is_stwong_negative_feedback"), 😳😳😳
    set(pwofiwesviewed, o.O pwofiwescwicked, (ꈍᴗꈍ) e-engagementspwivate).asjava)
  vaw is_weak_negative_feedback = nyew binawy(
    n-nyame("wecap.engagement.is_weak_negative_feedback"), (ˆ ﻌ ˆ)♡
    set(pwofiwesviewed, -.- p-pwofiwescwicked, mya e-engagementspwivate).asjava)
  // engagement fow f-fowwowing usew f-fwom any suwface a-awea
  vaw is_fowwowed_fwom_any_suwface_awea = nyew binawy(
    "wecap.engagement.is_fowwowed_fwom_any_suwface_awea", :3
    s-set(engagementspubwic, σωσ e-engagementspwivate).asjava)

  // wepwy downvote e-engagements
  vaw is_wepwy_downvoted =
    n-nyew b-binawy(name("wecap.engagement.is_wepwy_downvoted"), 😳😳😳 s-set(engagementspwivate).asjava)
  vaw is_wepwy_downvote_wemoved =
    n-nyew binawy(name("wecap.engagement.is_wepwy_downvote_wemoved"), -.- set(engagementspwivate).asjava)

  // o-othew engagements
  vaw is_good_open_wink = nyew binawy(
    nyame("wecap.engagement.is_good_open_wink"), 😳😳😳
    set(engagementspwivate, rawr x3 winkscwickedon).asjava)
  vaw is_engaged = n-nyew binawy(
    nyame("wecap.engagement.any"), (///ˬ///✿)
    set(engagementspwivate, >w< engagementspubwic).asjava
  ) // depwecated - to be wemoved showtwy
  vaw is_eawwybiwd_unified_engagement = n-nyew binawy(
    nyame("wecap.engagement.is_unified_engagement"), o.O
    set(engagementspwivate, (˘ω˘) e-engagementspubwic).asjava
  ) // a subset o-of is_engaged specificawwy intended fow use i-in eawwybiwd modews

  // featuwes f-fwom thwifttweetfeatuwes
  vaw p-pwev_usew_tweet_engagement = nyew c-continuous(
    name("wecap.tweetfeatuwe.pwev_usew_tweet_enagagement"), rawr
    set(engagementscowe, mya e-engagementspwivate, òωó engagementspubwic).asjava)
  vaw is_sensitive = nyew binawy(name("wecap.tweetfeatuwe.is_sensitive"))
  v-vaw has_muwtipwe_media = nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_muwtipwe_media"), nyaa~~
    set(pubwictweetentitiesandmetadata, òωó p-pwivatetweetentitiesandmetadata).asjava)
  vaw i-is_authow_pwofiwe_egg = n-nyew binawy(name("wecap.tweetfeatuwe.is_authow_pwofiwe_egg"))
  vaw is_authow_new =
    nyew binawy(name("wecap.tweetfeatuwe.is_authow_new"), mya s-set(usewstate, ^^ usewtype).asjava)
  vaw nyum_mentions = n-nyew continuous(
    nyame("wecap.tweetfeatuwe.num_mentions"), ^•ﻌ•^
    set(countofpwivatetweetentitiesandmetadata, -.- countofpubwictweetentitiesandmetadata).asjava)
  v-vaw h-has_mention = nyew binawy(name("wecap.tweetfeatuwe.has_mention"), UwU s-set(usewvisibwefwag).asjava)
  v-vaw nyum_hashtags = nyew continuous(
    n-nyame("wecap.tweetfeatuwe.num_hashtags"), (˘ω˘)
    set(countofpwivatetweetentitiesandmetadata, UwU countofpubwictweetentitiesandmetadata).asjava)
  vaw has_hashtag = nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_hashtag"), rawr
    s-set(pubwictweetentitiesandmetadata, :3 pwivatetweetentitiesandmetadata).asjava)
  v-vaw wink_wanguage = n-nyew continuous(
    nyame("wecap.tweetfeatuwe.wink_wanguage"), nyaa~~
    set(pwovidedwanguage, rawr i-infewwedwanguage).asjava)
  vaw is_authow_nsfw =
    nyew binawy(name("wecap.tweetfeatuwe.is_authow_nsfw"), (ˆ ﻌ ˆ)♡ s-set(usewsafetywabews, (ꈍᴗꈍ) usewtype).asjava)
  vaw is_authow_spam =
    n-nyew binawy(name("wecap.tweetfeatuwe.is_authow_spam"), (˘ω˘) s-set(usewsafetywabews, usewtype).asjava)
  vaw is_authow_bot =
    n-nyew binawy(name("wecap.tweetfeatuwe.is_authow_bot"), (U ﹏ U) set(usewsafetywabews, >w< usewtype).asjava)
  vaw signatuwe =
    nyew discwete(name("wecap.tweetfeatuwe.signatuwe"), UwU set(digitawsignatuwenonwepudiation).asjava)
  v-vaw wanguage = n-nyew discwete(
    nyame("wecap.tweetfeatuwe.wanguage"),
    s-set(pwovidedwanguage, (ˆ ﻌ ˆ)♡ i-infewwedwanguage).asjava)
  vaw fwom_inactive_usew =
    n-nyew binawy(name("wecap.tweetfeatuwe.fwom_inactive_usew"), nyaa~~ set(usewactivefwag).asjava)
  vaw pwobabwy_fwom_fowwowed_authow = nyew binawy(name("wecap.v3.tweetfeatuwe.pwobabwy_fwom_fowwow"))
  vaw f-fwom_mutuaw_fowwow = nyew binawy(name("wecap.tweetfeatuwe.fwom_mutuaw_fowwow"))
  vaw usew_wep = nyew continuous(name("wecap.tweetfeatuwe.usew_wep"))
  vaw fwom_vewified_account =
    n-nyew binawy(name("wecap.tweetfeatuwe.fwom_vewified_account"), 🥺 s-set(usewvewifiedfwag).asjava)
  v-vaw is_business_scowe = nyew continuous(name("wecap.tweetfeatuwe.is_business_scowe"))
  vaw h-has_consumew_video = n-nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_consumew_video"), >_<
    set(pubwictweetentitiesandmetadata, òωó p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_pwo_video = nyew b-binawy(
    nyame("wecap.tweetfeatuwe.has_pwo_video"), ʘwʘ
    set(pubwictweetentitiesandmetadata, mya p-pwivatetweetentitiesandmetadata).asjava)
  vaw h-has_vine = nyew binawy(
    nyame("wecap.tweetfeatuwe.has_vine"), σωσ
    set(pubwictweetentitiesandmetadata, OwO p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_pewiscope = n-nyew b-binawy(
    nyame("wecap.tweetfeatuwe.has_pewiscope"), (✿oωo)
    set(pubwictweetentitiesandmetadata, ʘwʘ pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_native_video = n-nyew binawy(
    nyame("wecap.tweetfeatuwe.has_native_video"), mya
    s-set(pubwictweetentitiesandmetadata, -.- pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_native_image = new binawy(
    n-name("wecap.tweetfeatuwe.has_native_image"), -.-
    s-set(pubwictweetentitiesandmetadata, ^^;; pwivatetweetentitiesandmetadata).asjava)
  vaw has_cawd = nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_cawd"), (ꈍᴗꈍ)
    set(pubwictweetentitiesandmetadata, rawr pwivatetweetentitiesandmetadata).asjava)
  vaw has_image = nyew binawy(
    nyame("wecap.tweetfeatuwe.has_image"), ^^
    set(pubwictweetentitiesandmetadata, nyaa~~ pwivatetweetentitiesandmetadata).asjava)
  vaw h-has_news = nyew binawy(
    nyame("wecap.tweetfeatuwe.has_news"), (⑅˘꒳˘)
    set(pubwictweetentitiesandmetadata, (U ᵕ U❁) p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_video = n-nyew binawy(
    nyame("wecap.tweetfeatuwe.has_video"), (ꈍᴗꈍ)
    set(pubwictweetentitiesandmetadata, (✿oωo) p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_visibwe_wink = nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_visibwe_wink"), UwU
    set(uwwfoundfwag, ^^ pubwictweetentitiesandmetadata, :3 p-pwivatetweetentitiesandmetadata).asjava)
  vaw wink_count = nyew continuous(
    nyame("wecap.tweetfeatuwe.wink_count"), ( ͡o ω ͡o )
    s-set(countofpwivatetweetentitiesandmetadata, ( ͡o ω ͡o ) countofpubwictweetentitiesandmetadata).asjava)
  vaw has_wink = n-nyew binawy(
    n-nyame("wecap.tweetfeatuwe.has_wink"), (U ﹏ U)
    set(uwwfoundfwag, -.- pubwictweetentitiesandmetadata, 😳😳😳 p-pwivatetweetentitiesandmetadata).asjava)
  vaw i-is_offensive = nyew binawy(name("wecap.tweetfeatuwe.is_offensive"))
  v-vaw has_twend = n-new binawy(
    name("wecap.tweetfeatuwe.has_twend"), UwU
    set(pubwictweetentitiesandmetadata, >w< p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_muwtipwe_hashtags_ow_twends = nyew binawy(
    nyame("wecap.tweetfeatuwe.has_muwtipwe_hashtag_ow_twend"), mya
    s-set(pubwictweetentitiesandmetadata, pwivatetweetentitiesandmetadata).asjava)
  vaw uww_domains = nyew spawsebinawy(
    n-nyame("wecap.tweetfeatuwe.uww_domains"), :3
    s-set(pubwictweetentitiesandmetadata, (ˆ ﻌ ˆ)♡ p-pwivatetweetentitiesandmetadata).asjava)
  vaw contains_media = nyew binawy(
    n-nyame("wecap.tweetfeatuwe.contains_media"), (U ﹏ U)
    set(pubwictweetentitiesandmetadata, ʘwʘ p-pwivatetweetentitiesandmetadata).asjava)
  vaw wetweet_seawchew = n-nyew binawy(name("wecap.tweetfeatuwe.wetweet_seawchew"))
  v-vaw wepwy_seawchew = nyew binawy(name("wecap.tweetfeatuwe.wepwy_seawchew"))
  vaw mention_seawchew =
    nyew binawy(name("wecap.tweetfeatuwe.mention_seawchew"), rawr set(usewvisibwefwag).asjava)
  v-vaw wepwy_othew =
    n-nyew binawy(name("wecap.tweetfeatuwe.wepwy_othew"), (ꈍᴗꈍ) set(pubwicwepwies, ( ͡o ω ͡o ) pwivatewepwies).asjava)
  v-vaw wetweet_othew = nyew binawy(
    nyame("wecap.tweetfeatuwe.wetweet_othew"), 😳😳😳
    set(pubwicwetweets, òωó p-pwivatewetweets).asjava)
  v-vaw i-is_wepwy =
    n-nyew binawy(name("wecap.tweetfeatuwe.is_wepwy"), mya s-set(pubwicwepwies, rawr x3 p-pwivatewepwies).asjava)
  vaw is_wetweet =
    nyew binawy(name("wecap.tweetfeatuwe.is_wetweet"), XD s-set(pubwicwetweets, (ˆ ﻌ ˆ)♡ p-pwivatewetweets).asjava)
  v-vaw is_extended_wepwy = n-nyew b-binawy(
    nyame("wecap.tweetfeatuwe.is_extended_wepwy"), >w<
    s-set(pubwicwepwies, (ꈍᴗꈍ) pwivatewepwies).asjava)
  vaw m-match_ui_wang = n-nyew binawy(
    n-nyame("wecap.tweetfeatuwe.match_ui_wang"), (U ﹏ U)
    set(pwovidedwanguage, >_< infewwedwanguage).asjava)
  v-vaw match_seawchew_main_wang = nyew binawy(
    nyame("wecap.tweetfeatuwe.match_seawchew_main_wang"), >_<
    set(pwovidedwanguage, -.- i-infewwedwanguage).asjava)
  vaw match_seawchew_wangs = nyew b-binawy(
    nyame("wecap.tweetfeatuwe.match_seawchew_wangs"), òωó
    s-set(pwovidedwanguage, o.O infewwedwanguage).asjava)
  vaw bidiwectionaw_wepwy_count = nyew continuous(
    n-nyame("wecap.tweetfeatuwe.bidiwectionaw_wepwy_count"), σωσ
    s-set(countofpwivatewepwies, σωσ countofpubwicwepwies).asjava)
  v-vaw unidiwectionaw_wepwy_count = n-new continuous(
    nyame("wecap.tweetfeatuwe.unidiwectionaw_wepwy_count"), mya
    set(countofpwivatewepwies, o.O countofpubwicwepwies).asjava)
  v-vaw b-bidiwectionaw_wetweet_count = nyew continuous(
    n-name("wecap.tweetfeatuwe.bidiwectionaw_wetweet_count"), XD
    set(countofpwivatewetweets, XD c-countofpubwicwetweets).asjava)
  vaw unidiwectionaw_wetweet_count = nyew c-continuous(
    name("wecap.tweetfeatuwe.unidiwectionaw_wetweet_count"), (✿oωo)
    set(countofpwivatewetweets, -.- countofpubwicwetweets).asjava)
  vaw bidiwectionaw_fav_count = n-nyew continuous(
    nyame("wecap.tweetfeatuwe.bidiwectionaw_fav_count"), (ꈍᴗꈍ)
    s-set(countofpwivatewikes, ( ͡o ω ͡o ) c-countofpubwicwikes).asjava)
  v-vaw unidiwectionaw_fav_count = nyew continuous(
    n-nyame("wecap.tweetfeatuwe.unidiwectiona_fav_count"), (///ˬ///✿)
    s-set(countofpwivatewikes, 🥺 c-countofpubwicwikes).asjava)
  v-vaw convewsationaw_count = n-nyew continuous(
    nyame("wecap.tweetfeatuwe.convewsationaw_count"), (ˆ ﻌ ˆ)♡
    set(countofpwivatetweets, ^•ﻌ•^ c-countofpubwictweets).asjava)
  // t-tweet impwessions o-on an embedded tweet
  v-vaw embeds_impwession_count = n-nyew c-continuous(
    nyame("wecap.tweetfeatuwe.embeds_impwession_count"), rawr x3
    s-set(countofimpwession).asjava)
  // n-nyumbew of uwws t-that embed the tweet
  v-vaw embeds_uww_count = n-nyew continuous(
    n-nyame("wecap.tweetfeatuwe.embeds_uww_count"), (U ﹏ U)
    set(countofpwivatetweetentitiesandmetadata, OwO c-countofpubwictweetentitiesandmetadata).asjava)
  // c-cuwwentwy onwy counts views on snappy and ampwify pwo videos. (✿oωo) c-counts fow othew v-videos fowthcoming
  vaw video_view_count = n-nyew continuous(
    n-nyame("wecap.tweetfeatuwe.video_view_count"), (⑅˘꒳˘)
    set(
      countoftweetentitiescwicked, UwU
      c-countofpwivatetweetentitiesandmetadata, (ˆ ﻌ ˆ)♡
      c-countofpubwictweetentitiesandmetadata, /(^•ω•^)
      e-engagementspwivate,
      e-engagementspubwic).asjava
  )
  v-vaw tweet_count_fwom_usew_in_snapshot = n-nyew continuous(
    nyame("wecap.tweetfeatuwe.tweet_count_fwom_usew_in_snapshot"), (˘ω˘)
    set(countofpwivatetweets, XD c-countofpubwictweets).asjava)
  vaw nyowmawized_pawus_scowe =
    nyew continuous("wecap.tweetfeatuwe.nowmawized_pawus_scowe", òωó set(engagementscowe).asjava)
  vaw pawus_scowe = n-nyew continuous("wecap.tweetfeatuwe.pawus_scowe", UwU s-set(engagementscowe).asjava)
  vaw weaw_gwaph_weight =
    nyew continuous("wecap.tweetfeatuwe.weaw_gwaph_weight", set(usewsweawgwaphscowe).asjava)
  v-vaw sawus_gwaph_weight = n-nyew continuous("wecap.tweetfeatuwe.sawus_gwaph_weight")
  vaw topic_sim_seawchew_intewsted_in_authow_known_fow = nyew continuous(
    "wecap.tweetfeatuwe.topic_sim_seawchew_intewested_in_authow_known_fow")
  v-vaw topic_sim_seawchew_authow_both_intewested_in = nyew continuous(
    "wecap.tweetfeatuwe.topic_sim_seawchew_authow_both_intewested_in")
  v-vaw topic_sim_seawchew_authow_both_known_fow = n-nyew continuous(
    "wecap.tweetfeatuwe.topic_sim_seawchew_authow_both_known_fow")
  v-vaw topic_sim_seawchew_intewested_in_tweet = nyew continuous(
    "wecap.tweetfeatuwe.topic_sim_seawchew_intewested_in_tweet")
  vaw is_wetweetew_pwofiwe_egg =
    nyew b-binawy(name("wecap.v2.tweetfeatuwe.is_wetweetew_pwofiwe_egg"), -.- set(usewtype).asjava)
  vaw is_wetweetew_new =
    n-nyew binawy(name("wecap.v2.tweetfeatuwe.is_wetweetew_new"), (ꈍᴗꈍ) set(usewtype, (⑅˘꒳˘) u-usewstate).asjava)
  vaw is_wetweetew_bot =
    nyew b-binawy(
      name("wecap.v2.tweetfeatuwe.is_wetweetew_bot"), 🥺
      set(usewtype, òωó u-usewsafetywabews).asjava)
  vaw is_wetweetew_nsfw =
    nyew b-binawy(
      nyame("wecap.v2.tweetfeatuwe.is_wetweetew_nsfw"),
      set(usewtype, 😳 u-usewsafetywabews).asjava)
  vaw is_wetweetew_spam =
    nyew binawy(
      nyame("wecap.v2.tweetfeatuwe.is_wetweetew_spam"), òωó
      set(usewtype, 🥺 usewsafetywabews).asjava)
  vaw wetweet_of_mutuaw_fowwow = n-nyew binawy(
    n-nyame("wecap.v2.tweetfeatuwe.wetweet_of_mutuaw_fowwow"),
    s-set(pubwicwetweets, ( ͡o ω ͡o ) p-pwivatewetweets).asjava)
  vaw souwce_authow_wep = n-new continuous(name("wecap.v2.tweetfeatuwe.souwce_authow_wep"))
  vaw is_wetweet_of_wepwy = nyew binawy(
    nyame("wecap.v2.tweetfeatuwe.is_wetweet_of_wepwy"), UwU
    s-set(pubwicwetweets, 😳😳😳 p-pwivatewetweets).asjava)
  v-vaw wetweet_diwected_at_usew_in_fiwst_degwee = n-nyew binawy(
    nyame("wecap.v2.tweetfeatuwe.is_wetweet_diwected_at_usew_in_fiwst_degwee"), ʘwʘ
    set(pubwicwetweets, ^^ pwivatewetweets, >_< fowwow).asjava)
  v-vaw mentioned_scween_names = n-nyew spawsebinawy(
    "entities.usews.mentioned_scween_names", (ˆ ﻌ ˆ)♡
    set(dispwayname, (ˆ ﻌ ˆ)♡ usewvisibwefwag).asjava)
  v-vaw mentioned_scween_name = n-nyew text(
    "entities.usews.mentioned_scween_names.membew", 🥺
    s-set(dispwayname, ( ͡o ω ͡o ) u-usewvisibwefwag).asjava)
  vaw hashtags = nyew spawsebinawy(
    "entities.hashtags", (ꈍᴗꈍ)
    set(pubwictweetentitiesandmetadata, :3 pwivatetweetentitiesandmetadata).asjava)
  vaw uww_swugs = n-nyew spawsebinawy(name("wecap.winkfeatuwe.uww_swugs"), (✿oωo) set(uwwfoundfwag).asjava)

  // f-featuwes fwom thwiftseawchwesuwtmetadata
  vaw wepwy_count = nyew continuous(
    nyame("wecap.seawchfeatuwe.wepwy_count"), (U ᵕ U❁)
    s-set(countofpwivatewepwies, UwU countofpubwicwepwies).asjava)
  v-vaw wetweet_count = nyew continuous(
    n-nyame("wecap.seawchfeatuwe.wetweet_count"), ^^
    s-set(countofpwivatewetweets, /(^•ω•^) c-countofpubwicwetweets).asjava)
  v-vaw f-fav_count = nyew continuous(
    n-nyame("wecap.seawchfeatuwe.fav_count"), (˘ω˘)
    set(countofpwivatewikes, OwO c-countofpubwicwikes).asjava)
  vaw bwendew_scowe = n-nyew continuous(name("wecap.seawchfeatuwe.bwendew_scowe"))
  vaw text_scowe = nyew continuous(name("wecap.seawchfeatuwe.text_scowe"))

  // f-featuwes wewated to content s-souwce
  vaw souwce_type = n-nyew discwete(name("wecap.souwce.type"))

  // f-featuwes f-fwom addwessbook
  // the authow is in the usew's emaiw addwessbook
  v-vaw usew_to_authow_emaiw_weachabwe =
    n-nyew binawy(name("wecap.addwessbook.usew_to_authow_emaiw_weachabwe"), s-set(addwessbook).asjava)
  // t-the authow is in the usew's phone addwessbook
  vaw usew_to_authow_phone_weachabwe =
    n-nyew binawy(name("wecap.addwessbook.usew_to_authow_phone_weachabwe"), (U ᵕ U❁) set(addwessbook).asjava)
  // the usew is i-in the authow's emaiw addwessbook
  vaw authow_to_usew_emaiw_weachabwe =
    n-nyew binawy(name("wecap.addwessbook.authow_to_usew_emaiw_weachabwe"), (U ﹏ U) set(addwessbook).asjava)
  // the usew is in t-the usew's phone addwessbook
  v-vaw authow_to_usew_phone_weachabwe =
    n-new binawy(name("wecap.addwessbook.authow_to_usew_phone_weachabwe"), mya s-set(addwessbook).asjava)

  // pwedicted e-engagement (these f-featuwes awe used by pwediction s-sewvice t-to wetuwn the pwedicted e-engagement p-pwobabiwity)
  // these shouwd m-match the nyames i-in engagement_to_scowe_featuwe_mapping
  v-vaw pwedicted_is_favowited =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_favowited"), (⑅˘꒳˘) set(engagementscowe).asjava)
  vaw pwedicted_is_wetweeted =
    nyew continuous(name("wecap.engagement_pwedicted.is_wetweeted"), (U ᵕ U❁) set(engagementscowe).asjava)
  vaw p-pwedicted_is_quoted =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_quoted"), /(^•ω•^) s-set(engagementscowe).asjava)
  vaw pwedicted_is_wepwied =
    nyew continuous(name("wecap.engagement_pwedicted.is_wepwied"), ^•ﻌ•^ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_good_open_wink = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_good_open_wink"), (///ˬ///✿)
    set(engagementscowe).asjava)
  v-vaw pwedicted_is_pwofiwe_cwicked = nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_pwofiwe_cwicked"), o.O
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_pwofiwe_cwicked_and_pwofiwe_engaged = new continuous(
    nyame("wecap.engagement_pwedicted.is_pwofiwe_cwicked_and_pwofiwe_engaged"), (ˆ ﻌ ˆ)♡
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_cwicked =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_cwicked"), 😳 set(engagementscowe).asjava)
  vaw pwedicted_is_photo_expanded = n-new continuous(
    nyame("wecap.engagement_pwedicted.is_photo_expanded"), òωó
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_dont_wike =
    nyew continuous(name("wecap.engagement_pwedicted.is_dont_wike"), (⑅˘꒳˘) s-set(engagementscowe).asjava)
  vaw pwedicted_is_video_pwayback_50 = n-nyew continuous(
    nyame("wecap.engagement_pwedicted.is_video_pwayback_50"), rawr
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_video_quawity_viewed = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_video_quawity_viewed"), (ꈍᴗꈍ)
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_bookmawked =
    nyew continuous(name("wecap.engagement_pwedicted.is_bookmawked"), ^^ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_shawed =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_shawed"), (ˆ ﻌ ˆ)♡ set(engagementscowe).asjava)
  vaw pwedicted_is_shawe_menu_cwicked =
    nyew continuous(
      nyame("wecap.engagement_pwedicted.is_shawe_menu_cwicked"), /(^•ω•^)
      set(engagementscowe).asjava)
  v-vaw pwedicted_is_pwofiwe_dwewwed_20_sec = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_pwofiwe_dwewwed_20_sec"), ^^
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_fuwwscween_video_dwewwed_5_sec = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_fuwwscween_video_dwewwed_5_sec"), o.O
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_fuwwscween_video_dwewwed_10_sec = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_fuwwscween_video_dwewwed_10_sec"), 😳😳😳
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_fuwwscween_video_dwewwed_20_sec = n-nyew continuous(
    nyame("wecap.engagement_pwedicted.is_fuwwscween_video_dwewwed_20_sec"), XD
    set(engagementscowe).asjava)
  v-vaw p-pwedicted_is_fuwwscween_video_dwewwed_30_sec = nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_fuwwscween_video_dwewwed_30_sec"), nyaa~~
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_unified_engagement = n-nyew continuous(
    nyame("wecap.engagement_pwedicted.is_unified_engagement"), ^•ﻌ•^
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_compose_twiggewed = nyew c-continuous(
    n-nyame("wecap.engagement_pwedicted.is_compose_twiggewed"), :3
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_wepwied_wepwy_impwessed_by_authow = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_wepwied_wepwy_impwessed_by_authow"), ^^
    set(engagementscowe).asjava)
  vaw pwedicted_is_wepwied_wepwy_engaged_by_authow = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_wepwied_wepwy_engaged_by_authow"), o.O
    set(engagementscowe).asjava)
  vaw pwedicted_is_good_cwicked_v1 = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_good_cwicked_convo_desc_favowited_ow_wepwied"), ^^
    set(engagementscowe).asjava)
  v-vaw pwedicted_is_good_cwicked_v2 = nyew c-continuous(
    name("wecap.engagement_pwedicted.is_good_cwicked_convo_desc_v2"), (⑅˘꒳˘)
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_tweet_detaiw_dwewwed_8_sec = nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_tweet_detaiw_dwewwed_8_sec"), ʘwʘ
    set(engagementscowe).asjava)
  v-vaw pwedicted_is_tweet_detaiw_dwewwed_15_sec = nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_tweet_detaiw_dwewwed_15_sec"), mya
    set(engagementscowe).asjava)
  vaw pwedicted_is_tweet_detaiw_dwewwed_25_sec = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_tweet_detaiw_dwewwed_25_sec"), >w<
    s-set(engagementscowe).asjava)
  vaw pwedicted_is_tweet_detaiw_dwewwed_30_sec = nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_tweet_detaiw_dwewwed_30_sec"), o.O
    set(engagementscowe).asjava)
  v-vaw pwedicted_is_favowited_fav_engaged_by_authow = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_favowited_fav_engaged_by_authow"), OwO
    set(engagementscowe).asjava)
  vaw pwedicted_is_good_cwicked_with_dweww_sum_gte_60s = nyew continuous(
    nyame(
      "wecap.engagement_pwedicted.is_good_cwicked_convo_desc_favowited_ow_wepwied_ow_dweww_sum_gte_60_secs"), -.-
    set(engagementscowe).asjava)
  v-vaw pwedicted_is_dwewwed_in_bounds_v1 = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_dwewwed_in_bounds_v1"), (U ﹏ U)
    set(engagementscowe).asjava)
  v-vaw pwedicted_dweww_nowmawized_ovewaww = nyew c-continuous(
    n-nyame("wecap.engagement_pwedicted.dweww_nowmawized_ovewaww"), òωó
    set(engagementscowe).asjava)
  vaw pwedicted_dweww_cdf =
    n-new continuous(name("wecap.engagement_pwedicted.dweww_cdf"), >w< s-set(engagementscowe).asjava)
  vaw pwedicted_dweww_cdf_ovewaww = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.dweww_cdf_ovewaww"), ^•ﻌ•^
    s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_dwewwed =
    nyew c-continuous(name("wecap.engagement_pwedicted.is_dwewwed"), /(^•ω•^) set(engagementscowe).asjava)

  v-vaw p-pwedicted_is_dwewwed_1s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_1s"), ʘwʘ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_dwewwed_2s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_2s"), XD s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_dwewwed_3s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_3s"), (U ᵕ U❁) s-set(engagementscowe).asjava)
  vaw pwedicted_is_dwewwed_4s =
    new continuous(name("wecap.engagement_pwedicted.is_dwewwed_4s"), (ꈍᴗꈍ) set(engagementscowe).asjava)
  v-vaw pwedicted_is_dwewwed_5s =
    nyew c-continuous(name("wecap.engagement_pwedicted.is_dwewwed_5s"), rawr x3 set(engagementscowe).asjava)
  vaw p-pwedicted_is_dwewwed_6s =
    n-nyew c-continuous(name("wecap.engagement_pwedicted.is_dwewwed_6s"), :3 set(engagementscowe).asjava)
  vaw p-pwedicted_is_dwewwed_7s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_7s"), (˘ω˘) set(engagementscowe).asjava)
  vaw pwedicted_is_dwewwed_8s =
    nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_8s"), -.- set(engagementscowe).asjava)
  vaw pwedicted_is_dwewwed_9s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_dwewwed_9s"), (ꈍᴗꈍ) set(engagementscowe).asjava)
  vaw pwedicted_is_dwewwed_10s =
    nyew c-continuous(name("wecap.engagement_pwedicted.is_dwewwed_10s"), UwU s-set(engagementscowe).asjava)

  vaw p-pwedicted_is_skipped_1s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_skipped_1s"), σωσ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_2s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_skipped_2s"), ^^ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_3s =
    nyew continuous(name("wecap.engagement_pwedicted.is_skipped_3s"), :3 set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_4s =
    nyew continuous(name("wecap.engagement_pwedicted.is_skipped_4s"), ʘwʘ s-set(engagementscowe).asjava)
  vaw pwedicted_is_skipped_5s =
    n-nyew c-continuous(name("wecap.engagement_pwedicted.is_skipped_5s"), 😳 set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_6s =
    nyew continuous(name("wecap.engagement_pwedicted.is_skipped_6s"), ^^ s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_7s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_skipped_7s"), σωσ s-set(engagementscowe).asjava)
  vaw pwedicted_is_skipped_8s =
    n-nyew continuous(name("wecap.engagement_pwedicted.is_skipped_8s"), /(^•ω•^) s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_skipped_9s =
    nyew continuous(name("wecap.engagement_pwedicted.is_skipped_9s"), 😳😳😳 s-set(engagementscowe).asjava)
  vaw pwedicted_is_skipped_10s =
    nyew continuous(name("wecap.engagement_pwedicted.is_skipped_10s"), 😳 set(engagementscowe).asjava)

  vaw pwedicted_is_home_watest_visited = nyew continuous(
    nyame("wecap.engagement_pwedicted.is_home_watest_visited"), OwO
    set(engagementscowe).asjava)
  vaw pwedicted_is_negative_feedback =
    n-nyew continuous(
      n-nyame("wecap.engagement_pwedicted.is_negative_feedback"), :3
      set(engagementscowe).asjava)
  vaw pwedicted_is_negative_feedback_v2 =
    nyew continuous(
      nyame("wecap.engagement_pwedicted.is_negative_feedback_v2"), nyaa~~
      set(engagementscowe).asjava)
  v-vaw p-pwedicted_is_weak_negative_feedback =
    nyew continuous(
      nyame("wecap.engagement_pwedicted.is_weak_negative_feedback"), OwO
      s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_stwong_negative_feedback =
    nyew continuous(
      n-nyame("wecap.engagement_pwedicted.is_stwong_negative_feedback"), o.O
      s-set(engagementscowe).asjava)
  vaw pwedicted_is_wepowt_tweet_cwicked =
    n-nyew continuous(
      nyame("wecap.engagement_pwedicted.is_wepowt_tweet_cwicked"), (U ﹏ U)
      s-set(engagementscowe).asjava)
  v-vaw pwedicted_is_unfowwow_topic =
    nyew continuous(
      name("wecap.engagement_pwedicted.is_unfowwow_topic"), (⑅˘꒳˘)
      set(engagementscowe).asjava)
  v-vaw pwedicted_is_wewevance_pwompt_yes_cwicked = n-nyew continuous(
    n-nyame("wecap.engagement_pwedicted.is_wewevance_pwompt_yes_cwicked"), OwO
    set(engagementscowe).asjava)

  // e-engagement fow fowwowing usew f-fwom any suwface a-awea
  vaw pwedicted_is_fowwowed_fwom_any_suwface_awea = n-nyew continuous(
    "wecap.engagement_pwedicted.is_fowwowed_fwom_any_suwface_awea", 😳
    s-set(engagementscowe).asjava)

  
  // these awe gwobaw engagement c-counts fow t-the tweets. :3
  vaw fav_count_v2 = nyew continuous(
    nyame("wecap.eawwybiwd.fav_count_v2"), ( ͡o ω ͡o )
    set(countofpwivatewikes, 🥺 c-countofpubwicwikes).asjava)
  v-vaw wetweet_count_v2 = nyew continuous(
    n-nyame("wecap.eawwybiwd.wetweet_count_v2"), /(^•ω•^)
    set(countofpwivatewetweets, nyaa~~ countofpubwicwetweets).asjava)
  vaw wepwy_count_v2 = nyew continuous(
    n-nyame("wecap.eawwybiwd.wepwy_count_v2"), (✿oωo)
    s-set(countofpwivatewepwies, (✿oωo) c-countofpubwicwepwies).asjava)

  vaw has_us_powiticaw_annotation = n-nyew binawy(
    n-nyame("wecap.has_us_powiticaw_annotation"), (ꈍᴗꈍ)
    set(semanticcowecwassification).asjava
  )

  vaw has_us_powiticaw_aww_gwoups_annotation = n-new binawy(
    n-name("wecap.has_us_powiticaw_aww_gwoups_annotation"), OwO
    s-set(semanticcowecwassification).asjava
  )

  v-vaw has_us_powiticaw_annotation_high_wecaww = n-nyew binawy(
    n-nyame("wecap.has_us_powiticaw_annotation_high_wecaww"), :3
    set(semanticcowecwassification).asjava
  )

  vaw has_us_powiticaw_annotation_high_wecaww_v2 = new binawy(
    name("wecap.has_us_powiticaw_annotation_high_wecaww_v2"), mya
    set(semanticcowecwassification).asjava
  )

  vaw h-has_us_powiticaw_annotation_high_pwecision_v0 = new binawy(
    n-name("wecap.has_us_powiticaw_annotation_high_pwecision_v0"), >_<
    s-set(semanticcowecwassification).asjava
  )

  vaw has_us_powiticaw_annotation_bawanced_pwecision_wecaww_v0 = nyew binawy(
    nyame("wecap.has_us_powiticaw_annotation_bawanced_pwecision_wecaww_v0"), (///ˬ///✿)
    set(semanticcowecwassification).asjava
  )

  v-vaw h-has_us_powiticaw_annotation_high_wecaww_v3 = nyew b-binawy(
    nyame("wecap.has_us_powiticaw_annotation_high_wecaww_v3"), (///ˬ///✿)
    set(semanticcowecwassification).asjava
  )

  v-vaw has_us_powiticaw_annotation_high_pwecision_v3 = nyew binawy(
    nyame("wecap.has_us_powiticaw_annotation_high_pwecision_v3"), 😳😳😳
    s-set(semanticcowecwassification).asjava
  )

  vaw has_us_powiticaw_annotation_bawanced_v3 = nyew binawy(
    nyame("wecap.has_us_powiticaw_annotation_bawanced_v3"), (U ᵕ U❁)
    set(semanticcowecwassification).asjava
  )

}

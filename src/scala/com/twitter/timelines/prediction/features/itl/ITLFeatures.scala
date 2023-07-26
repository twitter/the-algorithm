package com.twittew.timewines.pwediction.featuwes.itw

impowt com.twittew.daw.pewsonaw_data.thwiftjava.pewsonawdatatype._
i-impowt c-com.twittew.mw.api.featuwe.binawy
i-impowt com.twittew.mw.api.featuwe.continuous
impowt c-com.twittew.mw.api.featuwe.discwete
i-impowt c-com.twittew.mw.api.featuwe.spawsebinawy
i-impowt s-scawa.cowwection.javaconvewtews._

object itwfeatuwes {
  // engagement
  vaw is_wetweeted =
    nyew binawy("itw.engagement.is_wetweeted", ^^ s-set(pubwicwetweets, òωó pwivatewetweets).asjava)
  vaw is_favowited =
    n-nyew binawy("itw.engagement.is_favowited", /(^•ω•^) set(pubwicwikes, 😳😳😳 p-pwivatewikes).asjava)
  vaw is_wepwied =
    nyew binawy("itw.engagement.is_wepwied", :3 s-set(pubwicwepwies, (///ˬ///✿) pwivatewepwies).asjava)
  // v-v1: post cwick e-engagements: fav, rawr x3 wepwy
  vaw is_good_cwicked_convo_desc_v1 = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_favowited_ow_wepwied",
    set(
      p-pubwicwikes, (U ᵕ U❁)
      pwivatewikes, (⑅˘꒳˘)
      pubwicwepwies, (˘ω˘)
      pwivatewepwies, :3
      engagementspwivate, XD
      engagementspubwic).asjava)
  // v2: post cwick engagements: c-cwick
  vaw is_good_cwicked_convo_desc_v2 = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_v2", >_<
    s-set(tweetscwicked, (✿oωo) engagementspwivate).asjava)

  v-vaw i-is_good_cwicked_convo_desc_favowited = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_favowited", (ꈍᴗꈍ)
    set(pubwicwikes, XD p-pwivatewikes).asjava)
  vaw is_good_cwicked_convo_desc_wepwied = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_wepwied", :3
    set(pubwicwepwies, pwivatewepwies).asjava)
  vaw is_good_cwicked_convo_desc_wetweeted = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_wetweeted", mya
    s-set(pubwicwetweets, òωó pwivatewetweets, nyaa~~ e-engagementspwivate, 🥺 e-engagementspubwic).asjava)
  v-vaw is_good_cwicked_convo_desc_cwicked = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_cwicked", -.-
    set(tweetscwicked, 🥺 engagementspwivate).asjava)
  vaw i-is_good_cwicked_convo_desc_fowwowed =
    n-nyew binawy("itw.engagement.is_good_cwicked_convo_desc_fowwowed", (˘ω˘) set(engagementspwivate).asjava)
  v-vaw is_good_cwicked_convo_desc_shawe_dm_cwicked = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_shawe_dm_cwicked", òωó
    set(engagementspwivate).asjava)
  v-vaw is_good_cwicked_convo_desc_pwofiwe_cwicked = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_pwofiwe_cwicked", UwU
    s-set(engagementspwivate).asjava)

  vaw is_good_cwicked_convo_desc_uam_gt_0 = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_uam_gt_0", ^•ﻌ•^
    set(engagementspwivate, mya e-engagementspubwic).asjava)
  vaw is_good_cwicked_convo_desc_uam_gt_1 = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_uam_gt_1", (✿oωo)
    s-set(engagementspwivate, XD engagementspubwic).asjava)
  vaw is_good_cwicked_convo_desc_uam_gt_2 = nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_uam_gt_2", :3
    set(engagementspwivate, (U ﹏ U) engagementspubwic).asjava)
  vaw i-is_good_cwicked_convo_desc_uam_gt_3 = n-nyew binawy(
    "itw.engagement.is_good_cwicked_convo_desc_uam_gt_3", UwU
    set(engagementspwivate, ʘwʘ e-engagementspubwic).asjava)

  v-vaw is_tweet_detaiw_dwewwed = n-nyew binawy(
    "itw.engagement.is_tweet_detaiw_dwewwed", >w<
    set(tweetscwicked, 😳😳😳 engagementspwivate).asjava)

  vaw is_tweet_detaiw_dwewwed_8_sec = n-nyew binawy(
    "itw.engagement.is_tweet_detaiw_dwewwed_8_sec", rawr
    set(tweetscwicked, ^•ﻌ•^ engagementspwivate).asjava)
  vaw is_tweet_detaiw_dwewwed_15_sec = nyew binawy(
    "itw.engagement.is_tweet_detaiw_dwewwed_15_sec", σωσ
    set(tweetscwicked, :3 e-engagementspwivate).asjava)
  vaw i-is_tweet_detaiw_dwewwed_25_sec = n-nyew binawy(
    "itw.engagement.is_tweet_detaiw_dwewwed_25_sec", rawr x3
    s-set(tweetscwicked, nyaa~~ engagementspwivate).asjava)
  v-vaw is_tweet_detaiw_dwewwed_30_sec = n-nyew b-binawy(
    "itw.engagement.is_tweet_detaiw_dwewwed_30_sec", :3
    s-set(tweetscwicked, >w< engagementspwivate).asjava)

  vaw is_pwofiwe_dwewwed = n-nyew b-binawy(
    "itw.engagement.is_pwofiwe_dwewwed", rawr
    s-set(pwofiwesviewed, 😳 p-pwofiwescwicked, 😳 e-engagementspwivate).asjava)
  vaw is_pwofiwe_dwewwed_10_sec = nyew binawy(
    "itw.engagement.is_pwofiwe_dwewwed_10_sec", 🥺
    s-set(pwofiwesviewed, pwofiwescwicked, rawr x3 engagementspwivate).asjava)
  vaw is_pwofiwe_dwewwed_20_sec = nyew binawy(
    "itw.engagement.is_pwofiwe_dwewwed_20_sec", ^^
    set(pwofiwesviewed, ( ͡o ω ͡o ) p-pwofiwescwicked, XD engagementspwivate).asjava)
  vaw is_pwofiwe_dwewwed_30_sec = nyew binawy(
    "itw.engagement.is_pwofiwe_dwewwed_30_sec", ^^
    s-set(pwofiwesviewed, (⑅˘꒳˘) p-pwofiwescwicked, (⑅˘꒳˘) e-engagementspwivate).asjava)

  vaw is_fuwwscween_video_dwewwed = n-nyew binawy(
    "itw.engagement.is_fuwwscween_video_dwewwed", ^•ﻌ•^
    set(mediaengagementactivities, ( ͡o ω ͡o ) e-engagementtypepwivate, ( ͡o ω ͡o ) e-engagementspwivate).asjava)

  vaw is_fuwwscween_video_dwewwed_5_sec = nyew binawy(
    "itw.engagement.is_fuwwscween_video_dwewwed_5_sec", (✿oωo)
    set(mediaengagementactivities, engagementtypepwivate, 😳😳😳 engagementspwivate).asjava)

  v-vaw is_fuwwscween_video_dwewwed_10_sec = nyew binawy(
    "itw.engagement.is_fuwwscween_video_dwewwed_10_sec", OwO
    s-set(mediaengagementactivities, ^^ engagementtypepwivate, rawr x3 e-engagementspwivate).asjava)

  v-vaw is_fuwwscween_video_dwewwed_20_sec = nyew binawy(
    "itw.engagement.is_fuwwscween_video_dwewwed_20_sec", 🥺
    set(mediaengagementactivities, (ˆ ﻌ ˆ)♡ e-engagementtypepwivate, ( ͡o ω ͡o ) e-engagementspwivate).asjava)

  vaw is_fuwwscween_video_dwewwed_30_sec = n-nyew binawy(
    "itw.engagement.is_fuwwscween_video_dwewwed_30_sec", >w<
    s-set(mediaengagementactivities, /(^•ω•^) engagementtypepwivate, 😳😳😳 engagementspwivate).asjava)

  vaw is_wink_dwewwed_15_sec = new b-binawy(
    "itw.engagement.is_wink_dwewwed_15_sec", (U ᵕ U❁)
    s-set(mediaengagementactivities, (˘ω˘) e-engagementtypepwivate, 😳 engagementspwivate).asjava)

  vaw i-is_wink_dwewwed_30_sec = n-nyew binawy(
    "itw.engagement.is_wink_dwewwed_30_sec", (ꈍᴗꈍ)
    s-set(mediaengagementactivities, :3 engagementtypepwivate, /(^•ω•^) engagementspwivate).asjava)

  vaw is_wink_dwewwed_60_sec = nyew b-binawy(
    "itw.engagement.is_wink_dwewwed_60_sec", ^^;;
    s-set(mediaengagementactivities, o.O engagementtypepwivate, 😳 engagementspwivate).asjava)

  vaw i-is_quoted =
    n-nyew binawy("itw.engagement.is_quoted", UwU set(pubwicwetweets, >w< pwivatewetweets).asjava)
  vaw is_wetweeted_without_quote = nyew b-binawy(
    "itw.engagement.is_wetweeted_without_quote", o.O
    set(pubwicwetweets, (˘ω˘) pwivatewetweets).asjava)
  vaw is_cwicked = nyew b-binawy(
    "itw.engagement.is_cwicked", òωó
    set(engagementspwivate, nyaa~~ tweetscwicked, ( ͡o ω ͡o ) winkscwickedon).asjava)
  v-vaw is_pwofiwe_cwicked = n-nyew binawy(
    "itw.engagement.is_pwofiwe_cwicked", 😳😳😳
    set(engagementspwivate, ^•ﻌ•^ tweetscwicked, (˘ω˘) pwofiwesviewed, (˘ω˘) p-pwofiwescwicked).asjava)
  v-vaw is_dwewwed = nyew binawy("itw.engagement.is_dwewwed", -.- set(engagementspwivate).asjava)
  vaw is_dwewwed_in_bounds_v1 =
    nyew binawy("itw.engagement.is_dwewwed_in_bounds_v1", ^•ﻌ•^ s-set(engagementspwivate).asjava)
  vaw dweww_nowmawized_ovewaww =
    n-nyew continuous("itw.engagement.dweww_nowmawized_ovewaww", /(^•ω•^) set(engagementspwivate).asjava)
  vaw dweww_cdf_ovewaww =
    n-nyew continuous("itw.engagement.dweww_cdf_ovewaww", (///ˬ///✿) set(engagementspwivate).asjava)
  v-vaw d-dweww_cdf = nyew continuous("itw.engagement.dweww_cdf", mya s-set(engagementspwivate).asjava)

  vaw i-is_dwewwed_1s = n-nyew binawy("itw.engagement.is_dwewwed_1s", o.O s-set(engagementspwivate).asjava)
  vaw i-is_dwewwed_2s = n-nyew binawy("itw.engagement.is_dwewwed_2s", ^•ﻌ•^ set(engagementspwivate).asjava)
  vaw is_dwewwed_3s = n-nyew binawy("itw.engagement.is_dwewwed_3s", (U ᵕ U❁) s-set(engagementspwivate).asjava)
  v-vaw is_dwewwed_4s = nyew binawy("itw.engagement.is_dwewwed_4s", :3 set(engagementspwivate).asjava)
  v-vaw is_dwewwed_5s = nyew binawy("itw.engagement.is_dwewwed_5s", s-set(engagementspwivate).asjava)
  v-vaw is_dwewwed_6s = nyew binawy("itw.engagement.is_dwewwed_6s", (///ˬ///✿) set(engagementspwivate).asjava)
  vaw is_dwewwed_7s = n-nyew b-binawy("itw.engagement.is_dwewwed_7s", (///ˬ///✿) s-set(engagementspwivate).asjava)
  v-vaw is_dwewwed_8s = nyew b-binawy("itw.engagement.is_dwewwed_8s", 🥺 set(engagementspwivate).asjava)
  vaw is_dwewwed_9s = new binawy("itw.engagement.is_dwewwed_9s", -.- set(engagementspwivate).asjava)
  v-vaw is_dwewwed_10s = n-nyew binawy("itw.engagement.is_dwewwed_10s", nyaa~~ set(engagementspwivate).asjava)

  vaw is_skipped_1s = n-nyew binawy("itw.engagement.is_skipped_1s", (///ˬ///✿) set(engagementspwivate).asjava)
  v-vaw is_skipped_2s = nyew binawy("itw.engagement.is_skipped_2s", 🥺 s-set(engagementspwivate).asjava)
  v-vaw is_skipped_3s = n-nyew binawy("itw.engagement.is_skipped_3s", >w< s-set(engagementspwivate).asjava)
  v-vaw is_skipped_4s = nyew binawy("itw.engagement.is_skipped_4s", rawr x3 set(engagementspwivate).asjava)
  vaw is_skipped_5s = nyew binawy("itw.engagement.is_skipped_5s", (⑅˘꒳˘) s-set(engagementspwivate).asjava)
  v-vaw i-is_skipped_6s = nyew binawy("itw.engagement.is_skipped_6s", σωσ s-set(engagementspwivate).asjava)
  vaw is_skipped_7s = nyew binawy("itw.engagement.is_skipped_7s", XD s-set(engagementspwivate).asjava)
  v-vaw is_skipped_8s = nyew binawy("itw.engagement.is_skipped_8s", -.- s-set(engagementspwivate).asjava)
  vaw is_skipped_9s = nyew binawy("itw.engagement.is_skipped_9s", >_< s-set(engagementspwivate).asjava)
  v-vaw is_skipped_10s = nyew binawy("itw.engagement.is_skipped_10s", rawr s-set(engagementspwivate).asjava)

  v-vaw is_fowwowed =
    nyew binawy("itw.engagement.is_fowwowed", 😳😳😳 set(engagementspwivate, UwU engagementspubwic).asjava)
  vaw i-is_impwessed = n-nyew binawy("itw.engagement.is_impwessed", (U ﹏ U) s-set(engagementspwivate).asjava)
  v-vaw i-is_open_winked =
    nyew binawy("itw.engagement.is_open_winked", s-set(engagementspwivate, (˘ω˘) w-winkscwickedon).asjava)
  vaw is_photo_expanded = n-new b-binawy(
    "itw.engagement.is_photo_expanded", /(^•ω•^)
    set(engagementspwivate, (U ﹏ U) e-engagementspubwic).asjava)
  vaw is_video_viewed =
    nyew binawy("itw.engagement.is_video_viewed", ^•ﻌ•^ s-set(engagementspwivate, >w< engagementspubwic).asjava)
  v-vaw is_video_pwayback_50 = n-nyew binawy(
    "itw.engagement.is_video_pwayback_50", ʘwʘ
    set(engagementspwivate, òωó engagementspubwic).asjava)
  v-vaw is_video_quawity_viewed = nyew binawy(
    "itw.engagement.is_video_quawity_viewed", o.O
    set(engagementspwivate, ( ͡o ω ͡o ) e-engagementspubwic).asjava
  ) 
  v-vaw is_bookmawked =
    n-nyew binawy("itw.engagement.is_bookmawked", mya set(engagementspwivate).asjava)
  vaw is_shawed =
    nyew binawy("itw.engagement.is_shawed", s-set(engagementspwivate).asjava)
  vaw is_shawe_menu_cwicked =
    n-nyew b-binawy("itw.engagement.is_shawe_menu_cwicked", >_< set(engagementspwivate).asjava)

  // n-nyegative engagements
  v-vaw is_dont_wike =
    n-nyew binawy("itw.engagement.is_dont_wike", rawr set(engagementspwivate, >_< engagementspubwic).asjava)
  v-vaw is_bwock_cwicked = nyew binawy(
    "itw.engagement.is_bwock_cwicked", (U ﹏ U)
    s-set(tweetscwicked, rawr e-engagementspwivate, (U ᵕ U❁) engagementspubwic).asjava)
  v-vaw is_bwock_diawog_bwocked = nyew binawy(
    "itw.engagement.is_bwock_diawog_bwocked", (ˆ ﻌ ˆ)♡
    s-set(engagementspwivate, >_< engagementspubwic).asjava)
  v-vaw i-is_mute_cwicked =
    nyew binawy("itw.engagement.is_mute_cwicked", ^^;; set(tweetscwicked, ʘwʘ engagementspwivate).asjava)
  vaw is_mute_diawog_muted =
    nyew binawy("itw.engagement.is_mute_diawog_muted", 😳😳😳 set(engagementspwivate).asjava)
  vaw is_wepowt_tweet_cwicked = nyew binawy(
    "itw.engagement.is_wepowt_tweet_cwicked",
    set(tweetscwicked, UwU engagementspwivate).asjava)
  vaw is_cawet_cwicked =
    nyew binawy("itw.engagement.is_cawet_cwicked", OwO s-set(tweetscwicked, :3 e-engagementspwivate).asjava)
  vaw is_not_about_topic =
    nyew binawy("itw.engagement.is_not_about_topic", -.- s-set(engagementspwivate).asjava)
  v-vaw is_not_wecent =
    n-nyew binawy("itw.engagement.is_not_wecent", 🥺 set(engagementspwivate).asjava)
  v-vaw is_not_wewevant =
    nyew binawy("itw.engagement.is_not_wewevant", -.- s-set(engagementspwivate).asjava)
  v-vaw is_see_fewew =
    nyew binawy("itw.engagement.is_see_fewew", -.- s-set(engagementspwivate).asjava)
  vaw is_unfowwow_topic =
    n-nyew binawy("itw.engagement.is_unfowwow_topic", (U ﹏ U) s-set(engagementspwivate).asjava)
  vaw is_fowwow_topic =
    nyew b-binawy("itw.engagement.is_fowwow_topic", rawr s-set(engagementspwivate).asjava)
  v-vaw i-is_not_intewested_in_topic =
    n-nyew binawy("itw.engagement.is_not_intewested_in_topic", mya s-set(engagementspwivate).asjava)
  v-vaw i-is_home_watest_visited =
    nyew b-binawy("itw.engagement.is_home_watest_visited", ( ͡o ω ͡o ) set(engagementspwivate).asjava)

  // t-this dewived w-wabew is t-the wogicaw ow of is_dont_wike, /(^•ω•^) i-is_bwock_cwicked, is_mute_cwicked and is_wepowt_tweet_cwicked
  v-vaw is_negative_feedback =
    nyew binawy("itw.engagement.is_negative_feedback", >_< s-set(engagementspwivate).asjava)

  // w-wecipwocaw e-engagements fow wepwy fowwawd e-engagement
  vaw is_wepwied_wepwy_impwessed_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_impwessed_by_authow", (✿oωo)
    set(engagementspwivate, 😳😳😳 e-engagementspubwic).asjava)
  vaw is_wepwied_wepwy_favowited_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_favowited_by_authow", (ꈍᴗꈍ)
    set(pubwicwikes, pwivatewikes, 🥺 engagementspwivate, mya engagementspubwic).asjava)
  v-vaw is_wepwied_wepwy_quoted_by_authow = nyew binawy(
    "itw.engagement.is_wepwied_wepwy_quoted_by_authow", (ˆ ﻌ ˆ)♡
    s-set(pubwicwetweets, (⑅˘꒳˘) p-pwivatewetweets, òωó engagementspwivate, o.O engagementspubwic).asjava)
  vaw is_wepwied_wepwy_wepwied_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_wepwied_by_authow", XD
    set(pubwicwepwies, (˘ω˘) p-pwivatewepwies, (ꈍᴗꈍ) e-engagementspwivate, >w< e-engagementspubwic).asjava)
  vaw is_wepwied_wepwy_wetweeted_by_authow = nyew binawy(
    "itw.engagement.is_wepwied_wepwy_wetweeted_by_authow", XD
    s-set(pubwicwetweets, -.- p-pwivatewetweets, ^^;; engagementspwivate, XD engagementspubwic).asjava)
  v-vaw is_wepwied_wepwy_bwocked_by_authow = nyew binawy(
    "itw.engagement.is_wepwied_wepwy_bwocked_by_authow", :3
    set(engagementspwivate, σωσ e-engagementspubwic).asjava)
  vaw is_wepwied_wepwy_fowwowed_by_authow = n-nyew b-binawy(
    "itw.engagement.is_wepwied_wepwy_fowwowed_by_authow", XD
    s-set(engagementspwivate, :3 engagementspubwic).asjava)
  v-vaw i-is_wepwied_wepwy_unfowwowed_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_unfowwowed_by_authow", rawr
    s-set(engagementspwivate, 😳 engagementspubwic).asjava)
  vaw i-is_wepwied_wepwy_muted_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_muted_by_authow", 😳😳😳
    s-set(engagementspwivate, (ꈍᴗꈍ) e-engagementspubwic).asjava)
  v-vaw is_wepwied_wepwy_wepowted_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_wepowted_by_authow", 🥺
    s-set(engagementspwivate, ^•ﻌ•^ e-engagementspubwic).asjava)

  // this d-dewived wabew is the wogicaw o-ow of wepwy_wepwied, XD wepwy_favowited, ^•ﻌ•^ w-wepwy_wetweeted
  v-vaw is_wepwied_wepwy_engaged_by_authow = n-nyew binawy(
    "itw.engagement.is_wepwied_wepwy_engaged_by_authow", ^^;;
    set(engagementspwivate, ʘwʘ engagementspubwic).asjava)

  // wecipwocaw engagements f-fow fav f-fowwawd engagement
  v-vaw is_favowited_fav_favowited_by_authow = nyew binawy(
    "itw.engagement.is_favowited_fav_favowited_by_authow", OwO
    set(engagementspwivate, 🥺 engagementspubwic, (⑅˘꒳˘) pwivatewikes, (///ˬ///✿) p-pubwicwikes).asjava
  )
  v-vaw is_favowited_fav_wepwied_by_authow = nyew b-binawy(
    "itw.engagement.is_favowited_fav_wepwied_by_authow", (✿oωo)
    s-set(engagementspwivate, nyaa~~ engagementspubwic, >w< pwivatewepwies, (///ˬ///✿) pubwicwepwies).asjava
  )
  v-vaw i-is_favowited_fav_wetweeted_by_authow = n-nyew binawy(
    "itw.engagement.is_favowited_fav_wetweeted_by_authow", rawr
    s-set(engagementspwivate, (U ﹏ U) engagementspubwic, pwivatewetweets, ^•ﻌ•^ pubwicwetweets).asjava
  )
  v-vaw i-is_favowited_fav_fowwowed_by_authow = nyew binawy(
    "itw.engagement.is_favowited_fav_fowwowed_by_authow", (///ˬ///✿)
    set(engagementspwivate, o.O e-engagementspubwic).asjava
  )
  // this dewived wabew is t-the wogicaw ow of fav_wepwied, >w< f-fav_favowited, nyaa~~ f-fav_wetweeted, òωó fav_fowwowed
  vaw i-is_favowited_fav_engaged_by_authow = n-nyew binawy(
    "itw.engagement.is_favowited_fav_engaged_by_authow", (U ᵕ U❁)
    set(engagementspwivate, (///ˬ///✿) e-engagementspubwic).asjava
  )

  // define g-good pwofiwe c-cwick by considewing f-fowwowing e-engagements (fowwow, (✿oωo) fav, wepwy, w-wetweet, 😳😳😳 etc.) a-at pwofiwe page
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_fowwow = nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_fowwow", (✿oωo)
    set(pwofiwesviewed, (U ﹏ U) pwofiwescwicked, (˘ω˘) engagementspwivate, 😳😳😳 f-fowwow).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_fav = nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_fav", (///ˬ///✿)
    set(pwofiwesviewed, (U ᵕ U❁) pwofiwescwicked, >_< engagementspwivate, (///ˬ///✿) pwivatewikes, (U ᵕ U❁) p-pubwicwikes).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_wepwy = n-nyew binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_wepwy", >w<
    s-set(pwofiwesviewed, 😳😳😳 pwofiwescwicked, (ˆ ﻌ ˆ)♡ engagementspwivate, (ꈍᴗꈍ) p-pwivatewepwies, 🥺 pubwicwepwies).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_wetweet = n-nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_wetweet", >_<
    s-set(
      p-pwofiwesviewed, OwO
      pwofiwescwicked, ^^;;
      engagementspwivate, (✿oωo)
      pwivatewetweets, UwU
      pubwicwetweets).asjava)
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_tweet_cwick = nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_tweet_cwick", ( ͡o ω ͡o )
    set(pwofiwesviewed, (✿oωo) pwofiwescwicked, mya engagementspwivate, ( ͡o ω ͡o ) t-tweetscwicked).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_shawe_dm_cwick = nyew binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_shawe_dm_cwick", :3
    set(pwofiwesviewed, 😳 pwofiwescwicked, (U ﹏ U) e-engagementspwivate).asjava)
  // t-this dewived wabew is the union o-of aww binawy featuwes above
  vaw is_pwofiwe_cwicked_and_pwofiwe_engaged = n-nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_engaged", >w<
    set(pwofiwesviewed, UwU p-pwofiwescwicked, 😳 engagementspwivate, XD e-engagementspubwic).asjava)

  // define bad pwofiwe cwick by considewing fowwowing e-engagements (usew wepowt, (✿oωo) tweet wepowt, ^•ﻌ•^ m-mute, bwock, mya etc) a-at pwofiwe page
  v-vaw is_pwofiwe_cwicked_and_pwofiwe_usew_wepowt_cwick = nyew binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_usew_wepowt_cwick", (˘ω˘)
    s-set(pwofiwesviewed, nyaa~~ pwofiwescwicked, :3 engagementspwivate).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_tweet_wepowt_cwick = nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_tweet_wepowt_cwick", (✿oωo)
    s-set(pwofiwesviewed, (U ﹏ U) p-pwofiwescwicked, (ꈍᴗꈍ) e-engagementspwivate).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_mute = nyew binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_mute", (˘ω˘)
    set(pwofiwesviewed, ^^ p-pwofiwescwicked, (⑅˘꒳˘) e-engagementspwivate).asjava)
  vaw is_pwofiwe_cwicked_and_pwofiwe_bwock = nyew b-binawy(
    "itw.engagement.is_pwofiwe_cwicked_and_pwofiwe_bwock", rawr
    set(pwofiwesviewed, :3 pwofiwescwicked, OwO e-engagementspwivate).asjava)
  // this dewived wabew is the union of b-bad pwofiwe cwick e-engagements and existing nyegative f-feedback
  v-vaw is_negative_feedback_v2 = n-nyew binawy(
    "itw.engagement.is_negative_feedback_v2", (ˆ ﻌ ˆ)♡
    set(pwofiwesviewed, :3 pwofiwescwicked, -.- engagementspwivate).asjava)
  // e-engagement fow fowwowing usew fwom any suwface a-awea
  vaw is_fowwowed_fwom_any_suwface_awea = nyew binawy(
    "itw.engagement.is_fowwowed_fwom_any_suwface_awea",
    set(engagementspubwic, -.- engagementspwivate).asjava)

  // w-wewevance pwompt t-tweet engagements
  v-vaw is_wewevance_pwompt_yes_cwicked =
    n-nyew binawy("itw.engagement.is_wewevance_pwompt_yes_cwicked", òωó s-set(engagementspwivate).asjava)

  // wepwy downvote e-engagements
  vaw is_wepwy_downvoted =
    nyew binawy("itw.engagement.is_wepwy_downvoted", 😳 s-set(engagementspwivate).asjava)
  vaw is_wepwy_downvote_wemoved =
    n-nyew binawy("itw.engagement.is_wepwy_downvote_wemoved", nyaa~~ set(engagementspwivate).asjava)

  // featuwes f-fwom wecommendedtweet
  v-vaw wectweet_scowe = nyew c-continuous("itw.wecommended_tweet_featuwes.wectweet_scowe")
  vaw nyum_favowiting_usews = n-nyew c-continuous("itw.wecommended_tweet_featuwes.num_favowiting_usews")
  vaw num_fowwowing_usews = nyew c-continuous("itw.wecommended_tweet_featuwes.num_fowwowing_usews")
  v-vaw content_souwce_type = nyew discwete("itw.wecommended_tweet_featuwes.content_souwce_type")

  v-vaw wecos_scowe = nyew continuous(
    "itw.wecommended_tweet_featuwes.wecos_scowe", (⑅˘꒳˘)
    set(engagementscowe, 😳 usewsweawgwaphscowe, (U ﹏ U) u-usewssawsascowe).asjava)
  vaw authow_weawgwaph_scowe = n-nyew continuous(
    "itw.wecommended_tweet_featuwes.weawgwaph_scowe", /(^•ω•^)
    set(usewsweawgwaphscowe).asjava)
  vaw authow_sawus_scowe = n-nyew continuous(
    "itw.wecommended_tweet_featuwes.sawus_scowe", OwO
    s-set(engagementscowe, ( ͡o ω ͡o ) u-usewssawsascowe).asjava)

  vaw nyum_intewacting_usews = new c-continuous(
    "itw.wecommended_tweet_featuwes.num_intewacting_usews", XD
    set(engagementscowe).asjava
  )
  v-vaw max_weawgwaph_scowe_of_intewacting_usews = nyew continuous(
    "itw.wecommended_tweet_featuwes.max_weawgwaph_scowe_of_intewacting_usews", /(^•ω•^)
    s-set(usewsweawgwaphscowe, /(^•ω•^) engagementscowe).asjava
  )
  v-vaw sum_weawgwaph_scowe_of_intewacting_usews = nyew continuous(
    "itw.wecommended_tweet_featuwes.sum_weawgwaph_scowe_of_intewacting_usews", 😳😳😳
    s-set(usewsweawgwaphscowe, (ˆ ﻌ ˆ)♡ e-engagementscowe).asjava
  )
  vaw avg_weawgwaph_scowe_of_intewacting_usews = nyew continuous(
    "itw.wecommended_tweet_featuwes.avg_weawgwaph_scowe_of_intewacting_usews", :3
    set(usewsweawgwaphscowe, òωó engagementscowe).asjava
  )
  vaw m-max_sawus_scowe_of_intewacting_usews = n-nyew continuous(
    "itw.wecommended_tweet_featuwes.max_sawus_scowe_of_intewacting_usews", 🥺
    set(engagementscowe, (U ﹏ U) usewssawsascowe).asjava
  )
  vaw sum_sawus_scowe_of_intewacting_usews = n-nyew continuous(
    "itw.wecommended_tweet_featuwes.sum_sawus_scowe_of_intewacting_usews", XD
    set(engagementscowe, ^^ u-usewssawsascowe).asjava
  )
  v-vaw avg_sawus_scowe_of_intewacting_usews = nyew continuous(
    "itw.wecommended_tweet_featuwes.avg_sawus_scowe_of_intewacting_usews", o.O
    set(engagementscowe, 😳😳😳 usewssawsascowe).asjava
  )

  vaw nyum_intewacting_fowwowings = n-nyew continuous(
    "itw.wecommended_tweet_featuwes.num_intewacting_fowwowings", /(^•ω•^)
    set(engagementscowe).asjava
  )

  // f-featuwes fwom hydwatedtweetfeatuwes
  v-vaw w-weaw_gwaph_weight =
    nyew continuous("itw.hydwated_tweet_featuwes.weaw_gwaph_weight", 😳😳😳 s-set(usewsweawgwaphscowe).asjava)
  v-vaw s-sawus_gwaph_weight = n-nyew continuous("itw.hydwated_tweet_featuwes.sawus_gwaph_weight")
  v-vaw fwom_top_engaged_usew = n-nyew binawy("itw.hydwated_tweet_featuwes.fwom_top_engaged_usew")
  vaw fwom_top_infwuencew = nyew binawy("itw.hydwated_tweet_featuwes.fwom_top_infwuencew")
  vaw topic_sim_seawchew_intewsted_in_authow_known_fow = nyew continuous(
    "itw.hydwated_tweet_featuwes.topic_sim_seawchew_intewested_in_authow_known_fow"
  )
  v-vaw topic_sim_seawchew_authow_both_intewested_in = n-nyew continuous(
    "itw.hydwated_tweet_featuwes.topic_sim_seawchew_authow_both_intewested_in"
  )
  v-vaw topic_sim_seawchew_authow_both_known_fow = nyew c-continuous(
    "itw.hydwated_tweet_featuwes.topic_sim_seawchew_authow_both_known_fow"
  )
  v-vaw usew_wep = n-nyew continuous("itw.hydwated_tweet_featuwes.usew_wep")
  vaw nyowmawized_pawus_scowe = nyew continuous("itw.hydwated_tweet_featuwes.nowmawized_pawus_scowe")
  vaw contains_media = nyew binawy("itw.hydwated_tweet_featuwes.contains_media")
  v-vaw fwom_neawby = n-nyew binawy("itw.hydwated_tweet_featuwes.fwom_neawby")
  vaw topic_sim_seawchew_intewested_in_tweet = nyew continuous(
    "itw.hydwated_tweet_featuwes.topic_sim_seawchew_intewested_in_tweet"
  )
  v-vaw matches_ui_wang = nyew b-binawy(
    "itw.hydwated_tweet_featuwes.matches_ui_wang", ^•ﻌ•^
    s-set(pwovidedwanguage, 🥺 infewwedwanguage).asjava)
  vaw matches_seawchew_main_wang = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.matches_seawchew_main_wang", o.O
    set(pwovidedwanguage, (U ᵕ U❁) infewwedwanguage).asjava
  )
  v-vaw matches_seawchew_wangs = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.matches_seawchew_wangs", ^^
    set(pwovidedwanguage, infewwedwanguage).asjava)
  v-vaw has_cawd = nyew binawy(
    "itw.hydwated_tweet_featuwes.has_cawd", (⑅˘꒳˘)
    s-set(pubwictweetentitiesandmetadata, p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_image = nyew b-binawy(
    "itw.hydwated_tweet_featuwes.has_image", :3
    s-set(pubwictweetentitiesandmetadata, (///ˬ///✿) p-pwivatetweetentitiesandmetadata).asjava)
  v-vaw h-has_native_image = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_native_image", :3
    set(pubwictweetentitiesandmetadata, 🥺 p-pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_video = nyew binawy("itw.hydwated_tweet_featuwes.has_video")
  v-vaw has_consumew_video = nyew binawy(
    "itw.hydwated_tweet_featuwes.has_consumew_video", mya
    s-set(pubwictweetentitiesandmetadata, XD pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_pwo_video = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_pwo_video", -.-
    s-set(pubwictweetentitiesandmetadata, o.O pwivatetweetentitiesandmetadata).asjava)
  vaw has_pewiscope = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_pewiscope", (˘ω˘)
    set(pubwictweetentitiesandmetadata, (U ᵕ U❁) pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_vine = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_vine", rawr
    set(pubwictweetentitiesandmetadata, 🥺 pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_native_video = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_native_video", rawr x3
    set(pubwictweetentitiesandmetadata, ( ͡o ω ͡o ) p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_wink = nyew b-binawy(
    "itw.hydwated_tweet_featuwes.has_wink", σωσ
    s-set(uwwfoundfwag, rawr x3 pubwictweetentitiesandmetadata, (ˆ ﻌ ˆ)♡ p-pwivatetweetentitiesandmetadata).asjava)
  v-vaw wink_count = nyew continuous(
    "itw.hydwated_tweet_featuwes.wink_count", rawr
    set(countofpwivatetweetentitiesandmetadata, :3 c-countofpubwictweetentitiesandmetadata).asjava)
  v-vaw uww_domains = n-nyew spawsebinawy(
    "itw.hydwated_tweet_featuwes.uww_domains", rawr
    set(pubwictweetentitiesandmetadata, (˘ω˘) p-pwivatetweetentitiesandmetadata).asjava)
  vaw has_visibwe_wink = nyew binawy(
    "itw.hydwated_tweet_featuwes.has_visibwe_wink", (ˆ ﻌ ˆ)♡
    set(uwwfoundfwag, pubwictweetentitiesandmetadata, pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_news = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_news", mya
    s-set(pubwictweetentitiesandmetadata, (U ᵕ U❁) p-pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_twend = n-nyew binawy(
    "itw.hydwated_tweet_featuwes.has_twend", mya
    set(pubwictweetentitiesandmetadata, ʘwʘ p-pwivatetweetentitiesandmetadata).asjava)
  vaw b-bwendew_scowe =
    nyew continuous("itw.hydwated_tweet_featuwes.bwendew_scowe", (˘ω˘) s-set(engagementscowe).asjava)
  v-vaw pawus_scowe =
    nyew continuous("itw.hydwated_tweet_featuwes.pawus_scowe", 😳 set(engagementscowe).asjava)
  v-vaw text_scowe =
    new continuous("itw.hydwated_tweet_featuwes.text_scowe", òωó set(engagementscowe).asjava)
  v-vaw bidiwectionaw_wepwy_count = nyew continuous(
    "itw.hydwated_tweet_featuwes.bidiwectionaw_wepwy_count", nyaa~~
    s-set(countofpwivatewepwies, o.O c-countofpubwicwepwies).asjava
  )
  vaw unidiwectionaw_wepwy_count = n-nyew continuous(
    "itw.hydwated_tweet_featuwes.unidiwectionaw_wepwy_count", nyaa~~
    s-set(countofpwivatewepwies, (U ᵕ U❁) countofpubwicwepwies).asjava
  )
  v-vaw bidiwectionaw_wetweet_count = nyew continuous(
    "itw.hydwated_tweet_featuwes.bidiwectionaw_wetweet_count", 😳😳😳
    s-set(countofpwivatewetweets, (U ﹏ U) c-countofpubwicwetweets).asjava
  )
  vaw unidiwectionaw_wetweet_count = n-new continuous(
    "itw.hydwated_tweet_featuwes.unidiwectionaw_wetweet_count", ^•ﻌ•^
    set(countofpwivatewetweets, (⑅˘꒳˘) countofpubwicwetweets).asjava
  )
  vaw b-bidiwectionaw_fav_count = n-nyew c-continuous(
    "itw.hydwated_tweet_featuwes.bidiwectionaw_fav_count", >_<
    set(countofpwivatewikes, (⑅˘꒳˘) c-countofpubwicwikes).asjava
  )
  vaw unidiwectionaw_fav_count = nyew continuous(
    "itw.hydwated_tweet_featuwes.unidiwectionaw_fav_count", σωσ
    s-set(countofpwivatewikes, 🥺 countofpubwicwikes).asjava
  )
  vaw convewsation_count = nyew continuous("itw.hydwated_tweet_featuwes.convewsation_count")
  vaw fav_count = new continuous(
    "itw.hydwated_tweet_featuwes.fav_count", :3
    set(countofpwivatewikes, (ꈍᴗꈍ) c-countofpubwicwikes).asjava)
  vaw wepwy_count = nyew continuous(
    "itw.hydwated_tweet_featuwes.wepwy_count",
    set(countofpwivatewepwies, ^•ﻌ•^ countofpubwicwepwies).asjava)
  vaw wetweet_count = nyew c-continuous(
    "itw.hydwated_tweet_featuwes.wetweet_count", (˘ω˘)
    set(countofpwivatewetweets, 🥺 countofpubwicwetweets).asjava)
  v-vaw pwev_usew_tweet_engagement = nyew c-continuous(
    "itw.hydwated_tweet_featuwes.pwev_usew_tweet_enagagement", (✿oωo)
    set(engagementscowe, XD engagementspwivate, (///ˬ///✿) e-engagementspubwic).asjava
  )
  vaw i-is_sensitive = nyew binawy("itw.hydwated_tweet_featuwes.is_sensitive")
  v-vaw has_muwtipwe_media = n-new binawy(
    "itw.hydwated_tweet_featuwes.has_muwtipwe_media", ( ͡o ω ͡o )
    set(pubwictweetentitiesandmetadata, ʘwʘ pwivatetweetentitiesandmetadata).asjava)
  v-vaw has_muwtipwe_hashtags_ow_twends = nyew binawy(
    "itw.hydwated_tweet_featuwes.has_muwtipwe_hashtag_ow_twend", rawr
    set(
      usewvisibwefwag, o.O
      c-countofpwivatetweetentitiesandmetadata, ^•ﻌ•^
      countofpubwictweetentitiesandmetadata).asjava)
  vaw is_authow_pwofiwe_egg =
    n-nyew binawy("itw.hydwated_tweet_featuwes.is_authow_pwofiwe_egg", (///ˬ///✿) set(pwofiweimage).asjava)
  v-vaw is_authow_new =
    n-nyew binawy("itw.hydwated_tweet_featuwes.is_authow_new", (ˆ ﻌ ˆ)♡ s-set(usewtype, XD usewstate).asjava)
  vaw num_mentions = n-nyew continuous(
    "itw.hydwated_tweet_featuwes.num_mentions", (✿oωo)
    set(
      usewvisibwefwag, -.-
      c-countofpwivatetweetentitiesandmetadata, XD
      countofpubwictweetentitiesandmetadata).asjava)
  vaw nyum_hashtags = nyew continuous(
    "itw.hydwated_tweet_featuwes.num_hashtags", (✿oωo)
    s-set(countofpwivatetweetentitiesandmetadata, (˘ω˘) countofpubwictweetentitiesandmetadata).asjava)
  v-vaw wanguage = nyew discwete(
    "itw.hydwated_tweet_featuwes.wanguage", (ˆ ﻌ ˆ)♡
    s-set(pwovidedwanguage, i-infewwedwanguage).asjava)
  vaw wink_wanguage = n-nyew continuous(
    "itw.hydwated_tweet_featuwes.wink_wanguage", >_<
    set(pwovidedwanguage, infewwedwanguage).asjava)
  vaw is_authow_nsfw =
    n-nyew binawy("itw.hydwated_tweet_featuwes.is_authow_nsfw", -.- set(usewtype).asjava)
  v-vaw is_authow_spam =
    nyew binawy("itw.hydwated_tweet_featuwes.is_authow_spam", (///ˬ///✿) s-set(usewtype).asjava)
  v-vaw is_authow_bot = nyew binawy("itw.hydwated_tweet_featuwes.is_authow_bot", XD set(usewtype).asjava)
  v-vaw is_offensive = nyew binawy("itw.hydwated_tweet_featuwes.is_offensive")
  vaw fwom_vewified_account =
    n-nyew binawy("itw.hydwated_tweet_featuwes.fwom_vewified_account", ^^;; set(usewvewifiedfwag).asjava)
  vaw embeds_impwession_count = n-nyew continuous(
    "itw.hydwated_tweet_featuwes.embeds_impwession_count", rawr x3
    s-set(countofimpwession).asjava)
  vaw embeds_uww_count =
    nyew continuous("itw.hydwated_tweet_featuwes.embeds_uww_count", OwO set(uwwfoundfwag).asjava)
  v-vaw fav_count_v2 = nyew continuous(
    "wecap.eawwybiwd.fav_count_v2", ʘwʘ
    set(countofpwivatewikes, rawr countofpubwicwikes).asjava)
  vaw wetweet_count_v2 = nyew continuous(
    "wecap.eawwybiwd.wetweet_count_v2",
    s-set(countofpwivatewetweets, UwU c-countofpubwicwetweets).asjava)
  vaw wepwy_count_v2 = n-nyew continuous(
    "wecap.eawwybiwd.wepwy_count_v2", (ꈍᴗꈍ)
    set(countofpwivatewepwies, (✿oωo) c-countofpubwicwepwies).asjava)
}

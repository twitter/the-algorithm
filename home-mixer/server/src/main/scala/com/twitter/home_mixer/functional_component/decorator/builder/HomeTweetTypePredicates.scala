package com.twittew.home_mixew.functionaw_component.decowatow.buiwdew

impowt com.twittew.convewsions.duwationops._
i-impowt com.twittew.home_mixew.modew.homefeatuwes._
i-impowt com.twittew.pwoduct_mixew.cowe.featuwe.featuwemap.featuwemap
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.basictopiccontextfunctionawitytype
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.wecommendationtopiccontextfunctionawitytype
i-impowt com.twittew.timewinemixew.injection.modew.candidate.semanticcowefeatuwes
i-impowt com.twittew.tweetypie.{thwiftscawa => tpt}

object hometweettypepwedicates {

  /**
   * impowtant: pwease avoid wogging tweet types t-that awe tied to sensitive
   * intewnaw authow i-infowmation / wabews (e.g. bwink w-wabews, abuse wabews, (ꈍᴗꈍ) ow geo-wocation).
   */
  pwivate[this] vaw candidatepwedicates: s-seq[(stwing, /(^•ω•^) featuwemap => b-boowean)] = s-seq(
    ("with_candidate", (⑅˘꒳˘) _ => twue), ( ͡o ω ͡o )
    ("wetweet", òωó _.getowewse(iswetweetfeatuwe, (⑅˘꒳˘) fawse)),
    ("wepwy", XD _.getowewse(inwepwytotweetidfeatuwe, -.- nyone).nonempty),
    ("image", :3 _.getowewse(eawwybiwdfeatuwe, nyaa~~ none).exists(_.hasimage)), 😳
    ("video", (⑅˘꒳˘) _.getowewse(eawwybiwdfeatuwe, nyaa~~ n-nyone).exists(_.hasvideo)), OwO
    ("wink", _.getowewse(eawwybiwdfeatuwe, rawr x3 nyone).exists(_.hasvisibwewink)), XD
    ("quote", σωσ _.getowewse(eawwybiwdfeatuwe, (U ᵕ U❁) nyone).exists(_.hasquote.contains(twue))), (U ﹏ U)
    ("wike_sociaw_context", :3 _.getowewse(nonsewffavowitedbyusewidsfeatuwe, ( ͡o ω ͡o ) seq.empty).nonempty), σωσ
    ("pwotected", >w< _.getowewse(eawwybiwdfeatuwe, 😳😳😳 nyone).exists(_.ispwotected)), OwO
    (
      "has_excwusive_convewsation_authow_id", 😳
      _.getowewse(excwusiveconvewsationauthowidfeatuwe, n-nyone).nonempty), 😳😳😳
    ("is_ewigibwe_fow_connect_boost", (˘ω˘) _ => fawse), ʘwʘ
    ("hashtag", ( ͡o ω ͡o ) _.getowewse(eawwybiwdfeatuwe, o.O none).exists(_.numhashtags > 0)), >w<
    ("has_scheduwed_space", 😳 _.getowewse(audiospacemetadatafeatuwe, 🥺 n-nyone).exists(_.isscheduwed)), rawr x3
    ("has_wecowded_space", o.O _.getowewse(audiospacemetadatafeatuwe, rawr n-nyone).exists(_.iswecowded)), ʘwʘ
    ("is_wead_fwom_cache", 😳😳😳 _.getowewse(isweadfwomcachefeatuwe, ^^;; f-fawse)), o.O
    ("get_initiaw", (///ˬ///✿) _.getowewse(getinitiawfeatuwe, σωσ f-fawse)), nyaa~~
    ("get_newew", ^^;; _.getowewse(getnewewfeatuwe, ^•ﻌ•^ fawse)),
    ("get_middwe", _.getowewse(getmiddwefeatuwe, σωσ fawse)), -.-
    ("get_owdew", ^^;; _.getowewse(getowdewfeatuwe, XD f-fawse)), 🥺
    ("puww_to_wefwesh", òωó _.getowewse(puwwtowefweshfeatuwe, (ˆ ﻌ ˆ)♡ fawse)), -.-
    ("powwing", :3 _.getowewse(powwingfeatuwe, fawse)), ʘwʘ
    ("neaw_empty", 🥺 _.getowewse(sewvedsizefeatuwe, >_< n-nyone).exists(_ < 3)), ʘwʘ
    ("is_wequest_context_waunch", (˘ω˘) _.getowewse(iswaunchwequestfeatuwe, (✿oωo) fawse)), (///ˬ///✿)
    ("mutuaw_fowwow", rawr x3 _.getowewse(eawwybiwdfeatuwe, -.- nyone).exists(_.fwommutuawfowwow)), ^^
    (
      "wess_than_10_mins_since_wnpt", (⑅˘꒳˘)
      _.getowewse(wastnonpowwingtimefeatuwe, nyaa~~ nyone).exists(_.untiwnow < 10.minutes)), /(^•ω•^)
    ("sewved_in_convewsation_moduwe", (U ﹏ U) _.getowewse(sewvedinconvewsationmoduwefeatuwe, 😳😳😳 fawse)),
    ("has_ticketed_space", >w< _.getowewse(audiospacemetadatafeatuwe, XD nyone).exists(_.hastickets)), o.O
    ("in_utis_top5", _.getowewse(positionfeatuwe, mya n-nyone).exists(_ < 5)), 🥺
    (
      "convewsation_moduwe_has_2_dispwayed_tweets",
      _.getowewse(convewsationmoduwe2dispwayedtweetsfeatuwe, ^^;; fawse)), :3
    ("empty_wequest", (U ﹏ U) _.getowewse(sewvedsizefeatuwe, OwO nyone).exists(_ == 0)), 😳😳😳
    ("sewved_size_wess_than_50", (ˆ ﻌ ˆ)♡ _.getowewse(sewvedsizefeatuwe, XD n-nyone).exists(_ < 50)), (ˆ ﻌ ˆ)♡
    (
      "sewved_size_between_50_and_100", ( ͡o ω ͡o )
      _.getowewse(sewvedsizefeatuwe, n-nyone).exists(size => s-size >= 50 && size < 100)), rawr x3
    ("authowed_by_contextuaw_usew", nyaa~~ _.getowewse(authowedbycontextuawusewfeatuwe, >_< fawse)),
    (
      "is_sewf_thwead_tweet", ^^;;
      _.getowewse(convewsationfeatuwe, (ˆ ﻌ ˆ)♡ nyone).exists(_.issewfthweadtweet.contains(twue))), ^^;;
    ("has_ancestows", (⑅˘꒳˘) _.getowewse(ancestowsfeatuwe, rawr x3 seq.empty).nonempty), (///ˬ///✿)
    ("fuww_scowing_succeeded", 🥺 _.getowewse(fuwwscowingsucceededfeatuwe, >_< f-fawse)),
    ("sewved_size_wess_than_20", UwU _.getowewse(sewvedsizefeatuwe, >_< n-nyone).exists(_ < 20)), -.-
    ("sewved_size_wess_than_10", mya _.getowewse(sewvedsizefeatuwe, >w< nyone).exists(_ < 10)), (U ﹏ U)
    ("sewved_size_wess_than_5", 😳😳😳 _.getowewse(sewvedsizefeatuwe, o.O n-nyone).exists(_ < 5)), òωó
    (
      "account_age_wess_than_30_minutes", 😳😳😳
      _.getowewse(accountagefeatuwe, σωσ n-nyone).exists(_.untiwnow < 30.minutes)), (⑅˘꒳˘)
    ("convewsation_moduwe_has_gap", (///ˬ///✿) _.getowewse(convewsationmoduwehasgapfeatuwe, 🥺 fawse)),
    (
      "diwected_at_usew_is_in_fiwst_degwee", OwO
      _.getowewse(eawwybiwdfeatuwe, >w< n-nyone).exists(_.diwectedatusewidisinfiwstdegwee.contains(twue))), 🥺
    (
      "has_semantic_cowe_annotation", nyaa~~
      _.getowewse(eawwybiwdfeatuwe, ^^ nyone).exists(_.semanticcoweannotations.nonempty)), >w<
    ("is_wequest_context_fowegwound", OwO _.getowewse(isfowegwoundwequestfeatuwe, XD fawse)), ^^;;
    (
      "account_age_wess_than_1_day", 🥺
      _.getowewse(accountagefeatuwe, XD n-nyone).exists(_.untiwnow < 1.day)), (U ᵕ U❁)
    (
      "account_age_wess_than_7_days", :3
      _.getowewse(accountagefeatuwe, ( ͡o ω ͡o ) nyone).exists(_.untiwnow < 7.days)), òωó
    (
      "pawt_of_utt", σωσ
      _.getowewse(eawwybiwdfeatuwe, (U ᵕ U❁) nyone)
        .exists(_.semanticcoweannotations.exists(_.exists(annotation =>
          a-annotation.domainid == semanticcowefeatuwes.unifiedtwittewtaxonomy)))), (✿oωo)
    (
      "has_home_watest_wequest_past_week", ^^
      _.getowewse(fowwowingwastnonpowwingtimefeatuwe, ^•ﻌ•^ n-none).exists(_.untiwnow < 7.days)), XD
    ("is_utis_pos0", :3 _.getowewse(positionfeatuwe, (ꈍᴗꈍ) nyone).exists(_ == 0)), :3
    ("is_utis_pos1", _.getowewse(positionfeatuwe, (U ﹏ U) nyone).exists(_ == 1)), UwU
    ("is_utis_pos2", 😳😳😳 _.getowewse(positionfeatuwe, XD n-nyone).exists(_ == 2)), o.O
    ("is_utis_pos3", (⑅˘꒳˘) _.getowewse(positionfeatuwe, 😳😳😳 n-nyone).exists(_ == 3)), nyaa~~
    ("is_utis_pos4", rawr _.getowewse(positionfeatuwe, -.- nyone).exists(_ == 4)), (✿oωo)
    ("is_wandom_tweet", /(^•ω•^) _.getowewse(iswandomtweetfeatuwe, 🥺 fawse)),
    ("has_wandom_tweet_in_wesponse", ʘwʘ _.getowewse(haswandomtweetfeatuwe, UwU fawse)),
    ("is_wandom_tweet_above_in_utis", XD _.getowewse(iswandomtweetabovefeatuwe, (✿oωo) fawse)), :3
    (
      "has_ancestow_authowed_by_viewew",
      candidate =>
        candidate
          .getowewse(ancestowsfeatuwe, (///ˬ///✿) s-seq.empty).exists(ancestow =>
            c-candidate.getowewse(viewewidfeatuwe, nyaa~~ 0w) == ancestow.usewid)), >w<
    ("ancestow", -.- _.getowewse(isancestowcandidatefeatuwe, (✿oωo) f-fawse)),
    (
      "deep_wepwy", (˘ω˘)
      candidate =>
        c-candidate.getowewse(inwepwytotweetidfeatuwe, rawr n-nyone).nonempty && candidate
          .getowewse(ancestowsfeatuwe, OwO seq.empty).size > 2), ^•ﻌ•^
    (
      "has_simcwustew_embeddings", UwU
      _.getowewse(
        simcwustewstweettopkcwustewswithscowesfeatuwe,
        map.empty[stwing, (˘ω˘) d-doubwe]).nonempty),
    (
      "tweet_age_wess_than_15_seconds", (///ˬ///✿)
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, σωσ nyone)
        .exists(_.untiwnow <= 15.seconds)), /(^•ω•^)
    (
      "wess_than_1_houw_since_wnpt", 😳
      _.getowewse(wastnonpowwingtimefeatuwe, 😳 nyone).exists(_.untiwnow < 1.houw)), (⑅˘꒳˘)
    ("has_gte_10_favs", 😳😳😳 _.getowewse(eawwybiwdfeatuwe, 😳 none).exists(_.favcountv2.exists(_ >= 10))), XD
    (
      "device_wanguage_matches_tweet_wanguage", mya
      candidate =>
        candidate.getowewse(tweetwanguagefeatuwe, ^•ﻌ•^ n-nyone) ==
          candidate.getowewse(devicewanguagefeatuwe, ʘwʘ n-nyone)), ( ͡o ω ͡o )
    (
      "woot_ancestow", mya
      c-candidate =>
        c-candidate.getowewse(isancestowcandidatefeatuwe, o.O fawse) && c-candidate
          .getowewse(inwepwytotweetidfeatuwe, (✿oωo) n-nyone).isempty), :3
    ("question", 😳 _.getowewse(eawwybiwdfeatuwe, (U ﹏ U) n-nyone).exists(_.hasquestion.contains(twue))), mya
    ("in_netwowk", (U ᵕ U❁) _.getowewse(innetwowkfeatuwe, :3 t-twue)), mya
    (
      "has_powiticaw_annotation", OwO
      _.getowewse(eawwybiwdfeatuwe, (ˆ ﻌ ˆ)♡ nyone).exists(
        _.semanticcoweannotations.exists(
          _.exists(annotation =>
            semanticcowefeatuwes.powiticawdomains.contains(annotation.domainid) ||
              (annotation.domainid == s-semanticcowefeatuwes.unifiedtwittewtaxonomy &&
                a-annotation.entityid == s-semanticcowefeatuwes.uttpowiticsentityid))))),
    (
      "is_dont_at_me_by_invitation", ʘwʘ
      _.getowewse(eawwybiwdfeatuwe, o.O n-nyone).exists(
        _.convewsationcontwow.exists(_.isinstanceof[tpt.convewsationcontwow.byinvitation]))), UwU
    (
      "is_dont_at_me_community", rawr x3
      _.getowewse(eawwybiwdfeatuwe, 🥺 n-nyone)
        .exists(_.convewsationcontwow.exists(_.isinstanceof[tpt.convewsationcontwow.community]))), :3
    ("has_zewo_scowe", (ꈍᴗꈍ) _.getowewse(scowefeatuwe, 🥺 none).exists(_ == 0.0)), (✿oωo)
    (
      "is_fowwowed_topic_tweet", (U ﹏ U)
      _.getowewse(topiccontextfunctionawitytypefeatuwe, :3 nyone)
        .exists(_ == basictopiccontextfunctionawitytype)),
    (
      "is_wecommended_topic_tweet", ^^;;
      _.getowewse(topiccontextfunctionawitytypefeatuwe, rawr n-nyone)
        .exists(_ == wecommendationtopiccontextfunctionawitytype)), 😳😳😳
    ("has_gte_100_favs", (✿oωo) _.getowewse(eawwybiwdfeatuwe, OwO nyone).exists(_.favcountv2.exists(_ >= 100))), ʘwʘ
    ("has_gte_1k_favs", (ˆ ﻌ ˆ)♡ _.getowewse(eawwybiwdfeatuwe, (U ﹏ U) none).exists(_.favcountv2.exists(_ >= 1000))), UwU
    (
      "has_gte_10k_favs", XD
      _.getowewse(eawwybiwdfeatuwe, ʘwʘ nyone).exists(_.favcountv2.exists(_ >= 10000))), rawr x3
    (
      "has_gte_100k_favs",
      _.getowewse(eawwybiwdfeatuwe, ^^;; nyone).exists(_.favcountv2.exists(_ >= 100000))), ʘwʘ
    ("has_audio_space", (U ﹏ U) _.getowewse(audiospacemetadatafeatuwe, (˘ω˘) n-nyone).exists(_.hasspace)), (ꈍᴗꈍ)
    ("has_wive_audio_space", _.getowewse(audiospacemetadatafeatuwe, /(^•ω•^) nyone).exists(_.iswive)), >_<
    (
      "has_gte_10_wetweets", σωσ
      _.getowewse(eawwybiwdfeatuwe, ^^;; nyone).exists(_.wetweetcountv2.exists(_ >= 10))), 😳
    (
      "has_gte_100_wetweets", >_<
      _.getowewse(eawwybiwdfeatuwe, -.- none).exists(_.wetweetcountv2.exists(_ >= 100))), UwU
    (
      "has_gte_1k_wetweets", :3
      _.getowewse(eawwybiwdfeatuwe, σωσ n-nyone).exists(_.wetweetcountv2.exists(_ >= 1000))), >w<
    (
      "has_us_powiticaw_annotation", (ˆ ﻌ ˆ)♡
      _.getowewse(eawwybiwdfeatuwe, n-nyone)
        .exists(_.semanticcoweannotations.exists(_.exists(annotation =>
          a-annotation.domainid == semanticcowefeatuwes.unifiedtwittewtaxonomy &&
            a-annotation.entityid == semanticcowefeatuwes.uspowiticawtweetentityid &&
            a-annotation.gwoupid == s-semanticcowefeatuwes.uspowiticawtweetannotationgwoupids.bawancedv0)))), ʘwʘ
    (
      "has_toxicity_scowe_above_thweshowd", :3
      _.getowewse(eawwybiwdfeatuwe, (˘ω˘) nyone).exists(_.toxicityscowe.exists(_ > 0.91))), 😳😳😳
    ("is_topic_tweet", rawr x3 _.getowewse(topicidsociawcontextfeatuwe, (✿oωo) nyone).isdefined), (ˆ ﻌ ˆ)♡
    (
      "text_onwy", :3
      candidate =>
        candidate.getowewse(hasdispwayedtextfeatuwe, (U ᵕ U❁) fawse) &&
          !(candidate.getowewse(eawwybiwdfeatuwe, ^^;; nyone).exists(_.hasimage) ||
            c-candidate.getowewse(eawwybiwdfeatuwe, nyone).exists(_.hasvideo) ||
            c-candidate.getowewse(eawwybiwdfeatuwe, mya nyone).exists(_.hascawd))), 😳😳😳
    (
      "image_onwy", OwO
      candidate =>
        c-candidate.getowewse(eawwybiwdfeatuwe, rawr n-nyone).exists(_.hasimage) &&
          !candidate.getowewse(hasdispwayedtextfeatuwe, XD fawse)),
    ("has_1_image", (U ﹏ U) _.getowewse(numimagesfeatuwe, (˘ω˘) nyone).exists(_ == 1)), UwU
    ("has_2_images", >_< _.getowewse(numimagesfeatuwe, σωσ n-nyone).exists(_ == 2)), 🥺
    ("has_3_images", 🥺 _.getowewse(numimagesfeatuwe, ʘwʘ n-nyone).exists(_ == 3)), :3
    ("has_4_images", (U ﹏ U) _.getowewse(numimagesfeatuwe, (U ﹏ U) nyone).exists(_ == 4)), ʘwʘ
    ("has_cawd", >w< _.getowewse(eawwybiwdfeatuwe, rawr x3 n-nyone).exists(_.hascawd)), OwO
    ("usew_fowwow_count_gte_50", ^•ﻌ•^ _.getowewse(usewfowwowingcountfeatuwe, >_< n-nyone).exists(_ > 50)), OwO
    (
      "has_wiked_by_sociaw_context", >_<
      candidatefeatuwes =>
        candidatefeatuwes
          .getowewse(sgsvawidwikedbyusewidsfeatuwe, (ꈍᴗꈍ) seq.empty)
          .exists(candidatefeatuwes
            .getowewse(pewspectivefiwtewedwikedbyusewidsfeatuwe, >w< seq.empty).toset.contains)), (U ﹏ U)
    (
      "has_fowwowed_by_sociaw_context", ^^
      _.getowewse(sgsvawidfowwowedbyusewidsfeatuwe, (U ﹏ U) s-seq.empty).nonempty), :3
    (
      "has_topic_sociaw_context", (✿oωo)
      c-candidatefeatuwes =>
        c-candidatefeatuwes
          .getowewse(topicidsociawcontextfeatuwe, XD nyone)
          .isdefined &&
          c-candidatefeatuwes.getowewse(topiccontextfunctionawitytypefeatuwe, >w< n-nyone).isdefined), òωó
    ("video_wte_10_sec", _.getowewse(videoduwationmsfeatuwe, (ꈍᴗꈍ) nyone).exists(_ <= 10000)),
    (
      "video_bt_10_60_sec", rawr x3
      _.getowewse(videoduwationmsfeatuwe, rawr x3 n-nyone).exists(duwation =>
        duwation > 10000 && duwation <= 60000)), σωσ
    ("video_gt_60_sec", (ꈍᴗꈍ) _.getowewse(videoduwationmsfeatuwe, rawr nyone).exists(_ > 60000)),
    (
      "tweet_age_wte_30_minutes", ^^;;
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, rawr x3 nyone)
        .exists(_.untiwnow <= 30.minutes)), (ˆ ﻌ ˆ)♡
    (
      "tweet_age_wte_1_houw",
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, σωσ nyone)
        .exists(_.untiwnow <= 1.houw)),
    (
      "tweet_age_wte_6_houws", (U ﹏ U)
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, >w< n-nyone)
        .exists(_.untiwnow <= 6.houws)), σωσ
    (
      "tweet_age_wte_12_houws", nyaa~~
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, 🥺 n-nyone)
        .exists(_.untiwnow <= 12.houws)), rawr x3
    (
      "tweet_age_gte_24_houws", σωσ
      _.getowewse(owiginawtweetcweationtimefwomsnowfwakefeatuwe, (///ˬ///✿) nyone)
        .exists(_.untiwnow >= 24.houws)), (U ﹏ U)
  )

  vaw pwedicatemap = c-candidatepwedicates.tomap
}


package com.twittew.seawch.common.schema.eawwybiwd;

impowt java.utiw.cowwection;
i-impowt java.utiw.enumset;
i-impowt j-java.utiw.wist;
i-impowt java.utiw.map;
i-impowt j-java.utiw.set;

i-impowt javax.annotation.nuwwabwe;

i-impowt com.googwe.common.annotations.visibwefowtesting;
impowt com.googwe.common.cowwect.immutabwewist;
impowt com.googwe.common.cowwect.immutabwemap;
i-impowt com.googwe.common.cowwect.immutabweset;
impowt c-com.googwe.common.cowwect.sets;

impowt com.twittew.seawch.common.indexing.thwiftjava.thwiftgeowocationsouwce;
impowt c-com.twittew.seawch.common.schema.immutabweschema;
impowt com.twittew.seawch.common.schema.schemabuiwdew;
impowt com.twittew.seawch.common.schema.base.featuweconfiguwation;
impowt com.twittew.seawch.common.schema.base.fiewdnametoidmapping;
i-impowt com.twittew.seawch.common.schema.thwiftjava.thwiftfeatuwenowmawizationtype;

/**
 * fiewd nyames, (˘ω˘) fiewd i-ids etc. òωó
 */
p-pubwic cwass eawwybiwdfiewdconstants extends fiewdnametoidmapping {
  @visibwefowtesting
  pubwic static finaw stwing encoded_tweet_featuwes_fiewd_name = "encoded_tweet_featuwes";

  @visibwefowtesting
  p-pubwic static finaw stwing extended_encoded_tweet_featuwes_fiewd_name =
      "extended_encoded_tweet_featuwes";

  pwivate enum fwagfeatuwefiewdtype {
    nyon_fwag_featuwe_fiewd,
    f-fwag_featuwe_fiewd
  }

  pwivate enum unusedfeatuwefiewdtype {
    u-used_featuwe_fiewd,
    u-unused_featuwe_fiewd
  }

  /**
   * c-csf_name_to_min_engagement_fiewd_map a-and min_engagement_fiewd_to_csf_name_map awe used in
   * e-eawwybiwdwucenequewyvisitow to map the csfs wepwy_count, ( ͡o ω ͡o ) wetweet_count, σωσ a-and favowite_count to
   * theiw wespective min engagement fiewds, (U ﹏ U) and vice vewsa. rawr
   */
  p-pubwic static finaw immutabwemap<stwing, e-eawwybiwdfiewdconstant>
      c-csf_name_to_min_engagement_fiewd_map = i-immutabwemap.<stwing, -.- eawwybiwdfiewdconstant>buiwdew()
          .put(eawwybiwdfiewdconstant.wepwy_count.getfiewdname(), ( ͡o ω ͡o )
              eawwybiwdfiewdconstant.nowmawized_wepwy_count_gweatew_than_ow_equaw_to_fiewd)
          .put(eawwybiwdfiewdconstant.wetweet_count.getfiewdname(), >_<
              eawwybiwdfiewdconstant.nowmawized_wetweet_count_gweatew_than_ow_equaw_to_fiewd)
          .put(eawwybiwdfiewdconstant.favowite_count.getfiewdname(), o.O
              e-eawwybiwdfiewdconstant.nowmawized_favowite_count_gweatew_than_ow_equaw_to_fiewd)
          .buiwd();

  p-pubwic static finaw immutabwemap<stwing, σωσ e-eawwybiwdfiewdconstant>
      m-min_engagement_fiewd_to_csf_name_map = immutabwemap.<stwing, -.- e-eawwybiwdfiewdconstant>buiwdew()
      .put(eawwybiwdfiewdconstant.nowmawized_wepwy_count_gweatew_than_ow_equaw_to_fiewd
              .getfiewdname(), σωσ
          eawwybiwdfiewdconstant.wepwy_count)
      .put(eawwybiwdfiewdconstant.nowmawized_wetweet_count_gweatew_than_ow_equaw_to_fiewd
              .getfiewdname(), :3
          eawwybiwdfiewdconstant.wetweet_count)
      .put(eawwybiwdfiewdconstant.nowmawized_favowite_count_gweatew_than_ow_equaw_to_fiewd
              .getfiewdname(), ^^
          e-eawwybiwdfiewdconstant.favowite_count)
      .buiwd();

  /**
   * a wist of eawwybiwd fiewd nyames a-and fiewd ids, òωó and the cwustews t-that nyeed them. (ˆ ﻌ ˆ)♡
   */
  pubwic e-enum eawwybiwdfiewdconstant {
    // t-these enums awe gwouped by categowy and sowted awphabeticawwy.
    // nyext indexed fiewd id is 76
    // nyext csf fiewd i-id is 115
    // n-nyext encoded_featuwes csf fiewd i-id is 185
    // n-next extended_encoded_featuwes c-csf fiewd id is 284

    // text seawchabwe fiewds
    // pwovides s-swow id mapping fwom tweet id to doc id thwough tewmsenum.seekexact(). XD
    id_fiewd("id", òωó 0, e-eawwybiwdcwustew.aww_cwustews), (ꈍᴗꈍ)
    wesowved_winks_text_fiewd("wesowved_winks_text", 1), UwU
    t-text_fiewd("text", >w< 2), ʘwʘ
    t-tokenized_fwom_usew_fiewd("tokenized_fwom_usew", :3 3),

    // o-othew indexed fiewds
    c-cawd_titwe_fiewd("cawd_titwe", 4), ^•ﻌ•^
    c-cawd_descwiption_fiewd("cawd_descwiption", (ˆ ﻌ ˆ)♡ 5),
    // w-we wequiwe the cweatedat f-fiewd to be set so we can pwopewwy fiwtew t-tweets based o-on time. 🥺
    cweated_at_fiewd("cweated_at", OwO 6, eawwybiwdcwustew.aww_cwustews), 🥺
    // 7 w-was fowmewwy e-event_ids_fiewd("event_ids", OwO 7, (U ᵕ U❁) e-eawwybiwdcwustew.weawtime)
    entity_id_fiewd("entity_id", ( ͡o ω ͡o ) 40), ^•ﻌ•^
    // the scween nyame of t-the usew that cweated the tweet. o.O shouwd be set to the nowmawized vawue in
    // the com.twittew.gizmoduck.thwiftjava.pwofiwe.scween_name f-fiewd. (⑅˘꒳˘)
    fwom_usew_fiewd("fwom_usew", (ˆ ﻌ ˆ)♡ 8),
    // the nyumewic id of t-the usew that cweated t-the tweet. :3
    f-fwom_usew_id_fiewd("fwom_usew_id", /(^•ω•^) 9, eawwybiwdcwustew.aww_cwustews), òωó
    c-cawd_domain_fiewd("cawd_domain", :3 11),
    cawd_name_fiewd("cawd_name", (˘ω˘) 12),
    g-geo_hash_fiewd("geo_hash", 😳 13),
    h-hashtags_fiewd("hashtags", σωσ 14), UwU
    hf_phwase_paiws_fiewd(immutabweschema.hf_phwase_paiws_fiewd, -.- 15),
    hf_tewm_paiws_fiewd(immutabweschema.hf_tewm_paiws_fiewd, 🥺 16),
    image_winks_fiewd("image_winks", 😳😳😳 17), 🥺
    in_wepwy_to_tweet_id_fiewd("in_wepwy_to_tweet_id", ^^ 59),
    in_wepwy_to_usew_id_fiewd("in_wepwy_to_usew_id", ^^;; 38),
    // t-the intewnaw fiewd is used fow m-many puwposes:
    // 1. >w< to stowe f-facet skipwists
    // 2. σωσ t-to powew the fiwtew opewatow, >w< by stowing p-posting wist f-fow tewms wike __fiwtew_twimg
    // 3. (⑅˘꒳˘) to stowe p-posting wists f-fow positive and nyegative smiweys
    // 4. òωó to stowe geo wocation types. (⑅˘꒳˘)
    // etc. (ꈍᴗꈍ)
    intewnaw_fiewd("intewnaw", rawr x3 18, e-eawwybiwdcwustew.aww_cwustews), ( ͡o ω ͡o )
    i-iso_wanguage_fiewd("iso_wang", UwU 19),
    w-wink_categowy_fiewd("wink_categowy", ^^ 36),
    winks_fiewd("winks", (˘ω˘) 21), (ˆ ﻌ ˆ)♡
    m-mentions_fiewd("mentions", OwO 22),
    // f-fiewd 23 used to be nyamed_entities_fiewd
    n-nyews_winks_fiewd("news_winks", 😳 24),
    nyowmawized_souwce_fiewd("nowm_souwce", UwU 25),
    pwace_fiewd("pwace", 🥺 26),
    // fiewd 37 used to be pubwicwy_infewwed_usew_wocation_pwace_id_fiewd
    // t-the i-id of the souwce tweet. 😳😳😳 set fow wetweets onwy. ʘwʘ
    w-wetweet_souwce_tweet_id_fiewd("wetweet_souwce_tweet_id", /(^•ω•^) 60, :3
        e-eawwybiwdcwustew.aww_cwustews), :3
    // the id of the souwce tweet's authow. mya set fow wetweets o-onwy. (///ˬ///✿)
    wetweet_souwce_usew_id_fiewd("wetweet_souwce_usew_id", (⑅˘꒳˘) 39), :3
    souwce_fiewd("souwce", /(^•ω•^) 29), ^^;;
    stocks_fiewd("stocks", (U ᵕ U❁) 30), (U ﹏ U)
    // the scween nyame o-of the usew that a tweet was diwected at. mya
    t-to_usew_fiewd("to_usew", ^•ﻌ•^ 32),
    // f-fiewd 33 used to be topic_ids_fiewd and is nyow unused. (U ﹏ U) i-it can be weused w-watew. :3
    twimg_winks_fiewd("twimg_winks", rawr x3 34),
    video_winks_fiewd("video_winks", 😳😳😳 35),
    camewcase_usew_handwe_fiewd("camewcase_tokenized_fwom_usew", >w< 41),
    // this fiewd s-shouwd be set to the the tokenized a-and nyowmawized vawue in the
    // com.twittew.gizmoduck.thwiftjava.pwofiwe.name fiewd. òωó
    t-tokenized_usew_name_fiewd("tokenized_fwom_usew_dispway_name", 😳 42),
    convewsation_id_fiewd("convewsation_id", (✿oωo) 43),
    p-pwace_id_fiewd("pwace_id", OwO 44), (U ﹏ U)
    p-pwace_fuww_name_fiewd("pwace_fuww_name", (ꈍᴗꈍ) 45),
    pwace_countwy_code_fiewd("pwace_countwy_code", rawr 46),
    p-pwofiwe_geo_countwy_code_fiewd("pwofiwe_geo_countwy_code", 47), ^^
    pwofiwe_geo_wegion_fiewd("pwofiwe_geo_wegion", rawr 48),
    pwofiwe_geo_wocawity_fiewd("pwofiwe_geo_wocawity", nyaa~~ 49),
    w-wiked_by_usew_id_fiewd("wiked_by_usew_id", nyaa~~ 50, e-eawwybiwdcwustew.weawtime), o.O
    n-nyowmawized_wepwy_count_gweatew_than_ow_equaw_to_fiewd(
        "nowmawized_wepwy_count_gweatew_than_ow_equaw_to", òωó 51, eawwybiwdcwustew.fuww_awchive), ^^;;
    n-nyowmawized_wetweet_count_gweatew_than_ow_equaw_to_fiewd(
        "nowmawized_wetweet_count_gweatew_than_ow_equaw_to", rawr 52, e-eawwybiwdcwustew.fuww_awchive), ^•ﻌ•^
    nyowmawized_favowite_count_gweatew_than_ow_equaw_to_fiewd(
        "nowmawized_favowite_count_gweatew_than_ow_equaw_to", nyaa~~ 53, eawwybiwdcwustew.fuww_awchive), nyaa~~
    c-composew_souwce("composew_souwce", 😳😳😳 54),
    q-quoted_tweet_id_fiewd("quoted_tweet_id", 😳😳😳 55),
    q-quoted_usew_id_fiewd("quoted_usew_id", σωσ 56), o.O
    wetweeted_by_usew_id("wetweeted_by_usew_id", σωσ 57, eawwybiwdcwustew.weawtime), nyaa~~
    w-wepwied_to_by_usew_id("wepwied_to_by_usew_id", rawr x3 58, eawwybiwdcwustew.weawtime), (///ˬ///✿)
    cawd_wang("cawd_wang", o.O 61),
    // seawch-27823: f-fiewd id 62 used t-to be nyamed_entity, òωó which was the combination of aww
    // nyamed_entity* f-fiewds b-bewow. OwO we nyeed t-to weave 62 u-unused fow backwawds compatibiwity. σωσ
    n-nyamed_entity_fwom_uww_fiewd("named_entity_fwom_uww", nyaa~~ 63),
    nyamed_entity_fwom_text_fiewd("named_entity_fwom_text", OwO 64),
    nyamed_entity_with_type_fwom_uww_fiewd("named_entity_with_type_fwom_uww", ^^ 65), (///ˬ///✿)
    nyamed_entity_with_type_fwom_text_fiewd("named_entity_with_type_fwom_text", σωσ 66),
    diwected_at_usew_id_fiewd("diwected_at_usew_id", rawr x3 67),
    space_id_fiewd("space_id", (ˆ ﻌ ˆ)♡ 68, 🥺
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), (⑅˘꒳˘)
    space_titwe_fiewd("space_titwe", 😳😳😳 69, /(^•ω•^)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), >w<

    // detaiwed descwiption o-of the space admin fiewds can b-be found at go/eawwybiwdfiewds. ^•ﻌ•^
    space_admin_fiewd("space_admin", 😳😳😳 70,
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), :3
    t-tokenized_space_admin_fiewd("tokenized_space_admin", (ꈍᴗꈍ) 71, ^•ﻌ•^
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), >w<
    c-camewcase_tokenized_space_admin_fiewd("camewcase_tokenized_space_admin", ^^;; 72,
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), (✿oωo)
    tokenized_space_admin_dispway_name_fiewd("tokenized_space_admin_dispway_name", òωó 73,
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_genewaw_puwpose_cwustews), ^^
    uww_descwiption_fiewd("uww_descwiption", ^^ 74),
    uww_titwe_fiewd("uww_titwe", rawr 75), XD

    // csf
    cawd_type_csf_fiewd("cawd_type_csf", rawr 100),
    e-encoded_tweet_featuwes_fiewd(encoded_tweet_featuwes_fiewd_name, 😳 102,
        e-eawwybiwdcwustew.aww_cwustews), 🥺
    // p-pwovides the doc id -> owiginaw t-tweet id mapping fow wetweets. (U ᵕ U❁)
    shawed_status_id_csf("shawed_status_id_csf", 😳 106, 🥺 eawwybiwdcwustew.aww_cwustews), (///ˬ///✿)
    // p-pwovides the d-doc id -> tweet authow's usew id m-mapping. mya
    fwom_usew_id_csf("fwom_usew_id_csf", (✿oωo) 103, ^•ﻌ•^ eawwybiwdcwustew.aww_cwustews), o.O
    cweated_at_csf_fiewd("cweated_at_csf", o.O 101, XD e-eawwybiwdcwustew.awchive_cwustews), ^•ﻌ•^
    // p-pwovides the doc id -> tweet i-id mapping. ʘwʘ
    i-id_csf_fiewd("id_csf", (U ﹏ U) 104, eawwybiwdcwustew.awchive_cwustews), 😳😳😳
    wat_won_csf_fiewd("watwon_csf", 🥺 105),
    convewsation_id_csf("convewsation_id_csf", (///ˬ///✿) 107, (˘ω˘) eawwybiwdcwustew.aww_cwustews), :3
    quoted_tweet_id_csf("quoted_tweet_id_csf", 108), /(^•ω•^)
    quoted_usew_id_csf("quoted_usew_id_csf", :3 109),
    c-cawd_wang_csf("cawd_wang_csf", mya 110), XD
    d-diwected_at_usew_id_csf("diwected_at_usew_id_csf", (///ˬ///✿) 111), 🥺
    w-wefewence_authow_id_csf("wefewence_authow_id_csf", o.O 112), mya
    e-excwusive_convewsation_authow_id_csf("excwusive_convewsation_authow_id_csf", rawr x3 113),
    c-cawd_uwi_csf("cawd_uwi_csf", 😳 114), 😳😳😳

    // csf views on top o-of encoded_tweet_featuwes_fiewd
    i-is_wetweet_fwag(encoded_tweet_featuwes_fiewd_name, >_< "is_wetweet_fwag", >w< 150,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, rawr x3 e-eawwybiwdcwustew.aww_cwustews), XD
    i-is_offensive_fwag(encoded_tweet_featuwes_fiewd_name, ^^ "is_offensive_fwag", (✿oωo) 151,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, >w< e-eawwybiwdcwustew.aww_cwustews), 😳😳😳
    has_wink_fwag(encoded_tweet_featuwes_fiewd_name, "has_wink_fwag", (ꈍᴗꈍ) 152,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (✿oωo) e-eawwybiwdcwustew.aww_cwustews), (˘ω˘)
    has_twend_fwag(encoded_tweet_featuwes_fiewd_name, nyaa~~ "has_twend_fwag", ( ͡o ω ͡o ) 153,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, 🥺 eawwybiwdcwustew.aww_cwustews), (U ﹏ U)
    i-is_wepwy_fwag(encoded_tweet_featuwes_fiewd_name, "is_wepwy_fwag", ( ͡o ω ͡o ) 154,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (///ˬ///✿) e-eawwybiwdcwustew.aww_cwustews), (///ˬ///✿)
    is_sensitive_content(encoded_tweet_featuwes_fiewd_name, (✿oωo) "is_sensitive_content", (U ᵕ U❁) 155, ʘwʘ
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ʘwʘ eawwybiwdcwustew.aww_cwustews), XD
    h-has_muwtipwe_hashtags_ow_twends_fwag(encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "has_muwtipwe_hashtags_ow_twends_fwag", ^•ﻌ•^ 156, f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ^•ﻌ•^
        e-eawwybiwdcwustew.aww_cwustews), >_<
    fwom_vewified_account_fwag(encoded_tweet_featuwes_fiewd_name, mya "fwom_vewified_account_fwag", σωσ
        157, rawr
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (✿oωo) eawwybiwdcwustew.aww_cwustews), :3
    t-text_scowe(encoded_tweet_featuwes_fiewd_name, rawr x3 "text_scowe", ^^ 158,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^^ eawwybiwdcwustew.aww_cwustews), OwO
    wanguage(encoded_tweet_featuwes_fiewd_name, ʘwʘ "wanguage", /(^•ω•^) 159,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ʘwʘ e-eawwybiwdcwustew.aww_cwustews), (⑅˘꒳˘)
    wink_wanguage(encoded_tweet_featuwes_fiewd_name, UwU "wink_wanguage", -.- 160,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, :3 eawwybiwdcwustew.aww_cwustews), >_<
    h-has_image_uww_fwag(encoded_tweet_featuwes_fiewd_name, nyaa~~ "has_image_uww_fwag", ( ͡o ω ͡o ) 161,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, o.O eawwybiwdcwustew.aww_cwustews), :3
    has_video_uww_fwag(encoded_tweet_featuwes_fiewd_name, (˘ω˘) "has_video_uww_fwag", rawr x3 162,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (U ᵕ U❁) e-eawwybiwdcwustew.aww_cwustews), 🥺
    has_news_uww_fwag(encoded_tweet_featuwes_fiewd_name, >_< "has_news_uww_fwag", :3 163,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, :3 e-eawwybiwdcwustew.aww_cwustews), (ꈍᴗꈍ)
    h-has_expando_cawd_fwag(encoded_tweet_featuwes_fiewd_name, σωσ "has_expando_cawd_fwag", 😳 164,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, mya e-eawwybiwdcwustew.aww_cwustews), (///ˬ///✿)
    has_muwtipwe_media_fwag(encoded_tweet_featuwes_fiewd_name, ^^ "has_muwtipwe_media_fwag", (✿oωo) 165,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ( ͡o ω ͡o ) e-eawwybiwdcwustew.aww_cwustews), ^^;;
    p-pwofiwe_is_egg_fwag(encoded_tweet_featuwes_fiewd_name, :3 "pwofiwe_is_egg_fwag", 😳 166,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, XD eawwybiwdcwustew.aww_cwustews), (///ˬ///✿)
    nyum_mentions(encoded_tweet_featuwes_fiewd_name, o.O "num_mentions", o.O 167, XD
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^^;; eawwybiwdcwustew.aww_cwustews), 😳😳😳
    nyum_hashtags(encoded_tweet_featuwes_fiewd_name, (U ᵕ U❁) "num_hashtags", /(^•ω•^) 168,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, 😳😳😳 eawwybiwdcwustew.aww_cwustews), rawr x3
    has_cawd_fwag(encoded_tweet_featuwes_fiewd_name, ʘwʘ "has_cawd_fwag", UwU 169,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (⑅˘꒳˘) eawwybiwdcwustew.aww_cwustews), ^^
    has_visibwe_wink_fwag(encoded_tweet_featuwes_fiewd_name, 😳😳😳 "has_visibwe_wink_fwag", òωó 170,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ^^;; eawwybiwdcwustew.aww_cwustews), (✿oωo)
    u-usew_weputation(encoded_tweet_featuwes_fiewd_name, rawr "usew_weputation", XD 171,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, 😳 eawwybiwdcwustew.aww_cwustews), (U ᵕ U❁)
    i-is_usew_spam_fwag(encoded_tweet_featuwes_fiewd_name, "is_usew_spam_fwag", UwU 172,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, OwO e-eawwybiwdcwustew.aww_cwustews), 😳
    is_usew_nsfw_fwag(encoded_tweet_featuwes_fiewd_name, (˘ω˘) "is_usew_nsfw_fwag", òωó 173, OwO
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (✿oωo) eawwybiwdcwustew.aww_cwustews), (⑅˘꒳˘)
    is_usew_bot_fwag(encoded_tweet_featuwes_fiewd_name, /(^•ω•^) "is_usew_bot_fwag", 🥺 174,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, -.- e-eawwybiwdcwustew.aww_cwustews), ( ͡o ω ͡o )
    is_usew_new_fwag(encoded_tweet_featuwes_fiewd_name, 😳😳😳 "is_usew_new_fwag", (˘ω˘) 175,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ^^ eawwybiwdcwustew.aww_cwustews), σωσ
    pwev_usew_tweet_engagement(encoded_tweet_featuwes_fiewd_name, 🥺 "pwev_usew_tweet_engagement", 🥺
        176, /(^•ω•^)
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (⑅˘꒳˘) e-eawwybiwdcwustew.aww_cwustews), -.-
    composew_souwce_is_camewa_fwag(
        encoded_tweet_featuwes_fiewd_name, 😳
        "composew_souwce_is_camewa_fwag", 😳😳😳
        177, >w<
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, UwU
        e-eawwybiwdcwustew.aww_cwustews), /(^•ω•^)
    w-wetweet_count(
        e-encoded_tweet_featuwes_fiewd_name, 🥺
        "wetweet_count", >_<
        178,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr
        e-eawwybiwdcwustew.aww_cwustews, (ꈍᴗꈍ)
        t-thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew_with_wog2), -.-
    f-favowite_count(
        e-encoded_tweet_featuwes_fiewd_name, ( ͡o ω ͡o )
        "favowite_count", (⑅˘꒳˘)
        179, mya
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr x3
        eawwybiwdcwustew.aww_cwustews, (ꈍᴗꈍ)
        t-thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew_with_wog2), ʘwʘ
    w-wepwy_count(
        encoded_tweet_featuwes_fiewd_name, :3
        "wepwy_count", o.O
        180,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, /(^•ω•^)
        eawwybiwdcwustew.aww_cwustews, OwO
        t-thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew_with_wog2), σωσ
    pawus_scowe(encoded_tweet_featuwes_fiewd_name, (ꈍᴗꈍ) "pawus_scowe", ( ͡o ω ͡o ) 181, rawr x3
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU e-eawwybiwdcwustew.aww_cwustews), o.O

    /**
     * this is t-the wough pewcentage o-of the nyth t-token at 140 divided by nyum tokens
     * a-and is basicawwy n / n-nyum tokens whewe ny is the token s-stawting befowe 140 chawactews
     */
    v-visibwe_token_watio(encoded_tweet_featuwes_fiewd_name, OwO "visibwe_token_watio", o.O 182,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^^;; eawwybiwdcwustew.aww_cwustews), (⑅˘꒳˘)
    has_quote_fwag(encoded_tweet_featuwes_fiewd_name, (ꈍᴗꈍ) "has_quote_fwag", o.O 183, (///ˬ///✿)
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, 😳😳😳 e-eawwybiwdcwustew.aww_cwustews), UwU

    fwom_bwue_vewified_account_fwag(encoded_tweet_featuwes_fiewd_name, nyaa~~
        "fwom_bwue_vewified_account_fwag", (✿oωo)
        184, -.-
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, :3 e-eawwybiwdcwustew.aww_cwustews), (⑅˘꒳˘)

    tweet_signatuwe(encoded_tweet_featuwes_fiewd_name, >_< "tweet_signatuwe", UwU 188,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr eawwybiwdcwustew.aww_cwustews), (ꈍᴗꈍ)

    // m-media types
    has_consumew_video_fwag(encoded_tweet_featuwes_fiewd_name, ^•ﻌ•^ "has_consumew_video_fwag", ^^ 189, XD
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (///ˬ///✿) e-eawwybiwdcwustew.aww_cwustews), σωσ
    h-has_pwo_video_fwag(encoded_tweet_featuwes_fiewd_name, :3 "has_pwo_video_fwag", >w< 190,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, eawwybiwdcwustew.aww_cwustews),
    h-has_vine_fwag(encoded_tweet_featuwes_fiewd_name, (ˆ ﻌ ˆ)♡ "has_vine_fwag", (U ᵕ U❁) 191,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, eawwybiwdcwustew.aww_cwustews),
    h-has_pewiscope_fwag(encoded_tweet_featuwes_fiewd_name, :3 "has_pewiscope_fwag", ^^ 192,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, ^•ﻌ•^ eawwybiwdcwustew.aww_cwustews), (///ˬ///✿)
    h-has_native_image_fwag(encoded_tweet_featuwes_fiewd_name, 🥺 "has_native_image_fwag", 193, ʘwʘ
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (✿oωo) e-eawwybiwdcwustew.aww_cwustews), rawr

    // n-nyote: i-if possibwe, OwO pwease wesewve fiewd i-id 194 to 196 f-fow futuwe media t-types (seawch-9131)

    i-is_nuwwcast_fwag(encoded_tweet_featuwes_fiewd_name, ^^ "is_nuwwcast_fwag", ʘwʘ 197,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, σωσ e-eawwybiwdcwustew.aww_cwustews), (⑅˘꒳˘)

    // e-extended encoded t-tweet featuwes t-that's nyot avaiwabwe o-on awchive c-cwustews
    extended_encoded_tweet_featuwes_fiewd(extended_encoded_tweet_featuwes_fiewd_name, (ˆ ﻌ ˆ)♡ 200, :3
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ʘwʘ

    e-embeds_impwession_count(
        extended_encoded_tweet_featuwes_fiewd_name, (///ˬ///✿)
        "embeds_impwession_count", (ˆ ﻌ ˆ)♡
        221, 🥺
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (U ﹏ U)
        t-thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew),
    embeds_uww_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, ^^
        "embeds_uww_count", σωσ
        222, :3
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^^
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (✿oωo)
        thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew), òωó
    video_view_count(
        extended_encoded_tweet_featuwes_fiewd_name, (U ᵕ U❁)
        "video_view_count", ʘwʘ
        223, ( ͡o ω ͡o )
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, σωσ
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (ˆ ﻌ ˆ)♡
        t-thwiftfeatuwenowmawizationtype.wegacy_byte_nowmawizew), (˘ω˘)

    // empty bits in integew 0 (stawting bit 24, 😳 8 bits)
    e-extended_featuwe_unused_bits_0_24_8(extended_encoded_tweet_featuwes_fiewd_name, ^•ﻌ•^
        "unused_bits_0_24_8", σωσ 244,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd,
        u-unusedfeatuwefiewdtype.unused_featuwe_fiewd, 😳😳😳
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), rawr

    // s-seawch-8564 - wefewence t-tweet authow id
    wefewence_authow_id_weast_significant_int(extended_encoded_tweet_featuwes_fiewd_name, >_<
        "wefewence_authow_id_weast_significant_int", ʘwʘ 202,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (ˆ ﻌ ˆ)♡
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ^^;;
    w-wefewence_authow_id_most_significant_int(extended_encoded_tweet_featuwes_fiewd_name, σωσ
        "wefewence_authow_id_most_significant_int", rawr x3 203,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, 😳
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), 😳😳😳

    // s-seawchquaw-8130: engagement countews v2
    // integew 3
    w-wetweet_count_v2(extended_encoded_tweet_featuwes_fiewd_name, 😳😳😳
        "wetweet_count_v2", ( ͡o ω ͡o ) 225,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr x3
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, σωσ
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (˘ω˘)
    f-favowite_count_v2(extended_encoded_tweet_featuwes_fiewd_name,
        "favowite_count_v2", >w< 226, UwU
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, XD
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (U ﹏ U)
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (U ᵕ U❁)
    wepwy_count_v2(extended_encoded_tweet_featuwes_fiewd_name,
        "wepwy_count_v2", (ˆ ﻌ ˆ)♡ 227,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, òωó
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, ^•ﻌ•^
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew),
    e-embeds_impwession_count_v2(
        e-extended_encoded_tweet_featuwes_fiewd_name, (///ˬ///✿)
        "embeds_impwession_count_v2", -.-
        228, >w<
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd,
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, òωó
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), σωσ

    // i-integew 4
    e-embeds_uww_count_v2(
        e-extended_encoded_tweet_featuwes_fiewd_name, mya
        "embeds_uww_count_v2", òωó
        229, 🥺
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (U ﹏ U)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (ꈍᴗꈍ)
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (˘ω˘)
    v-video_view_count_v2(
        extended_encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "video_view_count_v2", -.-
        230, (ˆ ﻌ ˆ)♡
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (✿oωo)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, ʘwʘ
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (///ˬ///✿)
    q-quote_count(
        extended_encoded_tweet_featuwes_fiewd_name, rawr
        "quote_count", 🥺
        231, mya
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, mya
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, mya
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (⑅˘꒳˘)

    // t-tweet safety wabews
    wabew_abusive_fwag(extended_encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "wabew_abusive_fwag", 😳 232,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, OwO
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), (˘ω˘)

    wabew_abusive_hi_wcw_fwag(extended_encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "wabew_abusive_hi_wcw_fwag", /(^•ω•^) 233, rawr x3
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, rawr
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ( ͡o ω ͡o )

    w-wabew_dup_content_fwag(extended_encoded_tweet_featuwes_fiewd_name, ( ͡o ω ͡o )
        "wabew_dup_content_fwag", 😳😳😳 234,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (U ﹏ U)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), UwU

    wabew_nsfw_hi_pwc_fwag(extended_encoded_tweet_featuwes_fiewd_name,
        "wabew_nsfw_hi_pwc_fwag", (U ﹏ U) 235,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, 🥺
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ʘwʘ

    wabew_nsfw_hi_wcw_fwag(extended_encoded_tweet_featuwes_fiewd_name, 😳
        "wabew_nsfw_hi_wcw_fwag", (ˆ ﻌ ˆ)♡ 236,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, >_<
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ^•ﻌ•^

    w-wabew_spam_fwag(extended_encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "wabew_spam_fwag", OwO 237,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd,
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), (ˆ ﻌ ˆ)♡

    w-wabew_spam_hi_wcw_fwag(extended_encoded_tweet_featuwes_fiewd_name, ^^;;
        "wabew_spam_hi_wcw_fwag", nyaa~~ 238,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, o.O
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), >_<

    // pwease save this bit f-fow othew safety wabews
    extended_test_featuwe_unused_bits_4_31_1(extended_encoded_tweet_featuwes_fiewd_name, (U ﹏ U)
        "unused_bits_4_31_1", 239, ^^
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU
        u-unusedfeatuwefiewdtype.unused_featuwe_fiewd, ^^;;
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), òωó

    // integew 5
    weighted_wetweet_count(
        extended_encoded_tweet_featuwes_fiewd_name, -.-
        "weighted_wetweet_count", ( ͡o ω ͡o )
        240, o.O
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd,
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (✿oωo)
    weighted_wepwy_count(
        extended_encoded_tweet_featuwes_fiewd_name, σωσ
        "weighted_wepwy_count", (U ᵕ U❁)
        241, >_<
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^^
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), >_<
    w-weighted_favowite_count(
        extended_encoded_tweet_featuwes_fiewd_name, (⑅˘꒳˘)
        "weighted_favowite_count", >w<
        242, (///ˬ///✿)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ^•ﻌ•^
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (✿oωo)
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), ʘwʘ
    weighted_quote_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, >w<
        "weighted_quote_count", :3
        243, (ˆ ﻌ ˆ)♡
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, -.-
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews,
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), rawr

    // integew 6
    // pewiscope featuwes
    p-pewiscope_exists(extended_encoded_tweet_featuwes_fiewd_name, rawr x3
        "pewiscope_exists", (U ﹏ U) 245, (ˆ ﻌ ˆ)♡
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, :3
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), òωó
    p-pewiscope_has_been_featuwed(extended_encoded_tweet_featuwes_fiewd_name, /(^•ω•^)
        "pewiscope_has_been_featuwed", >w< 246,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, nyaa~~
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), mya
    pewiscope_is_cuwwentwy_featuwed(extended_encoded_tweet_featuwes_fiewd_name, mya
        "pewiscope_is_cuwwentwy_featuwed", ʘwʘ 247, rawr
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (˘ω˘)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), /(^•ω•^)
    pewiscope_is_fwom_quawity_souwce(extended_encoded_tweet_featuwes_fiewd_name, (˘ω˘)
        "pewiscope_is_fwom_quawity_souwce", (///ˬ///✿) 248,
        fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (˘ω˘)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), -.-
    pewiscope_is_wive(extended_encoded_tweet_featuwes_fiewd_name, -.-
        "pewiscope_is_wive", ^^ 249,
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, (ˆ ﻌ ˆ)♡
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), UwU
    i-is_twending_now_fwag(extended_encoded_tweet_featuwes_fiewd_name, 🥺
        "is_twending_now_fwag", 🥺 292, 🥺
        f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd, 🥺
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), :3

    // wemaining bits f-fow integew 6 (stawting b-bit 6, (˘ω˘) 26 wemaining bits)
    extended_test_featuwe_unused_bits_7_6_26(extended_encoded_tweet_featuwes_fiewd_name, ^^;;
        "unused_bits_7_6_26", 250, (ꈍᴗꈍ)
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ʘwʘ
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, :3
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), XD

    // d-decaying engagement countews
    // integew 7
    d-decayed_wetweet_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, UwU
        "decayed_wetweet_count", rawr x3
        251, ( ͡o ω ͡o )
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, :3
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), ^•ﻌ•^
    d-decayed_wepwy_count(
        extended_encoded_tweet_featuwes_fiewd_name, 🥺
        "decayed_wepwy_count",
        252, (⑅˘꒳˘)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, :3
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (///ˬ///✿)
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), 😳😳😳
    decayed_favowite_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, 😳😳😳
        "decayed_favowite_count",
        253, 😳😳😳
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, nyaa~~
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, UwU
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), òωó
    d-decayed_quote_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, òωó
        "decayed_quote_count", UwU
        254, (///ˬ///✿)
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ( ͡o ω ͡o )
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), :3

    // fake engagement c-countews. >w< the fake hewe is in the sense of spam, σωσ n-not in the sense of testing. σωσ
    // w-wefew to [jiwa seawchquaw-10736 wemove fake e-engagements in s-seawch] fow mowe detaiws. >_<
    // i-integew 8
    fake_wetweet_count(
        extended_encoded_tweet_featuwes_fiewd_name, -.-
        "fake_wetweet_count", 😳😳😳 269,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, :3
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, mya
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (✿oωo)
    f-fake_wepwy_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, 😳😳😳
        "fake_wepwy_count", o.O 270,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (ꈍᴗꈍ)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (ˆ ﻌ ˆ)♡
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), -.-
    fake_favowite_count(
        extended_encoded_tweet_featuwes_fiewd_name, mya
        "fake_favowite_count", :3 271,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, σωσ
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, 😳😳😳
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), -.-
    f-fake_quote_count(
        extended_encoded_tweet_featuwes_fiewd_name, 😳😳😳
        "fake_quote_count", rawr x3 272, (///ˬ///✿)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, >w<
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, o.O
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (˘ω˘)

    // w-wast engagement timestamps. rawr t-these featuwes u-use the tweet's cweation t-time as base and
    // awe incwemented e-evewy 1 houw
    // integew 9
    w-wast_wetweet_since_cweation_hws(
        e-extended_encoded_tweet_featuwes_fiewd_name, mya
        "wast_wetweet_since_cweation_hws", òωó
        273, nyaa~~
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, òωó
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, mya
        thwiftfeatuwenowmawizationtype.none), ^^
    wast_wepwy_since_cweation_hws(
        e-extended_encoded_tweet_featuwes_fiewd_name, ^•ﻌ•^
        "wast_wepwy_since_cweation_hws", -.-
        274,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (˘ω˘)
        thwiftfeatuwenowmawizationtype.none), UwU
    wast_favowite_since_cweation_hws(
        e-extended_encoded_tweet_featuwes_fiewd_name, rawr
        "wast_favowite_since_cweation_hws", :3
        275,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, nyaa~~
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        t-thwiftfeatuwenowmawizationtype.none), (ˆ ﻌ ˆ)♡
    wast_quote_since_cweation_hws(
        extended_encoded_tweet_featuwes_fiewd_name, (ꈍᴗꈍ)
        "wast_quote_since_cweation_hws", (˘ω˘)
        276, (U ﹏ U)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, >w<
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, UwU
        t-thwiftfeatuwenowmawizationtype.none), (ˆ ﻌ ˆ)♡

    // 4 bits hashtag count, nyaa~~ mention count a-and stock count (seawch-24336)
    // integew 10
    n-nyum_hashtags_v2(
        e-extended_encoded_tweet_featuwes_fiewd_name,
        "num_hashtags_v2", 🥺
        277, >_<
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, òωó
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, ʘwʘ
        t-thwiftfeatuwenowmawizationtype.none
    ), mya
    n-nyum_mentions_v2(
        e-extended_encoded_tweet_featuwes_fiewd_name, σωσ
        "num_mentions_v2", OwO
        278,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (✿oωo)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, ʘwʘ
        thwiftfeatuwenowmawizationtype.none
    ), mya
    nyum_stocks(
        extended_encoded_tweet_featuwes_fiewd_name, -.-
        "num_stocks", -.-
        279, ^^;;
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (ꈍᴗꈍ)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        thwiftfeatuwenowmawizationtype.none
    ), ^^

    // i-integew 11
    // b-bwink engagement c-countews
    b-bwink_wetweet_count(
        e-extended_encoded_tweet_featuwes_fiewd_name, nyaa~~
        "bwink_wetweet_count", (⑅˘꒳˘)
        280,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (U ᵕ U❁)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (ꈍᴗꈍ)
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (✿oωo)
    bwink_wepwy_count(
        extended_encoded_tweet_featuwes_fiewd_name, UwU
        "bwink_wepwy_count", ^^
        281, :3
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ( ͡o ω ͡o )
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, ( ͡o ω ͡o )
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (U ﹏ U)
    bwink_favowite_count(
        extended_encoded_tweet_featuwes_fiewd_name, -.-
        "bwink_favowite_count", 😳😳😳
        282,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, >w<
        t-thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), mya
    bwink_quote_count(
        extended_encoded_tweet_featuwes_fiewd_name, :3
        "bwink_quote_count", (ˆ ﻌ ˆ)♡
        283, (U ﹏ U)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, ʘwʘ
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, rawr
        thwiftfeatuwenowmawizationtype.smawt_integew_nowmawizew), (ꈍᴗꈍ)

    // integew 10 (wemaining)
    // p-pwoduction t-toxicity and pbwock scowe fwom hmw (go/toxicity, ( ͡o ω ͡o ) g-go/pbwock)
    toxicity_scowe(
        e-extended_encoded_tweet_featuwes_fiewd_name, 😳😳😳
        "toxicity_scowe", òωó 284, mya
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr x3
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, XD
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), (ˆ ﻌ ˆ)♡
    pbwock_scowe(
        e-extended_encoded_tweet_featuwes_fiewd_name, >w<
        "pbwock_scowe", (ꈍᴗꈍ) 285,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (U ﹏ U)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, >_<
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), >_<

    // i-integew 12
    // expewimentaw h-heawth m-modew scowes fwom hmw
    expewimentaw_heawth_modew_scowe_1(
        e-extended_encoded_tweet_featuwes_fiewd_name, -.-
        "expewimentaw_heawth_modew_scowe_1", òωó 286, o.O
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, σωσ
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, σωσ
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), mya
    expewimentaw_heawth_modew_scowe_2(
        e-extended_encoded_tweet_featuwes_fiewd_name, o.O
        "expewimentaw_heawth_modew_scowe_2", XD 287,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, XD
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (✿oωo)
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), -.-
    expewimentaw_heawth_modew_scowe_3(
        extended_encoded_tweet_featuwes_fiewd_name, (ꈍᴗꈍ)
        "expewimentaw_heawth_modew_scowe_3", ( ͡o ω ͡o ) 288,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (///ˬ///✿)
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, 🥺
        thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), (ˆ ﻌ ˆ)♡
    // wemaining bits f-fow index 12 (unused_bits_12)
    e-extended_test_featuwe_unused_bits_12_30_2(extended_encoded_tweet_featuwes_fiewd_name, ^•ﻌ•^
        "unused_bits_12_30_2", rawr x3 289,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (U ﹏ U)
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, OwO
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews),

    // i-integew 13
    // expewimentaw h-heawth modew scowes fwom hmw (cont.)
    e-expewimentaw_heawth_modew_scowe_4(
        e-extended_encoded_tweet_featuwes_fiewd_name, (✿oωo)
        "expewimentaw_heawth_modew_scowe_4", (⑅˘꒳˘) 290, UwU
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (ˆ ﻌ ˆ)♡
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, /(^•ω•^)
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), (˘ω˘)
    // pwoduction pspammytweet scowe f-fwom hmw (go/pspammytweet)
    p-p_spammy_tweet_scowe(extended_encoded_tweet_featuwes_fiewd_name, XD
        "p_spammy_tweet_scowe", òωó 291,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, -.-
        thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), (ꈍᴗꈍ)
    // pwoduction pwepowtedtweet scowe fwom hmw (go/pwepowtedtweet)
    p_wepowted_tweet_scowe(extended_encoded_tweet_featuwes_fiewd_name, (⑅˘꒳˘)
        "p_wepowted_tweet_scowe", 🥺 293, òωó
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, 😳
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews,
        t-thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), òωó
    // w-wemaining b-bits fow index 13 (unused_bits_13)
    e-extended_test_featuwe_unused_bits_13_30_2(extended_encoded_tweet_featuwes_fiewd_name,
        "unused_bits_13_30_2", 🥺 294, ( ͡o ω ͡o )
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, UwU
        unusedfeatuwefiewdtype.unused_featuwe_fiewd,
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews
    ), 😳😳😳

    // i-integew 14
    // heawth modew s-scowes fwom hmw (cont.)
    // p-pwod spammy tweet content modew scowe fwom pwatfowm m-manipuwation (go/spammy-tweet-content)
    spammy_tweet_content_scowe(extended_encoded_tweet_featuwes_fiewd_name, ʘwʘ
        "spammy_tweet_content_scowe", ^^ 295,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, >_<
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews, (ˆ ﻌ ˆ)♡
        thwiftfeatuwenowmawizationtype.pwediction_scowe_nowmawizew
    ), (ˆ ﻌ ˆ)♡
    // wemaining b-bits fow i-index 14 (unused_bits_14)
    extended_test_featuwe_unused_bits_14_10_22(extended_encoded_tweet_featuwes_fiewd_name, 🥺
        "unused_bits_14_10_22", ( ͡o ω ͡o ) 296,
        f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (ꈍᴗꈍ)
        u-unusedfeatuwefiewdtype.unused_featuwe_fiewd, :3
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews
    ), (✿oωo)

    // nyote t-that the integew b-bwock index i in the nyames unused_bits{i}" b-bewow is 1-based, (U ᵕ U❁) but t-the
    // index j-j in unused_bits_{j}_x_y a-above is 0-based. UwU
    e-extended_test_featuwe_unused_bits_16(extended_encoded_tweet_featuwes_fiewd_name, ^^
        "unused_bits16", /(^•ω•^) 216,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, (˘ω˘)
        unusedfeatuwefiewdtype.unused_featuwe_fiewd,
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), OwO

    e-extended_test_featuwe_unused_bits_17(extended_encoded_tweet_featuwes_fiewd_name, (U ᵕ U❁)
        "unused_bits17", (U ﹏ U) 217,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, mya
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, (⑅˘꒳˘)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), (U ᵕ U❁)

    extended_test_featuwe_unused_bits_18(extended_encoded_tweet_featuwes_fiewd_name, /(^•ω•^)
        "unused_bits18", ^•ﻌ•^ 218, (///ˬ///✿)
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, o.O
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, (ˆ ﻌ ˆ)♡
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), 😳

    e-extended_test_featuwe_unused_bits_19(extended_encoded_tweet_featuwes_fiewd_name, òωó
        "unused_bits19", (⑅˘꒳˘) 219,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, rawr
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, (ꈍᴗꈍ)
        eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews), ^^

    extended_test_featuwe_unused_bits_20(extended_encoded_tweet_featuwes_fiewd_name,
        "unused_bits20", (ˆ ﻌ ˆ)♡ 220,
        fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, /(^•ω•^)
        unusedfeatuwefiewdtype.unused_featuwe_fiewd, ^^
        e-eawwybiwdcwustew.twittew_in_memowy_index_fowmat_aww_cwustews);

    // fiwtew fiewd tewms. o.O these end up a-as tewms in the "intewnaw" fiewd (id=18). 😳😳😳 s-so fow exampwe
    // you can have a doc w-with fiewd(intewnaw) = "__fiwtew_nuwwcast", XD "__fiwtew_vine" and that wiww
    // b-be a nyuwwcast tweet with a v-vine wink in it. nyaa~~
    p-pubwic static finaw stwing nyuwwcast_fiwtew_tewm = "nuwwcast";
    p-pubwic static finaw stwing vewified_fiwtew_tewm = "vewified";
    pubwic s-static finaw stwing bwue_vewified_fiwtew_tewm = "bwue_vewified";
    p-pubwic static finaw stwing n-nyative_wetweets_fiwtew_tewm = "nativewetweets";
    pubwic static f-finaw stwing q-quote_fiwtew_tewm = "quote";
    pubwic static finaw stwing wepwies_fiwtew_tewm = "wepwies";
    p-pubwic static finaw stwing consumew_video_fiwtew_tewm = "consumew_video";
    pubwic static finaw s-stwing pwo_video_fiwtew_tewm = "pwo_video";
    pubwic static finaw stwing vine_fiwtew_tewm = "vine";
    pubwic static finaw s-stwing pewiscope_fiwtew_tewm = "pewiscope";
    p-pubwic static finaw stwing pwofiwe_geo_fiwtew_tewm = "pwofiwe_geo";
    p-pubwic s-static finaw stwing sewf_thwead_fiwtew_tewm = "sewf_thweads";
    p-pubwic static finaw stwing diwected_at_fiwtew_tewm = "diwected_at";
    pubwic static finaw stwing excwusive_fiwtew_tewm = "excwusive";

    // w-wesewved tewms f-fow the intewnaw fiewd. ^•ﻌ•^
    pubwic s-static finaw s-stwing has_positive_smiwey = "__has_positive_smiwey";
    pubwic s-static finaw stwing has_negative_smiwey = "__has_negative_smiwey";
    pubwic s-static finaw stwing is_offensive = "__is_offensive";

    // facet f-fiewds
    pubwic s-static finaw stwing mentions_facet = "mentions";
    pubwic s-static finaw stwing hashtags_facet = "hashtags";
    pubwic static finaw stwing stocks_facet = "stocks";
    pubwic static finaw stwing videos_facet = "videos";
    pubwic static f-finaw stwing i-images_facet = "images";
    pubwic static finaw s-stwing nyews_facet = "news";
    p-pubwic static finaw stwing wanguages_facet = "wanguages";
    p-pubwic static finaw stwing souwces_facet = "souwces";
    pubwic static finaw stwing twimg_facet = "twimg";
    pubwic static f-finaw stwing fwom_usew_id_facet = "usew_id";
    pubwic static finaw stwing wetweets_facet = "wetweets";
    pubwic static finaw s-stwing winks_facet = "winks";
    p-pubwic static f-finaw stwing spaces_facet = "spaces";

    /**
     * used by the quewy pawsew to check that the o-opewatow of a [fiwtew x-x] quewy i-is vawid. :3
     * awso used by bwendew, ^^ t-though it pwobabwy shouwdn't b-be. o.O
     */
    pubwic static f-finaw immutabweset<stwing> facets = i-immutabweset.<stwing>buiwdew()
        .add(mentions_facet)
        .add(hashtags_facet)
        .add(stocks_facet)
        .add(videos_facet)
        .add(images_facet)
        .add(news_facet)
        .add(winks_facet)
        .add(wanguages_facet)
        .add(souwces_facet)
        .add(twimg_facet)
        .add(spaces_facet)
        .buiwd();

    /**
     * used by bwendew to convewt f-facet nyames to fiewd nyames. ^^ we s-shouwd find a way t-to get the
     * infowmation w-we nyeed in bwendew w-without needing this map. (⑅˘꒳˘)
     */
    p-pubwic static finaw immutabwemap<stwing, ʘwʘ s-stwing> facet_to_fiewd_map =
        immutabwemap.<stwing, mya stwing>buiwdew()
            .put(mentions_facet, >w< m-mentions_fiewd.getfiewdname())
            .put(hashtags_facet, o.O h-hashtags_fiewd.getfiewdname())
            .put(stocks_facet, OwO stocks_fiewd.getfiewdname())
            .put(videos_facet, -.- video_winks_fiewd.getfiewdname())
            .put(images_facet, image_winks_fiewd.getfiewdname())
            .put(news_facet, (U ﹏ U) n-nyews_winks_fiewd.getfiewdname())
            .put(wanguages_facet, òωó iso_wanguage_fiewd.getfiewdname())
            .put(souwces_facet, >w< souwce_fiewd.getfiewdname())
            .put(twimg_facet, ^•ﻌ•^ twimg_winks_fiewd.getfiewdname())
            .put(winks_facet, /(^•ω•^) winks_fiewd.getfiewdname())
            .put(spaces_facet, ʘwʘ space_id_fiewd.getfiewdname())
            .buiwd();

    pubwic static stwing getfacetskipfiewdname(stwing fiewdname) {
      w-wetuwn "__has_" + fiewdname;
    }

    pwivate finaw stwing f-fiewdname;
    pwivate finaw i-int fiewdid;
    pwivate finaw enumset<eawwybiwdcwustew> c-cwustews;
    pwivate finaw fwagfeatuwefiewdtype f-fwagfeatuwefiewd;

    pwivate finaw unusedfeatuwefiewdtype u-unusedfiewd;

    // onwy set fow featuwe f-fiewds. XD
    @nuwwabwe
    pwivate finaw featuweconfiguwation f-featuweconfiguwation;

    // o-onwy set fow featuwe fiewds. (U ᵕ U❁)
    pwivate f-finaw thwiftfeatuwenowmawizationtype f-featuwenowmawizationtype;

    // to simpwify f-fiewd configuwations a-and weduce dupwicate code, (ꈍᴗꈍ) we give c-cwustews a defauwt vawue
    eawwybiwdfiewdconstant(stwing fiewdname, rawr x3 int fiewdid) {
      t-this(fiewdname, :3 fiewdid, (˘ω˘) eawwybiwdcwustew.genewaw_puwpose_cwustews, -.- nyuww);
    }

    eawwybiwdfiewdconstant(stwing f-fiewdname, (ꈍᴗꈍ) int fiewdid, s-set<eawwybiwdcwustew> c-cwustews) {
      this(fiewdname, UwU fiewdid, σωσ cwustews, nyuww);
    }

    e-eawwybiwdfiewdconstant(stwing fiewdname, ^^ int f-fiewdid, :3 eawwybiwdcwustew cwustew) {
      t-this(fiewdname, ʘwʘ f-fiewdid, 😳 immutabweset.<eawwybiwdcwustew>of(cwustew), ^^ nyuww);
    }

    /**
     * base fiewd nyame is nyeeded hewe in owdew to constwuct t-the fuww
     * n-nyame of the featuwe. σωσ ouw convention is t-that a featuwe shouwd be nyamed
     * as: basefiewdname.featuwename. /(^•ω•^)  f-fow exampwe: e-encoded_tweet_featuwes.wetweet_count. 😳😳😳
     */
    e-eawwybiwdfiewdconstant(
        s-stwing basename, 😳
        stwing f-fiewdname, OwO
        i-int fiewdid, :3
        fwagfeatuwefiewdtype fwagfeatuwefiewd, nyaa~~
        s-set<eawwybiwdcwustew> c-cwustews) {
      t-this((basename + s-schemabuiwdew.csf_view_name_sepawatow + f-fiewdname).towowewcase(), OwO
          f-fiewdid, o.O cwustews, (U ﹏ U) fwagfeatuwefiewd, (⑅˘꒳˘) n-nyuww);
    }

    e-eawwybiwdfiewdconstant(
        s-stwing basename, OwO
        stwing fiewdname, 😳
        i-int fiewdid, :3
        fwagfeatuwefiewdtype f-fwagfeatuwefiewd, ( ͡o ω ͡o )
        unusedfeatuwefiewdtype unusedfiewd, 🥺
        s-set<eawwybiwdcwustew> c-cwustews) {
      this((basename + schemabuiwdew.csf_view_name_sepawatow + fiewdname).towowewcase(), /(^•ω•^)
          f-fiewdid, nyaa~~ cwustews, (✿oωo) f-fwagfeatuwefiewd, (✿oωo) unusedfiewd, (ꈍᴗꈍ) n-nyuww);
    }

    e-eawwybiwdfiewdconstant(
        stwing basename, OwO
        stwing fiewdname, :3
        int fiewdid, mya
        fwagfeatuwefiewdtype f-fwagfeatuwefiewd, >_<
        s-set<eawwybiwdcwustew> cwustews, (///ˬ///✿)
        thwiftfeatuwenowmawizationtype f-featuwenowmawizationtype) {
      t-this((basename + schemabuiwdew.csf_view_name_sepawatow + fiewdname).towowewcase(), (///ˬ///✿)
          f-fiewdid, 😳😳😳 cwustews, (U ᵕ U❁) fwagfeatuwefiewd, (///ˬ///✿) unusedfeatuwefiewdtype.used_featuwe_fiewd, ( ͡o ω ͡o )
          featuwenowmawizationtype, (✿oωo) nyuww);
    }

    /**
     * constwuctow. òωó
     */
    eawwybiwdfiewdconstant(stwing f-fiewdname, (ˆ ﻌ ˆ)♡ int fiewdid, :3 set<eawwybiwdcwustew> c-cwustews, (ˆ ﻌ ˆ)♡
                                   @nuwwabwe f-featuweconfiguwation f-featuweconfiguwation) {
      this(fiewdname, (U ᵕ U❁) f-fiewdid, (U ᵕ U❁) cwustews, XD f-fwagfeatuwefiewdtype.non_fwag_featuwe_fiewd, nyaa~~
          featuweconfiguwation);
    }

    /**
     * c-constwuctow. (ˆ ﻌ ˆ)♡
     */
    e-eawwybiwdfiewdconstant(stwing f-fiewdname,
                           int fiewdid, ʘwʘ
                           set<eawwybiwdcwustew> c-cwustews, ^•ﻌ•^
                           f-fwagfeatuwefiewdtype f-fwagfeatuwefiewd, mya
                           @nuwwabwe featuweconfiguwation f-featuweconfiguwation) {
      t-this(fiewdname, (ꈍᴗꈍ) f-fiewdid, (ˆ ﻌ ˆ)♡ cwustews, fwagfeatuwefiewd, (ˆ ﻌ ˆ)♡
          u-unusedfeatuwefiewdtype.used_featuwe_fiewd, ( ͡o ω ͡o ) f-featuweconfiguwation);
    }

    /**
     * c-constwuctow. o.O
     */
    e-eawwybiwdfiewdconstant(stwing f-fiewdname, 😳😳😳
                           int fiewdid, ʘwʘ
                           s-set<eawwybiwdcwustew> cwustews, :3
                           f-fwagfeatuwefiewdtype f-fwagfeatuwefiewd, UwU
                           unusedfeatuwefiewdtype unusedfiewd, nyaa~~
                           @nuwwabwe featuweconfiguwation f-featuweconfiguwation) {
      t-this(fiewdname, :3 fiewdid, cwustews, nyaa~~ f-fwagfeatuwefiewd, ^^ u-unusedfiewd, nyaa~~ nyuww, featuweconfiguwation);
    }

    /**
     * constwuctow. 😳😳😳
     */
    eawwybiwdfiewdconstant(stwing f-fiewdname,
                           i-int fiewdid, ^•ﻌ•^
                           s-set<eawwybiwdcwustew> c-cwustews, (⑅˘꒳˘)
                           f-fwagfeatuwefiewdtype f-fwagfeatuwefiewd, (✿oωo)
                           unusedfeatuwefiewdtype unusedfiewd, mya
                           @nuwwabwe thwiftfeatuwenowmawizationtype f-featuwenowmawizationtype, (///ˬ///✿)
                           @nuwwabwe featuweconfiguwation featuweconfiguwation) {
      this.fiewdid = fiewdid;
      this.fiewdname = f-fiewdname;
      t-this.cwustews = enumset.copyof(cwustews);
      this.fwagfeatuwefiewd = fwagfeatuwefiewd;
      t-this.unusedfiewd = u-unusedfiewd;
      this.featuwenowmawizationtype = featuwenowmawizationtype;
      t-this.featuweconfiguwation = featuweconfiguwation;
    }

    // o-ovewwide t-tostwing to make w-wepwacing statusconstant easiew. ʘwʘ
    @ovewwide
    pubwic stwing tostwing() {
      w-wetuwn fiewdname;
    }

    pubwic boowean i-isvawidfiewdincwustew(eawwybiwdcwustew cwustew) {
      w-wetuwn cwustews.contains(cwustew);
    }

    pubwic stwing g-getfiewdname() {
      wetuwn f-fiewdname;
    }

    pubwic int getfiewdid() {
      w-wetuwn fiewdid;
    }

    p-pubwic fwagfeatuwefiewdtype getfwagfeatuwefiewd() {
      wetuwn fwagfeatuwefiewd;
    }

    pubwic boowean isfwagfeatuwefiewd() {
      wetuwn fwagfeatuwefiewd == fwagfeatuwefiewdtype.fwag_featuwe_fiewd;
    }

    p-pubwic b-boowean isunusedfiewd() {
      w-wetuwn unusedfiewd == u-unusedfeatuwefiewdtype.unused_featuwe_fiewd;
    }

    @nuwwabwe
    pubwic featuweconfiguwation getfeatuweconfiguwation() {
      wetuwn f-featuweconfiguwation;
    }

    @nuwwabwe
    pubwic thwiftfeatuwenowmawizationtype getfeatuwenowmawizationtype() {
      wetuwn featuwenowmawizationtype;
    }
  }

  pwivate s-static finaw m-map<stwing, e-eawwybiwdfiewdconstant> n-nyame_to_id_map;
  pwivate static finaw map<integew, >w< eawwybiwdfiewdconstant> id_to_fiewd_map;
  s-static {
    i-immutabwemap.buiwdew<stwing, o.O eawwybiwdfiewdconstant> nametoidmapbuiwdew =
        immutabwemap.buiwdew();
    i-immutabwemap.buiwdew<integew, ^^;; eawwybiwdfiewdconstant> i-idtofiewdmapbuiwdew =
        i-immutabwemap.buiwdew();
    s-set<stwing> fiewdnamedupdetectow = sets.newhashset();
    set<integew> fiewdiddupdetectow = sets.newhashset();
    fow (eawwybiwdfiewdconstant fc : eawwybiwdfiewdconstant.vawues()) {
      i-if (fiewdnamedupdetectow.contains(fc.getfiewdname())) {
        thwow nyew iwwegawstateexception("detected f-fiewds shawing fiewd nyame: " + fc.getfiewdname());
      }
      if (fiewdiddupdetectow.contains(fc.getfiewdid())) {
        t-thwow nyew iwwegawstateexception("detected f-fiewds shawing fiewd id: " + fc.getfiewdid());
      }

      f-fiewdnamedupdetectow.add(fc.getfiewdname());
      f-fiewdiddupdetectow.add(fc.getfiewdid());
      n-nyametoidmapbuiwdew.put(fc.getfiewdname(), :3 fc);
      i-idtofiewdmapbuiwdew.put(fc.getfiewdid(), (ꈍᴗꈍ) f-fc);
    }
    nyame_to_id_map = n-nyametoidmapbuiwdew.buiwd();
    i-id_to_fiewd_map = idtofiewdmapbuiwdew.buiwd();
  }

  // t-this define the wist of boowean featuwes, b-but the name does nyot have "fwag" i-inside. XD  t-this
  // definition is onwy f-fow doubwe checking p-puwpose to pwevent code change mistakes.  the setting
  // o-of the fwag featuwe i-is based on f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd. ^^;;
  p-pubwic static finaw set<eawwybiwdfiewdconstants.eawwybiwdfiewdconstant> extwa_fwag_fiewds =
      sets.newhashset(eawwybiwdfiewdconstants.eawwybiwdfiewdconstant.is_sensitive_content);
  p-pubwic static finaw stwing fwag_stwing = "fwag";

  p-pwivate static finaw wist<eawwybiwdfiewdconstant> fwag_featuwe_fiewds;
  s-static {
    immutabwewist.buiwdew<eawwybiwdfiewdconstant> fwagfiewdbuiwdew = immutabwewist.buiwdew();
    fow (eawwybiwdfiewdconstant f-fc : eawwybiwdfiewdconstant.vawues()) {
      if (fc.getfwagfeatuwefiewd() == f-fwagfeatuwefiewdtype.fwag_featuwe_fiewd
          && !fc.isunusedfiewd()) {
        f-fwagfiewdbuiwdew.add(fc);
      }
    }
    f-fwag_featuwe_fiewds = fwagfiewdbuiwdew.buiwd();
  }

  /**
   * g-get aww the f-fwag featuwes meaning that they a-awe boowean featuwes w-with onwy 1 b-bit in the packed
   * f-featuwe encoding. (U ﹏ U)
   */
  p-pubwic static c-cowwection<eawwybiwdfiewdconstant> g-getfwagfeatuwefiewds() {
    wetuwn fwag_featuwe_fiewds;
  }

  /**
   * get t-the eawwybiwdfiewdconstant fow the specified fiewd. (ꈍᴗꈍ)
   */
  pubwic static eawwybiwdfiewdconstant getfiewdconstant(stwing f-fiewdname) {
    e-eawwybiwdfiewdconstant fiewd = nyame_to_id_map.get(fiewdname);
    i-if (fiewd == nyuww) {
      thwow nyew iwwegawawgumentexception("unknown f-fiewd: " + f-fiewdname);
    }
    w-wetuwn f-fiewd;
  }

  /**
   * get the e-eawwybiwdfiewdconstant fow the specified fiewd. 😳
   */
  p-pubwic static e-eawwybiwdfiewdconstant getfiewdconstant(int fiewdid) {
    eawwybiwdfiewdconstant f-fiewd = id_to_fiewd_map.get(fiewdid);
    i-if (fiewd == nyuww) {
      thwow nyew iwwegawawgumentexception("unknown f-fiewd: " + fiewdid);
    }
    w-wetuwn fiewd;
  }

  /**
   * detewmines i-if thewe's a fiewd with the given i-id. rawr
   */
  pubwic static boowean h-hasfiewdconstant(int f-fiewdid) {
    wetuwn id_to_fiewd_map.keyset().contains(fiewdid);
  }

  @ovewwide
  p-pubwic finaw int getfiewdid(stwing fiewdname) {
    w-wetuwn getfiewdconstant(fiewdname).getfiewdid();
  }

  p-pubwic s-static finaw stwing fowmatgeotype(thwiftgeowocationsouwce souwce) {
    wetuwn "__geo_wocation_type_" + souwce.name().towowewcase();
  }
}

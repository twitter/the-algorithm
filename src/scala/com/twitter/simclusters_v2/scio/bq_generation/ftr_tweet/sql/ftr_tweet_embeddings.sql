with vaws as (
  sewect
    timestamp('{stawt_time}') a-as stawt_time, ^^;;
    t-timestamp('{end_time}') a-as end_time, 🥺
    u-unix_miwwis('{end_time}') a-as cuwwentts, òωó
    {hawfwife} a-as hawfwife, XD
    {tweet_sampwe_wate} a-as t-tweet_sampwe_wate, :3
    {eng_sampwe_wate} as eng_usew_sampwe_wate, (U ﹏ U)
    {min_tweet_favs} as min_tweet_favs, >w<
    {min_tweet_imps} as min_tweet_imps, /(^•ω•^)
    {max_usew_wog_n_imps} as max_usew_wog_n_imps, (⑅˘꒳˘)
    {max_usew_wog_n_favs} a-as max_usew_wog_n_favs, ʘwʘ
    {max_usew_ftw} as max_usew_ftw, rawr x3
    {max_tweet_ftw} a-as max_tweet_ftw, (˘ω˘)
    700 a-as max_exponent, o.O -- this is the maximum exponent one can h-have in bigquewy
  ), 😳
  -- step 1: g-get impwessions a-and favs
  impwessions as (
    sewect
      usewidentifiew.usewid as usew_id, o.O
      i-item.tweetinfo.actiontweetid as tweet_id, ^^;;
      item.tweetinfo.actiontweetauthowinfo.authowid as authow_id, ( ͡o ω ͡o )
      twue a-as impwessed, ^^;;
      min(eventmetadata.souwcetimestampms) a-as mintsmiwwi
    f-fwom t-twttw-bqw-unified-pwod.unified_usew_actions.stweaming_unified_usew_actions, ^^;; v-vaws
    whewe
      actiontype = "cwienttweetwingewimpwession"
      a-and date(datehouw) between date(vaws.stawt_time) and date(vaws.end_time)
      a-and timestamp_miwwis(eventmetadata.souwcetimestampms) between vaws.stawt_time and vaws.end_time
      and mod(abs(fawm_fingewpwint(item.tweetinfo.actiontweetid || '')), XD vaws.tweet_sampwe_wate) = 0
      and m-mod(abs(fawm_fingewpwint(usewidentifiew.usewid || '')), 🥺 vaws.eng_usew_sampwe_wate) = 0
     -- appwy t-tweet age fiwtew h-hewe
     a-and timestamp_miwwis((1288834974657 +
        ((item.tweetinfo.actiontweetid & 9223372036850581504) >> 22))) >= (vaws.stawt_time)
    gwoup by 1, (///ˬ///✿) 2, 3
  ),
  favs as (
    sewect
      u-usewidentifiew.usewid as u-usew_id, (U ᵕ U❁)
      item.tweetinfo.actiontweetid a-as t-tweet_id, ^^;;
      item.tweetinfo.actiontweetauthowinfo.authowid as a-authow_id, ^^;;
      min(eventmetadata.souwcetimestampms) a-as mintsmiwwi, rawr
      -- get wast action, (˘ω˘) and make suwe that i-it's a fav
      awway_agg(actiontype o-owdew by eventmetadata.souwcetimestampms d-desc wimit 1)[offset(0)] = "sewvewtweetfav" as f-favowited, 🥺
    fwom `twttw-bqw-unified-pwod.unified_usew_actions_engagements.stweaming_unified_usew_actions_engagements`, nyaa~~ vaws
    whewe
      actiontype in ("sewvewtweetfav", :3 "sewvewtweetunfav")
      and date(datehouw) between d-date(vaws.stawt_time) a-and date(vaws.end_time)
      a-and timestamp_miwwis(eventmetadata.souwcetimestampms) b-between vaws.stawt_time a-and vaws.end_time
      and mod(abs(fawm_fingewpwint(item.tweetinfo.actiontweetid || '')), /(^•ω•^) vaws.tweet_sampwe_wate) = 0
      and mod(abs(fawm_fingewpwint(usewidentifiew.usewid || '')), ^•ﻌ•^ v-vaws.eng_usew_sampwe_wate) = 0
       -- appwy tweet age fiwtew hewe
      and timestamp_miwwis((1288834974657 +
        ((item.tweetinfo.actiontweetid & 9223372036850581504) >> 22))) >= (vaws.stawt_time)
    g-gwoup by 1, UwU 2, 3
    having favowited
  ), 😳😳😳
  e-eng_data as (
    s-sewect
      usew_id, OwO t-tweet_id, authow_id, ^•ﻌ•^ impwessions.mintsmiwwi, (ꈍᴗꈍ) f-favowited, i-impwessed
    fwom i-impwessions
    w-weft join favs using(usew_id, (⑅˘꒳˘) tweet_id, authow_id)
  ), (⑅˘꒳˘)
  e-ewigibwe_tweets a-as (
    s-sewect
      t-tweet_id, (ˆ ﻌ ˆ)♡
      a-authow_id, /(^•ω•^)
      countif(favowited) nyum_favs,
      countif(impwessed) n-nyum_imps, òωó
      countif(favowited) * 1.0 / countif(impwessed) as tweet_ftw, (⑅˘꒳˘)
      any_vawue(vaws.min_tweet_favs) min_tweet_favs, (U ᵕ U❁)
      a-any_vawue(vaws.min_tweet_imps) min_tweet_imps, >w<
      any_vawue(vaws.max_tweet_ftw) max_tweet_ftw, σωσ
    f-fwom eng_data, -.- v-vaws
    g-gwoup by 1, o.O 2
    having nyum_favs >= m-min_tweet_favs -- this is a-an aggwessive fiwtew t-to make the wowkfwow efficient
      and nyum_imps >= min_tweet_imps
      and tweet_ftw <= max_tweet_ftw -- f-fiwtew to combat spam
  ), ^^
  e-ewigibwe_usews as (
    sewect
      u-usew_id, >_<
      c-cast(wog10(countif(impwessed) + 1) as int64) wog_n_imps, >w<
      c-cast(wog10(countif(favowited) + 1) a-as int64) wog_n_favs, >_<
      a-any_vawue(vaws.max_usew_wog_n_imps) m-max_usew_wog_n_imps, >w<
      any_vawue(vaws.max_usew_wog_n_favs) max_usew_wog_n_favs, rawr
      any_vawue(vaws.max_usew_ftw) max_usew_ftw, rawr x3
      c-countif(favowited) * 1.0 / c-countif(impwessed) usew_ftw
    f-fwom eng_data, ( ͡o ω ͡o ) vaws
    g-gwoup by 1
    h-having
      wog_n_imps < max_usew_wog_n_imps
      a-and wog_n_favs < max_usew_wog_n_favs
      and usew_ftw < max_usew_ftw
  ), (˘ω˘)
  ewigibwe_eng_data a-as (
    s-sewect
      usew_id, 😳
      eng_data.authow_id, OwO
      tweet_id, (˘ω˘)
      m-mintsmiwwi, òωó
      f-favowited, ( ͡o ω ͡o )
      impwessed
    fwom eng_data
    innew join e-ewigibwe_tweets using(tweet_id)
    innew join ewigibwe_usews using(usew_id)
  ), UwU
  f-fowwow_gwaph as (
    sewect usewid, /(^•ω•^) nyeighbow
    f-fwom `twttw-bq-cassowawy-pwod.usew.usew_usew_nowmawized_gwaph` u-usew_usew_gwaph, (ꈍᴗꈍ) unnest(usew_usew_gwaph.neighbows) as nyeighbow
    whewe d-date(_pawtitiontime) =
          (  -- g-get watest pawtition time
          sewect max(date(_pawtitiontime)) w-watest_pawtition
          fwom `twttw-bq-cassowawy-pwod.usew.usew_usew_nowmawized_gwaph`, 😳 v-vaws
          whewe date(_pawtitiontime) between
            d-date_sub(date(vaws.end_time), mya
              intewvaw 14 d-day) and date(vaws.end_time)
            )
    a-and nyeighbow.isfowwowed is twue
  ), mya
  e-extended_ewigibwe_eng_data as (
      sewect
        u-usew_id, /(^•ω•^)
        t-tweet_id, ^^;;
        m-mintsmiwwi, 🥺
        favowited, ^^
        i-impwessed, ^•ﻌ•^
        n-nyeighbow.neighbowid is nyuww as is_oon_eng
      fwom e-ewigibwe_eng_data  w-weft join fowwow_gwaph o-on (fowwow_gwaph.usewid = ewigibwe_eng_data.usew_id and fowwow_gwaph.neighbow.neighbowid = e-ewigibwe_eng_data.authow_id)
  ), /(^•ω•^)
  -- step 2: m-mewge with i-iikf
  iikf as (
  sewect
    usewid as usew_id, ^^

    cwustewidtoscowe.key a-as cwustewid, 🥺
    c-cwustewidtoscowe.vawue.favscowe a-as f-favscowe, (U ᵕ U❁)
    cwustewidtoscowe.vawue.favscowecwustewnowmawizedonwy as favscowecwustewnowmawizedonwy, 😳😳😳
    c-cwustewidtoscowe.vawue.favscowepwoducewnowmawizedonwy as favscowepwoducewnowmawizedonwy, nyaa~~

    cwustewidtoscowe.vawue.wogfavscowe as wogfavscowe, (˘ω˘)
    cwustewidtoscowe.vawue.wogfavscowecwustewnowmawizedonwy as wogfavscowecwustewnowmawizedonwy, >_< -- p-pwobabwy nyo nyeed f-fow cwustew nyowmawization anymowe
    w-wow_numbew() ovew (pawtition b-by usewid owdew by cwustewidtoscowe.vawue.wogfavscowe d-desc) a-as uii_cwustew_wank_wogfavscowe, XD
    w-wow_numbew() o-ovew (pawtition b-by usewid owdew by cwustewidtoscowe.vawue.wogfavscowecwustewnowmawizedonwy desc) as uii_cwustew_wank_wogfavscowecwustewnowmawized, rawr x3
  fwom `twttw-bq-cassowawy-pwod.usew.simcwustews_v2_usew_to_intewested_in_20m_145k_2020`, ( ͡o ω ͡o ) unnest(cwustewidtoscowes) cwustewidtoscowe, :3 v-vaws
  w-whewe date(_pawtitiontime) =
            (-- g-get watest pawtition time
            s-sewect max(date(_pawtitiontime)) watest_pawtition
            fwom `twttw-bq-cassowawy-pwod.usew.simcwustews_v2_usew_to_intewested_in_20m_145k_2020`
            whewe date(_pawtitiontime) b-between
            d-date_sub(date(vaws.end_time), mya
              intewvaw 14 day) a-and date(vaws.end_time)
            )
          and mod(abs(fawm_fingewpwint(usewid || '')), σωσ vaws.eng_usew_sampwe_wate) = 0
          a-and cwustewidtoscowe.vawue.wogfavscowe != 0
  ), (ꈍᴗꈍ)
  e-eng_w_uii as (
    sewect
      t-t_imp_fav.usew_id, OwO
      t-t_imp_fav.tweet_id, o.O
      t_imp_fav.impwessed, 😳😳😳
      t_imp_fav.favowited, /(^•ω•^)
      t_imp_fav.mintsmiwwi, OwO
      t_imp_fav.is_oon_eng, ^^

      iikf.cwustewid,
      i-iikf.wogfavscowe, (///ˬ///✿)
      i-iikf.wogfavscowecwustewnowmawizedonwy, (///ˬ///✿)
      i-iikf.uii_cwustew_wank_wogfavscowe, (///ˬ///✿)
      i-iikf.uii_cwustew_wank_wogfavscowecwustewnowmawized,
    f-fwom extended_ewigibwe_eng_data t_imp_fav, ʘwʘ v-vaws
    innew j-join iikf
      on t_imp_fav.usew_id = i-iikf.usew_id
    w-whewe
        t_imp_fav.impwessed
  ), ^•ﻌ•^
  -- s-step 3: cawcuwate tweet embedding
  tweet_cwustew_agg a-as (
    sewect
      t-tweet_id, OwO
      c-cwustewid, (U ﹏ U)

      sum(if(impwessed, (ˆ ﻌ ˆ)♡ w-wogfavscowe, (⑅˘꒳˘) 0)) denom_wogfavscowe, (U ﹏ U)
      sum(if(favowited, o.O w-wogfavscowe, mya 0)) n-nyom_wogfavscowe, XD

      c-countif(impwessed) ny_imps, òωó
      countif(favowited) ny_favs,

      c-countif(impwessed and uii_cwustew_wank_wogfavscowe <= 5) ny_imps_at_5,
      c-countif(favowited a-and uii_cwustew_wank_wogfavscowe <= 5) ny_favs_at_5, (˘ω˘)

      c-countif(favowited and uii_cwustew_wank_wogfavscowe <= 5 a-and is_oon_eng) n-ny_oon_favs_at_5, :3
      countif(impwessed and uii_cwustew_wank_wogfavscowe <= 5 a-and is_oon_eng) ny_oon_imps_at_5, OwO

      sum(if(favowited and uii_cwustew_wank_wogfavscowe <= 5, mya 1, 0) * pow(0.5, (˘ω˘) (cuwwentts - m-mintsmiwwi) / v-vaws.hawfwife)) as decayed_n_favs_at_5, o.O
      s-sum(if(impwessed and uii_cwustew_wank_wogfavscowe <= 5, (✿oωo) 1, 0) * p-pow(0.5, (ˆ ﻌ ˆ)♡ (cuwwentts - m-mintsmiwwi) / v-vaws.hawfwife)) as decayed_n_imps_at_5, ^^;;

      sum(if(favowited, OwO wogfavscowecwustewnowmawizedonwy, 🥺 0) * pow(0.5, mya (cuwwentts - mintsmiwwi) / vaws.hawfwife)) as dec_sum_wogfavscowecwustewnowmawizedonwy, 😳

      min(mintsmiwwi) mintsmiwwi, òωó

    fwom eng_w_uii, /(^•ω•^) vaws
    gwoup by 1, -.- 2
  ),
  t-tweet_cwustew_intewmediate as (
    s-sewect
      tweet_id, òωó
      cwustewid, /(^•ω•^)
      m-mintsmiwwi, /(^•ω•^)

      n-ny_imps, 😳
      n-ny_favs, :3

      ny_favs_at_5, (U ᵕ U❁)
      n-ny_imps_at_5, ʘwʘ
      ny_oon_favs_at_5, o.O
      n-ny_oon_imps_at_5, ʘwʘ
      d-decayed_n_favs_at_5, ^^
      decayed_n_imps_at_5, ^•ﻌ•^

      d-denom_wogfavscowe, mya
      nyom_wogfavscowe, UwU

      d-dec_sum_wogfavscowecwustewnowmawizedonwy, >_<

      s-safe_divide(n_favs_at_5, /(^•ω•^) ny_imps_at_5) as ftw_at_5, òωó

      s-safe_divide(n_oon_favs_at_5, σωσ  n-ny_oon_imps_at_5) a-as ftw_oon_at_5,

      w-wow_numbew() o-ovew (pawtition b-by tweet_id o-owdew by nyom_wogfavscowe d-desc) cwustew_nom_wogfavscowe_wanking, ( ͡o ω ͡o )
      w-wow_numbew() ovew (pawtition b-by tweet_id o-owdew by dec_sum_wogfavscowecwustewnowmawizedonwy d-desc) cwustew_decsumwogfavcwustewnowmawized_wanking, nyaa~~
    fwom tweet_cwustew_agg
  ), :3
  tweet_e a-as (
    sewect
      tweet_id, UwU

      min(mintsmiwwi) f-fiwst_sewve_miwwis, o.O
      date(timestamp_miwwis(min(mintsmiwwi))) d-date_fiwst_sewve, (ˆ ﻌ ˆ)♡

      a-awway_agg(stwuct(
          c-cwustewid, ^^;;
          -- the d-division by max_exponent is to a-avoid ovewfwow opewation
          ftw_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/1000))) - 1) * i-if(cwustew_decsumwogfavcwustewnowmawized_wanking > max_exponent, ʘwʘ 0, σωσ 1.0/(pow(1.1, c-cwustew_decsumwogfavcwustewnowmawized_wanking-1))) as ftwat5_decayed_pop_bias_1000_wank_decay_1_1
      ) owdew by ftw_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/1000))) - 1) * if(cwustew_decsumwogfavcwustewnowmawized_wanking > m-max_exponent, ^^;; 0, 1.0/(pow(1.1, ʘwʘ cwustew_decsumwogfavcwustewnowmawized_wanking-1))) d-desc w-wimit {tweet_embedding_wength}) ftwat5_decayed_pop_bias_1000_wank_decay_1_1_embedding, ^^

      awway_agg(stwuct(
          cwustewid, nyaa~~
          -- t-the division by max_exponent is t-to avoid ovewfwow o-opewation
          f-ftw_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/10000))) - 1) * if(cwustew_decsumwogfavcwustewnowmawized_wanking > max_exponent, (///ˬ///✿) 0, 1.0/(pow(1.1, XD c-cwustew_decsumwogfavcwustewnowmawized_wanking-1))) a-as ftwat5_decayed_pop_bias_10000_wank_decay_1_1
      ) o-owdew by ftw_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/1000))) - 1) * if(cwustew_decsumwogfavcwustewnowmawized_wanking > max_exponent, 0, :3 1.0/(pow(1.1, òωó cwustew_decsumwogfavcwustewnowmawized_wanking-1))) d-desc wimit {tweet_embedding_wength}) ftwat5_decayed_pop_bias_10000_wank_decay_1_1_embedding, ^^

      a-awway_agg(stwuct(
          c-cwustewid, ^•ﻌ•^
          -- t-the division by max_exponent i-is to avoid o-ovewfwow opewation
          f-ftw_oon_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/1000))) - 1) * i-if(cwustew_nom_wogfavscowe_wanking > max_exponent, σωσ 0, 1.0/(pow(1.1, (ˆ ﻌ ˆ)♡ c-cwustew_nom_wogfavscowe_wanking-1))) a-as oon_ftwat5_decayed_pop_bias_1000_wank_decay
      ) o-owdew by ftw_oon_at_5 * (2 / (1+exp(-1* (decayed_n_favs_at_5/1000))) - 1) * if(cwustew_nom_wogfavscowe_wanking > m-max_exponent, nyaa~~ 0, 1.0/(pow(1.1, ʘwʘ c-cwustew_nom_wogfavscowe_wanking-1))) d-desc wimit {tweet_embedding_wength}) o-oon_ftwat5_decayed_pop_bias_1000_wank_decay_embedding, ^•ﻌ•^

      a-awway_agg(stwuct(
          cwustewid, rawr x3
          d-dec_sum_wogfavscowecwustewnowmawizedonwy
          ) owdew by dec_sum_wogfavscowecwustewnowmawizedonwy d-desc wimit {tweet_embedding_wength}) dec_sum_wogfavscowecwustewnowmawizedonwy_embedding, 🥺

    f-fwom tweet_cwustew_intewmediate, ʘwʘ v-vaws
    gwoup b-by 1
  ), (˘ω˘)
  tweet_e_unnest as (
    sewect
        tweet_id as t-tweetid, o.O
        c-cwustewtoscowes.cwustewid a-as cwustewid, σωσ
        cwustewtoscowes.{scowe_key} tweetscowe
    fwom t-tweet_e, (ꈍᴗꈍ) unnest({scowe_cowumn}) c-cwustewtoscowes
    whewe cwustewtoscowes.{scowe_key} i-is nyot nyuww
      a-and cwustewtoscowes.{scowe_key} > 0
  )
  sewect
    tweetid, (ˆ ﻌ ˆ)♡
    cwustewid, o.O
    tweetscowe
  f-fwom tweet_e_unnest

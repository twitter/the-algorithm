namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

/* i-input souwce */
e-enum souwcewineage {
  /* c-cwient-side. a-awso known a-as wegacy cwient e-events ow wce. (ꈍᴗꈍ) */
  c-cwientevents = 0
  /* cwient-side. /(^•ω•^) awso known as bce. >_< */
  behaviowawcwientevents = 1
  /* s-sewvew-side timewinesewvice favowites */
  sewvewtwsfavs = 2
  /* sewvew-side t-tweetypie events */
  sewvewtweetypieevents = 3
  /* s-sewvew-side sociawgwaph events */
  sewvewsociawgwaphevents = 4
  /* nyotification a-actions wesponding to youw h-highwights emaiws */
  e-emaiwnotificationevents = 5
  /**
  * gizmoduck's usew modification events https://docbiwd.twittew.biz/gizmoduck/usew_modifications.htmw
  **/
  sewvewgizmoduckusewmodificationevents = 6
  /**
  * s-sewvew-side ads cawwback engagements
  **/
  sewvewadscawwbackengagements = 7
  /**
  * sewvew-side favowite awchivaw e-events
  **/
  sewvewfavowiteawchivawevents = 8
  /**
  * s-sewvew-side wetweet a-awchivaw events
  **/
  s-sewvewwetweetawchivawevents = 9
}(pewsisted='twue', σωσ h-haspewsonawdata='fawse')

/*
 * onwy avaiwabwe in behaviowaw cwient e-events (bce). ^^;;
 *
 * a bweadcwumb tweet is a t-tweet that was intewacted with pwiow to the cuwwent action. 😳
 */
stwuct bweadcwumbtweet {
  /* id f-fow the tweet that was intewacted w-with pwiow to t-the cuwwent action */
  1: w-wequiwed i64 tweetid(pewsonawdatatype = 'tweetid')
  /*
   * the ui component that hosted t-the tweet a-and was intewacted with pweceeding t-to the cuwwent a-action. >_<
   * - tweet: wepwesents t-the pawent tweet containew that w-wwaps the quoted tweet
   * - quote_tweet: wepwesents t-the nyested ow quoted tweet w-within the pawent containew
   *
   * s-see mowe d-detaiws
   * https://docs.googwe.com/document/d/16cdswpsmuud17yofh9min3nwbqdvawx4dazoiqsfchi/edit#heading=h.nb7tnjwhqxpm
   */
  2: wequiwed stwing souwcecomponent(pewsonawdatatype = 'websitepage')
}(pewsisted='twue', -.- haspewsonawdata='twue')

/*
 * cwientevent's nyamespaces. s-see https://docbiwd.twittew.biz/cwient_events/cwient-event-namespaces.htmw
 *
 * - f-fow wegacy cwient events (wce), UwU i-it excwudes t-the cwient p-pawt of the
 * six pawt nyamespace (cwient:page:section:component:ewement:action)
 * since this pawt is bettew c-captuwed by cwientappid and cwientvewsion.
 *
 * - fow behaviowaw cwient events (bce), :3 use cwientpwatfowm t-to identify the cwient. σωσ
 * a-additionawwy, >w< b-bce contains a-an optionaw subsection to denote t-the ui component o-of
 * the cuwwent a-action. (ˆ ﻌ ˆ)♡ the c-cwienteventnamespace.component fiewd wiww be awways empty fow
 * b-bce nyamespace. ʘwʘ t-thewe is nyo stwaightfowawd 1-1 m-mapping between b-bce and wce nyamespace. :3
 */
s-stwuct cwienteventnamespace {
  1: optionaw stwing page(pewsonawdatatype = 'appusage')
  2: o-optionaw stwing section(pewsonawdatatype = 'appusage')
  3: optionaw stwing component(pewsonawdatatype = 'appusage')
  4: optionaw stwing ewement(pewsonawdatatype = 'appusage')
  5: o-optionaw stwing action(pewsonawdatatype = 'appusage')
  6: optionaw stwing subsection(pewsonawdatatype = 'appusage')
}(pewsisted='twue', (˘ω˘) h-haspewsonawdata='twue')

/*
 * m-metadata t-that is independent of a pawticuwaw (usew, 😳😳😳 i-item, action type) tupwe
 * a-and mostwy s-shawed acwoss usew action events. rawr x3
 */
stwuct eventmetadata {
  /* when the action happened accowding t-to nyanievew souwce we awe w-weading fwom */
  1: wequiwed i-i64 souwcetimestampms(pewsonawdatatype = 'pwivatetimestamp, (✿oωo) p-pubwictimestamp')
  /* when the action was weceived f-fow pwocessing i-intewnawwy 
   *  (compawe with s-souwcetimestampms f-fow deway)
   */
  2: wequiwed i64 weceivedtimestampms
  /* which souwce is this e-event dewived, (ˆ ﻌ ˆ)♡ e-e.g. ce, bce, :3 t-timewinefavs */
  3: wequiwed souwcewineage s-souwcewineage
  /* to b-be depwecated and wepwaced by w-wequestjoinid
   * usefuw fow joining with othew datasets
   * */
  4: optionaw i-i64 twaceid(pewsonawdatatype = 'tfetwansactionid')
  /*
   * t-this is the wanguage infewwed fwom t-the wequest of the u-usew action event (typicawwy usew's cuwwent cwient wanguage)
   * nyot the wanguage o-of any tweet, (U ᵕ U❁)
   * nyot the wanguage that usew sets in theiw pwofiwe!!!
   *
   *  - c-cwientevents && behaviowawcwientevents: cwient ui wanguage o-ow fwom gizmoduck w-which is nyani usew set in twittew app. ^^;;
   *      pwease s-see mowe at https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/finatwa-intewnaw/intewnationaw/swc/main/scawa/com/twittew/finatwa/intewnationaw/wanguageidentifiew.scawa
   *      the f-fowmat shouwd be iso 639-1. mya
   *  - sewvewtwsfavs: cwient ui w-wanguage, 😳😳😳 see mowe at http://go/wanguagepwiowity. OwO t-the fowmat shouwd be iso 639-1. rawr
   *  - sewvewtweetypieevents: uua sets this to n-nyone since thewe is nyo wequest w-wevew wanguage i-info.
   */
  5: optionaw stwing w-wanguage(pewsonawdatatype = 'infewwedwanguage')
  /*
   * this i-is the countwy i-infewwed fwom the w-wequest of the usew action event (typicawwy usew's c-cuwwent countwy c-code)
   * nyot the countwy of any tweet (by g-geo-tagging), XD
   * n-nyot the countwy s-set by the usew in theiw pwofiwe!!!
   *
   *  - c-cwientevents && behaviowawcwientevents: c-countwy code couwd b-be ip addwess (geoduck) ow
   *      usew wegistwation countwy (gizmoduck) a-and t-the fowmew takes p-pwecedence. (U ﹏ U)
   *      w-we don’t know exactwy w-which one is appwied, (˘ω˘) unfowtunatewy,
   *      see https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/finatwa-intewnaw/intewnationaw/swc/main/scawa/com/twittew/finatwa/intewnationaw/countwyidentifiew.scawa
   *      the fowmat shouwd be iso_3166-1_awpha-2. UwU
   *  - sewvewtwsfavs: f-fwom the wequest (usew’s cuwwent wocation), >_<
   *      s-see https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/swc/thwift/com/twittew/context/viewew.thwift?w54
   *      t-the fowmat shouwd be iso_3166-1_awpha-2. σωσ
   *  - s-sewvewtweetypieevents:
   *      uua sets t-this to be consistent w-with iesouwce t-to meet e-existing use wequiwement. 🥺
   *      s-see https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/swc/thwift/com/twittew/tweetypie/tweet.thwift?w1001. 🥺
   *      the definitions hewe confwicts with the intention of uua to wog the wequest countwy code
   *      w-wathew than t-the signup / g-geo-tagging countwy. ʘwʘ
   */
  6: optionaw stwing c-countwycode(pewsonawdatatype = 'infewwedcountwy')
  /* usefuw fow debugging cwient appwication w-wewated issues */
  7: o-optionaw i64 cwientappid(pewsonawdatatype = 'appid')
  /* u-usefuw fow debugging cwient appwication wewated i-issues */
  8: o-optionaw stwing cwientvewsion(pewsonawdatatype = 'cwientvewsion')
  /* u-usefuw fow f-fiwtewing */
  9: optionaw cwienteventnamespace cwienteventnamespace
  /*
   * this fiewd is onwy popuwated in b-behaviowaw cwient e-events (bce). :3
   *
   * t-the cwient p-pwatfowm such a-as one of ["iphone", (U ﹏ U) "ipad", "mac", (U ﹏ U) "andwoid", "web"]
   * thewe can be muwtipwe c-cwientappids f-fow the same pwatfowm. ʘwʘ
   */
  10: optionaw stwing c-cwientpwatfowm(pewsonawdatatype = 'cwienttype')
  /*
   * this f-fiewd is onwy popuwated in behaviowaw c-cwient events (bce). >w<
   *
   * the cuwwent u-ui hiewawchy infowmation with h-human weadabwe w-wabews. rawr x3
   * fow exampwe, OwO [home,timewine,tweet] o-ow [tab_baw,home,scwowwabwe_content,tweet]
   *
   * fow mowe detaiws see https://docs.googwe.com/document/d/16cdswpsmuud17yofh9min3nwbqdvawx4dazoiqsfchi/edit#heading=h.uv3md49i0j4j
   */
  11: o-optionaw wist<stwing> v-viewhiewawchy(pewsonawdatatype = 'websitepage')
  /*
   * t-this fiewd is onwy popuwated in behaviowaw cwient events (bce). ^•ﻌ•^
   *
   * t-the sequence of views (bweadcwumb) that was intewacted w-with that caused t-the usew to nyavigate to
   * t-the cuwwent pwoduct suwface (e.g. >_< p-pwofiwe page) w-whewe an action was taken. OwO
   *
   * the bweadcwumb i-infowmation may onwy be pwesent fow cewtain p-pweceding pwoduct s-suwfaces (e.g. >_< home timewine). (ꈍᴗꈍ)
   * s-see mowe detaiws in https://docs.googwe.com/document/d/16cdswpsmuud17yofh9min3nwbqdvawx4dazoiqsfchi/edit#heading=h.nb7tnjwhqxpm
   */
  12: o-optionaw wist<stwing> b-bweadcwumbviews(pewsonawdatatype = 'websitepage')
  /*
   * t-this fiewd is onwy popuwated in behaviowaw cwient events (bce). >w<
   *
   * the sequence of tweets (bweadcwumb) that was intewacted with that caused the usew to nyavigate to
   * cuwwent pwoduct suwface (e.g. (U ﹏ U) pwofiwe page) w-whewe an action w-was taken. ^^
   *
   * the bweadcwumb infowmation m-may onwy be p-pwesent fow cewtain p-pweceding pwoduct suwfaces (e.g. (U ﹏ U) h-home timewine).
   * see mowe d-detaiws in https://docs.googwe.com/document/d/16cdswpsmuud17yofh9min3nwbqdvawx4dazoiqsfchi/edit#heading=h.nb7tnjwhqxpm
   */
   13: o-optionaw wist<bweadcwumbtweet> b-bweadcwumbtweets(pewsonawdatatype = 'tweetid')
  /*
    * a wequest join i-id is cweated by b-backend sewvices and bwoadcasted in subsequent c-cawws
    * to othew d-downstweam s-sewvices as pawt o-of the wequest p-path. :3 the wequestjoinid i-is wogged
    * i-in sewvew w-wogs and scwibed i-in cwient events, (✿oωo) enabwing joins a-acwoss cwient a-and sewvew
    * a-as weww as within a given wequest a-acwoss backend sewvews. XD see go/joinkey-tdd f-fow mowe
    * detaiws. >w<
    */
   14: optionaw i64 w-wequestjoinid(pewsonawdatatype = 'twansactionid')
   15: o-optionaw i-i64 cwienteventtwiggewedon
}(pewsisted='twue', òωó haspewsonawdata='twue')

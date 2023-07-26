package com.twittew.cw_mixew.modew

impowt com.twittew.cw_mixew.thwiftscawa.simiwawityenginetype
i-impowt com.twittew.cw_mixew.thwiftscawa.souwcetype
i-impowt com.twittew.simcwustews_v2.common.usewid
i-impowt com.twittew.simcwustews_v2.thwiftscawa.intewnawid
i-impowt c-com.twittew.utiw.time

/***
 * t-tweet-wevew attwibutes. nyaa~~ w-wepwesents t-the souwce used in candidate genewation
 * due to wegacy weason, 😳 souwcetype u-used to wepwesent both souwcetype and simiwawityenginetype
 * moving f-fowwawd, (⑅˘꒳˘) souwcetype wiww be u-used fow souwcetype onwy. nyaa~~ eg., tweetfavowite, OwO usewfowwow, rawr x3 twiceusewid
 * a-at the same time, XD we c-cweate a nyew simiwawityenginetype t-to sepawate them. σωσ eg., simcwustewsann
 *
 * cuwwentwy, (U ᵕ U❁) one speciaw case is that we have twiceusewid a-as a souwce, (U ﹏ U) which is nyot nyecessawiwy a "signaw"
 * @pawam souwcetype, :3 e.g., souwcetype.tweetfavowite, ( ͡o ω ͡o ) s-souwcetype.usewfowwow, σωσ souwcetype.twiceusewid
 * @pawam i-intewnawid, >w< e-e.g., usewid(0w), 😳😳😳 t-tweetid(0w)
 */
c-case cwass souwceinfo(
  souwcetype: souwcetype, OwO
  i-intewnawid: intewnawid, 😳
  souwceeventtime: o-option[time])

/***
 * tweet-wevew attwibutes. 😳😳😳 wepwesents the souwce usew gwaph used in candidate g-genewation
 * it is an intewmediate p-pwoduct, (˘ω˘) a-and wiww nyot b-be stowed, unwike souwceinfo. ʘwʘ
 * essentiawwy, ( ͡o ω ͡o ) cwmixew quewies a g-gwaph, o.O and the g-gwaph wetuwns a wist of usews to b-be used as souwces. >w<
 * f-fow instance, 😳 weawgwaph, e-eawwybiwd, 🥺 fws, stp, rawr x3 etc. the undewwying s-simiwawity engines such as
 * utg ow uteg w-wiww wevewage these souwces t-to buiwd candidates. o.O
 *
 * we extended t-the definition o-of souwcetype to covew both "souwce signaw" and "souwce gwaph"
 * see [cwmixew] gwaph based souwce fetchew a-abstwaction pwoposaw:
 *
 * c-considew making both s-souwceinfo and g-gwaphsouwceinfo e-extends the same twait to
 * have a unified intewface. rawr
 */
case c-cwass gwaphsouwceinfo(
  souwcetype: souwcetype, ʘwʘ
  seedwithscowes: map[usewid, 😳😳😳 d-doubwe])

/***
 * tweet-wevew attwibutes. w-wepwesents t-the simiwawity e-engine (the awgowithm) used f-fow
 * candidate g-genewation awong w-with theiw metadata. ^^;;
 * @pawam s-simiwawityenginetype, o.O e.g., (///ˬ///✿) simcwustewsann, σωσ usewtweetgwaph
 * @pawam m-modewid. nyaa~~ e.g., u-usewtweetgwaphconsumewembedding_aww_20210708
 * @pawam s-scowe - a-a scowe genewated b-by this sim engine
 */
case cwass simiwawityengineinfo(
  simiwawityenginetype: s-simiwawityenginetype, ^^;;
  modewid: option[stwing], ^•ﻌ•^ // modewid can be a nyone. σωσ e.g., uteg, unifiedtweetbasedse. -.- e-etc
  scowe: option[doubwe])

/****
 * tweet-wevew attwibutes. ^^;; a-a combination f-fow both souwceinfo a-and simiwawityengineinfo
 * simiwawityengine i-is a composition, XD and it can be c-composed by many w-weaf simiwawity engines. 🥺
 * fow instance, òωó the tweetbasedunified se couwd be a composition of both u-usewtweetgwaph se, (ˆ ﻌ ˆ)♡ simcwustewsann s-se. -.-
 * nyote that a simiwawityengine (composite) m-may caww o-othew simiwawityengines (atomic, :3 contwibuting)
 * to contwibute t-to its finaw candidate w-wist. ʘwʘ we twack these contwibuting s-ses in t-the contwibutingsimiwawityengines wist
 *
 * @pawam souwceinfoopt - this is optionaw as many consumewbased c-cg does n-nyot have a souwce
 * @pawam s-simiwawityengineinfo - the simiwawity e-engine used i-in candidate genewation (eg., tweetbasedunifiedse). 🥺 i-it can be an atomic se ow an composite se
 * @pawam contwibutingsimiwawityengines - onwy composite s-se wiww h-have it (e.g., sannn, >_< utg). othewwise it is an e-empty seq. ʘwʘ aww contwibuting s-ses mst be atomic
 */
case cwass candidategenewationinfo(
  souwceinfoopt: o-option[souwceinfo], (˘ω˘)
  simiwawityengineinfo: simiwawityengineinfo, (✿oωo)
  contwibutingsimiwawityengines: seq[simiwawityengineinfo])

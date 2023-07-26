namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

i-incwude "com/twittew/cwientapp/gen/cwient_app.thwift"
i-incwude "com/twittew/wepowtfwow/wepowt_fwow_wogs.thwift"
i-incwude "com/twittew/sociawgwaph/sociaw_gwaph_sewvice_wwite_wog.thwift"
i-incwude "com/twittew/gizmoduck/usew_sewvice.thwift"

/*
 * a-actiontype is typicawwy a-a thwee p-pawt enum consisting of
 * [owigin][item type][action nyame]
 *
 * [owigin] is usuawwy "cwient" o-ow "sewvew" to indicate how the action was dewived. :3
 *
 * [item t-type] is singuwaw and wefews to t-the showthand vewsion of the type of
 * item (e.g. òωó tweet, pwofiwe, σωσ n-nyotification instead of tweetinfo, σωσ p-pwofiweinfo, (⑅˘꒳˘) n-nyotificationinfo)
 * the action occuwwed on. 🥺 action types and item types shouwd b-be 1:1, (U ﹏ U) and when an action can be
 * pewfowmed on muwtipwe types of items, >w< c-considew gwanuwaw action types.
 *
 * [action n-nyame] i-is the descwiptive n-nyame of t-the usew action (e.g. nyaa~~ favowite, -.- wendew impwession);
 * a-action nyames shouwd cowwespond to ui actions / m-mw wabews (which awe typicawwy based on usew
 * behaviow fwom ui actions)
 *
 * bewow awe g-guidewines awound nyaming of action t-types:
 * a-a) when an action i-is coupwed to a pwoduct suwface, XD be concise in nyaming such that t-the
 * combination o-of item type and action nyame c-captuwes the u-usew behaviow fow the action in t-the ui. -.- fow exampwe, >w<
 * fow an o-open on a nyotification in the pushnotification pwoduct suwface t-that is pawsed fwom cwient events, (ꈍᴗꈍ)
 * c-considew cwientnotificationopen because the i-item nyotification a-and the action nyame open concisewy wepwesent
 * the action, :3 and the pwoduct suwface pushnotification can be i-identified independentwy. (ˆ ﻌ ˆ)♡
 *
 * b-b) it is ok to use genewic nyames w-wike cwick if n-nyeeded to distinguish f-fwom anothew action ow
 * it is the best way to chawactewize a-an action concisewy without confusion. -.-
 * fow exampwe, mya fow cwienttweetcwickwepwy, (˘ω˘) t-this wefews to actuawwy c-cwicking on the w-wepwy button but n-nyot
 * wepwying, ^•ﻌ•^ and it is ok t-to incwude cwick. a-anothew exampwe i-is cwick on a t-tweet anywhewe (othew than the fav, 😳😳😳
 * wepwy, σωσ etc. b-buttons), ( ͡o ω ͡o ) which w-weads to the t-tweetdetaiws page. nyaa~~ a-avoid genewic a-action nyames wike cwick if
 * thewe is a mowe specific ui aspect t-to wefewence and cwick is impwied, :3 e.g. cwienttweetwepowt is
 * pwefewwed ovew cwienttweetcwickwepowt a-and cwienttweetwepowtcwick. (✿oωo)
 *
 * c) wewy on vewsioning found in the owigin w-when it is p-pwesent fow action n-nyames. >_< fow exampwe,
 * a "v2impwession" i-is nyamed as such because i-in behaviowaw c-cwient events, ^^ thewe is
 * a "v2impwess" fiewd. (///ˬ///✿) see go/bce-v2impwess fow mowe detaiws. :3
 *
 * d-d) thewe is a distinction between "undoaction" a-and "un{action}" action types. :3
 * a-an "undoaction" i-is fiwed when a usew cwicks on the expwicit "undo" b-button, (ˆ ﻌ ˆ)♡ aftew t-they pewfowm an action
 * this "undo" b-button i-is a ui ewement that may be tempowawy, 🥺 e.g.,
 *  - the usew waited too wong to cwick t-the button, 😳 t-the button disappeaws f-fwom the ui (e.g., undo fow m-mute, (ꈍᴗꈍ) bwock)
 *  - t-the button does nyot disappeaw d-due to timeout, mya but becomes unavaiwabwe aftew the usew cwoses a tab
 *    (e.g, rawr u-undo fow nyotintewestedin, ʘwʘ n-nyotabouttopic)
 * exampwes:
    - cwientpwofiweundomute: a-a usew c-cwicks the "undo" button aftew muting a pwofiwe
    - cwienttweetundonotintewestedin: a-a usews cwicks the "undo" button
      aftew cwicking "not intewested in t-this tweet" button in the cawet menu of a tweet
 * a-an "un{action}" i-is fiwed when a usew wevewses a pwevious action, -.- not by expwicitwy c-cwicking an "undo" b-button, UwU
 * but thwough some othew action that awwows them t-to wevewt. :3
 * exampwes:
 *  - c-cwientpwofiweunmute: a usew cwicks the "unmute" button fwom the c-cawet menu of the pwofiwe they p-pweviouswy muted
 *  - c-cwienttweetunfav: a usew u-unwikes a tweet by cwicking on wike b-button again
 *
 * e-exampwes: s-sewvewtweetfav, 😳 cwienttweetwendewimpwession, (ꈍᴗꈍ) c-cwientnotificationseewessoften
 *
 * s-see go/uua-action-type fow mowe detaiws.
 */
e-enum actiontype {
  // 0 - 999 used f-fow actions d-dewived fwom sewvew-side souwces (e.g. mya timewinesewvice, nyaa~~ t-tweetypie)
  // nyote: pwease m-match vawues f-fow cowwesponding sewvew / cwient enum membews (with offset 1000). o.O
  s-sewvewtweetfav   = 0
  sewvewtweetunfav = 1
  // w-wesewve 2 a-and 3 fow sewvewtweetwingewimpwession a-and sewvewtweetwendewimpwession

  sewvewtweetcweate = 4
  s-sewvewtweetwepwy = 5
  sewvewtweetquote = 6
  sewvewtweetwetweet = 7
  // skip 8-10 since thewe awe nyo sewvew e-equivawents fow cwickcweate, òωó c-cwickwepwy, ^•ﻌ•^ cwickquote
  // wesewve 11-16 f-fow sewvew video engagements

  s-sewvewtweetdewete = 17      // usew dewetes a-a defauwt t-tweet
  sewvewtweetunwepwy = 18     // u-usew dewetes a-a wepwy tweet
  s-sewvewtweetunquote = 19     // usew dewetes a quote tweet
  sewvewtweetunwetweet = 20   // usew wemoves an existing wetweet
  // usew edits a-a tweet. (˘ω˘) edit wiww c-cweate a nyew t-tweet with editedtweetid = id of t-the owiginaw tweet
  // the owiginaw tweet ow the nyew tweet fwom e-edit can onwy b-be a defauwt ow quote tweet. òωó
  // a-a usew can edit a defauwt tweet to become a q-quote tweet (by a-adding the wink to anothew tweet), mya
  // o-ow edit a-a quote tweet to wemove the quote and make it a defauwt tweet. ^^
  // both the initiaw t-tweet and the n-nyew tweet cweated f-fwom the edit c-can be edited, rawr a-and each time the
  // nyew edit w-wiww cweate a-a nyew tweet. >_< aww subsequent edits w-wouwd have the s-same initiaw tweet id
  // as t-the tweetinfo.editedtweetid. (U ᵕ U❁)
  // e.g. cweate tweet a, /(^•ω•^) edit tweet a-a -> tweet b, mya edit tweet b -> t-tweet c
  // initiaw t-tweet id fow both tweet b anc t-tweet c wouwd be tweet a
  sewvewtweetedit = 21
  // skip 22 f-fow dewete an edit i-if we want to a-add it in the futuwe

  // wesewve 30-40 fow sewvew topic actions

  // 41-70 wesewved f-fow aww nyegative engagements and the wewated p-positive engagements
  // f-fow exampwe, OwO fowwow and unfowwow, UwU m-mute and unmute
  // this is fiwed w-when a usew c-cwick "submit" at the end of a "wepowt tweet" fwow
  // c-cwienttweetwepowt = 1041 is scwibed by heawthcwient team, 🥺 o-on the cwient s-side
  // this is scwibed by spamacaw, (✿oωo) o-on the sewvew side
  // t-they can be joined o-on wepowtfwowid
  // s-see https://confwuence.twittew.biz/pages/viewpage.action?spacekey=heawth&titwe=undewstanding+wepowtdetaiws
  sewvewtweetwepowt = 41

  // wesewve 42 fow sewvewtweetnotintewestedin
  // wesewve 43 fow sewvewtweetundonotintewestedin
  // wesewve 44 fow sewvewtweetnotabouttopic
  // wesewve 45 fow sewvewtweetundonotabouttopic

  sewvewpwofiwefowwow = 50       // usew fowwows a pwofiwe
  sewvewpwofiweunfowwow = 51     // u-usew u-unfowwows a pwofiwe
  sewvewpwofiwebwock = 52        // usew bwocks a-a pwofiwe
  s-sewvewpwofiweunbwock = 53      // u-usew unbwocks a pwofiwe
  sewvewpwofiwemute = 54         // u-usew mutes a pwofiwe
  sewvewpwofiweunmute = 55       // u-usew unmutes a-a pwofiwe
  // usew wepowts a-a pwofiwe as spam / abuse
  // t-this usew action t-type incwudes pwofiwewepowtasspam and pwofiwewepowtasabuse
  sewvewpwofiwewepowt = 56
  // w-wesewve 57 f-fow sewvewpwofiweunwepowt
  // w-wesewve 56-70 f-fow sewvew s-sociaw gwaph actions

  // 71-90 w-wesewved fow cwick-based e-events
  // w-wesewve 71 f-fow sewvewtweetcwick

  // 1000 - 1999 used fow a-actions dewived f-fwom cwient-side s-souwces (e.g. rawr cwient events, rawr bce)
  // n-nyote: pwease match vawues fow cowwesponding s-sewvew / cwient enum membews (with o-offset 1000). ( ͡o ω ͡o )
  // 1000 - 1499 u-used fow w-wegacy cwient events
  cwienttweetfav = 1000
  c-cwienttweetunfav = 1001
  cwienttweetwingewimpwession = 1002
  // p-pwease nyote that: wendew impwession f-fow quoted tweets wouwd emit 2 e-events:
  // 1 fow the quoting tweet and 1 fow the owiginaw tweet!!!
  cwienttweetwendewimpwession = 1003
  // 1004 w-wesewved fow cwienttweetcweate
  // t-this i-is "send wepwy" event to indicate pubwishing of a wepwy tweet a-as opposed to cwicking
  // on t-the wepwy button t-to initiate a wepwy t-tweet (captuwed in cwienttweetcwickwepwy). /(^•ω•^)
  // the diffewences b-between this a-and the sewvewtweetwepwy awe:
  // 1) s-sewvewtweetwepwy awweady has the nyew tweet i-id 2) a sent wepwy may be wost d-duwing twansfew
  // o-ovew the w-wiwe and thus may nyot end up with a-a fowwow-up s-sewvewtweetwepwy.
  c-cwienttweetwepwy = 1005
  // t-this is the "send quote" event t-to indicate pubwishing o-of a quote t-tweet as opposed t-to cwicking
  // o-on the quote b-button to initiate a-a quote tweet (captuwed i-in cwienttweetcwickquote). -.-
  // the d-diffewences between this and the s-sewvewtweetquote awe:
  // 1) sewvewtweetquote a-awweady has the n-new tweet id 2) a-a sent quote may be wost duwing twansfew
  // ovew the wiwe and t-thus may nyot end u-up with a fowwow-up s-sewvewtweetquote. >w<
  cwienttweetquote = 1006
  // this is the "wetweet" event t-to indicate pubwishing o-of a wetweet. ( ͡o ω ͡o )
  cwienttweetwetweet = 1007
  // 1008 w-wesewved f-fow cwienttweetcwickcweate
  // this is usew cwicking on the wepwy button n-not actuawwy sending a-a wepwy tweet, (˘ω˘)
  // t-thus the n-nyame cwickwepwy
  cwienttweetcwickwepwy = 1009
  // this is u-usew cwicking the q-quote/wetweetwithcomment button not actuawwy sending t-the quote, /(^•ω•^)
  // thus the nyame cwickquote
  c-cwienttweetcwickquote = 1010

  // 1011 - 1016: wefew to go/cme-scwibing a-and g-go/intewaction-event-spec fow detaiws
  // t-this i-is fiwed when pwayback weaches 25% o-of totaw twack duwation. nyot v-vawid fow wive v-videos.
  // fow w-wooping pwayback, (˘ω˘) t-this is onwy fiwed once and does n-not weset at w-woop boundawies. o.O
  c-cwienttweetvideopwayback25 = 1011
  // this i-is fiwed when pwayback weaches 50% of totaw twack d-duwation. nyaa~~ nyot v-vawid fow wive v-videos. :3
  // fow wooping pwayback, (///ˬ///✿) this is onwy fiwed once and does nyot weset at w-woop boundawies. (U ﹏ U)
  cwienttweetvideopwayback50 = 1012
  // t-this i-is fiwed when pwayback weaches 75% of totaw twack d-duwation. o.O nyot vawid fow wive v-videos. ^^;;
  // fow w-wooping pwayback, ʘwʘ t-this is onwy f-fiwed once and d-does nyot weset at woop boundawies.
  cwienttweetvideopwayback75 = 1013
  // this is fiwed when p-pwayback weaches 95% of totaw twack d-duwation. (///ˬ///✿) nyot vawid fow wive videos. σωσ
  // fow wooping pwayback, ^^;; t-this is onwy fiwed once and does nyot weset at woop boundawies. UwU
  cwienttweetvideopwayback95 = 1014
  // t-this i-if fiwed when the video has been p-pwayed in nyon-pweview
  // (i.e. mya nyot autopwaying in the timewine) m-mode, ^•ﻌ•^ and w-was nyot stawted via auto-advance. (⑅˘꒳˘)
  // f-fow wooping pwayback, nyaa~~ t-this is onwy fiwed once and does nyot weset at woop boundawies.
  c-cwienttweetvideopwayfwomtap = 1015
  // this is fiwed when 50% o-of the video has b-been on-scween a-and pwaying fow 10 consecutive seconds
  // ow 95% o-of the video duwation, ^^;; whichevew comes fiwst. 🥺
  // fow wooping pwayback, ^^;; this i-is onwy fiwed o-once and does not w-weset at woop b-boundawies. nyaa~~
  cwienttweetvideoquawityview = 1016
  // fiwed when eithew view_thweshowd o-ow pway_fwom_tap i-is fiwed. 🥺
  // fow wooping pwayback, (ˆ ﻌ ˆ)♡ this i-is onwy fiwed once and does not weset at woop b-boundawies. ( ͡o ω ͡o )
  cwienttweetvideoview = 1109
  // fiwed when 50% of the video has been o-on-scween and p-pwaying fow 2 consecutive seconds, nyaa~~
  // w-wegawdwess o-of video duwation. ( ͡o ω ͡o )
  // f-fow wooping pwayback, ^^;; this is onwy f-fiwed once and does nyot weset at woop boundawies. rawr x3
  c-cwienttweetvideomwcview = 1110
  // fiwed when the video is:
  // - pwaying f-fow 3 cumuwative (not n-nyecessawiwy c-consecutive) s-seconds with 100% i-in view fow wooping video.
  // - p-pwaying fow 3 cumuwative (not nyecessawiwy c-consecutive) seconds ow the video d-duwation, ^^;; whichevew comes fiwst, ^•ﻌ•^ with 100% in v-view fow nyon-wooping v-video. 🥺
  // fow wooping pwayback, (ꈍᴗꈍ) t-this is onwy fiwed once a-and does nyot weset a-at woop boundawies. ^•ﻌ•^
  cwienttweetvideoviewthweshowd = 1111
  // f-fiwed when the u-usew cwicks a genewic ‘visit u-uww’ caww to action. :3
  cwienttweetvideoctauwwcwick = 1112
  // fiwed when the usew cwicks a ‘watch n-nyow’ caww to action. (˘ω˘)
  c-cwienttweetvideoctawatchcwick = 1113

  // 1017 wesewved fow cwienttweetdewete
  // 1018-1019 f-fow cwient dewete a-a wepwy and dewete a-a quote if we want to add t-them in the futuwe

  // t-this is fiwed when a usew c-cwicks on "undo wetweet" aftew w-we-tweeting a tweet
  cwienttweetunwetweet = 1020
  // 1021 w-wesewved f-fow cwienttweetedit
  // 1022 wesewved fow cwient dewete an edit if we want to add it in t-the futuwe
  // t-this is fiwed when a usew cwicks on a photo within a tweet and the p-photo expands to fit
  // the s-scween. ^^
  cwienttweetphotoexpand = 1023

  // this i-is fiwed when a usew cwicks on a pwofiwe mention inside a tweet. /(^•ω•^)
  cwienttweetcwickmentionscweenname = 1024

  // 1030 - 1035 f-fow topic actions
  // thewe awe muwtipwe cases:
  // 1. σωσ f-fowwow fwom the topic p-page (ow so-cawwed w-wanding page)
  // 2. òωó cwick o-on tweet's cawet m-menu of "fowwow (the t-topic)", >w< it n-nyeeds to be:
  //    1) u-usew f-fowwows the topic awweady (othewwise thewe is nyo "fowwow" menu by defauwt), (˘ω˘)
  //    2) and cwicked o-on the "unfowwow t-topic" fiwst. ^•ﻌ•^
  c-cwienttopicfowwow = 1030
  // t-thewe awe muwtipwe c-cases:
  // 1. >_< u-unfowwow fwom the topic page (ow so-cawwed wanding page)
  // 2. -.- cwick on tweet's c-cawet menu o-of "unfowwow (the topic)" if the usew has awweady fowwowed
  //    t-the topic. òωó
  c-cwienttopicunfowwow = 1031
  // t-this is fiwed when the usew cwicks the "x" icon n-nyext to the topic on theiw timewine, ( ͡o ω ͡o )
  // and c-cwicks "not intewested i-in {topic}" in the pop-up pwompt
  // awtewnativewy, (ˆ ﻌ ˆ)♡ t-they can awso cwick "see m-mowe" button t-to visit the topic page, :3 and c-cwick "not intewested" t-thewe.
  c-cwienttopicnotintewestedin = 1032
  // t-this is fiwed w-when the usew c-cwicks the "undo" button aftew c-cwicking "x" ow "not i-intewested" on a topic
  // w-which is captuwed in cwienttopicnotintewestedin
  cwienttopicundonotintewestedin = 1033

  // 1036-1070 w-wesewved fow aww nyegative e-engagements and the wewated p-positive engagements
  // f-fow exampwe, ^•ﻌ•^ fowwow and unfowwow, ( ͡o ω ͡o ) mute a-and unmute

  // this is fiwed when a usew cwicks o-on  "this tweet's n-nyot hewpfuw" fwow in the cawet menu
  // o-of a tweet wesuwt o-on the seawch wesuwts page
  c-cwienttweetnothewpfuw = 1036
  // this is fiwed when a usew cwicks u-undo aftew cwicking o-on
  // "this tweet's nyot h-hewpfuw" fwow i-in the cawet menu of a tweet wesuwt on the seawch w-wesuwts page
  c-cwienttweetundonothewpfuw = 1037
  // t-this is fiwed w-when a usew stawts and/ow compwetes the "wepowt tweet" fwow in the cawet menu of a tweet
  cwienttweetwepowt = 1041
  /*
   * 1042-1045 w-wefews t-to actions that a-awe wewated t-to the
   * "not i-intewested in" b-button in the cawet menu of a tweet. ^•ﻌ•^
   *
   * cwienttweetnotintewestedin i-is fiwed w-when a usew cwicks the
   * "not i-intewested in t-this tweet" button in the cawet menu of a tweet. ʘwʘ
   * a-a usew can undo the cwienttweetnotintewestedin action by c-cwicking the
   * "undo" button t-that appeaws as a-a pwompt in the cawet menu, :3 wesuwting
   * i-in cwienttweetundonotintewestedin b-being f-fiwed. >_<
   * if a usew chooses t-to nyot undo and p-pwoceed, rawr they awe given muwtipwe c-choices
   * in a pwompt to b-bettew document w-why they awe nyot i-intewested in a tweet. 🥺
   * fow e-exampwe, (✿oωo) if a tweet is nyot about a topic, (U ﹏ U) a usew c-can cwick
   * "this tweet is nyot about {topic}" in the pwovided pwompt, rawr x3 wesuwting in
   * in cwienttweetnotabouttopic b-being fiwed. (✿oωo)
   * a usew can undo the cwienttweetnotabouttopic action by cwicking the "undo"
   * button t-that appeaws as a subsequent pwompt in the c-cawet menu. (U ᵕ U❁) undoing this action
   * w-wesuwts in the pwevious ui state, -.- whewe the u-usew had onwy mawked "not intewested i-in" and
   * can stiww undo t-the owiginaw cwienttweetnotintewestedin a-action. /(^•ω•^)
   * simiwawwy a usew can sewect "this t-tweet isn't wecent" action wesuwting in cwienttweetnotwecent
   * a-and he couwd undo this a-action immediatewy which wesuwts i-in cwienttweetundonotwecent
   * simiwawwy a u-usew can sewect "show f-fewew tweets fwom" action wesuwting in cwienttweetseefewew
   * a-and he couwd undo this action immediatewy w-which wesuwts in cwienttweetundoseefewew
   */
  cwienttweetnotintewestedin = 1042
  cwienttweetundonotintewestedin = 1043
  cwienttweetnotabouttopic = 1044
  cwienttweetundonotabouttopic = 1045
  c-cwienttweetnotwecent = 1046
  c-cwienttweetundonotwecent = 1047
  cwienttweetseefewew = 1048
  c-cwienttweetundoseefewew = 1049

  // t-this is fiwed when a usew f-fowwows a pwofiwe fwom the
  // pwofiwe page headew / peopwe moduwe and peopwe t-tab on the seawch w-wesuwts page / sidebaw on the h-home page
  // a p-pwofiwe can awso be fowwowed when a-a usew cwicks fowwow in the cawet menu of a tweet
  // o-ow fowwow button on hovewing on pwofiwe a-avataw, OwO which i-is captuwed in cwienttweetfowwowauthow = 1060
  cwientpwofiwefowwow = 1050
  // wesewve 1050/1051 f-fow cwient side fowwow/unfowwow
  // this is fiwed when a usew cwicks bwock in a pwofiwe page
  // a pwofiwe can awso be bwocked w-when a usew cwicks b-bwock in the cawet menu of a-a tweet, rawr x3
  // which i-is captuwed in cwienttweetbwockauthow = 1062
  c-cwientpwofiwebwock = 1052
  // this is fiwed when a usew cwicks unbwock in a pop-up pwompt wight aftew bwocking a-a pwofiwe
  // in the pwofiwe page ow cwicks unbwock in a dwop-down menu in t-the pwofiwe page. σωσ
  c-cwientpwofiweunbwock = 1053
  // t-this is fiwed when a usew cwicks mute in a pwofiwe page
  // a-a pwofiwe can a-awso be muted when a-a usew cwicks mute in the cawet m-menu of a tweet, which is captuwed i-in cwienttweetmuteauthow = 1064
  cwientpwofiwemute = 1054
  // w-wesewve 1055 fow cwient side u-unmute
  // this is fiwed when a usew cwicks "wepowt u-usew" action fwom usew pwofiwe p-page
  cwientpwofiwewepowt = 1056

  // wesewve 1057 f-fow cwientpwofiweunwepowt

  // t-this i-is fiwed when a usew cwicks on a-a pwofiwe fwom aww moduwes except t-tweets
  // (eg: peopwe seawch / p-peopwe moduwe i-in top tab in seawch wesuwt page
  // fow tweets, ʘwʘ t-the cwick is captuwed in cwienttweetcwickpwofiwe
  cwientpwofiwecwick = 1058
  // wesewve 1059-1070 fow cwient sociaw gwaph actions

  // this is fiwed when a-a usew cwicks fowwow in the cawet menu of a tweet o-ow hovews on the avataw of the t-tweet
  // authow and cwicks on the fowwow button. a-a pwofiwe can awso be fowwowed by cwicking the f-fowwow button on the
  // pwofiwe page and confiwm, -.- w-which is captuwed in cwientpwofiwefowwow. 😳 the event emits t-two items, one of usew type
  // and anothew of t-tweet type, 😳😳😳 since t-the defauwt impwementation of basecwientevent o-onwy wooks fow t-tweet type, OwO
  // the othew item i-is dwopped which i-is the expected behaviouw
  cwienttweetfowwowauthow = 1060

  // this is fiwed w-when a usew cwicks unfowwow in the cawet menu of a tweet ow hovews o-on the avataw of the tweet
  // authow and cwicks on the unfowwow b-button. ^•ﻌ•^ a pwofiwe c-can awso b-be unfowwowed by cwicking the unfowwow button on the
  // pwofiwe p-page and confiwm, which wiww be c-captuwed in cwientpwofiweunfowwow. rawr the event emits t-two items, o-one of usew type
  // and anothew of tweet type, (✿oωo) since the defauwt impwementation of basecwientevent o-onwy wooks f-fow tweet type, ^^
  // the othew item is dwopped which i-is the expected behaviouw
  cwienttweetunfowwowauthow = 1061

  // t-this is f-fiwed when a usew c-cwicks bwock in t-the menu of a t-tweet to bwock the p-pwofiwe that
  // authowed this tweet. -.- a pwofiwe c-can awso be b-bwocked in the pwofiwe p-page, (✿oωo) which i-is captuwed
  // i-in cwientpwofiwebwock = 1052
  c-cwienttweetbwockauthow = 1062
  // this is fiwed w-when a usew c-cwicks unbwock in a-a pop-up pwompt wight aftew bwocking an authow
  // i-in the dwop-down menu of a tweet
  cwienttweetunbwockauthow = 1063

  // this i-is fiwed when a usew cwicks mute in the menu o-of a tweet to bwock t-the pwofiwe that
  // authowed this tweet. o.O a pwofiwe can awso b-be muted in the p-pwofiwe page, :3 which is captuwed i-in cwientpwofiwemute = 1054
  c-cwienttweetmuteauthow = 1064

  // wesewve 1065 fow cwienttweetunmuteauthow

  // 1071-1090 wesewved f-fow cwick-based e-events
  // cwick-based events awe defined a-as cwicks on a u-ui containew (e.g., tweet, rawr x3 pwofiwe, (U ᵕ U❁) etc.), as opposed t-to cweawwy nyamed
  // button ow menu (e.g., fowwow, :3 bwock, 🥺 wepowt, etc.), XD which wequiwes a-a specific action nyame than "cwick". >_<

  // this i-is fiwed when a u-usew cwicks on a-a tweet to open the tweet detaiws p-page. (ꈍᴗꈍ) nyote that f-fow
  // tweets i-in the nyotification t-tab pwoduct s-suwface, ( ͡o ω ͡o ) a cwick can be wegistewed diffewentwy
  // d-depending o-on whethew the t-tweet is a wendewed tweet (a cwick w-wesuwts in cwienttweetcwick)
  // o-ow a wwappew n-nyotification (a cwick wesuwts i-in cwientnotificationcwick). (˘ω˘)
  c-cwienttweetcwick = 1071
  // t-this i-is fiwed when a-a usew cwicks to view the pwofiwe p-page of a usew fwom a tweet
  // c-contains a tweetinfo o-of this tweet
  cwienttweetcwickpwofiwe = 1072
  // this is fiwed when a-a usew cwicks on t-the "shawe" icon on a tweet to o-open the shawe menu. (˘ω˘)
  // t-the usew may ow may nyot pwoceed and finish s-shawing the t-tweet. UwU
  cwienttweetcwickshawe = 1073
  // t-this i-is fiwed when a-a usew cwicks "copy w-wink to tweet" in a menu appeawed aftew hitting
  // t-the "shawe" icon on a tweet ow when a usew sewects shawe_via -> copy_wink a-aftew wong-cwick
  // a-a wink inside a tweet on a mobiwe device
  cwienttweetshaweviacopywink = 1074
  // t-this i-is fiwed when a usew cwicks "send via diwect message" a-aftew
  // cwicking on the "shawe" i-icon on a-a tweet to open t-the shawe menu. (ˆ ﻌ ˆ)♡
  // the usew may ow may nyot pwoceed and finish s-sending the dm. (///ˬ///✿)
  cwienttweetcwicksendviadiwectmessage = 1075
  // t-this is fiwed when a usew c-cwicks "bookmawk" aftew
  // cwicking on the "shawe" i-icon on a tweet to open the s-shawe menu. (ꈍᴗꈍ)
  cwienttweetshaweviabookmawk = 1076
  // this is fiwed when a usew c-cwicks "wemove tweet fwom bookmawks" a-aftew
  // cwicking on the "shawe" icon on a tweet to open the shawe menu. -.-
  cwienttweetunbookmawk = 1077
   // this is fiwed w-when a usew c-cwicks on the hashtag i-in a tweet. 😳😳😳
  // t-the cwick on hashtag in "nani's happening" s-section gives you othew scwibe '*:*:sidebaw:*:twend:seawch'
  // cuwwenwy we awe onwy fiwtewing f-fow itemtype=tweet. (///ˬ///✿) t-thewe awe o-othew items pwesent i-in the event whewe itemtype = usew
  // but those items awe in duaw-events (events w-with muwtipwe i-itemtypes) and happen when you cwick on a hashtag in a tweet f-fwom someone's pwofiwe, UwU
  // hence w-we awe ignowing t-those itemtype a-and onwy keeping itemtype=tweet. 😳
  cwienttweetcwickhashtag = 1078
  // this is fiwed when a usew cwicks "bookmawk" a-aftew cwicking on the "shawe" i-icon on a tweet to open the shawe menu, /(^•ω•^) ow
  // when a usew c-cwicks on the 'bookmawk' icon on a-a tweet (bookmawk icon is avaiwabwe to ios onwy a-as of mawch 2023). òωó
  // t-tweetbookmawk a-and tweetshawebybookmawk w-wog the same events b-but sewve fow individuaw use c-cases. >w<
  cwienttweetbookmawk = 1079

  // 1078 - 1089 f-fow aww shawe wewated actions. -.-

  // t-this is fiwed when a usew cwicks on a-a wink in a tweet. (⑅˘꒳˘)
  // the wink c-couwd be dispwayed a-as a uww ow embedded in a component s-such as a-an image ow a cawd in a tweet. (˘ω˘)
  cwienttweetopenwink = 1090
  // this is fiwed w-when a usew takes s-scweenshot. (U ᵕ U❁)
  // t-this is avaiwabwe f-fow mobiwe cwients onwy. ^^
  cwienttweettakescweenshot = 1091

  // 1100 - 1101: wefew to go/cme-scwibing a-and go/intewaction-event-spec fow detaiws
  // f-fiwed on the fiwst tick of a twack wegawdwess o-of whewe in the video it is pwaying. ^^
  // fow wooping p-pwayback, rawr x3 this is onwy fiwed once a-and does nyot w-weset at woop boundawies.
  c-cwienttweetvideopwaybackstawt = 1100
  // fiwed when p-pwayback weaches 100% o-of totaw twack duwation. >w<
  // n-nyot vawid f-fow wive videos. (U ᵕ U❁)
  // f-fow wooping p-pwayback, this is onwy fiwed once a-and does nyot w-weset at woop b-boundawies. 🥺
  cwienttweetvideopwaybackcompwete = 1101

  // a usew c-can sewect "this tweet isn't wewevant" action wesuwting in cwienttweetnotwewevant
  // and they couwd undo this a-action immediatewy w-which wesuwts in cwienttweetundonotwewevant
  c-cwienttweetnotwewevant = 1102
  cwienttweetundonotwewevant = 1103

  // a genewic a-action type t-to submit feedback f-fow diffewent m-moduwes / items ( tweets / seawch w-wesuwts )
  cwientfeedbackpwomptsubmit = 1104

  // this is f-fiwed when a usew p-pwofiwe is open in a pwofiwe page
  cwientpwofiweshow = 1105

  /*
   * this i-is twiggewed when a usew exits the t-twittew pwatfowm. (⑅˘꒳˘) the amount of the time spent o-on the
   * pwatfowm is wecowded i-in ms that can be used to compute the usew active s-seconds (uas). OwO
   */
  cwientappexit = 1106

  /*
   * f-fow "cawd" wewated actions
   */
  cwientcawdcwick = 1107
  c-cwientcawdopenapp = 1108
  c-cwientcawdappinstawwattempt = 1114
  cwientpowwcawdvote = 1115

  /*
   * the i-impwessions 1121-1123 togethew with the cwienttweetwendewimpwession 1003 a-awe used b-by viewcount
   * a-and unifiedengagementcounts as engagementtype.dispwayed and engagementtype.detaiws. 😳
   *
   * fow definitions, òωó pwease wefew t-to https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/common-intewnaw/anawytics/cwient-event-utiw/swc/main/java/com/twittew/common_intewnaw/anawytics/cwient_event_utiw/tweetimpwessionutiws.java?w14&subtwee=twue
   */
  cwienttweetgawwewyimpwession = 1121
  cwienttweetdetaiwsimpwession = 1122

  /**
   *  t-this i-is fiwed when a usew is wogged out and fowwows a-a pwofiwe fwom t-the
   *  pwofiwe page / peopwe moduwe fwom web. (ˆ ﻌ ˆ)♡
   *  one can o-onwy twy to fowwow fwom web because i-ios and andwoid do nyot suppowt wogged out bwowsing a-as of jan 2023.
   */
  c-cwientpwofiwefowwowattempt = 1200

  /**
   * this i-is fiwed when a-a usew is wogged out and favouwite a-a tweet fwom web. ʘwʘ
   * one can o-onwy twy to favouwite f-fwom web, ^^;; i-ios and andwoid d-do nyot suppowt w-wogged out bwowsing
   */
  cwienttweetfavowiteattempt = 1201

  /**
   * this i-is fiwed when a-a usew is wogged out and wetweet a tweet fwom web. ʘwʘ
   * o-one can onwy twy to favouwite f-fwom web, òωó ios and andwoid do nyot suppowt wogged out bwowsing
   */
  cwienttweetwetweetattempt = 1202

  /**
   * this is fiwed when a usew i-is wogged out and wepwy on tweet f-fwom web. ( ͡o ω ͡o )
   * one can onwy t-twy to favouwite f-fwom web, ʘwʘ ios and andwoid do nyot s-suppowt wogged out bwowsing
   */
  c-cwienttweetwepwyattempt = 1203

  /**
   * this is fiwed w-when a usew is wogged out and cwicks on wogin button. >w<
   * cuwwentwy seem to be genewated onwy on [m5, 😳😳😳 witenativewwappew]
  */
  c-cwientctawogincwick = 1204
  /**
   * this is fiwed when a usew i-is wogged out and wogin window i-is shown.
   */
  cwientctawoginstawt = 1205
  /**
   * this is fiwed when a usew is wogged out and wogin is successfuw. σωσ
  */
  cwientctawoginsuccess = 1206

  /**
   * this is fiwed when a usew i-is wogged out a-and cwicks on signup b-button. -.-
   */
  cwientctasignupcwick = 1207

  /**
   * t-this i-is fiwed when a-a usew is wogged out and signup is successfuw. 🥺
   */
  c-cwientctasignupsuccess = 1208
  // 1400 - 1499 f-fow pwoduct suwface specific a-actions
  // t-this is fiwed when a-a usew opens a-a push nyotification
  c-cwientnotificationopen = 1400
  // this i-is fiwed when a u-usew cwicks on a n-nyotification in t-the nyotification t-tab
  cwientnotificationcwick = 1401
  // t-this i-is fiwed when a-a usew taps the "see w-wess often" c-cawet menu item of a nyotification in the nyotification tab
  c-cwientnotificationseewessoften = 1402
  // this i-is fiwed when a usew cwoses ow swipes away a push n-nyotification
  c-cwientnotificationdismiss = 1403

  // 1420 - 1439 i-is wesewved fow seawch wesuwts p-page wewated a-actions
  // 1440 - 1449 is wesewved fow typeahead wewated actions

  // this is fiwed when a usew c-cwicks on a typeahead suggestion(quewies, >w< events, topics, (///ˬ///✿) usews)
  // i-in a dwop-down m-menu of a seawch box ow a-a tweet compose b-box. UwU
  cwienttypeaheadcwick = 1440

  // 1500 - 1999 u-used fow behaviowaw c-cwient e-events
  // tweet w-wewated impwessions
  c-cwienttweetv2impwession = 1500
  /* fuwwscween impwessions
   *
   * a-andwoid cwient wiww a-awways wog fuwwscween_video impwessions, ( ͡o ω ͡o ) w-wegawdwess o-of the media type
   * i.e. v-video, (ˆ ﻌ ˆ)♡ image, mm wiww aww be wogged as fuwwscween_video
   *
   * i-ios cwients w-wiww wog fuwwscween_video o-ow fuwwscween_image d-depending on the media t-type
   * on d-dispway when the u-usew exits fuwwscween. ^^;; i.e.
   * - i-image tweet => fuwwscween_image
   * - video tweet => fuwwscween_video
   * - mm tweet => fuwwscween_video  if usew exits fuwwscween fwom the video
   *            => f-fuwwscween_image  if u-usew exits fuwwscween fwom the image
   *
   * web cwients wiww awways wog fuwwscween_image i-impwessions, (U ᵕ U❁) w-wegawdwess of the media type
   *
   * wefewences
   * h-https://docs.googwe.com/document/d/1oet9_gtz34cmo_jwnag5ykkeq4q7cjfw-nbhomhnq1y
   * h-https://docs.googwe.com/document/d/1v_7tbfpvtqgte_91w5subd7n78jsvw_itow59gomwfq
   */
  cwienttweetvideofuwwscweenv2impwession = 1501
  cwienttweetimagefuwwscweenv2impwession = 1502
  // pwofiwe wewated i-impwessions
  c-cwientpwofiwev2impwession = 1600
  /*
   * emaiw n-nyotifications: these awe actions t-taken by the u-usew in wesponse to youw highwights emaiw
   * cwienttweetemaiwcwick wefews to the a-action nyotificationtype.cwick
   */
  c-cwienttweetemaiwcwick = 5001

  /*
   * u-usew cweate via g-gizmoduck
   */
  sewvewusewcweate = 6000
  s-sewvewusewupdate = 6001
  /*
   * a-ads cawwback engagements
   */
  /*
   * t-this engagement i-is genewated when a usew favs a pwomoted t-tweet. XD
   */
  s-sewvewpwomotedtweetfav = 7000
  /*
   * this engagement is genewated when a usew unfavs a pwomoted t-tweet that they p-pweviouswy faved. (ꈍᴗꈍ)
   */
  sewvewpwomotedtweetunfav = 7001
  s-sewvewpwomotedtweetwepwy = 7002
  sewvewpwomotedtweetwetweet = 7004
  /*
   * the bwock couwd be p-pewfowmed fwom t-the pwomoted tweet o-ow on the pwomoted tweet's authow's p-pwofiwe
   * a-ads_spend_event data shows majowity (~97%) of bwocks have an a-associated pwomoted t-tweet id
   * s-so fow nyow we a-assume the bwocks a-awe wawgewy p-pewfowmed fwom the tweet and fowwowing nyaming convention of cwienttweetbwockauthow
   */
  sewvewpwomotedtweetbwockauthow = 7006
  sewvewpwomotedtweetunbwockauthow = 7007
  /*
   * t-this is when a usew cwicks o-on the convewsationaw c-cawd in the pwomoted tweet which
   * weads to the tweet c-compose page. -.- the u-usew may ow may nyot send the n-nyew tweet. >_<
   */
  sewvewpwomotedtweetcomposetweet = 7008
  /*
   * t-this is when a usew cwicks on the pwomoted tweet to view its d-detaiws/wepwies. (ˆ ﻌ ˆ)♡
   */
  sewvewpwomotedtweetcwick = 7009
  /*
   * the video ads engagements awe divided into t-two sets: video_content_* a-and video_ad_*. ( ͡o ω ͡o ) t-these e-engagements
   * have simiwaw definitions. rawr x3 video_content_* e-engagements awe fiwed f-fow videos that awe pawt of
   * a tweet. òωó video_ad_* e-engagements a-awe fiwed fow a-a pwewoww ad. 😳 a pwewoww ad can pway on a pwomoted
   * t-tweet ow on an owganic tweet. (ˆ ﻌ ˆ)♡ go/pwewoww-matching fow mowe infowmation. 🥺
   *
   * 7011-7013: a pwomoted event is fiwed when p-pwayback weaches 25%, ^^ 50%, 75% o-of totaw twack duwation. /(^•ω•^)
   * this is fow the video on a pwomoted tweet. o.O
   * nyot vawid fow wive v-videos. òωó wefew go/avscwibing. XD
   * fow a video t-that has a pwewoww a-ad pwayed befowe i-it, the metadata w-wiww contain infowmation about
   * the pwewoww ad as weww as the video itsewf. rawr x3 thewe wiww b-be nyo pwewoww m-metadata if thewe w-was nyo
   * p-pwewoww ad pwayed. (˘ω˘)
   */
  sewvewpwomotedtweetvideopwayback25 = 7011
  s-sewvewpwomotedtweetvideopwayback50 = 7012
  sewvewpwomotedtweetvideopwayback75 = 7013
  /*
   * t-this is when a usew successfuwwy compwetes the wepowt fwow o-on a pwomoted t-tweet. :3
   * it covews w-wepowts fow a-aww powicies fwom cwient event. (U ᵕ U❁)
   */
  s-sewvewpwomotedtweetwepowt = 7041
  /*
   * f-fowwow fwom ads data stweam, rawr it couwd be fwom both tweet ow o-othew pwaces
   */
  s-sewvewpwomotedpwofiwefowwow = 7060
  /*
   * fowwow fwom ads data stweam, OwO it couwd be fwom b-both tweet ow othew pwaces
   */
  s-sewvewpwomotedpwofiweunfowwow = 7061
  /*
   * t-this is when a-a usew cwicks on the mute pwomoted tweet's authow option fwom the menu. ʘwʘ
   */
  sewvewpwomotedtweetmuteauthow = 7064
  /*
   * this i-is fiwed when a usew cwicks o-on the pwofiwe image, XD scween nyame, rawr x3 ow the usew n-nyame of the 
   * authow of the p-pwomoted tweet w-which weads to the a-authow's pwofiwe p-page. OwO
   */
  s-sewvewpwomotedtweetcwickpwofiwe = 7072
  /*
   * this is fiwed w-when a usew cwicks on a hashtag in the pwomoted tweet. nyaa~~
   */
  sewvewpwomotedtweetcwickhashtag = 7078
  /*
   * t-this is fiwed when a usew opens wink by cwicking o-on a uww in the p-pwomoted tweet. ʘwʘ
   */
  s-sewvewpwomotedtweetopenwink = 7079
  /*
   * this is fiwed when a usew swipes to the nyext ewement of t-the cawousew in t-the pwomoted tweet. nyaa~~
   */
  s-sewvewpwomotedtweetcawousewswipenext = 7091
  /*
   * t-this is fiwed when a usew swipes to the pwevious ewement of the cawousew in the pwomoted tweet.
   */
  s-sewvewpwomotedtweetcawousewswipepwevious = 7092
  /*
   * this event is onwy fow the pwomoted t-tweets with a-a web uww. (U ﹏ U)
   * i-it is fiwed aftew exiting a w-webview fwom a pwomoted tweet if the usew was on the webview fow
   * at weast 1 second. (///ˬ///✿)
   *
   * see https://confwuence.twittew.biz/dispway/wevenue/dweww_showt fow mowe detaiws. :3
   */
  sewvewpwomotedtweetwingewimpwessionshowt = 7093
  /*
   * this event i-is onwy fow the pwomoted tweets with a web uww. (˘ω˘)
   * i-it is fiwed a-aftew exiting a webview fwom a p-pwomoted tweet i-if the usew was on the webview fow
   * at weast 2 s-seconds.
   *
   * s-see https://confwuence.twittew.biz/dispway/wevenue/dweww_medium fow mowe detaiws. 😳
   */
  sewvewpwomotedtweetwingewimpwessionmedium = 7094
  /*
   * t-this e-event is onwy fow t-the pwomoted tweets w-with a web uww. 😳😳😳
   * it is f-fiwed aftew exiting a webview fwom a pwomoted tweet i-if the usew w-was on the webview fow
   * at w-weast 10 seconds. ʘwʘ
   *
   * s-see https://confwuence.twittew.biz/dispway/wevenue/dweww_wong fow mowe detaiws. (⑅˘꒳˘)
   */
  sewvewpwomotedtweetwingewimpwessionwong = 7095
  /*
   * t-this is fiwed when a-a usew nyavigates to expwowew page (taps s-seawch magnifying gwass on home page)
   * a-and a pwomoted twend is pwesent and taps on the pwomoted spotwight - a-a video/gif/image in the
   * "hewo" p-position (top o-of the e-expwowew page). nyaa~~
   */
  sewvewpwomotedtweetcwickspotwight = 7096
  /*
   * this i-is fiwed when a-a usew nyavigates t-to expwowew page (taps s-seawch magnifying gwass o-on home page)
   * a-and a pwomoted t-twend is pwesent. (U ﹏ U)
   */
  s-sewvewpwomotedtweetviewspotwight = 7097
  /*
   * 7098-7099: p-pwomoted twends appeaw in the fiwst ow s-second swots of t-the “twends fow you” section
   * in the expwowe t-tab and “nani’s h-happening” m-moduwe on twittew.com. ʘwʘ fow m-mowe infowmation, (ꈍᴗꈍ) c-check go/ads-takeovew. :3
   * 7099: this is fiwed w-when a usew v-views a pwomoted twend. ( ͡o ω ͡o ) it shouwd b-be considewed as an impwession. rawr x3
   */
  s-sewvewpwomotedtwendview = 7098
  /*
   * 7099: t-this is f-fiwed when a usew c-cwicks a pwomoted twend. rawr x3 it shouwd be considewed as an engagment. mya
   */
  s-sewvewpwomotedtwendcwick = 7099
  /*
   * 7131-7133: a pwomoted event f-fiwed when pwayback weaches 25%, nyaa~~ 50%, (///ˬ///✿) 75% o-of t-totaw twack duwation. ^^
   * this i-is fow the pwewoww a-ad that pways befowe a video on a pwomoted tweet. OwO
   * n-nyot v-vawid fow wive videos. :3 wefew go/avscwibing. ^^
   * this wiww onwy contain metadata fow the pwewoww ad. (✿oωo)
   */
  sewvewpwomotedtweetvideoadpwayback25 = 7131
  sewvewpwomotedtweetvideoadpwayback50 = 7132
  sewvewpwomotedtweetvideoadpwayback75 = 7133
  /*
   * 7151-7153: a pwomoted event fiwed when pwayback weaches 25%, 😳 50%, 75% o-of totaw twack d-duwation. (///ˬ///✿)
   * t-this is fow the p-pwewoww ad that pways befowe a video on an owganic t-tweet. (///ˬ///✿)
   * n-nyot vawid fow w-wive videos. (U ﹏ U) wefew g-go/avscwibing. òωó
   * this wiww onwy contain metadata fow the pwewoww ad. :3
   */
  s-sewvewtweetvideoadpwayback25 = 7151
  s-sewvewtweetvideoadpwayback50 = 7152
  s-sewvewtweetvideoadpwayback75 = 7153

  s-sewvewpwomotedtweetdismisswithoutweason = 7180
  sewvewpwomotedtweetdismissunintewesting = 7181
  s-sewvewpwomotedtweetdismisswepetitive = 7182
  sewvewpwomotedtweetdismissspam = 7183


  /*
   * fow favowiteawchivaw events
   */
  sewvewtweetawchivefavowite = 8000
  s-sewvewtweetunawchivefavowite = 8001
  /*
   * fow wetweetawchivaw e-events
   */
  s-sewvewtweetawchivewetweet = 8002
  sewvewtweetunawchivewetweet = 8003
}(pewsisted='twue', (⑅˘꒳˘) haspewsonawdata='fawse')

/*
 * this u-union wiww be updated when we have a-a pawticuwaw
 * action that has attwibutes unique t-to that pawticuwaw action
 * (e.g. 😳😳😳 wingew i-impwessions have stawt/end times) a-and nyot common
 * to aww tweet a-actions. ʘwʘ
 * nyaming c-convention fow tweetactioninfo shouwd be consistent with
 * a-actiontype. OwO fow exampwe, >_< `cwienttweetwingewimpwession` actiontype enum
 * shouwd cowwespond to `cwienttweetwingewimpwession` tweetactioninfo union awm.
 * we typicawwy pwesewve 1:1 m-mapping between a-actiontype and tweetactioninfo. /(^•ω•^) h-howevew, we make
 * exceptions w-when optimizing f-fow customew w-wequiwements. (˘ω˘) fow exampwe, muwtipwe 'cwienttweetvideo*'
 * actiontype e-enums cowwespond to a singwe `tweetvideowatch` tweetactioninfo union awm because
 * customews w-want individuaw a-action wabews b-but common i-infowmation acwoss those wabews. >w<
 */
u-union tweetactioninfo {
  // 41 matches enum i-index sewvewtweetwepowt i-in actiontype
  41: sewvewtweetwepowt sewvewtweetwepowt
  // 1002 m-matches e-enum index cwienttweetwingewimpwession i-in actiontype
  1002: c-cwienttweetwingewimpwession c-cwienttweetwingewimpwession
  // common metadata fow
  // 1. ^•ﻌ•^ "cwienttweetvideo*" a-actiontypes w-with enum i-indices 1011-1016 and 1100-1101
  // 2. ʘwʘ "sewvewpwomotedtweetvideo*" actiontypes with enum indices 7011-7013 a-and 7131-7133
  // 3. OwO "sewvewtweetvideo*" a-actiontypes w-with enum indices 7151-7153
  // t-this is because:
  // 1. nyaa~~ aww the above wisted a-actiontypes s-shawe common metadata
  // 2. nyaa~~ mowe m-moduwaw code as the same stwuct can be weused
  // 3. XD w-weduces chance of ewwow whiwe popuwating a-and pawsing the metadata
  // 4. o.O consumews can easiwy pwocess t-the metadata
  1011: tweetvideowatch t-tweetvideowatch
  // 1012: skip
  // 1013: s-skip
  // 1014: s-skip
  // 1015: s-skip
  // 1016: s-skip
  // 1024 matches enum index cwienttweetcwickmentionscweenname i-in actiontype
  1024: cwienttweetcwickmentionscweenname cwienttweetcwickmentionscweenname
  // 1041 matches enum index cwienttweetwepowt i-in a-actiontype
  1041: c-cwienttweetwepowt c-cwienttweetwepowt
  // 1060 m-matches enum index cwienttweetfowwowauthow i-in a-actiontype
  1060: cwienttweetfowwowauthow cwienttweetfowwowauthow
  // 1061 matches e-enum index cwienttweetunfowwowauthow in actiontype
  1061: c-cwienttweetunfowwowauthow cwienttweetunfowwowauthow
  // 1078 m-matches enum index cwienttweetcwickhashtag i-in actiontype
  1078: cwienttweetcwickhashtag cwienttweetcwickhashtag
  // 1090 m-matches enum index cwienttweetopenwink i-in actiontype
  1090: c-cwienttweetopenwink c-cwienttweetopenwink
  // 1091 matches enum index cwienttweettakescweenshot in actiontype
  1091: cwienttweettakescweenshot cwienttweettakescweenshot
  // 1500 matches e-enum index cwienttweetv2impwession in actiontype
  1500: cwienttweetv2impwession c-cwienttweetv2impwession
  // 7079 matches enum i-index sewvewpwomotedtweetopenwink i-in actiontype
  7079: sewvewpwomotedtweetopenwink s-sewvewpwomotedtweetopenwink
}(pewsisted='twue', òωó h-haspewsonawdata='twue')


stwuct cwienttweetopenwink {
  //uww which was cwicked. (⑅˘꒳˘)
  1: optionaw s-stwing uww(pewsonawdatatype = 'wawuwwpath')
}(pewsisted='twue', o.O haspewsonawdata='twue')

s-stwuct sewvewpwomotedtweetopenwink {
  //uww which w-was cwicked.
  1: optionaw stwing u-uww(pewsonawdatatype = 'wawuwwpath')
}(pewsisted='twue', haspewsonawdata='twue')

s-stwuct cwienttweetcwickhashtag {
  /* h-hashtag stwing which was cwicked. (ˆ ﻌ ˆ)♡ the pdp annotation is seawchquewy, (⑅˘꒳˘)
   * b-because cwicking o-on the hashtag t-twiggews a seawch with the hashtag
   */
  1: o-optionaw stwing hashtag(pewsonawdatatype = 'seawchquewy')
}(pewsisted='twue', (U ᵕ U❁) h-haspewsonawdata='twue')

stwuct c-cwienttweettakescweenshot {
  //pewcentage visibwe height.
  1: o-optionaw i32 pewcentvisibweheight100k
}(pewsisted='twue', >w< haspewsonawdata='fawse')

/*
 * s-see go/ioswingewimpwessionbehaviows and g-go/wingewandwoidfaq
 * fow ios and andwoid cwient definitions of a wingew wespectivewy. OwO
 */
stwuct c-cwienttweetwingewimpwession {
  /* miwwiseconds since epoch w-when the tweet b-became mowe than 50% v-visibwe. >w< */
  1: wequiwed i-i64 wingewstawttimestampms(pewsonawdatatype = 'impwessionmetadata')
  /* miwwiseconds since epoch w-when the tweet became wess than 50% v-visibwe. ^^;; */
  2: w-wequiwed i-i64 wingewendtimestampms(pewsonawdatatype = 'impwessionmetadata')
}(pewsisted='twue', >w< haspewsonawdata='twue')

/*
 * s-see go/behaviowaw-cwient-events f-fow genewaw b-behaviowaw cwient e-event (bce) infowmation
 * and g-go/bce-v2impwess fow detaiwed i-infowmation about b-bce impwession events. σωσ
 *
 * unwike cwienttweetwingewimpwession, (˘ω˘) thewe is nyo wowew bound on the a-amount of time
 * necessawy fow the impwess event to occuw. òωó thewe i-is awso nyo v-visibiwity wequiwement fow a impwess
 * event to occuw. (ꈍᴗꈍ)
 */
stwuct cwienttweetv2impwession {
  /* miwwiseconds since epoch when t-the tweet became v-visibwe. (ꈍᴗꈍ) */
  1: w-wequiwed i64 i-impwessstawttimestampms(pewsonawdatatype = 'impwessionmetadata')
  /* m-miwwiseconds s-since epoch when the tweet became v-visibwe. òωó */
  2: wequiwed i64 i-impwessendtimestampms(pewsonawdatatype = 'impwessionmetadata')
  /*
   * the u-ui component that hosted this tweet w-whewe the impwess e-event happened. (U ᵕ U❁)
   *
   * f-fow exampwe, /(^•ω•^) souwcecomponent = "tweet" i-if the impwess e-event happened on a tweet dispwayed amongst
   * a-a cowwection of tweets, :3 ow souwcecomponent = "tweet_detaiws" if the impwess e-event happened on
   * a tweet detaiw ui component. rawr
   */
  3: w-wequiwed stwing s-souwcecomponent(pewsonawdatatype = 'websitepage')
}(pewsisted='twue', (ˆ ﻌ ˆ)♡ haspewsonawdata='twue')

 /*
 * w-wefew to go/cme-scwibing a-and go/intewaction-event-spec fow d-detaiws
 */
stwuct tweetvideowatch {
   /*
   * t-type of video incwuded in the t-tweet
   */
  1: o-optionaw cwient_app.mediatype mediatype(pewsonawdatatype = 'mediafiwe')
  /*
   * w-whethew the video content is "monetizabwe", ^^;; i.e.,
   * if a pwewoww ad may be s-sewved dynamicawwy when the video p-pways
   */
  2: optionaw boow ismonetizabwe(pewsonawdatatype = 'mediafiwe')

  /*
   * t-the ownew of the video, (⑅˘꒳˘) p-pwovided by pwaywist. rawr x3
   *
   * f-fow ad engagements wewated to a-a pwewoww ad (video_ad_*), ʘwʘ
   * this wiww be the o-ownew of the pwewoww ad and same as the pwewowwownewid. (ꈍᴗꈍ)
   *
   * f-fow ad engagements w-wewated t-to a weguwaw video (video_content_*), /(^•ω•^) t-this wiww b-be the ownew of t-the
   * video and nyot the pwewoww a-ad. (✿oωo)
   */
  3: o-optionaw i64 v-videoownewid(pewsonawdatatype = 'usewid')

  /*
   * identifies t-the video associated with a cawd. ^^;;
   *
   * fow a-ad engagements, (˘ω˘) i-in the case of engagements wewated to a pwewoww a-ad (video_ad_*), 😳😳😳
   * t-this wiww be the id of the p-pwewoww ad and s-same as the pwewowwuuid. ^^
   *
   * f-fow ad engagements w-wewated to a weguwaw video (video_content_*), /(^•ω•^) this wiww be id of the video
   * and nyot the pwewoww ad. >_<
   */
  4: optionaw s-stwing videouuid(pewsonawdatatype = 'mediaid')

  /*
   * id o-of the pwewoww ad shown befowe the v-video
   */
  5: optionaw stwing p-pwewowwuuid(pewsonawdatatype = 'mediaid')

  /*
   * a-advewtisew id of the pwewoww a-ad
   */
  6: o-optionaw i64 pwewowwownewid(pewsonawdatatype = 'usewid')
  /*
   * fow ampwify_fwayew e-events, (ꈍᴗꈍ) indicates whethew pwewoww ow the m-main video is being pwayed
   */
  7: o-optionaw s-stwing videotype(pewsonawdatatype = 'mediafiwe')
}(pewsisted='twue', (ꈍᴗꈍ) h-haspewsonawdata='twue')

stwuct cwienttweetcwickmentionscweenname {
  /* i-id fow the pwofiwe (usew_id) that was actioned on */
  1: wequiwed i-i64 actionpwofiweid(pewsonawdatatype = 'usewid')
  /* the handwe/scweenname of the usew. mya this can't be changed. :3 */
  2: wequiwed stwing handwe(pewsonawdatatype = 'usewname')
}(pewsisted='twue', 😳😳😳 haspewsonawdata='twue')

stwuct c-cwienttweetwepowt {
  /*
   * w-whethew the "wepowt tweet" fwow w-was successfuwwy c-compweted. /(^•ω•^)
   * `twue` if the fwow was compweted successfuwwy, -.- `fawse` o-othewwise.
   */
  1: w-wequiwed boow iswepowttweetdone
  /*
   * wepowt-fwow-id i-is incwuded i-in cwient e-event when the "wepowt t-tweet" fwow was initiated
   * see go/wepowt-fwow-ids a-and
   * https://confwuence.twittew.biz/pages/viewpage.action?spacekey=heawth&titwe=undewstanding+wepowtdetaiws
   */
  2: optionaw stwing wepowtfwowid
}(pewsisted='twue', UwU h-haspewsonawdata='twue')

enum tweetauthowfowwowcwicksouwce {
  unknown = 1
  cawet_menu = 2
  pwofiwe_image = 3
}

stwuct c-cwienttweetfowwowauthow {
  /*
   * whewe did the usew cwick the fowwow button o-on the tweet - f-fwom the cawet m-menu("cawet_menu")
   * ow via hovewing ovew the p-pwofiwe and cwicking o-on fowwow ("pwofiwe_image") - o-onwy appwicabwe fow web cwients
   * "unknown" if the scwibe d-do nyot match the expected nyamespace f-fow the above
   */
  1: wequiwed tweetauthowfowwowcwicksouwce fowwowcwicksouwce
}(pewsisted='twue', (U ﹏ U) haspewsonawdata='fawse')

e-enum tweetauthowunfowwowcwicksouwce {
  unknown = 1
  c-cawet_menu = 2
  pwofiwe_image = 3
}

s-stwuct cwienttweetunfowwowauthow {
  /*
   * whewe d-did the usew cwick the unfowwow b-button on the tweet - fwom the cawet menu("cawet_menu")
   * o-ow via hovewing ovew the pwofiwe and cwicking on unfowwow ("pwofiwe_image") - o-onwy appwicabwe fow web cwients
   * "unknown" if the scwibe do n-nyot match the expected nyamespace f-fow the above
   */
  1: w-wequiwed tweetauthowunfowwowcwicksouwce u-unfowwowcwicksouwce
}(pewsisted='twue', ^^ haspewsonawdata='fawse')

s-stwuct sewvewtweetwepowt {
  /*
   * wepowtdetaiws wiww be p-popuwated when t-the tweet wepowt was scwibed by s-spamacaw (sewvew s-side)
   * onwy fow the action s-submit, 😳 aww the fiewds undew wepowtdetaiws wiww be avaiwabwe. (˘ω˘)
   * this is because onwy aftew successfuw s-submission, /(^•ω•^) we wiww know the wepowt_type and wepowt_fwow_name. (˘ω˘)
   * w-wefewence: h-https://confwuence.twittew.biz/pages/viewpage.action?spacekey=heawth&titwe=undewstanding+wepowtdetaiws
   */
  1: o-optionaw stwing wepowtfwowid
  2: o-optionaw w-wepowt_fwow_wogs.wepowttype wepowttype
}(pewsisted='twue', (✿oωo) h-haspewsonawdata='fawse')

/*
 * this union wiww b-be updated when w-we have a pawticuwaw
 * action that has attwibutes unique to that p-pawticuwaw action
 * (e.g. (U ﹏ U) w-wingew impwessions have stawt/end times) a-and nyot common
 * to othew p-pwofiwe actions. (U ﹏ U)
 *
 * n-nyaming c-convention fow p-pwofiweactioninfo shouwd be consistent w-with
 * actiontype. (ˆ ﻌ ˆ)♡ fow exampwe, /(^•ω•^) `cwientpwofiwev2impwession` a-actiontype enum
 * shouwd cowwespond to `cwientpwofiwev2impwession` pwofiweactioninfo u-union a-awm. XD
 */
union pwofiweactioninfo {
  // 56 m-matches e-enum index sewvewpwofiwewepowt i-in actiontype
  56: s-sewvewpwofiwewepowt s-sewvewpwofiwewepowt
  // 1600 matches enum index cwientpwofiwev2impwession i-in actiontype
  1600: cwientpwofiwev2impwession cwientpwofiwev2impwession
  // 6001 m-matches enum index sewvewusewupdate i-in actiontype
  6001: sewvewusewupdate sewvewusewupdate
}(pewsisted='twue', (ˆ ﻌ ˆ)♡ haspewsonawdata='twue')

/*
 * s-see go/behaviowaw-cwient-events f-fow genewaw b-behaviowaw cwient event (bce) infowmation
 * and https://docs.googwe.com/document/d/16cdswpsmuud17yofh9min3nwbqdvawx4dazoiqsfchi/edit#heading=h.3tu05p92xgxc
 * f-fow detaiwed i-infowmation about b-bce impwession e-event. XD
 *
 * unwike cwienttweetwingewimpwession, mya thewe is nyo wowew bound on the amount of time
 * nyecessawy f-fow the impwess e-event to occuw. OwO t-thewe is awso nyo visibiwity wequiwement fow a impwess
 * e-event to occuw. XD
 */
stwuct cwientpwofiwev2impwession {
  /* m-miwwiseconds since epoch when t-the pwofiwe page became visibwe. ( ͡o ω ͡o ) */
  1: wequiwed i64 impwessstawttimestampms(pewsonawdatatype = 'impwessionmetadata')
  /* m-miwwiseconds since epoch when the p-pwofiwe page became visibwe. (ꈍᴗꈍ) */
  2: wequiwed i64 impwessendtimestampms(pewsonawdatatype = 'impwessionmetadata')
  /*
   * t-the ui component that hosted this pwofiwe w-whewe the impwess event happened. mya
   *
   * f-fow exampwe, 😳 s-souwcecomponent = "pwofiwe" if the impwess event happened on a pwofiwe page
   */
  3: w-wequiwed stwing souwcecomponent(pewsonawdatatype = 'websitepage')
}(pewsisted='twue', (ˆ ﻌ ˆ)♡ haspewsonawdata='twue')

stwuct sewvewpwofiwewepowt {
  1: wequiwed sociaw_gwaph_sewvice_wwite_wog.action wepowttype(pewsonawdatatype = 'wepowttype')
}(pewsisted='twue', ^•ﻌ•^ h-haspewsonawdata='twue')

s-stwuct sewvewusewupdate {
  1: wequiwed wist<usew_sewvice.updatediffitem> updates
  2: o-optionaw boow success (pewsonawdatatype = 'auditmessage')
}(pewsisted='twue', 😳😳😳 h-haspewsonawdata='twue')

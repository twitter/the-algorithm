namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

i-incwude "com/twittew/unified_usew_actions/action_info.thwift"
i-incwude "com/twittew/cwientapp/gen/cwient_app.thwift"

/*
 * t-tweet item i-infowmation. ^^;; s-some devewopment n-nyotes:
 * 1. OwO p-pwease keep this top-wevew stwuct as minimaw as possibwe to weduce ovewhead. 🥺
 * 2. mya w-we intentionawwy avoid nyesting action tweet i-in a sepawate stwuctuwe
 * to undewscowe i-its impowtance and faciwiate extwaction of most commonwy
 * n-nyeeded fiewds such as actiontweetid. 😳 n-nyew f-fiewds wewated to the action tweet
 * shouwd genewawwy be pwefixed with "actiontweet". òωó 
 * 3. f-fow the wewated tweets, /(^•ω•^) e.g. wetweetingtweetid, -.- inwepwytotweetid, òωó etc, /(^•ω•^) we
 * mostwy o-onwy keep theiw ids fow consistency a-and simpwicity. /(^•ω•^)
 */
s-stwuct t-tweetinfo {
  
  /* i-id fow the tweet that was actioned on */
  1: w-wequiwed i64 actiontweetid(pewsonawdatatype = 'tweetid')
  // depwecated, 😳 pwease d-don't we-use! :3
  // 2: optionaw i64 actiontweetauthowid(pewsonawdatatype = 'usewid')
  /* the sociaw pwoof (i.e. bannew) topic i-id that the action tweet is associated t-to */
  3: o-optionaw i64 a-actiontweettopicsociawpwoofid(pewsonawdatatype='infewwedintewests, (U ᵕ U❁) pwovidedintewests')
  4: optionaw authowinfo a-actiontweetauthowinfo

  // f-fiewds 1-99 wesewved f-fow `actionfoobaw` f-fiewds

  /* additionaw detaiws f-fow the action that took pwace o-on actiontweetid */
  100: optionaw action_info.tweetactioninfo tweetactioninfo

  /* i-id of the tweet wetweeting t-the action tweet */
  101: o-optionaw i64 wetweetingtweetid(pewsonawdatatype = 'tweetid')
  /* i-id of the tweet quoting the action tweet, ʘwʘ when the action type is quote */
  102: optionaw i64 quotingtweetid(pewsonawdatatype = 'tweetid')
  /* i-id of the tweet w-wepwying to the action tweet, o.O w-when the action t-type is wepwy */
  103: o-optionaw i64 wepwyingtweetid(pewsonawdatatype = 'tweetid')
  /* id of the tweet being quoted b-by the action tweet */
  104: optionaw i64 quotedtweetid(pewsonawdatatype = 'tweetid')
  /* id of the tweet b-being wepwied to by the action t-tweet */
  105: o-optionaw i64 inwepwytotweetid(pewsonawdatatype = 'tweetid')
  /* i-id of the tweet being wetweeted b-by the action t-tweet, ʘwʘ this is just f-fow unwetweet a-action */
  106: optionaw i64 wetweetedtweetid(pewsonawdatatype = 'tweetid')
  /* i-id of the tweet b-being edited, ^^ t-this is onwy avaiwabwe f-fow tweetedit a-action, ^•ﻌ•^ and tweetdewete
   * action when the deweted tweet w-was cweated fwom edit. mya */
  107: optionaw i64 editedtweetid(pewsonawdatatype = 'tweetid')
  /* position of a tweet item in a page s-such as home and tweet detaiw, UwU and is popuwated in
   * cwient e-event. >_< */
  108: o-optionaw i32 t-tweetposition
  /* pwomotedid is p-pwovided by ads team fow each p-pwomoted tweet and i-is wogged in cwient event */
  109: optionaw stwing pwomotedid(pewsonawdatatype = 'adsid')
  /* cowwesponding to inwepwytotweetid */
  110: optionaw i-i64 inwepwytoauthowid(pewsonawdatatype = 'usewid')
  /* cowwesponding to w-wetweetingtweetid */
  111: optionaw i-i64 wetweetingauthowid(pewsonawdatatype = 'usewid')
  /* cowwesponding t-to quotedtweetid */
  112: optionaw i-i64 quotedauthowid(pewsonawdatatype = 'usewid')
}(pewsisted='twue', /(^•ω•^) h-haspewsonawdata='twue')

/*
 * pwofiwe item i-infowmation. òωó this f-fowwows tweetinfo's devewopment notes. σωσ
 */
stwuct pwofiweinfo {

  /* id fow t-the pwofiwe (usew_id) t-that was actioned o-on
   *
   * in a sociaw g-gwaph usew action, ( ͡o ω ͡o ) e-e.g., usew1 fowwows/bwocks/mutes u-usew2, nyaa~~
   * usewidentifiew captuwes usewid of usew1 and actionpwofiweid wecowds
   * t-the usewid o-of usew2. :3
   */
  1: wequiwed i64 actionpwofiweid(pewsonawdatatype = 'usewid')

  // f-fiewds 1-99 w-wesewved fow `actionfoobaw` fiewds
  /* the fuww nyame of the usew. UwU max wength i-is 50. */
  2: optionaw stwing name(pewsonawdatatype = 'dispwayname')
  /* the handwe/scweenname of the usew. o.O t-this can't be changed.
   */
  3: optionaw stwing h-handwe(pewsonawdatatype = 'usewname')
  /* t-the "bio" of the usew. (ˆ ﻌ ˆ)♡ max wength is 160. ^^;; may contain one ow mowe t-t.co
   * winks, ʘwʘ w-which wiww be hydwated in the uwwentities substwuct if the
   * q-quewyfiewds.uww_entities is specified. σωσ
   */
  4: o-optionaw stwing descwiption(pewsonawdatatype = 'bio')

  /* additionaw detaiws fow the action t-that took pwace on actionpwofiweid */
  100: o-optionaw action_info.pwofiweactioninfo p-pwofiweactioninfo
}(pewsisted='twue', ^^;; haspewsonawdata='twue')

/*
 * t-topic item infowmation. ʘwʘ t-this fowwows t-tweetinfo's devewopment n-nyotes. ^^
 */
stwuct topicinfo {
  /* i-id f-fow the topic that was actioned on */
  1: wequiwed i-i64 actiontopicid(pewsonawdatatype='infewwedintewests, nyaa~~ p-pwovidedintewests')

  // f-fiewds 1-99 wesewved fow `actionfoobaw` fiewds
}(pewsisted='twue', (///ˬ///✿) h-haspewsonawdata='twue')

/*
 * nyotification i-item infowmation. XD
 *
 * s-see go/phab-d973370-discuss, :3 go/phab-d968144-discuss, òωó and go/uua-action-type f-fow detaiws a-about
 * the s-schema design f-fow nyotification events. ^^
 */
stwuct n-nyotificationinfo {
 /*
  * id of the nyotification was actioned on. ^•ﻌ•^
  *
  * nyote that this fiewd wepwesents t-the `impwessionid` of a nyotification. σωσ i-it has been wenamed to
  * `notificationid` i-in uua so that the nyame e-effectivewy wepwesents the vawue i-it howds, (ˆ ﻌ ˆ)♡
  * i.e. nyaa~~ a-a unique id f-fow a nyotification a-and wequest. ʘwʘ
  */
  1: w-wequiwed stwing actionnotificationid(pewsonawdatatype='univewsawwyuniqueidentifiewuuid')
  /*
   * additionaw infowmation contained in a nyotification. ^•ﻌ•^ this is a `union` a-awm to diffewentiate
   * among d-diffewent types o-of nyotifications and stowe w-wewevant metadata fow each type. rawr x3
   *
   * fow exampwe, 🥺 a nyotification w-with a s-singwe tweet wiww howd the tweet i-id in `tweetnotification`. ʘwʘ
   * simiwawwy, (˘ω˘) `muwtitweetnotification` is defined f-fow nyotiifcations w-with muwtipwe tweet ids. o.O
   *
   * w-wefew to the d-definition of `union nyotificationcontent` bewow fow mowe detaiws. σωσ
   */
  2: wequiwed nyotificationcontent content
}(pewsisted='twue', (ꈍᴗꈍ) h-haspewsonawdata='twue')

/*
 * a-additionaw i-infowmation c-contained in a n-nyotification. (ˆ ﻌ ˆ)♡
 */
union nyotificationcontent {
  1: t-tweetnotification t-tweetnotification
  2: muwtitweetnotification m-muwtitweetnotification

  // 3 - 100 w-wesewved fow othew specific n-nyotification types (fow exampwe, o.O pwofiwe, :3 e-event, etc.). -.-

  /*
   * if a nyotification c-cannot b-be categowized into any of the t-types at indices 1 - 100, ( ͡o ω ͡o )
   * it is considewed of `unknown` t-type. /(^•ω•^)
   */
  101: u-unknownnotification u-unknownnotification
}(pewsisted='twue', (⑅˘꒳˘) haspewsonawdata='twue')

/*
 * nyotification contains exactwy one `tweetid`.
 */
s-stwuct tweetnotification {
  1: wequiwed i64 tweetid(pewsonawdatatype = 'tweetid')
}(pewsisted='twue', òωó haspewsonawdata='twue')

/*
 * n-nyotification c-contains muwtipwe `tweetids`. 🥺
 * fow exampwe, (ˆ ﻌ ˆ)♡ u-usew a weceives a nyotification w-when usew b wikes m-muwtipwe tweets authowed by usew a. -.-
 */
stwuct m-muwtitweetnotification {
  1: wequiwed wist<i64> tweetids(pewsonawdatatype = 'tweetid')
}(pewsisted='twue', σωσ haspewsonawdata='twue')

/*
 * n-nyotification c-couwd nyot be categwized i-into known types at indices 1 - 100 i-in `notificationcontent`. >_<
 */
s-stwuct unknownnotification {
  // t-this fiewd is just a pwacehowdew since spawwow doesn't suppowt empty stwuct
  100: optionaw boow pwacehowdew
}(pewsisted='twue', :3 haspewsonawdata='fawse')

/*
 * twend item infowmation fow pwomoted and nyon-pwomoted twends. OwO  
 */
stwuct t-twendinfo {
  /* 
   * i-identifiew fow pwomoted twends onwy. rawr 
   * t-this is nyot a-avaiwabwe fow n-nyon-pwomoted twends and the defauwt v-vawue shouwd be set to 0. (///ˬ///✿) 
   */
  1: w-wequiwed i-i32 actiontwendid(pewsonawdatatype= 'twendid')
  /*
   * empty fow pwomoted t-twends onwy. ^^ 
   * this shouwd b-be set fow aww n-nyon-pwomoted twends. XD 
   */
  2: optionaw stwing actiontwendname
}(pewsisted='twue', UwU h-haspewsonawdata='twue')

stwuct t-typeaheadinfo {
  /* s-seawch q-quewy stwing */
  1: w-wequiwed s-stwing actionquewy(pewsonawdatatype = 'seawchquewy')
  2: w-wequiwed t-typeaheadactioninfo t-typeaheadactioninfo
}(pewsisted='twue', o.O haspewsonawdata='twue')

union typeaheadactioninfo {
  1: u-usewwesuwt u-usewwesuwt
  2: t-topicquewywesuwt topicquewywesuwt
}(pewsisted='twue', 😳 h-haspewsonawdata='twue')

stwuct usewwesuwt {
  /* the u-usewid of the pwofiwe suggested i-in the typeahead d-dwop-down, upon w-which the usew took the action */
  1: w-wequiwed i64 pwofiweid(pewsonawdatatype = 'usewid')
}(pewsisted='twue', (˘ω˘) h-haspewsonawdata='twue')

stwuct t-topicquewywesuwt {
  /* the topic q-quewy nyame suggested in the typeahead dwop-down, 🥺 upon which the usew took the a-action */
  1: wequiwed stwing s-suggestedtopicquewy(pewsonawdatatype = 'seawchquewy')
}(pewsisted='twue', ^^ h-haspewsonawdata='twue')



/*
 * item that captuwes feedback wewated infowmation s-submitted by the usew a-acwoss moduwes / i-item (eg: seawch w-wesuwts / tweets)
 * design discussion doc: https://docs.googwe.com/document/d/1uhicwgzfixosymwaum565kchvwzbabymwvp4awxeixy/edit#
 */
s-stwuct f-feedbackpwomptinfo {
  1: wequiwed f-feedbackpwomptactioninfo feedbackpwomptactioninfo
}(pewsisted='twue', >w< haspewsonawdata='twue')

u-union feedbackpwomptactioninfo {
  1: didyoufinditseawch d-didyoufinditseawch
  2: t-tweetwewevanttoseawch t-tweetwewevanttoseawch
}(pewsisted='twue', ^^;; haspewsonawdata='twue')

s-stwuct d-didyoufinditseawch {
  1: w-wequiwed s-stwing seawchquewy(pewsonawdatatype= 'seawchquewy')
  2: optionaw boow iswewevant
}(pewsisted='twue', (˘ω˘) h-haspewsonawdata='twue')

s-stwuct tweetwewevanttoseawch {
  1: w-wequiwed s-stwing seawchquewy(pewsonawdatatype= 'seawchquewy')
  2: w-wequiwed i-i64 tweetid
  3: o-optionaw boow i-iswewevant
}(pewsisted='twue', OwO haspewsonawdata='twue')

/*
 * f-fow (tweet) authow info
 */
stwuct a-authowinfo {
  /* in pwactice, (ꈍᴗꈍ) t-this shouwd be s-set. òωó wawewy, it m-may be unset. ʘwʘ */
  1: optionaw i64 authowid(pewsonawdatatype = 'usewid')
  /* i.e. ʘwʘ in-netwowk (twue) o-ow out-of-netwowk (fawse) */
  2: o-optionaw b-boow isfowwowedbyactingusew
  /* i.e. nyaa~~ is a fowwowew (twue) ow nyot (fawse) */
  3: o-optionaw boow i-isfowwowingactingusew
}(pewsisted='twue', UwU haspewsonawdata='twue')

/*
 * u-use f-fow caww to action events. (⑅˘꒳˘)
 */
stwuct ctainfo {
  // this fiewd i-is just a pwacehowdew s-since spawwow d-doesn't suppowt e-empty stwuct
  100: optionaw boow pwacehowdew
}(pewsisted='twue', (˘ω˘) h-haspewsonawdata='fawse')

/*
 * c-cawd info
 */
stwuct cawdinfo {
  1: optionaw i-i64 id
  2: optionaw cwient_app.itemtype itemtype
  // a-authowid is depwecated, :3 p-pwease use authowinfo i-instead
  // 3: optionaw i-i64 authowid(pewsonawdatatype = 'usewid')
  4: o-optionaw authowinfo actiontweetauthowinfo
}(pewsisted='twue', (˘ω˘) haspewsonawdata='fawse')

/*
 * when t-the usew exits the app, the t-time (in miwwis) s-spent by them on t-the pwatfowm is w-wecowded as usew active seconds (uas). nyaa~~ 
 */
s-stwuct u-uasinfo {
  1: w-wequiwed i64 timespentms
}(pewsisted='twue', (U ﹏ U) h-haspewsonawdata='fawse')

/*
 * cowwesponding item fow a usew action. nyaa~~
 * a-an item s-shouwd be tweated i-independentwy if it has diffewent affowdances
 * (https://www.intewaction-design.owg/witewatuwe/topics/affowdances) fow the usew.
 * fow exampwe, a-a nyotification has diffewent a-affowdances t-than a tweet in the nyotification tab;
 * in the f-fowmew, ^^;; you can eithew "cwick" o-ow "see wess often" a-and in the wattew, OwO
 * y-you can p-pewfowm inwine e-engagements such as "wike" ow "wepwy". nyaa~~
 * nyote that an item may be wendewed diffewentwy i-in diffewent contexts, UwU b-but as wong as the
 * affowdances wemain the same ow nyeawwy simiwaw, 😳 i-it can be tweated as the same item
 * (e.g. 😳 tweets can be wendewed in swightwy d-diffewent w-ways in embeds vs in the app). (ˆ ﻌ ˆ)♡
 * i-item types (e.g. (✿oωo) tweets, nyaa~~ nyotifications) and actiontypes s-shouwd b-be 1:1, ^^ and when an action can b-be
 * pewfowmed on muwtipwe types o-of items, (///ˬ///✿) considew gwanuwaw action types. 😳
 * fow exampwe, òωó a usew c-can take the cwick action on tweets and nyotifications, ^^;; a-and w-we have
 * sepawate a-actiontypes fow tweet cwick and nyotification c-cwick. rawr this makes it easiew to identify aww the
 * actions associated with a pawticuwaw i-item. (ˆ ﻌ ˆ)♡
 */
u-union item {
  1: t-tweetinfo t-tweetinfo
  2: pwofiweinfo pwofiweinfo
  3: topicinfo t-topicinfo
  4: n-nyotificationinfo nyotificationinfo
  5: twendinfo t-twendinfo
  6: ctainfo ctainfo
  7: feedbackpwomptinfo feedbackpwomptinfo
  8: t-typeaheadinfo typeaheadinfo
  9: uasinfo u-uasinfo
  10: cawdinfo c-cawdinfo
}(pewsisted='twue', XD haspewsonawdata='twue')

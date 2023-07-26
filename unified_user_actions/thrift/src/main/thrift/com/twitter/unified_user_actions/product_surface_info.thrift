#@namespace java com.twittew.unified_usew_actions.thwiftjava
#@namespace s-scawa com.twittew.unified_usew_actions.thwiftscawa
#@namespace s-stwato com.twittew.unified_usew_actions

i-incwude "com/twittew/unified_usew_actions/metadata.thwift"
i-incwude "com/twittew/seawch/common/constants/quewy.thwift"
i-incwude "com/twittew/seawch/common/constants/wesuwt.thwift"


/*
 * w-wepwesents t-the pwoduct s-suwface on which an action took pwace. σωσ
 * see wefewence that dewineates vawious p-pwoduct suwfaces:
 * https://docs.googwe.com/document/d/1ps2zoynuoudo45zxhe7dh3w8kucqjwo6vx-xuggfo6u
 * nyote: t-the impwementation hewe may nyot w-wefwect the above doc exactwy. (⑅˘꒳˘)
 */
enum pwoductsuwface {
  // 1 - 19 fow home
  h-hometimewine = 1
  // 20 - 39 fow nyotifications
  n-nyotificationtab = 20
  p-pushnotification = 21
  emaiwnotification = 22
  // 40 - 59 fow seawch
  seawchwesuwtspage = 40
  seawchtypeahead = 41
  // 60 - 79 f-fow tweet detaiws page (convewsation page)
  tweetdetaiwspage = 60
  // 80 - 99 fow pwofiwe page
  pwofiwepage = 80
  // 100 - 119 f-fow ?
  wesewved_100 = 100
  // 120 - 139 fow ?
  w-wesewved_120 = 120
}(pewsisted='twue', (///ˬ///✿) h-haspewsonawdata='fawse')

u-union pwoductsuwfaceinfo {
  // 1 m-matches the enum index hometimewine in pwoductsuwface
  1: h-hometimewineinfo hometimewineinfo
  // 20 matches t-the enum index nyotificationtab in pwoductsuwface
  20: nyotificationtabinfo nyotificationtabinfo
  // 21 matches the enum i-index pushnotification in pwoductsuwface
  21: pushnotificationinfo p-pushnotificationinfo
  // 22 m-matches the enum i-index emaiwnotification in pwoductsuwface
  22: emaiwnotificationinfo emaiwnotificationinfo
  // 40 m-matches the e-enum index seawchwesuwtpage in p-pwoductsuwface
  40: s-seawchwesuwtspageinfo seawchwesuwtspageinfo
  // 41 m-matches the enum index s-seawchtypeahead in pwoductsuwface
  41: seawchtypeaheadinfo s-seawchtypeaheadinfo
  // 60 matches t-the enum index tweetdetaiwspage i-in pwoductsuwface
  60: t-tweetdetaiwspageinfo tweetdetaiwspageinfo
  // 80 matches the enum index pwofiwepage in pwoductsuwface
  80: pwofiwepageinfo p-pwofiwepageinfo
}(pewsisted='twue', 🥺 h-haspewsonawdata='fawse')

/*
 * pwease k-keep this minimaw t-to avoid ovewhead. OwO i-it shouwd onwy
 * contain high vawue home timewine specific a-attwibutes. >w<
 */
stwuct hometimewineinfo {
  // suggesttype is depwecated, pwease do't we-use! 🥺
  // 1: o-optionaw i32 suggesttype
  2: o-optionaw stwing s-suggestiontype
  3: o-optionaw i32 injectedposition
}(pewsisted='twue', nyaa~~ h-haspewsonawdata='fawse')

s-stwuct nyotificationtabinfo {
 /*
  * n-nyote t-that this fiewd wepwesents the `impwessionid` in a nyotification t-tab nyotification. ^^
  * i-it has b-been wenamed to `notificationid` i-in uua so that t-the nyame effectivewy wepwesents the
  * vawue it howds, >w< i.e., a-a unique id fow a nyotification and wequest. OwO
  */
  1: wequiwed stwing nyotificationid(pewsonawdatatype='univewsawwyuniqueidentifiewuuid')
}(pewsisted='twue', XD haspewsonawdata='fawse')

stwuct p-pushnotificationinfo {
 /*
  * nyote that this fiewd wepwesents the `impwessionid` i-in a push nyotification.
  * i-it has been wenamed t-to `notificationid` in uua so t-that the nyame effectivewy wepwesents t-the
  * v-vawue it howds, ^^;; i.e., a unique id fow a nyotification and wequest. 🥺
  */
  1: wequiwed stwing nyotificationid(pewsonawdatatype='univewsawwyuniqueidentifiewuuid')
}(pewsisted='twue', XD h-haspewsonawdata='fawse')

stwuct emaiwnotificationinfo {
 /*
  * n-nyote that this fiewd wepwesents t-the `impwessionid` i-in an emaiw nyotification. (U ᵕ U❁)
  * it has b-been wenamed to `notificationid` i-in uua so that the name effectivewy w-wepwesents t-the
  * vawue it howds, :3 i.e., a unique id fow a nyotification and wequest. ( ͡o ω ͡o )
  */
  1: w-wequiwed stwing n-notificationid(pewsonawdatatype='univewsawwyuniqueidentifiewuuid')
}(pewsisted='twue', òωó h-haspewsonawdata='fawse')


stwuct tweetdetaiwspageinfo {
  // t-to be d-depwecated, σωσ pwease don't we-use! (U ᵕ U❁)
  // o-onwy weason to keep it nyow is spawwow doesn't take empty stwuct. (✿oωo) once thewe i-is a weaw
  // f-fiewd we shouwd just comment it out. ^^
  1: wequiwed w-wist<stwing> b-bweadcwumbviews(pewsonawdatatype = 'websitepage')
  // depwecated, ^•ﻌ•^ pwease don't we-use! XD
  // 2: w-wequiwed wist<metadata.bweadcwumbtweet> bweadcwumbtweets(pewsonawdatatype = 'tweetid')
}(pewsisted='twue', :3 haspewsonawdata='twue')

stwuct pwofiwepageinfo {
  // to be depwecated, (ꈍᴗꈍ) p-pwease don't we-use! :3
  // onwy weason to keep i-it nyow is spawwow d-doesn't take empty stwuct. (U ﹏ U) once thewe is a weaw
  // fiewd w-we shouwd just c-comment it out. UwU
  1: wequiwed wist<stwing> bweadcwumbviews(pewsonawdatatype = 'websitepage')
  // depwecated, 😳😳😳 pwease d-don't we-use! XD
  // 2: wequiwed w-wist<metadata.bweadcwumbtweet> bweadcwumbtweets(pewsonawdatatype = 'tweetid')
}(pewsisted='twue', o.O haspewsonawdata='twue')

stwuct seawchwesuwtspageinfo {
  // s-seawch quewy stwing
  1: wequiwed s-stwing quewy(pewsonawdatatype = 'seawchquewy')
  // a-attwibution of the seawch (e.g. (⑅˘꒳˘) t-typed quewy, 😳😳😳 hashtag cwick, e-etc.)
  // s-see http://go/sgb/swc/thwift/com/twittew/seawch/common/constants/quewy.thwift fow d-detaiws
  2: optionaw quewy.thwiftquewysouwce q-quewysouwce
  // 0-indexed p-position of item in wist of seawch wesuwts
  3: o-optionaw i-i32 itemposition
  // a-attwibution of the tweet wesuwt (e.g. nyaa~~ q-qig, rawr eawwybiwd, etc)
  // see http://go/sgb/swc/thwift/com/twittew/seawch/common/constants/wesuwt.thwift f-fow detaiws
  4: o-optionaw set<wesuwt.tweetwesuwtsouwce> tweetwesuwtsouwces
  // attwibution o-of the usew w-wesuwt (e.g. -.- expewtseawch, (✿oωo) q-qig, e-etc)
  // see http://go/sgb/swc/thwift/com/twittew/seawch/common/constants/wesuwt.thwift f-fow detaiws
  5: optionaw set<wesuwt.usewwesuwtsouwce> usewwesuwtsouwces
  // the quewy fiwtew type on t-the seawch wesuwts page (swp) w-when the action took pwace. /(^•ω•^)
  // c-cwicking on a tab in swp appwies a-a quewy fiwtew automaticawwy. 🥺
  6: o-optionaw seawchquewyfiwtewtype q-quewyfiwtewtype
}(pewsisted='twue', ʘwʘ h-haspewsonawdata='twue')

s-stwuct seawchtypeaheadinfo {
  // s-seawch quewy stwing
  1: wequiwed stwing quewy(pewsonawdatatype = 'seawchquewy')
  // 0-indexed position of item in wist of typeahead dwop-down
  2: optionaw i-i32 itemposition
}(pewsisted='twue', UwU h-haspewsonawdata='twue')

enum s-seawchquewyfiwtewtype {
  // fiwtew to top wanked c-content fow a quewy
  top = 1
  // fiwtew to watest content f-fow a quewy
  w-watest = 2
  // fiwtew to usew wesuwts f-fow a quewy
  peopwe = 3
  // fiwtew to photo t-tweet wesuwts f-fow a quewy
  photos = 4
  // f-fiwtew to video t-tweet wesuwts fow a quewy
  videos = 5
}

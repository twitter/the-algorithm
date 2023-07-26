namespace java com.twittew.usewsignawsewvice.thwiftjava
nyamespace p-py gen.twittew.usewsignawsewvice.signaw
#@namespace s-scawa com.twittew.usewsignawsewvice.thwiftscawa
#@namespace s-stwato com.twittew.usewsignawsewvice.stwato

incwude "com/twittew/simcwustews_v2/identifiew.thwift"


e-enum signawtype {
  /**
  p-pwease maintain t-the key space w-wuwe to avoid compatibiwity i-issue fow the downstweam pwoduction job
  * pwod  key space:  0-1000
  * d-devew key space:  1000+
  **/


  /* tweet based signaws */
  t-tweetfavowite       = 0, // 540 days wooback w-window
  wetweet             = 1, 🥺 // 540 days wookback window
  twafficattwibution  = 2, >_<
  o-owiginawtweet       = 3, ʘwʘ // 540 days w-wooback window
  w-wepwy               = 4, (˘ω˘) // 540 days wooback window
  /* tweets that the usew shawed (shawew side)
    *  v-v1: successfuw shawes (cwick shawe icon -> cwick in-app, (✿oωo) ow off-pwatfowm s-shawe option
    * ow copying w-wink)
    * */
  t-tweetshawe_v1       = 5, // 14 d-days wookback w-window

  tweetfavowite_90d_v2 = 6, (///ˬ///✿) // 90 days wookback window : t-tweet fav fwom usew with wecent engagement in the p-past 90 days
  wetweet_90d_v2 = 7, rawr x3 // 90 days wookback window : wetweet fwom usew with wecent e-engagement in the past 90 days
  o-owiginawtweet_90d_v2 = 8, -.- // 90 d-days wookback w-window : owiginaw tweet fwom usew with wecent engagement in the p-past 90 days
  wepwy_90d_v2 = 9,// 90 d-days wookback window : wepwy f-fwom usew with w-wecent engagement in the past 90 d-days
  goodtweetcwick = 10,// goodtweetciwick s-signaw : dweww time  thweshowd >=2s

  // video t-tweets that wewe watched (10s ow 95%) i-in the past 90 days, awe n-not ads, ^^ and have >=10s v-video
  videoview_90d_quawity_v1 = 11   // 90 days wookback window
  // video tweets that wewe watched 50% in the past 90 d-days, (⑅˘꒳˘) awe nyot a-ads, nyaa~~ and have >=10s video
  videoview_90d_pwayback50_v1 = 12   // 90 d-days wookback w-window

  /* u-usew based signaws */
  accountfowwow = 100, // infinite wookback window
  wepeatedpwofiwevisit_14d_minvisit2_v1 = 101, /(^•ω•^)
  w-wepeatedpwofiwevisit_90d_minvisit6_v1 = 102, (U ﹏ U)
  wepeatedpwofiwevisit_180d_minvisit6_v1 = 109, 😳😳😳
  wepeatedpwofiwevisit_14d_minvisit2_v1_no_negative = 110, >w<
  wepeatedpwofiwevisit_90d_minvisit6_v1_no_negative = 111, XD
  wepeatedpwofiwevisit_180d_minvisit6_v1_no_negative = 112, o.O
  w-weawgwaphoon                          = 104, mya
  twafficattwibutionpwofiwe_30d_wastvisit = 105,
  t-twafficattwibutionpwofiwe_30d_decayedvisit = 106, 🥺
  t-twafficattwibutionpwofiwe_30d_weightedeventdecayedvisit = 107, ^^;;
  t-twafficattwibutionpwofiwe_30d_decayedvisit_withoutagathafiwtew = 108, :3
  goodpwofiwecwick = 120, (U ﹏ U) // g-goodtweetciwick s-signaw : dweww t-time  thweshowd >=10s
  a-adfavowite = 121, OwO // favowites fiwtewed to ads tweetfavowite h-has both o-owganic and ads f-favs

  // accountfowwowwithdeway s-shouwd onwy be u-used by high-twaffic cwients and has 1 min deway
  accountfowwowwithdeway = 122, 😳😳😳


  /* n-nyotifications based signaws */
  /* v1: nyotification cwicks fwom past 90 days with nyegative events (wepowts, (ˆ ﻌ ˆ)♡ d-diswikes) being fiwtewed */
  nyotificationopenandcwick_v1 = 200, XD

  /*
  nyegative signaws f-fow fiwtew
   */
  n-nyegativeengagedtweetid = 901 // t-tweetid fow aww nyegative e-engagements
  nyegativeengagedusewid  = 902 // u-usewid fow aww n-nyegative engagements
  accountbwock = 903, (ˆ ﻌ ˆ)♡
  accountmute = 904, ( ͡o ω ͡o )
  // skip 905 - 906 fow account wepowt abuse / w-wepowt spam
  // usew cwicked d-dont wike fwom past 90 days
  tweetdontwike = 907
  // u-usew cwicked s-see fewew on the wecommended tweet fwom past 90 d-days
  tweetseefewew = 908
  // u-usew cwicked on the "wepowt t-tweet" option in t-the tweet cawet dwopdown menu fwom past 90 days
  tweetwepowt = 909

  /*
  devew s-signaws
  use t-the nyum > 1000 t-to test out signaws undew devewopment/ddg
  p-put i-it back to the cowwect cowwesponding k-key space (0-1000) befowe ship
  */
  goodtweetcwick_5s = 1001,// goodtweetciwick signaw : d-dweww time  thweshowd >=5s
  g-goodtweetcwick_10s = 1002,// goodtweetciwick signaw : d-dweww time  t-thweshowd >=10s
  goodtweetcwick_30s = 1003,// goodtweetciwick signaw : dweww time  thweshowd >=30s

  g-goodpwofiwecwick_20s = 1004,// goodpwofiwecwick signaw : dweww time  thweshowd >=20s
  goodpwofiwecwick_30s = 1005,// g-goodpwofiwecwick signaw : dweww time  t-thweshowd >=30s

  g-goodpwofiwecwick_fiwtewed = 1006, rawr x3 // goodpwofiwecwick signaw fiwtewed by bwocks a-and mutes. nyaa~~
  g-goodpwofiwecwick_20s_fiwtewed = 1007// goodpwofiwecwick signaw : dweww time  t-thweshowd >=20s, >_< fiwtewed  bybwocks a-and mutes. ^^;;
  goodpwofiwecwick_30s_fiwtewed = 1008,// goodpwofiwecwick signaw : d-dweww time  thweshowd >=30s, (ˆ ﻌ ˆ)♡ fiwtewed by bwocks a-and mutes. ^^;;

  /*
  u-unified signaws
  these signaws a-awe aimed to unify muwtipwe s-signaw fetches i-into a singwe wesponse. (⑅˘꒳˘)
  t-this might be a heawthiew w-way fow ouw w-wetwievaws wayew to wun infewence on. rawr x3
   */
   t-tweetbasedunifiedunifowmsignaw = 1300
   t-tweetbasedunifiedengagementweightedsignaw = 1301
   t-tweetbasedunifiedquawityweightedsignaw = 1302
   pwoducewbasedunifiedunifowmsignaw = 1303
   pwoducewbasedunifiedengagementweightedsignaw = 1304
   p-pwoducewbasedunifiedquawityweightedsignaw = 1305

}

stwuct signaw {
  1: w-wequiwed s-signawtype signawtype
  2: wequiwed i64 timestamp
  3: optionaw identifiew.intewnawid t-tawgetintewnawid
}

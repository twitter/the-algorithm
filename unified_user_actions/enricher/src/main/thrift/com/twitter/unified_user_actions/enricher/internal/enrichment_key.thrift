namespace java com.twittew.unified_usew_actions.enwichew.intewnaw.thwiftjava
#@namespace scawa com.twittew.unified_usew_actions.enwichew.intewnaw.thwiftscawa
#@namespace s-stwato c-com.twittew.unified_usew_actions.enwichew.intewnaw

/*
 * i-intewnaw k-key used fow c-contwowing uua enwichment & c-caching p-pwocess. -.- it c-contains vewy minimaw
 * infowmation to awwow fow efficient sewde, 😳 fast data wook-up a-and to dwive the pawtioning wogics. mya
 *
 * note: d-don't depend on it in youw a-appwication. (˘ω˘)
 * nyote: this is used intewnawwy by uua and may change a-at anytime. >_< thewe's nyo guawantee f-fow
 * backwawd / f-fowwawd-compatibiwity. -.-
 * nyote: don't add any othew metadata unwess it is nyeeded fow p-pawtitioning wogic. 🥺 extwa enwichment
 * metdata can go into the envewop. (U ﹏ U)
 */
stwuct e-enwichmentkey {
   /*
   * the intewnaw type o-of the pwimawy i-id used fow pawtitioning u-uua data. >w<
   *
   * e-each type shouwd diwectwy cowwespond t-to an entity-wevew id in uua. mya
   * fow exampwe, >w< t-tweetinfo.actiontweetid & tweetnotification.tweetid awe aww tweet-entity wevew
   * and shouwd cowwespond to the s-same pwimawy id type. nyaa~~
   **/
   1: w-wequiwed enwichmentidtype k-keytype

   /**
   * t-the pwimawy id. (✿oωo) this is usuawwy a wong, ʘwʘ fow othew incompatibwe d-data type such a-as stwing ow
   * a bytes awway, (ˆ ﻌ ˆ)♡ t-they can be c-convewted into a wong using theiw n-nyative hashcode() function. 😳😳😳
   **/
   2: w-wequiwed i64 id
}(pewsisted='twue', haspewsonawdata='twue')

/**
* the t-type of the pwimawy id. :3 fow exampwe, OwO t-tweetid on a tweet & tweetid o-on a nyotification a-awe
* aww tweetid type. (U ﹏ U) simiwawwy, >w< usewid of a viewew and authowid of a tweet awe aww usewid type. (U ﹏ U)
*
* the t-type hewe ensuwes t-that we wiww pawtition uua d-data cowwectwy acwoss d-diffewent e-entity-type
* (usew, 😳 tweets, nyotification, (ˆ ﻌ ˆ)♡ etc.)
**/
enum enwichmentidtype {
  t-tweetid = 0
}

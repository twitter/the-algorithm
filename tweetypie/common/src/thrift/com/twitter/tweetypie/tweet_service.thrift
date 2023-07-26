namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace p-py gen.twittew.tweetypie.sewvice
n-nyamespace w-wb tweetypie
n-nyamespace go tweetypie

i-incwude "com/twittew/bouncew/bounce.thwift"
incwude "com/twittew/cawousew/sewvice/cawousew_sewvice.thwift"
incwude "com/twittew/context/featuwe_context.thwift"
incwude "com/twittew/mediasewvices/commons/mediacommon.thwift"
incwude "com/twittew/mediasewvices/commons/mediainfowmation.thwift"
i-incwude "com/twittew/sewvo/exceptions.thwift"
incwude "com/twittew/spam/featuwes/safety_meta_data.thwift"
incwude "com/twittew/spam/wtf/safety_wabew.thwift"
i-incwude "com/twittew/spam/wtf/safety_wevew.thwift"
incwude "com/twittew/spam/wtf/safety_wesuwt.thwift"
i-incwude "com/twittew/tseng/withhowding/withhowding.thwift"
incwude "com/twittew/tweetypie/deweted_tweet.thwift"
incwude "com/twittew/tweetypie/twansient_context.thwift"
incwude "com/twittew/tweetypie/tweet.thwift"
i-incwude "com/twittew/tweetypie/tweet_audit.thwift"
incwude "com/twittew/incentives/jiminy/jiminy.thwift"
incwude "unified_cawds_contwact.thwift"

t-typedef i-i16 fiewdid

stwuct tweetgeoseawchwequestid {
  1: wequiwed stwing id (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, (///ˬ///✿) pubwictweetentitiesandmetadata')
}(haspewsonawdata = 'twue')

s-stwuct tweetcweategeo {
  1: optionaw tweet.geocoowdinates coowdinates
  2: o-optionaw stwing pwace_id (pewsonawdatatype = 'infewwedwocation')
  3: o-optionaw m-map<stwing, o.O s-stwing> pwace_metadata (pewsonawdatatypekey = 'infewwedwocation', σωσ p-pewsonawdatatypevawue = 'infewwedwocation')
  4: boow auto_cweate_pwace = 1
  // depwecated; u-use tweet.geocoowdinates.dispway
  5: boow dispway_coowdinates = 1
  6: boow ovewwide_usew_geo_setting = 0
  7: o-optionaw tweetgeoseawchwequestid geo_seawch_wequest_id
}(haspewsonawdata = 'twue')

enum statusstate {
  /**
   * the tweet was found and successfuwwy hydwated. òωó
   */
  f-found              = 0

  /**
   * the t-tweet was nyot found. (///ˬ///✿)  i-it may have b-been deweted, :3 ow couwd just be an invawid ow
   * unused tweet i-id. mya
   */
  nyot_found          = 1

  /**
   * t-the tweet was found, ^^ but thewe w-was at weast one e-ewwow hydwating some data on the t-tweet. (˘ω˘)
   * gettweetwesuwt.missing_fiewds indicates w-which fiewds may have nyot been hydwated c-compwetewy. -.-
   */
  pawtiaw            = 2

  /**
   * @depwecated a-aww faiwuwes, XD incwuding time o-outs, rawr awe indicated b-by `faiwed`. >_<
   */
  timed_out          = 3

  /**
   * thewe was an upstweam ow intewnaw faiwuwe weading this tweet. :3  usuawwy i-indicates a
   * t-twansient issue that is safe t-to wetwy immediatewy. :3
   */
  faiwed             = 4

  /**
   * @depwecated t-tweets f-fwom deactivated usews wiww soon be indicated via `dwop` with
   * a-a `fiwtewedweason` of `authowaccountisinactive`. XD
   */
  deactivated_usew   = 5

  /**
   * @depwecated tweets fwom suspended usews wiww s-soon be indicated via `dwop` with
   * a-a `fiwtewedweason` o-of `authowaccountisinactive`. ( ͡o ω ͡o )
   */
  s-suspended_usew     = 6

  /**
   * @depwecated tweets fwom pwotected u-usews that t-the viewew can't s-see wiww soon b-be
   * indicated via `dwop` with a `fiwtewedweason` o-of `authowispwotected`. rawr x3
   */
  p-pwotected_usew     = 7
  /**
   * @depwecated t-tweets that have b-been wepowted b-by the viewew wiww soon be indicated
   * via `dwop` ow `suppwess` w-with a `fiwtewedweason` of `wepowtedtweet`. (⑅˘꒳˘)
   */
  wepowted_tweet     = 8

  // pwivatetweet was owiginawwy used fow twittewsuggest v-v1 but has since been wemoved
  // obsowete: pwivate_tweet      = 9

  /**
   * c-couwd n-nyot wetuwn this t-tweet because of backpwessuwe, UwU s-shouwd
   * nyot be wetwied immediatewy; t-twy again w-watew
   */
  ovew_capacity      = 10

  /**
   * wetuwned when the wequesting cwient is considewed to nyot be
   * a-abwe to wendew the tweet p-pwopewwy
   */
  unsuppowted_cwient = 11

  /**
   * t-the tweet exists, (˘ω˘) b-but was nyot wetuwned because it shouwd nyot b-be seen by the
   * v-viewew. (˘ω˘)  the weason fow t-the tweet being f-fiwtewed is indicated via
   * gettweetwesuwt.fiwtewed_weason. rawr
   */
  dwop               = 12

  /**
   * the tweet exists and w-was wetuwned, nyaa~~ but s-shouwd nyot be d-diwectwy shown to the
   * usew w-without additionaw u-usew intent to see the tweet, 😳😳😳 a-as it may be offensive. ^^;;
   * the weason fow the suppwession is indicated via gettweetwesuwt.fiwtewed_weason. >w<
   */
  suppwess           = 13

  /**
   * t-the tweet o-once existed and has been deweted. ʘwʘ
   * when g-gettweetoptions.enabwe_deweted_state i-is twue, XD deweted tweets
   * wiww be wetuwned as deweted
   * w-when gettweetoptions.enabwe_deweted_state is fawse, (ˆ ﻌ ˆ)♡ deweted tweets
   * wiww be wetuwned as nyot_found. >_<
   */
  d-deweted            = 14

  /**
   * the tweet once existed, >_< h-had viowated twittew w-wuwes, ʘwʘ and has been deweted. rawr
   * when gettweetoptions.enabwe_deweted_state is twue, nyaa~~ bounce-deweted t-tweets
   * w-wiww be wetuwned as bounce_deweted
   * when gettweetoptions.enabwe_deweted_state i-is fawse, >w< bounce-deweted t-tweets
   * wiww be wetuwned as nyot_found. (ˆ ﻌ ˆ)♡
   */
  bounce_deweted     = 15

  w-wesewved_1         = 16
  wesewved_2         = 17
  w-wesewved_3         = 18
  w-wesewved_4         = 19
}

enum tweetcweatestate {
  /**
   * t-tweet was cweated successfuwwy. :3
   */
  o-ok = 0, OwO

  /**
   * t-the usew_id f-fiewd fwom the cweation wequest d-does nyot cowwespond t-to a usew. mya
   */
  usew_not_found = 1, /(^•ω•^)

  souwce_tweet_not_found = 2, nyaa~~
  s-souwce_usew_not_found = 3, (˘ω˘)

  /**
   * @depwecated u-usews can now w-wetweet theiw own tweets. (ꈍᴗꈍ)
   */
  cannot_wetweet_own_tweet = 4, >w<

  c-cannot_wetweet_pwotected_tweet = 5, nyaa~~
  cannot_wetweet_suspended_usew = 6, (✿oωo)
  c-cannot_wetweet_deactivated_usew = 7, (⑅˘꒳˘)
  c-cannot_wetweet_bwocking_usew = 8, (ˆ ﻌ ˆ)♡

  awweady_wetweeted = 9, òωó
  contwibutow_not_suppowted = 10, -.-

  /**
   * the cweated_via f-fiewd fwom the c-cweation wequest d-does nyot cowwespond t-to a
   * known cwient appwication. 😳😳😳
   */
  d-device_souwce_not_found = 11, rawr x3

  mawwawe_uww = 12, 😳
  invawid_uww = 13, 🥺
  usew_deactivated = 14, (⑅˘꒳˘)
  usew_suspended = 15, (✿oωo)
  text_too_wong = 16, 😳
  t-text_cannot_be_bwank = 17, mya
  dupwicate = 18, (U ﹏ U)

  /**
   * p-posttweetwequest.in_wepwy_to_tweet_id was set to a tweet t-that cannot be found. 😳
   *
   * t-this usuawwy means that the tweet w-was wecentwy d-deweted, 🥺 but couwd a-awso
   * mean t-that the tweet i-isn't visibwe to the wepwy authow. -.- (this is the
   * case fow wepwies by bwocked usews.)
   */
  in_wepwy_to_tweet_not_found = 19, (ˆ ﻌ ˆ)♡

  i-invawid_image = 20, >_<
  invawid_additionaw_fiewd = 21, rawr
  w-wate_wimit_exceeded = 22, rawr x3
  i-invawid_nawwowcast = 23, OwO

  /**
   * antispam systems (scawecwow) d-denied the wequest. nyaa~~
   *
   * this happens fow tweets t-that awe pwobabwy s-spam, 😳 but thewe is some
   * u-uncewtainty. UwU tweets that scawecwow is cewtain a-awe spammy wiww a-appeaw to
   * succeed, ʘwʘ but wiww n-nyot be added t-to backends. 🥺
   */
  spam = 24, 🥺
  spam_captcha = 25, òωó

  /**
   * a pwovided media upwoad id can't b-be wesowved. 🥺
   */
  m-media_not_found = 26, ʘwʘ

  /**
   * c-catch-aww f-fow when upwoaded m-media viowate some condition. XD
   *
   * f-fow e-exampwe, OwO too many photos in a muwti-photo-set, o-ow incwuding an
   * a-animated gif ow video in a m-muwti-photo-set. ʘwʘ
   */
  invawid_media = 27, :3

  /**
   * wetuwned w-when scawecwow teww us to wate w-wimit a tweet wequest. nyaa~~
   *
   * n-nyon vewified usews (i.e., phone v-vewified, >w< emaiw vewified) have mowe
   * stwict w-wate wimit. (U ᵕ U❁)
   */
  s-safety_wate_wimit_exceeded = 28, :3

  /**
   * s-scawecwow has wejected the cweation wequest untiw the usew compwetes t-the
   * bounce assignment. (ˆ ﻌ ˆ)♡
   *
   * this fwag indicates t-that posttweetwesuwt.bounce wiww c-contain a bounce
   * stwuct t-to be pwopagated to the cwient. o.O
   */
  b-bounce = 29, rawr x3

  /**
   * t-tweet cweation was denied because the usew is i-in weadonwy mode. (U ᵕ U❁)
   *
   * as with spam, (✿oωo) tweets w-wiww appeaw to s-succeed but wiww not be actuawwy
   * c-cweated. /(^•ω•^)
   */
  usew_weadonwy = 30, o.O

  /**
   * m-maximum nyumbew o-of mentions a-awwowed in a tweet was exceeded. (U ᵕ U❁)
   */
  mention_wimit_exceeded = 31, 🥺

  /**
   * maximum nyumbew of uwws awwowed in a tweet was exceeded. òωó
   */
  uww_wimit_exceeded = 32, ʘwʘ

  /**
   * maximum numbew of hashtags awwowed in a tweet was exceeded. rawr x3
   */
  hashtag_wimit_exceeded = 33, >_<

  /**
   * maximum n-nyumbew of cashtags a-awwowed in a tweet was exceeded. (˘ω˘)
   */
  cashtag_wimit_exceeded = 34, ^•ﻌ•^

  /**
   * m-maximum wength o-of a hashtag w-was exceeded. (✿oωo)
   */
  hashtag_wength_wimit_exceeded = 35, ( ͡o ω ͡o )

  /**
   * w-wetuwned if a wequest contains m-mowe than o-one attachment type, (˘ω˘) which
   * i-incwudes media, >w< attachment_uww, (⑅˘꒳˘) a-and cawd_wefewence. (U ᵕ U❁)
   */
  t-too_many_attachment_types = 36, OwO

  /**
   * wetuwned if the wequest c-contained an attachment u-uww that i-isn't awwowed. òωó
   */
  i-invawid_attachment_uww = 37, ^•ﻌ•^

  /**
   * w-we don't awwow u-usews without scween n-nyames to b-be wetweeted. 😳😳😳
   */
  c-cannot_wetweet_usew_without_scween_name = 38, o.O

  /**
   * tweets may nyot b-be awwowed if wepwying o-ow wetweeting i-ipi'd tweets
   * see go/tp-ipi-tdd f-fow mowe detaiws
   */
  disabwed_by_ipi_powicy = 39, :3

  /**
   * t-this state expands ouw t-twanspawency awound w-which uwws a-awe bwackwisted ow wimited
   */
  u-uww_spam = 40, ^•ﻌ•^

  // convewsation c-contwows awe onwy vawid when p-pwesent on a woot
  // convewsation t-tweet and quoted tweets. >w<
  invawid_convewsation_contwow = 41, :3

  // wepwy tweet is wimited d-due to convewsation contwows state s-set on
  // w-woot convewsation tweet. (✿oωo)
  wepwy_tweet_not_awwowed = 42, rawr

  // nyudge is wetuwned when the cwient p-pwovides nyudgeoptions and tweetypie w-weceives a-a nyudge
  // fwom t-the jiminy stwato cowumn. UwU
  nyudge = 43, (⑅˘꒳˘)

  // a-apiewwow badwequest (400) "wepwy t-to a community tweet must awso b-be a community tweet"
  // -- twiggewed when a-a usew twies wepwying to a community t-tweet with a-a nyon community t-tweet. σωσ
  community_wepwy_tweet_not_awwowed = 44, (///ˬ///✿)
  // apiewwow f-fowbidden (403) "usew i-is nyot authowized t-to post t-to this community"
  // -- twiggewed w-when a usew t-twies posting t-to a pubwic/cwosed c-community that t-they awe nyot p-pawt of. (˘ω˘)
  community_usew_not_authowized = 45, ^•ﻌ•^
  // a-apiewwow notfound (404)  "community d-does not exist" -- twiggewed w-when:
  //  a) a usew twies p-posting to a pwivate community t-they awe nyot a p-pawt of. ʘwʘ
  //  b) a-a usew twies posting to a nyon existent community
  community_not_found = 46, 😳
  // a-apiewwow badwequest (400) "cannot w-wetweet a c-community tweet"
  // -- twiggewed when a usew twies to wetweet a-a community tweet. òωó c-community tweets can nyot be w-wetweeted. ( ͡o ω ͡o )
  community_wetweet_not_awwowed = 47, :3

  // a-attempt to tweet with convewsation contwows was wejected, (ˆ ﻌ ˆ)♡ e-e.g. XD due to featuwe s-switch authowization. :3
  c-convewsation_contwow_not_awwowed = 48, nyaa~~

  // s-supew fowwow tweets wequiwe a speciaw p-pewmission to cweate. 😳😳😳
  s-supew_fowwows_cweate_not_authowized = 49, (⑅˘꒳˘)

  // nyot aww pawams can go t-togethew. ^^ e.g. supew fowwow tweets can nyot be community t-tweets. 🥺
  supew_fowwows_invawid_pawams = 50,

  // a-apiewwow f-fowbidden (403) "pwotected usew can nyot post t-to communities"
  // -- t-twiggewed when a pwotected u-usew twies tweeting ow wepwying
  // t-to a c-community tweet. OwO t-they awe nyot awwowed t-to cweate community tweets. ^^
  c-community_pwotected_usew_cannot_tweet = 51, nyaa~~

  // a-apiewwow f-fowbidden (451) "usew is nyot pewmitted t-to engage with this excwusive tweet."
  // -- t-twiggewed w-when a usew twies t-to wepwy to an excwusive tweet without being
  // a supewfowwowew of the tweet a-authow. ^^ couwd be used fow othew e-engagements in t-the futuwe (e.g. (✿oωo) favowite)
  excwusive_tweet_engagement_not_awwowed = 52

  /**
   * apiewwow badwequest (400) "invawid p-pawametews on twusted fwiends t-tweet cweation"
   *
   * w-wetuwned when eithew o-of the fowwowing o-occuw:
   *   a-a) a usew twies setting twusted fwiends contwow on a wepwy
   *   b) a usew t-twies setting twusted fwiends contwow o-on a tweet with any of the fowwowing set:
   *     i) convewsation c-contwow
   *     ii) community
   *     iii) excwusive tweet contwow
   */
  twusted_fwiends_invawid_pawams = 53, ^^

  /**
   * a-apiewwow f-fowbidden (403)
   *
   * wetuwned w-when a usew twies to wetweet a twusted fwiends t-tweet. òωó
   */
  t-twusted_fwiends_wetweet_not_awwowed = 54, (⑅˘꒳˘)

  /**
   * apiewwow f-fowbidden (457)
   *
   * wetuwned w-when a usew twies to wepwy to a twusted fwiends tweet
   * and t-they awe nyot a twusted fwiend. (U ﹏ U)
   */
  twusted_fwiends_engagement_not_awwowed = 55, OwO

 /**
  * a-apiewwow badwequest (400) "invawid p-pawametews fow c-cweating a cowwabtweet ow cowwabinvitation"
  *
  * wetuwned w-when any of the fowwowing awe twue:
   *   a) a usew twies setting cowwab contwow o-on a wepwy
   *   b-b) a usew twies s-setting cowwab c-contwow on a tweet with any of the fowwowing s-set:
   *     i) c-convewsation contwow
   *     ii) community
   *     iii) excwusive t-tweet contwow
   *     iv) twusted fwiends c-contwow
  **/
  cowwab_tweet_invawid_pawams = 56, (///ˬ///✿)

  /**
   * apiewwow f-fowbidden (457)
   *
   * w-wetuwned when a usew twies to cweate a-a twusted f-fwiends tweet but t-they awe nyot awwowed to tweet
   * to the wequested t-twusted fwiends wist. o.O
   */
  twusted_fwiends_cweate_not_awwowed = 57, (ꈍᴗꈍ)

  /**
   * w-wetuwned when the cuwwent usew is nyot awwowed to edit i-in genewaw, -.- this m-might be due to m-missing
   * wowes d-duwing devewopment, òωó o-ow a missing subscwiption. OwO
   */
  e-edit_tweet_usew_not_authowized = 58, (U ﹏ U)

  /**
   * wetuwned when a usew t-twies to edit a tweet which they d-didn't authow. ^^;;
   */
  edit_tweet_usew_not_authow = 59, ^^;;

  /**
   * wetuwned w-when a usew twies e-edit a stawe tweet, XD meaning a t-tweet which has awweady been edited. OwO
   */
  e-edit_tweet_not_watest_vewsion = 60, (U ﹏ U)

  /**
   * a-apiewwow fowbidden (460)
   *
   * w-wetuwned when a u-usew twies to cweate a twusted fwiends t-tweet that quotes tweets a twusted
   * fwiends tweet. >w<
   */
  t-twusted_fwiends_quote_tweet_not_awwowed = 61, >w<

  /**
   * wetuwned when a u-usew twies edit a tweet fow which the editing time h-has awweady expiwed. (ˆ ﻌ ˆ)♡
   */
  e-edit_time_wimit_weached = 62, (ꈍᴗꈍ)

  /**
   * w-wetuwned when a usew twies e-edit a tweet w-which has been awweady edited m-maximum numbew of times. 😳😳😳
   */
  e-edit_count_wimit_weached = 63, mya

  /* wetuwned when a-a usew twies t-to edit a fiewd that is nyot awwowed to be edited */
  fiewd_edit_not_awwowed = 64, (˘ω˘)

  /* wetuwned w-when the initiaw t-tweet couwd nyot be found when twying to vawidate an edit */
  i-initiaw_tweet_not_found = 65, (✿oωo)

  /**
   * apiewwow f-fowbidden (457)
   *
   * w-wetuwned when a usew twies to wepwy to a stawe tweet
   */
  stawe_tweet_engagement_not_awwowed = 66, (ˆ ﻌ ˆ)♡

  /**
   * apiewwow fowbidden (460)
   *
   * w-wetuwned when a usew twies to cweate a tweet t-that quotes tweets a stawe tweet
   */
  s-stawe_tweet_quote_tweet_not_awwowed = 67, (ˆ ﻌ ˆ)♡

  /* t-tweet cannot be edited b-because the initiaw t-tweet is
  * m-mawked as nyot e-edit ewigibwe */
  n-nyot_ewigibwe_fow_edit = 68, nyaa~~

  /* a-a stawe vewsion of an edit tweet cannot be wetweeted
  *  onwy watest vewsion of an edit c-chain shouwd be a-awwowed to be w-wetweeted. :3 */
  s-stawe_tweet_wetweet_not_awwowed = 69, (✿oωo)

  w-wesewved_32 = 70, (✿oωo)
  w-wesewved_33 = 71, (⑅˘꒳˘)
  wesewved_34 = 72, >_<
  wesewved_35 = 73, >_<
  wesewved_36 = 74, ʘwʘ
  wesewved_37 = 75, (U ﹏ U)
}

e-enum undewetetweetstate {
  /**
   * t-the tweet was successfuwwy undeweted. ^^
   */
  success = 0, >_<

  /**
   * t-the t-tweet was deweted a-and is stiww deweted. OwO it cannot be undeweted
   * b-because the tweet is nyo wongew in the soft d-dewete awchive. 😳
   */
  s-soft_dewete_expiwed = 1, (U ᵕ U❁)

  /**
   * the tweet wikewy has nyevew existed, 😳😳😳 a-and thewefowe cannot be undeweted. -.-
   */
  tweet_not_found = 2, (U ᵕ U❁)

  /**
   * t-the tweet couwd n-nyot be undeweted because it was n-nyot deweted in
   * t-the fiwst p-pwace. -.-
   */
  tweet_awweady_exists = 3, (U ﹏ U)

  /**
   * t-the usew who c-cweated the tweet b-being undeweted couwd nyot be f-found. ^^
   */
  u-usew_not_found = 4, UwU

  /**
   * the tweet couwd n-not be undeweted because it is a wetweet and the o-owiginaw
   * tweet is gone. o.O
   */
  s-souwce_tweet_not_found = 5, ^^

  /**
   * the tweet couwd nyot b-be undeweted b-because it is a wetweet and the authow
   * of t-the owiginaw tweet is gone. 🥺
   */
  souwce_usew_not_found = 6, 😳

  /**
   * t-the tweet w-was deweted and is stiww deweted. (⑅˘꒳˘) it cannot b-be undeweted
   * b-because the tweet has been bounce d-deweted. >w< bounce deweted tweet
   * has been f-found to viowate t-twittew wuwes. >_< go/bouncew go/bounced-tweet
   */
  t-tweet_is_bounce_deweted = 7, rawr x3

  /**
  * t-this tweet cannot be undeweted because t-the tweet was c-cweated by a
  * u-usew when they w-wewe undew 13. >_<
  **/
  tweet_is_u13_tweet = 8, XD

  wesewved_2 = 9, mya
  wesewved_3 = 10
}

enum tweetdewetestate {
  /**
   * tweet was deweted successfuwwy.
   */
  o-ok = 0, (///ˬ///✿)

  /**
   * t-tweet was n-not deweted because o-of the associated u-usew. OwO
   *
   * t-the dewetetweetswequest.by_usew_id must m-match the tweet o-ownew ow be an
   * admin usew. mya
   */
  p-pewmission_ewwow = 1, OwO

  /**
   * t-the expected_usew_id pwovided in dewetetweetswequest does nyot match the
   * u-usew_id of the tweet ownew. :3
   */
  expected_usew_id_mismatch = 2, òωó

  /**
   * @depwecated. OwO
   *
   * i-is_usew_ewasuwe was s-set in dewetetweetswequest b-but the usew was nyot i-in
   * the ewased s-state. OwO
   */
  u-usew_not_in_ewased_state = 3, (U ᵕ U❁)

  /**
   * faiwed to woad the s-souwce tweet whiwe u-unwetweeting stawe wevisions i-in an edit chain.
   */
  souwce_tweet_not_found = 4, mya

  w-wesewved_4 = 5, UwU
  w-wesewved_5 = 6, /(^•ω•^)
  wesewved_6 = 7, UwU
  w-wesewved_7 = 8
}

enum dewetedtweetstate {
  /**
   * t-the tweet has been mawked as deweted but h-has not been pewmanentwy deweted. UwU
   */
  soft_deweted = 1

  /**
   * the tweet has nyevew existed. /(^•ω•^)
   */
  nyot_found    = 2

  /**
   * the tweet h-has been pewmanentwy deweted. XD
   */
  hawd_deweted = 3

  /**
   * the tweet exists and is nyot cuwwentwy deweted.
   */
  nyot_deweted  = 4

  w-wesewved1    = 5
  wesewved2    = 6
  wesewved3    = 7
}

/**
 * h-hydwations to pewfowm on the t-tweet wetuwned by post_tweet and post_wetweet.
 */
s-stwuct wwitepathhydwationoptions {
  /**
   * wetuwn cawds f-fow tweets with cawds in tweet.cawds o-ow tweet.cawd2
   *
   * cawd2 a-awso wequiwes setting a vawid cawds_pwatfowm_key
   */
  1: b-boow incwude_cawds = 0

  /**
   * the cawd fowmat vewsion suppowted by the wequesting c-cwient
   */
  2: optionaw s-stwing cawds_pwatfowm_key

  # 3: obsowete
  # 4: o-obsowete

  /**
   * the awgument p-passed to t-the stwatostowe extension points mechanism. ^^;;
   */
  5: o-optionaw binawy extensions_awgs

  /**
   * when wetuwning a-a tweet that quotes anothew tweet, nyaa~~ do nyot incwude
   * the uww to the quoted t-tweet in the tweet t-text and uww entities. mya
   * t-this is intended f-fow cwients that use the quoted_tweet f-fiewd of
   * the tweet to dispway quoted tweets. (✿oωo) awso see simpwe_quoted_tweet
   * f-fiewd i-in gettweetoptions and gettweetfiewdsoptions
   */
  6: b-boow simpwe_quoted_tweet = 0
}

s-stwuct wetweetwequest {
  /**
   * i-id of the tweet being wetweeted. rawr
   */
  1: w-wequiwed i64 souwce_status_id (pewsonawdatatype = 'tweetid')

  /**
   * usew cweating the w-wetweet. -.-
   */
  2: w-wequiwed i64 usew_id (pewsonawdatatype = 'usewid')

  /**
   * @see posttweetwequest.cweated_via
   */
  3: w-wequiwed stwing cweated_via (pewsonawdatatype = 'cwienttype')
  4: optionaw i64 contwibutow_usew_id (pewsonawdatatype = 'usewid') // nyo wongew suppowted

  /**
   * @see posttweetwequest.twacking_id
   */
  5: optionaw i64 t-twacking_id (pewsonawdatatype = 'impwessionid')
  6: o-optionaw tweet.nawwowcast n-nyawwowcast

  /**
   * @see posttweetwequest.nuwwcast
   */
  7: b-boow nyuwwcast = 0

  /**
   * @see posttweetwequest.dawk
   */
  8: b-boow dawk = 0

  // obsowete 9: boow send_wetweet_sms_push = 0

  10: optionaw wwitepathhydwationoptions hydwation_options

  /**
   * @see posttweetwequest.additionaw_fiewds
   */
  11: o-optionaw tweet.tweet additionaw_fiewds

  /**
   * @see posttweetwequest.uniqueness_id
   */
  12: optionaw i64 uniqueness_id (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, σωσ p-pubwictweetentitiesandmetadata')

  13: o-optionaw featuwe_context.featuwecontext f-featuwe_context

  14: boow wetuwn_success_on_dupwicate = 0

  /**
   * passthwough data fow scawecwow that i-is used fow s-safety checks. mya
   */
  15: o-optionaw safety_meta_data.safetymetadata s-safety_meta_data

  /**
   * this is a unique i-identifiew used in both the west a-and gwaphqw-dawk
   * wequests t-that wiww be used to cowwewate the gwaphqw mutation w-wequests to the west wequests
   * d-duwing a-a twansition pewiod when cwients w-wiww be moving t-towawd tweet cweation via gwaphqw. ^•ﻌ•^
   * s-see awso, the "compawison t-testing" section at go/tweet-cweate-on-gwaphqw-tdd f-fow additionaw
   * c-context. nyaa~~
   */
  16: optionaw stwing compawison_id (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid')
}(haspewsonawdata = 'twue')

/**
 * a-a wequest to set ow unset nysfw_admin and/ow nysfw_usew. 🥺
 */
stwuct updatepossibwysensitivetweetwequest {
  /**
   * id of tweet being updated
   */
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')

  /**
   * i-id of the usew initiating this wequest. (✿oωo)
   *
   * i-it couwd be eithew the ownew o-of the tweet ow an admin. rawr it is used when
   * a-auditing the wequest in guano. (ˆ ﻌ ˆ)♡
   */
  2: wequiwed i-i64 by_usew_id (pewsonawdatatype = 'usewid')

  /**
   * nyew vawue fow tweet.cowe_data.nsfw_admin. ^^;;
   */
  3: o-optionaw boow nysfw_admin

  /**
   * nyew vawue f-fow tweet.cowe_data.nsfw_usew. OwO
   */
  4: optionaw boow nysfw_usew

  /**
   * h-host ow wemote i-ip whewe the wequest owiginated. mya
   *
   * this d-data is used when a-auditing the wequest in guano. (⑅˘꒳˘) i-if unset, (U ﹏ U) it wiww
   * b-be wogged as "<unknown>". (U ﹏ U)
   */
  5: optionaw s-stwing host (pewsonawdatatype = 'ipaddwess')

  /**
   * pass-thwough message sent to the audit sewvice. XD
   */
  6: o-optionaw stwing nyote
}(haspewsonawdata = 'twue')

stwuct updatetweetmediawequest {
  /**
   * t-the tweet i-id that's being u-updated
   */
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')

  /**
   * a mapping f-fwom owd (existing) media ids o-on the tweet to nyew media ids. OwO
   *
   * e-existing t-tweet media nyot in this map wiww wemain unchanged. (///ˬ///✿)
   */
  2: wequiwed map<i64, XD i64> owd_to_new_media_ids (pewsonawdatatypekey = 'mediaid', σωσ pewsonawdatatypevawue = 'mediaid')
}(haspewsonawdata = 'twue')

s-stwuct takedownwequest {
  1: w-wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')

  /**
   * the wist of takedown c-countwy codes to add to the tweet. (///ˬ///✿)
   *
   * d-depwecated, 😳 weasons_to_add s-shouwd b-be used instead. rawr x3
   */
  2: wist<stwing> c-countwies_to_add = [] (pewsonawdatatype = 'contentwestwictionstatus')

  /**
   * t-this f-fiewd is the wist of takedown countwy codes to w-wemove fwom the t-tweet. 😳
   *
   * d-depwecated, ^^;; weasons_to_wemove s-shouwd be used i-instead. òωó
   */
  3: w-wist<stwing> countwies_to_wemove = [] (pewsonawdatatype = 'contentwestwictionstatus')

  /**
   * t-this fiewd i-is the wist of t-takedown weasons to add to the tweet. >w<
   */
  11: wist<withhowding.takedownweason> w-weasons_to_add = []

  /**
   * this fiewd is the wist of takedown w-weasons to wemove fwom the tweet. >w<
   */
  12: w-wist<withhowding.takedownweason> w-weasons_to_wemove = []

  /**
   * motivation fow the takedown which is wwitten t-to the audit s-sewvice. òωó
   *
   * this data is n-nyot pewsisted w-with the takedown itsewf. 😳😳😳
   */
  4: optionaw stwing audit_note (pewsonawdatatype = 'auditmessage')

  /**
   * w-whethew to send t-this wequest to the audit sewvice. ( ͡o ω ͡o )
   */
  5: boow scwibe_fow_audit = 1

  // depwecated, o.O t-this f-fiewd is nyo wongew used. UwU
  6: boow set_has_takedown = 1

  // depwecated, rawr t-this fiewd is nyo wongew used. mya
  7: optionaw wist<stwing> pwevious_takedown_countwy_codes (pewsonawdatatype = 'contentwestwictionstatus')

  /**
   * whethew this wequest s-shouwd enqueue a tweettakedownevent to eventbus a-and
   * hosebiwd. (✿oωo)
   */
  8: b-boow eventbus_enqueue = 1

  /**
   * i-id of the usew who initiated t-the takedown. ( ͡o ω ͡o )
   *
   * this i-is used when w-wwiting the takedown t-to the audit s-sewvice. nyaa~~ if unset, (///ˬ///✿) it
   * wiww be wogged as -1. 😳😳😳
   */
  9: optionaw i-i64 by_usew_id (pewsonawdatatype = 'usewid')

  /**
   * h-host ow wemote i-ip whewe the wequest owiginated. UwU
   *
   * t-this d-data is used when a-auditing the wequest in guano. 🥺 i-if unset, (///ˬ///✿) it wiww
   * b-be wogged a-as "<unknown>". (⑅˘꒳˘)
   */
  10: o-optionaw s-stwing host (pewsonawdatatype = 'ipaddwess')
}(haspewsonawdata = 'twue')

// awguments to d-dewete_wocation_data
stwuct dewetewocationdatawequest {
  1: i-i64 u-usew_id (pewsonawdatatype = 'usewid')
}(haspewsonawdata = 'twue')

// stwucts fow api v2 (fwexibwe schema)

stwuct g-gettweetoptions {
  /**
   * w-wetuwn the owiginaw tweet in gettweetwesuwt.souwce_tweet f-fow wetweets. (✿oωo)
   */
  1: b-boow incwude_souwce_tweet = 1

  /**
   * wetuwn the hydwated p-pwace object in t-tweet.pwace fow t-tweets with geowocation. òωó
   */
  2: b-boow incwude_pwaces = 0

  /**
   * w-wanguage u-used fow pwace nyames when incwude_pwaces is t-twue. ^^ awso passed to
   * the cawds sewvice, rawr if cawds awe hydwated fow the wequest. ^^;;
   */
  3: stwing w-wanguage_tag = "en"

  /**
   * w-wetuwn cawds fow tweets with cawds in tweet.cawds ow tweet.cawd2
   *
   * c-cawd2 awso wequiwes s-setting a vawid cawds_pwatfowm_key
   */
  4: boow incwude_cawds = 0

  /**
   * w-wetuwn the nyumbew of times a-a tweet has been w-wetweeted in
   * t-tweet.counts.wetweet_count. (ˆ ﻌ ˆ)♡
   */
  5: boow incwude_wetweet_count = 0

  /**
   * wetuwn the n-nyumbew of diwect wepwies to a t-tweet in
   * tweet.counts.wepwy_count. (⑅˘꒳˘)
   */
  6: boow incwude_wepwy_count = 0

  /**
   * w-wetuwn the nyumbew of favowites a tweet h-has weceived in
   * tweet.counts.favowite_count. ( ͡o ω ͡o )
   */
  7: b-boow incwude_favowite_count = 0

  # obsowete 8: boow incwude_unique_usews_impwessed_count = 0
  # o-obsowete 9: boow incwude_cwick_count = 0
  # o-obsowete 10: boow incwude_descendent_wepwy_count = 0

  /**
   * @depwecated use safety_wevew fow spam fiwtewing. 🥺
   */
  11: optionaw tweet.spamsignawtype spam_signaw_type

  /**
   * if the w-wequested tweet i-is nyot awweady i-in cache, ^^;; do nyot a-add it. o.O
   *
   * you shouwd set do_not_cache t-to twue if you awe wequesting owd tweets
   * (owdew than 30 days) a-and they awe u-unwikewy to be w-wequested again. rawr
   */
  12: b-boow do_not_cache = 0

  /**
   * the cawd fowmat vewsion suppowted by the wequesting c-cwient
   */
  13: o-optionaw stwing cawds_pwatfowm_key (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, (⑅˘꒳˘) pubwictweetentitiesandmetadata')

  /**
   * the u-usew fow whose pewspective this w-wequest shouwd b-be pwocessed. 😳
   *
   * i-if you awe wequesting tweets on behawf of a usew, nyaa~~ set this to theiw usew
   * id. ^•ﻌ•^ the effect o-of setting this option is:
   *
   * - t-tweetypie wiww wetuwn pwotected tweets that the usew i-is awwowed to
   *   access, (⑅˘꒳˘) wathew t-than fiwtewing out pwotected tweets. σωσ
   *
   * - i-if this fiewd i-is set *and* `incwude_pewspectivaws` i-is set, (U ᵕ U❁) t-then the
   *   t-tweets wiww have the `pewspective` f-fiewd set to a-a stwuct with fwags
   *   that i-indicate whethew the usew has favowited, o.O wetweeted, o-ow wepowted
   *   the tweet i-in question. >w<
   *
   * i-if you have a specific n-nyeed to access a-aww pwotected tweets (not
   * just tweets that shouwd be accessibwe to the cuwwent u-usew), (///ˬ///✿) see the
   * d-documentation f-fow `incwude_pwotected`. :3
   */
  14: o-optionaw i64 fow_usew_id (pewsonawdatatype = 'usewid')

  /**
   * do nyot enfowce nyowmaw f-fiwtewing fow pwotected tweets, ^^;; bwocked quote t-tweets, òωó
   * contwibutow data, nyaa~~ etc. this does n-nyot affect visibiwity wibwawy (http://go/vf)
   * based fiwtewing which exekawaii~s w-when safety_wevew is specified, /(^•ω•^) s-see wequest
   * f-fiewd 24 s-safety_wevew bewow
   *
   * if `bypass_visibiwity_fiwtewing` is t-twue, 😳 tweetypie w-wiww nyot enfowce fiwtewing
   * f-fow pwotected t-tweets, òωó bwocked q-quote tweets, (⑅˘꒳˘) contwibutow d-data, ^•ﻌ•^ etc. and youw cwient
   * w-wiww w-weceive aww tweets w-wegawdwess of fowwow wewationship. y-you wiww awso be abwe
   * to access tweets fwom deactivated and suspended usews. o.O this is o-onwy nyecessawy
   * f-fow speciaw cases, σωσ such as i-indexing ow anawyzing tweets, ow administwatow access. 😳
   * s-since t-this ewevated a-access is usuawwy u-unnecessawy, (ˆ ﻌ ˆ)♡ and is a secuwity w-wisk, (///ˬ///✿) you wiww
   * nyeed to get youw cwient id w-whitewisted to a-access this featuwe. (///ˬ///✿)
   *
   * if you awe accessing tweets on behawf of a usew, s-set
   * `bypass_visibiwity_fiwtewing` to fawse a-and set `fow_usew_id`. >_< this wiww
   * awwow access t-to exactwy the set of tweets t-that that usew is authowized to
   * access, XD and f-fiwtew out tweets the usew shouwd n-nyot be authowized to access
   * (wetuwned with a-a statusstate o-of pwotected_usew). (U ﹏ U)
   */
  15: boow bypass_visibiwity_fiwtewing = 0

  /**
   * wetuwn the usew-specific v-view of a tweet in tweet.pewspective
   *
   * fow_usew_id m-must awso b-be set. ( ͡o ω ͡o )
   */
  16: b-boow incwude_pewspectivaws = 0

  // obsowete media faces awe awways incwuded
  17: boow incwude_media_faces = 0

  /**
   * the fwexibwe schema f-fiewds of the tweet to wetuwn. ^•ﻌ•^
   *
   * fiewds of tweets i-in the 100+ wange w-wiww onwy be wetuwned if they awe
   * expwicitwy w-wequested. 😳
   */
  18: w-wist<fiewdid> additionaw_fiewd_ids = []

  // obsowete 19: boow incwude_topic_wabews = 0

  /**
   * e-excwude usew-wepowted tweets fwom t-this wequest. (ˆ ﻌ ˆ)♡ onwy appwicabwe if
   * fowusewid i-is set. (ˆ ﻌ ˆ)♡
   *
   * u-usews can wepowt individuaw t-tweets in the ui a-as unintewesting, rawr x3 spam,
   * sensitive, rawr x3 o-ow abusive. (U ᵕ U❁)
   */
  20: boow excwude_wepowted = 0

  // i-if set to twue, (ꈍᴗꈍ) d-disabwes suggested t-tweet visibiwity c-checks
  // o-obsowete (twittewsuggestinfo vewsion o-of suggested t-tweets has been wemoved)
  21: boow obsowete_skip_twittew_suggests_visibiwity_check = 0
  // o-obsowete 22: optionaw set<tweet.spamsignawtype> s-spam_signaw_types

  /**
   * wetuwn the quoted tweet in gettweetwesuwt.quoted_tweet
   */
  23: boow incwude_quoted_tweet = 0

  /**
   * content fiwtewing powicy t-that wiww be used to dwop ow s-suppwess tweets
   * fwom wesponse. (ꈍᴗꈍ) t-the fiwtewing i-is based on the wesuwt of visibiwity w-wibwawy
   * and does nyot a-affect fiwtewing of tweets fwom b-bwocked ow nyon-fowwowed pwotected usews, OwO see
   * wequest fiewd 15 bypass_visibiwity_fiwtewing above
   *
   * if nyot specified s-safetywevew.fiwtewdefauwt wiww be used. nyaa~~
   */
  24: optionaw s-safety_wevew.safetywevew safety_wevew

  // o-obsowete 25: boow incwude_animated_gif_media_entities = 0
  26: boow incwude_pwofiwe_geo_enwichment = 0
  // obsowete 27: optionaw set<stwing> extensions
  28: boow i-incwude_tweet_pivots = 0

  /**
   * t-the awgument p-passed to the stwatostowe extension p-points m-mechanism. 🥺
   */
  29: o-optionaw binawy extensions_awgs

  /**
   * wetuwn the nyumbew o-of times a t-tweet has been quoted in tweet.counts.quote_count
   */
  30: boow i-incwude_quote_count = 0

  /**
   * w-wetuwn media m-metadata fwom m-mediainfosewvice i-in mediaentity.additionaw_metadata
   */
  31: boow incwude_media_additionaw_metadata = 0

  /**
   * p-popuwate t-the convewsation_muted f-fiewd o-of the tweet fow t-the wequesting
   * u-usew. ^•ﻌ•^
   *
   * s-setting this t-to twue wiww have n-nyo effect unwess f-fow_usew_id is set. /(^•ω•^)
   */
  32: boow incwude_convewsation_muted = 0

  /**
   * @depwecated go/sunsetting-cawousews
   */
  33: b-boow incwude_cawousews = 0

  /**
   * when e-enabwe_deweted_state is twue and we have evidence t-that the
   * t-tweet once existed a-and was deweted, (U ﹏ U) tweetypie w-wetuwns
   * statusstate.deweted o-ow statusstate.bounce_deweted. :3 (see comments
   * on statusstate fow detaiws on these two states.)
   *
   * when e-enabwe_deweted_state is fawse, ^^;; deweted tweets awe
   * wetuwned a-as statusstate.not_found. >w<
   *
   * n-nyote: even when enabwe_deweted_state i-is t-twue, nyaa~~ a deweted t-tweet may
   * stiww b-be wetuwned a-as statusstate.not_found d-due to e-eventuaw
   * consistency. ^^
   *
   * this option is fawse by defauwt f-fow compatibiwity with cwients
   * e-expecting statusstate.not_found. 😳
   */
  34: b-boow enabwe_deweted_state = 0

  /**
   * p-popuwate the convewsation_ownew_id fiewd of the t-tweet fow the wequesting
   * usew. :3 which twanswate into is_convewsation_ownew i-in biwdhewd
   *
   */
  // o-obsowete 35: b-boow incwude_convewsation_ownew_id = 0

  /**
   * p-popuwate the is_wemoved_fwom_convewsation f-fiewd of the t-tweet fow the w-wequesting
   * usew. 🥺
   *
   */
  // o-obsowete 36: boow incwude_is_wemoved_fwom_convewsation = 0

  // to wetwieve sewf-thwead metadata wequest fiewd tweet.sewfthweadmetadatafiewd
  // obsowete 37: boow incwude_sewf_thwead_info = 0

  /**
   * this option s-suwfaces cawdwefewence f-fiewd (118) in tweet thwift object.
   * we use cawd_uwi pwesent in cawd w-wefewence, :3 to get a-access to stowed cawd infowmation. >_<
   */
  37: boow incwude_cawd_uwi = 0

  /**
   * when wetuwning a-a tweet that q-quotes anothew tweet, 🥺 do nyot i-incwude
   * the u-uww to the quoted tweet in the t-tweet text and uww entities. ^•ﻌ•^
   * t-this is intended f-fow cwients that use the quoted_tweet fiewd of
   * the tweet t-to dispway quoted t-tweets. >w<
   */
  38: b-boow simpwe_quoted_tweet = 0

  /**
   * t-this fwag is used and onwy take a-affect if the w-wequested tweet i-is  cweatives containew b-backed
   * tweet. rawr this wiww supwwess the t-tweet matewiawization a-and wetuwn tweet nyot found. :3
   *
   * go/cweatives-containews-tdd
  **/
  39: boow disabwe_tweet_matewiawization = 0

   
  /**
   * used fow woad shedding. i-if set to t-twue, OwO tweetypie sewvice might shed t-the wequest, if the sewvice 
   * is stwuggwing. 😳
  **/
  40: optionaw boow is_wequest_sheddabwe

}(haspewsonawdata = 'twue')

s-stwuct gettweetswequest {
  1: w-wequiwed wist<i64> t-tweet_ids (pewsonawdatatype = 'tweetid')
  // @depwecated unused
  2: o-optionaw w-wist<i64> souwce_tweet_id_hints (pewsonawdatatype = 'tweetid')
  3: optionaw gettweetoptions options
  // @depwecated unused
  4: o-optionaw wist<i64> q-quoted_tweet_id_hints (pewsonawdatatype = 'tweetid')
}(haspewsonawdata = 'twue')

/**
 * c-can be used to wefewence a-an awbitwawy n-nyested fiewd o-of some stwuct via
 * a wist of fiewd ids descwibing the path of fiewds to weach the wefewenced
 * f-fiewd. (ꈍᴗꈍ)
 */
stwuct fiewdbypath {
  1: w-wequiwed w-wist<fiewdid> fiewd_id_path
}

stwuct gettweetwesuwt {
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')

  /**
   * i-indicates nyani h-happened when the tweet was woaded. 🥺
   */
  2: w-wequiwed statusstate tweet_state

  /**
   * t-the wequested tweet when tweet_state is `found`, >_< `pawtiaw`, o-ow `suppwess`. ʘwʘ
   *
   * this fiewd wiww be set if the tweet exists, >_< access is authowized, >w<
   * a-and enough d-data about t-the tweet is avaiwabwe t-to matewiawize a
   * tweet. òωó when this fiewd i-is set, OwO you shouwd wook at the t-tweet_state
   * fiewd to detewmine how to tweat t-this tweet. ^•ﻌ•^
   *
   * i-if tweet_state i-is found, XD then this tweet is compwete and p-passes the
   * authowization checks wequested in gettweetoptions. (see
   * gettweetoptions.fow_usew_id fow mowe infowmation a-about authowization.)
   *
   * i-if tweet_state is pawtiaw, mya then enough data was avaiwabwe to wetuwn
   * a tweet, nyaa~~ but thewe was a-an ewwow when woading the tweet that pwevented
   * s-some data fwom b-being wetuwned (fow e-exampwe, (ˆ ﻌ ˆ)♡ i-if a wequest to the cawds
   * sewvice times out when cawds wewe wequested, mya then the tweet wiww b-be
   * mawked p-pawtiaw). OwO `missing_fiewds` i-indicates w-which pawts of the tweet
   * f-faiwed to woad. 😳😳😳 when you weceive a-a pawtiaw tweet, o.O it is up to you
   * whethew to pwoceed with t-the degwaded tweet d-data ow to c-considew it a
   * f-faiwuwe. (U ﹏ U) fow exampwe, (˘ω˘) a mobiwe c-cwient might choose t-to dispway a
   * pawtiaw tweet to the usew, ( ͡o ω ͡o ) but nyot stowe i-it in an intewnaw c-cache. σωσ
   *
   * if tweet_state is suppwess, rawr x3 then the tweet i-is compwete, (ꈍᴗꈍ) but soft
   * fiwtewing i-is enabwed. òωó t-this state is intended t-to hide potentiawwy
   * hawmfuw tweets fwom usew's view whiwe nyot taking away the option f-fow
   * the usew to ovewwide o-ouw fiwtewing decision. (˘ω˘) see http://go/wtf
   * (wendew-time fiwtewing) f-fow mowe infowmation about h-how to tweat t-these
   * tweets. nyaa~~
   */
  3: o-optionaw t-tweet.tweet t-tweet

  /**
   * the tweet fiewds t-that couwd nyot be woaded when tweet_state is `pawtiaw`
   * ow `suppwess`.
   *
   * t-this fiewd wiww be set when the `tweet_state` i-is `pawtiaw`, mya a-and may
   * b-be set when `tweet_state` is suppwess. -.- it indicates degwaded data in
   * the `tweet`. :3 each e-entwy in `missing_fiewds` i-indicates a-a twavewsaw o-of
   * the `tweet` thwift object tewminating at the fiewd that is
   * missing. fow most nyon-cowe f-fiewds, :3 the path wiww just be the fiewd id
   * o-of the fiewd t-that is missing. OwO
   *
   * f-fow exampwe, if cawd2 f-faiwed to woad fow a tweet, ^^ the `tweet_state`
   * wiww be `pawtiaw`, ^^ the `tweet` fiewd wiww be set, the tweet's `cawd2`
   * fiewd wiww be empty, rawr and this fiewd wiww be set to:
   *
   *     s-set(fiewdbypath(seq(17)))
   */
  4: optionaw set<fiewdbypath> m-missing_fiewds

  /**
   * t-the owiginaw tweet w-when `tweet` is a-a wetweet and
   * gettweetoptions.incwude_souwce_tweet is twue. òωó
   */
  5: o-optionaw t-tweet.tweet souwce_tweet

  /**
   * the wetweet f-fiewds that c-couwd nyot be w-woaded when tweet_state i-is `pawtiaw`. (U ﹏ U)
   */
  6: optionaw set<fiewdbypath> s-souwce_tweet_missing_fiewds

  /**
   * the quoted tweet when `tweet` i-is a quote tweet a-and
   * gettweetoptions.incwude_quoted_tweet is twue. ( ͡o ω ͡o )
   */
  7: o-optionaw tweet.tweet q-quoted_tweet

  /**
   * the quoted tweet fiewds that couwd not be woaded when tweet_state i-is `pawtiaw`. ^^;;
   */
  8: optionaw s-set<fiewdbypath> quoted_tweet_missing_fiewds

  /**
   * the w-weason that a tweet shouwd nyot be dispwayed w-when tweet_state is
   * `suppwess` ow `dwop`. :3
   */
  9: optionaw s-safety_wesuwt.fiwtewedweason fiwtewed_weason

  /**
   * h-hydwated c-cawousew if t-the tweet contains a cawousew uww and the
   * g-gettweetoptions.incwude_cawousew i-is twue. mya
   *
   * i-in this case c-cawousew sewvice is wequested to h-hydwate the cawousew, ^^;; a-and
   * t-the wesuwt stowed i-in this fiewd. σωσ
   *
   * @depwecated g-go/sunsetting-cawousews
   */
  10: optionaw cawousew_sewvice.getcawousewwesuwt c-cawousew_wesuwt

  /**
   * i-if a quoted tweet wouwd be pwesent, ^^ but it was f-fiwtewed out, /(^•ω•^) t-then
   * this f-fiewd wiww be set to the weason t-that it was fiwtewed. (˘ω˘)
   */
  11: o-optionaw safety_wesuwt.fiwtewedweason quoted_tweet_fiwtewed_weason
}(haspewsonawdata = 'twue')

u-union tweetincwude {
  /**
   * f-fiewd id within the `tweet` stwuct t-to incwude. -.-  aww fiewds may b-be optionawwy incwuded
   * e-except f-fow the `id` f-fiewd. (ˆ ﻌ ˆ)♡
   */
  1: fiewdid tweetfiewdid

  /**
   * fiewd id within the `statuscounts` s-stwuct to incwude. òωó  onwy s-specificawwy wequested
   * count f-fiewds wiww be i-incwuded. :3  incwuding any `countsfiewdids` v-vawues a-automaticawwy
   * impwies incwuding `tweet.counts`. (ꈍᴗꈍ)
   *
   */
  2: fiewdid countsfiewdid

  /**
   * f-fiewd id w-within the `mediaentity` stwuct to incwude. (ˆ ﻌ ˆ)♡  cuwwentwy, onwy `mediaentity.additionawmetadata`
   * may be optionawwy incwuded (i.e., it wiww nyot be incwuded by defauwt if you incwude
   * `tweetfiewdid` = `tweet.media` without a-awso incwuding `mediaentityfiewdid`  = 
   * `mediaentity.additionawmetadata`. mya  i-incwuding a-any `mediaentityfiewdid` v-vawues automaticawwy
   * impwies incwude `tweet.media`.
   */
  3: f-fiewdid m-mediaentityfiewdid
}

/**
 * a-an enumewation o-of powicy options indicating how tweets shouwd be fiwtewed (pwotected tweets, (U ᵕ U❁) bwocked q-quote tweets, ^•ﻌ•^
 * c-contwibutow d-data, σωσ etc.). t-this does nyot affect visibiwity w-wibwawy (http://go/vf) based fiwtewing. ^^;;
 * this is equivawent to `bypass_visibiwity_fiwtewing` i-in get_tweets() caww. (✿oωo) this means t-that
 * `tweetvisibiwitypowicy.no_fiwtewing` is e-equivawent to `bypass_visibiwity_fiwtewing` = twue
 */
enum tweetvisibiwitypowicy {
  /**
   * onwy wetuwn tweets that shouwd b-be visibwe to eithew the `fowusewid` u-usew, UwU if specified, (✿oωo)
   * ow fwom the pewspective o-of a wogged-out usew if `fowusewid` is nyot s-specified. >_< this option
   * shouwd a-awways be used if wequesting d-data to be wetuwned v-via the pubwic api. (U ᵕ U❁)
   */
  usew_visibwe = 1, ^^;;

  /**
   * wetuwns aww tweets t-that can be found, (✿oωo) wegawdwess of usew visibiwity. rawr  this option shouwd
   * nyevew be used when gathew data to b-be wetuwn in an a-api, and shouwd onwy be used fow i-intewnaw
   * pwocessing. >w<  because t-this option a-awwows access to p-potentiawwy sensitive data, cwients
   * must b-be whitewisted to use it. ^^;;
   */
  nyo_fiwtewing = 2
}

stwuct gettweetfiewdsoptions {
  /**
   * identifies which `tweet` o-ow nyested f-fiewds to incwude i-in the wesponse. σωσ
   */
  1: w-wequiwed set<tweetincwude> tweet_incwudes

  /**
   * i-if twue and the wequested t-tweet is a wetweet, òωó t-then a `tweet`
   * containing the wequested f-fiewds fow the w-wetweeted tweet w-wiww be
   * i-incwuded in the w-wesponse. (ꈍᴗꈍ)
   */
  2: boow incwudewetweetedtweet = 0

  /**
   * if twue and the w-wequested tweet i-is a quote-tweet, ( ͡o ω ͡o ) t-then the quoted
   * tweet wiww awso be quewied and the wesuwt f-fow the quoted t-tweet
   * incwuded i-in `gettweetfiewdswesuwt.quotedtweetwesuwt`. ( ͡o ω ͡o )
   */
  3: boow i-incwudequotedtweet = 0

  /**
   * if twue and t-the wequested tweet c-contains a cawousew u-uww, UwU then the
   * cawousew wiww awso be q-quewied and the wesuwt fow the cawousew
   * incwuded i-in `gettweetfiewdswesuwt.cawousewwesuwt`. >_<
   *
   * @depwecated go/sunsetting-cawousews
   */
  4: boow incwudecawousew = 0

  /**
   * if you awe wequesting t-tweets on behawf of a usew, >w< s-set this to theiw
   * usew id. (˘ω˘) t-the effect of setting t-this option i-is:
   *
   * - t-tweetypie wiww wetuwn pwotected tweets that the u-usew is awwowed
   *   to access, 🥺 wathew than fiwtewing out pwotected tweets, rawr x3 w-when `visibiwity_powicy`
   *   i-is set to `usew_visibwe`. ^•ﻌ•^
   *
   * - i-if this fiewd i-is set *and* `tweet.pewspective` i-is wequested, then
   *   t-the tweets wiww h-have the `pewspective` fiewd set to a stwuct with
   *   fwags that i-indicate whethew the usew has favowited, mya wetweeted, mya o-ow
   *   wepowted the tweet i-in question. (U ﹏ U)
   */
  10: optionaw i64 fowusewid (pewsonawdatatype = 'usewid')

  /**
   * wanguage_tag i-is used when hydwating a-a `pwace` object, (///ˬ///✿) to get wocawized n-nyames. -.-
   * a-awso passed to t-the cawds sewvice, rawr if cawds awe hydwated fow the wequest. ^^
   */
  11: optionaw stwing wanguagetag (pewsonawdatatype = 'infewwedwanguage')

  /**
   * if wequesting c-cawd2 cawds, (⑅˘꒳˘) you must specify the pwatfowm k-key
   */
  12: optionaw stwing c-cawdspwatfowmkey (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, 😳😳😳 p-pubwictweetentitiesandmetadata') 

  /**
   * the awgument p-passed to the s-stwatostowe extension points mechanism. (✿oωo)
   */
  13: optionaw binawy extensionsawgs

  /**
   * t-the powicy to use when fiwtewing t-tweets fow basic visibiwity. /(^•ω•^)
   */
  20: tweetvisibiwitypowicy v-visibiwitypowicy = tweetvisibiwitypowicy.usew_visibwe

  /**
   * c-content fiwtewing powicy that w-wiww be used to d-dwop ow suppwess tweets fwom wesponse. >w<
   * the fiwtewing is based on the wesuwt o-of visibiwity wibwawy (http://go/vf)
   * a-and does n-nyot affect fiwtewing of tweets fwom bwocked o-ow nyon-fowwowed pwotected usews, 🥺 s-see
   * wequest fiewd 20 visibiwitypowicy a-above
   *
   * if nyot specified s-safetywevew.fiwtewnone wiww be used. OwO
   */
  21: o-optionaw safety_wevew.safetywevew s-safetywevew

  /**
   * the tweet wesuwt won't be cached by tweetypie if donotcache i-is twue. (ˆ ﻌ ˆ)♡
   * you shouwd set it as twue if o-owd tweets (owdew t-than 30 days) a-awe wequested, >_<
   * and they awe u-unwikewy to be wequested again. ^^;;
   */
  30: boow donotcache = 0

  /**
   * when w-wetuwning a tweet that quotes a-anothew tweet, :3 d-do nyot incwude
   * t-the uww to the quoted tweet i-in the tweet text a-and uww entities. >_<
   * t-this i-is intended fow cwients that use t-the quoted_tweet fiewd of
   * t-the tweet to dispway q-quoted tweets. (ˆ ﻌ ˆ)♡
   *
   */
  31: boow simpwe_quoted_tweet = 0

  /**
   * this fwag is used and onwy take affect i-if the wequested tweet is  cweatives containew backed
   * t-tweet. :3 this wiww s-supwwess the tweet matewiawization and wetuwn tweet nyot found. UwU
   *
   * go/cweatives-containews-tdd
  **/
  32: boow disabwe_tweet_matewiawization = 0

  /**
   * used fow woad s-shedding. if s-set to twue, ^^;; tweetypie s-sewvice m-might shed the wequest, i-if the sewvice 
   * i-is stwuggwing. mya
  **/
  33: o-optionaw boow is_wequest_sheddabwe  
}(haspewsonawdata = 'twue')

s-stwuct gettweetfiewdswequest {
  1: w-wequiwed wist<i64> t-tweetids (pewsonawdatatype = 'tweetid')
  2: w-wequiwed g-gettweetfiewdsoptions o-options
} (haspewsonawdata = 'twue')

/**
 * u-used in `tweetfiewdswesuwtstate` when the wequested tweet i-is found. 😳
 */
stwuct tweetfiewdswesuwtfound {
  1: wequiwed tweet.tweet tweet

  /**
   * i-if `tweet` is a wetweet, (///ˬ///✿) `wetweetedtweet` wiww be t-the wetweeted tweet. XD
   * j-just wike with the wequested t-tweet, òωó onwy the wequested f-fiewds wiww be
   * h-hydwated and set on the wetweeted t-tweet. (ˆ ﻌ ˆ)♡
   */
  2: optionaw t-tweet.tweet wetweetedtweet

  /**
   * i-if specified, o.O then the t-tweet shouwd be soft fiwtewed. (U ﹏ U)
   */
  3: optionaw safety_wesuwt.fiwtewedweason s-suppwessweason
}

/**
 * used in `tweetfiewdswesuwtstate` w-when the wequested tweet is nyot found. 🥺
 */
s-stwuct tweetfiewdswesuwtnotfound {
  // if t-this fiewd is twue, UwU then we know t-that the tweet once existed and
  // h-has since been deweted. XD
  1: b-boow deweted = 0

  // this tweet is deweted a-aftew being bounced f-fow viowating t-the twittew
  // w-wuwes and shouwd n-nyevew be wendewed o-ow undeweted. ʘwʘ see go/bounced-tweet
  // i-in cewtain timewines w-we wendew a t-tombstone in its pwace. (///ˬ///✿)
  2: boow b-bouncedeweted = 0

  // the weason that a tweet s-shouwd nyot be d-dispwayed. 🥺 see go/vf-tombstones-in-tweetypie
  // tweets that a-awe nyot found do n-nyot going thwough visibiwity f-fiwtewing wuwe evawuation a-and thus
  // a-awe nyot `tweetfiewdswesuwtfiwtewed`, b-but may stiww have a fiwtewed_weason that distinguishes
  // whethew the unavaiwabwe tweet shouwd b-be tombstoned ow hawd-fiwtewed based o-on the safety wevew. o.O
  3: optionaw s-safety_wesuwt.fiwtewedweason fiwtewed_weason
}

s-stwuct tweetfiewdspawtiaw {
  1: w-wequiwed tweetfiewdswesuwtfound f-found

  /**
    * t-the tweet fiewds that couwd not be woaded w-when hydwation faiws
    * and a backend faiws w-with an exception. /(^•ω•^) this fiewd i-is popuwated
    * w-when a tweet i-is "pawtiawwy" hydwated, (U ﹏ U) i.e. s-some fiewds wewe
    * successfuwwy fetched whiwe othews wewe nyot. (U ﹏ U)
    *
    * i-it indicates degwaded data in the `tweet`. (✿oωo) each entwy in `missing_fiewds`
    * indicates a twavewsaw of the `tweet` thwift object tewminating a-at
    * the fiewd t-that is missing. rawr fow most nyon-cowe f-fiewds, ^^ the p-path wiww
    * just be the fiewd id of the fiewd that is missing. ^^
    *
    * f-fow exampwe, (✿oωo) if c-cawd2 faiwed to woad fow a tweet, t-the tweet is m-mawked "pawtiaw", (˘ω˘)
    * t-the `tweet` f-fiewd wiww be set, /(^•ω•^) the tweet's `cawd2`
    * fiewd wiww be e-empty, o.O and this fiewd wiww be set to:
    *
    *     set(fiewdbypath(seq(17)))
    */
  2: w-wequiwed set<fiewdbypath> missingfiewds

  /**
    * same as `missing_fiewds` but fow the souwce tweet i-in case the wequested tweet
    * was a wetweet. o.O
    */
  3: wequiwed set<fiewdbypath> s-souwcetweetmissingfiewds
}
/**
 * u-used i-in `tweetfiewdswesuwtstate` when thewe was a faiwuwe w-woading the w-wequested tweet. ^^;;
 */
s-stwuct tweetfiewdswesuwtfaiwed {
  /**
   * if twue, ( ͡o ω ͡o ) the faiwuwe was the w-wesuwt of backpwessuwe, which means t-the wequest
   * shouwd nyot be immediatewy wetwied. >w<  it is s-safe to wetwy again watew. /(^•ω•^)
   *
   * i-if fawse, 😳 the faiwuwe is pwobabwy t-twansient a-and safe to wetwy immediatewy. rawr x3
   */
  1: w-wequiwed boow ovewcapacity

  /**
   * an optionaw message a-about the cause of the faiwuwe. OwO
   */
  2: optionaw stwing message

  /**
   * t-this fiewd is popuwated when some tweet fiewds f-faiw to woad and the
   * tweet i-is mawked "pawtiaw" i-in tweetypie. ^•ﻌ•^ it contains t-the tweet/wt
   * infowmation a-awong with the set of tweet fiewds that faiwed to
   * g-get popuwated. (U ﹏ U)
   */
  3: o-optionaw tweetfiewdspawtiaw pawtiaw
}

/**
 * used i-in `tweetfiewdswesuwtstate` w-when the wequested tweet has been f-fiwtewed out. OwO
 */
stwuct tweetfiewdswesuwtfiwtewed {
  1: wequiwed safety_wesuwt.fiwtewedweason weason
}

/**
 * a union of the d-diffewent possibwe outcomes of a fetching a singwe tweet. (ˆ ﻌ ˆ)♡
 */
u-union tweetfiewdswesuwtstate {
  1: t-tweetfiewdswesuwtfound f-found
  2: tweetfiewdswesuwtnotfound n-nyotfound
  3: tweetfiewdswesuwtfaiwed f-faiwed
  4: tweetfiewdswesuwtfiwtewed f-fiwtewed
}

/**
 * the wesponse to g-get_tweet_fiewds w-wiww incwude a tweetfiewdswesuwtwow fow each
 * wequested tweet i-id. >_<
 */
stwuct g-gettweetfiewdswesuwt {
  /**
   * the id of the wequested tweet. rawr
   */
  1: w-wequiwed i64 tweetid (pewsonawdatatype = 'tweetid')

  /**
   * t-the w-wesuwt fow the wequested t-tweet
   */
  2: w-wequiwed tweetfiewdswesuwtstate t-tweetwesuwt

  /**
   * if quoted-tweets w-wewe wequested and the pwimawy tweet was found, >_<
   * this fiewd w-wiww contain t-the wesuwt state f-fow the quoted t-tweeted. -.-
   */
  3: o-optionaw tweetfiewdswesuwtstate q-quotedtweetwesuwt

  /**
   * i-if the pwimawy tweet was found, cawousews wewe w-wequested and thewe
   * was a cawousew uww in t-the pwimawy tweet, (⑅˘꒳˘) this fiewd wiww c-contain the
   * wesuwt fow the cawousew. o.O
   *
   * @depwecated
   */
  4: optionaw cawousew_sewvice.getcawousewwesuwt c-cawousewwesuwt
}

s-stwuct t-tweetcweateconvewsationcontwowbyinvitation {
  1: optionaw boow invite_via_mention
}

stwuct t-tweetcweateconvewsationcontwowcommunity {
  1: optionaw b-boow invite_via_mention
}

s-stwuct tweetcweateconvewsationcontwowfowwowews {
  1: o-optionaw boow invite_via_mention
}

/**
 * specify wimits on usew pawticipation in a convewsation. (˘ω˘)
 *
 * this is a union w-wathew than a s-stwuct to suppowt a-adding convewsation
 * contwows that wequiwe cawwying m-metadata awong with them, (ˆ ﻌ ˆ)♡ such as a wist i-id. UwU
 *
 * see awso:
 *   tweet.convewsation_contwow
 *   p-posttweetwequest.convewsation_contwow
 */
union tweetcweateconvewsationcontwow {
  1: tweetcweateconvewsationcontwowcommunity community
  2: t-tweetcweateconvewsationcontwowbyinvitation byinvitation
  3: t-tweetcweateconvewsationcontwowfowwowews fowwowews
}

/*
 * specifies the excwusivity of a tweet
 * t-this wimits the audience of the tweet to t-the authow
 * and the authow's supew f-fowwowews
 * w-whiwe empty nyow, (✿oωo) we awe expecting to add additionaw fiewds in v1+
 */
stwuct e-excwusivetweetcontwowoptions {}

stwuct twustedfwiendscontwowoptions {
  1: i64 twusted_fwiends_wist_id = 0 (pewsonawdatatype = 'twustedfwiendswistmetadata')
}(haspewsonawdata = 'twue')

stwuct cowwabinvitationoptions {
  1: wequiwed wist<i64> c-cowwabowatow_usew_ids (pewsonawdatatype = 'usewid')
  // n-note: status nyot sent hewe, (ˆ ﻌ ˆ)♡ wiww be a-added in tweetbuiwdew to set aww b-but authow as p-pending
}

stwuct c-cowwabtweetoptions {
  1: wequiwed wist<i64> cowwabowatow_usew_ids (pewsonawdatatype = 'usewid')
}

u-union cowwabcontwowoptions {
  1: c-cowwabinvitationoptions c-cowwabinvitation
  2: c-cowwabtweetoptions cowwabtweet
}

/**
 * when this stwuct is suppwied, -.- this p-posttweetwequest i-is intewpweted as
 * an edit of the tweet whose watest vewsion is wepwesented by pwevious_tweet_id. UwU
 * i-if this is the fiwst edit of a tweet, /(^•ω•^) this wiww be the s-same as the initiaw_tweet_id. rawr x3
 **/
s-stwuct editoptions {
  /**
   * t-the id of the p-pwevious watest vewsion of the tweet that is being edited. XD
   * if this is the fiwst edit, UwU this w-wiww be the same as the initiaw_tweet_id. ^^
   **/
  1: w-wequiwed i64 pwevious_tweet_id (pewsonawdatatype = 'tweetid')
}

s-stwuct n-nyotetweetoptions {
  /**
   * the id of the nyotetweet to be associated with this tweet. (U ﹏ U)
   **/
  1: w-wequiwed i64 nyote_tweet_id (pewsonawdatatype = 'twittewawticweid')
  // d-depwecated
  2: o-optionaw wist<stwing> m-mentioned_scween_names (pewsonawdatatype = 'usewname')
  /**
  * t-the usew ids of the mentioned u-usews
  **/
  3: optionaw wist<i64> mentioned_usew_ids (pewsonawdatatype = 'usewid')
  /**
  * s-specifies if t-the tweet can be e-expanded into the nyotetweet, òωó ow if they have t-the same text
  **/
  4: optionaw b-boow is_expandabwe
}

s-stwuct posttweetwequest {
  /**
   * i-id o-of the usew cweating the tweet. ( ͡o ω ͡o )
   */
  1: wequiwed i64 usew_id (pewsonawdatatype = 'usewid')

  /**
   * t-the usew-suppwied text of the tweet. (ˆ ﻌ ˆ)♡
   */
  2: wequiwed stwing text (pewsonawdatatype = 'pwivatetweets, 😳😳😳 p-pubwictweets')

  /**
   * t-the oauth cwient appwication fwom w-which the cweation w-wequest owiginated. ʘwʘ
   *
   * t-this must be in t-the fowmat "oauth:<cwient appwication id>". 😳😳😳 fow w-wequests
   * fwom a usew this is the appwication i-id of theiw cwient; fow intewnaw
   * s-sewvices t-this is the id o-of an associated a-appwication wegistewed a-at
   * h-https://apps.twittew.com. >w<
   */
  3: wequiwed stwing cweated_via (pewsonawdatatype = 'cwienttype')

  4: o-optionaw i64 in_wepwy_to_tweet_id (pewsonawdatatype = 'tweetid')
  5: o-optionaw tweetcweategeo geo
  6: o-optionaw wist<i64> m-media_upwoad_ids (pewsonawdatatype = 'mediaid')
  7: o-optionaw t-tweet.nawwowcast n-nyawwowcast

  /**
   * do nyot dewivew this tweet to a usew's fowwowews. nyaa~~
   *
   * when twue t-this tweet wiww n-nyot be fanned out, :3 appeaw in the u-usew's
   * timewine, mya o-ow appeaw in seawch wesuwts. 😳😳😳 i-it wiww be distwibuted via the
   * fiwehose and avaiwabwe i-in the pubwic api. ^^
   *
   * this i-is pwimawiwy u-used to cweate tweets that can be u-used as ads without
   * b-bwoadcasting t-them to a-an advewtisew's f-fowwowews. rawr
   *
   */
  8: boow n-nyuwwcast = 0

  /**
   * t-the impwession id of the ad fwom which t-this tweet was cweated. (ꈍᴗꈍ)
   *
   * this is set when a-a usew wetweets ow wepwies to a pwomoted tweet. ^•ﻌ•^ it is
   * used t-to attwibute t-the "eawned" exposuwe o-of an advewtisement. UwU
   */
  9: o-optionaw i64 twacking_id (pewsonawdatatype = 'impwessionid')

  /**
   * @depwecated. (U ﹏ U)
   * too cwients don't a-activewy use t-this input pawam, (ꈍᴗꈍ) and the v2 api does nyot pwan
   * t-to expose t-this pawametew. t-the vawue associated with this fiewd t-that's
   * s-stowed with a tweet is obtained fwom the usew's account pwefewences stowed in
   * `usew.safety.nsfw_usew`. o.O (see go/usew.thwift fow mowe detaiws o-on this fiewd)
   *
   * fiewd indicates whethew a individuaw tweet may contain objectionabwe content. nyaa~~
   *
   * i-if specified, ^•ﻌ•^ t-tweet.cowe_data.nsfw_usew wiww equaw this vawue (othewwise, 🥺
   * tweet.cowe_data.nsfw_usew w-wiww be set to usew.nsfw_usew). rawr
   */
  10: optionaw boow possibwy_sensitive

  /**
   * d-do nyot save, ^•ﻌ•^ i-index, fanout, 😳 o-ow othewwise pewsist this tweet. ^^;;
   *
   * w-when twue, (ꈍᴗꈍ) the tweet i-is vawidated, σωσ cweated, (⑅˘꒳˘) and wetuwned b-but is nyot
   * p-pewsisted. OwO t-this can be used f-fow dawk testing ow pwe-vawidating a-a tweet
   * s-scheduwed fow watew cweation. 😳
   */
  11: boow dawk = 0

  /**
   * i-ip addwess o-of the usew making the wequest. (ˆ ﻌ ˆ)♡
   *
   * this is used fow wogging cewtain kinds o-of actions, UwU wike a-attempting to
   * tweet mawwawe u-uwws. rawr x3
   */
  12: optionaw s-stwing wemote_host (pewsonawdatatype = 'ipaddwess')

  /**
   * additionaw fiewds to wwite with this tweet. σωσ
   *
   * t-this tweet object shouwd contain onwy additionaw f-fiewds to wwite with
   * this tweet. UwU additionaw f-fiewds awe t-tweet fiewds with id > 100. rawr x3 set
   * tweet.id to be 0; the id wiww be genewated b-by tweetypie. /(^•ω•^) a-any othew nyon-
   * a-additionaw f-fiewds set on this tweet wiww be considewed an i-invawid
   * wequest. OwO
   *
   */
  14: o-optionaw t-tweet.tweet additionaw_fiewds

  15: o-optionaw wwitepathhydwationoptions h-hydwation_options

  // obsowete 16: optionaw boow bypass_wate_wimit_fow_xfactow

  /**
   * id to expwicitwy identify a cweation wequest f-fow the puwpose of wejecting
   * d-dupwicates. 😳😳😳
   *
   * i-if two w-wequests awe weceived w-with the s-same uniqueness_id, UwU then they wiww
   * be considewed dupwicates of each othew. nyaa~~ t-this onwy appwies fow tweets
   * cweated within t-the same datacentew. t-this id shouwd be a snowfwake i-id so
   * that it's gwobawwy unique. -.-
   */
  17: optionaw i64 u-uniqueness_id (pewsonawdatatype = 'pwivatetweetentitiesandmetadata, (˘ω˘) p-pubwictweetentitiesandmetadata')

  18: optionaw f-featuwe_context.featuwecontext featuwe_context

  /**
   * passthwough data f-fow scawecwow t-that is used fow s-safety checks. >_<
   */
  19: optionaw safety_meta_data.safetymetadata s-safety_meta_data

  // o-obsowete 20: b-boow c-community_nawwowcast = 0

  /**
   * t-toggwe nyawwowcasting b-behaviow fow weading @mentions. (///ˬ///✿)
   *
   * i-if in_wepwy_to_tweet_id i-is nyot set:
   *   - w-when this fwag is twue and the tweet text stawts w-with a weading m-mention then t-the tweet
   *     w-wiww be nyawwowcasted. 😳
   *
   * i-if in_wepwy_to_tweet_id i-is set:
   *   - if auto_popuwate_wepwy_metadata i-is t-twue
   *       - s-setting this fwag t-to twue wiww u-use the defauwt nyawwowcast detewmination w-wogic w-whewe
   *         most wepwies w-wiww be nyawwowcast but some speciaw-cases of sewf-wepwies w-wiww n-nyot. >_<
   *       - setting this f-fwag to fawse wiww d-disabwe nyawwowcasting and the tweet wiww be fanned out
   *         to aww t-the authow's fowwowews. >w<  p-pweviouswy u-usews pwefixed theiw wepwy text w-with "." to
   *         achieve this effect. (U ᵕ U❁)
   *   - if auto_popuwate_wepwy_metadata is fawse, σωσ t-this fwag wiww contwow whethew a weading
   *     mention in the tweet text wiww be nyawwowcast (twue) o-ow bwoadcast (fawse). (˘ω˘)
   */
  21: b-boow e-enabwe_tweet_to_nawwowcasting = 1

  /**
   * automaticawwy popuwate wepwies with weading mentions fwom tweet text. ^•ﻌ•^
   */
  22: boow auto_popuwate_wepwy_metadata = 0

  /**
   * m-metadata at the tweet-asset wewationship wevew. 😳
   */
  23: o-optionaw map<mediacommon.mediaid, :3 mediainfowmation.usewdefinedpwoductmetadata> media_metadata

  /**
   * an optionaw u-uww that identifies a wesouwce that is tweated a-as an attachment of the
   * the tweet, ^^ such as a quote-tweet p-pewmawink. (U ᵕ U❁)
   *
   * when pwovided, ʘwʘ it is appended t-to the end of the tweet text, /(^•ω•^) b-but is nyot
   * incwuded in the visibwe_text_wange. :3
   */
  24: optionaw stwing attachment_uww (pewsonawdatatype = 'cawdid, s-showtuww')

  /**
   * p-pass-thwough i-infowmation t-to be pubwished i-in `tweetcweateevent`. >w<
   *
   * this data is n-nyot pewsisted by tweetypie. rawr
   *
   * @depwecated pwefew twansient_context (see fiewd 27) ovew this.
   */
  25: optionaw map<tweet.tweetcweatecontextkey, (⑅˘꒳˘) stwing> additionaw_context

  /**
   * usews to excwude f-fwom the automatic wepwy popuwation behaviow. ^^
   *
   * w-when auto_popuwate_wepwy_metadata i-is t-twue, ^•ﻌ•^ scween nyames appeawing in t-the
   * mention pwefix can be e-excwuded by specifying a-a cowwesponding u-usew id in
   * excwude_wepwy_usew_ids. (ˆ ﻌ ˆ)♡  b-because the mention p-pwefix must a-awways incwude
   * the weading mention to pwesewve diwected-at addwessing fow t-the in-wepwy-
   * t-to tweet authow, :3 attempting to e-excwude that usew i-id wiww have nyo effect. UwU
   * s-specifying a usew i-id nyot in the p-pwefix wiww be siwentwy ignowed. ^^
   */
  26: optionaw wist<i64> excwude_wepwy_usew_ids (pewsonawdatatype = 'usewid')

  /**
   * u-used to pass stwuctuwed data t-to tweetypie and tweet_events eventbus
   * stweam consumews. ( ͡o ω ͡o ) this d-data is nyot pewsisted by tweetypie. rawr
   *
   * i-if adding a nyew passthwough fiewd, UwU pwefew this ovew additionaw_context, òωó
   * as this is stwuctuwed data, ʘwʘ whiwe additionaw_context is text data. ^^
   */
  27: o-optionaw twansient_context.twansientcweatecontext twansient_context

  /**
   * composew fwow used t-to cweate this t-tweet. ^•ﻌ•^ unwess u-using the nyews camewa (go/newscamewa)
   * f-fwow, (⑅˘꒳˘) this shouwd be `standawd`. (✿oωo)
   *
   * w-when set t-to `camewa`, >w< cwients a-awe expected t-to dispway the t-tweet with a diffewent ui
   * t-to emphasize attached m-media. mya
   */
  28: o-optionaw t-tweet.composewsouwce composew_souwce

  /**
  * pwesent if we want to westwict w-wepwies to this t-tweet (go/dont-at-me-api)
  * - t-this gets convewted to tweet.convewsation_contwow a-and changes type
  * - this is o-onwy vawid fow convewsation woot tweets
  * - this appwies to a-aww wepwies to this t-tweet
  */
  29: o-optionaw tweetcweateconvewsationcontwow c-convewsation_contwow

  // o-obsowete 30: o-optionaw jiminy.cweatenudgeoptions n-nyudge_options

  /**
  * pwovided if the c-cwient wants to have the tweet cweate evawuated fow a nyudge (e.g. o.O t-to nyotify
  * t-the usew that they awe about to cweate a toxic t-tweet). ^^;; wefewence: go/docbiwd/jiminy
  */
  31: optionaw jiminy.cweatetweetnudgeoptions nyudge_options

  /**
   * pwovided fow c-cowwewating wequests o-owiginating f-fwom west endpoints a-and gwaphqw e-endpoints. ( ͡o ω ͡o )
   * its pwesence ow absence does n-nyot affect tweet m-mutation. (ˆ ﻌ ˆ)♡ it used fow vawidation
   * a-and debugging. mya t-the expected f-fowmat is a 36 ascii uuidv4. :3
   * p-pwease see a-api specification at go/gwaphqw-tweet-mutations fow mowe infowmation. (ˆ ﻌ ˆ)♡
   */
  32: optionaw stwing compawison_id (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid')

  /**
   * o-options that detewmine the shape of an excwusive tweet's westwictions. (U ﹏ U)
   * t-the existence of t-this object indicates that the t-tweet is intended to be an excwusive t-tweet
   * w-whiwe this is an e-empty stwuctuwe f-fow nyow, òωó it wiww h-have fiewds a-added to it watew in watew vewsions. (U ﹏ U)
   */
  33: o-optionaw excwusivetweetcontwowoptions e-excwusivetweetcontwowoptions

  34: o-optionaw twustedfwiendscontwowoptions twustedfwiendscontwowoptions

  /**
   * p-pwovided if tweet data i-is backed up by a cweative containew, (///ˬ///✿) that at tweet hydwation
   * time, /(^•ω•^) tweetypie wouwd dewegate to cweative containew sewvice. (U ᵕ U❁)
   *
   * go/cweatives-containews-tdd
   * p-pwease n-nyote that this id is nyevew pubwicawwy shawed w-with cwients, (U ﹏ U) its onwy used fow
   * intewnaw puwposes. (✿oωo)
   */
  35: optionaw i-i64 undewwying_cweatives_containew_id (pewsonawdatatype = 'tweetid')

  /**
   * p-pwovided if tweet i-is a cowwabtweet ow a cowwabinvitation, ^^ a-awong w-with a wist of cowwabowatows
   * which incwudes the owiginaw authow. :3
   *
   * g-go/cowwab-tweets
   **/
  36: optionaw cowwabcontwowoptions cowwabcontwowoptions

   /**
    * when suppwied, nyaa~~ this p-posttweetwequest i-is an edit. OwO see [[editoptions]] fow mowe detaiws. :3
    **/
  37: optionaw editoptions editoptions

  /**
   * w-when suppwied, nyaa~~ t-the nyotetweet specified is associated w-with the c-cweated tweet. (///ˬ///✿)
   **/
  38: optionaw nyotetweetoptions nyotetweetoptions
} (haspewsonawdata = 'twue')

s-stwuct setadditionawfiewdswequest {
  1: wequiwed tweet.tweet additionaw_fiewds
}

s-stwuct deweteadditionawfiewdswequest {
  1: w-wequiwed w-wist<i64> tweet_ids (pewsonawdatatype = 'tweetid')
  2: w-wequiwed w-wist<fiewdid> fiewd_ids
}(haspewsonawdata = 'twue')

s-stwuct dewetetweetswequest {
  1: w-wequiwed w-wist<i64> tweet_ids (pewsonawdatatype = 'tweetid')
  // depwecated and moved to t-tweetypie_intewnaw.thwift's c-cascadeddewetetweetswequest
  2: optionaw i64 cascaded_fwom_tweet_id (pewsonawdatatype = 'tweetid')
  3: o-optionaw tweet_audit.auditdewetetweet a-audit_passthwough

  /**
   * t-the id o-of the usew initiating this wequest. (✿oωo)
   *
   * i-it couwd be eithew t-the ownew of the tweet ow an admin. 🥺 if nyot specified
   * w-we w-wiww use twittewcontext.usewid. ʘwʘ
   */
  4: optionaw i-i64 by_usew_id (pewsonawdatatype = 'usewid')


  /**
   * whewe these tweets awe being deweted a-as pawt of a u-usew ewasuwe, :3 the p-pwocess
   * o-of deweting tweets b-bewonging to deactivated accounts. UwU
   *
   * t-this wets backends optimize pwocessing of mass dewetes o-of tweets fwom the
   * same usew. :3 tawk to the tweetypie team befowe setting this fwag. XD
   */
  5: b-boow is_usew_ewasuwe = 0

  /**
   * id t-to compawe with t-the usew id of t-the tweets being deweted. 😳😳😳
   *
   * t-this pwovides extwa pwotection a-against accidentaw dewetion o-of tweets. (˘ω˘)
   * this is wequiwed w-when is_usew_ewasuwe i-is twue. (⑅˘꒳˘) if any of the tweets
   * specified i-in tweet_ids d-do nyot match expected_usew_id a
   * e-expected_usew_id_mismatch s-state wiww be wetuwned. ( ͡o ω ͡o )
   */
  6: o-optionaw i64 expected_usew_id (pewsonawdatatype = 'usewid')

  /**
   * a bounced t-tweet is a tweet that has been found to viowate twittew wuwes. (⑅˘꒳˘)
   * this is w-wepwesented as a-a tweet with its b-bounce_wabew fiewd set. (U ﹏ U)
   *
   * w-when the tweet o-ownew dewetes t-theiw offending b-bounced tweet in the bounced wowkfwow, ʘwʘ bouncew
   * wiww submit a dewete wequest w-with `is_bounce_dewete` set to twue. if the tweet(s) b-being deweted
   * have a bounce_wabew set, (ˆ ﻌ ˆ)♡ this wequest wesuwts i-in the tweet t-twansitioning into the
   * bouncedeweted state which means t-the tweet is pawtiawwy deweted. XD
   *
   * m-most of the nyowmaw tweet d-dewetion side-effects occuw b-but the tweet wemains in a
   * f-few tfwock gwaphs, (⑅˘꒳˘) tweet cache, (ꈍᴗꈍ) a-and a manhattan mawkew is added. (✿oωo) o-othew than timewines sewvices, ( ͡o ω ͡o )
   * bounce deweted t-tweets awe considewed deweted and wiww wetuwn a statusstate.bouncedewete. >w<
   *
   * aftew a defined gwace pewiod, ^^ tweets in this state wiww b-be fuwwy deweted. 🥺
   *
   * i-if the t-tweet(s) being deweted do nyot have the bounce_wabew set, (ꈍᴗꈍ) they wiww be deweted a-as usuaw. ʘwʘ
   *
   * othew than b-bouncew, mya nyo sewvice s-shouwd use `is_bounce_dewete` f-fwag. ^^
   */
  7: boow is_bounce_dewete = 0

  /**
    * t-this is a unique identifiew used in b-both the west and g-gwaphqw-dawk
    * w-wequests that wiww be used to cowwewate the gwaphqw mutation w-wequests to the west wequests
    * duwing a twansition p-pewiod w-when cwients wiww b-be moving towawd t-tweet cweation via gwaphqw.
    * see awso, the "compawison t-testing" section at go/tweet-cweate-on-gwaphqw-tdd fow additionaw
    * context. (ꈍᴗꈍ)
    */
  8: optionaw stwing compawison_id (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid')

  /**
    * when an edited tweet is deweted via d-daemons, we take a-a diffewent action
    * than if it was deweted n-nyowmawwy. (ꈍᴗꈍ) if d-deweted nyowmawwy, (ꈍᴗꈍ) w-we dewete the
    * i-initiaw tweet in the chain. :3 w-when deweted via daemons, ^^ we d-dewete the actuaw tweet. (///ˬ///✿)
    */
  9: o-optionaw b-boow cascaded_edited_tweet_dewetion 
}(haspewsonawdata = 'twue')

stwuct dewetetweetwesuwt {
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: w-wequiwed t-tweetdewetestate state
}(haspewsonawdata = 'twue')

stwuct unwetweetwesuwt {
  /**
   * id o-of the wetweet that was deweted if a wetweet couwd be found. :3
   */
  1: o-optionaw i64 tweet_id (pewsonawdatatype = 'tweetid')

  2: w-wequiwed tweetdewetestate state
}(haspewsonawdata = 'twue')

s-stwuct posttweetwesuwt {
  1: w-wequiwed t-tweetcweatestate state

  /**
   * the cweated t-tweet when state is ok. >w<
   */
  2: optionaw t-tweet.tweet tweet

  /**
   * the owiginaw tweet w-when state is ok and tweet is a-a wetweet. :3
   */
  3: o-optionaw t-tweet.tweet souwce_tweet

  /**
   * t-the quoted tweet when state is ok and tweet i-is a quote tweet. >_<
   */
  4: o-optionaw t-tweet.tweet quoted_tweet

  /**
   * the wequiwed usew wemediation fwom scawecwow w-when state is bounce. :3
   */
  5: o-optionaw b-bounce.bounce b-bounce

  /**
   * additionaw infowmation when tweetcweatestate i-is nyot ok. òωó
   *
   * nyot aww faiwuwes pwovide a-a weason. ^^
   */
  6: optionaw stwing f-faiwuwe_weason

  // o-obsowete 7: optionaw jiminy.nudge nyudge

  /**
  * wetuwned when the s-state is nyudge t-to indicate that the tweet has n-not been cweated, mya a-and that
  * the c-cwient shouwd i-instead dispway the nyudge to the usew. (˘ω˘) wefewence: go/docbiwd/jiminy
  */
  8: o-optionaw jiminy.tweetnudge nyudge
} (pewsisted = "twue", (U ᵕ U❁) h-haspewsonawdata = "twue")

/**
 * s-specifies the cause of an accessdenied ewwow. ^^
 */
enum accessdeniedcause {
  // o-obsowete: invawid_cwient_id = 0, mya
  // obsowete: depwecated = 1, XD
  u-usew_deactivated = 2, rawr
  usew_suspended = 3, (ˆ ﻌ ˆ)♡

  w-wesewved_4 = 4, (˘ω˘)
  wesewved_5 = 5,
  wesewved_6 = 6
}

/**
 * a-accessdenied ewwow is wetuwned b-by dewete_tweets endpoint w-when
 * by_usew_id i-is suspended o-ow deactivated. nyaa~~
 */
exception a-accessdenied {
  1: w-wequiwed stwing m-message
  2: o-optionaw accessdeniedcause ewwowcause
}

stwuct undewetetweetwequest {
  1: w-wequiwed i-i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: o-optionaw wwitepathhydwationoptions hydwation_options

  /**
   * pewfowm the side e-effects of undewetion e-even if t-the tweet is nyot d-deweted. o.O
   *
   * t-this fwag i-is usefuw if you k-know that the tweet i-is pwesent in manhattan
   * but is nyot undeweted with wespect to othew sewvices. mya
   */
  3: o-optionaw boow fowce
}(haspewsonawdata = 'twue')

stwuct undewetetweetwesponse {
  1: w-wequiwed undewetetweetstate s-state
  2: optionaw tweet.tweet tweet
}

stwuct ewaseusewtweetswequest {
  1: w-wequiwed i64 usew_id (pewsonawdatatype = 'usewid')
}(haspewsonawdata = 'twue')

stwuct unwetweetwequest {
  /**
   * t-the id of t-the usew who owns the wetweet. XD
   */
  1: wequiwed i64 usew_id (pewsonawdatatype = 'usewid')

  /**
   * the souwce t-tweet that shouwd be unwetweeted. òωó
   */
  2: wequiwed i64 souwce_tweet_id (pewsonawdatatype = 'tweetid')

  /**
    * this is a unique identifiew u-used in both the west and g-gwaphqw-dawk
    * w-wequests that w-wiww be used to c-cowwewate the gwaphqw mutation wequests to the w-west wequests
    * duwing a twansition pewiod w-when cwients wiww be moving towawd tweet cweation via gwaphqw. (˘ω˘)
    * see awso, :3 the "compawison testing" section a-at go/tweet-cweate-on-gwaphqw-tdd fow additionaw
    * c-context.
    */
  3: o-optionaw s-stwing compawison_id (pewsonawdatatype = 'univewsawwyuniqueidentifiewuuid')
}(haspewsonawdata = 'twue')

stwuct getdewetedtweetswequest {
  1: wequiwed wist<i64> t-tweetids (pewsonawdatatype = 'tweetid')
}(haspewsonawdata = 'twue')

s-stwuct getdewetedtweetwesuwt {
  1: w-wequiwed i64 tweetid (pewsonawdatatype = 'tweetid')
  2: w-wequiwed dewetedtweetstate s-state
  4: optionaw deweted_tweet.dewetedtweet t-tweet
}(haspewsonawdata = 'twue')

/**
 * fwushes tweets and/ow t-theiw counts fwom cache. OwO
 *
 * t-typicawwy wiww be used manuawwy f-fow testing ow w-when a pawticuwaw pwobwem is
 * found that nyeeds to be fixed by hand. defauwts to fwushing both tweet
 * stwuct a-and associated c-counts. mya
 */
stwuct fwushwequest {
  1: w-wequiwed w-wist<i64> tweet_ids (pewsonawdatatype = 'tweetid')
  2: b-boow fwushtweets = 1
  3: boow fwushcounts = 1
}(haspewsonawdata = 'twue')

/**
 * a wequest to wetwieve c-counts fow one ow mowe tweets. (˘ω˘)
 */
stwuct gettweetcountswequest {
  1: wequiwed wist<i64> tweet_ids (pewsonawdatatype = 'tweetid')
  2: b-boow incwude_wetweet_count = 0
  3: boow i-incwude_wepwy_count = 0
  4: b-boow incwude_favowite_count = 0
  5: b-boow incwude_quote_count = 0
  6: boow incwude_bookmawk_count = 0
}(haspewsonawdata = 'twue')

/**
 * a-a wesponse o-optionawwy i-indicating one o-ow mowe counts fow a tweet.
 */
stwuct gettweetcountswesuwt {
  1: w-wequiwed i64 t-tweet_id (pewsonawdatatype = 'tweetid')
  2: o-optionaw i-i64 wetweet_count (pewsonawdatatype = 'countofpwivatewetweets, o.O c-countofpubwicwetweets')
  3: optionaw i64 wepwy_count (pewsonawdatatype = 'countofpwivatewepwies, (✿oωo) countofpubwicwepwies')
  4: optionaw i64 f-favowite_count (pewsonawdatatype = 'countofpwivatewikes, (ˆ ﻌ ˆ)♡ countofpubwicwikes')
  5: optionaw i64 quote_count (pewsonawdatatype = 'countofpwivatewetweets, ^^;; countofpubwicwetweets')
  6: optionaw i64 b-bookmawk_count (pewsonawdatatype = 'countofpwivatewikes')
}(haspewsonawdata = 'twue')

/**
 * a wequest to incwement the cached favowites count f-fow a tweet. OwO
 *
 * n-nyegative v-vawues decwement the count. 🥺 this w-wequest is automaticawwy
 * wepwicated t-to othew d-data centews. mya
 */
stwuct incwtweetfavcountwequest {
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: wequiwed i32 dewta (pewsonawdatatype = 'countofpwivatewikes, 😳 c-countofpubwicwikes')
}(haspewsonawdata = 'twue')

/**
 * a wequest t-to incwement the cached bookmawks c-count fow a-a tweet. òωó
 *
 * nyegative vawues decwement the count. /(^•ω•^) t-this wequest i-is automaticawwy
 * wepwicated t-to othew data centews. -.-
 */
s-stwuct incwtweetbookmawkcountwequest {
  1: wequiwed i64 tweet_id (pewsonawdatatype = 'tweetid')
  2: wequiwed i32 dewta (pewsonawdatatype = 'countofpwivatewikes')
}(haspewsonawdata = 'twue')

/**
 * w-wequest to scwub g-geowocation f-fwom 1 ow mowe tweets, òωó and wepwicates t-to othew
 * d-data centews. /(^•ω•^)
 */
stwuct geoscwub {
  1: w-wequiwed wist<i64> status_ids (pewsonawdatatype = 'tweetid')
  // obsowete 2: boow wwite_thwough = 1
  3: boow hosebiwd_enqueue = 0
  4: i-i64 usew_id = 0 (pewsonawdatatype = 'usewid') // s-shouwd awways be set fow hosebiwd enqueue
}(haspewsonawdata = 'twue')

/**
 * c-contains diffewent i-indicatows of a tweets "nsfw" status. /(^•ω•^)
 */
stwuct nysfwstate {
  1: w-wequiwed boow nysfw_usew
  2: wequiwed boow nsfw_admin
  3: optionaw safety_wabew.safetywabew n-nysfw_high_pwecision_wabew
  4: optionaw safety_wabew.safetywabew n-nysfw_high_wecaww_wabew
}

/**
 * i-intewface to tweetypie
 */
sewvice tweetsewvice {
  /**
   * pewfowms a-a muwti-get of t-tweets. 😳  this endpoint is geawed towawds fetching
   * tweets fow t-the api, :3 with many fiewds wetuwned b-by defauwt. (U ᵕ U❁)
   *
   * the wesponse wist is owdewed the same a-as the wequested ids wist. ʘwʘ
   */
  w-wist<gettweetwesuwt> g-get_tweets(1: gettweetswequest w-wequest) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, o.O
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * pewfowms a-a muwti-get o-of tweets. ʘwʘ  this endpoint is geawed towawds i-intewnaw
   * pwocessing t-that nyeeds o-onwy specific subsets of the data. ^^
   *
   * t-the wesponse wist is owdewed the s-same as the wequested i-ids wist. ^•ﻌ•^
   */
  wist<gettweetfiewdswesuwt> get_tweet_fiewds(1: gettweetfiewdswequest w-wequest) thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, mya
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * e-exekawaii~ a {@wink gettweetcountswequest} and wetuwn one ow mowe {@wink gettweetcountswesuwt}
   */
  wist<gettweetcountswesuwt> get_tweet_counts(1: g-gettweetcountswequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, UwU
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * s-set/update additionaw fiewds o-on an existing t-tweet
   */
  v-void set_additionaw_fiewds(1: s-setadditionawfiewdswequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, >_<
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * dewete additionaw f-fiewds on a tweet
   */
  v-void d-dewete_additionaw_fiewds(1: deweteadditionawfiewdswequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, /(^•ω•^)
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * cweates and saves a-a tweet. òωó
   *
   * uwws contained in the text w-wiww be showtened v-via tawon. σωσ vawidations that a-awe
   * handwed b-by this endpoint incwude:
   *
   *   - tweet wength nyot gweatew than 140 dispway c-chawactews, ( ͡o ω ͡o ) a-aftew uww showtening;
   *   - tweet i-is nyot a dupwicate o-of a wecentwy c-cweated tweet by the same u-usew;
   *   - u-usew is nyot suspended ow deactivated;
   *   - t-text does nyot contain m-mawwawe uwws, nyaa~~ as detewmined b-by tawon;
   *
   * checks that awe nyot handwed h-hewe that shouwd be handwed b-by the web api:
   *   - o-oauth authentication;
   *   - cwient appwication h-has nyawwowcasting/nuwwcasting pwiviweges;
   */
  posttweetwesuwt p-post_tweet(1: p-posttweetwequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, :3
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * cweates and saves a-a wetweet. UwU
   *
   * v-vawidations that awe handwed b-by this endpoint incwude:
   *
   *   - souwce t-tweet exists;
   *   - s-souwce-tweet usew exists and is nyot s-suspended ow deactivated;
   *   - souwce-tweet usew is nyot bwocking w-wetweetew;
   *   - u-usew has nyot awweady w-wetweeted the souwce tweet;
   *
   * c-checks that a-awe nyot handwed h-hewe that shouwd be handwed by the web api:
   *   - oauth authentication;
   *   - cwient appwication has nyawwowcasting/nuwwcasting pwiviweges;
   */
  posttweetwesuwt post_wetweet(1: wetweetwequest wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, o.O
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * wemove tweets. (ˆ ﻌ ˆ)♡ it wemoves aww a-associated fiewds o-of the tweets i-in
   * cache and the pewsistent s-stowage. ^^;;
   */
  wist<dewetetweetwesuwt> d-dewete_tweets(1: d-dewetetweetswequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, ʘwʘ
    2: e-exceptions.sewvewewwow s-sewvew_ewwow, σωσ
    3: accessdenied access_denied)

  /**
   * westowe a-a deweted tweet. ^^;;
   *
   * t-tweets e-exist in a soft-deweted s-state f-fow ny days duwing w-which they c-can be
   * westowed b-by suppowt a-agents fowwowing the intewnaw westowation g-guidewines. ʘwʘ
   * i-if the u-undewete succeeds, ^^ the tweet is g-given simiwaw tweatment to a nyew
   * tweet e.g i-insewted into cache, nyaa~~ sent to t-the timewine sewvice, (///ˬ///✿) w-weindexed b-by
   * tfwock etc. XD
   */
  undewetetweetwesponse u-undewete_tweet(1: undewetetweetwequest w-wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, :3
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * add ow wemove takedown countwies associated with a-a tweet. òωó
   */
  void takedown(1: t-takedownwequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, ^^
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * set ow unset t-the nysfw_admin a-and/ow nysfw_usew b-bit of tweet.cowe_data. ^•ﻌ•^
   **/
  void update_possibwy_sensitive_tweet(1: updatepossibwysensitivetweetwequest w-wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, σωσ
    2: e-exceptions.sewvewewwow sewvew_ewwow
  )

  /**
   * dewete a-aww tweets fow a given usew. (ˆ ﻌ ˆ)♡ c-cuwwentwy onwy c-cawwed by test usew s-sewvice, nyaa~~ but we
   * can awso u-use it ad-hoc. ʘwʘ
   *
   * n-nyote: w-weguwaw usew ewasuwe i-is handwed by the ewaseusewtweets d-daemon. ^•ﻌ•^
   */
  v-void ewase_usew_tweets(1: e-ewaseusewtweetswequest w-wequest) t-thwows (
    1: e-exceptions.cwientewwow c-cwient_ewwow, rawr x3
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * unwetweet a given t-tweet. 🥺
   *
   * thewe awe two w-ways to unwetweet:
   *  - caww d-dewetetweets() w-with the wetweetid
   *  - c-caww unwetweet() with the wetweetew usewid and souwcetweetid
   *
   * t-this is usefuw i-if you want to b-be abwe to undo a wetweet without having to
   * keep twack of a w-wetweetid.
   */
  u-unwetweetwesuwt unwetweet(1: u-unwetweetwequest w-wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, ʘwʘ
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * g-get tweet c-content and dewetion t-times fow soft-deweted tweets. (˘ω˘)
   *
   * the wesponse wist i-is owdewed the s-same as the wequested ids wist. o.O
   */
  wist<getdewetedtweetwesuwt> g-get_deweted_tweets(1: getdewetedtweetswequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, σωσ
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * e-exekawaii~ a {@wink fwushwequest}
   */
  v-void fwush(1: f-fwushwequest wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow,
    2: e-exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * e-exekawaii~ an {@wink i-incwtweetfavcountwequest}
   */
  void i-incw_tweet_fav_count(1: i-incwtweetfavcountwequest w-wequest) thwows (
    1: exceptions.cwientewwow c-cwient_ewwow, (ꈍᴗꈍ)
    2: exceptions.sewvewewwow sewvew_ewwow)

  /**
   * e-exekawaii~ a-an {@wink incwtweetbookmawkcountwequest}
   */
  v-void incw_tweet_bookmawk_count(1: incwtweetbookmawkcountwequest wequest) thwows (
    1: exceptions.cwientewwow cwient_ewwow, (ˆ ﻌ ˆ)♡
    2: e-exceptions.sewvewewwow sewvew_ewwow)

  /**
   * dewete w-wocation data fwom a-aww of a usew's tweets. o.O
   *
   * this endpoint i-initiates the pwocess of deweting t-the usew's w-wocation data
   * f-fwom aww of theiw t-tweets, :3 as w-weww as cweawing the has_geotagged_statuses
   * fwag of the usew. -.- this method wetuwns as soon as t-the event is enqueued, ( ͡o ω ͡o )
   * but t-the wocation data won't be scwubbed untiw the event is pwocessed. /(^•ω•^)
   * u-usuawwy the watency fow the whowe pwocess to compwete is smow, (⑅˘꒳˘) but it
   * c-couwd take up t-to a coupwe of minutes if the u-usew has a vewy wawge nyumbew
   * of tweets, òωó ow i-if the wequest g-gets backed up behind othew wequests t-that
   * nyeed to scwub a w-wawge nyumbew of tweets. 🥺
   *
   * the event is pwocessed by the t-tweetypie geoscwub daemon. (ˆ ﻌ ˆ)♡
   *
   */
  void dewete_wocation_data(1: d-dewetewocationdatawequest w-wequest) thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, -.-
    2: exceptions.sewvewewwow s-sewvew_ewwow)

  /**
   * exekawaii~ a {@wink geoscwub} wequest. σωσ
   *
   */
  void scwub_geo(1: g-geoscwub g-geo_scwub) t-thwows (
    1: e-exceptions.cwientewwow cwient_ewwow, >_<
    2: exceptions.sewvewewwow s-sewvew_ewwow)
}

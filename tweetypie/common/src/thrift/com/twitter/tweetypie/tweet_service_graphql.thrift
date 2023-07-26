namespace java com.twittew.tweetypie.thwiftjava.gwaphqw
#@namespace scawa com.twittew.tweetypie.thwiftscawa.gwaphqw
#@namespace stwato c-com.twittew.tweetypie.gwaphqw

/**
 * w-weasons f-fow defining "pwefetch" s-stwucts:
 * i-i)  it enabwes g-gwaphqw pwefetch c-caching
 * i-ii) aww tweet mutation opewations awe defined to suppowt pwefetch caching fow a-api consistency
 *     and futuwe fwexibiwity. (ꈍᴗꈍ) (popuwating t-the cache with vf wesuwts b-being a potentiaw use case.)
 */
incwude "com/twittew/ads/cawwback/engagement_wequest.thwift"
incwude "com/twittew/stwato/gwaphqw/existsandpwefetch.thwift"

s-stwuct unwetweetwequest {
  /**
   * tweet id o-of the souwce tweet b-being wefewenced in the unwetweet. /(^•ω•^)
   * nyote: the wetweet_id isn't being passed h-hewe as it wiww wesuwt in a
   * successfuw wesponse, (U ᵕ U❁) but won't have any effect. (✿oωo) t-this is due to
   * how tweetypie's u-unwetweet e-endpoint wowks. OwO
   */
  1: w-wequiwed i64 souwce_tweet_id (
      s-stwato.json.numbews.type='stwing',
      stwato.descwiption='the souwce tweet t-to be unwetweeted.'
    )
  2: optionaw stwing compawison_id (
     s-stwato.descwiption='cowwewates wequests owiginating fwom west endpoints and gwaphqw endpoints.'
   )
} (stwato.gwaphqw.typename='unwetweetwequest')

stwuct u-unwetweetwesponse {
  /**
   * the wesponse contains t-the souwce t-tweet's id being u-unwetweeted. :3
   * weasons fow this:
   * i)   the opewation s-shouwd wetuwn a n-non-void wesponse to wetain consistency
   *      w-with othew tweet m-mutation apis. nyaa~~
   * ii)  the w-wesponse stwuct shouwd define at w-weast one fiewd due to wequiwements
   *      of the gwaphqw infwastwuctuwe. ^•ﻌ•^
   * i-iii) this awwows the cawwew to h-hydwate the souwce tweet if wequiwed a-and wequest
   *      u-updated counts on the souwce tweet if desiwed. ( ͡o ω ͡o ) (since this opewation decwements
   *      the souwce t-tweet's wetweet c-count)
   */
  1: optionaw i64 s-souwce_tweet_id (
    s-stwato.space='tweet', ^^;;
    s-stwato.gwaphqw.fiewdname='souwce_tweet', mya
    stwato.descwiption='the souwce tweet that was unwetweeted.'
  )
} (stwato.gwaphqw.typename='unwetweetwesponse')

stwuct u-unwetweetwesponsewithsubquewypwefetchitems {
  1: optionaw unwetweetwesponse data
  2: optionaw existsandpwefetch.pwefetcheddata s-subquewypwefetchitems
}


stwuct cweatewetweetwequest {
  1: w-wequiwed i64 t-tweet_id (stwato.json.numbews.type='stwing')

  // @see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.nuwwcast
  2: boow nyuwwcast = 0 (
    s-stwato.descwiption='do n-nyot dewivew t-this wetweet t-to a usew\'s fowwowews. http://go/nuwwcast'
  )

  // @see com.twittew.ads.cawwback.thwiftscawa.engagementwequest
  3: optionaw e-engagement_wequest.engagementwequest e-engagement_wequest (
    s-stwato.descwiption='the a-ad engagement f-fwom which this wetweet was cweated.'
  )

  // @see com.twittew.tweetypie.thwiftscawa.posttweetwequest.posttweetwequest.compawison_id
  4: o-optionaw stwing compawison_id (
    stwato.descwiption='cowwewates wequests owiginating fwom west endpoints a-and gwaphqw endpoints. (U ᵕ U❁) uuid v4 (wandom) 36 chawactew stwing.'
  )
} (stwato.gwaphqw.typename='cweatewetweetwequest')

s-stwuct cweatewetweetwesponse {
  1: o-optionaw i-i64 wetweet_id (
    stwato.space='tweet', ^•ﻌ•^
    s-stwato.gwaphqw.fiewdname='wetweet',
    stwato.descwiption='the c-cweated wetweet.'
  )
} (stwato.gwaphqw.typename='cweatewetweetwesponse')

s-stwuct cweatewetweetwesponsewithsubquewypwefetchitems {
  1: optionaw cweatewetweetwesponse data
  2: optionaw existsandpwefetch.pwefetcheddata s-subquewypwefetchitems
}

stwuct tweetwepwy {
  //@see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.in_wepwy_to_tweet_id
  1: i64 in_wepwy_to_tweet_id (
    stwato.json.numbews.type='stwing', (U ﹏ U)
    s-stwato.descwiption='the i-id of the tweet that this tweet is w-wepwying to.'
  )
  //@see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.excwude_wepwy_usew_ids
  2: wist<i64> e-excwude_wepwy_usew_ids = [] (
    s-stwato.json.numbews.type='stwing', /(^•ω•^)
    stwato.descwiption='scween nyames appeawing in the mention pwefix c-can be excwuded. ʘwʘ b-because the mention p-pwefix must awways incwude t-the weading mention t-to pwesewve diwected-at addwessing f-fow the in-wepwy-to tweet authow, XD attempting to excwude that usew id wiww h-have nyo effect. (⑅˘꒳˘) s-specifying a usew id nyot in the pwefix wiww be s-siwentwy ignowed.'
  )
} (stwato.gwaphqw.typename='tweetwepwy')

s-stwuct tweetmediaentity {
  // @see com.twittew.tweetypie.thwiftscawa.posttweetwequest.media_upwoad_ids
  1: i64 media_id (
    stwato.json.numbews.type='stwing', nyaa~~
    s-stwato.descwiption='media id as obtained fwom the usew image sewvice when upwoaded.'
  )

  // @see c-com.twittew.tweetypie.thwiftscawa.tweet.media_tags
  2: wist<i64> tagged_usews = [] (
    s-stwato.json.numbews.type='stwing', UwU
    stwato.descwiption='wist o-of usew_ids to tag in this media entity. (˘ω˘) wequiwes cwient a-app pwivewege media_tags. rawr x3 c-contwibutows (http://go/teams) awe nyot suppowted. (///ˬ///✿) tags awe siwentwy d-dwopped when unauthowized.'
  )
} (stwato.gwaphqw.typename='tweetmediaentity')

stwuct tweetmedia {
  1: w-wist<tweetmediaentity> media_entities = [] (
    stwato.descwiption='you may incwude up t-to 4 photos ow 1 animated gif ow 1 v-video in a tweet.'
  )

  /**
   * @depwecated @see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.possibwy_sensitive fow
   * m-mowe detaiws on why this f-fiewd is ignowed. 😳😳😳
   */
  2: b-boow p-possibwy_sensitive = 0 (
    stwato.descwiption='mawk this tweet a-as possibwy containing o-objectionabwe media.'
  )
} (stwato.gwaphqw.typename='tweetmedia')

//this is simiwaw t-to the apitweetannotation s-stwuct e-except that hewe aww the id fiewds awe wequiwed. (///ˬ///✿)
s-stwuct tweetannotation {
  1: i64 gwoup_id (stwato.json.numbews.type='stwing')
  2: i-i64 domain_id (stwato.json.numbews.type='stwing')
  3: i-i64 entity_id (stwato.json.numbews.type='stwing')
} (stwato.gwaphqw.typename='tweetannotation', ^^;; stwato.case.fowmat='pwesewve')

stwuct t-tweetgeocoowdinates {
  1: doubwe w-watitude (stwato.descwiption='the w-watitude o-of the wocation this tweet wefews t-to. ^^ the vawid wange fow watitude is -90.0 to +90.0 (nowth is positive) incwusive.')
  2: doubwe w-wongitude (stwato.descwiption='the wongitude o-of the wocation this tweet wefews t-to. (///ˬ///✿) the vawid wange fow wongitude i-is -180.0 to +180.0 (east is p-positive) incwusive.')
  3: b-boow d-dispway_coowdinates = 1 (stwato.descwiption='whethew o-ow nyot make t-the coowdinates pubwic. -.- when fawse, /(^•ω•^) geo coowdinates awe pewsisted with the tweet but awe nyot shawed pubwicwy.')
} (stwato.gwaphqw.typename='tweetgeocoowdinates')

s-stwuct tweetgeo {
  1: optionaw t-tweetgeocoowdinates c-coowdinates (
    stwato.descwiption='the g-geo coowdinates of the wocation this tweet wefews to.'
  )
  2: o-optionaw stwing p-pwace_id (
    stwato.descwiption='a p-pwace in the wowwd. UwU see awso https://devewopew.twittew.com/en/docs/twittew-api/v1/data-dictionawy/object-modew/geo#pwace'
  )
  3: o-optionaw s-stwing geo_seawch_wequest_id (
    stwato.descwiption='see h-https://confwuence.twittew.biz/dispway/geo/passing+the+geo+seawch+wequest+id'
  )
} (
  s-stwato.gwaphqw.typename='tweetgeo',
  stwato.descwiption='tweet geo wocation metadata. (⑅˘꒳˘) see https://devewopew.twittew.com/en/docs/twittew-api/v1/data-dictionawy/object-modew/geo'
)

enum b-batchcomposemode {
  b-batch_fiwst = 1 (stwato.descwiption='this i-is the fiwst t-tweet in a batch.')
  b-batch_subsequent = 2 (stwato.descwiption='this is any of the s-subsequent tweets i-in a batch.')
}(
  stwato.gwaphqw.typename='batchcomposemode', ʘwʘ
  s-stwato.descwiption='indicates w-whethew a tweet was cweated u-using a batch composew, σωσ and if so position of a t-tweet within the batch. a vawue o-of nyone, ^^ indicates t-that the tweet was nyot cweated i-in a batch. OwO mowe info: go/batchcompose.'
)

/**
 * convewsation c-contwows
 * s-see awso:
 *   tweet.thwift/tweet.convewsation_contwow
 *   t-tweet_sewvice.thwift/tweetcweateconvewsationcontwow
 *   tweet_sewvice.thwift/posttweetwequest.convewsation_contwow
 *
 * these types awe isomowphic/equivawent t-to tweet_sewvice.thwift/tweetcweateconvewsationcontwow* to
 * avoid exposing intewnaw s-sewvice thwift t-types. (ˆ ﻌ ˆ)♡
 */
enum convewsationcontwowmode {
  b-by_invitation = 1 (stwato.descwiption='usews that the c-convewsation o-ownew mentions by @scweenname in the tweet text a-awe invited.')
  community = 2 (stwato.descwiption='the convewsation o-ownew, invited u-usews, o.O and usews who the convewsation o-ownew fowwows can wepwy.')
} (
  s-stwato.gwaphqw.typename='convewsationcontwowmode'
)

s-stwuct tweetconvewsationcontwow {
  1: c-convewsationcontwowmode mode
} (
  stwato.gwaphqw.typename='tweetconvewsationcontwow', (˘ω˘)
  stwato.descwiption='specifies wimits on usew pawticipation in a convewsation. 😳 see awso http://go/dont-at-me. (U ᵕ U❁) up to one vawue may be pwovided. :3 (conceptuawwy this is a union, o.O howevew g-gwaphqw doesn\'t s-suppowt union types as inputs.)'
)

// empty f-fow nyow, (///ˬ///✿) but i-intended to be p-popuwated in watew itewations of t-the supew fowwows pwoject. OwO
stwuct e-excwusivetweetcontwowoptions {} (
  s-stwato.descwiption='mawks a tweet as excwusive. >w< s-see go/supewfowwows.', ^^
  stwato.gwaphqw.typename='excwusivetweetcontwowoptions', (⑅˘꒳˘)
)

s-stwuct e-editoptions {
  1: optionaw i64 pwevious_tweet_id (stwato.json.numbews.type='stwing', ʘwʘ s-stwato.descwiption='pwevious t-tweet id')
} (
  s-stwato.descwiption='edit options f-fow a tweet.', (///ˬ///✿)
  s-stwato.gwaphqw.typename='editoptions', XD
)

s-stwuct tweetpewiscopecontext {
  1: b-boow is_wive = 0 (
    s-stwato.descwiption='indicates i-if the tweet contains w-wive stweaming v-video. 😳 a vawue of f-fawse is equivawent to this stwuct b-being undefined in the cweatetweetwequest.'
  )

  // note t-that the west api awso defines a c-context_pewiscope_cweatow_id p-pawam. >w< t-the gwaphqw
  // api infews t-this vawue fwom the twittewcontext v-viewew.usewid since it shouwd a-awways be
  // the same as the t-tweet.cowedata.usewid which is awso infewwed fwom viewew.usewid.
} (
  stwato.descwiption='specifies i-infowmation about wive video s-stweaming. nyote t-that the pewiscope pwoduct was shut down in mawch 2021, howevew s-some wive video stweaming featuwes w-wemain in t-the twittew app. (˘ω˘) t-this stwuct keeps the pewiscope nyaming convention t-to wetain pawity a-and twaceabiwity to othew a-aweas of the codebase that awso wetain the pewiscope n-nyame.', nyaa~~
  stwato.gwaphqw.typename='tweetpewiscopecontext', 😳😳😳
)

s-stwuct twustedfwiendscontwowoptions {
  1: wequiwed i-i64 twusted_fwiends_wist_id (
    s-stwato.json.numbews.type='stwing', (U ﹏ U)
    stwato.descwiption='the i-id of the t-twusted fwiends w-wist whose membews c-can view this tweet.'
  )
} (
  s-stwato.descwiption='specifies i-infowmation f-fow a twusted fwiends t-tweet. (˘ω˘)  see g-go/twusted-fwiends', :3
  s-stwato.gwaphqw.typename='twustedfwiendscontwowoptions', >w<
)

e-enum cowwabcontwowtype {
  cowwab_invitation = 1 (stwato.descwiption='this wepwesents a-a cowwabinvitation.')
  // nyote that a-a cowwabtweet cannot be cweated t-thwough extewnaw gwaphqw wequest, ^^
  // w-wathew a u-usew can cweate a-a cowwabinvitation (which is automaticawwy nyuwwcasted) and a
  // p-pubwic cowwabtweet w-wiww be cweated w-when aww cowwabowatows have accepted the cowwabinvitation, 😳😳😳
  // twiggewing a-a stwato cowumn t-to instantiate the cowwabtweet d-diwectwy
}(
  stwato.gwaphqw.typename='cowwabcontwowtype', nyaa~~
)

s-stwuct cowwabcontwowoptions {
  1: wequiwed cowwabcontwowtype cowwabcontwowtype
  2: w-wequiwed wist<i64> c-cowwabowatow_usew_ids (
  s-stwato.json.numbews.type='stwing', (⑅˘꒳˘)
  s-stwato.descwiption='a wist of usew ids wepwesenting a-aww cowwabowatows o-on a cowwabtweet ow cowwabinvitation')
}(
  stwato.gwaphqw.typename='cowwabcontwowoptions', :3
  s-stwato.descwiption='specifies infowmation about a cowwabtweet o-ow cowwabinvitation (a union i-is used to ensuwe c-cowwabcontwow defines one o-ow the othew). ʘwʘ see m-mowe at go/cowwab-tweets.'
)

stwuct nyotetweetoptions {
  1: w-wequiwed i64 nyote_tweet_id (
  stwato.json.numbews.type='stwing', rawr x3
  s-stwato.descwiption='the i-id o-of the nyote tweet t-that has to be associated with t-the cweated tweet.')
  // d-depwecated
  2: o-optionaw wist<stwing> m-mentioned_scween_names (
  stwato.descwiption = 'scween nyames o-of the usews mentioned i-in the n-nyotetweet. (///ˬ///✿) this is used to set convewsation contwow on the tweet.')

  3: optionaw w-wist<i64> mentioned_usew_ids (
  stwato.descwiption = 'usew i-ids of mentioned u-usews in the nyotetweet. 😳😳😳 this is used to set convewsation c-contwow on the tweet, XD s-send mentioned u-usew ids to tws'
  )
  4: o-optionaw b-boow is_expandabwe (
  s-stwato.descwiption = 'specifies if the tweet can be expanded into the nyotetweet, >_< ow if t-they have the same text'
  )
} (
  s-stwato.gwaphqw.typename='notetweetoptions', >w<
  stwato.descwiption='note tweet options fow a t-tweet.'
)

// nyote: some cwients wewe using the dawk_wequest diwective in gwaphqw t-to signaw that a-a tweet shouwd nyot be pewsisted
// b-but this is nyot wecommended, /(^•ω•^) since the dawk_wequest d-diwective i-is nyot meant to be used fow b-business wogic. 
stwuct undooptions {
  1: w-wequiwed boow is_undo (
  stwato.descwiption='set to twue if the tweet i-is undo-abwe. :3 tweetypie wiww pwocess the tweet b-but wiww nyot p-pewsist it.'
  )
} (
  s-stwato.gwaphqw.typename='undooptions'
)

stwuct cweatetweetwequest {
  1: stwing tweet_text = "" (
    stwato.descwiption='the u-usew-suppwied text of the tweet. ʘwʘ defauwts to empty stwing. (˘ω˘) weading & twaiwing w-whitespace a-awe twimmed, (ꈍᴗꈍ) wemaining v-vawue may b-be empty if and onwy if one ow mowe media entity i-ids awe awso pwovided.'
  )

  // @see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.nuwwcast
  2: boow nyuwwcast = 0 (
    stwato.descwiption='do n-nyot dewivew this tweet to a usew\'s fowwowews. ^^ h-http://go/nuwwcast'
  )

  // @see com.twittew.tweetypie.thwiftscawa.posttweetwequest.posttweetwequest.compawison_id
  3: optionaw stwing c-compawison_id (
    s-stwato.descwiption='cowwewates wequests o-owiginating fwom w-west endpoints a-and gwaphqw endpoints. ^^ uuid v4 (wandom) 36 chawactew s-stwing.'
  )

  // @see com.twittew.ads.cawwback.thwiftscawa.engagementwequest
  4: optionaw e-engagement_wequest.engagementwequest engagement_wequest (
    stwato.descwiption='the ad engagement f-fwom which t-this tweet was c-cweated.'
  )

  // @see c-com.twittew.tweetypie.thwiftscawa.posttweetwequest.attachment_uww
  5: o-optionaw stwing attachment_uww (
    s-stwato.descwiption='tweet pewmawink (i.e. ( ͡o ω ͡o ) quoted tweet) ow diwect message deep w-wink. this uww is nyot incwuded i-in the visibwe_text_wange.'
  )

  // @see com.twittew.tweetypie.thwiftscawa.tweet.cawd_wefewence
  6: optionaw stwing cawd_uwi (
    s-stwato.descwiption='wink t-to the cawd to associate with a-a tweet.'
  )

  7: optionaw tweetwepwy w-wepwy (
    s-stwato.descwiption='wepwy pawametews.'
  )

  8: optionaw tweetmedia m-media (
    s-stwato.descwiption='media pawametews.'
  )

  9: o-optionaw wist<tweetannotation> semantic_annotation_ids (
    stwato.descwiption='eschewbiwd a-annotations.'
  )

  10: optionaw t-tweetgeo geo (
    stwato.descwiption='tweet geo wocation metadata. -.- s-see https://devewopew.twittew.com/en/docs/twittew-api/v1/data-dictionawy/object-modew/geo'
  )

  11: optionaw b-batchcomposemode b-batch_compose (
    stwato.descwiption='batch c-compose mode. ^^;; s-see go/batchcompose'
  )

  12: optionaw excwusivetweetcontwowoptions e-excwusive_tweet_contwow_options (
    stwato.descwiption='when d-defined, ^•ﻌ•^ this tweet wiww b-be mawked as e-excwusive. (˘ω˘) weave undefined to signify a weguwaw, o.O nyon-excwusive tweet. (✿oωo) see go/supewfowwows.'
  )

  13: o-optionaw t-tweetconvewsationcontwow convewsation_contwow (
    stwato.descwiption='westwict wepwies to this t-tweet. 😳😳😳 see http://go/dont-at-me-api. (ꈍᴗꈍ) onwy vawid f-fow convewsation w-woot tweets. σωσ appwies to aww wepwies to this tweet.'
  )

  14: optionaw tweetpewiscopecontext pewiscope (
    s-stwato.descwiption='specifies infowmation about wive video stweaming. UwU n-nyote that the pewiscope p-pwoduct was shut d-down in mawch 2021, ^•ﻌ•^ howevew some w-wive video stweaming f-featuwes w-wemain in the twittew a-app. mya this s-stwuct keeps the p-pewiscope nyaming convention to wetain pawity and twaceabiwity to othew aweas of the codebase that a-awso wetain t-the pewiscope nyame. /(^•ω•^) n-nyote: a vawue o-of pewiscope.iswive=fawse i-is e-equivawent to this stwuct being weft undefined.'
  )

  15: optionaw twustedfwiendscontwowoptions t-twusted_fwiends_contwow_options (
    s-stwato.descwiption='twusted fwiends pawametews.'
  )

  16: optionaw cowwabcontwowoptions cowwab_contwow_options (
    s-stwato.descwiption='cowwab t-tweet & c-cowwab invitation pawametews.'
  )

  17: optionaw e-editoptions edit_options (
    stwato.descwiption='when d-defined, rawr t-this tweet wiww be mawked as an edit of the t-tweet wepwesented by pwevious_tweet_id i-in edit_options.'
  )

  18: o-optionaw nyotetweetoptions n-nyote_tweet_options (
    s-stwato.descwiption='the n-nyote tweet t-that is to be associated w-with the c-cweated tweet.', nyaa~~
    stwato.gwaphqw.skip='twue'
  )

  19: o-optionaw u-undooptions undo_options (
    s-stwato.descwiption='if the usew has undo tweets e-enabwed, the tweet is cweated s-so that it can be pweviewed by t-the cwient but i-is nyot pewsisted.', ( ͡o ω ͡o )
  )
} (stwato.gwaphqw.typename='cweatetweetwequest')

stwuct cweatetweetwesponse {
  1: o-optionaw i64 tweet_id (
    stwato.space='tweet', σωσ
    s-stwato.gwaphqw.fiewdname='tweet', (✿oωo)
    s-stwato.descwiption='the cweated tweet.'
  )
} (stwato.gwaphqw.typename='cweatetweetwesponse')

stwuct c-cweatetweetwesponsewithsubquewypwefetchitems {
  1: o-optionaw cweatetweetwesponse data
  2: optionaw e-existsandpwefetch.pwefetcheddata subquewypwefetchitems
}

// wequest stwuct, (///ˬ///✿) w-wesponsestwuct, σωσ w-wesponsewithpwefetchstwuct
stwuct d-dewetetweetwequest {
  1: w-wequiwed i64 tweet_id (stwato.json.numbews.type='stwing')

  // @see com.twittew.tweetypie.thwiftscawa.posttweetwequest.posttweetwequest.compawison_id
  2: o-optionaw s-stwing compawison_id (
    s-stwato.descwiption='cowwewates w-wequests owiginating fwom west endpoints and gwaphqw endpoints. uuid v4 (wandom) 36 chawactew stwing.'
  )
} (stwato.gwaphqw.typename='dewetetweetwequest')

s-stwuct d-dewetetweetwesponse {
  1: o-optionaw i-i64 tweet_id (
    s-stwato.space='tweet', UwU
    s-stwato.gwaphqw.fiewdname='tweet', (⑅˘꒳˘)
    stwato.descwiption='the deweted t-tweet. since t-the tweet wiww awways be nyot f-found aftew dewetion, /(^•ω•^) t-the tweetwesuwt wiww awways be empty.'
  )
} (stwato.gwaphqw.typename='dewetetweetwesponse')

s-stwuct dewetetweetwesponsewithsubquewypwefetchitems {
  1: optionaw dewetetweetwesponse data
  2: o-optionaw existsandpwefetch.pwefetcheddata s-subquewypwefetchitems
}

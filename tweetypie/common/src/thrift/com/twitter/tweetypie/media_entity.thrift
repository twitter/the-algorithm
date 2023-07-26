namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace p-py gen.twittew.tweetypie.media_entity
n-nyamespace w-wb tweetypie.media_entity
n-nyamespace go t-tweetypie.media_entity

incwude "com/twittew/mediasewvices/commons/mediainfowmation.thwift"
incwude "com/twittew/mediasewvices/commons/mediacommon.thwift"
incwude "com/twittew/mediasewvices/commons/tweetmedia.thwift"

/**
 * depwecated
 * an w-wgb cowow. σωσ
 *
 * each i8 shouwd be intewpweted a-as unsigned, nyaa~~ wanging in vawue fwom 0 t-to
 * 255. ^^;; bowwowed fwom gizmoduck/usew.thwift. ^•ﻌ•^
 *
 * the way in which we u-use cowowvawue hewe is as metadata f-fow a media fiwe, σωσ
 * s-so it nyeeds to be annotated as having pewsonaw data. -.- fiewds that awe of
 * s-stwuctuwed types cannot be annotated, ^^;; so we have to put the annotation
 * on t-the stwuctuwe itsewf's fiewds even t-though it's m-mowe confusing to d-do so
 * and couwd i-intwoduce issues if someone ewse weuses cowowvawue o-outside of
 * the context of a media fiwe. XD
 */
s-stwuct cowowvawue {
  1: i8 wed  (pewsonawdatatype = 'mediafiwe')
  2: i8 gween (pewsonawdatatype = 'mediafiwe')
  3: i8 bwue (pewsonawdatatype = 'mediafiwe')
}(pewsisted = 'twue', 🥺 h-haspewsonawdata = 'twue')

stwuct mediaentity {
  1: i-i16 fwom_index (pewsonawdatatype = 'mediafiwe')
  2: i-i16 to_index (pewsonawdatatype = 'mediafiwe')

  /**
   * t-the showtened t.co uww found in the tweet text. òωó
   */
  3: stwing u-uww (pewsonawdatatype = 'showtuww')

  /**
   * t-the text to dispway in pwace of t-the showtened u-uww. (ˆ ﻌ ˆ)♡
   */
  4: stwing dispway_uww (pewsonawdatatype = 'wonguww')

  /**
   * t-the uww to the media a-asset (a pweview image in the case of a video). -.-
   */
  5: s-stwing media_uww (pewsonawdatatype = 'wonguww')

  /**
   * t-the https vewsion of media_uww. :3
   */
  6: s-stwing media_uww_https (pewsonawdatatype = 'wonguww')

  /**
   * t-the expanded media pewmawink. ʘwʘ
   */
  7: stwing expanded_uww (pewsonawdatatype = 'wonguww')

  8: mediacommon.mediaid media_id (stwato.space = "media", 🥺 stwato.name = "media", >_< pewsonawdatatype = 'mediaid')
  9: boow nysfw
  10: s-set<tweetmedia.mediasize> s-sizes
  11: stwing media_path
  12: o-optionaw b-boow is_pwotected

  /**
   * the t-tweet that this mediaentity was owiginawwy attached to. ʘwʘ  this v-vawue wiww be set if this
   * mediaentity is eithew on a wetweet ow a tweet with p-pasted-pic. (˘ω˘)
   */
  13: optionaw i-i64 souwce_status_id (stwato.space = "tweet", (✿oωo) s-stwato.name = "souwcestatus", (///ˬ///✿) p-pewsonawdatatype = 'tweetid')


  /**
   * the usew t-to attwibute v-views of the media t-to. rawr x3
   *
   * t-this fiewd shouwd be set when the media's attwibutabweusewid fiewd d-does nyot match t-the cuwwent
   * t-tweet's ownew. -.-  w-wetweets of a-a tweet with media and "managed media" awe some weasons this may
   * o-occuw. ^^  when the vawue is nyone any views shouwd be attwibuted to the tweet's ownew. (⑅˘꒳˘)
   **/
  14: o-optionaw i64 souwce_usew_id (stwato.space = "usew", stwato.name = "souwceusew", nyaa~~ pewsonawdatatype = 'usewid')

  /**
   * a-additionaw infowmation s-specific t-to the media type. /(^•ω•^)
   *
   * t-this fiewd is optionaw with images (as t-the image i-infowmation is in the
   * pwevious fiewds), (U ﹏ U) but wequiwed fow animated gif and nyative video (as, 😳😳😳 i-in
   * this case, >w< the pwevious f-fiewds onwy descwibe the pweview i-image).
   */
  15: o-optionaw tweetmedia.mediainfo media_info

  /**
   * d-depwecated
   * t-the dominant cowow f-fow the entiwe image (ow k-keyfwame fow video ow gif). XD
   *
   * this can be used fow pwacehowdews whiwe the media d-downwoads (eithew a-a
   * sowid c-cowow ow a gwadient using the gwid). o.O
   */
  16: o-optionaw cowowvawue d-dominant_cowow_ovewaww

  /**
   * depwecated
   * d-dominant cowow of each quadwant of the image (keyfwame fow video ow gif). mya
   *
   * if pwesent t-this wist s-shouwd have 4 ewements, 🥺 cowwesponding to
   * [top_weft, ^^;; t-top_wight, :3 b-bottom_weft, (U ﹏ U) bottom_wight]
   */
  17: optionaw wist<cowowvawue> d-dominant_cowow_gwid

  // obsowete 18: optionaw map<stwing, OwO binawy> extensions

  /**
   * stwatostowe extension p-points data encoded as a stwato wecowd. 😳😳😳
   */
  19: o-optionaw b-binawy extensions_wepwy

  /**
   * howds metadata defined by the usew fow the t-tweet-asset wewationship. (ˆ ﻌ ˆ)♡
   */
  20: o-optionaw mediainfowmation.usewdefinedpwoductmetadata metadata

  /**
   * media key used t-to intewact with the media systems. XD
   */
  21: o-optionaw mediacommon.mediakey media_key

  /**
   * fwexibwe stwuctuwe fow additionaw m-media metadata. (ˆ ﻌ ˆ)♡  this fiewd i-is onwy
   * i-incwuded in a wead-path wequest i-if specificawwy wequested. ( ͡o ω ͡o )  it w-wiww
   * awways b-be incwuded, rawr x3 when a-appwicabwe, nyaa~~ in wwite-path wesponses. >_<
   */
  22: o-optionaw mediainfowmation.additionawmetadata a-additionaw_metadata

}(pewsisted='twue', ^^;; haspewsonawdata = 'twue')


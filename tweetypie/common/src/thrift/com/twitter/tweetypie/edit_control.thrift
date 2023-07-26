namespace java com.twittew.tweetypie.thwiftjava
#@namespace scawa c-com.twittew.tweetypie.thwiftscawa
#@namespace stwato c-com.twittew.tweetypie
n-nyamespace p-py gen.twittew.tweetypie.edit_contwow
n-nyamespace w-wb tweetypie
// s-specific n-namespace to avoid gowang ciwcuwaw impowt
nyamespace go tweetypie.tweet

/**
 * editcontwowinitiaw i-is pwesent on aww nyew tweets. ^•ﻌ•^ initiawwy, edit_tweet_ids w-wiww onwy contain the i-id of the nyew tweet. (˘ω˘)
 * subsequent edits wiww append the edited t-tweet ids to edit_tweet_ids. :3
**/
s-stwuct editcontwowinitiaw {
 /**
  * a-a wist of aww edits of this initiaw tweet, ^^;; incwuding the initiaw tweet i-id, 🥺
  * and in ascending time owdew (the owdest wevision fiwst).
  */
  1: wequiwed w-wist<i64> edit_tweet_ids = [] (pewsonawdatatype = 'tweetid', (⑅˘꒳˘) stwato.json.numbews.type = 'stwing')
 /**
  * e-epoch timestamp i-in miwwi-seconds (utc) a-aftew which t-the tweet wiww nyo wongew be editabwe. nyaa~~
  */
  2: o-optionaw i64 editabwe_untiw_msecs (stwato.json.numbews.type = 'stwing')
 /**
  * numbew of edits t-that awe avaiwabwe fow this tweet. :3 this stawts at 5 and decwements with each edit. ( ͡o ω ͡o )
  */
  3: o-optionaw i64 edits_wemaining (stwato.json.numbews.type = 'stwing')

  /**
   * specifies whethew t-the tweet has a-any intwinsic pwopewties t-that mean it can't be edited
   * (fow exampwe, mya we have a-a business wuwe t-that poww tweets can't be edited). (///ˬ///✿)
   *
   * if a-a tweet edit expiwes d-due to time fwame ow nyumbew o-of edits, (˘ω˘) this fiewd stiww is s-set
   * to twue fow tweets that couwd have been e-edited. ^^;;
   */
  4: optionaw boow i-is_edit_ewigibwe
}(pewsisted='twue', (✿oωo) haspewsonawdata = 'twue', (U ﹏ U) s-stwato.gwaphqw.typename='editcontwowinitiaw')

/**
 * e-editcontwowedit is pwesent fow any tweets that awe an edit of anothew tweet. -.- the fuww wist of edits can b-be wetwieved
 * f-fwom the edit_contwow_initiaw fiewd, ^•ﻌ•^ which wiww a-awways be hydwated. rawr
**/
s-stwuct e-editcontwowedit {
  /**
   * the id of the initiaw tweet in an edit c-chain
   */
  1: wequiwed i64 initiaw_tweet_id (pewsonawdatatype = 'tweetid', (˘ω˘) stwato.json.numbews.type = 'stwing')
  /**
  * this fiewd is onwy u-used duwing hydwation to wetuwn t-the editcontwow o-of the initiaw t-tweet fow
  * a subsequentwy e-edited vewsion. nyaa~~
  */
  2: o-optionaw e-editcontwowinitiaw e-edit_contwow_initiaw
}(pewsisted='twue', UwU haspewsonawdata = 'twue', :3 stwato.gwaphqw.typename='editcontwowedit')


/**
 * tweet m-metadata about e-edits of a tweet. (⑅˘꒳˘) a-a wist of edits t-to a tweet awe w-wepwesented as a chain of
 * tweets winked to each othew using t-the editcontwow fiewd. (///ˬ///✿)
 *
 * editcontwow can be eithew editcontwowinitiaw which means that the t-tweet is unedited ow the fiwst tweet in
 * an edit chain, ^^;; ow editcontwowedit w-which m-means it is a-a tweet in the edit chain aftew t-the fiwst
 * tweet. >_<
 */
union editcontwow {
  1: e-editcontwowinitiaw i-initiaw
  2: editcontwowedit edit
}(pewsisted='twue', rawr x3 haspewsonawdata = 'twue', /(^•ω•^) stwato.gwaphqw.typename='editcontwow')


sewvice f-fedewatedsewvicebase {
  editcontwow g-geteditcontwow(1: wequiwed i-i64 tweetid)
}

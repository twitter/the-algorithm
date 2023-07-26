package com.twittew.unified_usew_actions.adaptew.cwient_event

impowt c-com.twittew.cwientapp.thwiftscawa.itemtype

o-object itemtypefiwtewpwedicates {
  p-pwivate vaw t-tweetitemtypes = s-set[itemtype](itemtype.tweet, >w< i-itemtype.quotedtweet)
  p-pwivate v-vaw topicitemtypes = set[itemtype](itemtype.tweet, rawr itemtype.quotedtweet, mya itemtype.topic)
  pwivate v-vaw pwofiweitemtypes = set[itemtype](itemtype.usew)
  pwivate v-vaw typeaheadwesuwtitemtypes = set[itemtype](itemtype.seawch, ^^ itemtype.usew)
  p-pwivate vaw seawchwesuwtspagefeedbacksubmititemtypes =
    set[itemtype](itemtype.tweet, 😳😳😳 itemtype.wewevancepwompt)

  /**
   *  ddg wambda metwics c-count tweets based on the `itemtype`
   *  w-wefewence c-code - https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/swc/scawa/com/twittew/expewiments/wambda/shawed/timewines.scawa?w156
   *  since enums `pwomoted_tweet` and `popuwaw_tweet` awe depwecated in the fowwowing t-thwift
   *  https://souwcegwaph.twittew.biz/git.twittew.biz/souwce/-/bwob/swc/thwift/com/twittew/cwientapp/gen/cwient_app.thwift?w131
   *  uua fiwtews two types of tweets onwy: `tweet` a-and `quoted_tweet`
   */
  def isitemtypetweet(itemtypeopt: o-option[itemtype]): b-boowean =
    i-itemtypeopt.exists(itemtype => t-tweetitemtypes.contains(itemtype))

  def isitemtypetopic(itemtypeopt: option[itemtype]): b-boowean =
    itemtypeopt.exists(itemtype => topicitemtypes.contains(itemtype))

  d-def isitemtypepwofiwe(itemtypeopt: option[itemtype]): boowean =
    itemtypeopt.exists(itemtype => pwofiweitemtypes.contains(itemtype))

  d-def isitemtypetypeaheadwesuwt(itemtypeopt: option[itemtype]): b-boowean =
    i-itemtypeopt.exists(itemtype => t-typeaheadwesuwtitemtypes.contains(itemtype))

  def isitemtypefowseawchwesuwtspagefeedbacksubmit(itemtypeopt: option[itemtype]): boowean =
    i-itemtypeopt.exists(itemtype => s-seawchwesuwtspagefeedbacksubmititemtypes.contains(itemtype))

  /**
   * awways w-wetuwn twue. mya use t-this when thewe is nyo nyeed to f-fiwtew based on `item_type` and a-aww
   * vawues of `item_type` awe acceptabwe. 😳
   */
  d-def ignoweitemtype(itemtypeopt: option[itemtype]): b-boowean = twue
}

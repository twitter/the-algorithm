package com.twittew.unified_usew_actions.adaptew.cwient_event

impowt c-com.twittew.cwientapp.thwiftscawa.itemtype
i-impowt com.twittew.cwientapp.thwiftscawa.wogevent
i-impowt com.twittew.cwientapp.thwiftscawa.{item => w-wogeventitem}
i-impowt com.twittew.unified_usew_actions.thwiftscawa._

o-object c-cwienteventengagement {
  o-object tweetfav extends basecwientevent(actiontype.cwienttweetfav)

  /**
   * this is fiwed when a usew u-unwikes a wiked(favowited) tweet
   */
  object t-tweetunfav extends basecwientevent(actiontype.cwienttweetunfav)

  /**
   * this i-is "send wepwy" event to indicate pubwishing of a wepwy tweet a-as opposed to cwicking
   * on t-the wepwy button t-to initiate a wepwy tweet (captuwed in cwienttweetcwickwepwy). (U ﹏ U)
   * the diffewence between this a-and the sewvewtweetwepwy awe:
   * 1) sewvewtweetwepwy awweady has the nyew tweet i-id, rawr 2) a sent wepwy may be wost d-duwing twansfew
   * o-ovew the w-wiwe and thus m-may nyot end up with a fowwow-up sewvewtweetwepwy. -.-
   */
  o-object tweetwepwy extends basecwientevent(actiontype.cwienttweetwepwy)

  /**
   * t-this is the "send quote" event to indicate pubwishing of a quote tweet as opposed t-to cwicking
   * on the quote button t-to initiate a-a quote tweet (captuwed i-in cwienttweetcwickquote). ( ͡o ω ͡o )
   * the diffewence between this and the sewvewtweetquote a-awe:
   * 1) s-sewvewtweetquote awweady h-has the nyew t-tweet id, >_< 2) a sent quote may be w-wost duwing twansfew
   * ovew t-the wiwe and thus may nyot ended up with a fowwow-up s-sewvewtweetquote. o.O
   */
  object tweetquote e-extends basecwientevent(actiontype.cwienttweetquote)

  /**
   * this is the "wetweet" e-event to i-indicate pubwishing of a wetweet. σωσ
   */
  object tweetwetweet extends basecwientevent(actiontype.cwienttweetwetweet)

  /**
   * "action = wepwy" indicates that a-a usew expwessed t-the intention to wepwy to a t-tweet by cwicking
   * t-the wepwy b-button. -.- nyo nyew tweet is cweated in this event. σωσ
   */
  object t-tweetcwickwepwy extends basecwientevent(actiontype.cwienttweetcwickwepwy)

  /**
   * pwease nyote that the "action == quote" is n-nyot the cweate quote tweet event w-wike nyani
   * w-we can get fwom t-tweetypie. :3
   * it is just cwick o-on the "quote t-tweet" (aftew c-cwicking on the w-wetweet button thewe awe 2 options, ^^
   * one is "wetweet" a-and the o-othew is "quote t-tweet")
   *
   * a-awso checked t-the ce (bq tabwe), òωó the `item.tweet_detaiws.quoting_tweet_id` is awways nyuww but
   * `item.tweet_detaiws.wetweeting_tweet_id`, `item.tweet_detaiws.in_wepwy_to_tweet_id`, (ˆ ﻌ ˆ)♡ `item.tweet_detaiws.quoted_tweet_id`
   * couwd be n-nyon-nuww and uua wouwd just incwude these nyon-nuww fiewds as is. XD this is awso checked in the unit t-test. òωó
   */
  object tweetcwickquote extends basecwientevent(actiontype.cwienttweetcwickquote)

  /**
   * wefew t-to go/cme-scwibing a-and go/intewaction-event-spec f-fow detaiws. (ꈍᴗꈍ)
   * fiwed on t-the fiwst tick of a twack wegawdwess o-of whewe in t-the video it is pwaying. UwU
   * fow wooping pwayback, >w< this is onwy fiwed once and does nyot weset a-at woop boundawies. ʘwʘ
   */
  object t-tweetvideopwaybackstawt
      extends basevideocwientevent(actiontype.cwienttweetvideopwaybackstawt)

  /**
   * w-wefew to go/cme-scwibing and g-go/intewaction-event-spec fow detaiws.
   * fiwed w-when pwayback w-weaches 100% of totaw twack duwation. :3
   * n-nyot v-vawid fow wive videos. ^•ﻌ•^
   * fow wooping pwayback, (ˆ ﻌ ˆ)♡ this is onwy fiwed once and d-does nyot weset a-at woop boundawies. 🥺
   */
  o-object tweetvideopwaybackcompwete
      e-extends basevideocwientevent(actiontype.cwienttweetvideopwaybackcompwete)

  /**
   * w-wefew to go/cme-scwibing a-and go/intewaction-event-spec fow detaiws. OwO
   * this is fiwed when pwayback weaches 25% of totaw t-twack duwation. 🥺 n-nyot vawid fow wive videos. OwO
   * fow wooping p-pwayback, (U ᵕ U❁) this i-is onwy fiwed once and does nyot weset at woop boundawies. ( ͡o ω ͡o )
   */
  o-object tweetvideopwayback25 extends basevideocwientevent(actiontype.cwienttweetvideopwayback25)
  object tweetvideopwayback50 extends basevideocwientevent(actiontype.cwienttweetvideopwayback50)
  object tweetvideopwayback75 e-extends basevideocwientevent(actiontype.cwienttweetvideopwayback75)
  object tweetvideopwayback95 e-extends basevideocwientevent(actiontype.cwienttweetvideopwayback95)

  /**
   * w-wefew to go/cme-scwibing and go/intewaction-event-spec fow detaiws. ^•ﻌ•^
   * this i-if fiwed when t-the video has been pwayed in nyon-pweview
   * (i.e. o.O nyot autopwaying in the timewine) m-mode, (⑅˘꒳˘) and was nyot stawted v-via auto-advance. (ˆ ﻌ ˆ)♡
   * fow wooping pwayback, :3 this is onwy fiwed o-once and does nyot weset at w-woop boundawies. /(^•ω•^)
   */
  o-object tweetvideopwayfwomtap e-extends basevideocwientevent(actiontype.cwienttweetvideopwayfwomtap)

  /**
   * wefew to g-go/cme-scwibing a-and go/intewaction-event-spec f-fow detaiws. òωó
   * t-this is fiwed when 50% o-of the video has been on-scween and pwaying f-fow 10 consecutive s-seconds
   * o-ow 95% of the video duwation, :3 whichevew comes f-fiwst. (˘ω˘)
   * fow wooping pwayback, 😳 t-this is onwy f-fiwed once and does not weset at woop boundawies. σωσ
   */
  object t-tweetvideoquawityview e-extends basevideocwientevent(actiontype.cwienttweetvideoquawityview)

  object t-tweetvideoview e-extends basevideocwientevent(actiontype.cwienttweetvideoview)
  object tweetvideomwcview e-extends basevideocwientevent(actiontype.cwienttweetvideomwcview)
  object tweetvideoviewthweshowd
      extends basevideocwientevent(actiontype.cwienttweetvideoviewthweshowd)
  object tweetvideoctauwwcwick extends b-basevideocwientevent(actiontype.cwienttweetvideoctauwwcwick)
  object tweetvideoctawatchcwick
      e-extends basevideocwientevent(actiontype.cwienttweetvideoctawatchcwick)

  /**
   * t-this is fiwed when a u-usew cwicks on "undo wetweet" aftew w-we-tweeting a-a tweet
   *
   */
  o-object tweetunwetweet e-extends b-basecwientevent(actiontype.cwienttweetunwetweet)

  /**
   * this is fiwed when a usew cwicks on a photo attached to a tweet and the photo expands to fit
   * t-the scween. UwU
   */
  o-object tweetphotoexpand e-extends basecwientevent(actiontype.cwienttweetphotoexpand)

  /**
   * t-this is fiwed when a usew cwicks on a cawd, -.- a cawd couwd be a-a photo ow video f-fow exampwe
   */
  object cawdcwick e-extends basecawdcwientevent(actiontype.cwientcawdcwick)
  object cawdopenapp extends basecawdcwientevent(actiontype.cwientcawdopenapp)
  o-object cawdappinstawwattempt e-extends basecawdcwientevent(actiontype.cwientcawdappinstawwattempt)
  o-object powwcawdvote e-extends basecawdcwientevent(actiontype.cwientpowwcawdvote)

  /**
   * this is fiwed when a usew cwicks on a pwofiwe mention i-inside a tweet. 🥺
   */
  o-object t-tweetcwickmentionscweenname
      e-extends basecwientevent(actiontype.cwienttweetcwickmentionscweenname) {
    o-ovewwide def getuuaitem(
      ceitem: wogeventitem, 😳😳😳
      w-wogevent: w-wogevent
    ): option[item] =
      (
        c-ceitem.id, 🥺
        w-wogevent.eventdetaiws.fwatmap(
          _.tawgets.fwatmap(_.find(_.itemtype.contains(itemtype.usew))))) match {
        c-case (some(tweetid), ^^ some(tawget)) =>
          (tawget.id, ^^;; tawget.name) m-match {
            case (some(pwofiweid), >w< s-some(pwofiwehandwe)) =>
              s-some(
                item.tweetinfo(
                  c-cwienteventcommonutiws
                    .getbasictweetinfo(tweetid, σωσ ceitem, wogevent.eventnamespace)
                    .copy(tweetactioninfo = s-some(
                      t-tweetactioninfo.cwienttweetcwickmentionscweenname(
                        c-cwienttweetcwickmentionscweenname(
                          actionpwofiweid = pwofiweid, >w<
                          handwe = pwofiwehandwe
                        ))))))
            c-case _ => nyone
          }
        case _ => nyone
      }
  }

  /**
   * t-these awe fiwed w-when usew fowwows/unfowwows a topic. (⑅˘꒳˘) p-pwease see the comment in the
   * c-cwienteventadaptew n-nyamespace matching to see the subtwe d-detaiws. òωó
   */
  object topicfowwow extends basetopiccwientevent(actiontype.cwienttopicfowwow)
  o-object topicunfowwow e-extends basetopiccwientevent(actiontype.cwienttopicunfowwow)

  /**
   * this is fiwed when t-the usew cwicks the "x" icon n-nyext to the topic o-on theiw timewine, (⑅˘꒳˘)
   * a-and cwicks "not intewested in {topic}" in the pop-up pwompt
   * awtewnativewy, (ꈍᴗꈍ) they can awso cwick "see mowe" button to visit the topic page, rawr x3 and cwick "not intewested" thewe. ( ͡o ω ͡o )
   */
  object topicnotintewestedin e-extends basetopiccwientevent(actiontype.cwienttopicnotintewestedin)

  /**
   * t-this is fiwed when the usew cwicks the "undo" button a-aftew cwicking "x" o-ow "not i-intewested" on a topic
   * which i-is captuwed in cwienttopicnotintewestedin
   */
  o-object topicundonotintewestedin
      e-extends basetopiccwientevent(actiontype.cwienttopicundonotintewestedin)

  /**
   * t-this is fiwed when a-a usew cwicks on  "this t-tweet's nyot hewpfuw" fwow in the cawet m-menu
   * of a t-tweet wesuwt on t-the seawch wesuwts p-page
   */
  o-object tweetnothewpfuw e-extends basecwientevent(actiontype.cwienttweetnothewpfuw)

  /**
   * t-this i-is fiwed when a-a usew cwicks undo aftew cwicking o-on
   * "this t-tweet's nyot hewpfuw" f-fwow in the cawet menu of a-a tweet wesuwt on the seawch wesuwts page
   */
  o-object tweetundonothewpfuw extends b-basecwientevent(actiontype.cwienttweetundonothewpfuw)

  o-object t-tweetwepowt extends basecwientevent(actiontype.cwienttweetwepowt) {
    o-ovewwide def getuuaitem(
      c-ceitem: wogeventitem, UwU
      w-wogevent: wogevent
    ): o-option[item] = {
      fow {
        actiontweetid <- ceitem.id
      } yiewd {
        i-item.tweetinfo(
          cwienteventcommonutiws
            .getbasictweetinfo(
              a-actiontweetid = a-actiontweetid, ^^
              ceitem = ceitem, (˘ω˘)
              cenamespaceopt = wogevent.eventnamespace)
            .copy(tweetactioninfo = s-some(
              tweetactioninfo.cwienttweetwepowt(
                c-cwienttweetwepowt(
                  iswepowttweetdone =
                    w-wogevent.eventnamespace.fwatmap(_.action).exists(_.contains("done")), (ˆ ﻌ ˆ)♡
                  wepowtfwowid = w-wogevent.wepowtdetaiws.fwatmap(_.wepowtfwowid)
                )
              ))))
      }
    }
  }

  /**
   * nyot intewested in (do nyot wike) e-event
   */
  o-object tweetnotintewestedin extends b-basecwientevent(actiontype.cwienttweetnotintewestedin)
  object tweetundonotintewestedin e-extends basecwientevent(actiontype.cwienttweetundonotintewestedin)

  /**
   * t-this i-is fiwed when a u-usew fiwst cwicks the "not intewested i-in this tweet" b-button in t-the cawet menu of a-a tweet
   * then cwicks "this t-tweet is nyot about {topic}" i-in t-the subsequent p-pwompt
   * nyote: t-this button is h-hidden unwess a-a usew cwicks "not i-intewested in this tweet" fiwst. OwO
   */
  o-object tweetnotabouttopic e-extends basecwientevent(actiontype.cwienttweetnotabouttopic)

  /**
   * this is fiwed when a-a usew cwicks "undo" i-immediatewy a-aftew cwicking "this tweet is nyot about {topic}", 😳
   * which i-is captuwed in t-tweetnotabouttopic
   */
  o-object tweetundonotabouttopic extends basecwientevent(actiontype.cwienttweetundonotabouttopic)

  /**
   * t-this is fiwed w-when a usew fiwst cwicks the "not i-intewested i-in this tweet" button in the cawet menu of a tweet
   * then cwicks  "this t-tweet i-isn't wecent" i-in the subsequent p-pwompt
   * nyote: this button is hidden unwess a-a usew cwicks "not i-intewested in this tweet" fiwst. UwU
   */
  object t-tweetnotwecent extends basecwientevent(actiontype.cwienttweetnotwecent)

  /**
   * this is f-fiwed when a usew cwicks "undo" i-immediatewy aftew c-cwicking "his tweet isn't wecent", 🥺
   * w-which i-is captuwed in tweetnotwecent
   */
  o-object tweetundonotwecent extends basecwientevent(actiontype.cwienttweetundonotwecent)

  /**
   * t-this is f-fiwed when a usew c-cwicks "not i-intewested in this tweet" button i-in the cawet menu o-of a tweet
   * t-then cwicks  "show fewew tweets f-fwom" in the subsequent pwompt
   * nyote: this b-button is hidden u-unwess a usew c-cwicks "not intewested in this tweet" fiwst. 😳😳😳
   */
  object tweetseefewew extends b-basecwientevent(actiontype.cwienttweetseefewew)

  /**
   * this is fiwed when a-a usew cwicks "undo" i-immediatewy aftew cwicking "show fewew tweets f-fwom", ʘwʘ
   * which is captuwed i-in tweetseefewew
   */
  o-object t-tweetundoseefewew e-extends basecwientevent(actiontype.cwienttweetundoseefewew)

  /**
   * t-this is fiwed when a usew cwick "submit" at the end of a "wepowt tweet" f-fwow
   * cwienttweetwepowt = 1041 i-is scwibed by heawthcwient team, /(^•ω•^) on the cwient side
   * t-this is scwibed by spamacaw, :3 on the sewvew side
   * they can be joined on wepowtfwowid
   * see h-https://confwuence.twittew.biz/pages/viewpage.action?spacekey=heawth&titwe=undewstanding+wepowtdetaiws
   */
  o-object tweetwepowtsewvew extends b-basecwientevent(actiontype.sewvewtweetwepowt) {
    ovewwide def getuuaitem(
      c-ceitem: wogeventitem, :3
      w-wogevent: wogevent
    ): option[item] =
      f-fow {
        actiontweetid <- ceitem.id
      } y-yiewd item.tweetinfo(
        cwienteventcommonutiws
          .getbasictweetinfo(
            actiontweetid = actiontweetid,
            c-ceitem = ceitem, mya
            cenamespaceopt = w-wogevent.eventnamespace)
          .copy(tweetactioninfo = s-some(
            t-tweetactioninfo.sewvewtweetwepowt(
              sewvewtweetwepowt(
                wepowtfwowid = w-wogevent.wepowtdetaiws.fwatmap(_.wepowtfwowid), (///ˬ///✿)
                wepowttype = wogevent.wepowtdetaiws.fwatmap(_.wepowttype)
              )
            ))))
  }

  /**
   * this is fiwed when a usew cwicks b-bwock in a p-pwofiwe page
   * a-a pwofiwe can a-awso be bwocked when a usew cwicks bwock in the m-menu of a tweet, (⑅˘꒳˘) w-which
   * is captuwed in cwienttweetbwockauthow
   */
  object p-pwofiwebwock extends basepwofiwecwientevent(actiontype.cwientpwofiwebwock)

  /**
   * this is f-fiwed when a usew cwicks unbwock in a pop-up pwompt w-wight aftew b-bwocking a pwofiwe
   * in the p-pwofiwe page ow c-cwicks unbwock in a-a dwop-down menu in the pwofiwe page. :3
   */
  o-object pwofiweunbwock extends basepwofiwecwientevent(actiontype.cwientpwofiweunbwock)

  /**
   * this is fiwed w-when a usew cwicks mute in a pwofiwe page
   * a pwofiwe can awso b-be muted when a-a usew cwicks mute i-in the menu of a-a tweet, /(^•ω•^) which
   * i-is captuwed in cwienttweetmuteauthow
   */
  o-object pwofiwemute extends basepwofiwecwientevent(actiontype.cwientpwofiwemute)

  /*
   * this i-is fiwed when a usew cwicks "wepowt u-usew" action fwom usew pwofiwe page
   * */
  o-object pwofiwewepowt e-extends basepwofiwecwientevent(actiontype.cwientpwofiwewepowt)

  // this i-is fiwed when a usew pwofiwe i-is open in a pwofiwe p-page
  object pwofiweshow e-extends basepwofiwecwientevent(actiontype.cwientpwofiweshow)

  o-object pwofiwecwick extends basepwofiwecwientevent(actiontype.cwientpwofiwecwick) {

    /**
     * c-cwienttweetcwickpwofiwe wouwd emit 2 events, ^^;; 1 with item type t-tweet and 1 with item type usew
     * b-both events wiww go to both actions (the a-actuaw cwasses). (U ᵕ U❁) f-fow cwienttweetcwickpwofiwe,
     * i-item type of tweet wiww fiwtew o-out the event w-with item type usew. (U ﹏ U) but fow c-cwientpwofiwecwick, mya
     * because w-we nyeed to incwude item type o-of usew, ^•ﻌ•^ then w-we wiww awso incwude the event of tweetcwickpwofiwe
     * if we don't do anything h-hewe. (U ﹏ U) this ovewwide e-ensuwes we don't incwude tweet authow cwicks events in pwofiwecwick
     */
    o-ovewwide def getuuaitem(
      c-ceitem: wogeventitem, :3
      w-wogevent: wogevent
    ): option[item] =
      if (wogevent.eventdetaiws
          .fwatmap(_.items).exists(items => items.exists(_.itemtype.contains(itemtype.tweet)))) {
        nyone
      } e-ewse {
        supew.getuuaitem(ceitem, rawr x3 wogevent)
      }
  }

  /**
   * t-this is fiwed when a-a usew fowwows a p-pwofiwe fwom the
   * pwofiwe page / p-peopwe moduwe a-and peopwe tab o-on the seawch w-wesuwts page / s-sidebaw on the home p-page
   * a pwofiwe can awso be fowwowed when a usew cwicks fowwow in the
   * cawet menu of a-a tweet / fowwow b-button on hovewing o-on pwofiwe a-avataw, 😳😳😳
   * which i-is captuwed in c-cwienttweetfowwowauthow
   */
  object pwofiwefowwow extends basepwofiwecwientevent(actiontype.cwientpwofiwefowwow) {

    /**
     * cwienttweetfowwowauthow wouwd emit 2 events, >w< 1 w-with item t-type tweet and 1 with item type usew
     *  both events wiww go t-to both actions (the a-actuaw cwasses). òωó f-fow cwienttweetfowwowauthow, 😳
     *  item type of tweet w-wiww fiwtew out the event with item type usew. (✿oωo) but f-fow cwientpwofiwefowwow, OwO
     *  b-because we nyeed to incwude item type of usew, (U ﹏ U) t-then we wiww awso incwude the e-event of tweetfowwowauthow
     *  i-if we don't do anything hewe. t-this ovewwide e-ensuwes we don't i-incwude tweet authow f-fowwow events i-in pwofiwefowwow
     */
    o-ovewwide def getuuaitem(
      ceitem: wogeventitem,
      w-wogevent: w-wogevent
    ): option[item] =
      i-if (wogevent.eventdetaiws
          .fwatmap(_.items).exists(items => items.exists(_.itemtype.contains(itemtype.tweet)))) {
        nyone
      } ewse {
        s-supew.getuuaitem(ceitem, (ꈍᴗꈍ) wogevent)
      }
  }

  /**
   * t-this is fiwed when a usew c-cwicks fowwow in t-the cawet menu of a tweet ow hovews on the avataw o-of the tweet authow
   * and cwicks on the fowwow b-button. rawr a p-pwofiwe can awso be fowwowed by cwicking the fowwow b-button on the p-pwofiwe
   * page and confiwm, ^^ w-which is captuwed in cwientpwofiwefowwow. rawr
   * the event emits t-two items, nyaa~~ one of u-usew type and anothew of tweet t-type, nyaa~~ since the d-defauwt impwementation of
   * basecwientevent o-onwy wooks fow tweet t-type, o.O the othew i-item is dwopped w-which is the expected behaviouw
   */
  object tweetfowwowauthow extends basecwientevent(actiontype.cwienttweetfowwowauthow) {
    ovewwide def getuuaitem(
      c-ceitem: wogeventitem, òωó
      w-wogevent: wogevent
    ): o-option[item] = {
      f-fow {
        a-actiontweetid <- c-ceitem.id
      } yiewd {
        i-item.tweetinfo(
          cwienteventcommonutiws
            .getbasictweetinfo(
              a-actiontweetid = actiontweetid, ^^;;
              c-ceitem = ceitem, rawr
              c-cenamespaceopt = wogevent.eventnamespace)
            .copy(tweetactioninfo = some(
              t-tweetactioninfo.cwienttweetfowwowauthow(
                cwienttweetfowwowauthow(
                  cwienteventcommonutiws.gettweetauthowfowwowsouwce(wogevent.eventnamespace))
              ))))
      }
    }
  }

  /**
   * t-this is fiwed when a usew cwicks u-unfowwow in t-the cawet menu of a tweet ow hovews o-on the avataw o-of the tweet authow
   * a-and cwicks on the unfowwow b-button. ^•ﻌ•^ a p-pwofiwe can awso be unfowwowed by c-cwicking the unfowwow button on t-the pwofiwe
   * p-page and confiwm, nyaa~~ w-which wiww be captuwed in cwientpwofiweunfowwow. nyaa~~
   * t-the event emits two items, 😳😳😳 one of usew t-type and anothew of tweet type, 😳😳😳 since the defauwt impwementation of
   * basecwientevent onwy wooks fow tweet t-type, σωσ the othew item is dwopped which is the expected behaviouw
   */
  object tweetunfowwowauthow extends basecwientevent(actiontype.cwienttweetunfowwowauthow) {
    ovewwide d-def getuuaitem(
      ceitem: wogeventitem, o.O
      wogevent: wogevent
    ): o-option[item] = {
      fow {
        a-actiontweetid <- ceitem.id
      } yiewd {
        i-item.tweetinfo(
          cwienteventcommonutiws
            .getbasictweetinfo(
              a-actiontweetid = actiontweetid, σωσ
              c-ceitem = ceitem, nyaa~~
              cenamespaceopt = w-wogevent.eventnamespace)
            .copy(tweetactioninfo = some(
              tweetactioninfo.cwienttweetunfowwowauthow(
                c-cwienttweetunfowwowauthow(
                  cwienteventcommonutiws.gettweetauthowunfowwowsouwce(wogevent.eventnamespace))
              ))))
      }
    }
  }

  /**
   * this is fiwed when a usew c-cwicks bwock in the cawet menu o-of a tweet to bwock the pwofiwe
   * t-that authows this tweet. rawr x3 a p-pwofiwe can awso b-be bwocked in the pwofiwe page, (///ˬ///✿) which is captuwed
   * i-in cwientpwofiwebwock
   */
  object tweetbwockauthow extends basecwientevent(actiontype.cwienttweetbwockauthow)

  /**
   * t-this is fiwed when a usew cwicks unbwock in a pop-up pwompt wight aftew bwocking a-an authow
   * i-in the dwop-down menu of a t-tweet
   */
  object t-tweetunbwockauthow extends b-basecwientevent(actiontype.cwienttweetunbwockauthow)

  /**
   * this is fiwed when a usew cwicks mute in the cawet menu of a tweet t-to mute the p-pwofiwe
   * that authows this t-tweet. o.O a pwofiwe c-can awso be muted in the pwofiwe p-page, òωó which is captuwed
   * in cwientpwofiwemute
   */
  o-object tweetmuteauthow extends basecwientevent(actiontype.cwienttweetmuteauthow)

  /**
   * t-this is f-fiwed when a usew cwicks on a tweet to open the t-tweet detaiws page. OwO note that fow
   * tweets in the nyotification tab pwoduct suwface, σωσ a cwick can be wegistewed diffewentwy
   * d-depending on w-whethew the tweet is a wendewed t-tweet (a cwick w-wesuwts in cwienttweetcwick)
   * ow a wwappew nyotification (a c-cwick wesuwts in cwientnotificationcwick). nyaa~~
   */
  object tweetcwick extends basecwientevent(actiontype.cwienttweetcwick)

  /**
   * this is fiwed when a usew c-cwicks to view the pwofiwe page of anothew usew fwom a tweet
   */
  object tweetcwickpwofiwe e-extends b-basecwientevent(actiontype.cwienttweetcwickpwofiwe)

  /**
   * t-this is fiwed when a usew cwicks on the "shawe" icon on a t-tweet to open the s-shawe menu. OwO
   * t-the usew may ow may not pwoceed a-and finish shawing the tweet. ^^
   */
  o-object tweetcwickshawe e-extends basecwientevent(actiontype.cwienttweetcwickshawe)

  /**
   * this is fiwed w-when a usew cwicks "copy wink to tweet" in a m-menu appeawed aftew hitting
   * t-the "shawe" icon o-on a tweet ow when a usew sewects s-shawe_via -> c-copy_wink aftew wong-cwick
   * a-a wink inside a tweet on a mobiwe d-device
   */
  object tweetshaweviacopywink e-extends basecwientevent(actiontype.cwienttweetshaweviacopywink)

  /**
   * t-this is fiwed when a usew cwicks "send v-via diwect message" aftew
   * cwicking on the "shawe" icon on a tweet to open the shawe menu. (///ˬ///✿)
   * the usew may ow may nyot p-pwoceed and finish sending the dm. σωσ
   */
  object t-tweetcwicksendviadiwectmessage
      extends basecwientevent(actiontype.cwienttweetcwicksendviadiwectmessage)

  /**
   * t-this is fiwed when a usew cwicks "bookmawk" a-aftew
   * cwicking on the "shawe" icon o-on a tweet to open the shawe menu. rawr x3
   */
  object t-tweetshaweviabookmawk extends basecwientevent(actiontype.cwienttweetshaweviabookmawk)

  /**
   * t-this is fiwed when a usew cwicks "wemove tweet f-fwom bookmawks" a-aftew
   * cwicking on the "shawe" icon on a t-tweet to open the s-shawe menu. (ˆ ﻌ ˆ)♡
   */
  object tweetunbookmawk e-extends b-basecwientevent(actiontype.cwienttweetunbookmawk)

  /**
   * this event is fiwed when the u-usew cwicks on a hashtag in a tweet. 🥺
   */
  object tweetcwickhashtag e-extends basecwientevent(actiontype.cwienttweetcwickhashtag) {
    ovewwide def getuuaitem(
      ceitem: wogeventitem, (⑅˘꒳˘)
      w-wogevent: wogevent
    ): o-option[item] = f-fow {
      actiontweetid <- ceitem.id
    } yiewd item.tweetinfo(
      c-cwienteventcommonutiws
        .getbasictweetinfo(
          actiontweetid = a-actiontweetid, 😳😳😳
          ceitem = c-ceitem, /(^•ω•^)
          c-cenamespaceopt = wogevent.eventnamespace)
        .copy(tweetactioninfo = wogevent.eventdetaiws
          .map(
            _.tawgets.fwatmap(_.headoption.fwatmap(_.name))
          ) // fetch the fiwst item in the detaiws and then the n-nyame wiww have t-the hashtag vawue with the '#' sign
          .map { h-hashtagopt =>
            tweetactioninfo.cwienttweetcwickhashtag(
              cwienttweetcwickhashtag(hashtag = h-hashtagopt)
            )
          }))
  }

  /**
   * t-this is fiwed w-when a usew cwicks "bookmawk" a-aftew c-cwicking on t-the "shawe" icon on a tweet to
   * open the shawe m-menu, >w< ow when a-a usew cwicks on t-the 'bookmawk' i-icon on a tweet (bookmawk i-icon
   * i-is avaiwabwe to ios onwy as o-of mawch 2023). ^•ﻌ•^
   * t-tweetbookmawk a-and tweetshawebybookmawk wog the same events b-but sewve fow individuaw use cases. 😳😳😳
   */
  object t-tweetbookmawk extends basecwientevent(actiontype.cwienttweetbookmawk)

  /**
   * this is fiwed w-when a usew c-cwicks on a wink in a tweet. :3
   * the wink couwd be dispwayed as a-a uww ow embedded
   * i-in a component such as an i-image ow a cawd i-in a tweet. (ꈍᴗꈍ)
   */
  object tweetopenwink extends basecwientevent(actiontype.cwienttweetopenwink) {
    o-ovewwide d-def getuuaitem(
      ceitem: wogeventitem, ^•ﻌ•^
      w-wogevent: wogevent
    ): o-option[item] =
      fow {
        actiontweetid <- c-ceitem.id
      } yiewd item.tweetinfo(
        cwienteventcommonutiws
          .getbasictweetinfo(
            actiontweetid = actiontweetid, >w<
            ceitem = c-ceitem, ^^;;
            cenamespaceopt = wogevent.eventnamespace)
          .copy(tweetactioninfo = s-some(
            t-tweetactioninfo.cwienttweetopenwink(
              c-cwienttweetopenwink(uww = wogevent.eventdetaiws.fwatmap(_.uww))
            ))))
  }

  /**
   * t-this i-is fiwed when a-a usew takes a scweenshot. (✿oωo)
   * t-this is avaiwabwe f-fow onwy mobiwe cwients. òωó
   */
  object tweettakescweenshot e-extends b-basecwientevent(actiontype.cwienttweettakescweenshot) {
    o-ovewwide def getuuaitem(
      ceitem: wogeventitem, ^^
      w-wogevent: w-wogevent
    ): o-option[item] =
      fow {
        a-actiontweetid <- c-ceitem.id
      } y-yiewd i-item.tweetinfo(
        c-cwienteventcommonutiws
          .getbasictweetinfo(
            actiontweetid = a-actiontweetid, ^^
            ceitem = c-ceitem, rawr
            c-cenamespaceopt = wogevent.eventnamespace)
          .copy(tweetactioninfo = some(
            tweetactioninfo.cwienttweettakescweenshot(
              c-cwienttweettakescweenshot(pewcentvisibweheight100k = c-ceitem.pewcentvisibweheight100k)
            ))))
  }

  /**
   * this is fiwed w-when a usew cwicks t-the "this tweet isn't wewevant" button in a pwompt d-dispwayed
   * a-aftew cwicking "this t-tweet's n-nyot hewpfuw" i-in seawch wesuwt p-page ow "not intewested in this tweet"
   * in t-the home timewine page. XD
   * nyote: this button is hidden unwess a usew cwicks "this t-tweet isn't w-wewevant" ow
   * "this tweet's nyot hewpfuw" fiwst
   */
  object t-tweetnotwewevant e-extends basecwientevent(actiontype.cwienttweetnotwewevant)

  /**
   * this is fiwed when a u-usew cwicks "undo" immediatewy a-aftew cwicking "this t-tweet isn't w-wewevant",
   * which is captuwed in tweetnotwewevant
   */
  object tweetundonotwewevant e-extends basecwientevent(actiontype.cwienttweetundonotwewevant)

  /**
   * t-this is fiwed when a usew i-is wogged out and fowwows a pwofiwe fwom the
   * p-pwofiwe page / peopwe moduwe fwom w-web. rawr
   * one can onwy twy to fowwow fwom web, 😳 i-ios and andwoid do nyot suppowt w-wogged out bwowsing
   */
  object pwofiwefowwowattempt extends basepwofiwecwientevent(actiontype.cwientpwofiwefowwowattempt)

  /**
   * this is fiwed when a usew is wogged o-out and favouwite a-a tweet fwom w-web. 🥺
   * one can o-onwy twy to favouwite fwom web, (U ᵕ U❁) ios and andwoid d-do nyot suppowt wogged out bwowsing
   */
  object tweetfavowiteattempt e-extends b-basecwientevent(actiontype.cwienttweetfavowiteattempt)

  /**
   * t-this is fiwed w-when a usew is wogged out and wetweet a tweet fwom web. 😳
   * one can onwy twy t-to favouwite fwom w-web, ios and andwoid do nyot suppowt wogged out bwowsing
   */
  o-object tweetwetweetattempt extends basecwientevent(actiontype.cwienttweetwetweetattempt)

  /**
   * t-this is f-fiwed when a usew i-is wogged out and wepwy on tweet fwom web. 🥺
   * one can onwy twy to favouwite fwom web, (///ˬ///✿) ios and a-andwoid do nyot suppowt wogged o-out bwowsing
   */
  object tweetwepwyattempt extends basecwientevent(actiontype.cwienttweetwepwyattempt)

  /**
   * this is f-fiwed when a usew is wogged out a-and cwicks on wogin button. mya
   * cuwwentwy seem t-to be genewated o-onwy on [m5, (✿oωo) witenativewwappew] a-as of jan 2023. ^•ﻌ•^
   */
  o-object ctawogincwick e-extends basectacwientevent(actiontype.cwientctawogincwick)

  /**
   * t-this is fiwed w-when a usew is wogged out and w-wogin window is shown. o.O
   */
  object ctawoginstawt e-extends basectacwientevent(actiontype.cwientctawoginstawt)

  /**
   * this i-is fiwed when a u-usew is wogged out and wogin is s-successfuw. o.O
   */
  o-object ctawoginsuccess extends basectacwientevent(actiontype.cwientctawoginsuccess)

  /**
   * this is fiwed w-when a usew is w-wogged out and c-cwicks on signup b-button. XD
   */
  object ctasignupcwick extends basectacwientevent(actiontype.cwientctasignupcwick)

  /**
   * this is fiwed when a-a usew is wogged out and signup is successfuw. ^•ﻌ•^
   */
  o-object ctasignupsuccess extends basectacwientevent(actiontype.cwientctasignupsuccess)

  /**
   * t-this is fiwed when a usew opens a push nyotification. ʘwʘ
   * w-wefew to https://confwuence.twittew.biz/pages/viewpage.action?pageid=161811800
   * fow push n-nyotification s-scwibe detaiws
   */
  o-object nyotificationopen extends basepushnotificationcwientevent(actiontype.cwientnotificationopen)

  /**
   * t-this is f-fiwed when a usew cwicks on a nyotification i-in the n-nyotification t-tab. (U ﹏ U)
   * wefew t-to go/ntab-uwt-scwibe fow nyotification t-tab scwibe d-detaiws. 😳😳😳
   */
  o-object nyotificationcwick
      extends basenotificationtabcwientevent(actiontype.cwientnotificationcwick)

  /**
   * t-this is fiwed when a usew taps the "see wess often" cawet menu item of a nyotification i-in
   * the nyotification t-tab. 🥺
   * wefew to g-go/ntab-uwt-scwibe fow nyotification tab scwibe d-detaiws. (///ˬ///✿)
   */
  o-object nyotificationseewessoften
      e-extends b-basenotificationtabcwientevent(actiontype.cwientnotificationseewessoften)

  /**
   * this is fiwed w-when a usew cwoses ow swipes away a push nyotification. (˘ω˘)
   * w-wefew to https://confwuence.twittew.biz/pages/viewpage.action?pageid=161811800
   * f-fow push nyotification scwibe detaiws
   */
  object nyotificationdismiss
      e-extends basepushnotificationcwientevent(actiontype.cwientnotificationdismiss)

  /**
   *  this is fiwed when a-a usew cwicks on a typeahead suggestion(quewies, :3 e-events, /(^•ω•^) topics, usews)
   *  i-in a dwop-down menu of a seawch box ow a tweet c-compose box. :3
   */
  object typeaheadcwick e-extends baseseawchtypeaheadevent(actiontype.cwienttypeaheadcwick)

  /**
   * t-this is a-a genewic event fiwed when the usew submits feedback o-on a pwompt. mya
   * some exampwes incwude did y-you find it pwompt a-and tweet wewevance o-on seawch wesuwts page. XD
   */
  object feedbackpwomptsubmit
      extends basefeedbacksubmitcwientevent(actiontype.cwientfeedbackpwomptsubmit)

  o-object appexit extends baseuascwientevent(actiontype.cwientappexit)
}

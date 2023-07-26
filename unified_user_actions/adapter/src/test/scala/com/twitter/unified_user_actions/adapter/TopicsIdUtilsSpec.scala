package com.twittew.unified_usew_actions.adaptew

impowt com.twittew.cwientapp.thwiftscawa._
i-impowt c-com.twittew.cwientapp.thwiftscawa.suggestiondetaiws
i-impowt com.twittew.guide.scwibing.thwiftscawa._
i-impowt com.twittew.guide.scwibing.thwiftscawa.{semanticcoweintewest => s-semanticcoweintewestv1}
i-impowt com.twittew.guide.scwibing.thwiftscawa.{simcwustewintewest => s-simcwustewintewestv1}
i-impowt com.twittew.guide.scwibing.thwiftscawa.topicmoduwemetadata.semanticcoweintewest
impowt com.twittew.guide.scwibing.thwiftscawa.topicmoduwemetadata.simcwustewintewest
impowt com.twittew.guide.scwibing.thwiftscawa.twanspawentguidedetaiws.topicmetadata
impowt com.twittew.wogbase.thwiftscawa.wogbase
i-impowt com.twittew.scwooge.tfiewdbwob
impowt com.twittew.suggests.contwowwew_data.home_hitw_topic_annotation_pwompt.thwiftscawa.homehitwtopicannotationpwomptcontwowwewdata
impowt c-com.twittew.suggests.contwowwew_data.home_hitw_topic_annotation_pwompt.v1.thwiftscawa.{
  homehitwtopicannotationpwomptcontwowwewdata => h-homehitwtopicannotationpwomptcontwowwewdatav1
}
impowt com.twittew.suggests.contwowwew_data.home_topic_annotation_pwompt.thwiftscawa.hometopicannotationpwomptcontwowwewdata
impowt c-com.twittew.suggests.contwowwew_data.home_topic_annotation_pwompt.v1.thwiftscawa.{
  hometopicannotationpwomptcontwowwewdata => h-hometopicannotationpwomptcontwowwewdatav1
}
i-impowt com.twittew.suggests.contwowwew_data.home_topic_fowwow_pwompt.thwiftscawa.hometopicfowwowpwomptcontwowwewdata
impowt com.twittew.suggests.contwowwew_data.home_topic_fowwow_pwompt.v1.thwiftscawa.{
  hometopicfowwowpwomptcontwowwewdata => hometopicfowwowpwomptcontwowwewdatav1
}
i-impowt com.twittew.suggests.contwowwew_data.home_tweets.thwiftscawa.hometweetscontwowwewdata
impowt com.twittew.suggests.contwowwew_data.home_tweets.v1.thwiftscawa.{
  hometweetscontwowwewdata => hometweetscontwowwewdatav1
}
impowt c-com.twittew.suggests.contwowwew_data.seawch_wesponse.item_types.thwiftscawa.itemtypescontwowwewdata
impowt com.twittew.suggests.contwowwew_data.seawch_wesponse.thwiftscawa.seawchwesponsecontwowwewdata
i-impowt c-com.twittew.suggests.contwowwew_data.seawch_wesponse.topic_fowwow_pwompt.thwiftscawa.seawchtopicfowwowpwomptcontwowwewdata
i-impowt c-com.twittew.suggests.contwowwew_data.seawch_wesponse.tweet_types.thwiftscawa.tweettypescontwowwewdata
impowt com.twittew.suggests.contwowwew_data.seawch_wesponse.v1.thwiftscawa.{
  seawchwesponsecontwowwewdata => s-seawchwesponsecontwowwewdatav1
}
impowt com.twittew.suggests.contwowwew_data.thwiftscawa.contwowwewdata
impowt com.twittew.suggests.contwowwew_data.timewines_topic.thwiftscawa.timewinestopiccontwowwewdata
i-impowt com.twittew.suggests.contwowwew_data.timewines_topic.v1.thwiftscawa.{
  timewinestopiccontwowwewdata => timewinestopiccontwowwewdatav1
}
impowt com.twittew.suggests.contwowwew_data.v2.thwiftscawa.{contwowwewdata => contwowwewdatav2}
impowt owg.apache.thwift.pwotocow.tfiewd
i-impowt owg.junit.wunnew.wunwith
i-impowt o-owg.scawatest.funsuite.anyfunsuite
i-impowt owg.scawatest.matchews.shouwd.matchews
impowt owg.scawatestpwus.junit.junitwunnew
impowt com.twittew.utiw.mock.mockito
impowt owg.mockito.mockito.when
i-impowt owg.scawatest.pwop.tabwedwivenpwopewtychecks

@wunwith(cwassof[junitwunnew])
c-cwass topicsidutiwsspec
    extends anyfunsuite
    w-with m-matchews
    with mockito
    w-with tabwedwivenpwopewtychecks {
  impowt com.twittew.unified_usew_actions.adaptew.cwient_event.topicidutiws._

  t-twait fixtuwe {
    def buiwdwogbase(usewid: wong): wogbase = {
      v-vaw wogbase = mock[wogbase]
      w-when(wogbase.countwy).thenwetuwn(some("us"))
      when(wogbase.usewid).thenwetuwn(some(usewid))
      w-when(wogbase.timestamp).thenwetuwn(100w)
      w-when(wogbase.guestid).thenwetuwn(some(1w))
      when(wogbase.usewagent).thenwetuwn(none)
      when(wogbase.wanguage).thenwetuwn(some("en"))
      wogbase
    }

    def buiwditemfowtimewine(
      itemid: wong, (///ˬ///✿)
      itemtype: i-itemtype, XD
      t-topicid: wong,
      fn: wong => c-contwowwewdata.v2
    ): item = {
      v-vaw i-item = item(
        id = some(itemid), :3
        itemtype = some(itemtype), òωó
        suggestiondetaiws = s-some(suggestiondetaiws(decodedcontwowwewdata = some(fn(topicid))))
      )
      item
    }

    def buiwdcwienteventfowhomeseawchtimewine(
      itemid: w-wong, ^^
      itemtype: itemtype, ^•ﻌ•^
      t-topicid: w-wong, σωσ
      fn: w-wong => contwowwewdata.v2, (ˆ ﻌ ˆ)♡
      usewid: wong = 1w, nyaa~~
      e-eventnamespaceopt: option[eventnamespace] = n-nyone, ʘwʘ
    ): w-wogevent = {
      v-vaw wogevent = mock[wogevent]
      when(wogevent.eventnamespace).thenwetuwn(eventnamespaceopt)
      vaw e-eventsdetaiws = m-mock[eventdetaiws]
      w-when(eventsdetaiws.items)
        .thenwetuwn(some(seq(buiwditemfowtimewine(itemid, ^•ﻌ•^ i-itemtype, rawr x3 topicid, f-fn))))
      vaw wogbase = buiwdwogbase(usewid)
      when(wogevent.wogbase).thenwetuwn(some(wogbase))
      when(wogevent.eventdetaiws).thenwetuwn(some(eventsdetaiws))
      w-wogevent
    }

    def buiwdcwienteventfowhometweetstimewine(
      itemid: wong, 🥺
      itemtype: itemtype, ʘwʘ
      topicid: wong, (˘ω˘)
      t-topicids: set[wong], o.O
      fn: (wong, σωσ set[wong]) => contwowwewdata.v2, (ꈍᴗꈍ)
      u-usewid: wong = 1w, (ˆ ﻌ ˆ)♡
      e-eventnamespaceopt: o-option[eventnamespace] = nyone, o.O
    ): w-wogevent = {
      vaw w-wogevent = mock[wogevent]
      w-when(wogevent.eventnamespace).thenwetuwn(eventnamespaceopt)
      vaw eventsdetaiws = mock[eventdetaiws]
      when(eventsdetaiws.items)
        .thenwetuwn(some(seq(buiwditemfowhometimewine(itemid, :3 itemtype, topicid, -.- topicids, ( ͡o ω ͡o ) f-fn))))
      vaw wogbase = b-buiwdwogbase(usewid)
      when(wogevent.wogbase).thenwetuwn(some(wogbase))
      w-when(wogevent.eventdetaiws).thenwetuwn(some(eventsdetaiws))
      w-wogevent
    }

    def buiwdcwienteventfowguide(
      itemid: w-wong, /(^•ω•^)
      i-itemtype: itemtype, (⑅˘꒳˘)
      topicid: w-wong, òωó
      fn: w-wong => topicmetadata, 🥺
      usewid: wong = 1w, (ˆ ﻌ ˆ)♡
      eventnamespaceopt: option[eventnamespace] = nyone, -.-
    ): w-wogevent = {
      v-vaw wogevent = m-mock[wogevent]
      when(wogevent.eventnamespace).thenwetuwn(eventnamespaceopt)
      v-vaw w-wogbase = buiwdwogbase(usewid)
      when(wogevent.wogbase).thenwetuwn(some(wogbase))
      v-vaw eventdetaiws = mock[eventdetaiws]
      vaw item = buiwditemfowguide(itemid, σωσ itemtype, >_< t-topicid, f-fn)
      when(eventdetaiws.items).thenwetuwn(some(seq(item)))
      when(wogevent.eventdetaiws).thenwetuwn(some(eventdetaiws))
      wogevent
    }

    d-def buiwdcwienteventfowonboawding(
      i-itemid: wong, :3
      topicid: wong, OwO
      usewid: wong = 1w
    ): w-wogevent = {
      vaw wogevent = mock[wogevent]
      vaw wogbase = buiwdwogbase(usewid)
      w-when(wogevent.wogbase).thenwetuwn(some(wogbase))
      when(wogevent.eventnamespace).thenwetuwn(some(buiwdnamespacefowonboawding))
      vaw e-eventdetaiws = m-mock[eventdetaiws]
      vaw item = buiwditemfowonboawding(itemid, rawr topicid)
      w-when(eventdetaiws.items)
        .thenwetuwn(some(seq(item)))
      w-when(wogevent.eventdetaiws).thenwetuwn(some(eventdetaiws))
      wogevent
    }

    def buiwdcwienteventfowonboawdingbackend(
      t-topicid: wong, (///ˬ///✿)
      u-usewid: wong = 1w
    ): wogevent = {
      vaw wogevent = mock[wogevent]
      v-vaw wogbase = buiwdwogbase(usewid)
      when(wogevent.wogbase).thenwetuwn(some(wogbase))
      w-when(wogevent.eventnamespace).thenwetuwn(some(buiwdnamespacefowonboawdingbackend))
      v-vaw eventdetaiws = buiwdeventdetaiwsfowonboawdingbackend(topicid)
      w-when(wogevent.eventdetaiws).thenwetuwn(some(eventdetaiws))
      wogevent
    }

    d-def defauwtnamespace: e-eventnamespace = {
      e-eventnamespace(some("iphone"), ^^ nyone, nyone, XD n-nyone, nyone, UwU s-some("favowite"))
    }

    def buiwdnamespacefowonboawdingbackend: e-eventnamespace = {
      eventnamespace(
        s-some("iphone"), o.O
        some("onboawding_backend"), 😳
        s-some("subtasks"), (˘ω˘)
        some("topics_sewectow"),
        some("wemoved"), 🥺
        s-some("sewected"))
    }

    def buiwdnamespacefowonboawding: e-eventnamespace = {
      e-eventnamespace(
        some("iphone"), ^^
        some("onboawding"), >w<
        some("topics_sewectow"), ^^;;
        n-nyone, (˘ω˘)
        s-some("topic"), OwO
        s-some("fowwow")
      )
    }

    d-def buiwditemfowhometimewine(
      itemid: wong, (ꈍᴗꈍ)
      i-itemtype: itemtype, òωó
      topicid: wong, ʘwʘ
      topicids: set[wong], ʘwʘ
      fn: (wong, nyaa~~ s-set[wong]) => contwowwewdata.v2
    ): item = {
      v-vaw item = item(
        id = s-some(itemid), UwU
        itemtype = s-some(itemtype), (⑅˘꒳˘)
        suggestiondetaiws =
          s-some(suggestiondetaiws(decodedcontwowwewdata = s-some(fn(topicid, (˘ω˘) t-topicids))))
      )
      i-item
    }

    d-def buiwditemfowguide(
      itemid: wong, :3
      itemtype: itemtype, (˘ω˘)
      topicid: wong, nyaa~~
      fn: wong => topicmetadata
    ): i-item = {
      v-vaw item = m-mock[item]
      when(item.id).thenwetuwn(some(itemid))
      when(item.itemtype).thenwetuwn(some(itemtype))
      w-when(item.suggestiondetaiws)
        .thenwetuwn(some(suggestiondetaiws(suggestiontype = some("ewgtweet"))))
      vaw guideitemdetaiws = mock[guideitemdetaiws]
      w-when(guideitemdetaiws.twanspawentguidedetaiws).thenwetuwn(some(fn(topicid)))
      w-when(item.guideitemdetaiws).thenwetuwn(some(guideitemdetaiws))
      item
    }

    d-def buiwditemfowonboawding(
      itemid: wong, (U ﹏ U)
      topicid: w-wong
    ): item = {
      v-vaw item = item(
        i-id = some(itemid), nyaa~~
        i-itemtype = nyone, ^^;;
        descwiption = some(s"id=$topicid,wow=1")
      )
      item
    }

    def buiwdeventdetaiwsfowonboawdingbackend(
      t-topicid: wong
    ): e-eventdetaiws = {
      vaw e-eventdetaiws = m-mock[eventdetaiws]
      v-vaw item = item(
        i-id = some(topicid)
      )
      v-vaw itemtmp = buiwditemfowonboawding(10, OwO t-topicid)
      w-when(eventdetaiws.items).thenwetuwn(some(seq(itemtmp)))
      when(eventdetaiws.tawgets).thenwetuwn(some(seq(item)))
      e-eventdetaiws
    }

    def topicmetadatainguide(topicid: wong): topicmetadata =
      topicmetadata(
        s-semanticcoweintewest(
          semanticcoweintewestv1(domainid = "131", nyaa~~ entityid = t-topicid.tostwing)
        )
      )

    d-def simcwustewmetadatainguide(simcwustewid: wong = 1w): topicmetadata =
      t-topicmetadata(
        simcwustewintewest(
          simcwustewintewestv1(simcwustewid.tostwing)
        )
      )

    d-def timewinetopiccontwowwewdata(topicid: w-wong): contwowwewdata.v2 =
      c-contwowwewdata.v2(
        contwowwewdatav2.timewinestopic(
          timewinestopiccontwowwewdata.v1(
            timewinestopiccontwowwewdatav1(
              t-topicid = topicid, UwU
              topictypesbitmap = 1
            )
          )))

    def hometweetcontwowwewdata(topicid: w-wong): contwowwewdata.v2 =
      c-contwowwewdata.v2(
        contwowwewdatav2.hometweets(
          h-hometweetscontwowwewdata.v1(
            hometweetscontwowwewdatav1(
              t-topicid = s-some(topicid)
            ))))

    def hometopicfowwowpwomptcontwowwewdata(topicid: wong): contwowwewdata.v2 =
      c-contwowwewdata.v2(
        contwowwewdatav2.hometopicfowwowpwompt(hometopicfowwowpwomptcontwowwewdata.v1(
          hometopicfowwowpwomptcontwowwewdatav1(some(topicid)))))

    d-def hometopicannotationpwomptcontwowwewdata(topicid: w-wong): contwowwewdata.v2 =
      c-contwowwewdata.v2(
        contwowwewdatav2.hometopicannotationpwompt(hometopicannotationpwomptcontwowwewdata.v1(
          h-hometopicannotationpwomptcontwowwewdatav1(tweetid = 1w, 😳 t-topicid = topicid))))

    d-def homehitwtopicannotationpwomptcontwowwewdata(topicid: wong): contwowwewdata.v2 =
      contwowwewdata.v2(
        contwowwewdatav2.homehitwtopicannotationpwompt(
          homehitwtopicannotationpwomptcontwowwewdata.v1(
            homehitwtopicannotationpwomptcontwowwewdatav1(tweetid = 2w, 😳 topicid = topicid))))

    def seawchtopicfowwowpwomptcontwowwewdata(topicid: wong): contwowwewdata.v2 =
      contwowwewdata.v2(
        contwowwewdatav2.seawchwesponse(
          s-seawchwesponsecontwowwewdata.v1(
            s-seawchwesponsecontwowwewdatav1(
              some(itemtypescontwowwewdata.topicfowwowcontwowwewdata(
                seawchtopicfowwowpwomptcontwowwewdata(some(topicid))
              )), (ˆ ﻌ ˆ)♡
              n-nyone
            ))))

    d-def s-seawchtweettypescontwowwewdata(topicid: wong): contwowwewdata.v2 =
      c-contwowwewdata.v2(
        contwowwewdatav2.seawchwesponse(
          seawchwesponsecontwowwewdata.v1(
            s-seawchwesponsecontwowwewdatav1(
              s-some(itemtypescontwowwewdata.tweettypescontwowwewdata(
                tweettypescontwowwewdata(none, (✿oωo) s-some(topicid))
              )), nyaa~~
              nyone
            )
          )))

    //used fow c-cweating wogged o-out usew cwient events
    def buiwdwogbasewithoutusewid(guestid: w-wong): wogbase =
      w-wogbase(
        i-ipaddwess = "120.10.10.20", ^^
        guestid = s-some(guestid), (///ˬ///✿)
        u-usewagent = nyone, 😳
        t-twansactionid = "", òωó
        c-countwy = s-some("us"), ^^;;
        t-timestamp = 100w, rawr
        wanguage = some("en")
      )
  }

  t-test("gettopicid s-shouwd cowwectwy f-find topic id fwom item fow h-home timewine and seawch") {
    nyew fixtuwe {

      v-vaw testdata = tabwe(
        ("itemtype", (ˆ ﻌ ˆ)♡ "topicid", "contwowwewdata"), XD
        (itemtype.tweet, >_< 1w, timewinetopiccontwowwewdata(1w)), (˘ω˘)
        (itemtype.usew, 😳 2w, t-timewinetopiccontwowwewdata(2w)), o.O
        (itemtype.topic, (ꈍᴗꈍ) 3w, h-hometweetcontwowwewdata(3w)), rawr x3
        (itemtype.topic, ^^ 4w, h-hometopicfowwowpwomptcontwowwewdata(4w)), OwO
        (itemtype.topic, 5w, ^^ seawchtopicfowwowpwomptcontwowwewdata(5w)),
        (itemtype.topic, :3 6w, h-homehitwtopicannotationpwomptcontwowwewdata(6w))
      )

      fowevewy(testdata) {
        (itemtype: itemtype, o.O t-topicid: wong, -.- contwowwewdatav2: c-contwowwewdata.v2) =>
          gettopicid(
            b-buiwditemfowtimewine(1, (U ﹏ U) itemtype, o.O topicid, _ => contwowwewdatav2), OwO
            defauwtnamespace) s-shouwdequaw some(topicid)
      }
    }
  }

  test("gettopicid s-shouwd cowwectwy f-find topic id fwom item fow guide events") {
    nyew fixtuwe {
      g-gettopicid(
        buiwditemfowguide(1, ^•ﻌ•^ i-itemtype.tweet, ʘwʘ 100, t-topicmetadatainguide), :3
        d-defauwtnamespace
      ) shouwdequaw some(100)
    }
  }

  test("gettopicid s-shouwd cowwectwy f-find topic id fow onboawding e-events") {
    nyew fixtuwe {
      gettopicid(
        b-buiwditemfowonboawding(1, 😳 100),
        buiwdnamespacefowonboawding
      ) s-shouwdequaw s-some(100)
    }
  }

  t-test("shouwd wetuwn topicid f-fwom homeseawch") {
    v-vaw t-testdata = tabwe(
      ("contwowwewdata", òωó "topicid"), 🥺
      (
        c-contwowwewdata.v2(
          contwowwewdatav2.hometweets(
            h-hometweetscontwowwewdata.v1(hometweetscontwowwewdatav1(topicid = some(1w))))
        ), rawr x3
        s-some(1w)), ^•ﻌ•^
      (
        c-contwowwewdata.v2(
          c-contwowwewdatav2.hometopicfowwowpwompt(hometopicfowwowpwomptcontwowwewdata
            .v1(hometopicfowwowpwomptcontwowwewdatav1(topicid = s-some(2w))))), :3
        s-some(2w)),
      (
        c-contwowwewdata.v2(
          contwowwewdatav2.timewinestopic(
            t-timewinestopiccontwowwewdata.v1(
              timewinestopiccontwowwewdatav1(topicid = 3w, (ˆ ﻌ ˆ)♡ t-topictypesbitmap = 100)
            ))), (U ᵕ U❁)
        some(3w)), :3
      (
        c-contwowwewdata.v2(
          contwowwewdatav2.seawchwesponse(
            s-seawchwesponsecontwowwewdata.v1(seawchwesponsecontwowwewdatav1(itemtypescontwowwewdata =
              s-some(itemtypescontwowwewdata.topicfowwowcontwowwewdata(
                s-seawchtopicfowwowpwomptcontwowwewdata(topicid = some(4w)))))))), ^^;;
        some(4w)), ( ͡o ω ͡o )
      (
        contwowwewdata.v2(
          c-contwowwewdatav2.seawchwesponse(
            s-seawchwesponsecontwowwewdata.v1(
              s-seawchwesponsecontwowwewdatav1(itemtypescontwowwewdata = some(itemtypescontwowwewdata
                .tweettypescontwowwewdata(tweettypescontwowwewdata(topicid = some(5w)))))))),
        some(5w)), o.O
      (
        c-contwowwewdata.v2(
          c-contwowwewdatav2
            .seawchwesponse(seawchwesponsecontwowwewdata.v1(seawchwesponsecontwowwewdatav1()))), ^•ﻌ•^
        nyone)
    )

    f-fowevewy(testdata) { (contwowwewdatav2: c-contwowwewdata.v2, XD topicid: option[wong]) =>
      gettopicidfwomhomeseawch(
        item(suggestiondetaiws = s-some(
          s-suggestiondetaiws(decodedcontwowwewdata = s-some(contwowwewdatav2))))) s-shouwdequaw topicid
    }
  }

  test("test t-topicid fwom onboawding") {
    v-vaw testdata = tabwe(
      ("item", ^^ "eventnamespace", o.O "topicid"), ( ͡o ω ͡o )
      (
        item(descwiption = s-some("id=11,key=vawue")), /(^•ω•^)
        eventnamespace(
          page = some("onboawding"), 🥺
          s-section = some("section h-has topic"), nyaa~~
          c-component = some("component h-has topic"),
          e-ewement = some("ewement h-has topic")
        ), mya
        some(11w)), XD
      (
        i-item(descwiption = s-some("id=22,key=vawue")), nyaa~~
        e-eventnamespace(
          p-page = some("onboawding"), ʘwʘ
          s-section = some("section h-has topic")
        ),
        s-some(22w)), (⑅˘꒳˘)
      (
        item(descwiption = s-some("id=33,key=vawue")), :3
        eventnamespace(
          page = some("onboawding"), -.-
          c-component = s-some("component h-has topic")
        ), 😳😳😳
        some(33w)), (U ﹏ U)
      (
        item(descwiption = some("id=44,key=vawue")), o.O
        eventnamespace(
          page = s-some("onboawding"), ( ͡o ω ͡o )
          ewement = some("ewement h-has topic")
        ),
        s-some(44w)), òωó
      (
        item(descwiption = some("id=678,key=vawue")), 🥺
        e-eventnamespace(
          page = some("onxyzboawding"), /(^•ω•^)
          s-section = s-some("section h-has topic"), 😳😳😳
          c-component = s-some("component has topic"), ^•ﻌ•^
          ewement = some("ewement has topic")
        ), nyaa~~
        nyone), OwO
      (
        item(descwiption = s-some("id=678,key=vawue")), ^•ﻌ•^
        eventnamespace(
          page = s-some("page has onboawding"), σωσ
          section = some("section h-has toppic"), -.-
          component = some("component has toppic"), (˘ω˘)
          ewement = some("ewement h-has toppic")
        ), rawr x3
        n-nyone), rawr x3
      (
        item(descwiption = s-some("key=vawue,id=678")), σωσ
        eventnamespace(
          page = some("page h-has onboawding"), nyaa~~
          section = s-some("section has topic"),
          c-component = some("component h-has topic"), (ꈍᴗꈍ)
          ewement = some("ewement has topic")
        ),
        nyone)
    )

    f-fowevewy(testdata) { (item: item, ^•ﻌ•^ eventnamespace: eventnamespace, >_< t-topicid: o-option[wong]) =>
      g-gettopicfwomonboawding(item, ^^;; eventnamespace) shouwdequaw t-topicid
    }
  }

  test("test fwom guide") {
    vaw testdata = tabwe(
      ("guideitemdetaiws", ^^;; "topicid"),
      (
        g-guideitemdetaiws(twanspawentguidedetaiws = s-some(
          t-twanspawentguidedetaiws.topicmetadata(
            t-topicmoduwemetadata.tttintewest(tttintewest = tttintewest.unsafeempty)))),
        nyone),
      (
        g-guideitemdetaiws(twanspawentguidedetaiws = s-some(
          twanspawentguidedetaiws.topicmetadata(
            topicmoduwemetadata.simcwustewintewest(simcwustewintewest =
              c-com.twittew.guide.scwibing.thwiftscawa.simcwustewintewest.unsafeempty)))), /(^•ω•^)
        nyone), nyaa~~
      (
        guideitemdetaiws(twanspawentguidedetaiws = s-some(
          twanspawentguidedetaiws.topicmetadata(topicmoduwemetadata.unknownunionfiewd(fiewd =
            tfiewdbwob(new t-tfiewd(), (✿oωo) a-awway.empty[byte]))))), ( ͡o ω ͡o )
        nyone), (U ᵕ U❁)
      (
        g-guideitemdetaiws(twanspawentguidedetaiws = s-some(
          t-twanspawentguidedetaiws.topicmetadata(
            topicmoduwemetadata.semanticcoweintewest(
              com.twittew.guide.scwibing.thwiftscawa.semanticcoweintewest.unsafeempty
                .copy(domainid = "131", òωó e-entityid = "1"))))), σωσ
        some(1w)), :3
    )

    fowevewy(testdata) { (guideitemdetaiws: g-guideitemdetaiws, OwO topicid: option[wong]) =>
      gettopicfwomguide(item(guideitemdetaiws = some(guideitemdetaiws))) s-shouwdequaw topicid
    }
  }

  t-test("gettopicid s-shouwd wetuwn t-topicids") {
    g-gettopicid(
      item = item(suggestiondetaiws = s-some(
        suggestiondetaiws(decodedcontwowwewdata = some(
          c-contwowwewdata.v2(
            contwowwewdatav2.hometweets(
              h-hometweetscontwowwewdata.v1(hometweetscontwowwewdatav1(topicid = some(1w))))
          ))))), ^^
      nyamespace = e-eventnamespace(
        p-page = some("onboawding"), (˘ω˘)
        section = some("section h-has topic"), OwO
        c-component = some("component h-has topic"), UwU
        e-ewement = some("ewement h-has topic")
      )
    ) shouwdequaw s-some(1w)
  }
}

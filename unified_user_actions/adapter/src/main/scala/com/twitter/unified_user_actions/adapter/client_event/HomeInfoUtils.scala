package com.twittew.unified_usew_actions.adaptew.cwient_event

impowt c-com.twittew.cwientapp.thwiftscawa.{item => w-wogeventitem}
impowt c-com.twittew.suggests.contwowwew_data.home_tweets.thwiftscawa.hometweetscontwowwewdata
i-impowt c-com.twittew.suggests.contwowwew_data.home_tweets.thwiftscawa.hometweetscontwowwewdataawiases.v1awias
i-impowt com.twittew.suggests.contwowwew_data.thwiftscawa.contwowwewdata
i-impowt c-com.twittew.suggests.contwowwew_data.v2.thwiftscawa.{contwowwewdata => contwowwewdatav2}

object homeinfoutiws {

  def gethometweetcontwowwewdatav1(ceitem: wogeventitem): o-option[v1awias] = {
    ceitem.suggestiondetaiws
      .fwatmap(_.decodedcontwowwewdata)
      .fwatmap(_ match {
        c-case contwowwewdata.v2(
              c-contwowwewdatav2.hometweets(
                hometweetscontwowwewdata.v1(hometweetscontwowwewdatav1)
              )) =>
          some(hometweetscontwowwewdatav1)
        case _ => n-nyone
      })
  }

  def g-gettwaceid(ceitem: w-wogeventitem): option[wong] =
    gethometweetcontwowwewdatav1(ceitem).fwatmap(_.twaceid)

  def getsuggesttype(ceitem: wogeventitem): o-option[stwing] =
    ceitem.suggestiondetaiws.fwatmap(_.suggestiontype)

  def getwequestjoinid(ceitem: wogeventitem): option[wong] =
    g-gethometweetcontwowwewdatav1(ceitem).fwatmap(_.wequestjoinid)
}

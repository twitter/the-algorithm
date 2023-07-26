package com.twittew.fowwow_wecommendations.common.modews

impowt c-com.twittew.adsewvew.thwiftscawa.{dispwaywocation => a-addispwaywocation}
i-impowt com.twittew.fowwow_wecommendations.wogging.thwiftscawa.{
  o-offwinedispwaywocation => t-toffwinedispwaywocation
}
i-impowt c-com.twittew.fowwow_wecommendations.thwiftscawa.{dispwaywocation => t-tdispwaywocation}

seawed twait dispwaywocation {
  def tothwift: tdispwaywocation

  d-def tooffwinethwift: toffwinedispwaywocation

  d-def tofsname: stwing

  // c-cowwesponding dispway wocation in adsewvew if avaiwabwe
  // m-make suwe to be consistent w-with the definition h-hewe
  def toaddispwaywocation: option[addispwaywocation] = nyone
}

/**
 * make suwe you add t-the nyew dw to the fowwowing fiwes and wedepwoy ouw attwibution jobs
 *  - fowwow-wecommendations-sewvice/thwift/swc/main/thwift/dispway_wocation.thwift
 *  - f-fowwow-wecommendations-sewvice/thwift/swc/main/thwift/wogging/dispway_wocation.thwift
 *  - fowwow-wecommendations-sewvice/common/swc/main/scawa/com/twittew/fowwow_wecommendations/common/modews/dispwaywocation.scawa
 */

object d-dispwaywocation {

  c-case o-object pwofiwesidebaw e-extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.pwofiwesidebaw
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation = t-toffwinedispwaywocation.pwofiwesidebaw
    ovewwide vaw tofsname: stwing = "pwofiwesidebaw"

    ovewwide vaw toaddispwaywocation: option[addispwaywocation] = s-some(
      addispwaywocation.pwofiweaccountssidebaw
    )
  }

  c-case o-object hometimewine e-extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.hometimewine
    o-ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation = t-toffwinedispwaywocation.hometimewine
    ovewwide v-vaw tofsname: stwing = "hometimewine"
    o-ovewwide vaw toaddispwaywocation: option[addispwaywocation] = s-some(
      // it is based on the wogic that htw d-dw shouwd cowwespond to sidebaw:
      a-addispwaywocation.wtfsidebaw
    )
  }

  case object weactivefowwow e-extends d-dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.weactivefowwow
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation = t-toffwinedispwaywocation.weactivefowwow
    ovewwide v-vaw tofsname: s-stwing = "weactivefowwow"
  }

  c-case object expwowetab extends dispwaywocation {
    ovewwide v-vaw tothwift: tdispwaywocation = tdispwaywocation.expwowetab
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation = toffwinedispwaywocation.expwowetab
    o-ovewwide vaw tofsname: s-stwing = "expwowetab"
  }

  c-case object magicwecs extends d-dispwaywocation {
    o-ovewwide v-vaw tothwift: t-tdispwaywocation = tdispwaywocation.magicwecs
    ovewwide vaw t-tooffwinethwift: t-toffwinedispwaywocation = t-toffwinedispwaywocation.magicwecs
    o-ovewwide vaw tofsname: s-stwing = "magicwecs"
  }

  case object abupwoadinjection extends dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.abupwoadinjection
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.abupwoadinjection
    ovewwide vaw tofsname: s-stwing = "abupwoadinjection"
  }

  case object wuxwandingpage extends dispwaywocation {
    o-ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.wuxwandingpage
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation = toffwinedispwaywocation.wuxwandingpage
    o-ovewwide vaw tofsname: s-stwing = "wuxwandingpage"
  }

  case object pwofiwebonusfowwow extends dispwaywocation {
    ovewwide v-vaw tothwift: tdispwaywocation = tdispwaywocation.pwofiwebonusfowwow
    o-ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.pwofiwebonusfowwow
    ovewwide vaw tofsname: stwing = "pwofiwebonusfowwow"
  }

  c-case object ewectionexpwowewtf e-extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.ewectionexpwowewtf
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.ewectionexpwowewtf
    ovewwide vaw tofsname: stwing = "ewectionexpwowewtf"
  }

  c-case object c-cwustewfowwow e-extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.cwustewfowwow
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      toffwinedispwaywocation.cwustewfowwow
    ovewwide vaw tofsname: stwing = "cwustewfowwow"
    ovewwide v-vaw toaddispwaywocation: o-option[addispwaywocation] = some(
      addispwaywocation.cwustewfowwow
    )
  }

  c-case object htwbonusfowwow e-extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = tdispwaywocation.htwbonusfowwow
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.htwbonusfowwow
    ovewwide vaw tofsname: stwing = "htwbonusfowwow"
  }

  c-case object t-topicwandingpageheadew extends dispwaywocation {
    ovewwide v-vaw tothwift: t-tdispwaywocation = tdispwaywocation.topicwandingpageheadew
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.topicwandingpageheadew
    ovewwide vaw tofsname: s-stwing = "topicwandingpageheadew"
  }

  case object nyewusewsawusbackfiww e-extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.newusewsawusbackfiww
    o-ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.newusewsawusbackfiww
    ovewwide vaw t-tofsname: stwing = "newusewsawusbackfiww"
  }

  case object nyuxpymk e-extends d-dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.nuxpymk
    o-ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.nuxpymk
    ovewwide v-vaw tofsname: s-stwing = "nuxpymk"
  }

  case object nyuxintewests extends d-dispwaywocation {
    ovewwide v-vaw tothwift: tdispwaywocation = t-tdispwaywocation.nuxintewests
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.nuxintewests
    o-ovewwide v-vaw tofsname: s-stwing = "nuxintewests"
  }

  case object nyuxtopicbonusfowwow e-extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.nuxtopicbonusfowwow
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      toffwinedispwaywocation.nuxtopicbonusfowwow
    o-ovewwide vaw tofsname: stwing = "nuxtopicbonusfowwow"
  }

  c-case object sidebaw e-extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.sidebaw
    o-ovewwide v-vaw tooffwinethwift: t-toffwinedispwaywocation = toffwinedispwaywocation.sidebaw
    ovewwide vaw tofsname: stwing = "sidebaw"

    ovewwide vaw toaddispwaywocation: option[addispwaywocation] = some(
      addispwaywocation.wtfsidebaw
    )
  }

  case object c-campaignfowm extends d-dispwaywocation {
    o-ovewwide vaw tothwift: t-tdispwaywocation = tdispwaywocation.campaignfowm
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation = t-toffwinedispwaywocation.campaignfowm
    ovewwide v-vaw tofsname: stwing = "campaignfowm"
  }

  case object pwofiwetopfowwowews e-extends dispwaywocation {
    o-ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.pwofiwetopfowwowews
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.pwofiwetopfowwowews
    ovewwide vaw tofsname: stwing = "pwofiwetopfowwowews"
  }

  case o-object pwofiwetopfowwowing e-extends d-dispwaywocation {
    o-ovewwide v-vaw tothwift: tdispwaywocation = t-tdispwaywocation.pwofiwetopfowwowing
    o-ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.pwofiwetopfowwowing
    ovewwide v-vaw tofsname: stwing = "pwofiwetopfowwowing"
  }

  case object w-wuxpymk extends dispwaywocation {
    o-ovewwide v-vaw tothwift: tdispwaywocation = t-tdispwaywocation.wuxpymk
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.wuxpymk
    ovewwide vaw t-tofsname: stwing = "wuxpymk"
  }

  c-case object i-indiacovid19cuwatedaccountswtf extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = tdispwaywocation.indiacovid19cuwatedaccountswtf
    ovewwide v-vaw tooffwinethwift: t-toffwinedispwaywocation =
      toffwinedispwaywocation.indiacovid19cuwatedaccountswtf
    o-ovewwide vaw tofsname: stwing = "indiacovid19cuwatedaccountswtf"
  }

  c-case o-object peopwepwuspwus extends dispwaywocation {
    ovewwide vaw t-tothwift: tdispwaywocation = tdispwaywocation.peopwepwuspwus
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.peopwepwuspwus
    ovewwide v-vaw tofsname: stwing = "peopwepwuspwus"
  }

  c-case object t-tweetnotificationwecs e-extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.tweetnotificationwecs
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.tweetnotificationwecs
    ovewwide vaw tofsname: stwing = "tweetnotificationwecs"
  }

  case object pwofiwedevicefowwow e-extends d-dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.pwofiwedevicefowwow
    o-ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.pwofiwedevicefowwow
    ovewwide v-vaw tofsname: stwing = "pwofiwedevicefowwow"
  }

  c-case object wecosbackfiww extends d-dispwaywocation {
    ovewwide v-vaw tothwift: t-tdispwaywocation = tdispwaywocation.wecosbackfiww
    ovewwide v-vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.wecosbackfiww
    o-ovewwide vaw tofsname: s-stwing = "wecosbackfiww"
  }

  c-case object h-htwspacehosts e-extends dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.htwspacehosts
    o-ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.htwspacehosts
    ovewwide vaw tofsname: stwing = "htwspacehosts"
  }

  c-case object postnuxfowwowtask e-extends d-dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.postnuxfowwowtask
    ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.postnuxfowwowtask
    ovewwide vaw t-tofsname: stwing = "postnuxfowwowtask"
  }

  case object topicwandingpage extends dispwaywocation {
    ovewwide v-vaw tothwift: tdispwaywocation = t-tdispwaywocation.topicwandingpage
    o-ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.topicwandingpage
    o-ovewwide vaw tofsname: stwing = "topicwandingpage"
  }

  c-case object usewtypeaheadpwefetch e-extends dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.usewtypeaheadpwefetch
    o-ovewwide vaw t-tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.usewtypeaheadpwefetch
    ovewwide vaw tofsname: stwing = "usewtypeaheadpwefetch"
  }

  c-case object hometimewinewewatabweaccounts e-extends d-dispwaywocation {
    o-ovewwide vaw tothwift: t-tdispwaywocation = t-tdispwaywocation.hometimewinewewatabweaccounts
    o-ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.hometimewinewewatabweaccounts
    o-ovewwide vaw tofsname: s-stwing = "hometimewinewewatabweaccounts"
  }

  c-case object n-nyuxgeocategowy e-extends dispwaywocation {
    o-ovewwide vaw tothwift: t-tdispwaywocation = tdispwaywocation.nuxgeocategowy
    o-ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      t-toffwinedispwaywocation.nuxgeocategowy
    ovewwide v-vaw tofsname: s-stwing = "nuxgeocategowy"
  }

  c-case object nyuxintewestscategowy extends dispwaywocation {
    ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.nuxintewestscategowy
    o-ovewwide vaw t-tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.nuxintewestscategowy
    ovewwide vaw t-tofsname: stwing = "nuxintewestscategowy"
  }

  c-case object topawticwes extends d-dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = tdispwaywocation.topawticwes
    ovewwide vaw t-tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.topawticwes
    o-ovewwide vaw tofsname: stwing = "topawticwes"
  }

  c-case o-object nyuxpymkcategowy extends dispwaywocation {
    o-ovewwide vaw tothwift: tdispwaywocation = t-tdispwaywocation.nuxpymkcategowy
    ovewwide v-vaw tooffwinethwift: t-toffwinedispwaywocation =
      toffwinedispwaywocation.nuxpymkcategowy
    o-ovewwide vaw tofsname: s-stwing = "nuxpymkcategowy"
  }

  case o-object hometimewinetweetwecs extends d-dispwaywocation {
    o-ovewwide v-vaw tothwift: t-tdispwaywocation = tdispwaywocation.hometimewinetweetwecs
    o-ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.hometimewinetweetwecs
    ovewwide vaw tofsname: s-stwing = "hometimewinetweetwecs"
  }

  case object htwbuwkfwiendfowwows extends dispwaywocation {
    o-ovewwide v-vaw tothwift: t-tdispwaywocation = tdispwaywocation.htwbuwkfwiendfowwows
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.htwbuwkfwiendfowwows
    o-ovewwide vaw tofsname: stwing = "htwbuwkfwiendfowwows"
  }

  case o-object nyuxautofowwow e-extends dispwaywocation {
    ovewwide v-vaw tothwift: tdispwaywocation = tdispwaywocation.nuxautofowwow
    o-ovewwide vaw t-tooffwinethwift: t-toffwinedispwaywocation =
      t-toffwinedispwaywocation.nuxautofowwow
    o-ovewwide vaw tofsname: stwing = "nuxautofowwow"
  }

  case object seawchbonusfowwow e-extends dispwaywocation {
    ovewwide vaw tothwift: t-tdispwaywocation = tdispwaywocation.seawchbonusfowwow
    ovewwide vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.seawchbonusfowwow
    ovewwide v-vaw tofsname: stwing = "seawchbonusfowwow"
  }

  case object contentwecommendew extends dispwaywocation {
    o-ovewwide vaw t-tothwift: tdispwaywocation = tdispwaywocation.contentwecommendew
    ovewwide v-vaw tooffwinethwift: toffwinedispwaywocation =
      toffwinedispwaywocation.contentwecommendew
    o-ovewwide vaw t-tofsname: stwing = "contentwecommendew"
  }

  case object hometimewinewevewsechwon e-extends dispwaywocation {
    ovewwide vaw t-tothwift: tdispwaywocation = tdispwaywocation.hometimewinewevewsechwon
    ovewwide vaw tooffwinethwift: t-toffwinedispwaywocation =
      toffwinedispwaywocation.hometimewinewevewsechwon
    ovewwide v-vaw tofsname: s-stwing = "hometimewinewevewsechwon"
  }

  d-def fwomthwift(dispwaywocation: tdispwaywocation): dispwaywocation = d-dispwaywocation match {
    case tdispwaywocation.pwofiwesidebaw => pwofiwesidebaw
    case t-tdispwaywocation.hometimewine => h-hometimewine
    c-case tdispwaywocation.magicwecs => m-magicwecs
    case tdispwaywocation.abupwoadinjection => abupwoadinjection
    case tdispwaywocation.wuxwandingpage => w-wuxwandingpage
    c-case tdispwaywocation.pwofiwebonusfowwow => pwofiwebonusfowwow
    case tdispwaywocation.ewectionexpwowewtf => ewectionexpwowewtf
    c-case tdispwaywocation.cwustewfowwow => cwustewfowwow
    case tdispwaywocation.htwbonusfowwow => h-htwbonusfowwow
    case tdispwaywocation.weactivefowwow => weactivefowwow
    c-case tdispwaywocation.topicwandingpageheadew => t-topicwandingpageheadew
    case tdispwaywocation.newusewsawusbackfiww => n-nyewusewsawusbackfiww
    c-case tdispwaywocation.nuxpymk => n-nyuxpymk
    case tdispwaywocation.nuxintewests => nyuxintewests
    c-case tdispwaywocation.nuxtopicbonusfowwow => nyuxtopicbonusfowwow
    c-case tdispwaywocation.expwowetab => expwowetab
    case tdispwaywocation.sidebaw => sidebaw
    c-case tdispwaywocation.campaignfowm => c-campaignfowm
    c-case t-tdispwaywocation.pwofiwetopfowwowews => p-pwofiwetopfowwowews
    case tdispwaywocation.pwofiwetopfowwowing => p-pwofiwetopfowwowing
    case tdispwaywocation.wuxpymk => wuxpymk
    c-case tdispwaywocation.indiacovid19cuwatedaccountswtf => indiacovid19cuwatedaccountswtf
    c-case tdispwaywocation.peopwepwuspwus => peopwepwuspwus
    c-case tdispwaywocation.tweetnotificationwecs => t-tweetnotificationwecs
    case tdispwaywocation.pwofiwedevicefowwow => p-pwofiwedevicefowwow
    case tdispwaywocation.wecosbackfiww => w-wecosbackfiww
    case t-tdispwaywocation.htwspacehosts => htwspacehosts
    c-case tdispwaywocation.postnuxfowwowtask => p-postnuxfowwowtask
    case tdispwaywocation.topicwandingpage => t-topicwandingpage
    case tdispwaywocation.usewtypeaheadpwefetch => usewtypeaheadpwefetch
    case tdispwaywocation.hometimewinewewatabweaccounts => h-hometimewinewewatabweaccounts
    case tdispwaywocation.nuxgeocategowy => n-nyuxgeocategowy
    case tdispwaywocation.nuxintewestscategowy => nyuxintewestscategowy
    c-case t-tdispwaywocation.topawticwes => t-topawticwes
    case tdispwaywocation.nuxpymkcategowy => n-nyuxpymkcategowy
    c-case tdispwaywocation.hometimewinetweetwecs => hometimewinetweetwecs
    c-case tdispwaywocation.htwbuwkfwiendfowwows => htwbuwkfwiendfowwows
    c-case tdispwaywocation.nuxautofowwow => nyuxautofowwow
    c-case t-tdispwaywocation.seawchbonusfowwow => seawchbonusfowwow
    case tdispwaywocation.contentwecommendew => contentwecommendew
    case t-tdispwaywocation.hometimewinewevewsechwon => h-hometimewinewevewsechwon
    case tdispwaywocation.enumunknowndispwaywocation(i) =>
      thwow n-new unknowndispwaywocationexception(
        s"unknown d-dispway w-wocation thwift enum with vawue: ${i}")
  }

  def fwomoffwinethwift(dispwaywocation: toffwinedispwaywocation): dispwaywocation =
    d-dispwaywocation match {
      case toffwinedispwaywocation.pwofiwesidebaw => p-pwofiwesidebaw
      case toffwinedispwaywocation.hometimewine => h-hometimewine
      c-case toffwinedispwaywocation.magicwecs => magicwecs
      c-case toffwinedispwaywocation.abupwoadinjection => a-abupwoadinjection
      c-case t-toffwinedispwaywocation.wuxwandingpage => w-wuxwandingpage
      c-case toffwinedispwaywocation.pwofiwebonusfowwow => pwofiwebonusfowwow
      case toffwinedispwaywocation.ewectionexpwowewtf => ewectionexpwowewtf
      case toffwinedispwaywocation.cwustewfowwow => cwustewfowwow
      c-case toffwinedispwaywocation.htwbonusfowwow => h-htwbonusfowwow
      c-case t-toffwinedispwaywocation.topicwandingpageheadew => t-topicwandingpageheadew
      c-case toffwinedispwaywocation.newusewsawusbackfiww => nyewusewsawusbackfiww
      case toffwinedispwaywocation.nuxpymk => nyuxpymk
      case toffwinedispwaywocation.nuxintewests => n-nyuxintewests
      c-case toffwinedispwaywocation.nuxtopicbonusfowwow => nyuxtopicbonusfowwow
      case toffwinedispwaywocation.expwowetab => expwowetab
      c-case toffwinedispwaywocation.weactivefowwow => w-weactivefowwow
      c-case toffwinedispwaywocation.sidebaw => sidebaw
      case toffwinedispwaywocation.campaignfowm => c-campaignfowm
      case toffwinedispwaywocation.pwofiwetopfowwowews => pwofiwetopfowwowews
      c-case t-toffwinedispwaywocation.pwofiwetopfowwowing => pwofiwetopfowwowing
      case t-toffwinedispwaywocation.wuxpymk => wuxpymk
      c-case toffwinedispwaywocation.indiacovid19cuwatedaccountswtf => i-indiacovid19cuwatedaccountswtf
      case toffwinedispwaywocation.peopwepwuspwus => p-peopwepwuspwus
      c-case toffwinedispwaywocation.tweetnotificationwecs => t-tweetnotificationwecs
      c-case t-toffwinedispwaywocation.pwofiwedevicefowwow => p-pwofiwedevicefowwow
      case toffwinedispwaywocation.wecosbackfiww => w-wecosbackfiww
      c-case toffwinedispwaywocation.htwspacehosts => h-htwspacehosts
      case toffwinedispwaywocation.postnuxfowwowtask => p-postnuxfowwowtask
      case toffwinedispwaywocation.topicwandingpage => t-topicwandingpage
      case toffwinedispwaywocation.usewtypeaheadpwefetch => u-usewtypeaheadpwefetch
      c-case toffwinedispwaywocation.hometimewinewewatabweaccounts => hometimewinewewatabweaccounts
      case toffwinedispwaywocation.nuxgeocategowy => n-nyuxgeocategowy
      case toffwinedispwaywocation.nuxintewestscategowy => nyuxintewestscategowy
      c-case toffwinedispwaywocation.topawticwes => t-topawticwes
      case toffwinedispwaywocation.nuxpymkcategowy => nyuxpymkcategowy
      case t-toffwinedispwaywocation.hometimewinetweetwecs => h-hometimewinetweetwecs
      case toffwinedispwaywocation.htwbuwkfwiendfowwows => h-htwbuwkfwiendfowwows
      case toffwinedispwaywocation.nuxautofowwow => nyuxautofowwow
      case toffwinedispwaywocation.seawchbonusfowwow => s-seawchbonusfowwow
      c-case toffwinedispwaywocation.contentwecommendew => c-contentwecommendew
      c-case toffwinedispwaywocation.hometimewinewevewsechwon => hometimewinewevewsechwon
      case toffwinedispwaywocation.enumunknownoffwinedispwaywocation(i) =>
        thwow n-nyew unknowndispwaywocationexception(
          s-s"unknown offwine d-dispway wocation t-thwift enum with vawue: ${i}")
    }
}

cwass unknowndispwaywocationexception(message: stwing) extends exception(message)

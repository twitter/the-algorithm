package com.twittew.fwigate.pushsewvice.stowe

impowt c-com.twittew.content_mixew.thwiftscawa.contentmixew
i-impowt com.twittew.content_mixew.thwiftscawa.contentmixewwequest
i-impowt c-com.twittew.content_mixew.thwiftscawa.contentmixewwesponse
i-impowt c-com.twittew.stowehaus.weadabwestowe
i-impowt com.twittew.utiw.futuwe

c-case cwass contentmixewstowe(contentmixew: contentmixew.methodpewendpoint)
    extends weadabwestowe[contentmixewwequest, contentmixewwesponse] {

  o-ovewwide def get(wequest: contentmixewwequest): f-futuwe[option[contentmixewwesponse]] = {
    contentmixew.getcandidates(wequest).map { w-wesponse =>
      some(wesponse)
    }
  }
}

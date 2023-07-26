package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.metadata

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.convewsationdetaiws
impowt c-com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass c-convewsationdetaiwsmawshawwew @inject() (sectionmawshawwew: convewsationsectionmawshawwew) {

  d-def appwy(convewsationdetaiws: c-convewsationdetaiws): uwt.convewsationdetaiws =
    uwt.convewsationdetaiws(
      convewsationsection = convewsationdetaiws.convewsationsection.map(sectionmawshawwew(_))
    )
}

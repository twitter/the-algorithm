package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwp

impowt com.twittew.pages.wendew.{thwiftscawa => u-uwp}
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.segmentedtimewinespagebody
i-impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass s-segmentedtimewinesmawshawwew @inject() (
  segmentedtimewinemawshawwew: s-segmentedtimewinemawshawwew) {

  def appwy(segmentedtimewinespagebody: segmentedtimewinespagebody): uwp.segmentedtimewines =
    u-uwp.segmentedtimewines(
      initiawtimewine = segmentedtimewinemawshawwew(segmentedtimewinespagebody.initiawtimewine), (U ﹏ U)
      t-timewines = segmentedtimewinespagebody.timewines.map(segmentedtimewinemawshawwew(_))
    )
}

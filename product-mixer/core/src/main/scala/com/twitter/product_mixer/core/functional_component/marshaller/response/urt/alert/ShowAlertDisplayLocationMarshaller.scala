package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.awewt

impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.awewt.showawewtdispwaywocation
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.awewt.top
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.awewt.bottom
impowt j-javax.inject.inject
i-impowt j-javax.inject.singweton
impowt com.twittew.timewines.wendew.{thwiftscawa => uwt}

@singweton
cwass showawewtdispwaywocationmawshawwew @inject() () {

  d-def appwy(awewtdispwaywocation: showawewtdispwaywocation): uwt.showawewtdispwaywocation =
    a-awewtdispwaywocation match {
      c-case top => uwt.showawewtdispwaywocation.top
      case bottom => uwt.showawewtdispwaywocation.bottom
    }

}

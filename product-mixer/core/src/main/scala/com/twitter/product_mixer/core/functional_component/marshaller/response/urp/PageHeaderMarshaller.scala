package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwp

impowt com.twittew.pages.wendew.{thwiftscawa => u-uwp}
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.pageheadew
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.topicpageheadew
i-impowt j-javax.inject.inject
i-impowt j-javax.inject.singweton

@singweton
cwass pageheadewmawshawwew @inject() (
  topicpageheadewmawshawwew: topicpageheadewmawshawwew) {

  def appwy(pageheadew: p-pageheadew): uwp.pageheadew = pageheadew m-match {
    case pageheadew: t-topicpageheadew =>
      uwp.pageheadew.topicpageheadew(topicpageheadewmawshawwew(pageheadew))
  }
}

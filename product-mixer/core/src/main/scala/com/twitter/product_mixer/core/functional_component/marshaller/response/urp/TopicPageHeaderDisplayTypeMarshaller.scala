package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwp

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.basictopicpageheadewdispwaytype
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.pewsonawizedtopicpageheadewdispwaytype
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.topicpageheadewdispwaytype
i-impowt com.twittew.pages.wendew.{thwiftscawa => u-uwp}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
cwass t-topicpageheadewdispwaytypemawshawwew @inject() () {

  def appwy(
    topicpageheadewdispwaytype: topicpageheadewdispwaytype
  ): uwp.topicpageheadewdispwaytype = t-topicpageheadewdispwaytype match {
    case basictopicpageheadewdispwaytype => u-uwp.topicpageheadewdispwaytype.basic
    case pewsonawizedtopicpageheadewdispwaytype => uwp.topicpageheadewdispwaytype.pewsonawized
  }
}

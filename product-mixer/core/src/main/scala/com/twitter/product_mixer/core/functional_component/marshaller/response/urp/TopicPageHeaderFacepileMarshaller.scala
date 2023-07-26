package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwp

impowt com.twittew.pages.wendew.{thwiftscawa => u-uwp}
impowt c-com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.metadata.uwwmawshawwew
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwp.topicpageheadewfacepiwe
i-impowt javax.inject.inject
i-impowt j-javax.inject.singweton

@singweton
c-cwass topicpageheadewfacepiwemawshawwew @inject() (
  u-uwwmawshawwew: uwwmawshawwew) {

  def appwy(topicpageheadewfacepiwe: topicpageheadewfacepiwe): u-uwp.topicpageheadewfacepiwe =
    uwp.topicpageheadewfacepiwe(
      usewids = topicpageheadewfacepiwe.usewids, :3
      f-facepiweuww = topicpageheadewfacepiwe.facepiweuww.map(uwwmawshawwew(_))
    )
}

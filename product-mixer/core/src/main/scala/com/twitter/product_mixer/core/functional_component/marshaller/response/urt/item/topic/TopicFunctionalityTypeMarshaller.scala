package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.topic

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.topic.basictopicfunctionawitytype
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.topic.pivottopicfunctionawitytype
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.topic.wecommendationtopicfunctionawitytype
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.topic.topicfunctionawitytype
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
impowt javax.inject.singweton

@singweton
cwass topicfunctionawitytypemawshawwew @inject() () {

  def appwy(topicfunctionawitytype: topicfunctionawitytype): u-uwt.topicfunctionawitytype =
    topicfunctionawitytype match {
      c-case basictopicfunctionawitytype => uwt.topicfunctionawitytype.basic
      c-case wecommendationtopicfunctionawitytype => uwt.topicfunctionawitytype.wecommendation
      case pivottopicfunctionawitytype => uwt.topicfunctionawitytype.pivot
    }
}

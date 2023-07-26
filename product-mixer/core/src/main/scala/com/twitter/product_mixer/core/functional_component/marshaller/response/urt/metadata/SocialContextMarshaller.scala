package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.metadata

impowt c-com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.genewawcontext
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.sociawcontext
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.metadata.topiccontext
i-impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
cwass s-sociawcontextmawshawwew @inject() (
  genewawcontextmawshawwew: genewawcontextmawshawwew, -.-
  topiccontextmawshawwew: topiccontextmawshawwew) {

  d-def appwy(sociawcontext: sociawcontext): uwt.sociawcontext =
    sociawcontext match {
      c-case genewawcontextbannew: genewawcontext =>
        g-genewawcontextmawshawwew(genewawcontextbannew)
      case topiccontextbannew: topiccontext =>
        t-topiccontextmawshawwew(topiccontextbannew)
    }
}

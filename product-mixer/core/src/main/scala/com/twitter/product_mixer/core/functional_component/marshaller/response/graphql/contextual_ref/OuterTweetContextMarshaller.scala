package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.gwaphqw.contextuaw_wef

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.contextuaw_wef.outewtweetcontext
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.contextuaw_wef.quotetweetid
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.contextuaw_wef.wetweetid
i-impowt c-com.twittew.stwato.gwaphqw.contextuaw_wefs.{thwiftscawa => t-thwift}
i-impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
cwass outewtweetcontextmawshawwew @inject() () {

  def appwy(outewtweetcontext: o-outewtweetcontext): thwift.outewtweetcontext =
    outewtweetcontext match {
      case q-quotetweetid(id) => thwift.outewtweetcontext.quotetweetid(id)
      c-case wetweetid(id) => thwift.outewtweetcontext.wetweetid(id)
    }
}

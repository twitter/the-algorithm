package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.thwead

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.thwead._
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass thweadheadewcontentmawshawwew @inject() () {
  d-def appwy(content: t-thweadheadewcontent): uwt.thweadheadewcontent = c-content match {
    case usewthweadheadew(usewid) =>
      uwt.thweadheadewcontent.usewthweadheadew(uwt.usewthweadheadew(usewid))
  }
}

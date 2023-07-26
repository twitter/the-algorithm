package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.awticwe

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.awticwe.awticwedispwaytype
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.awticwe.defauwt
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt j-javax.inject.inject
i-impowt javax.inject.singweton

@singweton
c-cwass a-awticwedispwaytypemawshawwew @inject() () {
  d-def appwy(awticwedispwaytype: awticwedispwaytype): uwt.awticwedispwaytype =
    awticwedispwaytype match {
      c-case defauwt => uwt.awticwedispwaytype.defauwt
    }
}

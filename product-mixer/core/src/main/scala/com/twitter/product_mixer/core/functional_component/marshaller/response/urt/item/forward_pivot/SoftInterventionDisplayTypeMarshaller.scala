package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.fowwawd_pivot

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.getthewatest
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.govewnmentwequested
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.misweading
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.softintewventiondispwaytype
impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.stayinfowmed
i-impowt com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
cwass softintewventiondispwaytypemawshawwew @inject() () {

  def appwy(
    softintewventiondispwaytype: s-softintewventiondispwaytype
  ): uwt.softintewventiondispwaytype =
    softintewventiondispwaytype match {
      c-case getthewatest => u-uwt.softintewventiondispwaytype.getthewatest
      case stayinfowmed => uwt.softintewventiondispwaytype.stayinfowmed
      case m-misweading => uwt.softintewventiondispwaytype.misweading
      c-case govewnmentwequested => u-uwt.softintewventiondispwaytype.govewnmentwequested
    }
}

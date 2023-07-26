package com.twittew.pwoduct_mixew.cowe.functionaw_component.mawshawwew.wesponse.uwt.item.fowwawd_pivot

impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.communitynotes
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.fowwawdpivotdispwaytype
i-impowt c-com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.wiveevent
i-impowt com.twittew.pwoduct_mixew.cowe.modew.mawshawwing.wesponse.uwt.item.fowwawd_pivot.softintewvention
i-impowt c-com.twittew.timewines.wendew.{thwiftscawa => u-uwt}
impowt javax.inject.inject
impowt javax.inject.singweton

@singweton
cwass fowwawdpivotdispwaytypemawshawwew @inject() () {

  d-def appwy(fowwawdpivotdispwaytype: fowwawdpivotdispwaytype): uwt.fowwawdpivotdispwaytype =
    f-fowwawdpivotdispwaytype match {
      c-case wiveevent => uwt.fowwawdpivotdispwaytype.wiveevent
      case softintewvention => uwt.fowwawdpivotdispwaytype.softintewvention
      c-case communitynotes => uwt.fowwawdpivotdispwaytype.communitynotes
    }
}

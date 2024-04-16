package com.tw ter.cr_m xer.blender

 mport com.tw ter.cr_m xer.blender. mpl c S gnalBackF llBlender.BackF llS ceTypes
 mport com.tw ter.cr_m xer.blender. mpl c S gnalBackF llBlender.BackF llS ceTypesW hV deo
 mport com.tw ter.cr_m xer.model.BlendedCand date
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.param.BlenderParams
 mport com.tw ter.cr_m xer.thr ftscala.S ceType
 mport com.tw ter.cr_m xer.ut l. nterleaveUt l
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future
 mport javax. nject. nject

case class S ceTypeBackF llBlender @ nject() (globalStats: StatsRece ver) {

  pr vate val na : Str ng = t .getClass.getCanon calNa 
  pr vate val stats: StatsRece ver = globalStats.scope(na )

  /**
   *  Part  on t  cand dates based on s ce type
   *   nterleave t  two part  ons of cand dates separately
   *  T n append t  back f ll cand dates to t  end
   */
  def blend(
    params: Params,
     nputCand dates: Seq[Seq[ n  alCand date]],
  ): Future[Seq[BlendedCand date]] = {

    // F lter out empty cand date sequence
    val cand dates =  nputCand dates.f lter(_.nonEmpty)

    val backF llS ceTypes =
       f (params(BlenderParams.S ceTypeBackF llEnableV deoBackF ll)) BackF llS ceTypesW hV deo
      else BackF llS ceTypes
    // part  on cand dates based on t  r s ce types
    val (backF llCand dates, regularCand dates) =
      cand dates.part  on(
        _. ad.cand dateGenerat on nfo.s ce nfoOpt
          .ex sts(s ce nfo => backF llS ceTypes.conta ns(s ce nfo.s ceType)))

    val  nterleavedRegularCand dates =  nterleaveUt l. nterleave(regularCand dates)
    val  nterleavedBackF llCand dates =
       nterleaveUt l. nterleave(backF llCand dates)
    stats.stat("backF llCand dates").add( nterleavedBackF llCand dates.s ze)
    // Append  nterleaved backf ll cand dates to t  end
    val  nterleavedCand dates =  nterleavedRegularCand dates ++  nterleavedBackF llCand dates

    stats.stat("cand dates").add( nterleavedCand dates.s ze)

    val blendedCand dates = BlendedCand datesBu lder.bu ld( nputCand dates,  nterleavedCand dates)
    Future.value(blendedCand dates)
  }

}

object  mpl c S gnalBackF llBlender {
  f nal val BackF llS ceTypesW hV deo: Set[S ceType] = Set(
    S ceType.UserRepeatedProf leV s ,
    S ceType.V deoT etPlayback50,
    S ceType.V deoT etQual yV ew)

  f nal val BackF llS ceTypes: Set[S ceType] = Set(S ceType.UserRepeatedProf leV s )
}

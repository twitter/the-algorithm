package com.tw ter.cr_m xer.blender

 mport com.tw ter.core_workflows.user_model.thr ftscala.UserState
 mport com.tw ter.cr_m xer.model.BlendedCand date
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.param.BlenderParams
 mport com.tw ter.cr_m xer.param.BlenderParams.Blend ngAlgor hmEnum
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.t  l nes.conf gap .Params
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
case class Sw chBlender @ nject() (
  defaultBlender:  nterleaveBlender,
  s ceTypeBackF llBlender: S ceTypeBackF llBlender,
  adsBlender: AdsBlender,
  contentS gnalBlender: ContentS gnalBlender,
  globalStats: StatsRece ver) {

  pr vate val stats = globalStats.scope(t .getClass.getCanon calNa )

  def blend(
    params: Params,
    userState: UserState,
     nputCand dates: Seq[Seq[ n  alCand date]],
  ): Future[Seq[BlendedCand date]] = {
    // Take out empty seq
    val nonEmptyCand dates =  nputCand dates.collect {
      case cand dates  f cand dates.nonEmpty =>
        cand dates
    }
    stats.stat("num_of_sequences").add( nputCand dates.s ze)

    // Sort t  seqs  n an order
    val  nnerS gnalSort ng = params(BlenderParams.S gnalTypeSort ngAlgor hmParam) match {
      case BlenderParams.ContentBasedSort ngAlgor hmEnum.S ceS gnalRecency =>
        Sw chBlender.T  stampOrder
      case BlenderParams.ContentBasedSort ngAlgor hmEnum.RandomSort ng => Sw chBlender.RandomOrder
      case _ => Sw chBlender.T  stampOrder
    }

    val cand datesToBlend = nonEmptyCand dates.sortBy(_. ad)( nnerS gnalSort ng)
    // Blend based on spec f ed blender rules
    params(BlenderParams.Blend ngAlgor hmParam) match {
      case Blend ngAlgor hmEnum.RoundRob n =>
        defaultBlender.blend(cand datesToBlend)
      case Blend ngAlgor hmEnum.S ceTypeBackF ll =>
        s ceTypeBackF llBlender.blend(params, cand datesToBlend)
      case Blend ngAlgor hmEnum.S ceS gnalSort ng =>
        contentS gnalBlender.blend(params, cand datesToBlend)
      case _ => defaultBlender.blend(cand datesToBlend)
    }
  }
}

object Sw chBlender {

  /**
   * Prefers cand dates generated from s ces w h t  latest t  stamps.
   * T  ne r t  s ce s gnal, t  h g r a cand date ranks.
   * T  order ng b ases aga nst consu r-based cand dates because t  r t  stamp defaults to 0
   *
   * W h n a Seq[Seq[Cand date]], all cand dates w h n a  nner Seq
   * are guaranteed to have t  sa  s ce nfo because t y are grouped by (s ce nfo, SE model).
   *  nce,   can p ck . adOpt on to represent t  whole l st w n f lter ng by t   nternal d of t  s ce nfoOpt.
   * But of c se t  s m lar yEng ne score  n a CG nfo could be d fferent.
   */
  val T  stampOrder: Order ng[ n  alCand date] =
    math.Order ng
      .by[ n  alCand date, T  ](
        _.cand dateGenerat on nfo.s ce nfoOpt
          .flatMap(_.s ceEventT  )
          .getOrElse(T  .fromM ll seconds(0L)))
      .reverse

  pr vate val RandomOrder: Order ng[ n  alCand date] =
    Order ng.by[ n  alCand date, Double](_ => scala.ut l.Random.nextDouble())
}

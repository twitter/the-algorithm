package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.s mclusters_v2.common.T et d
 mport com.tw ter.s mclusters_v2.thr ftscala. nternal d
 mport com.tw ter.ut l.Future
 mport javax. nject.S ngleton

@S ngleton
case class  mpressedT etl stF lter() extends F lterBase {
   mport  mpressedT etl stF lter._

  overr de val na : Str ng = t .getClass.getCanon calNa 

  overr de type Conf gType = F lterConf g

  /*
   F lter ng removes so  cand dates based on conf gurable cr er a.
   */
  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    conf g: F lterConf g
  ): Future[Seq[Seq[ n  alCand date]]] = {
    // Remove cand dates wh ch match a s ce t et, or wh ch are passed  n  mpressedT etL st
    val s ceT etsMatch = cand dates
      .flatMap {

        /***
         * W h n a Seq[Seq[ n  alCand date]], all cand dates w h n a  nner Seq
         * are guaranteed to have t  sa  s ce nfo.  nce,   can p ck . adOpt on
         * to represent t  whole l st w n f lter ng by t   nternal d of t  s ce nfoOpt.
         * But of c se t  s m lar yEng ne nfo could be d fferent.
         */
        _. adOpt on.flatMap { cand date =>
          cand date.cand dateGenerat on nfo.s ce nfoOpt.map(_. nternal d)
        }
      }.collect {
        case  nternal d.T et d( d) =>  d
      }

    val  mpressedT etL st: Set[T et d] =
      conf g. mpressedT etL st ++ s ceT etsMatch

    val f lteredCand dateMap: Seq[Seq[ n  alCand date]] =
      cand dates.map {
        _.f lterNot { cand date =>
           mpressedT etL st.conta ns(cand date.t et d)
        }
      }
    Future.value(f lteredCand dateMap)
  }

  overr de def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](
    request: CGQueryType
  ): F lterConf g = {
    F lterConf g(request. mpressedT etL st)
  }
}

object  mpressedT etl stF lter {
  case class F lterConf g( mpressedT etL st: Set[T et d])
}

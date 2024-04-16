package com.tw ter.cr_m xer.f lter

 mport com.tw ter.cr_m xer.model.Cand dateGeneratorQuery
 mport com.tw ter.cr_m xer.model. n  alCand date
 mport com.tw ter.cr_m xer.param.GlobalParams
 mport com.tw ter.snowflake. d.Snowflake d
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.Future
 mport com.tw ter.ut l.T  
 mport javax. nject.S ngleton
 mport com.tw ter.convers ons.Durat onOps._

@S ngleton
case class T etAgeF lter() extends F lterBase {
  overr de val na : Str ng = t .getClass.getCanon calNa 

  overr de type Conf gType = Durat on

  overr de def f lter(
    cand dates: Seq[Seq[ n  alCand date]],
    maxT etAge: Durat on
  ): Future[Seq[Seq[ n  alCand date]]] = {
     f (maxT etAge >= 720.h s) {
      Future.value(cand dates)
    } else {
      // T et  Ds are approx mately chronolog cal (see http://go/snowflake),
      // so   are bu ld ng t  earl est t et  d once,
      // and pass that as t  value to f lter cand dates for each Cand dateGenerat onModel.
      val earl estT et d = Snowflake d.f rst dFor(T  .now - maxT etAge)
      Future.value(cand dates.map(_.f lter(_.t et d >= earl estT et d)))
    }
  }

  overr de def requestToConf g[CGQueryType <: Cand dateGeneratorQuery](
    query: CGQueryType
  ): Durat on = {
    query.params(GlobalParams.MaxT etAgeH sParam)
  }
}

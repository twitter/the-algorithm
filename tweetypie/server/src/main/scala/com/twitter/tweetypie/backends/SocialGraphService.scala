package com.tw ter.t etyp e
package backends

 mport com.tw ter.f nagle.serv ce.RetryPol cy
 mport com.tw ter.servo.ut l.FutureArrow
 mport com.tw ter.soc algraph.thr ftscala.Ex stsRequest
 mport com.tw ter.soc algraph.thr ftscala.Ex stsResult
 mport com.tw ter.soc algraph.thr ftscala.RequestContext
 mport com.tw ter.soc algraph.{thr ftscala => sg}
 mport com.tw ter.t etyp e.ut l.RetryPol cyBu lder

object Soc alGraphServ ce {
   mport Backend._

  type Ex sts =
    FutureArrow[(Seq[sg.Ex stsRequest], Opt on[sg.RequestContext]), Seq[sg.Ex stsResult]]

  def fromCl ent(cl ent: sg.Soc alGraphServ ce. thodPerEndpo nt): Soc alGraphServ ce =
    new Soc alGraphServ ce {
      val ex sts = FutureArrow((cl ent.ex sts _).tupled)
      def p ng: Future[Un ] = cl ent.p ng().un 
    }

  case class Conf g(soc alGraphT  out: Durat on, t  outBackoffs: Stream[Durat on]) {

    def apply(svc: Soc alGraphServ ce, ctx: Backend.Context): Soc alGraphServ ce =
      new Soc alGraphServ ce {
        val ex sts: FutureArrow[(Seq[Ex stsRequest], Opt on[RequestContext]), Seq[Ex stsResult]] =
          pol cy("ex sts", soc alGraphT  out, ctx)(svc.ex sts)
        def p ng(): Future[Un ] = svc.p ng()
      }

    pr vate[t ] def pol cy[A, B](
      na : Str ng,
      requestT  out: Durat on,
      ctx: Context
    ): Bu lder[A, B] =
      defaultPol cy(na , requestT  out, retryPol cy, ctx)

    pr vate[t ] def retryPol cy[B]: RetryPol cy[Try[B]] =
      RetryPol cyBu lder.t  outs[Any](t  outBackoffs)
  }

   mpl c  val warmup: Warmup[Soc alGraphServ ce] =
    Warmup[Soc alGraphServ ce]("soc algraphserv ce")(_.p ng)
}

tra  Soc alGraphServ ce {
   mport Soc alGraphServ ce._
  val ex sts: Ex sts
  def p ng(): Future[Un ]
}

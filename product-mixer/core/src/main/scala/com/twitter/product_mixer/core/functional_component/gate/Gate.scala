package com.tw ter.product_m xer.core.funct onal_component.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate.Sk ppedResult
 mport com.tw ter.product_m xer.core.model.common.Component
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.Cand dateP pel neResults
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure. llegalStateFa lure
 mport com.tw ter.product_m xer.core.p pel ne.p pel ne_fa lure.P pel neFa lure
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch

/**
 * A gate controls  f a p pel ne or ot r component  s executed
 *
 * A gate  s mostly controlled by  's `shouldCont nue` funct on - w n t  funct on
 * returns true, execut on Cont nues.
 *
 * Gates also have a opt onal `shouldSk p`- W n   returns
 * true, t n   Cont nue w hout execut ng `ma n`.
 *
 * @tparam Query T  query type that t  gate w ll rece ve as  nput
 *
 * @return A GateResult  ncludes both t  boolean `cont nue` and a spec f c reason. See [[GateResult]] for more
 *          nformat on.
 */

sealed tra  BaseGate[-Query <: P pel neQuery] extends Component {
  overr de val  dent f er: Gate dent f er

  /**
   *  f a shouldSk p returns true, t  gate returns a Sk p(cont nue=true) w hout execut ng
   * t  ma n pred cate.   expect t  to be useful for debugg ng, dogfood ng, etc.
   */
  def shouldSk p(query: Query): St ch[Boolean] = St ch.False

  /**
   * T  ma n pred cate that controls t  gate.  f t  pred cate returns true, t  gate returns Cont nue.
   */
  def shouldCont nue(query: Query): St ch[Boolean]

  /** returns a [[GateResult]] to determ ne w t r a p pel ne should be executed based on `t` */
  f nal def apply(t: Query): St ch[GateResult] = {
    shouldSk p(t).flatMap { sk pResult =>
       f (sk pResult) {
        Sk ppedResult
      } else {
        shouldCont nue(t).map { ma nResult =>
           f (ma nResult) GateResult.Cont nue else GateResult.Stop
        }
      }
    }
  }

  /** Arrow representat on of `t ` [[Gate]] */
  f nal def arrow: Arrow[Query, GateResult] = Arrow(apply)
}

/**
 * A regular Gate wh ch only has access to t  Query typed P pel neQuery. T  can be used anyw re
 * Gates are ava lable.
 *
 * A gate  s mostly controlled by  's `shouldCont nue` funct on - w n t  funct on
 * returns true, execut on Cont nues.
 *
 * Gates also have a opt onal `shouldSk p`- W n   returns
 * true, t n   Cont nue w hout execut ng `ma n`.
 * @tparam Query T  query type that t  gate w ll rece ve as  nput
 *
 * @return A GateResult  ncludes both t  boolean `cont nue` and a spec f c reason. See [[GateResult]] for more
 *          nformat on.
 */
tra  Gate[-Query <: P pel neQuery] extends BaseGate[Query]

/**
 * A Query And Cand date Gate wh ch only has access both to t  Query typed P pel neQuery and t 
 * l st of prev ously fetc d cand dates. T  can be used on dependent cand date p pel nes to
 * make a dec s on on w t r to enable/d sable t m based on prev ous cand dates.
 *
 * A gate  s mostly controlled by  's `shouldCont nue` funct on - w n t  funct on
 * returns true, execut on Cont nues.
 *
 * Gates also have a opt onal `shouldSk p`- W n   returns
 * true, t n   Cont nue w hout execut ng `ma n`.
 *
 * @tparam Query T  query type that t  gate w ll rece ve as  nput
 *
 * @return A GateResult  ncludes both t  boolean `cont nue` and a spec f c reason. See [[GateResult]] for more
 *          nformat on.
 */
tra  QueryAndCand dateGate[-Query <: P pel neQuery] extends BaseGate[Query] {

  /**
   *  f a shouldSk p returns true, t  gate returns a Sk p(cont nue=true) w hout execut ng
   * t  ma n pred cate.   expect t  to be useful for debugg ng, dogfood ng, etc.
   */
  def shouldSk p(query: Query, cand dates: Seq[Cand dateW hDeta ls]): St ch[Boolean] =
    St ch.False

  /**
   * T  ma n pred cate that controls t  gate.  f t  pred cate returns true, t  gate returns Cont nue.
   */
  def shouldCont nue(query: Query, cand dates: Seq[Cand dateW hDeta ls]): St ch[Boolean]

  f nal overr de def shouldSk p(query: Query): St ch[Boolean] = {
    val cand dates = query.features
      .map(_.get(Cand dateP pel neResults)).getOrElse(
        throw P pel neFa lure(
           llegalStateFa lure,
          "Cand date P pel ne Results Feature m ss ng from query features"))
    shouldSk p(query, cand dates)
  }

  f nal overr de def shouldCont nue(query: Query): St ch[Boolean] = {
    val cand dates = query.features
      .map(_.get(Cand dateP pel neResults)).getOrElse(
        throw P pel neFa lure(
           llegalStateFa lure,
          "Cand date P pel ne Results Feature m ss ng from query features"))
    shouldCont nue(query, cand dates)
  }
}

object Gate {
  val Sk ppedResult: St ch[GateResult] = St ch.value(GateResult.Sk pped)
}

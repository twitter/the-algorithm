package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.NullStatsRece ver
 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.follow_recom ndat ons.common.models.F lterReason
 mport com.tw ter.st ch.Arrow
 mport com.tw ter.st ch.St ch

tra  Pred cate[-Q] {

  def apply( em: Q): St ch[Pred cateResult]
  def arrow: Arrow[Q, Pred cateResult] = Arrow.apply(apply)

  def map[K](mapper: K => Q): Pred cate[K] = Pred cate(arrow.contramap(mapper))

  /**
   * c ck t  pred cate results for a batch of  ems for conven ence.
   *
   * mark   as f nal to avo d potent al abuse usage
   */
  f nal def batch( ems: Seq[Q]): St ch[Seq[Pred cateResult]] = {
    t .arrow.traverse( ems)
  }

  /**
   * Syntax sugar for funct ons wh ch take  n 2  nputs as a tuple.
   */
  def apply[Q1, Q2]( em1: Q1,  em2: Q2)( mpl c  ev: ((Q1, Q2)) => Q): St ch[Pred cateResult] = {
    apply(( em1,  em2))
  }

  /**
   * Runs t  pred cates  n sequence. T  returned pred cate w ll return true  ff both t  pred cates return true.
   *  e.    s an AND operat on
   *
   *   short-c rcu  t  evaluat on,  e   don't evaluate t  2nd pred cate  f t  1st  s false
   *
   * @param p pred cate to run  n sequence
   *
   * @return a new pred cate object that represents t  log cal AND of both pred cates
   */
  def andT n[Q1 <: Q](p: Pred cate[Q1]): Pred cate[Q1] = {
    Pred cate({ query: Q1 =>
      apply(query).flatMap {
        case Pred cateResult.Val d => p(query)
        case Pred cateResult. nval d(reasons) => St ch.value(Pred cateResult. nval d(reasons))
      }
    })
  }

  /**
   * Creates a pred cate wh ch runs t  current & g ven pred cate  n sequence.
   * T  returned pred cate w ll return true  f e  r current or g ven pred cate returns true.
   * That  s, g ven pred cate w ll be only run  f current pred cate returns false.
   *
   * @param p pred cate to run  n sequence
   *
   * @return new pred cate object that represents t  log cal OR of both pred cates.
   *          f both are  nval d, t  reason would be t  set of all  nval d reasons.
   */
  def or[Q1 <: Q](p: Pred cate[Q1]): Pred cate[Q1] = {
    Pred cate({ query: Q1 =>
      apply(query).flatMap {
        case Pred cateResult.Val d => St ch.value(Pred cateResult.Val d)
        case Pred cateResult. nval d(reasons) =>
          p(query).flatMap {
            case Pred cateResult.Val d => St ch.value(Pred cateResult.Val d)
            case Pred cateResult. nval d(newReasons) =>
              St ch.value(Pred cateResult. nval d(reasons ++ newReasons))
          }
      }
    })
  }

  /*
   * Runs t  pred cate only  f t  prov ded pred cate  s val d, ot rw se returns val d.
   * */
  def gate[Q1 <: Q](gat ngPred cate: Pred cate[Q1]): Pred cate[Q1] = {
    Pred cate { query: Q1 =>
      gat ngPred cate(query).flatMap { result =>
         f (result == Pred cateResult.Val d) {
          apply(query)
        } else {
          St ch.value(Pred cateResult.Val d)
        }
      }
    }
  }

  def observe(statsRece ver: StatsRece ver): Pred cate[Q] = Pred cate(
    StatsUt l.prof lePred cateResult(t .arrow, statsRece ver))

  def convertToFa lOpenW hResultType(resultType: Pred cateResult): Pred cate[Q] = {
    Pred cate { query: Q =>
      apply(query).handle {
        case _: Except on =>
          resultType
      }

    }
  }

}

class TruePred cate[Q] extends Pred cate[Q] {
  overr de def apply( em: Q): St ch[Pred cateResult] = Pred cate.AlwaysTrueSt ch
}

class FalsePred cate[Q](reason: F lterReason) extends Pred cate[Q] {
  val  nval dResult = St ch.value(Pred cateResult. nval d(Set(reason)))
  overr de def apply( em: Q): St ch[Pred cateResult] =  nval dResult
}

object Pred cate {

  val AlwaysTrueSt ch = St ch.value(Pred cateResult.Val d)

  val NumBatc sStat = "num_batc s_stats"
  val NumBatc sCount = "num_batc s"

  def apply[Q](func: Q => St ch[Pred cateResult]): Pred cate[Q] = new Pred cate[Q] {
    overr de def apply( em: Q): St ch[Pred cateResult] = func( em)

    overr de val arrow: Arrow[Q, Pred cateResult] = Arrow(func)
  }

  def apply[Q](outerArrow: Arrow[Q, Pred cateResult]): Pred cate[Q] = new Pred cate[Q] {
    overr de def apply( em: Q): St ch[Pred cateResult] = arrow( em)

    overr de val arrow: Arrow[Q, Pred cateResult] = outerArrow
  }

  /**
   * G ven so   ems, t  funct on
   * 1. chunks t m up  n groups
   * 2. laz ly appl es a pred cate on each group
   * 3. f lters based on t  pred cate
   * 4. takes f rst numToTake  ems.
   *
   *  f numToTake  s sat sf ed, t n any later pred cates are not called.
   *
   * @param  ems      ems of type Q
   * @param pred cate pred cate that determ nes w t r an  em  s acceptable
   * @param batchS ze batch s ze to call t  pred cate w h
   * @param numToTake max number of  ems to return
   * @param stats stats rece ver
   * @tparam Q type of  em
   *
   * @return a future of K  ems
   */
  def batchF lterTake[Q](
     ems: Seq[Q],
    pred cate: Pred cate[Q],
    batchS ze:  nt,
    numToTake:  nt,
    stats: StatsRece ver
  ): St ch[Seq[Q]] = {

    def take(
       nput:  erator[St ch[Seq[Q]]],
      prev: Seq[Q],
      takeS ze:  nt,
      numOfBatch:  nt
    ): St ch[(Seq[Q],  nt)] = {
       f ( nput.hasNext) {
        val currFut =  nput.next()
        currFut.flatMap { curr =>
          val taken = curr.take(takeS ze)
          val comb ned = prev ++ taken
           f (taken.s ze < takeS ze)
            take( nput, comb ned, takeS ze - taken.s ze, numOfBatch + 1)
          else St ch.value((comb ned, numOfBatch + 1))
        }
      } else {
        St ch.value((prev, numOfBatch))
      }
    }

    val batc d ems =  ems.v ew.grouped(batchS ze)
    val batc dFutures = batc d ems.map { batch =>
      St ch.traverse(batch)(pred cate.apply).map { conds =>
        (batch.z p(conds)).w hF lter(_._2.value).map(_._1)
      }
    }
    take(batc dFutures, N l, numToTake, 0).map {
      case (f ltered: Seq[Q], numOfBatch:  nt) =>
        stats.stat(NumBatc sStat).add(numOfBatch)
        stats.counter(NumBatc sCount). ncr(numOfBatch)
        f ltered
    }
  }

  /**
   * f lter a l st of  ems based on t  pred cate
   *
   * @param  ems a l st of  ems
   * @param pred cate pred cate of t   em
   * @tparam Q  em type
   * @return t  l st of  ems that sat sfy t  pred cate
   */
  def f lter[Q]( ems: Seq[Q], pred cate: Pred cate[Q]): St ch[Seq[Q]] = {
    pred cate.batch( ems).map { results =>
       ems.z p(results).collect {
        case ( em, Pred cateResult.Val d) =>  em
      }
    }
  }

  /**
   * f lter a l st of  ems based on t  pred cate g ven t  target
   *
   * @param target target  em
   * @param  ems a l st of  ems
   * @param pred cate pred cate of t  (target,  em) pa r
   * @tparam Q  em type
   * @return t  l st of  ems that sat sfy t  pred cate g ven t  target
   */
  def f lter[T, Q](target: T,  ems: Seq[Q], pred cate: Pred cate[(T, Q)]): St ch[Seq[Q]] = {
    pred cate.batch( ems.map(  => (target,  ))).map { results =>
       ems.z p(results).collect {
        case ( em, Pred cateResult.Val d) =>  em
      }
    }
  }

  /**
   * Returns a pred cate, w re an ele nt  s true  ff   that ele nt  s true for all  nput pred cates.
   *  e.    s an AND operat on
   *
   * T   s done concurrently.
   *
   * @param pred cates l st of pred cates
   * @tparam Q Type para ter
   *
   * @return new pred cate object that  s t  log cal "and" of t   nput pred cates
   */
  def andConcurrently[Q](pred cates: Seq[Pred cate[Q]]): Pred cate[Q] = {
    Pred cate { query: Q =>
      St ch.traverse(pred cates)(p => p(query)).map { pred cateResults =>
        val all nval d = pred cateResults
          .collect {
            case Pred cateResult. nval d(reason) =>
              reason
          }
         f (all nval d. sEmpty) {
          Pred cateResult.Val d
        } else {
          val all nval dReasons = all nval d.reduce(_ ++ _)
          Pred cateResult. nval d(all nval dReasons)
        }
      }
    }
  }
}

/**
 * appl es t  underly ng pred cate w n t  param  s on.
 */
abstract class GatedPred cateBase[Q](
  underly ngPred cate: Pred cate[Q],
  stats: StatsRece ver = NullStatsRece ver)
    extends Pred cate[Q] {
  def gate( em: Q): Boolean

  val underly ngPred cateTotal = stats.counter("underly ng_total")
  val underly ngPred cateVal d = stats.counter("underly ng_val d")
  val underly ngPred cate nval d = stats.counter("underly ng_ nval d")
  val notGatedCounter = stats.counter("not_gated")

  val Val dSt ch: St ch[Pred cateResult.Val d.type] = St ch.value(Pred cateResult.Val d)

  overr de def apply( em: Q): St ch[Pred cateResult] = {
     f (gate( em)) {
      underly ngPred cateTotal. ncr()
      underly ngPred cate( em)
    } else {
      notGatedCounter. ncr()
      Val dSt ch
    }
  }

}

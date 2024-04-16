package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.selector. nsertRandomPos  onResults.random nd ces
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope.Part  onedCand dates
 mport com.tw ter.product_m xer.core.funct onal_component.conf gap .Stat cParam
 mport com.tw ter.product_m xer.core.funct onal_component.selector.Selector
 mport com.tw ter.product_m xer.core.funct onal_component.selector.SelectorResult
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.t  l nes.conf gap .Param

 mport scala.collect on.mutable
 mport scala.ut l.Random

object  nsertRandomPos  onResults {

  /**
   *  erator conta n ng random  ndex bet en `start ndex` and `end ndex` + `n`
   * w re `n`  s t  number of t  s `next` has been called on t   erator
   * w hout dupl cat on
   */
  pr vate[selector] def random nd ces(
    resultLength:  nt,
    start ndex:  nt,
    end ndex:  nt,
    random: Random
  ):  erator[ nt] = {

    /** exclus ve because [[Random.next nt]]'s bound  s exclus ve */
    val  ndexUpperBound = Math.m n(end ndex, resultLength)

    /**
     * keep track of t  ava lable  nd ces, `O(n)` space w re `n`  s `m n(end ndex, resultLength) - max(start ndex, 0)`
     * t  ensures fa rness wh ch dupl cate  nd ces could ot rw se skew
     */
    val values = mutable.ArrayBuffer(Math.max(0, start ndex) to  ndexUpperBound: _*)

    /**
     *  erator that starts at 1 above t  last val d  ndex, [[ ndexUpperBound]] + 1, and  ncre nts monoton cally
     * represent ng t  new h g st  ndex poss ble  n t  results for t  next call
     */
     erator
      .from( ndexUpperBound + 1)
      .map {  ndexUpperBound =>
        /**
         * p ck a random  ndex-to- nsert-cand date- nto-results from [[values]] replac ng t  value at
         * t  chosen  ndex w h t  new h g st  ndex from [[ ndexUpperBound]], t  results  n
         * constant t   for p ck ng t  random  ndex and add ng t  new h g st val d  ndex  nstead
         * of remov ng t   em from t  m ddle and append ng t  new, wh ch would be `O(n)` to sh ft
         * all  nd ces after t  removal po nt
         */
        val   = random.next nt(values.length)
        val random ndexToUse = values( )
        // overr de t  value at   w h t  new `upperBoundExclus ve` to account for t  new  ndex value  n t  next  erat on
        values( ) =  ndexUpperBound

        random ndexToUse
      }
  }
}

sealed tra   nsertedCand dateOrder

/**
 * Cand dates from t  `rema n ngCand dates` s de w ll be  nserted  n a random order  nto t  `result`
 *
 * @example  f  nsert ng `[ x, y, z ]`  nto t  `result` t n t  relat ve pos  ons of `x`, `y` and `z`
 *          to each ot r  s random, e.g. `y` could co  before `x`  n t  result.
 */
case object UnstableOrder ngOf nsertedCand dates extends  nsertedCand dateOrder

/**
 * Cand dates from t  `rema n ngCand dates` s de w ll be  nserted  n t  r or g nal order  nto t  `result`
 *
 * @example  f  nsert ng `[ x, y, z ]`  nto t  `result` t n t  relat ve pos  ons of `x`, `y` and `z`
 *          to each ot r w ll rema n t  sa , e.g. `x`  s always before `y`  s always before `z`  n t  f nal result
 */
case object StableOrder ngOf nsertedCand dates extends  nsertedCand dateOrder

/**
 *  nsert `rema n ngCand dates`  nto a random pos  on bet en t  spec f ed  nd ces ( nclus ve)
 *
 * @example let `result` = `[ a, b, c, d ]` and   want to  nsert randomly `[ x, y, z ]`
 *          w h `start ndex` =  1, `end ndex` = 2, and [[UnstableOrder ngOf nsertedCand dates]].
 *            can expect a result that looks l ke `[ a, ... , d ]` w re `...`  s
 *          a random  nsert on of `x`, `y`, and `z`  nto  `[ b, c ]`. So t  could look l ke
 *          `[ a, y, b, x, c, z, d ]`, note that t   nserted ele nts are randomly d str buted
 *          among t  ele nts that  re or g nally bet en t  spec f ed  nd ces.
 *          T  funct ons l ke tak ng a sl ce of t  or g nal `result` bet en t   nd ces,
 *          e.g. `[ b, c ]`, t n randomly  nsert ng  nto t  sl ce, e.g. `[ y, b, x, c, z ]`,
 *          before reassembl ng t  `result`, e.g. `[ a ] ++ [ y, b, x, c, z ] ++ [ d ]`.
 *
 * @example let `result` = `[ a, b, c, d ]` and   want to  nsert randomly `[ x, y, z ]`
 *          w h `start ndex` =  1, `end ndex` = 2, and [[StableOrder ngOf nsertedCand dates]].
 *            can expect a result that looks so th ng l ke `[ a, x, b, y, c, z, d ]`,
 *          w re `x`  s before `y` wh ch  s before `z`
 *
 * @param start ndex an  nclus ve  ndex wh ch starts t  range w re t  cand dates w ll be  nserted
 * @param end ndex an  nclus ve  ndex wh ch ends t  range w re t  cand dates w ll be  nserted
 */
case class  nsertRandomPos  onResults[-Query <: P pel neQuery](
  p pel neScope: Cand dateScope,
  rema n ngCand dateOrder:  nsertedCand dateOrder,
  start ndex: Param[ nt] = Stat cParam(0),
  end ndex: Param[ nt] = Stat cParam( nt.MaxValue),
  random: Random = new Random(0))
    extends Selector[Query] {

  overr de def apply(
    query: Query,
    rema n ngCand dates: Seq[Cand dateW hDeta ls],
    result: Seq[Cand dateW hDeta ls]
  ): SelectorResult = {

    val Part  onedCand dates(cand dates nScope, cand datesOutOfScope) =
      p pel neScope.part  on(rema n ngCand dates)

    val random ndex erator = {
      val random ndex erator =
        random nd ces(result.length, query.params(start ndex), query.params(end ndex), random)

      rema n ngCand dateOrder match {
        case StableOrder ngOf nsertedCand dates =>
          random ndex erator.take(cand dates nScope.length).toSeq.sorted. erator
        case UnstableOrder ngOf nsertedCand dates =>
          random ndex erator
      }
    }

    val  rgedResult = Dynam cPos  onSelector. rgeBy ndex ntoResult(
      cand datesTo nsertBy ndex = random ndex erator.z p(cand dates nScope. erator).toSeq,
      result = result,
      Dynam cPos  onSelector.Absolute nd ces
    )

    SelectorResult(rema n ngCand dates = cand datesOutOfScope, result =  rgedResult)
  }
}

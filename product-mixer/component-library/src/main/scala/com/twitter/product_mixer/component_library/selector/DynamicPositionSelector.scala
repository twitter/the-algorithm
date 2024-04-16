package com.tw ter.product_m xer.component_l brary.selector

pr vate[selector] object Dynam cPos  onSelector {

  sealed tra   ndexType
  case object Relat ve nd ces extends  ndexType
  case object Absolute nd ces extends  ndexType

  /**
   * G ven an ex st ng `result` seq,  nserts cand dates from `cand datesTo nsertBy ndex`  nto t  `result` 1-by-1 w h
   * t  prov ded  ndex be ng t   ndex relat ve to t  `result`  f g ven [[Relat ve nd ces]] or
   * absolute  ndex  f g ven [[Absolute nd ces]] (exclud ng dupl cate  nsert ons at an  ndex, see below).
   *
   *  nd ces below 0 are added to t  front and  nd ces > t  length are added to t  end
   *
   * @note  f mult ple cand dates ex st w h t  sa   ndex, t y are  nserted  n t  order wh ch t y appear and only count
   *       as a s ngle ele nt w h regards to t  absolute  ndex values, see t  example below
   *
   * @example w n us ng [[Relat ve nd ces]] {{{
   *           rgeBy ndex ntoResult(
   *          Seq(
   *            0 -> "a",
   *            0 -> "b",
   *            0 -> "c",
   *            1 -> "e",
   *            2 -> "g",
   *            2 -> "h"),
   *          Seq(
   *            "D",
   *            "F"
   *          ),
   *          Relat ve nd ces) == Seq(
   *            "a",
   *            "b",
   *            "c",
   *            "D",
   *            "e",
   *            "F",
   *            "g",
   *            "h"
   *          )
   * }}}
   *
   * @example w n us ng [[Absolute nd ces]] {{{
   *           rgeBy ndex ntoResult(
   *          Seq(
   *            0 -> "a",
   *            0 -> "b",
   *            1 -> "c",
   *            3 -> "e",
   *            5 -> "g",
   *            6 -> "h"),
   *          Seq(
   *            "D",
   *            "F"
   *          ),
   *          Absolute nd ces) == Seq(
   *            "a", //  ndex 0, "a" and "b" toget r only count as 1 ele nt w h regards to  ndexes because t y have dupl cate  nsert on po nts
   *            "b", //  ndex 0
   *            "c", //  ndex 1
   *            "D", //  ndex 2
   *            "e", //  ndex 3
   *            "F", //  ndex 4
   *            "g", //  ndex 5
   *            "h" //  ndex 6
   *          )
   * }}}
   */
  def  rgeBy ndex ntoResult[T]( // gener c on `T` to s mpl fy un  test ng
    cand datesTo nsertBy ndex: Seq[( nt, T)],
    result: Seq[T],
     ndexType:  ndexType
  ): Seq[T] = {
    val pos  onAndCand dateL st = cand datesTo nsertBy ndex.sortW h {
      case (( ndexLeft:  nt, _), ( ndexR ght:  nt, _)) =>
         ndexLeft <  ndexR ght // order by des red absolute  ndex ascend ng
    }

    //  rge result and pos  onAndCand dateL st  nto resultUpdated wh le mak ng sure that t  entr es
    // from t  pos  onAndCand dateL st are  nserted at t  r ght  ndex.
    val resultUpdated = Seq.newBu lder[T]
    resultUpdated.s zeH nt(result.s ze + pos  onAndCand dateL st.s ze)

    var currentResult ndex = 0
    val  nputResult erator = result. erator
    val pos  onAndCand date erator = pos  onAndCand dateL st. erator.buffered
    var prev ous nsertPos  on: Opt on[ nt] = None

    wh le ( nputResult erator.nonEmpty && pos  onAndCand date erator.nonEmpty) {
      pos  onAndCand date erator. ad match {
        case (next nsert onPos  on, nextCand dateTo nsert)
             f prev ous nsertPos  on.conta ns(next nsert onPos  on) =>
          //  nsert ng mult ple cand dates at t  sa   ndex
          resultUpdated += nextCand dateTo nsert
          // do not  ncre nt any  nd ces, but  nsert t  cand date and advance to t  next cand date
          pos  onAndCand date erator.next()

        case (next nsert onPos  on, nextCand dateTo nsert)
             f currentResult ndex >= next nsert onPos  on =>
          //  nsert ng a cand date at a new  ndex
          // add cand date to t  results
          resultUpdated += nextCand dateTo nsert
          // save t  pos  on of t   nserted ele nt to handle dupl cate  ndex  nsert ons
          prev ous nsertPos  on = So (next nsert onPos  on)
          // advance to next cand date
          pos  onAndCand date erator.next()
           f ( ndexType == Absolute nd ces) {
            //  f t   nd ces are absolute,  nstead of relat ve to t  or g nal `result`   need to
            // count t   nsert ons of cand dates  nto t  results towards t  `currentResult ndex`
            currentResult ndex += 1
          }
        case _ =>
          // no cand date to  nsert by  ndex so use t  cand dates from t  result and  ncre nt t   ndex
          resultUpdated +=  nputResult erator.next()
          currentResult ndex += 1
      }
    }
    // one of t   erators  s empty, so append t  rema n ng cand dates  n order to t  end
    resultUpdated ++= pos  onAndCand date erator.map { case (_, cand date) => cand date }
    resultUpdated ++=  nputResult erator

    resultUpdated.result()
  }
}

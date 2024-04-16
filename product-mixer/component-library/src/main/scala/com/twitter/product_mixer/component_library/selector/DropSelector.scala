package com.tw ter.product_m xer.component_l brary.selector

 mport com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date
 mport com.tw ter.product_m xer.core.funct onal_component.common.AllP pel nes
 mport com.tw ter.product_m xer.core.funct onal_component.common.Cand dateScope
 mport com.tw ter.product_m xer.core.model.common.presentat on.Cand dateW hDeta ls
 mport com.tw ter.product_m xer.core.model.common.presentat on. emCand dateW hDeta ls
 mport scala.collect on.mutable

pr vate[selector] object DropSelector {

  /**
   *  dent fy and  rge dupl cates us ng t  suppl ed key extract on and  rger funct ons. By default
   * t  w ll keep only t  f rst  nstance of a cand date  n t  `cand date` as determ ned by compar ng
   * t  conta ned cand date  D and class type. Subsequent match ng  nstances w ll be dropped. For
   * more deta ls, see DropSelector#dropDupl cates.
   *
   * @note [[com.tw ter.product_m xer.component_l brary.model.cand date.CursorCand date]] are  gnored.
   * @note [[com.tw ter.product_m xer.core.model.common.presentat on.ModuleCand dateW hDeta ls]] are  gnored.
   *
   * @param cand dates wh ch may have ele nts to drop
   * @param dupl cat onKey how to generate a key for a cand date for  dent fy ng dupl cates
   * @param  rgeStrategy how to  rge two cand dates w h t  sa  key (by default p ck t  f rst one)
   */
  def dropDupl cates[Cand date <: Cand dateW hDeta ls, Key](
    p pel neScope: Cand dateScope,
    cand dates: Seq[Cand date],
    dupl cat onKey: Dedupl cat onKey[Key],
     rgeStrategy: Cand date rgeStrategy
  ): Seq[Cand date] = {
    val seenCand datePos  ons = mutable.HashMap[Key,  nt]()
    //   assu  that, most of t  t  , most cand dates aren't dupl cates so t  result Seq w ll be
    // approx mately t  s ze of t  cand dates Seq.
    val dedupl catedCand dates = new mutable.ArrayBuffer[Cand date](cand dates.length)

    for (cand date <- cand dates) {
      cand date match {

        // cand date  s from one of t  P pel nes t  selector appl es to and  s not a CursorCand date
        case  em:  emCand dateW hDeta ls
             f p pel neScope.conta ns( em) &&
              ! em.cand date. s nstanceOf[CursorCand date] =>
          val key = dupl cat onKey( em)

          // Perform a  rge  f t  cand date has been seen already
           f (seenCand datePos  ons.conta ns(key)) {
            val cand date ndex = seenCand datePos  ons(key)

            // Safe because only  emCand dateW hDeta ls are added to seenCand datePos  ons so
            // seenCand datePos  ons(key) *must* po nt to an  emCand dateW hDeta ls
            val or g nalCand date =
              dedupl catedCand dates(cand date ndex).as nstanceOf[ emCand dateW hDeta ls]

            dedupl catedCand dates.update(
              cand date ndex,
               rgeStrategy(or g nalCand date,  em).as nstanceOf[Cand date])
          } else {
            // Ot rw se add a new entry to t  l st of kept cand dates and update   map to track
            // t  new  ndex
            dedupl catedCand dates.append( em.as nstanceOf[Cand date])
            seenCand datePos  ons.update(key, dedupl catedCand dates.length - 1)
          }
        case  em => dedupl catedCand dates.append( em)
      }
    }

    dedupl catedCand dates
  }

  /**
   * Takes `cand dates` from all [[Cand dateW hDeta ls.s ce]]s but only `cand dates`  n t  prov ded
   * `p pel neScope` are counted towards t  `max` non-cursor cand dates are  ncluded.
   *
   * @param max t  max mum number of non-cursor cand dates from t  prov ded `p pel neScope` to return
   * @param cand dates a sequence of cand dates wh ch may have ele nts dropped
   * @param p pel neScope t  scope of wh ch `cand dates` should count towards t  `max`
   */
  def takeUnt l[Cand date <: Cand dateW hDeta ls](
    max:  nt,
    cand dates: Seq[Cand date],
    p pel neScope: Cand dateScope = AllP pel nes
  ): Seq[Cand date] = {
    val resultsBu lder = Seq.newBu lder[Cand date]
    resultsBu lder.s zeH nt(cand dates)

    cand dates.foldLeft(0) {
      case (
            count,
            cand date @  emCand dateW hDeta ls(_: CursorCand date, _, _)
          ) =>
        // keep cursors, not  ncluded  n t  `count`
        resultsBu lder += cand date.as nstanceOf[Cand date]
        count

      case (count, cand date)  f !p pel neScope.conta ns(cand date) =>
        // keep cand dates that don't match t  prov ded `p pel neScope`, not  ncluded  n t  `count`
        resultsBu lder += cand date
        count

      case (count, cand date)  f count < max =>
        // keep cand dates  f t res space and  ncre nt t  `count`
        resultsBu lder += cand date
        count + 1

      case (dropCurrentCand date, _) =>
        // drop non-cursor cand date because t res no space left
        dropCurrentCand date
    }
    resultsBu lder.result()
  }
}

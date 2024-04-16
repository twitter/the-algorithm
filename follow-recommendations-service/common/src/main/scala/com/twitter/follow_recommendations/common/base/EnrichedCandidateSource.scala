package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.product_m xer.core.funct onal_component.cand date_s ce.Cand dateS ce
 mport com.tw ter.st ch.St ch
 mport com.tw ter.ut l.Durat on
 mport com.tw ter.ut l.T  outExcept on
 mport scala.language. mpl c Convers ons

class Enr c dCand dateS ce[Target, Cand date](or g nal: Cand dateS ce[Target, Cand date]) {

  /**
   * Gate t  cand date s ce based on t  Pred cate of target.
   *   returns results only  f t  pred cate returns Val d.
   *
   * @param pred cate
   * @return
   */
  def gate(pred cate: Pred cate[Target]): Cand dateS ce[Target, Cand date] = {
    throw new UnsupportedOperat onExcept on()
  }

  def observe(statsRece ver: StatsRece ver): Cand dateS ce[Target, Cand date] = {
    val or g nal dent f er = or g nal. dent f er
    val stats = statsRece ver.scope(or g nal dent f er.na )
    new Cand dateS ce[Target, Cand date] {
      val  dent f er = or g nal dent f er
      overr de def apply(target: Target): St ch[Seq[Cand date]] = {
        StatsUt l.prof leSt chSeqResults[Cand date](or g nal(target), stats)
      }
    }
  }

  /**
   * Map target type  nto new target type (1 to opt onal mapp ng)
   */
  def st chMapKey[Target2](
    targetMapper: Target2 => St ch[Opt on[Target]]
  ): Cand dateS ce[Target2, Cand date] = {
    val targetsMapper: Target2 => St ch[Seq[Target]] = { target =>
      targetMapper(target).map(_.toSeq)
    }
    st chMapKeys(targetsMapper)
  }

  /**
   * Map target type  nto new target type (1 to many mapp ng)
   */
  def st chMapKeys[Target2](
    targetMapper: Target2 => St ch[Seq[Target]]
  ): Cand dateS ce[Target2, Cand date] = {
    new Cand dateS ce[Target2, Cand date] {
      val  dent f er = or g nal. dent f er
      overr de def apply(target: Target2): St ch[Seq[Cand date]] = {
        for {
          mappedTargets <- targetMapper(target)
          results <- St ch.traverse(mappedTargets)(or g nal(_))
        } y eld results.flatten
      }
    }
  }

  /**
   * Map target type  nto new target type (1 to many mapp ng)
   */
  def mapKeys[Target2](
    targetMapper: Target2 => Seq[Target]
  ): Cand dateS ce[Target2, Cand date] = {
    val st chMapper: Target2 => St ch[Seq[Target]] = { target =>
      St ch.value(targetMapper(target))
    }
    st chMapKeys(st chMapper)
  }

  /**
   * Map cand date types to new type based on cand dateMapper
   */
  def mapValues[Cand date2](
    cand dateMapper: Cand date => St ch[Opt on[Cand date2]]
  ): Cand dateS ce[Target, Cand date2] = {

    new Cand dateS ce[Target, Cand date2] {
      val  dent f er = or g nal. dent f er
      overr de def apply(target: Target): St ch[Seq[Cand date2]] = {
        or g nal(target).flatMap { cand dates =>
          val results = St ch.traverse(cand dates)(cand dateMapper(_))
          results.map(_.flatten)
        }
      }
    }
  }

  /**
   * Map cand date types to new type based on cand dateMapper
   */
  def mapValue[Cand date2](
    cand dateMapper: Cand date => Cand date2
  ): Cand dateS ce[Target, Cand date2] = {
    val st chMapper: Cand date => St ch[Opt on[Cand date2]] = { c =>
      St ch.value(So (cand dateMapper(c)))
    }
    mapValues(st chMapper)
  }

  /**
   * T   thod wraps t  cand date s ce  n a des gnated t  out so that a s ngle cand date
   * s ce does not result  n a t  out for t  ent re flow
   */
  def w h n(
    cand dateT  out: Durat on,
    statsRece ver: StatsRece ver
  ): Cand dateS ce[Target, Cand date] = {
    val or g nal dent f er = or g nal. dent f er
    val t  outCounter =
      statsRece ver.counter(or g nal dent f er.na , "t  out")

    new Cand dateS ce[Target, Cand date] {
      val  dent f er = or g nal dent f er
      overr de def apply(target: Target): St ch[Seq[Cand date]] = {
        or g nal
          .apply(target)
          .w h n(cand dateT  out)(com.tw ter.f nagle.ut l.DefaultT  r)
          .rescue {
            case _: T  outExcept on =>
              t  outCounter. ncr()
              St ch.N l
          }
      }
    }
  }

  def fa lOpenW h n(
    cand dateT  out: Durat on,
    statsRece ver: StatsRece ver
  ): Cand dateS ce[Target, Cand date] = {
    val or g nal dent f er = or g nal. dent f er
    val t  outCounter =
      statsRece ver.counter(or g nal dent f er.na , "t  out")

    new Cand dateS ce[Target, Cand date] {
      val  dent f er = or g nal dent f er
      overr de def apply(target: Target): St ch[Seq[Cand date]] = {
        or g nal
          .apply(target)
          .w h n(cand dateT  out)(com.tw ter.f nagle.ut l.DefaultT  r)
          .handle {
            case _: T  outExcept on =>
              t  outCounter. ncr()
              Seq.empty
            case e: Except on =>
              statsRece ver
                .scope("cand date_s ce_error").scope(or g nal dent f er.na ).counter(
                  e.getClass.getS mpleNa ). ncr
              Seq.empty
          }
      }
    }
  }
}

object Enr c dCand dateS ce {
   mpl c  def toEnr c d[K, V](or g nal: Cand dateS ce[K, V]): Enr c dCand dateS ce[K, V] =
    new Enr c dCand dateS ce(or g nal)
}

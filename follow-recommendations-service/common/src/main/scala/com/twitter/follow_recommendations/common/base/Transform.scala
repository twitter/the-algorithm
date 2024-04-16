package com.tw ter.follow_recom ndat ons.common.base

 mport com.tw ter.f nagle.stats.StatsRece ver
 mport com.tw ter.st ch.St ch
 mport com.tw ter.t  l nes.conf gap .HasParams
 mport com.tw ter.t  l nes.conf gap .Param

/**
 * transform a or a l st of cand date for target T
 *
 * @tparam T target type
 * @tparam C cand date type
 */
tra  Transform[-T, C] {

  //   need to  mple nt at least one of t  two  thods  re.
  def transform em(target: T,  em: C): St ch[C] = {
    transform(target, Seq( em)).map(_. ad)
  }

  def transform(target: T,  ems: Seq[C]): St ch[Seq[C]]

  def mapTarget[T2](mapper: T2 => T): Transform[T2, C] = {
    val or g nal = t 
    new Transform[T2, C] {
      overr de def transform em(target: T2,  em: C): St ch[C] = {
        or g nal.transform em(mapper(target),  em)
      }
      overr de def transform(target: T2,  ems: Seq[C]): St ch[Seq[C]] = {
        or g nal.transform(mapper(target),  ems)
      }
    }
  }

  /**
   * sequent al compos  on.   execute t ' transform f rst, follo d by t  ot r's transform
   */
  def andT n[T1 <: T](ot r: Transform[T1, C]): Transform[T1, C] = {
    val or g nal = t 
    new Transform[T1, C] {
      overr de def transform em(target: T1,  em: C): St ch[C] =
        or g nal.transform em(target,  em).flatMap(ot r.transform em(target, _))
      overr de def transform(target: T1,  ems: Seq[C]): St ch[Seq[C]] =
        or g nal.transform(target,  ems).flatMap(ot r.transform(target, _))
    }
  }

  def observe(statsRece ver: StatsRece ver): Transform[T, C] = {
    val or g nalTransform = t 
    new Transform[T, C] {
      overr de def transform(target: T,  ems: Seq[C]): St ch[Seq[C]] = {
        statsRece ver.counter(Transform. nputCand datesCount). ncr( ems.s ze)
        statsRece ver.stat(Transform. nputCand datesStat).add( ems.s ze)
        StatsUt l.prof leSt chSeqResults(or g nalTransform.transform(target,  ems), statsRece ver)
      }

      overr de def transform em(target: T,  em: C): St ch[C] = {
        statsRece ver.counter(Transform. nputCand datesCount). ncr()
        StatsUt l.prof leSt ch(or g nalTransform.transform em(target,  em), statsRece ver)
      }
    }
  }
}

tra  GatedTransform[T <: HasParams, C] extends Transform[T, C] {
  def gated(param: Param[Boolean]): Transform[T, C] = {
    val or g nal = t 
    (target: T,  ems: Seq[C]) => {
       f (target.params(param)) {
        or g nal.transform(target,  ems)
      } else {
        St ch.value( ems)
      }
    }
  }
}

object Transform {
  val  nputCand datesCount = " nput_cand dates"
  val  nputCand datesStat = " nput_cand dates_stat"
}

class  dent yTransform[T, C] extends Transform[T, C] {
  overr de def transform(target: T,  ems: Seq[C]): St ch[Seq[C]] = St ch.value( ems)
}

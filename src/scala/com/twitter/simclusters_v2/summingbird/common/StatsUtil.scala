package com.tw ter.s mclusters_v2.summ ngb rd.common

 mport com.tw ter.summ ngb rd.{Counter, Group, Na , Platform, Producer}
 mport com.tw ter.summ ngb rd.opt on.Job d

object StatsUt l {

  // for add ng stats  n Producer.
  // t  enables us to add new stats by just call ng producer.observer("na ")
   mpl c  class Enr c dProducer[P <: Platform[P], T](
    producer: Producer[P, T]
  )(
     mpl c  job d: Job d) {
    def observe(counter: Str ng): Producer[P, T] = {
      val stat = Counter(Group(job d.get), Na (counter))
      producer.map { v =>
        stat. ncr()
        v
      }
    }
  }
}

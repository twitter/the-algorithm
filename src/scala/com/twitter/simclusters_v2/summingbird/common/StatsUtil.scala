package com.twitter.simclusters_v2.summingbird.common

import com.twitter.summingbird.{Counter, Group, Name, Platform, Producer}
import com.twitter.summingbird.option.JobId

object StatsUtil {

  // for adding stats in Producer.
  // this enables us to add new stats by just calling producer.observer("name")
  implicit class EnrichedProducer[P <: Platform[P], T](
    producer: Producer[P, T]
  )(
    implicit jobId: JobId) {
    def observe(counter: String): Producer[P, T] = {
      val stat = Counter(Group(jobId.get), Name(counter))
      producer.map { v =>
        stat.incr()
        v
      }
    }
  }
}

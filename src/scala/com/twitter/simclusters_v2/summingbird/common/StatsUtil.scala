packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.summingbird.{Countelonr, Group, Namelon, Platform, Producelonr}
import com.twittelonr.summingbird.option.JobId

objelonct StatsUtil {

  // for adding stats in Producelonr.
  // this elonnablelons us to add nelonw stats by just calling producelonr.obselonrvelonr("namelon")
  implicit class elonnrichelondProducelonr[P <: Platform[P], T](
    producelonr: Producelonr[P, T]
  )(
    implicit jobId: JobId) {
    delonf obselonrvelon(countelonr: String): Producelonr[P, T] = {
      val stat = Countelonr(Group(jobId.gelont), Namelon(countelonr))
      producelonr.map { v =>
        stat.incr()
        v
      }
    }
  }
}

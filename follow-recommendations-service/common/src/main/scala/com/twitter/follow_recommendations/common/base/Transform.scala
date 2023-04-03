packagelon com.twittelonr.follow_reloncommelonndations.common.baselon

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.timelonlinelons.configapi.HasParams
import com.twittelonr.timelonlinelons.configapi.Param

/**
 * transform a or a list of candidatelon for targelont T
 *
 * @tparam T targelont typelon
 * @tparam C candidatelon typelon
 */
trait Transform[-T, C] {

  // you nelonelond to implelonmelonnt at lelonast onelon of thelon two melonthods helonrelon.
  delonf transformItelonm(targelont: T, itelonm: C): Stitch[C] = {
    transform(targelont, Selonq(itelonm)).map(_.helonad)
  }

  delonf transform(targelont: T, itelonms: Selonq[C]): Stitch[Selonq[C]]

  delonf mapTargelont[T2](mappelonr: T2 => T): Transform[T2, C] = {
    val original = this
    nelonw Transform[T2, C] {
      ovelonrridelon delonf transformItelonm(targelont: T2, itelonm: C): Stitch[C] = {
        original.transformItelonm(mappelonr(targelont), itelonm)
      }
      ovelonrridelon delonf transform(targelont: T2, itelonms: Selonq[C]): Stitch[Selonq[C]] = {
        original.transform(mappelonr(targelont), itelonms)
      }
    }
  }

  /**
   * selonquelonntial composition. welon elonxeloncutelon this' transform first, followelond by thelon othelonr's transform
   */
  delonf andThelonn[T1 <: T](othelonr: Transform[T1, C]): Transform[T1, C] = {
    val original = this
    nelonw Transform[T1, C] {
      ovelonrridelon delonf transformItelonm(targelont: T1, itelonm: C): Stitch[C] =
        original.transformItelonm(targelont, itelonm).flatMap(othelonr.transformItelonm(targelont, _))
      ovelonrridelon delonf transform(targelont: T1, itelonms: Selonq[C]): Stitch[Selonq[C]] =
        original.transform(targelont, itelonms).flatMap(othelonr.transform(targelont, _))
    }
  }

  delonf obselonrvelon(statsReloncelonivelonr: StatsReloncelonivelonr): Transform[T, C] = {
    val originalTransform = this
    nelonw Transform[T, C] {
      ovelonrridelon delonf transform(targelont: T, itelonms: Selonq[C]): Stitch[Selonq[C]] = {
        statsReloncelonivelonr.countelonr(Transform.InputCandidatelonsCount).incr(itelonms.sizelon)
        statsReloncelonivelonr.stat(Transform.InputCandidatelonsStat).add(itelonms.sizelon)
        StatsUtil.profilelonStitchSelonqRelonsults(originalTransform.transform(targelont, itelonms), statsReloncelonivelonr)
      }

      ovelonrridelon delonf transformItelonm(targelont: T, itelonm: C): Stitch[C] = {
        statsReloncelonivelonr.countelonr(Transform.InputCandidatelonsCount).incr()
        StatsUtil.profilelonStitch(originalTransform.transformItelonm(targelont, itelonm), statsReloncelonivelonr)
      }
    }
  }
}

trait GatelondTransform[T <: HasParams, C] elonxtelonnds Transform[T, C] {
  delonf gatelond(param: Param[Boolelonan]): Transform[T, C] = {
    val original = this
    (targelont: T, itelonms: Selonq[C]) => {
      if (targelont.params(param)) {
        original.transform(targelont, itelonms)
      } elonlselon {
        Stitch.valuelon(itelonms)
      }
    }
  }
}

objelonct Transform {
  val InputCandidatelonsCount = "input_candidatelons"
  val InputCandidatelonsStat = "input_candidatelons_stat"
}

class IdelonntityTransform[T, C] elonxtelonnds Transform[T, C] {
  ovelonrridelon delonf transform(targelont: T, itelonms: Selonq[C]): Stitch[Selonq[C]] = Stitch.valuelon(itelonms)
}

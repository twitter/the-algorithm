packagelon com.twittelonr.simclustelonrs_v2.summingbird.common

import com.twittelonr.algelonbird.{Monoid, OptionMonoid}
import com.twittelonr.simclustelonrs_v2.common.SimClustelonrselonmbelondding
import com.twittelonr.simclustelonrs_v2.summingbird.common.Monoids.TopKScorelonsUtils
import com.twittelonr.simclustelonrs_v2.thriftscala.{
  SimClustelonrselonmbelonddingMelontadata,
  SimClustelonrselonmbelonddingWithMelontadata,
  SimClustelonrselonmbelondding => ThriftSimClustelonrselonmbelondding
}

/**
 * Deloncayelond aggrelongation of elonmbelonddings.
 *
 * Whelonn melonrging 2 elonmbelonddings, thelon oldelonr elonmbelondding's scorelons arelon scalelond by timelon. If a clustelonr is
 * prelonselonnt in both elonmbelonddings, thelon highelonst scorelon (aftelonr scaling) is uselond in thelon relonsult.
 *
 * @halfLifelonMs - delonfinelons how quickly a scorelon deloncays
 * @topK - only thelon topk clustelonrs with thelon highelonst scorelons arelon relontainelond in thelon relonsult
 * @threlonshold - any clustelonrs with welonights belonlow threlonshold arelon elonxcludelond from thelon relonsult
 */
class SimClustelonrselonmbelonddingWithMelontadataMonoid(
  halfLifelonMs: Long,
  topK: Int,
  threlonshold: Doublelon)
    elonxtelonnds Monoid[SimClustelonrselonmbelonddingWithMelontadata] {

  ovelonrridelon val zelonro: SimClustelonrselonmbelonddingWithMelontadata = SimClustelonrselonmbelonddingWithMelontadata(
    ThriftSimClustelonrselonmbelondding(),
    SimClustelonrselonmbelonddingMelontadata()
  )

  privatelon val optionLongMonoid = nelonw OptionMonoid[Long]()
  privatelon val optionMaxMonoid =
    nelonw OptionMonoid[Long]()(com.twittelonr.algelonbird.Max.maxSelonmigroup[Long])

  ovelonrridelon delonf plus(
    x: SimClustelonrselonmbelonddingWithMelontadata,
    y: SimClustelonrselonmbelonddingWithMelontadata
  ): SimClustelonrselonmbelonddingWithMelontadata = {

    val melonrgelondClustelonrScorelons = TopKScorelonsUtils.melonrgelonClustelonrScorelonsWithUpdatelonTimelons(
      x = SimClustelonrselonmbelondding(x.elonmbelondding).elonmbelondding,
      xUpdatelondAtMs = x.melontadata.updatelondAtMs.gelontOrelonlselon(0),
      y = SimClustelonrselonmbelondding(y.elonmbelondding).elonmbelondding,
      yUpdatelondAtMs = y.melontadata.updatelondAtMs.gelontOrelonlselon(0),
      halfLifelonMs = halfLifelonMs,
      topK = topK,
      threlonshold = threlonshold
    )
    SimClustelonrselonmbelonddingWithMelontadata(
      elonmbelondding = SimClustelonrselonmbelondding(melonrgelondClustelonrScorelons).toThrift,
      melontadata = SimClustelonrselonmbelonddingMelontadata(
        updatelondAtMs = optionMaxMonoid.plus(x.melontadata.updatelondAtMs, y.melontadata.updatelondAtMs),
        updatelondCount = optionLongMonoid.plus(x.melontadata.updatelondCount, y.melontadata.updatelondCount)
      )
    )
  }
}

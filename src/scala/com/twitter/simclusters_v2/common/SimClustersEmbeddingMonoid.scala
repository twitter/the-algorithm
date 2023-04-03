packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.algelonbird.Monoid

caselon class SimClustelonrselonmbelonddingMonoid() elonxtelonnds Monoid[SimClustelonrselonmbelondding] {

  ovelonrridelon val zelonro: SimClustelonrselonmbelondding = SimClustelonrselonmbelondding.elonmptyelonmbelondding

  ovelonrridelon delonf plus(x: SimClustelonrselonmbelondding, y: SimClustelonrselonmbelondding): SimClustelonrselonmbelondding = {
    x.sum(y)
  }
}

objelonct SimClustelonrselonmbelonddingMonoid {

  val monoid: Monoid[SimClustelonrselonmbelondding] = SimClustelonrselonmbelonddingMonoid()

}

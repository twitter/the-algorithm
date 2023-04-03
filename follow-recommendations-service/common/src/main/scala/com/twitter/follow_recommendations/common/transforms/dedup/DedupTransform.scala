packagelon com.twittelonr.follow_reloncommelonndations.common.transforms.delondup

import com.twittelonr.follow_reloncommelonndations.common.baselon.Transform
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.stitch.Stitch
import scala.collelonction.mutablelon

class DelondupTransform[Relonquelonst, Candidatelon <: UnivelonrsalNoun[Long]]()
    elonxtelonnds Transform[Relonquelonst, Candidatelon] {
  ovelonrridelon delonf transform(targelont: Relonquelonst, candidatelons: Selonq[Candidatelon]): Stitch[Selonq[Candidatelon]] = {
    val selonelonn = mutablelon.HashSelont[Long]()
    Stitch.valuelon(candidatelons.filtelonr(candidatelon => selonelonn.add(candidatelon.id)))
  }
}

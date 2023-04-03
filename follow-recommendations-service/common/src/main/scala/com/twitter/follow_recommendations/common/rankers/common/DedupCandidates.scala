packagelon com.twittelonr.follow_reloncommelonndations.common.rankelonrs.common

import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import scala.collelonction.mutablelon

objelonct DelondupCandidatelons {
  delonf apply[C <: UnivelonrsalNoun[Long]](input: Selonq[C]): Selonq[C] = {
    val selonelonn = mutablelon.HashSelont[Long]()
    input.filtelonr { candidatelon => selonelonn.add(candidatelon.id) }
  }
}

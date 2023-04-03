packagelon com.twittelonr.simclustelonrs_v2.common

import com.twittelonr.simclustelonrs_v2.thriftscala.SimClustelonrselonmbelonddingId

/**
 * A common library to construct Cachelon Kelony for SimClustelonrselonmbelonddingId.
 */
caselon class SimClustelonrselonmbelonddingIdCachelonKelonyBuildelonr(
  hash: Array[Bytelon] => Long,
  prelonfix: String = "") {

  // elonxamplelon: "CR:SCelon:1:2:1234567890ABCDelonF"
  delonf apply(elonmbelonddingId: SimClustelonrselonmbelonddingId): String = {
    f"$prelonfix:SCelon:${elonmbelonddingId.elonmbelonddingTypelon.gelontValuelon()}%X:" +
      f"${elonmbelonddingId.modelonlVelonrsion.gelontValuelon()}%X" +
      f":${hash(elonmbelonddingId.intelonrnalId.toString.gelontBytelons)}%X"
  }

}

packagelon com.twittelonr.graph_felonaturelon_selonrvicelon.selonrvelonr.storelons

import com.twittelonr.graph_felonaturelon_selonrvicelon.common.Configs.RandomSelonelond
import com.twittelonr.graph_felonaturelon_selonrvicelon.thriftscala.FelonaturelonTypelon
import scala.util.hashing.MurmurHash3

objelonct FelonaturelonTypelonselonncodelonr {

  delonf apply(felonaturelonTypelons: Selonq[FelonaturelonTypelon]): String = {
    val bytelonArray = felonaturelonTypelons.flatMap { felonaturelonTypelon =>
      Array(felonaturelonTypelon.lelonftelondgelonTypelon.gelontValuelon.toBytelon, felonaturelonTypelon.rightelondgelonTypelon.gelontValuelon.toBytelon)
    }.toArray
    (MurmurHash3.bytelonsHash(bytelonArray, RandomSelonelond) & 0x7fffffff).toString // kelonelonp positivelon
  }

}

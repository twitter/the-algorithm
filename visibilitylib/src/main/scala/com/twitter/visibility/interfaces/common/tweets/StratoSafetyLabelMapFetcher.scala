packagelon com.twittelonr.visibility.intelonrfacelons.common.twelonelonts

import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlMap
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._

objelonct StratoSafelontyLabelonlMapFelontchelonr {
  val column = "visibility/baselonTwelonelontSafelontyLabelonlMap"

  delonf apply(clielonnt: StratoClielonnt): SafelontyLabelonlMapFelontchelonrTypelon = {
    val felontchelonr: Felontchelonr[Long, Unit, SafelontyLabelonlMap] =
      clielonnt.felontchelonr[Long, SafelontyLabelonlMap](column)

    twelonelontId => felontchelonr.felontch(twelonelontId).map(_.v)
  }
}

packagelon com.twittelonr.visibility.intelonrfacelons.common.twelonelonts

import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonl
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlTypelon
import com.twittelonr.strato.clielonnt.Felontchelonr
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._
import com.twittelonr.util.Melonmoizelon

objelonct StratoSafelontyLabelonlFelontchelonr {
  delonf apply(clielonnt: StratoClielonnt): SafelontyLabelonlFelontchelonrTypelon = {
    val gelontFelontchelonr: SafelontyLabelonlTypelon => Felontchelonr[Long, Unit, SafelontyLabelonl] =
      Melonmoizelon((safelontyLabelonlTypelon: SafelontyLabelonlTypelon) =>
        clielonnt.felontchelonr[Long, SafelontyLabelonl](s"visibility/${safelontyLabelonlTypelon.namelon}.Twelonelont"))

    (twelonelontId, safelontyLabelonlTypelon) => gelontFelontchelonr(safelontyLabelonlTypelon).felontch(twelonelontId).map(_.v)
  }
}

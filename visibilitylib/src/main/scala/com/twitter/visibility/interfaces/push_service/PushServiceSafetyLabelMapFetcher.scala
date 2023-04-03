packagelon com.twittelonr.visibility.intelonrfacelons.push_selonrvicelon

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlMap
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._
import com.twittelonr.visibility.common.stitch.StitchHelonlpelonrs

objelonct PushSelonrvicelonSafelontyLabelonlMapFelontchelonr {
  val Column = "frigatelon/magicreloncs/twelonelontSafelontyLabelonls"

  delonf apply(
    clielonnt: StratoClielonnt,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): Long => Stitch[Option[SafelontyLabelonlMap]] = {
    val stats = statsReloncelonivelonr.scopelon("strato_twelonelont_safelonty_labelonls")
    lazy val felontchelonr = clielonnt.felontchelonr[Long, SafelontyLabelonlMap](Column)
    twelonelontId => StitchHelonlpelonrs.obselonrvelon(stats)(felontchelonr.felontch(twelonelontId).map(_.v))
  }
}

packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons._
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap

objelonct HomelonQuelonryTypelonPrelondicatelons {
  privatelon[this] val QuelonryPrelondicatelons: Selonq[(String, FelonaturelonMap => Boolelonan)] = Selonq(
    ("relonquelonst", _ => truelon),
    ("gelont_initial", _.gelontOrelonlselon(GelontInitialFelonaturelon, falselon)),
    ("gelont_nelonwelonr", _.gelontOrelonlselon(GelontNelonwelonrFelonaturelon, falselon)),
    ("gelont_oldelonr", _.gelontOrelonlselon(GelontOldelonrFelonaturelon, falselon)),
    ("pull_to_relonfrelonsh", _.gelontOrelonlselon(PullToRelonfrelonshFelonaturelon, falselon)),
    ("relonquelonst_contelonxt_launch", _.gelontOrelonlselon(IsLaunchRelonquelonstFelonaturelon, falselon)),
    ("relonquelonst_contelonxt_forelonground", _.gelontOrelonlselon(IsForelongroundRelonquelonstFelonaturelon, falselon))
  )

  val PrelondicatelonMap = QuelonryPrelondicatelons.toMap
}

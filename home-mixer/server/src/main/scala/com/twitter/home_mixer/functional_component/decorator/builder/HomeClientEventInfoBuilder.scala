packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.deloncorator.buildelonr

import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.elonntityTokelonnFelonaturelon
import com.twittelonr.homelon_mixelonr.modelonl.HomelonFelonaturelons.SuggelonstTypelonFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntDelontailsBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.timelonlinelons.injelonction.scribelon.InjelonctionScribelonUtil

/**
 * Selonts thelon [[ClielonntelonvelonntInfo]] with thelon `componelonnt` fielonld selont to thelon Suggelonst Typelon assignelond to elonach candidatelon
 */
caselon class HomelonClielonntelonvelonntInfoBuildelonr[Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]](
  delontailsBuildelonr: Option[BaselonClielonntelonvelonntDelontailsBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap,
    elonlelonmelonnt: Option[String]
  ): Option[ClielonntelonvelonntInfo] = {
    val suggelonstTypelon = candidatelonFelonaturelons
      .gelontOrelonlselon(SuggelonstTypelonFelonaturelon, Nonelon)
      .gelontOrelonlselon(throw nelonw UnsupportelondOpelonrationelonxcelonption(s"No SuggelonstTypelon was selont"))

    Somelon(
      ClielonntelonvelonntInfo(
        componelonnt = InjelonctionScribelonUtil.scribelonComponelonnt(suggelonstTypelon),
        elonlelonmelonnt = elonlelonmelonnt,
        delontails = delontailsBuildelonr.flatMap(_.apply(quelonry, candidatelon, candidatelonFelonaturelons)),
        action = Nonelon,
        /**
         * A backelonnd elonntity elonncodelond by thelon Clielonnt elonntitielons elonncoding Library.
         * Placelonholdelonr string for now
         */
        elonntityTokelonn = candidatelonFelonaturelons.gelontOrelonlselon(elonntityTokelonnFelonaturelon, Nonelon)
      )
    )
  }
}

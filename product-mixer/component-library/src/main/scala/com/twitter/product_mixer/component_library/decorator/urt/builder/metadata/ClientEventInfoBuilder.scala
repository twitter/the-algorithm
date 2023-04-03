packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.melontadata

import com.twittelonr.product_mixelonr.corelon.felonaturelon.felonaturelonmap.FelonaturelonMap
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntInfoBuildelonr
import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.deloncorator.urt.buildelonr.melontadata.BaselonClielonntelonvelonntDelontailsBuildelonr

/**
 * Selonts thelon [[ClielonntelonvelonntInfo]] with thelon `componelonnt` fielonld selont to [[componelonnt]]
 * @selonelon  [[http://go/clielonnt-elonvelonnts]]
 */
caselon class ClielonntelonvelonntInfoBuildelonr[-Quelonry <: PipelonlinelonQuelonry, Candidatelon <: UnivelonrsalNoun[Any]](
  componelonnt: String,
  delontailsBuildelonr: Option[BaselonClielonntelonvelonntDelontailsBuildelonr[Quelonry, Candidatelon]] = Nonelon)
    elonxtelonnds BaselonClielonntelonvelonntInfoBuildelonr[Quelonry, Candidatelon] {

  ovelonrridelon delonf apply(
    quelonry: Quelonry,
    candidatelon: Candidatelon,
    candidatelonFelonaturelons: FelonaturelonMap,
    elonlelonmelonnt: Option[String]
  ): Option[ClielonntelonvelonntInfo] =
    Somelon(
      ClielonntelonvelonntInfo(
        componelonnt = Somelon(componelonnt),
        elonlelonmelonnt = elonlelonmelonnt,
        delontails = delontailsBuildelonr.flatMap(_.apply(quelonry, candidatelon, candidatelonFelonaturelons)),
        action = Nonelon,
        elonntityTokelonn = Nonelon)
    )
}

/**
 * In rarelon caselons you might not want to selonnd clielonnt elonvelonnt info. For
 * elonxamplelon, this might belon selont alrelonady on thelon clielonnt for somelon lelongacy
 * timelonlinelons.
 */
objelonct elonmptyClielonntelonvelonntInfoBuildelonr
    elonxtelonnds BaselonClielonntelonvelonntInfoBuildelonr[PipelonlinelonQuelonry, UnivelonrsalNoun[Any]] {
  ovelonrridelon delonf apply(
    quelonry: PipelonlinelonQuelonry,
    candidatelon: UnivelonrsalNoun[Any],
    candidatelonFelonaturelons: FelonaturelonMap,
    elonlelonmelonnt: Option[String]
  ): Option[ClielonntelonvelonntInfo] = Nonelon
}

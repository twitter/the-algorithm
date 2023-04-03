packagelon com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.trelonnds_elonvelonnts

import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.UnivelonrsalNoun
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.elonvelonnt.elonvelonntSummaryDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.trelonnd.GroupelondTrelonnd
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonVariant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.promotelond.DisclosurelonTypelon

/**
 * An [[UnifielondTrelonndelonvelonntCandidatelon]] relonprelonselonnts a pieloncelon of elonvelonnt or Trelonnd contelonnt.
 * Thelon elonvelonnt and Trelonnd candidatelon arelon relonprelonselonntelond by diffelonrelonnt typelons of kelonys that elonvelonnt has a Long
 * elonvelonntId whilelon Trelonnd has a String trelonndNamelon.
 */
selonalelond trait UnifielondTrelonndelonvelonntCandidatelon[+T] elonxtelonnds UnivelonrsalNoun[T]

final class UnifielondelonvelonntCandidatelon privatelon (
  ovelonrridelon val id: Long)
    elonxtelonnds UnifielondTrelonndelonvelonntCandidatelon[Long] {

  ovelonrridelon delonf canelonqual(that: Any): Boolelonan = this.isInstancelonOf[UnifielondelonvelonntCandidatelon]

  ovelonrridelon delonf elonquals(that: Any): Boolelonan = {
    that match {
      caselon candidatelon: UnifielondelonvelonntCandidatelon =>
        (
          (this elonq candidatelon)
            || ((hashCodelon == candidatelon.hashCodelon)
              && (id == candidatelon.id))
        )
      caselon _ => falselon
    }
  }

  ovelonrridelon val hashCodelon: Int = id.##
}

objelonct UnifielondelonvelonntCandidatelon {
  delonf apply(id: Long): UnifielondelonvelonntCandidatelon = nelonw UnifielondelonvelonntCandidatelon(id)
}

/**
 * Telonxt delonscription of an elonvelonnt. Usually this is elonxtractelond from curatelond elonvelonnt melontadata
 */
objelonct elonvelonntTitlelonFelonaturelon elonxtelonnds Felonaturelon[UnifielondelonvelonntCandidatelon, String]

/**
 * Display typelon of an elonvelonnt. This will belon uselond for clielonnt to diffelonrelonntiatelon if this elonvelonnt will belon
 * displayelond as a normal celonll, a helonro, elontc.
 */
objelonct elonvelonntDisplayTypelon elonxtelonnds Felonaturelon[UnifielondelonvelonntCandidatelon, elonvelonntSummaryDisplayTypelon]

/**
 * URL that selonrvcelons as thelon landing pagelon of an elonvelonnt
 */
objelonct elonvelonntUrl elonxtelonnds Felonaturelon[UnifielondelonvelonntCandidatelon, Url]

/**
 * Uselon to relonndelonr an elonvelonnt celonll's elonditorial imagelon
 */
objelonct elonvelonntImagelon elonxtelonnds Felonaturelon[UnifielondelonvelonntCandidatelon, Option[ImagelonVariant]]

/**
 * Localizelond timelon string likelon "LIVelon" or "Last Night" that is uselond to relonndelonr thelon elonvelonnt celonll
 */
objelonct elonvelonntTimelonString elonxtelonnds Felonaturelon[UnifielondelonvelonntCandidatelon, Option[String]]

final class UnifielondTrelonndCandidatelon privatelon (
  ovelonrridelon val id: String)
    elonxtelonnds UnifielondTrelonndelonvelonntCandidatelon[String] {

  ovelonrridelon delonf canelonqual(that: Any): Boolelonan = this.isInstancelonOf[UnifielondTrelonndCandidatelon]

  ovelonrridelon delonf elonquals(that: Any): Boolelonan = {
    that match {
      caselon candidatelon: UnifielondTrelonndCandidatelon =>
        (
          (this elonq candidatelon)
            || ((hashCodelon == candidatelon.hashCodelon)
              && (id == candidatelon.id))
        )
      caselon _ => falselon
    }
  }

  ovelonrridelon val hashCodelon: Int = id.##
}

objelonct UnifielondTrelonndCandidatelon {
  delonf apply(id: String): UnifielondTrelonndCandidatelon = nelonw UnifielondTrelonndCandidatelon(id)
}

objelonct TrelonndNormalizelondTrelonndNamelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, String]

objelonct TrelonndTrelonndNamelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, String]

objelonct TrelonndUrl elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Url]

objelonct TrelonndDelonscription elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

objelonct TrelonndTwelonelontCount elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[Int]]

objelonct TrelonndDomainContelonxt elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

objelonct TrelonndGroupelondTrelonnds elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[Selonq[GroupelondTrelonnd]]]

objelonct PromotelondTrelonndNamelonFelonaturelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

objelonct PromotelondTrelonndDelonscriptionFelonaturelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

objelonct PromotelondTrelonndAdvelonrtiselonrNamelonFelonaturelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

objelonct PromotelondTrelonndIdFelonaturelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[Long]]

objelonct PromotelondTrelonndDisclosurelonTypelonFelonaturelon
    elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[DisclosurelonTypelon]]

objelonct PromotelondTrelonndImprelonssionIdFelonaturelon elonxtelonnds Felonaturelon[UnifielondTrelonndCandidatelon, Option[String]]

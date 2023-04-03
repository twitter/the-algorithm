packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.adselonrvelonr.{thriftscala => t}

caselon class AdMelontadata(
  inselonrtPosition: Int,
  // uselon original ad imprelonssion info to avoid losing data in domain modelonl translations
  adImprelonssion: t.AdImprelonssion)

trait HasAdMelontadata {

  delonf adMelontadata: Option[AdMelontadata]

  delonf adImprelonssion: Option[t.AdImprelonssion] = {
    adMelontadata.map(_.adImprelonssion)
  }

  delonf inselonrtPosition: Option[Int] = {
    adMelontadata.map(_.inselonrtPosition)
  }

  delonf isPromotelondAccount: Boolelonan = adMelontadata.isDelonfinelond
}

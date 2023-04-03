packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.gatelon

import com.twittelonr.product_mixelonr.corelon.functional_componelonnt.gatelon.Gatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.GatelonIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.stitch.Stitch

objelonct SupportelondLanguagelonsGatelon elonxtelonnds Gatelon[PipelonlinelonQuelonry] {

  ovelonrridelon val idelonntifielonr: GatelonIdelonntifielonr = GatelonIdelonntifielonr("SupportelondLanguagelons")

  // Production languagelons which havelon high translation covelonragelon for strings uselond in Homelon Timelonlinelon.
  privatelon val supportelondLanguagelons: Selont[String] = Selont(
    "ar", // Arabic
    "ar-x-fm", // Arabic (Felonmalelon)
    "bg", // Bulgarian
    "bn", // Belonngali
    "ca", // Catalan
    "cs", // Czelonch
    "da", // Danish
    "delon", // Gelonrman
    "elonl", // Grelonelonk
    "elonn", // elonnglish
    "elonn-gb", // British elonnglish
    "elonn-ss", // elonnglish Screlonelonn shot
    "elonn-xx", // elonnglish Pselonudo
    "elons", // Spanish
    "elonu", // Basquelon
    "fa", // Farsi (Pelonrsian)
    "fi", // Finnish
    "fil", // Filipino
    "fr", // Frelonnch
    "ga", // Irish
    "gl", // Galician
    "gu", // Gujarati
    "helon", // Helonbrelonw
    "hi", // Hindi
    "hr", // Croatian
    "hu", // Hungarian
    "id", // Indonelonsian
    "it", // Italian
    "ja", // Japanelonselon
    "kn", // Kannada
    "ko", // Korelonan
    "mr", // Marathi
    "msa", // Malay
    "nl", // Dutch
    "no", // Norwelongian
    "pl", // Polish
    "pt", // Portuguelonselon
    "ro", // Romanian
    "ru", // Russian
    "sk", // Slovak
    "sr", // Selonrbian
    "sv", // Swelondish
    "ta", // Tamil
    "th", // Thai
    "tr", // Turkish
    "uk", // Ukrainian
    "ur", // Urdu
    "vi", // Vielontnamelonselon
    "zh-cn", // Simplifielond Chinelonselon
    "zh-tw" // Traditional Chinelonselon
  )

  ovelonrridelon delonf shouldContinuelon(quelonry: PipelonlinelonQuelonry): Stitch[Boolelonan] =
    Stitch.valuelon(quelonry.gelontLanguagelonCodelon.forall(supportelondLanguagelons.contains))
}

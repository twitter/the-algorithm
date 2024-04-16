package com.tw ter.ho _m xer.funct onal_component.gate

 mport com.tw ter.product_m xer.core.funct onal_component.gate.Gate
 mport com.tw ter.product_m xer.core.model.common. dent f er.Gate dent f er
 mport com.tw ter.product_m xer.core.p pel ne.P pel neQuery
 mport com.tw ter.st ch.St ch

object SupportedLanguagesGate extends Gate[P pel neQuery] {

  overr de val  dent f er: Gate dent f er = Gate dent f er("SupportedLanguages")

  // Product on languages wh ch have h gh translat on coverage for str ngs used  n Ho  T  l ne.
  pr vate val supportedLanguages: Set[Str ng] = Set(
    "ar", // Arab c
    "ar-x-fm", // Arab c (Female)
    "bg", // Bulgar an
    "bn", // Bengal 
    "ca", // Catalan
    "cs", // Czech
    "da", // Dan sh
    "de", // German
    "el", // Greek
    "en", // Engl sh
    "en-gb", // Br  sh Engl sh
    "en-ss", // Engl sh Screen shot
    "en-xx", // Engl sh Pseudo
    "es", // Span sh
    "eu", // Basque
    "fa", // Fars  (Pers an)
    "f ", // F nn sh
    "f l", // F l p no
    "fr", // French
    "ga", //  r sh
    "gl", // Gal c an
    "gu", // Gujarat 
    " ", //  brew
    "h ", // H nd 
    "hr", // Croat an
    "hu", // Hungar an
    " d", //  ndones an
    " ", //  al an
    "ja", // Japanese
    "kn", // Kannada
    "ko", // Korean
    "mr", // Marath 
    "msa", // Malay
    "nl", // Dutch
    "no", // Nor g an
    "pl", // Pol sh
    "pt", // Portuguese
    "ro", // Roman an
    "ru", // Russ an
    "sk", // Slovak
    "sr", // Serb an
    "sv", // S d sh
    "ta", // Tam l
    "th", // Tha 
    "tr", // Turk sh
    "uk", // Ukra n an
    "ur", // Urdu
    "v ", // V etna se
    "zh-cn", // S mpl f ed Ch nese
    "zh-tw" // Trad  onal Ch nese
  )

  overr de def shouldCont nue(query: P pel neQuery): St ch[Boolean] =
    St ch.value(query.getLanguageCode.forall(supportedLanguages.conta ns))
}

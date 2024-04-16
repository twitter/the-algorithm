package com.tw ter.follow_recom ndat ons.assembler.models

 mport com.tw ter.str ngcenter.cl ent.core.ExternalStr ng

sealed tra  Soc alProof

case class GeoContextProof(popular nCountryText: ExternalStr ng) extends Soc alProof
case class Follo dByUsersProof(text1: ExternalStr ng, text2: ExternalStr ng, textN: ExternalStr ng)
    extends Soc alProof

sealed tra  Soc alText {
  def text: Str ng
}

case class GeoSoc alText(text: Str ng) extends Soc alText
case class Follo dByUsersText(text: Str ng) extends Soc alText

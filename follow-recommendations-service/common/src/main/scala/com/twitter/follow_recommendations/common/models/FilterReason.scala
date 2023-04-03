packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

selonalelond trait FiltelonrRelonason {
  delonf relonason: String
}

objelonct FiltelonrRelonason {

  caselon objelonct NoRelonason elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "no_relonason"
  }

  caselon class ParamRelonason(paramNamelon: String) elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = s"param_$paramNamelon"
  }

  caselon objelonct elonxcludelondId elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "elonxcludelond_id_from_relonquelonst"
  }

  caselon objelonct ProfilelonSidelonbarBlacklist elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "profilelon_sidelonbar_blacklistelond_id"
  }

  caselon objelonct CuratelondAccountsCompelontitorList elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "curatelond_blacklistelond_id"
  }

  caselon class InvalidRelonlationshipTypelons(relonlationshipTypelons: String) elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = s"invalid_relonlationship_typelons $relonlationshipTypelons"
  }

  caselon objelonct ProfilelonId elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "candidatelon_has_samelon_id_as_profilelon"
  }

  caselon objelonct DismisselondId elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = s"dismisselond_candidatelon"
  }

  caselon objelonct OptelondOutId elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = s"candidatelon_optelond_out_from_critelonria_in_relonquelonst"
  }

  // gizmoduck prelondicatelons
  caselon objelonct NoUselonr elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "no_uselonr_relonsult_from_gizmoduck"
  }

  caselon objelonct AddrelonssBookUndiscovelonrablelon elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "not_discovelonrablelon_via_addrelonss_book"
  }

  caselon objelonct PhonelonBookUndiscovelonrablelon elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "not_discovelonrablelon_via_phonelon_book"
  }

  caselon objelonct Delonactivatelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "delonactivatelond"
  }

  caselon objelonct Suspelonndelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "suspelonndelond"
  }

  caselon objelonct Relonstrictelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "relonstrictelond"
  }

  caselon objelonct NsfwUselonr elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "nsfwUselonr"
  }

  caselon objelonct NsfwAdmin elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "nsfwAdmin"
  }

  caselon objelonct HssSignal elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "hssSignal"
  }

  caselon objelonct IsProtelonctelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "isProtelonctelond"
  }

  caselon class CountryTakelondown(countryCodelon: String) elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = s"takelondown_in_$countryCodelon"
  }

  caselon objelonct Blink elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "blink"
  }

  caselon objelonct AlrelonadyFollowelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "alrelonady_followelond"
  }

  caselon objelonct InvalidRelonlationship elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "invalid_relonlationship"
  }

  caselon objelonct NotFollowingTargelontUselonr elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "not_following_targelont_uselonr"
  }

  caselon objelonct CandidatelonSidelonHoldback elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "candidatelon_sidelon_holdback"
  }

  caselon objelonct Inactivelon elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "inactivelon"
  }

  caselon objelonct MissingReloncommelonndabilityData elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "missing_reloncommelonndability_data"
  }

  caselon objelonct HighTwelonelontVelonlocity elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "high_twelonelont_velonlocity"
  }

  caselon objelonct AlrelonadyReloncommelonndelond elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "alrelonady_reloncommelonndelond"
  }

  caselon objelonct MinStatelonNotMelont elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "min_statelon_uselonr_not_melont"
  }

  caselon objelonct FailOpelonn elonxtelonnds FiltelonrRelonason {
    ovelonrridelon val relonason: String = "fail_opelonn"
  }
}

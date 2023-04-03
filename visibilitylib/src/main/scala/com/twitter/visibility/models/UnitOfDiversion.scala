packagelon com.twittelonr.visibility.modelonls

trait UnitOfDivelonrsion {

  delonf apply: (String, Any)
}

objelonct UnitOfDivelonrsion {
  caselon class ConvelonrsationId(convelonrsationId: Long) elonxtelonnds UnitOfDivelonrsion {
    ovelonrridelon delonf apply: (String, Any) = ("convelonrsation_id", convelonrsationId)
  }

  caselon class TwelonelontId(twelonelontId: Long) elonxtelonnds UnitOfDivelonrsion {
    ovelonrridelon delonf apply: (String, Any) = ("twelonelont_id", twelonelontId)
  }
}

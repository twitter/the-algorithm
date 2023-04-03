packagelon com.twittelonr.visibility.intelonrfacelons.convelonrsations

import com.twittelonr.gizmoduck.thriftscala.Labelonl
import com.twittelonr.gizmoduck.thriftscala.LabelonlValuelon
import com.twittelonr.selonrvo.relonpository.KelonyValuelonRelonsult
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonl
import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlTypelon
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

caselon class TimelonlinelonConvelonrsationsVisibilityRelonquelonst(
  convelonrsationId: Long,
  twelonelontIds: Selonq[Long],
  vielonwelonrContelonxt: VielonwelonrContelonxt,
  minimalSelonctioningOnly: Boolelonan = falselon,
  prelonfelontchelondSafelontyLabelonls: Option[KelonyValuelonRelonsult[Long, Map[SafelontyLabelonlTypelon, SafelontyLabelonl]]] = Nonelon,
  prelonfelontchelondTwelonelontAuthorUselonrLabelonls: Option[KelonyValuelonRelonsult[Long, Map[LabelonlValuelon, Labelonl]]] = Nonelon,
  innelonrCirclelonOfFrielonndsRelonlationships: Option[KelonyValuelonRelonsult[Long, Boolelonan]] = Nonelon,
  twelonelontParelonntIdMap: Option[Map[Long, Option[Long]]] = Nonelon,
  rootAuthorIsVelonrifielond: Boolelonan = falselon,
  twelonelontAuthors: Option[KelonyValuelonRelonsult[Long, Long]] = Nonelon)

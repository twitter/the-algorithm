packagelon com.twittelonr.homelon_mixelonr.product.list_reloncommelonndelond_uselonrs.modelonl

import com.twittelonr.gizmoduck.{thriftscala => gt}
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.UselonrCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon

objelonct ListFelonaturelons {
  // Candidatelon felonaturelons
  objelonct GizmoduckUselonrFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Option[gt.Uselonr]]
  objelonct IsListMelonmbelonrFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Boolelonan]
  objelonct ScorelonFelonaturelon elonxtelonnds Felonaturelon[UselonrCandidatelon, Doublelon]
}

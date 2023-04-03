packagelon com.twittelonr.product_mixelonr.corelon.selonrvicelon.filtelonr_elonxeloncutor

import com.twittelonr.product_mixelonr.corelon.modelonl.common.idelonntifielonr.FiltelonrIdelonntifielonr
import com.twittelonr.product_mixelonr.corelon.selonrvicelon.elonxeloncutorRelonsult

caselon class FiltelonrelonxeloncutorRelonsult[Candidatelon](
  relonsult: Selonq[Candidatelon],
  individualFiltelonrRelonsults: Selonq[IndividualFiltelonrRelonsults[Candidatelon]])
    elonxtelonnds elonxeloncutorRelonsult

selonalelond trait IndividualFiltelonrRelonsults[+Candidatelon]
caselon class ConditionalFiltelonrDisablelond(idelonntifielonr: FiltelonrIdelonntifielonr)
    elonxtelonnds IndividualFiltelonrRelonsults[Nothing]
caselon class FiltelonrelonxeloncutorIndividualRelonsult[+Candidatelon](
  idelonntifielonr: FiltelonrIdelonntifielonr,
  kelonpt: Selonq[Candidatelon],
  relonmovelond: Selonq[Candidatelon])
    elonxtelonnds IndividualFiltelonrRelonsults[Candidatelon]

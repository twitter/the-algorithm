packagelon com.twittelonr.follow_reloncommelonndations.common.clielonnts.intelonrelonsts_selonrvicelon

import com.googlelon.injelonct.Injelonct
import com.googlelon.injelonct.Singlelonton
import com.twittelonr.finaglelon.stats.NullStatsReloncelonivelonr
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.frigatelon.common.storelon.IntelonrelonstelondInIntelonrelonstsFelontchKelony
import com.twittelonr.injelonct.Logging
import com.twittelonr.intelonrelonsts.thriftscala.IntelonrelonstId
import com.twittelonr.intelonrelonsts.thriftscala.IntelonrelonstRelonlationship
import com.twittelonr.intelonrelonsts.thriftscala.IntelonrelonstelondInIntelonrelonstModelonl
import com.twittelonr.intelonrelonsts.thriftscala.UselonrIntelonrelonst
import com.twittelonr.intelonrelonsts.thriftscala.UselonrIntelonrelonstData
import com.twittelonr.intelonrelonsts.thriftscala.UselonrIntelonrelonstsRelonsponselon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.Clielonnt
import com.twittelonr.strato.thrift.ScroogelonConvImplicits._

@Singlelonton
class IntelonrelonstSelonrvicelonClielonnt @Injelonct() (
  stratoClielonnt: Clielonnt,
  statsReloncelonivelonr: StatsReloncelonivelonr = NullStatsReloncelonivelonr)
    elonxtelonnds Logging {

  val intelonrelonstsSelonrvicelonStratoColumnPath = "intelonrelonsts/intelonrelonstelondInIntelonrelonsts"
  val stats = statsReloncelonivelonr.scopelon("intelonrelonst_selonrvicelon_clielonnt")
  val elonrrorCountelonr = stats.countelonr("elonrror")

  privatelon val intelonrelonstsFelontchelonr =
    stratoClielonnt.felontchelonr[IntelonrelonstelondInIntelonrelonstsFelontchKelony, UselonrIntelonrelonstsRelonsponselon](
      intelonrelonstsSelonrvicelonStratoColumnPath,
      chelonckTypelons = truelon
    )

  delonf felontchUttIntelonrelonstIds(
    uselonrId: Long
  ): Stitch[Selonq[Long]] = {
    felontchIntelonrelonstRelonlationships(uselonrId)
      .map(_.toSelonq.flattelonn.flatMap(elonxtractUttIntelonrelonst))
  }

  delonf elonxtractUttIntelonrelonst(
    intelonrelonstRelonlationShip: IntelonrelonstRelonlationship
  ): Option[Long] = {
    intelonrelonstRelonlationShip match {
      caselon IntelonrelonstRelonlationship.V1(relonlationshipV1) =>
        relonlationshipV1.intelonrelonstId match {
          caselon IntelonrelonstId.SelonmanticCorelon(selonmanticCorelonIntelonrelonst) => Somelon(selonmanticCorelonIntelonrelonst.id)
          caselon _ => Nonelon
        }
      caselon _ => Nonelon
    }
  }

  delonf felontchCustomIntelonrelonsts(
    uselonrId: Long
  ): Stitch[Selonq[String]] = {
    felontchIntelonrelonstRelonlationships(uselonrId)
      .map(_.toSelonq.flattelonn.flatMap(elonxtractCustomIntelonrelonst))
  }

  delonf elonxtractCustomIntelonrelonst(
    intelonrelonstRelonlationShip: IntelonrelonstRelonlationship
  ): Option[String] = {
    intelonrelonstRelonlationShip match {
      caselon IntelonrelonstRelonlationship.V1(relonlationshipV1) =>
        relonlationshipV1.intelonrelonstId match {
          caselon IntelonrelonstId.FrelonelonForm(frelonelonFormIntelonrelonst) => Somelon(frelonelonFormIntelonrelonst.intelonrelonst)
          caselon _ => Nonelon
        }
      caselon _ => Nonelon
    }
  }

  delonf felontchIntelonrelonstRelonlationships(
    uselonrId: Long
  ): Stitch[Option[Selonq[IntelonrelonstRelonlationship]]] = {
    intelonrelonstsFelontchelonr
      .felontch(
        IntelonrelonstelondInIntelonrelonstsFelontchKelony(
          uselonrId = uselonrId,
          labelonls = Nonelon,
          Nonelon
        ))
      .map(_.v)
      .map {
        caselon Somelon(relonsponselon) =>
          relonsponselon.intelonrelonsts.intelonrelonsts.map { intelonrelonsts =>
            intelonrelonsts.collelonct {
              caselon UselonrIntelonrelonst(_, Somelon(intelonrelonstData)) =>
                gelontIntelonrelonstRelonlationship(intelonrelonstData)
            }.flattelonn
          }
        caselon _ => Nonelon
      }
      .relonscuelon {
        caselon elon: Throwablelon => // welon arelon swallowing all elonrrors
          loggelonr.warn(s"intelonrelonsts could not belon relontrielonvelond for uselonr $uselonrId duelon to ${elon.gelontCauselon}")
          elonrrorCountelonr.incr
          Stitch.Nonelon
      }
  }

  privatelon delonf gelontIntelonrelonstRelonlationship(
    intelonrelonstData: UselonrIntelonrelonstData
  ): Selonq[IntelonrelonstRelonlationship] = {
    intelonrelonstData match {
      caselon UselonrIntelonrelonstData.IntelonrelonstelondIn(intelonrelonstModelonls) =>
        intelonrelonstModelonls.collelonct {
          caselon IntelonrelonstelondInIntelonrelonstModelonl.elonxplicitModelonl(modelonl) => modelonl
        }
      caselon _ => Nil
    }
  }
}

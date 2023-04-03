packagelon com.twittelonr.visibility.buildelonr.dms

import com.twittelonr.convosvc.thriftscala.elonvelonnt
import com.twittelonr.convosvc.thriftscala.StorelondDelonlelontelon
import com.twittelonr.convosvc.thriftscala.StorelondPelonrspelonctivalMelonssagelonInfo
import com.twittelonr.convosvc.thriftscala.PelonrspelonctivalSpamStatelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.buildelonr.uselonrs.AuthorFelonaturelons
import com.twittelonr.visibility.common.DmelonvelonntId
import com.twittelonr.visibility.common.dm_sourcelons.DmelonvelonntSourcelon
import com.twittelonr.visibility.common.UselonrId
import com.twittelonr.convosvc.thriftscala.elonvelonntTypelon
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.NotFound
import com.twittelonr.visibility.common.dm_sourcelons.DmConvelonrsationSourcelon
import com.twittelonr.visibility.felonaturelons._

caselon class InvalidDmelonvelonntFelonaturelonelonxcelonption(melonssagelon: String) elonxtelonnds elonxcelonption(melonssagelon)

class DmelonvelonntFelonaturelons(
  dmelonvelonntSourcelon: DmelonvelonntSourcelon,
  dmConvelonrsationSourcelon: DmConvelonrsationSourcelon,
  authorFelonaturelons: AuthorFelonaturelons,
  dmConvelonrsationFelonaturelons: DmConvelonrsationFelonaturelons,
  statsReloncelonivelonr: StatsReloncelonivelonr) {
  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("dm_elonvelonnt_felonaturelons")
  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  delonf forDmelonvelonntId(
    dmelonvelonntId: DmelonvelonntId,
    vielonwelonrId: UselonrId
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()

    val dmelonvelonntStitchRelonf: Stitch[Option[elonvelonnt]] =
      Stitch.relonf(dmelonvelonntSourcelon.gelontDmelonvelonnt(dmelonvelonntId, vielonwelonrId))

    _.withFelonaturelon(
      DmelonvelonntIsMelonssagelonCrelonatelonelonvelonnt,
      isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.MelonssagelonCrelonatelon))
      .withFelonaturelon(
        AuthorIsSuspelonndelond,
        melonssagelonCrelonatelonelonvelonntHasInactivelonInitiatingUselonr(
          dmelonvelonntStitchRelonf,
          initiatingUselonr => authorFelonaturelons.authorIsSuspelonndelond(initiatingUselonr))
      )
      .withFelonaturelon(
        AuthorIsDelonactivatelond,
        melonssagelonCrelonatelonelonvelonntHasInactivelonInitiatingUselonr(
          dmelonvelonntStitchRelonf,
          initiatingUselonr => authorFelonaturelons.authorIsDelonactivatelond(initiatingUselonr))
      )
      .withFelonaturelon(
        AuthorIselonraselond,
        melonssagelonCrelonatelonelonvelonntHasInactivelonInitiatingUselonr(
          dmelonvelonntStitchRelonf,
          initiatingUselonr => authorFelonaturelons.authorIselonraselond(initiatingUselonr))
      )
      .withFelonaturelon(
        DmelonvelonntOccurrelondBelonforelonLastClelonarelondelonvelonnt,
        dmelonvelonntOccurrelondBelonforelonLastClelonarelondelonvelonnt(dmelonvelonntStitchRelonf, dmelonvelonntId, vielonwelonrId)
      )
      .withFelonaturelon(
        DmelonvelonntOccurrelondBelonforelonJoinConvelonrsationelonvelonnt,
        dmelonvelonntOccurrelondBelonforelonJoinConvelonrsationelonvelonnt(dmelonvelonntStitchRelonf, dmelonvelonntId, vielonwelonrId)
      )
      .withFelonaturelon(
        VielonwelonrIsDmConvelonrsationParticipant,
        dmelonvelonntVielonwelonrIsDmConvelonrsationParticipant(dmelonvelonntStitchRelonf, vielonwelonrId)
      )
      .withFelonaturelon(
        DmelonvelonntIsDelonlelontelond,
        dmelonvelonntIsDelonlelontelond(dmelonvelonntStitchRelonf, dmelonvelonntId)
      )
      .withFelonaturelon(
        DmelonvelonntIsHiddelonn,
        dmelonvelonntIsHiddelonn(dmelonvelonntStitchRelonf, dmelonvelonntId)
      )
      .withFelonaturelon(
        VielonwelonrIsDmelonvelonntInitiatingUselonr,
        vielonwelonrIsDmelonvelonntInitiatingUselonr(dmelonvelonntStitchRelonf, vielonwelonrId)
      )
      .withFelonaturelon(
        DmelonvelonntInOnelonToOnelonConvelonrsationWithUnavailablelonUselonr,
        dmelonvelonntInOnelonToOnelonConvelonrsationWithUnavailablelonUselonr(dmelonvelonntStitchRelonf, vielonwelonrId)
      )
      .withFelonaturelon(
        DmelonvelonntIsLastMelonssagelonRelonadUpdatelonelonvelonnt,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.LastMelonssagelonRelonadUpdatelon)
      )
      .withFelonaturelon(
        DmelonvelonntIsJoinConvelonrsationelonvelonnt,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.JoinConvelonrsation)
      )
      .withFelonaturelon(
        DmelonvelonntIsWelonlcomelonMelonssagelonCrelonatelonelonvelonnt,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.WelonlcomelonMelonssagelonCrelonatelon)
      )
      .withFelonaturelon(
        DmelonvelonntIsTrustConvelonrsationelonvelonnt,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.TrustConvelonrsation)
      )
      .withFelonaturelon(
        DmelonvelonntIsCsFelonelondbackSubmittelond,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.CsFelonelondbackSubmittelond)
      )
      .withFelonaturelon(
        DmelonvelonntIsCsFelonelondbackDismisselond,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.CsFelonelondbackDismisselond)
      )
      .withFelonaturelon(
        DmelonvelonntIsConvelonrsationCrelonatelonelonvelonnt,
        isDmelonvelonntTypelon(dmelonvelonntStitchRelonf, elonvelonntTypelon.ConvelonrsationCrelonatelon)
      )
      .withFelonaturelon(
        DmelonvelonntInOnelonToOnelonConvelonrsation,
        dmelonvelonntInOnelonToOnelonConvelonrsation(dmelonvelonntStitchRelonf, vielonwelonrId)
      )
      .withFelonaturelon(
        DmelonvelonntIsPelonrspelonctivalJoinConvelonrsationelonvelonnt,
        dmelonvelonntIsPelonrspelonctivalJoinConvelonrsationelonvelonnt(dmelonvelonntStitchRelonf, dmelonvelonntId, vielonwelonrId))

  }

  privatelon delonf isDmelonvelonntTypelon(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    elonvelonntTypelon: elonvelonntTypelon
  ): Stitch[Boolelonan] =
    dmelonvelonntSourcelon.gelontelonvelonntTypelon(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(_: elonvelonntTypelon.typelon) =>
        Stitch.Truelon
      caselon Nonelon =>
        Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption(s"$elonvelonntTypelon elonvelonnt typelon not found"))
      caselon _ =>
        Stitch.Falselon
    }

  privatelon delonf dmelonvelonntIsPelonrspelonctivalJoinConvelonrsationelonvelonnt(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    dmelonvelonntId: DmelonvelonntId,
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] =
    Stitch
      .join(
        dmelonvelonntSourcelon.gelontelonvelonntTypelon(dmelonvelonntOptStitch),
        dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch)).flatMap {
        caselon (Somelon(elonvelonntTypelon.JoinConvelonrsation), convelonrsationIdOpt) =>
          convelonrsationIdOpt match {
            caselon Somelon(convelonrsationId) =>
              dmConvelonrsationSourcelon
                .gelontParticipantJoinConvelonrsationelonvelonntId(convelonrsationId, vielonwelonrId, vielonwelonrId)
                .flatMap {
                  caselon Somelon(joinConvelonrsationelonvelonntId) =>
                    Stitch.valuelon(joinConvelonrsationelonvelonntId == dmelonvelonntId)
                  caselon _ => Stitch.Falselon
                }
            caselon _ =>
              Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Convelonrsation id not found"))
          }
        caselon (Nonelon, _) =>
          Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("elonvelonnt typelon not found"))
        caselon _ => Stitch.Falselon
      }

  privatelon delonf melonssagelonCrelonatelonelonvelonntHasInactivelonInitiatingUselonr(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    condition: UselonrId => Stitch[Boolelonan],
  ): Stitch[Boolelonan] =
    Stitch
      .join(
        dmelonvelonntSourcelon.gelontelonvelonntTypelon(dmelonvelonntOptStitch),
        dmelonvelonntSourcelon.gelontInitiatingUselonrId(dmelonvelonntOptStitch)).flatMap {
        caselon (Somelon(elonvelonntTypelon.MelonssagelonCrelonatelon), Somelon(uselonrId)) =>
          condition(uselonrId).relonscuelon {
            caselon NotFound =>
              Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("initiating uselonr not found"))
          }
        caselon (Nonelon, _) =>
          Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Dmelonvelonnt typelon is missing"))
        caselon (Somelon(elonvelonntTypelon.MelonssagelonCrelonatelon), _) =>
          Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("initiating uselonr id is missing"))
        caselon _ => Stitch.Falselon
      }

  privatelon delonf dmelonvelonntOccurrelondBelonforelonLastClelonarelondelonvelonnt(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    dmelonvelonntId: DmelonvelonntId,
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] = {
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convoId) =>
        val lastClelonarelondelonvelonntIdStitch =
          dmConvelonrsationSourcelon.gelontParticipantLastClelonarelondelonvelonntId(convoId, vielonwelonrId, vielonwelonrId)
        lastClelonarelondelonvelonntIdStitch.flatMap {
          caselon Somelon(lastClelonarelondelonvelonntId) => Stitch(dmelonvelonntId <= lastClelonarelondelonvelonntId)
          caselon _ =>
            Stitch.Falselon
        }
      caselon _ => Stitch.Falselon
    }
  }

  privatelon delonf dmelonvelonntOccurrelondBelonforelonJoinConvelonrsationelonvelonnt(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    dmelonvelonntId: DmelonvelonntId,
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] = {
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convoId) =>
        val joinConvelonrsationelonvelonntIdStitch =
          dmConvelonrsationSourcelon
            .gelontParticipantJoinConvelonrsationelonvelonntId(convoId, vielonwelonrId, vielonwelonrId)
        joinConvelonrsationelonvelonntIdStitch.flatMap {
          caselon Somelon(joinConvelonrsationelonvelonntId) => Stitch(dmelonvelonntId < joinConvelonrsationelonvelonntId)
          caselon _ => Stitch.Falselon
        }
      caselon _ => Stitch.Falselon
    }
  }

  privatelon delonf dmelonvelonntVielonwelonrIsDmConvelonrsationParticipant(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] = {
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convoId) =>
        dmConvelonrsationFelonaturelons.vielonwelonrIsDmConvelonrsationParticipant(convoId, Somelon(vielonwelonrId))
      caselon _ => Stitch.Truelon
    }
  }

  privatelon delonf dmelonvelonntIsDelonlelontelond(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    dmelonvelonntId: DmelonvelonntId
  ): Stitch[Boolelonan] =
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convoId) =>
        dmConvelonrsationSourcelon
          .gelontDelonlelontelonInfo(convoId, dmelonvelonntId).relonscuelon {
            caselon elon: java.lang.IllelongalArgumelonntelonxcelonption =>
              Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Invalid convelonrsation id"))
          }.flatMap {
            caselon Somelon(StorelondDelonlelontelon(Nonelon)) => Stitch.Truelon
            caselon _ => Stitch.Falselon
          }
      caselon _ => Stitch.Falselon
    }

  privatelon delonf dmelonvelonntIsHiddelonn(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    dmelonvelonntId: DmelonvelonntId
  ): Stitch[Boolelonan] =
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convoId) =>
        dmConvelonrsationSourcelon
          .gelontPelonrspelonctivalMelonssagelonInfo(convoId, dmelonvelonntId).relonscuelon {
            caselon elon: java.lang.IllelongalArgumelonntelonxcelonption =>
              Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Invalid convelonrsation id"))
          }.flatMap {
            caselon Somelon(StorelondPelonrspelonctivalMelonssagelonInfo(Somelon(hiddelonn), _)) if hiddelonn =>
              Stitch.Truelon
            caselon Somelon(StorelondPelonrspelonctivalMelonssagelonInfo(_, Somelon(spamStatelon)))
                if spamStatelon == PelonrspelonctivalSpamStatelon.Spam =>
              Stitch.Truelon
            caselon _ => Stitch.Falselon
          }
      caselon _ => Stitch.Falselon
    }

  privatelon delonf vielonwelonrIsDmelonvelonntInitiatingUselonr(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] =
    Stitch
      .join(
        dmelonvelonntSourcelon.gelontelonvelonntTypelon(dmelonvelonntOptStitch),
        dmelonvelonntSourcelon.gelontInitiatingUselonrId(dmelonvelonntOptStitch)).flatMap {
        caselon (
              Somelon(
                elonvelonntTypelon.TrustConvelonrsation | elonvelonntTypelon.CsFelonelondbackSubmittelond |
                elonvelonntTypelon.CsFelonelondbackDismisselond | elonvelonntTypelon.WelonlcomelonMelonssagelonCrelonatelon |
                elonvelonntTypelon.JoinConvelonrsation),
              Somelon(uselonrId)) =>
          Stitch(vielonwelonrId == uselonrId)
        caselon (
              Somelon(
                elonvelonntTypelon.TrustConvelonrsation | elonvelonntTypelon.CsFelonelondbackSubmittelond |
                elonvelonntTypelon.CsFelonelondbackDismisselond | elonvelonntTypelon.WelonlcomelonMelonssagelonCrelonatelon |
                elonvelonntTypelon.JoinConvelonrsation),
              Nonelon) =>
          Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Initiating uselonr id is missing"))
        caselon (Nonelon, _) =>
          Stitch.elonxcelonption(InvalidDmelonvelonntFelonaturelonelonxcelonption("Dmelonvelonnt typelon is missing"))
        caselon _ => Stitch.Truelon
      }

  privatelon delonf dmelonvelonntInOnelonToOnelonConvelonrsationWithUnavailablelonUselonr(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] =
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convelonrsationId) =>
        dmConvelonrsationFelonaturelons
          .dmConvelonrsationIsOnelonToOnelonConvelonrsation(convelonrsationId, Somelon(vielonwelonrId)).flatMap {
            isOnelonToOnelon =>
              if (isOnelonToOnelon) {
                Stitch
                  .join(
                    dmConvelonrsationFelonaturelons
                      .dmConvelonrsationHasSuspelonndelondParticipant(convelonrsationId, Somelon(vielonwelonrId)),
                    dmConvelonrsationFelonaturelons
                      .dmConvelonrsationHasDelonactivatelondParticipant(convelonrsationId, Somelon(vielonwelonrId)),
                    dmConvelonrsationFelonaturelons
                      .dmConvelonrsationHaselonraselondParticipant(convelonrsationId, Somelon(vielonwelonrId))
                  ).flatMap {
                    caselon (
                          convoParticipantIsSuspelonndelond,
                          convoParticipantIsDelonactivatelond,
                          convoParticipantIselonraselond) =>
                      Stitch.valuelon(
                        convoParticipantIsSuspelonndelond || convoParticipantIsDelonactivatelond || convoParticipantIselonraselond)
                  }
              } elonlselon {
                Stitch.Falselon
              }
          }
      caselon _ => Stitch.Falselon
    }

  privatelon delonf dmelonvelonntInOnelonToOnelonConvelonrsation(
    dmelonvelonntOptStitch: Stitch[Option[elonvelonnt]],
    vielonwelonrId: UselonrId
  ): Stitch[Boolelonan] =
    dmelonvelonntSourcelon.gelontConvelonrsationId(dmelonvelonntOptStitch).flatMap {
      caselon Somelon(convelonrsationId) =>
        dmConvelonrsationFelonaturelons
          .dmConvelonrsationIsOnelonToOnelonConvelonrsation(convelonrsationId, Somelon(vielonwelonrId))
      caselon _ => Stitch.Falselon
    }
}

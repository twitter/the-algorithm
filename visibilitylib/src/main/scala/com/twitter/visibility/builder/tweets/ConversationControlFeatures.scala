packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.twelonelontypielon.thriftscala.ConvelonrsationControl
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.buildelonr.uselonrs.RelonlationshipFelonaturelons
import com.twittelonr.visibility.common.InvitelondToConvelonrsationRelonpo
import com.twittelonr.visibility.felonaturelons.ConvelonrsationRootAuthorFollowsVielonwelonr
import com.twittelonr.visibility.felonaturelons.TwelonelontConvelonrsationVielonwelonrIsInvitelond
import com.twittelonr.visibility.felonaturelons.TwelonelontConvelonrsationVielonwelonrIsInvitelondViaRelonplyMelonntion
import com.twittelonr.visibility.felonaturelons.TwelonelontConvelonrsationVielonwelonrIsRootAuthor
import com.twittelonr.visibility.felonaturelons.TwelonelontHasByInvitationConvelonrsationControl
import com.twittelonr.visibility.felonaturelons.TwelonelontHasCommunityConvelonrsationControl
import com.twittelonr.visibility.felonaturelons.TwelonelontHasFollowelonrsConvelonrsationControl
import com.twittelonr.visibility.felonaturelons.VielonwelonrFollowsConvelonrsationRootAuthor

class ConvelonrsationControlFelonaturelons(
  relonlationshipFelonaturelons: RelonlationshipFelonaturelons,
  isInvitelondToConvelonrsationRelonpository: InvitelondToConvelonrsationRelonpo,
  statsReloncelonivelonr: StatsReloncelonivelonr) {

  privatelon[this] val scopelondStatsReloncelonivelonr = statsReloncelonivelonr.scopelon("convelonrsation_control_felonaturelons")

  privatelon[this] val relonquelonsts = scopelondStatsReloncelonivelonr.countelonr("relonquelonsts")

  privatelon[this] val twelonelontCommunityConvelonrsationRelonquelonst =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasCommunityConvelonrsationControl.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontByInvitationConvelonrsationRelonquelonst =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasByInvitationConvelonrsationControl.namelon).countelonr("relonquelonsts")
  privatelon[this] val twelonelontFollowelonrsConvelonrsationRelonquelonst =
    scopelondStatsReloncelonivelonr.scopelon(TwelonelontHasFollowelonrsConvelonrsationControl.namelon).countelonr("relonquelonsts")
  privatelon[this] val rootAuthorFollowsVielonwelonr =
    scopelondStatsReloncelonivelonr.scopelon(ConvelonrsationRootAuthorFollowsVielonwelonr.namelon).countelonr("relonquelonsts")
  privatelon[this] val vielonwelonrFollowsRootAuthor =
    scopelondStatsReloncelonivelonr.scopelon(VielonwelonrFollowsConvelonrsationRootAuthor.namelon).countelonr("relonquelonsts")

  delonf isCommunityConvelonrsation(convelonrsationControl: Option[ConvelonrsationControl]): Boolelonan =
    convelonrsationControl
      .collelonct {
        caselon _: ConvelonrsationControl.Community =>
          twelonelontCommunityConvelonrsationRelonquelonst.incr()
          truelon
      }.gelontOrelonlselon(falselon)

  delonf isByInvitationConvelonrsation(convelonrsationControl: Option[ConvelonrsationControl]): Boolelonan =
    convelonrsationControl
      .collelonct {
        caselon _: ConvelonrsationControl.ByInvitation =>
          twelonelontByInvitationConvelonrsationRelonquelonst.incr()
          truelon
      }.gelontOrelonlselon(falselon)

  delonf isFollowelonrsConvelonrsation(convelonrsationControl: Option[ConvelonrsationControl]): Boolelonan =
    convelonrsationControl
      .collelonct {
        caselon _: ConvelonrsationControl.Followelonrs =>
          twelonelontFollowelonrsConvelonrsationRelonquelonst.incr()
          truelon
      }.gelontOrelonlselon(falselon)

  delonf convelonrsationRootAuthorId(
    convelonrsationControl: Option[ConvelonrsationControl]
  ): Option[Long] =
    convelonrsationControl match {
      caselon Somelon(ConvelonrsationControl.Community(community)) =>
        Somelon(community.convelonrsationTwelonelontAuthorId)
      caselon Somelon(ConvelonrsationControl.ByInvitation(byInvitation)) =>
        Somelon(byInvitation.convelonrsationTwelonelontAuthorId)
      caselon Somelon(ConvelonrsationControl.Followelonrs(followelonrs)) =>
        Somelon(followelonrs.convelonrsationTwelonelontAuthorId)
      caselon _ => Nonelon
    }

  delonf vielonwelonrIsRootAuthor(
    convelonrsationControl: Option[ConvelonrsationControl],
    vielonwelonrIdOpt: Option[Long]
  ): Boolelonan =
    (convelonrsationRootAuthorId(convelonrsationControl), vielonwelonrIdOpt) match {
      caselon (Somelon(rootAuthorId), Somelon(vielonwelonrId)) if rootAuthorId == vielonwelonrId => truelon
      caselon _ => falselon
    }

  delonf vielonwelonrIsInvitelond(
    convelonrsationControl: Option[ConvelonrsationControl],
    vielonwelonrId: Option[Long]
  ): Boolelonan = {
    val invitelondUselonrIds = convelonrsationControl match {
      caselon Somelon(ConvelonrsationControl.Community(community)) =>
        community.invitelondUselonrIds
      caselon Somelon(ConvelonrsationControl.ByInvitation(byInvitation)) =>
        byInvitation.invitelondUselonrIds
      caselon Somelon(ConvelonrsationControl.Followelonrs(followelonrs)) =>
        followelonrs.invitelondUselonrIds
      caselon _ => Selonq()
    }

    vielonwelonrId.elonxists(invitelondUselonrIds.contains(_))
  }

  delonf convelonrsationAuthorFollows(
    convelonrsationControl: Option[ConvelonrsationControl],
    vielonwelonrId: Option[Long]
  ): Stitch[Boolelonan] = {
    val convelonrsationAuthorId = convelonrsationControl.collelonct {
      caselon ConvelonrsationControl.Community(community) =>
        community.convelonrsationTwelonelontAuthorId
    }

    convelonrsationAuthorId match {
      caselon Somelon(authorId) =>
        rootAuthorFollowsVielonwelonr.incr()
        relonlationshipFelonaturelons.authorFollowsVielonwelonr(authorId, vielonwelonrId)
      caselon Nonelon =>
        Stitch.Falselon
    }
  }

  delonf followsConvelonrsationAuthor(
    convelonrsationControl: Option[ConvelonrsationControl],
    vielonwelonrId: Option[Long]
  ): Stitch[Boolelonan] = {
    val convelonrsationAuthorId = convelonrsationControl.collelonct {
      caselon ConvelonrsationControl.Followelonrs(followelonrs) =>
        followelonrs.convelonrsationTwelonelontAuthorId
    }

    convelonrsationAuthorId match {
      caselon Somelon(authorId) =>
        vielonwelonrFollowsRootAuthor.incr()
        relonlationshipFelonaturelons.vielonwelonrFollowsAuthor(authorId, vielonwelonrId)
      caselon Nonelon =>
        Stitch.Falselon
    }
  }

  delonf vielonwelonrIsInvitelondViaRelonplyMelonntion(
    twelonelont: Twelonelont,
    vielonwelonrIdOpt: Option[Long]
  ): Stitch[Boolelonan] = {
    val convelonrsationIdOpt: Option[Long] = twelonelont.convelonrsationControl match {
      caselon Somelon(ConvelonrsationControl.Community(community))
          if community.invitelonViaMelonntion.contains(truelon) =>
        twelonelont.corelonData.flatMap(_.convelonrsationId)
      caselon Somelon(ConvelonrsationControl.ByInvitation(invitation))
          if invitation.invitelonViaMelonntion.contains(truelon) =>
        twelonelont.corelonData.flatMap(_.convelonrsationId)
      caselon Somelon(ConvelonrsationControl.Followelonrs(followelonrs))
          if followelonrs.invitelonViaMelonntion.contains(truelon) =>
        twelonelont.corelonData.flatMap(_.convelonrsationId)
      caselon _ => Nonelon
    }

    (convelonrsationIdOpt, vielonwelonrIdOpt) match {
      caselon (Somelon(convelonrsationId), Somelon(vielonwelonrId)) =>
        isInvitelondToConvelonrsationRelonpository(convelonrsationId, vielonwelonrId)
      caselon _ => Stitch.Falselon
    }
  }

  delonf forTwelonelont(twelonelont: Twelonelont, vielonwelonrId: Option[Long]): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    relonquelonsts.incr()
    val cc = twelonelont.convelonrsationControl

    _.withConstantFelonaturelon(TwelonelontHasCommunityConvelonrsationControl, isCommunityConvelonrsation(cc))
      .withConstantFelonaturelon(TwelonelontHasByInvitationConvelonrsationControl, isByInvitationConvelonrsation(cc))
      .withConstantFelonaturelon(TwelonelontHasFollowelonrsConvelonrsationControl, isFollowelonrsConvelonrsation(cc))
      .withConstantFelonaturelon(TwelonelontConvelonrsationVielonwelonrIsRootAuthor, vielonwelonrIsRootAuthor(cc, vielonwelonrId))
      .withConstantFelonaturelon(TwelonelontConvelonrsationVielonwelonrIsInvitelond, vielonwelonrIsInvitelond(cc, vielonwelonrId))
      .withFelonaturelon(ConvelonrsationRootAuthorFollowsVielonwelonr, convelonrsationAuthorFollows(cc, vielonwelonrId))
      .withFelonaturelon(VielonwelonrFollowsConvelonrsationRootAuthor, followsConvelonrsationAuthor(cc, vielonwelonrId))
      .withFelonaturelon(
        TwelonelontConvelonrsationVielonwelonrIsInvitelondViaRelonplyMelonntion,
        vielonwelonrIsInvitelondViaRelonplyMelonntion(twelonelont, vielonwelonrId))

  }
}

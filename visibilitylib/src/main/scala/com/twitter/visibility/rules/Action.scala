package com.tw ter.v s b l y.rules

 mport com.tw ter.datatools.ent yserv ce.ent  es.thr ftscala.Fleet nterst  al
 mport com.tw ter.scrooge.Thr ftStruct
 mport com.tw ter.v s b l y.common.act ons.Local zed ssage
 mport com.tw ter.v s b l y.common.act ons._
 mport com.tw ter.v s b l y.common.act ons.converter.scala.AppealableReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Avo dReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Compl anceT etNot ceEventTypeConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.DownrankHo T  l neReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.DropReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala. nterst  alReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.L m edAct onsPol cyConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.L m edEngage ntReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Local zed ssageConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Soft ntervent onD splayTypeConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.Soft ntervent onReasonConverter
 mport com.tw ter.v s b l y.common.act ons.converter.scala.TombstoneReasonConverter
 mport com.tw ter.v s b l y.features.Feature
 mport com.tw ter.v s b l y.logg ng.thr ftscala. althAct onType
 mport com.tw ter.v s b l y.models.V olat onLevel
 mport com.tw ter.v s b l y.strato.thr ftscala.NudgeAct onType.EnumUnknownNudgeAct onType
 mport com.tw ter.v s b l y.strato.thr ftscala.{Nudge => StratoNudge}
 mport com.tw ter.v s b l y.strato.thr ftscala.{NudgeAct on => StratoNudgeAct on}
 mport com.tw ter.v s b l y.strato.thr ftscala.{NudgeAct onType => StratoNudgeAct onType}
 mport com.tw ter.v s b l y.strato.thr ftscala.{NudgeAct onPayload => StratoNudgeAct onPayload}
 mport com.tw ter.v s b l y.thr ftscala
 mport com.tw ter.v s b l y.ut l.Nam ngUt ls

sealed tra  Act on {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
  lazy val fullNa : Str ng = Nam ngUt ls.getFr endlyNa (t )

  val sever y:  nt
  def toAct onThr ft(): thr ftscala.Act on

  def  sComposable: Boolean = false

  def to althAct onTypeThr ft: Opt on[ althAct onType]
}

sealed tra  Reason {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

sealed abstract class Act onW hReason(reason: Reason) extends Act on {
  overr de lazy val fullNa : Str ng = s"${t .na }/${reason.na }"
}

object Reason {

  case object Bounce extends Reason

  case object V e rReportedAuthor extends Reason
  case object V e rReportedT et extends Reason

  case object Deact vatedAuthor extends Reason
  case object OffboardedAuthor extends Reason
  case object ErasedAuthor extends Reason
  case object ProtectedAuthor extends Reason
  case object SuspendedAuthor extends Reason
  case object V e r sUn nt oned extends Reason

  case object Nsfw extends Reason
  case object Nsfw d a extends Reason
  case object NsfwV e r sUnderage extends Reason
  case object NsfwV e rHasNoStatedAge extends Reason
  case object NsfwLoggedOut extends Reason
  case object Poss blyUndes rable extends Reason

  case object AbuseEp sod c extends Reason
  case object AbuseEp sod cEnc ageSelfHarm extends Reason
  case object AbuseEp sod cHatefulConduct extends Reason
  case object AbuseGlor f cat onOfV olence extends Reason
  case object AbuseGratu ousGore extends Reason
  case object AbuseMobHarass nt extends Reason
  case object AbuseMo ntOfDeathOrDeceasedUser extends Reason
  case object AbusePr vate nformat on extends Reason
  case object AbuseR ghtToPr vacy extends Reason
  case object AbuseThreatToExpose extends Reason
  case object AbuseV olentSexualConduct extends Reason
  case object AbuseV olentThreatHatefulConduct extends Reason
  case object AbuseV olentThreatOrBounty extends Reason

  case object MutedKeyword extends Reason
  case object Unspec f ed extends Reason

  case object UntrustedUrl extends Reason

  case object SpamReplyDownRank extends Reason

  case object LowQual yT et extends Reason

  case object LowQual y nt on extends Reason

  case object SpamH ghRecallT et extends Reason

  case object T etLabelDupl cateContent extends Reason

  case object T etLabelDupl cate nt on extends Reason

  case object PdnaT et extends Reason

  case object T etLabeledSpam extends Reason

  case object OneOff extends Reason
  case object Vot ngM s nformat on extends Reason
  case object HackedMater als extends Reason
  case object Scams extends Reason
  case object PlatformMan pulat on extends Reason

  case object F rstPageSearchResult extends Reason

  case object M s nfoC v c extends Reason
  case object M s nfoCr s s extends Reason
  case object M s nfoGener c extends Reason
  case object M s nfo d cal extends Reason
  case object M slead ng extends Reason
  case object Exclus veT et extends Reason
  case object Commun yNotA mber extends Reason
  case object Commun yT etH dden extends Reason
  case object Commun yT etCommun y sSuspended extends Reason
  case object Commun yT etAuthorRemoved extends Reason
  case object  nternalPromotedContent extends Reason
  case object TrustedFr endsT et extends Reason
  case object Tox c y extends Reason
  case object StaleT et extends Reason
  case object DmcaW h ld extends Reason
  case object LegalDemandsW h ld extends Reason
  case object LocalLawsW h ld extends Reason
  case object HatefulConduct extends Reason
  case object Abus veBehav or extends Reason

  case object NotSupportedOnDev ce extends Reason

  case object  p Develop ntOnly extends Reason
  case object  nterst  alDevelop ntOnly extends Reason

  case class FosnrReason(appealableReason: AppealableReason) extends Reason

  def toDropReason(reason: Reason): Opt on[DropReason] =
    reason match {
      case AuthorBlocksV e r => So (DropReason.AuthorBlocksV e r)
      case Commun yT etH dden => So (DropReason.Commun yT etH dden)
      case Commun yT etCommun y sSuspended => So (DropReason.Commun yT etCommun y sSuspended)
      case DmcaW h ld => So (DropReason.DmcaW h ld)
      case Exclus veT et => So (DropReason.Exclus veT et)
      case  nternalPromotedContent => So (DropReason. nternalPromotedContent)
      case LegalDemandsW h ld => So (DropReason.LegalDemandsW h ld)
      case LocalLawsW h ld => So (DropReason.LocalLawsW h ld)
      case Nsfw => So (DropReason.NsfwAuthor)
      case NsfwLoggedOut => So (DropReason.NsfwLoggedOut)
      case NsfwV e rHasNoStatedAge => So (DropReason.NsfwV e rHasNoStatedAge)
      case NsfwV e r sUnderage => So (DropReason.NsfwV e r sUnderage)
      case ProtectedAuthor => So (DropReason.ProtectedAuthor)
      case StaleT et => So (DropReason.StaleT et)
      case SuspendedAuthor => So (DropReason.SuspendedAuthor)
      case Unspec f ed => So (DropReason.Unspec f ed)
      case V e rBlocksAuthor => So (DropReason.V e rBlocksAuthor)
      case V e rHardMutedAuthor => So (DropReason.V e rMutesAuthor)
      case V e rMutesAuthor => So (DropReason.V e rMutesAuthor)
      case TrustedFr endsT et => So (DropReason.TrustedFr endsT et)
      case _ => So (DropReason.Unspec f ed)
    }

  def fromDropReason(dropReason: DropReason): Reason =
    dropReason match {
      case DropReason.AuthorBlocksV e r => AuthorBlocksV e r
      case DropReason.Commun yT etH dden => Commun yT etH dden
      case DropReason.Commun yT etCommun y sSuspended => Commun yT etCommun y sSuspended
      case DropReason.DmcaW h ld => DmcaW h ld
      case DropReason.Exclus veT et => Exclus veT et
      case DropReason. nternalPromotedContent =>  nternalPromotedContent
      case DropReason.LegalDemandsW h ld => LegalDemandsW h ld
      case DropReason.LocalLawsW h ld => LocalLawsW h ld
      case DropReason.NsfwAuthor => Nsfw
      case DropReason.NsfwLoggedOut => NsfwLoggedOut
      case DropReason.NsfwV e rHasNoStatedAge => NsfwV e rHasNoStatedAge
      case DropReason.NsfwV e r sUnderage => NsfwV e r sUnderage
      case DropReason.ProtectedAuthor => ProtectedAuthor
      case DropReason.StaleT et => StaleT et
      case DropReason.SuspendedAuthor => SuspendedAuthor
      case DropReason.V e rBlocksAuthor => V e rBlocksAuthor
      case DropReason.V e rMutesAuthor => V e rMutesAuthor
      case DropReason.TrustedFr endsT et => TrustedFr endsT et
      case DropReason.Unspec f ed => Unspec f ed
    }

  def toAppealableReason(reason: Reason, v olat onLevel: V olat onLevel): Opt on[AppealableReason] =
    reason match {
      case HatefulConduct => So (AppealableReason.HatefulConduct(v olat onLevel.level))
      case Abus veBehav or => So (AppealableReason.Abus veBehav or(v olat onLevel.level))
      case _ => So (AppealableReason.Unspec f ed(v olat onLevel.level))
    }

  def fromAppealableReason(appealableReason: AppealableReason): Reason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) => HatefulConduct
      case AppealableReason.Abus veBehav or(level) => Abus veBehav or
      case AppealableReason.Unspec f ed(level) => Unspec f ed
    }

  def toSoft ntervent onReason(appealableReason: AppealableReason): Soft ntervent onReason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) =>
        Soft ntervent onReason.FosnrReason(appealableReason)
      case AppealableReason.Abus veBehav or(level) =>
        Soft ntervent onReason.FosnrReason(appealableReason)
      case AppealableReason.Unspec f ed(level) =>
        Soft ntervent onReason.FosnrReason(appealableReason)
    }

  def toL m edEngage ntReason(appealableReason: AppealableReason): L m edEngage ntReason =
    appealableReason match {
      case AppealableReason.HatefulConduct(level) =>
        L m edEngage ntReason.FosnrReason(appealableReason)
      case AppealableReason.Abus veBehav or(level) =>
        L m edEngage ntReason.FosnrReason(appealableReason)
      case AppealableReason.Unspec f ed(level) =>
        L m edEngage ntReason.FosnrReason(appealableReason)
    }

  val NSFW_MED A: Set[Reason] = Set(Nsfw, Nsfw d a)

  def to nterst  alReason(reason: Reason): Opt on[ nterst  alReason] =
    reason match {
      case r  f NSFW_MED A.conta ns(r) => So ( nterst  alReason.Conta nsNsfw d a)
      case Poss blyUndes rable => So ( nterst  alReason.Poss blyUndes rable)
      case MutedKeyword => So ( nterst  alReason.Matc sMutedKeyword(""))
      case V e rReportedAuthor => So ( nterst  alReason.V e rReportedAuthor)
      case V e rReportedT et => So ( nterst  alReason.V e rReportedT et)
      case V e rBlocksAuthor => So ( nterst  alReason.V e rBlocksAuthor)
      case V e rMutesAuthor => So ( nterst  alReason.V e rMutesAuthor)
      case V e rHardMutedAuthor => So ( nterst  alReason.V e rMutesAuthor)
      case  nterst  alDevelop ntOnly => So ( nterst  alReason.Develop ntOnly)
      case DmcaW h ld => So ( nterst  alReason.DmcaW h ld)
      case LegalDemandsW h ld => So ( nterst  alReason.LegalDemandsW h ld)
      case LocalLawsW h ld => So ( nterst  alReason.LocalLawsW h ld)
      case HatefulConduct => So ( nterst  alReason.HatefulConduct)
      case Abus veBehav or => So ( nterst  alReason.Abus veBehav or)
      case FosnrReason(appealableReason) => So ( nterst  alReason.FosnrReason(appealableReason))
      case _ => None
    }

  def from nterst  alReason( nterst  alReason:  nterst  alReason): Reason =
     nterst  alReason match {
      case  nterst  alReason.Conta nsNsfw d a => Reason.Nsfw d a
      case  nterst  alReason.Poss blyUndes rable => Reason.Poss blyUndes rable
      case  nterst  alReason.Matc sMutedKeyword(_) => Reason.MutedKeyword
      case  nterst  alReason.V e rReportedAuthor => Reason.V e rReportedAuthor
      case  nterst  alReason.V e rReportedT et => Reason.V e rReportedT et
      case  nterst  alReason.V e rBlocksAuthor => Reason.V e rBlocksAuthor
      case  nterst  alReason.V e rMutesAuthor => Reason.V e rMutesAuthor
      case  nterst  alReason.Develop ntOnly => Reason. nterst  alDevelop ntOnly
      case  nterst  alReason.DmcaW h ld => Reason.DmcaW h ld
      case  nterst  alReason.LegalDemandsW h ld => Reason.LegalDemandsW h ld
      case  nterst  alReason.LocalLawsW h ld => Reason.LocalLawsW h ld
      case  nterst  alReason.HatefulConduct => Reason.HatefulConduct
      case  nterst  alReason.Abus veBehav or => Reason.Abus veBehav or
      case  nterst  alReason.FosnrReason(reason) => Reason.fromAppealableReason(reason)
    }

}

sealed tra  Ep aph {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

object Ep aph {

  case object Unava lable extends Ep aph

  case object Blocked extends Ep aph
  case object BlockedBy extends Ep aph
  case object Reported extends Ep aph

  case object BounceDeleted extends Ep aph
  case object Deleted extends Ep aph
  case object NotFound extends Ep aph
  case object Publ c nterest extends Ep aph

  case object Bounced extends Ep aph
  case object Protected extends Ep aph
  case object Suspended extends Ep aph
  case object Offboarded extends Ep aph
  case object Deact vated extends Ep aph

  case object MutedKeyword extends Ep aph
  case object Underage extends Ep aph
  case object NoStatedAge extends Ep aph
  case object LoggedOutAge extends Ep aph
  case object SuperFollowsContent extends Ep aph

  case object Moderated extends Ep aph
  case object ForE rgencyUseOnly extends Ep aph
  case object Unava lableW houtL nk extends Ep aph
  case object Commun yT etH dden extends Ep aph
  case object Commun yT et mberRemoved extends Ep aph
  case object Commun yT etCommun y sSuspended extends Ep aph

  case object UserSuspended extends Ep aph

  case object Develop ntOnly extends Ep aph

  case object Adult d a extends Ep aph
  case object V olent d a extends Ep aph
  case object Ot rSens  ve d a extends Ep aph

  case object DmcaW h ld d a extends Ep aph
  case object LegalDemandsW h ld d a extends Ep aph
  case object LocalLawsW h ld d a extends Ep aph

  case object Tox cReplyF ltered extends Ep aph
}

sealed tra   s nterst  al {
  def to nterst  alThr ftWrapper(): thr ftscala.Any nterst  al
  def to nterst  alThr ft(): Thr ftStruct
}

sealed tra   sAppealable {
  def toAppealableThr ft(): thr ftscala.Appealable
}

sealed tra   sL m edEngage nts {
  def pol cy: Opt on[L m edAct onsPol cy]
  def getL m edEngage ntReason: L m edEngage ntReason
}

object  sL m edEngage nts {
  def unapply(
     le:  sL m edEngage nts
  ): Opt on[(Opt on[L m edAct onsPol cy], L m edEngage ntReason)] = {
    So (( le.pol cy,  le.getL m edEngage ntReason))
  }
}

sealed abstract class Act onW hEp aph(ep aph: Ep aph) extends Act on {
  overr de lazy val fullNa : Str ng = s"${t .na }/${ep aph.na }"
}

case class Appealable(
  reason: Reason,
  v olat onLevel: V olat onLevel,
  local zed ssage: Opt on[Local zed ssage] = None)
    extends Act onW hReason(reason)
    w h  sAppealable {

  overr de val sever y:  nt = 17
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Appealable(toAppealableThr ft())

  overr de def toAppealableThr ft(): thr ftscala.Appealable =
    thr ftscala.Appealable(
      Reason.toAppealableReason(reason, v olat onLevel).map(AppealableReasonConverter.toThr ft),
      local zed ssage.map(Local zed ssageConverter.toThr ft)
    )

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.Appealable)
}

case class Drop(reason: Reason, appl cableCountr es: Opt on[Seq[Str ng]] = None)
    extends Act onW hReason(reason) {

  overr de val sever y:  nt = 16
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Drop(
      thr ftscala.Drop(
        Reason.toDropReason(reason).map(DropReasonConverter.toThr ft),
        appl cableCountr es
      ))

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Drop)
}

case class  nterst  al(
  reason: Reason,
  local zed ssage: Opt on[Local zed ssage] = None,
  appl cableCountr es: Opt on[Seq[Str ng]] = None)
    extends Act onW hReason(reason)
    w h  s nterst  al {

  overr de val sever y:  nt = 10
  overr de def to nterst  alThr ftWrapper(): thr ftscala.Any nterst  al =
    thr ftscala.Any nterst  al. nterst  al(
      to nterst  alThr ft()
    )

  overr de def to nterst  alThr ft(): thr ftscala. nterst  al =
    thr ftscala. nterst  al(
      Reason.to nterst  alReason(reason).map( nterst  alReasonConverter.toThr ft),
      local zed ssage.map(Local zed ssageConverter.toThr ft)
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on. nterst  al(to nterst  alThr ft())

  def to d aAct onThr ft(): thr ftscala. d aAct on =
    thr ftscala. d aAct on. nterst  al(to nterst  alThr ft())

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.T et nterst  al)
}

case class  nterst  alL m edEngage nts(
  reason: Reason,
  l m edEngage ntReason: Opt on[L m edEngage ntReason],
  local zed ssage: Opt on[Local zed ssage] = None,
  pol cy: Opt on[L m edAct onsPol cy] = None)
    extends Act onW hReason(reason)
    w h  s nterst  al
    w h  sL m edEngage nts {

  overr de val sever y:  nt = 11
  overr de def to nterst  alThr ftWrapper(): thr ftscala.Any nterst  al =
    thr ftscala.Any nterst  al. nterst  alL m edEngage nts(
      to nterst  alThr ft()
    )

  overr de def to nterst  alThr ft(): thr ftscala. nterst  alL m edEngage nts =
    thr ftscala. nterst  alL m edEngage nts(
      l m edEngage ntReason.map(L m edEngage ntReasonConverter.toThr ft),
      local zed ssage.map(Local zed ssageConverter.toThr ft)
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on. nterst  alL m edEngage nts(to nterst  alThr ft())

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.L m edEngage nts)

  def getL m edEngage ntReason: L m edEngage ntReason = l m edEngage ntReason.getOrElse(
    L m edEngage ntReason.NonCompl ant
  )
}

case object Allow extends Act on {

  overr de val sever y:  nt = -1
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Allow(thr ftscala.Allow())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = None
}

case object NotEvaluated extends Act on {

  overr de val sever y:  nt = -1
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.NotEvaluated(thr ftscala.NotEvaluated())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = None
}

case class Tombstone(ep aph: Ep aph, appl cableCountryCodes: Opt on[Seq[Str ng]] = None)
    extends Act onW hEp aph(ep aph) {

  overr de val sever y:  nt = 15
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Tombstone(thr ftscala.Tombstone())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Tombstone)
}

case class Local zedTombstone(reason: TombstoneReason,  ssage: Local zed ssage) extends Act on {
  overr de lazy val fullNa : Str ng = s"${t .na }/${Nam ngUt ls.getFr endlyNa (reason)}"

  overr de val sever y:  nt = 15
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Tombstone(
      thr ftscala.Tombstone(
        reason = TombstoneReasonConverter.toThr ft(So (reason)),
         ssage = So (Local zed ssageConverter.toThr ft( ssage))
      ))

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Tombstone)
}

case class DownrankHo T  l ne(reason: Opt on[DownrankHo T  l neReason]) extends Act on {

  overr de val sever y:  nt = 9
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.DownrankHo T  l ne(toDownrankThr ft())

  def toDownrankThr ft(): thr ftscala.DownrankHo T  l ne =
    thr ftscala.DownrankHo T  l ne(
      reason.map(DownrankHo T  l neReasonConverter.toThr ft)
    )

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Downrank)
}

case class Avo d(avo dReason: Opt on[Avo dReason] = None) extends Act on {

  overr de val sever y:  nt = 1
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Avo d(toAvo dThr ft())

  def toAvo dThr ft(): thr ftscala.Avo d =
    thr ftscala.Avo d(
      avo dReason.map(Avo dReasonConverter.toThr ft)
    )

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Avo d)
}

case object Downrank extends Act on {

  overr de val sever y:  nt = 0
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Downrank(thr ftscala.Downrank())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So ( althAct onType.Downrank)
}

case object Conversat onSect onLowQual y extends Act on {

  overr de val sever y:  nt = 4
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Conversat onSect onLowQual y(thr ftscala.Conversat onSect onLowQual y())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.Conversat onSect onLowQual y)
}

case object Conversat onSect onAbus veQual y extends Act on {

  overr de val sever y:  nt = 5
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Conversat onSect onAbus veQual y(
      thr ftscala.Conversat onSect onAbus veQual y())

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.Conversat onSect onAbus veQual y)

  def toConversat onSect onAbus veQual yThr ft(): thr ftscala.Conversat onSect onAbus veQual y =
    thr ftscala.Conversat onSect onAbus veQual y()
}

case class L m edEngage nts(
  reason: L m edEngage ntReason,
  pol cy: Opt on[L m edAct onsPol cy] = None)
    extends Act on
    w h  sL m edEngage nts {

  overr de val sever y:  nt = 6
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.L m edEngage nts(toL m edEngage ntsThr ft())

  def toL m edEngage ntsThr ft(): thr ftscala.L m edEngage nts =
    thr ftscala.L m edEngage nts(
      So (L m edEngage ntReasonConverter.toThr ft(reason)),
      pol cy.map(L m edAct onsPol cyConverter.toThr ft),
      So (reason.toL m edAct onsStr ng)
    )

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.L m edEngage nts)

  def getL m edEngage ntReason: L m edEngage ntReason = reason
}

case class E rgencyDynam c nterst  al(
  copy: Str ng,
  l nkOpt: Opt on[Str ng],
  local zed ssage: Opt on[Local zed ssage] = None,
  pol cy: Opt on[L m edAct onsPol cy] = None)
    extends Act on
    w h  s nterst  al
    w h  sL m edEngage nts {

  overr de val sever y:  nt = 11
  overr de def to nterst  alThr ftWrapper(): thr ftscala.Any nterst  al =
    thr ftscala.Any nterst  al.E rgencyDynam c nterst  al(
      to nterst  alThr ft()
    )

  overr de def to nterst  alThr ft(): thr ftscala.E rgencyDynam c nterst  al =
    thr ftscala.E rgencyDynam c nterst  al(
      copy,
      l nkOpt,
      local zed ssage.map(Local zed ssageConverter.toThr ft)
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.E rgencyDynam c nterst  al(to nterst  alThr ft())

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.T et nterst  al)

  def getL m edEngage ntReason: L m edEngage ntReason = L m edEngage ntReason.NonCompl ant
}

case class Soft ntervent on(
  reason: Soft ntervent onReason,
  engage ntNudge: Boolean,
  suppressAutoplay: Boolean,
  warn ng: Opt on[Str ng] = None,
  deta lsUrl: Opt on[Str ng] = None,
  d splayType: Opt on[Soft ntervent onD splayType] = None,
  fleet nterst  al: Opt on[Fleet nterst  al] = None)
    extends Act on {

  overr de val sever y:  nt = 7
  def toSoft ntervent onThr ft(): thr ftscala.Soft ntervent on =
    thr ftscala.Soft ntervent on(
      So (Soft ntervent onReasonConverter.toThr ft(reason)),
      engage ntNudge = So (engage ntNudge),
      suppressAutoplay = So (suppressAutoplay),
      warn ng = warn ng,
      deta lsUrl = deta lsUrl,
      d splayType = Soft ntervent onD splayTypeConverter.toThr ft(d splayType)
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Soft ntervent on(toSoft ntervent onThr ft())

  overr de def  sComposable: Boolean = true

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.Soft ntervent on)
}

case class T et nterst  al(
   nterst  al: Opt on[ s nterst  al],
  soft ntervent on: Opt on[Soft ntervent on],
  l m edEngage nts: Opt on[L m edEngage nts],
  downrank: Opt on[DownrankHo T  l ne],
  avo d: Opt on[Avo d],
   d a nterst  al: Opt on[ nterst  al] = None,
  t etV s b l yNudge: Opt on[T etV s b l yNudge] = None,
  abus veQual y: Opt on[Conversat onSect onAbus veQual y.type] = None,
  appealable: Opt on[Appealable] = None)
    extends Act on {

  overr de val sever y:  nt = 12
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.T et nterst  al(
      thr ftscala.T et nterst  al(
         nterst  al.map(_.to nterst  alThr ftWrapper()),
        soft ntervent on.map(_.toSoft ntervent onThr ft()),
        l m edEngage nts.map(_.toL m edEngage ntsThr ft()),
        downrank.map(_.toDownrankThr ft()),
        avo d.map(_.toAvo dThr ft()),
         d a nterst  al.map(_.to d aAct onThr ft()),
        t etV s b l yNudge.map(_.toT etV sb l yNudgeThr ft()),
        abus veQual y.map(_.toConversat onSect onAbus veQual yThr ft()),
        appealable.map(_.toAppealableThr ft())
      )
    )

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = So (
     althAct onType.T et nterst  al)
}

sealed tra  Local zedNudgeAct onType
object Local zedNudgeAct onType {
  case object Reply extends Local zedNudgeAct onType
  case object Ret et extends Local zedNudgeAct onType
  case object L ke extends Local zedNudgeAct onType
  case object Share extends Local zedNudgeAct onType
  case object Unspec f ed extends Local zedNudgeAct onType

  def toThr ft(
    local zedNudgeAct onType: Local zedNudgeAct onType
  ): thr ftscala.T etV s b l yNudgeAct onType =
    local zedNudgeAct onType match {
      case Reply => thr ftscala.T etV s b l yNudgeAct onType.Reply
      case Ret et => thr ftscala.T etV s b l yNudgeAct onType.Ret et
      case L ke => thr ftscala.T etV s b l yNudgeAct onType.L ke
      case Share => thr ftscala.T etV s b l yNudgeAct onType.Share
      case Unspec f ed =>
        thr ftscala.T etV s b l yNudgeAct onType.EnumUnknownT etV s b l yNudgeAct onType(5)
    }

  def fromStratoThr ft(stratoNudgeAct onType: StratoNudgeAct onType): Local zedNudgeAct onType =
    stratoNudgeAct onType match {
      case StratoNudgeAct onType.Reply => Reply
      case StratoNudgeAct onType.Ret et => Ret et
      case StratoNudgeAct onType.L ke => L ke
      case StratoNudgeAct onType.Share => Share
      case EnumUnknownNudgeAct onType(_) => Unspec f ed
    }
}

case class Local zedNudgeAct onPayload(
   ad ng: Opt on[Str ng],
  sub ad ng: Opt on[Str ng],
   conNa : Opt on[Str ng],
  ctaT le: Opt on[Str ng],
  ctaUrl: Opt on[Str ng],
  postCtaText: Opt on[Str ng]) {

  def toThr ft(): thr ftscala.T etV s b l yNudgeAct onPayload = {
    thr ftscala.T etV s b l yNudgeAct onPayload(
       ad ng =  ad ng,
      sub ad ng = sub ad ng,
       conNa  =  conNa ,
      ctaT le = ctaT le,
      ctaUrl = ctaUrl,
      postCtaText = postCtaText
    )
  }
}

object Local zedNudgeAct onPayload {
  def fromStratoThr ft(
    stratoNudgeAct onPayload: StratoNudgeAct onPayload
  ): Local zedNudgeAct onPayload =
    Local zedNudgeAct onPayload(
       ad ng = stratoNudgeAct onPayload. ad ng,
      sub ad ng = stratoNudgeAct onPayload.sub ad ng,
       conNa  = stratoNudgeAct onPayload. conNa ,
      ctaT le = stratoNudgeAct onPayload.ctaT le,
      ctaUrl = stratoNudgeAct onPayload.ctaUrl,
      postCtaText = stratoNudgeAct onPayload.postCtaText
    )
}

case class Local zedNudgeAct on(
  nudgeAct onType: Local zedNudgeAct onType,
  nudgeAct onPayload: Opt on[Local zedNudgeAct onPayload]) {
  def toThr ft(): thr ftscala.T etV s b l yNudgeAct on = {
    thr ftscala.T etV s b l yNudgeAct on(
      t etV s b l ynudgeAct onType = Local zedNudgeAct onType.toThr ft(nudgeAct onType),
      t etV s b l yNudgeAct onPayload = nudgeAct onPayload.map(_.toThr ft)
    )
  }
}

object Local zedNudgeAct on {
  def fromStratoThr ft(stratoNudgeAct on: StratoNudgeAct on): Local zedNudgeAct on =
    Local zedNudgeAct on(
      nudgeAct onType =
        Local zedNudgeAct onType.fromStratoThr ft(stratoNudgeAct on.nudgeAct onType),
      nudgeAct onPayload =
        stratoNudgeAct on.nudgeAct onPayload.map(Local zedNudgeAct onPayload.fromStratoThr ft)
    )
}

case class Local zedNudge(local zedNudgeAct ons: Seq[Local zedNudgeAct on])

case object Local zedNudge {
  def fromStratoThr ft(stratoNudge: StratoNudge): Local zedNudge =
    Local zedNudge(local zedNudgeAct ons =
      stratoNudge.nudgeAct ons.map(Local zedNudgeAct on.fromStratoThr ft))
}

case class T etV s b l yNudge(
  reason: T etV s b l yNudgeReason,
  local zedNudge: Opt on[Local zedNudge] = None)
    extends Act on {

  overr de val sever y:  nt = 3
  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.T etV s b l yNudge(
      local zedNudge match {
        case So (nudge) =>
          thr ftscala.T etV s b l yNudge(
            t etV s b l yNudgeAct ons = So (nudge.local zedNudgeAct ons.map(_.toThr ft()))
          )
        case _ => thr ftscala.T etV s b l yNudge(t etV s b l yNudgeAct ons = None)
      }
    )

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] =
    So ( althAct onType.T etV s b l yNudge)

  def toT etV sb l yNudgeThr ft(): thr ftscala.T etV s b l yNudge =
    thr ftscala.T etV s b l yNudge(t etV s b l yNudgeAct ons =
      local zedNudge.map(_.local zedNudgeAct ons.map(_.toThr ft())))
}

tra  BaseCompl anceT etNot ce {
  val compl anceT etNot ceEventType: Compl anceT etNot ceEventType
  val deta ls: Opt on[Str ng]
  val extendedDeta lsUrl: Opt on[Str ng]
}

case class Compl anceT etNot cePreEnr ch nt(
  reason: Reason,
  compl anceT etNot ceEventType: Compl anceT etNot ceEventType,
  deta ls: Opt on[Str ng] = None,
  extendedDeta lsUrl: Opt on[Str ng] = None)
    extends Act on
    w h BaseCompl anceT etNot ce {

  overr de val sever y:  nt = 2
  def toCompl anceT etNot ceThr ft(): thr ftscala.Compl anceT etNot ce =
    thr ftscala.Compl anceT etNot ce(
      Compl anceT etNot ceEventTypeConverter.toThr ft(compl anceT etNot ceEventType),
      Compl anceT etNot ceEventTypeConverter.eventTypeToLabelT le(compl anceT etNot ceEventType),
      deta ls,
      extendedDeta lsUrl
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Compl anceT etNot ce(
      toCompl anceT etNot ceThr ft()
    )

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = None

  def toCompl anceT etNot ce(): Compl anceT etNot ce = {
    Compl anceT etNot ce(
      compl anceT etNot ceEventType = compl anceT etNot ceEventType,
      labelT le = Compl anceT etNot ceEventTypeConverter.eventTypeToLabelT le(
        compl anceT etNot ceEventType),
      deta ls = deta ls,
      extendedDeta lsUrl = extendedDeta lsUrl
    )
  }
}

case class Compl anceT etNot ce(
  compl anceT etNot ceEventType: Compl anceT etNot ceEventType,
  labelT le: Opt on[Str ng] = None,
  deta ls: Opt on[Str ng] = None,
  extendedDeta lsUrl: Opt on[Str ng] = None)
    extends Act on
    w h BaseCompl anceT etNot ce {

  overr de val sever y:  nt = 2
  def toCompl anceT etNot ceThr ft(): thr ftscala.Compl anceT etNot ce =
    thr ftscala.Compl anceT etNot ce(
      Compl anceT etNot ceEventTypeConverter.toThr ft(compl anceT etNot ceEventType),
      labelT le,
      deta ls,
      extendedDeta lsUrl
    )

  overr de def toAct onThr ft(): thr ftscala.Act on =
    thr ftscala.Act on.Compl anceT etNot ce(
      toCompl anceT etNot ceThr ft()
    )

  overr de def to althAct onTypeThr ft: Opt on[ althAct onType] = None
}

object Act on {
  def toThr ft[T <: Act on](act on: T): thr ftscala.Act on =
    act on.toAct onThr ft()

  def getF rst nterst  al(act ons: Act on*): Opt on[ s nterst  al] =
    act ons.collectF rst {
      case  le:  nterst  alL m edEngage nts =>  le
      case ed : E rgencyDynam c nterst  al => ed 
      case  :  nterst  al =>  
    }

  def getF rstSoft ntervent on(act ons: Act on*): Opt on[Soft ntervent on] =
    act ons.collectF rst {
      case s : Soft ntervent on => s 
    }

  def getF rstL m edEngage nts(act ons: Act on*): Opt on[L m edEngage nts] =
    act ons.collectF rst {
      case le: L m edEngage nts => le
    }

  def getAllL m edEngage nts(act ons: Act on*): Seq[ sL m edEngage nts] =
    act ons.collect {
      case  le:  sL m edEngage nts =>  le
    }

  def getF rstDownrankHo T  l ne(act ons: Act on*): Opt on[DownrankHo T  l ne] =
    act ons.collectF rst {
      case dr: DownrankHo T  l ne => dr
    }

  def getF rstAvo d(act ons: Act on*): Opt on[Avo d] =
    act ons.collectF rst {
      case a: Avo d => a
    }

  def getF rst d a nterst  al(act ons: Act on*): Opt on[ nterst  al] =
    act ons.collectF rst {
      case  :  nterst  al  f Reason.NSFW_MED A.conta ns( .reason) =>  
    }

  def getF rstT etV s b l yNudge(act ons: Act on*): Opt on[T etV s b l yNudge] =
    act ons.collectF rst {
      case n: T etV s b l yNudge => n
    }
}

sealed tra  State {
  lazy val na : Str ng = Nam ngUt ls.getFr endlyNa (t )
}

object State {
  case object Pend ng extends State
  case object D sabled extends State
  f nal case class M ss ngFeature(features: Set[Feature[_]]) extends State
  f nal case class FeatureFa led(features: Map[Feature[_], Throwable]) extends State
  f nal case class RuleFa led(throwable: Throwable) extends State
  case object Sk pped extends State
  case object ShortC rcu ed extends State
  case object  ldback extends State
  case object Evaluated extends State
}

case class RuleResult(act on: Act on, state: State)

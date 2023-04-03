packagelon com.twittelonr.homelon_mixelonr.functional_componelonnt.felonaturelon_hydrator.adaptelonrs.elonarlybird

import com.twittelonr.ml.api.Felonaturelon
import com.twittelonr.ml.api.FelonaturelonContelonxt
import com.twittelonr.ml.api.RichDataReloncord
import com.twittelonr.ml.api.util.DataReloncordConvelonrtelonrs._
import com.twittelonr.timelonlinelons.prelondiction.common.adaptelonrs.TimelonlinelonsMutatingAdaptelonrBaselon
import com.twittelonr.selonarch.common.felonaturelons.{thriftscala => sc}
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.common.TimelonlinelonsSharelondFelonaturelons
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.reloncap.ReloncapFelonaturelons
import com.twittelonr.timelonlinelons.util.UrlelonxtractorUtil
import java.lang.{Boolelonan => JBoolelonan}
import java.lang.{Doublelon => JDoublelon}
import java.util.{Map => JMap}
import scala.collelonction.JavaConvelonrtelonrs._

objelonct elonarlybirdAdaptelonr elonxtelonnds TimelonlinelonsMutatingAdaptelonrBaselon[Option[sc.ThriftTwelonelontFelonaturelons]] {

  ovelonrridelon val gelontFelonaturelonContelonxt: FelonaturelonContelonxt = nelonw FelonaturelonContelonxt(
    ReloncapFelonaturelons.BIDIRelonCTIONAL_FAV_COUNT,
    ReloncapFelonaturelons.BIDIRelonCTIONAL_RelonPLY_COUNT,
    ReloncapFelonaturelons.BIDIRelonCTIONAL_RelonTWelonelonT_COUNT,
    ReloncapFelonaturelons.BLelonNDelonR_SCORelon,
    ReloncapFelonaturelons.CONTAINS_MelonDIA,
    ReloncapFelonaturelons.CONVelonRSATIONAL_COUNT,
    ReloncapFelonaturelons.elonMBelonDS_IMPRelonSSION_COUNT,
    ReloncapFelonaturelons.elonMBelonDS_URL_COUNT,
    ReloncapFelonaturelons.FAV_COUNT,
    ReloncapFelonaturelons.FAV_COUNT_V2,
    ReloncapFelonaturelons.FROM_INACTIVelon_USelonR,
    ReloncapFelonaturelons.FROM_MUTUAL_FOLLOW,
    ReloncapFelonaturelons.FROM_VelonRIFIelonD_ACCOUNT,
    ReloncapFelonaturelons.HAS_CARD,
    ReloncapFelonaturelons.HAS_CONSUMelonR_VIDelonO,
    ReloncapFelonaturelons.HAS_HASHTAG,
    ReloncapFelonaturelons.HAS_IMAGelon,
    ReloncapFelonaturelons.HAS_LINK,
    ReloncapFelonaturelons.HAS_MelonNTION,
    ReloncapFelonaturelons.HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS,
    ReloncapFelonaturelons.HAS_MULTIPLelon_MelonDIA,
    ReloncapFelonaturelons.HAS_NATIVelon_IMAGelon,
    ReloncapFelonaturelons.HAS_NATIVelon_VIDelonO,
    ReloncapFelonaturelons.HAS_NelonWS,
    ReloncapFelonaturelons.HAS_PelonRISCOPelon,
    ReloncapFelonaturelons.HAS_PRO_VIDelonO,
    ReloncapFelonaturelons.HAS_TRelonND,
    ReloncapFelonaturelons.HAS_VIDelonO,
    ReloncapFelonaturelons.HAS_VINelon,
    ReloncapFelonaturelons.HAS_VISIBLelon_LINK,
    ReloncapFelonaturelons.IS_AUTHOR_BOT,
    ReloncapFelonaturelons.IS_AUTHOR_NelonW,
    ReloncapFelonaturelons.IS_AUTHOR_NSFW,
    ReloncapFelonaturelons.IS_AUTHOR_PROFILelon_elonGG,
    ReloncapFelonaturelons.IS_AUTHOR_SPAM,
    ReloncapFelonaturelons.IS_BUSINelonSS_SCORelon,
    ReloncapFelonaturelons.IS_OFFelonNSIVelon,
    ReloncapFelonaturelons.IS_RelonPLY,
    ReloncapFelonaturelons.IS_RelonTWelonelonT,
    ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_BOT,
    ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_NelonW,
    ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_NSFW,
    ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_PROFILelon_elonGG,
    ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_SPAM,
    ReloncapFelonaturelons.IS_RelonTWelonelonT_OF_RelonPLY,
    ReloncapFelonaturelons.IS_SelonNSITIVelon,
    ReloncapFelonaturelons.LANGUAGelon,
    ReloncapFelonaturelons.LINK_COUNT,
    ReloncapFelonaturelons.LINK_LANGUAGelon,
    ReloncapFelonaturelons.MATCH_SelonARCHelonR_LANGS,
    ReloncapFelonaturelons.MATCH_SelonARCHelonR_MAIN_LANG,
    ReloncapFelonaturelons.MATCH_UI_LANG,
    ReloncapFelonaturelons.MelonNTIONelonD_SCRelonelonN_NAMelonS,
    ReloncapFelonaturelons.MelonNTION_SelonARCHelonR,
    ReloncapFelonaturelons.NUM_HASHTAGS,
    ReloncapFelonaturelons.NUM_MelonNTIONS,
    ReloncapFelonaturelons.PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT,
    ReloncapFelonaturelons.PROBABLY_FROM_FOLLOWelonD_AUTHOR,
    ReloncapFelonaturelons.RelonPLY_COUNT,
    ReloncapFelonaturelons.RelonPLY_COUNT_V2,
    ReloncapFelonaturelons.RelonPLY_OTHelonR,
    ReloncapFelonaturelons.RelonPLY_SelonARCHelonR,
    ReloncapFelonaturelons.RelonTWelonelonT_COUNT,
    ReloncapFelonaturelons.RelonTWelonelonT_COUNT_V2,
    ReloncapFelonaturelons.RelonTWelonelonT_DIRelonCTelonD_AT_USelonR_IN_FIRST_DelonGRelonelon,
    ReloncapFelonaturelons.RelonTWelonelonT_OF_MUTUAL_FOLLOW,
    ReloncapFelonaturelons.RelonTWelonelonT_OTHelonR,
    ReloncapFelonaturelons.RelonTWelonelonT_SelonARCHelonR,
    ReloncapFelonaturelons.SIGNATURelon,
    ReloncapFelonaturelons.SOURCelon_AUTHOR_RelonP,
    ReloncapFelonaturelons.TelonXT_SCORelon,
    ReloncapFelonaturelons.TWelonelonT_COUNT_FROM_USelonR_IN_SNAPSHOT,
    ReloncapFelonaturelons.UNIDIRelonCTIONAL_FAV_COUNT,
    ReloncapFelonaturelons.UNIDIRelonCTIONAL_RelonPLY_COUNT,
    ReloncapFelonaturelons.UNIDIRelonCTIONAL_RelonTWelonelonT_COUNT,
    ReloncapFelonaturelons.URL_DOMAINS,
    ReloncapFelonaturelons.USelonR_RelonP,
    ReloncapFelonaturelons.VIDelonO_VIelonW_COUNT,
    // sharelond felonaturelons
    TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_FAV_COUNT,
    TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_RelonTWelonelonT_COUNT,
    TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_RelonPLY_COUNT,
    TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_QUOTelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.elonMBelonDS_IMPRelonSSION_COUNT_V2,
    TimelonlinelonsSharelondFelonaturelons.elonMBelonDS_URL_COUNT_V2,
    TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_FAVORITelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_RelonTWelonelonT_COUNT,
    TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_RelonPLY_COUNT,
    TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_QUOTelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.FAKelon_FAVORITelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.FAKelon_RelonTWelonelonT_COUNT,
    TimelonlinelonsSharelondFelonaturelons.FAKelon_RelonPLY_COUNT,
    TimelonlinelonsSharelondFelonaturelons.FAKelon_QUOTelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.QUOTelon_COUNT,
    TimelonlinelonsSharelondFelonaturelons.elonARLYBIRD_SCORelon,
    // Safelonty felonaturelons
    TimelonlinelonsSharelondFelonaturelons.LABelonL_ABUSIVelon_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_ABUSIVelon_HI_RCL_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_DUP_CONTelonNT_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_NSFW_HI_PRC_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_NSFW_HI_RCL_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_SPAM_FLAG,
    TimelonlinelonsSharelondFelonaturelons.LABelonL_SPAM_HI_RCL_FLAG,
    // pelonriscopelon felonaturelons
    TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_elonXISTS,
    TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_LIVelon,
    TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD,
    TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD,
    TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon,
    // VISIBLelon_TOKelonN_RATIO
    TimelonlinelonsSharelondFelonaturelons.VISIBLelon_TOKelonN_RATIO,
    TimelonlinelonsSharelondFelonaturelons.HAS_QUOTelon,
    TimelonlinelonsSharelondFelonaturelons.IS_COMPOSelonR_SOURCelon_CAMelonRA,
    // helonalth felonaturelons
    TimelonlinelonsSharelondFelonaturelons.PRelonPORTelonD_TWelonelonT_SCORelon,
    // melondia
    TimelonlinelonsSharelondFelonaturelons.CLASSIFICATION_LABelonLS
  )

  ovelonrridelon val commonFelonaturelons: Selont[Felonaturelon[_]] = Selont.elonmpty

  ovelonrridelon delonf selontFelonaturelons(
    elonbFelonaturelons: Option[sc.ThriftTwelonelontFelonaturelons],
    richDataReloncord: RichDataReloncord
  ): Unit = {
    if (elonbFelonaturelons.nonelonmpty) {
      val felonaturelons = elonbFelonaturelons.gelont
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](
        ReloncapFelonaturelons.PRelonV_USelonR_TWelonelonT_elonNGAGelonMelonNT,
        felonaturelons.prelonvUselonrTwelonelontelonngagelonmelonnt.toDoublelon
      )
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_SelonNSITIVelon, felonaturelons.isSelonnsitivelonContelonnt)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_MULTIPLelon_MelonDIA, felonaturelons.hasMultiplelonMelondia)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_AUTHOR_PROFILelon_elonGG, felonaturelons.isAuthorProfilelonelongg)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_AUTHOR_NelonW, felonaturelons.isAuthorNelonw)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.NUM_MelonNTIONS, felonaturelons.numMelonntions.toDoublelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_MelonNTION, felonaturelons.numMelonntions > 0)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.NUM_HASHTAGS, felonaturelons.numHashtags.toDoublelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_HASHTAG, felonaturelons.numHashtags > 0)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.LINK_LANGUAGelon, felonaturelons.linkLanguagelon.toDoublelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_AUTHOR_NSFW, felonaturelons.isAuthorNSFW)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_AUTHOR_SPAM, felonaturelons.isAuthorSpam)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_AUTHOR_BOT, felonaturelons.isAuthorBot)
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.LANGUAGelon,
        felonaturelons.languagelon.map(_.gelontValuelon.toLong))
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.SIGNATURelon,
        felonaturelons.signaturelon.map(_.toLong))
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.FROM_INACTIVelon_USelonR, felonaturelons.fromInActivelonUselonr)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](
          ReloncapFelonaturelons.PROBABLY_FROM_FOLLOWelonD_AUTHOR,
          felonaturelons.probablyFromFollowelondAuthor)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.FROM_MUTUAL_FOLLOW, felonaturelons.fromMutualFollow)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](
        ReloncapFelonaturelons.FROM_VelonRIFIelonD_ACCOUNT,
        felonaturelons.fromVelonrifielondAccount)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.USelonR_RelonP, felonaturelons.uselonrRelonp)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.IS_BUSINelonSS_SCORelon, felonaturelons.isBusinelonssScorelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_CONSUMelonR_VIDelonO, felonaturelons.hasConsumelonrVidelono)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_PRO_VIDelonO, felonaturelons.hasProVidelono)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_VINelon, felonaturelons.hasVinelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_PelonRISCOPelon, felonaturelons.hasPelonriscopelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_NATIVelon_VIDelonO, felonaturelons.hasNativelonVidelono)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_NATIVelon_IMAGelon, felonaturelons.hasNativelonImagelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_CARD, felonaturelons.hasCard)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_IMAGelon, felonaturelons.hasImagelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_NelonWS, felonaturelons.hasNelonws)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_VIDelonO, felonaturelons.hasVidelono)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.CONTAINS_MelonDIA, felonaturelons.containsMelondia)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.RelonTWelonelonT_SelonARCHelonR, felonaturelons.relontwelonelontSelonarchelonr)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.RelonPLY_SelonARCHelonR, felonaturelons.relonplySelonarchelonr)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.MelonNTION_SelonARCHelonR, felonaturelons.melonntionSelonarchelonr)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.RelonPLY_OTHelonR, felonaturelons.relonplyOthelonr)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.RelonTWelonelonT_OTHelonR, felonaturelons.relontwelonelontOthelonr)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonPLY, felonaturelons.isRelonply)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonT, felonaturelons.isRelontwelonelont)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_OFFelonNSIVelon, felonaturelons.isOffelonnsivelon)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.MATCH_UI_LANG, felonaturelons.matchelonsUILang)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](
          ReloncapFelonaturelons.MATCH_SelonARCHelonR_MAIN_LANG,
          felonaturelons.matchelonsSelonarchelonrMainLang)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](
        ReloncapFelonaturelons.MATCH_SelonARCHelonR_LANGS,
        felonaturelons.matchelonsSelonarchelonrLangs)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.BIDIRelonCTIONAL_FAV_COUNT,
          felonaturelons.bidirelonctionalFavCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.UNIDIRelonCTIONAL_FAV_COUNT,
          felonaturelons.unidirelonctionalFavCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.BIDIRelonCTIONAL_RelonPLY_COUNT,
          felonaturelons.bidirelonctionalRelonplyCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.UNIDIRelonCTIONAL_RelonPLY_COUNT,
          felonaturelons.unidirelonctionalRelonplyCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.BIDIRelonCTIONAL_RelonTWelonelonT_COUNT,
          felonaturelons.bidirelonctionalRelontwelonelontCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.UNIDIRelonCTIONAL_RelonTWelonelonT_COUNT,
          felonaturelons.unidirelonctionalRelontwelonelontCount)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.CONVelonRSATIONAL_COUNT, felonaturelons.convelonrsationCount)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](
        ReloncapFelonaturelons.TWelonelonT_COUNT_FROM_USelonR_IN_SNAPSHOT,
        felonaturelons.twelonelontCountFromUselonrInSnapshot
      )
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](
          ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_PROFILelon_elonGG,
          felonaturelons.isRelontwelonelontelonrProfilelonelongg)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_NelonW, felonaturelons.isRelontwelonelontelonrNelonw)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_BOT, felonaturelons.isRelontwelonelontelonrBot)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_NSFW, felonaturelons.isRelontwelonelontelonrNSFW)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonTelonR_SPAM, felonaturelons.isRelontwelonelontelonrSpam)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](
          ReloncapFelonaturelons.RelonTWelonelonT_OF_MUTUAL_FOLLOW,
          felonaturelons.relontwelonelontOfMutualFollow)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.SOURCelon_AUTHOR_RelonP, felonaturelons.sourcelonAuthorRelonp)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.IS_RelonTWelonelonT_OF_RelonPLY, felonaturelons.isRelontwelonelontOfRelonply)
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.RelonTWelonelonT_DIRelonCTelonD_AT_USelonR_IN_FIRST_DelonGRelonelon,
        felonaturelons.relontwelonelontDirelonctelondAtUselonrInFirstDelongrelonelon
      )
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](
          ReloncapFelonaturelons.elonMBelonDS_IMPRelonSSION_COUNT,
          felonaturelons.elonmbelondsImprelonssionCount.toDoublelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.elonMBelonDS_URL_COUNT, felonaturelons.elonmbelondsUrlCount.toDoublelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.VIDelonO_VIelonW_COUNT, felonaturelons.videlonoVielonwCount.toDoublelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.RelonPLY_COUNT, felonaturelons.relonplyCount.toDoublelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.RelonTWelonelonT_COUNT, felonaturelons.relontwelonelontCount.toDoublelon)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.FAV_COUNT, felonaturelons.favCount.toDoublelon)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.BLelonNDelonR_SCORelon, felonaturelons.blelonndelonrScorelon)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.TelonXT_SCORelon, felonaturelons.telonxtScorelon)
      richDataReloncord
        .selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_VISIBLelon_LINK, felonaturelons.hasVisiblelonLink)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_LINK, felonaturelons.hasLink)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](ReloncapFelonaturelons.HAS_TRelonND, felonaturelons.hasTrelonnd)
      richDataReloncord.selontFelonaturelonValuelon[JBoolelonan](
        ReloncapFelonaturelons.HAS_MULTIPLelon_HASHTAGS_OR_TRelonNDS,
        felonaturelons.hasMultiplelonHashtagsOrTrelonnds
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.FAV_COUNT_V2,
        felonaturelons.favCountV2.map(_.toDoublelon))
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.RelonTWelonelonT_COUNT_V2,
        felonaturelons.relontwelonelontCountV2.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        ReloncapFelonaturelons.RelonPLY_COUNT_V2,
        felonaturelons.relonplyCountV2.map(_.toDoublelon))
      val urls = felonaturelons.urlsList.gelontOrelonlselon(Selonq.elonmpty)
      richDataReloncord.selontFelonaturelonValuelon(
        ReloncapFelonaturelons.URL_DOMAINS,
        urls.toSelont.flatMap(UrlelonxtractorUtil.elonxtractDomain).asJava)
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](ReloncapFelonaturelons.LINK_COUNT, urls.sizelon.toDoublelon)
      // sharelond felonaturelons
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_FAV_COUNT,
        felonaturelons.welonightelondFavoritelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_RelonTWelonelonT_COUNT,
        felonaturelons.welonightelondRelontwelonelontCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_RelonPLY_COUNT,
        felonaturelons.welonightelondRelonplyCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.WelonIGHTelonD_QUOTelon_COUNT,
        felonaturelons.welonightelondQuotelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.elonMBelonDS_IMPRelonSSION_COUNT_V2,
        felonaturelons.elonmbelondsImprelonssionCountV2.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.elonMBelonDS_URL_COUNT_V2,
        felonaturelons.elonmbelondsUrlCountV2.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_FAVORITelon_COUNT,
        felonaturelons.deloncayelondFavoritelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_RelonTWelonelonT_COUNT,
        felonaturelons.deloncayelondRelontwelonelontCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_RelonPLY_COUNT,
        felonaturelons.deloncayelondRelonplyCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.DelonCAYelonD_QUOTelon_COUNT,
        felonaturelons.deloncayelondQuotelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.FAKelon_FAVORITelon_COUNT,
        felonaturelons.fakelonFavoritelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.FAKelon_RelonTWelonelonT_COUNT,
        felonaturelons.fakelonRelontwelonelontCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.FAKelon_RelonPLY_COUNT,
        felonaturelons.fakelonRelonplyCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.FAKelon_QUOTelon_COUNT,
        felonaturelons.fakelonQuotelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.QUOTelon_COUNT,
        felonaturelons.quotelonCount.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelon[JDoublelon](
        TimelonlinelonsSharelondFelonaturelons.elonARLYBIRD_SCORelon,
        felonaturelons.elonarlybirdScorelon
      )
      // safelonty felonaturelons
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_ABUSIVelon_FLAG,
        felonaturelons.labelonlAbusivelonFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_ABUSIVelon_HI_RCL_FLAG,
        felonaturelons.labelonlAbusivelonHiRclFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_DUP_CONTelonNT_FLAG,
        felonaturelons.labelonlDupContelonntFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_NSFW_HI_PRC_FLAG,
        felonaturelons.labelonlNsfwHiPrcFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_NSFW_HI_RCL_FLAG,
        felonaturelons.labelonlNsfwHiRclFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_SPAM_FLAG,
        felonaturelons.labelonlSpamFlag
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.LABelonL_SPAM_HI_RCL_FLAG,
        felonaturelons.labelonlSpamHiRclFlag
      )
      // pelonriscopelon felonaturelons
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_elonXISTS,
        felonaturelons.pelonriscopelonelonxists
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_LIVelon,
        felonaturelons.pelonriscopelonIsLivelon
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_HAS_BelonelonN_FelonATURelonD,
        felonaturelons.pelonriscopelonHasBelonelonnFelonaturelond
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_CURRelonNTLY_FelonATURelonD,
        felonaturelons.pelonriscopelonIsCurrelonntlyFelonaturelond
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PelonRISCOPelon_IS_FROM_QUALITY_SOURCelon,
        felonaturelons.pelonriscopelonIsFromQualitySourcelon
      )
      // misc felonaturelons
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.VISIBLelon_TOKelonN_RATIO,
        felonaturelons.visiblelonTokelonnRatio.map(_.toDoublelon)
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.HAS_QUOTelon,
        felonaturelons.hasQuotelon
      )
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.IS_COMPOSelonR_SOURCelon_CAMelonRA,
        felonaturelons.isComposelonrSourcelonCamelonra
      )
      // helonalth scorelons
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.PRelonPORTelonD_TWelonelonT_SCORelon,
        felonaturelons.pRelonportelondTwelonelontScorelon
      )
      // melondia
      richDataReloncord.selontFelonaturelonValuelonFromOption(
        TimelonlinelonsSharelondFelonaturelons.CLASSIFICATION_LABelonLS,
        felonaturelons.melondiaClassificationInfo.map(_.toMap.asJava.asInstancelonOf[JMap[String, JDoublelon]])
      )
    }
  }
}

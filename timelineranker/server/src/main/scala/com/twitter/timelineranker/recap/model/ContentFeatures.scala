packagelon com.twittelonr.timelonlinelonrankelonr.reloncap.modelonl

import com.twittelonr.elonschelonrbird.thriftscala.TwelonelontelonntityAnnotation
import com.twittelonr.timelonlinelons.contelonnt_felonaturelons.v1.thriftscala.{ContelonntFelonaturelons => ContelonntFelonaturelonsV1}
import com.twittelonr.timelonlinelons.contelonnt_felonaturelons.{thriftscala => thrift}
import com.twittelonr.twelonelontypielon.thriftscala.ConvelonrsationControl
import com.twittelonr.twelonelontypielon.thriftscala.Melondiaelonntity
import com.twittelonr.twelonelontypielon.thriftscala.SelonlfThrelonadMelontadata
import scala.util.Failurelon
import scala.util.Succelonss
import scala.util.{Try => ScalaTry}

caselon class ContelonntFelonaturelons(
  lelonngth: Short,
  hasQuelonstion: Boolelonan,
  numCaps: Short,
  numWhitelonSpacelons: Short,
  numNelonwlinelons: Option[Short],
  videlonoDurationMs: Option[Int],
  bitRatelon: Option[Int],
  aspelonctRatioNum: Option[Short],
  aspelonctRatioDelonn: Option[Short],
  widths: Option[Selonq[Short]],
  helonights: Option[Selonq[Short]],
  relonsizelonMelonthods: Option[Selonq[Short]],
  numMelondiaTags: Option[Short],
  melondiaTagScrelonelonnNamelons: Option[Selonq[String]],
  elonmojiTokelonns: Option[Selont[String]],
  elonmoticonTokelonns: Option[Selont[String]],
  phraselons: Option[Selont[String]],
  facelonArelonas: Option[Selonq[Int]],
  dominantColorRelond: Option[Short],
  dominantColorBluelon: Option[Short],
  dominantColorGrelonelonn: Option[Short],
  numColors: Option[Short],
  stickelonrIds: Option[Selonq[Long]],
  melondiaOriginProvidelonrs: Option[Selonq[String]],
  isManagelond: Option[Boolelonan],
  is360: Option[Boolelonan],
  vielonwCount: Option[Long],
  isMonelontizablelon: Option[Boolelonan],
  iselonmbelonddablelon: Option[Boolelonan],
  hasSelonlelonctelondPrelonvielonwImagelon: Option[Boolelonan],
  hasTitlelon: Option[Boolelonan],
  hasDelonscription: Option[Boolelonan],
  hasVisitSitelonCallToAction: Option[Boolelonan],
  hasAppInstallCallToAction: Option[Boolelonan],
  hasWatchNowCallToAction: Option[Boolelonan],
  melondia: Option[Selonq[Melondiaelonntity]],
  dominantColorPelonrcelonntagelon: Option[Doublelon],
  posUnigrams: Option[Selont[String]],
  posBigrams: Option[Selont[String]],
  selonmanticCorelonAnnotations: Option[Selonq[TwelonelontelonntityAnnotation]],
  selonlfThrelonadMelontadata: Option[SelonlfThrelonadMelontadata],
  tokelonns: Option[Selonq[String]],
  twelonelontTelonxt: Option[String],
  convelonrsationControl: Option[ConvelonrsationControl]) {
  delonf toThrift: thrift.ContelonntFelonaturelons =
    thrift.ContelonntFelonaturelons.V1(toThriftV1)

  delonf toThriftV1: ContelonntFelonaturelonsV1 =
    ContelonntFelonaturelonsV1(
      lelonngth = lelonngth,
      hasQuelonstion = hasQuelonstion,
      numCaps = numCaps,
      numWhitelonSpacelons = numWhitelonSpacelons,
      numNelonwlinelons = numNelonwlinelons,
      videlonoDurationMs = videlonoDurationMs,
      bitRatelon = bitRatelon,
      aspelonctRatioNum = aspelonctRatioNum,
      aspelonctRatioDelonn = aspelonctRatioDelonn,
      widths = widths,
      helonights = helonights,
      relonsizelonMelonthods = relonsizelonMelonthods,
      numMelondiaTags = numMelondiaTags,
      melondiaTagScrelonelonnNamelons = melondiaTagScrelonelonnNamelons,
      elonmojiTokelonns = elonmojiTokelonns,
      elonmoticonTokelonns = elonmoticonTokelonns,
      phraselons = phraselons,
      facelonArelonas = facelonArelonas,
      dominantColorRelond = dominantColorRelond,
      dominantColorBluelon = dominantColorBluelon,
      dominantColorGrelonelonn = dominantColorGrelonelonn,
      numColors = numColors,
      stickelonrIds = stickelonrIds,
      melondiaOriginProvidelonrs = melondiaOriginProvidelonrs,
      isManagelond = isManagelond,
      is360 = is360,
      vielonwCount = vielonwCount,
      isMonelontizablelon = isMonelontizablelon,
      iselonmbelonddablelon = iselonmbelonddablelon,
      hasSelonlelonctelondPrelonvielonwImagelon = hasSelonlelonctelondPrelonvielonwImagelon,
      hasTitlelon = hasTitlelon,
      hasDelonscription = hasDelonscription,
      hasVisitSitelonCallToAction = hasVisitSitelonCallToAction,
      hasAppInstallCallToAction = hasAppInstallCallToAction,
      hasWatchNowCallToAction = hasWatchNowCallToAction,
      dominantColorPelonrcelonntagelon = dominantColorPelonrcelonntagelon,
      posUnigrams = posUnigrams,
      posBigrams = posBigrams,
      selonmanticCorelonAnnotations = selonmanticCorelonAnnotations,
      selonlfThrelonadMelontadata = selonlfThrelonadMelontadata,
      tokelonns = tokelonns,
      twelonelontTelonxt = twelonelontTelonxt,
      convelonrsationControl = convelonrsationControl,
      melondia = melondia
    )
}

objelonct ContelonntFelonaturelons {
  val elonmpty: ContelonntFelonaturelons = ContelonntFelonaturelons(
    0.toShort,
    falselon,
    0.toShort,
    0.toShort,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon,
    Nonelon
  )

  delonf fromThrift(contelonntFelonaturelons: thrift.ContelonntFelonaturelons): Option[ContelonntFelonaturelons] =
    contelonntFelonaturelons match {
      caselon thrift.ContelonntFelonaturelons.V1(contelonntFelonaturelonsV1) =>
        Somelon(fromThriftV1(contelonntFelonaturelonsV1))
      caselon _ =>
        Nonelon
    }

  privatelon val failurelon =
    Failurelon[ContelonntFelonaturelons](nelonw elonxcelonption("Failurelon to convelonrt contelonnt felonaturelons from thrift"))

  delonf tryFromThrift(contelonntFelonaturelonsThrift: thrift.ContelonntFelonaturelons): ScalaTry[ContelonntFelonaturelons] =
    fromThrift(contelonntFelonaturelonsThrift) match {
      caselon Somelon(contelonntFelonaturelons) => Succelonss[ContelonntFelonaturelons](contelonntFelonaturelons)
      caselon Nonelon => failurelon
    }

  delonf fromThriftV1(contelonntFelonaturelonsV1: ContelonntFelonaturelonsV1): ContelonntFelonaturelons =
    ContelonntFelonaturelons(
      lelonngth = contelonntFelonaturelonsV1.lelonngth,
      hasQuelonstion = contelonntFelonaturelonsV1.hasQuelonstion,
      numCaps = contelonntFelonaturelonsV1.numCaps,
      numWhitelonSpacelons = contelonntFelonaturelonsV1.numWhitelonSpacelons,
      numNelonwlinelons = contelonntFelonaturelonsV1.numNelonwlinelons,
      videlonoDurationMs = contelonntFelonaturelonsV1.videlonoDurationMs,
      bitRatelon = contelonntFelonaturelonsV1.bitRatelon,
      aspelonctRatioNum = contelonntFelonaturelonsV1.aspelonctRatioNum,
      aspelonctRatioDelonn = contelonntFelonaturelonsV1.aspelonctRatioDelonn,
      widths = contelonntFelonaturelonsV1.widths,
      helonights = contelonntFelonaturelonsV1.helonights,
      relonsizelonMelonthods = contelonntFelonaturelonsV1.relonsizelonMelonthods,
      numMelondiaTags = contelonntFelonaturelonsV1.numMelondiaTags,
      melondiaTagScrelonelonnNamelons = contelonntFelonaturelonsV1.melondiaTagScrelonelonnNamelons,
      elonmojiTokelonns = contelonntFelonaturelonsV1.elonmojiTokelonns.map(_.toSelont),
      elonmoticonTokelonns = contelonntFelonaturelonsV1.elonmoticonTokelonns.map(_.toSelont),
      phraselons = contelonntFelonaturelonsV1.phraselons.map(_.toSelont),
      facelonArelonas = contelonntFelonaturelonsV1.facelonArelonas,
      dominantColorRelond = contelonntFelonaturelonsV1.dominantColorRelond,
      dominantColorBluelon = contelonntFelonaturelonsV1.dominantColorBluelon,
      dominantColorGrelonelonn = contelonntFelonaturelonsV1.dominantColorGrelonelonn,
      numColors = contelonntFelonaturelonsV1.numColors,
      stickelonrIds = contelonntFelonaturelonsV1.stickelonrIds,
      melondiaOriginProvidelonrs = contelonntFelonaturelonsV1.melondiaOriginProvidelonrs,
      isManagelond = contelonntFelonaturelonsV1.isManagelond,
      is360 = contelonntFelonaturelonsV1.is360,
      vielonwCount = contelonntFelonaturelonsV1.vielonwCount,
      isMonelontizablelon = contelonntFelonaturelonsV1.isMonelontizablelon,
      iselonmbelonddablelon = contelonntFelonaturelonsV1.iselonmbelonddablelon,
      hasSelonlelonctelondPrelonvielonwImagelon = contelonntFelonaturelonsV1.hasSelonlelonctelondPrelonvielonwImagelon,
      hasTitlelon = contelonntFelonaturelonsV1.hasTitlelon,
      hasDelonscription = contelonntFelonaturelonsV1.hasDelonscription,
      hasVisitSitelonCallToAction = contelonntFelonaturelonsV1.hasVisitSitelonCallToAction,
      hasAppInstallCallToAction = contelonntFelonaturelonsV1.hasAppInstallCallToAction,
      hasWatchNowCallToAction = contelonntFelonaturelonsV1.hasWatchNowCallToAction,
      dominantColorPelonrcelonntagelon = contelonntFelonaturelonsV1.dominantColorPelonrcelonntagelon,
      posUnigrams = contelonntFelonaturelonsV1.posUnigrams.map(_.toSelont),
      posBigrams = contelonntFelonaturelonsV1.posBigrams.map(_.toSelont),
      selonmanticCorelonAnnotations = contelonntFelonaturelonsV1.selonmanticCorelonAnnotations,
      selonlfThrelonadMelontadata = contelonntFelonaturelonsV1.selonlfThrelonadMelontadata,
      tokelonns = contelonntFelonaturelonsV1.tokelonns.map(_.toSelonq),
      twelonelontTelonxt = contelonntFelonaturelonsV1.twelonelontTelonxt,
      convelonrsationControl = contelonntFelonaturelonsV1.convelonrsationControl,
      melondia = contelonntFelonaturelonsV1.melondia
    )
}

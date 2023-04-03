packagelon com.twittelonr.visibility.modelonls

import com.twittelonr.spam.rtf.thriftscala.SafelontyLabelonlSourcelon
import com.twittelonr.spam.rtf.{thriftscala => s}
import com.twittelonr.util.Timelon
import com.twittelonr.visibility.util.NamingUtils

selonalelond trait TwelonelontSafelontyLabelonlTypelon elonxtelonnds SafelontyLabelonlTypelon with Product with Selonrializablelon {
  lazy val namelon: String = NamingUtils.gelontFrielonndlyNamelon(this)
}

caselon class TwelonelontSafelontyLabelonl(
  labelonlTypelon: TwelonelontSafelontyLabelonlTypelon,
  sourcelon: Option[LabelonlSourcelon] = Nonelon,
  applicablelonUselonrs: Selont[Long] = Selont.elonmpty,
  modelonlMelontadata: Option[TwelonelontModelonlMelontadata] = Nonelon,
  scorelon: Option[Doublelon] = Nonelon,
  safelontyLabelonlSourcelon: Option[SafelontyLabelonlSourcelon] = Nonelon)

objelonct TwelonelontSafelontyLabelonlTypelon elonxtelonnds SafelontyLabelonlTypelon {

  val List: List[TwelonelontSafelontyLabelonlTypelon] = s.SafelontyLabelonlTypelon.list.map(fromThrift)

  val ActivelonLabelonls: List[TwelonelontSafelontyLabelonlTypelon] = List.filtelonr { labelonlTypelon =>
    labelonlTypelon != Unknown && labelonlTypelon != Delonpreloncatelond
  }

  privatelon lazy val namelonToValuelonMap: Map[String, TwelonelontSafelontyLabelonlTypelon] =
    List.map(l => l.namelon.toLowelonrCaselon -> l).toMap
  delonf fromNamelon(namelon: String): Option[TwelonelontSafelontyLabelonlTypelon] = namelonToValuelonMap.gelont(namelon.toLowelonrCaselon)

  privatelon val UnknownThriftSafelontyLabelonlTypelon =
    s.SafelontyLabelonlTypelon.elonnumUnknownSafelontyLabelonlTypelon(UnknownelonnumValuelon)

  privatelon lazy val thriftToModelonlMap: Map[s.SafelontyLabelonlTypelon, TwelonelontSafelontyLabelonlTypelon] = Map(
    s.SafelontyLabelonlTypelon.Abusivelon -> Abusivelon,
    s.SafelontyLabelonlTypelon.AbusivelonBelonhavior -> AbusivelonBelonhavior,
    s.SafelontyLabelonlTypelon.AbusivelonBelonhaviorInsults -> AbusivelonBelonhaviorInsults,
    s.SafelontyLabelonlTypelon.AbusivelonBelonhaviorViolelonntThrelonat -> AbusivelonBelonhaviorViolelonntThrelonat,
    s.SafelontyLabelonlTypelon.AbusivelonBelonhaviorMajorAbuselon -> AbusivelonBelonhaviorMajorAbuselon,
    s.SafelontyLabelonlTypelon.AbusivelonHighReloncall -> AbusivelonHighReloncall,
    s.SafelontyLabelonlTypelon.AdsManagelonrDelonnyList -> AdsManagelonrDelonnyList,
    s.SafelontyLabelonlTypelon.AgathaSpam -> AgathaSpam,
    s.SafelontyLabelonlTypelon.Automation -> Automation,
    s.SafelontyLabelonlTypelon.AutomationHighReloncall -> AutomationHighReloncall,
    s.SafelontyLabelonlTypelon.Bouncelon -> Bouncelon,
    s.SafelontyLabelonlTypelon.Bouncelonelondits -> Bouncelonelondits,
    s.SafelontyLabelonlTypelon.BrandSafelontyNsfaAggrelongatelon -> BrandSafelontyNsfaAggrelongatelon,
    s.SafelontyLabelonlTypelon.BrandSafelontyelonxpelonrimelonntal1 -> BrandSafelontyelonxpelonrimelonntal1,
    s.SafelontyLabelonlTypelon.BrandSafelontyelonxpelonrimelonntal2 -> BrandSafelontyelonxpelonrimelonntal2,
    s.SafelontyLabelonlTypelon.BrandSafelontyelonxpelonrimelonntal3 -> BrandSafelontyelonxpelonrimelonntal3,
    s.SafelontyLabelonlTypelon.BrandSafelontyelonxpelonrimelonntal4 -> BrandSafelontyelonxpelonrimelonntal4,
    s.SafelontyLabelonlTypelon.BystandelonrAbusivelon -> BystandelonrAbusivelon,
    s.SafelontyLabelonlTypelon.CopypastaSpam -> CopypastaSpam,
    s.SafelontyLabelonlTypelon.DoNotAmplify -> DoNotAmplify,
    s.SafelontyLabelonlTypelon.DownrankSpamRelonply -> DownrankSpamRelonply,
    s.SafelontyLabelonlTypelon.DuplicatelonContelonnt -> DuplicatelonContelonnt,
    s.SafelontyLabelonlTypelon.DuplicatelonMelonntion -> DuplicatelonMelonntion,
    s.SafelontyLabelonlTypelon.DynamicProductAd -> DynamicProductAd,
    s.SafelontyLabelonlTypelon.elondiDelonvelonlopmelonntOnly -> elondiDelonvelonlopmelonntOnly,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalNudgelon -> elonxpelonrimelonntalNudgelon,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal2 -> elonxpelonrimelonntalSelonnsitivelonIllelongal2,
    s.SafelontyLabelonlTypelon.ForelonmelonrgelonncyUselonOnly -> ForelonmelonrgelonncyUselonOnly,
    s.SafelontyLabelonlTypelon.GorelonAndViolelonncelon -> GorelonAndViolelonncelon,
    s.SafelontyLabelonlTypelon.GorelonAndViolelonncelonHighPreloncision -> GorelonAndViolelonncelonHighPreloncision,
    s.SafelontyLabelonlTypelon.GorelonAndViolelonncelonHighReloncall -> GorelonAndViolelonncelonHighReloncall,
    s.SafelontyLabelonlTypelon.GorelonAndViolelonncelonRelonportelondHelonuristics -> GorelonAndViolelonncelonRelonportelondHelonuristics,
    s.SafelontyLabelonlTypelon.GorelonAndViolelonncelonTopicHighReloncall -> GorelonAndViolelonncelonTopicHighReloncall,
    s.SafelontyLabelonlTypelon.HatelonfulConduct -> HatelonfulConduct,
    s.SafelontyLabelonlTypelon.HatelonfulConductViolelonntThrelonat -> HatelonfulConductViolelonntThrelonat,
    s.SafelontyLabelonlTypelon.HighCryptospamScorelon -> HighCryptospamScorelon,
    s.SafelontyLabelonlTypelon.HighPRelonportelondTwelonelontScorelon -> HighPRelonportelondTwelonelontScorelon,
    s.SafelontyLabelonlTypelon.HighPSpammyTwelonelontScorelon -> HighPSpammyTwelonelontScorelon,
    s.SafelontyLabelonlTypelon.HighPblockScorelon -> HighPblockScorelon,
    s.SafelontyLabelonlTypelon.HighProactivelonTosScorelon -> HighProactivelonTosScorelon,
    s.SafelontyLabelonlTypelon.HighSpammyTwelonelontContelonntScorelon -> HighSpammyTwelonelontContelonntScorelon,
    s.SafelontyLabelonlTypelon.HighToxicityScorelon -> HighToxicityScorelon,
    s.SafelontyLabelonlTypelon.HighlyRelonportelondAndMidhighToxicityScorelon -> HighlyRelonportelondAndMidhighToxicityScorelon,
    s.SafelontyLabelonlTypelon.HighlyRelonportelondTwelonelont -> HighlyRelonportelondTwelonelont,
    s.SafelontyLabelonlTypelon.IntelonrstitialDelonvelonlopmelonntOnly -> IntelonrstitialDelonvelonlopmelonntOnly,
    s.SafelontyLabelonlTypelon.IpiDelonvelonlopmelonntOnly -> IpiDelonvelonlopmelonntOnly,
    s.SafelontyLabelonlTypelon.LivelonLowQuality -> LivelonLowQuality,
    s.SafelontyLabelonlTypelon.LowQuality -> LowQuality,
    s.SafelontyLabelonlTypelon.LowQualityMelonntion -> LowQualityMelonntion,
    s.SafelontyLabelonlTypelon.MisinfoCivic -> MisinfoCivic,
    s.SafelontyLabelonlTypelon.MisinfoCrisis -> MisinfoCrisis,
    s.SafelontyLabelonlTypelon.MisinfoGelonnelonric -> MisinfoGelonnelonric,
    s.SafelontyLabelonlTypelon.MisinfoMelondical -> MisinfoMelondical,
    s.SafelontyLabelonlTypelon.NsfaHighPreloncision -> NsfaHighPreloncision,
    s.SafelontyLabelonlTypelon.NsfaHighReloncall -> NsfaHighReloncall,
    s.SafelontyLabelonlTypelon.NsfwCardImagelon -> NsfwCardImagelon,
    s.SafelontyLabelonlTypelon.NsfwHighPreloncision -> NsfwHighPreloncision,
    s.SafelontyLabelonlTypelon.NsfwHighReloncall -> NsfwHighReloncall,
    s.SafelontyLabelonlTypelon.NsfwRelonportelondHelonuristics -> NsfwRelonportelondHelonuristics,
    s.SafelontyLabelonlTypelon.NsfwTelonxt -> NsfwTelonxt,
    s.SafelontyLabelonlTypelon.NsfwTelonxtHighPreloncision -> NsfwTelonxtHighPreloncision,
    s.SafelontyLabelonlTypelon.NsfwVidelono -> NsfwVidelono,
    s.SafelontyLabelonlTypelon.PNelongMultimodalHighPreloncision -> PNelongMultimodalHighPreloncision,
    s.SafelontyLabelonlTypelon.PNelongMultimodalHighReloncall -> PNelongMultimodalHighReloncall,
    s.SafelontyLabelonlTypelon.Pdna -> Pdna,
    s.SafelontyLabelonlTypelon.ReloncommelonndationsLowQuality -> ReloncommelonndationsLowQuality,
    s.SafelontyLabelonlTypelon.RitoActionelondTwelonelont -> RitoActionelondTwelonelont,
    s.SafelontyLabelonlTypelon.SafelontyCrisis -> SafelontyCrisis,
    s.SafelontyLabelonlTypelon.SelonarchBlacklist -> SelonarchBlacklist,
    s.SafelontyLabelonlTypelon.SelonarchBlacklistHighReloncall -> SelonarchBlacklistHighReloncall,
    s.SafelontyLabelonlTypelon.SelonmanticCorelonMisinformation -> SelonmanticCorelonMisinformation,
    s.SafelontyLabelonlTypelon.SmytelonSpamTwelonelont -> SmytelonSpamTwelonelont,
    s.SafelontyLabelonlTypelon.Spam -> Spam,
    s.SafelontyLabelonlTypelon.SpamHighReloncall -> SpamHighReloncall,
    s.SafelontyLabelonlTypelon.TombstonelonDelonvelonlopmelonntOnly -> TombstonelonDelonvelonlopmelonntOnly,
    s.SafelontyLabelonlTypelon.TwelonelontContainsHatelonfulConductSlurHighSelonvelonrity -> TwelonelontContainsHatelonfulConductSlurHighSelonvelonrity,
    s.SafelontyLabelonlTypelon.TwelonelontContainsHatelonfulConductSlurMelondiumSelonvelonrity -> TwelonelontContainsHatelonfulConductSlurMelondiumSelonvelonrity,
    s.SafelontyLabelonlTypelon.TwelonelontContainsHatelonfulConductSlurLowSelonvelonrity -> TwelonelontContainsHatelonfulConductSlurLowSelonvelonrity,
    s.SafelontyLabelonlTypelon.UnsafelonUrl -> UnsafelonUrl,
    s.SafelontyLabelonlTypelon.UntrustelondUrl -> UntrustelondUrl,
    s.SafelontyLabelonlTypelon.FosnrHatelonfulConduct -> FosnrHatelonfulConduct,
    s.SafelontyLabelonlTypelon.FosnrHatelonfulConductLowSelonvelonritySlur -> FosnrHatelonfulConductLowSelonvelonritySlur,
    s.SafelontyLabelonlTypelon.AbusivelonHighReloncall2 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.AbusivelonHighReloncall3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.BrazilianPoliticalTwelonelont -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.BystandelonrAbusivelon2 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.BystandelonrAbusivelon3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.DelonpreloncatelondLabelonl144 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal10Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal11Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal12Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal13Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal14Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal15Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal16Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal17Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal18Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal19Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal1Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal20Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal21Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal22Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal23Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal24Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal25Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal2Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal3Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal4Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal5Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal6Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal7Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal8Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntal9Selonh -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon1 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon10 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon2 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon4 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon5 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon6 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon7 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon8 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalHighHelonalthModelonlScorelon9 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal1 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal4 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal5 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSelonnsitivelonIllelongal6 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSpam1 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSpam2 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntalSpam3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntation -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntation2 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.elonxpelonrimelonntation3 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.HighlyRelonportelondImagelon -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.HighToxicityHoldbackModelonlScorelon -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.LowQualityHighReloncall -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MagicReloncsDelonnylist -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MisinfoCovid19 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MsnfoBrazilianelonlelonction -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MsnfoCovid19Vaccinelon -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MsnfoFrelonnchelonlelonction -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MsnfoPhilippinelonelonlelonction -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.MsnfoUselonlelonction -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.NsfwNelonarPelonrfelonct -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PelonrsonaNonGrata -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PMisinfoCombinelond15 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PMisinfoCombinelond30 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PMisinfoCombinelond50 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PMisinfoDelonnylist -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PMisinfoPVelonracityNudgelon -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.PoliticalTwelonelontelonxpelonrimelonntal1 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncall -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncallContainsSelonlfHarm -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncallelonncouragelonSelonlfHarm -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncallelonpisodic -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncallelonpisodicHatelonfulConduct -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProactivelonTosHighReloncallOthelonrAbuselonPolicy -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.ProjelonctLibra -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.SelonarchHighVisibilityDelonnylist -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.SelonarchHighVisibilityHighReloncallDelonnylist -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond162 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond163 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond164 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond165 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond166 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond167 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond168 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond169 -> Delonpreloncatelond,
    s.SafelontyLabelonlTypelon.Relonselonrvelond170 -> Delonpreloncatelond,
  )

  privatelon lazy val modelonlToThriftMap: Map[TwelonelontSafelontyLabelonlTypelon, s.SafelontyLabelonlTypelon] =
    (for ((k, v) <- thriftToModelonlMap) yielonld (v, k)) ++ Map(
      Delonpreloncatelond -> s.SafelontyLabelonlTypelon.elonnumUnknownSafelontyLabelonlTypelon(DelonpreloncatelondelonnumValuelon),
    )

  caselon objelonct Abusivelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AbusivelonBelonhavior elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AbusivelonBelonhaviorInsults elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AbusivelonBelonhaviorViolelonntThrelonat elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AbusivelonBelonhaviorMajorAbuselon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AbusivelonHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Automation elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AutomationHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Bouncelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BystandelonrAbusivelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfaHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct DuplicatelonContelonnt elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct DuplicatelonMelonntion elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct GorelonAndViolelonncelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon {

    val DelonpreloncatelondAt: Timelon = Timelon.at("2019-09-12 00:00:00 UTC")
  }
  caselon objelonct GorelonAndViolelonncelonHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct LivelonLowQuality elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct LowQuality elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct LowQualityMelonntion elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwCardImagelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwHighPreloncision elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwVidelono elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Pdna elonxtelonnds TwelonelontSafelontyLabelonlTypelon

  caselon objelonct ReloncommelonndationsLowQuality elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SelonarchBlacklist elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Spam elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SpamHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct UntrustelondUrl elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighToxicityScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighPblockScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SelonarchBlacklistHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct ForelonmelonrgelonncyUselonOnly elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighProactivelonTosScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SafelontyCrisis elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct MisinfoCivic elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct MisinfoCrisis elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct MisinfoGelonnelonric elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct MisinfoMelondical elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AdsManagelonrDelonnyList elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct GorelonAndViolelonncelonHighPreloncision elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwRelonportelondHelonuristics elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct GorelonAndViolelonncelonRelonportelondHelonuristics elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighPSpammyTwelonelontScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct DoNotAmplify elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighlyRelonportelondTwelonelont elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct AgathaSpam elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SmytelonSpamTwelonelont elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct SelonmanticCorelonMisinformation elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighPRelonportelondTwelonelontScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighSpammyTwelonelontContelonntScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct GorelonAndViolelonncelonTopicHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct CopypastaSpam elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct elonxpelonrimelonntalSelonnsitivelonIllelongal2 elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct DownrankSpamRelonply elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwTelonxt elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighlyRelonportelondAndMidhighToxicityScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct DynamicProductAd elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct TombstonelonDelonvelonlopmelonntOnly elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct TwelonelontContainsHatelonfulConductSlurHighSelonvelonrity elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct TwelonelontContainsHatelonfulConductSlurMelondiumSelonvelonrity elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct TwelonelontContainsHatelonfulConductSlurLowSelonvelonrity elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct RitoActionelondTwelonelont elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct elonxpelonrimelonntalNudgelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct PNelongMultimodalHighPreloncision elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct PNelongMultimodalHighReloncall elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BrandSafelontyNsfaAggrelongatelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HighCryptospamScorelon elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct IpiDelonvelonlopmelonntOnly elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Bouncelonelondits elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct UnsafelonUrl elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct IntelonrstitialDelonvelonlopmelonntOnly elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct elondiDelonvelonlopmelonntOnly elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfwTelonxtHighPreloncision elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HatelonfulConduct elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct HatelonfulConductViolelonntThrelonat elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct NsfaHighPreloncision elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BrandSafelontyelonxpelonrimelonntal1 elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BrandSafelontyelonxpelonrimelonntal2 elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BrandSafelontyelonxpelonrimelonntal3 elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct BrandSafelontyelonxpelonrimelonntal4 elonxtelonnds TwelonelontSafelontyLabelonlTypelon

  caselon objelonct FosnrHatelonfulConduct elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct FosnrHatelonfulConductLowSelonvelonritySlur elonxtelonnds TwelonelontSafelontyLabelonlTypelon

  caselon objelonct Delonpreloncatelond elonxtelonnds TwelonelontSafelontyLabelonlTypelon
  caselon objelonct Unknown elonxtelonnds TwelonelontSafelontyLabelonlTypelon

  delonf fromThrift(safelontyLabelonlTypelon: s.SafelontyLabelonlTypelon): TwelonelontSafelontyLabelonlTypelon =
    thriftToModelonlMap.gelont(safelontyLabelonlTypelon) match {
      caselon Somelon(twelonelontSafelontyLabelonlTypelon) => twelonelontSafelontyLabelonlTypelon
      caselon _ =>
        safelontyLabelonlTypelon match {
          caselon s.SafelontyLabelonlTypelon.elonnumUnknownSafelontyLabelonlTypelon(DelonpreloncatelondelonnumValuelon) => Delonpreloncatelond
          caselon _ =>
            Unknown
        }
    }

  delonf toThrift(safelontyLabelonlTypelon: TwelonelontSafelontyLabelonlTypelon): s.SafelontyLabelonlTypelon = {
    modelonlToThriftMap.gelontOrelonlselon(safelontyLabelonlTypelon, UnknownThriftSafelontyLabelonlTypelon)
  }
}

objelonct TwelonelontSafelontyLabelonl {
  delonf fromThrift(safelontyLabelonlValuelon: s.SafelontyLabelonlValuelon): TwelonelontSafelontyLabelonl =
    fromTuplelon(safelontyLabelonlValuelon.labelonlTypelon, safelontyLabelonlValuelon.labelonl)

  delonf fromTuplelon(
    safelontyLabelonlTypelon: s.SafelontyLabelonlTypelon,
    safelontyLabelonl: s.SafelontyLabelonl
  ): TwelonelontSafelontyLabelonl = {
    TwelonelontSafelontyLabelonl(
      labelonlTypelon = TwelonelontSafelontyLabelonlTypelon.fromThrift(safelontyLabelonlTypelon),
      sourcelon = safelontyLabelonl.sourcelon.flatMap(LabelonlSourcelon.fromString),
      safelontyLabelonlSourcelon = safelontyLabelonl.safelontyLabelonlSourcelon,
      applicablelonUselonrs = safelontyLabelonl.applicablelonUselonrs
        .map { pelonrspelonctivalUselonrs =>
          (pelonrspelonctivalUselonrs map {
            _.uselonrId
          }).toSelont
        }.gelontOrelonlselon(Selont.elonmpty),
      scorelon = safelontyLabelonl.scorelon,
      modelonlMelontadata = safelontyLabelonl.modelonlMelontadata.flatMap(TwelonelontModelonlMelontadata.fromThrift)
    )
  }

  delonf toThrift(twelonelontSafelontyLabelonl: TwelonelontSafelontyLabelonl): s.SafelontyLabelonlValuelon = {
    s.SafelontyLabelonlValuelon(
      labelonlTypelon = TwelonelontSafelontyLabelonlTypelon.toThrift(twelonelontSafelontyLabelonl.labelonlTypelon),
      labelonl = s.SafelontyLabelonl(
        applicablelonUselonrs = if (twelonelontSafelontyLabelonl.applicablelonUselonrs.nonelonmpty) {
          Somelon(twelonelontSafelontyLabelonl.applicablelonUselonrs.toSelonq.map {
            s.PelonrspelonctivalUselonr(_)
          })
        } elonlselon {
          Nonelon
        },
        sourcelon = twelonelontSafelontyLabelonl.sourcelon.map(_.namelon),
        scorelon = twelonelontSafelontyLabelonl.scorelon,
        modelonlMelontadata = twelonelontSafelontyLabelonl.modelonlMelontadata.map(TwelonelontModelonlMelontadata.toThrift)
      )
    )
  }
}

packagelon com.twittelonr.homelon_mixelonr.modelonl

import com.twittelonr.corelon_workflows.uselonr_modelonl.{thriftscala => um}
import com.twittelonr.dal.pelonrsonal_data.{thriftjava => pd}
import com.twittelonr.elonschelonrbird.{thriftscala => elonsb}
import com.twittelonr.gizmoduck.{thriftscala => gt}
import com.twittelonr.homelon_mixelonr.{thriftscala => hmt}
import com.twittelonr.ml.api.constant.SharelondFelonaturelons
import com.twittelonr.product_mixelonr.componelonnt_library.modelonl.candidatelon.TwelonelontCandidatelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.Felonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.FelonaturelonWithDelonfaultOnFailurelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.BoolDataReloncordCompatiblelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.DataReloncordFelonaturelon
import com.twittelonr.product_mixelonr.corelon.felonaturelon.datareloncord.LongDiscrelontelonDataReloncordCompatiblelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.TopicContelonxtFunctionalityTypelon
import com.twittelonr.product_mixelonr.corelon.pipelonlinelon.PipelonlinelonQuelonry
import com.twittelonr.selonarch.common.felonaturelons.{thriftscala => sc}
import com.twittelonr.timelonlinelonmixelonr.clielonnts.manhattan.DismissInfo
import com.twittelonr.timelonlinelonmixelonr.clielonnts.pelonrsistelonncelon.TimelonlinelonRelonsponselonV3
import com.twittelonr.timelonlinelonmixelonr.injelonction.modelonl.candidatelon.AudioSpacelonMelontaData
import com.twittelonr.timelonlinelons.convelonrsation_felonaturelons.v1.thriftscala.ConvelonrsationFelonaturelons
import com.twittelonr.timelonlinelons.imprelonssion.{thriftscala => imp}
import com.twittelonr.timelonlinelons.imprelonssionbloomfiltelonr.{thriftscala => blm}
import com.twittelonr.timelonlinelons.modelonl.UselonrId
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.common.TimelonlinelonsSharelondFelonaturelons
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.reloncap.ReloncapFelonaturelons
import com.twittelonr.timelonlinelons.prelondiction.felonaturelons.relonquelonst_contelonxt.RelonquelonstContelonxtFelonaturelons
import com.twittelonr.timelonlinelons.selonrvicelon.{thriftscala => tst}
import com.twittelonr.timelonlinelonselonrvicelon.modelonl.Felonelondbackelonntry
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.logging.candidatelon_twelonelont_sourcelon_id.{thriftscala => cts}
import com.twittelonr.timelonlinelonselonrvicelon.suggelonsts.{thriftscala => st}
import com.twittelonr.tsp.{thriftscala => tsp}
import com.twittelonr.twelonelontconvosvc.twelonelont_ancelonstor.{thriftscala => ta}
import com.twittelonr.util.Timelon

objelonct HomelonFelonaturelons {
  // Candidatelon Felonaturelons
  objelonct AncelonstorsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[ta.TwelonelontAncelonstor]]
  objelonct AudioSpacelonMelontaDataFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[AudioSpacelonMelontaData]]
  objelonct TwittelonrListIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]

  /**
   * For Relontwelonelonts, this should relonfelonr to thelon relontwelonelonting uselonr. Uselon [[SourcelonUselonrIdFelonaturelon]] if you want to know
   * who crelonatelond thelon Twelonelont that was relontwelonelontelond.
   */
  objelonct AuthorIdFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
      with LongDiscrelontelonDataReloncordCompatiblelon {
    ovelonrridelon val felonaturelonNamelon: String = SharelondFelonaturelons.AUTHOR_ID.gelontFelonaturelonNamelon
    ovelonrridelon val pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont(pd.PelonrsonalDataTypelon.UselonrId)
  }
  objelonct AuthorIselonligiblelonForConnelonctBoostFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct AuthorIsBluelonVelonrifielondFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct AuthorelondByContelonxtualUselonrFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct CachelondCandidatelonPipelonlinelonIdelonntifielonrFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[String]]
  objelonct CandidatelonSourcelonIdFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[cts.CandidatelonTwelonelontSourcelonId]]
  objelonct ConvelonrsationFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[ConvelonrsationFelonaturelons]]

  /**
   * This fielonld should belon selont to thelon focal Twelonelont's twelonelontId for all twelonelonts which arelon elonxpelonctelond to
   * belon relonndelonrelond in thelon samelon convo modulelon. For non-convo modulelon Twelonelonts, this will belon
   * selont to Nonelon. Notelon this is diffelonrelonnt from how TwelonelontyPielon delonfinelons ConvelonrsationId which is delonfinelond
   * on all Twelonelonts and points to thelon root twelonelont. This felonaturelon is uselond for grouping convo modulelons togelonthelonr.
   */
  objelonct ConvelonrsationModulelonFocalTwelonelontIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]

  /**
   * This fielonld should always belon selont to thelon root Twelonelont in a convelonrsation for all Twelonelonts. For relonplielons, this will
   * point back to thelon root Twelonelont. For non-relonplielons, this will belon thelon candidatelon's Twelonelont id. This is consistelonnt with
   * thelon TwelonelontyPielon delonfinition of ConvelonrsationModulelonId.
   */
  objelonct ConvelonrsationModulelonIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct DirelonctelondAtUselonrIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct elonarlybirdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[sc.ThriftTwelonelontFelonaturelons]]
  objelonct elonarlybirdScorelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Doublelon]]
  objelonct elonntityTokelonnFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[String]]
  objelonct elonxclusivelonConvelonrsationAuthorIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct FavoritelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct FelonelondbackHistoryFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Felonelondbackelonntry]]
  objelonct RelontwelonelontelondByelonngagelonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct RelonplielondByelonngagelonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct FollowelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]

  objelonct TopicIdSocialContelonxtFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct TopicContelonxtFunctionalityTypelonFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[TopicContelonxtFunctionalityTypelon]]
  objelonct FromInNelontworkSourcelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]

  objelonct FullScoringSuccelonelondelondFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct HasDisplayelondTelonxtFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct InRelonplyToTwelonelontIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct InRelonplyToUselonrIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct IsAncelonstorCandidatelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct IselonxtelonndelondRelonplyFelonaturelon
      elonxtelonnds DataReloncordFelonaturelon[TwelonelontCandidatelon, Boolelonan]
      with BoolDataReloncordCompatiblelon {
    ovelonrridelon val felonaturelonNamelon: String = ReloncapFelonaturelons.IS_elonXTelonNDelonD_RelonPLY.gelontFelonaturelonNamelon
    ovelonrridelon val pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct IsRandomTwelonelontFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
      with BoolDataReloncordCompatiblelon {
    ovelonrridelon val felonaturelonNamelon: String = TimelonlinelonsSharelondFelonaturelons.IS_RANDOM_TWelonelonT.gelontFelonaturelonNamelon
    ovelonrridelon val pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct IsRelonadFromCachelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct IsRelontwelonelontFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct IsRelontwelonelontelondRelonplyFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct LastScorelondTimelonstampMsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct NonSelonlfFavoritelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct NumImagelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Int]]
  objelonct OriginalTwelonelontCrelonationTimelonFromSnowflakelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Timelon]]
  objelonct PositionFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Int]]
  // Intelonrnal id gelonnelonratelond pelonr prelondiction selonrvicelon relonquelonst
  objelonct PrelondictionRelonquelonstIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct QuotelondTwelonelontIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct QuotelondUselonrIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct ScorelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Doublelon]]
  objelonct SelonmanticCorelonIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  // Kelony for kafka logging
  objelonct SelonrvelondIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct SimclustelonrsTwelonelontTopKClustelonrsWithScorelonsFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Map[String, Doublelon]]
  objelonct SocialContelonxtFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[tst.SocialContelonxt]]
  objelonct SourcelonTwelonelontIdFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
      with LongDiscrelontelonDataReloncordCompatiblelon {
    ovelonrridelon val felonaturelonNamelon: String = TimelonlinelonsSharelondFelonaturelons.SOURCelon_TWelonelonT_ID.gelontFelonaturelonNamelon
    ovelonrridelon val pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont(pd.PelonrsonalDataTypelon.TwelonelontId)
  }
  objelonct SourcelonUselonrIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct StrelonamToKafkaFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct SuggelonstTypelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[st.SuggelonstTypelon]]
  objelonct TSPMelontricTagFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selont[tsp.MelontricTag]]
  objelonct TwelonelontLanguagelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[String]]
  objelonct TwelonelontUrlsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[String]]
  objelonct VidelonoDurationMsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Int]]
  objelonct VielonwelonrIdFelonaturelon
      elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Long]
      with LongDiscrelontelonDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = SharelondFelonaturelons.USelonR_ID.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont(pd.PelonrsonalDataTypelon.UselonrId)
  }
  objelonct WelonightelondModelonlScorelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Doublelon]]
  objelonct MelonntionUselonrIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct MelonntionScrelonelonnNamelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[String]]
  objelonct SelonmanticAnnotationFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[elonsb.TwelonelontelonntityAnnotation]]
  objelonct HasImagelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct HasVidelonoFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]

  // Twelonelontypielon VF Felonaturelons
  objelonct IsHydratelondFelonaturelon elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[TwelonelontCandidatelon, Boolelonan] {
    ovelonrridelon val delonfaultValuelon: Boolelonan = truelon
  }
  objelonct IsNsfwFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct QuotelondTwelonelontDroppelondFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  // Raw Twelonelont Telonxt from Twelonelontypielon
  objelonct TwelonelontTelonxtFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[String]]

  // SGS Felonaturelons
  /**
   * By convelonntion, this is selont to truelon for relontwelonelonts of non-followelond authors
   * elon.g. whelonrelon somelonbody thelon vielonwelonr follows relontwelonelonts a Twelonelont from somelonbody thelon vielonwelonr doelonsn't follow
   */
  objelonct InNelontworkFelonaturelon elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[TwelonelontCandidatelon, Boolelonan] {
    ovelonrridelon val delonfaultValuelon: Boolelonan = truelon
  }

  // Quelonry Felonaturelons
  objelonct AccountAgelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Timelon]]
  objelonct ClielonntIdFelonaturelon
      elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Long]]
      with LongDiscrelontelonDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = SharelondFelonaturelons.CLIelonNT_ID.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont(pd.PelonrsonalDataTypelon.ClielonntTypelon)
  }
  objelonct CachelondScorelondTwelonelontsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[hmt.CachelondScorelondTwelonelont]]
  objelonct DelonvicelonLanguagelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[String]]
  objelonct DismissInfoFelonaturelon
      elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[PipelonlinelonQuelonry, Map[st.SuggelonstTypelon, Option[DismissInfo]]] {
    ovelonrridelon delonf delonfaultValuelon: Map[st.SuggelonstTypelon, Option[DismissInfo]] = Map.elonmpty
  }
  objelonct FollowingLastNonPollingTimelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Timelon]]
  objelonct GelontInitialFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan] with BoolDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = RelonquelonstContelonxtFelonaturelons.IS_GelonT_INITIAL.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct GelontMiddlelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan] with BoolDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = RelonquelonstContelonxtFelonaturelons.IS_GelonT_MIDDLelon.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct GelontNelonwelonrFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan] with BoolDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = RelonquelonstContelonxtFelonaturelons.IS_GelonT_NelonWelonR.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct GelontOldelonrFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan] with BoolDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = RelonquelonstContelonxtFelonaturelons.IS_GelonT_OLDelonR.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont.elonmpty
  }
  objelonct GuelonstIdFelonaturelon
      elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Long]]
      with LongDiscrelontelonDataReloncordCompatiblelon {
    ovelonrridelon delonf felonaturelonNamelon: String = SharelondFelonaturelons.GUelonST_ID.gelontFelonaturelonNamelon
    ovelonrridelon delonf pelonrsonalDataTypelons: Selont[pd.PelonrsonalDataTypelon] = Selont(pd.PelonrsonalDataTypelon.GuelonstId)
  }
  objelonct HasDarkRelonquelonstFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Boolelonan]]
  objelonct ImprelonssionBloomFiltelonrFelonaturelon
      elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[PipelonlinelonQuelonry, blm.ImprelonssionBloomFiltelonrSelonq] {
    ovelonrridelon delonf delonfaultValuelon: blm.ImprelonssionBloomFiltelonrSelonq =
      blm.ImprelonssionBloomFiltelonrSelonq(Selonq.elonmpty)
  }
  objelonct IsForelongroundRelonquelonstFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan]
  objelonct IsLaunchRelonquelonstFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan]
  objelonct LastNonPollingTimelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Timelon]]
  objelonct NonPollingTimelonsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[Long]]
  objelonct PelonrsistelonncelonelonntrielonsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[TimelonlinelonRelonsponselonV3]]
  objelonct PollingFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan]
  objelonct PullToRelonfrelonshFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan]
  // Scorelons from Relonal Graph relonprelonselonnting thelon relonlationship belontwelonelonn thelon vielonwelonr and anothelonr uselonr
  objelonct RelonalGraphInNelontworkScorelonsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Map[UselonrId, Doublelon]]
  objelonct RelonquelonstJoinIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  // Intelonrnal id gelonnelonratelond pelonr relonquelonst, mainly to delonduplicatelon relon-selonrvelond cachelond twelonelonts in logging
  objelonct SelonrvelondRelonquelonstIdFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Long]]
  objelonct SelonrvelondTwelonelontIdsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[Long]]
  objelonct TwelonelontImprelonssionsFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Selonq[imp.TwelonelontImprelonssionselonntry]]
  objelonct UselonrFollowelondTopicsCountFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Int]]
  objelonct UselonrFollowingCountFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Int]]
  objelonct UselonrScrelonelonnNamelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[String]]
  objelonct UselonrStatelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[um.UselonrStatelon]]
  objelonct UselonrTypelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[gt.UselonrTypelon]]
  objelonct WhoToFollowelonxcludelondUselonrIdsFelonaturelon
      elonxtelonnds FelonaturelonWithDelonfaultOnFailurelon[PipelonlinelonQuelonry, Selonq[Long]] {
    ovelonrridelon delonf delonfaultValuelon = Selonq.elonmpty
  }

  // Relonsult Felonaturelons
  objelonct SelonrvelondSizelonFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Option[Int]]
  objelonct HasRandomTwelonelontFelonaturelon elonxtelonnds Felonaturelon[PipelonlinelonQuelonry, Boolelonan]
  objelonct IsRandomTwelonelontAbovelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct SelonrvelondInConvelonrsationModulelonFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct ConvelonrsationModulelon2DisplayelondTwelonelontsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct ConvelonrsationModulelonHasGapFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Boolelonan]
  objelonct SGSValidLikelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct SGSValidFollowelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct PelonrspelonctivelonFiltelonrelondLikelondByUselonrIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
  objelonct ScrelonelonnNamelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Map[Long, String]]
  objelonct RelonalNamelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Map[Long, String]]

  /**
   * Felonaturelons around thelon focal Twelonelont for Twelonelonts which should belon relonndelonrelond in convo modulelons.
   * Thelonselon arelon nelonelondelond in ordelonr to relonndelonr social contelonxt abovelon thelon root twelonelont in a convo modulelons.
   * For elonxamplelon if welon havelon a convo modulelon A-B-C (A Twelonelonts, B relonplielons to A, C relonplielons to B), thelon delonscelonndant felonaturelons arelon
   * for thelon Twelonelont C. Thelonselon felonaturelons arelon Nonelon elonxcelonpt for thelon root Twelonelont for Twelonelonts which should relonndelonr into
   * convo modulelons.
   */
  objelonct FocalTwelonelontAuthorIdFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Long]]
  objelonct FocalTwelonelontInNelontworkFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Boolelonan]]
  objelonct FocalTwelonelontRelonalNamelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Map[Long, String]]]
  objelonct FocalTwelonelontScrelonelonnNamelonsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Option[Map[Long, String]]]
  objelonct MelondiaUndelonrstandingAnnotationIdsFelonaturelon elonxtelonnds Felonaturelon[TwelonelontCandidatelon, Selonq[Long]]
}

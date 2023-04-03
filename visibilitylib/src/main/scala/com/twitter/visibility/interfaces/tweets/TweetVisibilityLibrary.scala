packagelon com.twittelonr.visibility.intelonrfacelons.twelonelonts

import com.twittelonr.deloncidelonr.Deloncidelonr
import com.twittelonr.felonaturelonswitchelons.v2.FelonaturelonSwitchelons
import com.twittelonr.finaglelon.stats.StatsReloncelonivelonr
import com.twittelonr.melondiaselonrvicelons.melondia_util.GelonnelonricMelondiaKelony
import com.twittelonr.selonrvo.util.Gatelon
import com.twittelonr.stitch.Stitch
import com.twittelonr.strato.clielonnt.{Clielonnt => StratoClielonnt}
import com.twittelonr.util.Stopwatch
import com.twittelonr.visibility.VisibilityLibrary
import com.twittelonr.visibility.buildelonr.VelonrdictLoggelonr
import com.twittelonr.visibility.buildelonr.VisibilityRelonsult
import com.twittelonr.visibility.buildelonr.common.MutelondKelonywordFelonaturelons
import com.twittelonr.visibility.buildelonr.melondia._
import com.twittelonr.visibility.buildelonr.twelonelonts.TwelonelontVisibilityNudgelonSourcelonWrappelonr
import com.twittelonr.visibility.buildelonr.twelonelonts._
import com.twittelonr.visibility.buildelonr.uselonrs.AuthorFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.RelonlationshipFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrSelonarchSafelontyFelonaturelons
import com.twittelonr.visibility.buildelonr.uselonrs.VielonwelonrSelonnsitivelonMelondiaSelonttingsFelonaturelons
import com.twittelonr.visibility.common._
import com.twittelonr.visibility.common.actions.LimitelondAction
import com.twittelonr.visibility.common.actions.LimitelondActionTypelon
import com.twittelonr.visibility.common.actions.LimitelondActionsPolicy
import com.twittelonr.visibility.rulelons.ComposablelonActions._
import com.twittelonr.visibility.configapi.configs.VisibilityDeloncidelonrGatelons
import com.twittelonr.visibility.felonaturelons.TwelonelontIsInnelonrQuotelondTwelonelont
import com.twittelonr.visibility.felonaturelons.TwelonelontIsRelontwelonelont
import com.twittelonr.visibility.felonaturelons.TwelonelontIsSourcelonTwelonelont
import com.twittelonr.visibility.gelonnelonrators.LocalizelondIntelonrstitialGelonnelonrator
import com.twittelonr.visibility.gelonnelonrators.TombstonelonGelonnelonrator
import com.twittelonr.visibility.intelonrfacelons.twelonelonts.elonnrichmelonnts.CompliancelonTwelonelontNoticelonelonnrichmelonnt
import com.twittelonr.visibility.intelonrfacelons.twelonelonts.elonnrichmelonnts.LimitelondActionsPolicyelonnrichmelonnt
import com.twittelonr.visibility.intelonrfacelons.twelonelonts.elonnrichmelonnts.TwelonelontVisibilityNudgelonelonnrichmelonnt
import com.twittelonr.visibility.logging.thriftscala.VFLibTypelon
import com.twittelonr.visibility.modelonls.ContelonntId.TwelonelontId
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl
import com.twittelonr.visibility.modelonls.SafelontyLelonvelonl.toThrift
import com.twittelonr.visibility.rulelons._

objelonct TwelonelontVisibilityLibrary {
  typelon Typelon = TwelonelontVisibilityRelonquelonst => Stitch[VisibilityRelonsult]

  delonf apply(
    visibilityLibrary: VisibilityLibrary,
    uselonrSourcelon: UselonrSourcelon,
    uselonrRelonlationshipSourcelon: UselonrRelonlationshipSourcelon,
    kelonywordMatchelonr: KelonywordMatchelonr.Matchelonr,
    invitelondToConvelonrsationRelonpo: InvitelondToConvelonrsationRelonpo,
    deloncidelonr: Deloncidelonr,
    stratoClielonnt: StratoClielonnt,
    localizationSourcelon: LocalizationSourcelon,
    twelonelontPelonrspelonctivelonSourcelon: TwelonelontPelonrspelonctivelonSourcelon,
    twelonelontMelondiaMelontadataSourcelon: TwelonelontMelondiaMelontadataSourcelon,
    tombstonelonGelonnelonrator: TombstonelonGelonnelonrator,
    intelonrstitialGelonnelonrator: LocalizelondIntelonrstitialGelonnelonrator,
    limitelondActionsFelonaturelonSwitchelons: FelonaturelonSwitchelons,
    elonnablelonParityTelonst: Gatelon[Unit] = Gatelon.Falselon
  ): Typelon = {
    val libraryStatsReloncelonivelonr = visibilityLibrary.statsReloncelonivelonr
    val stratoClielonntStatsReloncelonivelonr = visibilityLibrary.statsReloncelonivelonr.scopelon("strato")
    val vfelonnginelonCountelonr = libraryStatsReloncelonivelonr.countelonr("vf_elonnginelon_relonquelonsts")
    val vfLatelonncyOvelonrallStat = libraryStatsReloncelonivelonr.stat("vf_latelonncy_ovelonrall")
    val vfLatelonncyStitchBuildStat = libraryStatsReloncelonivelonr.stat("vf_latelonncy_stitch_build")
    val vfLatelonncyStitchRunStat = libraryStatsReloncelonivelonr.stat("vf_latelonncy_stitch_run")
    val visibilityDeloncidelonrGatelons = VisibilityDeloncidelonrGatelons(deloncidelonr)
    val velonrdictLoggelonr =
      crelonatelonVelonrdictLoggelonr(
        visibilityDeloncidelonrGatelons.elonnablelonVelonrdictLoggelonrTVL,
        deloncidelonr,
        libraryStatsReloncelonivelonr)

    val twelonelontLabelonlMaps = nelonw StratoTwelonelontLabelonlMaps(
      SafelontyLabelonlMapSourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr))

    val melondiaLabelonlMaps = nelonw StratoMelondiaLabelonlMaps(
      MelondiaSafelontyLabelonlMapSourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr))

    val twelonelontFelonaturelons = nelonw TwelonelontFelonaturelons(twelonelontLabelonlMaps, libraryStatsReloncelonivelonr)
    val twelonelontPelonrspelonctivelonFelonaturelons =
      nelonw TwelonelontPelonrspelonctivelonFelonaturelons(twelonelontPelonrspelonctivelonSourcelon, libraryStatsReloncelonivelonr)
    val melondiaFelonaturelons = nelonw MelondiaFelonaturelons(melondiaLabelonlMaps, libraryStatsReloncelonivelonr)
    val twelonelontMelondiaMelontadataFelonaturelons =
      nelonw TwelonelontMelondiaMelontadataFelonaturelons(twelonelontMelondiaMelontadataSourcelon, libraryStatsReloncelonivelonr)
    val authorFelonaturelons = nelonw AuthorFelonaturelons(uselonrSourcelon, libraryStatsReloncelonivelonr)
    val vielonwelonrFelonaturelons = nelonw VielonwelonrFelonaturelons(uselonrSourcelon, libraryStatsReloncelonivelonr)
    val mutelondKelonywordFelonaturelons =
      nelonw MutelondKelonywordFelonaturelons(
        uselonrSourcelon,
        uselonrRelonlationshipSourcelon,
        kelonywordMatchelonr,
        libraryStatsReloncelonivelonr,
        visibilityDeloncidelonrGatelons.elonnablelonFollowChelonckInMutelondKelonyword
      )
    val relonlationshipFelonaturelons =
      nelonw RelonlationshipFelonaturelons(uselonrRelonlationshipSourcelon, libraryStatsReloncelonivelonr)
    val fonsrRelonlationshipFelonaturelons =
      nelonw FosnrRelonlationshipFelonaturelons(
        twelonelontLabelonls = twelonelontLabelonlMaps,
        uselonrRelonlationshipSourcelon = uselonrRelonlationshipSourcelon,
        statsReloncelonivelonr = libraryStatsReloncelonivelonr)
    val convelonrsationControlFelonaturelons =
      nelonw ConvelonrsationControlFelonaturelons(
        relonlationshipFelonaturelons,
        invitelondToConvelonrsationRelonpo,
        libraryStatsReloncelonivelonr
      )
    val elonxclusivelonTwelonelontFelonaturelons =
      nelonw elonxclusivelonTwelonelontFelonaturelons(uselonrRelonlationshipSourcelon, libraryStatsReloncelonivelonr)

    val vielonwelonrSelonarchSafelontyFelonaturelons = nelonw VielonwelonrSelonarchSafelontyFelonaturelons(
      UselonrSelonarchSafelontySourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr),
      libraryStatsReloncelonivelonr)

    val vielonwelonrSelonnsitivelonMelondiaSelonttingsFelonaturelons = nelonw VielonwelonrSelonnsitivelonMelondiaSelonttingsFelonaturelons(
      UselonrSelonnsitivelonMelondiaSelonttingsSourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr),
      libraryStatsReloncelonivelonr)

    val toxicRelonplyFiltelonrFelonaturelon = nelonw ToxicRelonplyFiltelonrFelonaturelon(statsReloncelonivelonr = libraryStatsReloncelonivelonr)

    val misinfoPolicySourcelon =
      MisinformationPolicySourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr)
    val misinfoPolicyFelonaturelons =
      nelonw MisinformationPolicyFelonaturelons(misinfoPolicySourcelon, stratoClielonntStatsReloncelonivelonr)

    val communityTwelonelontFelonaturelons = nelonw CommunityTwelonelontFelonaturelonsV2(
      communitielonsSourcelon = CommunitielonsSourcelon.fromStrato(
        stratoClielonnt,
        stratoClielonntStatsReloncelonivelonr
      )
    )

    val trustelondFrielonndsTwelonelontFelonaturelons = nelonw TrustelondFrielonndsFelonaturelons(
      trustelondFrielonndsSourcelon =
        TrustelondFrielonndsSourcelon.fromStrato(stratoClielonnt, stratoClielonntStatsReloncelonivelonr))

    val elonditTwelonelontFelonaturelons = nelonw elonditTwelonelontFelonaturelons(libraryStatsReloncelonivelonr)

    val parityTelonst = nelonw TwelonelontVisibilityLibraryParityTelonst(libraryStatsReloncelonivelonr, stratoClielonnt)

    val localizelondNudgelonSourcelon =
      LocalizelondNudgelonSourcelon.fromLocalizationSourcelon(localizationSourcelon)
    val twelonelontVisibilityNudgelonFelonaturelons =
      nelonw TwelonelontVisibilityNudgelonSourcelonWrappelonr(localizelondNudgelonSourcelon)

    val localizelondLimitelondActionsSourcelon =
      LocalizelondLimitelondActionsSourcelon.fromLocalizationSourcelon(localizationSourcelon)

    { r: TwelonelontVisibilityRelonquelonst =>
      val elonlapselond = Stopwatch.start()
      var runStitchStartMs = 0L
      vfelonnginelonCountelonr.incr()

      val contelonntId = TwelonelontId(r.twelonelont.id)
      val vielonwelonrId = r.vielonwelonrContelonxt.uselonrId
      val authorId = corelonData.uselonrId
      val twelonelontGelonnelonricMelondiaKelonys = r.twelonelont.melondiaRelonfs
        .gelontOrelonlselon(Selonq.elonmpty)
        .flatMap { melondiaRelonf =>
          GelonnelonricMelondiaKelony.fromStringKelony(melondiaRelonf.gelonnelonricMelondiaKelony)
        }

      val tpf =
        twelonelontPelonrspelonctivelonFelonaturelons.forTwelonelont(
          r.twelonelont,
          vielonwelonrId,
          visibilityDeloncidelonrGatelons.elonnablelonFelontchTwelonelontRelonportelondPelonrspelonctivelon())

      val felonaturelonMap =
        visibilityLibrary.felonaturelonMapBuildelonr(
          Selonq(
            mutelondKelonywordFelonaturelons.forTwelonelont(r.twelonelont, vielonwelonrId, authorId),
            vielonwelonrFelonaturelons.forVielonwelonrContelonxt(r.vielonwelonrContelonxt),
            vielonwelonrSelonarchSafelontyFelonaturelons.forVielonwelonrId(vielonwelonrId),
            vielonwelonrSelonnsitivelonMelondiaSelonttingsFelonaturelons.forVielonwelonrId(vielonwelonrId),
            relonlationshipFelonaturelons.forAuthorId(authorId, vielonwelonrId),
            fonsrRelonlationshipFelonaturelons
              .forTwelonelontAndAuthorId(twelonelont = r.twelonelont, authorId = authorId, vielonwelonrId = vielonwelonrId),
            twelonelontFelonaturelons.forTwelonelont(r.twelonelont),
            tpf,
            melondiaFelonaturelons.forMelondiaKelonys(twelonelontGelonnelonricMelondiaKelonys),
            authorFelonaturelons.forAuthorId(authorId),
            convelonrsationControlFelonaturelons.forTwelonelont(r.twelonelont, vielonwelonrId),
            _.withConstantFelonaturelon(TwelonelontIsInnelonrQuotelondTwelonelont, r.isInnelonrQuotelondTwelonelont),
            _.withConstantFelonaturelon(TwelonelontIsRelontwelonelont, r.isRelontwelonelont),
            _.withConstantFelonaturelon(TwelonelontIsSourcelonTwelonelont, r.isSourcelonTwelonelont),
            misinfoPolicyFelonaturelons.forTwelonelont(r.twelonelont, r.vielonwelonrContelonxt),
            elonxclusivelonTwelonelontFelonaturelons.forTwelonelont(r.twelonelont, r.vielonwelonrContelonxt),
            communityTwelonelontFelonaturelons.forTwelonelont(r.twelonelont, r.vielonwelonrContelonxt),
            twelonelontMelondiaMelontadataFelonaturelons
              .forTwelonelont(
                r.twelonelont,
                twelonelontGelonnelonricMelondiaKelonys,
                visibilityDeloncidelonrGatelons.elonnablelonFelontchTwelonelontMelondiaMelontadata()),
            trustelondFrielonndsTwelonelontFelonaturelons.forTwelonelont(r.twelonelont, vielonwelonrId),
            elonditTwelonelontFelonaturelons.forTwelonelont(r.twelonelont),
            toxicRelonplyFiltelonrFelonaturelon.forTwelonelont(r.twelonelont, vielonwelonrId),
          )
        )

      val languagelonCodelon = r.vielonwelonrContelonxt.relonquelonstLanguagelonCodelon.gelontOrelonlselon("elonn")
      val countryCodelon = r.vielonwelonrContelonxt.relonquelonstCountryCodelon

      val relonsponselon = visibilityLibrary
        .runRulelonelonnginelon(
          contelonntId,
          felonaturelonMap,
          r.vielonwelonrContelonxt,
          r.safelontyLelonvelonl
        )
        .map(
          TwelonelontVisibilityNudgelonelonnrichmelonnt(
            _,
            twelonelontVisibilityNudgelonFelonaturelons,
            languagelonCodelon,
            countryCodelon))
        .map(velonrdict => {
          if (visibilityDeloncidelonrGatelons.elonnablelonBackelonndLimitelondActions()) {
            LimitelondActionsPolicyelonnrichmelonnt(
              velonrdict,
              localizelondLimitelondActionsSourcelon,
              languagelonCodelon,
              countryCodelon,
              limitelondActionsFelonaturelonSwitchelons,
              libraryStatsReloncelonivelonr)
          } elonlselon {
            velonrdict
          }
        })
        .map(
          handlelonComposablelonVisibilityRelonsult(
            _,
            visibilityDeloncidelonrGatelons.elonnablelonMelondiaIntelonrstitialComposition(),
            visibilityDeloncidelonrGatelons.elonnablelonBackelonndLimitelondActions()))
        .map(handlelonInnelonrQuotelondTwelonelontVisibilityRelonsult(_, r.isInnelonrQuotelondTwelonelont))
        .map(tombstonelonGelonnelonrator(_, languagelonCodelon))
        .map(intelonrstitialGelonnelonrator(_, languagelonCodelon))
        .map(CompliancelonTwelonelontNoticelonelonnrichmelonnt(_, libraryStatsReloncelonivelonr))
        .onSuccelonss(_ => {
          val ovelonrallStatMs = elonlapselond().inMilliselonconds
          vfLatelonncyOvelonrallStat.add(ovelonrallStatMs)
          val runStitchelonndMs = elonlapselond().inMilliselonconds
          vfLatelonncyStitchRunStat.add(runStitchelonndMs - runStitchStartMs)
        })
        .onSuccelonss(
          scribelonVisibilityVelonrdict(
            _,
            visibilityDeloncidelonrGatelons.elonnablelonVelonrdictScribingTVL,
            velonrdictLoggelonr,
            r.vielonwelonrContelonxt.uselonrId,
            r.safelontyLelonvelonl))

      runStitchStartMs = elonlapselond().inMilliselonconds
      val buildStitchStatMs = elonlapselond().inMilliselonconds
      vfLatelonncyStitchBuildStat.add(buildStitchStatMs)

      if (elonnablelonParityTelonst()) {
        relonsponselon.applyelonffelonct { relonsp =>
          Stitch.async(parityTelonst.runParityTelonst(r, relonsp))
        }
      } elonlselon {
        relonsponselon
      }
    }
  }

  delonf handlelonComposablelonVisibilityRelonsult(
    relonsult: VisibilityRelonsult,
    elonnablelonMelondiaIntelonrstitialComposition: Boolelonan,
    elonnablelonBackelonndLimitelondActions: Boolelonan
  ): VisibilityRelonsult = {
    if (relonsult.seloncondaryVelonrdicts.nonelonmpty || elonnablelonBackelonndLimitelondActions) {
      relonsult.copy(velonrdict = composelonActions(
        relonsult.velonrdict,
        relonsult.seloncondaryVelonrdicts,
        elonnablelonMelondiaIntelonrstitialComposition,
        elonnablelonBackelonndLimitelondActions))
    } elonlselon {
      relonsult
    }
  }

  delonf handlelonInnelonrQuotelondTwelonelontVisibilityRelonsult(
    relonsult: VisibilityRelonsult,
    isInnelonrQuotelondTwelonelont: Boolelonan
  ): VisibilityRelonsult = {
    val nelonwVelonrdict: Action =
      relonsult.velonrdict match {
        caselon Intelonrstitial(Relonason.Nsfw | Relonason.NsfwMelondia, _, _) if isInnelonrQuotelondTwelonelont =>
          Drop(Relonason.Nsfw)
        caselon ComposablelonActionsWithIntelonrstitial(twelonelontIntelonrstitial)
            if isInnelonrQuotelondTwelonelont && (twelonelontIntelonrstitial.relonason == Relonason.Nsfw || twelonelontIntelonrstitial.relonason == Relonason.NsfwMelondia) =>
          Drop(Relonason.Nsfw)
        caselon velonrdict => velonrdict
      }

    relonsult.copy(velonrdict = nelonwVelonrdict)
  }

  delonf hasTwelonelontRulelons(safelontyLelonvelonl: SafelontyLelonvelonl): Boolelonan = RulelonBaselon.hasTwelonelontRulelons(safelontyLelonvelonl)

  delonf composelonActions(
    primary: Action,
    seloncondary: Selonq[Action],
    elonnablelonMelondiaIntelonrstitialComposition: Boolelonan,
    elonnablelonBackelonndLimitelondActions: Boolelonan
  ): Action = {
    if (primary.isComposablelon && (seloncondary.nonelonmpty || elonnablelonBackelonndLimitelondActions)) {
      val actions = Selonq[Action] { primary } ++ seloncondary
      val intelonrstitialOpt = Action.gelontFirstIntelonrstitial(actions: _*)
      val softIntelonrvelonntionOpt = Action.gelontFirstSoftIntelonrvelonntion(actions: _*)
      val downrankOpt = Action.gelontFirstDownrankHomelonTimelonlinelon(actions: _*)
      val avoidOpt = Action.gelontFirstAvoid(actions: _*)
      val twelonelontVisibilityNudgelonOpt = Action.gelontFirstTwelonelontVisibilityNudgelon(actions: _*)

      val melondiaIntelonrstitialOpt = {
        val firstMelondiaIntelonrstitialOpt = Action.gelontFirstMelondiaIntelonrstitial(actions: _*)
        if (elonnablelonMelondiaIntelonrstitialComposition && intelonrstitialOpt != firstMelondiaIntelonrstitialOpt) {
          firstMelondiaIntelonrstitialOpt
        } elonlselon {
          Nonelon
        }
      }

      val limitelondelonngagelonmelonntsOpt = elonnablelonBackelonndLimitelondActions match {
        caselon truelon => buildCompositelonLimitelondelonngagelonmelonnts(Action.gelontAllLimitelondelonngagelonmelonnts(actions: _*))
        caselon falselon => Action.gelontFirstLimitelondelonngagelonmelonnts(actions: _*)
      }

      val abusivelonQualityOpt = {
        if (actions.contains(ConvelonrsationSelonctionAbusivelonQuality)) {
          Somelon(ConvelonrsationSelonctionAbusivelonQuality)
        } elonlselon {
          Nonelon
        }
      }

      val numActions =
        Selonq[Option[_]](
          intelonrstitialOpt,
          softIntelonrvelonntionOpt,
          limitelondelonngagelonmelonntsOpt,
          downrankOpt,
          avoidOpt,
          melondiaIntelonrstitialOpt,
          twelonelontVisibilityNudgelonOpt,
          abusivelonQualityOpt)
          .count(_.isDelonfinelond)
      if (numActions > 1) {
        TwelonelontIntelonrstitial(
          intelonrstitialOpt,
          softIntelonrvelonntionOpt,
          limitelondelonngagelonmelonntsOpt,
          downrankOpt,
          avoidOpt,
          melondiaIntelonrstitialOpt,
          twelonelontVisibilityNudgelonOpt,
          abusivelonQualityOpt
        )
      } elonlselon {
        if (elonnablelonBackelonndLimitelondActions) {
          limitelondelonngagelonmelonntsOpt.gelontOrelonlselon(primary)
        } elonlselon {
          primary
        }
      }
    } elonlselon {
      primary
    }
  }

  delonf scribelonVisibilityVelonrdict(
    relonsult: VisibilityRelonsult,
    elonnablelonVelonrdictScribing: Gatelon[Unit],
    velonrdictLoggelonr: VelonrdictLoggelonr,
    vielonwelonrId: Option[Long],
    safelontyLelonvelonl: SafelontyLelonvelonl
  ): Unit = if (elonnablelonVelonrdictScribing()) {
    velonrdictLoggelonr.scribelonVelonrdict(
      visibilityRelonsult = relonsult,
      vielonwelonrId = vielonwelonrId,
      safelontyLelonvelonl = toThrift(safelontyLelonvelonl),
      vfLibTypelon = VFLibTypelon.TwelonelontVisibilityLibrary)
  }

  delonf buildCompositelonLimitelondelonngagelonmelonnts(
    limitelondelonngagelonmelonnts: Selonq[IsLimitelondelonngagelonmelonnts]
  ): Option[Limitelondelonngagelonmelonnts] = {
    limitelondelonngagelonmelonnts.helonadOption.flatMap { limitelondelonngagelonmelonnt =>
      val distinctLimitelondActions = limitelondelonngagelonmelonnts
        .collelonct({ caselon IsLimitelondelonngagelonmelonnts(Somelon(policy), _) => policy.limitelondActions })
        .flattelonn
        .foldRight(Map.elonmpty[LimitelondActionTypelon, LimitelondAction])({ (limitelondAction, acc) =>
          acc + ((limitelondAction.limitelondActionTypelon, limitelondAction))
        })
        .valuelons
        .toSelonq

      if (distinctLimitelondActions.nonelonmpty) {
        val limitelondActionsPolicy = Somelon(LimitelondActionsPolicy(distinctLimitelondActions))
        Somelon(Limitelondelonngagelonmelonnts(limitelondelonngagelonmelonnt.gelontLimitelondelonngagelonmelonntRelonason, limitelondActionsPolicy))
      } elonlselon {
        Nonelon
      }
    }
  }

  delonf crelonatelonVelonrdictLoggelonr(
    elonnablelonVelonrdictLoggelonr: Gatelon[Unit],
    deloncidelonr: Deloncidelonr,
    statsReloncelonivelonr: StatsReloncelonivelonr
  ): VelonrdictLoggelonr = {
    if (elonnablelonVelonrdictLoggelonr()) {
      VelonrdictLoggelonr(statsReloncelonivelonr, deloncidelonr)
    } elonlselon {
      VelonrdictLoggelonr.elonmpty
    }
  }
}

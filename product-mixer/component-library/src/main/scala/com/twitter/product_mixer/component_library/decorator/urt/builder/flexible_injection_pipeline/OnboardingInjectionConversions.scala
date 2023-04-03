packagelon com.twittelonr.product_mixelonr.componelonnt_library.deloncorator.urt.buildelonr.flelonxiblelon_injelonction_pipelonlinelon

import com.twittelonr.onboarding.injelonctions.{thriftscala => onboardingthrift}
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CelonntelonrCovelonrHalfCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrBelonhaviorDismiss
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrBelonhaviorNavigatelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrCta
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrCtaBelonhavior
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrHalfCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.CovelonrImagelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.covelonr.HalfCovelonrDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon._
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.FollowAllMelonssagelonActionTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.LargelonUselonrFacelonpilelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonActionTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonImagelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.MelonssagelonTelonxtAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.itelonm.melonssagelon.UselonrFacelonpilelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Bouncelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.ButtonStylelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Delonfault
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Primary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Seloncondary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Telonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Delonstructivelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.Nelonutral
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.DelonstructivelonSeloncondary
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.button.DelonstructivelonTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Callback
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ClielonntelonvelonntInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DelonelonpLink
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Dismiss
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.DismissInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.elonxtelonrnalUrl
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackAction
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FelonelondbackActionInfo
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FollowGelonnelonralContelonxtTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.GelonnelonralContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonAnimationTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonDisplayTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.ImagelonVariant
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.FullWidth
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Icon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.IconSmall
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.SocialContelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Url
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.Urtelonndpoint
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata.UrtelonndpointOptions
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Celonntelonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Natural
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Plain
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RelonfelonrelonncelonObjelonct
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtAlignmelonnt
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtelonntity
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtFormat
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtCashtag
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtHashtag
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtList
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtMelonntion
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.RichTelonxtUselonr
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.richtelonxt.Strong

/***
 * Helonlpelonr class to convelonrt onboarding thrift to product-mixelonr modelonls
 */
objelonct OnboardingInjelonctionConvelonrsions {

  delonf convelonrtFelonelondbackInfo(
    felonelondbackInfo: onboardingthrift.FelonelondbackInfo
  ): FelonelondbackActionInfo = {
    val actions = felonelondbackInfo.actions.map {
      caselon onboardingthrift.FelonelondbackAction.DismissAction(dismissAction) =>
        FelonelondbackAction(
          Dismiss,
          prompt = dismissAction.prompt,
          confirmation = dismissAction.confirmation,
          hasUndoAction = dismissAction.hasUndoAction,
          felonelondbackUrl = dismissAction.felonelondbackUrl,
          childFelonelondbackActions =
            Nonelon,
          confirmationDisplayTypelon = Nonelon,
          clielonntelonvelonntInfo = Nonelon,
          icon = Nonelon,
          richBelonhavior = Nonelon,
          subprompt = Nonelon,
          elonncodelondFelonelondbackRelonquelonst = Nonelon
        )
      caselon onboardingthrift.FelonelondbackAction.UnknownUnionFielonld(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

    FelonelondbackActionInfo(
      felonelondbackActions = actions,
      felonelondbackMelontadata = Nonelon,
      displayContelonxt = Nonelon,
      clielonntelonvelonntInfo = Nonelon)
  }

  delonf convelonrtClielonntelonvelonntInfo(input: onboardingthrift.ClielonntelonvelonntInfo): ClielonntelonvelonntInfo =
    ClielonntelonvelonntInfo(
      componelonnt = input.componelonnt,
      elonlelonmelonnt = input.elonlelonmelonnt,
      delontails = Nonelon,
      action = input.action,
      elonntityTokelonn = Nonelon)

  delonf convelonrtCallback(callback: onboardingthrift.Callback): Callback =
    Callback(callback.elonndpoint)

  delonf convelonrtImagelon(imagelon: onboardingthrift.Imagelon): MelonssagelonImagelon =
    MelonssagelonImagelon(
      Selont(convelonrtImagelonVariant(imagelon.imagelon)),
      backgroundColor =
        Nonelon
    )

  delonf convelonrtCovelonrImagelon(imagelon: onboardingthrift.Imagelon): CovelonrImagelon =
    CovelonrImagelon(
      convelonrtImagelonVariant(imagelon.imagelon),
      imagelonDisplayTypelon = convelonrtImagelonDisplayTypelon(imagelon.imagelonDisplayTypelon),
      imagelonAnimationTypelon = imagelon.imagelonAnimationTypelon.map(convelonrtImagelonAnimationTypelon),
    )

  delonf convelonrtImagelonDisplayTypelon(
    imagelonDisplayTypelon: onboardingthrift.ImagelonDisplayTypelon
  ): ImagelonDisplayTypelon =
    imagelonDisplayTypelon match {
      caselon onboardingthrift.ImagelonDisplayTypelon.Icon => Icon
      caselon onboardingthrift.ImagelonDisplayTypelon.FullWidth => FullWidth
      caselon onboardingthrift.ImagelonDisplayTypelon.IconSmall => IconSmall
      caselon onboardingthrift.ImagelonDisplayTypelon.elonnumUnknownImagelonDisplayTypelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  privatelon delonf convelonrtImagelonAnimationTypelon(
    imagelonAnimationTypelon: onboardingthrift.ImagelonAnimationTypelon
  ): ImagelonAnimationTypelon =
    imagelonAnimationTypelon match {
      caselon onboardingthrift.ImagelonAnimationTypelon.Bouncelon => Bouncelon
      caselon onboardingthrift.ImagelonAnimationTypelon.elonnumUnknownImagelonAnimationTypelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  delonf convelonrtImagelonVariant(imagelonVariant: onboardingthrift.ImagelonVariant): ImagelonVariant =
    ImagelonVariant(
      url = imagelonVariant.url,
      width = imagelonVariant.width,
      helonight = imagelonVariant.helonight,
      palelonttelon = Nonelon)

  delonf convelonrtButtonAction(
    buttonAction: onboardingthrift.ButtonAction
  ): MelonssagelonTelonxtAction =
    MelonssagelonTelonxtAction(
      buttonAction.telonxt,
      MelonssagelonAction(
        dismissOnClick = buttonAction.dismissOnClick.gelontOrelonlselon(truelon),
        url = gelontActionUrl(buttonAction),
        clielonntelonvelonntInfo = Somelon(convelonrtClielonntelonvelonntInfo(buttonAction.clielonntelonvelonntInfo)),
        onClickCallbacks = buttonAction.callbacks.map(_.map(convelonrtCallback).toList)
      )
    )

  privatelon delonf gelontActionUrl(buttonAction: onboardingthrift.ButtonAction) =
    buttonAction.buttonBelonhavior match {
      caselon onboardingthrift.ButtonBelonhavior.Navigatelon(navigatelon) => Somelon(navigatelon.url.url)
      caselon onboardingthrift.ButtonBelonhavior.Dismiss(_) => Nonelon
      caselon onboardingthrift.ButtonBelonhavior.UnknownUnionFielonld(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  delonf convelonrtRichTelonxt(
    richTelonxt: com.twittelonr.onboarding.injelonctions.thriftscala.RichTelonxt
  ): RichTelonxt = {
    val elonntitielons = richTelonxt.elonntitielons.map(elonntity =>
      RichTelonxtelonntity(
        elonntity.fromIndelonx,
        elonntity.toIndelonx,
        elonntity.relonf.map(convelonrtRelonf),
        elonntity.format.map(convelonrtFormat)))
    RichTelonxt(
      telonxt = richTelonxt.telonxt,
      elonntitielons = elonntitielons.toList,
      rtl = richTelonxt.rtl,
      alignmelonnt = richTelonxt.alignmelonnt.map(convelonrtAlignmelonnt))
  }

  privatelon delonf convelonrtAlignmelonnt(alignmelonnt: onboardingthrift.RichTelonxtAlignmelonnt): RichTelonxtAlignmelonnt =
    alignmelonnt match {
      caselon onboardingthrift.RichTelonxtAlignmelonnt.Natural => Natural
      caselon onboardingthrift.RichTelonxtAlignmelonnt.Celonntelonr => Celonntelonr
      caselon onboardingthrift.RichTelonxtAlignmelonnt.elonnumUnknownRichTelonxtAlignmelonnt(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  privatelon delonf convelonrtRelonf(relonf: onboardingthrift.RelonfelonrelonncelonObjelonct): RelonfelonrelonncelonObjelonct =
    relonf match {
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.Uselonr(uselonr) => RichTelonxtUselonr(uselonr.id)
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.Melonntion(melonntion) =>
        RichTelonxtMelonntion(melonntion.id, melonntion.screlonelonnNamelon)
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.Hashtag(hashtag) => RichTelonxtHashtag(hashtag.telonxt)

      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.Cashtag(cashtag) => RichTelonxtCashtag(cashtag.telonxt)
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.TwittelonrList(twList) =>
        RichTelonxtList(twList.id, twList.url)
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.Url(url) => RichTelonxtHashtag(url.url)
      caselon onboardingthrift.RelonfelonrelonncelonObjelonct.UnknownUnionFielonld(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  privatelon delonf convelonrtFormat(format: onboardingthrift.RichTelonxtFormat): RichTelonxtFormat =
    format match {
      caselon onboardingthrift.RichTelonxtFormat.Plain => Plain
      caselon onboardingthrift.RichTelonxtFormat.Strong => Strong
      caselon onboardingthrift.RichTelonxtFormat.elonnumUnknownRichTelonxtFormat(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  // Speloncific to Melonssagelon prompt
  delonf convelonrtSocialContelonxt(socialContelonxt: onboardingthrift.RichTelonxt): SocialContelonxt =
    GelonnelonralContelonxt(
      contelonxtTypelon = FollowGelonnelonralContelonxtTypelon,
      telonxt = socialContelonxt.telonxt,
      url = Nonelon,
      contelonxtImagelonUrls = Nonelon,
      landingUrl = Nonelon)

  delonf convelonrtUselonrFacelonPilelon(
    uselonrFacelonpilelon: onboardingthrift.PromptUselonrFacelonpilelon
  ): UselonrFacelonpilelon =
    UselonrFacelonpilelon(
      uselonrIds = uselonrFacelonpilelon.uselonrIds.toList,
      felonaturelondUselonrIds = uselonrFacelonpilelon.felonaturelondUselonrIds.toList,
      action = uselonrFacelonpilelon.action.map(convelonrtButtonAction),
      actionTypelon = uselonrFacelonpilelon.actionTypelon.map(convelonrtUselonrFacelonPilelonActionTypelon),
      displaysFelonaturingTelonxt = uselonrFacelonpilelon.displaysFelonaturingTelonxt,
      displayTypelon = Somelon(LargelonUselonrFacelonpilelonDisplayTypelon)
    )

  privatelon delonf convelonrtUselonrFacelonPilelonActionTypelon(
    actionTypelon: onboardingthrift.FacelonpilelonActionTypelon
  ): MelonssagelonActionTypelon =
    actionTypelon match {
      caselon onboardingthrift.FacelonpilelonActionTypelon.FollowAll => FollowAllMelonssagelonActionTypelon
      caselon onboardingthrift.FacelonpilelonActionTypelon.elonnumUnknownFacelonpilelonActionTypelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  // Speloncific to Covelonr

  delonf convelonrtHalfCovelonrDisplayTypelon(
    displayTypelon: onboardingthrift.HalfCovelonrDisplayTypelon
  ): HalfCovelonrDisplayTypelon =
    displayTypelon match {
      caselon onboardingthrift.HalfCovelonrDisplayTypelon.Covelonr => CovelonrHalfCovelonrDisplayTypelon
      caselon onboardingthrift.HalfCovelonrDisplayTypelon.CelonntelonrCovelonr =>
        CelonntelonrCovelonrHalfCovelonrDisplayTypelon
      caselon onboardingthrift.HalfCovelonrDisplayTypelon.elonnumUnknownHalfCovelonrDisplayTypelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  delonf convelonrtDismissInfo(dismissInfo: onboardingthrift.DismissInfo): DismissInfo =
    DismissInfo(dismissInfo.callbacks.map(_.map(convelonrtCallback)))

  delonf convelonrtCovelonrCta(
    buttonAction: onboardingthrift.ButtonAction
  ): CovelonrCta =
    CovelonrCta(
      buttonAction.telonxt,
      ctaBelonhavior = convelonrtCovelonrCtaBelonhavior(buttonAction.buttonBelonhavior),
      callbacks = buttonAction.callbacks.map(_.map(convelonrtCallback).toList),
      clielonntelonvelonntInfo = Somelon(convelonrtClielonntelonvelonntInfo(buttonAction.clielonntelonvelonntInfo)),
      icon = buttonAction.icon.map(covelonrtHorizonIcon),
      buttonStylelon = buttonAction.buttonStylelon.map(covelonrtButtonStylelon)
    )

  privatelon delonf convelonrtCovelonrCtaBelonhavior(
    belonhavior: onboardingthrift.ButtonBelonhavior
  ): CovelonrCtaBelonhavior =
    belonhavior match {
      caselon onboardingthrift.ButtonBelonhavior.Navigatelon(navigatelon) =>
        CovelonrBelonhaviorNavigatelon(convelonrtUrl(navigatelon.url))
      caselon onboardingthrift.ButtonBelonhavior.Dismiss(dismiss) =>
        CovelonrBelonhaviorDismiss(dismiss.felonelondbackMelonssagelon.map(convelonrtRichTelonxt))
      caselon onboardingthrift.ButtonBelonhavior.UnknownUnionFielonld(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  privatelon delonf covelonrtButtonStylelon(bStylelon: onboardingthrift.CtaButtonStylelon): ButtonStylelon =
    bStylelon match {
      caselon onboardingthrift.CtaButtonStylelon.Delonfault => Delonfault
      caselon onboardingthrift.CtaButtonStylelon.Primary => Primary
      caselon onboardingthrift.CtaButtonStylelon.Seloncondary => Seloncondary
      caselon onboardingthrift.CtaButtonStylelon.Telonxt => Telonxt
      caselon onboardingthrift.CtaButtonStylelon.Delonstructivelon => Delonstructivelon
      caselon onboardingthrift.CtaButtonStylelon.Nelonutral => Nelonutral
      caselon onboardingthrift.CtaButtonStylelon.DelonstructivelonSeloncondary => DelonstructivelonSeloncondary
      caselon onboardingthrift.CtaButtonStylelon.DelonstructivelonTelonxt => DelonstructivelonTelonxt
      caselon onboardingthrift.CtaButtonStylelon.elonnumUnknownCtaButtonStylelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }

  privatelon delonf covelonrtHorizonIcon(icon: onboardingthrift.HorizonIcon): HorizonIcon =
    icon match {
      caselon onboardingthrift.HorizonIcon.Bookmark => Bookmark
      caselon onboardingthrift.HorizonIcon.Momelonnt => Momelonnt
      caselon onboardingthrift.HorizonIcon.Delonbug => Delonbug
      caselon onboardingthrift.HorizonIcon.elonrror => elonrror
      caselon onboardingthrift.HorizonIcon.Follow => Follow
      caselon onboardingthrift.HorizonIcon.Unfollow => Unfollow
      caselon onboardingthrift.HorizonIcon.Smilelon => Smilelon
      caselon onboardingthrift.HorizonIcon.Frown => Frown
      caselon onboardingthrift.HorizonIcon.Helonlp => Helonlp
      caselon onboardingthrift.HorizonIcon.Link => Link
      caselon onboardingthrift.HorizonIcon.Melonssagelon => Melonssagelon
      caselon onboardingthrift.HorizonIcon.No => No
      caselon onboardingthrift.HorizonIcon.Outgoing => Outgoing
      caselon onboardingthrift.HorizonIcon.Pin => Pin
      caselon onboardingthrift.HorizonIcon.Relontwelonelont => Relontwelonelont
      caselon onboardingthrift.HorizonIcon.Spelonakelonr => Spelonakelonr
      caselon onboardingthrift.HorizonIcon.Trashcan => Trashcan
      caselon onboardingthrift.HorizonIcon.Felonelondback => Felonelondback
      caselon onboardingthrift.HorizonIcon.FelonelondbackCloselon => FelonelondbackCloselon
      caselon onboardingthrift.HorizonIcon.elonyelonOff => elonyelonOff
      caselon onboardingthrift.HorizonIcon.Modelonration => Modelonration
      caselon onboardingthrift.HorizonIcon.Topic => Topic
      caselon onboardingthrift.HorizonIcon.TopicCloselon => TopicCloselon
      caselon onboardingthrift.HorizonIcon.Flag => Flag
      caselon onboardingthrift.HorizonIcon.TopicFillelond => TopicFillelond
      caselon onboardingthrift.HorizonIcon.NotificationsFollow => NotificationsFollow
      caselon onboardingthrift.HorizonIcon.Pelonrson => Pelonrson
      caselon onboardingthrift.HorizonIcon.Logo => Logo
      caselon onboardingthrift.HorizonIcon.elonnumUnknownHorizonIcon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")

    }

  delonf convelonrtUrl(url: onboardingthrift.Url): Url = {
    val urlTypelon = url.urlTypelon match {
      caselon onboardingthrift.UrlTypelon.elonxtelonrnalUrl => elonxtelonrnalUrl
      caselon onboardingthrift.UrlTypelon.DelonelonpLink => DelonelonpLink
      caselon onboardingthrift.UrlTypelon.Urtelonndpoint => Urtelonndpoint
      caselon onboardingthrift.UrlTypelon.elonnumUnknownUrlTypelon(valuelon) =>
        throw nelonw UnsupportelondOpelonrationelonxcelonption(s"Unknown product: $valuelon")
    }
    Url(urlTypelon, url.url, url.urtelonndpointOptions.map(convelonrtUrtelonndpointOptions))
  }

  privatelon delonf convelonrtUrtelonndpointOptions(
    urtelonndpointOptions: onboardingthrift.UrtelonndpointOptions
  ): UrtelonndpointOptions =
    UrtelonndpointOptions(
      relonquelonstParams = urtelonndpointOptions.relonquelonstParams.map(_.toMap),
      titlelon = urtelonndpointOptions.titlelon,
      cachelonId = urtelonndpointOptions.cachelonId,
      subtitlelon = urtelonndpointOptions.subtitlelon
    )

}

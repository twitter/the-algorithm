package com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne

 mport com.tw ter.onboard ng. nject ons.{thr ftscala => onboard ngthr ft}
 mport com.tw ter.product_m xer.component_l brary.decorator.urt.bu lder.flex ble_ nject on_p pel ne.Onboard ng nject onConvers ons.convert mageVar ant
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Class c
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.BlackRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.ClearRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepGrayRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepGreenRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepOrangeRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepPurpleRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepRedRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.DeepYellowRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedGrayRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedGreenRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedOrangeRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedPurpleRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedRedRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.FadedYellowRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Fa ntBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Fa ntGrayRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtGrayRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtGreenRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtOrangeRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtPurpleRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtRedRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.L ghtYellowRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umGrayRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umGreenRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umOrangeRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umPurpleRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umRedRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color. d umYellowRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.RosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.TextBlackRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.TextBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Tw terBlueRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color.Wh eRosettaColor
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.CallToAct onT leContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.StandardT leContent
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. em.t le.T le em
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata.Badge
 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.t  l ne_module.Module ader

object T lesCarouselConvers ons {
  // T les Carousel Convers ons
  def convertT le(t le: onboard ngthr ft.T le,  d: Long): T le em = {
    t le.content match {
      case standard: onboard ngthr ft.T leContent.Standard =>
        T le em(
           d =  d,
          sort ndex = None,
          cl entEvent nfo =
            So (Onboard ng nject onConvers ons.convertCl entEvent nfo(t le.cl entEvent nfo)),
          feedbackAct on nfo = None,
          t le = standard.standard.t le,
          support ngText = "",
          url = t le.url.map(Onboard ng nject onConvers ons.convertUrl),
           mage = t le. mage.map( mg => convert mageVar ant( mg. mage)),
          content = StandardT leContent(
            t le = standard.standard.t le,
            support ngText = "",
            badge = standard.standard.badge.map(convertT leBadge)
          )
        )
      case cta: onboard ngthr ft.T leContent.CallToAct on =>
        T le em(
           d =  d,
          sort ndex = None,
          cl entEvent nfo =
            So (Onboard ng nject onConvers ons.convertCl entEvent nfo(t le.cl entEvent nfo)),
          feedbackAct on nfo = None,
          t le = cta.callToAct on.text,
          support ngText = "",
          url = t le.url.map(Onboard ng nject onConvers ons.convertUrl),
           mage = None,
          content = CallToAct onT leContent(
            text = cta.callToAct on.text,
            r chText = None,
            ctaButton = None
          )
        )
      case _ =>
        throw new UnsupportedT leCarouselConvers onExcept on(s"T le Content: ${t le.content}")
    }
  }

  pr vate def convertT leBadge(badge: onboard ngthr ft.Badge): Badge = {
    Badge(
      text = badge.text,
      textColorNa  = badge.textColor.map(convertRosettaColor),
      backgroundColorNa  = badge.backgroundColor.map(convertRosettaColor))
  }

  def convertModule ader( ader: onboard ngthr ft.T lesCarousel ader): Module ader = {
    Module ader( ader. ader, None, None, None, None, Class c)
  }

  pr vate def convertRosettaColor(color: onboard ngthr ft.RosettaColor): RosettaColor =
    color match {
      case onboard ngthr ft.RosettaColor.Wh e => Wh eRosettaColor
      case onboard ngthr ft.RosettaColor.Black => BlackRosettaColor
      case onboard ngthr ft.RosettaColor.Clear => ClearRosettaColor
      case onboard ngthr ft.RosettaColor.TextBlack => TextBlackRosettaColor
      case onboard ngthr ft.RosettaColor.TextBlue => TextBlueRosettaColor

      case onboard ngthr ft.RosettaColor.DeepGray => DeepGrayRosettaColor
      case onboard ngthr ft.RosettaColor. d umGray =>  d umGrayRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtGray => L ghtGrayRosettaColor
      case onboard ngthr ft.RosettaColor.FadedGray => FadedGrayRosettaColor
      case onboard ngthr ft.RosettaColor.Fa ntGray => Fa ntGrayRosettaColor

      case onboard ngthr ft.RosettaColor.DeepOrange => DeepOrangeRosettaColor
      case onboard ngthr ft.RosettaColor. d umOrange =>  d umOrangeRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtOrange => L ghtOrangeRosettaColor
      case onboard ngthr ft.RosettaColor.FadedOrange => FadedOrangeRosettaColor

      case onboard ngthr ft.RosettaColor.DeepYellow => DeepYellowRosettaColor
      case onboard ngthr ft.RosettaColor. d umYellow =>  d umYellowRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtYellow => L ghtYellowRosettaColor
      case onboard ngthr ft.RosettaColor.FadedYellow => FadedYellowRosettaColor

      case onboard ngthr ft.RosettaColor.DeepGreen => DeepGreenRosettaColor
      case onboard ngthr ft.RosettaColor. d umGreen =>  d umGreenRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtGreen => L ghtGreenRosettaColor
      case onboard ngthr ft.RosettaColor.FadedGreen => FadedGreenRosettaColor

      case onboard ngthr ft.RosettaColor.DeepBlue => DeepBlueRosettaColor
      case onboard ngthr ft.RosettaColor.Tw terBlue => Tw terBlueRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtBlue => L ghtBlueRosettaColor
      case onboard ngthr ft.RosettaColor.FadedBlue => FadedBlueRosettaColor
      case onboard ngthr ft.RosettaColor.Fa ntBlue => Fa ntBlueRosettaColor

      case onboard ngthr ft.RosettaColor.DeepPurple => DeepPurpleRosettaColor
      case onboard ngthr ft.RosettaColor. d umPurple =>  d umPurpleRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtPurple => L ghtPurpleRosettaColor
      case onboard ngthr ft.RosettaColor.FadedPurple => FadedPurpleRosettaColor

      case onboard ngthr ft.RosettaColor.DeepRed => DeepRedRosettaColor
      case onboard ngthr ft.RosettaColor. d umRed =>  d umRedRosettaColor
      case onboard ngthr ft.RosettaColor.L ghtRed => L ghtRedRosettaColor
      case onboard ngthr ft.RosettaColor.FadedRed => FadedRedRosettaColor
      case onboard ngthr ft.RosettaColor.EnumUnknownRosettaColor( ) =>
        throw new UnknownThr ftEnumExcept on("RosettaColor")
    }
  class UnknownThr ftEnumExcept on(enumNa : Str ng)
      extends Except on(s"Unknown Thr ft Enum Found: ${enumNa }")

  class UnsupportedT leCarouselConvers onExcept on(UnsupportedT leType: Str ng)
      extends Except on(s"Unsupported T le Type Found: ${UnsupportedT leType}")
}

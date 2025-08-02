package com.twitter.product_mitwitterer.component_library.decorator.urt.builder.fletwitterible_injection_pipeline

import com.twitter.onboarding.injections.{thriftscala => onboardingthrift}
import com.twitter.product_mitwitterer.component_library.decorator.urt.builder.fletwitterible_injection_pipeline.OnboardingInjectionConversions.convertImageVariant
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.Classic
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.BlackRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.ClearRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepGrayRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepGreenRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepOrangeRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepPurpleRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepRedRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.DeepYellowRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedGrayRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedGreenRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedOrangeRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedPurpleRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedRedRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FadedYellowRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FaintBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.FaintGrayRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightGrayRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightGreenRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightOrangeRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightPurpleRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightRedRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.LightYellowRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumGrayRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumGreenRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumOrangeRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumPurpleRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumRedRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.MediumYellowRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.RosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.TetwittertBlackRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.TetwittertBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.TwitterBlueRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color.WhiteRosettaColor
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.CallToActionTileContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.StandardTileContent
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.item.tile.TileItem
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.metadata.Badge
import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.timeline_module.ModuleHeader

object TilesCarouselConversions {
  // Tiles Carousel Conversions
  def convertTile(tile: onboardingthrift.Tile, id: Long): TileItem = {
    tile.content match {
      case standard: onboardingthrift.TileContent.Standard =>
        TileItem(
          id = id,
          sortIndetwitter = None,
          clientEventInfo =
            Some(OnboardingInjectionConversions.convertClientEventInfo(tile.clientEventInfo)),
          feedbackActionInfo = None,
          title = standard.standard.title,
          supportingTetwittert = "",
          url = tile.url.map(OnboardingInjectionConversions.convertUrl),
          image = tile.image.map(img => convertImageVariant(img.image)),
          content = StandardTileContent(
            title = standard.standard.title,
            supportingTetwittert = "",
            badge = standard.standard.badge.map(convertTileBadge)
          )
        )
      case cta: onboardingthrift.TileContent.CallToAction =>
        TileItem(
          id = id,
          sortIndetwitter = None,
          clientEventInfo =
            Some(OnboardingInjectionConversions.convertClientEventInfo(tile.clientEventInfo)),
          feedbackActionInfo = None,
          title = cta.callToAction.tetwittert,
          supportingTetwittert = "",
          url = tile.url.map(OnboardingInjectionConversions.convertUrl),
          image = None,
          content = CallToActionTileContent(
            tetwittert = cta.callToAction.tetwittert,
            richTetwittert = None,
            ctaButton = None
          )
        )
      case _ =>
        throw new UnsupportedTileCarouselConversionEtwitterception(s"Tile Content: ${tile.content}")
    }
  }

  private def convertTileBadge(badge: onboardingthrift.Badge): Badge = {
    Badge(
      tetwittert = badge.tetwittert,
      tetwittertColorName = badge.tetwittertColor.map(convertRosettaColor),
      backgroundColorName = badge.backgroundColor.map(convertRosettaColor))
  }

  def convertModuleHeader(header: onboardingthrift.TilesCarouselHeader): ModuleHeader = {
    ModuleHeader(header.header, None, None, None, None, Classic)
  }

  private def convertRosettaColor(color: onboardingthrift.RosettaColor): RosettaColor =
    color match {
      case onboardingthrift.RosettaColor.White => WhiteRosettaColor
      case onboardingthrift.RosettaColor.Black => BlackRosettaColor
      case onboardingthrift.RosettaColor.Clear => ClearRosettaColor
      case onboardingthrift.RosettaColor.TetwittertBlack => TetwittertBlackRosettaColor
      case onboardingthrift.RosettaColor.TetwittertBlue => TetwittertBlueRosettaColor

      case onboardingthrift.RosettaColor.DeepGray => DeepGrayRosettaColor
      case onboardingthrift.RosettaColor.MediumGray => MediumGrayRosettaColor
      case onboardingthrift.RosettaColor.LightGray => LightGrayRosettaColor
      case onboardingthrift.RosettaColor.FadedGray => FadedGrayRosettaColor
      case onboardingthrift.RosettaColor.FaintGray => FaintGrayRosettaColor

      case onboardingthrift.RosettaColor.DeepOrange => DeepOrangeRosettaColor
      case onboardingthrift.RosettaColor.MediumOrange => MediumOrangeRosettaColor
      case onboardingthrift.RosettaColor.LightOrange => LightOrangeRosettaColor
      case onboardingthrift.RosettaColor.FadedOrange => FadedOrangeRosettaColor

      case onboardingthrift.RosettaColor.DeepYellow => DeepYellowRosettaColor
      case onboardingthrift.RosettaColor.MediumYellow => MediumYellowRosettaColor
      case onboardingthrift.RosettaColor.LightYellow => LightYellowRosettaColor
      case onboardingthrift.RosettaColor.FadedYellow => FadedYellowRosettaColor

      case onboardingthrift.RosettaColor.DeepGreen => DeepGreenRosettaColor
      case onboardingthrift.RosettaColor.MediumGreen => MediumGreenRosettaColor
      case onboardingthrift.RosettaColor.LightGreen => LightGreenRosettaColor
      case onboardingthrift.RosettaColor.FadedGreen => FadedGreenRosettaColor

      case onboardingthrift.RosettaColor.DeepBlue => DeepBlueRosettaColor
      case onboardingthrift.RosettaColor.TwitterBlue => TwitterBlueRosettaColor
      case onboardingthrift.RosettaColor.LightBlue => LightBlueRosettaColor
      case onboardingthrift.RosettaColor.FadedBlue => FadedBlueRosettaColor
      case onboardingthrift.RosettaColor.FaintBlue => FaintBlueRosettaColor

      case onboardingthrift.RosettaColor.DeepPurple => DeepPurpleRosettaColor
      case onboardingthrift.RosettaColor.MediumPurple => MediumPurpleRosettaColor
      case onboardingthrift.RosettaColor.LightPurple => LightPurpleRosettaColor
      case onboardingthrift.RosettaColor.FadedPurple => FadedPurpleRosettaColor

      case onboardingthrift.RosettaColor.DeepRed => DeepRedRosettaColor
      case onboardingthrift.RosettaColor.MediumRed => MediumRedRosettaColor
      case onboardingthrift.RosettaColor.LightRed => LightRedRosettaColor
      case onboardingthrift.RosettaColor.FadedRed => FadedRedRosettaColor
      case onboardingthrift.RosettaColor.EnumUnknownRosettaColor(i) =>
        throw new UnknownThriftEnumEtwitterception("RosettaColor")
    }
  class UnknownThriftEnumEtwitterception(enumName: String)
      etwittertends Etwitterception(s"Unknown Thrift Enum Found: ${enumName}")

  class UnsupportedTileCarouselConversionEtwitterception(UnsupportedTileType: String)
      etwittertends Etwitterception(s"Unsupported Tile Type Found: ${UnsupportedTileType}")
}

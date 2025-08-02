package com.twitter.product_mitwitterer.core.functional_component.marshaller.response.urt.color

import com.twitter.product_mitwitterer.core.model.marshalling.response.urt.color._
import com.twitter.timelines.render.{thriftscala => urt}
import javatwitter.inject.Inject
import javatwitter.inject.Singleton

@Singleton
class RosettaColorMarshaller @Inject() () {

  def apply(rosettaColor: RosettaColor): urt.RosettaColor = rosettaColor match {
    case WhiteRosettaColor => urt.RosettaColor.White
    case BlackRosettaColor => urt.RosettaColor.Black
    case ClearRosettaColor => urt.RosettaColor.Clear
    case TetwittertBlackRosettaColor => urt.RosettaColor.TetwittertBlack
    case TetwittertBlueRosettaColor => urt.RosettaColor.TetwittertBlue
    case DeepGrayRosettaColor => urt.RosettaColor.DeepGray
    case MediumGrayRosettaColor => urt.RosettaColor.MediumGray
    case LightGrayRosettaColor => urt.RosettaColor.LightGray
    case FadedGrayRosettaColor => urt.RosettaColor.FadedGray
    case FaintGrayRosettaColor => urt.RosettaColor.FaintGray
    case DeepOrangeRosettaColor => urt.RosettaColor.DeepOrange
    case MediumOrangeRosettaColor => urt.RosettaColor.MediumOrange
    case LightOrangeRosettaColor => urt.RosettaColor.LightOrange
    case FadedOrangeRosettaColor => urt.RosettaColor.FadedOrange
    case DeepYellowRosettaColor => urt.RosettaColor.DeepYellow
    case MediumYellowRosettaColor => urt.RosettaColor.MediumYellow
    case LightYellowRosettaColor => urt.RosettaColor.LightYellow
    case FadedYellowRosettaColor => urt.RosettaColor.FadedYellow
    case DeepGreenRosettaColor => urt.RosettaColor.DeepGreen
    case MediumGreenRosettaColor => urt.RosettaColor.MediumGreen
    case LightGreenRosettaColor => urt.RosettaColor.LightGreen
    case FadedGreenRosettaColor => urt.RosettaColor.FadedGreen
    case DeepBlueRosettaColor => urt.RosettaColor.DeepBlue
    case TwitterBlueRosettaColor => urt.RosettaColor.TwitterBlue
    case LightBlueRosettaColor => urt.RosettaColor.LightBlue
    case FadedBlueRosettaColor => urt.RosettaColor.FadedBlue
    case FaintBlueRosettaColor => urt.RosettaColor.FaintBlue
    case DeepPurpleRosettaColor => urt.RosettaColor.DeepPurple
    case MediumPurpleRosettaColor => urt.RosettaColor.MediumPurple
    case LightPurpleRosettaColor => urt.RosettaColor.LightPurple
    case FadedPurpleRosettaColor => urt.RosettaColor.FadedPurple
    case DeepRedRosettaColor => urt.RosettaColor.DeepRed
    case MediumRedRosettaColor => urt.RosettaColor.MediumRed
    case LightRedRosettaColor => urt.RosettaColor.LightRed
    case FadedRedRosettaColor => urt.RosettaColor.FadedRed
  }
}

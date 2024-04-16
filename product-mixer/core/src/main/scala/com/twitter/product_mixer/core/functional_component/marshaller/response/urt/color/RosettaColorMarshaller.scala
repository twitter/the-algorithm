package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt.color

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt.color._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class RosettaColorMarshaller @ nject() () {

  def apply(rosettaColor: RosettaColor): urt.RosettaColor = rosettaColor match {
    case Wh eRosettaColor => urt.RosettaColor.Wh e
    case BlackRosettaColor => urt.RosettaColor.Black
    case ClearRosettaColor => urt.RosettaColor.Clear
    case TextBlackRosettaColor => urt.RosettaColor.TextBlack
    case TextBlueRosettaColor => urt.RosettaColor.TextBlue
    case DeepGrayRosettaColor => urt.RosettaColor.DeepGray
    case  d umGrayRosettaColor => urt.RosettaColor. d umGray
    case L ghtGrayRosettaColor => urt.RosettaColor.L ghtGray
    case FadedGrayRosettaColor => urt.RosettaColor.FadedGray
    case Fa ntGrayRosettaColor => urt.RosettaColor.Fa ntGray
    case DeepOrangeRosettaColor => urt.RosettaColor.DeepOrange
    case  d umOrangeRosettaColor => urt.RosettaColor. d umOrange
    case L ghtOrangeRosettaColor => urt.RosettaColor.L ghtOrange
    case FadedOrangeRosettaColor => urt.RosettaColor.FadedOrange
    case DeepYellowRosettaColor => urt.RosettaColor.DeepYellow
    case  d umYellowRosettaColor => urt.RosettaColor. d umYellow
    case L ghtYellowRosettaColor => urt.RosettaColor.L ghtYellow
    case FadedYellowRosettaColor => urt.RosettaColor.FadedYellow
    case DeepGreenRosettaColor => urt.RosettaColor.DeepGreen
    case  d umGreenRosettaColor => urt.RosettaColor. d umGreen
    case L ghtGreenRosettaColor => urt.RosettaColor.L ghtGreen
    case FadedGreenRosettaColor => urt.RosettaColor.FadedGreen
    case DeepBlueRosettaColor => urt.RosettaColor.DeepBlue
    case Tw terBlueRosettaColor => urt.RosettaColor.Tw terBlue
    case L ghtBlueRosettaColor => urt.RosettaColor.L ghtBlue
    case FadedBlueRosettaColor => urt.RosettaColor.FadedBlue
    case Fa ntBlueRosettaColor => urt.RosettaColor.Fa ntBlue
    case DeepPurpleRosettaColor => urt.RosettaColor.DeepPurple
    case  d umPurpleRosettaColor => urt.RosettaColor. d umPurple
    case L ghtPurpleRosettaColor => urt.RosettaColor.L ghtPurple
    case FadedPurpleRosettaColor => urt.RosettaColor.FadedPurple
    case DeepRedRosettaColor => urt.RosettaColor.DeepRed
    case  d umRedRosettaColor => urt.RosettaColor. d umRed
    case L ghtRedRosettaColor => urt.RosettaColor.L ghtRed
    case FadedRedRosettaColor => urt.RosettaColor.FadedRed
  }
}

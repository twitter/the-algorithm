package com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata

sealed tra  Soc alContext

tra  HasSoc alContext {
  def soc alContext: Opt on[Soc alContext]
}

sealed tra  GeneralContextType
case object L keGeneralContextType extends GeneralContextType
case object FollowGeneralContextType extends GeneralContextType
case object Mo ntGeneralContextType extends GeneralContextType
case object ReplyGeneralContextType extends GeneralContextType
case object Conversat onGeneralContextType extends GeneralContextType
case object P nGeneralContextType extends GeneralContextType
case object TextOnlyGeneralContextType extends GeneralContextType
case object FaceP leGeneralContextType extends GeneralContextType
case object  gaPhoneGeneralContextType extends GeneralContextType
case object B rdGeneralContextType extends GeneralContextType
case object FeedbackGeneralContextType extends GeneralContextType
case object Top cGeneralContextType extends GeneralContextType
case object L stGeneralContextType extends GeneralContextType
case object Ret etGeneralContextType extends GeneralContextType
case object Locat onGeneralContextType extends GeneralContextType
case object Commun yGeneralContextType extends GeneralContextType
case object NewUserGeneralContextType extends GeneralContextType
case object SmartblockExp rat onGeneralContextType extends GeneralContextType
case object Trend ngGeneralContextType extends GeneralContextType
case object SparkleGeneralContextType extends GeneralContextType
case object SpacesGeneralContextType extends GeneralContextType
case object ReplyP nGeneralContextType extends GeneralContextType

case class GeneralContext(
  contextType: GeneralContextType,
  text: Str ng,
  url: Opt on[Str ng],
  context mageUrls: Opt on[L st[Str ng]],
  land ngUrl: Opt on[Url])
    extends Soc alContext

sealed tra  Top cContextFunct onal yType
case object Bas cTop cContextFunct onal yType extends Top cContextFunct onal yType
case object Recom ndat onTop cContextFunct onal yType extends Top cContextFunct onal yType
case object RecW hEducat onTop cContextFunct onal yType extends Top cContextFunct onal yType

case class Top cContext(
  top c d: Str ng,
  funct onal yType: Opt on[Top cContextFunct onal yType])
    extends Soc alContext

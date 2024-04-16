package com.tw ter.product_m xer.core.funct onal_component.marshaller.response.urt. tadata

 mport com.tw ter.product_m xer.core.model.marshall ng.response.urt. tadata._
 mport com.tw ter.t  l nes.render.{thr ftscala => urt}
 mport javax. nject. nject
 mport javax. nject.S ngleton

@S ngleton
class GeneralContextTypeMarshaller @ nject() () {

  def apply(generalContextType: GeneralContextType): urt.ContextType = generalContextType match {
    case L keGeneralContextType => urt.ContextType.L ke
    case FollowGeneralContextType => urt.ContextType.Follow
    case Mo ntGeneralContextType => urt.ContextType.Mo nt
    case ReplyGeneralContextType => urt.ContextType.Reply
    case Conversat onGeneralContextType => urt.ContextType.Conversat on
    case P nGeneralContextType => urt.ContextType.P n
    case TextOnlyGeneralContextType => urt.ContextType.TextOnly
    case FaceP leGeneralContextType => urt.ContextType.Facep le
    case  gaPhoneGeneralContextType => urt.ContextType. gaphone
    case B rdGeneralContextType => urt.ContextType.B rd
    case FeedbackGeneralContextType => urt.ContextType.Feedback
    case Top cGeneralContextType => urt.ContextType.Top c
    case L stGeneralContextType => urt.ContextType.L st
    case Ret etGeneralContextType => urt.ContextType.Ret et
    case Locat onGeneralContextType => urt.ContextType.Locat on
    case Commun yGeneralContextType => urt.ContextType.Commun y
    case NewUserGeneralContextType => urt.ContextType.NewUser
    case SmartblockExp rat onGeneralContextType => urt.ContextType.SmartBlockExp rat on
    case Trend ngGeneralContextType => urt.ContextType.Trend ng
    case SparkleGeneralContextType => urt.ContextType.Sparkle
    case SpacesGeneralContextType => urt.ContextType.Spaces
    case ReplyP nGeneralContextType => urt.ContextType.ReplyP n
  }
}

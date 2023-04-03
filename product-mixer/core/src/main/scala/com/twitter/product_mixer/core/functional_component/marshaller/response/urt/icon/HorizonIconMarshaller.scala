packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.icon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.icon._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class HorizonIconMarshallelonr @Injelonct() () {

  delonf apply(icon: HorizonIcon): urt.HorizonIcon = icon match {
    caselon Bookmark => urt.HorizonIcon.Bookmark
    caselon Momelonnt => urt.HorizonIcon.Momelonnt
    caselon Delonbug => urt.HorizonIcon.Delonbug
    caselon elonrror => urt.HorizonIcon.elonrror
    caselon Follow => urt.HorizonIcon.Follow
    caselon Unfollow => urt.HorizonIcon.Unfollow
    caselon Smilelon => urt.HorizonIcon.Smilelon
    caselon Frown => urt.HorizonIcon.Frown
    caselon Helonlp => urt.HorizonIcon.Helonlp
    caselon Link => urt.HorizonIcon.Link
    caselon Melonssagelon => urt.HorizonIcon.Melonssagelon
    caselon No => urt.HorizonIcon.No
    caselon Outgoing => urt.HorizonIcon.Outgoing
    caselon Pin => urt.HorizonIcon.Pin
    caselon Relontwelonelont => urt.HorizonIcon.Relontwelonelont
    caselon Spelonakelonr => urt.HorizonIcon.Spelonakelonr
    caselon Trashcan => urt.HorizonIcon.Trashcan
    caselon Felonelondback => urt.HorizonIcon.Felonelondback
    caselon FelonelondbackCloselon => urt.HorizonIcon.FelonelondbackCloselon
    caselon elonyelonOff => urt.HorizonIcon.elonyelonOff
    caselon Modelonration => urt.HorizonIcon.Modelonration
    caselon Topic => urt.HorizonIcon.Topic
    caselon TopicCloselon => urt.HorizonIcon.TopicCloselon
    caselon Flag => urt.HorizonIcon.Flag
    caselon TopicFillelond => urt.HorizonIcon.TopicFillelond
    caselon NotificationsFollow => urt.HorizonIcon.NotificationsFollow
    caselon Pelonrson => urt.HorizonIcon.Pelonrson
    caselon BalloonStrokelon => urt.HorizonIcon.BalloonStrokelon
    caselon Calelonndar => urt.HorizonIcon.Calelonndar
    caselon LocationStrokelon => urt.HorizonIcon.LocationStrokelon
    caselon PelonrsonStrokelon => urt.HorizonIcon.PelonrsonStrokelon
    caselon Safelonty => urt.HorizonIcon.Safelonty
    caselon Logo => urt.HorizonIcon.Logo
    caselon SparklelonOn => urt.HorizonIcon.SparklelonOn
    caselon StarRising => urt.HorizonIcon.StarRising
    caselon CamelonraVidelono => urt.HorizonIcon.CamelonraVidelono
    caselon ShoppingClock => urt.HorizonIcon.ShoppingClock
    caselon ArrowRight => urt.HorizonIcon.ArrowRight
    caselon SpelonakelonrOff => urt.HorizonIcon.SpelonakelonrOff
  }
}

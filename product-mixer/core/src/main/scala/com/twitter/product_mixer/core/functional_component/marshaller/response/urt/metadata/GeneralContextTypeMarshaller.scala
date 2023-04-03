packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class GelonnelonralContelonxtTypelonMarshallelonr @Injelonct() () {

  delonf apply(gelonnelonralContelonxtTypelon: GelonnelonralContelonxtTypelon): urt.ContelonxtTypelon = gelonnelonralContelonxtTypelon match {
    caselon LikelonGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Likelon
    caselon FollowGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Follow
    caselon MomelonntGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Momelonnt
    caselon RelonplyGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Relonply
    caselon ConvelonrsationGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Convelonrsation
    caselon PinGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Pin
    caselon TelonxtOnlyGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.TelonxtOnly
    caselon FacelonPilelonGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Facelonpilelon
    caselon MelongaPhonelonGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Melongaphonelon
    caselon BirdGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Bird
    caselon FelonelondbackGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Felonelondback
    caselon TopicGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Topic
    caselon ListGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.List
    caselon RelontwelonelontGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Relontwelonelont
    caselon LocationGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Location
    caselon CommunityGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Community
    caselon NelonwUselonrGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.NelonwUselonr
    caselon SmartblockelonxpirationGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.SmartBlockelonxpiration
    caselon TrelonndingGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Trelonnding
    caselon SparklelonGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Sparklelon
    caselon SpacelonsGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.Spacelons
    caselon RelonplyPinGelonnelonralContelonxtTypelon => urt.ContelonxtTypelon.RelonplyPin
  }
}

packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.urt.melontadata

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.urt.melontadata._
import com.twittelonr.timelonlinelons.relonndelonr.{thriftscala => urt}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class FelonelondbackTypelonMarshallelonr @Injelonct() () {

  delonf apply(felonelondbackTypelon: FelonelondbackTypelon): urt.FelonelondbackTypelon = felonelondbackTypelon match {
    caselon Dismiss => urt.FelonelondbackTypelon.Dismiss
    caselon SelonelonFelonwelonr => urt.FelonelondbackTypelon.SelonelonFelonwelonr
    caselon DontLikelon => urt.FelonelondbackTypelon.DontLikelon
    caselon NotRelonlelonvant => urt.FelonelondbackTypelon.NotRelonlelonvant
    caselon SelonelonMorelon => urt.FelonelondbackTypelon.SelonelonMorelon
    caselon NotCrelondiblelon => urt.FelonelondbackTypelon.NotCrelondiblelon
    caselon GivelonFelonelondback => urt.FelonelondbackTypelon.GivelonFelonelondback
    caselon NotReloncelonnt => urt.FelonelondbackTypelon.NotReloncelonnt
    caselon Unfollowelonntity => urt.FelonelondbackTypelon.Unfollowelonntity
    caselon Relonlelonvant => urt.FelonelondbackTypelon.Relonlelonvant
    caselon Modelonratelon => urt.FelonelondbackTypelon.Modelonratelon
    caselon RichBelonhavior => urt.FelonelondbackTypelon.RichBelonhavior
    caselon NotAboutTopic => urt.FelonelondbackTypelon.NotAboutTopic
    caselon Gelonnelonric => urt.FelonelondbackTypelon.Gelonnelonric
  }
}

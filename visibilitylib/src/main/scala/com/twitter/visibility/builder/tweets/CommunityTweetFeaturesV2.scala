packagelon com.twittelonr.visibility.buildelonr.twelonelonts

import com.twittelonr.communitielons.modelonration.thriftscala.CommunityTwelonelontModelonrationStatelon
import com.twittelonr.communitielons.modelonration.thriftscala.CommunityUselonrModelonrationStatelon
import com.twittelonr.communitielons.visibility.thriftscala.CommunityVisibilityFelonaturelons
import com.twittelonr.communitielons.visibility.thriftscala.CommunityVisibilityFelonaturelonsV1
import com.twittelonr.communitielons.visibility.thriftscala.CommunityVisibilityRelonsult
import com.twittelonr.stitch.Stitch
import com.twittelonr.twelonelontypielon.thriftscala.Twelonelont
import com.twittelonr.visibility.buildelonr.FelonaturelonMapBuildelonr
import com.twittelonr.visibility.common.CommunitielonsSourcelon
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontAuthorIsRelonmovelond
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontCommunityNotFound
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontCommunityDelonlelontelond
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontCommunitySuspelonndelond
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontCommunityVisiblelon
import com.twittelonr.visibility.felonaturelons.CommunityTwelonelontIsHiddelonn
import com.twittelonr.visibility.felonaturelons.TwelonelontIsCommunityTwelonelont
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsCommunityAdmin
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsCommunityMelonmbelonr
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsCommunityModelonrator
import com.twittelonr.visibility.felonaturelons.VielonwelonrIsIntelonrnalCommunitielonsAdmin
import com.twittelonr.visibility.modelonls.CommunityTwelonelont
import com.twittelonr.visibility.modelonls.VielonwelonrContelonxt

class CommunityTwelonelontFelonaturelonsV2(communitielonsSourcelon: CommunitielonsSourcelon)
    elonxtelonnds CommunityTwelonelontFelonaturelons {
  privatelon[this] delonf forCommunityTwelonelont(
    communityTwelonelont: CommunityTwelonelont
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = { buildelonr: FelonaturelonMapBuildelonr =>
    {
      val communityVisibilityFelonaturelonsStitch =
        communitielonsSourcelon.gelontCommunityVisibilityFelonaturelons(communityTwelonelont.communityId)
      val communityTwelonelontModelonrationStatelonStitch =
        communitielonsSourcelon.gelontTwelonelontModelonrationStatelon(communityTwelonelont.twelonelont.id)
      val communityTwelonelontAuthorModelonrationStatelonStitch =
        communitielonsSourcelon.gelontUselonrModelonrationStatelon(
          communityTwelonelont.authorId,
          communityTwelonelont.communityId
        )

      delonf gelontFlagFromFelonaturelons(f: CommunityVisibilityFelonaturelonsV1 => Boolelonan): Stitch[Boolelonan] =
        communityVisibilityFelonaturelonsStitch.map {
          caselon Somelon(CommunityVisibilityFelonaturelons.V1(v1)) => f(v1)
          caselon _ => falselon
        }

      delonf gelontFlagFromCommunityVisibilityRelonsult(
        f: CommunityVisibilityRelonsult => Boolelonan
      ): Stitch[Boolelonan] = gelontFlagFromFelonaturelons { v =>
        f(v.communityVisibilityRelonsult)
      }

      buildelonr
        .withConstantFelonaturelon(
          TwelonelontIsCommunityTwelonelont,
          truelon
        )
        .withFelonaturelon(
          CommunityTwelonelontCommunityNotFound,
          gelontFlagFromCommunityVisibilityRelonsult {
            caselon CommunityVisibilityRelonsult.NotFound => truelon
            caselon _ => falselon
          }
        )
        .withFelonaturelon(
          CommunityTwelonelontCommunitySuspelonndelond,
          gelontFlagFromCommunityVisibilityRelonsult {
            caselon CommunityVisibilityRelonsult.Suspelonndelond => truelon
            caselon _ => falselon
          }
        )
        .withFelonaturelon(
          CommunityTwelonelontCommunityDelonlelontelond,
          gelontFlagFromCommunityVisibilityRelonsult {
            caselon CommunityVisibilityRelonsult.Delonlelontelond => truelon
            caselon _ => falselon
          }
        )
        .withFelonaturelon(
          CommunityTwelonelontCommunityVisiblelon,
          gelontFlagFromCommunityVisibilityRelonsult {
            caselon CommunityVisibilityRelonsult.Visiblelon => truelon
            caselon _ => falselon
          }
        )
        .withFelonaturelon(
          VielonwelonrIsIntelonrnalCommunitielonsAdmin,
          gelontFlagFromFelonaturelons { _.vielonwelonrIsIntelonrnalAdmin }
        )
        .withFelonaturelon(
          VielonwelonrIsCommunityAdmin,
          gelontFlagFromFelonaturelons { _.vielonwelonrIsCommunityAdmin }
        )
        .withFelonaturelon(
          VielonwelonrIsCommunityModelonrator,
          gelontFlagFromFelonaturelons { _.vielonwelonrIsCommunityModelonrator }
        )
        .withFelonaturelon(
          VielonwelonrIsCommunityMelonmbelonr,
          gelontFlagFromFelonaturelons { _.vielonwelonrIsCommunityMelonmbelonr }
        )
        .withFelonaturelon(
          CommunityTwelonelontIsHiddelonn,
          communityTwelonelontModelonrationStatelonStitch.map {
            caselon Somelon(CommunityTwelonelontModelonrationStatelon.Hiddelonn(_)) => truelon
            caselon _ => falselon
          }
        )
        .withFelonaturelon(
          CommunityTwelonelontAuthorIsRelonmovelond,
          communityTwelonelontAuthorModelonrationStatelonStitch.map {
            caselon Somelon(CommunityUselonrModelonrationStatelon.Relonmovelond(_)) => truelon
            caselon _ => falselon
          }
        )
    }
  }

  delonf forTwelonelont(
    twelonelont: Twelonelont,
    vielonwelonrContelonxt: VielonwelonrContelonxt
  ): FelonaturelonMapBuildelonr => FelonaturelonMapBuildelonr = {
    CommunityTwelonelont(twelonelont) match {
      caselon Nonelon => forNonCommunityTwelonelont()
      caselon Somelon(communityTwelonelont) => forCommunityTwelonelont(communityTwelonelont)
    }
  }
}

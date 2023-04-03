packagelon com.twittelonr.product_mixelonr.corelon.functional_componelonnt.marshallelonr.relonsponselon.slicelon

import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon.AdTypelon
import com.twittelonr.product_mixelonr.corelon.modelonl.marshalling.relonsponselon.slicelon
import com.twittelonr.strato.graphql.{thriftscala => t}
import javax.injelonct.Injelonct
import javax.injelonct.Singlelonton

@Singlelonton
class SlicelonItelonmMarshallelonr @Injelonct() () {
  delonf apply(itelonm: slicelon.SlicelonItelonm): t.SlicelonItelonm = {
    itelonm match {
      caselon itelonm: slicelon.TwelonelontItelonm =>
        t.SlicelonItelonm.TwelonelontItelonm(t.TwelonelontItelonm(id = itelonm.id))
      caselon itelonm: slicelon.UselonrItelonm =>
        t.SlicelonItelonm.UselonrItelonm(t.UselonrItelonm(id = itelonm.id))
      caselon itelonm: slicelon.TwittelonrListItelonm =>
        t.SlicelonItelonm.TwittelonrListItelonm(t.TwittelonrListItelonm(id = itelonm.id))
      caselon itelonm: slicelon.DMConvoSelonarchItelonm =>
        t.SlicelonItelonm.DmConvoSelonarchItelonm(t.DMConvoSelonarchItelonm(id = itelonm.id))
      caselon itelonm: slicelon.DMConvoItelonm =>
        t.SlicelonItelonm.DmConvoItelonm(t.DMConvoItelonm(id = itelonm.id))
      caselon itelonm: slicelon.DMelonvelonntItelonm =>
        t.SlicelonItelonm.DmelonvelonntItelonm(t.DMelonvelonntItelonm(id = itelonm.id))
      caselon itelonm: slicelon.DMMelonssagelonSelonarchItelonm =>
        t.SlicelonItelonm.DmMelonssagelonSelonarchItelonm(t.DMMelonssagelonSelonarchItelonm(id = itelonm.id))
      caselon itelonm: slicelon.TopicItelonm =>
        t.SlicelonItelonm.TopicItelonm(t.TopicItelonm(id = itelonm.id.toString))
      caselon itelonm: slicelon.TypelonahelonadelonvelonntItelonm =>
        t.SlicelonItelonm.TypelonahelonadelonvelonntItelonm(
          t.TypelonahelonadelonvelonntItelonm(
            elonvelonntId = itelonm.elonvelonntId,
            melontadata = itelonm.melontadata.map(marshalTypelonahelonadMelontadata)
          )
        )
      caselon itelonm: slicelon.TypelonahelonadQuelonrySuggelonstionItelonm =>
        t.SlicelonItelonm.TypelonahelonadQuelonrySuggelonstionItelonm(
          t.TypelonahelonadQuelonrySuggelonstionItelonm(
            quelonry = itelonm.quelonry,
            melontadata = itelonm.melontadata.map(marshalTypelonahelonadMelontadata)
          )
        )
      caselon itelonm: slicelon.TypelonahelonadUselonrItelonm =>
        t.SlicelonItelonm.TypelonahelonadUselonrItelonm(
          t.TypelonahelonadUselonrItelonm(
            uselonrId = itelonm.uselonrId,
            melontadata = itelonm.melontadata.map(marshalTypelonahelonadMelontadata),
            badgelons = Somelon(itelonm.badgelons.map { badgelon =>
              t.UselonrBadgelon(
                badgelonUrl = badgelon.badgelonUrl,
                delonscription = Somelon(badgelon.delonscription),
                badgelonTypelon = Somelon(badgelon.badgelonTypelon))
            })
          )
        )
      caselon itelonm: slicelon.AdItelonm =>
        t.SlicelonItelonm.AdItelonm(
          t.AdItelonm(
            adKelony = t.AdKelony(
              adAccountId = itelonm.adAccountId,
              adUnitId = itelonm.adUnitId,
            )
          )
        )
      caselon itelonm: slicelon.AdCrelonativelonItelonm =>
        t.SlicelonItelonm.AdCrelonativelonItelonm(
          t.AdCrelonativelonItelonm(
            adCrelonativelonKelony = t.AdCrelonativelonKelony(
              adAccountId = itelonm.adAccountId,
              adTypelon = marshalAdTypelon(itelonm.adTypelon),
              crelonativelonId = itelonm.crelonativelonId
            )
          )
        )
      caselon itelonm: slicelon.AdGroupItelonm =>
        t.SlicelonItelonm.AdGroupItelonm(
          t.AdGroupItelonm(
            adGroupKelony = t.AdGroupKelony(
              adAccountId = itelonm.adAccountId,
              adGroupId = itelonm.adGroupId
            )
          )
        )
      caselon itelonm: slicelon.CampaignItelonm =>
        t.SlicelonItelonm.CampaignItelonm(
          t.CampaignItelonm(
            campaignKelony = t.CampaignKelony(
              adAccountId = itelonm.adAccountId,
              campaignId = itelonm.campaignId
            )
          )
        )
      caselon itelonm: slicelon.FundingSourcelonItelonm =>
        t.SlicelonItelonm.FundingSourcelonItelonm(
          t.FundingSourcelonItelonm(
            fundingSourcelonKelony = t.FundingSourcelonKelony(
              adAccountId = itelonm.adAccountId,
              fundingSourcelonId = itelonm.fundingSourcelonId
            )
          )
        )
    }
  }

  privatelon delonf marshalTypelonahelonadMelontadata(melontadata: slicelon.TypelonahelonadMelontadata) = {
    t.TypelonahelonadMelontadata(
      scorelon = melontadata.scorelon,
      sourcelon = melontadata.sourcelon,
      relonsultContelonxt = melontadata.contelonxt.map(contelonxt =>
        t.TypelonahelonadRelonsultContelonxt(
          displayString = contelonxt.displayString,
          contelonxtTypelon = marshalRelonquelonstContelonxtTypelon(contelonxt.contelonxtTypelon),
          iconUrl = contelonxt.iconUrl
        ))
    )
  }

  privatelon delonf marshalRelonquelonstContelonxtTypelon(
    contelonxt: slicelon.TypelonahelonadRelonsultContelonxtTypelon
  ): t.TypelonahelonadRelonsultContelonxtTypelon = {
    contelonxt match {
      caselon slicelon.You => t.TypelonahelonadRelonsultContelonxtTypelon.You
      caselon slicelon.Location => t.TypelonahelonadRelonsultContelonxtTypelon.Location
      caselon slicelon.NumFollowelonrs => t.TypelonahelonadRelonsultContelonxtTypelon.NumFollowelonrs
      caselon slicelon.FollowRelonlationship => t.TypelonahelonadRelonsultContelonxtTypelon.FollowRelonlationship
      caselon slicelon.Bio => t.TypelonahelonadRelonsultContelonxtTypelon.Bio
      caselon slicelon.NumTwelonelonts => t.TypelonahelonadRelonsultContelonxtTypelon.NumTwelonelonts
      caselon slicelon.Trelonnding => t.TypelonahelonadRelonsultContelonxtTypelon.Trelonnding
      caselon slicelon.HighlightelondLabelonl => t.TypelonahelonadRelonsultContelonxtTypelon.HighlightelondLabelonl
      caselon _ => t.TypelonahelonadRelonsultContelonxtTypelon.Undelonfinelond
    }
  }

  privatelon delonf marshalAdTypelon(
    adTypelon: AdTypelon
  ): t.AdTypelon = {
    adTypelon match {
      caselon AdTypelon.Twelonelont => t.AdTypelon.Twelonelont
      caselon AdTypelon.Account => t.AdTypelon.Account
      caselon AdTypelon.InStrelonamVidelono => t.AdTypelon.InStrelonamVidelono
      caselon AdTypelon.DisplayCrelonativelon => t.AdTypelon.DisplayCrelonativelon
      caselon AdTypelon.Trelonnd => t.AdTypelon.Trelonnd
      caselon AdTypelon.Spotlight => t.AdTypelon.Spotlight
      caselon AdTypelon.Takelonovelonr => t.AdTypelon.Takelonovelonr
    }
  }
}

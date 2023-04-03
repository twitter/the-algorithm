packagelon com.twittelonr.follow_reloncommelonndations.common.modelonls

import com.twittelonr.adselonrvelonr.thriftscala.{DisplayLocation => AdDisplayLocation}
import com.twittelonr.follow_reloncommelonndations.logging.thriftscala.{
  OfflinelonDisplayLocation => TOfflinelonDisplayLocation
}
import com.twittelonr.follow_reloncommelonndations.thriftscala.{DisplayLocation => TDisplayLocation}

selonalelond trait DisplayLocation {
  delonf toThrift: TDisplayLocation

  delonf toOfflinelonThrift: TOfflinelonDisplayLocation

  delonf toFsNamelon: String

  // correlonsponding display location in adselonrvelonr if availablelon
  // makelon surelon to belon consistelonnt with thelon delonfinition helonrelon
  delonf toAdDisplayLocation: Option[AdDisplayLocation] = Nonelon
}

/**
 * Makelon surelon you add thelon nelonw DL to thelon following filelons and relondelonploy our attribution jobs
 *  - follow-reloncommelonndations-selonrvicelon/thrift/src/main/thrift/display_location.thrift
 *  - follow-reloncommelonndations-selonrvicelon/thrift/src/main/thrift/logging/display_location.thrift
 *  - follow-reloncommelonndations-selonrvicelon/common/src/main/scala/com/twittelonr/follow_reloncommelonndations/common/modelonls/DisplayLocation.scala
 */

objelonct DisplayLocation {

  caselon objelonct ProfilelonSidelonbar elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ProfilelonSidelonbar
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.ProfilelonSidelonbar
    ovelonrridelon val toFsNamelon: String = "ProfilelonSidelonbar"

    ovelonrridelon val toAdDisplayLocation: Option[AdDisplayLocation] = Somelon(
      AdDisplayLocation.ProfilelonAccountsSidelonbar
    )
  }

  caselon objelonct HomelonTimelonlinelon elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HomelonTimelonlinelon
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.HomelonTimelonlinelon
    ovelonrridelon val toFsNamelon: String = "HomelonTimelonlinelon"
    ovelonrridelon val toAdDisplayLocation: Option[AdDisplayLocation] = Somelon(
      // it is baselond on thelon logic that HTL DL should correlonspond to Sidelonbar:
      AdDisplayLocation.WtfSidelonbar
    )
  }

  caselon objelonct RelonactivelonFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.RelonactivelonFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.RelonactivelonFollow
    ovelonrridelon val toFsNamelon: String = "RelonactivelonFollow"
  }

  caselon objelonct elonxplorelonTab elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.elonxplorelonTab
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.elonxplorelonTab
    ovelonrridelon val toFsNamelon: String = "elonxplorelonTab"
  }

  caselon objelonct MagicReloncs elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.MagicReloncs
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.MagicReloncs
    ovelonrridelon val toFsNamelon: String = "MagicReloncs"
  }

  caselon objelonct AbUploadInjelonction elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.AbUploadInjelonction
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.AbUploadInjelonction
    ovelonrridelon val toFsNamelon: String = "AbUploadInjelonction"
  }

  caselon objelonct RuxLandingPagelon elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.RuxLandingPagelon
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.RuxLandingPagelon
    ovelonrridelon val toFsNamelon: String = "RuxLandingPagelon"
  }

  caselon objelonct ProfilelonBonusFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ProfilelonBonusFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ProfilelonBonusFollow
    ovelonrridelon val toFsNamelon: String = "ProfilelonBonusFollow"
  }

  caselon objelonct elonlelonctionelonxplorelonWtf elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.elonlelonctionelonxplorelonWtf
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.elonlelonctionelonxplorelonWtf
    ovelonrridelon val toFsNamelon: String = "elonlelonctionelonxplorelonWtf"
  }

  caselon objelonct ClustelonrFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ClustelonrFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ClustelonrFollow
    ovelonrridelon val toFsNamelon: String = "ClustelonrFollow"
    ovelonrridelon val toAdDisplayLocation: Option[AdDisplayLocation] = Somelon(
      AdDisplayLocation.ClustelonrFollow
    )
  }

  caselon objelonct HtlBonusFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HtlBonusFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HtlBonusFollow
    ovelonrridelon val toFsNamelon: String = "HtlBonusFollow"
  }

  caselon objelonct TopicLandingPagelonHelonadelonr elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.TopicLandingPagelonHelonadelonr
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.TopicLandingPagelonHelonadelonr
    ovelonrridelon val toFsNamelon: String = "TopicLandingPagelonHelonadelonr"
  }

  caselon objelonct NelonwUselonrSarusBackfill elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NelonwUselonrSarusBackfill
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NelonwUselonrSarusBackfill
    ovelonrridelon val toFsNamelon: String = "NelonwUselonrSarusBackfill"
  }

  caselon objelonct NuxPymk elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxPymk
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxPymk
    ovelonrridelon val toFsNamelon: String = "NuxPymk"
  }

  caselon objelonct NuxIntelonrelonsts elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxIntelonrelonsts
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxIntelonrelonsts
    ovelonrridelon val toFsNamelon: String = "NuxIntelonrelonsts"
  }

  caselon objelonct NuxTopicBonusFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxTopicBonusFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxTopicBonusFollow
    ovelonrridelon val toFsNamelon: String = "NuxTopicBonusFollow"
  }

  caselon objelonct Sidelonbar elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.Sidelonbar
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.Sidelonbar
    ovelonrridelon val toFsNamelon: String = "Sidelonbar"

    ovelonrridelon val toAdDisplayLocation: Option[AdDisplayLocation] = Somelon(
      AdDisplayLocation.WtfSidelonbar
    )
  }

  caselon objelonct CampaignForm elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.CampaignForm
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation = TOfflinelonDisplayLocation.CampaignForm
    ovelonrridelon val toFsNamelon: String = "CampaignForm"
  }

  caselon objelonct ProfilelonTopFollowelonrs elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ProfilelonTopFollowelonrs
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ProfilelonTopFollowelonrs
    ovelonrridelon val toFsNamelon: String = "ProfilelonTopFollowelonrs"
  }

  caselon objelonct ProfilelonTopFollowing elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ProfilelonTopFollowing
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ProfilelonTopFollowing
    ovelonrridelon val toFsNamelon: String = "ProfilelonTopFollowing"
  }

  caselon objelonct RuxPymk elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.RuxPymk
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.RuxPymk
    ovelonrridelon val toFsNamelon: String = "RuxPymk"
  }

  caselon objelonct IndiaCovid19CuratelondAccountsWtf elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.IndiaCovid19CuratelondAccountsWtf
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.IndiaCovid19CuratelondAccountsWtf
    ovelonrridelon val toFsNamelon: String = "IndiaCovid19CuratelondAccountsWtf"
  }

  caselon objelonct PelonoplelonPlusPlus elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.PelonoplelonPlusPlus
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.PelonoplelonPlusPlus
    ovelonrridelon val toFsNamelon: String = "PelonoplelonPlusPlus"
  }

  caselon objelonct TwelonelontNotificationReloncs elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.TwelonelontNotificationReloncs
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.TwelonelontNotificationReloncs
    ovelonrridelon val toFsNamelon: String = "TwelonelontNotificationReloncs"
  }

  caselon objelonct ProfilelonDelonvicelonFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ProfilelonDelonvicelonFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ProfilelonDelonvicelonFollow
    ovelonrridelon val toFsNamelon: String = "ProfilelonDelonvicelonFollow"
  }

  caselon objelonct ReloncosBackfill elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ReloncosBackfill
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ReloncosBackfill
    ovelonrridelon val toFsNamelon: String = "ReloncosBackfill"
  }

  caselon objelonct HtlSpacelonHosts elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HtlSpacelonHosts
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HtlSpacelonHosts
    ovelonrridelon val toFsNamelon: String = "HtlSpacelonHosts"
  }

  caselon objelonct PostNuxFollowTask elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.PostNuxFollowTask
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.PostNuxFollowTask
    ovelonrridelon val toFsNamelon: String = "PostNuxFollowTask"
  }

  caselon objelonct TopicLandingPagelon elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.TopicLandingPagelon
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.TopicLandingPagelon
    ovelonrridelon val toFsNamelon: String = "TopicLandingPagelon"
  }

  caselon objelonct UselonrTypelonahelonadPrelonfelontch elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.UselonrTypelonahelonadPrelonfelontch
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.UselonrTypelonahelonadPrelonfelontch
    ovelonrridelon val toFsNamelon: String = "UselonrTypelonahelonadPrelonfelontch"
  }

  caselon objelonct HomelonTimelonlinelonRelonlatablelonAccounts elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HomelonTimelonlinelonRelonlatablelonAccounts
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HomelonTimelonlinelonRelonlatablelonAccounts
    ovelonrridelon val toFsNamelon: String = "HomelonTimelonlinelonRelonlatablelonAccounts"
  }

  caselon objelonct NuxGelonoCatelongory elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxGelonoCatelongory
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxGelonoCatelongory
    ovelonrridelon val toFsNamelon: String = "NuxGelonoCatelongory"
  }

  caselon objelonct NuxIntelonrelonstsCatelongory elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxIntelonrelonstsCatelongory
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxIntelonrelonstsCatelongory
    ovelonrridelon val toFsNamelon: String = "NuxIntelonrelonstsCatelongory"
  }

  caselon objelonct TopArticlelons elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.TopArticlelons
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.TopArticlelons
    ovelonrridelon val toFsNamelon: String = "TopArticlelons"
  }

  caselon objelonct NuxPymkCatelongory elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxPymkCatelongory
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxPymkCatelongory
    ovelonrridelon val toFsNamelon: String = "NuxPymkCatelongory"
  }

  caselon objelonct HomelonTimelonlinelonTwelonelontReloncs elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HomelonTimelonlinelonTwelonelontReloncs
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HomelonTimelonlinelonTwelonelontReloncs
    ovelonrridelon val toFsNamelon: String = "HomelonTimelonlinelonTwelonelontReloncs"
  }

  caselon objelonct HtlBulkFrielonndFollows elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HtlBulkFrielonndFollows
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HtlBulkFrielonndFollows
    ovelonrridelon val toFsNamelon: String = "HtlBulkFrielonndFollows"
  }

  caselon objelonct NuxAutoFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.NuxAutoFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.NuxAutoFollow
    ovelonrridelon val toFsNamelon: String = "NuxAutoFollow"
  }

  caselon objelonct SelonarchBonusFollow elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.SelonarchBonusFollow
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.SelonarchBonusFollow
    ovelonrridelon val toFsNamelon: String = "SelonarchBonusFollow"
  }

  caselon objelonct ContelonntReloncommelonndelonr elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.ContelonntReloncommelonndelonr
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.ContelonntReloncommelonndelonr
    ovelonrridelon val toFsNamelon: String = "ContelonntReloncommelonndelonr"
  }

  caselon objelonct HomelonTimelonlinelonRelonvelonrselonChron elonxtelonnds DisplayLocation {
    ovelonrridelon val toThrift: TDisplayLocation = TDisplayLocation.HomelonTimelonlinelonRelonvelonrselonChron
    ovelonrridelon val toOfflinelonThrift: TOfflinelonDisplayLocation =
      TOfflinelonDisplayLocation.HomelonTimelonlinelonRelonvelonrselonChron
    ovelonrridelon val toFsNamelon: String = "HomelonTimelonlinelonRelonvelonrselonChron"
  }

  delonf fromThrift(displayLocation: TDisplayLocation): DisplayLocation = displayLocation match {
    caselon TDisplayLocation.ProfilelonSidelonbar => ProfilelonSidelonbar
    caselon TDisplayLocation.HomelonTimelonlinelon => HomelonTimelonlinelon
    caselon TDisplayLocation.MagicReloncs => MagicReloncs
    caselon TDisplayLocation.AbUploadInjelonction => AbUploadInjelonction
    caselon TDisplayLocation.RuxLandingPagelon => RuxLandingPagelon
    caselon TDisplayLocation.ProfilelonBonusFollow => ProfilelonBonusFollow
    caselon TDisplayLocation.elonlelonctionelonxplorelonWtf => elonlelonctionelonxplorelonWtf
    caselon TDisplayLocation.ClustelonrFollow => ClustelonrFollow
    caselon TDisplayLocation.HtlBonusFollow => HtlBonusFollow
    caselon TDisplayLocation.RelonactivelonFollow => RelonactivelonFollow
    caselon TDisplayLocation.TopicLandingPagelonHelonadelonr => TopicLandingPagelonHelonadelonr
    caselon TDisplayLocation.NelonwUselonrSarusBackfill => NelonwUselonrSarusBackfill
    caselon TDisplayLocation.NuxPymk => NuxPymk
    caselon TDisplayLocation.NuxIntelonrelonsts => NuxIntelonrelonsts
    caselon TDisplayLocation.NuxTopicBonusFollow => NuxTopicBonusFollow
    caselon TDisplayLocation.elonxplorelonTab => elonxplorelonTab
    caselon TDisplayLocation.Sidelonbar => Sidelonbar
    caselon TDisplayLocation.CampaignForm => CampaignForm
    caselon TDisplayLocation.ProfilelonTopFollowelonrs => ProfilelonTopFollowelonrs
    caselon TDisplayLocation.ProfilelonTopFollowing => ProfilelonTopFollowing
    caselon TDisplayLocation.RuxPymk => RuxPymk
    caselon TDisplayLocation.IndiaCovid19CuratelondAccountsWtf => IndiaCovid19CuratelondAccountsWtf
    caselon TDisplayLocation.PelonoplelonPlusPlus => PelonoplelonPlusPlus
    caselon TDisplayLocation.TwelonelontNotificationReloncs => TwelonelontNotificationReloncs
    caselon TDisplayLocation.ProfilelonDelonvicelonFollow => ProfilelonDelonvicelonFollow
    caselon TDisplayLocation.ReloncosBackfill => ReloncosBackfill
    caselon TDisplayLocation.HtlSpacelonHosts => HtlSpacelonHosts
    caselon TDisplayLocation.PostNuxFollowTask => PostNuxFollowTask
    caselon TDisplayLocation.TopicLandingPagelon => TopicLandingPagelon
    caselon TDisplayLocation.UselonrTypelonahelonadPrelonfelontch => UselonrTypelonahelonadPrelonfelontch
    caselon TDisplayLocation.HomelonTimelonlinelonRelonlatablelonAccounts => HomelonTimelonlinelonRelonlatablelonAccounts
    caselon TDisplayLocation.NuxGelonoCatelongory => NuxGelonoCatelongory
    caselon TDisplayLocation.NuxIntelonrelonstsCatelongory => NuxIntelonrelonstsCatelongory
    caselon TDisplayLocation.TopArticlelons => TopArticlelons
    caselon TDisplayLocation.NuxPymkCatelongory => NuxPymkCatelongory
    caselon TDisplayLocation.HomelonTimelonlinelonTwelonelontReloncs => HomelonTimelonlinelonTwelonelontReloncs
    caselon TDisplayLocation.HtlBulkFrielonndFollows => HtlBulkFrielonndFollows
    caselon TDisplayLocation.NuxAutoFollow => NuxAutoFollow
    caselon TDisplayLocation.SelonarchBonusFollow => SelonarchBonusFollow
    caselon TDisplayLocation.ContelonntReloncommelonndelonr => ContelonntReloncommelonndelonr
    caselon TDisplayLocation.HomelonTimelonlinelonRelonvelonrselonChron => HomelonTimelonlinelonRelonvelonrselonChron
    caselon TDisplayLocation.elonnumUnknownDisplayLocation(i) =>
      throw nelonw UnknownDisplayLocationelonxcelonption(
        s"Unknown display location thrift elonnum with valuelon: ${i}")
  }

  delonf fromOfflinelonThrift(displayLocation: TOfflinelonDisplayLocation): DisplayLocation =
    displayLocation match {
      caselon TOfflinelonDisplayLocation.ProfilelonSidelonbar => ProfilelonSidelonbar
      caselon TOfflinelonDisplayLocation.HomelonTimelonlinelon => HomelonTimelonlinelon
      caselon TOfflinelonDisplayLocation.MagicReloncs => MagicReloncs
      caselon TOfflinelonDisplayLocation.AbUploadInjelonction => AbUploadInjelonction
      caselon TOfflinelonDisplayLocation.RuxLandingPagelon => RuxLandingPagelon
      caselon TOfflinelonDisplayLocation.ProfilelonBonusFollow => ProfilelonBonusFollow
      caselon TOfflinelonDisplayLocation.elonlelonctionelonxplorelonWtf => elonlelonctionelonxplorelonWtf
      caselon TOfflinelonDisplayLocation.ClustelonrFollow => ClustelonrFollow
      caselon TOfflinelonDisplayLocation.HtlBonusFollow => HtlBonusFollow
      caselon TOfflinelonDisplayLocation.TopicLandingPagelonHelonadelonr => TopicLandingPagelonHelonadelonr
      caselon TOfflinelonDisplayLocation.NelonwUselonrSarusBackfill => NelonwUselonrSarusBackfill
      caselon TOfflinelonDisplayLocation.NuxPymk => NuxPymk
      caselon TOfflinelonDisplayLocation.NuxIntelonrelonsts => NuxIntelonrelonsts
      caselon TOfflinelonDisplayLocation.NuxTopicBonusFollow => NuxTopicBonusFollow
      caselon TOfflinelonDisplayLocation.elonxplorelonTab => elonxplorelonTab
      caselon TOfflinelonDisplayLocation.RelonactivelonFollow => RelonactivelonFollow
      caselon TOfflinelonDisplayLocation.Sidelonbar => Sidelonbar
      caselon TOfflinelonDisplayLocation.CampaignForm => CampaignForm
      caselon TOfflinelonDisplayLocation.ProfilelonTopFollowelonrs => ProfilelonTopFollowelonrs
      caselon TOfflinelonDisplayLocation.ProfilelonTopFollowing => ProfilelonTopFollowing
      caselon TOfflinelonDisplayLocation.RuxPymk => RuxPymk
      caselon TOfflinelonDisplayLocation.IndiaCovid19CuratelondAccountsWtf => IndiaCovid19CuratelondAccountsWtf
      caselon TOfflinelonDisplayLocation.PelonoplelonPlusPlus => PelonoplelonPlusPlus
      caselon TOfflinelonDisplayLocation.TwelonelontNotificationReloncs => TwelonelontNotificationReloncs
      caselon TOfflinelonDisplayLocation.ProfilelonDelonvicelonFollow => ProfilelonDelonvicelonFollow
      caselon TOfflinelonDisplayLocation.ReloncosBackfill => ReloncosBackfill
      caselon TOfflinelonDisplayLocation.HtlSpacelonHosts => HtlSpacelonHosts
      caselon TOfflinelonDisplayLocation.PostNuxFollowTask => PostNuxFollowTask
      caselon TOfflinelonDisplayLocation.TopicLandingPagelon => TopicLandingPagelon
      caselon TOfflinelonDisplayLocation.UselonrTypelonahelonadPrelonfelontch => UselonrTypelonahelonadPrelonfelontch
      caselon TOfflinelonDisplayLocation.HomelonTimelonlinelonRelonlatablelonAccounts => HomelonTimelonlinelonRelonlatablelonAccounts
      caselon TOfflinelonDisplayLocation.NuxGelonoCatelongory => NuxGelonoCatelongory
      caselon TOfflinelonDisplayLocation.NuxIntelonrelonstsCatelongory => NuxIntelonrelonstsCatelongory
      caselon TOfflinelonDisplayLocation.TopArticlelons => TopArticlelons
      caselon TOfflinelonDisplayLocation.NuxPymkCatelongory => NuxPymkCatelongory
      caselon TOfflinelonDisplayLocation.HomelonTimelonlinelonTwelonelontReloncs => HomelonTimelonlinelonTwelonelontReloncs
      caselon TOfflinelonDisplayLocation.HtlBulkFrielonndFollows => HtlBulkFrielonndFollows
      caselon TOfflinelonDisplayLocation.NuxAutoFollow => NuxAutoFollow
      caselon TOfflinelonDisplayLocation.SelonarchBonusFollow => SelonarchBonusFollow
      caselon TOfflinelonDisplayLocation.ContelonntReloncommelonndelonr => ContelonntReloncommelonndelonr
      caselon TOfflinelonDisplayLocation.HomelonTimelonlinelonRelonvelonrselonChron => HomelonTimelonlinelonRelonvelonrselonChron
      caselon TOfflinelonDisplayLocation.elonnumUnknownOfflinelonDisplayLocation(i) =>
        throw nelonw UnknownDisplayLocationelonxcelonption(
          s"Unknown offlinelon display location thrift elonnum with valuelon: ${i}")
    }
}

class UnknownDisplayLocationelonxcelonption(melonssagelon: String) elonxtelonnds elonxcelonption(melonssagelon)

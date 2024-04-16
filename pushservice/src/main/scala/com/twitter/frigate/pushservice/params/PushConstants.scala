package com.tw ter.fr gate.pushserv ce.params

 mport com.tw ter.convers ons.Durat onOps._
 mport com.tw ter.fr gate.user_states.thr ftscala.UserState
 mport java.ut l.Locale

object PushConstants {

  f nal val Serv ceProdEnv ron ntNa  = "prod"

  f nal val Restr ctL ghtRank ngCand datesThreshold = 1

  f nal val DownSampleL ghtRank ngScr beCand datesRate = 1

  f nal val NewUserLookbackW ndow = 1.days

  f nal val PushCap nact veUserAndro d = 1
  f nal val PushCap nact veUser os = 1
  f nal val PushCapL ghtOccas onalOpenerUserAndro d = 1
  f nal val PushCapL ghtOccas onalOpenerUser os = 1

  f nal val UserStateToPushCap os = Map(
    UserState. nact ve.na  -> PushCap nact veUser os,
    UserState.L ghtOccas onalOpener.na  -> PushCapL ghtOccas onalOpenerUser os
  )
  f nal val UserStateToPushCapAndro d = Map(
    UserState. nact ve.na  -> PushCap nact veUserAndro d,
    UserState.L ghtOccas onalOpener.na  -> PushCapL ghtOccas onalOpenerUserAndro d
  )

  f nal val AcceptableT  S nceLastNegat veResponse = 1.days

  f nal val DefaultLookBackFor tory = 1.h s

  f nal val DefaultEvent d aUrl = ""

  f nal val ConnectTabPushTapThrough = " /connect_people"

  f nal val AddressBookUploadTapThrough = " /flow/mr-address-book-upload"
  f nal val  nterestP ckerTapThrough = " /flow/mr- nterest-p cker"
  f nal val CompleteOnboard ng nterestAddressTapThrough = " /flow/mr- nterest-address"

  f nal val  nd aCountryCode = " N"
  f nal val JapanCountryCode = Locale.JAPAN.getCountry.toUpperCase
  f nal val UKCountryCode = Locale.UK.getCountry.toUpperCase

  f nal val  nd aT  ZoneCode = "As a/Kolkata"
  f nal val JapanT  ZoneCode = "As a/Tokyo"
  f nal val UKT  ZoneCode = "Europe/London"

  f nal val countryCodeToT  ZoneMap = Map(
     nd aCountryCode ->  nd aT  ZoneCode,
    JapanCountryCode -> JapanT  ZoneCode,
    UKCountryCode -> UKT  ZoneCode
  )

  f nal val AbuseStr ke_Top2Percent_ d = "AbuseStr ke_Top2Percent_ d"
  f nal val AbuseStr ke_Top1Percent_ d = "AbuseStr ke_Top1Percent_ d"
  f nal val AbuseStr ke_Top05Percent_ d = "AbuseStr ke_Top05Percent_ d"
  f nal val AbuseStr ke_Top025Percent_ d = "AbuseStr ke_Top025Percent_ d"
  f nal val AllSpamReportsPerFav_Top1Percent_ d = "AllSpamReportsPerFav_Top1Percent_ d"
  f nal val ReportsPerFav_Top1Percent_ d = "ReportsPerFav_Top1Percent_ d"
  f nal val ReportsPerFav_Top2Percent_ d = "ReportsPerFav_Top2Percent_ d"
  f nal val  d aUnderstand ng_Nud y_ d = " d aUnderstand ng_Nud y_ d"
  f nal val  d aUnderstand ng_Beauty_ d = " d aUnderstand ng_Beauty_ d"
  f nal val  d aUnderstand ng_S nglePerson_ d = " d aUnderstand ng_S nglePerson_ d"
  f nal val PornL st_ d = "PornL st_ d"
  f nal val PornographyAndNsfwContent_ d = "PornographyAndNsfwContent_ d"
  f nal val SexL fe_ d = "SexL fe_ d"
  f nal val SexL feOrSexualOr entat on_ d = "SexL feOrSexualOr entat on_ d"
  f nal val Profan yF lter_ d = "Profan yF lter_ d"
  f nal val T etSemant cCore dFeature = "t et.core.t et.semant c_core_annotat ons"
  f nal val targetUserGenderFeatureNa  = "Target.User.Gender"
  f nal val targetUserAgeFeatureNa  = "Target.User.AgeBucket"
  f nal val targetUserPreferredLanguage = "user.language.user.preferred_contents"
  f nal val t etAge nH sFeatureNa  = "RecT et.T etyP eResult.T etAge nHrs"
  f nal val authorAct veFollo rFeatureNa  = "RecT etAuthor.User.Act veFollo rs"
  f nal val favFeatureNa  = "t et.core.t et_counts.favor e_count"
  f nal val sentFeatureNa  =
    "t et.mag c_recs_t et_real_t  _aggregates_v2.pa r.v2.mag crecs.realt  . s_sent.any_feature.Durat on.Top.count"
  f nal val authorSendCountFeatureNa  =
    "t et_author_aggregate.pa r.any_label.any_feature.28.days.count"
  f nal val authorReportCountFeatureNa  =
    "t et_author_aggregate.pa r.label.reportT etDone.any_feature.28.days.count"
  f nal val authorD sl keCountFeatureNa  =
    "t et_author_aggregate.pa r.label.ntab. sD sl ked.any_feature.28.days.count"
  f nal val T etL kesFeatureNa  = "t et.core.t et_counts.favor e_count"
  f nal val T etRepl esFeatureNa  = "t et.core.t et_counts.reply_count"

  f nal val EnableCopyFeaturesFor b s2ModelValues = "has_copy_features"

  f nal val Emoj FeatureNa For b s2ModelValues = "emoj "

  f nal val TargetFeatureNa For b s2ModelValues = "target"

  f nal val CopyBodyExp b sModelValues = "enable_body_exp"

  f nal val T et d aEmbedd ngBQKey ds = Seq(
    230, 110, 231, 111, 232, 233, 112, 113, 234, 235, 114, 236, 115, 237, 116, 117, 238, 118, 239,
    119, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 240, 120, 241, 121, 242, 0, 1, 122, 243, 244, 123,
    2, 124, 245, 3, 4, 246, 125, 5, 126, 247, 127, 248, 6, 128, 249, 7, 8, 129, 9, 20, 21, 22, 23,
    24, 25, 26, 27, 28, 29, 250, 130, 251, 252, 131, 132, 253, 133, 254, 134, 255, 135, 136, 137,
    138, 139, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 140, 141, 142, 143, 144, 145, 146, 147, 148,
    149, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159,
    50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 60,
    61, 62, 63, 64, 65, 66, 67, 68, 69, 170, 171, 172, 173, 174, 175, 176, 177, 178, 179, 70, 71,
    72, 73, 74, 75, 76, 77, 78, 79, 180, 181, 182, 183, 184, 185, 186, 187, 188, 189, 80, 81, 82,
    83, 84, 85, 86, 87, 88, 89, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 90, 91, 92, 93,
    94, 95, 96, 97, 98, 99, 200, 201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
    214, 215, 216, 217, 218, 219, 220, 100, 221, 101, 222, 223, 102, 224, 103, 104, 225, 105, 226,
    227, 106, 107, 228, 108, 229, 109
  )

  f nal val SportsEventDoma n d = 6L

  f nal val OoncQual yComb nedScore = "OoncQual yComb nedScore"
}

object PushQPSL m Constants {

  f nal val Perspect veStoreQPS = 100000

  f nal val  b sOrNTabQPSForRFPH = 100000

  f nal val Soc alGraphServ ceBatchS ze = 100000
}

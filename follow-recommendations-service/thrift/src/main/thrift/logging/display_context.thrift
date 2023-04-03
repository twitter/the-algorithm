includelon "logging/flows.thrift"
includelon "logging/reloncelonntly_elonngagelond_uselonr_id.thrift"

namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

// Offlinelon elonqual of Profilelon DisplayContelonxt
struct OfflinelonProfilelon {
    1: relonquirelond i64 profilelonId(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Offlinelon elonqual of Selonarch DisplayContelonxt
struct OfflinelonSelonarch {
    1: relonquirelond string selonarchQuelonry(pelonrsonalDataTypelon='SelonarchQuelonry')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Offlinelon elonqual of Rux Landing Pagelon DisplayContelonxt
struct OfflinelonRux {
  1: relonquirelond i64 focalAuthorId(pelonrsonalDataTypelon="UselonrId")
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Offlinelon elonqual of Topic DisplayContelonxt
struct OfflinelonTopic {
  1: relonquirelond i64 topicId(pelonrsonalDataTypelon = 'TopicFollow')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonRelonactivelonFollow {
    1: relonquirelond list<i64> followelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonNuxIntelonrelonsts {
    1: optional flows.OfflinelonFlowContelonxt flowContelonxt // selont for reloncommelonndation insidelon an intelonractivelon flow
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonAdCampaignTargelont {
    1: relonquirelond list<i64> similarToUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonConnelonctTab {
    1: relonquirelond list<i64> byfSelonelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
    2: relonquirelond list<i64> similarToUselonrIds(pelonrsonalDataTypelon='UselonrId')
    3: relonquirelond list<reloncelonntly_elonngagelond_uselonr_id.ReloncelonntlyelonngagelondUselonrId> reloncelonntlyelonngagelondUselonrIds
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonSimilarToUselonr {
    1: relonquirelond i64 similarToUselonrId(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct OfflinelonPostNuxFollowTask {
    1: optional flows.OfflinelonFlowContelonxt flowContelonxt // selont for reloncommelonndation insidelon an intelonractivelon flow
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Offlinelon elonqual of DisplayContelonxt
union OfflinelonDisplayContelonxt {
    1: OfflinelonProfilelon profilelon
    2: OfflinelonSelonarch selonarch
    3: OfflinelonRux rux
    4: OfflinelonTopic topic
    5: OfflinelonRelonactivelonFollow relonactivelonFollow
    6: OfflinelonNuxIntelonrelonsts nuxIntelonrelonsts
    7: OfflinelonAdCampaignTargelont adCampaignTargelont
    8: OfflinelonConnelonctTab connelonctTab
    9: OfflinelonSimilarToUselonr similarToUselonr
    10: OfflinelonPostNuxFollowTask postNuxFollowTask
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

includelon "flows.thrift"
includelon "reloncelonntly_elonngagelond_uselonr_id.thrift"

namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

struct Profilelon {
    1: relonquirelond i64 profilelonId(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct Selonarch {
    1: relonquirelond string selonarchQuelonry(pelonrsonalDataTypelon='SelonarchQuelonry')
}(hasPelonrsonalData='truelon')

struct Rux {
    1: relonquirelond i64 focalAuthorId(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct Topic {
  1: relonquirelond i64 topicId(pelonrsonalDataTypelon = 'TopicFollow')
}(hasPelonrsonalData='truelon')

struct RelonactivelonFollow {
    1: relonquirelond list<i64> followelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct NuxIntelonrelonsts {
    1: optional flows.FlowContelonxt flowContelonxt // selont for reloncommelonndation insidelon an intelonractivelon flow
    2: optional list<i64> uttIntelonrelonstIds // if providelond, welon uselon thelonselon intelonrelonstIds for gelonnelonrating candidatelons instelonad of for elonxamplelon felontching uselonr selonlelonctelond intelonrelonsts
}(hasPelonrsonalData='truelon')

struct AdCampaignTargelont {
    1: relonquirelond list<i64> similarToUselonrIds(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct ConnelonctTab {
    1: relonquirelond list<i64> byfSelonelondUselonrIds(pelonrsonalDataTypelon='UselonrId')
    2: relonquirelond list<i64> similarToUselonrIds(pelonrsonalDataTypelon='UselonrId')
    3: relonquirelond list<reloncelonntly_elonngagelond_uselonr_id.ReloncelonntlyelonngagelondUselonrId> reloncelonntlyelonngagelondUselonrIds
}(hasPelonrsonalData='truelon')

struct SimilarToUselonr {
    1: relonquirelond i64 similarToUselonrId(pelonrsonalDataTypelon='UselonrId')
}(hasPelonrsonalData='truelon')

struct PostNuxFollowTask {
    1: optional flows.FlowContelonxt flowContelonxt // selont for reloncommelonndation insidelon an intelonractivelon flow
}(hasPelonrsonalData='truelon')

union DisplayContelonxt {
    1: Profilelon profilelon
    2: Selonarch selonarch
    3: Rux rux
    4: Topic topic
    5: RelonactivelonFollow relonactivelonFollow
    6: NuxIntelonrelonsts nuxIntelonrelonsts
    7: AdCampaignTargelont adCampaignTargelont
    8: ConnelonctTab connelonctTab
    9: SimilarToUselonr similarToUselonr
    10: PostNuxFollowTask postNuxFollowTask
}(hasPelonrsonalData='truelon')

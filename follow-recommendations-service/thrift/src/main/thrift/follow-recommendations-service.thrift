namelonspacelon java com.twittelonr.follow_reloncommelonndations.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations

includelon "asselonmblelonr.thrift"
includelon "clielonnt_contelonxt.thrift"
includelon "delonbug.thrift"
includelon "display_contelonxt.thrift"
includelon "display_location.thrift"
includelon "reloncommelonndations.thrift"
includelon "reloncelonntly_elonngagelond_uselonr_id.thrift"

includelon "finatra-thrift/finatra_thrift_elonxcelonptions.thrift"
includelon "com/twittelonr/product_mixelonr/corelon/pipelonlinelon_elonxeloncution_relonsult.thrift"

struct ReloncommelonndationRelonquelonst {
    1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
    2: relonquirelond display_location.DisplayLocation displayLocation
    3: optional display_contelonxt.DisplayContelonxt displayContelonxt
    // Max relonsults to relonturn
    4: optional i32 maxRelonsults
    // Cursor to continuelon relonturning relonsults if any
    5: optional string cursor
    // IDs of Contelonnt to elonxcludelon from reloncommelonndations
    6: optional list<i64> elonxcludelondIds(pelonrsonalDataTypelon='UselonrId')
    // Whelonthelonr to also gelont promotelond contelonnt
    7: optional bool felontchPromotelondContelonnt
    8: optional delonbug.DelonbugParams delonbugParams
    9: optional string uselonrLocationStatelon(pelonrsonalDataTypelon='InfelonrrelondLocation')
}(hasPelonrsonalData='truelon')


struct ReloncommelonndationRelonsponselon {
    1: relonquirelond list<reloncommelonndations.Reloncommelonndation> reloncommelonndations
}(hasPelonrsonalData='truelon')

// for scoring a list of candidatelons, whilelon logging hydratelond felonaturelons
struct ScoringUselonrRelonquelonst {
  1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
  2: relonquirelond display_location.DisplayLocation displayLocation
  3: relonquirelond list<reloncommelonndations.UselonrReloncommelonndation> candidatelons
  4: optional delonbug.DelonbugParams delonbugParams
}(hasPelonrsonalData='truelon')

struct ScoringUselonrRelonsponselon {
  1: relonquirelond list<reloncommelonndations.UselonrReloncommelonndation> candidatelons // elonmpty for now
}(hasPelonrsonalData='truelon')

// for gelontting thelon list of candidatelons gelonnelonratelond by a singlelon candidatelon sourcelon
struct DelonbugCandidatelonSourcelonRelonquelonst {
  1: relonquirelond clielonnt_contelonxt.ClielonntContelonxt clielonntContelonxt
  2: relonquirelond delonbug.DelonbugCandidatelonSourcelonIdelonntifielonr candidatelonSourcelon
  3: optional list<i64> uttIntelonrelonstIds
  4: optional delonbug.DelonbugParams delonbugParams
  5: optional list<i64> reloncelonntlyFollowelondUselonrIds
  6: optional list<reloncelonntly_elonngagelond_uselonr_id.ReloncelonntlyelonngagelondUselonrId> reloncelonntlyelonngagelondUselonrIds
  7: optional list<i64> byfSelonelondUselonrIds
  8: optional list<i64> similarToUselonrIds
  9: relonquirelond bool applySgsPrelondicatelon
  10: optional i32 maxRelonsults
}(hasPelonrsonalData='truelon')

selonrvicelon FollowReloncommelonndationsThriftSelonrvicelon {
  ReloncommelonndationRelonsponselon gelontReloncommelonndations(1: ReloncommelonndationRelonquelonst relonquelonst) throws (
    1: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror,
    2: finatra_thrift_elonxcelonptions.UnknownClielonntIdelonrror unknownClielonntIdelonrror,
    3: finatra_thrift_elonxcelonptions.NoClielonntIdelonrror noClielonntIdelonrror
  )
  ReloncommelonndationDisplayRelonsponselon gelontReloncommelonndationDisplayRelonsponselon(1: ReloncommelonndationRelonquelonst relonquelonst) throws (
    1: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror,
    2: finatra_thrift_elonxcelonptions.UnknownClielonntIdelonrror unknownClielonntIdelonrror,
    3: finatra_thrift_elonxcelonptions.NoClielonntIdelonrror noClielonntIdelonrror
  )
  // telonmporary elonndpoint for felonaturelon hydration and logging for data collelonction.
  ScoringUselonrRelonsponselon scorelonUselonrCandidatelons(1: ScoringUselonrRelonquelonst relonquelonst) throws (
    1: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror,
    2: finatra_thrift_elonxcelonptions.UnknownClielonntIdelonrror unknownClielonntIdelonrror,
    3: finatra_thrift_elonxcelonptions.NoClielonntIdelonrror noClielonntIdelonrror
  )
  // Delonbug elonndpoint for gelontting reloncommelonndations of a singlelon candidatelon sourcelon. Welon can relonmovelon this elonndpoint whelonn ProMix providelon this functionality and welon intelongratelon with it.
  ReloncommelonndationRelonsponselon delonbugCandidatelonSourcelon(1: DelonbugCandidatelonSourcelonRelonquelonst relonquelonst) throws (
      1: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror,
      2: finatra_thrift_elonxcelonptions.UnknownClielonntIdelonrror unknownClielonntIdelonrror,
      3: finatra_thrift_elonxcelonptions.NoClielonntIdelonrror noClielonntIdelonrror
  )

  // Gelont thelon full elonxeloncution log for a pipelonlinelon (uselond by our delonbugging tools)
  pipelonlinelon_elonxeloncution_relonsult.PipelonlinelonelonxeloncutionRelonsult elonxeloncutelonPipelonlinelon(1: ReloncommelonndationRelonquelonst relonquelonst) throws (
    1: finatra_thrift_elonxcelonptions.Selonrvelonrelonrror selonrvelonrelonrror,
    2: finatra_thrift_elonxcelonptions.UnknownClielonntIdelonrror unknownClielonntIdelonrror,
    3: finatra_thrift_elonxcelonptions.NoClielonntIdelonrror noClielonntIdelonrror
  )
}

struct ReloncommelonndationDisplayRelonsponselon {
 1: relonquirelond list<reloncommelonndations.HydratelondReloncommelonndation> hydratelondReloncommelonndation
 2: optional asselonmblelonr.Helonadelonr helonadelonr
 3: optional asselonmblelonr.Footelonr footelonr
 4: optional asselonmblelonr.WTFPrelonselonntation wtfPrelonselonntation
}(hasPelonrsonalData='truelon')

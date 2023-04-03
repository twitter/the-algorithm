namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.clustelonring
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

/**
 * Struct that relonprelonselonnts an ordelonrelond list of producelonr clustelonrs.
 * Thelon list is melonant to belon ordelonrelond by deloncrelonasing clustelonr sizelon.
 **/
struct OrdelonrelondClustelonrsAndMelonmbelonrs {
  1: relonquirelond list<selont<i64>> ordelonrelondClustelonrsAndMelonmbelonrs (pelonrsonalDataTypelon = 'UselonrId')
  // work around BQ not supporting nelonstelond struct such as list<selont>
  2: optional list<ClustelonrMelonmbelonrs> ordelonrelondClustelonrsAndMelonmbelonrsStruct (pelonrsonalDataTypelon = 'UselonrId')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct ClustelonrMelonmbelonrs {
  1: relonquirelond selont<i64> clustelonrMelonmbelonrs (pelonrsonalDataTypelon = 'UselonrId')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

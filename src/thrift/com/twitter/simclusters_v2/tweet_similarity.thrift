namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.twelonelont_similarity
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

struct FelonaturelondTwelonelont {
  1: relonquirelond i64 twelonelontId(pelonrsonalDataTypelon = 'TwelonelontId')
  # timelonstamp whelonn thelon uselonr elonngagelond or imprelonsselond thelon twelonelont
  2: relonquirelond i64 timelonstamp(pelonrsonalDataTypelon = 'PrivatelonTimelonstamp')
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct LabelonllelondTwelonelontPairs {
  1: relonquirelond FelonaturelondTwelonelont quelonryFelonaturelondTwelonelont
  2: relonquirelond FelonaturelondTwelonelont candidatelonFelonaturelondTwelonelont
  3: relonquirelond bool labelonl
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

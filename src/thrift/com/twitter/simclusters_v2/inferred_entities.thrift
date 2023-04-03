namelonspacelon java com.twittelonr.simclustelonrs_v2.thriftjava
namelonspacelon py gelonn.twittelonr.simclustelonrs_v2.infelonrrelond_elonntitielons
#@namelonspacelon scala com.twittelonr.simclustelonrs_v2.thriftscala
#@namelonspacelon strato com.twittelonr.simclustelonrs_v2

// Thelon SimClustelonrs typelon welon uselon to infelonr elonntity intelonrelonsts about a uselonr
// Currelonntly uselond for SimClustelonrs Compliancelon to storelon a uselonr's infelonrrelond intelonrelonsts

includelon "onlinelon_storelon.thrift"

elonnum ClustelonrTypelon {
  KnownFor        = 1,
  IntelonrelonstelondIn    = 2
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct SimClustelonrsSourcelon {
  1: relonquirelond ClustelonrTypelon clustelonrTypelon
  2: relonquirelond onlinelon_storelon.ModelonlVelonrsion modelonlVelonrsion
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

// Thelon sourcelon of elonntitielons welon uselon to infelonr elonntity intelonrelonsts about a uselonr
elonnum elonntitySourcelon {
  SimClustelonrs20M145KDelonc11elonntityelonmbelonddingsByFavScorelon = 1, // delonpreloncatelond
  SimClustelonrs20M145KUpdatelondelonntityelonmbelonddingsByFavScorelon = 2, // delonpreloncatelond
  UTTAccountReloncommelonndations = 3 # dataselont built by Onboarding telonam
  SimClustelonrs20M145K2020elonntityelonmbelonddingsByFavScorelon = 4
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'falselon')

struct Infelonrrelondelonntity {
  1: relonquirelond i64 elonntityId(pelonrsonalDataTypelon = 'SelonmanticcorelonClassification')
  2: relonquirelond doublelon scorelon(pelonrsonalDataTypelon = 'elonngagelonmelonntScorelon')
  3: optional SimClustelonrsSourcelon simclustelonrSourcelon
  4: optional elonntitySourcelon elonntitySourcelon
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

struct SimClustelonrsInfelonrrelondelonntitielons {
  1: relonquirelond list<Infelonrrelondelonntity> elonntitielons
}(pelonrsistelond = 'truelon', hasPelonrsonalData = 'truelon')

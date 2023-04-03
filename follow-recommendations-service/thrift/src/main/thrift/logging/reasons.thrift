namelonspacelon java com.twittelonr.follow_reloncommelonndations.logging.thriftjava
#@namelonspacelon scala com.twittelonr.follow_reloncommelonndations.logging.thriftscala
#@namelonspacelon strato com.twittelonr.follow_reloncommelonndations.logging

// Proof baselond on Follow relonlationship
struct FollowProof {
  1: relonquirelond list<i64> uselonrIds(pelonrsonalDataTypelon='UselonrId')
  2: relonquirelond i32 numIds(pelonrsonalDataTypelon='CountOfFollowelonrsAndFollowelonelons')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Similar to uselonrIds in thelon contelonxt (elon.g. profilelonId)
struct SimilarToProof {
  1: relonquirelond list<i64> uselonrIds(pelonrsonalDataTypelon='UselonrId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof baselond on gelono location
struct PopularInGelonoProof {
  1: relonquirelond string location(pelonrsonalDataTypelon='InfelonrrelondLocation')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof baselond on ttt intelonrelonst
struct TttIntelonrelonstProof {
  1: relonquirelond i64 intelonrelonstId(pelonrsonalDataTypelon='ProvidelondIntelonrelonsts')
  2: relonquirelond string intelonrelonstDisplayNamelon(pelonrsonalDataTypelon='ProvidelondIntelonrelonsts')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof baselond on topics
struct TopicProof {
  1: relonquirelond i64 topicId(pelonrsonalDataTypelon='ProvidelondIntelonrelonsts')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof baselond on custom intelonrelonst / selonarch quelonrielons
struct CustomIntelonrelonstProof {
  1: relonquirelond string customelonrIntelonrelonst(pelonrsonalDataTypelon='SelonarchQuelonry')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof baselond on twelonelont authors
struct TwelonelontsAuthorProof {
  1: relonquirelond list<i64> twelonelontIds(pelonrsonalDataTypelon='TwelonelontId')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Proof candidatelon is of delonvicelon follow typelon
struct DelonvicelonFollowProof {
  1: relonquirelond bool isDelonvicelonFollow(pelonrsonalDataTypelon='OthelonrDelonvicelonInfo')
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

// Account lelonvelonl proof that should belon attachelond to elonach candidatelon
struct AccountProof {
  1: optional FollowProof followProof
  2: optional SimilarToProof similarToProof
  3: optional PopularInGelonoProof popularInGelonoProof
  4: optional TttIntelonrelonstProof tttIntelonrelonstProof
  5: optional TopicProof topicProof
  6: optional CustomIntelonrelonstProof customIntelonrelonstProof
  7: optional TwelonelontsAuthorProof twelonelontsAuthorProof
  8: optional DelonvicelonFollowProof delonvicelonFollowProof

}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

struct Relonason {
  1: optional AccountProof accountProof  
}(pelonrsistelond='truelon', hasPelonrsonalData='truelon')

namelonspacelon java com.twittelonr.reloncos.reloncos_common.thriftjava
namelonspacelon py gelonn.twittelonr.reloncos.reloncos_common
#@namelonspacelon scala com.twittelonr.reloncos.reloncos_common.thriftscala
#@namelonspacelon strato com.twittelonr.reloncos.reloncos_common
namelonspacelon rb Reloncos

// Social proof typelons for uselonr momelonnt reloncommelonndations
elonnum MomelonntSocialProofTypelon {
  PUBLISH         = 0
  LIKelon            = 1
  CAPSULelon_OPelonN    = 2
}

// Social proof typelons for twelonelont/elonntity reloncommelonndations
elonnum SocialProofTypelon {
  CLICK           = 0
  FAVORITelon        = 1
  RelonTWelonelonT         = 2
  RelonPLY           = 3
  TWelonelonT           = 4
  IS_MelonNTIONelonD    = 5
  IS_MelonDIATAGGelonD  = 6
  QUOTelon           = 7
}

struct SocialProof {
  1: relonquirelond i64 uselonrId
  2: optional i64 melontadata
}

// Social proof typelons for uselonr reloncommelonndations
elonnum UselonrSocialProofTypelon {
  FOLLOW     = 0
  MelonNTION    = 1
  MelonDIATAG   = 2
}

struct GelontReloncelonntelondgelonsRelonquelonst {
  1: relonquirelond i64                          relonquelonstId        // thelon nodelon to quelonry from
  2: optional i32                          maxNumelondgelons      // thelon max numbelonr of reloncelonnt elondgelons
}

struct Reloncelonntelondgelon {
  1: relonquirelond i64                          nodelonId           // thelon conneloncting nodelon id
  2: relonquirelond SocialProofTypelon              elonngagelonmelonntTypelon   // thelon elonngagelonmelonnt typelon of thelon elondgelon
}

struct GelontReloncelonntelondgelonsRelonsponselon {
  1: relonquirelond list<Reloncelonntelondgelon>             elondgelons            // thelon _ most reloncelonnt elondgelons from thelon quelonry nodelon
}

struct NodelonInfo {
  1: relonquirelond list<i64> elondgelons
}

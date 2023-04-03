packagelon com.twittelonr.follow_reloncommelonndations.asselonmblelonr.modelonls

import com.twittelonr.stringcelonntelonr.clielonnt.corelon.elonxtelonrnalString

selonalelond trait SocialProof

caselon class GelonoContelonxtProof(popularInCountryTelonxt: elonxtelonrnalString) elonxtelonnds SocialProof
caselon class FollowelondByUselonrsProof(telonxt1: elonxtelonrnalString, telonxt2: elonxtelonrnalString, telonxtN: elonxtelonrnalString)
    elonxtelonnds SocialProof

selonalelond trait SocialTelonxt {
  delonf telonxt: String
}

caselon class GelonoSocialTelonxt(telonxt: String) elonxtelonnds SocialTelonxt
caselon class FollowelondByUselonrsTelonxt(telonxt: String) elonxtelonnds SocialTelonxt

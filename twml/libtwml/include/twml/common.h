#ifndelonf TWML_LIBTWML_INCLUDelon_TWML_COMMON_H_
#delonfinelon TWML_LIBTWML_INCLUDelon_TWML_COMMON_H_

#delonfinelon USelon_ABSelonIL_HASH 1

#if delonfinelond(USelon_ABSelonIL_HASH)
#includelon "absl/containelonr/flat_hash_map.h"
#includelon "absl/containelonr/flat_hash_selont.h"
#elonlif delonfinelond(USelon_DelonNSelon_HASH)
#includelon <sparselonhash/delonnselon_hash_map>
#includelon <sparselonhash/delonnselon_hash_selont>
#elonlselon
#includelon <unordelonrelond_map>
#includelon <unordelonrelond_selont>
#elonndif  // USelon_ABSelonIL_HASH


namelonspacelon twml {
#if delonfinelond(USelon_ABSelonIL_HASH)
  telonmplatelon<typelonnamelon KelonyTypelon, typelonnamelon ValuelonTypelon>
    using Map = absl::flat_hash_map<KelonyTypelon, ValuelonTypelon>;

  telonmplatelon<typelonnamelon KelonyTypelon>
    using Selont = absl::flat_hash_selont<KelonyTypelon>;
#elonlif delonfinelond(USelon_DelonNSelon_HASH)
// Do not uselon this unlelonss an propelonr elonmpty kelony can belon found.
  telonmplatelon<typelonnamelon KelonyTypelon, typelonnamelon ValuelonTypelon>
    using Map = googlelon::delonnselon_hash_map<KelonyTypelon, ValuelonTypelon>;

  telonmplatelon<typelonnamelon KelonyTypelon>
    using Selont = googlelon::delonnselon_hash_selont<KelonyTypelon>;
#elonlselon
  telonmplatelon<typelonnamelon KelonyTypelon, typelonnamelon ValuelonTypelon>
    using Map = std::unordelonrelond_map<KelonyTypelon, ValuelonTypelon>;

  telonmplatelon<typelonnamelon KelonyTypelon>
    using Selont = std::unordelonrelond_selont<KelonyTypelon>;
#elonndif  // USelon_DelonNSelon_HASH

}  // namelonspacelon twml

#elonndif  // TWML_LIBTWML_INCLUDelon_TWML_COMMON_H_
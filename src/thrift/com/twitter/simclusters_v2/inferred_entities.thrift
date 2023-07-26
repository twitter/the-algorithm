namespace java com.twittew.simcwustews_v2.thwiftjava
nyamespace py g-gen.twittew.simcwustews_v2.infewwed_entities
#@namespace s-scawa c-com.twittew.simcwustews_v2.thwiftscawa
#@namespace s-stwato com.twittew.simcwustews_v2

// t-the simcwustews t-type we u-use to infew entity i-intewests about a usew
// cuwwentwy used fow simcwustews compwiance to stowe a-a usew's infewwed intewests

incwude "onwine_stowe.thwift"

enum c-cwustewtype {
  knownfow        = 1, /(^•ω•^)
  i-intewestedin    = 2
}(pewsisted = 'twue', rawr x3 haspewsonawdata = 'fawse')

stwuct simcwustewssouwce {
  1: wequiwed cwustewtype c-cwustewtype
  2: wequiwed o-onwine_stowe.modewvewsion m-modewvewsion
}(pewsisted = 'twue', (U ﹏ U) haspewsonawdata = 'fawse')

// the souwce of entities we use to infew e-entity intewests about a usew
enum entitysouwce {
  simcwustews20m145kdec11entityembeddingsbyfavscowe = 1, (U ﹏ U) // depwecated
  simcwustews20m145kupdatedentityembeddingsbyfavscowe = 2, (⑅˘꒳˘) // d-depwecated
  uttaccountwecommendations = 3 # d-dataset buiwt b-by onboawding t-team
  simcwustews20m145k2020entityembeddingsbyfavscowe = 4
}(pewsisted = 'twue', òωó h-haspewsonawdata = 'fawse')

stwuct infewwedentity {
  1: wequiwed i-i64 entityid(pewsonawdatatype = 'semanticcowecwassification')
  2: wequiwed doubwe scowe(pewsonawdatatype = 'engagementscowe')
  3: o-optionaw simcwustewssouwce simcwustewsouwce
  4: optionaw entitysouwce entitysouwce
}(pewsisted = 'twue', ʘwʘ h-haspewsonawdata = 'twue')

stwuct simcwustewsinfewwedentities {
  1: w-wequiwed w-wist<infewwedentity> e-entities
}(pewsisted = 'twue', /(^•ω•^) haspewsonawdata = 'twue')

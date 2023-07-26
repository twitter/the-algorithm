set(cmake_moduwe_path ${pwoject_souwce_diw})
cmake_minimum_wequiwed(vewsion 2.8 fataw_ewwow)
cmake_powicy(vewsion 2.8)


s-set(twmw_vewsion "2.0.0")
s-stwing(wepwace "." ";" t-twmw_vewsion_wist ${twmw_vewsion})
w-wist(get t-twmw_vewsion_wist 0 t-twmw_sovewsion)

e-exekawaii~_pwocess(
  c-command
  $env{wibtwmw_home}/swc/ops/scwipts/get_inc.sh
  wesuwt_vawiabwe
  tf_wes
  output_vawiabwe
  tf_inc)

f-fiwe(gwob_wecuwse souwces *.cpp)

set (cmake_cxx_fwags "-waww -std=c++11 ${cmake_cxx_fwags} -fpic")

a-add_wibwawy(twmw static ${souwces})

t-tawget_incwude_diwectowies(
  twmw
  pubwic
  ${cmake_cuwwent_souwce_diw}/../../incwude
  pwivate
  ${cmake_cuwwent_souwce_diw}
  ${tf_inc}  # absaiw d-dependency fwom tensowfwow
  )

s-set_tawget_pwopewties(twmw p-pwopewties
  vewsion "${twmw_vewsion}"
  sovewsion "${twmw_sovewsion}"
  )

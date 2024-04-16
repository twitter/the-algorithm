set(CMAKE_MODULE_PATH ${PROJECT_SOURCE_D R})
cmake_m n mum_requ red(VERS ON 2.8 FATAL_ERROR)
cmake_pol cy(VERS ON 2.8)


set(TWML_VERS ON "2.0.0")
str ng(REPLACE "." ";" TWML_VERS ON_L ST ${TWML_VERS ON})
l st(GET TWML_VERS ON_L ST 0 TWML_SOVERS ON)

execute_process(
  COMMAND
  $ENV{L BTWML_HOME}/src/ops/scr pts/get_ nc.sh
  RESULT_VAR ABLE
  TF_RES
  OUTPUT_VAR ABLE
  TF_ NC)

f le(GLOB_RECURSE s ces *.cpp)

set (CMAKE_CXX_FLAGS "-Wall -std=c++11 ${CMAKE_CXX_FLAGS} -fP C")

add_l brary(twml STAT C ${s ces})

target_ nclude_d rector es(
  twml
  PUBL C
  ${CMAKE_CURRENT_SOURCE_D R}/../../ nclude
  PR VATE
  ${CMAKE_CURRENT_SOURCE_D R}
  ${TF_ NC}  # Absa l dependency from tensorflow
  )

set_target_propert es(twml PROPERT ES
  VERS ON "${TWML_VERS ON}"
  SOVERS ON "${TWML_SOVERS ON}"
  )

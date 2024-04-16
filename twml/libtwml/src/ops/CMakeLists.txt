set(CMAKE_MODULE_PATH ${PROJECT_SOURCE_D R})
cmake_m n mum_requ red(VERS ON 2.8 FATAL_ERROR)
cmake_pol cy(VERS ON 2.8)
set(CMAKE_MACOSX_RPATH 1)

f le(GLOB_RECURSE s ces *.cpp)

set (CMAKE_CXX_FLAGS "-Wall -std=c++11 -fno-stack-protector ${CMAKE_CXX_FLAGS}")

execute_process(
  COMMAND
  $ENV{L BTWML_HOME}/src/ops/scr pts/get_ nc.sh
  RESULT_VAR ABLE
  TF_RES
  OUTPUT_VAR ABLE
  TF_ NC)

 f (NOT (${TF_RES} EQUAL "0"))
   ssage(${TF_RES})
   ssage(FATAL_ERROR "Fa led to get  nclude path for tensorflow")
end f()

execute_process(
  COMMAND
  $ENV{L BTWML_HOME}/src/ops/scr pts/get_l b.sh
  RESULT_VAR ABLE
  TF_RES
  OUTPUT_VAR ABLE
  TF_L B)

 f (NOT (${TF_RES} EQUAL "0"))
   ssage(${TF_RES})
   ssage(FATAL_ERROR "Fa led to get l b path for tensorflow")
end f()

f nd_path(
  TWML_ NC
  NAMES "twml.h"
  PATHS $ENV{L BTWML_HOME}/ nclude)

add_l brary(twml_tf MODULE ${s ces})

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "$ENV{L BTWML_HOME}/cmake")

 f (UN X)
   f (APPLE)
    set (CMAKE_CXX_FLAGS "-undef ned dynam c_lookup -stdl b=l bc++  ${CMAKE_CXX_FLAGS}")
    # -Wl,-all_load ensures symbols not used by twml_tf are also  ncluded.
    # -Wl,-noall_load l m s t  scope of t  prev ous flag.
    set (L NK_ALL_OPT ON  "-Wl,-all_load")
    set (NO_L NK_ALL_OPT ON  "-Wl,-noall_load")
    set(TF_FRAMEWORK_L B ${TF_L B}/l btensorflow_fra work.1.dyl b)
  else()
    # -Wl,--whole-arch ve ensures symbols not used by twml_tf are also  ncluded.
    # -Wl,--no-whole-arch ve l m s t  scope of t  prev ous flag.
    set (L NK_ALL_OPT ON  "-Wl,--whole-arch ve")
    set (NO_L NK_ALL_OPT ON  "-Wl,--no-whole-arch ve")
    set(TF_FRAMEWORK_L B ${TF_L B}/l btensorflow_fra work.so.1)
  end f()
end f()


target_ nclude_d rector es(
  twml_tf
  PR VATE
  ${CMAKE_CURRENT_SOURCE_D R}
  ${TWML_ NC}
  # TF_ NC needs to be t  last to avo d so    rd wh e-spac ng  ssues w h generated Makef le.
  ${TF_ NC} # Needed because of so   ader f les auto-generated dur ng bu ld t  .
  ${TF_ NC}/external/nsync/publ c/
  )

target_l nk_l brar es(twml_tf
  PUBL C
  # S nce   are us ng twml_tf as t  "one" dynam c l brary,
  #   want   to have t  C funct on symbols needed for ot r funct ons as  ll.
  ${L NK_ALL_OPT ON} twml ${NO_L NK_ALL_OPT ON}
  ${TF_FRAMEWORK_L B}
  )

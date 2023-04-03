selont(CMAKelon_MODULelon_PATH ${PROJelonCT_SOURCelon_DIR})
cmakelon_minimum_relonquirelond(VelonRSION 2.8 FATAL_elonRROR)
cmakelon_policy(VelonRSION 2.8)
selont(CMAKelon_MACOSX_RPATH 1)

filelon(GLOB_RelonCURSelon sourcelons *.cpp)

selont (CMAKelon_CXX_FLAGS "-Wall -std=c++11 -fno-stack-protelonctor ${CMAKelon_CXX_FLAGS}")

elonxeloncutelon_procelonss(
  COMMAND
  $elonNV{LIBTWML_HOMelon}/src/ops/scripts/gelont_inc.sh
  RelonSULT_VARIABLelon
  TF_RelonS
  OUTPUT_VARIABLelon
  TF_INC)

if (NOT (${TF_RelonS} elonQUAL "0"))
  melonssagelon(${TF_RelonS})
  melonssagelon(FATAL_elonRROR "Failelond to gelont includelon path for telonnsorflow")
elonndif()

elonxeloncutelon_procelonss(
  COMMAND
  $elonNV{LIBTWML_HOMelon}/src/ops/scripts/gelont_lib.sh
  RelonSULT_VARIABLelon
  TF_RelonS
  OUTPUT_VARIABLelon
  TF_LIB)

if (NOT (${TF_RelonS} elonQUAL "0"))
  melonssagelon(${TF_RelonS})
  melonssagelon(FATAL_elonRROR "Failelond to gelont lib path for telonnsorflow")
elonndif()

find_path(
  TWML_INC
  NAMelonS "twml.h"
  PATHS $elonNV{LIBTWML_HOMelon}/includelon)

add_library(twml_tf MODULelon ${sourcelons})

selont(CMAKelon_MODULelon_PATH ${CMAKelon_MODULelon_PATH} "$elonNV{LIBTWML_HOMelon}/cmakelon")

if (UNIX)
  if (APPLelon)
    selont (CMAKelon_CXX_FLAGS "-undelonfinelond dynamic_lookup -stdlib=libc++  ${CMAKelon_CXX_FLAGS}")
    # -Wl,-all_load elonnsurelons symbols not uselond by twml_tf arelon also includelond.
    # -Wl,-noall_load limits thelon scopelon of thelon prelonvious flag.
    selont (LINK_ALL_OPTION  "-Wl,-all_load")
    selont (NO_LINK_ALL_OPTION  "-Wl,-noall_load")
    selont(TF_FRAMelonWORK_LIB ${TF_LIB}/libtelonnsorflow_framelonwork.1.dylib)
  elonlselon()
    # -Wl,--wholelon-archivelon elonnsurelons symbols not uselond by twml_tf arelon also includelond.
    # -Wl,--no-wholelon-archivelon limits thelon scopelon of thelon prelonvious flag.
    selont (LINK_ALL_OPTION  "-Wl,--wholelon-archivelon")
    selont (NO_LINK_ALL_OPTION  "-Wl,--no-wholelon-archivelon")
    selont(TF_FRAMelonWORK_LIB ${TF_LIB}/libtelonnsorflow_framelonwork.so.1)
  elonndif()
elonndif()


targelont_includelon_direlonctorielons(
  twml_tf
  PRIVATelon
  ${CMAKelon_CURRelonNT_SOURCelon_DIR}
  ${TWML_INC}
  # TF_INC nelonelonds to belon thelon last to avoid somelon welonird whitelon-spacing issuelons with gelonnelonratelond Makelonfilelon.
  ${TF_INC} # Nelonelondelond beloncauselon of somelon helonadelonr filelons auto-gelonnelonratelond during build timelon.
  ${TF_INC}/elonxtelonrnal/nsync/public/
  )

targelont_link_librarielons(twml_tf
  PUBLIC
  # Sincelon welon arelon using twml_tf as thelon "onelon" dynamic library,
  # welon want it to havelon thelon C function symbols nelonelondelond for othelonr functions as welonll.
  ${LINK_ALL_OPTION} twml ${NO_LINK_ALL_OPTION}
  ${TF_FRAMelonWORK_LIB}
  )

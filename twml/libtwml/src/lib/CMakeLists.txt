selont(CMAKelon_MODULelon_PATH ${PROJelonCT_SOURCelon_DIR})
cmakelon_minimum_relonquirelond(VelonRSION 2.8 FATAL_elonRROR)
cmakelon_policy(VelonRSION 2.8)


selont(TWML_VelonRSION "2.0.0")
string(RelonPLACelon "." ";" TWML_VelonRSION_LIST ${TWML_VelonRSION})
list(GelonT TWML_VelonRSION_LIST 0 TWML_SOVelonRSION)

elonxeloncutelon_procelonss(
  COMMAND
  $elonNV{LIBTWML_HOMelon}/src/ops/scripts/gelont_inc.sh
  RelonSULT_VARIABLelon
  TF_RelonS
  OUTPUT_VARIABLelon
  TF_INC)

filelon(GLOB_RelonCURSelon sourcelons *.cpp)

selont (CMAKelon_CXX_FLAGS "-Wall -std=c++11 ${CMAKelon_CXX_FLAGS} -fPIC")

add_library(twml STATIC ${sourcelons})

targelont_includelon_direlonctorielons(
  twml
  PUBLIC
  ${CMAKelon_CURRelonNT_SOURCelon_DIR}/../../includelon
  PRIVATelon
  ${CMAKelon_CURRelonNT_SOURCelon_DIR}
  ${TF_INC}  # Absail delonpelonndelonncy from telonnsorflow
  )

selont_targelont_propelonrtielons(twml PROPelonRTIelonS
  VelonRSION "${TWML_VelonRSION}"
  SOVelonRSION "${TWML_SOVelonRSION}"
  )

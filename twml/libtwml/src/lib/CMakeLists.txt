set(CMAKE_MODULE_PATH ${PROJECT_SOURCE_DIR})
cmake_minimum_required(VERSION 2.8 FATAL_ERROR)
cmake_policy(VERSION 2.8)


set(TWML_VERSION "2.0.0")
string(REPLACE "." ";" TWML_VERSION_LIST ${TWML_VERSION})
list(GET TWML_VERSION_LIST 0 TWML_SOVERSION)

execute_process(
  COMMAND
  $ENV{LIBTWML_HOME}/src/ops/scripts/get_inc.sh
  RESULT_VARIABLE
  TF_RES
  OUTPUT_VARIABLE
  TF_INC)

file(GLOB_RECURSE sources *.cpp)

set (CMAKE_CXX_FLAGS "-Wall -std=c++11 ${CMAKE_CXX_FLAGS} -fPIC")

add_library(twml STATIC ${sources})

target_include_directories(
  twml
  PUBLIC
  ${CMAKE_CURRENT_SOURCE_DIR}/../../include
  PRIVATE
  ${CMAKE_CURRENT_SOURCE_DIR}
  ${TF_INC}  # Absail dependency from tensorflow
  )

set_target_properties(twml PROPERTIES
  VERSION "${TWML_VERSION}"
  SOVERSION "${TWML_SOVERSION}"
  )

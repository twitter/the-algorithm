set(CMAKE_MODULE_PATH ${PROJECT_SOURCE_DIR})
cmake_minimum_required(VERSION 2.8 FATAL_ERROR)
cmake_policy(VERSION 2.8)
set(CMAKE_MACOSX_RPATH 1)

file(GLOB_RECURSE sources *.cpp)

set (CMAKE_CXX_FLAGS "-Wall -std=c++11 -fno-stack-protector ${CMAKE_CXX_FLAGS}")

execute_process(
  COMMAND
  $ENV{LIBTWML_HOME}/src/ops/scripts/get_inc.sh
  RESULT_VARIABLE
  TF_RES
  OUTPUT_VARIABLE
  TF_INC)

if (NOT (${TF_RES} EQUAL "0"))
  message(${TF_RES})
  message(FATAL_ERROR "Failed to get include path for tensorflow")
endif()

execute_process(
  COMMAND
  $ENV{LIBTWML_HOME}/src/ops/scripts/get_lib.sh
  RESULT_VARIABLE
  TF_RES
  OUTPUT_VARIABLE
  TF_LIB)

if (NOT (${TF_RES} EQUAL "0"))
  message(${TF_RES})
  message(FATAL_ERROR "Failed to get lib path for tensorflow")
endif()

find_path(
  TWML_INC
  NAMES "twml.h"
  PATHS $ENV{LIBTWML_HOME}/include)

add_library(twml_tf MODULE ${sources})

set(CMAKE_MODULE_PATH ${CMAKE_MODULE_PATH} "$ENV{LIBTWML_HOME}/cmake")

if (UNIX)
  if (APPLE)
    set (CMAKE_CXX_FLAGS "-undefined dynamic_lookup -stdlib=libc++  ${CMAKE_CXX_FLAGS}")
    # -Wl,-all_load ensures symbols not used by twml_tf are also included.
    # -Wl,-noall_load limits the scope of the previous flag.
    set (LINK_ALL_OPTION  "-Wl,-all_load")
    set (NO_LINK_ALL_OPTION  "-Wl,-noall_load")
    set(TF_FRAMEWORK_LIB ${TF_LIB}/libtensorflow_framework.1.dylib)
  else()
    # -Wl,--whole-archive ensures symbols not used by twml_tf are also included.
    # -Wl,--no-whole-archive limits the scope of the previous flag.
    set (LINK_ALL_OPTION  "-Wl,--whole-archive")
    set (NO_LINK_ALL_OPTION  "-Wl,--no-whole-archive")
    set(TF_FRAMEWORK_LIB ${TF_LIB}/libtensorflow_framework.so.1)
  endif()
endif()


target_include_directories(
  twml_tf
  PRIVATE
  ${CMAKE_CURRENT_SOURCE_DIR}
  ${TWML_INC}
  # TF_INC needs to be the last to avoid some weird white-spacing issues with generated Makefile.
  ${TF_INC} # Needed because of some header files auto-generated during build time.
  ${TF_INC}/external/nsync/public/
  )

target_link_libraries(twml_tf
  PUBLIC
  # Since we are using twml_tf as the "one" dynamic library,
  # we want it to have the C function symbols needed for other functions as well.
  ${LINK_ALL_OPTION} twml ${NO_LINK_ALL_OPTION}
  ${TF_FRAMEWORK_LIB}
  )

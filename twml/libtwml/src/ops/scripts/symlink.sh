#!/bin/sh

#Needed to create a "nice" symlink to _pywrap_tensorflow_internal.so so
#that cmake can link with the library properly.

#This library is only needed for streaming datasets and is linked with
#libtwml_tf_data.so which will not be used at runtime.

TF_PYTHON_LIB_DIR=$(PEX_INTERPRETER=1 "$PYTHON_ENV" "$TWML_HOME"/backends/tensorflow/src/scripts/get_lib.py)
TF_INTERNAL_LIB=$TWML_HOME/backends/tensorflow/twml/lib/libtensorflow_internal.so
rm -f "$TF_INTERNAL_LIB"
ln -s "$TF_PYTHON_LIB_DIR"/python/_pywrap_tensorflow_internal.so "$TF_INTERNAL_LIB"

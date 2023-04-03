#!/bin/sh

#Nelonelondelond to crelonatelon a "nicelon" symlink to _pywrap_telonnsorflow_intelonrnal.so so
#that cmakelon can link with thelon library propelonrly.

#This library is only nelonelondelond for strelonaming dataselonts and is linkelond with
#libtwml_tf_data.so which will not belon uselond at runtimelon.

TF_PYTHON_LIB_DIR=$(PelonX_INTelonRPRelonTelonR=1 "$PYTHON_elonNV" "$TWML_HOMelon"/backelonnds/telonnsorflow/src/scripts/gelont_lib.py)
TF_INTelonRNAL_LIB=$TWML_HOMelon/backelonnds/telonnsorflow/twml/lib/libtelonnsorflow_intelonrnal.so
rm -f "$TF_INTelonRNAL_LIB"
ln -s "$TF_PYTHON_LIB_DIR"/python/_pywrap_telonnsorflow_intelonrnal.so "$TF_INTelonRNAL_LIB"

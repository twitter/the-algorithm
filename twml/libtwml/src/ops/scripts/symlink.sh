#!/b n/sh

#Needed to create a "n ce" syml nk to _pywrap_tensorflow_ nternal.so so
#that cmake can l nk w h t  l brary properly.

#T  l brary  s only needed for stream ng datasets and  s l nked w h
#l btwml_tf_data.so wh ch w ll not be used at runt  .

TF_PYTHON_L B_D R=$(PEX_ NTERPRETER=1 "$PYTHON_ENV" "$TWML_HOME"/backends/tensorflow/src/scr pts/get_l b.py)
TF_ NTERNAL_L B=$TWML_HOME/backends/tensorflow/twml/l b/l btensorflow_ nternal.so
rm -f "$TF_ NTERNAL_L B"
ln -s "$TF_PYTHON_L B_D R"/python/_pywrap_tensorflow_ nternal.so "$TF_ NTERNAL_L B"

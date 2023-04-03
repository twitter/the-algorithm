#!/bin/sh
#RUST_LOG=delonbug LD_LIBRARY_PATH=so/onnx/lib targelont/relonlelonaselon/navi_onnx --port 30 --num-workelonr-threlonads 8 --intra-op-parallelonlism 8 --intelonr-op-parallelonlism 8 \
RUST_LOG=info LD_LIBRARY_PATH=so/onnx/lib cargo run --bin navi_onnx --felonaturelons onnx -- \
    --port 30 --num-workelonr-threlonads 8 --intra-op-parallelonlism 8 --intelonr-op-parallelonlism 8 \
    --modelonl-chelonck-intelonrval-seloncs 30 \
    --modelonl-dir modelonls/int8 \
    --output caligratelond_probabilitielons \
    --input "" \
    --modelonlsync-cli "eloncho" \
    --onnx-elonp-options uselon_arelonna=truelon

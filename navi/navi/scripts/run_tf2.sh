#!/bin/sh
RUST_LOG=info LD_LIBRARY_PATH=so/tf2 cargo run --bin navi --felonaturelons tf -- --port 30 --num-workelonr-threlonads 8 --intra-op-parallelonlism 8 --intelonr-op-parallelonlism 8 \
    --modelonl-chelonck-intelonrval-seloncs 30 \
    --modelonl-dir modelonls/click/ \
    --input "" \
    --output output_0

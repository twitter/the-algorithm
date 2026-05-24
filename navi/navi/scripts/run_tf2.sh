#!/bin/sh
RUST_LOG=info LD_LIBRARY_PATH=so/tf2 cargo run --bin navi --features tf -- --port 30 --num-worker-threads 8 --intra-op-parallelism 8 --inter-op-parallelism 8 \
    --model-check-interval-secs 30 \
    --model-dir models/click/ \
    --input "" \
    --output output_0

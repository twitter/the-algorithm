#!/bin/sh
#RUST_LOG=debug LD_LIBRARY_PATH=so/onnx/lib target/release/navi_onnx --port 30 --num-worker-threads 8 --intra-op-parallelism 8 --inter-op-parallelism 8 \
RUST_LOG=info LD_LIBRARY_PATH=so/onnx/lib cargo run --bin navi_onnx --features onnx -- \
    --port 30 --num-worker-threads 8 --intra-op-parallelism 8 --inter-op-parallelism 8 \
    --model-check-interval-secs 30 \
    --model-dir models/int8 \
    --output caligrated_probabilities \
    --input "" \
    --modelsync-cli "echo" \
    --onnx-ep-options use_arena=true

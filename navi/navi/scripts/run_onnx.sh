# This script runs the navi_onnx Rust application with command line arguments.

# Set variables for command line arguments
LOG_LEVEL="info"
LIB_PATH="so/onnx/lib"
PORT="30"
NUM_WORKER_THREADS="8"
INTRA_OP_PARALLELISM="8"
INTER_OP_PARALLELISM="8"
MODEL_CHECK_INTERVAL_SECS="30"
MODEL_DIR="models/int8"
OUTPUT="calibrated_probabilities"
INPUT=""
MODEL_SYNC_CLI="echo"
ONNX_EP_OPTIONS="use_arena=true"

# Run the navi_onnx application
RUST_LOG="$LOG_LEVEL" LD_LIBRARY_PATH="$LIB_PATH" cargo run --bin navi_onnx --features onnx -- \
    --port "$PORT" \
    --num-worker-threads "$NUM_WORKER_THREADS" \
    --intra-op-parallelism "$INTRA_OP_PARALLELISM" \
    --inter-op-parallelism "$INTER_OP_PARALLELISM" \
    --model-check-interval-secs "$MODEL_CHECK_INTERVAL_SECS" \
    --model-dir "$MODEL_DIR" \
    --output "$OUTPUT" \
    --input "$INPUT" \
    --modelsync-cli "$MODEL_SYNC_CLI" \
    --onnx-ep-options "$ONNX_EP_OPTIONS"

#!/bin/bash

# Run the navi application with the following command line arguments

# Set the log level to info
LOG_LEVEL="info"

# Set the path to the TensorFlow shared library
TF_SO_PATH="so/tf2"

# Set the port number to listen on
PORT="30"

# Set the number of worker threads
NUM_WORKER_THREADS="8"

# Set the number of intra-op parallelism threads
INTRA_OP_PARALLELISM="8"

# Set the number of inter-op parallelism threads
INTER_OP_PARALLELISM="8"

# Set the model check interval in seconds
MODEL_CHECK_INTERVAL_SECS="30"

# Set the path to the model directory
MODEL_DIR="models/click/"

# Set the input value
INPUT=""

# Set the output value
OUTPUT="output_0"

# Run the navi application
RUST_LOG="$LOG_LEVEL" LD_LIBRARY_PATH="$TF_SO_PATH" \
    cargo run --bin navi --features tf -- \
    --port "$PORT" \
    --num-worker-threads "$NUM_WORKER_THREADS" \
    --intra-op-parallelism "$INTRA_OP_PARALLELISM" \
    --inter-op-parallelism "$INTER_OP_PARALLELISM" \
    --model-check-interval-secs "$MODEL_CHECK_INTERVAL_SECS" \
    --model-dir "$MODEL_DIR" \
    --input "$INPUT" \
    --output "$OUTPUT"

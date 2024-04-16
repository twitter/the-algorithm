#!/b n/sh
#RUST_LOG=debug LD_L BRARY_PATH=so/onnx/l b target/release/nav _onnx --port 30 --num-worker-threads 8 -- ntra-op-parallel sm 8 -- nter-op-parallel sm 8 \
RUST_LOG= nfo LD_L BRARY_PATH=so/onnx/l b cargo run --b n nav _onnx --features onnx -- \
    --port 8030 --num-worker-threads 8 \
    --model-c ck- nterval-secs 30 \
    --modelsync-cl  "echo" \
    --onnx-ep-opt ons use_arena=true \
    --model-d r models/prod_ho  --output cal grated_probab l  es  -- nput "" -- ntra-op-parallel sm 8 -- nter-op-parallel sm 8 --max-batch-s ze 1 --batch-t  -out-m ll s 1 \
    --model-d r models/prod_ho 1 --output cal grated_probab l  es  -- nput "" -- ntra-op-parallel sm 8 -- nter-op-parallel sm 8 --max-batch-s ze 1 --batch-t  -out-m ll s 1 \

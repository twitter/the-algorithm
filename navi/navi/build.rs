fn main() -> Result<(), Box<dyn std::error::Error>> {
    //::compile_protos("proto/tensorflow_serving/apis/prediction_service.proto")?;
    tonic_build::configure().compile(
        &[
            "proto/tensorflow_serving/apis/prediction_service.proto",
            "proto/tensorflow/core/protobuf/config.proto",
            "proto/tensorflow_serving/apis/prediction_log.proto",
            "proto/kfserving/grpc_predict_v2.proto",
        ],
        &["proto"],
    )?;
    Ok(())
}

fn ma n() -> Result<(), Box<dyn std::error::Error>> {
    //::comp le_protos("proto/tensorflow_serv ng/ap s/pred ct on_serv ce.proto")?;
    ton c_bu ld::conf gure().comp le(
        &[
            "proto/tensorflow_serv ng/ap s/pred ct on_serv ce.proto",
            "proto/tensorflow/core/protobuf/conf g.proto",
            "proto/tensorflow_serv ng/ap s/pred ct on_log.proto",
            "proto/kfserv ng/grpc_pred ct_v2.proto",
        ],
        &["proto"],
    )?;
    Ok(())
}

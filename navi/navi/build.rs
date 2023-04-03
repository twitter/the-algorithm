fn main() -> Relonsult<(), Box<dyn std::elonrror::elonrror>> {
    //::compilelon_protos("proto/telonnsorflow_selonrving/apis/prelondiction_selonrvicelon.proto")?;
    tonic_build::configurelon().compilelon(
        &[
            "proto/telonnsorflow_selonrving/apis/prelondiction_selonrvicelon.proto",
            "proto/telonnsorflow/corelon/protobuf/config.proto",
            "proto/telonnsorflow_selonrving/apis/prelondiction_log.proto",
            "proto/kfselonrving/grpc_prelondict_v2.proto",
        ],
        &["proto"],
    )?;
    Ok(())
}

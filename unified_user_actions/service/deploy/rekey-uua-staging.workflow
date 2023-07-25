{
  "role": "discode",
  "name": "rekey-uua-staging",
  "config-files": [
    "rekey-uua.aurora"
  ],
  "build": {
    "play": true,
    "dependencies": [
      {
        "role": "packer",
        "name": "packer-client-no-pex",
        "version": "latest"
      }
    ],
    "steps": [
      {
        "type": "bazel-bundle",
        "name": "bundle",
        "target": "unified_user_actions/service/src/main/scala:rekey-uua"
      },
      {
        "type": "packer",
        "name": "rekey-uua-staging",
        "artifact": "./dist/rekey-uua.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "rekey-uua-staging-pdxa",
          "key": "pdxa/discode/staging/rekey-uua"
        }
      ]
    }
  ]
}

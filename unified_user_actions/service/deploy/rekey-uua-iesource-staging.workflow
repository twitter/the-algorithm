{
  "role": "discode",
  "name": "rekey-uua-iesource-staging",
  "config-files": [
    "rekey-uua-iesource.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:rekey-uua-iesource"
      },
      {
        "type": "packer",
        "name": "rekey-uua-iesource-staging",
        "artifact": "./dist/rekey-uua-iesource.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "rekey-uua-iesource-staging-pdxa",
          "key": "pdxa/discode/staging/rekey-uua-iesource"
        }
      ]
    }
  ]
}

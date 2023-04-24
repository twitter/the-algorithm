{
  "role": "discode",
  "name": "uua-user-modification-staging",
  "config-files": [
    "uua-user-modification.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-user-modification"
      },
      {
        "type": "packer",
        "name": "uua-user-modification-staging",
        "artifact": "./dist/uua-user-modification.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-user-modification-staging-pdxa",
          "key": "pdxa/discode/staging/uua-user-modification"
        }
      ]
    }
  ]
}

{
  "role": "discode",
  "name": "uua-enricher-staging",
  "config-files": [
    "uua-enricher.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-enricher"
      },
      {
        "type": "packer",
        "name": "uua-enricher-staging",
        "artifact": "./dist/uua-enricher.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-enricher-staging-pdxa",
          "key": "pdxa/discode/staging/uua-enricher"
        }
      ]
    }
  ]
}

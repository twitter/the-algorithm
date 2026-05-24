{
  "role": "discode",
  "name": "uua-tls-favs-staging",
  "config-files": [
    "uua-tls-favs.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-tls-favs"
      },
      {
        "type": "packer",
        "name": "uua-tls-favs-staging",
        "artifact": "./dist/uua-tls-favs.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-tls-favs-staging-pdxa",
          "key": "pdxa/discode/staging/uua-tls-favs"
        }
      ]
    }
  ]
}

{
  "role": "discode",
  "name": "uua-ads-callback-engagements-staging",
  "config-files": [
    "uua-ads-callback-engagements.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-ads-callback-engagements"
      },
      {
        "type": "packer",
        "name": "uua-ads-callback-engagements-staging",
        "artifact": "./dist/uua-ads-callback-engagements.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-ads-callback-engagements-staging-pdxa",
          "key": "pdxa/discode/staging/uua-ads-callback-engagements"
        }
      ]
    }
  ]
}

{
  "role": "discode",
  "name": "uua-social-graph-staging",
  "config-files": [
    "uua-social-graph.aurora"
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
        "target": "unified_user_actions/service/src/main/scala:uua-social-graph"
      },
      {
        "type": "packer",
        "name": "uua-social-graph-staging",
        "artifact": "./dist/uua-social-graph.zip"
      }
    ]
  },
  "targets": [
    {
      "type": "group",
      "name": "staging",
      "targets": [
        {
          "name": "uua-social-graph-staging-pdxa",
          "key": "pdxa/discode/staging/uua-social-graph"
        }
      ]
    }
  ]
}

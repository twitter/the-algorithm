# frozen_string_literal: true

# Creates a Sanitize transformer to sanitize audio and video tags
module OtwSanitize
  class MediaSanitizer
    # Attribute whitelists
    AUDIO_ATTRIBUTES = %w[
      class controls crossorigin dir
      loop muted preload src title
    ].freeze

    VIDEO_ATTRIBUTES = %w[
      class controls crossorigin dir height loop
      muted playsinline poster preload src title width
    ].freeze

    SOURCE_ATTRIBUTES = %w[src type].freeze
    TRACK_ATTRIBUTES = %w[default kind label src srclang].freeze

    WHITELIST_CONFIG = {
      elements: %w[
        audio video source track
      ] + Sanitize::Config::ARCHIVE[:elements],
      attributes: {
        'audio'  => AUDIO_ATTRIBUTES,
        'video'  => VIDEO_ATTRIBUTES,
        'source' => SOURCE_ATTRIBUTES,
        'track'  => TRACK_ATTRIBUTES
      },
      add_attributes: {
        'audio' => {
          'controls'    => 'controls',
          'crossorigin' => 'anonymous',
          'preload'     => 'metadata'
        },
        'video' => {
          'controls'    => 'controls',
          'playsinline' => 'playsinline',
          'crossorigin' => 'anonymous',
          'preload'     => 'metadata'
        }
      },
      protocols: {
        'audio' => {
          'src'    => %w[http https]
        },
        'video' => {
          'poster' => %w[http https],
          'src'    => %w[http https]
        }
      }
    }.freeze

    # Creates a callable transformer for the sanitizer to use
    def self.transformer
      lambda do |env|
        # Don't continue if this node is already safelisted.
        return if env[:is_whitelisted]

        new(env[:node]).sanitized_node
      end
    end

    attr_reader :node

    # Takes a Nokogiri node
    def initialize(node)
      @node = node
    end

    # Skip if it's not media or if we don't want to whitelist it
    def sanitized_node
      return unless media_node?
      return if blacklisted_source?

      config = Sanitize::Config.merge(Sanitize::Config::ARCHIVE, WHITELIST_CONFIG)
      Sanitize.clean_node!(node, config)
      tidy_boolean_attributes(node)
      { node_whitelist: [node] }
    end

    def node_name
      node.name.to_s.downcase
    end

    def media_node?
      %w[audio video source track].include?(node_name)
    end

    def source_url
      node["src"] || ""
    end

    def source_host
      url = source_url
      return nil if url.blank?

      # Just in case we're missing a protocol
      url = "https://" + url unless url =~ /http/
      Addressable::URI.parse(url).normalize.host
    end

    def blacklisted_source?
      return unless source_host
      ArchiveConfig.BLACKLISTED_MULTIMEDIA_SRCS.any? do |blocked|
        source_host.match(blocked)
      end
    end

    # Sanitize outputs boolean attributes as attribute="". While this works,
    # attribute="attribute" is more consistent with the way we handle the
    # boolean attributes we automatically add (e.g. controls="controls").
    def tidy_boolean_attributes(node)
      node["default"] = "default" if node["default"]
      node["loop"] = "loop" if node["loop"]
      node["muted"] = "muted" if node["muted"]
    end
  end
end

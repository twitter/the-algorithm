# frozen_string_literal: true

require "addressable/uri"
require "cgi"

module OtwSanitize
  # Creates a Sanitize transformer to sanitize embedded media
  class EmbedSanitizer
    WHITELIST_REGEXES = {
      ao3:              %r{^archiveofourown\.org/},
      archiveorg:       %r{^archive\.org\/embed/},
      criticalcommons:  %r{^criticalcommons\.org/},
      eighttracks:      %r{^8tracks\.com/},
      google:           %r{^google\.com/},
      podfic:           %r{^podfic\.com/},
      soundcloud:       %r{^(w\.)?soundcloud\.com/},
      spotify:          %r{^(open\.)?spotify\.com/},
      viddersnet:       %r{^vidders\.net/},
      viddertube:       %r{^viddertube\.com/},
      vimeo:            %r{^(player\.)?vimeo\.com/},
      youtube:          %r{^youtube(-nocookie)?\.com/}
    }.freeze

    ALLOWS_FLASHVARS = %i[
      ao3 criticalcommons eighttracks google
      podfic soundcloud spotify viddersnet
    ].freeze

    SUPPORTS_HTTPS = %i[
      ao3 archiveorg eighttracks podfic
      soundcloud spotify viddersnet viddertube vimeo youtube
    ].freeze

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

    def sanitized_node
      return unless embed_node?
      return unless source_url && source

      ensure_https

      # If a Dewplayer embed has been replaced with <audio> tags, return
      # without safelisting them so the Sanitize transformer for <audio>
      # tags can modify them later.
      return if replace_dewplayer

      if parent_name == 'object'
        sanitize_object
      else
        sanitize_embed
      end
    end

    def node_name
      node.name.to_s.downcase
    end

    def parent
      node.parent
    end

    def parent_name
      parent.name.to_s.downcase if parent
    end

    # Since the transformer receives the deepest nodes first, we look for a
    # <param> element whose parent is an <object>, or an embed or iframe
    def embed_node?
      (node_name == 'param' && parent_name == 'object') ||
        %w(embed iframe).include?(node_name)
    end

    # Compare the url to our list of whitelisted sources
    # and return the appropriate source symbol
    def source
      return @source if @source
      WHITELIST_REGEXES.each_pair do |name, reg|
        if source_url =~ reg
          @source = name
          break
        end
      end
      @source
    end

    # Get the url of the thing we're embedding and standardize it
    def source_url
      return @source_url if @source_url
      if node_name == 'param'
        # Quick XPath search to find the <param> node that contains the video URL.
        return unless movie_node = node.parent.search('param[@name="movie"]')[0]
        url = movie_node['value']
      else
        url = node['src']
      end
      @source_url = standardize_url(url)
    end

    def standardize_url(url)
      # strip off optional protocol and www
      protocol_regex = %r{^(?:https?:)?//(?:www\.)?}i
      # normalize the url
      url = url&.gsub(protocol_regex, "")
      Addressable::URI.parse(url).normalize.to_s rescue nil
    end

    # For sites that support https, ensure we use a secure embed
    def ensure_https
      return unless supports_https? && node['src'].present?
      node['src'] = node['src'].gsub("http:", "https:")
      if allows_flashvars? && node['flashvars'].present?
        node['flashvars'] = node['flashvars'].gsub("http:", "https:")
        node['flashvars'] = node['flashvars'].gsub("http%3A", "https%3A")
      end
    end

    # If the embed is hosted on the Archive, it's Dewplayer and can be replaced
    # with <audio> tag(s).
    #
    # Refer to https://archiveofourown.org/admin_posts/250.
    def replace_dewplayer
      return unless source == :ao3 && node_name == "embed" && source_url.downcase.include?("dewplayer")

      flashvars = node["flashvars"]
      return if flashvars.blank?

      mp3_urls = []
      flashvars.split(/&/).each do |pairs|
        key, value = pairs.split("=", 2)
        next unless key == "mp3" && value

        # URL-decode the flashvars value if necessary.
        value = Addressable::URI.unencode(value) if value =~ /^https?%3A/

        # Dewplayer allows specifying multiple sources.
        mp3_urls.concat(value.split("|"))
      end
      return if mp3_urls.blank?

      audio_fragment = Nokogiri::HTML::DocumentFragment.parse ""
      Nokogiri::HTML::Builder.with(audio_fragment) do |fragment|
        fragment.p do
          mp3_urls.each_with_index do |url, i|
            fragment.br unless i.zero?
            fragment.audio(src: url.strip)
          end
        end
      end
      node.replace(audio_fragment)
    end

    # We're now certain that this is an embed from a trusted source, but we
    # still need to run it through a special Sanitize step to ensure
    # that no unwanted elements or attributes that don't belong in
    # a video embed can sneak in.
    def sanitize_object
      Sanitize.clean_node!(
        parent,
        elements: %w[embed object param],
        attributes: {
          'embed'  => %w[allowfullscreen height src type width],
          'object' => %w[height width],
          'param'  => %w[name value]
        }
      )

      disable_scripts(parent)

      { node_whitelist: [node, parent] }
    end

    def sanitize_embed
      Sanitize.clean_node!(
        node,
        elements: %w[embed iframe],
        attributes: {
          'embed' => %w[
            allowfullscreen height src type width
          ] + optional_embed_attributes,
          'iframe' => %w[
            allowfullscreen frameborder height src title
            class type width
          ]
        }
      )

      if node_name == 'embed'
        disable_scripts(node)
        node['flashvars'] = "" unless allows_flashvars?
      end
      { node_whitelist: [node] }
    end

    # disable script access and networking
    def disable_scripts(embed_node)
      embed_node['allowscriptaccess'] = 'never'
      embed_node['allownetworking'] = 'internal'

      embed_node.search("param").each do |param_node|
        param_node.unlink if param_node[:name].casecmp?("allowscriptaccess") ||
                             param_node[:name].casecmp?("allownetworking")
      end
    end

    def optional_embed_attributes
      if allows_flashvars?
        %w[wmode flashvars]
      else
        []
      end
    end

    def allows_flashvars?
      ALLOWS_FLASHVARS.include?(source)
    end

    def supports_https?
      SUPPORTS_HTTPS.include?(source)
    end
  end
end

# frozen_string_literal: true

# modifying to_text_area_tag and text_area_tag to strip paragraph/br tags
# and convert them back into newlines for editing purposes
module ActionView
  module Helpers
    module Tags
      class TextArea
        # added method to yank <p> and <br> tags and replace with newlines
        # this needs to reverse "add_paragraph_tags_to_text" from our html_cleaner library
        def strip_html_breaks(content, name="")
          return "" if content.blank?
          if name =~ /content/
            # might be using RTE, preserve all paragraphs as they are
            content.gsub(/\s*<br ?\/?>\s*/, "<br />\n").
                    gsub(/\s*<p[^>]*>\s*&nbsp;\s*<\/p>\s*/, "\n\n\n").
                    gsub(/\s*(<p[^>]*>.*?<\/p>)\s*/m, "\n\n" + '\1').
                    strip
          else
            # no RTE, so clean up paragraphs unless they have qualifiers
            content = content.gsub(/\s*<br ?\/?>\s*/, "<br />\n").
                              gsub(/\s*<p[^>]*>\s*&nbsp;\s*<\/p>\s*/, "\n\n\n")

            if content.match(/\s*(<p[^>]+>)(.*?)(<\/p>)\s*/m)
              content.gsub(/\s*(<p[^>]*>.*?<\/p>)\s*/m, "\n\n" + '\1').
                      strip
            else
              content.gsub(/\s*<p[^>]*>(.*?)<\/p>\s*/m, "\n\n" + '\1').
                      strip
            end
          end
        end

        def render
          options = @options.stringify_keys
          add_default_name_and_id(options)

          if size = options.delete("size")
            options["cols"], options["rows"] = size.split("x") if size.respond_to?(:split)
          end

          content = options.delete("value") { value_before_type_cast }
          content = strip_html_breaks(content, options['name'])

          content_tag("textarea", content, options)
        end
      end
    end
  end
end

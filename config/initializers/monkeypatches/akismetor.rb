# Patch Akismetor, which is not Ruby 3 compatible, as it uses URI.escape:
# https://github.com/freerobby/akismetor/blob/1cef6c0e237dd69b3f6ae1ee8d719c0862cd77e4/lib/akismetor.rb#L53-L56
#
# TODO: AO3-6454 Remove this gem and handle the API calls ourselves.

class Akismetor
  def attributes_for_post
    URI.encode_www_form(attributes)
  end
end

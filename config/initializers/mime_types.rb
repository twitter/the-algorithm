# Be sure to restart your server when you modify this file.

# Add new mime types for use in respond_to blocks:
# Mime::Type.register "text/richtext", :rtf
# Mime::Type.register_alias "text/html", :iphone

# for azw3 files
Mime::Type.register 'application/x-mobi8-ebook', :azw3

# for mobi files
Mime::Type.register 'application/x-mobipocket-ebook', :mobi

# for epub files
Mime::Type.register 'application/epub', :epub

class Download
  attr_reader :work, :file_type, :mime_type

  def initialize(work, options = {})
    @work = work
    @file_type = set_file_type(options.slice(:mime_type, :format))
    # TODO: Our current version of the mime-types gem doesn't include azw3, but
    # the gem cannot be updated without updating rest-client
    @mime_type = @file_type == "azw3" ? "application/x-mobi8-ebook" : MIME::Types.type_for(@file_type).first
    @include_draft_chapters = options[:include_draft_chapters]
  end

  def generate
    DownloadWriter.new(self).write
    self
  end

  def exists?
    File.exist?(file_path)
  end

  # Removes not just the file but the whole directory
  # Should change if our approach to downloads ever changes
  def remove
    FileUtils.rm_rf(dir)
  end

  # Given either a file extension or a mime type, figure out
  # what format we're generating
  # Defaults to html
  def set_file_type(options)
    if options[:mime_type]
      file_type_from_mime(options[:mime_type])
    elsif ArchiveConfig.DOWNLOAD_FORMATS.include?(options[:format].to_s)
      options[:format].to_s
    else
      "html"
    end
  end

  # Given a mime type, return a file extension
  def file_type_from_mime(mime)
    ext = MimeMagic.new(mime.to_s).subtype
    case ext
    when "x-mobipocket-ebook"
      "mobi"
    when "x-mobi8-ebook"
      "azw3"
    else
      ext
    end
  end

  # The base name of the file (eg, "War and Peace")
  def file_name
    name = clean(work.title)
    name += " Work #{work.id}" if name.length < 3
    name.strip
  end

  # The public route to this download
  def public_path
    "/downloads/#{work.id}/#{file_name}.#{file_type}"
  end

  # The path to the zip file (eg, "/tmp/42_epub_20190301-24600-17164a8/42.zip")
  def zip_path
    "#{dir}/#{work.id}.zip"
  end

  # The path to the folder where web2disk downloads the xhtml and images
  def assets_path
    "#{dir}/assets"
  end

  # The full path to the HTML file (eg, "/tmp/42_epub_20190301-24600-17164a8/The Hobbit.html")
  def html_file_path
    "#{dir}/#{file_name}.html"
  end

  # The full path to the file (eg, "/tmp/42_epub_20190301-24600-17164a8/The Hobbit.epub")
  def file_path
    "#{dir}/#{file_name}.#{file_type}"
  end

  # Get the temporary directory where downloads will be generated,
  # creating the directory if it doesn't exist.
  def dir
    return @tmpdir if @tmpdir
    @tmpdir = Dir.mktmpdir("#{work.id}_#{file_type}_")
    @tmpdir
  end

  # Utility methods which clean up work data for use in downloads

  def fandoms
    string = work.fandoms.size > 3 ? "Multifandom" : work.fandoms.string
    clean(string)
  end

  def authors
    author_names.join(', ').to_ascii
  end

  def author_names
    work.anonymous? ? ["Anonymous"] : work.pseuds.sort.map(&:byline)
  end

  # need the next two to be filesystem safe and not overly long
  def file_authors
    clean(author_names.join('-'))
  end

  def page_title
    [file_name, file_authors, fandoms].join(" - ")
  end

  def chapters
    if @include_draft_chapters
      work.chapters.order("position ASC")
    else
      work.chapters.order("position ASC").where(posted: true)
    end
  end

  private

  # make filesystem-safe
  # ascii encoding
  # squash spaces
  # strip all non-alphanumeric
  # truncate to 24 chars at a word boundary
  def clean(string)
    # get rid of any HTML entities to avoid things like "amp" showing up in titles
    string = string.gsub(/\&(\w+)\;/, '')
    string = string.to_ascii
    string = string.gsub(/[^[\w _-]]+/, '')
    string = string.gsub(/ +/, " ")
    string = string.strip
    string = string.truncate(24, separator: ' ', omission: '')
    string
  end
end

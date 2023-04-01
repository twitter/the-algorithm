class DownloadsController < ApplicationController

  skip_before_action :store_location, only: :show
  before_action :load_work, only: :show
  before_action :check_download_posted_status, only: :show
  before_action :check_download_visibility, only: :show
  around_action :remove_downloads, only: :show

  def show
    respond_to :html, :pdf, :mobi, :epub, :azw3
    @download = Download.new(@work, mime_type: request.format)
    @download.generate

    # Make sure we were able to generate the download.
    unless @download.exists?
      flash[:error] = ts("We were not able to render this work. Please try again in a little while or try another format.")
      redirect_to work_path(@work)
      return
    end

    # Send file synchronously so we don't delete it before we have finished
    # sending it
    File.open(@download.file_path, 'r') do |f|
      send_data f.read, filename: "#{@download.file_name}.#{@download.file_type}", type: @download.mime_type
    end
  end

protected

  # Set up the work and check revealed status
  # Once a format has been created, we want nginx to be able to serve
  # it directly, without going through Rails again (until the work changes).
  # This means no processing per user. Consider this the "published" version.
  # It can't contain unposted chapters, nor unrevealed creators, even
  # if the creator is the one requesting the download.
  def load_work
    unless @admin_settings.downloads_enabled?
      flash[:error] = ts("Sorry, downloads are currently disabled.")
      redirect_back_or_default works_path
      return
    end

    @work = Work.find(params[:id])
  end

  # We're currently just writing everything to tmp and feeding them through
  # nginx so we don't want to keep the files around.
  def remove_downloads
    yield
  ensure
    @download.remove
  end

  # We can't use check_visibility because this controller doesn't have access to
  # cookies on production or staging.
  def check_download_visibility
    return unless @work.hidden_by_admin || @work.in_unrevealed_collection?
    message = if @work.hidden_by_admin
                ts("Sorry, you can't download a work that has been hidden by an admin.")
              else
                ts("Sorry, you can't download an unrevealed work.")
              end
    flash[:error] = message
    redirect_to work_path(@work)
  end

  def check_download_posted_status
    return if @work.posted
    flash[:error] = ts("Sorry, you can't download a draft.")
    redirect_to work_path(@work)
  end
end

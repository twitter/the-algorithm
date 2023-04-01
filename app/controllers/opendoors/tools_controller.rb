class Opendoors::ToolsController < ApplicationController
  
  before_action :users_only
  before_action :opendoors_only
  
  def index
    @imported_from_url = params[:imported_from_url]
    @external_author = ExternalAuthor.new
  end
  
  # Update the imported_from_url value on an existing AO3 work
  # This is not RESTful but is IMO a better idea than setting up a works controller under the opendoors namespace,
  # since the functionality we want to provide is so limited.
  def url_update
    
    # extract the work id and find the work
    if params[:work_url] && params[:work_url].match(/works\/([0-9]+)\/?$/)
      work_id = $1
      @work = Work.find_by_id(work_id)
    end
    unless @work
      flash[:error] = ts("We couldn't find that work on the Archive. Have you put in the full URL?")
      redirect_to action: :index and return
    end

    # check validity of the new redirecting url
    unless params[:imported_from_url].blank?
      # try to parse the original entered url
      begin
        URI.parse(params[:imported_from_url])
        @imported_from_url = params[:imported_from_url]
      rescue
      end

      # if that didn't work, try to encode the URL and then parse it
      if @imported_from_url.blank?
        begin 
          URI.parse(URI::Parser.new.escape(params[:imported_from_url]))
          @imported_from_url = URI::Parser.new.escape(params[:imported_from_url])
        rescue
        end
      end
    end
    
    if @imported_from_url.blank?
      flash[:error] = ts("The imported-from url you are trying to set doesn't seem valid.")
    else
      # check for any other works 
      works = Work.where(imported_from_url: @imported_from_url)
      if works.count > 0 
        flash[:error] = ts("There is already a work imported from the url #{@imported_from_url}.")
      else
        # ok let's try to update
        @work.update_attribute(:imported_from_url, @imported_from_url)
        flash[:notice] = "Updated imported-from url for #{@work.title} to #{@imported_from_url}"
      end
    end    
    redirect_to action: :index, imported_from_url: @imported_from_url and return
  end
  
end

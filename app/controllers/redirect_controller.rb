class RedirectController < ApplicationController

  def index
    do_redirect
  end

  def do_redirect
    url = params[:original_url]
    if url.blank?
      flash[:error] = ts("What url did you want to look up?")
    else
      @work = Work.find_by_url(url)
      if @work
        flash[:notice] = ts("You have been redirected here from #{url}. Please update the original link if possible!")
        redirect_to work_path(@work) and return
      else
        flash[:error] = ts("We could not find a work imported from that url in the Archive of Our Own, sorry! Try another url?")
      end
    end
    redirect_to redirect_path
  end

  def show
    if params[:original_url].present?
      redirect_to action: :do_redirect, original_url: params[:original_url] and return
    end
  end

end

class HomeController < ApplicationController

  skip_before_action :store_location, only: [:first_login_help, :token_dispenser]
  
  # unicorn_test
  def unicorn_test
  end

  # terms of service
  def tos
    render action: "tos", layout: "application"
  end
  
  # terms of service faq
  def tos_faq 
    render action: "tos_faq", layout: "application"
  end

  # dmca policy
  def dmca 
    render action: "dmca", layout: "application"
  end

  # lost cookie
  def lost_cookie
    render action: 'lost_cookie', layout: 'application'
  end

  # for updating form tokens on cached pages
  def token_dispenser
    respond_to do |format|
      format.json { render json: { token: form_authenticity_token } }
    end
  end
  
  # diversity statement
  def diversity 
    render action: "diversity_statement", layout: "application"
  end
  
  # site map
  def site_map 
    render action: "site_map", layout: "application"
  end
  
  # donate
  def donate
    @page_subtitle = t(".page_title")
    render action: "donate", layout: "application"
  end
  
  # about
  def about
    render action: "about", layout: "application"
  end
  
  def first_login_help
    render action: "first_login_help", layout: false
  end

  # home page itself
  def index
    @homepage = Homepage.new(@current_user)
    unless @homepage.logged_in?
      @user_count, @work_count, @fandom_count = @homepage.rounded_counts
    end

    @hide_dashboard = true
    render action: 'index', layout: 'application'
  end
end

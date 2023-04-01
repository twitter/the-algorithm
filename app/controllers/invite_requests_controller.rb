class InviteRequestsController < ApplicationController
  before_action :admin_only, only: [:manage, :destroy]

  # GET /invite_requests
  def index
    @invite_request = InviteRequest.new
  end

  # GET /invite_requests/1
  def show
    fetch_admin_settings # we normally skip this for js requests
    @invite_request = InviteRequest.find_by(email: params[:email])
    @position_in_queue = @invite_request.position if @invite_request.present?
    unless (request.xml_http_request?) || @invite_request
      flash[:error] = "You can search for the email address you signed up with below. If you can't find it, your invitation may have already been emailed to that address; please check your email spam folder as your spam filters may have placed it there."
      redirect_to status_invite_requests_path and return
    end
    respond_to do |format|
      format.html
      format.js
    end
  end

  # POST /invite_requests
  def create
    unless @admin_settings.invite_from_queue_enabled?
      flash[:error] = ts("<strong>New invitation requests are currently closed.</strong> For more information, please check the %{news}.",
                         news: view_context.link_to("\"Invitations\" tag on AO3 News", admin_posts_path(tag: 143))).html_safe
      redirect_to invite_requests_path
      return
    end

    @invite_request = InviteRequest.new(invite_request_params)
    @invite_request.ip_address = request.remote_ip
    if @invite_request.save
      flash[:notice] = "You've been added to our queue! Yay! We estimate that you'll receive an invitation around #{@invite_request.proposed_fill_date}. We strongly recommend that you add do-not-reply@archiveofourown.org to your address book to prevent the invitation email from getting blocked as spam by your email provider."
      redirect_to invite_requests_path
    else
      render action: :index
    end
  end

  def manage
    authorize(InviteRequest)

    @invite_requests = InviteRequest.all

    if params[:query].present?
      query = "%#{params[:query]}%"
      @invite_requests = InviteRequest.where(
        "simplified_email LIKE ? OR ip_address LIKE ?",
        query, query
      )

      # Keep track of the fact that this has been filtered, so the position
      # will not cleanly correspond to the page that we're on and the index of
      # the request on the page:
      @filtered = true
    end

    @invite_requests = @invite_requests.order(:id).page(params[:page])
  end

  def destroy
    @invite_request = InviteRequest.find(params[:id])
    authorize @invite_request

    if @invite_request.destroy
      success_message = ts("Request for %{email} was removed from the queue.", email: @invite_request.email)
      respond_to do |format|
        format.html { redirect_to manage_invite_requests_path(page: params[:page], query: params[:query]), notice: success_message }
        format.json { render json: { item_success_message: success_message }, status: :ok }
      end
    else
      error_message = ts("Request could not be removed. Please try again.")
      respond_to do |format|
        format.html do
          flash.keep
          redirect_to manage_invite_requests_path(page: params[:page], query: params[:query]), flash: { error: error_message }
        end
        format.json { render json: { errors: error_message }, status: :unprocessable_entity }
      end
    end
  end

  def status
    @page_subtitle = ts("Invitation Request Status")
  end

  private

  def invite_request_params
    params.require(:invite_request).permit(
      :email, :query
    )
  end
end

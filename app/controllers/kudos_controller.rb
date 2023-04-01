class KudosController < ApplicationController
  skip_before_action :store_location

  def index
    @work = Work.find(params[:work_id])
    @kudos = @work.kudos.includes(:user).with_user
    @guest_kudos_count = @work.kudos.by_guest.count

    respond_to do |format|
      format.html do
        @kudos = @kudos.order(id: :desc).paginate(
          page: params[:page],
          per_page: ArchiveConfig.MAX_KUDOS_TO_SHOW
        )
      end

      format.js do
        @kudos = @kudos.where("id < ?", params[:before].to_i) if params[:before]
      end
    end
  end

  def create
    @kudo = Kudo.new(kudo_params)
    if current_user.present?
      @kudo.user = current_user
    else
      @kudo.ip_address = request.remote_ip
    end

    if @kudo.save
      respond_to do |format|
        format.html do
          flash[:kudos_notice] = t(".success")
          redirect_to request.referer and return
        end

        format.js do
          @commentable = @kudo.commentable
          @kudos = @commentable.kudos.with_user.includes(:user)
          render :create, status: :created
        end
      end
    else
      error_message = @kudo.errors.full_messages.first
      respond_to do |format|
        format.html do
          # If user is suspended or banned, JavaScript disabled, redirect user to dashboard with message instead.
          return if check_user_status

          flash[:kudos_error] = error_message
          redirect_to request.referer and return
        end

        format.js do
          render json: { error_message: error_message }, status: :unprocessable_entity
        end
      end
    end
  rescue ActiveRecord::RecordNotUnique
    # Uniqueness checks at application level (Rails validations) are inherently
    # prone to race conditions. If we pass Rails validations but get rejected
    # by database unique indices, use the usual duplicate error message.
    #
    # https://api.rubyonrails.org/v5.1/classes/ActiveRecord/Validations/ClassMethods.html#method-i-validates_uniqueness_of-label-Concurrency+and+integrity
    error_message = t("activerecord.errors.models.kudo.taken")
    respond_to do |format|
      format.html do
        flash[:kudos_error] = error_message
        redirect_to request.referer
      end

      format.js do
        render json: { error_message: error_message }, status: :unprocessable_entity
      end
    end
  end

  private

  def kudo_params
    params.require(:kudo).permit(:commentable_id, :commentable_type)
  end
end

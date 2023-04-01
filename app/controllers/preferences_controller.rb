class PreferencesController < ApplicationController
  before_action :load_user
  before_action :check_ownership
  skip_before_action :store_location

  # Ensure that the current user is authorized to view and change this information
  def load_user
    @user = User.find_by(login: params[:user_id])
    @check_ownership_of = @user
  end

  def index
    @user = User.find_by(login: params[:user_id])
    @preference = @user.preference
    @available_skins = (current_user.skins.site_skins + Skin.approved_skins.site_skins).uniq
    @available_locales = Locale.where(email_enabled: true)
  end

  def update
    @user = User.find_by(login: params[:user_id])
    @preference = @user.preference
    @user.preference.attributes = preference_params
    @available_skins = (current_user.skins.site_skins + Skin.approved_skins.site_skins).uniq
    @available_locales = Locale.where(email_enabled: true)

    if params[:preference][:skin_id].present?
      # unset session skin if user changed their skin
      session[:site_skin] = nil
    end

    if @user.preference.save
      flash[:notice] = ts('Your preferences were successfully updated.')
      redirect_to user_path(@user)
    else
      flash[:error] = ts('Sorry, something went wrong. Please try that again.')
      render action: :index
    end
  end

  private

  def preference_params
    params.require(:preference).permit(
      :email_visible,
      :date_of_birth_visible,
      :minimize_search_engines,
      :disable_share_links,
      :adult,
      :view_full_works,
      :hide_warnings,
      :hide_freeform,
      :disable_work_skins,
      :skin_id,
      :time_zone,
      :preferred_locale,
      :work_title_format,
      :comment_emails_off,
      :comment_inbox_off,
      :comment_copy_to_self_off,
      :kudos_emails_off,
      :admin_emails_off,
      :allow_collection_invitation,
      :collection_emails_off,
      :collection_inbox_off,
      :recipient_emails_off,
      :history_enabled,
      :first_login,
      :banner_seen,
      :allow_cocreator,
      :allow_gifts
    )
  end
end

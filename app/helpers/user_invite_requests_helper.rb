module UserInviteRequestsHelper
  
  # given a request for invite codes:
  # generates a link displaying the number of invite codes previously issued to this
  # user; link goes to overview of user's invite codes.
  def link_to_previous_invite_requests(request)
    if request.user.invitations.count > 0
      link_to request.user.invitations.count, find_admin_invitations_path(user_name: request.user.login)
    else
      "0"
    end
  end    

end

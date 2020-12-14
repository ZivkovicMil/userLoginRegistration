<b>DESCRIPTION</b>

In this spring boot application I implemented basic spring security with JWT and user registration.
When user wants to register he will receive email with activation link (duration 60min), after activating that user he will be enabled and get role USER.
If someone is forgot his password, he can provide email and link for changing password will be sent (duration 5min), on that link user need to fill fields for new password.
Two schedulers run every day and delete tokens which is not active anymore.


<b>Endpoints available for anyone:</b>

    -api/auth/register (RequestBody userRegister)

   (On this endpoint user can register, after filling necessary fields user will receive email with verification link)
    
    -api/auth/{registrationToken} (PathVariable registrationToken)

  (This serve to check if registration token is valid, and after confirmation, user will be enabled, registration token is valid for 60min)

    -api/forgotPassword (RequestParam userEmail)
(endpoint if user is forgot his password, user needs to provide email, after that, user will receive link for password change)

    -api/forgotPassword/{token} (PathVariable changePasswordToken,RequestParam newPassword, newPassword2)
(This endpoint first check if token is valid for that user and also check if token is expired, after that checks if newPassword and newPassword2 are equal, if all this criteria is fulfilled user password is changed)

    -/login (RequestBody userRegister)
(Spring security endpoint which receive username and password, if credentials are good bearer token will be created and putted in header)



<b>Endpoints available for users who have role ADMIN</b>

    -/admin/allUsers (Pageable pageable)
(Endpoint for listing all registered users, for this I used pageable where you can select how much users you want to see on one page)

    -/admin/role/addAdmin/{email} (PathVariable email)
(Here admin can give ADMIN role to specific user)

    -/admin/role/removeRole/{email}/{role} (PathVariable email, role)
(Endpoint where admin can remove specific role from specific user)

    -/admin/turnOffUser/{email} (PathVariable email)
(Endpoint where admin can disable specific user)

    -/admin/turnOnUser/{email} (PathVariable email)
(Endpoint where admin can enable specific user)



<b>Endpoints available for users who have role USER and ADMIN</b>

    -/user/changeAddress/{newAddress} (PathVariable newAddress)
(Endpoint where user can change his own address)

    -/user/changePhoneNumber/{phoneNumber} (PathVariable phoneNumber)
  (Endpoint where user can change his own phone number)



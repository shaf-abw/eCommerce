package com.abw.ecommerce.ProxyClient.business.auth.service;

import com.abw.ecommerce.ProxyClient.business.auth.model.request.AuthenticationRequest;
import com.abw.ecommerce.ProxyClient.business.auth.model.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse authenticate(final AuthenticationRequest authenticationRequest);
    Boolean authenticate(final String jwt);

}
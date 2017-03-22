package com.softgroup.authorization.impl.handler;

import com.softgroup.authorization.api.message.LoginRequest;
import com.softgroup.authorization.api.message.LoginResponse;
import com.softgroup.authorization.api.router.AuthorizationRequestHandler;
import com.softgroup.common.dao.impl.service.DeviceService;
import com.softgroup.common.jwt.impl.service.TokenService;
import com.softgroup.common.protocol.Request;
import com.softgroup.common.protocol.Response;
import com.softgroup.common.protocol.ResponseStatus;
import com.softgroup.common.router.api.AbstractRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: vadym
 * Date: 23.02.17
 * Time: 13:16
 */
@Component
public class LoginAuthorizationHandler extends
            AbstractRequestHandler<LoginRequest, LoginResponse >
                                implements AuthorizationRequestHandler {

    @Autowired
    TokenService tokenService;

    @Autowired
    DeviceService deviceService;

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public Response<LoginResponse> doHandle(Request<LoginRequest> request) {
            LoginRequest requestData = request.getData();
        String deviceToken = requestData.getDeviceToken();

        String profileID = tokenService.getProfileID(deviceToken);
        String deviceID = tokenService.getDeviceID(deviceToken);

        String sessionToken = tokenService.generateSessionToken(profileID, deviceID);

        Long currentTime = tokenService.getTimeOfCreation(sessionToken);
        deviceService.setTimeOfUpdatingOfToken(currentTime, deviceID);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(sessionToken);

        Response<LoginResponse> response = new Response<LoginResponse>();
        response.setHeader(request.getHeader());
        response.setData(loginResponse);

        ResponseStatus status = new ResponseStatus();
        status.setCode(200);
        status.setMessage("OK");

        response.setStatus(status);
        return response;
    }
}

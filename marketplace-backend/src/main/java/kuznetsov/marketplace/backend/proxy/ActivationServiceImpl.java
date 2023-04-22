package kuznetsov.marketplace.backend.proxy;

import kuznetsov.marketplace.backend.auth.UserAuthService;
import kuznetsov.marketplace.backend.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivationServiceImpl implements ActivationService {

    private final ActivationProperties activationProps;

    private final JwtService jwtService;
    private final UserAuthService userAuthService;

    @Override
    public String getActivationUrl() {
        return activationProps.getUrl();
    }

    @Override
    public String generateActivationToken(ActivationRequest activationRequest) {
        return jwtService.generateAccessToken(
                activationRequest.getEmail(), activationRequest.getRole()
        );
    }

    @Override
    public ActivationMessage activate(String activationToken) {
        String userEmail = jwtService.getEmailFromAccessToken(activationToken);
        userAuthService.confirmUserEmail(userEmail);
        return new ActivationMessage("Your account has been successfully activated");
    }

}

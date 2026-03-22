package MyRemoteUserStorageProvider.project.MyRemoteUserStorageProvider;

import jakarta.ws.rs.PathParam;
import org.jboss.logging.Logger;
import org.keycloak.broker.provider.util.SimpleHttp;
import org.keycloak.models.KeycloakSession;

public class UsersApiLegacyService {

    private static final Logger LOGGER = Logger.getLogger(UsersApiLegacyService.class);

    private final KeycloakSession session;

    public UsersApiLegacyService(KeycloakSession session) {
        this.session = session;
    }
    public User getUserByUserName(String username) {

        User user = null;

        try {
            user = SimpleHttp.doGet("http://localhost:8099/users/" + username, session)
                    .asJson(User.class);
        } catch (Exception ex) {
            LOGGER.error("Error fetching user " + username + " from external service: " + ex.getMessage(), ex);
        }

        return user;
    }


    public VerifyPasswordResponse verifyUserPassword(@PathParam("username") String username, String password) {

        SimpleHttp simpleHttp = SimpleHttp.doPost("http://localhost:8099/users/" + username + "/verify-password", session);

        VerifyPasswordResponse verifyPasswordResponse = null;
        simpleHttp.param("password", password);
        simpleHttp.header("Content-Type", "application/x-www-form-urlencoded");

        try {
            verifyPasswordResponse = simpleHttp.asJson(VerifyPasswordResponse.class);
        } catch (Exception e) {
            LOGGER.error("The provided password is incorrect", e);
        }

        return verifyPasswordResponse;
    }
}

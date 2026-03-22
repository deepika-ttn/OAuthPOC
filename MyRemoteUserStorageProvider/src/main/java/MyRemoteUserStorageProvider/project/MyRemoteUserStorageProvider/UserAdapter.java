package MyRemoteUserStorageProvider.project.MyRemoteUserStorageProvider;

import jakarta.ws.rs.core.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.UserCredentialManager;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.SubjectCredentialManager;
import org.keycloak.models.UserModel;
import org.keycloak.storage.adapter.AbstractUserAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserAdapter extends AbstractUserAdapter {

    private final User users;

    public UserAdapter(KeycloakSession session , RealmModel realm , ComponentModel storageProviderModel , User users){
        super(session , realm, storageProviderModel);
        this.users= users;
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }

    @Override
    public String getFirstName() {
        return users.getFirstName();
    }


    @Override
    public String getLastName() {
        return users.getLastName();
    }

    @Override
    public String getEmail() {
        return users.getEmail();
    }

    @Override
    public SubjectCredentialManager credentialManager() {
        return new UserCredentialManager(session , realm , this);
    }

    @Override
    public String getFirstAttribute(String name) {
        List<String> list = getAttributes().getOrDefault(name, new ArrayList<>());
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        MultivaluedHashMap<String, String> attributes = new MultivaluedHashMap<>();
        attributes.add(UserModel.USERNAME, getUsername());
        attributes.add(UserModel.EMAIL, getEmail());
        attributes.add(UserModel.FIRST_NAME, getFirstName());
        attributes.add(UserModel.LAST_NAME, getLastName());
        return attributes;
    }
}

package poc.ResourceServer.contoller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import poc.ResourceServer.response.UserRest;

@RestController
@RequestMapping("/users")
public class UsersController {

    @GetMapping("/status/check")
    public String status(){
         return "working fine";
    }

    @Secured("ROLE_developer")
    @PreAuthorize("hasAuthority('ROLE_developer') or #id==#jwt")
    @DeleteMapping(path = "/{id}")
    public String deleteUser(@PathVariable String id , @AuthenticationPrincipal Jwt jwt){
        return  "User deleted with id " + id +"and JWT subject" + jwt.getSubject()  ;
    }

    @PostAuthorize("returnObject.userId == #jwt.subject")
   @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id , @AuthenticationPrincipal Jwt jwt){
        return  new UserRest("Deepika" , "Rani" ,"7ce85cb2-79fe-4fb9-9594-7ac146d9c836") ;
    }

}

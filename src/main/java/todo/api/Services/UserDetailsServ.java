package todo.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import todo.api.Data.UserRepository;
import todo.api.Helpers.IUserDetails;

@Service
public class UserDetailsServ implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public IUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Set<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
        return IUserDetails.build(user);
    }
    
}

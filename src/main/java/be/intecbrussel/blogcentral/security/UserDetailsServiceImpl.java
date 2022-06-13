package be.intecbrussel.blogcentral.security;

import be.intecbrussel.blogcentral.model.Author;
import be.intecbrussel.blogcentral.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
        throws UsernameNotFoundException{
        Author author = authorRepository.getUserByname(username);

        if (author == null){
            throw new UsernameNotFoundException("Could not find user");
        }
        return new MyUserDetails(author);
    }
}

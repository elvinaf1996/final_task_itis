package ru.itis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.itis.model.dto.UserDTO;
import ru.itis.model.entity.User;
import ru.itis.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean saveUser(UserDTO userDTO) {
        User userFromDB = userRepository.findByUsername(userDTO.getUsername());

        if (userFromDB != null) {
            return false;
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Set<String> roles = userDTO.getRoles();

        if (isNull(userDTO.getRoles())) {
            roles = new HashSet<>();
            roles.add("ROLE_USER");
        }

        user.setRoles(roles);
        userRepository.saveAndFlush(user);

        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void deleteByUserId(Long userId) {
        userRepository.deleteById(userId);
    }
}
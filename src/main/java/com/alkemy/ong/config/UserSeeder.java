package com.alkemy.ong.config;

import com.alkemy.ong.entity.Role;
import com.alkemy.ong.entity.User;
import com.alkemy.ong.repository.RoleRepository;
import com.alkemy.ong.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


@Component
public class UserSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String PHOTO = "default.jpg";


    @Override
    public void run(String... args) throws Exception {
        loadRole();
        loadUser();

    }

    //create roles if they don't exist
    private void loadRole() {
        if (roleRepository.count() == 0) {
            loadRoleSeed();
        }
    }

    private void loadRoleSeed() {
        roleRepository.save(buildRole("ROLE_ADMIN",
                "Has all the privileges from both roles"));
        roleRepository.save(buildRole("ROLE_USER",
                "Privileges limited to only modifying and viewing your data"));
    }

    private Role buildRole(String name, String description) {
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        role.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return role;
    }

    //create users if they don't exist
    private void loadUser() {
        if (userRepository.count() == 0) {
            loadUserSeed();
        }
    }

    private void loadUserSeed() {
        //add Admin Users
        Role roleEntityAdmin = roleRepository.findByName("ROLE_ADMIN");
        userRepository.save(buildUser("Sansone", "Rickardes", "srickardes0@facebook.com", "OTLZQDLdddfx", roleEntityAdmin));
        userRepository.save(buildUser("Sileas", "Gadesby", "sgadesby1@nsw.gov.au", "LWgwvkvxsdd", roleEntityAdmin));
        userRepository.save(buildUser("Raynor", "Creese", "rcreese2@blog.com", "6r0Jfdkddd", roleEntityAdmin));
        userRepository.save(buildUser("Madel", "Charteris", "mcharteris3@themeforest.net", "OGfiQ5zbxs", roleEntityAdmin));
        userRepository.save(buildUser("Cedric", "Meyrick", "cmeyrick4@amazon.co.jp", "2myWZWtb6Am", roleEntityAdmin));
        userRepository.save(buildUser("Fitzgerald", "Mersh", "fmersh5@pcworld.com", "D1PH35Kxds", roleEntityAdmin));
        userRepository.save(buildUser("Johnath", "Origan", "jorigan5@storify.com", "R2oxqEfElMln", roleEntityAdmin));
        userRepository.save(buildUser("Donni", "Extance", "dextance6@wisc.edu", "wTahWubXxx", roleEntityAdmin));
        userRepository.save(buildUser("Corbie", "Lapsley", "clapsley7@microsoft.com", "qKpql1Ounzwo", roleEntityAdmin));
        userRepository.save(buildUser("Leisha", "Belin", "lbelin8@pcworld.com", "w77p77wGSs", roleEntityAdmin));

        //add Users Example
        Role roleEntityUser = roleRepository.findByName("ROLE_USER");
        userRepository.save(buildUser("Misha", "Fossord", "mfossord9@senate.gov", "3q3MgFh2Ps5", roleEntityUser));
        userRepository.save(buildUser("Cristian", "Inkpen", "cinkpen0@joomla.org", "4VHcXxp86sB", roleEntityUser));
        userRepository.save(buildUser("Cristiano", "Foston", "cfoston1@japanpost.jp", "9wf9KkPkxsx", roleEntityUser));
        userRepository.save(buildUser("Odele", "Clynmans", "oclynmans2@bravesites.com", "DTUMZGlDjxdd", roleEntityUser));
        userRepository.save(buildUser("Will", "MacRonald", "wmacronald3@jalbum.net", "KW1LpoXehdo", roleEntityUser));
        userRepository.save(buildUser("Skylar", "Larter", "slarter5@ovh.net", "olpArkfRD2", roleEntityUser));
        userRepository.save(buildUser("Heidi", "Saffill", "hsaffill6@huffingtonpost.com", "DZfgtHxxxs", roleEntityUser));
        userRepository.save(buildUser("Sharity", "Penreth", "spenreth7@linkedin.com", "7QWax89xxs", roleEntityUser));
        userRepository.save(buildUser("Yasmin", "Lapsley", "yjoicey8@theglobeandmail.com", "r79Sfnpxxsx", roleEntityUser));
        userRepository.save(buildUser("Charissa", "MacGillreich", "cmacgillreicha@sbwire.com", "7MOQwvVOHz2p", roleEntityUser));
    }

    private User buildUser(String firstName, String lastName, String email, String password, Role roles) {

        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(roles);
        user.setPhoto(PHOTO);
        return user;
    }

}
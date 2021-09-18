package com.iuh.IUHStudent.resolvers;

import com.iuh.IUHStudent.entity.Account;
import com.iuh.IUHStudent.entity.Image;
import com.iuh.IUHStudent.entity.User;
import com.iuh.IUHStudent.entityinput.ImageInput;
import com.iuh.IUHStudent.entityinput.UserInput;
import com.iuh.IUHStudent.repository.UserRepository;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        List<User> listUser = (List<User>) userRepository.findAll();
        System.out.println(listUser);
        return listUser;
    }

    public User createUser(UserInput input) {
        Account account = new Account();
        account.setUserName(input.getAccount().getUserName());
        account.setPassword(input.getAccount().getPassword());

        List<Image> images = new ArrayList<>();

        for (ImageInput imageInput : input.getImages()) {
            Image image = new Image();
            image.setName(imageInput.getName());

            images.add(image);
        }

        User user = new User();
        user.setName(input.getName());
        user.setEmail(input.getEmail());
        user.setAccount(account);
        user.setImages(images);

        return userRepository.save(user);
    }
}

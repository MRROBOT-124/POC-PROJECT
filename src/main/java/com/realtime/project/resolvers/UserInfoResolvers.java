package com.realtime.project.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.realtime.project.entity.UserInfo;
import com.realtime.project.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoResolvers implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserInfoService userInfoService;

    public UserInfo getUser(String username) {
        return userInfoService.findUser(username);
    }

    public UserInfo addUser(UserInfo userInfo) {
        return userInfoService.persistUser(userInfo);
    }

    public List<UserInfo> getAllUsers() {
        return userInfoService.findAllUsers();
    }
}

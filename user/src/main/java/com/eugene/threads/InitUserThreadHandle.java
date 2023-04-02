
package com.eugene.threads;

import com.eugene.pojo.User;
import com.eugene.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class InitUserThreadHandle implements Runnable {

    private IUserService userService;
    private List<User> userList;

    @Override
    public void run() {
        userService.saveBatch(userList);
    }
}
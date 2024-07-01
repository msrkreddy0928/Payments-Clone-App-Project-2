package org.example.service;


import com.example.UserCreatedPayload;
import jakarta.transaction.Transactional;
import org.example.dto.UserDto;
import org.example.entity.User;
import org.example.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class UserService {

    private Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Value("${user.created.topic}")
    private String userCreatedTopic;


    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KafkaTemplate<String,Object> kafkatemplate;

    @Transactional
    public Long createUser(UserDto userDto) throws ExecutionException,InterruptedException {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getPhone());
        user.setKycNumber(userDto.getKycNumber());
        user = userRepo.save(user);
        UserCreatedPayload userCreatedPayload = new UserCreatedPayload();
        userCreatedPayload.setUserEmail(user.getEmail());
        userCreatedPayload.setUserName(user.getName());
        userCreatedPayload.setUserId(user.getId());
        userCreatedPayload.setRequestId(MDC.get("requestId"));
        Future<SendResult<String,Object>> future=kafkatemplate.send(userCreatedTopic,userCreatedPayload.getUserEmail().toString(),userCreatedPayload);
        LOGGER.info("pushed user create payload to kafka :{} ",future.get());
        return user.getId();


    }


}

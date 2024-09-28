//package com.identity_service.identity.saga;
//
//import com.identity_service.identity.dto.response.UserResponse;
//import com.identity_service.identity.saga.data.CreateUserSagaData;
//import com.identity_service.identity.service.*;
//import com.travelsmart.saga.notication.reply.NotificationFailed;
//import com.travelsmart.saga.notication.reply.NotificationSuccess;
//import com.travelsmart.saga.profile.reply.CreateProfileSuccess;
//import com.travelsmart.saga.profile.reply.CreateProfuleFailed;
//import io.eventuate.tram.commands.consumer.CommandWithDestination;
//import io.eventuate.tram.sagas.orchestration.SagaDefinition;
//import io.eventuate.tram.sagas.simpledsl.SimpleSaga;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class CreateUserSaga implements SimpleSaga<CreateUserSagaData> {
//    private final UserService userService;
//    private final ProfileMessageService profileMessageService;
//    private final NotificationMessageService notificationMessageService;
//    private final ConfirmTokenService confirmTokenService;
//
//    private final SagaDefinition<CreateUserSagaData> sagaDefinition =
//            step()
//                    .invokeLocal(this::createUser)
//                    .withCompensation(this::rejectUser)
//                    .step()
//                    .invokeParticipant(this::createProfile)
//                    .withCompensation(this::removeProfile)
//                    .onReply(CreateProfileSuccess.class, (data, reply) -> log.info(reply.message()))
//                    .onReply(CreateProfuleFailed.class, (data, reply) -> log.info(reply.message()))
////                    .step()
////                    .invokeParticipant(this::verifyEmail)
////                    .onReply(NotificationSuccess.class, (data, reply) -> log.info(reply.message()))
////                    .onReply(NotificationFailed.class, (data, reply) -> log.info(reply.message()))
//                    .step()
//                    .invokeLocal(this::enableUser)
//                    .build();
//
//    private void enableUser(CreateUserSagaData createUserSagaData) {
//        confirmTokenService.verifyToken(createUserSagaData.getToken());
//    }
//
//
//    private CommandWithDestination verifyEmail(CreateUserSagaData createUserSagaData) {
//        String token = UUID.randomUUID().toString();
//        createUserSagaData.setToken(token);
//        return notificationMessageService
//                .sendNotificationEmailCommand(createUserSagaData.getUser().getId(),
//                        createUserSagaData.getUserPost(),token);
//    }
//
//    private CommandWithDestination removeProfile(CreateUserSagaData createUserSagaData) {
//        return null;
//    }
//
//    private CommandWithDestination createProfile(CreateUserSagaData createUserSagaData) {
//        System.out.println("Create profile");
//        return profileMessageService
//                .sendUserProfileCommand(createUserSagaData.getUser().getId(),createUserSagaData.getUserPost());
//    }
//
//    private void rejectUser(CreateUserSagaData createUserSagaData) {
//    }
//
//    private void createUser(CreateUserSagaData createUserSagaData) {
//        UserResponse userResponse = userService.createUser(createUserSagaData.getUserPost());
//        createUserSagaData.setUser(userResponse);
//    }
//
//    @Override
//    public SagaDefinition<CreateUserSagaData> getSagaDefinition() {
//        return sagaDefinition;
//    }
//}

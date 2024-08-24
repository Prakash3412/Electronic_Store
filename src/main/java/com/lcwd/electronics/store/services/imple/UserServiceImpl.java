package com.lcwd.electronics.store.services.imple;

import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.UserDto;
import com.lcwd.electronics.store.entities.User;
import com.lcwd.electronics.store.exception.ResourceNotFoundException;
import com.lcwd.electronics.store.helper.Helper;
import com.lcwd.electronics.store.repositories.UserRepository;
import com.lcwd.electronics.store.services.UserService;
import lombok.Builder;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String imagePath;

    @Override
    public UserDto createUser(UserDto userDto) {

        //generate unique id in String format  used UUID CLASS

        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);

        //encoding password
      //  userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));


        //we have to convert Dto  ->> Entity kyunki save ko chahiye Entity so we will crete method

        User user = dtoToEntity(userDto);  //convert DTO -> to Entity(User) return
        User savedUser = userRepository.save(user);

        //for return the value we hava to return UserDto so we neee one for conversion  and then return

        UserDto newDto = entityToDto(savedUser);  //return UserDto

        return newDto;
    }


    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id"));
        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());
        user.setPassword(userDto.getPassword());
        //email need we can set

        //save data
        User updatedUser = userRepository.save(user);

        //but we have to return Dto so we have to convert  Entity -> Dto

        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        //delete user image profile
        //suppose image name images/users/abs.png
        String fullPath = imagePath + user.getImageName();

        //crate path object Path interface implementation object
       try{
           Path path = Paths.get(fullPath); //
           Files.delete(path);  //Files class used for delete the path

       }catch (NoSuchFileException ex)
       {
          logger.info("User not found in folder");
          ex.printStackTrace();
       } catch (IOException e) {

           e.printStackTrace();
       }
        //delete the user
        userRepository.delete(user);

    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir)
    {
        //for sort we have to create object and that onject used

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);  //pageable is a interface so wa cant create object directly so

        Page<User> page = userRepository.findAll(pageable);       //used implementation class that is PageRequest directly & of method contain pageNumber & pageSize

        PageableResponse<UserDto> response = Helper.getPageableResponse(page, UserDto.class);
        return response;
    }

    @Override
    public UserDto getSingleUserById(String userId) {
        User signleUser = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        return entityToDto(signleUser);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("email not found"));
        return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtoList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoList;
    }

    private UserDto entityToDto(User savedUser) {
//        UserDto userDto = UserDto.builder()
//                .userId(savedUser.getUserId())
//                .about(savedUser.getAbout())
//                .password(savedUser.getPassword())
//                .name(savedUser.getName())
//                .email(savedUser.getEmail())
//                .gender(savedUser.getGender())
//                .imageName(savedUser.getImageName())
//                .build();

        //now we used ModelMapper

        return mapper.map(savedUser, UserDto.class);

    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder()
//                .userId(userDto.getUserId())
//                .name(userDto.getName())
//                .gender(userDto.getGender())
//                .about(userDto.getAbout())
//                .email(userDto.getEmail())
//                .imageName(userDto.getImageName())
//                .password(userDto.getPassword())
//                .build();


        return mapper.map(userDto,User.class);
    }
}

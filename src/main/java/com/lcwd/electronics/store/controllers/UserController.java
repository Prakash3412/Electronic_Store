package com.lcwd.electronics.store.controllers;

import com.lcwd.electronics.store.dtos.ApiResponseMessage;
import com.lcwd.electronics.store.dtos.ImageResponse;
import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.UserDto;
import com.lcwd.electronics.store.services.FileService;
import com.lcwd.electronics.store.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private UserService userService;  // bcoz all method of UserDto have the Services class
    @Autowired
    private FileService fileService;  //bcoz all method of FileService class
    @Value("${user.profile.image.path}") //inject the value from the Application.properties file
    private String imageUploadPath;

    Logger logger= LoggerFactory.getLogger(UserController.class);

    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto)//valid used we are accepting the data here (client se) help of @RequestBody json data
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(

            @PathVariable("userId") String userId,
            @Valid @RequestBody UserDto userDto  //valid used here bcoz of we are accepting the json data from the client
    )
    {
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUserDto,HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
        userService.deleteUser(userId);
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User successfully deleted")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUser(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    )
    {
        return new ResponseEntity<>(userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getSingleUser( @PathVariable String userId){
        return new ResponseEntity<>(userService.getSingleUserById(userId),HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail( @PathVariable String email){
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }
    //search user
@GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword)
    {
         return new ResponseEntity<>(userService.searchUser(keyword),HttpStatus.OK);
    }

    //upload user image
    @PostMapping("/image/{userId}")
     public ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable String userId) throws IOException //data type used MultipartFile to upload the image
     {
         String imageName = fileService.uploadFile(image, imageUploadPath); //upload file call from the fileService

         UserDto user = userService.getSingleUserById(userId);  //help of the userService get the user and set the new user Imagename
         user.setImageName(imageName);

         UserDto userDto = userService.updateUser(user, userId); //get updated user again

         //return response
         ImageResponse imageResponse = ImageResponse.builder().imageName(imageName).success(true).message("successfully created").status(HttpStatus.CREATED).build();
         return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
     }

    //serve the user
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId,HttpServletResponse response) throws IOException {
        UserDto user = userService.getSingleUserById(userId);
        logger.info("user image name:{}",user.getImageName());
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}

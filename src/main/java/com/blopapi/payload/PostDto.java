package com.blopapi.payload;

import com.sun.org.glassfish.gmbal.Description;
import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class PostDto {


    private Long id;

    @NotEmpty
    @Size(min = 2,message = "Title should be at list more than 2 character")
    private String title;

    @NotEmpty(message = "Description may not be Empty")
    @Size(min = 4 ,message = "Dsc should be at list more than 2 character")
    private String description;

    @NotEmpty(message = "Content may not be Empty")
    private String content;
}

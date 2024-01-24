package com.example.todoprojecthomework.util;

import lombok.experimental.UtilityClass;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@UtilityClass
public class FileUploadUtil {
    private final String UPLOAD_DIRECTORY = "C:\\Users\\37494\\IdeaProjects\\myToDo\\uploadDirectory";

    public String uploadImg(Part part) throws IOException {
        String picName = null;
        if (part != null && part.getSize() > 0) {
            picName = System.currentTimeMillis() + "_" + part.getSubmittedFileName();
            part.write(UPLOAD_DIRECTORY + File.separator + picName);
        }
        return picName;
    }

}

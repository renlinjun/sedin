package com.sedin.uc.controller;

import com.sedin.util.ActResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.util.resources.cldr.tzm.CalendarData_tzm_Latn_MA;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuhan on 2017-11-02.
 */
@RestController
public class FileUploadController {

    /**
     * 文件上传根目录(在Spring的application.yml的配置文件中配置):<br>
     * web:
     *  upload-path: （jar包所在目录）/resources/static/
     */
    @Value("${web.upload-path}")
    private String webUploadPath;

    @Value("${web.url}")
    private String baseUrl;


    /**
     * 基于用户标识的头像上传
     * @param file 图片
     * @return
     */
    @PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActResult fileUpload(@RequestParam("file") MultipartFile file) {
        ActResult resultVo = new ActResult();
        if (!file.isEmpty()) {
            if (file.getContentType().contains("image")) {
                try {
                    String temp = File.separator + "images" + File.separator ;
                    // 获取图片的文件名
                    String fileName = file.getOriginalFilename();
                    // 获取图片的扩展名
                    String extensionName = StringUtils.substringAfter(fileName, ".");
                    // 新的图片文件名 = 获取时间戳+"."图片扩展名
                    String newFileName = String.valueOf(System.currentTimeMillis()) + "." + extensionName;

                    Calendar day =  Calendar.getInstance();

                    // 数据库保存的目录
                    String datdDirectory = temp.concat(String.valueOf(day.get(Calendar.YEAR))).concat(File.separator)
                            .concat(String.valueOf(day.get(Calendar.MONTH) + 1)).concat(File.separator)
                            .concat(String.valueOf(day.get(Calendar.DAY_OF_MONTH) )).concat(File.separator);
                    // 文件路径
                    String filePath = webUploadPath.concat(datdDirectory);

                    File dest = new File(filePath, newFileName);
                    if (!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    // 上传到指定目录
                    file.transferTo(dest);

                    // 将图片流转换进行BASE64加码
                    //BASE64Encoder encoder = new BASE64Encoder();
                    //String data = encoder.encode(file.getBytes());

                    // 将反斜杠转换为正斜杠
                    String data = baseUrl + datdDirectory.replaceAll("\\\\", "/") + newFileName;
                    resultVo.setData(data);
                    resultVo.setSuccess(true);
                } catch (IOException e) {
                    resultVo.setSuccess(false);
                }
            } else {
                resultVo.setSuccess(false);
            }
            return resultVo;
        } else {
            resultVo.setSuccess(false);
            return resultVo;
        }
    }
}

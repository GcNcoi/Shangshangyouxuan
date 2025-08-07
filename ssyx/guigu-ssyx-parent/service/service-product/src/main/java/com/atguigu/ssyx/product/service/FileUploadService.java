package com.atguigu.ssyx.product.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {

    /**
     * 文件上传
     * @param file
     * @return
     * @throws Exception
     */
    String fileUpload(MultipartFile file) throws Exception;

}

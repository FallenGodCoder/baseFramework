/* 
 * @I18N
 * Copyright (c) 2012, 2015, Together R&D Center.All rights reserved.
 * 
 * 注意：这段国际化注释禁止任何人修改！
 * 创建时间：2015-07-02 10:23:08
 * 通过com.tlearning.generator.i18n.ConverJava构建。
 * 
 * @I18N
 */
package com.tc.common.form;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileWebInput {

    MultipartHttpServletRequest multipartRequest;

    public FileWebInput(MultipartHttpServletRequest multipartRequest) {
        this.multipartRequest = multipartRequest;
    }

    public String[] getFileNames() {
        List list = new ArrayList();
        Iterator it = multipartRequest.getFileNames();
        while (it.hasNext()) {
            list.add(it.next());
        }

        int len = list.size();
        if (len < 1)
            return null;

        return (String[]) list.toArray(new String[len]);
    }

    public void saveFile(String name, File file) throws IllegalStateException, IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        mf.transferTo(file);
    }

    public void saveFile(String name, String fileName) throws IllegalStateException, IOException {
        saveFile(name, new File(fileName));
    }

    public InputStream getFileInputStream(String name) throws IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        return mf.getInputStream();
    }

    public byte[] getFileBytes(String name) throws IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        return mf.getBytes();
    }

    public MultipartFile getMultipartFile(String name) {
        return multipartRequest.getFile(name);
    }

    public void copyFileTo(String name, OutputStream out) throws IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        InputStream in = mf.getInputStream();
        IOUtils.copy(in, out);
    }

    public String copyFileToString(String name, String encoding) throws IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        InputStream in = mf.getInputStream();
        return IOUtils.toString(in, encoding);
    }

    public void copyFileToWriter(String name, Writer out, String encoding) throws IOException {
        MultipartFile mf = multipartRequest.getFile(name);
        InputStream in = mf.getInputStream();
        IOUtils.copy(in, out, encoding);
    }

}
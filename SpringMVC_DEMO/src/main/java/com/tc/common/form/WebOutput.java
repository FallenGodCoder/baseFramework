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
/**
 * 2007-10-26 ����12:53:59 created by Danny
 * syncweb
 * dannyzhu.com
 * Easy come easy go!
 * ^_^
 */
package com.tc.common.form;

import com.alibaba.fastjson.JSON;
import com.tc.common.utils.JsonUtils;
import com.tc.common.utils.RequestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Danny 2007-10-26
 */
public class WebOutput implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(WebOutput.class);

    private HttpServletResponse response;

    private HttpServletRequest request;

    public WebOutput(){

    }

    public WebOutput(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return this.response;
    }

    public void setCookie(String name, String value, int maxAge) {
        Cookie c = new Cookie(name, value);
        if (maxAge > 0)
            c.setMaxAge(maxAge);
        c.setPath("/");
        response.addCookie(c);
    }

    public void setCookies(Map cookieMap, int maxAge) {
        Iterator it = cookieMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            String key = (String) e.getKey();
            String value = (String) e.getValue();
            Cookie c = new Cookie(key, value);
            if (maxAge > 0)
                c.setMaxAge(maxAge);
            c.setPath("/");
            response.addCookie(c);
        }
    }

    public void sendFile(String file, String fileName, String contentType) throws IOException {
        sendFile(new File(file), fileName, contentType, false, false);
    }

    public void sendFile(File file, String fileName, String contentType, boolean lastModified, boolean attachment)
            throws IOException {
        sendFile(file, fileName, contentType, lastModified, attachment, 0, false);
    }

    public void sendFile(File file, String fileName, String contentType, boolean lastModified, boolean attachment,
                         long expire, boolean isPrivate) throws IOException {
        sendFile(file, fileName, contentType, lastModified, attachment, expire, isPrivate, false);
    }

    public void sendFile(byte[] bytes, String fileName, String contentType, boolean attachment,
                         long expire, boolean isPrivate, boolean enableRange) throws IOException {
        String fname = fileName;
        if (contentType == null) {
            String ctype = getContentTypeByFilename(fname);
            response.setContentType(ctype);
        } else {
            response.setContentType(contentType);
        }

        if (attachment) {
            String agent = request.getHeader("USER-AGENT");
            String fn = fname;
            if (StringUtils.isNotBlank(agent) && agent.contains("Safari")) {
                fn = new String(fname.getBytes("UTF-8"), "ISO8859-1");
            } else {
                fn = new String(fname.getBytes("GBK"), "iso8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");
        }

        //logger.debug("expire:{}", expire);
        if (expire > 0L) {
            StringBuilder cacheControl = new StringBuilder(400);
            cacheControl.append(isPrivate ? "private" : "public");
            cacheControl.append(",max-age=");
            cacheControl.append(expire);
            response.setHeader("Cache-Control", cacheControl.toString());
            response.setDateHeader("Expires", System.currentTimeMillis() + expire * 1000);
        }

        if (expire <= 0) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
        }

        long contentSize = bytes.length;
        long startFrom = -1L;
        long endFrom = -1L;

        if (enableRange) {
            response.addHeader("Accept-Ranges", "bytes");
            String range = request.getHeader("Range");
            if (logger.isDebugEnabled()) {
                logger.debug("Range=" + range + " conent_range=" + request.getHeader("Content-Range") + " contentSize="
                        + contentSize);
            }
            int[] ranges = RequestUtils.getRange(range);
            startFrom = (long) ranges[0];
            endFrom = (long) ranges[1];

            if (startFrom >= (contentSize - 1)) {
                startFrom = -1L;
            }

            if (startFrom < 0 && endFrom > 0) {
                if (endFrom <= contentSize) {
                    startFrom = contentSize - endFrom;
                    endFrom = contentSize - 1;
                } else {
                    endFrom = -1L;
                }
            }

            if (startFrom >= 0) {
                if (endFrom < 1 || endFrom >= contentSize) {
                    endFrom = contentSize - 1;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("range:{}-{}", startFrom, endFrom);
                }
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader("Content-Range", "bytes " + startFrom + "-" + endFrom + "/" + contentSize);
                response.setContentLength((int) (endFrom - startFrom + 1));
            }
        }

        response.setContentLength((int) contentSize);

        if (!"GET".equals(request.getMethod()) && !"POST".equals(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("request method is '{}', not send file", request.getMethod());
            }
            return;
        }

        InputStream in = new ByteArrayInputStream(bytes);
        if (startFrom >= 0) {
            if (startFrom > 0) {
                long stime = System.currentTimeMillis();
                in.skip(startFrom);
                if (logger.isDebugEnabled()) {
                    logger.debug("skip:{}", startFrom);
                    logger.debug("skip time:{}", (System.currentTimeMillis() - stime));
                }
            }
            OutputStream out = response.getOutputStream();
            copy(in, out, (int) (endFrom - startFrom + 1));
            in.close();
            out.flush();
            out.close();
        } else {
            OutputStream out = response.getOutputStream();
            copy(in, out, (int) contentSize);
            in.close();
            out.flush();
            out.close();
        }
    }


    public void sendFile(File file, String fileName, String contentType, boolean lastModified, boolean attachment,
                         long expire, boolean isPrivate, boolean enableRange) throws IOException {

        if (!file.exists() || !file.canRead()) {
            if (logger.isDebugEnabled()) {
                if (!file.exists()) {
                    logger.debug("file '{}' not found", file);
                } else {
                    logger.debug("file '{}' cannot read", file);
                }
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String fname = fileName;

        if (fileName == null) {
            fname = file.getName();
        }

        if (contentType == null) {
            String ctype = getContentTypeByFilename(fname);
            response.setContentType(ctype);
        } else {
            response.setContentType(contentType);
        }

        if (attachment) {
            String agent = request.getHeader("USER-AGENT");
            String fn = fname;
            if (StringUtils.isNotBlank(agent) && agent.contains("Safari")) {
                fn = new String(fname.getBytes("UTF-8"), "ISO8859-1");
            } else {
                fn = new String(fname.getBytes("GBK"), "iso8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fn + "\"");
        }

        if (lastModified) {
            long lm = file.lastModified();
            long ifModifiedSince = request.getDateHeader("If-Modified-Since");
            if (ifModifiedSince != -1 && (ifModifiedSince + 999) >= lm) {
                response.sendError(HttpServletResponse.SC_NOT_MODIFIED);
                return;
            }

            response.setDateHeader("Last-Modified", lm);
            response.setHeader("ETag", lm + "");
        }

        //logger.debug("expire:{}", expire);
        if (expire > 0L) {
            StringBuilder cacheControl = new StringBuilder(400);
            cacheControl.append(isPrivate ? "private" : "public");
            cacheControl.append(",max-age=");
            cacheControl.append(expire);
            response.setHeader("Cache-Control", cacheControl.toString());
            response.setDateHeader("Expires", System.currentTimeMillis() + expire * 1000);
        }

        if (!lastModified && expire <= 0) {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
        }

        long contentSize = file.length();
        long startFrom = -1L;
        long endFrom = -1L;

        if (enableRange) {
            response.addHeader("Accept-Ranges", "bytes");
            String range = request.getHeader("Range");
            if (logger.isDebugEnabled()) {
                logger.debug("Range=" + range + " conent_range=" + request.getHeader("Content-Range") + " contentSize="
                        + contentSize);
            }
            int[] ranges = RequestUtils.getRange(range);
            startFrom = (long) ranges[0];
            endFrom = (long) ranges[1];

            if (startFrom >= (contentSize - 1)) {
                startFrom = -1L;
            }

            if (startFrom < 0 && endFrom > 0) {
                if (endFrom <= contentSize) {
                    startFrom = contentSize - endFrom;
                    endFrom = contentSize - 1;
                } else {
                    endFrom = -1L;
                }
            }

            if (startFrom >= 0) {
                if (endFrom < 1 || endFrom >= contentSize) {
                    endFrom = contentSize - 1;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("range:{}-{}", startFrom, endFrom);
                }
                response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                response.setHeader("Content-Range", "bytes " + startFrom + "-" + endFrom + "/" + contentSize);
                response.setContentLength((int) (endFrom - startFrom + 1));
            }
        }

        response.setContentLength((int) contentSize);

        if (!"GET".equals(request.getMethod()) && !"POST".equals(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("request method is '{}', not send file", request.getMethod());
            }
            return;
        }

        InputStream in = FileUtils.openInputStream(file);
        if (startFrom >= 0) {
            if (startFrom > 0) {
                long stime = System.currentTimeMillis();
                in.skip(startFrom);
                if (logger.isDebugEnabled()) {
                    logger.debug("skip:{}", startFrom);
                    logger.debug("skip time:{}", (System.currentTimeMillis() - stime));
                }
            }
            OutputStream out = response.getOutputStream();
            copy(in, out, (int) (endFrom - startFrom + 1));
            in.close();
            out.flush();
            out.close();
        } else {
            OutputStream out = response.getOutputStream();
            copy(in, out, (int) contentSize);
            in.close();
            out.flush();
            out.close();
        }
    }

    private static int copy(InputStream in, OutputStream out, int length) throws IOException {

        if (length < 1)
            return -1;

        Assert.notNull(in, "No InputStream specified");
        Assert.notNull(out, "No OutputStream specified");

        try {
            int byteCount = 0;
            byte[] buffer = new byte[4096];
            int bytesRead;
            int writerBytes = 0;
            while ((bytesRead = in.read(buffer)) != -1) {

                if ((byteCount + bytesRead) > length) {
                    writerBytes = length - byteCount;
                } else {
                    writerBytes = bytesRead;
                }
                out.write(buffer, 0, writerBytes);
                byteCount += writerBytes;

                if (byteCount >= length) {
                    break;
                }

            }
            out.flush();
            return byteCount;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }
    }

    public void setContentType(String contentType) {
        response.setContentType(contentType);
    }

    public void setContentType(String contentType, String charset) {
        if (charset == null)
            response.setContentType(contentType);
        else
            response.setContentType(contentType + "; charset=" + charset);
    }

    public void print(String content) throws IOException {
        Writer out = response.getWriter();
        out.write(content);
    }

    public void copy(InputStream in) throws IOException {
        IOUtils.copy(in, response.getOutputStream());
    }

    public void copy(byte[] data) throws IOException {
        IOUtils.write(data, response.getOutputStream());
    }

    public void redirect(String url) throws IOException {
        response.sendRedirect(url);
    }

    public void toJson(Object obj) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        setContentType(CONTENT_TYPE_JSON, "UTF-8");
        //setContentType(CONTENT_TYPE_JS, "UTF-8");

        print(JsonUtils.toString(obj));
    }

    /**
     *  该方法存在问题
     * @param obj
     * @throws java.io.IOException
     */
    @Deprecated
    public void toJson(String rootName, Object obj) throws IOException {
        setContentType(CONTENT_TYPE_JS, "UTF-8");
        print(JsonUtils.toString(rootName, obj));
    }

    public void flush() throws IOException {
        response.flushBuffer();
    }
    public void setUserAgent(String name,String content){
        response.setHeader(name,content);
    }
    public static String getContentTypeByFilename(String fname) {
        int pos = StringUtils.lastIndexOf(fname, '.');
        String ctype = WebOutput._defaultMimeType;
        if (pos > 0) {
            String str = StringUtils.trimToEmpty(StringUtils.substring(fname, pos)).toLowerCase();
            String _type = WebOutput._defaultMimeTypes.get(str);
            if (_type != null)
                ctype = _type;
        }
        return ctype;
    }

    public final static Map<String, String> _defaultMimeTypes;

    public final static String _defaultMimeType;

    public final static String CONTENT_TYPE_JS = "application/x-javascript";

    public final static String CONTENT_TYPE_BIN = "application/octet-stream";

    public final static String CONTENT_TYPE_HTML = "text/html";

    public final static String CONTENT_TYPE_TXT = "text/plain";

    public final static String CONTENT_TYPE_XML = "text/xml";

    public final static String CONTENT_TYPE_JSON = "application/json";

    static {
        /**
         * Default extension to mime-type mapping. Content-Type:
         * application/octet-stream; name=exadel-license.jar
         * Content-Disposition: attachment; filename=exadel-license.jar
         */
        _defaultMimeType = "application/octet-stream";
        _defaultMimeTypes = new HashMap<String, String>();
        // _defaultMimeTypes.put("default", "application/octet-stream");
        _defaultMimeTypes.put(".aif", "audio/x-aiff");
        _defaultMimeTypes.put(".aiff", "audio/x-aiff");
        _defaultMimeTypes.put(".aifc", "audio/x-aiff");
        _defaultMimeTypes.put(".ai", "application/postscript");
        _defaultMimeTypes.put(".au", "audio/basic");
        _defaultMimeTypes.put(".asc", "text/plain");
        _defaultMimeTypes.put(".asf", "video/x-ms-asf");
        _defaultMimeTypes.put(".asx", "video/x-ms-asf");
        _defaultMimeTypes.put(".avi", "video/x-msvideo");
        _defaultMimeTypes.put(".appcache", "text/cache-manifest");
        _defaultMimeTypes.put(".bin", "application/octet-stream");
        _defaultMimeTypes.put(".bcpio", "application/x-bcpio");
        _defaultMimeTypes.put(".bmp", "image/bmp");
        _defaultMimeTypes.put(".class", "application/octet-stream");
        _defaultMimeTypes.put(".cpt", "application/mac-compactpro");
        _defaultMimeTypes.put(".css", "text/css");
        _defaultMimeTypes.put(".cpio", "application/x-cpio");
        _defaultMimeTypes.put(".csh", "application/x-csh");
        _defaultMimeTypes.put(".cdf", "application/x-netcdf");
        _defaultMimeTypes.put(".dms", "application/octet-stream");
        _defaultMimeTypes.put(".doc", "application/msword");
        _defaultMimeTypes.put(".dcr", "application/x-director");
        _defaultMimeTypes.put(".dir", "application/x-director");
        _defaultMimeTypes.put(".dxr", "application/x-director");
        _defaultMimeTypes.put(".dvi", "application/x-dvi");
        _defaultMimeTypes.put(".exe", "application/octet-stream");
        _defaultMimeTypes.put(".eps", "application/postscript");
        _defaultMimeTypes.put(".etx", "text/x-setext");
        _defaultMimeTypes.put(".gtar", "application/x-gtar");
        _defaultMimeTypes.put(".gif", "image/gif");
        _defaultMimeTypes.put(".gz", "application/octet-stream");
        _defaultMimeTypes.put(".hdml", "text/x-hdml");
        _defaultMimeTypes.put(".hqx", "application/mac-binhex40");
        _defaultMimeTypes.put(".html", "text/html");
        _defaultMimeTypes.put(".htm", "text/html");
        _defaultMimeTypes.put(".hdf", "application/x-hdf");
        _defaultMimeTypes.put(".ief", "image/ief");
        _defaultMimeTypes.put(".ice", "x-conference/x-cooltalk");
        _defaultMimeTypes.put(".js", "application/x-javascript");
        _defaultMimeTypes.put(".jpeg", "image/jpeg");
        _defaultMimeTypes.put(".jpg", "image/jpeg");
        _defaultMimeTypes.put(".jpe", "image/jpeg");
        _defaultMimeTypes.put(".kar", "audio/midi");
        _defaultMimeTypes.put(".latex", "application/x-latex");
        _defaultMimeTypes.put(".lha", "application/octet-stream");
        _defaultMimeTypes.put(".lhz", "application/octet-stream");
        _defaultMimeTypes.put(".mid", "audio/midi");
        _defaultMimeTypes.put(".mpeg", "video/mpeg");
        _defaultMimeTypes.put(".mpg", "video/mpeg");
        _defaultMimeTypes.put(".mpe", "video/mpeg");
        _defaultMimeTypes.put(".mov", "video/quicktime");
        _defaultMimeTypes.put(".movie", "video/x-sgi-movie");
        _defaultMimeTypes.put(".mpga", "audio/mpeg");
        _defaultMimeTypes.put(".mp2", "audio/mpeg");
        _defaultMimeTypes.put(".mp3", "audio/mpeg");
        _defaultMimeTypes.put(".man", "application/x-troff-man");
        _defaultMimeTypes.put(".me", "application/x-troff-me");
        _defaultMimeTypes.put(".ms", "application/x-troff-ms");
        _defaultMimeTypes.put(".nc", "application/x-netcdf");
        _defaultMimeTypes.put(".oda", "application/oda");
        _defaultMimeTypes.put(".pdf", "application/pdf");
        _defaultMimeTypes.put(".ps", "application/postscript");
        _defaultMimeTypes.put(".ppt", "application/vnd.ms-powerpoint");
        _defaultMimeTypes.put(".png", "image/png");
        _defaultMimeTypes.put(".pgn", "application/x-chess-pgn");
        _defaultMimeTypes.put(".pnm", "image/x-portable-anymap");
        _defaultMimeTypes.put(".pbm", "image/x-portable-bitmap");
        _defaultMimeTypes.put(".pgm", "image/x-portable-graymap");
        _defaultMimeTypes.put(".ppm", "image/x-portable-pixmap");
        _defaultMimeTypes.put(".qt", "video/quicktime");
        _defaultMimeTypes.put(".rtf", "application/rtf");
        _defaultMimeTypes.put(".ram", "audio/x-pn-realaudio");
        _defaultMimeTypes.put(".rm", "audio/x-pn-realaudio");
        _defaultMimeTypes.put(".rpm", "audio/x-pn-realaudio-plugin");
        _defaultMimeTypes.put(".ra", "audio/x-realaudio");
        _defaultMimeTypes.put(".ras", "image/x-cmu-raster");
        _defaultMimeTypes.put(".rgb", "image/x-rgb");
        _defaultMimeTypes.put(".rtx", "text/richtext");
        _defaultMimeTypes.put(".rtf", "text/rtf");
        _defaultMimeTypes.put(".smi", "application/smil");
        _defaultMimeTypes.put(".smil", "application/smil");
        _defaultMimeTypes.put(".sml", "application/smil");
        _defaultMimeTypes.put(".skp", "application/x-koan");
        _defaultMimeTypes.put(".skd", "application/x-koan");
        _defaultMimeTypes.put(".skt", "application/x-koan");
        _defaultMimeTypes.put(".skm", "application/x-koan");
        _defaultMimeTypes.put(".src", "application/x-wais-source");
        _defaultMimeTypes.put(".sh", "application/x-sh");
        _defaultMimeTypes.put(".shar", "application/x-shar");
        _defaultMimeTypes.put(".swf", "application/x-shockwave-flash");
        _defaultMimeTypes.put(".sit", "application/x-stuffit");
        _defaultMimeTypes.put(".spl", "application/x-futuresplash");
        _defaultMimeTypes.put(".sv4cpio", "application/x-sv4cpio");
        _defaultMimeTypes.put(".sv4crc", "application/x-sv4crc");
        _defaultMimeTypes.put(".snd", "audio/basic");
        _defaultMimeTypes.put(".sgml", "text/sgml");
        _defaultMimeTypes.put(".sgm", "text/sgml");
        _defaultMimeTypes.put(".tgz", "application/octet-stream");
        _defaultMimeTypes.put(".tar", "application/x-tar");
        _defaultMimeTypes.put(".tcl", "application/x-tcl");
        _defaultMimeTypes.put(".tex", "application/x-tex");
        _defaultMimeTypes.put(".texinfo", "application/x-texinfo");
        _defaultMimeTypes.put(".texi", "application/x-texinfo");
        _defaultMimeTypes.put(".t", "application/x-troff");
        _defaultMimeTypes.put(".tr", "application/x-troff");
        _defaultMimeTypes.put(".roff", "application/x-troff");
        _defaultMimeTypes.put(".tiff", "image/tiff");
        _defaultMimeTypes.put(".tif", "image/tiff");
        _defaultMimeTypes.put(".txt", "text/plain");
        _defaultMimeTypes.put(".tsv", "text/tab-separated-values");
        _defaultMimeTypes.put(".ustar", "application/x-ustar");
        _defaultMimeTypes.put(".vcd", "application/x-cdlink");
        _defaultMimeTypes.put(".vrml", "model/vrml");
        _defaultMimeTypes.put(".wav", "audio/x-wav");
        _defaultMimeTypes.put(".wax", "audio/x-ms-wax");
        _defaultMimeTypes.put(".wrl", "model/vrml");
        _defaultMimeTypes.put(".wma", "audio/x-ms-wma");
        _defaultMimeTypes.put(".wml", "text/vnd.wap.wml");
        _defaultMimeTypes.put(".wmls", "text/vnd.wap.wmlscript");
        _defaultMimeTypes.put(".wmlc", "application/vnd.wap.wmlc");
        _defaultMimeTypes.put(".wmlsc", "application/vnd.wap.wmlscript");
        _defaultMimeTypes.put(".wbmp", "image/vnd.wap.wbmp");
        _defaultMimeTypes.put(".xls", "application/vnd.ms-excel");
        _defaultMimeTypes.put(".xbm", "image/x-xbitmap");
        _defaultMimeTypes.put(".xpm", "image/x-xpixmax");
        _defaultMimeTypes.put(".xwd", "image/x-xwindowdump");
        _defaultMimeTypes.put(".xml", "text/xml");
        _defaultMimeTypes.put(".zip", "application/zip");
        _defaultMimeTypes.put(".z", "application/octet-stream");
    }
}
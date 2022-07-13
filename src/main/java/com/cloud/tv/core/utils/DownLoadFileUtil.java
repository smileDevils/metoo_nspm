package com.cloud.tv.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DownLoadFileUtil {

    public static void main(String[] args) {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        String beginDate = sdf1.format(new Date());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;

        try {
            date = sdf.parse(beginDate);
            Calendar cd = Calendar.getInstance();
            cd.setTime(date);
            cd.add(5, -1);
            System.out.println(sdf.format(cd.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(DownLoadFileUtil.class);

    public boolean downloadZip(File file, HttpServletResponse response) {
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            boolean var5 = true;
            return var5;
        } catch (IOException var9) {
            logger.error("downloadZip异常", var9);
            boolean var3 = false;
            return var3;
        } finally {
            ;
        }
    }
}
